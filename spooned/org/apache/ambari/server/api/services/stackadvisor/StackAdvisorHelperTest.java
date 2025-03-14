package org.apache.ambari.server.api.services.stackadvisor;
import org.mockito.Mockito;
import static org.easymock.EasyMock.anyString;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.partialMockBuilder;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
public class StackAdvisorHelperTest {
    @org.junit.Test
    @java.lang.SuppressWarnings("unchecked")
    public void testValidate_returnsCommandResult() throws org.apache.ambari.server.api.services.stackadvisor.StackAdvisorException, java.io.IOException {
        org.apache.ambari.server.configuration.Configuration configuration = org.mockito.Mockito.mock(org.apache.ambari.server.configuration.Configuration.class);
        org.mockito.Mockito.when(configuration.getRecommendationsArtifactsRolloverMax()).thenReturn(100);
        org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRunner saRunner = org.mockito.Mockito.mock(org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRunner.class);
        org.apache.ambari.server.api.services.AmbariMetaInfo metaInfo = org.mockito.Mockito.mock(org.apache.ambari.server.api.services.AmbariMetaInfo.class);
        org.apache.ambari.server.state.ServiceInfo service = org.mockito.Mockito.mock(org.apache.ambari.server.state.ServiceInfo.class);
        org.mockito.Mockito.when(metaInfo.getService(org.mockito.Mockito.anyString(), org.mockito.Mockito.anyString(), org.mockito.Mockito.anyString())).thenReturn(service);
        org.mockito.Mockito.when(service.getServiceAdvisorType()).thenReturn(org.apache.ambari.server.state.ServiceInfo.ServiceAdvisorType.PYTHON);
        org.apache.ambari.server.api.services.stackadvisor.StackAdvisorHelper helper = org.apache.ambari.server.api.services.stackadvisor.StackAdvisorHelperTest.stackAdvisorHelperSpy(configuration, saRunner, metaInfo);
        org.apache.ambari.server.api.services.stackadvisor.commands.StackAdvisorCommand<org.apache.ambari.server.api.services.stackadvisor.validations.ValidationResponse> command = org.mockito.Mockito.mock(org.apache.ambari.server.api.services.stackadvisor.commands.StackAdvisorCommand.class);
        org.apache.ambari.server.api.services.stackadvisor.validations.ValidationResponse expected = org.mockito.Mockito.mock(org.apache.ambari.server.api.services.stackadvisor.validations.ValidationResponse.class);
        org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRequest.StackAdvisorRequestType requestType = org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRequest.StackAdvisorRequestType.HOST_GROUPS;
        org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRequest request = org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRequest.StackAdvisorRequestBuilder.forStack("stackName", "stackVersion").ofType(requestType).build();
        org.mockito.Mockito.when(command.invoke(request, org.apache.ambari.server.state.ServiceInfo.ServiceAdvisorType.PYTHON)).thenReturn(expected);
        org.mockito.Mockito.doReturn(command).when(helper).createValidationCommand("ZOOKEEPER", request);
        org.apache.ambari.server.api.services.stackadvisor.validations.ValidationResponse response = helper.validate(request);
        org.junit.Assert.assertEquals(expected, response);
    }

