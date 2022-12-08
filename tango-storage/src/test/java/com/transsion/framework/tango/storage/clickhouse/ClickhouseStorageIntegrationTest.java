package com.transsion.framework.tango.storage.clickhouse;

import com.google.gson.Gson;
import com.transsion.framework.tango.common.property.NamedType;
import com.transsion.framework.tango.common.property.Property;
import com.transsion.framework.tango.common.property.PropertyEnum;
import com.transsion.framework.tango.common.property.PropertyUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Author mengqi.lv
 * @Date 2022/10/24
 * @Version 1.0
 **/
public class ClickhouseStorageIntegrationTest {

    private static ClickhouseStorage storage;

    @BeforeAll
    static void setup() {
        ClickhouseEndpoint endpoint = new ClickhouseEndpoint();
        endpoint.setHost("localhost");
        endpoint.setPort(8123);

        storage = new ClickhouseStorage(Arrays.asList(endpoint), null);
    }

    @Disabled
    @Test
    void testTableSetup() {
        ClickhouseTableMeta tableMeta = new ClickhouseTableMeta("infra", "ck_test_abc");
        List<Property> propertyList = new ArrayList<>(8);
        propertyList.add(new Property("timestamp", NamedType.LONG));
        propertyList.add(new Property("service_name", NamedType.STRING));
        propertyList.add(new Property("path", NamedType.STRING));
        propertyList.add(new Property("level", NamedType.ENUM));
        propertyList.add(new Property("process_number", NamedType.INT));
        propertyList.add(new Property("body", NamedType.STRING));
        propertyList.add(new Property(null, "attributes", NamedType.MAP, NamedType.STRING));
        tableMeta.setProperties(propertyList);
        List<Property> orderBy = new ArrayList<>(2);
        orderBy.add(PropertyUtils.findPropertyByName(propertyList, "service_name"));
        orderBy.add(PropertyUtils.findPropertyByName(propertyList, "path"));
        tableMeta.setOrderBy(orderBy);
        tableMeta.setStorageCluster("gateway_cluster");
        tableMeta.setTtlSeconds(3600 * 24 * 2);
        List<PropertyEnum> enums = new ArrayList<>(1);
        enums.add(PropertyEnum.fromJson(new Gson().fromJson("{\"name\":\"level\",\"values\":{\"DEBUG\":5,\"INFO\":9,\"WARN\":13,\"ERROR\":17,\"FATAL\":21}}",
                Map.class), propertyList));
        tableMeta.setPropertyEnums(enums);

        this.storage.newStore(tableMeta);
    }
}
