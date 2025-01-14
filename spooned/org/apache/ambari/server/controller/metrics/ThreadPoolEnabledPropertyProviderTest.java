package org.apache.ambari.server.controller.metrics;
public class ThreadPoolEnabledPropertyProviderTest {
    @org.junit.Test
    public void testGetCacheKeyForException() throws java.lang.Exception {
        com.fasterxml.jackson.databind.ObjectMapper jmxObjectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
        jmxObjectMapper.configure(com.fasterxml.jackson.databind.MapperFeature.USE_ANNOTATIONS, false);
        com.fasterxml.jackson.databind.ObjectReader jmxObjectReader = jmxObjectMapper.reader(org.apache.ambari.server.controller.jmx.JMXMetricHolder.class);
        java.util.List<java.lang.Exception> exceptions = new java.util.ArrayList<>();
        for (int i = 0; i < 2; i++) {
            try {
                jmxObjectReader.readValue("Invalid string");
            } catch (java.lang.Exception e) {
                exceptions.add(e);
            }
        }
        org.junit.Assert.assertEquals(org.apache.ambari.server.controller.metrics.ThreadPoolEnabledPropertyProvider.getCacheKeyForException(exceptions.get(0)), org.apache.ambari.server.controller.metrics.ThreadPoolEnabledPropertyProvider.getCacheKeyForException(exceptions.get(1)));
    }
}
