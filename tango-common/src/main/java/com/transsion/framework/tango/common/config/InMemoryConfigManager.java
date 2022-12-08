package com.transsion.framework.tango.common.config;

import com.transsion.framework.tango.common.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author mengqi.lv
 * @Date 2021/7/16
 * @Version 1.0
 **/
public class InMemoryConfigManager extends StatefulConfigManager {
    private static final Logger logger = LoggerFactory.getLogger(InMemoryConfigManager.class);

    private Map<String, List<ConfigEntry>> repoConfigs;

    public InMemoryConfigManager() {
        super("in-memory-config");
        repoConfigs = new ConcurrentHashMap<>();
    }

    @Override
    public List<StatefulConfig> fetch(String repoName, String configName) {
        StatefulConfigBuilder builder = getConfigBuilder(repoName, configName);
        List<ConfigEntry> entries = repoConfigs.get(repoName);
        if (Utility.isEmpty(entries)) {
            return new ArrayList<>(0);
        }
        if (CONFIG_NAME_ALL.equals(configName)) {
            List<StatefulConfig> configList = new ArrayList<>(entries.size());
            for (ConfigEntry entry : entries) {
                try {
                    StatefulConfig newConfig = builder.build(entry.key, entry.value);
                    configList.add(newConfig);
                } catch (Exception e) {
                    logger.error(String.format("Build config error, repo: %s, config name: %s", repoName, configName), e);
                }
            }
            return configList;
        } else {
            try {
                Optional<ConfigEntry> optional = entries.stream().filter(en -> en.key.equals(configName)).findFirst();
                if (optional.isPresent()) {
                    return builder.buildList(configName, optional.get().value);
                }
            } catch (Exception e) {
                logger.error(String.format("Build config list error, repo: %s, config name: %s", repoName, configName), e);
            }
            return new ArrayList<>(0);
        }
    }

    @Override
    public String getStringConfig(String repoName, String configName, String defaultValue) {
        List<ConfigEntry> entries = repoConfigs.get(repoName);
        if (Utility.isEmpty(entries)) {
            return defaultValue;
        }
        Optional<ConfigEntry> optional = entries.stream().filter(en -> en.key.equals(configName)).findFirst();
        if (optional.isPresent()) {
            return optional.get().value;
        }
        return defaultValue;
    }

    @Override
    public Integer getIntConfig(String repoName, String configName, Integer defaultValue) {
        String str = getStringConfig(repoName, configName, "");
        if ("".equals(str)) {
            return defaultValue;
        }
        return Integer.parseInt(str);
    }

    @Override
    public Long getLongConfig(String repoName, String configName, Long defaultValue) {
        String str = getStringConfig(repoName, configName, "");
        if ("".equals(str)) {
            return defaultValue;
        }
        return Long.parseLong(str);
    }

    @Override
    protected void register(String repoName, String configName, StatefulConfigListener listener) {

    }

    public void addConfig(String repo, String key, String value) {
        this.repoConfigs.computeIfAbsent(repo, k -> new ArrayList<>()).add(new ConfigEntry(key, value));
    }

    private class ConfigEntry {
        String key;
        String value = "";

        public ConfigEntry() {
        }

        public ConfigEntry(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }
}