    @org.junit.Test(expected = org.apache.ambari.server.api.services.stackadvisor.StackAdvisorException.class)
    @java.lang.SuppressWarnings("unchecked")
    public void testValidate_commandThrowsException_throwsException() throws org.apache.ambari.server.api.services.stackadvisor.StackAdvisorException, java.io.IOException {
        org.apache.ambari.server.configuration.Configuration configuration = org.mockito.Mockito.mock(org.apache.ambari.server.configuration.Configuration.class);
        org.mockito.Mockito.when(configuration.getRecommendationsArtifactsRolloverMax()).thenReturn(100);
        org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRunner saRunner = org.mockito.Mockito.mock(org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRunner.class);
        org.apache.ambari.server.api.services.AmbariMetaInfo metaInfo = org.mockito.Mockito.mock(org.apache.ambari.server.api.services.AmbariMetaInfo.class);
        org.apache.ambari.server.state.ServiceInfo service = org.mockito.Mockito.mock(org.apache.ambari.server.state.ServiceInfo.class);
        org.mockito.Mockito.when(metaInfo.getService(org.mockito.Mockito.anyString(), org.mockito.Mockito.anyString(), org.mockito.Mockito.anyString())).thenReturn(service);
        org.mockito.Mockito.when(service.getServiceAdvisorType()).thenReturn(org.apache.ambari.server.state.ServiceInfo.ServiceAdvisorType.PYTHON);
        org.apache.ambari.server.api.services.stackadvisor.StackAdvisorHelper helper = org.apache.ambari.server.api.services.stackadvisor.StackAdvisorHelperTest.stackAdvisorHelperSpy(configuration, saRunner, metaInfo);
        org.apache.ambari.server.api.services.stackadvisor.commands.StackAdvisorCommand<org.apache.ambari.server.api.services.stackadvisor.validations.ValidationResponse> command = org.mockito.Mockito.mock(org.apache.ambari.server.api.services.stackadvisor.commands.StackAdvisorCommand.class);
        org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRequest.StackAdvisorRequestType requestType = org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRequest.StackAdvisorRequestType.HOST_GROUPS;
        org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRequest request = org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRequest.StackAdvisorRequestBuilder.forStack("stackName", "stackVersion").ofType(requestType).build();
        org.mockito.Mockito.when(command.invoke(request, org.apache.ambari.server.state.ServiceInfo.ServiceAdvisorType.PYTHON)).thenThrow(new org.apache.ambari.server.api.services.stackadvisor.StackAdvisorException("message"));
        org.mockito.Mockito.doReturn(command).when(helper).createValidationCommand("ZOOKEEPER", request);
        helper.validate(request);
        org.junit.Assert.fail();
    }

    @org.junit.Test
    @java.lang.SuppressWarnings("unchecked")
    public void testRecommend_returnsCommandResult() throws org.apache.ambari.server.api.services.stackadvisor.StackAdvisorException, java.io.IOException {
        org.apache.ambari.server.configuration.Configuration configuration = org.mockito.Mockito.mock(org.apache.ambari.server.configuration.Configuration.class);
        org.mockito.Mockito.when(configuration.getRecommendationsArtifactsRolloverMax()).thenReturn(100);
        org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRunner saRunner = org.mockito.Mockito.mock(org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRunner.class);
        org.apache.ambari.server.api.services.AmbariMetaInfo metaInfo = org.mockito.Mockito.mock(org.apache.ambari.server.api.services.AmbariMetaInfo.class);
        org.apache.ambari.server.state.ServiceInfo service = org.mockito.Mockito.mock(org.apache.ambari.server.state.ServiceInfo.class);
        org.mockito.Mockito.when(metaInfo.getService(org.mockito.Mockito.anyString(), org.mockito.Mockito.anyString(), org.mockito.Mockito.anyString())).thenReturn(service);
        org.mockito.Mockito.when(service.getServiceAdvisorType()).thenReturn(org.apache.ambari.server.state.ServiceInfo.ServiceAdvisorType.PYTHON);
        org.apache.ambari.server.api.services.stackadvisor.StackAdvisorHelper helper = org.apache.ambari.server.api.services.stackadvisor.StackAdvisorHelperTest.stackAdvisorHelperSpy(configuration, saRunner, metaInfo);
        org.apache.ambari.server.api.services.stackadvisor.commands.StackAdvisorCommand<org.apache.ambari.server.api.services.stackadvisor.recommendations.RecommendationResponse> command = org.mockito.Mockito.mock(org.apache.ambari.server.api.services.stackadvisor.commands.StackAdvisorCommand.class);
        org.apache.ambari.server.api.services.stackadvisor.recommendations.RecommendationResponse expected = org.mockito.Mockito.mock(org.apache.ambari.server.api.services.stackadvisor.recommendations.RecommendationResponse.class);
        org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRequest.StackAdvisorRequestType requestType = org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRequest.StackAdvisorRequestType.HOST_GROUPS;
        org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRequest request = org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRequest.StackAdvisorRequestBuilder.forStack("stackName", "stackVersion").ofType(requestType).build();
        org.mockito.Mockito.when(command.invoke(request, org.apache.ambari.server.state.ServiceInfo.ServiceAdvisorType.PYTHON)).thenReturn(expected);
        org.mockito.Mockito.doReturn(command).when(helper).createRecommendationCommand("ZOOKEEPER", request);
        org.apache.ambari.server.api.services.stackadvisor.recommendations.RecommendationResponse response = helper.recommend(request);
        org.junit.Assert.assertEquals(expected, response);
    }

