package com.transsion.framework.tango.source.kafka;

import com.transsion.framework.tango.core.data.Data;
import com.transsion.framework.tango.core.data.translate.Translator;
import com.transsion.framework.tango.source.Poller;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author mengqi.lv
 * @Date 2022/1/12
 * @Version 1.0
 **/
public class KafkaPoller<T extends Data> implements Poller<T> {
    private static final Logger logger = LoggerFactory.getLogger(KafkaPoller.class);

    private final String name;
    private final KafkaConfig config;
    private final KafkaConsumer kafkaConsumer;

    private final Translator<String, T> translator;

    public KafkaPoller(String name, KafkaConfig config, Translator<String, T> translator) {
        this.name = name;
        this.translator = translator;
        this.config = config;
        this.kafkaConsumer = new KafkaConsumer(config.toProperties());
        this.kafkaConsumer.subscribe(Arrays.asList(config.getTopic()));
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<T> poll(int max) {
        List<T> dataList = new ArrayList<>(config.getMaxPollRecords());
        ConsumerRecords<String, String> msgList = kafkaConsumer.poll(Duration.of(1000, ChronoUnit.MILLIS));
        if (msgList == null || msgList.count() < 1) {
            return dataList;
        }
        logger.info("receive message from kafka,size:{}", msgList.count());
        msgList.iterator().forEachRemaining(item -> {
                    T data = translator.translate(item.value());
                    if (data != null) {
                        dataList.add(data);
                    }
                }
        );

        return dataList;
    }
}
