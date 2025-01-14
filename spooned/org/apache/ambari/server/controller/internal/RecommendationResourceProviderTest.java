package org.apache.ambari.server.controller.internal;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.easymock.EasyMock.eq;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
public class RecommendationResourceProviderTest {
    @org.mockito.Mock
    private org.apache.ambari.server.api.services.stackadvisor.StackAdvisorHelper stackAdvisorHelper;

    @org.mockito.Mock
    private org.apache.ambari.server.configuration.Configuration configuration;

    @org.mockito.Mock
    private org.apache.ambari.server.state.Clusters clusters;

    @org.mockito.Mock
    private org.apache.ambari.server.api.services.AmbariMetaInfo ambariMetaInfo;

    @org.mockito.Mock
    private org.apache.ambari.server.controller.AmbariManagementController managementController;

    private org.apache.ambari.server.controller.internal.RecommendationResourceProvider provider;

    @org.junit.Before
    public void setUp() {
        org.mockito.MockitoAnnotations.initMocks(this);
        provider = org.mockito.Mockito.spy(new org.apache.ambari.server.controller.internal.RecommendationResourceProvider(managementController));
        org.apache.ambari.server.controller.internal.RecommendationResourceProvider.init(stackAdvisorHelper, configuration, clusters, ambariMetaInfo);
    }

    @org.junit.Test
    public void testCreateConfigurationResources() throws java.lang.Exception {
        java.util.Set<java.lang.String> hosts = new java.util.HashSet<>(java.util.Arrays.asList("hostName1", "hostName2", "hostName3"));
        java.util.Set<java.lang.String> services = new java.util.HashSet<>(java.util.Arrays.asList("serviceName1", "serviceName2", "serviceName3"));
        org.apache.ambari.server.controller.spi.RequestStatus requestStatus = testCreateResources(hosts, services, org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRequest.StackAdvisorRequestType.CONFIGURATIONS, true);
        org.junit.Assert.assertNotNull(requestStatus);
        org.junit.Assert.assertEquals(1, requestStatus.getAssociatedResources().size());
        org.junit.Assert.assertEquals(org.apache.ambari.server.controller.spi.Resource.Type.Recommendation, requestStatus.getAssociatedResources().iterator().next().getType());
        java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.lang.Object>> propertiesMap = requestStatus.getAssociatedResources().iterator().next().getPropertiesMap();
        org.junit.Assert.assertEquals(2, propertiesMap.size());
        org.junit.Assert.assertTrue(propertiesMap.containsKey("recommendations"));
        org.junit.Assert.assertTrue(propertiesMap.containsKey("recommendations/blueprint/configurations"));
        org.junit.Assert.assertEquals(1, propertiesMap.get("recommendations").size());
        org.junit.Assert.assertTrue(propertiesMap.get("recommendations").containsKey("config-groups"));
        org.junit.Assert.assertNotNull(propertiesMap.get("recommendations").get("config-groups"));
        org.junit.Assert.assertEquals(0, propertiesMap.get("recommendations/blueprint/configurations").size());
    }