    @org.junit.Test(expected = org.apache.ambari.server.api.services.stackadvisor.StackAdvisorException.class)
    @java.lang.SuppressWarnings("unchecked")
    public void testRecommend_commandThrowsException_throwsException() throws org.apache.ambari.server.api.services.stackadvisor.StackAdvisorException, java.io.IOException {
        org.apache.ambari.server.configuration.Configuration configuration = org.mockito.Mockito.mock(org.apache.ambari.server.configuration.Configuration.class);
        org.mockito.Mockito.when(configuration.getRecommendationsArtifactsRolloverMax()).thenReturn(100);
        org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRunner saRunner = org.mockito.Mockito.mock(org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRunner.class);
        org.apache.ambari.server.api.services.AmbariMetaInfo metaInfo = org.mockito.Mockito.mock(org.apache.ambari.server.api.services.AmbariMetaInfo.class);
        org.apache.ambari.server.state.ServiceInfo service = org.mockito.Mockito.mock(org.apache.ambari.server.state.ServiceInfo.class);
        org.mockito.Mockito.when(metaInfo.getService(org.mockito.Mockito.anyString(), org.mockito.Mockito.anyString(), org.mockito.Mockito.anyString())).thenReturn(service);
        org.mockito.Mockito.when(service.getServiceAdvisorType()).thenReturn(org.apache.ambari.server.state.ServiceInfo.ServiceAdvisorType.PYTHON);
        org.apache.ambari.server.api.services.stackadvisor.StackAdvisorHelper helper = org.apache.ambari.server.api.services.stackadvisor.StackAdvisorHelperTest.stackAdvisorHelperSpy(configuration, saRunner, metaInfo);
        org.apache.ambari.server.api.services.stackadvisor.commands.StackAdvisorCommand<org.apache.ambari.server.api.services.stackadvisor.recommendations.RecommendationResponse> command = org.mockito.Mockito.mock(org.apache.ambari.server.api.services.stackadvisor.commands.StackAdvisorCommand.class);
        org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRequest.StackAdvisorRequestType requestType = org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRequest.StackAdvisorRequestType.HOST_GROUPS;
        org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRequest request = org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRequest.StackAdvisorRequestBuilder.forStack("stackName", "stackVersion").ofType(requestType).build();
        org.mockito.Mockito.when(command.invoke(request, org.apache.ambari.server.state.ServiceInfo.ServiceAdvisorType.PYTHON)).thenThrow(new org.apache.ambari.server.api.services.stackadvisor.StackAdvisorException("message"));
        org.mockito.Mockito.doReturn(command).when(helper).createRecommendationCommand("ZOOKEEPER", request);
        helper.recommend(request);
        org.junit.Assert.fail("Expected StackAdvisorException to be thrown");
    }

    @org.junit.Test
    public void testCreateRecommendationCommand_returnsComponentLayoutRecommendationCommand() throws java.io.IOException, org.apache.ambari.server.api.services.stackadvisor.StackAdvisorException {
        org.apache.ambari.server.configuration.Configuration configuration = org.mockito.Mockito.mock(org.apache.ambari.server.configuration.Configuration.class);
        org.mockito.Mockito.when(configuration.getRecommendationsArtifactsRolloverMax()).thenReturn(100);
        org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRunner saRunner = org.mockito.Mockito.mock(org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRunner.class);
        org.apache.ambari.server.api.services.AmbariMetaInfo metaInfo = org.mockito.Mockito.mock(org.apache.ambari.server.api.services.AmbariMetaInfo.class);
        org.apache.ambari.server.state.ServiceInfo service = org.mockito.Mockito.mock(org.apache.ambari.server.state.ServiceInfo.class);
        org.mockito.Mockito.when(metaInfo.getService(org.mockito.Mockito.anyString(), org.mockito.Mockito.anyString(), org.mockito.Mockito.anyString())).thenReturn(service);
        org.mockito.Mockito.when(service.getServiceAdvisorType()).thenReturn(org.apache.ambari.server.state.ServiceInfo.ServiceAdvisorType.PYTHON);
        org.apache.ambari.server.api.services.stackadvisor.StackAdvisorHelper helper = new org.apache.ambari.server.api.services.stackadvisor.StackAdvisorHelper(configuration, saRunner, metaInfo, null, null);
        org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRequest.StackAdvisorRequestType requestType = org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRequest.StackAdvisorRequestType.HOST_GROUPS;
        org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRequest request = org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRequest.StackAdvisorRequestBuilder.forStack("stackName", "stackVersion").ofType(requestType).build();
        org.apache.ambari.server.api.services.stackadvisor.commands.StackAdvisorCommand<org.apache.ambari.server.api.services.stackadvisor.recommendations.RecommendationResponse> command = helper.createRecommendationCommand("ZOOKEEPER", request);
        org.junit.Assert.assertEquals(org.apache.ambari.server.api.services.stackadvisor.commands.ComponentLayoutRecommendationCommand.class, command.getClass());
    }

