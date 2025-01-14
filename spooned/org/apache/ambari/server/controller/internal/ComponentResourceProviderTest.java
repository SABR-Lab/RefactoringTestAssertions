package org.apache.ambari.server.controller.internal;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import static org.easymock.EasyMock.anyBoolean;
import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
public class ComponentResourceProviderTest {
    private static final long CLUSTER_ID = 100;

    private static final java.lang.String CLUSTER_NAME = "Cluster100";

    private static final java.lang.String SERVICE_NAME = "Service100";

    @org.junit.Before
    public void clearAuthentication() {
        org.apache.ambari.server.security.authorization.AuthorizationHelperInitializer.viewInstanceDAOReturningNull();
        org.springframework.security.core.context.SecurityContextHolder.getContext().setAuthentication(null);
    }

    @org.junit.Test
    public void testCreateResourcesAsAdministrator() throws java.lang.Exception {
        testCreateResources(org.apache.ambari.server.security.TestAuthenticationFactory.createAdministrator());
    }

    @org.junit.Test
    public void testCreateResourcesAsClusterAdministrator() throws java.lang.Exception {
        testCreateResources(org.apache.ambari.server.security.TestAuthenticationFactory.createClusterAdministrator());
    }

    @org.junit.Test(expected = org.apache.ambari.server.security.authorization.AuthorizationException.class)
    public void testCreateResourcesAsServiceAdministrator() throws java.lang.Exception {
        testCreateResources(org.apache.ambari.server.security.TestAuthenticationFactory.createServiceAdministrator());
    }

