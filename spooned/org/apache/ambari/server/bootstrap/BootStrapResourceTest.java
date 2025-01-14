package org.apache.ambari.server.bootstrap;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import org.codehaus.jettison.json.JSONException;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.DeploymentContext;
import org.glassfish.jersey.test.JerseyTest;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
public class BootStrapResourceTest extends org.glassfish.jersey.test.JerseyTest {
    static java.lang.String PACKAGE_NAME = "org.apache.ambari.server.api.rest";

    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(org.apache.ambari.server.bootstrap.BootStrapResourceTest.class);

    com.google.inject.Injector injector;

    org.apache.ambari.server.bootstrap.BootStrapImpl bsImpl;

    @java.lang.Override
    protected org.glassfish.jersey.server.ResourceConfig configure() {
        org.glassfish.jersey.server.ResourceConfig config = new org.glassfish.jersey.server.ResourceConfig();
        config.packages(org.apache.ambari.server.bootstrap.BootStrapResourceTest.PACKAGE_NAME);
        org.glassfish.jersey.test.DeploymentContext.builder(config).build();
        return config;
    }

    public class MockModule extends com.google.inject.AbstractModule {
        @java.lang.Override
        protected void configure() {
            org.apache.ambari.server.bootstrap.BootStrapImpl bsImpl = org.mockito.Mockito.mock(org.apache.ambari.server.bootstrap.BootStrapImpl.class);
            org.mockito.Mockito.when(bsImpl.getStatus(0)).thenReturn(generateDummyBSStatus());
            org.mockito.Mockito.when(bsImpl.runBootStrap(org.mockito.Matchers.any(org.apache.ambari.server.bootstrap.SshHostInfo.class))).thenReturn(generateBSResponse());
            bind(org.apache.ambari.server.bootstrap.BootStrapImpl.class).toInstance(bsImpl);
            requestStaticInjection(org.apache.ambari.server.api.rest.BootStrapResource.class);
        }
    }

    @java.lang.Override
    public void setUp() throws java.lang.Exception {
        super.setUp();
        injector = com.google.inject.Guice.createInjector(new org.apache.ambari.server.bootstrap.BootStrapResourceTest.MockModule());
    }

    protected int getPort(int defaultPort) {
        try (java.net.ServerSocket socket = new java.net.ServerSocket(0)) {
            return socket.getLocalPort();
        } catch (java.io.IOException e) {
        }
        return defaultPort;
    }

    protected org.apache.ambari.server.bootstrap.SshHostInfo createDummySshInfo() throws org.codehaus.jettison.json.JSONException {
        org.apache.ambari.server.bootstrap.SshHostInfo sshInfo = new org.apache.ambari.server.bootstrap.SshHostInfo();
        sshInfo.setSshKey("awesome");
        java.util.ArrayList<java.lang.String> hosts = new java.util.ArrayList<>();
        hosts.add("host1");
        sshInfo.setHosts(hosts);
        sshInfo.setVerbose(true);
        return sshInfo;
    }

    protected org.apache.ambari.server.bootstrap.BSResponse generateBSResponse() {
        org.apache.ambari.server.bootstrap.BSResponse response = new org.apache.ambari.server.bootstrap.BSResponse();
        response.setLog("Logging");
        response.setRequestId(1);
        response.setStatus(org.apache.ambari.server.bootstrap.BSResponse.BSRunStat.OK);
        return response;
    }

    protected org.apache.ambari.server.bootstrap.BootStrapStatus generateDummyBSStatus() {
        org.apache.ambari.server.bootstrap.BootStrapStatus status = new org.apache.ambari.server.bootstrap.BootStrapStatus();
        status.setLog("Logging ");
        status.setStatus(org.apache.ambari.server.bootstrap.BootStrapStatus.BSStat.ERROR);
        status.setHostsStatus(new java.util.ArrayList<>());
        return status;
    }

    @org.junit.Test
    public void bootStrapGet() {
        javax.ws.rs.client.WebTarget webTarget = target("/bootstrap/0");
        org.apache.ambari.server.bootstrap.BootStrapStatus status = webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(org.apache.ambari.server.bootstrap.BootStrapStatus.class);
        org.apache.ambari.server.bootstrap.BootStrapResourceTest.LOG.info((("GET Response from the API " + status.getLog()) + " ") + status.getStatus());
        junit.framework.Assert.assertEquals(org.apache.ambari.server.bootstrap.BootStrapStatus.BSStat.ERROR, status.getStatus());
    }

    @org.junit.Test
    public void bootStrapPost() throws org.codehaus.jettison.json.JSONException {
        javax.ws.rs.client.WebTarget webTarget = target("/bootstrap");
        com.fasterxml.jackson.databind.JsonNode object = webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.json(createDummySshInfo()), com.fasterxml.jackson.databind.JsonNode.class);
        junit.framework.Assert.assertEquals("OK", object.get("status").asText());
    }
}
