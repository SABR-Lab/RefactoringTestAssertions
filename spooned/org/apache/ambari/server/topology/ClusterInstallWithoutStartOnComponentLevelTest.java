package org.apache.ambari.server.topology;
import org.easymock.Capture;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.MockType;
import org.easymock.TestSubject;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import static org.easymock.EasyMock.anyBoolean;
import static org.easymock.EasyMock.anyLong;
import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.anyString;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.isNull;
import static org.easymock.EasyMock.newCapture;
@org.junit.runner.RunWith(org.powermock.modules.junit4.PowerMockRunner.class)
@org.powermock.core.classloader.annotations.PrepareForTest(org.apache.ambari.server.controller.AmbariServer.class)
public class ClusterInstallWithoutStartOnComponentLevelTest extends org.easymock.EasyMockSupport {
    private static final java.lang.String CLUSTER_NAME = "test-cluster";

    private static final long CLUSTER_ID = 1;

    private static final java.lang.String BLUEPRINT_NAME = "test-bp";

    private static final java.lang.String STACK_NAME = "test-stack";

    private static final java.lang.String STACK_VERSION = "test-stack-version";

    @org.junit.Rule
    public org.easymock.EasyMockRule mocks = new org.easymock.EasyMockRule(this);

    @org.easymock.TestSubject
    private org.apache.ambari.server.topology.TopologyManager topologyManager = new org.apache.ambari.server.topology.TopologyManager();

    @org.easymock.Mock(type = org.easymock.MockType.NICE)
    private org.apache.ambari.server.topology.Blueprint blueprint;

    @org.easymock.Mock(type = org.easymock.MockType.NICE)
    private org.apache.ambari.server.controller.internal.Stack stack;

    @org.easymock.Mock(type = org.easymock.MockType.NICE)
    private org.apache.ambari.server.controller.internal.ProvisionClusterRequest request;

    private org.apache.ambari.server.topology.PersistedTopologyRequest persistedTopologyRequest;

    private org.apache.ambari.server.topology.LogicalRequestFactory logicalRequestFactory;

    @org.easymock.Mock(type = org.easymock.MockType.DEFAULT)
    private org.apache.ambari.server.topology.LogicalRequest logicalRequest;

    @org.easymock.Mock(type = org.easymock.MockType.NICE)
    private org.apache.ambari.server.topology.AmbariContext ambariContext;

    @org.easymock.Mock(type = org.easymock.MockType.NICE)
    private org.apache.ambari.server.controller.ConfigurationRequest configurationRequest;

    @org.easymock.Mock(type = org.easymock.MockType.NICE)
    private org.apache.ambari.server.controller.ConfigurationRequest configurationRequest2;

    @org.easymock.Mock(type = org.easymock.MockType.NICE)
    private org.apache.ambari.server.controller.ConfigurationRequest configurationRequest3;

    @org.easymock.Mock(type = org.easymock.MockType.NICE)
    private org.apache.ambari.server.controller.RequestStatusResponse requestStatusResponse;

    @org.easymock.Mock(type = org.easymock.MockType.STRICT)
    private java.util.concurrent.ExecutorService executor;

    @org.easymock.Mock(type = org.easymock.MockType.STRICT)
    private org.apache.ambari.server.topology.PersistedState persistedState;

    @org.easymock.Mock(type = org.easymock.MockType.NICE)
    private org.apache.ambari.server.topology.HostGroup group1;

    @org.easymock.Mock(type = org.easymock.MockType.NICE)
    private org.apache.ambari.server.topology.HostGroup group2;

    @org.easymock.Mock(type = org.easymock.MockType.STRICT)
    private org.apache.ambari.server.topology.SecurityConfigurationFactory securityConfigurationFactory;

    @org.easymock.Mock(type = org.easymock.MockType.STRICT)
    private org.apache.ambari.server.security.encryption.CredentialStoreService credentialStoreService;

    @org.easymock.Mock(type = org.easymock.MockType.STRICT)
    private org.apache.ambari.server.controller.spi.ClusterController clusterController;

    @org.easymock.Mock(type = org.easymock.MockType.STRICT)
    private org.apache.ambari.server.controller.spi.ResourceProvider resourceProvider;

    @org.easymock.Mock(type = org.easymock.MockType.NICE)
    private org.apache.ambari.server.controller.AmbariManagementController managementController;

    @org.easymock.Mock(type = org.easymock.MockType.NICE)
    private org.apache.ambari.server.state.Clusters clusters;

