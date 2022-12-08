package com.transsion.framework.tango.storage.clickhouse;

import java.io.Serializable;

/**
 * @Author mengqi.lv
 * @Date 2022/3/3
 * @Version 1.0
 **/
public class ClickhouseEndpoint implements Serializable {
    private String database;
    private String protocol = "HTTP";
    private String host;
    private int port;
    // for write
    private int maxInsertThreads = 1;
    // for query
    private int maxThreads = 2;
    // default 1GB
    private int maxMemoryUsage = 1000 * 1000 * 1000;

    private Credentials credentials;

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public int getMaxInsertThreads() {
        return maxInsertThreads;
    }

    public void setMaxInsertThreads(int maxInsertThreads) {
        this.maxInsertThreads = maxInsertThreads;
    }

    public int getMaxThreads() {
        return maxThreads;
    }

    public void setMaxThreads(int maxThreads) {
        this.maxThreads = maxThreads;
    }

    public int getMaxMemoryUsage() {
        return maxMemoryUsage;
    }

    public void setMaxMemoryUsage(int maxMemoryUsage) {
        this.maxMemoryUsage = maxMemoryUsage;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public ClickhouseEndpoint deepCopy() {
        ClickhouseEndpoint node = new ClickhouseEndpoint();
        node.setPort(this.port);
        node.setHost(this.host);
        node.setProtocol(this.protocol);
        node.setMaxInsertThreads(this.maxInsertThreads);
        node.setMaxThreads(this.maxThreads);
        node.setMaxMemoryUsage(this.maxMemoryUsage);
        node.setCredentials(this.credentials);
        return node;
    }
}