    @org.junit.Test
    public void testCreateNotConfigurationResources() throws java.lang.Exception {
        java.util.Set<java.lang.String> hosts = new java.util.HashSet<>(java.util.Arrays.asList("hostName1", "hostName2", "hostName3"));
        java.util.Set<java.lang.String> services = new java.util.HashSet<>(java.util.Arrays.asList("serviceName1", "serviceName2", "serviceName3"));
        org.apache.ambari.server.controller.spi.RequestStatus requestStatus = testCreateResources(hosts, services, org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRequest.StackAdvisorRequestType.HOST_GROUPS, false);
        org.junit.Assert.assertNotNull(requestStatus);
        org.junit.Assert.assertEquals(1, requestStatus.getAssociatedResources().size());
        org.junit.Assert.assertEquals(org.apache.ambari.server.controller.spi.Resource.Type.Recommendation, requestStatus.getAssociatedResources().iterator().next().getType());
        java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.lang.Object>> propertiesMap = requestStatus.getAssociatedResources().iterator().next().getPropertiesMap();
        org.junit.Assert.assertEquals(7, propertiesMap.size());
        org.junit.Assert.assertTrue(propertiesMap.containsKey(""));
        org.junit.Assert.assertTrue(propertiesMap.containsKey("Recommendation"));
        org.junit.Assert.assertTrue(propertiesMap.containsKey("Versions"));
        org.junit.Assert.assertTrue(propertiesMap.containsKey("recommendations"));
        org.junit.Assert.assertTrue(propertiesMap.containsKey("recommendations/blueprint"));
        org.junit.Assert.assertTrue(propertiesMap.containsKey("recommendations/blueprint/configurations"));
        org.junit.Assert.assertTrue(propertiesMap.containsKey("recommendations/blueprint_cluster_binding"));
        org.junit.Assert.assertEquals(2, propertiesMap.get("").size());
        org.junit.Assert.assertTrue(propertiesMap.get("").containsKey("hosts"));
        org.junit.Assert.assertTrue(propertiesMap.get("").containsKey("services"));
        org.junit.Assert.assertEquals(hosts, propertiesMap.get("").get("hosts"));
        org.junit.Assert.assertEquals(services, propertiesMap.get("").get("services"));
        org.junit.Assert.assertEquals(1, propertiesMap.get("Recommendation").size());
        org.junit.Assert.assertTrue(propertiesMap.get("Recommendation").containsKey("id"));
        org.junit.Assert.assertEquals(1, propertiesMap.get("Recommendation").get("id"));
        org.junit.Assert.assertEquals(2, propertiesMap.get("Versions").size());
        org.junit.Assert.assertTrue(propertiesMap.get("Versions").containsKey("stack_name"));
        org.junit.Assert.assertTrue(propertiesMap.get("Versions").containsKey("stack_version"));
        org.junit.Assert.assertEquals("stackName", propertiesMap.get("Versions").get("stack_name"));
        org.junit.Assert.assertEquals("stackVersion", propertiesMap.get("Versions").get("stack_version"));
        org.junit.Assert.assertEquals(1, propertiesMap.get("recommendations").size());
        org.junit.Assert.assertTrue(propertiesMap.get("recommendations").containsKey("config-groups"));
        org.junit.Assert.assertNotNull(propertiesMap.get("recommendations").get("config-groups"));
        org.junit.Assert.assertEquals(1, propertiesMap.get("recommendations/blueprint").size());
        org.junit.Assert.assertTrue(propertiesMap.get("recommendations/blueprint").containsKey("host_groups"));
        org.junit.Assert.assertNotNull(propertiesMap.get("recommendations/blueprint").get("host_groups"));
        org.junit.Assert.assertEquals(0, propertiesMap.get("recommendations/blueprint/configurations").size());
        org.junit.Assert.assertEquals(1, propertiesMap.get("recommendations/blueprint_cluster_binding").size());
        org.junit.Assert.assertTrue(propertiesMap.get("recommendations/blueprint_cluster_binding").containsKey("host_groups"));
        org.junit.Assert.assertNotNull(propertiesMap.get("recommendations/blueprint_cluster_binding").get("host_groups"));
    }

    private org.apache.ambari.server.controller.spi.RequestStatus testCreateResources(java.util.Set<java.lang.String> hosts, java.util.Set<java.lang.String> services, org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRequest.StackAdvisorRequestType type, java.lang.Boolean configsOnlyResponse) throws java.lang.Exception {
        org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRequest stackAdvisorRequest = org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRequest.StackAdvisorRequestBuilder.forStack(null, null).ofType(type).withConfigsResponse(configsOnlyResponse).build();
        org.apache.ambari.server.controller.spi.Request request = org.mockito.Mockito.mock(org.apache.ambari.server.controller.spi.Request.class);
        org.mockito.Mockito.doReturn(stackAdvisorRequest).when(provider).prepareStackAdvisorRequest(org.easymock.EasyMock.eq(request));
        org.apache.ambari.server.api.services.stackadvisor.recommendations.RecommendationResponse response = new org.apache.ambari.server.api.services.stackadvisor.recommendations.RecommendationResponse();
        org.apache.ambari.server.api.services.stackadvisor.recommendations.RecommendationResponse.Recommendation recommendation = new org.apache.ambari.server.api.services.stackadvisor.recommendations.RecommendationResponse.Recommendation();
        recommendation.setConfigGroups(new java.util.HashSet<>());
        org.apache.ambari.server.api.services.stackadvisor.recommendations.RecommendationResponse.Blueprint blueprint = new org.apache.ambari.server.api.services.stackadvisor.recommendations.RecommendationResponse.Blueprint();
        blueprint.setConfigurations(new java.util.HashMap<>());
        blueprint.setHostGroups(new java.util.HashSet<>());
        recommendation.setBlueprint(blueprint);
        org.apache.ambari.server.api.services.stackadvisor.recommendations.RecommendationResponse.BlueprintClusterBinding blueprintClusterBinding = new org.apache.ambari.server.api.services.stackadvisor.recommendations.RecommendationResponse.BlueprintClusterBinding();
        blueprintClusterBinding.setHostGroups(new java.util.HashSet<>());
        recommendation.setBlueprintClusterBinding(blueprintClusterBinding);
        response.setRecommendations(recommendation);
        response.setId(1);
        org.apache.ambari.server.api.services.stackadvisor.StackAdvisorResponse.Version version = new org.apache.ambari.server.api.services.stackadvisor.StackAdvisorResponse.Version();
        version.setStackName("stackName");
        version.setStackVersion("stackVersion");
        response.setVersion(version);
        response.setHosts(hosts);
        response.setServices(services);
        org.mockito.Mockito.when(stackAdvisorHelper.recommend(org.mockito.ArgumentMatchers.any(org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRequest.class))).thenReturn(response);
        return provider.createResources(request);
    }
}
