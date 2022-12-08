package com.transsion.framework.tango.source.kafka;

import com.google.gson.Gson;
import com.transsion.framework.tango.common.exception.InitializeException;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.Base64;
import java.util.Map;

public class KafkaConfigBuilder {
    private String address;
    private String topic;
    private String groupId;
    private boolean autoCommit = true;
    private int autoCommitMillis = 1000;
    private int sessionTimeoutMillis = 30000;
    private String autoOffsetReset = "earliest";
    private int maxPollRecords = 500;
    private int fetchMaxBytes = 52428800;
    private String authDirPath = "";
    private String loginFilePath = "";
    private String jksFilePath = "";
    private String loginBase64 = "";
    private String jksBase64 = "";

    public KafkaConfigBuilder setAddress(String address) {
        this.address = address;
        return this;
    }

    public KafkaConfigBuilder setTopic(String topic) {
        this.topic = topic;
        return this;
    }

    public KafkaConfigBuilder setGroupId(String groupId) {
        this.groupId = groupId;
        return this;
    }

    public KafkaConfigBuilder setAutoCommit(boolean autoCommit) {
        this.autoCommit = autoCommit;
        return this;
    }

    public KafkaConfigBuilder setAutoCommitMillis(int autoCommitMillis) {
        this.autoCommitMillis = autoCommitMillis;
        return this;
    }

    public KafkaConfigBuilder setSessionTimeoutMillis(int sessionTimeoutMillis) {
        this.sessionTimeoutMillis = sessionTimeoutMillis;
        return this;
    }

    public KafkaConfigBuilder setAutoOffsetReset(String autoOffsetReset) {
        this.autoOffsetReset = autoOffsetReset;
        return this;
    }

    public KafkaConfigBuilder setMaxPollRecords(int maxPollRecords) {
        this.maxPollRecords = maxPollRecords;
        return this;
    }

    public KafkaConfigBuilder setFetchMaxBytes(int fetchMaxBytes) {
        this.fetchMaxBytes = fetchMaxBytes;
        return this;
    }

    public KafkaConfigBuilder setSslConfig(String json) {
        if (StringUtils.isNotEmpty(json)) {
            Map<String, Object> sslConfMap = new Gson().fromJson(json, Map.class);
            this.authDirPath = (String) sslConfMap.getOrDefault("authFileDir", "/tmp/tango");
            this.loginFilePath = authDirPath + File.separator + sslConfMap.getOrDefault("loginFile", "kafka_client_jaas.conf");
            this.jksFilePath = authDirPath + File.separator + sslConfMap.getOrDefault("jksFile", "kafka.client.truststore.jks");
            this.jksBase64 = (String) sslConfMap.getOrDefault("jks", "");
            this.loginBase64 = (String) sslConfMap.getOrDefault("login", "");
        }
        return this;
    }

    public KafkaConfig build() {
        createAuthFiles();

        KafkaConfig kafkaConfig = new KafkaConfig();
        kafkaConfig.setAddress(address);
        kafkaConfig.setTopic(topic);
        kafkaConfig.setGroupId(groupId);
        kafkaConfig.setAutoCommit(autoCommit);
        kafkaConfig.setAutoCommitMillis(autoCommitMillis);
        kafkaConfig.setSessionTimeoutMillis(sessionTimeoutMillis);
        kafkaConfig.setAutoOffsetReset(autoOffsetReset);
        kafkaConfig.setMaxPollRecords(maxPollRecords);
        kafkaConfig.setFetchMaxBytes(fetchMaxBytes);
        kafkaConfig.setJksFile(jksFilePath);
        kafkaConfig.setLoginFile(loginFilePath);
        return kafkaConfig;
    }

    private void createAuthFiles() {
        if (StringUtils.isEmpty(jksFilePath)) {
            return ;
        }
        File authDir = new File(authDirPath);
        authDir.mkdirs();
        File loginFile = new File(loginFilePath);
        if (loginFile.exists()) {
            loginFile.delete();
        }
        File jksFile = new File(jksFilePath);
        if (jksFile.exists()) {
            jksFile.delete();
        }

        FileWriter loginWriter = null;
        FileOutputStream jksOutputStream = null;
        try {
            loginWriter = new FileWriter(loginFilePath);
            jksOutputStream = new FileOutputStream(jksFilePath);

            loginWriter.write(new String(Base64.getDecoder().decode(loginBase64)));
            loginWriter.flush();
            jksOutputStream.write(Base64.getDecoder().decode(jksBase64));
            jksOutputStream.flush();
        } catch (Exception e) {
            throw new InitializeException("Write to auth file error.", e);
        } finally {
            if (loginWriter != null) {
                try {
                    loginWriter.close();
                } catch (Exception e) {
                    ;
                }
            }
            if (jksOutputStream != null) {
                try {
                    jksOutputStream.close();
                } catch (Exception e) {
                    ;
                }
            }
        }
    }
}