    @org.easymock.Mock(type = org.easymock.MockType.NICE)
    private org.apache.ambari.server.state.Cluster cluster;

    @org.easymock.Mock(type = org.easymock.MockType.NICE)
    private org.apache.ambari.server.actionmanager.HostRoleCommand hostRoleCommand;

    @org.easymock.Mock(type = org.easymock.MockType.NICE)
    private org.apache.ambari.server.topology.tasks.ConfigureClusterTaskFactory configureClusterTaskFactory;

    @org.easymock.Mock(type = org.easymock.MockType.NICE)
    private org.apache.ambari.server.topology.tasks.ConfigureClusterTask configureClusterTask;

    @org.easymock.Mock(type = org.easymock.MockType.NICE)
    private org.apache.ambari.server.state.ComponentInfo serviceComponentInfo;

    @org.easymock.Mock(type = org.easymock.MockType.NICE)
    private org.apache.ambari.server.state.ComponentInfo clientComponentInfo;

    @org.easymock.Mock(type = org.easymock.MockType.STRICT)
    private java.util.concurrent.Future mockFuture;

    @org.easymock.Mock
    private org.apache.ambari.server.topology.validators.TopologyValidatorService topologyValidatorServiceMock;

    @org.easymock.Mock(type = org.easymock.MockType.NICE)
    private org.apache.ambari.server.events.publishers.AmbariEventPublisher eventPublisher;

    private final org.apache.ambari.server.topology.Configuration stackConfig = new org.apache.ambari.server.topology.Configuration(new java.util.HashMap<>(), new java.util.HashMap<>());

    private final org.apache.ambari.server.topology.Configuration bpConfiguration = new org.apache.ambari.server.topology.Configuration(new java.util.HashMap<>(), new java.util.HashMap<>(), stackConfig);

    private final org.apache.ambari.server.topology.Configuration topoConfiguration = new org.apache.ambari.server.topology.Configuration(new java.util.HashMap<>(), new java.util.HashMap<>(), bpConfiguration);

    private final org.apache.ambari.server.topology.Configuration bpGroup1Config = new org.apache.ambari.server.topology.Configuration(new java.util.HashMap<>(), new java.util.HashMap<>(), bpConfiguration);

    private final org.apache.ambari.server.topology.Configuration bpGroup2Config = new org.apache.ambari.server.topology.Configuration(new java.util.HashMap<>(), new java.util.HashMap<>(), bpConfiguration);

    private final org.apache.ambari.server.topology.Configuration topoGroup1Config = new org.apache.ambari.server.topology.Configuration(new java.util.HashMap<>(), new java.util.HashMap<>(), bpGroup1Config);

    private final org.apache.ambari.server.topology.Configuration topoGroup2Config = new org.apache.ambari.server.topology.Configuration(new java.util.HashMap<>(), new java.util.HashMap<>(), bpGroup2Config);

    private org.apache.ambari.server.topology.HostGroupInfo group1Info = new org.apache.ambari.server.topology.HostGroupInfo("group1");

    private org.apache.ambari.server.topology.HostGroupInfo group2Info = new org.apache.ambari.server.topology.HostGroupInfo("group2");

    private java.util.Map<java.lang.String, org.apache.ambari.server.topology.HostGroupInfo> groupInfoMap = new java.util.HashMap<>();

    private java.util.Collection<java.lang.String> group1Components = java.util.Arrays.asList("component1", "component2", "component3");

    private java.util.Collection<java.lang.String> group2Components = java.util.Arrays.asList("component3", "component4");

    private java.util.Map<java.lang.String, java.util.Collection<java.lang.String>> group1ServiceComponents = new java.util.HashMap<>();

    private java.util.Map<java.lang.String, java.util.Collection<java.lang.String>> group2ServiceComponents = new java.util.HashMap<>();

    private java.util.Map<java.lang.String, java.util.Collection<java.lang.String>> serviceComponents = new java.util.HashMap<>();

    private java.lang.String predicate = "Hosts/host_name=foo";

    private java.util.List<org.apache.ambari.server.topology.TopologyValidator> topologyValidators = new java.util.ArrayList<>();

    private org.easymock.Capture<org.apache.ambari.server.topology.ClusterTopology> clusterTopologyCapture;

    private org.easymock.Capture<java.util.Map<java.lang.String, java.lang.Object>> configRequestPropertiesCapture;

