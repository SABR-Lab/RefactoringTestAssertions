package org.apache.ambari.server.controller.metrics.timeline;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;
import org.apache.hadoop.metrics2.sink.timeline.TimelineMetric;
import org.apache.hadoop.metrics2.sink.timeline.TimelineMetrics;
import org.apache.http.HttpStatus;
import org.apache.http.client.utils.URIBuilder;
import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isNull;
import static org.easymock.EasyMock.replay;
public class MetricsRequestHelperTest {
    @org.junit.Test
    public void testFetchTimelineMetrics() throws java.lang.Exception {
        org.easymock.EasyMockSupport easyMockSupport = new org.easymock.EasyMockSupport();
        final long now = java.lang.System.currentTimeMillis();
        org.apache.hadoop.metrics2.sink.timeline.TimelineMetrics metrics = new org.apache.hadoop.metrics2.sink.timeline.TimelineMetrics();
        org.apache.hadoop.metrics2.sink.timeline.TimelineMetric timelineMetric = new org.apache.hadoop.metrics2.sink.timeline.TimelineMetric();
        timelineMetric.setMetricName("cpu_user");
        timelineMetric.setAppId("app1");
        java.util.TreeMap<java.lang.Long, java.lang.Double> metricValues = new java.util.TreeMap<>();
        metricValues.put(now + 100, 1.0);
        metricValues.put(now + 200, 2.0);
        metricValues.put(now + 300, 3.0);
        timelineMetric.setMetricValues(metricValues);
        metrics.getMetrics().add(timelineMetric);
        com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
        com.fasterxml.jackson.databind.AnnotationIntrospector introspector = new com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector();
        mapper.setAnnotationIntrospector(introspector);
        com.fasterxml.jackson.databind.ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
        java.lang.String metricsResponse = writer.writeValueAsString(metrics);
        java.io.InputStream inputStream = org.apache.commons.io.IOUtils.toInputStream(metricsResponse);
        java.net.HttpURLConnection httpURLConnectionMock = org.easymock.EasyMock.createMock(java.net.HttpURLConnection.class);
        org.easymock.EasyMock.expect(httpURLConnectionMock.getInputStream()).andReturn(inputStream).once();
        org.easymock.EasyMock.expect(httpURLConnectionMock.getResponseCode()).andReturn(org.apache.http.HttpStatus.SC_OK).once();
        org.apache.ambari.server.controller.internal.URLStreamProvider urlStreamProviderMock = org.easymock.EasyMock.createMock(org.apache.ambari.server.controller.internal.URLStreamProvider.class);
        org.easymock.EasyMock.expect(urlStreamProviderMock.processURL(org.easymock.EasyMock.isA(java.lang.String.class), org.easymock.EasyMock.isA(java.lang.String.class), org.easymock.EasyMock.isNull(java.lang.String.class), org.easymock.EasyMock.anyObject())).andReturn(httpURLConnectionMock).once();
        org.easymock.EasyMock.replay(httpURLConnectionMock, urlStreamProviderMock);
        java.lang.String randomSpec = "http://localhost:6188/ws/v1/timeline/metrics?metricNames=cpu_wio&hostname=host1&appId=HOST" + "&startTime=1447912834&endTime=1447990034&precision=SECONDS";
        org.apache.ambari.server.controller.metrics.timeline.MetricsRequestHelper metricsRequestHelper = new org.apache.ambari.server.controller.metrics.timeline.MetricsRequestHelper(urlStreamProviderMock);
        metricsRequestHelper.fetchTimelineMetrics(new org.apache.http.client.utils.URIBuilder(randomSpec), now, now + 300);
        easyMockSupport.verifyAll();
        java.lang.String metricsPrecisionErrorResponse = ((("{\"exception\": \"PrecisionLimitExceededException\",\n" + "\"message\": \"Requested precision (SECONDS) for given time range causes result set size of 169840, ") + "which exceeds the limit - 15840. Please request higher precision.\",\n") + "\"javaClassName\": \"org.apache.hadoop.metrics2.sink.timeline.PrecisionLimitExceededException\"\n") + "}";
        java.io.InputStream errorStream = org.apache.commons.io.IOUtils.toInputStream(metricsPrecisionErrorResponse);
        inputStream = org.apache.commons.io.IOUtils.toInputStream(metricsResponse);
        httpURLConnectionMock = org.easymock.EasyMock.createMock(java.net.HttpURLConnection.class);
        org.easymock.EasyMock.expect(httpURLConnectionMock.getErrorStream()).andReturn(errorStream).once();
        org.easymock.EasyMock.expect(httpURLConnectionMock.getInputStream()).andReturn(inputStream).once();
        org.easymock.EasyMock.expect(httpURLConnectionMock.getResponseCode()).andReturn(org.apache.http.HttpStatus.SC_BAD_REQUEST).once().andReturn(org.apache.http.HttpStatus.SC_OK).once();
        urlStreamProviderMock = org.easymock.EasyMock.createMock(org.apache.ambari.server.controller.internal.URLStreamProvider.class);
        org.easymock.EasyMock.expect(urlStreamProviderMock.processURL(org.easymock.EasyMock.isA(java.lang.String.class), org.easymock.EasyMock.isA(java.lang.String.class), org.easymock.EasyMock.isNull(java.lang.String.class), org.easymock.EasyMock.anyObject())).andReturn(httpURLConnectionMock).times(2);
        org.easymock.EasyMock.replay(httpURLConnectionMock, urlStreamProviderMock);
        metricsRequestHelper = new org.apache.ambari.server.controller.metrics.timeline.MetricsRequestHelper(urlStreamProviderMock);
        metricsRequestHelper.fetchTimelineMetrics(new org.apache.http.client.utils.URIBuilder(randomSpec), now, now + 300);
        easyMockSupport.verifyAll();
    }
}
