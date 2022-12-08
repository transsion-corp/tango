package com.transsion.framework.tango.log;

import com.transsion.framework.tango.common.config.InMemoryConfigManager;
import com.transsion.framework.tango.core.data.Data;
import com.transsion.framework.tango.core.source.DataProducer;
import com.transsion.framework.tango.log.data.LogData;
import com.transsion.framework.tango.log.view.LogView;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author mengqi.lv
 * @Date 2022/10/22
 * @Version 1.0
 **/
public class LogEngineIntegrationTest {

    private static InMemoryConfigManager configManager = new InMemoryConfigManager();
    private static LogEngine engine;

    @BeforeAll
    public static void setup() {
        configManager.addConfig("application", "logengine.storage.max_batch", "1");
        initialConfig();
        engine = new LogEngine(configManager, Mockito.mock(DataProducer.class));
        engine.start();
    }

    @Disabled
    @Test
    public void testWriteToClickhouse() throws InterruptedException {
        LogData logData = newLogData();
        engine.addData(logData);
        engine.addData(logData);
    }

    @Test
    public void testQueryLogs() {
        LogView view = new LogView("infra", "ck_test_abc");
        view.setStartTimeNanos(System.currentTimeMillis() * 1000 * 1000 - 3600L * 1000 * 1000 * 1000 * 8);
        Data data = engine.queryLog(view);
        Assertions.assertNotNull(data);
    }

    private static void initialConfig() {
        String logMetaString = "{\"biz\":\"infra\",\"scenario\":\"ck_test_abc\",\"properties\":[{\"name\":\"service_name\",\"type\":\"string\",\"subtype\":\"\"},{\"name\":\"process_number\",\"type\":\"int\",\"subtype\":\"\"},{\"name\":\"level\",\"type\":\"enum\",\"subtype\":\"\"},{\"name\":\"timestamp\",\"type\":\"long\",\"subtype\":\"\"},{\"name\":\"body\",\"type\":\"string\",\"subtype\":\"\"},{\"name\":\"path\",\"type\":\"string\",\"subtype\":\"\"},{\"name\":\"attributes\",\"type\":\"map\",\"subtype\":\"string\"}],\"order_by\":[\"service_name\",\"path\"],\"ttl\":2592000,\"enums\":[{\"name\":\"level\",\"values\":{\"DEBUG\":5,\"INFO\":9,\"WARN\":13,\"ERROR\":17,\"FATAL\":21}}]}";
        System.out.println(logMetaString);
        configManager.addConfig(LogEngine.LOG_META_REPO, "cicd_demo_meta", logMetaString);
        configManager.addConfig("application", LogEngine.CONFIG_KEY_CLICKHOUSE_URLS, "localhost:8123");
    }

    private LogData newLogData() {
        LogData data = new LogData("infra", "ck_test_abc");
        Map<String, Object> properties = new HashMap<>();
        properties.put("service_name", "demo");
        properties.put("process_number", 234);
        properties.put("level", "INFO");
        properties.put("path", "/health");
        properties.put("timestamp", System.currentTimeMillis() * 1000 * 1000);
        properties.put("body", "this is my log message");

        Map<String, String> attributes = new HashMap<>();
        attributes.put("type", "type_a");
        properties.put("attributes", attributes);

        data.setProperties(properties);
        return data;
    }
}
