package org.apache.ambari.server.controller.internal;
import org.easymock.Capture;
import org.easymock.EasyMockSupport;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.newCapture;
public class AmbariServerSSOConfigurationHandlerTest extends org.easymock.EasyMockSupport {
    @org.junit.Test
    public void testUpdateCategoryAmbariNotManagingServices() throws org.apache.ambari.server.AmbariException {
        java.util.Map<java.lang.String, java.lang.String> ssoConfigurationProperties = new java.util.HashMap<>();
        ssoConfigurationProperties.put(org.apache.ambari.server.configuration.AmbariServerConfigurationKey.SSO_MANAGE_SERVICES.key(), "false");
        org.apache.ambari.server.orm.entities.AmbariConfigurationEntity entity = new org.apache.ambari.server.orm.entities.AmbariConfigurationEntity();
        entity.setCategoryName(org.apache.ambari.server.configuration.AmbariServerConfigurationCategory.SSO_CONFIGURATION.getCategoryName());
        entity.setPropertyName(org.apache.ambari.server.configuration.AmbariServerConfigurationKey.SSO_MANAGE_SERVICES.key());
        entity.setPropertyValue("false");
        java.util.List<org.apache.ambari.server.orm.entities.AmbariConfigurationEntity> entities = java.util.Collections.singletonList(entity);
        org.apache.ambari.server.orm.dao.AmbariConfigurationDAO ambariConfigurationDAO = createMock(org.apache.ambari.server.orm.dao.AmbariConfigurationDAO.class);
        org.easymock.EasyMock.expect(ambariConfigurationDAO.reconcileCategory(org.apache.ambari.server.configuration.AmbariServerConfigurationCategory.SSO_CONFIGURATION.getCategoryName(), java.util.Collections.singletonMap(org.apache.ambari.server.configuration.AmbariServerConfigurationKey.SSO_MANAGE_SERVICES.key(), "false"), true)).andReturn(false).once();
        org.easymock.EasyMock.expect(ambariConfigurationDAO.findByCategory(org.apache.ambari.server.configuration.AmbariServerConfigurationCategory.SSO_CONFIGURATION.getCategoryName())).andReturn(entities).once();
        org.apache.ambari.server.events.publishers.AmbariEventPublisher publisher = createMock(org.apache.ambari.server.events.publishers.AmbariEventPublisher.class);
        org.apache.ambari.server.state.Clusters clusters = createMock(org.apache.ambari.server.state.Clusters.class);
        org.apache.ambari.server.state.ConfigHelper configHelper = createMock(org.apache.ambari.server.state.ConfigHelper.class);
        org.apache.ambari.server.controller.AmbariManagementController managementController = createMock(org.apache.ambari.server.controller.AmbariManagementController.class);
        org.apache.ambari.server.api.services.stackadvisor.StackAdvisorHelper stackAdvisorHelper = createMock(org.apache.ambari.server.api.services.stackadvisor.StackAdvisorHelper.class);
        replayAll();
        org.apache.ambari.server.controller.internal.AmbariServerSSOConfigurationHandler handler = new org.apache.ambari.server.controller.internal.AmbariServerSSOConfigurationHandler(clusters, configHelper, managementController, stackAdvisorHelper, ambariConfigurationDAO, publisher);
        handler.updateComponentCategory(org.apache.ambari.server.configuration.AmbariServerConfigurationCategory.SSO_CONFIGURATION.getCategoryName(), ssoConfigurationProperties, true);
        verifyAll();
    }

