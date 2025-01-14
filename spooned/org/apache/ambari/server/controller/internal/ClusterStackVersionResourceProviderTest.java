package org.apache.ambari.server.controller.internal;
import org.apache.commons.lang.StringUtils;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.IAnswer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import static org.easymock.EasyMock.anyLong;
import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
public class ClusterStackVersionResourceProviderTest {
    public static final int MAX_TASKS_PER_STAGE = 2;

    private com.google.inject.Injector injector;

    private org.apache.ambari.server.api.services.AmbariMetaInfo ambariMetaInfo;

    private org.apache.ambari.server.orm.dao.RepositoryVersionDAO repositoryVersionDAOMock;

    private org.apache.ambari.server.state.ConfigHelper configHelper;

    private org.apache.ambari.server.configuration.Configuration configuration;

    private org.apache.ambari.server.actionmanager.StageFactory stageFactory;

    private org.apache.ambari.server.orm.dao.HostVersionDAO hostVersionDAO;

    private org.apache.ambari.server.orm.dao.HostComponentStateDAO hostComponentStateDAO;

    private org.apache.ambari.server.state.Clusters clusters;

    private org.apache.ambari.server.actionmanager.ActionManager actionManager;

    private org.apache.ambari.server.controller.AmbariManagementController managementController;

    public static final java.util.List<org.apache.ambari.server.orm.entities.RepoOsEntity> REPO_OS_ENTITIES = new java.util.ArrayList<>();

    static {
        org.apache.ambari.server.orm.entities.RepoDefinitionEntity repoDefinitionEntity1 = new org.apache.ambari.server.orm.entities.RepoDefinitionEntity();
        repoDefinitionEntity1.setRepoID("HDP-UTILS-1.1.0.20");
        repoDefinitionEntity1.setBaseUrl("http://s3.amazonaws.com/dev.hortonworks.com/HDP/centos5/2.x/updates/2.2.0.0");
        repoDefinitionEntity1.setRepoName("HDP-UTILS");
        org.apache.ambari.server.orm.entities.RepoDefinitionEntity repoDefinitionEntity2 = new org.apache.ambari.server.orm.entities.RepoDefinitionEntity();
        repoDefinitionEntity2.setRepoID("HDP-2.2");
        repoDefinitionEntity2.setBaseUrl("http://s3.amazonaws.com/dev.hortonworks.com/HDP/centos5/2.x/updates/2.2.0.0");
        repoDefinitionEntity2.setRepoName("HDP");
        org.apache.ambari.server.orm.entities.RepoOsEntity repoOsEntity = new org.apache.ambari.server.orm.entities.RepoOsEntity();
        repoOsEntity.setFamily("redhat6");
        repoOsEntity.setAmbariManaged(true);
        repoOsEntity.addRepoDefinition(repoDefinitionEntity1);
        repoOsEntity.addRepoDefinition(repoDefinitionEntity2);
        REPO_OS_ENTITIES.add(repoOsEntity);
    }

    public static final java.util.List<org.apache.ambari.server.orm.entities.RepoOsEntity> REPO_OS_NOT_MANAGED = new java.util.ArrayList<>();

    static {
        org.apache.ambari.server.orm.entities.RepoDefinitionEntity repoDefinitionEntity1 = new org.apache.ambari.server.orm.entities.RepoDefinitionEntity();
        repoDefinitionEntity1.setRepoID("HDP-UTILS-1.1.0.20");
        repoDefinitionEntity1.setBaseUrl("http://s3.amazonaws.com/dev.hortonworks.com/HDP/centos5/2.x/updates/2.2.0.0");
        repoDefinitionEntity1.setRepoName("HDP-UTILS");
        org.apache.ambari.server.orm.entities.RepoDefinitionEntity repoDefinitionEntity2 = new org.apache.ambari.server.orm.entities.RepoDefinitionEntity();
        repoDefinitionEntity2.setRepoID("HDP-2.2");
        repoDefinitionEntity2.setBaseUrl("http://s3.amazonaws.com/dev.hortonworks.com/HDP/centos5/2.x/updates/2.2.0.0");
        repoDefinitionEntity2.setRepoName("HDP");
        org.apache.ambari.server.orm.entities.RepoOsEntity repoOsEntity = new org.apache.ambari.server.orm.entities.RepoOsEntity();
        repoOsEntity.setFamily("redhat6");
        repoOsEntity.setAmbariManaged(false);
        repoOsEntity.addRepoDefinition(repoDefinitionEntity1);
        repoOsEntity.addRepoDefinition(repoDefinitionEntity2);
        REPO_OS_NOT_MANAGED.add(repoOsEntity);
    }

