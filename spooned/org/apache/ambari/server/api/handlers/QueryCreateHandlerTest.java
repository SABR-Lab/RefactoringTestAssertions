package org.apache.ambari.server.api.handlers;
import org.easymock.Capture;
import org.easymock.EasyMock;
import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.same;
import static org.easymock.EasyMock.verify;
public class QueryCreateHandlerTest {
    @org.junit.Test
    public void testHandleRequest() throws java.lang.Exception {
        final java.lang.String BODY_STRING = "Body string";
        org.apache.ambari.server.api.services.Request request = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.services.Request.class);
        org.apache.ambari.server.api.services.RequestBody body = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.services.RequestBody.class);
        org.apache.ambari.server.api.resources.ResourceInstance resourceInstance = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.resources.ResourceInstance.class);
        org.apache.ambari.server.api.resources.ResourceDefinition resourceDefinition = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.resources.ResourceDefinition.class);
        org.apache.ambari.server.api.resources.ResourceInstanceFactory resourceInstanceFactory = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.resources.ResourceInstanceFactory.class);
        org.apache.ambari.server.api.query.Query query = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.query.Query.class);
        org.apache.ambari.server.controller.spi.Predicate predicate = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.Predicate.class);
        org.apache.ambari.server.api.services.Result result = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.services.Result.class);
        org.apache.ambari.server.api.resources.ResourceInstance subResource = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.resources.ResourceInstance.class);
        org.apache.ambari.server.api.resources.ResourceDefinition subResourceDefinition = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.resources.ResourceDefinition.class);
        org.apache.ambari.server.controller.spi.ClusterController controller = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.ClusterController.class);
        org.apache.ambari.server.controller.spi.Schema serviceSchema = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.Schema.class);
        org.apache.ambari.server.controller.spi.Schema componentSchema = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.Schema.class);
        java.lang.String resourceKeyProperty = "resourceKeyProperty";
        java.lang.String createKeyProperty = "createKeyProperty";
        org.apache.ambari.server.controller.spi.Resource resource1 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.Resource.class);
        org.apache.ambari.server.controller.spi.Resource resource2 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.Resource.class);
        org.apache.ambari.server.api.services.persistence.PersistenceManager pm = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.services.persistence.PersistenceManager.class);
        org.apache.ambari.server.api.resources.ResourceInstance createResource = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.resources.ResourceInstance.class);
        org.apache.ambari.server.controller.spi.RequestStatus status = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.RequestStatus.class);
        org.apache.ambari.server.controller.spi.Resource statusResource1 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.Resource.class);
        org.apache.ambari.server.controller.spi.Resource statusResource2 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.Resource.class);
        org.apache.ambari.server.api.handlers.RequestHandler readHandler = org.easymock.EasyMock.createStrictMock(org.apache.ambari.server.api.handlers.RequestHandler.class);
        org.apache.ambari.server.api.services.ResultStatus resultStatus = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.services.ResultStatus.class);
        org.easymock.Capture<org.apache.ambari.server.api.services.RequestBody> bodyCapture = org.easymock.EasyMock.newCapture();
        java.util.Map<org.apache.ambari.server.controller.spi.Resource.Type, java.lang.String> mapIds = new java.util.HashMap<>();
        java.util.Set<org.apache.ambari.server.api.services.NamedPropertySet> setRequestProps = new java.util.HashSet<>();
        java.util.Map<java.lang.String, java.lang.Object> mapProperties = new java.util.HashMap<>();
        java.util.Set<java.util.Map<java.lang.String, java.lang.Object>> arraySet = new java.util.HashSet<>();
        mapProperties.put("components", arraySet);
        java.util.Map<java.lang.String, java.lang.Object> map = new java.util.HashMap<>();
        map.put(org.apache.ambari.server.controller.utilities.PropertyHelper.getPropertyId("ServiceComponentInfo", "component_name"), "SECONDARY_NAMENODE");
        arraySet.add(map);
        map = new java.util.HashMap<>();
        map.put(org.apache.ambari.server.controller.utilities.PropertyHelper.getPropertyId("ServiceComponentInfo", "component_name"), "HDFS_CLIENT");
        arraySet.add(map);
        setRequestProps.add(new org.apache.ambari.server.api.services.NamedPropertySet("", mapProperties));
        java.util.Set<java.util.Map<java.lang.String, java.lang.Object>> setCreateProps = new java.util.HashSet<>();
        java.util.Map<java.lang.String, java.lang.Object> map1 = new java.util.HashMap<>();
        map1.put(org.apache.ambari.server.controller.utilities.PropertyHelper.getPropertyId("ServiceComponentInfo", "component_name"), "SECONDARY_NAMENODE");
        map1.put(createKeyProperty, "id1");
        setCreateProps.add(map1);
        java.util.Map<java.lang.String, java.lang.Object> map2 = new java.util.HashMap<>();
        map2.put(org.apache.ambari.server.controller.utilities.PropertyHelper.getPropertyId("ServiceComponentInfo", "component_name"), "SECONDARY_NAMENODE");
        map2.put(createKeyProperty, "id2");
        setCreateProps.add(map2);
        java.util.Map<java.lang.String, java.lang.Object> map3 = new java.util.HashMap<>();
        map3.put(org.apache.ambari.server.controller.utilities.PropertyHelper.getPropertyId("ServiceComponentInfo", "component_name"), "HDFS_CLIENT");
        map3.put(createKeyProperty, "id1");
        setCreateProps.add(map3);
        java.util.Map<java.lang.String, java.lang.Object> map4 = new java.util.HashMap<>();
        map4.put(org.apache.ambari.server.controller.utilities.PropertyHelper.getPropertyId("ServiceComponentInfo", "component_name"), "HDFS_CLIENT");
        map4.put(createKeyProperty, "id2");
        setCreateProps.add(map4);
        java.util.Map<java.lang.String, org.apache.ambari.server.api.resources.ResourceInstance> mapSubResources = new java.util.HashMap<>();
        mapSubResources.put("components", subResource);
        org.apache.ambari.server.api.util.TreeNode<org.apache.ambari.server.controller.spi.Resource> resultTree = new org.apache.ambari.server.api.util.TreeNodeImpl<>(null, null, "result");
        resultTree.addChild(resource1, "resource1");
        resultTree.addChild(resource2, "resource2");
        java.util.Set<org.apache.ambari.server.controller.spi.Resource> setStatusResources = new java.util.HashSet<>();
        setStatusResources.add(statusResource1);
        setStatusResources.add(statusResource2);
        org.easymock.EasyMock.expect(readHandler.handleRequest(request)).andReturn(result);
        org.easymock.EasyMock.expect(result.getStatus()).andReturn(resultStatus).anyTimes();
        org.easymock.EasyMock.expect(resultStatus.isErrorState()).andReturn(false);
        org.easymock.EasyMock.expect(result.getResultTree()).andReturn(resultTree);
        org.easymock.EasyMock.expect(body.getBody()).andReturn(BODY_STRING).anyTimes();
        org.easymock.EasyMock.expect(request.getResource()).andReturn(resourceInstance).anyTimes();
        org.easymock.EasyMock.expect(request.getBody()).andReturn(body).anyTimes();
        org.easymock.EasyMock.expect(body.getNamedPropertySets()).andReturn(setRequestProps).anyTimes();
        org.easymock.EasyMock.expect(resourceInstance.getResourceDefinition()).andReturn(resourceDefinition).anyTimes();
        org.easymock.EasyMock.expect(resourceInstance.getKeyValueMap()).andReturn(mapIds).anyTimes();
        org.easymock.EasyMock.expect(resourceInstance.getSubResources()).andReturn(mapSubResources).anyTimes();
        org.easymock.EasyMock.expect(resourceDefinition.getType()).andReturn(org.apache.ambari.server.controller.spi.Resource.Type.Service).anyTimes();
        org.easymock.EasyMock.expect(resourceDefinition.isCreatable()).andReturn(true).anyTimes();
        org.easymock.EasyMock.expect(subResource.getResourceDefinition()).andReturn(subResourceDefinition).anyTimes();
        org.easymock.EasyMock.expect(subResourceDefinition.getType()).andReturn(org.apache.ambari.server.controller.spi.Resource.Type.Component).anyTimes();
        org.easymock.EasyMock.expect(controller.getSchema(org.apache.ambari.server.controller.spi.Resource.Type.Service)).andReturn(serviceSchema).anyTimes();
        org.easymock.EasyMock.expect(controller.getSchema(org.apache.ambari.server.controller.spi.Resource.Type.Component)).andReturn(componentSchema).anyTimes();
        org.easymock.EasyMock.expect(serviceSchema.getKeyPropertyId(org.apache.ambari.server.controller.spi.Resource.Type.Service)).andReturn(resourceKeyProperty).anyTimes();
        org.easymock.EasyMock.expect(componentSchema.getKeyPropertyId(org.apache.ambari.server.controller.spi.Resource.Type.Service)).andReturn(createKeyProperty).anyTimes();
        org.easymock.EasyMock.expect(result.getResultTree()).andReturn(resultTree).anyTimes();
        org.easymock.EasyMock.expect(resource1.getPropertyValue(resourceKeyProperty)).andReturn("id1").anyTimes();
        org.easymock.EasyMock.expect(resource2.getPropertyValue(resourceKeyProperty)).andReturn("id2").anyTimes();
        org.easymock.EasyMock.expect(resourceInstanceFactory.createResource(org.apache.ambari.server.controller.spi.Resource.Type.Component, mapIds)).andReturn(createResource).anyTimes();
        org.easymock.EasyMock.expect(createResource.getResourceDefinition()).andReturn(resourceDefinition).anyTimes();
        org.easymock.EasyMock.expect(pm.create(org.easymock.EasyMock.same(createResource), org.easymock.EasyMock.capture(bodyCapture))).andReturn(status);
        org.easymock.EasyMock.expect(status.getStatus()).andReturn(org.apache.ambari.server.controller.spi.RequestStatus.Status.Complete).anyTimes();
        org.easymock.EasyMock.expect(status.getAssociatedResources()).andReturn(setStatusResources).anyTimes();
        org.easymock.EasyMock.expect(statusResource1.getType()).andReturn(org.apache.ambari.server.controller.spi.Resource.Type.Component).anyTimes();
        org.easymock.EasyMock.expect(statusResource2.getType()).andReturn(org.apache.ambari.server.controller.spi.Resource.Type.Component).anyTimes();
        org.easymock.EasyMock.replay(request, body, resourceInstance, resourceDefinition, query, predicate, result, subResource, subResourceDefinition, controller, serviceSchema, componentSchema, resource1, resource2, pm, resourceInstanceFactory, createResource, status, statusResource1, statusResource2, readHandler, resultStatus);
        org.apache.ambari.server.api.services.Result testResult = new org.apache.ambari.server.api.handlers.QueryCreateHandlerTest.TestQueryCreateHandler(resourceInstanceFactory, controller, pm, readHandler).handleRequest(request);
        java.util.Collection<org.apache.ambari.server.api.util.TreeNode<org.apache.ambari.server.controller.spi.Resource>> children = testResult.getResultTree().getChild("resources").getChildren();
        org.junit.Assert.assertEquals(2, children.size());
        boolean containsStatusResource1 = false;
        boolean containsStatusResource2 = false;
        for (org.apache.ambari.server.api.util.TreeNode<org.apache.ambari.server.controller.spi.Resource> child : children) {
            org.apache.ambari.server.controller.spi.Resource r = child.getObject();
            if (r == statusResource1) {
                containsStatusResource1 = true;
            } else if (r == statusResource2) {
                containsStatusResource2 = true;
            }
        }
        org.junit.Assert.assertTrue(containsStatusResource1);
        org.junit.Assert.assertTrue(containsStatusResource2);
        org.junit.Assert.assertEquals(org.apache.ambari.server.api.services.ResultStatus.STATUS.CREATED, testResult.getStatus().getStatus());
        org.apache.ambari.server.api.services.RequestBody createBody = bodyCapture.getValue();
        org.junit.Assert.assertEquals(BODY_STRING, createBody.getBody());
        org.junit.Assert.assertEquals(4, createBody.getPropertySets().size());
        org.junit.Assert.assertEquals(setCreateProps, createBody.getPropertySets());
        org.easymock.EasyMock.verify(request, body, resourceInstance, resourceDefinition, query, predicate, result, subResource, subResourceDefinition, controller, serviceSchema, componentSchema, resource1, resource2, pm, resourceInstanceFactory, createResource, status, statusResource1, statusResource2, readHandler, resultStatus);
    }

    @org.junit.Test
    public void tesHandleRequest_NoSubResourceNameSpecified() {
        org.apache.ambari.server.api.services.Request request = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.services.Request.class);
        org.apache.ambari.server.api.services.RequestBody body = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.services.RequestBody.class);
        org.apache.ambari.server.api.resources.ResourceInstance resourceInstance = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.resources.ResourceInstance.class);
        org.apache.ambari.server.api.resources.ResourceDefinition resourceDefinition = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.resources.ResourceDefinition.class);
        org.apache.ambari.server.api.query.Query query = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.query.Query.class);
        org.apache.ambari.server.controller.spi.Predicate predicate = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.Predicate.class);
        org.apache.ambari.server.api.services.Result result = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.services.Result.class);
        org.apache.ambari.server.api.resources.ResourceInstance subResource = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.resources.ResourceInstance.class);
        org.apache.ambari.server.api.resources.ResourceDefinition subResourceDefinition = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.resources.ResourceDefinition.class);
        org.apache.ambari.server.controller.spi.ClusterController controller = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.ClusterController.class);
        org.apache.ambari.server.controller.spi.Schema serviceSchema = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.Schema.class);
        org.apache.ambari.server.controller.spi.Schema componentSchema = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.Schema.class);
        java.lang.String resourceKeyProperty = "resourceKeyProperty";
        org.apache.ambari.server.controller.spi.Resource resource1 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.Resource.class);
        org.apache.ambari.server.controller.spi.Resource resource2 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.Resource.class);
        org.apache.ambari.server.api.services.persistence.PersistenceManager pm = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.services.persistence.PersistenceManager.class);
        org.apache.ambari.server.api.resources.ResourceInstance createResource = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.resources.ResourceInstance.class);
        org.apache.ambari.server.controller.spi.RequestStatus status = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.RequestStatus.class);
        org.apache.ambari.server.controller.spi.Resource statusResource1 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.Resource.class);
        org.apache.ambari.server.controller.spi.Resource statusResource2 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.Resource.class);
        org.apache.ambari.server.api.handlers.RequestHandler readHandler = org.easymock.EasyMock.createStrictMock(org.apache.ambari.server.api.handlers.RequestHandler.class);
        org.apache.ambari.server.api.services.ResultStatus queryResultStatus = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.services.ResultStatus.class);
        java.util.Map<org.apache.ambari.server.controller.spi.Resource.Type, java.lang.String> mapIds = new java.util.HashMap<>();
        java.util.Set<org.apache.ambari.server.api.services.NamedPropertySet> setRequestProps = new java.util.HashSet<>();
        java.util.Map<java.lang.String, java.lang.Object> mapProperties = new java.util.HashMap<>();
        java.util.Set<java.util.Map<java.lang.String, java.lang.Object>> arraySet = new java.util.HashSet<>();
        mapProperties.put("", arraySet);
        java.util.Map<java.lang.String, java.lang.Object> map = new java.util.HashMap<>();
        map.put(org.apache.ambari.server.controller.utilities.PropertyHelper.getPropertyId("ServiceComponentInfo", "component_name"), "SECONDARY_NAMENODE");
        arraySet.add(map);
        map = new java.util.HashMap<>();
        map.put(org.apache.ambari.server.controller.utilities.PropertyHelper.getPropertyId("ServiceComponentInfo", "component_name"), "HDFS_CLIENT");
        arraySet.add(map);
        setRequestProps.add(new org.apache.ambari.server.api.services.NamedPropertySet("", mapProperties));
        org.apache.ambari.server.api.util.TreeNode<org.apache.ambari.server.controller.spi.Resource> resultTree = new org.apache.ambari.server.api.util.TreeNodeImpl<>(null, null, "result");
        resultTree.addChild(resource1, "resource1");
        resultTree.addChild(resource2, "resource2");
        org.easymock.EasyMock.expect(readHandler.handleRequest(request)).andReturn(result);
        org.easymock.EasyMock.expect(result.getStatus()).andReturn(queryResultStatus).anyTimes();
        org.easymock.EasyMock.expect(queryResultStatus.isErrorState()).andReturn(false);
        org.easymock.EasyMock.expect(result.getResultTree()).andReturn(resultTree);
        org.easymock.EasyMock.expect(request.getResource()).andReturn(resourceInstance).anyTimes();
        org.easymock.EasyMock.expect(request.getBody()).andReturn(body).anyTimes();
        org.easymock.EasyMock.expect(body.getNamedPropertySets()).andReturn(setRequestProps).anyTimes();
        org.easymock.EasyMock.expect(resourceInstance.getResourceDefinition()).andReturn(resourceDefinition).anyTimes();
        org.easymock.EasyMock.expect(resourceInstance.getKeyValueMap()).andReturn(mapIds).anyTimes();
        org.easymock.EasyMock.expect(resourceDefinition.getType()).andReturn(org.apache.ambari.server.controller.spi.Resource.Type.Service).anyTimes();
        org.easymock.EasyMock.expect(controller.getSchema(org.apache.ambari.server.controller.spi.Resource.Type.Service)).andReturn(serviceSchema).anyTimes();
        org.easymock.EasyMock.expect(result.getResultTree()).andReturn(resultTree).anyTimes();
        org.easymock.EasyMock.expect(resource1.getPropertyValue(resourceKeyProperty)).andReturn("id1").anyTimes();
        org.easymock.EasyMock.expect(resource2.getPropertyValue(resourceKeyProperty)).andReturn("id2").anyTimes();
        org.easymock.EasyMock.replay(request, body, resourceInstance, resourceDefinition, query, predicate, result, subResource, subResourceDefinition, controller, serviceSchema, componentSchema, resource1, resource2, pm, createResource, status, statusResource1, statusResource2, readHandler, queryResultStatus);
        org.apache.ambari.server.api.services.Result testResult = new org.apache.ambari.server.api.handlers.QueryCreateHandlerTest.TestQueryCreateHandler(null, controller, pm, readHandler).handleRequest(request);
        org.apache.ambari.server.api.services.ResultStatus resultStatus = testResult.getStatus();
        org.junit.Assert.assertEquals(org.apache.ambari.server.api.services.ResultStatus.STATUS.BAD_REQUEST, resultStatus.getStatus());
        org.junit.Assert.assertEquals("Invalid Request: A sub-resource name must be supplied.", resultStatus.getMessage());
        org.easymock.EasyMock.verify(request, body, resourceInstance, resourceDefinition, query, predicate, result, subResource, subResourceDefinition, controller, serviceSchema, componentSchema, resource1, resource2, pm, createResource, status, statusResource1, statusResource2, readHandler, queryResultStatus);
    }

    @org.junit.Test
    public void tesHandleRequest_InvalidSubResSpecified() {
        org.apache.ambari.server.api.services.Request request = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.services.Request.class);
        org.apache.ambari.server.api.services.RequestBody body = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.services.RequestBody.class);
        org.apache.ambari.server.api.resources.ResourceInstance resourceInstance = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.resources.ResourceInstance.class);
        org.apache.ambari.server.api.resources.ResourceDefinition resourceDefinition = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.resources.ResourceDefinition.class);
        org.apache.ambari.server.api.query.Query query = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.query.Query.class);
        org.apache.ambari.server.controller.spi.Predicate predicate = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.Predicate.class);
        org.apache.ambari.server.api.services.Result result = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.services.Result.class);
        org.apache.ambari.server.api.resources.ResourceInstance subResource = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.resources.ResourceInstance.class);
        org.apache.ambari.server.api.resources.ResourceDefinition subResourceDefinition = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.resources.ResourceDefinition.class);
        org.apache.ambari.server.controller.spi.ClusterController controller = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.ClusterController.class);
        org.apache.ambari.server.controller.spi.Schema serviceSchema = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.Schema.class);
        org.apache.ambari.server.controller.spi.Schema componentSchema = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.Schema.class);
        java.lang.String resourceKeyProperty = "resourceKeyProperty";
        org.apache.ambari.server.controller.spi.Resource resource1 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.Resource.class);
        org.apache.ambari.server.controller.spi.Resource resource2 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.Resource.class);
        org.apache.ambari.server.api.services.persistence.PersistenceManager pm = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.services.persistence.PersistenceManager.class);
        org.apache.ambari.server.api.resources.ResourceInstance createResource = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.resources.ResourceInstance.class);
        org.apache.ambari.server.controller.spi.RequestStatus status = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.RequestStatus.class);
        org.apache.ambari.server.controller.spi.Resource statusResource1 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.Resource.class);
        org.apache.ambari.server.controller.spi.Resource statusResource2 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.Resource.class);
        org.apache.ambari.server.api.handlers.RequestHandler readHandler = org.easymock.EasyMock.createStrictMock(org.apache.ambari.server.api.handlers.RequestHandler.class);
        org.apache.ambari.server.api.services.ResultStatus queryResultStatus = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.services.ResultStatus.class);
        java.util.Map<org.apache.ambari.server.controller.spi.Resource.Type, java.lang.String> mapIds = new java.util.HashMap<>();
        java.util.Set<org.apache.ambari.server.api.services.NamedPropertySet> setRequestProps = new java.util.HashSet<>();
        java.util.Map<java.lang.String, java.lang.Object> mapProperties = new java.util.HashMap<>();
        java.util.Set<java.util.Map<java.lang.String, java.lang.Object>> arraySet = new java.util.HashSet<>();
        mapProperties.put("INVALID", arraySet);
        java.util.Map<java.lang.String, java.lang.Object> map = new java.util.HashMap<>();
        map.put(org.apache.ambari.server.controller.utilities.PropertyHelper.getPropertyId("ServiceComponentInfo", "component_name"), "SECONDARY_NAMENODE");
        arraySet.add(map);
        map = new java.util.HashMap<>();
        map.put(org.apache.ambari.server.controller.utilities.PropertyHelper.getPropertyId("ServiceComponentInfo", "component_name"), "HDFS_CLIENT");
        arraySet.add(map);
        setRequestProps.add(new org.apache.ambari.server.api.services.NamedPropertySet("", mapProperties));
        java.util.Map<java.lang.String, org.apache.ambari.server.api.resources.ResourceInstance> mapSubResources = new java.util.HashMap<>();
        mapSubResources.put("components", subResource);
        org.apache.ambari.server.api.util.TreeNode<org.apache.ambari.server.controller.spi.Resource> resultTree = new org.apache.ambari.server.api.util.TreeNodeImpl<>(null, null, "result");
        resultTree.addChild(resource1, "resource1");
        resultTree.addChild(resource2, "resource2");
        org.easymock.EasyMock.expect(readHandler.handleRequest(request)).andReturn(result);
        org.easymock.EasyMock.expect(result.getStatus()).andReturn(queryResultStatus).anyTimes();
        org.easymock.EasyMock.expect(queryResultStatus.isErrorState()).andReturn(false);
        org.easymock.EasyMock.expect(result.getResultTree()).andReturn(resultTree);
        org.easymock.EasyMock.expect(request.getResource()).andReturn(resourceInstance).anyTimes();
        org.easymock.EasyMock.expect(request.getBody()).andReturn(body).anyTimes();
        org.easymock.EasyMock.expect(body.getNamedPropertySets()).andReturn(setRequestProps).anyTimes();
        org.easymock.EasyMock.expect(resourceInstance.getResourceDefinition()).andReturn(resourceDefinition).anyTimes();
        org.easymock.EasyMock.expect(resourceInstance.getKeyValueMap()).andReturn(mapIds).anyTimes();
        org.easymock.EasyMock.expect(resourceInstance.getSubResources()).andReturn(mapSubResources).anyTimes();
        org.easymock.EasyMock.expect(resourceDefinition.getType()).andReturn(org.apache.ambari.server.controller.spi.Resource.Type.Service).anyTimes();
        org.easymock.EasyMock.expect(controller.getSchema(org.apache.ambari.server.controller.spi.Resource.Type.Service)).andReturn(serviceSchema).anyTimes();
        org.easymock.EasyMock.expect(result.getResultTree()).andReturn(resultTree).anyTimes();
        org.easymock.EasyMock.expect(resource1.getPropertyValue(resourceKeyProperty)).andReturn("id1").anyTimes();
        org.easymock.EasyMock.expect(resource2.getPropertyValue(resourceKeyProperty)).andReturn("id2").anyTimes();
        org.easymock.EasyMock.replay(request, body, resourceInstance, resourceDefinition, query, predicate, result, subResource, subResourceDefinition, controller, serviceSchema, componentSchema, resource1, resource2, pm, createResource, status, statusResource1, statusResource2, readHandler, queryResultStatus);
        org.apache.ambari.server.api.services.Result testResult = new org.apache.ambari.server.api.handlers.QueryCreateHandlerTest.TestQueryCreateHandler(null, controller, pm, readHandler).handleRequest(request);
        org.apache.ambari.server.api.services.ResultStatus resultStatus = testResult.getStatus();
        org.junit.Assert.assertEquals(org.apache.ambari.server.api.services.ResultStatus.STATUS.BAD_REQUEST, resultStatus.getStatus());
        org.junit.Assert.assertEquals("Invalid Request: The specified sub-resource name is not valid: 'INVALID'.", resultStatus.getMessage());
        org.easymock.EasyMock.verify(request, body, resourceInstance, resourceDefinition, query, predicate, result, subResource, subResourceDefinition, controller, serviceSchema, componentSchema, resource1, resource2, pm, createResource, status, statusResource1, statusResource2, readHandler, queryResultStatus);
    }

    @org.junit.Test
    public void tesHandleRequest_NoSubResourcesSpecified() {
        org.apache.ambari.server.api.services.Request request = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.services.Request.class);
        org.apache.ambari.server.api.services.RequestBody body = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.services.RequestBody.class);
        org.apache.ambari.server.api.resources.ResourceInstance resourceInstance = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.resources.ResourceInstance.class);
        org.apache.ambari.server.api.resources.ResourceDefinition resourceDefinition = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.resources.ResourceDefinition.class);
        org.apache.ambari.server.api.query.Query query = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.query.Query.class);
        org.apache.ambari.server.controller.spi.Predicate predicate = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.Predicate.class);
        org.apache.ambari.server.api.services.Result result = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.services.Result.class);
        org.apache.ambari.server.api.resources.ResourceInstance subResource = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.resources.ResourceInstance.class);
        org.apache.ambari.server.api.resources.ResourceDefinition subResourceDefinition = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.resources.ResourceDefinition.class);
        org.apache.ambari.server.controller.spi.ClusterController controller = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.ClusterController.class);
        org.apache.ambari.server.controller.spi.Schema serviceSchema = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.Schema.class);
        org.apache.ambari.server.controller.spi.Schema componentSchema = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.Schema.class);
        java.lang.String resourceKeyProperty = "resourceKeyProperty";
        org.apache.ambari.server.controller.spi.Resource resource1 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.Resource.class);
        org.apache.ambari.server.controller.spi.Resource resource2 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.Resource.class);
        org.apache.ambari.server.api.services.persistence.PersistenceManager pm = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.services.persistence.PersistenceManager.class);
        org.apache.ambari.server.api.resources.ResourceInstance createResource = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.resources.ResourceInstance.class);
        org.apache.ambari.server.controller.spi.RequestStatus status = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.RequestStatus.class);
        org.apache.ambari.server.controller.spi.Resource statusResource1 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.Resource.class);
        org.apache.ambari.server.controller.spi.Resource statusResource2 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.Resource.class);
        org.apache.ambari.server.api.handlers.RequestHandler readHandler = org.easymock.EasyMock.createStrictMock(org.apache.ambari.server.api.handlers.RequestHandler.class);
        org.apache.ambari.server.api.services.ResultStatus queryResultStatus = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.services.ResultStatus.class);
        java.util.Map<org.apache.ambari.server.controller.spi.Resource.Type, java.lang.String> mapIds = new java.util.HashMap<>();
        java.util.Set<org.apache.ambari.server.api.services.NamedPropertySet> setRequestProps = new java.util.HashSet<>();
        org.apache.ambari.server.api.util.TreeNode<org.apache.ambari.server.controller.spi.Resource> resultTree = new org.apache.ambari.server.api.util.TreeNodeImpl<>(null, null, "result");
        resultTree.addChild(resource1, "resource1");
        resultTree.addChild(resource2, "resource2");
        org.easymock.EasyMock.expect(readHandler.handleRequest(request)).andReturn(result);
        org.easymock.EasyMock.expect(result.getStatus()).andReturn(queryResultStatus).anyTimes();
        org.easymock.EasyMock.expect(queryResultStatus.isErrorState()).andReturn(false);
        org.easymock.EasyMock.expect(result.getResultTree()).andReturn(resultTree);
        org.easymock.EasyMock.expect(request.getResource()).andReturn(resourceInstance).anyTimes();
        org.easymock.EasyMock.expect(request.getBody()).andReturn(body).anyTimes();
        org.easymock.EasyMock.expect(body.getNamedPropertySets()).andReturn(setRequestProps).anyTimes();
        org.easymock.EasyMock.expect(resourceInstance.getResourceDefinition()).andReturn(resourceDefinition).anyTimes();
        org.easymock.EasyMock.expect(resourceInstance.getKeyValueMap()).andReturn(mapIds).anyTimes();
        org.easymock.EasyMock.expect(resourceDefinition.getType()).andReturn(org.apache.ambari.server.controller.spi.Resource.Type.Service).anyTimes();
        org.easymock.EasyMock.expect(controller.getSchema(org.apache.ambari.server.controller.spi.Resource.Type.Service)).andReturn(serviceSchema).anyTimes();
        org.easymock.EasyMock.expect(result.getResultTree()).andReturn(resultTree).anyTimes();
        org.easymock.EasyMock.expect(resource1.getPropertyValue(resourceKeyProperty)).andReturn("id1").anyTimes();
        org.easymock.EasyMock.expect(resource2.getPropertyValue(resourceKeyProperty)).andReturn("id2").anyTimes();
        org.easymock.EasyMock.replay(request, body, resourceInstance, resourceDefinition, query, predicate, result, subResource, subResourceDefinition, controller, serviceSchema, componentSchema, resource1, resource2, pm, createResource, status, statusResource1, statusResource2, readHandler, queryResultStatus);
        org.apache.ambari.server.api.services.Result testResult = new org.apache.ambari.server.api.handlers.QueryCreateHandlerTest.TestQueryCreateHandler(null, controller, pm, readHandler).handleRequest(request);
        org.apache.ambari.server.api.services.ResultStatus resultStatus = testResult.getStatus();
        org.junit.Assert.assertEquals(org.apache.ambari.server.api.services.ResultStatus.STATUS.BAD_REQUEST, resultStatus.getStatus());
        org.junit.Assert.assertEquals("Invalid Request: A minimum of one sub-resource must be specified for creation.", resultStatus.getMessage());
        org.easymock.EasyMock.verify(request, body, resourceInstance, resourceDefinition, query, predicate, result, subResource, subResourceDefinition, controller, serviceSchema, componentSchema, resource1, resource2, pm, createResource, status, statusResource1, statusResource2, readHandler, queryResultStatus);
    }

    @org.junit.Test
    public void testHandleRequest_MultipleSubResources() throws java.lang.Exception {
        org.apache.ambari.server.api.services.Request request = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.services.Request.class);
        org.apache.ambari.server.api.services.RequestBody body = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.services.RequestBody.class);
        org.apache.ambari.server.api.resources.ResourceInstance resourceInstance = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.resources.ResourceInstance.class);
        org.apache.ambari.server.api.resources.ResourceDefinition resourceDefinition = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.resources.ResourceDefinition.class);
        org.apache.ambari.server.api.resources.ResourceInstanceFactory resourceInstanceFactory = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.resources.ResourceInstanceFactory.class);
        org.apache.ambari.server.api.query.Query query = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.query.Query.class);
        org.apache.ambari.server.controller.spi.Predicate predicate = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.Predicate.class);
        org.apache.ambari.server.api.services.Result result = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.services.Result.class);
        org.apache.ambari.server.api.resources.ResourceInstance subResource1 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.resources.ResourceInstance.class);
        org.apache.ambari.server.api.resources.ResourceInstance subResource2 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.resources.ResourceInstance.class);
        org.apache.ambari.server.api.resources.ResourceDefinition subResourceDefinition1 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.resources.ResourceDefinition.class);
        org.apache.ambari.server.api.resources.ResourceDefinition subResourceDefinition2 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.resources.ResourceDefinition.class);
        org.apache.ambari.server.controller.spi.ClusterController controller = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.ClusterController.class);
        org.apache.ambari.server.controller.spi.Schema serviceSchema = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.Schema.class);
        org.apache.ambari.server.controller.spi.Schema subResSchema1 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.Schema.class);
        org.apache.ambari.server.controller.spi.Schema subResSchema2 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.Schema.class);
        java.lang.String resourceKeyProperty = "resourceKeyProperty";
        java.lang.String createKeyProperty = "createKeyProperty";
        org.apache.ambari.server.controller.spi.Resource resource1 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.Resource.class);
        org.apache.ambari.server.controller.spi.Resource resource2 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.Resource.class);
        org.apache.ambari.server.api.services.persistence.PersistenceManager pm = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.services.persistence.PersistenceManager.class);
        org.apache.ambari.server.api.resources.ResourceInstance createResource = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.resources.ResourceInstance.class);
        org.apache.ambari.server.controller.spi.RequestStatus status = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.RequestStatus.class);
        org.apache.ambari.server.controller.spi.Resource statusResource1 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.Resource.class);
        org.apache.ambari.server.controller.spi.Resource statusResource2 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.Resource.class);
        org.apache.ambari.server.api.handlers.RequestHandler readHandler = org.easymock.EasyMock.createStrictMock(org.apache.ambari.server.api.handlers.RequestHandler.class);
        org.apache.ambari.server.api.services.ResultStatus queryResultStatus = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.services.ResultStatus.class);
        java.util.Map<org.apache.ambari.server.controller.spi.Resource.Type, java.lang.String> mapIds = new java.util.HashMap<>();
        java.util.Set<org.apache.ambari.server.api.services.NamedPropertySet> setRequestProps = new java.util.HashSet<>();
        java.util.Map<java.lang.String, java.lang.Object> mapProperties = new java.util.HashMap<>();
        java.util.Set<java.util.Map<java.lang.String, java.lang.Object>> arraySet = new java.util.HashSet<>();
        mapProperties.put("foo", arraySet);
        java.util.Map<java.lang.String, java.lang.Object> map = new java.util.HashMap<>();
        map.put("prop", "val");
        arraySet.add(map);
        arraySet = new java.util.HashSet<>();
        mapProperties.put("bar", arraySet);
        map = new java.util.HashMap<>();
        map.put("prop", "val");
        arraySet.add(map);
        setRequestProps.add(new org.apache.ambari.server.api.services.NamedPropertySet("", mapProperties));
        java.util.Map<java.lang.String, org.apache.ambari.server.api.resources.ResourceInstance> mapSubResources = new java.util.HashMap<>();
        mapSubResources.put("foo", subResource1);
        mapSubResources.put("bar", subResource2);
        org.apache.ambari.server.api.util.TreeNode<org.apache.ambari.server.controller.spi.Resource> resultTree = new org.apache.ambari.server.api.util.TreeNodeImpl<>(null, null, "result");
        resultTree.addChild(resource1, "resource1");
        resultTree.addChild(resource2, "resource2");
        org.easymock.EasyMock.expect(readHandler.handleRequest(request)).andReturn(result);
        org.easymock.EasyMock.expect(result.getStatus()).andReturn(queryResultStatus).anyTimes();
        org.easymock.EasyMock.expect(queryResultStatus.isErrorState()).andReturn(false);
        org.easymock.EasyMock.expect(result.getResultTree()).andReturn(resultTree);
        org.easymock.EasyMock.expect(request.getResource()).andReturn(resourceInstance).anyTimes();
        org.easymock.EasyMock.expect(request.getBody()).andReturn(body).anyTimes();
        org.easymock.EasyMock.expect(body.getNamedPropertySets()).andReturn(setRequestProps).anyTimes();
        org.easymock.EasyMock.expect(resourceInstance.getResourceDefinition()).andReturn(resourceDefinition).anyTimes();
        org.easymock.EasyMock.expect(resourceInstance.getKeyValueMap()).andReturn(mapIds).anyTimes();
        org.easymock.EasyMock.expect(resourceInstance.getSubResources()).andReturn(mapSubResources).anyTimes();
        org.easymock.EasyMock.expect(resourceDefinition.getType()).andReturn(org.apache.ambari.server.controller.spi.Resource.Type.Service).anyTimes();
        org.easymock.EasyMock.expect(subResource1.getResourceDefinition()).andReturn(subResourceDefinition1).anyTimes();
        org.easymock.EasyMock.expect(subResourceDefinition1.getType()).andReturn(org.apache.ambari.server.controller.spi.Resource.Type.Component).anyTimes();
        org.easymock.EasyMock.expect(subResource2.getResourceDefinition()).andReturn(subResourceDefinition2).anyTimes();
        org.easymock.EasyMock.expect(subResourceDefinition2.getType()).andReturn(org.apache.ambari.server.controller.spi.Resource.Type.HostComponent).anyTimes();
        org.easymock.EasyMock.expect(controller.getSchema(org.apache.ambari.server.controller.spi.Resource.Type.Service)).andReturn(serviceSchema).anyTimes();
        org.easymock.EasyMock.expect(controller.getSchema(org.apache.ambari.server.controller.spi.Resource.Type.Component)).andReturn(subResSchema1).anyTimes();
        org.easymock.EasyMock.expect(controller.getSchema(org.apache.ambari.server.controller.spi.Resource.Type.HostComponent)).andReturn(subResSchema2).anyTimes();
        org.easymock.EasyMock.expect(serviceSchema.getKeyPropertyId(org.apache.ambari.server.controller.spi.Resource.Type.Service)).andReturn(resourceKeyProperty).anyTimes();
        org.easymock.EasyMock.expect(subResSchema1.getKeyPropertyId(org.apache.ambari.server.controller.spi.Resource.Type.Service)).andReturn(createKeyProperty).anyTimes();
        org.easymock.EasyMock.expect(subResSchema2.getKeyPropertyId(org.apache.ambari.server.controller.spi.Resource.Type.Service)).andReturn(createKeyProperty).anyTimes();
        org.easymock.EasyMock.expect(result.getResultTree()).andReturn(resultTree).anyTimes();
        org.easymock.EasyMock.expect(resource1.getPropertyValue(resourceKeyProperty)).andReturn("id1").anyTimes();
        org.easymock.EasyMock.expect(resource2.getPropertyValue(resourceKeyProperty)).andReturn("id2").anyTimes();
        org.easymock.EasyMock.replay(request, body, resourceInstance, resourceDefinition, query, predicate, result, subResource1, subResource2, subResourceDefinition1, subResourceDefinition2, controller, serviceSchema, subResSchema1, subResSchema2, resource1, resource2, pm, resourceInstanceFactory, createResource, status, statusResource1, statusResource2, readHandler, queryResultStatus);
        org.apache.ambari.server.api.services.Result testResult = new org.apache.ambari.server.api.handlers.QueryCreateHandlerTest.TestQueryCreateHandler(resourceInstanceFactory, controller, pm, readHandler).handleRequest(request);
        org.apache.ambari.server.api.services.ResultStatus resultStatus = testResult.getStatus();
        org.junit.Assert.assertEquals(org.apache.ambari.server.api.services.ResultStatus.STATUS.BAD_REQUEST, resultStatus.getStatus());
        org.junit.Assert.assertEquals("Invalid Request: Multiple sub-resource types may not be created in the same request.", resultStatus.getMessage());
        org.easymock.EasyMock.verify(request, body, resourceInstance, resourceDefinition, query, predicate, result, subResource1, subResource2, subResourceDefinition1, subResourceDefinition2, controller, serviceSchema, subResSchema1, subResSchema2, resource1, resource2, pm, resourceInstanceFactory, createResource, status, statusResource1, statusResource2, readHandler, queryResultStatus);
    }

    @org.junit.Test
    public void testHandleRequest_AuthorizationFailure() throws java.lang.Exception {
        final java.lang.String BODY_STRING = "Body string";
        org.apache.ambari.server.api.services.Request request = org.easymock.EasyMock.createMock(org.apache.ambari.server.api.services.Request.class);
        org.apache.ambari.server.api.services.RequestBody body = org.easymock.EasyMock.createMock(org.apache.ambari.server.api.services.RequestBody.class);
        org.apache.ambari.server.api.resources.ResourceInstance resourceInstance = org.easymock.EasyMock.createMock(org.apache.ambari.server.api.resources.ResourceInstance.class);
        org.apache.ambari.server.api.resources.ResourceDefinition resourceDefinition = org.easymock.EasyMock.createMock(org.apache.ambari.server.api.resources.ResourceDefinition.class);
        org.apache.ambari.server.api.resources.ResourceInstanceFactory resourceInstanceFactory = org.easymock.EasyMock.createMock(org.apache.ambari.server.api.resources.ResourceInstanceFactory.class);
        org.apache.ambari.server.api.query.Query query = org.easymock.EasyMock.createMock(org.apache.ambari.server.api.query.Query.class);
        org.apache.ambari.server.controller.spi.Predicate predicate = org.easymock.EasyMock.createMock(org.apache.ambari.server.controller.spi.Predicate.class);
        org.apache.ambari.server.api.services.Result result = org.easymock.EasyMock.createMock(org.apache.ambari.server.api.services.Result.class);
        org.apache.ambari.server.api.resources.ResourceInstance subResource = org.easymock.EasyMock.createMock(org.apache.ambari.server.api.resources.ResourceInstance.class);
        org.apache.ambari.server.api.resources.ResourceDefinition subResourceDefinition = org.easymock.EasyMock.createMock(org.apache.ambari.server.api.resources.ResourceDefinition.class);
        org.apache.ambari.server.controller.spi.ClusterController controller = org.easymock.EasyMock.createMock(org.apache.ambari.server.controller.spi.ClusterController.class);
        org.apache.ambari.server.controller.spi.Schema serviceSchema = org.easymock.EasyMock.createMock(org.apache.ambari.server.controller.spi.Schema.class);
        org.apache.ambari.server.controller.spi.Schema componentSchema = org.easymock.EasyMock.createMock(org.apache.ambari.server.controller.spi.Schema.class);
        java.lang.String resourceKeyProperty = "resourceKeyProperty";
        java.lang.String createKeyProperty = "createKeyProperty";
        org.apache.ambari.server.controller.spi.Resource resource1 = org.easymock.EasyMock.createMock(org.apache.ambari.server.controller.spi.Resource.class);
        org.apache.ambari.server.controller.spi.Resource resource2 = org.easymock.EasyMock.createMock(org.apache.ambari.server.controller.spi.Resource.class);
        org.apache.ambari.server.api.services.persistence.PersistenceManager pm = org.easymock.EasyMock.createMock(org.apache.ambari.server.api.services.persistence.PersistenceManager.class);
        org.apache.ambari.server.api.resources.ResourceInstance createResource = org.easymock.EasyMock.createMock(org.apache.ambari.server.api.resources.ResourceInstance.class);
        org.apache.ambari.server.api.handlers.RequestHandler readHandler = org.easymock.EasyMock.createStrictMock(org.apache.ambari.server.api.handlers.RequestHandler.class);
        org.apache.ambari.server.api.services.ResultStatus resultStatus = org.easymock.EasyMock.createMock(org.apache.ambari.server.api.services.ResultStatus.class);
        java.util.Map<org.apache.ambari.server.controller.spi.Resource.Type, java.lang.String> mapIds = new java.util.HashMap<>();
        java.util.Set<org.apache.ambari.server.api.services.NamedPropertySet> setRequestProps = new java.util.HashSet<>();
        java.util.Map<java.lang.String, java.lang.Object> mapProperties = new java.util.HashMap<>();
        java.util.Set<java.util.Map<java.lang.String, java.lang.Object>> arraySet = new java.util.HashSet<>();
        mapProperties.put("components", arraySet);
        java.util.Map<java.lang.String, java.lang.Object> map = new java.util.HashMap<>();
        map.put(org.apache.ambari.server.controller.utilities.PropertyHelper.getPropertyId("ServiceComponentInfo", "component_name"), "SECONDARY_NAMENODE");
        arraySet.add(map);
        map = new java.util.HashMap<>();
        map.put(org.apache.ambari.server.controller.utilities.PropertyHelper.getPropertyId("ServiceComponentInfo", "component_name"), "HDFS_CLIENT");
        arraySet.add(map);
        setRequestProps.add(new org.apache.ambari.server.api.services.NamedPropertySet("", mapProperties));
        java.util.Map<java.lang.String, org.apache.ambari.server.api.resources.ResourceInstance> mapSubResources = new java.util.HashMap<>();
        mapSubResources.put("components", subResource);
        org.apache.ambari.server.api.util.TreeNode<org.apache.ambari.server.controller.spi.Resource> resultTree = new org.apache.ambari.server.api.util.TreeNodeImpl<>(null, null, "result");
        resultTree.addChild(resource1, "resource1");
        resultTree.addChild(resource2, "resource2");
        org.easymock.EasyMock.expect(readHandler.handleRequest(request)).andReturn(result).atLeastOnce();
        org.easymock.EasyMock.expect(result.getStatus()).andReturn(resultStatus).atLeastOnce();
        org.easymock.EasyMock.expect(resultStatus.isErrorState()).andReturn(false).atLeastOnce();
        org.easymock.EasyMock.expect(body.getBody()).andReturn(BODY_STRING).atLeastOnce();
        org.easymock.EasyMock.expect(request.getResource()).andReturn(resourceInstance).atLeastOnce();
        org.easymock.EasyMock.expect(request.getBody()).andReturn(body).atLeastOnce();
        org.easymock.EasyMock.expect(body.getNamedPropertySets()).andReturn(setRequestProps).atLeastOnce();
        org.easymock.EasyMock.expect(resourceInstance.getResourceDefinition()).andReturn(resourceDefinition).atLeastOnce();
        org.easymock.EasyMock.expect(resourceInstance.getKeyValueMap()).andReturn(mapIds).atLeastOnce();
        org.easymock.EasyMock.expect(resourceInstance.getSubResources()).andReturn(mapSubResources).atLeastOnce();
        org.easymock.EasyMock.expect(resourceDefinition.getType()).andReturn(org.apache.ambari.server.controller.spi.Resource.Type.Service).atLeastOnce();
        org.easymock.EasyMock.expect(subResource.getResourceDefinition()).andReturn(subResourceDefinition).atLeastOnce();
        org.easymock.EasyMock.expect(subResourceDefinition.getType()).andReturn(org.apache.ambari.server.controller.spi.Resource.Type.Component).atLeastOnce();
        org.easymock.EasyMock.expect(controller.getSchema(org.apache.ambari.server.controller.spi.Resource.Type.Service)).andReturn(serviceSchema).atLeastOnce();
        org.easymock.EasyMock.expect(controller.getSchema(org.apache.ambari.server.controller.spi.Resource.Type.Component)).andReturn(componentSchema).atLeastOnce();
        org.easymock.EasyMock.expect(serviceSchema.getKeyPropertyId(org.apache.ambari.server.controller.spi.Resource.Type.Service)).andReturn(resourceKeyProperty).atLeastOnce();
        org.easymock.EasyMock.expect(componentSchema.getKeyPropertyId(org.apache.ambari.server.controller.spi.Resource.Type.Service)).andReturn(createKeyProperty).atLeastOnce();
        org.easymock.EasyMock.expect(result.getResultTree()).andReturn(resultTree).atLeastOnce();
        org.easymock.EasyMock.expect(resource1.getPropertyValue(resourceKeyProperty)).andReturn("id1").atLeastOnce();
        org.easymock.EasyMock.expect(resource2.getPropertyValue(resourceKeyProperty)).andReturn("id2").atLeastOnce();
        org.easymock.EasyMock.expect(resourceInstanceFactory.createResource(org.apache.ambari.server.controller.spi.Resource.Type.Component, mapIds)).andReturn(createResource).atLeastOnce();
        org.easymock.EasyMock.expect(pm.create(org.easymock.EasyMock.anyObject(org.apache.ambari.server.api.resources.ResourceInstance.class), org.easymock.EasyMock.anyObject(org.apache.ambari.server.api.services.RequestBody.class))).andThrow(new org.apache.ambari.server.security.authorization.AuthorizationException());
        org.easymock.EasyMock.replay(request, body, resourceInstance, resourceDefinition, query, predicate, result, subResource, subResourceDefinition, controller, serviceSchema, componentSchema, resource1, resource2, pm, resourceInstanceFactory, createResource, readHandler, resultStatus);
        org.apache.ambari.server.api.services.Result testResult = new org.apache.ambari.server.api.handlers.QueryCreateHandlerTest.TestQueryCreateHandler(resourceInstanceFactory, controller, pm, readHandler).handleRequest(request);
        org.junit.Assert.assertEquals(org.apache.ambari.server.api.services.ResultStatus.STATUS.FORBIDDEN, testResult.getStatus().getStatus());
        org.easymock.EasyMock.verify(request, body, resourceInstance, resourceDefinition, query, predicate, result, subResource, subResourceDefinition, controller, serviceSchema, componentSchema, resource1, resource2, pm, resourceInstanceFactory, createResource, readHandler, resultStatus);
    }

    static class TestQueryCreateHandler extends org.apache.ambari.server.api.handlers.QueryCreateHandler {
        private org.apache.ambari.server.api.resources.ResourceInstanceFactory m_resourceFactory;

        private org.apache.ambari.server.controller.spi.ClusterController m_controller;

        private org.apache.ambari.server.api.services.persistence.PersistenceManager m_testPm;

        private org.apache.ambari.server.api.handlers.RequestHandler m_testReadHandler;

        TestQueryCreateHandler(org.apache.ambari.server.api.resources.ResourceInstanceFactory resourceFactory, org.apache.ambari.server.controller.spi.ClusterController controller, org.apache.ambari.server.api.services.persistence.PersistenceManager pm, org.apache.ambari.server.api.handlers.RequestHandler readHandler) {
            m_resourceFactory = resourceFactory;
            m_controller = controller;
            m_testPm = pm;
            m_testReadHandler = readHandler;
        }

        @java.lang.Override
        protected org.apache.ambari.server.api.resources.ResourceInstanceFactory getResourceFactory() {
            return m_resourceFactory;
        }

        @java.lang.Override
        protected org.apache.ambari.server.controller.spi.ClusterController getClusterController() {
            return m_controller;
        }

        @java.lang.Override
        protected org.apache.ambari.server.api.services.persistence.PersistenceManager getPersistenceManager() {
            return m_testPm;
        }

        @java.lang.Override
        protected org.apache.ambari.server.api.handlers.RequestHandler getReadHandler() {
            return m_testReadHandler;
        }
    }
}