    @org.junit.Test
    public void testCreateRecommendationCommand_returnsConfigurationRecommendationCommand() throws java.io.IOException, org.apache.ambari.server.api.services.stackadvisor.StackAdvisorException {
        testCreateConfigurationRecommendationCommand(org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRequest.StackAdvisorRequestType.CONFIGURATIONS, org.apache.ambari.server.api.services.stackadvisor.commands.StackAdvisorCommandType.RECOMMEND_CONFIGURATIONS);
    }

    @org.junit.Test
    public void testCreateRecommendationCommand_returnsSingleSignOnConfigurationRecommendationCommand() throws java.io.IOException, org.apache.ambari.server.api.services.stackadvisor.StackAdvisorException {
        testCreateConfigurationRecommendationCommand(org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRequest.StackAdvisorRequestType.SSO_CONFIGURATIONS, org.apache.ambari.server.api.services.stackadvisor.commands.StackAdvisorCommandType.RECOMMEND_CONFIGURATIONS_FOR_SSO);
    }

    @org.junit.Test
    public void testCreateRecommendationCommand_returnsLDAPConfigurationRecommendationCommand() throws java.io.IOException, org.apache.ambari.server.api.services.stackadvisor.StackAdvisorException {
        testCreateConfigurationRecommendationCommand(org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRequest.StackAdvisorRequestType.LDAP_CONFIGURATIONS, org.apache.ambari.server.api.services.stackadvisor.commands.StackAdvisorCommandType.RECOMMEND_CONFIGURATIONS_FOR_LDAP);
    }

    @org.junit.Test
    public void testCreateRecommendationCommand_returnsKerberosConfigurationRecommendationCommand() throws java.io.IOException, org.apache.ambari.server.api.services.stackadvisor.StackAdvisorException {
        testCreateConfigurationRecommendationCommand(org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRequest.StackAdvisorRequestType.KERBEROS_CONFIGURATIONS, org.apache.ambari.server.api.services.stackadvisor.commands.StackAdvisorCommandType.RECOMMEND_CONFIGURATIONS_FOR_KERBEROS);
    }

    @org.junit.Test
    public void testCreateValidationCommand_returnsComponentLayoutValidationCommand() throws java.io.IOException, org.apache.ambari.server.api.services.stackadvisor.StackAdvisorException {
        org.apache.ambari.server.configuration.Configuration configuration = org.mockito.Mockito.mock(org.apache.ambari.server.configuration.Configuration.class);
        org.mockito.Mockito.when(configuration.getRecommendationsArtifactsRolloverMax()).thenReturn(100);
        org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRunner saRunner = org.mockito.Mockito.mock(org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRunner.class);
        org.apache.ambari.server.api.services.AmbariMetaInfo metaInfo = org.mockito.Mockito.mock(org.apache.ambari.server.api.services.AmbariMetaInfo.class);
        org.apache.ambari.server.state.ServiceInfo service = org.mockito.Mockito.mock(org.apache.ambari.server.state.ServiceInfo.class);
        org.mockito.Mockito.when(metaInfo.getService(org.mockito.Mockito.anyString(), org.mockito.Mockito.anyString(), org.mockito.Mockito.anyString())).thenReturn(service);
        org.mockito.Mockito.when(service.getServiceAdvisorType()).thenReturn(org.apache.ambari.server.state.ServiceInfo.ServiceAdvisorType.PYTHON);
        org.apache.ambari.server.api.services.stackadvisor.StackAdvisorHelper helper = new org.apache.ambari.server.api.services.stackadvisor.StackAdvisorHelper(configuration, saRunner, metaInfo, null, null);
        org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRequest.StackAdvisorRequestType requestType = org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRequest.StackAdvisorRequestType.HOST_GROUPS;
        org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRequest request = org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRequest.StackAdvisorRequestBuilder.forStack("stackName", "stackVersion").ofType(requestType).build();
        org.apache.ambari.server.api.services.stackadvisor.commands.StackAdvisorCommand<org.apache.ambari.server.api.services.stackadvisor.validations.ValidationResponse> command = helper.createValidationCommand("ZOOKEEPER", request);
        org.junit.Assert.assertEquals(org.apache.ambari.server.api.services.stackadvisor.commands.ComponentLayoutValidationCommand.class, command.getClass());
    }

