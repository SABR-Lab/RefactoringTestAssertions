package org.apache.ambari.server.controller.internal;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
public class ValidationResourceProviderTest {
    @org.junit.Test
    public void testCreateResources_checkRequestId() throws java.lang.Exception {
        java.util.Map<org.apache.ambari.server.controller.spi.Resource.Type, java.lang.String> keyPropertyIds = java.util.Collections.emptyMap();
        java.util.Set<java.lang.String> propertyIds = java.util.Collections.singleton(org.apache.ambari.server.controller.internal.ValidationResourceProvider.VALIDATION_ID_PROPERTY_ID);
        org.apache.ambari.server.controller.AmbariManagementController ambariManagementController = org.mockito.Mockito.mock(org.apache.ambari.server.controller.AmbariManagementController.class);
        org.apache.ambari.server.controller.internal.ValidationResourceProvider provider = org.mockito.Mockito.spy(new org.apache.ambari.server.controller.internal.ValidationResourceProvider(ambariManagementController));
        org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRequest stackAdvisorRequest = org.mockito.Mockito.mock(org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRequest.class);
        org.apache.ambari.server.controller.spi.Request request = org.mockito.Mockito.mock(org.apache.ambari.server.controller.spi.Request.class);
        org.mockito.Mockito.doReturn(stackAdvisorRequest).when(provider).prepareStackAdvisorRequest(request);
        org.apache.ambari.server.api.services.stackadvisor.StackAdvisorHelper saHelper = org.mockito.Mockito.mock(org.apache.ambari.server.api.services.stackadvisor.StackAdvisorHelper.class);
        org.apache.ambari.server.configuration.Configuration configuration = org.mockito.Mockito.mock(org.apache.ambari.server.configuration.Configuration.class);
        org.apache.ambari.server.api.services.stackadvisor.validations.ValidationResponse response = org.mockito.Mockito.mock(org.apache.ambari.server.api.services.stackadvisor.validations.ValidationResponse.class);
        org.apache.ambari.server.api.services.stackadvisor.StackAdvisorResponse.Version version = org.mockito.Mockito.mock(org.apache.ambari.server.api.services.stackadvisor.StackAdvisorResponse.Version.class);
        org.mockito.Mockito.doReturn(3).when(response).getId();
        org.mockito.Mockito.doReturn(version).when(response).getVersion();
        org.mockito.Mockito.doReturn(response).when(saHelper).validate(org.mockito.Matchers.any(org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRequest.class));
        org.apache.ambari.server.controller.internal.ValidationResourceProvider.init(saHelper, configuration, org.mockito.Mockito.mock(org.apache.ambari.server.state.Clusters.class), org.mockito.Mockito.mock(org.apache.ambari.server.api.services.AmbariMetaInfo.class));
        org.apache.ambari.server.controller.spi.RequestStatus status = provider.createResources(request);
        java.util.Set<org.apache.ambari.server.controller.spi.Resource> associatedResources = status.getAssociatedResources();
        org.junit.Assert.assertNotNull(associatedResources);
        org.junit.Assert.assertEquals(1, associatedResources.size());
        org.apache.ambari.server.controller.spi.Resource resource = associatedResources.iterator().next();
        java.lang.Object requestId = resource.getPropertyValue(org.apache.ambari.server.controller.internal.ValidationResourceProvider.VALIDATION_ID_PROPERTY_ID);
        org.junit.Assert.assertNotNull(requestId);
        org.junit.Assert.assertEquals(3, requestId);
    }
}
