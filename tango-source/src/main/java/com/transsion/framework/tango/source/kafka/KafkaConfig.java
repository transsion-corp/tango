package com.transsion.framework.tango.source.kafka;

import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Author mengqi.lv
 * @Date 2022/3/1
 * @Version 1.0
 **/
public class KafkaConfig {

    public static final String CONFIG_KEY_KAFKA_ADDRESS = "kafka.address";
    public static final String CONFIG_KEY_CONSUME_LOG_TOPIC = "kafka.log.event.output";
    public static final String CONFIG_KEY_CONSUME_LOG_EVENT_TOPIC = "kafka.log.app.event.output";
    public static final String CONFIG_KEY_CONSUME_TRACE_TOPIC = "kafka.trace.event.output";

    public static final String CONFIG_KEY_KAFKA_GROUP_ID = "kafka.group.id";
    public static final String CONFIG_KEY_KAFKA_ENABLE_AUTO_COMMIT = "kafka.enable.auto.commit";
    public static final String CONFIG_KEY_KAFKA_AUTO_COMMIT_INTERVAL_MS = "kafka.auto.commit.interval.ms";
    public static final String CONFIG_KEY_KAFKA_SESSION_TIMEOUT_MS = "kafka.session.timeout.ms";
    public static final String CONFIG_KEY_KAFKA_AUTO_OFFSET_RESET = "kafka.auto.offset.reset";

    private String address;
    private String topic;
    private String groupId;
    private boolean autoCommit;
    private int autoCommitMillis;
    private int sessionTimeoutMillis;
    private String autoOffsetReset;
    private int maxPollRecords;
    private int fetchMaxBytes;
    private String jksFile;
    private String loginFile;

    public String getAddress() {
        return address;
    }

    public String getTopic() {
        return topic;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public boolean isAutoCommit() {
        return autoCommit;
    }

    public void setAutoCommit(boolean autoCommit) {
        this.autoCommit = autoCommit;
    }

    public int getAutoCommitMillis() {
        return autoCommitMillis;
    }

    public void setAutoCommitMillis(int autoCommitMillis) {
        this.autoCommitMillis = autoCommitMillis;
    }

    public int getSessionTimeoutMillis() {
        return sessionTimeoutMillis;
    }

    public void setSessionTimeoutMillis(int sessionTimeoutMillis) {
        this.sessionTimeoutMillis = sessionTimeoutMillis;
    }

    public String getAutoOffsetReset() {
        return autoOffsetReset;
    }

    public void setAutoOffsetReset(String autoOffsetReset) {
        this.autoOffsetReset = autoOffsetReset;
    }

    public int getMaxPollRecords() {
        return maxPollRecords;
    }

    public int getFetchMaxBytes() {
        return fetchMaxBytes;
    }

    public String getJksFile() {
        return jksFile;
    }

    public void setJksFile(String jksFile) {
        this.jksFile = jksFile;
    }

    public String getLoginFile() {
        return loginFile;
    }

    public void setLoginFile(String loginFile) {
        this.loginFile = loginFile;
    }

    public void setMaxPollRecords(int maxPollRecords) {
        this.maxPollRecords = maxPollRecords;
    }

    public void setFetchMaxBytes(int fetchMaxBytes) {
        this.fetchMaxBytes = fetchMaxBytes;
    }

    public KafkaConfig() {
    }

    public Properties toProperties() {
        Properties props = new Properties();
        Map<String, String> serverProperties = getKafkaServerProperties();
        for (Map.Entry<String, String> entry : serverProperties.entrySet()) {
            props.put(entry.getKey(), entry.getValue());
        }
        props.put("group.id", groupId);
        props.put("enable.auto.commit", autoCommit + "");
        props.put("auto.commit.interval.ms", autoCommitMillis + "");
        props.put("session.timeout.ms", sessionTimeoutMillis + "");
        props.put("auto.offset.reset", autoOffsetReset);
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());
        props.put("key.serializer", StringSerializer.class.getName());
        props.put("value.serializer", StringSerializer.class.getName());

        // default is 500
        props.put("max.poll.records", maxPollRecords + "");
        // default is 50M
        props.put("fetch.max.bytes", fetchMaxBytes + "");

        return props;
    }

    // public for test
    protected Map<String, String> getKafkaServerProperties() {
        Map<String, String> properties = new HashMap<>();
        properties.put("bootstrap.servers", address);
        if (StringUtils.isNotEmpty(jksFile)) {
            System.setProperty("java.security.auth.login.config", loginFile);
            properties.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, jksFile);
            properties.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, "KafkaOnsClient");
            properties.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_SSL");
            properties.put(SaslConfigs.SASL_MECHANISM, "PLAIN");
            properties.put(SslConfigs.SSL_ENDPOINT_IDENTIFICATION_ALGORITHM_CONFIG, "");
        }

        return properties;
    }
}