    private void testCreateResources(org.springframework.security.core.Authentication authentication) throws java.lang.Exception {
        org.apache.ambari.server.controller.spi.Resource.Type type = org.apache.ambari.server.controller.spi.Resource.Type.Component;
        org.apache.ambari.server.controller.MaintenanceStateHelper maintenanceStateHelper = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.MaintenanceStateHelper.class);
        org.apache.ambari.server.controller.AmbariManagementController managementController = org.easymock.EasyMock.createMock(org.apache.ambari.server.controller.AmbariManagementController.class);
        org.apache.ambari.server.state.Clusters clusters = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Clusters.class);
        org.apache.ambari.server.state.Cluster cluster = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Cluster.class);
        org.apache.ambari.server.state.Service service = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Service.class);
        org.apache.ambari.server.controller.RequestStatusResponse response = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.RequestStatusResponse.class);
        org.apache.ambari.server.api.services.AmbariMetaInfo ambariMetaInfo = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.services.AmbariMetaInfo.class);
        org.apache.ambari.server.state.ServiceComponentFactory serviceComponentFactory = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ServiceComponentFactory.class);
        org.apache.ambari.server.state.ServiceComponent serviceComponent = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ServiceComponent.class);
        org.apache.ambari.server.state.StackId stackId = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.StackId.class);
        org.apache.ambari.server.state.ComponentInfo componentInfo = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ComponentInfo.class);
        org.easymock.EasyMock.expect(managementController.getClusters()).andReturn(clusters);
        org.easymock.EasyMock.expect(managementController.getAmbariMetaInfo()).andReturn(ambariMetaInfo);
        org.easymock.EasyMock.expect(managementController.getServiceComponentFactory()).andReturn(serviceComponentFactory);
        org.easymock.EasyMock.expect(clusters.getCluster("Cluster100")).andReturn(cluster).anyTimes();
        org.easymock.EasyMock.expect(cluster.getService("Service100")).andReturn(service).anyTimes();
        org.easymock.EasyMock.expect(cluster.getClusterId()).andReturn(2L).anyTimes();
        org.easymock.EasyMock.expect(service.getDesiredStackId()).andReturn(stackId).anyTimes();
        org.easymock.EasyMock.expect(service.getName()).andReturn("Service100").anyTimes();
        org.easymock.EasyMock.expect(stackId.getStackName()).andReturn("HDP").anyTimes();
        org.easymock.EasyMock.expect(stackId.getStackVersion()).andReturn("99").anyTimes();
        org.easymock.EasyMock.expect(ambariMetaInfo.isValidServiceComponent("HDP", "99", "Service100", "Component100")).andReturn(true).anyTimes();
        org.easymock.EasyMock.expect(componentInfo.getName()).andReturn("Component100").anyTimes();
        org.easymock.EasyMock.expect(componentInfo.isRecoveryEnabled()).andReturn(true).anyTimes();
        org.easymock.EasyMock.expect(ambariMetaInfo.getComponent("HDP", "99", "Service100", "Component100")).andReturn(componentInfo).anyTimes();
        org.easymock.EasyMock.expect(serviceComponentFactory.createNew(service, "Component100")).andReturn(serviceComponent);
        org.easymock.EasyMock.replay(managementController, response, clusters, cluster, service, stackId, ambariMetaInfo, serviceComponentFactory, serviceComponent, componentInfo);
        org.springframework.security.core.context.SecurityContextHolder.getContext().setAuthentication(authentication);
        org.apache.ambari.server.controller.spi.ResourceProvider provider = new org.apache.ambari.server.controller.internal.ComponentResourceProvider(managementController, maintenanceStateHelper);
        java.util.Set<java.util.Map<java.lang.String, java.lang.Object>> propertySet = new java.util.LinkedHashSet<>();
        java.util.Map<java.lang.String, java.lang.Object> properties = new java.util.LinkedHashMap<>();
        properties.put(org.apache.ambari.server.controller.internal.ComponentResourceProvider.CLUSTER_NAME, "Cluster100");
        properties.put(org.apache.ambari.server.controller.internal.ComponentResourceProvider.SERVICE_NAME, "Service100");
        properties.put(org.apache.ambari.server.controller.internal.ComponentResourceProvider.COMPONENT_NAME, "Component100");
        propertySet.add(properties);
        org.apache.ambari.server.controller.spi.Request request = org.apache.ambari.server.controller.utilities.PropertyHelper.getCreateRequest(propertySet, null);
        provider.createResources(request);
        org.easymock.EasyMock.verify(managementController, response, clusters, cluster, service, stackId, ambariMetaInfo, serviceComponentFactory, serviceComponent);
    }

    @org.junit.Test
    public void testGetResourcesAsAdministrator() throws java.lang.Exception {
        testGetResources(org.apache.ambari.server.security.TestAuthenticationFactory.createAdministrator());
    }

    @org.junit.Test
    public void testGetResourcesAsClusterAdministrator() throws java.lang.Exception {
        testGetResources(org.apache.ambari.server.security.TestAuthenticationFactory.createClusterAdministrator());
    }

    @org.junit.Test
    public void testGetResourcesAsServiceAdministrator() throws java.lang.Exception {
        testGetResources(org.apache.ambari.server.security.TestAuthenticationFactory.createServiceAdministrator());
    }

    private void testGetResources(org.springframework.security.core.Authentication authentication) throws java.lang.Exception {
        org.apache.ambari.server.controller.spi.Resource.Type type = org.apache.ambari.server.controller.spi.Resource.Type.Component;
        org.apache.ambari.server.controller.MaintenanceStateHelper maintenanceStateHelper = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.MaintenanceStateHelper.class);
        org.apache.ambari.server.controller.AmbariManagementController managementController = org.easymock.EasyMock.createMock(org.apache.ambari.server.controller.AmbariManagementController.class);
        org.apache.ambari.server.state.Clusters clusters = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Clusters.class);
        org.apache.ambari.server.state.Cluster cluster = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Cluster.class);
        org.apache.ambari.server.api.services.AmbariMetaInfo ambariMetaInfo = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.services.AmbariMetaInfo.class);
        org.apache.ambari.server.state.Service service = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Service.class);
        org.apache.ambari.server.state.ServiceComponent serviceComponent1 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ServiceComponent.class);
        org.apache.ambari.server.state.ServiceComponent serviceComponent2 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ServiceComponent.class);
        org.apache.ambari.server.state.ServiceComponent serviceComponent3 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ServiceComponent.class);
        org.apache.ambari.server.state.StackId stackId = new org.apache.ambari.server.state.StackId("FOO-1.0");
        final org.apache.ambari.server.state.ComponentInfo componentInfo1 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ComponentInfo.class);
        final org.apache.ambari.server.state.ComponentInfo componentInfo2 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ComponentInfo.class);
        java.util.Map<java.lang.String, java.lang.Integer> serviceComponentStateCountMap = new java.util.HashMap<>();
        serviceComponentStateCountMap.put("startedCount", 1);
        serviceComponentStateCountMap.put("installedCount", 0);
        serviceComponentStateCountMap.put("installedAndMaintenanceOffCount", 0);
        serviceComponentStateCountMap.put("installFailedCount", 0);
        serviceComponentStateCountMap.put("initCount", 0);
        serviceComponentStateCountMap.put("unknownCount", 1);
        serviceComponentStateCountMap.put("totalCount", 2);
        java.util.Map<java.lang.String, org.apache.ambari.server.state.ServiceComponent> serviceComponentMap = new java.util.HashMap<>();
        serviceComponentMap.put("Component101", serviceComponent1);
        serviceComponentMap.put("Component102", serviceComponent2);
        serviceComponentMap.put("Component103", serviceComponent3);
        org.easymock.EasyMock.expect(managementController.getClusters()).andReturn(clusters);
        org.easymock.EasyMock.expect(managementController.getAmbariMetaInfo()).andReturn(ambariMetaInfo);
        org.easymock.EasyMock.expect(clusters.getCluster("Cluster100")).andReturn(cluster).anyTimes();
        org.easymock.EasyMock.expect(serviceComponent1.getName()).andReturn("Component100");
        org.easymock.EasyMock.expect(serviceComponent1.getDesiredStackId()).andReturn(stackId).anyTimes();
        org.easymock.EasyMock.expect(serviceComponent2.getName()).andReturn("Component101");
        org.easymock.EasyMock.expect(serviceComponent2.getDesiredStackId()).andReturn(stackId).anyTimes();
        org.easymock.EasyMock.expect(serviceComponent3.getName()).andReturn("Component102");
        org.easymock.EasyMock.expect(serviceComponent3.getDesiredStackId()).andReturn(stackId).anyTimes();
        org.easymock.EasyMock.expect(cluster.getServices()).andReturn(java.util.Collections.singletonMap("Service100", service)).anyTimes();
        org.easymock.EasyMock.expect(service.getServiceComponents()).andReturn(serviceComponentMap).anyTimes();
        org.easymock.EasyMock.expect(serviceComponent1.convertToResponse()).andReturn(new org.apache.ambari.server.controller.ServiceComponentResponse(100L, "Cluster100", "Service100", "Component100", stackId, "", serviceComponentStateCountMap, true, "Component100 Client", null, null));
        org.easymock.EasyMock.expect(serviceComponent2.convertToResponse()).andReturn(new org.apache.ambari.server.controller.ServiceComponentResponse(100L, "Cluster100", "Service100", "Component101", stackId, "", serviceComponentStateCountMap, false, "Component101 Client", null, null));
        org.easymock.EasyMock.expect(serviceComponent3.convertToResponse()).andReturn(new org.apache.ambari.server.controller.ServiceComponentResponse(100L, "Cluster100", "Service100", "Component102", stackId, "", serviceComponentStateCountMap, true, "Component102 Client", "1.1", org.apache.ambari.server.state.RepositoryVersionState.CURRENT));
        org.easymock.EasyMock.expect(ambariMetaInfo.getComponent("FOO", "1.0", null, "Component100")).andReturn(componentInfo1);
        org.easymock.EasyMock.expect(ambariMetaInfo.getComponent("FOO", "1.0", null, "Component101")).andReturn(componentInfo2);
        org.easymock.EasyMock.expect(ambariMetaInfo.getComponent("FOO", "1.0", null, "Component102")).andReturn(componentInfo1);
        org.easymock.EasyMock.expect(componentInfo1.getCategory()).andReturn("MASTER").anyTimes();
        org.easymock.EasyMock.expect(componentInfo2.getCategory()).andReturn("SLAVE").anyTimes();
        org.easymock.EasyMock.replay(managementController, clusters, cluster, ambariMetaInfo, service, serviceComponent1, serviceComponent2, serviceComponent3, componentInfo1, componentInfo2);
        org.springframework.security.core.context.SecurityContextHolder.getContext().setAuthentication(authentication);
        org.apache.ambari.server.controller.spi.ResourceProvider provider = new org.apache.ambari.server.controller.internal.ComponentResourceProvider(managementController, maintenanceStateHelper);
        java.util.Set<java.lang.String> propertyIds = new java.util.HashSet<>();
        propertyIds.add(org.apache.ambari.server.controller.internal.ComponentResourceProvider.CLUSTER_NAME);
        propertyIds.add(org.apache.ambari.server.controller.internal.ComponentResourceProvider.COMPONENT_NAME);
        propertyIds.add(org.apache.ambari.server.controller.internal.ComponentResourceProvider.CATEGORY);
        propertyIds.add(org.apache.ambari.server.controller.internal.ComponentResourceProvider.TOTAL_COUNT);
        propertyIds.add(org.apache.ambari.server.controller.internal.ComponentResourceProvider.STARTED_COUNT);
        propertyIds.add(org.apache.ambari.server.controller.internal.ComponentResourceProvider.INSTALLED_COUNT);
        propertyIds.add(org.apache.ambari.server.controller.internal.ComponentResourceProvider.INSTALLED_AND_MAINTENANCE_OFF_COUNT);
        propertyIds.add(org.apache.ambari.server.controller.internal.ComponentResourceProvider.INSTALL_FAILED_COUNT);
        propertyIds.add(org.apache.ambari.server.controller.internal.ComponentResourceProvider.INIT_COUNT);
        propertyIds.add(org.apache.ambari.server.controller.internal.ComponentResourceProvider.UNKNOWN_COUNT);
        propertyIds.add(org.apache.ambari.server.controller.internal.ComponentResourceProvider.RECOVERY_ENABLED);
        propertyIds.add(org.apache.ambari.server.controller.internal.ComponentResourceProvider.DESIRED_VERSION);
        propertyIds.add(org.apache.ambari.server.controller.internal.ComponentResourceProvider.REPOSITORY_STATE);
        org.apache.ambari.server.controller.spi.Predicate predicate = new org.apache.ambari.server.controller.utilities.PredicateBuilder().property(org.apache.ambari.server.controller.internal.ComponentResourceProvider.CLUSTER_NAME).equals("Cluster100").and().property(org.apache.ambari.server.controller.internal.ComponentResourceProvider.CATEGORY).equals("MASTER").toPredicate();
        org.apache.ambari.server.controller.spi.Request request = org.apache.ambari.server.controller.utilities.PropertyHelper.getReadRequest(propertyIds);
        java.util.Set<org.apache.ambari.server.controller.spi.Resource> resources = provider.getResources(request, predicate);
        org.junit.Assert.assertEquals(2, resources.size());
        for (org.apache.ambari.server.controller.spi.Resource resource : resources) {
            java.lang.String clusterName = ((java.lang.String) (resource.getPropertyValue(org.apache.ambari.server.controller.internal.ComponentResourceProvider.CLUSTER_NAME)));
            org.junit.Assert.assertEquals("Cluster100", clusterName);
            org.junit.Assert.assertEquals("MASTER", resource.getPropertyValue(org.apache.ambari.server.controller.internal.ComponentResourceProvider.CATEGORY));
            org.junit.Assert.assertEquals(2, resource.getPropertyValue(org.apache.ambari.server.controller.internal.ComponentResourceProvider.TOTAL_COUNT));
            org.junit.Assert.assertEquals(1, resource.getPropertyValue(org.apache.ambari.server.controller.internal.ComponentResourceProvider.STARTED_COUNT));
            org.junit.Assert.assertEquals(0, resource.getPropertyValue(org.apache.ambari.server.controller.internal.ComponentResourceProvider.INSTALLED_COUNT));
            org.junit.Assert.assertEquals(0, resource.getPropertyValue(org.apache.ambari.server.controller.internal.ComponentResourceProvider.INSTALLED_AND_MAINTENANCE_OFF_COUNT));
            org.junit.Assert.assertEquals(0, resource.getPropertyValue(org.apache.ambari.server.controller.internal.ComponentResourceProvider.INSTALL_FAILED_COUNT));
            org.junit.Assert.assertEquals(0, resource.getPropertyValue(org.apache.ambari.server.controller.internal.ComponentResourceProvider.INIT_COUNT));
            org.junit.Assert.assertEquals(1, resource.getPropertyValue(org.apache.ambari.server.controller.internal.ComponentResourceProvider.UNKNOWN_COUNT));
            org.junit.Assert.assertEquals(java.lang.String.valueOf(true), resource.getPropertyValue(org.apache.ambari.server.controller.internal.ComponentResourceProvider.RECOVERY_ENABLED));
            if (resource.getPropertyValue(org.apache.ambari.server.controller.internal.ComponentResourceProvider.COMPONENT_NAME).equals("Component102")) {
                org.junit.Assert.assertNotNull(resource.getPropertyValue(org.apache.ambari.server.controller.internal.ComponentResourceProvider.REPOSITORY_STATE));
                org.junit.Assert.assertNotNull(resource.getPropertyValue(org.apache.ambari.server.controller.internal.ComponentResourceProvider.DESIRED_VERSION));
                org.junit.Assert.assertEquals(org.apache.ambari.server.state.RepositoryVersionState.CURRENT, resource.getPropertyValue(org.apache.ambari.server.controller.internal.ComponentResourceProvider.REPOSITORY_STATE));
                org.junit.Assert.assertEquals("1.1", resource.getPropertyValue(org.apache.ambari.server.controller.internal.ComponentResourceProvider.DESIRED_VERSION));
            } else {
                org.junit.Assert.assertNull(resource.getPropertyValue(org.apache.ambari.server.controller.internal.ComponentResourceProvider.REPOSITORY_STATE));
                org.junit.Assert.assertNull(resource.getPropertyValue(org.apache.ambari.server.controller.internal.ComponentResourceProvider.DESIRED_VERSION));
            }
        }
        org.easymock.EasyMock.verify(managementController, clusters, cluster, ambariMetaInfo, service, serviceComponent1, serviceComponent2, serviceComponent3, componentInfo1, componentInfo2);
    }

    @org.junit.Test
    public void testUpdateResourcesAsAdministrator() throws java.lang.Exception {
        testUpdateResources(org.apache.ambari.server.security.TestAuthenticationFactory.createAdministrator());
    }

    @org.junit.Test
    public void testUpdateResourcesAsClusterAdministrator() throws java.lang.Exception {
        testUpdateResources(org.apache.ambari.server.security.TestAuthenticationFactory.createClusterAdministrator());
    }

    @org.junit.Test
    public void testUpdateResourcesAsServiceAdministrator() throws java.lang.Exception {
        testUpdateResources(org.apache.ambari.server.security.TestAuthenticationFactory.createServiceAdministrator());
    }

    private void testUpdateResources(org.springframework.security.core.Authentication authentication) throws java.lang.Exception {
        org.apache.ambari.server.controller.spi.Resource.Type type = org.apache.ambari.server.controller.spi.Resource.Type.Component;
        org.apache.ambari.server.controller.MaintenanceStateHelper maintenanceStateHelper = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.MaintenanceStateHelper.class);
        org.apache.ambari.server.controller.AmbariManagementController managementController = org.easymock.EasyMock.createMock(org.apache.ambari.server.controller.AmbariManagementController.class);
        org.apache.ambari.server.state.Clusters clusters = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Clusters.class);
        org.apache.ambari.server.state.Cluster cluster = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Cluster.class);
        org.apache.ambari.server.api.services.AmbariMetaInfo ambariMetaInfo = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.services.AmbariMetaInfo.class);
        org.apache.ambari.server.state.Service service = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Service.class);
        org.apache.ambari.server.state.ComponentInfo component1Info = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ComponentInfo.class);
        org.apache.ambari.server.state.ComponentInfo component2Info = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ComponentInfo.class);
        org.apache.ambari.server.state.ComponentInfo component3Info = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ComponentInfo.class);
        org.apache.ambari.server.state.ServiceComponent serviceComponent1 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ServiceComponent.class);
        org.apache.ambari.server.state.ServiceComponent serviceComponent2 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ServiceComponent.class);
        org.apache.ambari.server.state.ServiceComponent serviceComponent3 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ServiceComponent.class);
        org.apache.ambari.server.state.ServiceComponentHost serviceComponentHost = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ServiceComponentHost.class);
        org.apache.ambari.server.controller.RequestStatusResponse requestStatusResponse = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.RequestStatusResponse.class);
        org.apache.ambari.server.state.StackId stackId = new org.apache.ambari.server.state.StackId("stackName-1");
        java.util.Map<java.lang.String, org.apache.ambari.server.state.ServiceComponent> serviceComponentMap = new java.util.HashMap<>();
        serviceComponentMap.put("Component101", serviceComponent1);
        serviceComponentMap.put("Component102", serviceComponent2);
        serviceComponentMap.put("Component103", serviceComponent3);
        java.util.Map<java.lang.String, java.lang.Integer> serviceComponentStateCountMap = new java.util.HashMap<>();
        serviceComponentStateCountMap.put("startedCount", 0);
        serviceComponentStateCountMap.put("installedCount", 1);
        serviceComponentStateCountMap.put("installedAndMaintenanceOffCount", 0);
        serviceComponentStateCountMap.put("installFailedCount", 0);
        serviceComponentStateCountMap.put("initCount", 0);
        serviceComponentStateCountMap.put("unknownCount", 0);
        serviceComponentStateCountMap.put("totalCount", 1);
        org.easymock.EasyMock.expect(managementController.getClusters()).andReturn(clusters).anyTimes();
        org.easymock.EasyMock.expect(managementController.getAmbariMetaInfo()).andReturn(ambariMetaInfo).anyTimes();
        org.easymock.EasyMock.expect(managementController.getEffectiveMaintenanceState(org.easymock.EasyMock.capture(org.easymock.EasyMock.newCapture()))).andReturn(org.apache.ambari.server.state.MaintenanceState.OFF).anyTimes();
        org.easymock.EasyMock.expect(clusters.getCluster("Cluster100")).andReturn(cluster).anyTimes();
        org.easymock.EasyMock.expect(cluster.getService("Service100")).andReturn(service).anyTimes();
        org.easymock.EasyMock.expect(service.getName()).andReturn("Service100").anyTimes();
        org.easymock.EasyMock.expect(service.getServiceComponent("Component101")).andReturn(serviceComponent1).anyTimes();
        org.easymock.EasyMock.expect(service.getServiceComponent("Component102")).andReturn(serviceComponent1).anyTimes();
        org.easymock.EasyMock.expect(service.getServiceComponent("Component103")).andReturn(serviceComponent2).anyTimes();
        org.easymock.EasyMock.expect(serviceComponent1.getName()).andReturn("Component101").anyTimes();
        org.easymock.EasyMock.expect(serviceComponent1.getDesiredStackId()).andReturn(stackId).anyTimes();
        org.easymock.EasyMock.expect(serviceComponent2.getName()).andReturn("Component102").anyTimes();
        org.easymock.EasyMock.expect(serviceComponent2.getDesiredStackId()).andReturn(stackId).anyTimes();
        org.easymock.EasyMock.expect(serviceComponent3.getName()).andReturn("Component103").anyTimes();
        org.easymock.EasyMock.expect(serviceComponent3.getDesiredStackId()).andReturn(stackId).anyTimes();
        org.easymock.EasyMock.expect(cluster.getServices()).andReturn(java.util.Collections.singletonMap("Service100", service)).anyTimes();
        org.easymock.EasyMock.expect(cluster.getClusterId()).andReturn(2L).anyTimes();
        org.easymock.EasyMock.expect(service.getServiceComponents()).andReturn(serviceComponentMap).anyTimes();
        org.easymock.EasyMock.expect(ambariMetaInfo.getComponent("stackName", "1", "Service100", "Component101")).andReturn(component1Info).atLeastOnce();
        org.easymock.EasyMock.expect(ambariMetaInfo.getComponent("stackName", "1", "Service100", "Component102")).andReturn(component2Info).atLeastOnce();
        org.easymock.EasyMock.expect(ambariMetaInfo.getComponent("stackName", "1", "Service100", "Component103")).andReturn(component3Info).atLeastOnce();
        org.easymock.EasyMock.expect(component1Info.getCategory()).andReturn(null);
        org.easymock.EasyMock.expect(component2Info.getCategory()).andReturn(null);
        org.easymock.EasyMock.expect(component3Info.getCategory()).andReturn(null);
        org.easymock.EasyMock.expect(serviceComponent1.convertToResponse()).andReturn(new org.apache.ambari.server.controller.ServiceComponentResponse(100L, "Cluster100", "Service100", "Component101", stackId, "", serviceComponentStateCountMap, false, "Component101 Client", null, null));
        org.easymock.EasyMock.expect(serviceComponent2.convertToResponse()).andReturn(new org.apache.ambari.server.controller.ServiceComponentResponse(100L, "Cluster100", "Service100", "Component102", stackId, "", serviceComponentStateCountMap, false, "Component102 Client", null, null));
        org.easymock.EasyMock.expect(serviceComponent3.convertToResponse()).andReturn(new org.apache.ambari.server.controller.ServiceComponentResponse(100L, "Cluster100", "Service100", "Component103", stackId, "", serviceComponentStateCountMap, false, "Component103 Client", null, null));
        org.easymock.EasyMock.expect(serviceComponent1.getDesiredState()).andReturn(org.apache.ambari.server.state.State.INSTALLED).anyTimes();
        org.easymock.EasyMock.expect(serviceComponent2.getDesiredState()).andReturn(org.apache.ambari.server.state.State.INSTALLED).anyTimes();
        org.easymock.EasyMock.expect(serviceComponent3.getDesiredState()).andReturn(org.apache.ambari.server.state.State.INSTALLED).anyTimes();
        org.easymock.EasyMock.expect(serviceComponentHost.getState()).andReturn(org.apache.ambari.server.state.State.INSTALLED).anyTimes();
        java.util.Map<java.lang.String, org.apache.ambari.server.state.ServiceComponentHost> serviceComponentHosts = java.util.Collections.singletonMap("Host100", serviceComponentHost);
        org.easymock.EasyMock.expect(serviceComponent1.getServiceComponentHosts()).andReturn(serviceComponentHosts).anyTimes();
        org.easymock.EasyMock.expect(serviceComponent2.getServiceComponentHosts()).andReturn(serviceComponentHosts).anyTimes();
        org.easymock.EasyMock.expect(serviceComponent3.getServiceComponentHosts()).andReturn(serviceComponentHosts).anyTimes();
        org.easymock.EasyMock.expect(maintenanceStateHelper.isOperationAllowed(org.easymock.EasyMock.anyObject(org.apache.ambari.server.controller.spi.Resource.Type.class), org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.Service.class))).andReturn(true).anyTimes();
        org.easymock.Capture<java.util.Map<java.lang.String, java.lang.String>> requestPropertiesCapture = org.easymock.EasyMock.newCapture();
        org.easymock.Capture<java.util.Map<org.apache.ambari.server.state.State, java.util.List<org.apache.ambari.server.state.Service>>> changedServicesCapture = org.easymock.EasyMock.newCapture();
        org.easymock.Capture<java.util.Map<org.apache.ambari.server.state.State, java.util.List<org.apache.ambari.server.state.ServiceComponent>>> changedCompsCapture = org.easymock.EasyMock.newCapture();
        org.easymock.Capture<java.util.Map<java.lang.String, java.util.Map<org.apache.ambari.server.state.State, java.util.List<org.apache.ambari.server.state.ServiceComponentHost>>>> changedScHostsCapture = org.easymock.EasyMock.newCapture();
        org.easymock.Capture<java.util.Map<java.lang.String, java.lang.String>> requestParametersCapture = org.easymock.EasyMock.newCapture();
        org.easymock.Capture<java.util.Collection<org.apache.ambari.server.state.ServiceComponentHost>> ignoredScHostsCapture = org.easymock.EasyMock.newCapture();
        org.easymock.Capture<org.apache.ambari.server.state.Cluster> clusterCapture = org.easymock.EasyMock.newCapture();
        org.easymock.EasyMock.expect(managementController.createAndPersistStages(org.easymock.EasyMock.capture(clusterCapture), org.easymock.EasyMock.capture(requestPropertiesCapture), org.easymock.EasyMock.capture(requestParametersCapture), org.easymock.EasyMock.capture(changedServicesCapture), org.easymock.EasyMock.capture(changedCompsCapture), org.easymock.EasyMock.capture(changedScHostsCapture), org.easymock.EasyMock.capture(ignoredScHostsCapture), org.easymock.EasyMock.anyBoolean(), org.easymock.EasyMock.anyBoolean())).andReturn(requestStatusResponse);
        java.util.Map<java.lang.String, java.lang.String> mapRequestProps = new java.util.HashMap<>();
        mapRequestProps.put("context", "Called from a test");
        org.easymock.EasyMock.replay(managementController, clusters, cluster, ambariMetaInfo, service, component1Info, component2Info, component3Info, serviceComponent1, serviceComponent2, serviceComponent3, serviceComponentHost, requestStatusResponse, maintenanceStateHelper);
        org.springframework.security.core.context.SecurityContextHolder.getContext().setAuthentication(authentication);
        org.apache.ambari.server.controller.spi.ResourceProvider provider = new org.apache.ambari.server.controller.internal.ComponentResourceProvider(managementController, maintenanceStateHelper);
        java.util.Map<java.lang.String, java.lang.Object> properties = new java.util.LinkedHashMap<>();
        properties.put(org.apache.ambari.server.controller.internal.ComponentResourceProvider.RECOVERY_ENABLED, java.lang.String.valueOf(true));
        properties.put(org.apache.ambari.server.controller.internal.ComponentResourceProvider.STATE, "STARTED");
        properties.put(org.apache.ambari.server.controller.internal.ComponentResourceProvider.CLUSTER_NAME, "Cluster100");
        org.apache.ambari.server.controller.spi.Request request = org.apache.ambari.server.controller.utilities.PropertyHelper.getUpdateRequest(properties, mapRequestProps);
        org.apache.ambari.server.controller.spi.Predicate predicate = new org.apache.ambari.server.controller.utilities.PredicateBuilder().property(org.apache.ambari.server.controller.internal.ComponentResourceProvider.CLUSTER_NAME).equals("Cluster100").toPredicate();
        provider.updateResources(request, predicate);
        org.easymock.EasyMock.verify(managementController, clusters, cluster, ambariMetaInfo, service, component1Info, component2Info, component3Info, serviceComponent1, serviceComponent2, serviceComponent3, serviceComponentHost, requestStatusResponse, maintenanceStateHelper);
    }

    @org.junit.Test
    public void testSuccessDeleteResourcesAsAdministrator() throws java.lang.Exception {
        testSuccessDeleteResources(org.apache.ambari.server.security.TestAuthenticationFactory.createAdministrator(), org.apache.ambari.server.state.State.INSTALLED);
    }

    @org.junit.Test
    public void testSuccessDeleteResourcesAsClusterAdministrator() throws java.lang.Exception {
        testSuccessDeleteResources(org.apache.ambari.server.security.TestAuthenticationFactory.createClusterAdministrator(), org.apache.ambari.server.state.State.INSTALLED);
    }

    @org.junit.Test(expected = org.apache.ambari.server.security.authorization.AuthorizationException.class)
    public void testDeleteResourcesAsServiceAdministrator() throws java.lang.Exception {
        testSuccessDeleteResources(org.apache.ambari.server.security.TestAuthenticationFactory.createServiceAdministrator(), org.apache.ambari.server.state.State.INSTALLED);
    }

    @org.junit.Test(expected = org.apache.ambari.server.controller.spi.SystemException.class)
    public void testDeleteResourcesWithStartedHostComponentState() throws java.lang.Exception {
        testSuccessDeleteResources(org.apache.ambari.server.security.TestAuthenticationFactory.createAdministrator(), org.apache.ambari.server.state.State.STARTED);
    }

    private void testSuccessDeleteResources(org.springframework.security.core.Authentication authentication, org.apache.ambari.server.state.State hostComponentState) throws java.lang.Exception {
        org.apache.ambari.server.controller.spi.Resource.Type type = org.apache.ambari.server.controller.spi.Resource.Type.Component;
        org.apache.ambari.server.controller.AmbariManagementController managementController = org.easymock.EasyMock.createMock(org.apache.ambari.server.controller.AmbariManagementController.class);
        org.apache.ambari.server.controller.MaintenanceStateHelper maintenanceStateHelper = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.MaintenanceStateHelper.class);
        org.apache.ambari.server.state.Clusters clusters = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Clusters.class);
        org.apache.ambari.server.state.Cluster cluster = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Cluster.class);
        org.apache.ambari.server.state.Service service = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Service.class);
        org.apache.ambari.server.api.services.AmbariMetaInfo ambariMetaInfo = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.services.AmbariMetaInfo.class);
        org.apache.ambari.server.state.ServiceComponent serviceComponent = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ServiceComponent.class);
        org.apache.ambari.server.state.ServiceComponentHost serviceComponentHost = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ServiceComponentHost.class);
        org.apache.ambari.server.state.StackId stackId = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.StackId.class);
        java.util.Map<java.lang.String, org.apache.ambari.server.state.ServiceComponentHost> serviceComponentHosts = new java.util.HashMap<>();
        serviceComponentHosts.put("", serviceComponentHost);
        org.easymock.EasyMock.expect(managementController.getClusters()).andReturn(clusters);
        org.easymock.EasyMock.expect(managementController.getAmbariMetaInfo()).andReturn(ambariMetaInfo);
        org.easymock.EasyMock.expect(clusters.getCluster(org.apache.ambari.server.controller.internal.ComponentResourceProviderTest.CLUSTER_NAME)).andReturn(cluster);
        org.easymock.EasyMock.expect(cluster.getService(org.apache.ambari.server.controller.internal.ComponentResourceProviderTest.SERVICE_NAME)).andReturn(service);
        org.easymock.EasyMock.expect(cluster.getClusterId()).andReturn(org.apache.ambari.server.controller.internal.ComponentResourceProviderTest.CLUSTER_ID).anyTimes();
        org.easymock.EasyMock.expect(service.getServiceComponent("Component100")).andReturn(serviceComponent);
        org.easymock.EasyMock.expect(serviceComponent.getDesiredState()).andReturn(org.apache.ambari.server.state.State.STARTED);
        org.easymock.EasyMock.expect(serviceComponent.getServiceComponentHosts()).andReturn(serviceComponentHosts).anyTimes();
        org.easymock.EasyMock.expect(serviceComponentHost.getDesiredState()).andReturn(hostComponentState);
        service.deleteServiceComponent(org.easymock.EasyMock.eq("Component100"), org.easymock.EasyMock.anyObject(org.apache.ambari.server.controller.internal.DeleteHostComponentStatusMetaData.class));
        org.easymock.EasyMock.expectLastCall().once();
        org.easymock.EasyMock.replay(managementController, clusters, cluster, service, stackId, ambariMetaInfo, serviceComponent, serviceComponentHost, maintenanceStateHelper);
        org.springframework.security.core.context.SecurityContextHolder.getContext().setAuthentication(authentication);
        org.apache.ambari.server.controller.spi.ResourceProvider provider = org.apache.ambari.server.controller.internal.ComponentResourceProviderTest.getComponentProvider(managementController, maintenanceStateHelper);
        org.apache.ambari.server.controller.internal.AbstractResourceProviderTest.TestObserver observer = new org.apache.ambari.server.controller.internal.AbstractResourceProviderTest.TestObserver();
        ((org.apache.ambari.server.controller.internal.ObservableResourceProvider) (provider)).addObserver(observer);
        org.apache.ambari.server.controller.spi.Predicate predicate = new org.apache.ambari.server.controller.utilities.PredicateBuilder().property(org.apache.ambari.server.controller.internal.ComponentResourceProvider.CLUSTER_NAME).equals("Cluster100").and().property(org.apache.ambari.server.controller.internal.ComponentResourceProvider.SERVICE_NAME).equals("Service100").and().property(org.apache.ambari.server.controller.internal.ComponentResourceProvider.COMPONENT_NAME).equals("Component100").toPredicate();
        provider.deleteResources(new org.apache.ambari.server.controller.internal.RequestImpl(null, null, null, null), predicate);
        org.easymock.EasyMock.verify(managementController, service);
    }

    public static org.apache.ambari.server.controller.internal.ComponentResourceProvider getComponentProvider(org.apache.ambari.server.controller.AmbariManagementController managementController, org.apache.ambari.server.controller.MaintenanceStateHelper maintenanceStateHelper) throws java.lang.NoSuchFieldException, java.lang.IllegalAccessException {
        org.apache.ambari.server.controller.internal.ComponentResourceProvider provider = new org.apache.ambari.server.controller.internal.ComponentResourceProvider(managementController, maintenanceStateHelper);
        java.lang.reflect.Field STOMPComponentsDeleteHandlerField = org.apache.ambari.server.controller.internal.ComponentResourceProvider.class.getDeclaredField("STOMPComponentsDeleteHandler");
        STOMPComponentsDeleteHandlerField.setAccessible(true);
        org.apache.ambari.server.topology.STOMPComponentsDeleteHandler STOMPComponentsDeleteHandler = new org.apache.ambari.server.topology.STOMPComponentsDeleteHandler();
        STOMPComponentsDeleteHandlerField.set(provider, STOMPComponentsDeleteHandler);
        java.lang.reflect.Field topologyHolderProviderField = org.apache.ambari.server.topology.STOMPComponentsDeleteHandler.class.getDeclaredField("m_topologyHolder");
        topologyHolderProviderField.setAccessible(true);
        com.google.inject.Provider<org.apache.ambari.server.agent.stomp.TopologyHolder> m_topologyHolder = org.easymock.EasyMock.createMock(com.google.inject.Provider.class);
        topologyHolderProviderField.set(STOMPComponentsDeleteHandler, m_topologyHolder);
        org.apache.ambari.server.agent.stomp.TopologyHolder topologyHolder = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.agent.stomp.TopologyHolder.class);
        org.easymock.EasyMock.expect(m_topologyHolder.get()).andReturn(topologyHolder).anyTimes();
        java.lang.reflect.Field metadataHolderProviderField = org.apache.ambari.server.topology.STOMPComponentsDeleteHandler.class.getDeclaredField("metadataHolder");
        metadataHolderProviderField.setAccessible(true);
        com.google.inject.Provider<org.apache.ambari.server.agent.stomp.MetadataHolder> m_metadataHolder = org.easymock.EasyMock.createMock(com.google.inject.Provider.class);
        metadataHolderProviderField.set(STOMPComponentsDeleteHandler, m_metadataHolder);
        org.apache.ambari.server.agent.stomp.MetadataHolder metadataHolder = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.agent.stomp.MetadataHolder.class);
        org.easymock.EasyMock.expect(m_metadataHolder.get()).andReturn(metadataHolder).anyTimes();
        org.easymock.EasyMock.replay(m_metadataHolder, metadataHolder, m_topologyHolder, topologyHolder);
        return provider;
    }

    @org.junit.Test
    public void testDeleteResourcesWithEmptyClusterComponentNamesAsAdministrator() throws java.lang.Exception {
        testDeleteResourcesWithEmptyClusterComponentNames(org.apache.ambari.server.security.TestAuthenticationFactory.createAdministrator());
    }

    @org.junit.Test
    public void testDeleteResourcesWithEmptyClusterComponentNamesAsClusterAdministrator() throws java.lang.Exception {
        testDeleteResourcesWithEmptyClusterComponentNames(org.apache.ambari.server.security.TestAuthenticationFactory.createClusterAdministrator());
    }

    @org.junit.Test(expected = org.apache.ambari.server.security.authorization.AuthorizationException.class)
    public void testDeleteResourcesWithEmptyClusterComponentNamesAsServiceAdministrator() throws java.lang.Exception {
        testDeleteResourcesWithEmptyClusterComponentNames(org.apache.ambari.server.security.TestAuthenticationFactory.createServiceAdministrator());
    }

    private void testDeleteResourcesWithEmptyClusterComponentNames(org.springframework.security.core.Authentication authentication) throws java.lang.Exception {
        org.apache.ambari.server.controller.spi.Resource.Type type = org.apache.ambari.server.controller.spi.Resource.Type.Component;
        org.apache.ambari.server.controller.AmbariManagementController managementController = org.easymock.EasyMock.createMock(org.apache.ambari.server.controller.AmbariManagementController.class);
        org.apache.ambari.server.controller.MaintenanceStateHelper maintenanceStateHelper = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.MaintenanceStateHelper.class);
        org.apache.ambari.server.state.Clusters clusters = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Clusters.class);
        org.apache.ambari.server.api.services.AmbariMetaInfo ambariMetaInfo = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.services.AmbariMetaInfo.class);
        org.easymock.EasyMock.expect(managementController.getClusters()).andReturn(clusters).anyTimes();
        org.easymock.EasyMock.expect(managementController.getAmbariMetaInfo()).andReturn(ambariMetaInfo).anyTimes();
        org.easymock.EasyMock.replay(managementController, clusters, ambariMetaInfo, maintenanceStateHelper);
        org.springframework.security.core.context.SecurityContextHolder.getContext().setAuthentication(authentication);
        org.apache.ambari.server.controller.spi.ResourceProvider provider = new org.apache.ambari.server.controller.internal.ComponentResourceProvider(managementController, maintenanceStateHelper);
        org.apache.ambari.server.controller.internal.AbstractResourceProviderTest.TestObserver observer = new org.apache.ambari.server.controller.internal.AbstractResourceProviderTest.TestObserver();
        ((org.apache.ambari.server.controller.internal.ObservableResourceProvider) (provider)).addObserver(observer);
        org.apache.ambari.server.controller.spi.Predicate predicate1 = new org.apache.ambari.server.controller.utilities.PredicateBuilder().property(org.apache.ambari.server.controller.internal.ComponentResourceProvider.SERVICE_NAME).equals("Service100").and().property(org.apache.ambari.server.controller.internal.ComponentResourceProvider.COMPONENT_NAME).equals("Component100").toPredicate();
        try {
            provider.deleteResources(new org.apache.ambari.server.controller.internal.RequestImpl(null, null, null, null), predicate1);
            org.junit.Assert.fail("Expected IllegalArgumentException exception.");
        } catch (java.lang.IllegalArgumentException e) {
        }
        org.apache.ambari.server.controller.spi.Predicate predicate2 = new org.apache.ambari.server.controller.utilities.PredicateBuilder().property(org.apache.ambari.server.controller.internal.ComponentResourceProvider.CLUSTER_NAME).equals("Cluster100").and().property(org.apache.ambari.server.controller.internal.ComponentResourceProvider.SERVICE_NAME).equals("Service100").and().toPredicate();
        try {
            provider.deleteResources(new org.apache.ambari.server.controller.internal.RequestImpl(null, null, null, null), predicate2);
            org.junit.Assert.fail("Expected IllegalArgumentException exception.");
        } catch (java.lang.IllegalArgumentException e) {
        }
        org.easymock.EasyMock.verify(managementController);
    }

    @org.junit.Test
    public void testUpdateAutoStartAsAdministrator() throws java.lang.Exception {
        testUpdateAutoStart(org.apache.ambari.server.security.TestAuthenticationFactory.createAdministrator());
    }

    @org.junit.Test
    public void testUpdateAutoStartAsClusterAdministrator() throws java.lang.Exception {
        testUpdateAutoStart(org.apache.ambari.server.security.TestAuthenticationFactory.createClusterAdministrator());
    }

    @org.junit.Test
    public void testUpdateAutoStartAsServiceAdministrator() throws java.lang.Exception {
        testUpdateAutoStart(org.apache.ambari.server.security.TestAuthenticationFactory.createServiceAdministrator());
    }

    @org.junit.Test(expected = org.apache.ambari.server.security.authorization.AuthorizationException.class)
    public void testUpdateAutoStartAsClusterUser() throws java.lang.Exception {
        testUpdateAutoStart(org.apache.ambari.server.security.TestAuthenticationFactory.createClusterUser());
    }

    private void testUpdateAutoStart(org.springframework.security.core.Authentication authentication) throws java.lang.Exception {
        org.apache.ambari.server.controller.spi.Resource.Type type = org.apache.ambari.server.controller.spi.Resource.Type.Component;
        org.apache.ambari.server.controller.MaintenanceStateHelper maintenanceStateHelper = org.easymock.EasyMock.createMock(org.apache.ambari.server.controller.MaintenanceStateHelper.class);
        org.apache.ambari.server.controller.AmbariManagementController managementController = org.easymock.EasyMock.createMock(org.apache.ambari.server.controller.AmbariManagementController.class);
        org.apache.ambari.server.state.Clusters clusters = org.easymock.EasyMock.createMock(org.apache.ambari.server.state.Clusters.class);
        org.apache.ambari.server.state.Cluster cluster = org.easymock.EasyMock.createMock(org.apache.ambari.server.state.Cluster.class);
        org.apache.ambari.server.api.services.AmbariMetaInfo ambariMetaInfo = org.easymock.EasyMock.createMock(org.apache.ambari.server.api.services.AmbariMetaInfo.class);
        org.apache.ambari.server.state.Service service = org.easymock.EasyMock.createMock(org.apache.ambari.server.state.Service.class);
        org.apache.ambari.server.state.ComponentInfo component1Info = org.easymock.EasyMock.createMock(org.apache.ambari.server.state.ComponentInfo.class);
        org.apache.ambari.server.state.ServiceComponent serviceComponent1 = org.easymock.EasyMock.createMock(org.apache.ambari.server.state.ServiceComponent.class);
        org.apache.ambari.server.state.ServiceComponentHost serviceComponentHost = org.easymock.EasyMock.createMock(org.apache.ambari.server.state.ServiceComponentHost.class);
        org.apache.ambari.server.controller.RequestStatusResponse requestStatusResponse = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.RequestStatusResponse.class);
        org.apache.ambari.server.state.StackId stackId = new org.apache.ambari.server.state.StackId("stackName-1");
        java.util.Map<java.lang.String, org.apache.ambari.server.state.ServiceComponent> serviceComponentMap = new java.util.HashMap<>();
        serviceComponentMap.put("Component101", serviceComponent1);
        java.util.Map<java.lang.String, java.lang.Integer> serviceComponentStateCountMap = new java.util.HashMap<>();
        serviceComponentStateCountMap.put("startedCount", 0);
        serviceComponentStateCountMap.put("installedCount", 1);
        serviceComponentStateCountMap.put("installedAndMaintenanceOffCount", 0);
        serviceComponentStateCountMap.put("installFailedCount", 0);
        serviceComponentStateCountMap.put("initCount", 0);
        serviceComponentStateCountMap.put("unknownCount", 0);
        serviceComponentStateCountMap.put("totalCount", 1);
        org.easymock.EasyMock.expect(managementController.getClusters()).andReturn(clusters).anyTimes();
        org.easymock.EasyMock.expect(managementController.getAmbariMetaInfo()).andReturn(ambariMetaInfo).anyTimes();
        org.easymock.EasyMock.expect(managementController.getEffectiveMaintenanceState(org.easymock.EasyMock.capture(org.easymock.EasyMock.newCapture()))).andReturn(org.apache.ambari.server.state.MaintenanceState.OFF).anyTimes();
        org.easymock.EasyMock.expect(clusters.getCluster("Cluster100")).andReturn(cluster).anyTimes();
        org.easymock.EasyMock.expect(cluster.getResourceId()).andReturn(4L).atLeastOnce();
        org.easymock.EasyMock.expect(cluster.getServices()).andReturn(java.util.Collections.singletonMap("Service100", service)).anyTimes();
        org.easymock.EasyMock.expect(cluster.getClusterId()).andReturn(2L).anyTimes();
        org.easymock.EasyMock.expect(cluster.getService("Service100")).andReturn(service).anyTimes();
        org.easymock.EasyMock.expect(service.getName()).andReturn("Service100").anyTimes();
        org.easymock.EasyMock.expect(service.getServiceComponent("Component101")).andReturn(serviceComponent1).anyTimes();
        org.easymock.EasyMock.expect(serviceComponent1.getName()).andReturn("Component101").atLeastOnce();
        org.easymock.EasyMock.expect(serviceComponent1.isRecoveryEnabled()).andReturn(false).atLeastOnce();
        org.easymock.EasyMock.expect(serviceComponent1.getDesiredStackId()).andReturn(stackId).anyTimes();
        serviceComponent1.setRecoveryEnabled(true);
        org.easymock.EasyMock.expectLastCall().once();
        org.easymock.EasyMock.expect(service.getServiceComponents()).andReturn(serviceComponentMap).anyTimes();
        org.easymock.EasyMock.expect(ambariMetaInfo.getComponent("stackName", "1", "Service100", "Component101")).andReturn(component1Info).atLeastOnce();
        org.easymock.EasyMock.expect(component1Info.getCategory()).andReturn(null);
        org.easymock.EasyMock.expect(serviceComponent1.convertToResponse()).andReturn(new org.apache.ambari.server.controller.ServiceComponentResponse(100L, "Cluster100", "Service100", "Component101", stackId, "", serviceComponentStateCountMap, false, "Component101 Client", null, null));
        org.easymock.EasyMock.expect(serviceComponent1.getDesiredState()).andReturn(org.apache.ambari.server.state.State.INSTALLED).anyTimes();
        org.easymock.EasyMock.expect(serviceComponentHost.getState()).andReturn(org.apache.ambari.server.state.State.INSTALLED).anyTimes();
        java.util.Map<java.lang.String, org.apache.ambari.server.state.ServiceComponentHost> serviceComponentHosts = java.util.Collections.singletonMap("Host100", serviceComponentHost);
        org.easymock.EasyMock.expect(serviceComponent1.getServiceComponentHosts()).andReturn(serviceComponentHosts).anyTimes();
        org.easymock.EasyMock.expect(maintenanceStateHelper.isOperationAllowed(org.easymock.EasyMock.anyObject(org.apache.ambari.server.controller.spi.Resource.Type.class), org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.Service.class))).andReturn(true).anyTimes();
        org.easymock.Capture<java.util.Map<java.lang.String, java.lang.String>> requestPropertiesCapture = org.easymock.EasyMock.newCapture();
        org.easymock.Capture<java.util.Map<org.apache.ambari.server.state.State, java.util.List<org.apache.ambari.server.state.Service>>> changedServicesCapture = org.easymock.EasyMock.newCapture();
        org.easymock.Capture<java.util.Map<org.apache.ambari.server.state.State, java.util.List<org.apache.ambari.server.state.ServiceComponent>>> changedCompsCapture = org.easymock.EasyMock.newCapture();
        org.easymock.Capture<java.util.Map<java.lang.String, java.util.Map<org.apache.ambari.server.state.State, java.util.List<org.apache.ambari.server.state.ServiceComponentHost>>>> changedScHostsCapture = org.easymock.EasyMock.newCapture();
        org.easymock.Capture<java.util.Map<java.lang.String, java.lang.String>> requestParametersCapture = org.easymock.EasyMock.newCapture();
        org.easymock.Capture<java.util.Collection<org.apache.ambari.server.state.ServiceComponentHost>> ignoredScHostsCapture = org.easymock.EasyMock.newCapture();
        org.easymock.Capture<org.apache.ambari.server.state.Cluster> clusterCapture = org.easymock.EasyMock.newCapture();
        org.easymock.EasyMock.expect(managementController.createAndPersistStages(org.easymock.EasyMock.capture(clusterCapture), org.easymock.EasyMock.capture(requestPropertiesCapture), org.easymock.EasyMock.capture(requestParametersCapture), org.easymock.EasyMock.capture(changedServicesCapture), org.easymock.EasyMock.capture(changedCompsCapture), org.easymock.EasyMock.capture(changedScHostsCapture), org.easymock.EasyMock.capture(ignoredScHostsCapture), org.easymock.EasyMock.anyBoolean(), org.easymock.EasyMock.anyBoolean())).andReturn(requestStatusResponse);
        java.util.Map<java.lang.String, java.lang.String> mapRequestProps = new java.util.HashMap<>();
        mapRequestProps.put("context", "Called from a test");
        org.easymock.EasyMock.replay(managementController, clusters, cluster, ambariMetaInfo, service, component1Info, serviceComponent1, serviceComponentHost, requestStatusResponse, maintenanceStateHelper);
        org.springframework.security.core.context.SecurityContextHolder.getContext().setAuthentication(authentication);
        org.apache.ambari.server.controller.spi.ResourceProvider provider = new org.apache.ambari.server.controller.internal.ComponentResourceProvider(managementController, maintenanceStateHelper);
        java.util.Map<java.lang.String, java.lang.Object> properties = new java.util.LinkedHashMap<>();
        properties.put(org.apache.ambari.server.controller.internal.ComponentResourceProvider.RECOVERY_ENABLED, java.lang.String.valueOf(true));
        org.apache.ambari.server.controller.spi.Request request = org.apache.ambari.server.controller.utilities.PropertyHelper.getUpdateRequest(properties, mapRequestProps);
        org.apache.ambari.server.controller.spi.Predicate predicate = new org.apache.ambari.server.controller.utilities.PredicateBuilder().property(org.apache.ambari.server.controller.internal.ComponentResourceProvider.CLUSTER_NAME).equals("Cluster100").toPredicate();
        provider.updateResources(request, predicate);
        org.easymock.EasyMock.verify(managementController, clusters, cluster, ambariMetaInfo, service, component1Info, serviceComponent1, serviceComponentHost, requestStatusResponse, maintenanceStateHelper);
    }

    @org.junit.Test
    public void testGetComponents() throws java.lang.Exception {
        org.apache.ambari.server.controller.AmbariManagementController managementController = org.easymock.EasyMock.createMock(org.apache.ambari.server.controller.AmbariManagementController.class);
        org.apache.ambari.server.state.Clusters clusters = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Clusters.class);
        org.apache.ambari.server.api.services.AmbariMetaInfo ambariMetaInfo = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.services.AmbariMetaInfo.class);
        org.apache.ambari.server.state.StackId stackId = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.StackId.class);
        org.apache.ambari.server.state.Cluster cluster = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Cluster.class);
        org.apache.ambari.server.state.Service service = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Service.class);
        org.apache.ambari.server.state.ComponentInfo componentInfo = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ComponentInfo.class);
        org.apache.ambari.server.state.ServiceComponent component = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ServiceComponent.class);
        org.apache.ambari.server.controller.ServiceComponentResponse response = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.ServiceComponentResponse.class);
        org.apache.ambari.server.controller.ServiceComponentRequest request1 = new org.apache.ambari.server.controller.ServiceComponentRequest("cluster1", "service1", "component1", null, java.lang.String.valueOf(true));
        java.util.Set<org.apache.ambari.server.controller.ServiceComponentRequest> setRequests = new java.util.HashSet<>();
        setRequests.add(request1);
        org.easymock.EasyMock.expect(managementController.getClusters()).andReturn(clusters).anyTimes();
        org.easymock.EasyMock.expect(managementController.getAmbariMetaInfo()).andReturn(ambariMetaInfo).anyTimes();
        org.easymock.EasyMock.expect(stackId.getStackName()).andReturn("stackName").anyTimes();
        org.easymock.EasyMock.expect(stackId.getStackVersion()).andReturn("1").anyTimes();
        org.easymock.EasyMock.expect(clusters.getCluster("cluster1")).andReturn(cluster);
        org.easymock.EasyMock.expect(cluster.getService("service1")).andReturn(service);
        org.easymock.EasyMock.expect(service.getName()).andReturn("service1").anyTimes();
        org.easymock.EasyMock.expect(service.getServiceComponent("component1")).andReturn(component);
        org.easymock.EasyMock.expect(ambariMetaInfo.getComponent("stackName", "1", "service1", "component1")).andReturn(componentInfo);
        org.easymock.EasyMock.expect(componentInfo.getCategory()).andReturn(null);
        org.easymock.EasyMock.expect(component.getDesiredStackId()).andReturn(stackId).anyTimes();
        org.easymock.EasyMock.expect(component.convertToResponse()).andReturn(response);
        org.easymock.EasyMock.replay(clusters, cluster, service, componentInfo, component, response, ambariMetaInfo, stackId, managementController);
        java.util.Set<org.apache.ambari.server.controller.ServiceComponentResponse> setResponses = org.apache.ambari.server.controller.internal.ComponentResourceProviderTest.getComponentResourceProvider(managementController).getComponents(setRequests);
        org.junit.Assert.assertEquals(1, setResponses.size());
        org.junit.Assert.assertTrue(setResponses.contains(response));
        org.easymock.EasyMock.verify(clusters, cluster, service, componentInfo, component, response, ambariMetaInfo, stackId, managementController);
    }

    @org.junit.Test
    public void testGetComponents_OR_Predicate_ServiceComponentNotFoundException() throws java.lang.Exception {
        org.apache.ambari.server.controller.AmbariManagementController managementController = org.easymock.EasyMock.createMock(org.apache.ambari.server.controller.AmbariManagementController.class);
        org.apache.ambari.server.state.Clusters clusters = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Clusters.class);
        org.apache.ambari.server.api.services.AmbariMetaInfo ambariMetaInfo = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.services.AmbariMetaInfo.class);
        org.apache.ambari.server.state.StackId stackId = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.StackId.class);
        org.apache.ambari.server.state.Cluster cluster = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Cluster.class);
        org.apache.ambari.server.state.Service service = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Service.class);
        org.apache.ambari.server.state.ComponentInfo component3Info = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ComponentInfo.class);
        org.apache.ambari.server.state.ComponentInfo component4Info = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ComponentInfo.class);
        org.apache.ambari.server.state.ServiceComponent component1 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ServiceComponent.class);
        org.apache.ambari.server.state.ServiceComponent component2 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ServiceComponent.class);
        org.apache.ambari.server.controller.ServiceComponentResponse response1 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.ServiceComponentResponse.class);
        org.apache.ambari.server.controller.ServiceComponentResponse response2 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.ServiceComponentResponse.class);
        org.apache.ambari.server.controller.ServiceComponentRequest request1 = new org.apache.ambari.server.controller.ServiceComponentRequest("cluster1", "service1", "component1", null, java.lang.String.valueOf(true));
        org.apache.ambari.server.controller.ServiceComponentRequest request2 = new org.apache.ambari.server.controller.ServiceComponentRequest("cluster1", "service1", "component2", null, java.lang.String.valueOf(true));
        org.apache.ambari.server.controller.ServiceComponentRequest request3 = new org.apache.ambari.server.controller.ServiceComponentRequest("cluster1", "service1", "component3", null, java.lang.String.valueOf(true));
        org.apache.ambari.server.controller.ServiceComponentRequest request4 = new org.apache.ambari.server.controller.ServiceComponentRequest("cluster1", "service1", "component4", null, java.lang.String.valueOf(true));
        org.apache.ambari.server.controller.ServiceComponentRequest request5 = new org.apache.ambari.server.controller.ServiceComponentRequest("cluster1", "service2", null, null, java.lang.String.valueOf(true));
        java.util.Set<org.apache.ambari.server.controller.ServiceComponentRequest> setRequests = new java.util.HashSet<>();
        setRequests.add(request1);
        setRequests.add(request2);
        setRequests.add(request3);
        setRequests.add(request4);
        setRequests.add(request5);
        org.easymock.EasyMock.expect(managementController.getClusters()).andReturn(clusters).anyTimes();
        org.easymock.EasyMock.expect(managementController.getAmbariMetaInfo()).andReturn(ambariMetaInfo).anyTimes();
        org.easymock.EasyMock.expect(stackId.getStackName()).andReturn("stackName").anyTimes();
        org.easymock.EasyMock.expect(stackId.getStackVersion()).andReturn("1").anyTimes();
        org.easymock.EasyMock.expect(clusters.getCluster("cluster1")).andReturn(cluster).anyTimes();
        org.easymock.EasyMock.expect(cluster.getDesiredStackVersion()).andReturn(stackId).anyTimes();
        org.easymock.EasyMock.expect(cluster.getService("service1")).andReturn(service).anyTimes();
        org.easymock.EasyMock.expect(cluster.getService("service2")).andThrow(new org.apache.ambari.server.ObjectNotFoundException("service2"));
        org.easymock.EasyMock.expect(ambariMetaInfo.getComponent("stackName", "1", "service1", "component3")).andReturn(component3Info);
        org.easymock.EasyMock.expect(ambariMetaInfo.getComponent("stackName", "1", "service1", "component4")).andReturn(component4Info);
        org.easymock.EasyMock.expect(component3Info.getCategory()).andReturn(null);
        org.easymock.EasyMock.expect(component4Info.getCategory()).andReturn(null);
        org.easymock.EasyMock.expect(service.getName()).andReturn("service1").anyTimes();
        org.easymock.EasyMock.expect(service.getServiceComponent("component1")).andThrow(new org.apache.ambari.server.ServiceComponentNotFoundException("cluster1", "service1", "component1"));
        org.easymock.EasyMock.expect(service.getServiceComponent("component2")).andThrow(new org.apache.ambari.server.ServiceComponentNotFoundException("cluster1", "service1", "component2"));
        org.easymock.EasyMock.expect(service.getServiceComponent("component3")).andReturn(component1);
        org.easymock.EasyMock.expect(service.getServiceComponent("component4")).andReturn(component2);
        org.easymock.EasyMock.expect(component1.convertToResponse()).andReturn(response1);
        org.easymock.EasyMock.expect(component1.getDesiredStackId()).andReturn(stackId).anyTimes();
        org.easymock.EasyMock.expect(component2.convertToResponse()).andReturn(response2);
        org.easymock.EasyMock.expect(component2.getDesiredStackId()).andReturn(stackId).anyTimes();
        org.easymock.EasyMock.replay(clusters, cluster, service, component3Info, component4Info, component1, component2, response1, response2, ambariMetaInfo, stackId, managementController);
        java.util.Set<org.apache.ambari.server.controller.ServiceComponentResponse> setResponses = org.apache.ambari.server.controller.internal.ComponentResourceProviderTest.getComponentResourceProvider(managementController).getComponents(setRequests);
        org.junit.Assert.assertEquals(2, setResponses.size());
        org.junit.Assert.assertTrue(setResponses.contains(response1));
        org.junit.Assert.assertTrue(setResponses.contains(response2));
        org.easymock.EasyMock.verify(clusters, cluster, service, component3Info, component4Info, component1, component2, response1, response2, ambariMetaInfo, stackId, managementController);
    }

    public static org.apache.ambari.server.controller.internal.ComponentResourceProvider getComponentResourceProvider(org.apache.ambari.server.controller.AmbariManagementController managementController) throws org.apache.ambari.server.AmbariException {
        org.apache.ambari.server.controller.spi.Resource.Type type = org.apache.ambari.server.controller.spi.Resource.Type.Component;
        org.apache.ambari.server.controller.MaintenanceStateHelper maintenanceStateHelper = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.MaintenanceStateHelper.class);
        org.easymock.EasyMock.expect(maintenanceStateHelper.isOperationAllowed(org.easymock.EasyMock.anyObject(org.apache.ambari.server.controller.spi.Resource.Type.class), org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.Service.class))).andReturn(true).anyTimes();
        org.easymock.EasyMock.expect(maintenanceStateHelper.isOperationAllowed(org.easymock.EasyMock.anyObject(org.apache.ambari.server.controller.spi.Resource.Type.class), org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.ServiceComponentHost.class))).andReturn(true).anyTimes();
        org.easymock.EasyMock.replay(maintenanceStateHelper);
        return new org.apache.ambari.server.controller.internal.ComponentResourceProvider(managementController, maintenanceStateHelper);
    }

    @org.junit.Test
    public void testGetComponents_ServiceComponentNotFoundException() throws java.lang.Exception {
        com.google.inject.Injector injector = org.easymock.EasyMock.createStrictMock(com.google.inject.Injector.class);
        org.easymock.Capture<org.apache.ambari.server.controller.AmbariManagementController> controllerCapture = org.easymock.EasyMock.newCapture();
        org.apache.ambari.server.state.Clusters clusters = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Clusters.class);
        org.apache.ambari.server.controller.MaintenanceStateHelper maintHelper = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.MaintenanceStateHelper.class);
        org.apache.ambari.server.state.Cluster cluster = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Cluster.class);
        org.apache.ambari.server.state.Service service = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Service.class);
        org.apache.ambari.server.controller.ServiceComponentRequest request1 = new org.apache.ambari.server.controller.ServiceComponentRequest("cluster1", "service1", "component1", null, java.lang.String.valueOf(true));
        java.util.Set<org.apache.ambari.server.controller.ServiceComponentRequest> setRequests = new java.util.HashSet<>();
        setRequests.add(request1);
        org.apache.ambari.server.controller.AmbariManagementControllerImplTest.constructorInit(injector, controllerCapture, null, maintHelper, org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.KerberosHelper.class), null, null);
        org.easymock.EasyMock.expect(clusters.getCluster("cluster1")).andReturn(cluster);
        org.easymock.EasyMock.expect(cluster.getService("service1")).andReturn(service);
        org.easymock.EasyMock.expect(service.getServiceComponent("component1")).andThrow(new org.apache.ambari.server.ServiceComponentNotFoundException("cluster1", "service1", "component1"));
        org.easymock.EasyMock.replay(maintHelper, injector, clusters, cluster, service);
        org.apache.ambari.server.controller.AmbariManagementController controller = new org.apache.ambari.server.controller.AmbariManagementControllerImpl(null, clusters, injector);
        try {
            org.apache.ambari.server.controller.internal.ComponentResourceProviderTest.getComponentResourceProvider(controller).getComponents(setRequests);
            org.junit.Assert.fail("expected ServiceComponentNotFoundException");
        } catch (org.apache.ambari.server.ServiceComponentNotFoundException e) {
        }
        org.junit.Assert.assertSame(controller, controllerCapture.getValue());
        org.easymock.EasyMock.verify(injector, clusters, cluster, service);
    }

    public static void createComponents(org.apache.ambari.server.controller.AmbariManagementController controller, java.util.Set<org.apache.ambari.server.controller.ServiceComponentRequest> requests) throws org.apache.ambari.server.AmbariException, org.apache.ambari.server.security.authorization.AuthorizationException {
        org.apache.ambari.server.controller.internal.ComponentResourceProvider provider = org.apache.ambari.server.controller.internal.ComponentResourceProviderTest.getComponentResourceProvider(controller);
        provider.createComponents(requests);
    }

    public static java.util.Set<org.apache.ambari.server.controller.ServiceComponentResponse> getComponents(org.apache.ambari.server.controller.AmbariManagementController controller, java.util.Set<org.apache.ambari.server.controller.ServiceComponentRequest> requests) throws org.apache.ambari.server.AmbariException {
        org.apache.ambari.server.controller.internal.ComponentResourceProvider provider = org.apache.ambari.server.controller.internal.ComponentResourceProviderTest.getComponentResourceProvider(controller);
        return provider.getComponents(requests);
    }

    public static org.apache.ambari.server.controller.RequestStatusResponse updateComponents(org.apache.ambari.server.controller.AmbariManagementController controller, java.util.Set<org.apache.ambari.server.controller.ServiceComponentRequest> requests, java.util.Map<java.lang.String, java.lang.String> requestProperties, boolean runSmokeTest) throws org.apache.ambari.server.AmbariException, org.apache.ambari.server.security.authorization.AuthorizationException {
        org.apache.ambari.server.controller.internal.ComponentResourceProvider provider = org.apache.ambari.server.controller.internal.ComponentResourceProviderTest.getComponentResourceProvider(controller);
        return provider.updateComponents(requests, requestProperties, runSmokeTest);
    }
}
