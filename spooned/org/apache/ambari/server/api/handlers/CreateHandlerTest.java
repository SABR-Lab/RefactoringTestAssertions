package org.apache.ambari.server.api.handlers;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
public class CreateHandlerTest {
    @org.junit.Before
    public void before() {
        org.apache.ambari.server.events.publishers.AmbariEventPublisher publisher = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.events.publishers.AmbariEventPublisher.class);
        org.easymock.EasyMock.replay(publisher);
        org.apache.ambari.server.view.ViewRegistry.initInstance(new org.apache.ambari.server.view.ViewRegistry(publisher));
    }

    @org.junit.Test
    public void testHandleRequest__Synchronous_NoPropsInBody() throws java.lang.Exception {
        org.apache.ambari.server.api.services.Request request = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.services.Request.class);
        org.apache.ambari.server.api.services.RequestBody body = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.services.RequestBody.class);
        org.apache.ambari.server.api.resources.ResourceInstance resource = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.resources.ResourceInstance.class);
        org.apache.ambari.server.api.resources.ResourceDefinition resourceDefinition = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.resources.ResourceDefinition.class);
        org.apache.ambari.server.api.query.Query query = org.easymock.EasyMock.createStrictMock(org.apache.ambari.server.api.query.Query.class);
        org.apache.ambari.server.api.services.persistence.PersistenceManager pm = org.easymock.EasyMock.createStrictMock(org.apache.ambari.server.api.services.persistence.PersistenceManager.class);
        org.apache.ambari.server.controller.spi.RequestStatus status = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.RequestStatus.class);
        org.apache.ambari.server.controller.spi.Resource resource1 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.Resource.class);
        org.apache.ambari.server.controller.spi.Resource resource2 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.Resource.class);
        org.apache.ambari.server.api.query.render.Renderer renderer = new org.apache.ambari.server.api.query.render.DefaultRenderer();
        java.util.Set<org.apache.ambari.server.controller.spi.Resource> setResources = new java.util.HashSet<>();
        setResources.add(resource1);
        setResources.add(resource2);
        org.easymock.EasyMock.expect(request.getResource()).andReturn(resource).atLeastOnce();
        org.easymock.EasyMock.expect(request.getQueryPredicate()).andReturn(null).atLeastOnce();
        org.easymock.EasyMock.expect(request.getRenderer()).andReturn(renderer);
        org.easymock.EasyMock.expect(request.getBody()).andReturn(body);
        org.easymock.EasyMock.expect(resource.getQuery()).andReturn(query);
        query.setRenderer(renderer);
        org.easymock.EasyMock.expect(resource.getResourceDefinition()).andReturn(resourceDefinition).anyTimes();
        org.easymock.EasyMock.expect(resourceDefinition.isCreatable()).andReturn(true).anyTimes();
        org.easymock.EasyMock.expect(pm.create(resource, body)).andReturn(status);
        org.easymock.EasyMock.expect(status.getStatus()).andReturn(org.apache.ambari.server.controller.spi.RequestStatus.Status.Complete);
        org.easymock.EasyMock.expect(status.getAssociatedResources()).andReturn(setResources);
        org.easymock.EasyMock.expect(resource1.getType()).andReturn(org.apache.ambari.server.controller.spi.Resource.Type.Cluster).anyTimes();
        org.easymock.EasyMock.expect(resource2.getType()).andReturn(org.apache.ambari.server.controller.spi.Resource.Type.Cluster).anyTimes();
        org.easymock.EasyMock.replay(request, body, resource, resourceDefinition, query, pm, status, resource1, resource2);
        org.apache.ambari.server.api.services.Result result = new org.apache.ambari.server.api.handlers.CreateHandlerTest.TestCreateHandler(pm).handleRequest(request);
        org.junit.Assert.assertNotNull(result);
        org.apache.ambari.server.api.util.TreeNode<org.apache.ambari.server.controller.spi.Resource> tree = result.getResultTree();
        org.junit.Assert.assertEquals(1, tree.getChildren().size());
        org.apache.ambari.server.api.util.TreeNode<org.apache.ambari.server.controller.spi.Resource> resourcesNode = tree.getChild("resources");
        org.junit.Assert.assertEquals(2, resourcesNode.getChildren().size());
        boolean foundResource1 = false;
        boolean foundResource2 = false;
        for (org.apache.ambari.server.api.util.TreeNode<org.apache.ambari.server.controller.spi.Resource> child : resourcesNode.getChildren()) {
            org.apache.ambari.server.controller.spi.Resource r = child.getObject();
            if ((r == resource1) && (!foundResource1)) {
                foundResource1 = true;
            } else if ((r == resource2) && (!foundResource2)) {
                foundResource2 = true;
            } else {
                org.junit.Assert.fail();
            }
        }
        org.junit.Assert.assertEquals(org.apache.ambari.server.api.services.ResultStatus.STATUS.CREATED, result.getStatus().getStatus());
        org.easymock.EasyMock.verify(request, body, resource, resourceDefinition, query, pm, status, resource1, resource2);
    }

    @org.junit.Test
    public void testHandleRequest__Synchronous() throws java.lang.Exception {
        org.apache.ambari.server.api.services.Request request = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.services.Request.class);
        org.apache.ambari.server.api.services.RequestBody body = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.services.RequestBody.class);
        org.apache.ambari.server.api.resources.ResourceInstance resource = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.resources.ResourceInstance.class);
        org.apache.ambari.server.api.resources.ResourceDefinition resourceDefinition = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.resources.ResourceDefinition.class);
        org.apache.ambari.server.api.query.Query query = org.easymock.EasyMock.createStrictMock(org.apache.ambari.server.api.query.Query.class);
        org.apache.ambari.server.api.services.persistence.PersistenceManager pm = org.easymock.EasyMock.createStrictMock(org.apache.ambari.server.api.services.persistence.PersistenceManager.class);
        org.apache.ambari.server.controller.spi.RequestStatus status = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.RequestStatus.class);
        org.apache.ambari.server.controller.spi.Resource resource1 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.Resource.class);
        org.apache.ambari.server.controller.spi.Resource resource2 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.Resource.class);
        org.apache.ambari.server.api.query.render.Renderer renderer = new org.apache.ambari.server.api.query.render.DefaultRenderer();
        java.util.Set<org.apache.ambari.server.controller.spi.Resource> setResources = new java.util.HashSet<>();
        setResources.add(resource1);
        setResources.add(resource2);
        org.easymock.EasyMock.expect(request.getResource()).andReturn(resource).atLeastOnce();
        org.easymock.EasyMock.expect(request.getQueryPredicate()).andReturn(null).atLeastOnce();
        org.easymock.EasyMock.expect(request.getRenderer()).andReturn(renderer);
        org.easymock.EasyMock.expect(request.getBody()).andReturn(body).atLeastOnce();
        org.easymock.EasyMock.expect(resource.getQuery()).andReturn(query);
        query.setRenderer(renderer);
        org.easymock.EasyMock.expect(resource.getResourceDefinition()).andReturn(resourceDefinition).anyTimes();
        org.easymock.EasyMock.expect(resourceDefinition.isCreatable()).andReturn(true).anyTimes();
        org.easymock.EasyMock.expect(pm.create(resource, body)).andReturn(status);
        org.easymock.EasyMock.expect(status.getStatus()).andReturn(org.apache.ambari.server.controller.spi.RequestStatus.Status.Complete);
        org.easymock.EasyMock.expect(status.getAssociatedResources()).andReturn(setResources);
        org.easymock.EasyMock.expect(resource1.getType()).andReturn(org.apache.ambari.server.controller.spi.Resource.Type.Cluster).anyTimes();
        org.easymock.EasyMock.expect(resource2.getType()).andReturn(org.apache.ambari.server.controller.spi.Resource.Type.Cluster).anyTimes();
        org.easymock.EasyMock.replay(request, body, resource, resourceDefinition, query, pm, status, resource1, resource2);
        org.apache.ambari.server.api.services.Result result = new org.apache.ambari.server.api.handlers.CreateHandlerTest.TestCreateHandler(pm).handleRequest(request);
        org.junit.Assert.assertNotNull(result);
        org.apache.ambari.server.api.util.TreeNode<org.apache.ambari.server.controller.spi.Resource> tree = result.getResultTree();
        org.junit.Assert.assertEquals(1, tree.getChildren().size());
        org.apache.ambari.server.api.util.TreeNode<org.apache.ambari.server.controller.spi.Resource> resourcesNode = tree.getChild("resources");
        org.junit.Assert.assertEquals(2, resourcesNode.getChildren().size());
        boolean foundResource1 = false;
        boolean foundResource2 = false;
        for (org.apache.ambari.server.api.util.TreeNode<org.apache.ambari.server.controller.spi.Resource> child : resourcesNode.getChildren()) {
            org.apache.ambari.server.controller.spi.Resource r = child.getObject();
            if ((r == resource1) && (!foundResource1)) {
                foundResource1 = true;
            } else if ((r == resource2) && (!foundResource2)) {
                foundResource2 = true;
            } else {
                org.junit.Assert.fail();
            }
        }
        org.junit.Assert.assertEquals(org.apache.ambari.server.api.services.ResultStatus.STATUS.CREATED, result.getStatus().getStatus());
        org.easymock.EasyMock.verify(request, body, resource, resourceDefinition, query, pm, status, resource1, resource2);
    }

    @org.junit.Test
    public void testHandleRequest__Asynchronous() throws java.lang.Exception {
        org.apache.ambari.server.api.services.Request request = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.services.Request.class);
        org.apache.ambari.server.api.services.RequestBody body = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.services.RequestBody.class);
        org.apache.ambari.server.api.resources.ResourceInstance resource = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.resources.ResourceInstance.class);
        org.apache.ambari.server.api.resources.ResourceDefinition resourceDefinition = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.resources.ResourceDefinition.class);
        org.apache.ambari.server.api.query.Query query = org.easymock.EasyMock.createStrictMock(org.apache.ambari.server.api.query.Query.class);
        org.apache.ambari.server.api.services.persistence.PersistenceManager pm = org.easymock.EasyMock.createStrictMock(org.apache.ambari.server.api.services.persistence.PersistenceManager.class);
        org.apache.ambari.server.controller.spi.RequestStatus status = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.RequestStatus.class);
        org.apache.ambari.server.controller.spi.Resource resource1 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.Resource.class);
        org.apache.ambari.server.controller.spi.Resource resource2 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.Resource.class);
        org.apache.ambari.server.controller.spi.Resource requestResource = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.Resource.class);
        org.apache.ambari.server.api.query.render.Renderer renderer = new org.apache.ambari.server.api.query.render.DefaultRenderer();
        java.util.Set<org.apache.ambari.server.controller.spi.Resource> setResources = new java.util.HashSet<>();
        setResources.add(resource1);
        setResources.add(resource2);
        org.easymock.EasyMock.expect(request.getResource()).andReturn(resource).atLeastOnce();
        org.easymock.EasyMock.expect(request.getBody()).andReturn(body).atLeastOnce();
        org.easymock.EasyMock.expect(request.getQueryPredicate()).andReturn(null).atLeastOnce();
        org.easymock.EasyMock.expect(request.getRenderer()).andReturn(renderer);
        org.easymock.EasyMock.expect(resource.getQuery()).andReturn(query);
        query.setRenderer(renderer);
        org.easymock.EasyMock.expect(resource.getResourceDefinition()).andReturn(resourceDefinition).anyTimes();
        org.easymock.EasyMock.expect(resourceDefinition.isCreatable()).andReturn(true).anyTimes();
        org.easymock.EasyMock.expect(pm.create(resource, body)).andReturn(status);
        org.easymock.EasyMock.expect(status.getStatus()).andReturn(org.apache.ambari.server.controller.spi.RequestStatus.Status.Accepted);
        org.easymock.EasyMock.expect(status.getAssociatedResources()).andReturn(setResources);
        org.easymock.EasyMock.expect(resource1.getType()).andReturn(org.apache.ambari.server.controller.spi.Resource.Type.Cluster).anyTimes();
        org.easymock.EasyMock.expect(resource2.getType()).andReturn(org.apache.ambari.server.controller.spi.Resource.Type.Cluster).anyTimes();
        org.easymock.EasyMock.expect(status.getRequestResource()).andReturn(requestResource).anyTimes();
        org.easymock.EasyMock.replay(request, body, resource, resourceDefinition, query, pm, status, resource1, resource2, requestResource);
        org.apache.ambari.server.api.services.Result result = new org.apache.ambari.server.api.handlers.CreateHandlerTest.TestCreateHandler(pm).handleRequest(request);
        org.junit.Assert.assertNotNull(result);
        org.apache.ambari.server.api.util.TreeNode<org.apache.ambari.server.controller.spi.Resource> tree = result.getResultTree();
        org.junit.Assert.assertEquals(2, tree.getChildren().size());
        org.apache.ambari.server.api.util.TreeNode<org.apache.ambari.server.controller.spi.Resource> resourcesNode = tree.getChild("resources");
        org.junit.Assert.assertEquals(2, resourcesNode.getChildren().size());
        boolean foundResource1 = false;
        boolean foundResource2 = false;
        for (org.apache.ambari.server.api.util.TreeNode<org.apache.ambari.server.controller.spi.Resource> child : resourcesNode.getChildren()) {
            org.apache.ambari.server.controller.spi.Resource r = child.getObject();
            if ((r == resource1) && (!foundResource1)) {
                foundResource1 = true;
            } else if ((r == resource2) && (!foundResource2)) {
                foundResource2 = true;
            } else {
                org.junit.Assert.fail();
            }
        }
        org.apache.ambari.server.api.util.TreeNode<org.apache.ambari.server.controller.spi.Resource> statusNode = tree.getChild("request");
        org.junit.Assert.assertNotNull(statusNode);
        org.junit.Assert.assertEquals(0, statusNode.getChildren().size());
        org.junit.Assert.assertSame(requestResource, statusNode.getObject());
        org.junit.Assert.assertEquals(org.apache.ambari.server.api.services.ResultStatus.STATUS.ACCEPTED, result.getStatus().getStatus());
        org.easymock.EasyMock.verify(request, body, resource, resourceDefinition, query, pm, status, resource1, resource2, requestResource);
    }

    @org.junit.Test
    public void testHandleRequest__AuthorizationFailure() throws java.lang.Exception {
        org.apache.ambari.server.api.services.Request request = org.easymock.EasyMock.createMock(org.apache.ambari.server.api.services.Request.class);
        org.apache.ambari.server.api.services.RequestBody body = org.easymock.EasyMock.createMock(org.apache.ambari.server.api.services.RequestBody.class);
        org.apache.ambari.server.api.resources.ResourceInstance resource = org.easymock.EasyMock.createMock(org.apache.ambari.server.api.resources.ResourceInstance.class);
        org.apache.ambari.server.api.resources.ResourceDefinition resourceDefinition = org.easymock.EasyMock.createMock(org.apache.ambari.server.api.resources.ResourceDefinition.class);
        org.apache.ambari.server.api.query.Query query = org.easymock.EasyMock.createStrictMock(org.apache.ambari.server.api.query.Query.class);
        org.apache.ambari.server.api.services.persistence.PersistenceManager pm = org.easymock.EasyMock.createStrictMock(org.apache.ambari.server.api.services.persistence.PersistenceManager.class);
        org.apache.ambari.server.api.query.render.Renderer renderer = new org.apache.ambari.server.api.query.render.DefaultRenderer();
        org.easymock.EasyMock.expect(request.getResource()).andReturn(resource).atLeastOnce();
        org.easymock.EasyMock.expect(request.getBody()).andReturn(body).atLeastOnce();
        org.easymock.EasyMock.expect(request.getQueryPredicate()).andReturn(null).atLeastOnce();
        org.easymock.EasyMock.expect(request.getRenderer()).andReturn(renderer);
        org.easymock.EasyMock.expect(resource.getQuery()).andReturn(query);
        query.setRenderer(renderer);
        org.easymock.EasyMock.expect(resource.getResourceDefinition()).andReturn(resourceDefinition).anyTimes();
        org.easymock.EasyMock.expect(resourceDefinition.isCreatable()).andReturn(true).anyTimes();
        org.easymock.EasyMock.expect(pm.create(resource, body)).andThrow(new org.apache.ambari.server.security.authorization.AuthorizationException());
        org.easymock.EasyMock.replay(request, body, resource, resourceDefinition, query, pm);
        org.apache.ambari.server.api.services.Result result = new org.apache.ambari.server.api.handlers.CreateHandlerTest.TestCreateHandler(pm).handleRequest(request);
        org.junit.Assert.assertEquals(org.apache.ambari.server.api.services.ResultStatus.STATUS.FORBIDDEN, result.getStatus().getStatus());
        org.easymock.EasyMock.verify(request, body, resource, resourceDefinition, query, pm);
    }

    private class TestCreateHandler extends org.apache.ambari.server.api.handlers.CreateHandler {
        private org.apache.ambari.server.api.services.persistence.PersistenceManager m_testPm;

        private TestCreateHandler(org.apache.ambari.server.api.services.persistence.PersistenceManager pm) {
            m_testPm = pm;
        }

        @java.lang.Override
        protected org.apache.ambari.server.api.services.persistence.PersistenceManager getPersistenceManager() {
            return m_testPm;
        }
    }
}