    @org.junit.Test
    public void testUpdateCategoryAmbariManagingServices() throws org.apache.ambari.server.AmbariException, org.apache.ambari.server.api.services.stackadvisor.StackAdvisorException {
        java.util.Map<java.lang.String, java.lang.String> ssoConfigurationProperties = new java.util.HashMap<>();
        ssoConfigurationProperties.put(org.apache.ambari.server.configuration.AmbariServerConfigurationKey.SSO_MANAGE_SERVICES.key(), "true");
        org.apache.ambari.server.orm.entities.AmbariConfigurationEntity entity = new org.apache.ambari.server.orm.entities.AmbariConfigurationEntity();
        entity.setCategoryName(org.apache.ambari.server.configuration.AmbariServerConfigurationCategory.SSO_CONFIGURATION.getCategoryName());
        entity.setPropertyName(org.apache.ambari.server.configuration.AmbariServerConfigurationKey.SSO_MANAGE_SERVICES.key());
        entity.setPropertyValue("true");
        java.util.List<org.apache.ambari.server.orm.entities.AmbariConfigurationEntity> entities = java.util.Collections.singletonList(entity);
        org.apache.ambari.server.state.StackId stackId = new org.apache.ambari.server.state.StackId("HDP-3.0");
        java.util.Map<java.lang.String, java.util.Set<java.lang.String>> serviceComponentHostMap = java.util.Collections.singletonMap("ATLAS_COMPONENT", java.util.Collections.singleton("host1"));
        org.apache.ambari.server.state.Host host = createMock(org.apache.ambari.server.state.Host.class);
        org.easymock.EasyMock.expect(host.getHostName()).andReturn("host1").once();
        org.apache.ambari.server.state.Service service = createMock(org.apache.ambari.server.state.Service.class);
        org.easymock.EasyMock.expect(service.getName()).andReturn("SERVICE1").once();
        java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.lang.String>> tags = java.util.Collections.emptyMap();
        java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.lang.String>> existing_configurations = java.util.Collections.singletonMap("SERVICE1", java.util.Collections.singletonMap("service1-property1", "service1-value1"));
        org.apache.ambari.server.state.ValueAttributesInfo nonSSOProperty1Attributes = new org.apache.ambari.server.state.ValueAttributesInfo();
        nonSSOProperty1Attributes.setDelete("true");
        org.apache.ambari.server.api.services.stackadvisor.recommendations.RecommendationResponse.BlueprintConfigurations blueprintConfigurations = new org.apache.ambari.server.api.services.stackadvisor.recommendations.RecommendationResponse.BlueprintConfigurations();
        blueprintConfigurations.setProperties(java.util.Collections.singletonMap("service1-sso-property1", "service1-sso-value1"));
        blueprintConfigurations.setPropertyAttributes(java.util.Collections.singletonMap("service1-nonsso-property1", nonSSOProperty1Attributes));
        org.apache.ambari.server.api.services.stackadvisor.recommendations.RecommendationResponse.Blueprint blueprint = new org.apache.ambari.server.api.services.stackadvisor.recommendations.RecommendationResponse.Blueprint();
        blueprint.setConfigurations(java.util.Collections.singletonMap("service-site", blueprintConfigurations));
        org.apache.ambari.server.api.services.stackadvisor.recommendations.RecommendationResponse.Recommendation recommendations = new org.apache.ambari.server.api.services.stackadvisor.recommendations.RecommendationResponse.Recommendation();
        recommendations.setBlueprint(blueprint);
        org.apache.ambari.server.api.services.stackadvisor.recommendations.RecommendationResponse recommendationResponse = new org.apache.ambari.server.api.services.stackadvisor.recommendations.RecommendationResponse();
        recommendationResponse.setRecommendations(recommendations);
        org.easymock.Capture<org.apache.ambari.server.events.AmbariEvent> capturedEvent = org.easymock.EasyMock.newCapture();
        org.easymock.Capture<org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRequest> capturedRequest = org.easymock.EasyMock.newCapture();
        org.easymock.Capture<java.util.Map<java.lang.String, java.lang.String>> capturedUpdates = org.easymock.EasyMock.newCapture();
        org.easymock.Capture<java.util.Collection<java.lang.String>> capturedRemovals = org.easymock.EasyMock.newCapture();
        org.apache.ambari.server.controller.AmbariManagementController managementController = createMock(org.apache.ambari.server.controller.AmbariManagementController.class);
        org.apache.ambari.server.orm.dao.AmbariConfigurationDAO ambariConfigurationDAO = createMock(org.apache.ambari.server.orm.dao.AmbariConfigurationDAO.class);
        org.easymock.EasyMock.expect(ambariConfigurationDAO.reconcileCategory(org.apache.ambari.server.configuration.AmbariServerConfigurationCategory.SSO_CONFIGURATION.getCategoryName(), java.util.Collections.singletonMap(org.apache.ambari.server.configuration.AmbariServerConfigurationKey.SSO_MANAGE_SERVICES.key(), "true"), true)).andReturn(true).once();
        org.easymock.EasyMock.expect(ambariConfigurationDAO.findByCategory(org.apache.ambari.server.configuration.AmbariServerConfigurationCategory.SSO_CONFIGURATION.getCategoryName())).andReturn(entities).once();
        org.apache.ambari.server.events.publishers.AmbariEventPublisher publisher = createMock(org.apache.ambari.server.events.publishers.AmbariEventPublisher.class);
        publisher.publish(org.easymock.EasyMock.capture(capturedEvent));
        org.easymock.EasyMock.expectLastCall().once();
        org.apache.ambari.server.state.Cluster cluster = createMock(org.apache.ambari.server.state.Cluster.class);
        org.easymock.EasyMock.expect(cluster.getClusterName()).andReturn("c1").once();
        org.easymock.EasyMock.expect(cluster.getCurrentStackVersion()).andReturn(stackId).anyTimes();
        org.easymock.EasyMock.expect(cluster.getHosts()).andReturn(java.util.Collections.singleton(host)).once();
        org.easymock.EasyMock.expect(cluster.getServices()).andReturn(java.util.Collections.singletonMap("SERVICE1", service)).once();
        org.easymock.EasyMock.expect(cluster.getServiceComponentHostMap(null, null)).andReturn(serviceComponentHostMap).once();
        org.apache.ambari.server.state.ConfigHelper configHelper = createMock(org.apache.ambari.server.state.ConfigHelper.class);
        org.easymock.EasyMock.expect(configHelper.getEffectiveDesiredTags(cluster, null)).andReturn(tags).once();
        org.easymock.EasyMock.expect(configHelper.getEffectiveConfigProperties(cluster, tags)).andReturn(existing_configurations).once();
        configHelper.updateConfigType(org.easymock.EasyMock.eq(cluster), org.easymock.EasyMock.eq(stackId), org.easymock.EasyMock.eq(managementController), org.easymock.EasyMock.eq("service-site"), org.easymock.EasyMock.capture(capturedUpdates), org.easymock.EasyMock.capture(capturedRemovals), org.easymock.EasyMock.eq("internal"), org.easymock.EasyMock.eq("Ambari-managed single sign-on configurations"));
        org.easymock.EasyMock.expectLastCall().once();
        org.apache.ambari.server.state.Clusters clusters = createMock(org.apache.ambari.server.state.Clusters.class);
        org.easymock.EasyMock.expect(clusters.getClusters()).andReturn(java.util.Collections.singletonMap("c1", cluster)).once();
        org.apache.ambari.server.api.services.stackadvisor.StackAdvisorHelper stackAdvisorHelper = createMock(org.apache.ambari.server.api.services.stackadvisor.StackAdvisorHelper.class);
        org.easymock.EasyMock.expect(stackAdvisorHelper.recommend(org.easymock.EasyMock.capture(capturedRequest))).andReturn(recommendationResponse).once();
        replayAll();
        org.apache.ambari.server.controller.internal.AmbariServerSSOConfigurationHandler handler = new org.apache.ambari.server.controller.internal.AmbariServerSSOConfigurationHandler(clusters, configHelper, managementController, stackAdvisorHelper, ambariConfigurationDAO, publisher);
        handler.updateComponentCategory(org.apache.ambari.server.configuration.AmbariServerConfigurationCategory.SSO_CONFIGURATION.getCategoryName(), ssoConfigurationProperties, true);
        verifyAll();
        junit.framework.Assert.assertTrue(capturedEvent.hasCaptured());
        junit.framework.Assert.assertEquals(org.apache.ambari.server.events.AmbariEvent.AmbariEventType.AMBARI_CONFIGURATION_CHANGED, capturedEvent.getValue().getType());
        junit.framework.Assert.assertEquals(org.apache.ambari.server.configuration.AmbariServerConfigurationCategory.SSO_CONFIGURATION.getCategoryName(), ((org.apache.ambari.server.events.AmbariConfigurationChangedEvent) (capturedEvent.getValue())).getCategoryName());
        junit.framework.Assert.assertTrue(capturedUpdates.hasCaptured());
        junit.framework.Assert.assertTrue(capturedUpdates.getValue().containsKey("service1-sso-property1"));
        junit.framework.Assert.assertEquals("service1-sso-value1", capturedUpdates.getValue().get("service1-sso-property1"));
        junit.framework.Assert.assertTrue(capturedRemovals.hasCaptured());
        junit.framework.Assert.assertTrue(capturedRemovals.getValue().contains("service1-nonsso-property1"));
        junit.framework.Assert.assertTrue(capturedRequest.hasCaptured());
    }

