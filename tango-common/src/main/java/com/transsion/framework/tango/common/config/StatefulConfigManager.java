package com.transsion.framework.tango.common.config;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author mengqi.lv
 * @Date 2021/5/11
 * @Version 1.0
 **/
public abstract class StatefulConfigManager {
    public static final String CONFIG_NAME_ALL = "*";

    private final Map<String, StatefulConfigBuilder> configBuilderMap;
    private final String name;

    private String applicationRepo = "application";

    public StatefulConfigManager(String name) {
        this.name = name;
        this.configBuilderMap = new ConcurrentHashMap<>();
    }

    public void addConfigListener(String repoName, String configName, StatefulConfigListener listener) {
        if (repoName == null) {
            repoName = "";
        }
        if (configName == null) {
            configName = "";
        }
        List<StatefulConfig> list = fetch(repoName, configName);
        listener.onInitial(list);
        register(repoName, configName, listener);
    }

    public void addRepoListener(String repoName, StatefulConfigListener listener) {
        addConfigListener(repoName, CONFIG_NAME_ALL, listener);
    }

    public void addConfigListener(String configName, StatefulConfigListener listener) {
        addConfigListener(applicationRepo, configName, listener);
    }

    public void setApplicationRepo(String applicationRepo) {
        this.applicationRepo = applicationRepo;
    }

    public String getStringConfig(String configName, String defaultValue) {
        return getStringConfig(applicationRepo, configName, defaultValue);
    }

    public Integer getIntConfig(String configName, Integer defaultValue) {
        return getIntConfig(applicationRepo, configName, defaultValue);
    }

    public Long getLongConfig(String configName, Long defaultValue) {
        return getLongConfig(applicationRepo, configName, defaultValue);
    }

    public void registerRepoConfigBuilder(String repoName, StatefulConfigBuilder builder) {
        String key = getConfigBuilderKey(repoName, CONFIG_NAME_ALL);
        configBuilderMap.put(key, builder);
    }

    public void registerStatefulConfigBuilder(String repoName, String configName, StatefulConfigBuilder builder) {
        String key = getConfigBuilderKey(repoName, configName);
        configBuilderMap.put(key, builder);
    }
    public List<StatefulConfig> fetch(String repoName) {
        return fetch(repoName, CONFIG_NAME_ALL);
    }

    public abstract List<StatefulConfig> fetch(String repoName, String configName);

    public abstract String getStringConfig(String repoName, String configName, String defaultValue);

    public abstract Integer getIntConfig(String repoName, String configName, Integer defaultValue);

    public abstract Long getLongConfig(String repoName, String configName, Long defaultValue);

    protected abstract void register(String repoName, String configName, StatefulConfigListener listener);

    protected StatefulConfigBuilder getConfigBuilder(String repoName, String configName) {
        String key = getConfigBuilderKey(repoName, configName);
        return configBuilderMap.getOrDefault(key, new StringConfigBuilder());
    }

    private String getConfigBuilderKey(String repoName, String configName) {
        if (repoName == null) {
            repoName = "";
        }
        if (configName == null) {
            configName = "";
        }
        return repoName + "@@" + configName;
    }
}