    @org.junit.Test
    public void testCreateValidationCommand_returnsConfigurationValidationCommand() throws java.io.IOException, org.apache.ambari.server.api.services.stackadvisor.StackAdvisorException {
        org.apache.ambari.server.configuration.Configuration configuration = org.mockito.Mockito.mock(org.apache.ambari.server.configuration.Configuration.class);
        org.mockito.Mockito.when(configuration.getRecommendationsArtifactsRolloverMax()).thenReturn(100);
        org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRunner saRunner = org.mockito.Mockito.mock(org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRunner.class);
        org.apache.ambari.server.api.services.AmbariMetaInfo metaInfo = org.mockito.Mockito.mock(org.apache.ambari.server.api.services.AmbariMetaInfo.class);
        org.apache.ambari.server.state.ServiceInfo service = org.mockito.Mockito.mock(org.apache.ambari.server.state.ServiceInfo.class);
        org.mockito.Mockito.when(metaInfo.getService(org.mockito.Mockito.anyString(), org.mockito.Mockito.anyString(), org.mockito.Mockito.anyString())).thenReturn(service);
        org.mockito.Mockito.when(service.getServiceAdvisorType()).thenReturn(org.apache.ambari.server.state.ServiceInfo.ServiceAdvisorType.PYTHON);
        org.apache.ambari.server.api.services.stackadvisor.StackAdvisorHelper helper = new org.apache.ambari.server.api.services.stackadvisor.StackAdvisorHelper(configuration, saRunner, metaInfo, null, null);
        org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRequest.StackAdvisorRequestType requestType = org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRequest.StackAdvisorRequestType.CONFIGURATIONS;
        org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRequest request = org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRequest.StackAdvisorRequestBuilder.forStack("stackName", "stackVersion").ofType(requestType).build();
        org.apache.ambari.server.api.services.stackadvisor.commands.StackAdvisorCommand<org.apache.ambari.server.api.services.stackadvisor.validations.ValidationResponse> command = helper.createValidationCommand("ZOOKEEPER", request);
        org.junit.Assert.assertEquals(org.apache.ambari.server.api.services.stackadvisor.commands.ConfigurationValidationCommand.class, command.getClass());
    }

    @org.junit.Test
    public void testCreateRecommendationDependencyCommand_returnsConfigurationDependencyRecommendationCommand() throws java.io.IOException, org.apache.ambari.server.api.services.stackadvisor.StackAdvisorException {
        org.apache.ambari.server.configuration.Configuration configuration = org.mockito.Mockito.mock(org.apache.ambari.server.configuration.Configuration.class);
        org.mockito.Mockito.when(configuration.getRecommendationsArtifactsRolloverMax()).thenReturn(100);
        org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRunner saRunner = org.mockito.Mockito.mock(org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRunner.class);
        org.apache.ambari.server.api.services.AmbariMetaInfo metaInfo = org.mockito.Mockito.mock(org.apache.ambari.server.api.services.AmbariMetaInfo.class);
        org.apache.ambari.server.state.ServiceInfo service = org.mockito.Mockito.mock(org.apache.ambari.server.state.ServiceInfo.class);
        org.mockito.Mockito.when(metaInfo.getService(org.mockito.Mockito.anyString(), org.mockito.Mockito.anyString(), org.mockito.Mockito.anyString())).thenReturn(service);
        org.mockito.Mockito.when(service.getServiceAdvisorType()).thenReturn(org.apache.ambari.server.state.ServiceInfo.ServiceAdvisorType.PYTHON);
        org.apache.ambari.server.api.services.stackadvisor.StackAdvisorHelper helper = new org.apache.ambari.server.api.services.stackadvisor.StackAdvisorHelper(configuration, saRunner, metaInfo, null, null);
        org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRequest.StackAdvisorRequestType requestType = org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRequest.StackAdvisorRequestType.CONFIGURATION_DEPENDENCIES;
        org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRequest request = org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRequest.StackAdvisorRequestBuilder.forStack("stackName", "stackVersion").ofType(requestType).build();
        org.apache.ambari.server.api.services.stackadvisor.commands.StackAdvisorCommand<org.apache.ambari.server.api.services.stackadvisor.recommendations.RecommendationResponse> command = helper.createRecommendationCommand("ZOOKEEPER", request);
        org.junit.Assert.assertEquals(org.apache.ambari.server.api.services.stackadvisor.commands.ConfigurationDependenciesRecommendationCommand.class, command.getClass());
    }

