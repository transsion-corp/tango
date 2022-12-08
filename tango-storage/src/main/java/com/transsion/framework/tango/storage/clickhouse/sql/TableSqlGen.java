package com.transsion.framework.tango.storage.clickhouse.sql;

import com.transsion.framework.tango.common.Utility;
import com.transsion.framework.tango.common.property.*;
import com.transsion.framework.tango.storage.clickhouse.ClickhouseTableMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author mengqi.lv
 * @Date 2022/1/19
 * @Version 1.0
 **/
public class TableSqlGen implements SqlGen {

    private static final long nano_second = 1000 * 1000 * 1000;

    private ClickhouseTableMeta meta;
    private String storageCluster;

    public ClickhouseTableMeta getMeta() {
        return meta;
    }

    public void setMeta(ClickhouseTableMeta meta) {
        this.meta = meta;
    }

    public String getStorageCluster() {
        return storageCluster;
    }

    public void setStorageCluster(String storageCluster) {
        this.storageCluster = storageCluster;
    }

    public static List<String> genSqls(ClickhouseTableMeta tableMeta) {
        ClusterTableSqlGen sqlGen = new ClusterTableSqlGen();
        sqlGen.setMeta(tableMeta);
        return sqlGen.gen();
    }

    @Override
    public List<String> gen() {
        return null;
    }

    private static String genTableName(ClickhouseTableMeta meta) {
        return meta.getDb() + "." + meta.getTable() + "_local";
    }

    private static String genTableClusterName(ClickhouseTableMeta meta) {
        return meta.getDb() + "." + meta.getTable() + "_all";
    }

    private static class ClusterTableSqlGen extends TableSqlGen {
        @Override
        public List<String> gen() {
            List<String> sqls = new ArrayList<>(2);

            sqls.add(genLocalTable());
            sqls.add(genDistributedTable());

            return sqls;
        }

        private String genLocalTable() {
            ClickhouseTableMeta meta = getMeta();
            StringBuilder sb = new StringBuilder();
            sb.append("create table if not exists ");
            sb.append(genTableName(meta));
            sb.append(" on cluster '");
            sb.append(meta.getStorageCluster());
            sb.append("' (");

            boolean first = true;

            for (Property p : meta.getProperties()) {
                String name = p.getName();
                if (!first) {
                    sb.append(", ");
                } else {
                    first = false;
                }

                sb.append("`");
                sb.append(name);
                sb.append("` ");
                String type = mapType(p.getType());
                if (type.equals("Enum")) {
                    sb.append(getEnum(meta, name));
                } else if (type.equals("Map")) {
                    sb.append("Map(").append("String, ").
                            append(TableSqlGen.mapType(p.getSubType())).append(")");
                } else if (type.equals("Array")) {
                    sb.append("Array(").append(TableSqlGen.mapType(p.getSubType())).append(")");
                } else {
                    sb.append(type);
                }
            }
            sb.append(")");
            sb.append(" engine = ReplicatedMergeTree('/clickhouse/tables/");
            sb.append("{shard}/");
            sb.append(meta.getDb()).append("/");
            sb.append(meta.getTable());
            sb.append("_local', '{replica}");
            sb.append("') partition by toYYYYMMDD(toDate(intDiv(`timestamp`, 1000000000)))"); // in nanoseconds
            if (Utility.isEmpty(meta.getOrderBy())) {
                sb.append(" order by (`timestamp`)");
            } else {
                sb.append(" order by ").append(genOrderBy(meta.getOrderBy()));
            }
            if (getMeta().ttl() > 0) {
                sb.append(" ttl toDate(intDiv(`timestamp`, 1000000000)) + interval ").append(getMeta().ttl()).append(" second");
            }
            sb.append(" SETTINGS storage_policy = '").append(meta.getStoragePolicy()).append("';");
            return sb.toString();
        }

        private String genOrderBy(List<Property> orderBy) {
            StringBuilder sb = new StringBuilder();
            sb.append("(`timestamp`");
            for (Property p : orderBy) {
                sb.append(", `").append(p.getName()).append("`");
            }
            sb.append(")");
            return sb.toString();
        }

        private String genDistributedTable() {
            ClickhouseTableMeta meta = getMeta();
            StringBuilder sb = new StringBuilder();
            sb.append("create table if not exists ");
            sb.append(genTableClusterName(meta));
            sb.append(" on cluster '");
            sb.append(meta.getStorageCluster());
            sb.append("' as ");
            sb.append(TableSqlGen.genTableName(meta));
            sb.append(" engine = Distributed('");
            sb.append(meta.getStorageCluster());
            sb.append("', '");
            sb.append(meta.getDb());
            sb.append("', '");
            sb.append(meta.getTable());
            sb.append("_local");
            sb.append("', rand());");
            return sb.toString();
        }
    }

    private static String getEnum(ClickhouseTableMeta meta, String fieldName) {
        PropertyEnum propertyEnum = PropertyUtils.findPropertyEnumByName(meta.getPropertyEnums(), fieldName);
        StringBuilder sb = new StringBuilder();
        sb.append("Enum(");
        boolean first = true;
        for (EnumValue value : propertyEnum.getEnumValues()) {
            if (first) {
                first = false;
            } else {
                sb.append(", ");
            }
            sb.append("'").append(value.getName()).append("' = ").append(value.getValue());
        }
        sb.append(")");
        return sb.toString();
    }

    private static String mapType(NamedType type) {
        switch (type) {
            case DATETIME:
                return "Datetime";
            case LONG:
                return "Int64";
            case INT:
                return "Int32";
            case DOUBLE:
                return "Float64";
            case STRING:
                return "String";
            case BOOLEAN:
                return "UInt8";
            case ENUM:
                return "Enum";
            case MAP:
                return "Map";
            case LIST:
                return "Array";
            default:
                // TODO
                throw new RuntimeException("Unsupported NamedType: " + type);
        }
    }
}