    private org.easymock.Capture<java.util.Map<java.lang.String, java.lang.Object>> configRequestPropertiesCapture2;

    private org.easymock.Capture<java.util.Map<java.lang.String, java.lang.Object>> configRequestPropertiesCapture3;

    private org.easymock.Capture<org.apache.ambari.server.controller.ClusterRequest> updateClusterConfigRequestCapture;

    private org.easymock.Capture<java.lang.Runnable> updateConfigTaskCapture;

    @org.junit.Before
    public void setup() throws java.lang.Exception {
        clusterTopologyCapture = org.easymock.EasyMock.newCapture();
        configRequestPropertiesCapture = org.easymock.EasyMock.newCapture();
        configRequestPropertiesCapture2 = org.easymock.EasyMock.newCapture();
        configRequestPropertiesCapture3 = org.easymock.EasyMock.newCapture();
        updateClusterConfigRequestCapture = org.easymock.EasyMock.newCapture();
        updateConfigTaskCapture = org.easymock.EasyMock.newCapture();
        topoConfiguration.setProperty("service1-site", "s1-prop", "s1-prop-value");
        topoConfiguration.setProperty("service2-site", "s2-prop", "s2-prop-value");
        topoConfiguration.setProperty("cluster-env", "g-prop", "g-prop-value");
        group1Info.addHost("host1");
        group1Info.setConfiguration(topoGroup1Config);
        group2Info.setRequestedCount(2);
        group2Info.setPredicate(predicate);
        group2Info.setConfiguration(topoGroup2Config);
        groupInfoMap.put("group1", group1Info);
        groupInfoMap.put("group2", group2Info);
        java.util.Map<java.lang.String, org.apache.ambari.server.topology.HostGroup> groupMap = new java.util.HashMap<>();
        groupMap.put("group1", group1);
        groupMap.put("group2", group2);
        serviceComponents.put("service1", java.util.Arrays.asList("component1", "component3"));
        serviceComponents.put("service2", java.util.Arrays.asList("component2", "component4"));
        group1ServiceComponents.put("service1", java.util.Arrays.asList("component1", "component3"));
        group1ServiceComponents.put("service2", java.util.Collections.singleton("component2"));
        group2ServiceComponents.put("service2", java.util.Arrays.asList("component3", "component4"));
        org.easymock.EasyMock.expect(blueprint.getHostGroup("group1")).andReturn(group1).anyTimes();
        org.easymock.EasyMock.expect(blueprint.getHostGroup("group2")).andReturn(group2).anyTimes();
        org.easymock.EasyMock.expect(blueprint.getComponents("service1")).andReturn(java.util.Arrays.asList("component1", "component3")).anyTimes();
        org.easymock.EasyMock.expect(blueprint.getComponents("service2")).andReturn(java.util.Arrays.asList("component2", "component4")).anyTimes();
        org.easymock.EasyMock.expect(blueprint.getConfiguration()).andReturn(bpConfiguration).anyTimes();
        org.easymock.EasyMock.expect(blueprint.getHostGroups()).andReturn(groupMap).anyTimes();
        org.easymock.EasyMock.expect(blueprint.getHostGroupsForComponent("component1")).andReturn(java.util.Collections.singleton(group1)).anyTimes();
        org.easymock.EasyMock.expect(blueprint.getHostGroupsForComponent("component2")).andReturn(java.util.Collections.singleton(group1)).anyTimes();
        org.easymock.EasyMock.expect(blueprint.getHostGroupsForComponent("component3")).andReturn(java.util.Arrays.asList(group1, group2)).anyTimes();
        org.easymock.EasyMock.expect(blueprint.getHostGroupsForComponent("component4")).andReturn(java.util.Collections.singleton(group2)).anyTimes();
        org.easymock.EasyMock.expect(blueprint.getHostGroupsForService("service1")).andReturn(java.util.Arrays.asList(group1, group2)).anyTimes();
        org.easymock.EasyMock.expect(blueprint.getHostGroupsForService("service2")).andReturn(java.util.Arrays.asList(group1, group2)).anyTimes();
        org.easymock.EasyMock.expect(blueprint.getName()).andReturn(org.apache.ambari.server.topology.ClusterInstallWithoutStartOnComponentLevelTest.BLUEPRINT_NAME).anyTimes();
        org.easymock.EasyMock.expect(blueprint.getServices()).andReturn(java.util.Arrays.asList("service1", "service2")).anyTimes();
        org.easymock.EasyMock.expect(blueprint.getStack()).andReturn(stack).anyTimes();
        org.easymock.EasyMock.expect(blueprint.isValidConfigType(org.easymock.EasyMock.anyString())).andReturn(true).anyTimes();
        org.easymock.EasyMock.expect(blueprint.getRepositorySettings()).andReturn(new java.util.ArrayList<>()).anyTimes();
        org.easymock.EasyMock.expect(stack.getAllConfigurationTypes("service1")).andReturn(java.util.Arrays.asList("service1-site", "service1-env")).anyTimes();
        org.easymock.EasyMock.expect(stack.getAllConfigurationTypes("service2")).andReturn(java.util.Arrays.asList("service2-site", "service2-env")).anyTimes();
        org.easymock.EasyMock.expect(stack.getAutoDeployInfo("component1")).andReturn(null).anyTimes();
        org.easymock.EasyMock.expect(stack.getAutoDeployInfo("component2")).andReturn(null).anyTimes();
        org.easymock.EasyMock.expect(stack.getAutoDeployInfo("component3")).andReturn(null).anyTimes();
        org.easymock.EasyMock.expect(stack.getAutoDeployInfo("component4")).andReturn(null).anyTimes();
        org.easymock.EasyMock.expect(serviceComponentInfo.isClient()).andReturn(false).anyTimes();
        org.easymock.EasyMock.expect(clientComponentInfo.isClient()).andReturn(true).anyTimes();
        org.easymock.EasyMock.expect(stack.getComponentInfo("component1")).andReturn(serviceComponentInfo).anyTimes();
        org.easymock.EasyMock.expect(stack.getComponentInfo("component2")).andReturn(serviceComponentInfo).anyTimes();
        org.easymock.EasyMock.expect(stack.getComponentInfo("component3")).andReturn(clientComponentInfo).anyTimes();
        org.easymock.EasyMock.expect(stack.getComponentInfo("component4")).andReturn(clientComponentInfo).anyTimes();
        org.easymock.EasyMock.expect(stack.getCardinality("component1")).andReturn(new org.apache.ambari.server.topology.Cardinality("1")).anyTimes();
        org.easymock.EasyMock.expect(stack.getCardinality("component2")).andReturn(new org.apache.ambari.server.topology.Cardinality("1")).anyTimes();
        org.easymock.EasyMock.expect(stack.getCardinality("component3")).andReturn(new org.apache.ambari.server.topology.Cardinality("1+")).anyTimes();
        org.easymock.EasyMock.expect(stack.getCardinality("component4")).andReturn(new org.apache.ambari.server.topology.Cardinality("1+")).anyTimes();
        org.easymock.EasyMock.expect(stack.getComponents()).andReturn(serviceComponents).anyTimes();
        org.easymock.EasyMock.expect(stack.getComponents("service1")).andReturn(serviceComponents.get("service1")).anyTimes();
        org.easymock.EasyMock.expect(stack.getComponents("service2")).andReturn(serviceComponents.get("service2")).anyTimes();
        org.easymock.EasyMock.expect(stack.getConfiguration()).andReturn(stackConfig).anyTimes();
        org.easymock.EasyMock.expect(stack.getName()).andReturn(org.apache.ambari.server.topology.ClusterInstallWithoutStartOnComponentLevelTest.STACK_NAME).anyTimes();
        org.easymock.EasyMock.expect(stack.getVersion()).andReturn(org.apache.ambari.server.topology.ClusterInstallWithoutStartOnComponentLevelTest.STACK_VERSION).anyTimes();
        org.easymock.EasyMock.expect(stack.getServiceForConfigType("service1-site")).andReturn("service1").anyTimes();
        org.easymock.EasyMock.expect(stack.getServiceForConfigType("service2-site")).andReturn("service2").anyTimes();
        org.easymock.EasyMock.expect(stack.getExcludedConfigurationTypes("service1")).andReturn(java.util.Collections.emptySet()).anyTimes();
        org.easymock.EasyMock.expect(stack.getExcludedConfigurationTypes("service2")).andReturn(java.util.Collections.emptySet()).anyTimes();
        org.easymock.EasyMock.expect(request.getBlueprint()).andReturn(blueprint).anyTimes();
        org.easymock.EasyMock.expect(request.getClusterId()).andReturn(org.apache.ambari.server.topology.ClusterInstallWithoutStartOnComponentLevelTest.CLUSTER_ID).anyTimes();
        org.easymock.EasyMock.expect(request.getClusterName()).andReturn(org.apache.ambari.server.topology.ClusterInstallWithoutStartOnComponentLevelTest.CLUSTER_NAME).anyTimes();
        org.easymock.EasyMock.expect(request.getDescription()).andReturn("Provision Cluster Test").anyTimes();
        org.easymock.EasyMock.expect(request.getConfiguration()).andReturn(topoConfiguration).anyTimes();
        org.easymock.EasyMock.expect(request.getHostGroupInfo()).andReturn(groupInfoMap).anyTimes();
        org.easymock.EasyMock.expect(request.getConfigRecommendationStrategy()).andReturn(org.apache.ambari.server.topology.ConfigRecommendationStrategy.NEVER_APPLY);
        org.easymock.EasyMock.expect(request.getProvisionAction()).andReturn(org.apache.ambari.server.controller.internal.ProvisionAction.INSTALL_AND_START).anyTimes();
        org.easymock.EasyMock.expect(request.getSecurityConfiguration()).andReturn(null).anyTimes();
        org.easymock.EasyMock.expect(request.getRepositoryVersion()).andReturn("1").anyTimes();
        org.easymock.EasyMock.expect(group1.getBlueprintName()).andReturn(org.apache.ambari.server.topology.ClusterInstallWithoutStartOnComponentLevelTest.BLUEPRINT_NAME).anyTimes();
        org.easymock.EasyMock.expect(group1.getCardinality()).andReturn("test cardinality").anyTimes();
        org.easymock.EasyMock.expect(group1.containsMasterComponent()).andReturn(true).anyTimes();
        org.easymock.EasyMock.expect(group1.getComponentNames()).andReturn(group1Components).anyTimes();
        org.easymock.EasyMock.expect(group1.getComponentNames(org.apache.ambari.server.controller.internal.ProvisionAction.INSTALL_ONLY)).andReturn(java.util.Arrays.asList("component1")).anyTimes();
        org.easymock.EasyMock.expect(group1.getComponentNames(org.apache.ambari.server.controller.internal.ProvisionAction.START_ONLY)).andReturn(java.util.Collections.emptyList()).anyTimes();
        org.easymock.EasyMock.expect(group1.getComponents("service1")).andReturn(group1ServiceComponents.get("service1")).anyTimes();
        org.easymock.EasyMock.expect(group1.getComponents("service2")).andReturn(group1ServiceComponents.get("service1")).anyTimes();
        org.easymock.EasyMock.expect(group1.getConfiguration()).andReturn(topoGroup1Config).anyTimes();
        org.easymock.EasyMock.expect(group1.getName()).andReturn("group1").anyTimes();
        org.easymock.EasyMock.expect(group1.getServices()).andReturn(java.util.Arrays.asList("service1", "service2")).anyTimes();
        org.easymock.EasyMock.expect(group1.getStack()).andReturn(stack).anyTimes();
        org.easymock.EasyMock.expect(group2.getBlueprintName()).andReturn(org.apache.ambari.server.topology.ClusterInstallWithoutStartOnComponentLevelTest.BLUEPRINT_NAME).anyTimes();
        org.easymock.EasyMock.expect(group2.getCardinality()).andReturn("test cardinality").anyTimes();
        org.easymock.EasyMock.expect(group2.containsMasterComponent()).andReturn(false).anyTimes();
        org.easymock.EasyMock.expect(group2.getComponentNames()).andReturn(group2Components).anyTimes();
        org.easymock.EasyMock.expect(group2.getComponentNames(org.apache.ambari.server.controller.internal.ProvisionAction.INSTALL_ONLY)).andReturn(java.util.Collections.emptyList()).anyTimes();
        org.easymock.EasyMock.expect(group2.getComponentNames(org.apache.ambari.server.controller.internal.ProvisionAction.START_ONLY)).andReturn(java.util.Collections.emptyList()).anyTimes();
        org.easymock.EasyMock.expect(group2.getComponents("service1")).andReturn(group2ServiceComponents.get("service1")).anyTimes();
        org.easymock.EasyMock.expect(group2.getComponents("service2")).andReturn(group2ServiceComponents.get("service2")).anyTimes();
        org.easymock.EasyMock.expect(group2.getConfiguration()).andReturn(topoGroup2Config).anyTimes();
        org.easymock.EasyMock.expect(group2.getName()).andReturn("group2").anyTimes();
        org.easymock.EasyMock.expect(group2.getServices()).andReturn(java.util.Arrays.asList("service1", "service2")).anyTimes();
        org.easymock.EasyMock.expect(group2.getStack()).andReturn(stack).anyTimes();
        logicalRequestFactory = createMockBuilder(org.apache.ambari.server.topology.LogicalRequestFactory.class).addMockedMethod(org.apache.ambari.server.topology.LogicalRequestFactory.class.getMethod("createRequest", java.lang.Long.class, org.apache.ambari.server.topology.TopologyRequest.class, org.apache.ambari.server.topology.ClusterTopology.class, org.apache.ambari.server.orm.entities.TopologyLogicalRequestEntity.class)).createMock();
        java.lang.reflect.Field f = org.apache.ambari.server.topology.TopologyManager.class.getDeclaredField("logicalRequestFactory");
        f.setAccessible(true);
        f.set(topologyManager, logicalRequestFactory);
        org.powermock.api.easymock.PowerMock.mockStatic(org.apache.ambari.server.controller.AmbariServer.class);
        org.easymock.EasyMock.expect(org.apache.ambari.server.controller.AmbariServer.getController()).andReturn(managementController).anyTimes();
        org.powermock.api.easymock.PowerMock.replay(org.apache.ambari.server.controller.AmbariServer.class);
        org.easymock.EasyMock.expect(managementController.getClusters()).andReturn(clusters).anyTimes();
        org.easymock.EasyMock.expect(clusters.getClusterById(org.easymock.EasyMock.anyLong())).andReturn(cluster).anyTimes();
        org.easymock.EasyMock.expect(clusters.getCluster(org.apache.ambari.server.topology.ClusterInstallWithoutStartOnComponentLevelTest.CLUSTER_NAME)).andReturn(cluster).anyTimes();
        org.easymock.EasyMock.expect(cluster.getClusterName()).andReturn(org.apache.ambari.server.topology.ClusterInstallWithoutStartOnComponentLevelTest.CLUSTER_NAME).anyTimes();
        org.easymock.EasyMock.expect(ambariContext.getPersistedTopologyState()).andReturn(persistedState).anyTimes();
        ambariContext.createAmbariResources(org.easymock.EasyMock.isA(org.apache.ambari.server.topology.ClusterTopology.class), org.easymock.EasyMock.eq(org.apache.ambari.server.topology.ClusterInstallWithoutStartOnComponentLevelTest.CLUSTER_NAME), ((org.apache.ambari.server.state.SecurityType) (org.easymock.EasyMock.isNull())), ((java.lang.String) (org.easymock.EasyMock.eq("1"))), org.easymock.EasyMock.anyLong());
        org.easymock.EasyMock.expectLastCall().once();
        org.easymock.EasyMock.expect(ambariContext.getNextRequestId()).andReturn(1L).once();
        org.easymock.EasyMock.expect(ambariContext.isClusterKerberosEnabled(org.apache.ambari.server.topology.ClusterInstallWithoutStartOnComponentLevelTest.CLUSTER_ID)).andReturn(false).anyTimes();
        org.easymock.EasyMock.expect(ambariContext.getClusterId(org.apache.ambari.server.topology.ClusterInstallWithoutStartOnComponentLevelTest.CLUSTER_NAME)).andReturn(org.apache.ambari.server.topology.ClusterInstallWithoutStartOnComponentLevelTest.CLUSTER_ID).anyTimes();
        org.easymock.EasyMock.expect(ambariContext.getClusterName(org.apache.ambari.server.topology.ClusterInstallWithoutStartOnComponentLevelTest.CLUSTER_ID)).andReturn(org.apache.ambari.server.topology.ClusterInstallWithoutStartOnComponentLevelTest.CLUSTER_NAME).anyTimes();
        org.easymock.EasyMock.expect(ambariContext.createConfigurationRequests(org.easymock.EasyMock.capture(configRequestPropertiesCapture))).andReturn(java.util.Collections.singletonList(configurationRequest));
        org.easymock.EasyMock.expect(ambariContext.createConfigurationRequests(org.easymock.EasyMock.capture(configRequestPropertiesCapture2))).andReturn(java.util.Collections.singletonList(configurationRequest2)).once();
        org.easymock.EasyMock.expect(ambariContext.createConfigurationRequests(org.easymock.EasyMock.capture(configRequestPropertiesCapture3))).andReturn(java.util.Collections.singletonList(configurationRequest3)).once();
        org.easymock.EasyMock.expect(ambariContext.createAmbariTask(org.easymock.EasyMock.anyLong(), org.easymock.EasyMock.anyLong(), org.easymock.EasyMock.anyString(), org.easymock.EasyMock.anyString(), org.easymock.EasyMock.eq(org.apache.ambari.server.topology.AmbariContext.TaskType.INSTALL), org.easymock.EasyMock.anyBoolean())).andReturn(hostRoleCommand).times(7);
        org.easymock.EasyMock.expect(ambariContext.createAmbariTask(org.easymock.EasyMock.anyLong(), org.easymock.EasyMock.anyLong(), org.easymock.EasyMock.anyString(), org.easymock.EasyMock.anyString(), org.easymock.EasyMock.eq(org.apache.ambari.server.topology.AmbariContext.TaskType.START), org.easymock.EasyMock.anyBoolean())).andReturn(hostRoleCommand).times(1);
        org.easymock.EasyMock.expect(hostRoleCommand.getTaskId()).andReturn(1L).atLeastOnce();
        org.easymock.EasyMock.expect(hostRoleCommand.getRoleCommand()).andReturn(org.apache.ambari.server.RoleCommand.INSTALL).atLeastOnce();
        org.easymock.EasyMock.expect(hostRoleCommand.getRole()).andReturn(org.apache.ambari.server.Role.INSTALL_PACKAGES).atLeastOnce();
        org.easymock.EasyMock.expect(hostRoleCommand.getStatus()).andReturn(org.apache.ambari.server.actionmanager.HostRoleStatus.COMPLETED).atLeastOnce();
        ambariContext.setConfigurationOnCluster(org.easymock.EasyMock.capture(updateClusterConfigRequestCapture));
        org.easymock.EasyMock.expectLastCall().times(3);
        ambariContext.persistInstallStateForUI(org.apache.ambari.server.topology.ClusterInstallWithoutStartOnComponentLevelTest.CLUSTER_NAME, org.apache.ambari.server.topology.ClusterInstallWithoutStartOnComponentLevelTest.STACK_NAME, org.apache.ambari.server.topology.ClusterInstallWithoutStartOnComponentLevelTest.STACK_VERSION);
        org.easymock.EasyMock.expectLastCall().once();
        org.easymock.EasyMock.expect(configureClusterTaskFactory.createConfigureClusterTask(org.easymock.EasyMock.anyObject(), org.easymock.EasyMock.anyObject(), org.easymock.EasyMock.anyObject())).andReturn(configureClusterTask);
        org.easymock.EasyMock.expect(configureClusterTask.getTimeout()).andReturn(1000L);
        org.easymock.EasyMock.expect(configureClusterTask.getRepeatDelay()).andReturn(50L);
        org.easymock.EasyMock.expect(executor.submit(org.easymock.EasyMock.anyObject(org.apache.ambari.server.topology.AsyncCallableService.class))).andReturn(mockFuture).times(1);
        persistedTopologyRequest = new org.apache.ambari.server.topology.PersistedTopologyRequest(1, request);
        org.easymock.EasyMock.expect(persistedState.getAllRequests()).andReturn(java.util.Collections.emptyMap()).once();
        org.easymock.EasyMock.expect(persistedState.persistTopologyRequest(request)).andReturn(persistedTopologyRequest).once();
        persistedState.persistLogicalRequest(((org.apache.ambari.server.topology.LogicalRequest) (org.easymock.EasyMock.anyObject())), org.easymock.EasyMock.anyLong());
        org.easymock.EasyMock.expectLastCall().once();
        topologyValidatorServiceMock.validateTopologyConfiguration(org.easymock.EasyMock.anyObject(org.apache.ambari.server.topology.ClusterTopology.class));
        replayAll();
        java.lang.Class clazz = org.apache.ambari.server.topology.TopologyManager.class;
        f = clazz.getDeclaredField("executor");
        f.setAccessible(true);
        f.set(topologyManager, executor);
        org.easymock.EasyMockSupport.injectMocks(topologyManager);
    }

    @org.junit.After
    public void tearDown() {
        verifyAll();
        resetAll();
    }

    @org.junit.Test
    public void testProvisionCluster() throws java.lang.Exception {
        topologyManager.provisionCluster(request);
    }
}
