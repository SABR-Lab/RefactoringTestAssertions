package org.apache.ambari.server.api;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import org.easymock.EasyMockSupport;
import static org.easymock.EasyMock.expect;
public class AmbariErrorHandlerIT extends org.easymock.EasyMockSupport {
    private com.google.gson.Gson gson = new com.google.gson.Gson();

    @org.junit.Test
    public void testErrorWithJetty() throws java.lang.Exception {
        org.eclipse.jetty.server.Server server = new org.eclipse.jetty.server.Server(0);
        org.apache.ambari.server.security.authentication.jwt.JwtAuthenticationPropertiesProvider propertiesProvider = createNiceMock(org.apache.ambari.server.security.authentication.jwt.JwtAuthenticationPropertiesProvider.class);
        org.easymock.EasyMock.expect(propertiesProvider.get()).andReturn(null).anyTimes();
        replayAll();
        org.eclipse.jetty.servlet.ServletContextHandler root = new org.eclipse.jetty.servlet.ServletContextHandler(server, "/", org.eclipse.jetty.servlet.ServletContextHandler.SECURITY | org.eclipse.jetty.servlet.ServletContextHandler.SESSIONS);
        root.addServlet(org.apache.ambari.server.api.AmbariErrorHandlerIT.HelloServlet.class, "/hello");
        root.addServlet(org.eclipse.jetty.servlet.DefaultServlet.class, "/");
        root.setErrorHandler(new org.apache.ambari.server.api.AmbariErrorHandler(gson, propertiesProvider));
        server.start();
        int localPort = ((org.eclipse.jetty.server.ServerConnector) (server.getConnectors()[0])).getLocalPort();
        javax.ws.rs.client.Client client = javax.ws.rs.client.ClientBuilder.newClient();
        javax.ws.rs.client.WebTarget resource = client.target("http://localhost:" + localPort);
        javax.ws.rs.core.Response successResponse = resource.path("hello").request().get();
        assert successResponse.getStatus() == Response.Status.OK.getStatusCode();
        javax.ws.rs.core.Response failResponse = resource.path("fail").request().get();
        assert failResponse.getStatus() == Response.Status.NOT_FOUND.getStatusCode();
        try {
            java.lang.String response = readResponse(failResponse);
            java.lang.System.out.println(response);
            java.util.Map map;
            map = gson.fromJson(response, java.util.Map.class);
            java.lang.System.out.println(map);
            org.junit.Assert.assertNotNull("Incorrect response status", map.get("status"));
            org.junit.Assert.assertNotNull("Incorrect response message", map.get("message"));
        } catch (com.google.gson.JsonSyntaxException e1) {
            org.junit.Assert.fail("Incorrect response");
        }
        server.stop();
        verifyAll();
    }

    private java.lang.String readResponse(javax.ws.rs.core.Response response) {
        java.io.InputStream inputStream = ((java.io.InputStream) (response.getEntity()));
        java.util.Scanner scanner = new java.util.Scanner(inputStream, "UTF-8").useDelimiter("\\A");
        return scanner.hasNext() ? scanner.next() : "";
    }

    @java.lang.SuppressWarnings("serial")
    public static class HelloServlet extends javax.servlet.http.HttpServlet {
        @java.lang.Override
        protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
            response.setContentType("text/html");
            response.setStatus(javax.servlet.http.HttpServletResponse.SC_OK);
            response.getWriter().println("hello");
        }
    }
}