    @org.junit.Before
    public void setup() throws java.lang.Exception {
        repositoryVersionDAOMock = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.orm.dao.RepositoryVersionDAO.class);
        hostVersionDAO = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.orm.dao.HostVersionDAO.class);
        hostComponentStateDAO = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.orm.dao.HostComponentStateDAO.class);
        configHelper = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ConfigHelper.class);
        org.apache.ambari.server.orm.InMemoryDefaultTestModule inMemoryModule = new org.apache.ambari.server.orm.InMemoryDefaultTestModule();
        java.util.Properties properties = inMemoryModule.getProperties();
        properties.setProperty(org.apache.ambari.server.configuration.Configuration.AGENT_PACKAGE_PARALLEL_COMMANDS_LIMIT.getKey(), java.lang.String.valueOf(org.apache.ambari.server.controller.internal.ClusterStackVersionResourceProviderTest.MAX_TASKS_PER_STAGE));
        configuration = new org.apache.ambari.server.configuration.Configuration(properties);
        stageFactory = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.actionmanager.StageFactory.class);
        clusters = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Clusters.class);
        actionManager = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.actionmanager.ActionManager.class);
        managementController = org.easymock.EasyMock.createMock(org.apache.ambari.server.controller.AmbariManagementController.class);
        injector = com.google.inject.Guice.createInjector(com.google.inject.util.Modules.override(inMemoryModule).with(new org.apache.ambari.server.controller.internal.ClusterStackVersionResourceProviderTest.MockModule()));
        injector.getInstance(org.apache.ambari.server.orm.GuiceJpaInitializer.class);
        ambariMetaInfo = injector.getInstance(org.apache.ambari.server.api.services.AmbariMetaInfo.class);
    }

    @org.junit.After
    public void teardown() throws org.apache.ambari.server.AmbariException, java.sql.SQLException {
        org.apache.ambari.server.H2DatabaseCleaner.clearDatabaseAndStopPersistenceService(injector);
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
    public void testCreateResourcesAsClusterOperator() throws java.lang.Exception {
        testCreateResources(org.apache.ambari.server.security.TestAuthenticationFactory.createClusterOperator());
    }

    private void testCreateResources(org.springframework.security.core.Authentication authentication) throws java.lang.Exception {
        org.apache.ambari.server.state.Cluster cluster = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Cluster.class);
        java.util.Map<java.lang.String, java.lang.String> hostLevelParams = new java.util.HashMap<>();
        org.apache.ambari.server.state.StackId stackId = new org.apache.ambari.server.state.StackId("HDP", "2.0.1");
        org.apache.ambari.server.orm.entities.StackEntity stackEntity = new org.apache.ambari.server.orm.entities.StackEntity();
        stackEntity.setStackName("HDP");
        stackEntity.setStackVersion("2.1.1");
        org.apache.ambari.server.orm.entities.RepositoryVersionEntity repoVersion = new org.apache.ambari.server.orm.entities.RepositoryVersionEntity();
        repoVersion.setId(1L);
        repoVersion.addRepoOsEntities(org.apache.ambari.server.controller.internal.ClusterStackVersionResourceProviderTest.REPO_OS_ENTITIES);
        repoVersion.setStack(stackEntity);
        final java.lang.String hostWithoutVersionableComponents = "host2";
        java.util.List<org.apache.ambari.server.state.Host> hostsNeedingInstallCommands = new java.util.ArrayList<>();
        java.util.Map<java.lang.String, org.apache.ambari.server.state.Host> hostsForCluster = new java.util.HashMap<>();
        int hostCount = 10;
        for (int i = 0; i < hostCount; i++) {
            java.lang.String hostname = "host" + i;
            org.apache.ambari.server.state.Host host = org.easymock.EasyMock.createNiceMock(hostname, org.apache.ambari.server.state.Host.class);
            org.easymock.EasyMock.expect(host.getHostName()).andReturn(hostname).anyTimes();
            org.easymock.EasyMock.expect(host.getOsFamily()).andReturn("redhat6").anyTimes();
            org.easymock.EasyMock.expect(host.getMaintenanceState(org.easymock.EasyMock.anyLong())).andReturn(org.apache.ambari.server.state.MaintenanceState.OFF).anyTimes();
            org.easymock.EasyMock.expect(host.getAllHostVersions()).andReturn(java.util.Collections.emptyList()).anyTimes();
            org.easymock.EasyMock.replay(host);
            hostsForCluster.put(hostname, host);
            if (!org.apache.commons.lang.StringUtils.equals(hostWithoutVersionableComponents, hostname)) {
                hostsNeedingInstallCommands.add(host);
            }
        }
        final org.apache.ambari.server.state.ServiceComponentHost schDatanode = org.easymock.EasyMock.createMock(org.apache.ambari.server.state.ServiceComponentHost.class);
        org.easymock.EasyMock.expect(schDatanode.getServiceName()).andReturn("HDFS").anyTimes();
        org.easymock.EasyMock.expect(schDatanode.getServiceComponentName()).andReturn("DATANODE").anyTimes();
        final org.apache.ambari.server.state.ServiceComponentHost schNamenode = org.easymock.EasyMock.createMock(org.apache.ambari.server.state.ServiceComponentHost.class);
        org.easymock.EasyMock.expect(schNamenode.getServiceName()).andReturn("HDFS").anyTimes();
        org.easymock.EasyMock.expect(schNamenode.getServiceComponentName()).andReturn("NAMENODE").anyTimes();
        final org.apache.ambari.server.state.ServiceComponentHost schAMS = org.easymock.EasyMock.createMock(org.apache.ambari.server.state.ServiceComponentHost.class);
        org.easymock.EasyMock.expect(schAMS.getServiceName()).andReturn("AMBARI_METRICS").anyTimes();
        org.easymock.EasyMock.expect(schAMS.getServiceComponentName()).andReturn("METRICS_COLLECTOR").anyTimes();
        final java.util.List<org.apache.ambari.server.state.ServiceComponentHost> schsH1 = com.google.common.collect.Lists.newArrayList(schDatanode, schNamenode, schAMS);
        final java.util.List<org.apache.ambari.server.state.ServiceComponentHost> schsH2 = com.google.common.collect.Lists.newArrayList(schAMS);
        org.apache.ambari.server.state.ServiceOsSpecific.Package hdfsPackage = new org.apache.ambari.server.state.ServiceOsSpecific.Package();
        hdfsPackage.setName("hdfs");
        java.util.List<org.apache.ambari.server.state.ServiceOsSpecific.Package> packages = java.util.Collections.singletonList(hdfsPackage);
        org.apache.ambari.server.controller.RequestStatusResponse response = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.RequestStatusResponse.class);
        java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.lang.String>> hostConfigTags = new java.util.HashMap<>();
        org.easymock.EasyMock.expect(configHelper.getEffectiveDesiredTags(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.cluster.ClusterImpl.class), org.easymock.EasyMock.anyObject(java.lang.String.class))).andReturn(hostConfigTags);
        org.easymock.EasyMock.expect(managementController.getClusters()).andReturn(clusters).anyTimes();
        org.easymock.EasyMock.expect(managementController.getAmbariMetaInfo()).andReturn(ambariMetaInfo).anyTimes();
        org.easymock.EasyMock.expect(managementController.getAuthName()).andReturn("admin").anyTimes();
        org.easymock.EasyMock.expect(managementController.getActionManager()).andReturn(actionManager).anyTimes();
        org.easymock.EasyMock.expect(managementController.getJdkResourceUrl()).andReturn("/JdkResourceUrl").anyTimes();
        org.easymock.EasyMock.expect(managementController.getPackagesForServiceHost(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.ServiceInfo.class), org.easymock.EasyMock.<java.util.Map<java.lang.String, java.lang.String>>anyObject(), org.easymock.EasyMock.anyObject(java.lang.String.class))).andReturn(packages).anyTimes();
        org.easymock.EasyMock.expect(managementController.findConfigurationTagsWithOverrides(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.Cluster.class), org.easymock.EasyMock.anyString(), org.easymock.EasyMock.anyObject())).andReturn(new java.util.HashMap<java.lang.String, java.util.Map<java.lang.String, java.lang.String>>()).anyTimes();
        org.easymock.EasyMock.expect(clusters.getCluster(org.easymock.EasyMock.anyObject(java.lang.String.class))).andReturn(cluster);
        org.easymock.EasyMock.expect(clusters.getHostsForCluster(org.easymock.EasyMock.anyObject(java.lang.String.class))).andReturn(hostsForCluster).anyTimes();
        java.lang.String clusterName = "Cluster100";
        org.easymock.EasyMock.expect(cluster.getClusterId()).andReturn(1L).anyTimes();
        org.easymock.EasyMock.expect(cluster.getHosts()).andReturn(hostsForCluster.values()).atLeastOnce();
        org.easymock.EasyMock.expect(cluster.getServices()).andReturn(new java.util.HashMap<>()).anyTimes();
        org.easymock.EasyMock.expect(cluster.getCurrentStackVersion()).andReturn(stackId);
        org.easymock.EasyMock.expect(cluster.getServiceComponentHosts(org.easymock.EasyMock.anyObject(java.lang.String.class))).andAnswer(new org.easymock.IAnswer<java.util.List<org.apache.ambari.server.state.ServiceComponentHost>>() {
            @java.lang.Override
            public java.util.List<org.apache.ambari.server.state.ServiceComponentHost> answer() throws java.lang.Throwable {
                java.lang.String hostname = ((java.lang.String) (org.easymock.EasyMock.getCurrentArguments()[0]));
                if (hostname.equals(hostWithoutVersionableComponents)) {
                    return schsH2;
                } else {
                    return schsH1;
                }
            }
        }).anyTimes();
        org.easymock.EasyMock.expect(cluster.transitionHostsToInstalling(org.easymock.EasyMock.anyObject(org.apache.ambari.server.orm.entities.RepositoryVersionEntity.class), org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.repository.VersionDefinitionXml.class), org.easymock.EasyMock.eq(false))).andReturn(hostsNeedingInstallCommands).atLeastOnce();
        org.apache.ambari.server.agent.ExecutionCommand executionCommand = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.agent.ExecutionCommand.class);
        org.apache.ambari.server.actionmanager.ExecutionCommandWrapper executionCommandWrapper = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.actionmanager.ExecutionCommandWrapper.class);
        org.easymock.EasyMock.expect(executionCommandWrapper.getExecutionCommand()).andReturn(executionCommand).anyTimes();
        org.apache.ambari.server.actionmanager.Stage stage = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.actionmanager.Stage.class);
        org.easymock.EasyMock.expect(stage.getExecutionCommandWrapper(org.easymock.EasyMock.anyObject(java.lang.String.class), org.easymock.EasyMock.anyObject(java.lang.String.class))).andReturn(executionCommandWrapper).anyTimes();
        org.easymock.EasyMock.expect(executionCommand.getHostLevelParams()).andReturn(hostLevelParams).anyTimes();
        java.util.Map<org.apache.ambari.server.Role, java.lang.Float> successFactors = new java.util.HashMap<>();
        org.easymock.EasyMock.expect(stage.getSuccessFactors()).andReturn(successFactors).atLeastOnce();
        org.easymock.EasyMock.expect(stageFactory.createNew(org.easymock.EasyMock.anyLong(), org.easymock.EasyMock.anyObject(java.lang.String.class), org.easymock.EasyMock.anyObject(java.lang.String.class), org.easymock.EasyMock.anyLong(), org.easymock.EasyMock.anyObject(java.lang.String.class), org.easymock.EasyMock.anyObject(java.lang.String.class), org.easymock.EasyMock.anyObject(java.lang.String.class))).andReturn(stage).times(((int) (java.lang.Math.ceil(hostCount / org.apache.ambari.server.controller.internal.ClusterStackVersionResourceProviderTest.MAX_TASKS_PER_STAGE))));
        org.easymock.EasyMock.expect(repositoryVersionDAOMock.findByStackAndVersion(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.StackId.class), org.easymock.EasyMock.anyObject(java.lang.String.class))).andReturn(repoVersion);
        org.easymock.EasyMock.expect(actionManager.getRequestTasks(org.easymock.EasyMock.anyLong())).andReturn(java.util.Collections.emptyList()).anyTimes();
        org.apache.ambari.server.orm.entities.ClusterEntity clusterEntity = new org.apache.ambari.server.orm.entities.ClusterEntity();
        clusterEntity.setClusterId(1L);
        clusterEntity.setClusterName(clusterName);
        org.apache.ambari.server.utils.StageUtils.setTopologyManager(injector.getInstance(org.apache.ambari.server.topology.TopologyManager.class));
        org.apache.ambari.server.utils.StageUtils.setConfiguration(injector.getInstance(org.apache.ambari.server.configuration.Configuration.class));
        org.apache.ambari.server.controller.spi.ResourceProvider provider = createProvider(managementController);
        injector.injectMembers(provider);
        org.easymock.EasyMock.replay(managementController, response, clusters, cluster, repositoryVersionDAOMock, configHelper, schDatanode, schNamenode, schAMS, actionManager, executionCommand, executionCommandWrapper, stage, stageFactory);
        java.util.Set<java.util.Map<java.lang.String, java.lang.Object>> propertySet = new java.util.LinkedHashSet<>();
        java.util.Map<java.lang.String, java.lang.Object> properties = new java.util.LinkedHashMap<>();
        properties.put(org.apache.ambari.server.controller.internal.ClusterStackVersionResourceProvider.CLUSTER_STACK_VERSION_CLUSTER_NAME_PROPERTY_ID, "Cluster100");
        properties.put(org.apache.ambari.server.controller.internal.ClusterStackVersionResourceProvider.CLUSTER_STACK_VERSION_REPOSITORY_VERSION_PROPERTY_ID, "2.2.0.1-885");
        properties.put(org.apache.ambari.server.controller.internal.ClusterStackVersionResourceProvider.CLUSTER_STACK_VERSION_STACK_PROPERTY_ID, "HDP");
        properties.put(org.apache.ambari.server.controller.internal.ClusterStackVersionResourceProvider.CLUSTER_STACK_VERSION_VERSION_PROPERTY_ID, "2.1.1");
        propertySet.add(properties);
        org.apache.ambari.server.controller.spi.Request request = org.apache.ambari.server.controller.utilities.PropertyHelper.getCreateRequest(propertySet, null);
        org.springframework.security.core.context.SecurityContextHolder.getContext().setAuthentication(authentication);
        org.apache.ambari.server.controller.spi.RequestStatus status = provider.createResources(request);
        org.junit.Assert.assertNotNull(status);
        org.easymock.EasyMock.verify(managementController, response, clusters, stageFactory, stage);
        java.lang.Float successFactor = successFactors.get(org.apache.ambari.server.Role.INSTALL_PACKAGES);
        org.junit.Assert.assertEquals(java.lang.Float.valueOf(0.85F), successFactor);
    }

    @org.apache.ambari.annotations.Experimental(feature = org.apache.ambari.annotations.ExperimentalFeature.PATCH_UPGRADES)
    public void testCreateResourcesForPatch() throws java.lang.Exception {
        org.apache.ambari.server.controller.spi.Resource.Type type = org.apache.ambari.server.controller.spi.Resource.Type.ClusterStackVersion;
        org.apache.ambari.server.controller.AmbariManagementController managementController = org.easymock.EasyMock.createMock(org.apache.ambari.server.controller.AmbariManagementController.class);
        org.apache.ambari.server.state.Clusters clusters = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Clusters.class);
        org.apache.ambari.server.state.Cluster cluster = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Cluster.class);
        org.apache.ambari.server.state.StackId stackId = new org.apache.ambari.server.state.StackId("HDP", "2.0.1");
        java.io.File f = new java.io.File("src/test/resources/hbase_version_test.xml");
        org.apache.ambari.server.orm.entities.RepositoryVersionEntity repoVersion = new org.apache.ambari.server.orm.entities.RepositoryVersionEntity();
        repoVersion.setId(1L);
        repoVersion.addRepoOsEntities(org.apache.ambari.server.controller.internal.ClusterStackVersionResourceProviderTest.REPO_OS_ENTITIES);
        repoVersion.setVersionXml(org.apache.commons.io.IOUtils.toString(new java.io.FileInputStream(f)));
        repoVersion.setVersionXsd("version_definition.xsd");
        repoVersion.setType(org.apache.ambari.spi.RepositoryType.PATCH);
        ambariMetaInfo.getComponent("HDP", "2.1.1", "HBASE", "HBASE_MASTER").setVersionAdvertised(true);
        java.util.Map<java.lang.String, org.apache.ambari.server.state.Host> hostsForCluster = new java.util.HashMap<>();
        int hostCount = 10;
        for (int i = 0; i < hostCount; i++) {
            java.lang.String hostname = "host" + i;
            org.apache.ambari.server.state.Host host = org.easymock.EasyMock.createNiceMock(hostname, org.apache.ambari.server.state.Host.class);
            org.easymock.EasyMock.expect(host.getHostName()).andReturn(hostname).anyTimes();
            org.easymock.EasyMock.expect(host.getOsFamily()).andReturn("redhat6").anyTimes();
            org.easymock.EasyMock.expect(host.getMaintenanceState(org.easymock.EasyMock.anyLong())).andReturn(org.apache.ambari.server.state.MaintenanceState.OFF).anyTimes();
            org.easymock.EasyMock.expect(host.getAllHostVersions()).andReturn(java.util.Collections.emptyList()).anyTimes();
            org.easymock.EasyMock.replay(host);
            hostsForCluster.put(hostname, host);
        }
        org.apache.ambari.server.state.Service hdfsService = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Service.class);
        org.apache.ambari.server.state.Service hbaseService = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Service.class);
        org.easymock.EasyMock.expect(hdfsService.getName()).andReturn("HDFS").anyTimes();
        org.easymock.EasyMock.expect(hbaseService.getName()).andReturn("HBASE").anyTimes();
        org.easymock.EasyMock.expect(hdfsService.getServiceComponents()).andReturn(new java.util.HashMap<>());
        org.easymock.EasyMock.expect(hbaseService.getServiceComponents()).andReturn(new java.util.HashMap<>());
        java.util.Map<java.lang.String, org.apache.ambari.server.state.Service> serviceMap = new java.util.HashMap<>();
        serviceMap.put("HDFS", hdfsService);
        serviceMap.put("HBASE", hbaseService);
        final org.apache.ambari.server.state.ServiceComponentHost schDatanode = org.easymock.EasyMock.createMock(org.apache.ambari.server.state.ServiceComponentHost.class);
        org.easymock.EasyMock.expect(schDatanode.getServiceName()).andReturn("HDFS").anyTimes();
        org.easymock.EasyMock.expect(schDatanode.getServiceComponentName()).andReturn("DATANODE").anyTimes();
        final org.apache.ambari.server.state.ServiceComponentHost schNamenode = org.easymock.EasyMock.createMock(org.apache.ambari.server.state.ServiceComponentHost.class);
        org.easymock.EasyMock.expect(schNamenode.getServiceName()).andReturn("HDFS").anyTimes();
        org.easymock.EasyMock.expect(schNamenode.getServiceComponentName()).andReturn("NAMENODE").anyTimes();
        final org.apache.ambari.server.state.ServiceComponentHost schAMS = org.easymock.EasyMock.createMock(org.apache.ambari.server.state.ServiceComponentHost.class);
        org.easymock.EasyMock.expect(schAMS.getServiceName()).andReturn("AMBARI_METRICS").anyTimes();
        org.easymock.EasyMock.expect(schAMS.getServiceComponentName()).andReturn("METRICS_COLLECTOR").anyTimes();
        final org.apache.ambari.server.state.ServiceComponentHost schHBM = org.easymock.EasyMock.createMock(org.apache.ambari.server.state.ServiceComponentHost.class);
        org.easymock.EasyMock.expect(schHBM.getServiceName()).andReturn("HBASE").anyTimes();
        org.easymock.EasyMock.expect(schHBM.getServiceComponentName()).andReturn("HBASE_MASTER").anyTimes();
        final java.util.List<org.apache.ambari.server.state.ServiceComponentHost> schsH1 = java.util.Arrays.asList(schDatanode, schNamenode, schAMS);
        final java.util.List<org.apache.ambari.server.state.ServiceComponentHost> schsH2 = java.util.Arrays.asList(schAMS);
        final java.util.List<org.apache.ambari.server.state.ServiceComponentHost> schsH3 = java.util.Arrays.asList(schHBM);
        org.apache.ambari.server.state.ServiceOsSpecific.Package hdfsPackage = new org.apache.ambari.server.state.ServiceOsSpecific.Package();
        hdfsPackage.setName("hdfs");
        java.util.List<org.apache.ambari.server.state.ServiceOsSpecific.Package> packages = java.util.Collections.singletonList(hdfsPackage);
        org.apache.ambari.server.actionmanager.ActionManager actionManager = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.actionmanager.ActionManager.class);
        org.apache.ambari.server.controller.RequestStatusResponse response = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.RequestStatusResponse.class);
        org.apache.ambari.server.controller.ResourceProviderFactory resourceProviderFactory = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.ResourceProviderFactory.class);
        org.apache.ambari.server.controller.spi.ResourceProvider csvResourceProvider = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.internal.ClusterStackVersionResourceProvider.class);
        org.apache.ambari.server.controller.internal.AbstractControllerResourceProvider.init(resourceProviderFactory);
        java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.lang.String>> hostConfigTags = new java.util.HashMap<>();
        org.easymock.EasyMock.expect(configHelper.getEffectiveDesiredTags(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.cluster.ClusterImpl.class), org.easymock.EasyMock.anyObject(java.lang.String.class))).andReturn(hostConfigTags);
        org.easymock.EasyMock.expect(managementController.getClusters()).andReturn(clusters).anyTimes();
        org.easymock.EasyMock.expect(managementController.getAmbariMetaInfo()).andReturn(ambariMetaInfo).anyTimes();
        org.easymock.EasyMock.expect(managementController.getAuthName()).andReturn("admin").anyTimes();
        org.easymock.EasyMock.expect(managementController.getActionManager()).andReturn(actionManager).anyTimes();
        org.easymock.EasyMock.expect(managementController.getJdkResourceUrl()).andReturn("/JdkResourceUrl").anyTimes();
        org.easymock.EasyMock.expect(managementController.getPackagesForServiceHost(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.ServiceInfo.class), org.easymock.EasyMock.anyObject(), org.easymock.EasyMock.anyObject(java.lang.String.class))).andReturn(packages).times(1);
        org.easymock.EasyMock.expect(resourceProviderFactory.getHostResourceProvider(org.easymock.EasyMock.eq(managementController))).andReturn(csvResourceProvider).anyTimes();
        org.easymock.EasyMock.expect(clusters.getCluster(org.easymock.EasyMock.anyObject(java.lang.String.class))).andReturn(cluster);
        org.easymock.EasyMock.expect(clusters.getHostsForCluster(org.easymock.EasyMock.anyObject(java.lang.String.class))).andReturn(hostsForCluster).anyTimes();
        java.lang.String clusterName = "Cluster100";
        org.easymock.EasyMock.expect(cluster.getClusterId()).andReturn(1L).anyTimes();
        org.easymock.EasyMock.expect(cluster.getHosts()).andReturn(hostsForCluster.values()).atLeastOnce();
        org.easymock.EasyMock.expect(cluster.getServices()).andReturn(serviceMap).anyTimes();
        org.easymock.EasyMock.expect(cluster.getCurrentStackVersion()).andReturn(stackId);
        org.easymock.EasyMock.expect(cluster.getServiceComponentHosts(org.easymock.EasyMock.anyObject(java.lang.String.class))).andAnswer(new org.easymock.IAnswer<java.util.List<org.apache.ambari.server.state.ServiceComponentHost>>() {
            @java.lang.Override
            public java.util.List<org.apache.ambari.server.state.ServiceComponentHost> answer() throws java.lang.Throwable {
                java.lang.String hostname = ((java.lang.String) (org.easymock.EasyMock.getCurrentArguments()[0]));
                if (hostname.equals("host2")) {
                    return schsH2;
                } else if (hostname.equals("host3")) {
                    return schsH3;
                } else {
                    return schsH1;
                }
            }
        }).anyTimes();
        org.apache.ambari.server.agent.ExecutionCommand executionCommand = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.agent.ExecutionCommand.class);
        org.apache.ambari.server.actionmanager.ExecutionCommandWrapper executionCommandWrapper = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.actionmanager.ExecutionCommandWrapper.class);
        org.easymock.EasyMock.expect(executionCommandWrapper.getExecutionCommand()).andReturn(executionCommand).anyTimes();
        org.apache.ambari.server.actionmanager.Stage stage = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.actionmanager.Stage.class);
        org.easymock.EasyMock.expect(stage.getExecutionCommandWrapper(org.easymock.EasyMock.anyObject(java.lang.String.class), org.easymock.EasyMock.anyObject(java.lang.String.class))).andReturn(executionCommandWrapper).anyTimes();
        java.util.Map<org.apache.ambari.server.Role, java.lang.Float> successFactors = new java.util.HashMap<>();
        org.easymock.EasyMock.expect(stage.getSuccessFactors()).andReturn(successFactors).atLeastOnce();
        org.easymock.EasyMock.expect(stageFactory.createNew(org.easymock.EasyMock.anyLong(), org.easymock.EasyMock.anyObject(java.lang.String.class), org.easymock.EasyMock.anyObject(java.lang.String.class), org.easymock.EasyMock.anyLong(), org.easymock.EasyMock.anyObject(java.lang.String.class), org.easymock.EasyMock.anyObject(java.lang.String.class), org.easymock.EasyMock.anyObject(java.lang.String.class))).andReturn(stage).times(((int) (java.lang.Math.ceil(hostCount / org.apache.ambari.server.controller.internal.ClusterStackVersionResourceProviderTest.MAX_TASKS_PER_STAGE))));
        org.easymock.EasyMock.expect(repositoryVersionDAOMock.findByStackAndVersion(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.StackId.class), org.easymock.EasyMock.anyObject(java.lang.String.class))).andReturn(repoVersion);
        org.easymock.Capture<org.apache.ambari.server.actionmanager.Request> c = org.easymock.Capture.newInstance();
        org.easymock.Capture<org.apache.ambari.server.controller.ExecuteActionRequest> ear = org.easymock.Capture.newInstance();
        actionManager.sendActions(org.easymock.EasyMock.capture(c), org.easymock.EasyMock.capture(ear));
        org.easymock.EasyMock.expectLastCall().atLeastOnce();
        org.easymock.EasyMock.expect(actionManager.getRequestTasks(org.easymock.EasyMock.anyLong())).andReturn(java.util.Collections.emptyList()).anyTimes();
        org.apache.ambari.server.orm.entities.ClusterEntity clusterEntity = new org.apache.ambari.server.orm.entities.ClusterEntity();
        clusterEntity.setClusterId(1L);
        clusterEntity.setClusterName(clusterName);
        org.apache.ambari.server.topology.TopologyManager topologyManager = injector.getInstance(org.apache.ambari.server.topology.TopologyManager.class);
        org.apache.ambari.server.utils.StageUtils.setTopologyManager(topologyManager);
        org.easymock.EasyMock.replay(managementController, response, clusters, hdfsService, hbaseService, resourceProviderFactory, csvResourceProvider, cluster, repositoryVersionDAOMock, configHelper, schDatanode, schNamenode, schAMS, schHBM, actionManager, executionCommand, executionCommandWrapper, stage, stageFactory);
        org.apache.ambari.server.controller.spi.ResourceProvider provider = org.apache.ambari.server.controller.internal.AbstractControllerResourceProvider.getResourceProvider(type, managementController);
        injector.injectMembers(provider);
        java.util.Set<java.util.Map<java.lang.String, java.lang.Object>> propertySet = new java.util.LinkedHashSet<>();
        java.util.Map<java.lang.String, java.lang.Object> properties = new java.util.LinkedHashMap<>();
        properties.put(org.apache.ambari.server.controller.internal.ClusterStackVersionResourceProvider.CLUSTER_STACK_VERSION_CLUSTER_NAME_PROPERTY_ID, "Cluster100");
        properties.put(org.apache.ambari.server.controller.internal.ClusterStackVersionResourceProvider.CLUSTER_STACK_VERSION_REPOSITORY_VERSION_PROPERTY_ID, "2.2.0.1-885");
        properties.put(org.apache.ambari.server.controller.internal.ClusterStackVersionResourceProvider.CLUSTER_STACK_VERSION_STACK_PROPERTY_ID, "HDP");
        properties.put(org.apache.ambari.server.controller.internal.ClusterStackVersionResourceProvider.CLUSTER_STACK_VERSION_VERSION_PROPERTY_ID, "2.1.1");
        propertySet.add(properties);
        org.apache.ambari.server.controller.spi.Request request = org.apache.ambari.server.controller.utilities.PropertyHelper.getCreateRequest(propertySet, null);
        org.apache.ambari.server.controller.spi.RequestStatus status = provider.createResources(request);
        org.junit.Assert.assertNotNull(status);
        org.easymock.EasyMock.verify(managementController, response, clusters, stageFactory, stage);
        java.lang.Float successFactor = successFactors.get(org.apache.ambari.server.Role.INSTALL_PACKAGES);
        org.junit.Assert.assertEquals(java.lang.Float.valueOf(0.85F), successFactor);
    }

    @org.junit.Test
    public void testCreateResourcesWithRepoDefinitionAsAdministrator() throws java.lang.Exception {
        testCreateResourcesWithRepoDefinition(org.apache.ambari.server.security.TestAuthenticationFactory.createAdministrator());
    }

    @org.junit.Test
    public void testCreateResourcesWithRepoDefinitionAsClusterAdministrator() throws java.lang.Exception {
        testCreateResourcesWithRepoDefinition(org.apache.ambari.server.security.TestAuthenticationFactory.createClusterAdministrator());
    }

    @org.junit.Test(expected = org.apache.ambari.server.security.authorization.AuthorizationException.class)
    public void testCreateResourcesWithRepoDefinitionAsClusterOperator() throws java.lang.Exception {
        testCreateResourcesWithRepoDefinition(org.apache.ambari.server.security.TestAuthenticationFactory.createClusterOperator());
    }

    private void testCreateResourcesWithRepoDefinition(org.springframework.security.core.Authentication authentication) throws java.lang.Exception {
        org.apache.ambari.server.state.Cluster cluster = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Cluster.class);
        org.apache.ambari.server.state.StackId stackId = new org.apache.ambari.server.state.StackId("HDP", "2.0.1");
        java.io.File f = new java.io.File("src/test/resources/hbase_version_test.xml");
        org.apache.ambari.server.orm.entities.StackEntity stackEntity = new org.apache.ambari.server.orm.entities.StackEntity();
        stackEntity.setStackName("HDP");
        stackEntity.setStackVersion("2.1.1");
        org.apache.ambari.server.orm.entities.RepositoryVersionEntity repoVersion = new org.apache.ambari.server.orm.entities.RepositoryVersionEntity();
        repoVersion.setId(1L);
        repoVersion.addRepoOsEntities(org.apache.ambari.server.controller.internal.ClusterStackVersionResourceProviderTest.REPO_OS_ENTITIES);
        repoVersion.setVersionXml(org.apache.commons.io.IOUtils.toString(new java.io.FileInputStream(f)));
        repoVersion.setVersionXsd("version_definition.xsd");
        repoVersion.setType(org.apache.ambari.spi.RepositoryType.STANDARD);
        repoVersion.setStack(stackEntity);
        ambariMetaInfo.getComponent("HDP", "2.1.1", "HBASE", "HBASE_MASTER").setVersionAdvertised(true);
        final java.lang.String hostWithoutVersionableComponents = "host3";
        java.util.List<org.apache.ambari.server.state.Host> hostsNeedingInstallCommands = new java.util.ArrayList<>();
        java.util.Map<java.lang.String, org.apache.ambari.server.state.Host> hostsForCluster = new java.util.HashMap<>();
        int hostCount = 10;
        for (int i = 0; i < hostCount; i++) {
            java.lang.String hostname = "host" + i;
            org.apache.ambari.server.state.Host host = org.easymock.EasyMock.createNiceMock(hostname, org.apache.ambari.server.state.Host.class);
            org.easymock.EasyMock.expect(host.getHostName()).andReturn(hostname).anyTimes();
            org.easymock.EasyMock.expect(host.getOsFamily()).andReturn("redhat6").anyTimes();
            org.easymock.EasyMock.expect(host.getMaintenanceState(org.easymock.EasyMock.anyLong())).andReturn(org.apache.ambari.server.state.MaintenanceState.OFF).anyTimes();
            org.easymock.EasyMock.expect(host.getAllHostVersions()).andReturn(java.util.Collections.emptyList()).anyTimes();
            org.easymock.EasyMock.replay(host);
            hostsForCluster.put(hostname, host);
            if (!org.apache.commons.lang.StringUtils.equals(hostWithoutVersionableComponents, hostname)) {
                hostsNeedingInstallCommands.add(host);
            }
        }
        org.apache.ambari.server.state.Service hdfsService = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Service.class);
        org.apache.ambari.server.state.Service hbaseService = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Service.class);
        org.easymock.EasyMock.expect(hdfsService.getName()).andReturn("HDFS").anyTimes();
        org.easymock.EasyMock.expect(hbaseService.getName()).andReturn("HBASE").anyTimes();
        org.easymock.EasyMock.expect(hdfsService.getServiceComponents()).andReturn(new java.util.HashMap<>());
        org.easymock.EasyMock.expect(hbaseService.getServiceComponents()).andReturn(new java.util.HashMap<>());
        java.util.Map<java.lang.String, org.apache.ambari.server.state.Service> serviceMap = new java.util.HashMap<>();
        serviceMap.put("HDFS", hdfsService);
        serviceMap.put("HBASE", hbaseService);
        final org.apache.ambari.server.state.ServiceComponentHost schDatanode = org.easymock.EasyMock.createMock(org.apache.ambari.server.state.ServiceComponentHost.class);
        org.easymock.EasyMock.expect(schDatanode.getServiceName()).andReturn("HDFS").anyTimes();
        org.easymock.EasyMock.expect(schDatanode.getServiceComponentName()).andReturn("DATANODE").anyTimes();
        final org.apache.ambari.server.state.ServiceComponentHost schNamenode = org.easymock.EasyMock.createMock(org.apache.ambari.server.state.ServiceComponentHost.class);
        org.easymock.EasyMock.expect(schNamenode.getServiceName()).andReturn("HDFS").anyTimes();
        org.easymock.EasyMock.expect(schNamenode.getServiceComponentName()).andReturn("NAMENODE").anyTimes();
        final org.apache.ambari.server.state.ServiceComponentHost schHBM = org.easymock.EasyMock.createMock(org.apache.ambari.server.state.ServiceComponentHost.class);
        org.easymock.EasyMock.expect(schHBM.getServiceName()).andReturn("HBASE").anyTimes();
        org.easymock.EasyMock.expect(schHBM.getServiceComponentName()).andReturn("HBASE_MASTER").anyTimes();
        final java.util.List<org.apache.ambari.server.state.ServiceComponentHost> schsH1 = java.util.Arrays.asList(schDatanode, schNamenode);
        final java.util.List<org.apache.ambari.server.state.ServiceComponentHost> schsH2 = java.util.Arrays.asList(schDatanode);
        final java.util.List<org.apache.ambari.server.state.ServiceComponentHost> schsH3 = java.util.Arrays.asList(schHBM);
        org.apache.ambari.server.state.ServiceOsSpecific.Package hdfsPackage = new org.apache.ambari.server.state.ServiceOsSpecific.Package();
        hdfsPackage.setName("hdfs");
        java.util.List<org.apache.ambari.server.state.ServiceOsSpecific.Package> packages = java.util.Collections.singletonList(hdfsPackage);
        org.apache.ambari.server.actionmanager.ActionManager actionManager = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.actionmanager.ActionManager.class);
        org.apache.ambari.server.controller.RequestStatusResponse response = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.RequestStatusResponse.class);
        org.apache.ambari.server.controller.ResourceProviderFactory resourceProviderFactory = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.ResourceProviderFactory.class);
        org.apache.ambari.server.controller.spi.ResourceProvider csvResourceProvider = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.ResourceProvider.class);
        org.apache.ambari.server.controller.internal.AbstractControllerResourceProvider.init(resourceProviderFactory);
        java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.lang.String>> hostConfigTags = new java.util.HashMap<>();
        org.easymock.EasyMock.expect(configHelper.getEffectiveDesiredTags(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.cluster.ClusterImpl.class), org.easymock.EasyMock.anyObject(java.lang.String.class))).andReturn(hostConfigTags);
        org.easymock.EasyMock.expect(managementController.getClusters()).andReturn(clusters).anyTimes();
        org.easymock.EasyMock.expect(managementController.getAmbariMetaInfo()).andReturn(ambariMetaInfo).anyTimes();
        org.easymock.EasyMock.expect(managementController.getAuthName()).andReturn("admin").anyTimes();
        org.easymock.EasyMock.expect(managementController.getActionManager()).andReturn(actionManager).anyTimes();
        org.easymock.EasyMock.expect(managementController.getJdkResourceUrl()).andReturn("/JdkResourceUrl").anyTimes();
        org.easymock.EasyMock.expect(managementController.getPackagesForServiceHost(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.ServiceInfo.class), org.easymock.EasyMock.anyObject(), org.easymock.EasyMock.anyObject(java.lang.String.class))).andReturn(packages).anyTimes();
        org.easymock.EasyMock.expect(resourceProviderFactory.getHostResourceProvider(org.easymock.EasyMock.eq(managementController))).andReturn(csvResourceProvider).anyTimes();
        org.easymock.EasyMock.expect(managementController.findConfigurationTagsWithOverrides(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.Cluster.class), org.easymock.EasyMock.anyString(), org.easymock.EasyMock.anyObject())).andReturn(new java.util.HashMap<java.lang.String, java.util.Map<java.lang.String, java.lang.String>>()).anyTimes();
        org.easymock.EasyMock.expect(clusters.getCluster(org.easymock.EasyMock.anyObject(java.lang.String.class))).andReturn(cluster);
        org.easymock.EasyMock.expect(clusters.getHostsForCluster(org.easymock.EasyMock.anyObject(java.lang.String.class))).andReturn(hostsForCluster).anyTimes();
        java.lang.String clusterName = "Cluster100";
        org.easymock.EasyMock.expect(cluster.getClusterId()).andReturn(1L).anyTimes();
        org.easymock.EasyMock.expect(cluster.getHosts()).andReturn(hostsForCluster.values()).atLeastOnce();
        org.easymock.EasyMock.expect(cluster.getServices()).andReturn(serviceMap).anyTimes();
        org.easymock.EasyMock.expect(cluster.getCurrentStackVersion()).andReturn(stackId);
        org.easymock.EasyMock.expect(cluster.getServiceComponentHosts(org.easymock.EasyMock.anyObject(java.lang.String.class))).andAnswer(new org.easymock.IAnswer<java.util.List<org.apache.ambari.server.state.ServiceComponentHost>>() {
            @java.lang.Override
            public java.util.List<org.apache.ambari.server.state.ServiceComponentHost> answer() throws java.lang.Throwable {
                java.lang.String hostname = ((java.lang.String) (org.easymock.EasyMock.getCurrentArguments()[0]));
                if (hostname.equals("host2")) {
                    return schsH2;
                } else if (hostname.equals("host3")) {
                    return schsH3;
                } else {
                    return schsH1;
                }
            }
        }).anyTimes();
        org.easymock.EasyMock.expect(cluster.transitionHostsToInstalling(org.easymock.EasyMock.anyObject(org.apache.ambari.server.orm.entities.RepositoryVersionEntity.class), org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.repository.VersionDefinitionXml.class), org.easymock.EasyMock.eq(false))).andReturn(hostsNeedingInstallCommands).atLeastOnce();
        org.apache.ambari.server.agent.ExecutionCommand executionCommand = new org.apache.ambari.server.agent.ExecutionCommand();
        org.apache.ambari.server.actionmanager.ExecutionCommandWrapper executionCommandWrapper = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.actionmanager.ExecutionCommandWrapper.class);
        org.easymock.EasyMock.expect(executionCommandWrapper.getExecutionCommand()).andReturn(executionCommand).anyTimes();
        org.apache.ambari.server.actionmanager.Stage stage = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.actionmanager.Stage.class);
        org.easymock.EasyMock.expect(stage.getExecutionCommandWrapper(org.easymock.EasyMock.anyObject(java.lang.String.class), org.easymock.EasyMock.anyObject(java.lang.String.class))).andReturn(executionCommandWrapper).anyTimes();
        java.util.Map<org.apache.ambari.server.Role, java.lang.Float> successFactors = new java.util.HashMap<>();
        org.easymock.EasyMock.expect(stage.getSuccessFactors()).andReturn(successFactors).atLeastOnce();
        org.easymock.EasyMock.expect(stageFactory.createNew(org.easymock.EasyMock.anyLong(), org.easymock.EasyMock.anyObject(java.lang.String.class), org.easymock.EasyMock.anyObject(java.lang.String.class), org.easymock.EasyMock.anyLong(), org.easymock.EasyMock.anyObject(java.lang.String.class), org.easymock.EasyMock.anyObject(java.lang.String.class), org.easymock.EasyMock.anyObject(java.lang.String.class))).andReturn(stage).times(((int) (java.lang.Math.ceil(hostCount / org.apache.ambari.server.controller.internal.ClusterStackVersionResourceProviderTest.MAX_TASKS_PER_STAGE))));
        org.easymock.EasyMock.expect(repositoryVersionDAOMock.findByStackAndVersion(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.StackId.class), org.easymock.EasyMock.anyObject(java.lang.String.class))).andReturn(repoVersion);
        org.easymock.Capture<org.apache.ambari.server.actionmanager.Request> c = org.easymock.Capture.newInstance();
        org.easymock.Capture<org.apache.ambari.server.controller.ExecuteActionRequest> ear = org.easymock.Capture.newInstance();
        actionManager.sendActions(org.easymock.EasyMock.capture(c), org.easymock.EasyMock.capture(ear));
        org.easymock.EasyMock.expectLastCall().atLeastOnce();
        org.easymock.EasyMock.expect(actionManager.getRequestTasks(org.easymock.EasyMock.anyLong())).andReturn(java.util.Collections.emptyList()).anyTimes();
        org.apache.ambari.server.orm.entities.ClusterEntity clusterEntity = new org.apache.ambari.server.orm.entities.ClusterEntity();
        clusterEntity.setClusterId(1L);
        clusterEntity.setClusterName(clusterName);
        org.apache.ambari.server.utils.StageUtils.setTopologyManager(injector.getInstance(org.apache.ambari.server.topology.TopologyManager.class));
        org.apache.ambari.server.utils.StageUtils.setConfiguration(injector.getInstance(org.apache.ambari.server.configuration.Configuration.class));
        org.easymock.EasyMock.replay(managementController, response, clusters, hdfsService, hbaseService, resourceProviderFactory, csvResourceProvider, cluster, repositoryVersionDAOMock, configHelper, schDatanode, schNamenode, schHBM, actionManager, executionCommandWrapper, stage, stageFactory);
        org.apache.ambari.server.controller.spi.ResourceProvider provider = createProvider(managementController);
        injector.injectMembers(provider);
        java.util.Set<java.util.Map<java.lang.String, java.lang.Object>> propertySet = new java.util.LinkedHashSet<>();
        java.util.Map<java.lang.String, java.lang.Object> properties = new java.util.LinkedHashMap<>();
        properties.put(org.apache.ambari.server.controller.internal.ClusterStackVersionResourceProvider.CLUSTER_STACK_VERSION_CLUSTER_NAME_PROPERTY_ID, "Cluster100");
        properties.put(org.apache.ambari.server.controller.internal.ClusterStackVersionResourceProvider.CLUSTER_STACK_VERSION_REPOSITORY_VERSION_PROPERTY_ID, "2.2.0.1-885");
        properties.put(org.apache.ambari.server.controller.internal.ClusterStackVersionResourceProvider.CLUSTER_STACK_VERSION_STACK_PROPERTY_ID, "HDP");
        properties.put(org.apache.ambari.server.controller.internal.ClusterStackVersionResourceProvider.CLUSTER_STACK_VERSION_VERSION_PROPERTY_ID, "2.1.1");
        propertySet.add(properties);
        org.apache.ambari.server.controller.spi.Request request = org.apache.ambari.server.controller.utilities.PropertyHelper.getCreateRequest(propertySet, null);
        org.springframework.security.core.context.SecurityContextHolder.getContext().setAuthentication(authentication);
        org.apache.ambari.server.controller.spi.RequestStatus status = provider.createResources(request);
        org.junit.Assert.assertNotNull(status);
        org.easymock.EasyMock.verify(managementController, response, clusters, stageFactory, stage);
        java.lang.Float successFactor = successFactors.get(org.apache.ambari.server.Role.INSTALL_PACKAGES);
        org.junit.Assert.assertEquals(java.lang.Float.valueOf(0.85F), successFactor);
        org.junit.Assert.assertTrue(executionCommand.getRoleParams().containsKey(org.apache.ambari.server.agent.ExecutionCommand.KeyNames.PACKAGE_LIST));
        org.junit.Assert.assertTrue(executionCommand.getRoleParams().containsKey("stack_id"));
    }

    @org.junit.Test
    public void testCreateResourcesWithNonManagedOSAsAdministrator() throws java.lang.Exception {
        testCreateResourcesWithNonManagedOS(org.apache.ambari.server.security.TestAuthenticationFactory.createAdministrator());
    }

    @org.junit.Test
    public void testCreateResourcesWithNonManagedOSAsClusterAdministrator() throws java.lang.Exception {
        testCreateResourcesWithNonManagedOS(org.apache.ambari.server.security.TestAuthenticationFactory.createClusterAdministrator());
    }

    @org.junit.Test(expected = org.apache.ambari.server.security.authorization.AuthorizationException.class)
    public void testCreateResourcesWithNonManagedOSAsClusterOperator() throws java.lang.Exception {
        testCreateResourcesWithNonManagedOS(org.apache.ambari.server.security.TestAuthenticationFactory.createClusterOperator());
    }

    private void testCreateResourcesWithNonManagedOS(org.springframework.security.core.Authentication authentication) throws java.lang.Exception {
        org.apache.ambari.server.state.Cluster cluster = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Cluster.class);
        org.apache.ambari.server.state.StackId stackId = new org.apache.ambari.server.state.StackId("HDP", "2.0.1");
        java.io.File f = new java.io.File("src/test/resources/hbase_version_test.xml");
        org.apache.ambari.server.orm.entities.StackEntity stackEntity = new org.apache.ambari.server.orm.entities.StackEntity();
        stackEntity.setStackName("HDP");
        stackEntity.setStackVersion("2.1.1");
        org.apache.ambari.server.orm.entities.RepositoryVersionEntity repoVersion = new org.apache.ambari.server.orm.entities.RepositoryVersionEntity();
        repoVersion.setId(1L);
        repoVersion.addRepoOsEntities(org.apache.ambari.server.controller.internal.ClusterStackVersionResourceProviderTest.REPO_OS_NOT_MANAGED);
        repoVersion.setVersionXml(org.apache.commons.io.IOUtils.toString(new java.io.FileInputStream(f)));
        repoVersion.setVersionXsd("version_definition.xsd");
        repoVersion.setType(org.apache.ambari.spi.RepositoryType.STANDARD);
        repoVersion.setStack(stackEntity);
        ambariMetaInfo.getComponent("HDP", "2.1.1", "HBASE", "HBASE_MASTER").setVersionAdvertised(true);
        final java.lang.String hostWithoutVersionableComponents = "host3";
        java.util.List<org.apache.ambari.server.state.Host> hostsNeedingInstallCommands = new java.util.ArrayList<>();
        java.util.Map<java.lang.String, org.apache.ambari.server.state.Host> hostsForCluster = new java.util.HashMap<>();
        int hostCount = 10;
        for (int i = 0; i < hostCount; i++) {
            java.lang.String hostname = "host" + i;
            org.apache.ambari.server.state.Host host = org.easymock.EasyMock.createNiceMock(hostname, org.apache.ambari.server.state.Host.class);
            org.easymock.EasyMock.expect(host.getHostName()).andReturn(hostname).anyTimes();
            org.easymock.EasyMock.expect(host.getOsFamily()).andReturn("redhat6").anyTimes();
            org.easymock.EasyMock.expect(host.getMaintenanceState(org.easymock.EasyMock.anyLong())).andReturn(org.apache.ambari.server.state.MaintenanceState.OFF).anyTimes();
            org.easymock.EasyMock.expect(host.getAllHostVersions()).andReturn(java.util.Collections.emptyList()).anyTimes();
            org.easymock.EasyMock.replay(host);
            hostsForCluster.put(hostname, host);
            if (!org.apache.commons.lang.StringUtils.equals(hostWithoutVersionableComponents, hostname)) {
                hostsNeedingInstallCommands.add(host);
            }
        }
        org.apache.ambari.server.state.Service hdfsService = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Service.class);
        org.apache.ambari.server.state.Service hbaseService = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Service.class);
        org.easymock.EasyMock.expect(hdfsService.getName()).andReturn("HDFS").anyTimes();
        org.easymock.EasyMock.expect(hbaseService.getName()).andReturn("HBASE").anyTimes();
        org.easymock.EasyMock.expect(hdfsService.getServiceComponents()).andReturn(new java.util.HashMap<>());
        org.easymock.EasyMock.expect(hbaseService.getServiceComponents()).andReturn(new java.util.HashMap<>());
        java.util.Map<java.lang.String, org.apache.ambari.server.state.Service> serviceMap = new java.util.HashMap<>();
        serviceMap.put("HDFS", hdfsService);
        serviceMap.put("HBASE", hbaseService);
        final org.apache.ambari.server.state.ServiceComponentHost schDatanode = org.easymock.EasyMock.createMock(org.apache.ambari.server.state.ServiceComponentHost.class);
        org.easymock.EasyMock.expect(schDatanode.getServiceName()).andReturn("HDFS").anyTimes();
        org.easymock.EasyMock.expect(schDatanode.getServiceComponentName()).andReturn("DATANODE").anyTimes();
        final org.apache.ambari.server.state.ServiceComponentHost schNamenode = org.easymock.EasyMock.createMock(org.apache.ambari.server.state.ServiceComponentHost.class);
        org.easymock.EasyMock.expect(schNamenode.getServiceName()).andReturn("HDFS").anyTimes();
        org.easymock.EasyMock.expect(schNamenode.getServiceComponentName()).andReturn("NAMENODE").anyTimes();
        final org.apache.ambari.server.state.ServiceComponentHost schHBM = org.easymock.EasyMock.createMock(org.apache.ambari.server.state.ServiceComponentHost.class);
        org.easymock.EasyMock.expect(schHBM.getServiceName()).andReturn("HBASE").anyTimes();
        org.easymock.EasyMock.expect(schHBM.getServiceComponentName()).andReturn("HBASE_MASTER").anyTimes();
        final java.util.List<org.apache.ambari.server.state.ServiceComponentHost> schsH1 = java.util.Arrays.asList(schDatanode, schNamenode);
        final java.util.List<org.apache.ambari.server.state.ServiceComponentHost> schsH2 = java.util.Arrays.asList(schDatanode);
        final java.util.List<org.apache.ambari.server.state.ServiceComponentHost> schsH3 = java.util.Arrays.asList(schHBM);
        org.apache.ambari.server.state.ServiceOsSpecific.Package hdfsPackage = new org.apache.ambari.server.state.ServiceOsSpecific.Package();
        hdfsPackage.setName("hdfs");
        java.util.List<org.apache.ambari.server.state.ServiceOsSpecific.Package> packages = java.util.Collections.singletonList(hdfsPackage);
        org.apache.ambari.server.actionmanager.ActionManager actionManager = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.actionmanager.ActionManager.class);
        org.apache.ambari.server.controller.RequestStatusResponse response = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.RequestStatusResponse.class);
        org.apache.ambari.server.controller.spi.ResourceProvider csvResourceProvider = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.ResourceProvider.class);
        java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.lang.String>> hostConfigTags = new java.util.HashMap<>();
        org.easymock.EasyMock.expect(configHelper.getEffectiveDesiredTags(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.cluster.ClusterImpl.class), org.easymock.EasyMock.anyObject(java.lang.String.class))).andReturn(hostConfigTags);
        org.easymock.EasyMock.expect(managementController.getClusters()).andReturn(clusters).anyTimes();
        org.easymock.EasyMock.expect(managementController.getAmbariMetaInfo()).andReturn(ambariMetaInfo).anyTimes();
        org.easymock.EasyMock.expect(managementController.getAuthName()).andReturn("admin").anyTimes();
        org.easymock.EasyMock.expect(managementController.getActionManager()).andReturn(actionManager).anyTimes();
        org.easymock.EasyMock.expect(managementController.getJdkResourceUrl()).andReturn("/JdkResourceUrl").anyTimes();
        org.easymock.EasyMock.expect(managementController.getPackagesForServiceHost(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.ServiceInfo.class), org.easymock.EasyMock.anyObject(), org.easymock.EasyMock.anyObject(java.lang.String.class))).andReturn(packages).anyTimes();
        org.easymock.EasyMock.expect(managementController.findConfigurationTagsWithOverrides(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.Cluster.class), org.easymock.EasyMock.anyString(), org.easymock.EasyMock.anyObject())).andReturn(new java.util.HashMap<java.lang.String, java.util.Map<java.lang.String, java.lang.String>>()).anyTimes();
        org.easymock.EasyMock.expect(clusters.getCluster(org.easymock.EasyMock.anyObject(java.lang.String.class))).andReturn(cluster);
        org.easymock.EasyMock.expect(clusters.getHostsForCluster(org.easymock.EasyMock.anyObject(java.lang.String.class))).andReturn(hostsForCluster).anyTimes();
        java.lang.String clusterName = "Cluster100";
        org.easymock.EasyMock.expect(cluster.getClusterId()).andReturn(1L).anyTimes();
        org.easymock.EasyMock.expect(cluster.getHosts()).andReturn(hostsForCluster.values()).atLeastOnce();
        org.easymock.EasyMock.expect(cluster.getServices()).andReturn(serviceMap).anyTimes();
        org.easymock.EasyMock.expect(cluster.getCurrentStackVersion()).andReturn(stackId);
        org.easymock.EasyMock.expect(cluster.getServiceComponentHosts(org.easymock.EasyMock.anyObject(java.lang.String.class))).andAnswer(new org.easymock.IAnswer<java.util.List<org.apache.ambari.server.state.ServiceComponentHost>>() {
            @java.lang.Override
            public java.util.List<org.apache.ambari.server.state.ServiceComponentHost> answer() throws java.lang.Throwable {
                java.lang.String hostname = ((java.lang.String) (org.easymock.EasyMock.getCurrentArguments()[0]));
                if (hostname.equals("host2")) {
                    return schsH2;
                } else if (hostname.equals("host3")) {
                    return schsH3;
                } else {
                    return schsH1;
                }
            }
        }).anyTimes();
        org.easymock.EasyMock.expect(cluster.transitionHostsToInstalling(org.easymock.EasyMock.anyObject(org.apache.ambari.server.orm.entities.RepositoryVersionEntity.class), org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.repository.VersionDefinitionXml.class), org.easymock.EasyMock.eq(false))).andReturn(hostsNeedingInstallCommands).atLeastOnce();
        org.apache.ambari.server.agent.ExecutionCommand executionCommand = new org.apache.ambari.server.agent.ExecutionCommand();
        org.apache.ambari.server.actionmanager.ExecutionCommandWrapper executionCommandWrapper = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.actionmanager.ExecutionCommandWrapper.class);
        org.easymock.EasyMock.expect(executionCommandWrapper.getExecutionCommand()).andReturn(executionCommand).anyTimes();
        org.apache.ambari.server.actionmanager.Stage stage = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.actionmanager.Stage.class);
        org.easymock.EasyMock.expect(stage.getExecutionCommandWrapper(org.easymock.EasyMock.anyObject(java.lang.String.class), org.easymock.EasyMock.anyObject(java.lang.String.class))).andReturn(executionCommandWrapper).anyTimes();
        java.util.Map<org.apache.ambari.server.Role, java.lang.Float> successFactors = new java.util.HashMap<>();
        org.easymock.EasyMock.expect(stage.getSuccessFactors()).andReturn(successFactors).atLeastOnce();
        org.easymock.EasyMock.expect(stageFactory.createNew(org.easymock.EasyMock.anyLong(), org.easymock.EasyMock.anyObject(java.lang.String.class), org.easymock.EasyMock.anyObject(java.lang.String.class), org.easymock.EasyMock.anyLong(), org.easymock.EasyMock.anyObject(java.lang.String.class), org.easymock.EasyMock.anyObject(java.lang.String.class), org.easymock.EasyMock.anyObject(java.lang.String.class))).andReturn(stage).times(((int) (java.lang.Math.ceil(hostCount / org.apache.ambari.server.controller.internal.ClusterStackVersionResourceProviderTest.MAX_TASKS_PER_STAGE))));
        org.easymock.EasyMock.expect(repositoryVersionDAOMock.findByStackAndVersion(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.StackId.class), org.easymock.EasyMock.anyObject(java.lang.String.class))).andReturn(repoVersion);
        org.easymock.Capture<org.apache.ambari.server.actionmanager.Request> c = org.easymock.Capture.newInstance();
        org.easymock.Capture<org.apache.ambari.server.controller.ExecuteActionRequest> ear = org.easymock.Capture.newInstance();
        actionManager.sendActions(org.easymock.EasyMock.capture(c), org.easymock.EasyMock.capture(ear));
        org.easymock.EasyMock.expectLastCall().atLeastOnce();
        org.easymock.EasyMock.expect(actionManager.getRequestTasks(org.easymock.EasyMock.anyLong())).andReturn(java.util.Collections.emptyList()).anyTimes();
        org.apache.ambari.server.orm.entities.ClusterEntity clusterEntity = new org.apache.ambari.server.orm.entities.ClusterEntity();
        clusterEntity.setClusterId(1L);
        clusterEntity.setClusterName(clusterName);
        org.apache.ambari.server.utils.StageUtils.setTopologyManager(injector.getInstance(org.apache.ambari.server.topology.TopologyManager.class));
        org.apache.ambari.server.utils.StageUtils.setConfiguration(injector.getInstance(org.apache.ambari.server.configuration.Configuration.class));
        org.easymock.EasyMock.replay(managementController, response, clusters, hdfsService, hbaseService, csvResourceProvider, cluster, repositoryVersionDAOMock, configHelper, schDatanode, schNamenode, schHBM, actionManager, executionCommandWrapper, stage, stageFactory);
        org.apache.ambari.server.controller.spi.ResourceProvider provider = createProvider(managementController);
        injector.injectMembers(provider);
        java.util.Set<java.util.Map<java.lang.String, java.lang.Object>> propertySet = new java.util.LinkedHashSet<>();
        java.util.Map<java.lang.String, java.lang.Object> properties = new java.util.LinkedHashMap<>();
        properties.put(org.apache.ambari.server.controller.internal.ClusterStackVersionResourceProvider.CLUSTER_STACK_VERSION_CLUSTER_NAME_PROPERTY_ID, "Cluster100");
        properties.put(org.apache.ambari.server.controller.internal.ClusterStackVersionResourceProvider.CLUSTER_STACK_VERSION_REPOSITORY_VERSION_PROPERTY_ID, "2.2.0.1-885");
        properties.put(org.apache.ambari.server.controller.internal.ClusterStackVersionResourceProvider.CLUSTER_STACK_VERSION_STACK_PROPERTY_ID, "HDP");
        properties.put(org.apache.ambari.server.controller.internal.ClusterStackVersionResourceProvider.CLUSTER_STACK_VERSION_VERSION_PROPERTY_ID, "2.1.1");
        propertySet.add(properties);
        org.apache.ambari.server.controller.spi.Request request = org.apache.ambari.server.controller.utilities.PropertyHelper.getCreateRequest(propertySet, null);
        org.springframework.security.core.context.SecurityContextHolder.getContext().setAuthentication(authentication);
        org.apache.ambari.server.controller.spi.RequestStatus status = provider.createResources(request);
        org.junit.Assert.assertNotNull(status);
        org.easymock.EasyMock.verify(managementController, response, clusters, stageFactory, stage);
        java.lang.Float successFactor = successFactors.get(org.apache.ambari.server.Role.INSTALL_PACKAGES);
        org.junit.Assert.assertEquals(java.lang.Float.valueOf(0.85F), successFactor);
        org.junit.Assert.assertNotNull(executionCommand.getRepositoryFile());
        org.junit.Assert.assertEquals(2, executionCommand.getRepositoryFile().getRepositories().size());
        for (org.apache.ambari.server.agent.CommandRepository.Repository repo : executionCommand.getRepositoryFile().getRepositories()) {
            org.junit.Assert.assertFalse(repo.isAmbariManaged());
        }
    }

    @org.junit.Test
    public void testCreateResourcesMixedAsAdministrator() throws java.lang.Exception {
        testCreateResourcesMixed(org.apache.ambari.server.security.TestAuthenticationFactory.createAdministrator());
    }

    @org.junit.Test
    public void testCreateResourcesMixedAsClusterAdministrator() throws java.lang.Exception {
        testCreateResourcesMixed(org.apache.ambari.server.security.TestAuthenticationFactory.createClusterAdministrator());
    }

    @org.junit.Test(expected = org.apache.ambari.server.security.authorization.AuthorizationException.class)
    public void testCreateResourcesMixedAsClusterOperator() throws java.lang.Exception {
        testCreateResourcesMixed(org.apache.ambari.server.security.TestAuthenticationFactory.createClusterOperator());
    }

    private void testCreateResourcesMixed(org.springframework.security.core.Authentication authentication) throws java.lang.Exception {
        org.apache.ambari.server.state.Cluster cluster = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Cluster.class);
        java.util.Map<java.lang.String, java.lang.String> hostLevelParams = new java.util.HashMap<>();
        org.apache.ambari.server.state.StackId stackId = new org.apache.ambari.server.state.StackId("HDP", "2.0.1");
        java.io.File f = new java.io.File("src/test/resources/hbase_version_test.xml");
        java.lang.String xml = org.apache.commons.io.IOUtils.toString(new java.io.FileInputStream(f));
        xml = xml.replace("<package-version>2_3_4_0_3396</package-version>", "");
        org.apache.ambari.server.orm.entities.StackEntity stack = new org.apache.ambari.server.orm.entities.StackEntity();
        stack.setStackName("HDP");
        org.apache.ambari.server.orm.entities.RepositoryVersionEntity repoVersion = new org.apache.ambari.server.orm.entities.RepositoryVersionEntity();
        repoVersion.setStack(stack);
        repoVersion.setId(1L);
        repoVersion.addRepoOsEntities(org.apache.ambari.server.controller.internal.ClusterStackVersionResourceProviderTest.REPO_OS_ENTITIES);
        repoVersion.setVersionXml(xml);
        repoVersion.setVersionXsd("version_definition.xsd");
        repoVersion.setType(org.apache.ambari.spi.RepositoryType.STANDARD);
        java.util.Map<java.lang.String, org.apache.ambari.server.state.Host> hostsForCluster = new java.util.HashMap<>();
        int hostCount = 10;
        for (int i = 0; i < hostCount; i++) {
            java.lang.String hostname = "host" + i;
            java.util.List<org.apache.ambari.server.orm.entities.HostVersionEntity> hostVersions = new java.util.ArrayList<>();
            org.apache.ambari.server.orm.entities.HostVersionEntity hostVersion = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.orm.entities.HostVersionEntity.class);
            org.easymock.EasyMock.expect(hostVersion.getRepositoryVersion()).andReturn(repoVersion);
            hostVersions.add(hostVersion);
            if (i == 2) {
                org.apache.ambari.server.orm.entities.RepositoryVersionEntity badRve = new org.apache.ambari.server.orm.entities.RepositoryVersionEntity();
                badRve.setStack(stack);
                badRve.setVersion("2.2.1.0-1000");
                org.apache.ambari.server.orm.entities.HostVersionEntity badHostVersion = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.orm.entities.HostVersionEntity.class);
                org.easymock.EasyMock.expect(badHostVersion.getRepositoryVersion()).andReturn(badRve);
                hostVersions.add(badHostVersion);
                org.easymock.EasyMock.replay(badHostVersion);
            }
            org.apache.ambari.server.state.Host host = org.easymock.EasyMock.createNiceMock(hostname, org.apache.ambari.server.state.Host.class);
            org.easymock.EasyMock.expect(host.getHostName()).andReturn(hostname).anyTimes();
            org.easymock.EasyMock.expect(host.getOsFamily()).andReturn("redhat6").anyTimes();
            org.easymock.EasyMock.expect(host.getMaintenanceState(org.easymock.EasyMock.anyLong())).andReturn(org.apache.ambari.server.state.MaintenanceState.OFF).anyTimes();
            org.easymock.EasyMock.expect(host.getAllHostVersions()).andReturn(hostVersions).anyTimes();
            org.easymock.EasyMock.replay(host, hostVersion);
            hostsForCluster.put(hostname, host);
        }
        final org.apache.ambari.server.state.ServiceComponentHost schDatanode = org.easymock.EasyMock.createMock(org.apache.ambari.server.state.ServiceComponentHost.class);
        org.easymock.EasyMock.expect(schDatanode.getServiceName()).andReturn("HDFS").anyTimes();
        org.easymock.EasyMock.expect(schDatanode.getServiceComponentName()).andReturn("DATANODE").anyTimes();
        final org.apache.ambari.server.state.ServiceComponentHost schNamenode = org.easymock.EasyMock.createMock(org.apache.ambari.server.state.ServiceComponentHost.class);
        org.easymock.EasyMock.expect(schNamenode.getServiceName()).andReturn("HDFS").anyTimes();
        org.easymock.EasyMock.expect(schNamenode.getServiceComponentName()).andReturn("NAMENODE").anyTimes();
        final org.apache.ambari.server.state.ServiceComponentHost schAMS = org.easymock.EasyMock.createMock(org.apache.ambari.server.state.ServiceComponentHost.class);
        org.easymock.EasyMock.expect(schAMS.getServiceName()).andReturn("AMBARI_METRICS").anyTimes();
        org.easymock.EasyMock.expect(schAMS.getServiceComponentName()).andReturn("METRICS_COLLECTOR").anyTimes();
        final java.util.List<org.apache.ambari.server.state.ServiceComponentHost> schsH1 = com.google.common.collect.Lists.newArrayList(schDatanode, schNamenode, schAMS);
        final java.util.List<org.apache.ambari.server.state.ServiceComponentHost> schsH2 = com.google.common.collect.Lists.newArrayList(schAMS);
        org.apache.ambari.server.state.ServiceOsSpecific.Package hdfsPackage = new org.apache.ambari.server.state.ServiceOsSpecific.Package();
        hdfsPackage.setName("hdfs");
        java.util.List<org.apache.ambari.server.state.ServiceOsSpecific.Package> packages = java.util.Collections.singletonList(hdfsPackage);
        org.apache.ambari.server.actionmanager.ActionManager actionManager = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.actionmanager.ActionManager.class);
        org.apache.ambari.server.controller.RequestStatusResponse response = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.RequestStatusResponse.class);
        java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.lang.String>> hostConfigTags = new java.util.HashMap<>();
        org.easymock.EasyMock.expect(configHelper.getEffectiveDesiredTags(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.cluster.ClusterImpl.class), org.easymock.EasyMock.anyObject(java.lang.String.class))).andReturn(hostConfigTags);
        org.easymock.EasyMock.expect(managementController.getClusters()).andReturn(clusters).anyTimes();
        org.easymock.EasyMock.expect(managementController.getAmbariMetaInfo()).andReturn(ambariMetaInfo).anyTimes();
        org.easymock.EasyMock.expect(managementController.getAuthName()).andReturn("admin").anyTimes();
        org.easymock.EasyMock.expect(managementController.getActionManager()).andReturn(actionManager).anyTimes();
        org.easymock.EasyMock.expect(managementController.getJdkResourceUrl()).andReturn("/JdkResourceUrl").anyTimes();
        org.easymock.EasyMock.expect(managementController.getPackagesForServiceHost(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.ServiceInfo.class), org.easymock.EasyMock.<java.util.Map<java.lang.String, java.lang.String>>anyObject(), org.easymock.EasyMock.anyObject(java.lang.String.class))).andReturn(packages).anyTimes();
        org.easymock.EasyMock.expect(managementController.findConfigurationTagsWithOverrides(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.Cluster.class), org.easymock.EasyMock.anyString(), org.easymock.EasyMock.anyObject())).andReturn(new java.util.HashMap<java.lang.String, java.util.Map<java.lang.String, java.lang.String>>()).anyTimes();
        org.easymock.EasyMock.expect(clusters.getCluster(org.easymock.EasyMock.anyObject(java.lang.String.class))).andReturn(cluster);
        org.easymock.EasyMock.expect(clusters.getHostsForCluster(org.easymock.EasyMock.anyObject(java.lang.String.class))).andReturn(hostsForCluster).anyTimes();
        java.lang.String clusterName = "Cluster100";
        org.easymock.EasyMock.expect(cluster.getClusterId()).andReturn(1L).anyTimes();
        org.easymock.EasyMock.expect(cluster.getHosts()).andReturn(hostsForCluster.values()).atLeastOnce();
        org.easymock.EasyMock.expect(cluster.getServices()).andReturn(new java.util.HashMap<>()).anyTimes();
        org.easymock.EasyMock.expect(cluster.getCurrentStackVersion()).andReturn(stackId);
        org.easymock.EasyMock.expect(cluster.getServiceComponentHosts(org.easymock.EasyMock.anyObject(java.lang.String.class))).andAnswer(new org.easymock.IAnswer<java.util.List<org.apache.ambari.server.state.ServiceComponentHost>>() {
            @java.lang.Override
            public java.util.List<org.apache.ambari.server.state.ServiceComponentHost> answer() throws java.lang.Throwable {
                java.lang.String hostname = ((java.lang.String) (org.easymock.EasyMock.getCurrentArguments()[0]));
                if (hostname.equals("host2")) {
                    return schsH2;
                } else {
                    return schsH1;
                }
            }
        }).anyTimes();
        org.easymock.EasyMock.expect(cluster.transitionHostsToInstalling(org.easymock.EasyMock.anyObject(org.apache.ambari.server.orm.entities.RepositoryVersionEntity.class), org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.repository.VersionDefinitionXml.class), org.easymock.EasyMock.eq(false))).andReturn(java.util.Collections.emptyList()).anyTimes();
        org.apache.ambari.server.agent.ExecutionCommand executionCommand = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.agent.ExecutionCommand.class);
        org.apache.ambari.server.actionmanager.ExecutionCommandWrapper executionCommandWrapper = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.actionmanager.ExecutionCommandWrapper.class);
        org.easymock.EasyMock.expect(executionCommandWrapper.getExecutionCommand()).andReturn(executionCommand).anyTimes();
        org.apache.ambari.server.actionmanager.Stage stage = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.actionmanager.Stage.class);
        org.easymock.EasyMock.expect(stage.getExecutionCommandWrapper(org.easymock.EasyMock.anyObject(java.lang.String.class), org.easymock.EasyMock.anyObject(java.lang.String.class))).andReturn(executionCommandWrapper).anyTimes();
        org.easymock.EasyMock.expect(executionCommand.getHostLevelParams()).andReturn(hostLevelParams).anyTimes();
        java.util.Map<org.apache.ambari.server.Role, java.lang.Float> successFactors = new java.util.HashMap<>();
        org.easymock.EasyMock.expect(stage.getSuccessFactors()).andReturn(successFactors).atLeastOnce();
        org.easymock.EasyMock.expect(stageFactory.createNew(org.easymock.EasyMock.anyLong(), org.easymock.EasyMock.anyObject(java.lang.String.class), org.easymock.EasyMock.anyObject(java.lang.String.class), org.easymock.EasyMock.anyLong(), org.easymock.EasyMock.anyObject(java.lang.String.class), org.easymock.EasyMock.anyObject(java.lang.String.class), org.easymock.EasyMock.anyObject(java.lang.String.class))).andReturn(stage).times(((int) (java.lang.Math.ceil(hostCount / org.apache.ambari.server.controller.internal.ClusterStackVersionResourceProviderTest.MAX_TASKS_PER_STAGE))));
        org.easymock.EasyMock.expect(repositoryVersionDAOMock.findByStackAndVersion(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.StackId.class), org.easymock.EasyMock.anyObject(java.lang.String.class))).andReturn(repoVersion);
        org.easymock.EasyMock.expect(actionManager.getRequestTasks(org.easymock.EasyMock.anyLong())).andReturn(java.util.Collections.emptyList()).anyTimes();
        org.apache.ambari.server.orm.entities.ClusterEntity clusterEntity = new org.apache.ambari.server.orm.entities.ClusterEntity();
        clusterEntity.setClusterId(1L);
        clusterEntity.setClusterName(clusterName);
        org.apache.ambari.server.topology.TopologyManager topologyManager = injector.getInstance(org.apache.ambari.server.topology.TopologyManager.class);
        org.apache.ambari.server.utils.StageUtils.setTopologyManager(topologyManager);
        org.apache.ambari.server.utils.StageUtils.setConfiguration(injector.getInstance(org.apache.ambari.server.configuration.Configuration.class));
        org.easymock.EasyMock.replay(managementController, response, clusters, cluster, repositoryVersionDAOMock, configHelper, schDatanode, schNamenode, schAMS, actionManager, executionCommand, executionCommandWrapper, stage, stageFactory);
        org.apache.ambari.server.controller.spi.ResourceProvider provider = createProvider(managementController);
        injector.injectMembers(provider);
        java.util.Set<java.util.Map<java.lang.String, java.lang.Object>> propertySet = new java.util.LinkedHashSet<>();
        java.util.Map<java.lang.String, java.lang.Object> properties = new java.util.LinkedHashMap<>();
        properties.put(org.apache.ambari.server.controller.internal.ClusterStackVersionResourceProvider.CLUSTER_STACK_VERSION_CLUSTER_NAME_PROPERTY_ID, "Cluster100");
        properties.put(org.apache.ambari.server.controller.internal.ClusterStackVersionResourceProvider.CLUSTER_STACK_VERSION_REPOSITORY_VERSION_PROPERTY_ID, "2.2.0.1-885");
        properties.put(org.apache.ambari.server.controller.internal.ClusterStackVersionResourceProvider.CLUSTER_STACK_VERSION_STACK_PROPERTY_ID, "HDP");
        properties.put(org.apache.ambari.server.controller.internal.ClusterStackVersionResourceProvider.CLUSTER_STACK_VERSION_VERSION_PROPERTY_ID, "2.1.1");
        propertySet.add(properties);
        org.apache.ambari.server.controller.spi.Request request = org.apache.ambari.server.controller.utilities.PropertyHelper.getCreateRequest(propertySet, null);
        org.springframework.security.core.context.SecurityContextHolder.getContext().setAuthentication(authentication);
        try {
            provider.createResources(request);
            org.junit.Assert.fail("Expecting the create to fail due to an already installed version");
        } catch (java.lang.IllegalArgumentException iae) {
        }
    }

    @org.junit.Test
    public void testCreateResourcesExistingUpgradeAsAdministrator() throws java.lang.Exception {
        testCreateResourcesExistingUpgrade(org.apache.ambari.server.security.TestAuthenticationFactory.createAdministrator());
    }

    @org.junit.Test
    public void testCreateResourcesExistingUpgradeAsClusterAdministrator() throws java.lang.Exception {
        testCreateResourcesExistingUpgrade(org.apache.ambari.server.security.TestAuthenticationFactory.createClusterAdministrator());
    }

    @org.junit.Test(expected = org.apache.ambari.server.security.authorization.AuthorizationException.class)
    public void testCreateResourcesExistingUpgradeAsClusterOperator() throws java.lang.Exception {
        testCreateResourcesExistingUpgrade(org.apache.ambari.server.security.TestAuthenticationFactory.createClusterOperator());
    }

    @org.junit.Test
    public void testCreateResourcesInInstalledState() throws java.lang.Exception {
        org.apache.ambari.server.state.Cluster cluster = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Cluster.class);
        org.apache.ambari.server.state.StackId stackId = new org.apache.ambari.server.state.StackId("HDP", "2.2.0");
        java.lang.String repoVersion = "2.2.0.1-885";
        java.io.File f = new java.io.File("src/test/resources/hbase_version_test.xml");
        org.apache.ambari.server.orm.entities.StackEntity stackEntity = new org.apache.ambari.server.orm.entities.StackEntity();
        stackEntity.setStackName(stackId.getStackName());
        stackEntity.setStackVersion(stackId.getStackVersion());
        org.apache.ambari.server.orm.entities.RepositoryVersionEntity repoVersionEntity = new org.apache.ambari.server.orm.entities.RepositoryVersionEntity();
        repoVersionEntity.setId(1L);
        repoVersionEntity.addRepoOsEntities(org.apache.ambari.server.controller.internal.ClusterStackVersionResourceProviderTest.REPO_OS_ENTITIES);
        repoVersionEntity.setVersionXml(org.apache.commons.io.IOUtils.toString(new java.io.FileInputStream(f)));
        repoVersionEntity.setVersionXsd("version_definition.xsd");
        repoVersionEntity.setType(org.apache.ambari.spi.RepositoryType.STANDARD);
        repoVersionEntity.setVersion(repoVersion);
        repoVersionEntity.setStack(stackEntity);
        java.util.List<org.apache.ambari.server.state.Host> hostsNeedingInstallCommands = new java.util.ArrayList<>();
        java.util.Map<java.lang.String, org.apache.ambari.server.state.Host> hostsForCluster = new java.util.HashMap<>();
        java.util.List<org.apache.ambari.server.orm.entities.HostVersionEntity> hostVersionEntitiesMergedWithNotRequired = new java.util.ArrayList<>();
        int hostCount = 10;
        for (int i = 0; i < hostCount; i++) {
            java.lang.String hostname = "host" + i;
            org.apache.ambari.server.state.Host host = org.easymock.EasyMock.createNiceMock(hostname, org.apache.ambari.server.state.Host.class);
            org.easymock.EasyMock.expect(host.getHostName()).andReturn(hostname).anyTimes();
            org.easymock.EasyMock.expect(host.getOsFamily()).andReturn("redhat6").anyTimes();
            org.easymock.EasyMock.expect(host.getMaintenanceState(org.easymock.EasyMock.anyLong())).andReturn(org.apache.ambari.server.state.MaintenanceState.OFF).anyTimes();
            if (i < (hostCount - 2)) {
                org.easymock.EasyMock.expect(host.hasComponentsAdvertisingVersions(org.easymock.EasyMock.eq(stackId))).andReturn(true).atLeastOnce();
                hostsNeedingInstallCommands.add(host);
                org.easymock.EasyMock.expect(host.getAllHostVersions()).andReturn(java.util.Collections.emptyList()).anyTimes();
            } else {
                org.easymock.EasyMock.expect(host.hasComponentsAdvertisingVersions(org.easymock.EasyMock.eq(stackId))).andReturn(false).atLeastOnce();
                org.apache.ambari.server.orm.entities.HostVersionEntity hostVersionEntity = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.orm.entities.HostVersionEntity.class);
                org.easymock.EasyMock.expect(hostVersionEntity.getRepositoryVersion()).andReturn(repoVersionEntity).atLeastOnce();
                org.easymock.EasyMock.replay(hostVersionEntity);
                hostVersionEntitiesMergedWithNotRequired.add(hostVersionEntity);
                org.easymock.EasyMock.expect(host.getAllHostVersions()).andReturn(hostVersionEntitiesMergedWithNotRequired).anyTimes();
            }
            org.easymock.EasyMock.replay(host);
            hostsForCluster.put(hostname, host);
        }
        org.apache.ambari.server.state.Service hdfsService = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Service.class);
        org.easymock.EasyMock.expect(hdfsService.getName()).andReturn("HDFS").anyTimes();
        org.easymock.EasyMock.expect(hdfsService.getServiceComponents()).andReturn(new java.util.HashMap<>());
        java.util.Map<java.lang.String, org.apache.ambari.server.state.Service> serviceMap = new java.util.HashMap<>();
        serviceMap.put("HDFS", hdfsService);
        final org.apache.ambari.server.state.ServiceComponentHost schDatanode = org.easymock.EasyMock.createMock(org.apache.ambari.server.state.ServiceComponentHost.class);
        org.easymock.EasyMock.expect(schDatanode.getServiceName()).andReturn("HDFS").anyTimes();
        org.easymock.EasyMock.expect(schDatanode.getServiceComponentName()).andReturn("DATANODE").anyTimes();
        final java.util.List<org.apache.ambari.server.state.ServiceComponentHost> serviceComponentHosts = java.util.Arrays.asList(schDatanode);
        org.apache.ambari.server.state.ServiceOsSpecific.Package hdfsPackage = new org.apache.ambari.server.state.ServiceOsSpecific.Package();
        hdfsPackage.setName("hdfs");
        java.util.List<org.apache.ambari.server.state.ServiceOsSpecific.Package> packages = java.util.Collections.singletonList(hdfsPackage);
        org.apache.ambari.server.controller.RequestStatusResponse response = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.RequestStatusResponse.class);
        org.apache.ambari.server.controller.ResourceProviderFactory resourceProviderFactory = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.ResourceProviderFactory.class);
        org.apache.ambari.server.controller.spi.ResourceProvider csvResourceProvider = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.ResourceProvider.class);
        org.apache.ambari.server.controller.internal.AbstractControllerResourceProvider.init(resourceProviderFactory);
        org.easymock.EasyMock.expect(managementController.getClusters()).andReturn(clusters).anyTimes();
        org.easymock.EasyMock.expect(managementController.getAmbariMetaInfo()).andReturn(ambariMetaInfo).anyTimes();
        org.easymock.EasyMock.expect(managementController.getAuthName()).andReturn("admin").anyTimes();
        org.easymock.EasyMock.expect(managementController.getJdkResourceUrl()).andReturn("/JdkResourceUrl").anyTimes();
        org.easymock.EasyMock.expect(managementController.getPackagesForServiceHost(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.ServiceInfo.class), org.easymock.EasyMock.anyObject(), org.easymock.EasyMock.anyObject(java.lang.String.class))).andReturn(packages).anyTimes();
        org.easymock.EasyMock.expect(resourceProviderFactory.getHostResourceProvider(org.easymock.EasyMock.eq(managementController))).andReturn(csvResourceProvider).anyTimes();
        org.easymock.EasyMock.expect(clusters.getCluster(org.easymock.EasyMock.anyObject(java.lang.String.class))).andReturn(cluster);
        org.easymock.EasyMock.expect(clusters.getHostsForCluster(org.easymock.EasyMock.anyObject(java.lang.String.class))).andReturn(hostsForCluster).anyTimes();
        java.lang.String clusterName = "Cluster100";
        org.easymock.EasyMock.expect(cluster.getClusterId()).andReturn(1L).anyTimes();
        org.easymock.EasyMock.expect(cluster.getHosts()).andReturn(hostsForCluster.values()).atLeastOnce();
        org.easymock.EasyMock.expect(cluster.getServices()).andReturn(serviceMap).anyTimes();
        org.easymock.EasyMock.expect(cluster.getServiceComponentHosts(org.easymock.EasyMock.anyObject(java.lang.String.class))).andReturn(serviceComponentHosts).anyTimes();
        org.easymock.EasyMock.expect(cluster.getCurrentStackVersion()).andReturn(stackId).atLeastOnce();
        org.easymock.EasyMock.expect(repositoryVersionDAOMock.findByStackAndVersion(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.StackId.class), org.easymock.EasyMock.anyObject(java.lang.String.class))).andReturn(repoVersionEntity);
        org.apache.ambari.server.orm.entities.ClusterEntity clusterEntity = new org.apache.ambari.server.orm.entities.ClusterEntity();
        clusterEntity.setClusterId(1L);
        clusterEntity.setClusterName(clusterName);
        org.easymock.EasyMock.expect(cluster.transitionHostsToInstalling(repoVersionEntity, repoVersionEntity.getRepositoryXml(), true)).andReturn(hostsNeedingInstallCommands).once();
        org.easymock.EasyMock.replay(managementController, response, clusters, hdfsService, resourceProviderFactory, csvResourceProvider, cluster, repositoryVersionDAOMock, configHelper, schDatanode, stageFactory, hostVersionDAO);
        org.apache.ambari.server.controller.spi.ResourceProvider provider = createProvider(managementController);
        injector.injectMembers(provider);
        java.util.Set<java.util.Map<java.lang.String, java.lang.Object>> propertySet = new java.util.LinkedHashSet<>();
        java.util.Map<java.lang.String, java.lang.Object> properties = new java.util.LinkedHashMap<>();
        properties.put(org.apache.ambari.server.controller.internal.ClusterStackVersionResourceProvider.CLUSTER_STACK_VERSION_CLUSTER_NAME_PROPERTY_ID, clusterName);
        properties.put(org.apache.ambari.server.controller.internal.ClusterStackVersionResourceProvider.CLUSTER_STACK_VERSION_REPOSITORY_VERSION_PROPERTY_ID, repoVersion);
        properties.put(org.apache.ambari.server.controller.internal.ClusterStackVersionResourceProvider.CLUSTER_STACK_VERSION_STACK_PROPERTY_ID, stackId.getStackName());
        properties.put(org.apache.ambari.server.controller.internal.ClusterStackVersionResourceProvider.CLUSTER_STACK_VERSION_VERSION_PROPERTY_ID, stackId.getStackVersion());
        properties.put(org.apache.ambari.server.controller.internal.ClusterStackVersionResourceProvider.CLUSTER_STACK_VERSION_FORCE, "true");
        propertySet.add(properties);
        org.springframework.security.core.context.SecurityContextHolder.getContext().setAuthentication(org.apache.ambari.server.security.TestAuthenticationFactory.createAdministrator());
        org.apache.ambari.server.controller.spi.Request request = org.apache.ambari.server.controller.utilities.PropertyHelper.getCreateRequest(propertySet, null);
        org.apache.ambari.server.controller.spi.RequestStatus status = provider.createResources(request);
        org.junit.Assert.assertNotNull(status);
        org.easymock.EasyMock.verify(managementController, response, clusters, cluster, hostVersionDAO);
    }

    @org.junit.Test
    public void testCreateResourcesPPC() throws java.lang.Exception {
        org.apache.ambari.server.state.Cluster cluster = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Cluster.class);
        java.util.Map<java.lang.String, java.lang.String> hostLevelParams = new java.util.HashMap<>();
        org.apache.ambari.server.state.StackId stackId = new org.apache.ambari.server.state.StackId("HDP", "2.0.1");
        org.apache.ambari.server.stack.upgrade.RepositoryVersionHelper rvh = new org.apache.ambari.server.stack.upgrade.RepositoryVersionHelper();
        org.apache.ambari.server.orm.entities.RepositoryVersionEntity repoVersion = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.orm.entities.RepositoryVersionEntity.class);
        org.easymock.EasyMock.expect(repoVersion.getId()).andReturn(1L).anyTimes();
        org.easymock.EasyMock.expect(repoVersion.getStackId()).andReturn(new org.apache.ambari.server.state.StackId("HDP-2.1.1")).anyTimes();
        org.easymock.EasyMock.expect(repoVersion.isLegacy()).andReturn(false).anyTimes();
        org.easymock.EasyMock.expect(repoVersion.getStackName()).andReturn("HDP").anyTimes();
        java.util.List<org.apache.ambari.server.orm.entities.RepoOsEntity> oss = new java.util.ArrayList<>();
        org.apache.ambari.server.orm.entities.RepoDefinitionEntity repoDefinitionEntity1 = new org.apache.ambari.server.orm.entities.RepoDefinitionEntity();
        repoDefinitionEntity1.setRepoID("HDP-UTILS-1.1.0.20");
        repoDefinitionEntity1.setBaseUrl("http://s3.amazonaws.com/dev.hortonworks.com/HDP/centos-ppc7/2.x/updates/2.2.0.0");
        repoDefinitionEntity1.setRepoName("HDP-UTILS");
        org.apache.ambari.server.orm.entities.RepoDefinitionEntity repoDefinitionEntity2 = new org.apache.ambari.server.orm.entities.RepoDefinitionEntity();
        repoDefinitionEntity2.setRepoID("HDP-2.2");
        repoDefinitionEntity2.setBaseUrl("http://s3.amazonaws.com/dev.hortonworks.com/HDP/centos-ppc7/2.x/updates/2.2.0.0");
        repoDefinitionEntity2.setRepoName("HDP");
        org.apache.ambari.server.orm.entities.RepoOsEntity repoOsEntity = new org.apache.ambari.server.orm.entities.RepoOsEntity();
        repoOsEntity.setFamily("redhat-ppc7");
        repoOsEntity.setAmbariManaged(true);
        repoOsEntity.addRepoDefinition(repoDefinitionEntity1);
        repoOsEntity.addRepoDefinition(repoDefinitionEntity2);
        oss.add(repoOsEntity);
        org.easymock.EasyMock.expect(repoVersion.getRepoOsEntities()).andReturn(oss).anyTimes();
        org.easymock.EasyMock.expect(repoVersion.getType()).andReturn(org.apache.ambari.spi.RepositoryType.STANDARD).anyTimes();
        java.util.Map<java.lang.String, org.apache.ambari.server.state.Host> hostsForCluster = new java.util.HashMap<>();
        int hostCount = 2;
        for (int i = 0; i < hostCount; i++) {
            java.lang.String hostname = "host" + i;
            org.apache.ambari.server.state.Host host = org.easymock.EasyMock.createNiceMock(hostname, org.apache.ambari.server.state.Host.class);
            org.easymock.EasyMock.expect(host.getHostName()).andReturn(hostname).anyTimes();
            org.easymock.EasyMock.expect(host.getOsFamily()).andReturn("redhat-ppc7").anyTimes();
            org.easymock.EasyMock.expect(host.getMaintenanceState(org.easymock.EasyMock.anyLong())).andReturn(org.apache.ambari.server.state.MaintenanceState.OFF).anyTimes();
            org.easymock.EasyMock.expect(host.getAllHostVersions()).andReturn(java.util.Collections.<org.apache.ambari.server.orm.entities.HostVersionEntity>emptyList()).anyTimes();
            org.easymock.EasyMock.expect(host.getHostAttributes()).andReturn(com.google.common.collect.ImmutableMap.<java.lang.String, java.lang.String>builder().put("os_family", "redhat-ppc").put("os_release_version", "7.2").build()).anyTimes();
            org.easymock.EasyMock.replay(host);
            hostsForCluster.put(hostname, host);
        }
        final org.apache.ambari.server.state.ServiceComponentHost schDatanode = org.easymock.EasyMock.createMock(org.apache.ambari.server.state.ServiceComponentHost.class);
        org.easymock.EasyMock.expect(schDatanode.getServiceName()).andReturn("HDFS").anyTimes();
        org.easymock.EasyMock.expect(schDatanode.getServiceComponentName()).andReturn("DATANODE").anyTimes();
        final org.apache.ambari.server.state.ServiceComponentHost schNamenode = org.easymock.EasyMock.createMock(org.apache.ambari.server.state.ServiceComponentHost.class);
        org.easymock.EasyMock.expect(schNamenode.getServiceName()).andReturn("HDFS").anyTimes();
        org.easymock.EasyMock.expect(schNamenode.getServiceComponentName()).andReturn("NAMENODE").anyTimes();
        final org.apache.ambari.server.state.ServiceComponentHost schAMS = org.easymock.EasyMock.createMock(org.apache.ambari.server.state.ServiceComponentHost.class);
        org.easymock.EasyMock.expect(schAMS.getServiceName()).andReturn("AMBARI_METRICS").anyTimes();
        org.easymock.EasyMock.expect(schAMS.getServiceComponentName()).andReturn("METRICS_COLLECTOR").anyTimes();
        final java.util.List<org.apache.ambari.server.state.ServiceComponentHost> schsH1 = com.google.common.collect.Lists.newArrayList(schDatanode, schNamenode, schAMS);
        final java.util.List<org.apache.ambari.server.state.ServiceComponentHost> schsH2 = com.google.common.collect.Lists.newArrayList(schAMS);
        org.apache.ambari.server.state.ServiceOsSpecific.Package hdfsPackage = new org.apache.ambari.server.state.ServiceOsSpecific.Package();
        hdfsPackage.setName("hdfs");
        java.util.List<org.apache.ambari.server.state.ServiceOsSpecific.Package> packages = java.util.Collections.singletonList(hdfsPackage);
        org.apache.ambari.server.actionmanager.ActionManager actionManager = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.actionmanager.ActionManager.class);
        org.apache.ambari.server.controller.RequestStatusResponse response = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.RequestStatusResponse.class);
        org.apache.ambari.server.controller.ResourceProviderFactory resourceProviderFactory = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.ResourceProviderFactory.class);
        org.apache.ambari.server.controller.spi.ResourceProvider csvResourceProvider = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.internal.ClusterStackVersionResourceProvider.class);
        org.apache.ambari.server.controller.internal.AbstractControllerResourceProvider.init(resourceProviderFactory);
        java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.lang.String>> hostConfigTags = new java.util.HashMap<>();
        org.easymock.EasyMock.expect(configHelper.getEffectiveDesiredTags(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.cluster.ClusterImpl.class), org.easymock.EasyMock.anyObject(java.lang.String.class))).andReturn(hostConfigTags);
        org.easymock.EasyMock.expect(managementController.getClusters()).andReturn(clusters).anyTimes();
        org.easymock.EasyMock.expect(managementController.getAmbariMetaInfo()).andReturn(ambariMetaInfo).anyTimes();
        org.easymock.EasyMock.expect(managementController.getAuthName()).andReturn("admin").anyTimes();
        org.easymock.EasyMock.expect(managementController.getActionManager()).andReturn(actionManager).anyTimes();
        org.easymock.EasyMock.expect(managementController.getJdkResourceUrl()).andReturn("/JdkResourceUrl").anyTimes();
        org.easymock.EasyMock.expect(managementController.getPackagesForServiceHost(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.ServiceInfo.class), ((java.util.Map<java.lang.String, java.lang.String>) (org.easymock.EasyMock.anyObject(java.util.List.class))), org.easymock.EasyMock.anyObject(java.lang.String.class))).andReturn(packages).anyTimes();
        org.easymock.EasyMock.expect(managementController.findConfigurationTagsWithOverrides(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.Cluster.class), org.easymock.EasyMock.anyString(), org.easymock.EasyMock.anyObject())).andReturn(new java.util.HashMap<java.lang.String, java.util.Map<java.lang.String, java.lang.String>>()).anyTimes();
        org.easymock.EasyMock.expect(resourceProviderFactory.getHostResourceProvider(org.easymock.EasyMock.eq(managementController))).andReturn(csvResourceProvider).anyTimes();
        org.easymock.EasyMock.expect(clusters.getCluster(org.easymock.EasyMock.anyObject(java.lang.String.class))).andReturn(cluster);
        org.easymock.EasyMock.expect(clusters.getHostsForCluster(org.easymock.EasyMock.anyObject(java.lang.String.class))).andReturn(hostsForCluster).anyTimes();
        java.lang.String clusterName = "Cluster100";
        org.easymock.EasyMock.expect(cluster.getClusterId()).andReturn(1L).anyTimes();
        org.easymock.EasyMock.expect(cluster.getHosts()).andReturn(hostsForCluster.values()).atLeastOnce();
        org.easymock.EasyMock.expect(cluster.getServices()).andReturn(new java.util.HashMap<java.lang.String, org.apache.ambari.server.state.Service>()).anyTimes();
        org.easymock.EasyMock.expect(cluster.getCurrentStackVersion()).andReturn(stackId);
        org.easymock.EasyMock.expect(cluster.getServiceComponentHosts(org.easymock.EasyMock.anyObject(java.lang.String.class))).andAnswer(new org.easymock.IAnswer<java.util.List<org.apache.ambari.server.state.ServiceComponentHost>>() {
            @java.lang.Override
            public java.util.List<org.apache.ambari.server.state.ServiceComponentHost> answer() throws java.lang.Throwable {
                java.lang.String hostname = ((java.lang.String) (org.easymock.EasyMock.getCurrentArguments()[0]));
                if (hostname.equals("host2")) {
                    return schsH2;
                } else {
                    return schsH1;
                }
            }
        }).anyTimes();
        org.easymock.EasyMock.expect(cluster.transitionHostsToInstalling(org.easymock.EasyMock.anyObject(org.apache.ambari.server.orm.entities.RepositoryVersionEntity.class), org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.repository.VersionDefinitionXml.class), org.easymock.EasyMock.anyBoolean())).andReturn(new java.util.ArrayList<>(hostsForCluster.values())).anyTimes();
        org.apache.ambari.server.agent.ExecutionCommand executionCommand = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.agent.ExecutionCommand.class);
        org.apache.ambari.server.actionmanager.ExecutionCommandWrapper executionCommandWrapper = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.actionmanager.ExecutionCommandWrapper.class);
        org.easymock.EasyMock.expect(executionCommandWrapper.getExecutionCommand()).andReturn(executionCommand).anyTimes();
        org.apache.ambari.server.actionmanager.Stage stage = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.actionmanager.Stage.class);
        org.easymock.EasyMock.expect(stage.getExecutionCommandWrapper(org.easymock.EasyMock.anyObject(java.lang.String.class), org.easymock.EasyMock.anyObject(java.lang.String.class))).andReturn(executionCommandWrapper).anyTimes();
        org.easymock.EasyMock.expect(executionCommand.getHostLevelParams()).andReturn(hostLevelParams).anyTimes();
        java.util.Map<org.apache.ambari.server.Role, java.lang.Float> successFactors = new java.util.HashMap<>();
        org.easymock.EasyMock.expect(stage.getSuccessFactors()).andReturn(successFactors).atLeastOnce();
        org.easymock.EasyMock.expect(stageFactory.createNew(org.easymock.EasyMock.anyLong(), org.easymock.EasyMock.anyObject(java.lang.String.class), org.easymock.EasyMock.anyObject(java.lang.String.class), org.easymock.EasyMock.anyLong(), org.easymock.EasyMock.anyObject(java.lang.String.class), org.easymock.EasyMock.anyObject(java.lang.String.class), org.easymock.EasyMock.anyObject(java.lang.String.class))).andReturn(stage).times(((int) (java.lang.Math.ceil(hostCount / org.apache.ambari.server.controller.internal.ClusterStackVersionResourceProviderTest.MAX_TASKS_PER_STAGE))));
        org.easymock.EasyMock.expect(repositoryVersionDAOMock.findByStackAndVersion(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.StackId.class), org.easymock.EasyMock.anyObject(java.lang.String.class))).andReturn(repoVersion);
        org.easymock.EasyMock.expect(actionManager.getRequestTasks(org.easymock.EasyMock.anyLong())).andReturn(java.util.Collections.<org.apache.ambari.server.actionmanager.HostRoleCommand>emptyList()).anyTimes();
        org.apache.ambari.server.orm.entities.ClusterEntity clusterEntity = new org.apache.ambari.server.orm.entities.ClusterEntity();
        clusterEntity.setClusterId(1L);
        clusterEntity.setClusterName(clusterName);
        org.apache.ambari.server.utils.StageUtils.setTopologyManager(injector.getInstance(org.apache.ambari.server.topology.TopologyManager.class));
        org.apache.ambari.server.utils.StageUtils.setConfiguration(injector.getInstance(org.apache.ambari.server.configuration.Configuration.class));
        org.easymock.EasyMock.replay(managementController, response, clusters, resourceProviderFactory, csvResourceProvider, cluster, repoVersion, repositoryVersionDAOMock, configHelper, schDatanode, schNamenode, schAMS, actionManager, executionCommand, executionCommandWrapper, stage, stageFactory);
        org.apache.ambari.server.controller.spi.ResourceProvider provider = createProvider(managementController);
        injector.injectMembers(provider);
        java.util.Set<java.util.Map<java.lang.String, java.lang.Object>> propertySet = new java.util.LinkedHashSet<>();
        java.util.Map<java.lang.String, java.lang.Object> properties = new java.util.LinkedHashMap<>();
        properties.put(org.apache.ambari.server.controller.internal.ClusterStackVersionResourceProvider.CLUSTER_STACK_VERSION_CLUSTER_NAME_PROPERTY_ID, "Cluster100");
        properties.put(org.apache.ambari.server.controller.internal.ClusterStackVersionResourceProvider.CLUSTER_STACK_VERSION_REPOSITORY_VERSION_PROPERTY_ID, "2.2.0.1-885");
        properties.put(org.apache.ambari.server.controller.internal.ClusterStackVersionResourceProvider.CLUSTER_STACK_VERSION_STACK_PROPERTY_ID, "HDP");
        properties.put(org.apache.ambari.server.controller.internal.ClusterStackVersionResourceProvider.CLUSTER_STACK_VERSION_VERSION_PROPERTY_ID, "2.1.1");
        propertySet.add(properties);
        org.apache.ambari.server.controller.spi.Request request = org.apache.ambari.server.controller.utilities.PropertyHelper.getCreateRequest(propertySet, null);
        org.springframework.security.core.context.SecurityContextHolder.getContext().setAuthentication(org.apache.ambari.server.security.TestAuthenticationFactory.createAdministrator());
        org.apache.ambari.server.controller.spi.RequestStatus status = provider.createResources(request);
        org.junit.Assert.assertNotNull(status);
        org.easymock.EasyMock.verify(managementController, response, clusters, stageFactory, stage);
        java.lang.Float successFactor = successFactors.get(org.apache.ambari.server.Role.INSTALL_PACKAGES);
        org.junit.Assert.assertEquals(java.lang.Float.valueOf(0.85F), successFactor);
    }

    @org.junit.Test
    public void testGetSorted() throws java.lang.Exception {
        org.apache.ambari.server.state.Cluster cluster = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Cluster.class);
        org.apache.ambari.server.state.StackId stackId = new org.apache.ambari.server.state.StackId("HDP", "2.2.0");
        org.apache.ambari.server.orm.entities.StackEntity stackEntity = new org.apache.ambari.server.orm.entities.StackEntity();
        stackEntity.setStackName(stackId.getStackName());
        stackEntity.setStackVersion(stackId.getStackVersion());
        org.apache.ambari.server.controller.RequestStatusResponse response = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.RequestStatusResponse.class);
        org.apache.ambari.server.controller.ResourceProviderFactory resourceProviderFactory = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.ResourceProviderFactory.class);
        org.apache.ambari.server.controller.spi.ResourceProvider csvResourceProvider = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.internal.ClusterStackVersionResourceProvider.class);
        org.apache.ambari.server.controller.internal.AbstractControllerResourceProvider.init(resourceProviderFactory);
        org.easymock.EasyMock.expect(clusters.getCluster(org.easymock.EasyMock.anyObject(java.lang.String.class))).andReturn(cluster);
        org.easymock.EasyMock.expect(cluster.getClusterId()).andReturn(1L).anyTimes();
        java.lang.String[] versionStrings = new java.lang.String[]{ "2.1.0.0-15", "2.1.0.5-17", "2.1.1.5-19", "2.1.0.3-14", "2.1.1.5-74" };
        java.util.List<org.apache.ambari.server.orm.entities.RepositoryVersionEntity> repoVersionList = new java.util.ArrayList<>();
        for (int i = 0; i < versionStrings.length; i++) {
            java.lang.Long id = new java.lang.Long(i);
            org.apache.ambari.server.orm.entities.RepositoryVersionEntity repoVersion = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.orm.entities.RepositoryVersionEntity.class);
            org.easymock.EasyMock.expect(repoVersion.getVersion()).andReturn(versionStrings[i]).anyTimes();
            org.easymock.EasyMock.expect(repoVersion.getStack()).andReturn(stackEntity).anyTimes();
            org.easymock.EasyMock.expect(repoVersion.getId()).andReturn(id).anyTimes();
            org.easymock.EasyMock.expect(repositoryVersionDAOMock.findByPK(id)).andReturn(repoVersion).anyTimes();
            repoVersionList.add(repoVersion);
            org.easymock.EasyMock.replay(repoVersion);
        }
        org.easymock.EasyMock.expect(repositoryVersionDAOMock.findAll()).andReturn(repoVersionList).atLeastOnce();
        org.easymock.EasyMock.expect(hostVersionDAO.findHostVersionByClusterAndRepository(org.easymock.EasyMock.anyLong(), org.easymock.EasyMock.anyObject(org.apache.ambari.server.orm.entities.RepositoryVersionEntity.class))).andReturn(java.util.Collections.<org.apache.ambari.server.orm.entities.HostVersionEntity>emptyList()).anyTimes();
        org.easymock.EasyMock.replay(response, clusters, resourceProviderFactory, csvResourceProvider, cluster, repositoryVersionDAOMock, configHelper, stageFactory, hostVersionDAO);
        org.apache.ambari.server.controller.spi.ResourceProvider provider = createProvider(managementController);
        injector.injectMembers(provider);
        org.springframework.security.core.context.SecurityContextHolder.getContext().setAuthentication(org.apache.ambari.server.security.TestAuthenticationFactory.createAdministrator());
        java.util.Set<java.lang.String> ids = com.google.common.collect.Sets.newHashSet(org.apache.ambari.server.controller.internal.ClusterStackVersionResourceProvider.CLUSTER_STACK_VERSION_STATE_PROPERTY_ID, org.apache.ambari.server.controller.internal.ClusterStackVersionResourceProvider.CLUSTER_STACK_VERSION_VERSION_PROPERTY_ID, org.apache.ambari.server.controller.internal.ClusterStackVersionResourceProvider.CLUSTER_STACK_VERSION_REPOSITORY_VERSION_PROPERTY_ID);
        org.apache.ambari.server.controller.spi.Predicate predicate = new org.apache.ambari.server.controller.utilities.PredicateBuilder().property(org.apache.ambari.server.controller.internal.ClusterStackVersionResourceProvider.CLUSTER_STACK_VERSION_CLUSTER_NAME_PROPERTY_ID).equals("Cluster100").toPredicate();
        org.apache.ambari.server.controller.spi.Request request = org.apache.ambari.server.controller.utilities.PropertyHelper.getReadRequest(ids);
        java.util.Set<org.apache.ambari.server.controller.spi.Resource> responses = provider.getResources(request, predicate);
        org.junit.Assert.assertNotNull(responses);
        org.easymock.EasyMock.verify(response, clusters, cluster, hostVersionDAO);
        org.junit.Assert.assertEquals(5, responses.size());
        int i = 0;
        long[] orders = new long[]{ 0, 3, 1, 2, 4 };
        for (org.apache.ambari.server.controller.spi.Resource res : responses) {
            org.junit.Assert.assertEquals(orders[i], res.getPropertyValue(org.apache.ambari.server.controller.internal.ClusterStackVersionResourceProvider.CLUSTER_STACK_VERSION_REPOSITORY_VERSION_PROPERTY_ID));
            i++;
        }
    }

    private void testCreateResourcesExistingUpgrade(org.springframework.security.core.Authentication authentication) throws java.lang.Exception {
        org.apache.ambari.server.state.Cluster cluster = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Cluster.class);
        org.easymock.EasyMock.expect(managementController.getClusters()).andReturn(clusters).anyTimes();
        org.easymock.EasyMock.expect(managementController.getAmbariMetaInfo()).andReturn(ambariMetaInfo).anyTimes();
        org.easymock.EasyMock.expect(clusters.getCluster(org.easymock.EasyMock.anyObject(java.lang.String.class))).andReturn(cluster);
        org.apache.ambari.server.orm.entities.UpgradeEntity upgrade = new org.apache.ambari.server.orm.entities.UpgradeEntity();
        upgrade.setDirection(org.apache.ambari.server.stack.upgrade.Direction.UPGRADE);
        org.easymock.EasyMock.expect(cluster.getClusterName()).andReturn("c1").atLeastOnce();
        org.easymock.EasyMock.expect(cluster.getClusterId()).andReturn(1L).anyTimes();
        org.easymock.EasyMock.expect(cluster.getUpgradeInProgress()).andReturn(upgrade).once();
        org.easymock.EasyMock.replay(managementController, clusters, cluster);
        org.apache.ambari.server.controller.spi.ResourceProvider provider = createProvider(managementController);
        injector.injectMembers(provider);
        java.util.Set<java.util.Map<java.lang.String, java.lang.Object>> propertySet = new java.util.LinkedHashSet<>();
        java.util.Map<java.lang.String, java.lang.Object> properties = new java.util.LinkedHashMap<>();
        properties.put(org.apache.ambari.server.controller.internal.ClusterStackVersionResourceProvider.CLUSTER_STACK_VERSION_CLUSTER_NAME_PROPERTY_ID, "Cluster100");
        properties.put(org.apache.ambari.server.controller.internal.ClusterStackVersionResourceProvider.CLUSTER_STACK_VERSION_REPOSITORY_VERSION_PROPERTY_ID, "2.2.0.1-885");
        properties.put(org.apache.ambari.server.controller.internal.ClusterStackVersionResourceProvider.CLUSTER_STACK_VERSION_STACK_PROPERTY_ID, "HDP");
        properties.put(org.apache.ambari.server.controller.internal.ClusterStackVersionResourceProvider.CLUSTER_STACK_VERSION_VERSION_PROPERTY_ID, "2.1.1");
        propertySet.add(properties);
        org.apache.ambari.server.controller.spi.Request request = org.apache.ambari.server.controller.utilities.PropertyHelper.getCreateRequest(propertySet, null);
        org.springframework.security.core.context.SecurityContextHolder.getContext().setAuthentication(authentication);
        try {
            provider.createResources(request);
            org.junit.Assert.fail("Expecting the create to fail due to an already installed version");
        } catch (java.lang.IllegalArgumentException iae) {
            org.junit.Assert.assertEquals("Cluster c1 upgrade is in progress.  Cannot install packages.", iae.getMessage());
        }
        org.easymock.EasyMock.verify(cluster);
    }

    @org.junit.Test
    public void testCreateResourcesPatch() throws java.lang.Exception {
        org.apache.ambari.server.state.Cluster cluster = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Cluster.class);
        org.apache.ambari.server.state.StackId stackId = new org.apache.ambari.server.state.StackId("HDP", "2.0.1");
        org.apache.ambari.server.orm.entities.StackEntity fromStack = new org.apache.ambari.server.orm.entities.StackEntity();
        fromStack.setStackName(stackId.getStackName());
        fromStack.setStackVersion(stackId.getStackVersion());
        org.apache.ambari.server.orm.entities.RepositoryVersionEntity fromRepo = new org.apache.ambari.server.orm.entities.RepositoryVersionEntity();
        fromRepo.setId(0L);
        fromRepo.addRepoOsEntities(org.apache.ambari.server.controller.internal.ClusterStackVersionResourceProviderTest.REPO_OS_NOT_MANAGED);
        fromRepo.setVersionXsd("version_definition.xsd");
        fromRepo.setType(org.apache.ambari.spi.RepositoryType.STANDARD);
        fromRepo.setStack(fromStack);
        fromRepo.setVersion("2.0.1.0-1234");
        org.apache.ambari.server.orm.entities.StackEntity stackEntity = new org.apache.ambari.server.orm.entities.StackEntity();
        stackEntity.setStackName("HDP");
        stackEntity.setStackVersion("2.1.1");
        java.lang.String hbaseVersionTestXML = ((((((((((((((((((((((((((((((((((((((((((("\n" + "<repository-version xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n") + "  xsi:noNamespaceSchemaLocation=\"version_definition.xsd\">\n") + "  \n") + "  <release>\n") + "    <type>PATCH</type>\n") + "    <stack-id>HDP-2.3</stack-id>\n") + "    <version>2.3.4.0</version>\n") + "    <build>3396</build>\n") + "    <compatible-with>2.3.2.[0-9]</compatible-with>\n") + "    <release-notes>http://docs.hortonworks.com/HDPDocuments/HDP2/HDP-2.3.4/</release-notes>\n") + "  </release>\n") + "  \n") + "  <manifest>\n") + "    <service id=\"HBASE-112\" name=\"HBASE\" version=\"1.1.2\" version-id=\"2_3_4_0-3396\" />\n") + "  </manifest>\n") + "  \n") + "  <available-services>\n") + "    <service idref=\"HBASE-112\" />\n") + "  </available-services>\n") + "  \n") + "  <repository-info>\n") + "    <os family=\"redhat6\">\n") + "      <package-version>2_3_4_0_3396</package-version>\n") + "      <repo>\n") + "        <baseurl>http://public-repo-1.hortonworks.com/HDP/centos6/2.x/updates/2.3.4.0</baseurl>\n") + "        <repoid>HDP-2.3</repoid>\n") + "        <reponame>HDP</reponame>\n") + "        <unique>true</unique>\n") + "      </repo>\n") + "      <repo>\n") + "        <baseurl>http://public-repo-1.hortonworks.com/HDP-UTILS-1.1.0.20/repos/centos6</baseurl>\n") + "        <repoid>HDP-UTILS-1.1.0.20</repoid>\n") + "        <reponame>HDP-UTILS</reponame>\n") + "        <unique>false</unique>\n") + "      </repo>\n") + "    </os>\n") + "  </repository-info>\n") + "  \n") + "  <upgrade>\n") + "    <configuration type=\"hdfs-site\">\n") + "      <set key=\"foo\" value=\"bar\" />\n") + "    </configuration>\n") + "  </upgrade>\n") + "</repository-version>";
        java.lang.String xmlString = hbaseVersionTestXML;
        xmlString = xmlString.replace("<service idref=\"ZOOKEEPER-346\" />", "");
        org.apache.ambari.server.orm.entities.RepositoryVersionEntity repoVersion = new org.apache.ambari.server.orm.entities.RepositoryVersionEntity();
        repoVersion.setId(1L);
        repoVersion.addRepoOsEntities(org.apache.ambari.server.controller.internal.ClusterStackVersionResourceProviderTest.REPO_OS_NOT_MANAGED);
        repoVersion.setVersionXml(xmlString);
        repoVersion.setVersionXsd("version_definition.xsd");
        repoVersion.setType(org.apache.ambari.spi.RepositoryType.PATCH);
        repoVersion.setStack(stackEntity);
        ambariMetaInfo.getComponent("HDP", "2.1.1", "HBASE", "HBASE_MASTER").setVersionAdvertised(true);
        ambariMetaInfo.getComponent("HDP", "2.0.1", "HBASE", "HBASE_MASTER").setVersionAdvertised(true);
        final java.lang.String hostWithoutVersionableComponents = "host3";
        java.util.List<org.apache.ambari.server.state.Host> hostsNeedingInstallCommands = new java.util.ArrayList<>();
        java.util.Map<java.lang.String, org.apache.ambari.server.state.Host> hostsForCluster = new java.util.HashMap<>();
        int hostCount = 10;
        for (int i = 0; i < hostCount; i++) {
            java.lang.String hostname = "host" + i;
            org.apache.ambari.server.state.Host host = org.easymock.EasyMock.createNiceMock(hostname, org.apache.ambari.server.state.Host.class);
            org.easymock.EasyMock.expect(host.getHostName()).andReturn(hostname).anyTimes();
            org.easymock.EasyMock.expect(host.getOsFamily()).andReturn("redhat6").anyTimes();
            org.easymock.EasyMock.expect(host.getMaintenanceState(org.easymock.EasyMock.anyLong())).andReturn(org.apache.ambari.server.state.MaintenanceState.OFF).anyTimes();
            org.easymock.EasyMock.expect(host.getAllHostVersions()).andReturn(java.util.Collections.<org.apache.ambari.server.orm.entities.HostVersionEntity>emptyList()).anyTimes();
            org.easymock.EasyMock.expect(host.getHostAttributes()).andReturn(new java.util.HashMap<java.lang.String, java.lang.String>()).anyTimes();
            org.easymock.EasyMock.replay(host);
            hostsForCluster.put(hostname, host);
            if (org.apache.commons.lang.StringUtils.equals(hostWithoutVersionableComponents, hostname)) {
                hostsNeedingInstallCommands.add(host);
            }
        }
        org.apache.ambari.server.state.Service hdfsService = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Service.class);
        org.easymock.EasyMock.expect(hdfsService.getName()).andReturn("HDFS").anyTimes();
        org.easymock.EasyMock.expect(hdfsService.getServiceComponents()).andReturn(new java.util.HashMap<java.lang.String, org.apache.ambari.server.state.ServiceComponent>());
        org.easymock.EasyMock.expect(hdfsService.getDesiredRepositoryVersion()).andReturn(fromRepo).anyTimes();
        org.apache.ambari.server.state.Service hbaseService = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Service.class);
        org.easymock.EasyMock.expect(hbaseService.getName()).andReturn("HBASE").anyTimes();
        org.easymock.EasyMock.expect(hbaseService.getServiceComponents()).andReturn(new java.util.HashMap<java.lang.String, org.apache.ambari.server.state.ServiceComponent>());
        org.easymock.EasyMock.expect(hbaseService.getDesiredRepositoryVersion()).andReturn(fromRepo).anyTimes();
        java.util.Map<java.lang.String, org.apache.ambari.server.state.Service> serviceMap = new java.util.HashMap<>();
        serviceMap.put("HDFS", hdfsService);
        serviceMap.put("HBASE", hbaseService);
        final org.apache.ambari.server.state.ServiceComponentHost schDatanode = org.easymock.EasyMock.createMock(org.apache.ambari.server.state.ServiceComponentHost.class);
        org.easymock.EasyMock.expect(schDatanode.getServiceName()).andReturn("HDFS").anyTimes();
        org.easymock.EasyMock.expect(schDatanode.getServiceComponentName()).andReturn("DATANODE").anyTimes();
        final org.apache.ambari.server.state.ServiceComponentHost schNamenode = org.easymock.EasyMock.createMock(org.apache.ambari.server.state.ServiceComponentHost.class);
        org.easymock.EasyMock.expect(schNamenode.getServiceName()).andReturn("HDFS").anyTimes();
        org.easymock.EasyMock.expect(schNamenode.getServiceComponentName()).andReturn("NAMENODE").anyTimes();
        final org.apache.ambari.server.state.ServiceComponentHost schHBM = org.easymock.EasyMock.createMock(org.apache.ambari.server.state.ServiceComponentHost.class);
        org.easymock.EasyMock.expect(schHBM.getServiceName()).andReturn("HBASE").anyTimes();
        org.easymock.EasyMock.expect(schHBM.getServiceComponentName()).andReturn("HBASE_MASTER").anyTimes();
        final java.util.List<org.apache.ambari.server.state.ServiceComponentHost> schsH1 = java.util.Arrays.asList(schDatanode, schNamenode);
        final java.util.List<org.apache.ambari.server.state.ServiceComponentHost> schsH2 = java.util.Arrays.asList(schDatanode);
        final java.util.List<org.apache.ambari.server.state.ServiceComponentHost> schsH3 = java.util.Arrays.asList(schHBM);
        org.apache.ambari.server.state.ServiceOsSpecific.Package hdfsPackage = new org.apache.ambari.server.state.ServiceOsSpecific.Package();
        hdfsPackage.setName("hdfs");
        java.util.List<org.apache.ambari.server.state.ServiceOsSpecific.Package> packages = java.util.Collections.singletonList(hdfsPackage);
        org.apache.ambari.server.actionmanager.ActionManager actionManager = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.actionmanager.ActionManager.class);
        org.apache.ambari.server.controller.RequestStatusResponse response = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.RequestStatusResponse.class);
        org.apache.ambari.server.controller.spi.ResourceProvider csvResourceProvider = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.ResourceProvider.class);
        java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.lang.String>> hostConfigTags = new java.util.HashMap<>();
        org.easymock.EasyMock.expect(configHelper.getEffectiveDesiredTags(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.cluster.ClusterImpl.class), org.easymock.EasyMock.anyObject(java.lang.String.class))).andReturn(hostConfigTags);
        org.easymock.EasyMock.expect(managementController.getClusters()).andReturn(clusters).anyTimes();
        org.easymock.EasyMock.expect(managementController.getAmbariMetaInfo()).andReturn(ambariMetaInfo).anyTimes();
        org.easymock.EasyMock.expect(managementController.getAuthName()).andReturn("admin").anyTimes();
        org.easymock.EasyMock.expect(managementController.getActionManager()).andReturn(actionManager).anyTimes();
        org.easymock.EasyMock.expect(managementController.getJdkResourceUrl()).andReturn("/JdkResourceUrl").anyTimes();
        org.easymock.EasyMock.expect(managementController.getPackagesForServiceHost(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.ServiceInfo.class), org.easymock.EasyMock.<java.util.Map<java.lang.String, java.lang.String>>anyObject(), org.easymock.EasyMock.anyObject(java.lang.String.class))).andReturn(packages).anyTimes();
        org.easymock.EasyMock.expect(managementController.findConfigurationTagsWithOverrides(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.Cluster.class), org.easymock.EasyMock.anyString(), org.easymock.EasyMock.anyObject())).andReturn(new java.util.HashMap<java.lang.String, java.util.Map<java.lang.String, java.lang.String>>()).anyTimes();
        org.easymock.EasyMock.expect(clusters.getCluster(org.easymock.EasyMock.anyObject(java.lang.String.class))).andReturn(cluster).anyTimes();
        org.easymock.EasyMock.expect(clusters.getHostsForCluster(org.easymock.EasyMock.anyObject(java.lang.String.class))).andReturn(hostsForCluster).anyTimes();
        java.lang.String clusterName = "Cluster100";
        org.easymock.EasyMock.expect(cluster.getClusterId()).andReturn(1L).anyTimes();
        org.easymock.EasyMock.expect(cluster.getHosts()).andReturn(hostsForCluster.values()).atLeastOnce();
        org.easymock.EasyMock.expect(cluster.getServices()).andReturn(serviceMap).anyTimes();
        org.easymock.EasyMock.expect(cluster.getCurrentStackVersion()).andReturn(stackId);
        org.easymock.EasyMock.expect(cluster.getServiceComponentHosts(org.easymock.EasyMock.anyObject(java.lang.String.class))).andAnswer(new org.easymock.IAnswer<java.util.List<org.apache.ambari.server.state.ServiceComponentHost>>() {
            @java.lang.Override
            public java.util.List<org.apache.ambari.server.state.ServiceComponentHost> answer() throws java.lang.Throwable {
                java.lang.String hostname = ((java.lang.String) (org.easymock.EasyMock.getCurrentArguments()[0]));
                if (hostname.equals("host2")) {
                    return schsH2;
                } else if (hostname.equals("host3")) {
                    return schsH3;
                } else {
                    return schsH1;
                }
            }
        }).anyTimes();
        org.easymock.EasyMock.expect(cluster.transitionHostsToInstalling(org.easymock.EasyMock.anyObject(org.apache.ambari.server.orm.entities.RepositoryVersionEntity.class), org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.repository.VersionDefinitionXml.class), org.easymock.EasyMock.eq(false))).andReturn(hostsNeedingInstallCommands).atLeastOnce();
        org.apache.ambari.server.agent.ExecutionCommand executionCommand = new org.apache.ambari.server.agent.ExecutionCommand();
        org.apache.ambari.server.actionmanager.ExecutionCommandWrapper executionCommandWrapper = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.actionmanager.ExecutionCommandWrapper.class);
        org.easymock.EasyMock.expect(executionCommandWrapper.getExecutionCommand()).andReturn(executionCommand).anyTimes();
        org.apache.ambari.server.actionmanager.Stage stage = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.actionmanager.Stage.class);
        org.easymock.EasyMock.expect(stage.getExecutionCommandWrapper(org.easymock.EasyMock.anyObject(java.lang.String.class), org.easymock.EasyMock.anyObject(java.lang.String.class))).andReturn(executionCommandWrapper).anyTimes();
        java.util.Map<org.apache.ambari.server.Role, java.lang.Float> successFactors = new java.util.HashMap<>();
        org.easymock.EasyMock.expect(stage.getSuccessFactors()).andReturn(successFactors).atLeastOnce();
        org.easymock.EasyMock.expect(stageFactory.createNew(org.easymock.EasyMock.anyLong(), org.easymock.EasyMock.anyObject(java.lang.String.class), org.easymock.EasyMock.anyObject(java.lang.String.class), org.easymock.EasyMock.anyLong(), org.easymock.EasyMock.anyObject(java.lang.String.class), org.easymock.EasyMock.anyObject(java.lang.String.class), org.easymock.EasyMock.anyObject(java.lang.String.class))).andReturn(stage).times(((int) (java.lang.Math.ceil(hostCount / org.apache.ambari.server.controller.internal.ClusterStackVersionResourceProviderTest.MAX_TASKS_PER_STAGE))));
        org.easymock.EasyMock.expect(repositoryVersionDAOMock.findByStackAndVersion(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.StackId.class), org.easymock.EasyMock.anyObject(java.lang.String.class))).andReturn(repoVersion);
        org.easymock.Capture<org.apache.ambari.server.actionmanager.Request> c = org.easymock.Capture.newInstance();
        org.easymock.Capture<org.apache.ambari.server.controller.ExecuteActionRequest> ear = org.easymock.Capture.newInstance();
        actionManager.sendActions(org.easymock.EasyMock.capture(c), org.easymock.EasyMock.capture(ear));
        org.easymock.EasyMock.expectLastCall().atLeastOnce();
        org.easymock.EasyMock.expect(actionManager.getRequestTasks(org.easymock.EasyMock.anyLong())).andReturn(java.util.Collections.<org.apache.ambari.server.actionmanager.HostRoleCommand>emptyList()).anyTimes();
        org.apache.ambari.server.orm.entities.ClusterEntity clusterEntity = new org.apache.ambari.server.orm.entities.ClusterEntity();
        clusterEntity.setClusterId(1L);
        clusterEntity.setClusterName(clusterName);
        org.apache.ambari.server.utils.StageUtils.setTopologyManager(injector.getInstance(org.apache.ambari.server.topology.TopologyManager.class));
        org.apache.ambari.server.utils.StageUtils.setConfiguration(injector.getInstance(org.apache.ambari.server.configuration.Configuration.class));
        org.easymock.EasyMock.replay(managementController, response, clusters, hdfsService, hbaseService, csvResourceProvider, cluster, repositoryVersionDAOMock, configHelper, schDatanode, schNamenode, schHBM, actionManager, executionCommandWrapper, stage, stageFactory);
        org.apache.ambari.server.controller.spi.ResourceProvider provider = createProvider(managementController);
        injector.injectMembers(provider);
        java.util.Set<java.util.Map<java.lang.String, java.lang.Object>> propertySet = new java.util.LinkedHashSet<>();
        java.util.Map<java.lang.String, java.lang.Object> properties = new java.util.LinkedHashMap<>();
        properties.put(org.apache.ambari.server.controller.internal.ClusterStackVersionResourceProvider.CLUSTER_STACK_VERSION_CLUSTER_NAME_PROPERTY_ID, "Cluster100");
        properties.put(org.apache.ambari.server.controller.internal.ClusterStackVersionResourceProvider.CLUSTER_STACK_VERSION_REPOSITORY_VERSION_PROPERTY_ID, "2.2.0.1-885");
        properties.put(org.apache.ambari.server.controller.internal.ClusterStackVersionResourceProvider.CLUSTER_STACK_VERSION_STACK_PROPERTY_ID, "HDP");
        properties.put(org.apache.ambari.server.controller.internal.ClusterStackVersionResourceProvider.CLUSTER_STACK_VERSION_VERSION_PROPERTY_ID, "2.1.1");
        propertySet.add(properties);
        org.apache.ambari.server.controller.spi.Request request = org.apache.ambari.server.controller.utilities.PropertyHelper.getCreateRequest(propertySet, null);
        org.springframework.security.core.context.SecurityContextHolder.getContext().setAuthentication(org.apache.ambari.server.security.TestAuthenticationFactory.createAdministrator());
        org.apache.ambari.server.controller.spi.RequestStatus status = provider.createResources(request);
        org.junit.Assert.assertNotNull(status);
        java.lang.Float successFactor = successFactors.get(org.apache.ambari.server.Role.INSTALL_PACKAGES);
        org.junit.Assert.assertEquals(java.lang.Float.valueOf(0.85F), successFactor);
        org.junit.Assert.assertNotNull(executionCommand.getRepositoryFile());
        org.junit.Assert.assertNotNull(executionCommand.getRoleParameters());
        java.util.Map<java.lang.String, java.lang.Object> roleParams = executionCommand.getRoleParameters();
        org.junit.Assert.assertTrue(roleParams.containsKey(org.apache.ambari.server.agent.ExecutionCommand.KeyNames.CLUSTER_VERSION_SUMMARY));
        org.junit.Assert.assertEquals(org.apache.ambari.server.state.repository.ClusterVersionSummary.class, roleParams.get(org.apache.ambari.server.agent.ExecutionCommand.KeyNames.CLUSTER_VERSION_SUMMARY).getClass());
        org.junit.Assert.assertEquals(2, executionCommand.getRepositoryFile().getRepositories().size());
        for (org.apache.ambari.server.agent.CommandRepository.Repository repo : executionCommand.getRepositoryFile().getRepositories()) {
            org.junit.Assert.assertFalse(repo.isAmbariManaged());
        }
    }

    private org.apache.ambari.server.controller.internal.ClusterStackVersionResourceProvider createProvider(org.apache.ambari.server.controller.AmbariManagementController amc) {
        org.apache.ambari.server.controller.ResourceProviderFactory factory = injector.getInstance(org.apache.ambari.server.controller.ResourceProviderFactory.class);
        org.apache.ambari.server.controller.internal.AbstractControllerResourceProvider.init(factory);
        org.apache.ambari.server.controller.spi.Resource.Type type = org.apache.ambari.server.controller.spi.Resource.Type.ClusterStackVersion;
        return ((org.apache.ambari.server.controller.internal.ClusterStackVersionResourceProvider) (org.apache.ambari.server.controller.internal.AbstractControllerResourceProvider.getResourceProvider(type, amc)));
    }

    private class MockModule extends com.google.inject.AbstractModule {
        @java.lang.Override
        protected void configure() {
            bind(org.apache.ambari.server.orm.dao.RepositoryVersionDAO.class).toInstance(repositoryVersionDAOMock);
            bind(org.apache.ambari.server.state.ConfigHelper.class).toInstance(configHelper);
            bind(org.apache.ambari.server.configuration.Configuration.class).toInstance(configuration);
            bind(org.apache.ambari.server.actionmanager.StageFactory.class).toInstance(stageFactory);
            bind(org.apache.ambari.server.orm.dao.HostVersionDAO.class).toInstance(hostVersionDAO);
            bind(org.apache.ambari.server.orm.dao.HostComponentStateDAO.class).toInstance(hostComponentStateDAO);
            bind(org.apache.ambari.server.state.Clusters.class).toInstance(clusters);
            bind(org.apache.ambari.server.actionmanager.ActionManager.class).toInstance(actionManager);
            bind(org.apache.ambari.server.controller.AmbariManagementController.class).toInstance(managementController);
        }
    }
}
