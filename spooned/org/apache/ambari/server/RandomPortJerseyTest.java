package org.apache.ambari.server;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
public class RandomPortJerseyTest extends org.glassfish.jersey.test.JerseyTest {
    private static int testPort;

    @java.lang.Override
    protected org.glassfish.jersey.server.ResourceConfig configure() {
        forceSet(org.glassfish.jersey.test.TestProperties.CONTAINER_PORT, getPort(8079) + "");
        return new org.glassfish.jersey.server.ResourceConfig().packages("com.example");
    }

    protected int getPort(int defaultPort) {
        java.net.ServerSocket server = null;
        int port = -1;
        try {
            server = new java.net.ServerSocket(defaultPort);
            port = server.getLocalPort();
        } catch (java.io.IOException e) {
        } finally {
            if (server != null) {
                try {
                    server.close();
                } catch (java.io.IOException e) {
                }
            }
        }
        if ((port != (-1)) || (defaultPort == 0)) {
            this.testPort = port;
            return port;
        }
        return getPort(0);
    }

    public int getTestPort() {
        return org.apache.ambari.server.RandomPortJerseyTest.testPort;
    }
}
