package org.apache.ambari.server.api;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MultivaluedHashMap;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
public class AmbariCsrfProtectionFilterTest {
    @org.junit.Test
    public void testGetMethod() throws java.io.IOException {
        org.apache.ambari.server.api.AmbariCsrfProtectionFilter filter = new org.apache.ambari.server.api.AmbariCsrfProtectionFilter();
        javax.ws.rs.container.ContainerRequestContext containerRequest = org.easymock.EasyMock.createMock(javax.ws.rs.container.ContainerRequestContext.class);
        org.easymock.EasyMock.expect(containerRequest.getMethod()).andReturn("GET");
        org.easymock.EasyMock.replay(containerRequest);
        filter.filter(containerRequest);
    }

    @org.junit.Test(expected = javax.ws.rs.WebApplicationException.class)
    public void testPostNoXRequestedBy() throws java.io.IOException {
        org.apache.ambari.server.api.AmbariCsrfProtectionFilter filter = new org.apache.ambari.server.api.AmbariCsrfProtectionFilter();
        javax.ws.rs.container.ContainerRequestContext containerRequest = org.easymock.EasyMock.createMock(javax.ws.rs.container.ContainerRequestContext.class);
        javax.ws.rs.core.MultivaluedHashMap headers = new javax.ws.rs.core.MultivaluedHashMap();
        org.easymock.EasyMock.expect(containerRequest.getMethod()).andReturn("POST");
        org.easymock.EasyMock.expect(containerRequest.getHeaders()).andReturn(headers);
        org.easymock.EasyMock.replay(containerRequest);
        filter.filter(containerRequest);
    }

    @org.junit.Test
    public void testPostXRequestedBy() throws java.io.IOException {
        org.apache.ambari.server.api.AmbariCsrfProtectionFilter filter = new org.apache.ambari.server.api.AmbariCsrfProtectionFilter();
        javax.ws.rs.container.ContainerRequestContext containerRequest = org.easymock.EasyMock.createMock(javax.ws.rs.container.ContainerRequestContext.class);
        javax.ws.rs.core.MultivaluedHashMap headers = new javax.ws.rs.core.MultivaluedHashMap();
        headers.add("X-Requested-By", "anything");
        org.easymock.EasyMock.expect(containerRequest.getMethod()).andReturn("GET");
        org.easymock.EasyMock.expect(containerRequest.getHeaders()).andReturn(headers);
        org.easymock.EasyMock.replay(containerRequest);
        filter.filter(containerRequest);
    }
}