    @org.junit.Test
    public void testCheckingIfSSOIsEnabledPerEachService() {
        org.apache.ambari.server.state.Clusters clusters = createMock(org.apache.ambari.server.state.Clusters.class);
        org.apache.ambari.server.state.ConfigHelper configHelper = createMock(org.apache.ambari.server.state.ConfigHelper.class);
        org.apache.ambari.server.controller.AmbariManagementController managementController = createMock(org.apache.ambari.server.controller.AmbariManagementController.class);
        org.apache.ambari.server.api.services.stackadvisor.StackAdvisorHelper stackAdvisorHelper = createMock(org.apache.ambari.server.api.services.stackadvisor.StackAdvisorHelper.class);
        org.apache.ambari.server.events.publishers.AmbariEventPublisher publisher = createMock(org.apache.ambari.server.events.publishers.AmbariEventPublisher.class);
        java.util.List<org.apache.ambari.server.orm.entities.AmbariConfigurationEntity> entities = new java.util.ArrayList<>();
        org.apache.ambari.server.orm.entities.AmbariConfigurationEntity entity;
        entity = new org.apache.ambari.server.orm.entities.AmbariConfigurationEntity();
        entity.setCategoryName(org.apache.ambari.server.configuration.AmbariServerConfigurationCategory.SSO_CONFIGURATION.getCategoryName());
        entity.setPropertyName(org.apache.ambari.server.configuration.AmbariServerConfigurationKey.SSO_MANAGE_SERVICES.key());
        entity.setPropertyValue("true");
        entities.add(entity);
        entity = new org.apache.ambari.server.orm.entities.AmbariConfigurationEntity();
        entity.setCategoryName(org.apache.ambari.server.configuration.AmbariServerConfigurationCategory.SSO_CONFIGURATION.getCategoryName());
        entity.setPropertyName(org.apache.ambari.server.configuration.AmbariServerConfigurationKey.SSO_ENABLED_SERVICES.key());
        entity.setPropertyValue("SERVICE1,SERVICE2");
        entities.add(entity);
        org.apache.ambari.server.orm.dao.AmbariConfigurationDAO ambariConfigurationDAO = createMock(org.apache.ambari.server.orm.dao.AmbariConfigurationDAO.class);
        org.easymock.EasyMock.expect(ambariConfigurationDAO.findByCategory(org.apache.ambari.server.configuration.AmbariServerConfigurationCategory.SSO_CONFIGURATION.getCategoryName())).andReturn(entities).anyTimes();
        replayAll();
        org.apache.ambari.server.controller.internal.AmbariServerSSOConfigurationHandler handler = new org.apache.ambari.server.controller.internal.AmbariServerSSOConfigurationHandler(clusters, configHelper, managementController, stackAdvisorHelper, ambariConfigurationDAO, publisher);
        junit.framework.Assert.assertTrue(handler.getSSOEnabledServices().contains("SERVICE1"));
        junit.framework.Assert.assertTrue(handler.getSSOEnabledServices().contains("SERVICE2"));
        junit.framework.Assert.assertFalse(handler.getSSOEnabledServices().contains("SERVICE3"));
        verifyAll();
    }
}
