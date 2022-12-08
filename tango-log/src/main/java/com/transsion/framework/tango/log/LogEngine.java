package com.transsion.framework.tango.log;

import com.transsion.framework.tango.common.LoggingEventHandler;
import com.transsion.framework.tango.common.config.StatefulConfigManager;
import com.transsion.framework.tango.common.exception.InitializeException;
import com.transsion.framework.tango.core.data.Data;
import com.transsion.framework.tango.core.data.meta.DataMetaRegistry;
import com.transsion.framework.tango.core.data.meta.DefaultDataMetaRegistry;
import com.transsion.framework.tango.core.engine.CollectorEngine;
import com.transsion.framework.tango.core.engine.DefaultCollectorEngine;
import com.transsion.framework.tango.core.engine.DefaultDumperEngine;
import com.transsion.framework.tango.core.engine.DumperEngine;
import com.transsion.framework.tango.core.source.DataProducer;
import com.transsion.framework.tango.core.view.View;
import com.transsion.framework.tango.log.adapter.LogClickhouseAdapter;
import com.transsion.framework.tango.log.adapter.LogTranslator;
import com.transsion.framework.tango.source.SingleThreadProducer;
import com.transsion.framework.tango.source.kafka.KafkaConfig;
import com.transsion.framework.tango.source.kafka.KafkaConfigBuilder;
import com.transsion.framework.tango.source.kafka.KafkaPoller;
import com.transsion.framework.tango.storage.clickhouse.ClickhouseEndpoint;
import com.transsion.framework.tango.storage.clickhouse.ClickhouseStorage;
import com.transsion.framework.tango.storage.clickhouse.ClickhouseStorageAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static com.transsion.framework.tango.source.kafka.KafkaConfig.*;

/**
 * @Author mengqi.lv
 * @Date 2022/8/1
 * @Version 1.0
 **/
public class LogEngine {
    public static final String LOG_META_REPO = "tango.log.meta";
    public static final String CONFIG_KEY_CLICKHOUSE_URLS = "clickhouse.urls";

    private static final Logger logger = LoggerFactory.getLogger(LogEngine.class);

    private final CollectorEngine collectorEngine;
    private final DumperEngine dumperEngine;

    private final StatefulConfigManager configManager;
    private final DataProducer logProducer;
    private final DataMetaRegistry metaRegistry;

    private volatile boolean running = false;

    public LogEngine(StatefulConfigManager configManager, DataProducer producer) {
        this.metaRegistry = new DefaultDataMetaRegistry();
        this.configManager = configManager;
        ClickhouseStorage storage = createClickhouseStorage();
        ClickhouseStorageAdapter adapter = new LogClickhouseAdapter();
        this.collectorEngine = new DefaultCollectorEngine(storage, adapter, metaRegistry, null);
        this.dumperEngine = new DefaultDumperEngine(storage, adapter, metaRegistry);
        this.logProducer = producer;
    }

    public LogEngine(StatefulConfigManager configManager) {
        this.metaRegistry = new DefaultDataMetaRegistry();
        ClickhouseStorage storage = createClickhouseStorage();
        ClickhouseStorageAdapter adapter = new LogClickhouseAdapter();
        this.collectorEngine = new DefaultCollectorEngine(storage, adapter, metaRegistry, null);
        this.dumperEngine = new DefaultDumperEngine(storage, adapter, metaRegistry);
        this.configManager = configManager;
        this.logProducer = new SingleThreadProducer(new KafkaPoller("KafkaLogPoller", getKafkaConfig(), new LogTranslator(metaRegistry)));
        this.logProducer.setCollectorEngine(collectorEngine);
    }

    public Data queryLog(View view) {
        return this.dumperEngine.dump(view);
    }

    public void addData(Data data) {
        this.collectorEngine.addData(data);
    }

    public void start() {
        if (!running) {
            registerLogMetaListener();
            this.logProducer.start();
            running = true;
        }
    }

    public void stop() {
        if (running) {
            this.logProducer.stop();
            running = false;
        }
    }

    private void registerLogMetaListener() {
        this.configManager.registerRepoConfigBuilder(LOG_META_REPO, new LogMetaConfigBuilder());
        this.configManager.addRepoListener(LOG_META_REPO, new LogMetaConfigListener(this.metaRegistry));
    }

    private KafkaConfig getKafkaConfig() {
        return new KafkaConfigBuilder().setAddress(configManager.getStringConfig(CONFIG_KEY_KAFKA_ADDRESS, "localhost:9092"))
                .setTopic(configManager.getStringConfig(CONFIG_KEY_CONSUME_LOG_TOPIC, "tango_topic_log"))
                .setGroupId(configManager.getStringConfig(CONFIG_KEY_KAFKA_GROUP_ID, "tango-server-in-log"))
                .setAutoCommit(Boolean.valueOf(configManager.getStringConfig(CONFIG_KEY_KAFKA_ENABLE_AUTO_COMMIT, "true")))
                .setAutoCommitMillis(configManager.getIntConfig(CONFIG_KEY_KAFKA_AUTO_COMMIT_INTERVAL_MS, 1000))
                .setSessionTimeoutMillis(configManager.getIntConfig(CONFIG_KEY_KAFKA_SESSION_TIMEOUT_MS, 30 * 1000))
                .setAutoOffsetReset(configManager.getStringConfig(CONFIG_KEY_KAFKA_AUTO_OFFSET_RESET, "earliest"))
                .setSslConfig(configManager.getStringConfig(CONFIG_KEY_KAFKA_ADDRESS + ".ssl", ""))
                .build();
    }

    private ClickhouseStorage createClickhouseStorage() {
        String urls = configManager.getStringConfig(CONFIG_KEY_CLICKHOUSE_URLS, "localhost:8123");
        if (urls == null) {
            throw new InitializeException("Failed to get clickhouse urls in config.");
        }
        logger.info("Clickhouse endpoints: " + urls);
        List<ClickhouseEndpoint> endpointList = new ArrayList<>(urls.length());
        for (String s : urls.split(";")) {
            ClickhouseEndpoint endpoint = new ClickhouseEndpoint();
            String[] hostAndPort = s.split(":");
            endpoint.setHost(hostAndPort[0]);
            endpoint.setPort(Integer.parseInt(hostAndPort[1]));
            endpointList.add(endpoint);
        }
        return new ClickhouseStorage(endpointList, new LoggingEventHandler(),
                configManager.getIntConfig("logengine.storage.flush.seconds", 10),
                configManager.getIntConfig("logengine.storage.max_batch", 100 * 1000));
    }
}