    @org.junit.Test
    public void testClearCacheAndHost() throws java.io.IOException, java.lang.NoSuchFieldException, java.lang.IllegalAccessException {
        java.lang.reflect.Field hostInfoCacheField = org.apache.ambari.server.api.services.stackadvisor.StackAdvisorHelper.class.getDeclaredField("hostInfoCache");
        java.lang.reflect.Field configsRecommendationResponseField = org.apache.ambari.server.api.services.stackadvisor.StackAdvisorHelper.class.getDeclaredField("configsRecommendationResponse");
        org.apache.ambari.server.api.services.stackadvisor.StackAdvisorHelper helper = testClearCachesSetup(hostInfoCacheField, configsRecommendationResponseField);
        helper.clearCaches("hostName1");
        java.util.Map<java.lang.String, com.fasterxml.jackson.databind.JsonNode> hostInfoCache = ((java.util.Map<java.lang.String, com.fasterxml.jackson.databind.JsonNode>) (hostInfoCacheField.get(helper)));
        java.util.Map<java.lang.String, org.apache.ambari.server.api.services.stackadvisor.recommendations.RecommendationResponse> configsRecommendationResponse = ((java.util.Map<java.lang.String, org.apache.ambari.server.api.services.stackadvisor.recommendations.RecommendationResponse>) (configsRecommendationResponseField.get(helper)));
        org.junit.Assert.assertEquals(2, hostInfoCache.size());
        org.junit.Assert.assertTrue(hostInfoCache.containsKey("hostName2"));
        org.junit.Assert.assertTrue(hostInfoCache.containsKey("hostName3"));
        org.junit.Assert.assertTrue(configsRecommendationResponse.isEmpty());
    }

    @org.junit.Test
    public void testClearCacheAndHosts() throws java.io.IOException, java.lang.NoSuchFieldException, java.lang.IllegalAccessException {
        java.lang.reflect.Field hostInfoCacheField = org.apache.ambari.server.api.services.stackadvisor.StackAdvisorHelper.class.getDeclaredField("hostInfoCache");
        java.lang.reflect.Field configsRecommendationResponseField = org.apache.ambari.server.api.services.stackadvisor.StackAdvisorHelper.class.getDeclaredField("configsRecommendationResponse");
        org.apache.ambari.server.api.services.stackadvisor.StackAdvisorHelper helper = testClearCachesSetup(hostInfoCacheField, configsRecommendationResponseField);
        helper.clearCaches(new java.util.HashSet<>(java.util.Arrays.asList(new java.lang.String[]{ "hostName1", "hostName2" })));
        java.util.Map<java.lang.String, com.fasterxml.jackson.databind.JsonNode> hostInfoCache = ((java.util.Map<java.lang.String, com.fasterxml.jackson.databind.JsonNode>) (hostInfoCacheField.get(helper)));
        java.util.Map<java.lang.String, org.apache.ambari.server.api.services.stackadvisor.recommendations.RecommendationResponse> configsRecommendationResponse = ((java.util.Map<java.lang.String, org.apache.ambari.server.api.services.stackadvisor.recommendations.RecommendationResponse>) (configsRecommendationResponseField.get(helper)));
        org.junit.Assert.assertEquals(1, hostInfoCache.size());
        org.junit.Assert.assertTrue(hostInfoCache.containsKey("hostName3"));
        org.junit.Assert.assertTrue(configsRecommendationResponse.isEmpty());
    }

