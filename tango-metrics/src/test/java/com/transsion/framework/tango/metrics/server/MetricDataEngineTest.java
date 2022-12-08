package com.transsion.framework.tango.metrics.server;

import com.transsion.framework.tango.common.Identifier;
import com.transsion.framework.tango.common.config.StatefulConfigManager;
import com.transsion.framework.tango.common.property.NamedType;
import com.transsion.framework.tango.common.property.Property;
import com.transsion.framework.tango.core.aggregation.AggregateType;
import com.transsion.framework.tango.core.expression.SumExpression;
import com.transsion.framework.tango.core.view.DefaultView;
import com.transsion.framework.tango.core.view.View;
import com.transsion.framework.tango.core.window.TimeWindow;
import com.transsion.framework.tango.core.window.WindowUnit;
import com.transsion.framework.tango.metrics.data.DefaultUnit;
import com.transsion.framework.tango.metrics.data.MetricData;
import com.transsion.framework.tango.metrics.data.MetricPoint;
import com.transsion.framework.tango.metrics.data.adapter.MetricInMemoryAdapter;
import com.transsion.framework.tango.metrics.data.meta.MetricMeta;
import com.transsion.framework.tango.storage.memory.InMemoryStorage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author mengqi.lv
 * @Date 2022/8/16
 * @Version 1.0
 **/
public class MetricDataEngineTest {

    private static StatefulConfigManager fakeConfigManager = Mockito.mock(StatefulConfigManager.class);
    private static MetricsEngine engine = new MetricsEngine(fakeConfigManager, new InMemoryStorage(), new MetricInMemoryAdapter());

    @BeforeAll
    static void setup() {
        engine.start();
    }

    @Test
    void testRegisterMetrics() {
        MetricMeta meta = buildMetricMeta();
        engine.register(meta);
        Assertions.assertEquals(meta, engine.findMeta(meta.getId()));
    }

    @Test
    void testQuery() {
        MetricMeta meta = buildMetricMeta();
        engine.register(meta);

        engine.collectMetrics(buildMetricDataList());
        MetricData data = engine.queryMetrics(buildView());
        Assertions.assertNotNull(data);
    }

    @AfterAll
    static void teardown() {
        engine.stop();
    }

    private View buildView() {
        DefaultView.DefaultViewBuilder builder = DefaultView.DefaultViewBuilder.builder();
        SumExpression.LongSumExpression sumExpression = new SumExpression.LongSumExpression();
        sumExpression.setDimensions(Arrays.asList(new Property("service", NamedType.STRING), new Property("path", NamedType.STRING)));
        return builder.dataId(new Identifier("tango", "test"))
                .startTime(System.currentTimeMillis() - 2000)
                .endTime(System.currentTimeMillis() + 10000)
                .expression(sumExpression)
                .viewId(new Identifier("client", "view"))
                .build();
    }

    private List<MetricPoint> buildMetricDataList() {
        List<MetricPoint> data = new ArrayList<>();

        data.add(newPoint(10, System.currentTimeMillis() + 2000, "gateway", "/health"));
        data.add(newPoint(10, System.currentTimeMillis() + 3000, "gateway", "/health"));
        data.add(newPoint(10, System.currentTimeMillis() + 8000, "common", "/health"));

        return data;
    }

    private MetricPoint newPoint(Number value, long timestamp, String service, String path) {
        MetricPoint point = new MetricPoint();
        point.setValue(value);
        point.setTimestamp(timestamp);
        point.setId(new Identifier("tango", "test"));
        point.setProperties(Utils.newMap("service", service, "path", path));
        return point;
    }

    private MetricMeta buildMetricMeta() {
        return MetricMeta.MetricMetaBuilder.builder()
                .id("tango", "test").unit(DefaultUnit.COUNT)
                .dimension(Property.buildStringProperty("service")).dimension(Property.buildStringProperty("path"))
                .aggregationType(AggregateType.LONG_SUM)
                .window(new TimeWindow(WindowUnit.SECOND, 5)).build();
    }
}
