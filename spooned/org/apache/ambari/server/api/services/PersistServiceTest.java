package org.apache.ambari.server.api.services;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
public class PersistServiceTest extends org.apache.ambari.server.RandomPortJerseyTest {
    static java.lang.String PACKAGE_NAME = "org.apache.ambari.server.api.services";

    com.google.inject.Injector injector;

    protected javax.ws.rs.client.Client client;

    public PersistServiceTest() {
        super();
    }

    public class MockModule extends com.google.inject.AbstractModule {
        @java.lang.Override
        protected void configure() {
            requestStaticInjection(org.apache.ambari.server.api.services.PersistKeyValueService.class);
        }
    }

    @java.lang.Override
    @org.junit.Before
    public void setUp() throws java.lang.Exception {
        super.setUp();
        injector = com.google.inject.Guice.createInjector(new org.apache.ambari.server.orm.InMemoryDefaultTestModule(), new org.apache.ambari.server.api.services.PersistServiceTest.MockModule());
        injector.getInstance(org.apache.ambari.server.orm.GuiceJpaInitializer.class);
    }

    @java.lang.Override
    @org.junit.After
    public void tearDown() throws java.lang.Exception {
        super.tearDown();
        org.apache.ambari.server.H2DatabaseCleaner.clearDatabaseAndStopPersistenceService(injector);
    }

    @java.lang.Override
    protected org.glassfish.jersey.server.ResourceConfig configure() {
        return new org.glassfish.jersey.server.ResourceConfig().packages(org.apache.ambari.server.api.services.PersistServiceTest.PACKAGE_NAME).register(org.glassfish.jersey.jackson.JacksonFeature.class);
    }

    @org.junit.Test
    public void testPersistAPIs() throws java.io.IOException {
        java.lang.String input = "{\"key1\" : \"value1\",\"key2\" : \"value2\"}";
        java.lang.String response = target("persist").request().post(javax.ws.rs.client.Entity.text(input), java.lang.String.class);
        org.junit.Assert.assertEquals("", response);
        java.lang.String result = target("persist/key1").request().get(java.lang.String.class);
        org.junit.Assert.assertEquals("value1", result);
        result = target("persist/key2").request().get(java.lang.String.class);
        org.junit.Assert.assertEquals("value2", result);
        java.lang.String values = "[\"value3\", \"value4\"]";
        java.lang.String putResponse = target("persist").request().put(javax.ws.rs.client.Entity.text(values), java.lang.String.class);
        java.util.Collection<java.lang.String> keys = org.apache.ambari.server.utils.StageUtils.fromJson(putResponse, java.util.Collection.class);
        org.junit.Assert.assertEquals(2, keys.size());
        java.lang.String getAllResponse = target("persist").request().get(java.lang.String.class);
        java.util.Map<java.lang.String, java.lang.String> allKeys = org.apache.ambari.server.utils.StageUtils.fromJson(getAllResponse, java.util.Map.class);
        org.junit.Assert.assertEquals(4, allKeys.size());
        org.junit.Assert.assertEquals("value1", allKeys.get("key1"));
        org.junit.Assert.assertEquals("value2", allKeys.get("key2"));
    }
}
