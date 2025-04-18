package org.apache.ambari.server.api.handlers;
import org.easymock.Capture;
import org.easymock.EasyMock;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
public class ReadHandlerTest {
    @org.junit.Test
    public void testHandleRequest__InvalidField() {
        org.apache.ambari.server.api.services.Request request = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.services.Request.class);
        org.apache.ambari.server.api.resources.ResourceInstance resource = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.resources.ResourceInstance.class);
        org.apache.ambari.server.api.query.Query query = org.easymock.EasyMock.createStrictMock(org.apache.ambari.server.api.query.Query.class);
        java.util.Map<java.lang.String, org.apache.ambari.server.controller.spi.TemporalInfo> mapPartialResponseFields = new java.util.HashMap<>();
        mapPartialResponseFields.put("foo/bar", null);
        org.apache.ambari.server.api.query.render.Renderer renderer = new org.apache.ambari.server.api.query.render.DefaultRenderer();
        org.easymock.EasyMock.expect(request.getResource()).andReturn(resource);
        org.easymock.EasyMock.expect(request.getFields()).andReturn(mapPartialResponseFields);
        org.easymock.EasyMock.expect(request.getRenderer()).andReturn(renderer);
        org.easymock.EasyMock.expect(resource.getQuery()).andReturn(query);
        query.setPageRequest(null);
        query.setSortRequest(null);
        query.setRenderer(renderer);
        query.addProperty("foo/bar", null);
        org.easymock.EasyMock.expectLastCall().andThrow(new java.lang.IllegalArgumentException("testMsg"));
        org.easymock.EasyMock.replay(request, resource, query);
        org.apache.ambari.server.api.handlers.ReadHandler handler = new org.apache.ambari.server.api.handlers.ReadHandler();
        org.apache.ambari.server.api.services.Result result = handler.handleRequest(request);
        org.junit.Assert.assertEquals(org.apache.ambari.server.api.services.ResultStatus.STATUS.BAD_REQUEST, result.getStatus().getStatus());
        org.junit.Assert.assertEquals("testMsg", result.getStatus().getMessage());
        org.easymock.EasyMock.verify(request, resource, query);
    }

    @org.junit.Test
    public void testHandleRequest__OK() throws java.lang.Exception {
        org.apache.ambari.server.api.services.Request request = org.easymock.EasyMock.createStrictMock(org.apache.ambari.server.api.services.Request.class);
        org.apache.ambari.server.api.resources.ResourceInstance resource = org.easymock.EasyMock.createStrictMock(org.apache.ambari.server.api.resources.ResourceInstance.class);
        org.apache.ambari.server.api.query.Query query = org.easymock.EasyMock.createMock(org.apache.ambari.server.api.query.Query.class);
        org.apache.ambari.server.controller.spi.Predicate predicate = org.easymock.EasyMock.createMock(org.apache.ambari.server.controller.spi.Predicate.class);
        org.apache.ambari.server.api.services.Result result = org.easymock.EasyMock.createStrictMock(org.apache.ambari.server.api.services.Result.class);
        org.apache.ambari.server.api.services.RequestBody body = org.easymock.EasyMock.createStrictMock(org.apache.ambari.server.api.services.RequestBody.class);
        org.apache.ambari.server.api.query.render.Renderer renderer = new org.apache.ambari.server.api.query.render.DefaultRenderer();
        org.easymock.Capture<org.apache.ambari.server.api.services.ResultStatus> resultStatusCapture = org.easymock.EasyMock.newCapture();
        java.util.Map<java.lang.String, java.lang.String> requestInfoProperties = java.util.Collections.singletonMap("directive", "value");
        java.util.Map<java.lang.String, org.apache.ambari.server.controller.spi.TemporalInfo> mapPartialResponseFields = new java.util.HashMap<>();
        mapPartialResponseFields.put("foo", null);
        mapPartialResponseFields.put("bar/c", null);
        mapPartialResponseFields.put("bar/d/e", null);
        mapPartialResponseFields.put("category/", null);
        org.easymock.EasyMock.expect(request.getResource()).andReturn(resource);
        org.easymock.EasyMock.expect(resource.getQuery()).andReturn(query);
        org.easymock.EasyMock.expect(request.getPageRequest()).andReturn(null);
        org.easymock.EasyMock.expect(request.getSortRequest()).andReturn(null);
        org.easymock.EasyMock.expect(request.getRenderer()).andReturn(renderer);
        org.easymock.EasyMock.expect(request.getBody()).andReturn(body);
        org.easymock.EasyMock.expect(request.getFields()).andReturn(mapPartialResponseFields);
        org.easymock.EasyMock.expect(body.getRequestInfoProperties()).andReturn(requestInfoProperties);
        query.setRequestInfoProps(requestInfoProperties);
        query.addProperty("foo", null);
        query.addProperty("bar/c", null);
        query.addProperty("bar/d/e", null);
        query.addProperty("category/", null);
        org.easymock.EasyMock.expect(request.getQueryPredicate()).andReturn(predicate);
        query.setUserPredicate(predicate);
        query.setPageRequest(null);
        query.setSortRequest(null);
        query.setRenderer(renderer);
        org.easymock.EasyMock.expect(query.execute()).andReturn(result);
        result.setResultStatus(org.easymock.EasyMock.capture(resultStatusCapture));
        org.easymock.EasyMock.replay(request, resource, body, query, predicate, result);
        org.apache.ambari.server.api.handlers.ReadHandler handler = new org.apache.ambari.server.api.handlers.ReadHandler();
        org.junit.Assert.assertSame(result, handler.handleRequest(request));
        org.junit.Assert.assertEquals(org.apache.ambari.server.api.services.ResultStatus.STATUS.OK, resultStatusCapture.getValue().getStatus());
        org.easymock.EasyMock.verify(request, resource, body, query, predicate, result);
    }

    @org.junit.Test
    public void testHandleRequest__SystemException() throws java.lang.Exception {
        org.apache.ambari.server.api.services.Request request = org.easymock.EasyMock.createStrictMock(org.apache.ambari.server.api.services.Request.class);
        org.apache.ambari.server.api.resources.ResourceInstance resource = org.easymock.EasyMock.createStrictMock(org.apache.ambari.server.api.resources.ResourceInstance.class);
        org.apache.ambari.server.api.query.Query query = org.easymock.EasyMock.createMock(org.apache.ambari.server.api.query.Query.class);
        org.apache.ambari.server.controller.spi.Predicate predicate = org.easymock.EasyMock.createMock(org.apache.ambari.server.controller.spi.Predicate.class);
        org.apache.ambari.server.api.query.render.Renderer renderer = new org.apache.ambari.server.api.query.render.DefaultRenderer();
        org.easymock.EasyMock.expect(request.getResource()).andReturn(resource);
        org.easymock.EasyMock.expect(resource.getQuery()).andReturn(query);
        org.easymock.EasyMock.expect(request.getPageRequest()).andReturn(null);
        org.easymock.EasyMock.expect(request.getSortRequest()).andReturn(null);
        org.easymock.EasyMock.expect(request.getRenderer()).andReturn(renderer);
        org.easymock.EasyMock.expect(request.getBody()).andReturn(null);
        org.easymock.EasyMock.expect(request.getFields()).andReturn(java.util.Collections.emptyMap());
        org.easymock.EasyMock.expect(request.getQueryPredicate()).andReturn(predicate);
        query.setUserPredicate(predicate);
        query.setPageRequest(null);
        query.setSortRequest(null);
        query.setRenderer(renderer);
        org.apache.ambari.server.controller.spi.SystemException systemException = new org.apache.ambari.server.controller.spi.SystemException("testMsg", new java.lang.RuntimeException());
        org.easymock.EasyMock.expect(query.execute()).andThrow(systemException);
        org.easymock.EasyMock.replay(request, resource, query, predicate);
        org.apache.ambari.server.api.handlers.ReadHandler handler = new org.apache.ambari.server.api.handlers.ReadHandler();
        org.apache.ambari.server.api.services.Result result = handler.handleRequest(request);
        org.junit.Assert.assertEquals(org.apache.ambari.server.api.services.ResultStatus.STATUS.SERVER_ERROR, result.getStatus().getStatus());
        org.junit.Assert.assertEquals(systemException.getMessage(), result.getStatus().getMessage());
        org.easymock.EasyMock.verify(request, resource, query, predicate);
    }

    @org.junit.Test
    public void testHandleRequest__NoSuchParentResourceException() throws java.lang.Exception {
        org.apache.ambari.server.api.services.Request request = org.easymock.EasyMock.createStrictMock(org.apache.ambari.server.api.services.Request.class);
        org.apache.ambari.server.api.resources.ResourceInstance resource = org.easymock.EasyMock.createStrictMock(org.apache.ambari.server.api.resources.ResourceInstance.class);
        org.apache.ambari.server.api.query.Query query = org.easymock.EasyMock.createMock(org.apache.ambari.server.api.query.Query.class);
        org.apache.ambari.server.controller.spi.Predicate predicate = org.easymock.EasyMock.createMock(org.apache.ambari.server.controller.spi.Predicate.class);
        org.apache.ambari.server.controller.spi.NoSuchParentResourceException exception = new org.apache.ambari.server.controller.spi.NoSuchParentResourceException("exceptionMsg", new java.lang.RuntimeException());
        org.apache.ambari.server.api.query.render.Renderer renderer = new org.apache.ambari.server.api.query.render.DefaultRenderer();
        org.easymock.EasyMock.expect(request.getResource()).andReturn(resource);
        org.easymock.EasyMock.expect(resource.getQuery()).andReturn(query);
        org.easymock.EasyMock.expect(request.getPageRequest()).andReturn(null);
        org.easymock.EasyMock.expect(request.getSortRequest()).andReturn(null);
        org.easymock.EasyMock.expect(request.getRenderer()).andReturn(renderer);
        org.easymock.EasyMock.expect(request.getBody()).andReturn(null);
        org.easymock.EasyMock.expect(request.getFields()).andReturn(java.util.Collections.emptyMap());
        org.easymock.EasyMock.expect(request.getQueryPredicate()).andReturn(predicate);
        query.setUserPredicate(predicate);
        query.setPageRequest(null);
        query.setSortRequest(null);
        query.setRenderer(renderer);
        org.easymock.EasyMock.expect(query.execute()).andThrow(exception);
        org.easymock.EasyMock.replay(request, resource, query, predicate);
        org.apache.ambari.server.api.handlers.ReadHandler handler = new org.apache.ambari.server.api.handlers.ReadHandler();
        org.apache.ambari.server.api.services.Result result = handler.handleRequest(request);
        org.junit.Assert.assertEquals(org.apache.ambari.server.api.services.ResultStatus.STATUS.NOT_FOUND, result.getStatus().getStatus());
        org.junit.Assert.assertEquals("exceptionMsg", result.getStatus().getMessage());
        org.easymock.EasyMock.verify(request, resource, query, predicate);
    }

    @org.junit.Test
    public void testHandleRequest__UnsupportedPropertyException() throws java.lang.Exception {
        org.apache.ambari.server.api.services.Request request = org.easymock.EasyMock.createStrictMock(org.apache.ambari.server.api.services.Request.class);
        org.apache.ambari.server.api.resources.ResourceInstance resource = org.easymock.EasyMock.createStrictMock(org.apache.ambari.server.api.resources.ResourceInstance.class);
        org.apache.ambari.server.api.query.Query query = org.easymock.EasyMock.createMock(org.apache.ambari.server.api.query.Query.class);
        org.apache.ambari.server.controller.spi.Predicate predicate = org.easymock.EasyMock.createMock(org.apache.ambari.server.controller.spi.Predicate.class);
        org.apache.ambari.server.api.query.render.Renderer renderer = new org.apache.ambari.server.api.query.render.DefaultRenderer();
        org.apache.ambari.server.controller.spi.UnsupportedPropertyException exception = new org.apache.ambari.server.controller.spi.UnsupportedPropertyException(org.apache.ambari.server.controller.spi.Resource.Type.Cluster, java.util.Collections.singleton("foo"));
        org.easymock.EasyMock.expect(request.getResource()).andReturn(resource);
        org.easymock.EasyMock.expect(resource.getQuery()).andReturn(query);
        org.easymock.EasyMock.expect(request.getPageRequest()).andReturn(null);
        org.easymock.EasyMock.expect(request.getSortRequest()).andReturn(null);
        org.easymock.EasyMock.expect(request.getRenderer()).andReturn(renderer);
        org.easymock.EasyMock.expect(request.getBody()).andReturn(null);
        org.easymock.EasyMock.expect(request.getFields()).andReturn(java.util.Collections.emptyMap());
        org.easymock.EasyMock.expect(request.getQueryPredicate()).andReturn(predicate);
        query.setUserPredicate(predicate);
        query.setPageRequest(null);
        query.setSortRequest(null);
        query.setRenderer(renderer);
        org.easymock.EasyMock.expect(query.execute()).andThrow(exception);
        org.easymock.EasyMock.replay(request, resource, query, predicate);
        org.apache.ambari.server.api.handlers.ReadHandler handler = new org.apache.ambari.server.api.handlers.ReadHandler();
        org.apache.ambari.server.api.services.Result result = handler.handleRequest(request);
        org.junit.Assert.assertEquals(org.apache.ambari.server.api.services.ResultStatus.STATUS.BAD_REQUEST, result.getStatus().getStatus());
        org.junit.Assert.assertEquals(exception.getMessage(), result.getStatus().getMessage());
        org.easymock.EasyMock.verify(request, resource, query, predicate);
    }

    @org.junit.Test
    public void testHandleRequest__NoSuchResourceException_OK() throws java.lang.Exception {
        org.apache.ambari.server.api.services.Request request = org.easymock.EasyMock.createStrictMock(org.apache.ambari.server.api.services.Request.class);
        org.apache.ambari.server.api.resources.ResourceInstance resource = org.easymock.EasyMock.createStrictMock(org.apache.ambari.server.api.resources.ResourceInstance.class);
        org.apache.ambari.server.api.query.Query query = org.easymock.EasyMock.createMock(org.apache.ambari.server.api.query.Query.class);
        org.apache.ambari.server.controller.spi.Predicate predicate = org.easymock.EasyMock.createMock(org.apache.ambari.server.controller.spi.Predicate.class);
        org.apache.ambari.server.controller.spi.NoSuchResourceException exception = new org.apache.ambari.server.controller.spi.NoSuchResourceException("msg", new java.lang.RuntimeException());
        org.apache.ambari.server.api.query.render.Renderer renderer = new org.apache.ambari.server.api.query.render.DefaultRenderer();
        org.easymock.EasyMock.expect(request.getResource()).andReturn(resource);
        org.easymock.EasyMock.expect(resource.getQuery()).andReturn(query);
        org.easymock.EasyMock.expect(request.getPageRequest()).andReturn(null);
        org.easymock.EasyMock.expect(request.getSortRequest()).andReturn(null);
        org.easymock.EasyMock.expect(request.getRenderer()).andReturn(renderer);
        org.easymock.EasyMock.expect(request.getBody()).andReturn(null);
        org.easymock.EasyMock.expect(request.getFields()).andReturn(java.util.Collections.emptyMap());
        org.easymock.EasyMock.expect(request.getQueryPredicate()).andReturn(predicate).anyTimes();
        query.setUserPredicate(predicate);
        query.setPageRequest(null);
        query.setSortRequest(null);
        query.setRenderer(renderer);
        org.easymock.EasyMock.expect(query.execute()).andThrow(exception);
        org.easymock.EasyMock.replay(request, resource, query, predicate);
        org.apache.ambari.server.api.handlers.ReadHandler handler = new org.apache.ambari.server.api.handlers.ReadHandler();
        org.apache.ambari.server.api.services.Result result = handler.handleRequest(request);
        org.junit.Assert.assertEquals(org.apache.ambari.server.api.services.ResultStatus.STATUS.OK, result.getStatus().getStatus());
        org.easymock.EasyMock.verify(request, resource, query, predicate);
    }

    @org.junit.Test
    public void testHandleRequest__NoSuchResourceException_NOT_FOUND() throws java.lang.Exception {
        org.apache.ambari.server.api.services.Request request = org.easymock.EasyMock.createStrictMock(org.apache.ambari.server.api.services.Request.class);
        org.apache.ambari.server.api.resources.ResourceInstance resource = org.easymock.EasyMock.createStrictMock(org.apache.ambari.server.api.resources.ResourceInstance.class);
        org.apache.ambari.server.api.query.Query query = org.easymock.EasyMock.createMock(org.apache.ambari.server.api.query.Query.class);
        org.apache.ambari.server.controller.spi.NoSuchResourceException exception = new org.apache.ambari.server.controller.spi.NoSuchResourceException("msg", new java.lang.RuntimeException());
        org.apache.ambari.server.api.query.render.Renderer renderer = new org.apache.ambari.server.api.query.render.DefaultRenderer();
        org.easymock.EasyMock.expect(request.getResource()).andReturn(resource);
        org.easymock.EasyMock.expect(resource.getQuery()).andReturn(query);
        org.easymock.EasyMock.expect(request.getPageRequest()).andReturn(null);
        org.easymock.EasyMock.expect(request.getSortRequest()).andReturn(null);
        org.easymock.EasyMock.expect(request.getRenderer()).andReturn(renderer);
        org.easymock.EasyMock.expect(request.getBody()).andReturn(null);
        org.easymock.EasyMock.expect(request.getFields()).andReturn(java.util.Collections.emptyMap());
        org.easymock.EasyMock.expect(request.getQueryPredicate()).andReturn(null).anyTimes();
        query.setUserPredicate(null);
        query.setPageRequest(null);
        query.setSortRequest(null);
        query.setRenderer(renderer);
        org.easymock.EasyMock.expect(query.execute()).andThrow(exception);
        org.easymock.EasyMock.replay(request, resource, query);
        org.apache.ambari.server.api.handlers.ReadHandler handler = new org.apache.ambari.server.api.handlers.ReadHandler();
        org.apache.ambari.server.api.services.Result result = handler.handleRequest(request);
        org.junit.Assert.assertEquals(org.apache.ambari.server.api.services.ResultStatus.STATUS.NOT_FOUND, result.getStatus().getStatus());
        org.junit.Assert.assertEquals(exception.getMessage(), result.getStatus().getMessage());
        org.easymock.EasyMock.verify(request, resource, query);
    }

    @org.junit.Test
    public void testHandleRequest__AuthorizationException() throws java.lang.Exception {
        org.apache.ambari.server.api.services.Request request = org.easymock.EasyMock.createStrictMock(org.apache.ambari.server.api.services.Request.class);
        org.apache.ambari.server.api.resources.ResourceInstance resource = org.easymock.EasyMock.createStrictMock(org.apache.ambari.server.api.resources.ResourceInstance.class);
        org.apache.ambari.server.api.query.Query query = org.easymock.EasyMock.createMock(org.apache.ambari.server.api.query.Query.class);
        org.apache.ambari.server.controller.spi.Predicate predicate = org.easymock.EasyMock.createMock(org.apache.ambari.server.controller.spi.Predicate.class);
        org.apache.ambari.server.api.query.render.Renderer renderer = new org.apache.ambari.server.api.query.render.DefaultRenderer();
        org.easymock.EasyMock.expect(request.getResource()).andReturn(resource);
        org.easymock.EasyMock.expect(resource.getQuery()).andReturn(query);
        org.easymock.EasyMock.expect(request.getPageRequest()).andReturn(null);
        org.easymock.EasyMock.expect(request.getSortRequest()).andReturn(null);
        org.easymock.EasyMock.expect(request.getRenderer()).andReturn(renderer);
        org.easymock.EasyMock.expect(request.getBody()).andReturn(null);
        org.easymock.EasyMock.expect(request.getFields()).andReturn(java.util.Collections.emptyMap());
        org.easymock.EasyMock.expect(request.getQueryPredicate()).andReturn(predicate);
        query.setUserPredicate(predicate);
        query.setPageRequest(null);
        query.setSortRequest(null);
        query.setRenderer(renderer);
        org.apache.ambari.server.security.authorization.AuthorizationException authorizationException = new org.apache.ambari.server.security.authorization.AuthorizationException("testMsg");
        org.easymock.EasyMock.expect(query.execute()).andThrow(authorizationException);
        org.easymock.EasyMock.replay(request, resource, query, predicate);
        org.apache.ambari.server.api.handlers.ReadHandler handler = new org.apache.ambari.server.api.handlers.ReadHandler();
        org.apache.ambari.server.api.services.Result result = handler.handleRequest(request);
        org.junit.Assert.assertEquals(org.apache.ambari.server.api.services.ResultStatus.STATUS.FORBIDDEN, result.getStatus().getStatus());
        org.junit.Assert.assertEquals(authorizationException.getMessage(), result.getStatus().getMessage());
        org.easymock.EasyMock.verify(request, resource, query, predicate);
    }
}
