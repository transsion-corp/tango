package com.transsion.framework.tango.source;

import com.transsion.framework.tango.core.data.Data;
import com.transsion.framework.tango.core.engine.CollectorEngine;
import com.transsion.framework.tango.core.source.DataProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @Author mengqi.lv
 * @Date 2022/3/1
 * @Version 1.0
 **/
public class SingleThreadProducer implements DataProducer {
    private static final Logger logger = LoggerFactory.getLogger(SingleThreadProducer.class);

    private static final int pollLimit = 10000;

    private Thread worker;
    private volatile boolean running = false;

    private CollectorEngine collectorEngine;

    private final Poller poller;

    public SingleThreadProducer(Poller poller) {
        this.poller = poller;
    }

    @Override
    public void start() {
        if (running) {
            logger.warn("Already started.");
        }
        initialThread();
    }

    private void initialThread() {
        running = true;
        worker = new Thread("Event kafka consumer.") {
            @Override
            public void run() {
                int emptyCount = 0;
                while (running) {
                    try {
                        List<Data> dataList = poller.poll(pollLimit);
                        if (dataList.size() > 0) {
                            emptyCount = 0;
                            dataList.forEach(d -> collectorEngine.addData(d));
                        } else {
                            emptyCount++;
                        }
                        if (emptyCount >= 3) {
                            // flush some data
                            collectorEngine.addData(Data.Empty);
                            Thread.sleep(1000);
                        }
                    } catch (Throwable e) {
                        logger.error("Caught exception.", e);
                    }
                }
            }
        };
        worker.setDaemon(true);
        worker.start();
    }

    @Override
    public void stop() {
        if (running) {
            running = false;
        }
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setCollectorEngine(CollectorEngine engine) {
        this.collectorEngine = engine;
    }
}