    @org.junit.Test
    public void testCacheRecommendations() throws java.io.IOException, org.apache.ambari.server.api.services.stackadvisor.StackAdvisorException {
        org.apache.ambari.server.configuration.Configuration configuration = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.configuration.Configuration.class);
        org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRunner stackAdvisorRunner = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRunner.class);
        org.apache.ambari.server.api.services.AmbariMetaInfo ambariMetaInfo = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.services.AmbariMetaInfo.class);
        org.apache.ambari.server.controller.internal.AmbariServerConfigurationHandler ambariServerConfigurationHandler = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.internal.AmbariServerConfigurationHandler.class);
        org.easymock.EasyMock.expect(configuration.getRecommendationsArtifactsRolloverMax()).andReturn(1);
        org.easymock.EasyMock.replay(configuration, stackAdvisorRunner, ambariMetaInfo, ambariServerConfigurationHandler);
        org.apache.ambari.server.api.services.stackadvisor.StackAdvisorHelper helper = org.easymock.EasyMock.partialMockBuilder(org.apache.ambari.server.api.services.stackadvisor.StackAdvisorHelper.class).withConstructor(org.apache.ambari.server.configuration.Configuration.class, org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRunner.class, org.apache.ambari.server.api.services.AmbariMetaInfo.class, org.apache.ambari.server.controller.internal.AmbariServerConfigurationHandler.class, com.google.gson.Gson.class).withArgs(configuration, stackAdvisorRunner, ambariMetaInfo, ambariServerConfigurationHandler, new com.google.gson.Gson()).addMockedMethod("createRecommendationCommand").createMock();
        org.easymock.EasyMock.verify(configuration, stackAdvisorRunner, ambariMetaInfo, ambariServerConfigurationHandler);
        org.easymock.EasyMock.reset(ambariMetaInfo);
        org.apache.ambari.server.state.ServiceInfo serviceInfo = new org.apache.ambari.server.state.ServiceInfo();
        serviceInfo.setServiceAdvisorType(org.apache.ambari.server.state.ServiceInfo.ServiceAdvisorType.PYTHON);
        org.easymock.EasyMock.expect(ambariMetaInfo.getService(org.easymock.EasyMock.anyString(), org.easymock.EasyMock.anyString(), org.easymock.EasyMock.anyString())).andReturn(serviceInfo).atLeastOnce();
        org.apache.ambari.server.api.services.stackadvisor.commands.ConfigurationRecommendationCommand command = org.easymock.EasyMock.createMock(org.apache.ambari.server.api.services.stackadvisor.commands.ConfigurationRecommendationCommand.class);
        org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRequest request = org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRequest.StackAdvisorRequestBuilder.forStack(null, null).ofType(org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRequest.StackAdvisorRequestType.CONFIGURATIONS).build();
        org.easymock.EasyMock.expect(helper.createRecommendationCommand(org.easymock.EasyMock.eq("ZOOKEEPER"), org.easymock.EasyMock.eq(request))).andReturn(command).times(2);
        org.apache.ambari.server.api.services.stackadvisor.recommendations.RecommendationResponse response = new org.apache.ambari.server.api.services.stackadvisor.recommendations.RecommendationResponse();
        response.setServices(new java.util.HashSet<java.lang.String>() {
            {
                add("service1");
                add("service2");
                add("service3");
            }
        });
        org.easymock.EasyMock.expect(command.invoke(org.easymock.EasyMock.eq(request), org.easymock.EasyMock.eq(org.apache.ambari.server.state.ServiceInfo.ServiceAdvisorType.PYTHON))).andReturn(response).once();
        org.easymock.EasyMock.replay(ambariMetaInfo, helper, command);
        org.apache.ambari.server.api.services.stackadvisor.recommendations.RecommendationResponse calculatedResponse = helper.recommend(request);
        org.apache.ambari.server.api.services.stackadvisor.recommendations.RecommendationResponse cachedResponse = helper.recommend(request);
        org.easymock.EasyMock.verify(ambariMetaInfo, helper, command);
        org.junit.Assert.assertEquals(response.getServices(), calculatedResponse.getServices());
        org.junit.Assert.assertEquals(response.getServices(), cachedResponse.getServices());
    }

    private org.apache.ambari.server.api.services.stackadvisor.StackAdvisorHelper testClearCachesSetup(java.lang.reflect.Field hostInfoCacheField, java.lang.reflect.Field configsRecommendationResponseField) throws java.io.IOException, java.lang.NoSuchFieldException, java.lang.IllegalAccessException {
        org.apache.ambari.server.configuration.Configuration configuration = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.configuration.Configuration.class);
        org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRunner stackAdvisorRunner = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRunner.class);
        org.apache.ambari.server.api.services.AmbariMetaInfo ambariMetaInfo = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.services.AmbariMetaInfo.class);
        org.apache.ambari.server.controller.internal.AmbariServerConfigurationHandler ambariServerConfigurationHandler = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.internal.AmbariServerConfigurationHandler.class);
        org.easymock.EasyMock.replay(configuration, stackAdvisorRunner, ambariMetaInfo, ambariServerConfigurationHandler);
        org.apache.ambari.server.api.services.stackadvisor.StackAdvisorHelper helper = new org.apache.ambari.server.api.services.stackadvisor.StackAdvisorHelper(configuration, stackAdvisorRunner, ambariMetaInfo, ambariServerConfigurationHandler, null);
        org.easymock.EasyMock.verify(configuration, stackAdvisorRunner, ambariMetaInfo, ambariServerConfigurationHandler);
        hostInfoCacheField.setAccessible(true);
        configsRecommendationResponseField.setAccessible(true);
        java.util.Map<java.lang.String, com.fasterxml.jackson.databind.JsonNode> hostInfoCache = new java.util.concurrent.ConcurrentHashMap<>();
        com.fasterxml.jackson.databind.node.JsonNodeFactory jnf = com.fasterxml.jackson.databind.node.JsonNodeFactory.instance;
        hostInfoCache.put("hostName1", jnf.nullNode());
        hostInfoCache.put("hostName2", jnf.nullNode());
        hostInfoCache.put("hostName3", jnf.nullNode());
        java.util.Map<java.lang.String, org.apache.ambari.server.api.services.stackadvisor.recommendations.RecommendationResponse> configsRecommendationResponse = new java.util.concurrent.ConcurrentHashMap<>();
        configsRecommendationResponse.put("111", new org.apache.ambari.server.api.services.stackadvisor.recommendations.RecommendationResponse());
        configsRecommendationResponse.put("222", new org.apache.ambari.server.api.services.stackadvisor.recommendations.RecommendationResponse());
        hostInfoCacheField.set(helper, hostInfoCache);
        configsRecommendationResponseField.set(helper, configsRecommendationResponse);
        return helper;
    }

    private void testCreateConfigurationRecommendationCommand(org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRequest.StackAdvisorRequestType requestType, org.apache.ambari.server.api.services.stackadvisor.commands.StackAdvisorCommandType expectedCommandType) throws java.io.IOException, org.apache.ambari.server.api.services.stackadvisor.StackAdvisorException {
        org.apache.ambari.server.configuration.Configuration configuration = org.mockito.Mockito.mock(org.apache.ambari.server.configuration.Configuration.class);
        org.mockito.Mockito.when(configuration.getRecommendationsArtifactsRolloverMax()).thenReturn(100);
        org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRunner saRunner = org.mockito.Mockito.mock(org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRunner.class);
        org.apache.ambari.server.api.services.AmbariMetaInfo metaInfo = org.mockito.Mockito.mock(org.apache.ambari.server.api.services.AmbariMetaInfo.class);
        org.apache.ambari.server.state.ServiceInfo service = org.mockito.Mockito.mock(org.apache.ambari.server.state.ServiceInfo.class);
        org.mockito.Mockito.when(metaInfo.getService(org.mockito.Mockito.anyString(), org.mockito.Mockito.anyString(), org.mockito.Mockito.anyString())).thenReturn(service);
        org.mockito.Mockito.when(service.getServiceAdvisorType()).thenReturn(org.apache.ambari.server.state.ServiceInfo.ServiceAdvisorType.PYTHON);
        org.apache.ambari.server.api.services.stackadvisor.StackAdvisorHelper helper = new org.apache.ambari.server.api.services.stackadvisor.StackAdvisorHelper(configuration, saRunner, metaInfo, null, null);
        org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRequest request = org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRequest.StackAdvisorRequestBuilder.forStack("stackName", "stackVersion").ofType(requestType).build();
        org.apache.ambari.server.api.services.stackadvisor.commands.StackAdvisorCommand<org.apache.ambari.server.api.services.stackadvisor.recommendations.RecommendationResponse> command = helper.createRecommendationCommand("ZOOKEEPER", request);
        org.junit.Assert.assertTrue(command instanceof org.apache.ambari.server.api.services.stackadvisor.commands.ConfigurationRecommendationCommand);
        org.junit.Assert.assertEquals(expectedCommandType, ((org.apache.ambari.server.api.services.stackadvisor.commands.ConfigurationRecommendationCommand) (command)).getCommandType());
    }

    private static org.apache.ambari.server.api.services.stackadvisor.StackAdvisorHelper stackAdvisorHelperSpy(org.apache.ambari.server.configuration.Configuration configuration, org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRunner saRunner, org.apache.ambari.server.api.services.AmbariMetaInfo metaInfo) throws java.io.IOException {
        return org.mockito.Mockito.spy(new org.apache.ambari.server.api.services.stackadvisor.StackAdvisorHelper(configuration, saRunner, metaInfo, null, null));
    }
}
