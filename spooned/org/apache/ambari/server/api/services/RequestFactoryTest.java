package org.apache.ambari.server.api.services;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
public class RequestFactoryTest {
    @org.junit.Test
    public void testCreate_Post__NoQueryParams() {
        javax.ws.rs.core.HttpHeaders headers = org.easymock.EasyMock.createNiceMock(javax.ws.rs.core.HttpHeaders.class);
        javax.ws.rs.core.UriInfo uriInfo = org.easymock.EasyMock.createNiceMock(javax.ws.rs.core.UriInfo.class);
        org.apache.ambari.server.api.services.RequestBody body = org.easymock.EasyMock.createStrictMock(org.apache.ambari.server.api.services.RequestBody.class);
        org.apache.ambari.server.api.resources.ResourceInstance resource = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.resources.ResourceInstance.class);
        @java.lang.SuppressWarnings("unchecked")
        javax.ws.rs.core.MultivaluedMap<java.lang.String, java.lang.String> mapQueryParams = org.easymock.EasyMock.createMock(javax.ws.rs.core.MultivaluedMap.class);
        org.easymock.EasyMock.expect(uriInfo.getQueryParameters()).andReturn(mapQueryParams).anyTimes();
        org.easymock.EasyMock.expect(mapQueryParams.entrySet()).andReturn(java.util.Collections.emptySet()).anyTimes();
        org.easymock.EasyMock.expect(body.getQueryString()).andReturn(null);
        org.easymock.EasyMock.replay(headers, uriInfo, body, resource, mapQueryParams);
        org.apache.ambari.server.api.services.RequestFactory factory = new org.apache.ambari.server.api.services.RequestFactory();
        org.apache.ambari.server.api.services.Request request = factory.createRequest(headers, body, uriInfo, org.apache.ambari.server.api.services.Request.Type.POST, resource);
        org.junit.Assert.assertEquals(resource, request.getResource());
        org.junit.Assert.assertEquals(body, request.getBody());
        org.junit.Assert.assertEquals(org.apache.ambari.server.api.services.Request.Type.POST, request.getRequestType());
        org.easymock.EasyMock.verify(headers, uriInfo, body, resource, mapQueryParams);
    }

    @org.junit.Test
    public void testCreate_Post__UriQueryParams() {
        javax.ws.rs.core.HttpHeaders headers = org.easymock.EasyMock.createNiceMock(javax.ws.rs.core.HttpHeaders.class);
        javax.ws.rs.core.UriInfo uriInfo = org.easymock.EasyMock.createNiceMock(javax.ws.rs.core.UriInfo.class);
        org.apache.ambari.server.api.services.RequestBody body = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.services.RequestBody.class);
        org.apache.ambari.server.api.resources.ResourceInstance resource = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.resources.ResourceInstance.class);
        org.apache.ambari.server.api.resources.ResourceDefinition resourceDefinition = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.resources.ResourceDefinition.class);
        @java.lang.SuppressWarnings("unchecked")
        javax.ws.rs.core.MultivaluedMap<java.lang.String, java.lang.String> mapQueryParams = org.easymock.EasyMock.createMock(javax.ws.rs.core.MultivaluedMap.class);
        java.util.Map<java.lang.String, java.util.List<java.lang.String>> mapProps = new java.util.HashMap<>();
        mapProps.put("foo", java.util.Collections.singletonList("bar"));
        org.easymock.EasyMock.expect(uriInfo.getQueryParameters()).andReturn(mapQueryParams).anyTimes();
        org.easymock.EasyMock.expect(mapQueryParams.entrySet()).andReturn(mapProps.entrySet()).anyTimes();
        org.easymock.EasyMock.expect(resource.getResourceDefinition()).andReturn(resourceDefinition).anyTimes();
        org.easymock.EasyMock.expect(resourceDefinition.getCreateDirectives()).andReturn(java.util.Collections.emptySet());
        org.easymock.EasyMock.expect(body.getQueryString()).andReturn(null);
        org.easymock.EasyMock.replay(headers, uriInfo, body, resource, mapQueryParams, resourceDefinition);
        org.apache.ambari.server.api.services.RequestFactory factory = new org.apache.ambari.server.api.services.RequestFactory();
        org.apache.ambari.server.api.services.Request request = factory.createRequest(headers, body, uriInfo, org.apache.ambari.server.api.services.Request.Type.POST, resource);
        org.junit.Assert.assertEquals(resource, request.getResource());
        org.junit.Assert.assertEquals(body, request.getBody());
        org.junit.Assert.assertEquals(org.apache.ambari.server.api.services.Request.Type.QUERY_POST, request.getRequestType());
        org.easymock.EasyMock.verify(headers, uriInfo, body, resource, mapQueryParams, resourceDefinition);
    }

    @org.junit.Test
    public void testCreate_Post__WithUriDirective() {
        javax.ws.rs.core.HttpHeaders headers = org.easymock.EasyMock.createNiceMock(javax.ws.rs.core.HttpHeaders.class);
        javax.ws.rs.core.UriInfo uriInfo = org.easymock.EasyMock.createNiceMock(javax.ws.rs.core.UriInfo.class);
        org.apache.ambari.server.api.services.RequestBody body = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.services.RequestBody.class);
        org.apache.ambari.server.api.resources.ResourceInstance resource = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.resources.ResourceInstance.class);
        org.apache.ambari.server.api.resources.ResourceDefinition resourceDefinition = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.resources.ResourceDefinition.class);
        @java.lang.SuppressWarnings("unchecked")
        javax.ws.rs.core.MultivaluedMap<java.lang.String, java.lang.String> mapQueryParams = org.easymock.EasyMock.createMock(javax.ws.rs.core.MultivaluedMap.class);
        java.util.Map<java.lang.String, java.util.List<java.lang.String>> mapProps = new java.util.HashMap<>();
        mapProps.put("foo", java.util.Collections.singletonList("bar"));
        java.util.Map<java.lang.String, java.lang.String> requestInfoMap = new java.util.HashMap<>();
        org.easymock.EasyMock.expect(uriInfo.getQueryParameters()).andReturn(mapQueryParams).anyTimes();
        org.easymock.EasyMock.expect(mapQueryParams.entrySet()).andReturn(mapProps.entrySet()).anyTimes();
        org.easymock.EasyMock.expect(resource.getResourceDefinition()).andReturn(resourceDefinition).anyTimes();
        org.easymock.EasyMock.expect(resourceDefinition.getCreateDirectives()).andReturn(java.util.Collections.singleton("foo"));
        org.easymock.EasyMock.expect(body.getQueryString()).andReturn(null);
        org.easymock.EasyMock.expect(body.getRequestInfoProperties()).andReturn(requestInfoMap).anyTimes();
        org.easymock.EasyMock.replay(headers, uriInfo, body, resource, mapQueryParams, resourceDefinition);
        org.apache.ambari.server.api.services.RequestFactory factory = new org.apache.ambari.server.api.services.RequestFactory();
        org.apache.ambari.server.api.services.Request request = factory.createRequest(headers, body, uriInfo, org.apache.ambari.server.api.services.Request.Type.POST, resource);
        org.junit.Assert.assertEquals(resource, request.getResource());
        org.junit.Assert.assertEquals(body, request.getBody());
        org.junit.Assert.assertEquals(org.apache.ambari.server.api.services.Request.Type.POST, request.getRequestType());
        org.junit.Assert.assertEquals("bar", requestInfoMap.get("foo"));
        org.easymock.EasyMock.verify(headers, uriInfo, body, resource, mapQueryParams, resourceDefinition);
    }

    @org.junit.Test
    public void testCreate_Put__WithUriDirective() {
        javax.ws.rs.core.HttpHeaders headers = org.easymock.EasyMock.createNiceMock(javax.ws.rs.core.HttpHeaders.class);
        javax.ws.rs.core.UriInfo uriInfo = org.easymock.EasyMock.createNiceMock(javax.ws.rs.core.UriInfo.class);
        org.apache.ambari.server.api.services.RequestBody body = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.services.RequestBody.class);
        org.apache.ambari.server.api.resources.ResourceInstance resource = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.resources.ResourceInstance.class);
        org.apache.ambari.server.api.resources.ResourceDefinition resourceDefinition = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.resources.ResourceDefinition.class);
        @java.lang.SuppressWarnings("unchecked")
        javax.ws.rs.core.MultivaluedMap<java.lang.String, java.lang.String> mapQueryParams = org.easymock.EasyMock.createMock(javax.ws.rs.core.MultivaluedMap.class);
        java.util.Map<java.lang.String, java.util.List<java.lang.String>> mapProps = new java.util.HashMap<>();
        mapProps.put("foo", java.util.Collections.singletonList("bar"));
        java.util.Map<java.lang.String, java.lang.String> requestInfoMap = new java.util.HashMap<>();
        org.easymock.EasyMock.expect(uriInfo.getQueryParameters()).andReturn(mapQueryParams).anyTimes();
        org.easymock.EasyMock.expect(mapQueryParams.entrySet()).andReturn(mapProps.entrySet()).anyTimes();
        org.easymock.EasyMock.expect(resource.getResourceDefinition()).andReturn(resourceDefinition).anyTimes();
        org.easymock.EasyMock.expect(resourceDefinition.getUpdateDirectives()).andReturn(java.util.Collections.singleton("foo"));
        org.easymock.EasyMock.expect(body.getQueryString()).andReturn(null);
        org.easymock.EasyMock.expect(body.getRequestInfoProperties()).andReturn(requestInfoMap).anyTimes();
        org.easymock.EasyMock.replay(headers, uriInfo, body, resource, mapQueryParams, resourceDefinition);
        org.apache.ambari.server.api.services.RequestFactory factory = new org.apache.ambari.server.api.services.RequestFactory();
        org.apache.ambari.server.api.services.Request request = factory.createRequest(headers, body, uriInfo, org.apache.ambari.server.api.services.Request.Type.PUT, resource);
        org.junit.Assert.assertEquals(resource, request.getResource());
        org.junit.Assert.assertEquals(body, request.getBody());
        org.junit.Assert.assertEquals(org.apache.ambari.server.api.services.Request.Type.PUT, request.getRequestType());
        org.junit.Assert.assertEquals("bar", requestInfoMap.get("foo"));
        org.easymock.EasyMock.verify(headers, uriInfo, body, resource, mapQueryParams, resourceDefinition);
    }

    @org.junit.Test
    public void testCreate_Delete__WithUriDirective() {
        javax.ws.rs.core.HttpHeaders headers = org.easymock.EasyMock.createNiceMock(javax.ws.rs.core.HttpHeaders.class);
        javax.ws.rs.core.UriInfo uriInfo = org.easymock.EasyMock.createNiceMock(javax.ws.rs.core.UriInfo.class);
        org.apache.ambari.server.api.services.RequestBody body = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.services.RequestBody.class);
        org.apache.ambari.server.api.resources.ResourceInstance resource = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.resources.ResourceInstance.class);
        org.apache.ambari.server.api.resources.ResourceDefinition resourceDefinition = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.resources.ResourceDefinition.class);
        @java.lang.SuppressWarnings("unchecked")
        javax.ws.rs.core.MultivaluedMap<java.lang.String, java.lang.String> mapQueryParams = org.easymock.EasyMock.createMock(javax.ws.rs.core.MultivaluedMap.class);
        java.util.Map<java.lang.String, java.util.List<java.lang.String>> mapProps = new java.util.HashMap<>();
        mapProps.put("foo", java.util.Collections.singletonList("bar"));
        java.util.Map<java.lang.String, java.lang.String> requestInfoMap = new java.util.HashMap<>();
        org.easymock.EasyMock.expect(uriInfo.getQueryParameters()).andReturn(mapQueryParams).anyTimes();
        org.easymock.EasyMock.expect(mapQueryParams.entrySet()).andReturn(mapProps.entrySet()).anyTimes();
        org.easymock.EasyMock.expect(resource.getResourceDefinition()).andReturn(resourceDefinition).anyTimes();
        org.easymock.EasyMock.expect(resourceDefinition.getDeleteDirectives()).andReturn(java.util.Collections.singleton("foo"));
        org.easymock.EasyMock.expect(body.getQueryString()).andReturn(null);
        org.easymock.EasyMock.expect(body.getRequestInfoProperties()).andReturn(requestInfoMap).anyTimes();
        org.easymock.EasyMock.replay(headers, uriInfo, body, resource, mapQueryParams, resourceDefinition);
        org.apache.ambari.server.api.services.RequestFactory factory = new org.apache.ambari.server.api.services.RequestFactory();
        org.apache.ambari.server.api.services.Request request = factory.createRequest(headers, body, uriInfo, org.apache.ambari.server.api.services.Request.Type.DELETE, resource);
        org.junit.Assert.assertEquals(resource, request.getResource());
        org.junit.Assert.assertEquals(body, request.getBody());
        org.junit.Assert.assertEquals(org.apache.ambari.server.api.services.Request.Type.DELETE, request.getRequestType());
        org.junit.Assert.assertEquals("bar", requestInfoMap.get("foo"));
        org.easymock.EasyMock.verify(headers, uriInfo, body, resource, mapQueryParams, resourceDefinition);
    }

    @org.junit.Test
    public void testCreate_Delete__WithoutUriDirective() {
        javax.ws.rs.core.HttpHeaders headers = org.easymock.EasyMock.createNiceMock(javax.ws.rs.core.HttpHeaders.class);
        javax.ws.rs.core.UriInfo uriInfo = org.easymock.EasyMock.createNiceMock(javax.ws.rs.core.UriInfo.class);
        org.apache.ambari.server.api.services.RequestBody body = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.services.RequestBody.class);
        org.apache.ambari.server.api.resources.ResourceInstance resource = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.resources.ResourceInstance.class);
        org.apache.ambari.server.api.resources.ResourceDefinition resourceDefinition = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.resources.ResourceDefinition.class);
        @java.lang.SuppressWarnings("unchecked")
        javax.ws.rs.core.MultivaluedMap<java.lang.String, java.lang.String> mapQueryParams = org.easymock.EasyMock.createMock(javax.ws.rs.core.MultivaluedMap.class);
        java.util.Map<java.lang.String, java.util.List<java.lang.String>> mapProps = new java.util.HashMap<>();
        mapProps.put("foo", java.util.Collections.singletonList("bar"));
        java.util.Map<java.lang.String, java.lang.String> requestInfoMap = new java.util.HashMap<>();
        org.easymock.EasyMock.expect(uriInfo.getQueryParameters()).andReturn(mapQueryParams).anyTimes();
        org.easymock.EasyMock.expect(mapQueryParams.entrySet()).andReturn(mapProps.entrySet()).anyTimes();
        org.easymock.EasyMock.expect(resource.getResourceDefinition()).andReturn(resourceDefinition).anyTimes();
        org.easymock.EasyMock.expect(resourceDefinition.getDeleteDirectives()).andReturn(java.util.Collections.emptySet());
        org.easymock.EasyMock.expect(body.getQueryString()).andReturn(null);
        org.easymock.EasyMock.expect(body.getRequestInfoProperties()).andReturn(requestInfoMap).anyTimes();
        org.easymock.EasyMock.replay(headers, uriInfo, body, resource, mapQueryParams, resourceDefinition);
        org.apache.ambari.server.api.services.RequestFactory factory = new org.apache.ambari.server.api.services.RequestFactory();
        org.apache.ambari.server.api.services.Request request = factory.createRequest(headers, body, uriInfo, org.apache.ambari.server.api.services.Request.Type.DELETE, resource);
        org.junit.Assert.assertEquals(resource, request.getResource());
        org.junit.Assert.assertEquals(body, request.getBody());
        org.junit.Assert.assertEquals(org.apache.ambari.server.api.services.Request.Type.DELETE, request.getRequestType());
        org.junit.Assert.assertEquals(null, requestInfoMap.get("foo"));
        org.easymock.EasyMock.verify(headers, uriInfo, body, resource, mapQueryParams, resourceDefinition);
    }

    @org.junit.Test
    public void testCreate_Post__BodyQueryParams() {
        javax.ws.rs.core.HttpHeaders headers = org.easymock.EasyMock.createNiceMock(javax.ws.rs.core.HttpHeaders.class);
        javax.ws.rs.core.UriInfo uriInfo = org.easymock.EasyMock.createNiceMock(javax.ws.rs.core.UriInfo.class);
        org.apache.ambari.server.api.services.RequestBody body = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.services.RequestBody.class);
        org.apache.ambari.server.api.resources.ResourceInstance resource = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.resources.ResourceInstance.class);
        org.apache.ambari.server.api.resources.ResourceDefinition resourceDefinition = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.resources.ResourceDefinition.class);
        @java.lang.SuppressWarnings("unchecked")
        javax.ws.rs.core.MultivaluedMap<java.lang.String, java.lang.String> mapQueryParams = org.easymock.EasyMock.createMock(javax.ws.rs.core.MultivaluedMap.class);
        org.easymock.EasyMock.expect(uriInfo.getQueryParameters()).andReturn(mapQueryParams).anyTimes();
        org.easymock.EasyMock.expect(mapQueryParams.entrySet()).andReturn(java.util.Collections.emptySet()).anyTimes();
        org.easymock.EasyMock.expect(resource.getResourceDefinition()).andReturn(resourceDefinition).anyTimes();
        org.easymock.EasyMock.expect(resourceDefinition.getCreateDirectives()).andReturn(java.util.Collections.emptySet());
        org.easymock.EasyMock.expect(body.getQueryString()).andReturn("foo=bar");
        org.easymock.EasyMock.replay(headers, uriInfo, body, resource, mapQueryParams, resourceDefinition);
        org.apache.ambari.server.api.services.RequestFactory factory = new org.apache.ambari.server.api.services.RequestFactory();
        org.apache.ambari.server.api.services.Request request = factory.createRequest(headers, body, uriInfo, org.apache.ambari.server.api.services.Request.Type.POST, resource);
        org.junit.Assert.assertEquals(resource, request.getResource());
        org.junit.Assert.assertEquals(body, request.getBody());
        org.junit.Assert.assertEquals(org.apache.ambari.server.api.services.Request.Type.QUERY_POST, request.getRequestType());
        org.easymock.EasyMock.verify(headers, uriInfo, body, resource, mapQueryParams, resourceDefinition);
    }

    @org.junit.Test
    public void testCreate_Post__WithBodyDirective() {
        javax.ws.rs.core.HttpHeaders headers = org.easymock.EasyMock.createNiceMock(javax.ws.rs.core.HttpHeaders.class);
        javax.ws.rs.core.UriInfo uriInfo = org.easymock.EasyMock.createNiceMock(javax.ws.rs.core.UriInfo.class);
        org.apache.ambari.server.api.services.RequestBody body = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.services.RequestBody.class);
        org.apache.ambari.server.api.resources.ResourceInstance resource = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.resources.ResourceInstance.class);
        org.apache.ambari.server.api.resources.ResourceDefinition resourceDefinition = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.resources.ResourceDefinition.class);
        @java.lang.SuppressWarnings("unchecked")
        javax.ws.rs.core.MultivaluedMap<java.lang.String, java.lang.String> mapQueryParams = org.easymock.EasyMock.createMock(javax.ws.rs.core.MultivaluedMap.class);
        java.util.Map<java.lang.String, java.util.List<java.lang.String>> mapProps = new java.util.HashMap<>();
        mapProps.put("foo", java.util.Collections.singletonList("bar"));
        java.util.Map<java.lang.String, java.lang.String> requestInfoMap = new java.util.HashMap<>();
        org.easymock.EasyMock.expect(uriInfo.getQueryParameters()).andReturn(mapQueryParams).anyTimes();
        org.easymock.EasyMock.expect(mapQueryParams.entrySet()).andReturn(java.util.Collections.emptySet()).anyTimes();
        org.easymock.EasyMock.expect(resource.getResourceDefinition()).andReturn(resourceDefinition).anyTimes();
        org.easymock.EasyMock.expect(resourceDefinition.getCreateDirectives()).andReturn(java.util.Collections.singleton("foo"));
        org.easymock.EasyMock.expect(body.getQueryString()).andReturn("foo=bar");
        org.easymock.EasyMock.expect(body.getRequestInfoProperties()).andReturn(requestInfoMap).anyTimes();
        org.easymock.EasyMock.replay(headers, uriInfo, body, resource, mapQueryParams, resourceDefinition);
        org.apache.ambari.server.api.services.RequestFactory factory = new org.apache.ambari.server.api.services.RequestFactory();
        org.apache.ambari.server.api.services.Request request = factory.createRequest(headers, body, uriInfo, org.apache.ambari.server.api.services.Request.Type.POST, resource);
        org.junit.Assert.assertEquals(resource, request.getResource());
        org.junit.Assert.assertEquals(body, request.getBody());
        org.junit.Assert.assertEquals(org.apache.ambari.server.api.services.Request.Type.POST, request.getRequestType());
        org.junit.Assert.assertEquals("bar", requestInfoMap.get("foo"));
        org.easymock.EasyMock.verify(headers, uriInfo, body, resource, mapQueryParams, resourceDefinition);
    }

    @org.junit.Test
    public void testCreate_Get__WithUriDirective() {
        javax.ws.rs.core.HttpHeaders headers = org.easymock.EasyMock.createMock(javax.ws.rs.core.HttpHeaders.class);
        javax.ws.rs.core.UriInfo uriInfo = org.easymock.EasyMock.createMock(javax.ws.rs.core.UriInfo.class);
        org.apache.ambari.server.api.services.RequestBody body = org.easymock.EasyMock.createMock(org.apache.ambari.server.api.services.RequestBody.class);
        org.apache.ambari.server.api.resources.ResourceInstance resource = org.easymock.EasyMock.createMock(org.apache.ambari.server.api.resources.ResourceInstance.class);
        org.apache.ambari.server.api.resources.ResourceDefinition resourceDefinition = org.easymock.EasyMock.createMock(org.apache.ambari.server.api.resources.ResourceDefinition.class);
        @java.lang.SuppressWarnings("unchecked")
        javax.ws.rs.core.MultivaluedMap<java.lang.String, java.lang.String> mapQueryParams = org.easymock.EasyMock.createMock(javax.ws.rs.core.MultivaluedMap.class);
        java.util.Map<java.lang.String, java.util.List<java.lang.String>> mapProps = new java.util.HashMap<>();
        mapProps.put("foo", java.util.Collections.singletonList("bar"));
        java.util.Map<java.lang.String, java.lang.String> requestInfoMap = new java.util.HashMap<>();
        org.easymock.EasyMock.expect(uriInfo.getQueryParameters()).andReturn(mapQueryParams).anyTimes();
        org.easymock.EasyMock.expect(mapQueryParams.entrySet()).andReturn(mapProps.entrySet()).anyTimes();
        org.easymock.EasyMock.expect(resource.getResourceDefinition()).andReturn(resourceDefinition).anyTimes();
        org.easymock.EasyMock.expect(resourceDefinition.getReadDirectives()).andReturn(java.util.Collections.singleton("foo"));
        org.easymock.EasyMock.expect(body.getQueryString()).andReturn(null);
        org.easymock.EasyMock.expect(body.getRequestInfoProperties()).andReturn(requestInfoMap).anyTimes();
        org.easymock.EasyMock.replay(headers, uriInfo, body, resource, mapQueryParams, resourceDefinition);
        org.apache.ambari.server.api.services.RequestFactory factory = new org.apache.ambari.server.api.services.RequestFactory();
        org.apache.ambari.server.api.services.Request request = factory.createRequest(headers, body, uriInfo, org.apache.ambari.server.api.services.Request.Type.GET, resource);
        org.junit.Assert.assertEquals(resource, request.getResource());
        org.junit.Assert.assertEquals(body, request.getBody());
        org.junit.Assert.assertEquals(org.apache.ambari.server.api.services.Request.Type.GET, request.getRequestType());
        org.junit.Assert.assertEquals("bar", requestInfoMap.get("foo"));
        org.easymock.EasyMock.verify(headers, uriInfo, body, resource, mapQueryParams, resourceDefinition);
    }
}
