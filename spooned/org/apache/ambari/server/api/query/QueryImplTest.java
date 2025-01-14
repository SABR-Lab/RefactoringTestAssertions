package org.apache.ambari.server.api.query;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import static org.easymock.EasyMock.anyBoolean;
import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
public class QueryImplTest {
    @org.junit.Test
    public void testIsCollection__True() {
        org.apache.ambari.server.api.resources.ResourceDefinition resourceDefinition = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.resources.ResourceDefinition.class);
        java.util.Map<org.apache.ambari.server.controller.spi.Resource.Type, java.lang.String> mapIds = new java.util.HashMap<>();
        mapIds.put(org.apache.ambari.server.controller.spi.Resource.Type.Cluster, "cluster");
        mapIds.put(org.apache.ambari.server.controller.spi.Resource.Type.Service, null);
        org.easymock.EasyMock.expect(resourceDefinition.getType()).andReturn(org.apache.ambari.server.controller.spi.Resource.Type.Service).anyTimes();
        org.easymock.EasyMock.expect(resourceDefinition.getSubResourceDefinitions()).andReturn(java.util.Collections.emptySet()).anyTimes();
        org.easymock.EasyMock.replay(resourceDefinition);
        org.apache.ambari.server.api.resources.ResourceInstance instance = new org.apache.ambari.server.api.query.QueryImplTest.TestQuery(mapIds, resourceDefinition);
        org.junit.Assert.assertTrue(instance.isCollectionResource());
        org.easymock.EasyMock.verify(resourceDefinition);
    }

    @org.junit.Test
    public void testIsCollection__False() {
        org.apache.ambari.server.api.resources.ResourceDefinition resourceDefinition = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.resources.ResourceDefinition.class);
        java.util.Map<org.apache.ambari.server.controller.spi.Resource.Type, java.lang.String> mapIds = new java.util.HashMap<>();
        mapIds.put(org.apache.ambari.server.controller.spi.Resource.Type.Cluster, "cluster");
        mapIds.put(org.apache.ambari.server.controller.spi.Resource.Type.Service, "service");
        org.easymock.EasyMock.expect(resourceDefinition.getType()).andReturn(org.apache.ambari.server.controller.spi.Resource.Type.Service).anyTimes();
        org.easymock.EasyMock.expect(resourceDefinition.getSubResourceDefinitions()).andReturn(java.util.Collections.emptySet()).anyTimes();
        org.easymock.EasyMock.replay(resourceDefinition);
        org.apache.ambari.server.api.resources.ResourceInstance instance = new org.apache.ambari.server.api.query.QueryImplTest.TestQuery(mapIds, resourceDefinition);
        org.junit.Assert.assertFalse(instance.isCollectionResource());
        org.easymock.EasyMock.verify(resourceDefinition);
    }

    @org.junit.Test
    public void testExecute__Cluster_instance_noSpecifiedProps() throws java.lang.Exception {
        org.apache.ambari.server.api.resources.ResourceDefinition resourceDefinition = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.resources.ResourceDefinition.class);
        java.util.Map<org.apache.ambari.server.controller.spi.Resource.Type, java.lang.String> mapIds = new java.util.HashMap<>();
        mapIds.put(org.apache.ambari.server.controller.spi.Resource.Type.Cluster, "cluster");
        org.easymock.EasyMock.expect(resourceDefinition.getType()).andReturn(org.apache.ambari.server.controller.spi.Resource.Type.Cluster).anyTimes();
        java.util.Set<org.apache.ambari.server.api.resources.SubResourceDefinition> setChildren = new java.util.HashSet<>();
        setChildren.add(new org.apache.ambari.server.api.resources.SubResourceDefinition(org.apache.ambari.server.controller.spi.Resource.Type.Host));
        org.easymock.EasyMock.expect(resourceDefinition.getSubResourceDefinitions()).andReturn(setChildren).anyTimes();
        org.easymock.EasyMock.replay(resourceDefinition);
        org.apache.ambari.server.api.query.QueryImpl instance = new org.apache.ambari.server.api.query.QueryImplTest.TestQuery(mapIds, resourceDefinition);
        org.apache.ambari.server.api.services.Result result = instance.execute();
        org.easymock.EasyMock.verify(resourceDefinition);
        org.apache.ambari.server.api.util.TreeNode<org.apache.ambari.server.controller.spi.Resource> tree = result.getResultTree();
        org.junit.Assert.assertEquals(1, tree.getChildren().size());
        org.apache.ambari.server.api.util.TreeNode<org.apache.ambari.server.controller.spi.Resource> clusterNode = tree.getChild("Cluster:1");
        org.junit.Assert.assertEquals("Cluster:1", clusterNode.getName());
        org.junit.Assert.assertEquals(org.apache.ambari.server.controller.spi.Resource.Type.Cluster, clusterNode.getObject().getType());
        org.junit.Assert.assertEquals(1, clusterNode.getChildren().size());
        org.apache.ambari.server.api.util.TreeNode<org.apache.ambari.server.controller.spi.Resource> hostNode = clusterNode.getChild("hosts");
        org.junit.Assert.assertEquals(4, hostNode.getChildren().size());
    }

    @org.junit.Test
    public void testExecute__Stack_instance_noSpecifiedProps() throws java.lang.Exception {
        org.apache.ambari.server.api.resources.ResourceDefinition resourceDefinition = new org.apache.ambari.server.api.resources.StackResourceDefinition();
        java.util.Map<org.apache.ambari.server.controller.spi.Resource.Type, java.lang.String> mapIds = new java.util.HashMap<>();
        mapIds.put(org.apache.ambari.server.controller.spi.Resource.Type.Stack, "HDP");
        org.apache.ambari.server.api.query.QueryImpl instance = new org.apache.ambari.server.api.query.QueryImplTest.TestQuery(mapIds, resourceDefinition);
        org.apache.ambari.server.api.services.Result result = instance.execute();
        org.apache.ambari.server.api.util.TreeNode<org.apache.ambari.server.controller.spi.Resource> tree = result.getResultTree();
        org.junit.Assert.assertEquals(1, tree.getChildren().size());
        org.apache.ambari.server.api.util.TreeNode<org.apache.ambari.server.controller.spi.Resource> stackNode = tree.getChild("Stack:1");
        org.junit.Assert.assertEquals("Stack:1", stackNode.getName());
        org.junit.Assert.assertEquals(org.apache.ambari.server.controller.spi.Resource.Type.Stack, stackNode.getObject().getType());
        org.junit.Assert.assertEquals(1, stackNode.getChildren().size());
        org.apache.ambari.server.api.util.TreeNode<org.apache.ambari.server.controller.spi.Resource> versionsNode = stackNode.getChild("versions");
        org.junit.Assert.assertEquals(3, versionsNode.getChildren().size());
    }

    @org.junit.Test
    public void testGetJoinedResourceProperties() throws java.lang.Exception {
        org.apache.ambari.server.api.resources.ResourceDefinition resourceDefinition = new org.apache.ambari.server.api.resources.StackResourceDefinition();
        java.util.Map<org.apache.ambari.server.controller.spi.Resource.Type, java.lang.String> mapIds = new java.util.HashMap<>();
        mapIds.put(org.apache.ambari.server.controller.spi.Resource.Type.Stack, "HDP");
        org.apache.ambari.server.api.query.QueryImpl instance = new org.apache.ambari.server.api.query.QueryImplTest.TestQuery(mapIds, resourceDefinition);
        instance.addProperty("versions/*", null);
        instance.addProperty("versions/operating_systems/*", null);
        instance.addProperty("versions/operating_systems/repositories/*", null);
        instance.execute();
        java.util.Set<java.lang.String> propertyIds = new java.util.HashSet<>();
        propertyIds.add("versions/operating_systems/repositories/Repositories/repo_id");
        propertyIds.add("versions/operating_systems/OperatingSystems/os_type");
        java.util.Map<org.apache.ambari.server.controller.spi.Resource, java.util.Set<java.util.Map<java.lang.String, java.lang.Object>>> resourcePropertiesMap = instance.getJoinedResourceProperties(propertyIds, null, null);
        java.util.Set<java.util.Map<java.lang.String, java.lang.Object>> propertyMaps = null;
        for (java.util.Map.Entry<org.apache.ambari.server.controller.spi.Resource, java.util.Set<java.util.Map<java.lang.String, java.lang.Object>>> resourceSetEntry : resourcePropertiesMap.entrySet()) {
            org.junit.Assert.assertEquals(org.apache.ambari.server.controller.spi.Resource.Type.Stack, resourceSetEntry.getKey().getType());
            propertyMaps = resourceSetEntry.getValue();
        }
        if (propertyMaps == null) {
            org.junit.Assert.fail("No property maps found!");
        }
        org.junit.Assert.assertEquals(6, propertyMaps.size());
        for (java.util.Map<java.lang.String, java.lang.Object> map : propertyMaps) {
            org.junit.Assert.assertEquals(2, map.size());
            org.junit.Assert.assertTrue(map.containsKey("versions/operating_systems/OperatingSystems/os_type"));
            org.junit.Assert.assertTrue(map.containsKey("versions/operating_systems/repositories/Repositories/repo_id"));
        }
    }

    @org.junit.Test
    public void testExecute_subResourcePredicate() throws java.lang.Exception {
        org.apache.ambari.server.api.resources.ResourceDefinition resourceDefinition = new org.apache.ambari.server.api.resources.StackResourceDefinition();
        java.util.Map<org.apache.ambari.server.controller.spi.Resource.Type, java.lang.String> mapIds = new java.util.HashMap<>();
        mapIds.put(org.apache.ambari.server.controller.spi.Resource.Type.Stack, "HDP");
        org.apache.ambari.server.api.query.QueryImpl instance = new org.apache.ambari.server.api.query.QueryImplTest.TestQuery(mapIds, resourceDefinition);
        org.apache.ambari.server.controller.utilities.PredicateBuilder pb = new org.apache.ambari.server.controller.utilities.PredicateBuilder();
        org.apache.ambari.server.controller.spi.Predicate predicate = pb.property("versions/operating_systems/OperatingSystems/os_type").equals("centos5").toPredicate();
        instance.setUserPredicate(predicate);
        org.apache.ambari.server.api.services.Result result = instance.execute();
        org.apache.ambari.server.api.util.TreeNode<org.apache.ambari.server.controller.spi.Resource> tree = result.getResultTree();
        org.junit.Assert.assertEquals(1, tree.getChildren().size());
        org.apache.ambari.server.api.util.TreeNode<org.apache.ambari.server.controller.spi.Resource> stackNode = tree.getChild("Stack:1");
        org.junit.Assert.assertEquals("Stack:1", stackNode.getName());
        org.junit.Assert.assertEquals(org.apache.ambari.server.controller.spi.Resource.Type.Stack, stackNode.getObject().getType());
        org.junit.Assert.assertEquals(1, stackNode.getChildren().size());
        org.apache.ambari.server.api.util.TreeNode<org.apache.ambari.server.controller.spi.Resource> versionsNode = stackNode.getChild("versions");
        org.junit.Assert.assertEquals(3, versionsNode.getChildren().size());
        org.apache.ambari.server.api.util.TreeNode<org.apache.ambari.server.controller.spi.Resource> versionNode = versionsNode.getChild("StackVersion:1");
        org.junit.Assert.assertEquals("StackVersion:1", versionNode.getName());
        org.junit.Assert.assertEquals(org.apache.ambari.server.controller.spi.Resource.Type.StackVersion, versionNode.getObject().getType());
        org.junit.Assert.assertEquals(1, versionNode.getChildren().size());
        org.apache.ambari.server.api.util.TreeNode<org.apache.ambari.server.controller.spi.Resource> opSystemsNode = versionNode.getChild("operating_systems");
        org.junit.Assert.assertEquals(1, opSystemsNode.getChildren().size());
        org.apache.ambari.server.api.util.TreeNode<org.apache.ambari.server.controller.spi.Resource> opSystemNode = opSystemsNode.getChild("OperatingSystem:1");
        org.junit.Assert.assertEquals("OperatingSystem:1", opSystemNode.getName());
        org.apache.ambari.server.controller.spi.Resource osResource = opSystemNode.getObject();
        org.junit.Assert.assertEquals(org.apache.ambari.server.controller.spi.Resource.Type.OperatingSystem, opSystemNode.getObject().getType());
        org.junit.Assert.assertEquals("centos5", osResource.getPropertyValue("OperatingSystems/os_type"));
    }

    @org.junit.Test
    public void testExecute_NoSuchResourceException() throws java.lang.Exception {
        org.apache.ambari.server.api.resources.ResourceDefinition resourceDefinition = new org.apache.ambari.server.api.resources.ClusterResourceDefinition();
        java.util.Map<org.apache.ambari.server.controller.spi.Resource.Type, java.lang.String> mapIds = new java.util.HashMap<>();
        mapIds.put(org.apache.ambari.server.controller.spi.Resource.Type.Cluster, "c1");
        org.apache.ambari.server.controller.spi.ClusterController clusterController = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.ClusterController.class);
        org.apache.ambari.server.controller.spi.QueryResponse queryResponse = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.QueryResponse.class);
        org.apache.ambari.server.controller.spi.Schema schema = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.Schema.class);
        org.apache.ambari.server.api.query.render.Renderer renderer = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.query.render.Renderer.class);
        org.easymock.EasyMock.expect(clusterController.getSchema(org.apache.ambari.server.controller.spi.Resource.Type.Cluster)).andReturn(schema).anyTimes();
        org.easymock.EasyMock.expect(clusterController.getResources(org.easymock.EasyMock.eq(org.apache.ambari.server.controller.spi.Resource.Type.Cluster), org.easymock.EasyMock.anyObject(org.apache.ambari.server.controller.spi.Request.class), org.easymock.EasyMock.anyObject(org.apache.ambari.server.controller.spi.Predicate.class))).andReturn(queryResponse);
        org.easymock.EasyMock.expect(queryResponse.getResources()).andReturn(java.util.Collections.emptySet()).anyTimes();
        org.easymock.EasyMock.expect(schema.getKeyPropertyId(org.apache.ambari.server.controller.spi.Resource.Type.Cluster)).andReturn("Clusters/cluster_name").anyTimes();
        org.apache.ambari.server.api.util.TreeNode<java.util.Set<java.lang.String>> treeNode = new org.apache.ambari.server.api.util.TreeNodeImpl<>(null, java.util.Collections.<java.lang.String>emptySet(), null);
        org.easymock.EasyMock.expect(renderer.finalizeProperties(org.easymock.EasyMock.anyObject(), org.easymock.EasyMock.anyBoolean())).andReturn(treeNode).anyTimes();
        org.easymock.EasyMock.replay(clusterController, queryResponse, schema, renderer);
        org.apache.ambari.server.api.query.QueryImpl query = new org.apache.ambari.server.api.query.QueryImplTest.TestQuery(mapIds, resourceDefinition, clusterController);
        query.setRenderer(renderer);
        try {
            query.execute();
            org.junit.Assert.fail("Expected NoSuchResourceException!");
        } catch (org.apache.ambari.server.controller.spi.NoSuchResourceException e) {
        }
        org.easymock.EasyMock.verify(clusterController, queryResponse, schema, renderer);
    }

    @org.junit.Test
    public void testExecute_SubResourcePropertyPredicate() throws java.lang.Exception {
        org.apache.ambari.server.api.resources.ResourceDefinition resourceDefinition = new org.apache.ambari.server.api.resources.ClusterResourceDefinition();
        java.util.Map<org.apache.ambari.server.controller.spi.Resource.Type, java.lang.String> mapIds = new java.util.HashMap<>();
        mapIds.put(org.apache.ambari.server.controller.spi.Resource.Type.Cluster, "c1");
        mapIds.put(org.apache.ambari.server.controller.spi.Resource.Type.Host, "h1");
        org.apache.ambari.server.controller.spi.ClusterController clusterController = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.ClusterController.class);
        org.apache.ambari.server.controller.spi.QueryResponse clusterResponse = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.QueryResponse.class);
        org.apache.ambari.server.controller.spi.QueryResponse hostResponse = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.QueryResponse.class);
        org.apache.ambari.server.controller.spi.Schema clusterSchema = org.easymock.EasyMock.createNiceMock("ClusterSchema", org.apache.ambari.server.controller.spi.Schema.class);
        org.apache.ambari.server.controller.spi.Schema hostSchema = org.easymock.EasyMock.createNiceMock("HostSchema", org.apache.ambari.server.controller.spi.Schema.class);
        org.apache.ambari.server.api.query.render.Renderer renderer = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.query.render.Renderer.class);
        org.apache.ambari.server.controller.spi.Resource clusterResource = org.easymock.EasyMock.createMock("ClusterResource", org.apache.ambari.server.controller.spi.Resource.class);
        org.apache.ambari.server.controller.spi.Resource hostResource = org.easymock.EasyMock.createMock("HostResource", org.apache.ambari.server.controller.spi.Resource.class);
        java.util.Set<org.apache.ambari.server.controller.spi.Resource> clusterResources = java.util.Collections.singleton(clusterResource);
        java.util.Set<org.apache.ambari.server.controller.spi.Resource> hostResources = java.util.Collections.singleton(hostResource);
        java.lang.Iterable<org.apache.ambari.server.controller.spi.Resource> iterable = org.easymock.EasyMock.createNiceMock(java.lang.Iterable.class);
        java.util.Iterator<org.apache.ambari.server.controller.spi.Resource> iterator = org.easymock.EasyMock.createNiceMock(java.util.Iterator.class);
        org.easymock.EasyMock.expect(clusterController.getSchema(org.apache.ambari.server.controller.spi.Resource.Type.Cluster)).andReturn(clusterSchema).anyTimes();
        org.easymock.EasyMock.expect(clusterController.getSchema(org.apache.ambari.server.controller.spi.Resource.Type.Host)).andReturn(hostSchema).anyTimes();
        org.easymock.EasyMock.expect(clusterController.getResources(org.easymock.EasyMock.eq(org.apache.ambari.server.controller.spi.Resource.Type.Cluster), org.easymock.EasyMock.anyObject(org.apache.ambari.server.controller.spi.Request.class), org.easymock.EasyMock.anyObject(org.apache.ambari.server.controller.spi.Predicate.class))).andReturn(clusterResponse);
        org.easymock.EasyMock.expect(clusterController.getResources(org.easymock.EasyMock.eq(org.apache.ambari.server.controller.spi.Resource.Type.Host), org.easymock.EasyMock.anyObject(org.apache.ambari.server.controller.spi.Request.class), org.easymock.EasyMock.anyObject(org.apache.ambari.server.controller.spi.Predicate.class))).andReturn(hostResponse);
        org.easymock.EasyMock.expect(iterable.iterator()).andReturn(iterator).anyTimes();
        org.easymock.EasyMock.expect(iterator.hasNext()).andReturn(false).anyTimes();
        org.easymock.EasyMock.expect(clusterResponse.getResources()).andReturn(clusterResources).anyTimes();
        org.easymock.EasyMock.expect(hostResponse.getResources()).andReturn(hostResources).anyTimes();
        org.easymock.EasyMock.expect(clusterResource.getType()).andReturn(org.apache.ambari.server.controller.spi.Resource.Type.Cluster).anyTimes();
        org.easymock.EasyMock.expect(hostResource.getType()).andReturn(org.apache.ambari.server.controller.spi.Resource.Type.Host).anyTimes();
        org.easymock.EasyMock.expect(clusterSchema.getKeyPropertyId(org.apache.ambari.server.controller.spi.Resource.Type.Cluster)).andReturn("Clusters/cluster_name").anyTimes();
        org.easymock.EasyMock.expect(clusterSchema.getKeyTypes()).andReturn(java.util.Collections.singleton(org.apache.ambari.server.controller.spi.Resource.Type.Cluster)).anyTimes();
        org.easymock.EasyMock.expect(hostSchema.getKeyPropertyId(org.apache.ambari.server.controller.spi.Resource.Type.Cluster)).andReturn(null).anyTimes();
        org.easymock.EasyMock.expect(hostSchema.getKeyPropertyId(org.apache.ambari.server.controller.spi.Resource.Type.Host)).andReturn("Hosts/host_name").anyTimes();
        org.easymock.EasyMock.expect(hostSchema.getKeyTypes()).andReturn(java.util.Collections.singleton(org.apache.ambari.server.controller.spi.Resource.Type.Host)).anyTimes();
        org.easymock.EasyMock.expect(clusterResource.getPropertyValue("Clusters/cluster_name")).andReturn("c1").anyTimes();
        org.apache.ambari.server.api.util.TreeNode<java.util.Set<java.lang.String>> treeNode = new org.apache.ambari.server.api.util.TreeNodeImpl<>(null, java.util.Collections.<java.lang.String>emptySet(), null);
        org.easymock.EasyMock.expect(renderer.finalizeProperties(org.easymock.EasyMock.anyObject(), org.easymock.EasyMock.anyBoolean())).andReturn(treeNode).anyTimes();
        org.easymock.EasyMock.expect(clusterController.getIterable(org.easymock.EasyMock.eq(org.apache.ambari.server.controller.spi.Resource.Type.Cluster), org.easymock.EasyMock.anyObject(org.apache.ambari.server.controller.spi.QueryResponse.class), org.easymock.EasyMock.anyObject(org.apache.ambari.server.controller.spi.Request.class), org.easymock.EasyMock.anyObject(org.apache.ambari.server.controller.spi.Predicate.class), org.easymock.EasyMock.anyObject(org.apache.ambari.server.controller.spi.PageRequest.class), org.easymock.EasyMock.anyObject(org.apache.ambari.server.controller.spi.SortRequest.class))).andReturn(iterable).anyTimes();
        org.easymock.EasyMock.replay(clusterController, clusterResponse, clusterSchema, renderer, hostSchema, clusterResource, hostResource, hostResponse, iterable, iterator);
        org.apache.ambari.server.api.query.QueryImpl query = new org.apache.ambari.server.api.query.QueryImplTest.TestQuery(mapIds, resourceDefinition, clusterController);
        query.setUserPredicate(new org.apache.ambari.server.controller.utilities.PredicateBuilder().property("hosts/Hosts/host_name").equals("h1").and().property("metrics/boottime").equals("value").toPredicate());
        query.setRenderer(renderer);
        query.execute();
        org.easymock.EasyMock.verify(clusterController, clusterResponse, clusterSchema, renderer, hostSchema, clusterResource, hostResource, hostResponse, iterable, iterator);
    }

    @org.junit.Test
    public void testExecute_collection_NoSuchResourceException() throws java.lang.Exception {
        org.apache.ambari.server.api.resources.ResourceDefinition resourceDefinition = new org.apache.ambari.server.api.resources.ClusterResourceDefinition();
        org.apache.ambari.server.controller.spi.ClusterController clusterController = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.ClusterController.class);
        org.apache.ambari.server.controller.spi.QueryResponse queryResponse = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.QueryResponse.class);
        org.apache.ambari.server.controller.spi.Schema schema = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.Schema.class);
        org.apache.ambari.server.api.query.render.Renderer renderer = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.query.render.Renderer.class);
        java.lang.Iterable<org.apache.ambari.server.controller.spi.Resource> iterable = org.easymock.EasyMock.createNiceMock(java.lang.Iterable.class);
        java.util.Iterator<org.apache.ambari.server.controller.spi.Resource> iterator = org.easymock.EasyMock.createNiceMock(java.util.Iterator.class);
        org.easymock.EasyMock.expect(clusterController.getSchema(org.apache.ambari.server.controller.spi.Resource.Type.Cluster)).andReturn(schema).anyTimes();
        org.easymock.EasyMock.expect(clusterController.getResources(org.easymock.EasyMock.eq(org.apache.ambari.server.controller.spi.Resource.Type.Cluster), org.easymock.EasyMock.anyObject(org.apache.ambari.server.controller.spi.Request.class), org.easymock.EasyMock.anyObject(org.apache.ambari.server.controller.spi.Predicate.class))).andReturn(queryResponse);
        org.easymock.EasyMock.expect(clusterController.getIterable(org.easymock.EasyMock.eq(org.apache.ambari.server.controller.spi.Resource.Type.Cluster), org.easymock.EasyMock.anyObject(org.apache.ambari.server.controller.spi.QueryResponse.class), org.easymock.EasyMock.anyObject(org.apache.ambari.server.controller.spi.Request.class), org.easymock.EasyMock.anyObject(org.apache.ambari.server.controller.spi.Predicate.class), org.easymock.EasyMock.anyObject(org.apache.ambari.server.controller.spi.PageRequest.class), org.easymock.EasyMock.anyObject(org.apache.ambari.server.controller.spi.SortRequest.class))).andReturn(iterable).anyTimes();
        org.easymock.EasyMock.expect(iterable.iterator()).andReturn(iterator).anyTimes();
        org.easymock.EasyMock.expect(iterator.hasNext()).andReturn(false).anyTimes();
        org.easymock.EasyMock.expect(queryResponse.getResources()).andReturn(java.util.Collections.emptySet()).anyTimes();
        org.easymock.EasyMock.expect(schema.getKeyPropertyId(org.apache.ambari.server.controller.spi.Resource.Type.Cluster)).andReturn("Clusters/cluster_name").anyTimes();
        org.apache.ambari.server.api.util.TreeNode<java.util.Set<java.lang.String>> treeNode = new org.apache.ambari.server.api.util.TreeNodeImpl<>(null, java.util.Collections.<java.lang.String>emptySet(), null);
        org.easymock.EasyMock.expect(renderer.finalizeProperties(org.easymock.EasyMock.anyObject(), org.easymock.EasyMock.anyBoolean())).andReturn(treeNode).anyTimes();
        org.easymock.Capture<org.apache.ambari.server.api.services.Result> resultCapture = org.easymock.EasyMock.newCapture();
        org.easymock.EasyMock.expect(renderer.finalizeResult(org.easymock.EasyMock.capture(resultCapture))).andReturn(null);
        org.easymock.EasyMock.replay(clusterController, queryResponse, schema, renderer, iterable, iterator);
        org.apache.ambari.server.api.query.QueryImpl query = new org.apache.ambari.server.api.query.QueryImplTest.TestQuery(new java.util.HashMap<>(), resourceDefinition, clusterController);
        query.setRenderer(renderer);
        query.execute();
        org.apache.ambari.server.api.util.TreeNode<org.apache.ambari.server.controller.spi.Resource> tree = resultCapture.getValue().getResultTree();
        org.junit.Assert.assertEquals(0, tree.getChildren().size());
        org.easymock.EasyMock.verify(clusterController, queryResponse, schema, renderer, iterable, iterator);
    }

    @org.junit.Test
    public void testExecute__Stack_instance_specifiedSubResources() throws java.lang.Exception {
        org.apache.ambari.server.api.resources.ResourceDefinition resourceDefinition = new org.apache.ambari.server.api.resources.StackResourceDefinition();
        java.util.Map<org.apache.ambari.server.controller.spi.Resource.Type, java.lang.String> mapIds = new java.util.HashMap<>();
        mapIds.put(org.apache.ambari.server.controller.spi.Resource.Type.Stack, "HDP");
        org.apache.ambari.server.api.query.QueryImpl instance = new org.apache.ambari.server.api.query.QueryImplTest.TestQuery(mapIds, resourceDefinition);
        instance.addProperty("versions/*", null);
        instance.addProperty("versions/operating_systems/*", null);
        instance.addProperty("versions/operating_systems/repositories/*", null);
        org.apache.ambari.server.api.services.Result result = instance.execute();
        org.apache.ambari.server.api.util.TreeNode<org.apache.ambari.server.controller.spi.Resource> tree = result.getResultTree();
        org.junit.Assert.assertEquals(1, tree.getChildren().size());
        org.apache.ambari.server.api.util.TreeNode<org.apache.ambari.server.controller.spi.Resource> stackNode = tree.getChild("Stack:1");
        org.junit.Assert.assertEquals("Stack:1", stackNode.getName());
        org.junit.Assert.assertEquals(org.apache.ambari.server.controller.spi.Resource.Type.Stack, stackNode.getObject().getType());
        org.junit.Assert.assertEquals(1, stackNode.getChildren().size());
        org.apache.ambari.server.api.util.TreeNode<org.apache.ambari.server.controller.spi.Resource> versionsNode = stackNode.getChild("versions");
        org.junit.Assert.assertEquals(3, versionsNode.getChildren().size());
        org.apache.ambari.server.api.util.TreeNode<org.apache.ambari.server.controller.spi.Resource> versionNode = versionsNode.getChild("StackVersion:1");
        org.junit.Assert.assertEquals("StackVersion:1", versionNode.getName());
        org.junit.Assert.assertEquals(org.apache.ambari.server.controller.spi.Resource.Type.StackVersion, versionNode.getObject().getType());
        org.junit.Assert.assertEquals(7, versionNode.getChildren().size());
        org.apache.ambari.server.api.util.TreeNode<org.apache.ambari.server.controller.spi.Resource> opSystemsNode = versionNode.getChild("operating_systems");
        org.junit.Assert.assertEquals(3, opSystemsNode.getChildren().size());
        org.apache.ambari.server.api.util.TreeNode<org.apache.ambari.server.controller.spi.Resource> opSystemNode = opSystemsNode.getChild("OperatingSystem:1");
        org.junit.Assert.assertEquals("OperatingSystem:1", opSystemNode.getName());
        org.junit.Assert.assertEquals(org.apache.ambari.server.controller.spi.Resource.Type.OperatingSystem, opSystemNode.getObject().getType());
        org.junit.Assert.assertEquals(1, opSystemNode.getChildren().size());
        org.apache.ambari.server.api.util.TreeNode<org.apache.ambari.server.controller.spi.Resource> repositoriesNode = opSystemNode.getChild("repositories");
        org.junit.Assert.assertEquals(2, repositoriesNode.getChildren().size());
        org.apache.ambari.server.api.util.TreeNode<org.apache.ambari.server.controller.spi.Resource> repositoryNode = repositoriesNode.getChild("Repository:1");
        org.junit.Assert.assertEquals("Repository:1", repositoryNode.getName());
        org.apache.ambari.server.controller.spi.Resource repositoryResource = repositoryNode.getObject();
        org.junit.Assert.assertEquals(org.apache.ambari.server.controller.spi.Resource.Type.Repository, repositoryResource.getType());
        org.junit.Assert.assertEquals("repo1", repositoryResource.getPropertyValue("Repositories/repo_id"));
        org.junit.Assert.assertEquals("centos5", repositoryResource.getPropertyValue("Repositories/os_type"));
        org.junit.Assert.assertEquals("1.2.1", repositoryResource.getPropertyValue("Repositories/stack_version"));
        org.junit.Assert.assertEquals("HDP", repositoryResource.getPropertyValue("Repositories/stack_name"));
        org.apache.ambari.server.api.util.TreeNode<org.apache.ambari.server.controller.spi.Resource> artifactsNode = versionNode.getChild("artifacts");
        org.junit.Assert.assertEquals(1, artifactsNode.getChildren().size());
        org.apache.ambari.server.api.util.TreeNode<org.apache.ambari.server.controller.spi.Resource> artifactNode = artifactsNode.getChild("StackArtifact:1");
        org.junit.Assert.assertEquals("StackArtifact:1", artifactNode.getName());
        org.junit.Assert.assertEquals(org.apache.ambari.server.controller.spi.Resource.Type.StackArtifact, artifactNode.getObject().getType());
    }

    @org.junit.Test
    public void testExecute_StackVersionPageResourcePredicate() throws org.apache.ambari.server.controller.spi.NoSuchParentResourceException, org.apache.ambari.server.controller.spi.UnsupportedPropertyException, org.apache.ambari.server.controller.spi.NoSuchResourceException, org.apache.ambari.server.controller.spi.SystemException {
        org.apache.ambari.server.api.resources.ResourceDefinition resourceDefinition = new org.apache.ambari.server.api.resources.StackVersionResourceDefinition();
        java.util.Map<org.apache.ambari.server.controller.spi.Resource.Type, java.lang.String> mapIds = new java.util.HashMap<>();
        org.apache.ambari.server.api.query.QueryImpl instance = new org.apache.ambari.server.api.query.QueryImplTest.TestQuery(mapIds, resourceDefinition);
        org.apache.ambari.server.controller.utilities.PredicateBuilder pb = new org.apache.ambari.server.controller.utilities.PredicateBuilder();
        org.apache.ambari.server.controller.spi.Predicate predicate = pb.property("Versions/stack_version").equals("1.2.1").or().property("Versions/stack_version").equals("1.2.2").toPredicate();
        instance.setUserPredicate(predicate);
        instance.setPageRequest(new org.apache.ambari.server.controller.internal.PageRequestImpl(org.apache.ambari.server.controller.spi.PageRequest.StartingPoint.Beginning, 1, 0, null, null));
        org.apache.ambari.server.api.services.Result result = instance.execute();
        org.apache.ambari.server.api.util.TreeNode<org.apache.ambari.server.controller.spi.Resource> tree = result.getResultTree();
        org.junit.Assert.assertEquals(1, tree.getChildren().size());
        org.apache.ambari.server.api.util.TreeNode<org.apache.ambari.server.controller.spi.Resource> stackVersionNode = tree.getChild("StackVersion:1");
        org.junit.Assert.assertEquals("StackVersion:1", stackVersionNode.getName());
        org.junit.Assert.assertEquals(org.apache.ambari.server.controller.spi.Resource.Type.StackVersion, stackVersionNode.getObject().getType());
        org.junit.Assert.assertEquals("1.2.1", stackVersionNode.getObject().getPropertyValue("Versions/stack_version"));
        instance.setPageRequest(new org.apache.ambari.server.controller.internal.PageRequestImpl(org.apache.ambari.server.controller.spi.PageRequest.StartingPoint.OffsetStart, 1, 1, null, null));
        result = instance.execute();
        tree = result.getResultTree();
        org.junit.Assert.assertEquals(1, tree.getChildren().size());
        stackVersionNode = tree.getChild("StackVersion:1");
        org.junit.Assert.assertEquals("StackVersion:1", stackVersionNode.getName());
        org.junit.Assert.assertEquals(org.apache.ambari.server.controller.spi.Resource.Type.StackVersion, stackVersionNode.getObject().getType());
        org.junit.Assert.assertEquals("1.2.2", stackVersionNode.getObject().getPropertyValue("Versions/stack_version"));
    }

    @org.junit.Test
    public void testExecute_StackVersionPageSubResourcePredicate() throws org.apache.ambari.server.controller.spi.NoSuchParentResourceException, org.apache.ambari.server.controller.spi.UnsupportedPropertyException, org.apache.ambari.server.controller.spi.NoSuchResourceException, org.apache.ambari.server.controller.spi.SystemException {
        org.apache.ambari.server.api.resources.ResourceDefinition resourceDefinition = new org.apache.ambari.server.api.resources.StackVersionResourceDefinition();
        java.util.Map<org.apache.ambari.server.controller.spi.Resource.Type, java.lang.String> mapIds = new java.util.HashMap<>();
        org.apache.ambari.server.api.query.QueryImpl instance = new org.apache.ambari.server.api.query.QueryImplTest.TestQuery(mapIds, resourceDefinition);
        instance.addProperty("operating_systems/*", null);
        org.apache.ambari.server.controller.utilities.PredicateBuilder pb = new org.apache.ambari.server.controller.utilities.PredicateBuilder();
        org.apache.ambari.server.controller.spi.Predicate predicate = pb.property("operating_systems/OperatingSystems/os_type").equals("centos5").toPredicate();
        instance.setUserPredicate(predicate);
        instance.setPageRequest(new org.apache.ambari.server.controller.internal.PageRequestImpl(org.apache.ambari.server.controller.spi.PageRequest.StartingPoint.Beginning, 1, 0, null, null));
        org.apache.ambari.server.api.services.Result result = instance.execute();
        org.apache.ambari.server.api.util.TreeNode<org.apache.ambari.server.controller.spi.Resource> tree = result.getResultTree();
        org.junit.Assert.assertEquals(1, tree.getChildren().size());
        org.apache.ambari.server.api.util.TreeNode<org.apache.ambari.server.controller.spi.Resource> stackVersionNode = tree.getChild("StackVersion:1");
        org.junit.Assert.assertEquals("StackVersion:1", stackVersionNode.getName());
        org.junit.Assert.assertEquals(org.apache.ambari.server.controller.spi.Resource.Type.StackVersion, stackVersionNode.getObject().getType());
        org.junit.Assert.assertEquals("1.2.1", stackVersionNode.getObject().getPropertyValue("Versions/stack_version"));
        org.apache.ambari.server.api.query.QueryImpl instance2 = new org.apache.ambari.server.api.query.QueryImplTest.TestQuery(mapIds, resourceDefinition);
        instance2.addProperty("operating_systems/*", null);
        instance2.setUserPredicate(predicate);
        instance2.setPageRequest(new org.apache.ambari.server.controller.internal.PageRequestImpl(org.apache.ambari.server.controller.spi.PageRequest.StartingPoint.OffsetStart, 1, 1, null, null));
        org.apache.ambari.server.api.services.Result result2 = instance2.execute();
        org.apache.ambari.server.api.util.TreeNode<org.apache.ambari.server.controller.spi.Resource> tree2 = result2.getResultTree();
        org.junit.Assert.assertEquals(1, tree2.getChildren().size());
        org.apache.ambari.server.api.util.TreeNode<org.apache.ambari.server.controller.spi.Resource> stackVersionNode2 = tree2.getChild("StackVersion:1");
        org.junit.Assert.assertEquals("StackVersion:1", stackVersionNode2.getName());
        org.junit.Assert.assertEquals(org.apache.ambari.server.controller.spi.Resource.Type.StackVersion, stackVersionNode2.getObject().getType());
        org.junit.Assert.assertEquals("1.2.2", stackVersionNode2.getObject().getPropertyValue("Versions/stack_version"));
        org.apache.ambari.server.api.query.QueryImpl instance3 = new org.apache.ambari.server.api.query.QueryImplTest.TestQuery(mapIds, resourceDefinition);
        instance3.addProperty("operating_systems/*", null);
        instance3.setUserPredicate(predicate);
        instance3.setPageRequest(new org.apache.ambari.server.controller.internal.PageRequestImpl(org.apache.ambari.server.controller.spi.PageRequest.StartingPoint.OffsetStart, 2, 1, null, null));
        org.apache.ambari.server.api.services.Result result3 = instance3.execute();
        org.apache.ambari.server.api.util.TreeNode<org.apache.ambari.server.controller.spi.Resource> tree3 = result3.getResultTree();
        org.junit.Assert.assertEquals(2, tree3.getChildren().size());
        org.apache.ambari.server.api.util.TreeNode<org.apache.ambari.server.controller.spi.Resource> stackVersionNode3 = tree3.getChild("StackVersion:1");
        org.junit.Assert.assertEquals("StackVersion:1", stackVersionNode3.getName());
        org.junit.Assert.assertEquals(org.apache.ambari.server.controller.spi.Resource.Type.StackVersion, stackVersionNode3.getObject().getType());
        org.junit.Assert.assertEquals("1.2.2", stackVersionNode3.getObject().getPropertyValue("Versions/stack_version"));
        stackVersionNode3 = tree3.getChild("StackVersion:2");
        org.junit.Assert.assertEquals("StackVersion:2", stackVersionNode3.getName());
        org.junit.Assert.assertEquals(org.apache.ambari.server.controller.spi.Resource.Type.StackVersion, stackVersionNode3.getObject().getType());
        org.junit.Assert.assertEquals("2.0.1", stackVersionNode3.getObject().getPropertyValue("Versions/stack_version"));
    }

    @org.junit.Test
    public void testExecute__Host_collection_noSpecifiedProps() throws java.lang.Exception {
        org.apache.ambari.server.api.resources.ResourceDefinition resourceDefinition = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.resources.ResourceDefinition.class);
        java.util.Map<org.apache.ambari.server.controller.spi.Resource.Type, java.lang.String> mapIds = new java.util.HashMap<>();
        org.easymock.EasyMock.expect(resourceDefinition.getType()).andReturn(org.apache.ambari.server.controller.spi.Resource.Type.Host).anyTimes();
        java.util.Set<org.apache.ambari.server.api.resources.SubResourceDefinition> setChildren = new java.util.HashSet<>();
        org.easymock.EasyMock.expect(resourceDefinition.getSubResourceDefinitions()).andReturn(setChildren).anyTimes();
        org.easymock.EasyMock.replay(resourceDefinition);
        org.apache.ambari.server.api.query.QueryImpl instance = new org.apache.ambari.server.api.query.QueryImplTest.TestQuery(mapIds, resourceDefinition);
        org.apache.ambari.server.api.services.Result result = instance.execute();
        org.easymock.EasyMock.verify(resourceDefinition);
        org.apache.ambari.server.api.util.TreeNode<org.apache.ambari.server.controller.spi.Resource> tree = result.getResultTree();
        org.junit.Assert.assertEquals(4, tree.getChildren().size());
        org.apache.ambari.server.api.util.TreeNode<org.apache.ambari.server.controller.spi.Resource> hostNode = tree.getChild("Host:1");
        org.junit.Assert.assertEquals("Host:1", hostNode.getName());
        org.junit.Assert.assertEquals(org.apache.ambari.server.controller.spi.Resource.Type.Host, hostNode.getObject().getType());
        hostNode = tree.getChild("Host:2");
        org.junit.Assert.assertEquals("Host:2", hostNode.getName());
        org.junit.Assert.assertEquals(org.apache.ambari.server.controller.spi.Resource.Type.Host, hostNode.getObject().getType());
        hostNode = tree.getChild("Host:3");
        org.junit.Assert.assertEquals("Host:3", hostNode.getName());
        org.junit.Assert.assertEquals(org.apache.ambari.server.controller.spi.Resource.Type.Host, hostNode.getObject().getType());
        hostNode = tree.getChild("Host:4");
        org.junit.Assert.assertEquals("Host:4", hostNode.getName());
        org.junit.Assert.assertEquals(org.apache.ambari.server.controller.spi.Resource.Type.Host, hostNode.getObject().getType());
    }

    @org.junit.Test
    public void testExecute__Host_collection_AlertsSummary() throws java.lang.Exception {
        org.apache.ambari.server.api.resources.ResourceDefinition resourceDefinition = new org.apache.ambari.server.api.resources.HostResourceDefinition();
        java.util.Map<org.apache.ambari.server.controller.spi.Resource.Type, java.lang.String> mapIds = new java.util.HashMap<>();
        final java.util.concurrent.atomic.AtomicInteger pageCallCount = new java.util.concurrent.atomic.AtomicInteger(0);
        org.apache.ambari.server.controller.internal.ClusterControllerImpl clusterControllerImpl = new org.apache.ambari.server.controller.internal.ClusterControllerImpl(new org.apache.ambari.server.controller.internal.ClusterControllerImplTest.TestProviderModule()) {
            @java.lang.Override
            public org.apache.ambari.server.controller.spi.PageResponse getPage(org.apache.ambari.server.controller.spi.Resource.Type type, org.apache.ambari.server.controller.spi.QueryResponse queryResponse, org.apache.ambari.server.controller.spi.Request request, org.apache.ambari.server.controller.spi.Predicate predicate, org.apache.ambari.server.controller.spi.PageRequest pageRequest, org.apache.ambari.server.controller.spi.SortRequest sortRequest) throws org.apache.ambari.server.controller.spi.UnsupportedPropertyException, org.apache.ambari.server.controller.spi.SystemException, org.apache.ambari.server.controller.spi.NoSuchResourceException, org.apache.ambari.server.controller.spi.NoSuchParentResourceException {
                pageCallCount.incrementAndGet();
                return super.getPage(type, queryResponse, request, predicate, pageRequest, sortRequest);
            }
        };
        org.apache.ambari.server.api.query.QueryImpl instance = new org.apache.ambari.server.api.query.QueryImplTest.TestQuery(mapIds, resourceDefinition, clusterControllerImpl);
        pageCallCount.set(0);
        org.apache.ambari.server.api.services.Result result = instance.execute();
        org.apache.ambari.server.api.util.TreeNode<org.apache.ambari.server.controller.spi.Resource> tree = result.getResultTree();
        org.junit.Assert.assertEquals(4, tree.getChildren().size());
        org.junit.Assert.assertEquals(1, pageCallCount.get());
        pageCallCount.set(0);
        org.apache.ambari.server.controller.utilities.PredicateBuilder pb = new org.apache.ambari.server.controller.utilities.PredicateBuilder();
        org.apache.ambari.server.controller.spi.Predicate predicate = pb.property("alerts_summary/CRITICAL").greaterThan(0).toPredicate();
        instance.setUserPredicate(predicate);
        result = instance.execute();
        tree = result.getResultTree();
        org.junit.Assert.assertEquals(2, tree.getChildren().size());
        org.junit.Assert.assertEquals(2, pageCallCount.get());
        org.apache.ambari.server.api.util.TreeNode<org.apache.ambari.server.controller.spi.Resource> hostNode = tree.getChild("Host:1");
        org.junit.Assert.assertEquals("Host:1", hostNode.getName());
        org.junit.Assert.assertEquals("host:0", hostNode.getObject().getPropertyValue(org.apache.ambari.server.controller.utilities.PropertyHelper.getPropertyId("Hosts", "host_name")));
        org.junit.Assert.assertEquals(org.apache.ambari.server.controller.spi.Resource.Type.Host, hostNode.getObject().getType());
        org.junit.Assert.assertEquals("1", hostNode.getObject().getPropertyValue(org.apache.ambari.server.controller.utilities.PropertyHelper.getPropertyId("alerts_summary", "CRITICAL")));
        hostNode = tree.getChild("Host:2");
        org.junit.Assert.assertEquals("Host:2", hostNode.getName());
        org.junit.Assert.assertEquals("host:2", hostNode.getObject().getPropertyValue(org.apache.ambari.server.controller.utilities.PropertyHelper.getPropertyId("Hosts", "host_name")));
        org.junit.Assert.assertEquals(org.apache.ambari.server.controller.spi.Resource.Type.Host, hostNode.getObject().getType());
        org.junit.Assert.assertEquals("1", hostNode.getObject().getPropertyValue(org.apache.ambari.server.controller.utilities.PropertyHelper.getPropertyId("alerts_summary", "CRITICAL")));
        pageCallCount.set(0);
        pb = new org.apache.ambari.server.controller.utilities.PredicateBuilder();
        predicate = pb.property("alerts_summary/WARNING").greaterThan(0).toPredicate();
        instance.setUserPredicate(predicate);
        instance.setPageRequest(new org.apache.ambari.server.controller.internal.PageRequestImpl(org.apache.ambari.server.controller.spi.PageRequest.StartingPoint.Beginning, 1, 0, null, null));
        result = instance.execute();
        tree = result.getResultTree();
        org.junit.Assert.assertEquals(1, tree.getChildren().size());
        org.junit.Assert.assertEquals(2, pageCallCount.get());
        hostNode = tree.getChild("Host:1");
        org.junit.Assert.assertEquals("Host:1", hostNode.getName());
        org.junit.Assert.assertEquals("host:1", hostNode.getObject().getPropertyValue(org.apache.ambari.server.controller.utilities.PropertyHelper.getPropertyId("Hosts", "host_name")));
        org.junit.Assert.assertEquals(org.apache.ambari.server.controller.spi.Resource.Type.Host, hostNode.getObject().getType());
        org.junit.Assert.assertEquals("1", hostNode.getObject().getPropertyValue(org.apache.ambari.server.controller.utilities.PropertyHelper.getPropertyId("alerts_summary", "WARNING")));
        pageCallCount.set(0);
        pb = new org.apache.ambari.server.controller.utilities.PredicateBuilder();
        predicate = pb.property("alerts_summary/WARNING").greaterThan(0).or().property("host_components/HostRoles/component_name").equals("DATANODE").toPredicate();
        instance.setUserPredicate(predicate);
        instance.setPageRequest(null);
        result = instance.execute();
        tree = result.getResultTree();
        org.junit.Assert.assertEquals(0, tree.getChildren().size());
        org.junit.Assert.assertEquals(6, pageCallCount.get());
    }

    @org.junit.Test
    public void testExecute__collection_nullInternalPredicate_nullUserPredicate() throws java.lang.Exception {
        org.apache.ambari.server.api.resources.ResourceDefinition resourceDefinition = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.resources.ResourceDefinition.class);
        java.util.Map<org.apache.ambari.server.controller.spi.Resource.Type, java.lang.String> mapIds = new java.util.HashMap<>();
        org.easymock.EasyMock.expect(resourceDefinition.getType()).andReturn(org.apache.ambari.server.controller.spi.Resource.Type.Cluster).anyTimes();
        java.util.Set<org.apache.ambari.server.api.resources.SubResourceDefinition> setChildren = new java.util.HashSet<>();
        setChildren.add(new org.apache.ambari.server.api.resources.SubResourceDefinition(org.apache.ambari.server.controller.spi.Resource.Type.Host));
        org.easymock.EasyMock.expect(resourceDefinition.getSubResourceDefinitions()).andReturn(setChildren).anyTimes();
        org.easymock.EasyMock.replay(resourceDefinition);
        org.apache.ambari.server.api.query.QueryImpl instance = new org.apache.ambari.server.api.query.QueryImplTest.TestQuery(mapIds, resourceDefinition);
        org.apache.ambari.server.api.services.Result result = instance.execute();
        org.easymock.EasyMock.verify(resourceDefinition);
        org.apache.ambari.server.api.util.TreeNode<org.apache.ambari.server.controller.spi.Resource> tree = result.getResultTree();
        org.junit.Assert.assertEquals(1, tree.getChildren().size());
        org.apache.ambari.server.api.util.TreeNode<org.apache.ambari.server.controller.spi.Resource> clusterNode = tree.getChild("Cluster:1");
        org.junit.Assert.assertEquals("Cluster:1", clusterNode.getName());
        org.junit.Assert.assertEquals(org.apache.ambari.server.controller.spi.Resource.Type.Cluster, clusterNode.getObject().getType());
        org.junit.Assert.assertEquals(0, clusterNode.getChildren().size());
    }

    @org.junit.Test
    public void testExecute__collection_nullInternalPredicate_nonNullUserPredicate() throws java.lang.Exception {
        org.apache.ambari.server.api.resources.ResourceDefinition resourceDefinition = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.resources.ResourceDefinition.class);
        java.util.Map<org.apache.ambari.server.controller.spi.Resource.Type, java.lang.String> mapIds = new java.util.HashMap<>();
        mapIds.put(org.apache.ambari.server.controller.spi.Resource.Type.Cluster, "cluster");
        org.easymock.EasyMock.expect(resourceDefinition.getType()).andReturn(org.apache.ambari.server.controller.spi.Resource.Type.Host).anyTimes();
        java.util.Set<org.apache.ambari.server.api.resources.SubResourceDefinition> setChildren = new java.util.HashSet<>();
        org.easymock.EasyMock.expect(resourceDefinition.getSubResourceDefinitions()).andReturn(setChildren).anyTimes();
        org.easymock.EasyMock.replay(resourceDefinition);
        org.apache.ambari.server.api.query.QueryImpl instance = new org.apache.ambari.server.api.query.QueryImplTest.TestQuery(mapIds, resourceDefinition);
        org.apache.ambari.server.controller.utilities.PredicateBuilder pb = new org.apache.ambari.server.controller.utilities.PredicateBuilder();
        org.apache.ambari.server.controller.spi.Predicate predicate = pb.property("Hosts/host_name").equals("host:2").toPredicate();
        instance.setUserPredicate(predicate);
        org.apache.ambari.server.api.services.Result result = instance.execute();
        org.easymock.EasyMock.verify(resourceDefinition);
        org.apache.ambari.server.api.util.TreeNode<org.apache.ambari.server.controller.spi.Resource> tree = result.getResultTree();
        org.junit.Assert.assertEquals(1, tree.getChildren().size());
        org.apache.ambari.server.api.util.TreeNode<org.apache.ambari.server.controller.spi.Resource> hostNode = tree.getChild("Host:1");
        org.junit.Assert.assertEquals("Host:1", hostNode.getName());
        org.junit.Assert.assertEquals(org.apache.ambari.server.controller.spi.Resource.Type.Host, hostNode.getObject().getType());
        org.junit.Assert.assertEquals("host:2", hostNode.getObject().getPropertyValue("Hosts/host_name"));
    }

    @org.junit.Test
    public void testExecute__collection_nonNullInternalPredicate_nonNullUserPredicate() throws java.lang.Exception {
        org.apache.ambari.server.api.resources.ResourceDefinition resourceDefinition = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.resources.ResourceDefinition.class);
        java.util.Map<org.apache.ambari.server.controller.spi.Resource.Type, java.lang.String> mapIds = new java.util.HashMap<>();
        org.easymock.EasyMock.expect(resourceDefinition.getType()).andReturn(org.apache.ambari.server.controller.spi.Resource.Type.Host).anyTimes();
        java.util.Set<org.apache.ambari.server.api.resources.SubResourceDefinition> setChildren = new java.util.HashSet<>();
        org.easymock.EasyMock.expect(resourceDefinition.getSubResourceDefinitions()).andReturn(setChildren).anyTimes();
        org.easymock.EasyMock.replay(resourceDefinition);
        org.apache.ambari.server.api.query.QueryImpl instance = new org.apache.ambari.server.api.query.QueryImplTest.TestQuery(mapIds, resourceDefinition);
        org.apache.ambari.server.controller.utilities.PredicateBuilder pb = new org.apache.ambari.server.controller.utilities.PredicateBuilder();
        org.apache.ambari.server.controller.spi.Predicate predicate = pb.property("Hosts/host_name").equals("host:2").toPredicate();
        instance.setUserPredicate(predicate);
        org.apache.ambari.server.api.services.Result result = instance.execute();
        org.easymock.EasyMock.verify(resourceDefinition);
        org.apache.ambari.server.api.util.TreeNode<org.apache.ambari.server.controller.spi.Resource> tree = result.getResultTree();
        org.junit.Assert.assertEquals(1, tree.getChildren().size());
        org.apache.ambari.server.api.util.TreeNode<org.apache.ambari.server.controller.spi.Resource> hostNode = tree.getChild("Host:1");
        org.junit.Assert.assertEquals("Host:1", hostNode.getName());
        org.junit.Assert.assertEquals(org.apache.ambari.server.controller.spi.Resource.Type.Host, hostNode.getObject().getType());
        org.junit.Assert.assertEquals("host:2", hostNode.getObject().getPropertyValue("Hosts/host_name"));
    }

    @org.junit.Test
    public void testAddProperty__localProperty() throws java.lang.Exception {
        org.apache.ambari.server.api.resources.ResourceDefinition resourceDefinition = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.resources.ResourceDefinition.class);
        java.util.Map<org.apache.ambari.server.controller.spi.Resource.Type, java.lang.String> mapIds = new java.util.HashMap<>();
        org.easymock.EasyMock.expect(resourceDefinition.getType()).andReturn(org.apache.ambari.server.controller.spi.Resource.Type.Host).anyTimes();
        java.util.Set<org.apache.ambari.server.api.resources.SubResourceDefinition> setChildren = new java.util.HashSet<>();
        org.easymock.EasyMock.expect(resourceDefinition.getSubResourceDefinitions()).andReturn(setChildren).anyTimes();
        org.easymock.EasyMock.replay(resourceDefinition);
        org.apache.ambari.server.api.query.QueryImpl instance = new org.apache.ambari.server.api.query.QueryImplTest.TestQuery(mapIds, resourceDefinition);
        instance.addLocalProperty("c1/p1");
        org.apache.ambari.server.api.services.Result result = instance.execute();
        org.easymock.EasyMock.verify(resourceDefinition);
        org.apache.ambari.server.api.util.TreeNode<org.apache.ambari.server.controller.spi.Resource> tree = result.getResultTree();
        org.junit.Assert.assertEquals(4, tree.getChildren().size());
        org.apache.ambari.server.api.util.TreeNode<org.apache.ambari.server.controller.spi.Resource> hostNode = tree.getChild("Host:1");
        org.junit.Assert.assertEquals("Host:1", hostNode.getName());
        org.junit.Assert.assertEquals(org.apache.ambari.server.controller.spi.Resource.Type.Host, hostNode.getObject().getType());
        org.junit.Assert.assertNotNull(hostNode.getObject().getPropertyValue("c1/p1"));
        hostNode = tree.getChild("Host:2");
        org.junit.Assert.assertEquals("Host:2", hostNode.getName());
        org.junit.Assert.assertEquals(org.apache.ambari.server.controller.spi.Resource.Type.Host, hostNode.getObject().getType());
        org.junit.Assert.assertNotNull(hostNode.getObject().getPropertyValue("c1/p1"));
        hostNode = tree.getChild("Host:3");
        org.junit.Assert.assertEquals("Host:3", hostNode.getName());
        org.junit.Assert.assertEquals(org.apache.ambari.server.controller.spi.Resource.Type.Host, hostNode.getObject().getType());
        org.junit.Assert.assertNotNull(hostNode.getObject().getPropertyValue("c1/p1"));
        hostNode = tree.getChild("Host:4");
        org.junit.Assert.assertEquals("Host:4", hostNode.getName());
        org.junit.Assert.assertEquals(org.apache.ambari.server.controller.spi.Resource.Type.Host, hostNode.getObject().getType());
        org.junit.Assert.assertNotNull(hostNode.getObject().getPropertyValue("c1/p1"));
    }

    @org.junit.Test
    public void testAddProperty__allCategoryProperties() throws java.lang.Exception {
        org.apache.ambari.server.api.resources.ResourceDefinition resourceDefinition = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.resources.ResourceDefinition.class);
        java.util.Map<org.apache.ambari.server.controller.spi.Resource.Type, java.lang.String> mapIds = new java.util.HashMap<>();
        org.easymock.EasyMock.expect(resourceDefinition.getType()).andReturn(org.apache.ambari.server.controller.spi.Resource.Type.Host).anyTimes();
        java.util.Set<org.apache.ambari.server.api.resources.SubResourceDefinition> setChildren = new java.util.HashSet<>();
        org.easymock.EasyMock.expect(resourceDefinition.getSubResourceDefinitions()).andReturn(setChildren).anyTimes();
        org.easymock.EasyMock.replay(resourceDefinition);
        org.apache.ambari.server.api.query.QueryImpl instance = new org.apache.ambari.server.api.query.QueryImplTest.TestQuery(mapIds, resourceDefinition);
        instance.addLocalProperty("c1");
        org.apache.ambari.server.api.services.Result result = instance.execute();
        org.easymock.EasyMock.verify(resourceDefinition);
        org.apache.ambari.server.api.util.TreeNode<org.apache.ambari.server.controller.spi.Resource> tree = result.getResultTree();
        org.junit.Assert.assertEquals(4, tree.getChildren().size());
        org.apache.ambari.server.api.util.TreeNode<org.apache.ambari.server.controller.spi.Resource> hostNode = tree.getChild("Host:1");
        org.junit.Assert.assertEquals("Host:1", hostNode.getName());
        org.junit.Assert.assertEquals(org.apache.ambari.server.controller.spi.Resource.Type.Host, hostNode.getObject().getType());
        org.junit.Assert.assertNotNull(hostNode.getObject().getPropertyValue("c1/p1"));
        org.junit.Assert.assertNotNull(hostNode.getObject().getPropertyValue("c1/p2"));
        org.junit.Assert.assertNotNull(hostNode.getObject().getPropertyValue("c1/p3"));
        hostNode = tree.getChild("Host:2");
        org.junit.Assert.assertEquals("Host:2", hostNode.getName());
        org.junit.Assert.assertEquals(org.apache.ambari.server.controller.spi.Resource.Type.Host, hostNode.getObject().getType());
        org.junit.Assert.assertNotNull(hostNode.getObject().getPropertyValue("c1/p1"));
        org.junit.Assert.assertNotNull(hostNode.getObject().getPropertyValue("c1/p2"));
        org.junit.Assert.assertNotNull(hostNode.getObject().getPropertyValue("c1/p3"));
        hostNode = tree.getChild("Host:3");
        org.junit.Assert.assertEquals("Host:3", hostNode.getName());
        org.junit.Assert.assertEquals(org.apache.ambari.server.controller.spi.Resource.Type.Host, hostNode.getObject().getType());
        org.junit.Assert.assertNotNull(hostNode.getObject().getPropertyValue("c1/p1"));
        org.junit.Assert.assertNotNull(hostNode.getObject().getPropertyValue("c1/p2"));
        org.junit.Assert.assertNotNull(hostNode.getObject().getPropertyValue("c1/p3"));
        hostNode = tree.getChild("Host:4");
        org.junit.Assert.assertEquals("Host:4", hostNode.getName());
        org.junit.Assert.assertEquals(org.apache.ambari.server.controller.spi.Resource.Type.Host, hostNode.getObject().getType());
        org.junit.Assert.assertNotNull(hostNode.getObject().getPropertyValue("c1/p1"));
        org.junit.Assert.assertNotNull(hostNode.getObject().getPropertyValue("c1/p2"));
        org.junit.Assert.assertNotNull(hostNode.getObject().getPropertyValue("c1/p3"));
    }

    @org.junit.Test
    public void testExecute_RendererDoesNotRequirePropertyProviderInput() throws java.lang.Exception {
        org.easymock.EasyMockSupport mockSupport = new org.easymock.EasyMockSupport();
        org.apache.ambari.server.api.resources.ResourceDefinition mockResourceDefinition = mockSupport.createMock(org.apache.ambari.server.api.resources.ResourceDefinition.class);
        org.apache.ambari.server.api.resources.SubResourceDefinition mockSubResourceDefinition = mockSupport.createMock(org.apache.ambari.server.api.resources.SubResourceDefinition.class);
        org.apache.ambari.server.controller.spi.ClusterController mockClusterController = mockSupport.createMock(org.apache.ambari.server.controller.spi.ClusterController.class);
        org.apache.ambari.server.api.query.render.Renderer mockRenderer = mockSupport.createMock(org.apache.ambari.server.api.query.render.Renderer.class);
        org.apache.ambari.server.controller.spi.QueryResponse mockQueryResponse = mockSupport.createMock(org.apache.ambari.server.controller.spi.QueryResponse.class);
        org.apache.ambari.server.controller.spi.QueryResponse mockSubQueryResponse = mockSupport.createMock(org.apache.ambari.server.controller.spi.QueryResponse.class);
        org.apache.ambari.server.controller.spi.Resource mockResource = mockSupport.createMock(org.apache.ambari.server.controller.spi.Resource.class);
        org.apache.ambari.server.controller.spi.Schema mockSchema = mockSupport.createMock(org.apache.ambari.server.controller.spi.Schema.class);
        org.easymock.EasyMock.expect(mockResourceDefinition.getType()).andReturn(org.apache.ambari.server.controller.spi.Resource.Type.Host).atLeastOnce();
        org.easymock.EasyMock.expect(mockResourceDefinition.getSubResourceDefinitions()).andReturn(java.util.Collections.singleton(mockSubResourceDefinition)).atLeastOnce();
        org.easymock.EasyMock.expect(mockSubResourceDefinition.getType()).andReturn(org.apache.ambari.server.controller.spi.Resource.Type.Configuration).atLeastOnce();
        org.easymock.EasyMock.expect(mockSubResourceDefinition.isCollection()).andReturn(false).atLeastOnce();
        org.easymock.EasyMock.expect(mockSchema.getKeyPropertyId(org.easymock.EasyMock.isA(org.apache.ambari.server.controller.spi.Resource.Type.class))).andReturn("test-value").anyTimes();
        org.easymock.EasyMock.expect(mockSchema.getKeyTypes()).andReturn(java.util.Collections.emptySet()).anyTimes();
        mockRenderer.init(org.easymock.EasyMock.isA(org.apache.ambari.server.controller.spi.SchemaFactory.class));
        org.easymock.EasyMock.expect(mockRenderer.requiresPropertyProviderInput()).andReturn(false).times(2);
        org.easymock.EasyMock.expect(mockRenderer.finalizeProperties(org.easymock.EasyMock.isA(org.apache.ambari.server.api.util.TreeNode.class), org.easymock.EasyMock.eq(true))).andReturn(new org.apache.ambari.server.api.util.TreeNodeImpl<>(null, java.util.Collections.<java.lang.String>emptySet(), "test-node"));
        org.easymock.EasyMock.expect(mockRenderer.finalizeResult(org.easymock.EasyMock.isA(org.apache.ambari.server.api.services.Result.class))).andReturn(null);
        org.easymock.EasyMock.expect(mockClusterController.getSchema(org.apache.ambari.server.controller.spi.Resource.Type.Host)).andReturn(mockSchema).anyTimes();
        org.easymock.EasyMock.expect(mockClusterController.getSchema(org.apache.ambari.server.controller.spi.Resource.Type.Configuration)).andReturn(mockSchema).anyTimes();
        org.easymock.EasyMock.expect(mockClusterController.getResources(org.easymock.EasyMock.eq(org.apache.ambari.server.controller.spi.Resource.Type.Host), org.easymock.EasyMock.isA(org.apache.ambari.server.controller.spi.Request.class), ((org.apache.ambari.server.controller.spi.Predicate) (org.easymock.EasyMock.eq(null))))).andReturn(mockQueryResponse).atLeastOnce();
        org.easymock.EasyMock.expect(mockClusterController.getResources(org.easymock.EasyMock.eq(org.apache.ambari.server.controller.spi.Resource.Type.Configuration), org.easymock.EasyMock.isA(org.apache.ambari.server.controller.spi.Request.class), ((org.apache.ambari.server.controller.spi.Predicate) (org.easymock.EasyMock.eq(null))))).andReturn(mockSubQueryResponse).atLeastOnce();
        org.easymock.EasyMock.expect(mockClusterController.getIterable(org.easymock.EasyMock.eq(org.apache.ambari.server.controller.spi.Resource.Type.Host), org.easymock.EasyMock.isA(org.apache.ambari.server.controller.spi.QueryResponse.class), org.easymock.EasyMock.isA(org.apache.ambari.server.controller.spi.Request.class), ((org.apache.ambari.server.controller.spi.Predicate) (org.easymock.EasyMock.eq(null))), ((org.apache.ambari.server.controller.spi.PageRequest) (org.easymock.EasyMock.eq(null))), ((org.apache.ambari.server.controller.spi.SortRequest) (org.easymock.EasyMock.eq(null))))).andReturn(java.util.Collections.singleton(mockResource)).atLeastOnce();
        org.easymock.EasyMock.expect(mockClusterController.getIterable(org.easymock.EasyMock.eq(org.apache.ambari.server.controller.spi.Resource.Type.Configuration), org.easymock.EasyMock.isA(org.apache.ambari.server.controller.spi.QueryResponse.class), org.easymock.EasyMock.isA(org.apache.ambari.server.controller.spi.Request.class), ((org.apache.ambari.server.controller.spi.Predicate) (org.easymock.EasyMock.eq(null))), ((org.apache.ambari.server.controller.spi.PageRequest) (org.easymock.EasyMock.eq(null))), ((org.apache.ambari.server.controller.spi.SortRequest) (org.easymock.EasyMock.eq(null))))).andReturn(java.util.Collections.singleton(mockResource)).atLeastOnce();
        org.easymock.EasyMock.expect(mockQueryResponse.getResources()).andReturn(java.util.Collections.singleton(mockResource)).atLeastOnce();
        org.easymock.EasyMock.expect(mockSubQueryResponse.getResources()).andReturn(java.util.Collections.singleton(mockResource)).atLeastOnce();
        org.easymock.EasyMock.expect(mockResource.getType()).andReturn(org.apache.ambari.server.controller.spi.Resource.Type.Host).atLeastOnce();
        java.util.Map<org.apache.ambari.server.controller.spi.Resource.Type, java.lang.String> mapIds = new java.util.HashMap<>();
        mockSupport.replayAll();
        org.apache.ambari.server.api.query.QueryImpl instance = new org.apache.ambari.server.api.query.QueryImpl(mapIds, mockResourceDefinition, mockClusterController);
        instance.setRenderer(mockRenderer);
        instance.ensureSubResources();
        instance.addProperty("*", null);
        instance.execute();
        mockSupport.verifyAll();
    }

    @org.junit.Test
    public void testExecute_RendererRequiresPropertyProviderInput() throws java.lang.Exception {
        org.easymock.EasyMockSupport mockSupport = new org.easymock.EasyMockSupport();
        org.apache.ambari.server.api.resources.ResourceDefinition mockResourceDefinition = mockSupport.createMock(org.apache.ambari.server.api.resources.ResourceDefinition.class);
        org.apache.ambari.server.api.resources.SubResourceDefinition mockSubResourceDefinition = mockSupport.createMock(org.apache.ambari.server.api.resources.SubResourceDefinition.class);
        org.apache.ambari.server.controller.spi.ClusterController mockClusterController = mockSupport.createMock(org.apache.ambari.server.controller.spi.ClusterController.class);
        org.apache.ambari.server.api.query.render.Renderer mockRenderer = mockSupport.createMock(org.apache.ambari.server.api.query.render.Renderer.class);
        org.apache.ambari.server.controller.spi.QueryResponse mockQueryResponse = mockSupport.createMock(org.apache.ambari.server.controller.spi.QueryResponse.class);
        org.apache.ambari.server.controller.spi.QueryResponse mockSubQueryResponse = mockSupport.createMock(org.apache.ambari.server.controller.spi.QueryResponse.class);
        org.apache.ambari.server.controller.spi.Resource mockResource = mockSupport.createMock(org.apache.ambari.server.controller.spi.Resource.class);
        org.apache.ambari.server.controller.spi.Schema mockSchema = mockSupport.createMock(org.apache.ambari.server.controller.spi.Schema.class);
        org.easymock.EasyMock.expect(mockResourceDefinition.getType()).andReturn(org.apache.ambari.server.controller.spi.Resource.Type.Host).atLeastOnce();
        org.easymock.EasyMock.expect(mockResourceDefinition.getSubResourceDefinitions()).andReturn(java.util.Collections.singleton(mockSubResourceDefinition)).atLeastOnce();
        org.easymock.EasyMock.expect(mockSubResourceDefinition.getType()).andReturn(org.apache.ambari.server.controller.spi.Resource.Type.Configuration).atLeastOnce();
        org.easymock.EasyMock.expect(mockSubResourceDefinition.isCollection()).andReturn(false).atLeastOnce();
        org.easymock.EasyMock.expect(mockSchema.getKeyPropertyId(org.easymock.EasyMock.isA(org.apache.ambari.server.controller.spi.Resource.Type.class))).andReturn("test-value").anyTimes();
        org.easymock.EasyMock.expect(mockSchema.getKeyTypes()).andReturn(java.util.Collections.emptySet()).anyTimes();
        mockRenderer.init(org.easymock.EasyMock.isA(org.apache.ambari.server.controller.spi.SchemaFactory.class));
        org.easymock.EasyMock.expect(mockRenderer.requiresPropertyProviderInput()).andReturn(true).times(2);
        org.easymock.EasyMock.expect(mockRenderer.finalizeProperties(org.easymock.EasyMock.isA(org.apache.ambari.server.api.util.TreeNode.class), org.easymock.EasyMock.eq(true))).andReturn(new org.apache.ambari.server.api.util.TreeNodeImpl<>(null, java.util.Collections.<java.lang.String>emptySet(), "test-node"));
        org.easymock.EasyMock.expect(mockRenderer.finalizeResult(org.easymock.EasyMock.isA(org.apache.ambari.server.api.services.Result.class))).andReturn(null);
        org.easymock.EasyMock.expect(mockClusterController.getSchema(org.apache.ambari.server.controller.spi.Resource.Type.Host)).andReturn(mockSchema).anyTimes();
        org.easymock.EasyMock.expect(mockClusterController.getSchema(org.apache.ambari.server.controller.spi.Resource.Type.Configuration)).andReturn(mockSchema).anyTimes();
        org.easymock.EasyMock.expect(mockClusterController.getResources(org.easymock.EasyMock.eq(org.apache.ambari.server.controller.spi.Resource.Type.Host), org.easymock.EasyMock.isA(org.apache.ambari.server.controller.spi.Request.class), ((org.apache.ambari.server.controller.spi.Predicate) (org.easymock.EasyMock.eq(null))))).andReturn(mockQueryResponse).atLeastOnce();
        org.easymock.EasyMock.expect(mockClusterController.getResources(org.easymock.EasyMock.eq(org.apache.ambari.server.controller.spi.Resource.Type.Configuration), org.easymock.EasyMock.isA(org.apache.ambari.server.controller.spi.Request.class), ((org.apache.ambari.server.controller.spi.Predicate) (org.easymock.EasyMock.eq(null))))).andReturn(mockSubQueryResponse).atLeastOnce();
        org.easymock.EasyMock.expect(mockClusterController.getIterable(org.easymock.EasyMock.eq(org.apache.ambari.server.controller.spi.Resource.Type.Host), org.easymock.EasyMock.isA(org.apache.ambari.server.controller.spi.QueryResponse.class), org.easymock.EasyMock.isA(org.apache.ambari.server.controller.spi.Request.class), ((org.apache.ambari.server.controller.spi.Predicate) (org.easymock.EasyMock.eq(null))), ((org.apache.ambari.server.controller.spi.PageRequest) (org.easymock.EasyMock.eq(null))), ((org.apache.ambari.server.controller.spi.SortRequest) (org.easymock.EasyMock.eq(null))))).andReturn(java.util.Collections.singleton(mockResource)).atLeastOnce();
        org.easymock.EasyMock.expect(mockClusterController.getIterable(org.easymock.EasyMock.eq(org.apache.ambari.server.controller.spi.Resource.Type.Configuration), org.easymock.EasyMock.isA(org.apache.ambari.server.controller.spi.QueryResponse.class), org.easymock.EasyMock.isA(org.apache.ambari.server.controller.spi.Request.class), ((org.apache.ambari.server.controller.spi.Predicate) (org.easymock.EasyMock.eq(null))), ((org.apache.ambari.server.controller.spi.PageRequest) (org.easymock.EasyMock.eq(null))), ((org.apache.ambari.server.controller.spi.SortRequest) (org.easymock.EasyMock.eq(null))))).andReturn(java.util.Collections.singleton(mockResource)).atLeastOnce();
        org.easymock.EasyMock.expect(mockClusterController.populateResources(org.easymock.EasyMock.eq(org.apache.ambari.server.controller.spi.Resource.Type.Host), org.easymock.EasyMock.eq(java.util.Collections.singleton(mockResource)), org.easymock.EasyMock.isA(org.apache.ambari.server.controller.spi.Request.class), ((org.apache.ambari.server.controller.spi.Predicate) (org.easymock.EasyMock.eq(null))))).andReturn(java.util.Collections.emptySet()).times(1);
        org.easymock.EasyMock.expect(mockClusterController.populateResources(org.easymock.EasyMock.eq(org.apache.ambari.server.controller.spi.Resource.Type.Configuration), org.easymock.EasyMock.eq(java.util.Collections.singleton(mockResource)), org.easymock.EasyMock.isA(org.apache.ambari.server.controller.spi.Request.class), ((org.apache.ambari.server.controller.spi.Predicate) (org.easymock.EasyMock.eq(null))))).andReturn(java.util.Collections.emptySet()).times(1);
        org.easymock.EasyMock.expect(mockQueryResponse.getResources()).andReturn(java.util.Collections.singleton(mockResource)).atLeastOnce();
        org.easymock.EasyMock.expect(mockSubQueryResponse.getResources()).andReturn(java.util.Collections.singleton(mockResource)).atLeastOnce();
        org.easymock.EasyMock.expect(mockResource.getType()).andReturn(org.apache.ambari.server.controller.spi.Resource.Type.Host).atLeastOnce();
        java.util.Map<org.apache.ambari.server.controller.spi.Resource.Type, java.lang.String> mapIds = new java.util.HashMap<>();
        mockSupport.replayAll();
        org.apache.ambari.server.api.query.QueryImpl instance = new org.apache.ambari.server.api.query.QueryImpl(mapIds, mockResourceDefinition, mockClusterController);
        instance.setRenderer(mockRenderer);
        instance.ensureSubResources();
        instance.addProperty("*", null);
        instance.execute();
        mockSupport.verifyAll();
    }

    public static class TestQuery extends org.apache.ambari.server.api.query.QueryImpl {
        public TestQuery(java.util.Map<org.apache.ambari.server.controller.spi.Resource.Type, java.lang.String> mapIds, org.apache.ambari.server.api.resources.ResourceDefinition resourceDefinition) {
            super(mapIds, resourceDefinition, new org.apache.ambari.server.controller.internal.ClusterControllerImpl(new org.apache.ambari.server.controller.internal.ClusterControllerImplTest.TestProviderModule()));
            setRenderer(new org.apache.ambari.server.api.query.render.DefaultRenderer());
        }

        public TestQuery(java.util.Map<org.apache.ambari.server.controller.spi.Resource.Type, java.lang.String> mapIds, org.apache.ambari.server.api.resources.ResourceDefinition resourceDefinition, org.apache.ambari.server.controller.spi.ClusterController clusterController) {
            super(mapIds, resourceDefinition, clusterController);
            setRenderer(new org.apache.ambari.server.api.query.render.DefaultRenderer());
        }
    }
}
