package org.apache.ambari.server.upgrade;
import org.easymock.Capture;
import org.easymock.CaptureType;
import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import static org.easymock.EasyMock.anyBoolean;
import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.anyString;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMockBuilder;
import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.newCapture;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.startsWith;
import static org.easymock.EasyMock.verify;
public class UpgradeCatalog271Test {
    @org.junit.Test
    public void testExecuteDDLUpdates() throws java.lang.Exception {
        org.easymock.EasyMockSupport easyMockSupport = new org.easymock.EasyMockSupport();
        com.google.inject.Injector injector = easyMockSupport.createNiceMock(com.google.inject.Injector.class);
        org.apache.ambari.server.orm.DBAccessor dbAccessor = easyMockSupport.createNiceMock(org.apache.ambari.server.orm.DBAccessor.class);
        org.easymock.Capture<org.apache.ambari.server.orm.DBAccessor.DBColumnInfo> blueprintProvisioningStateColumnCapture = org.easymock.EasyMock.newCapture(org.easymock.CaptureType.ALL);
        dbAccessor.addColumn(org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog271.CLUSTERS_TABLE), org.easymock.EasyMock.capture(blueprintProvisioningStateColumnCapture));
        org.easymock.EasyMock.expectLastCall().once();
        org.easymock.EasyMock.replay(dbAccessor, injector);
        org.apache.ambari.server.upgrade.UpgradeCatalog271 upgradeCatalog271 = new org.apache.ambari.server.upgrade.UpgradeCatalog271(injector);
        upgradeCatalog271.dbAccessor = dbAccessor;
        upgradeCatalog271.executeDDLUpdates();
        org.apache.ambari.server.orm.DBAccessor.DBColumnInfo capturedBlueprintProvisioningStateColumn = blueprintProvisioningStateColumnCapture.getValue();
        org.junit.Assert.assertEquals(org.apache.ambari.server.upgrade.UpgradeCatalog271.CLUSTERS_BLUEPRINT_PROVISIONING_STATE_COLUMN, capturedBlueprintProvisioningStateColumn.getName());
        org.junit.Assert.assertEquals(org.apache.ambari.server.state.BlueprintProvisioningState.NONE, capturedBlueprintProvisioningStateColumn.getDefaultValue());
        org.junit.Assert.assertEquals(java.lang.String.class, capturedBlueprintProvisioningStateColumn.getType());
        easyMockSupport.verifyAll();
    }

    @org.junit.Test
    public void testExecuteDMLUpdates() throws java.lang.Exception {
        java.lang.reflect.Method addNewConfigurationsFromXml = org.apache.ambari.server.upgrade.AbstractUpgradeCatalog.class.getDeclaredMethod("addNewConfigurationsFromXml");
        java.lang.reflect.Method updateRangerLogDirConfigs = org.apache.ambari.server.upgrade.UpgradeCatalog271.class.getDeclaredMethod("updateRangerLogDirConfigs");
        java.lang.reflect.Method updateRangerKmsDbUrl = org.apache.ambari.server.upgrade.UpgradeCatalog271.class.getDeclaredMethod("updateRangerKmsDbUrl");
        java.lang.reflect.Method renameAmbariInfraInConfigGroups = org.apache.ambari.server.upgrade.UpgradeCatalog271.class.getDeclaredMethod("renameAmbariInfraService");
        java.lang.reflect.Method removeLogSearchPatternConfigs = org.apache.ambari.server.upgrade.UpgradeCatalog271.class.getDeclaredMethod("removeLogSearchPatternConfigs");
        java.lang.reflect.Method updateSolrConfigurations = org.apache.ambari.server.upgrade.UpgradeCatalog271.class.getDeclaredMethod("updateSolrConfigurations");
        java.lang.reflect.Method updateTimelineReaderAddress = org.apache.ambari.server.upgrade.UpgradeCatalog271.class.getDeclaredMethod("updateTimelineReaderAddress");
        org.apache.ambari.server.upgrade.UpgradeCatalog271 upgradeCatalog271 = org.easymock.EasyMock.createMockBuilder(org.apache.ambari.server.upgrade.UpgradeCatalog271.class).addMockedMethod(updateRangerKmsDbUrl).addMockedMethod(updateRangerLogDirConfigs).addMockedMethod(addNewConfigurationsFromXml).addMockedMethod(renameAmbariInfraInConfigGroups).addMockedMethod(removeLogSearchPatternConfigs).addMockedMethod(updateSolrConfigurations).addMockedMethod(updateTimelineReaderAddress).createMock();
        upgradeCatalog271.addNewConfigurationsFromXml();
        org.easymock.EasyMock.expectLastCall().once();
        upgradeCatalog271.updateRangerLogDirConfigs();
        org.easymock.EasyMock.expectLastCall().once();
        upgradeCatalog271.updateRangerKmsDbUrl();
        org.easymock.EasyMock.expectLastCall().once();
        upgradeCatalog271.renameAmbariInfraService();
        org.easymock.EasyMock.expectLastCall().once();
        upgradeCatalog271.removeLogSearchPatternConfigs();
        org.easymock.EasyMock.expectLastCall().once();
        upgradeCatalog271.updateSolrConfigurations();
        org.easymock.EasyMock.expectLastCall().once();
        upgradeCatalog271.updateTimelineReaderAddress();
        org.easymock.EasyMock.expectLastCall().once();
        org.easymock.EasyMock.replay(upgradeCatalog271);
        upgradeCatalog271.executeDMLUpdates();
        org.easymock.EasyMock.verify(upgradeCatalog271);
    }

    @org.junit.Test
    public void testRemoveLogSearchPatternConfigs() throws java.lang.Exception {
        org.easymock.EasyMockSupport easyMockSupport = new org.easymock.EasyMockSupport();
        com.google.inject.Injector injector = easyMockSupport.createNiceMock(com.google.inject.Injector.class);
        org.apache.ambari.server.orm.DBAccessor dbAccessor = easyMockSupport.createNiceMock(org.apache.ambari.server.orm.DBAccessor.class);
        org.easymock.EasyMock.expect(injector.getInstance(org.apache.ambari.server.orm.DBAccessor.class)).andReturn(dbAccessor).anyTimes();
        java.lang.String serviceConfigMapping = "serviceconfigmapping";
        java.lang.String clusterConfig = "clusterconfig";
        dbAccessor.executeQuery(org.easymock.EasyMock.startsWith("DELETE FROM " + serviceConfigMapping));
        org.easymock.EasyMock.expectLastCall().once();
        dbAccessor.executeQuery(org.easymock.EasyMock.startsWith("DELETE FROM " + clusterConfig));
        org.easymock.EasyMock.expectLastCall().once();
        org.easymock.EasyMock.replay(dbAccessor, injector);
        new org.apache.ambari.server.upgrade.UpgradeCatalog271(injector).removeLogSearchPatternConfigs();
        easyMockSupport.verifyAll();
    }

    @org.junit.Test
    public void testUpdateRangerLogDirConfigs() throws java.lang.Exception {
        java.util.Map<java.lang.String, org.apache.ambari.server.state.Service> installedServices = new java.util.HashMap<java.lang.String, org.apache.ambari.server.state.Service>() {
            {
                put("RANGER", null);
            }
        };
        java.util.Map<java.lang.String, java.lang.String> rangerEnvConfig = new java.util.HashMap<java.lang.String, java.lang.String>() {
            {
                put("ranger_admin_log_dir", "/var/log/ranger/admin");
                put("ranger_usersync_log_dir", "/var/log/ranger/usersync");
            }
        };
        java.util.Map<java.lang.String, java.lang.String> oldRangerUgsyncSiteConfig = new java.util.HashMap<java.lang.String, java.lang.String>() {
            {
                put("ranger.usersync.logdir", "{{usersync_log_dir}}");
            }
        };
        org.easymock.EasyMockSupport easyMockSupport = new org.easymock.EasyMockSupport();
        org.apache.ambari.server.state.Clusters clusters = easyMockSupport.createNiceMock(org.apache.ambari.server.state.Clusters.class);
        final org.apache.ambari.server.state.Cluster cluster = easyMockSupport.createNiceMock(org.apache.ambari.server.state.Cluster.class);
        com.google.inject.Injector injector = easyMockSupport.createNiceMock(com.google.inject.Injector.class);
        org.apache.ambari.server.controller.AmbariManagementControllerImpl controller = org.easymock.EasyMock.createMockBuilder(org.apache.ambari.server.controller.AmbariManagementControllerImpl.class).addMockedMethod("createConfiguration", org.apache.ambari.server.controller.ConfigurationRequest.class).addMockedMethod("getClusters", new java.lang.Class[]{  }).addMockedMethod("createConfig", org.apache.ambari.server.state.Cluster.class, org.apache.ambari.server.state.StackId.class, java.lang.String.class, java.util.Map.class, java.lang.String.class, java.util.Map.class).createNiceMock();
        org.easymock.EasyMock.expect(injector.getInstance(org.apache.ambari.server.controller.AmbariManagementController.class)).andReturn(controller).anyTimes();
        org.easymock.EasyMock.expect(controller.getClusters()).andReturn(clusters).anyTimes();
        org.easymock.EasyMock.expect(clusters.getClusters()).andReturn(new java.util.HashMap<java.lang.String, org.apache.ambari.server.state.Cluster>() {
            {
                put("normal", cluster);
            }
        }).once();
        org.easymock.EasyMock.expect(cluster.getClusterName()).andReturn("cl1").anyTimes();
        org.easymock.EasyMock.expect(cluster.getServices()).andReturn(installedServices).atLeastOnce();
        org.apache.ambari.server.state.Config mockRangerEnvConfig = easyMockSupport.createNiceMock(org.apache.ambari.server.state.Config.class);
        org.easymock.EasyMock.expect(cluster.getDesiredConfigByType("ranger-env")).andReturn(mockRangerEnvConfig).atLeastOnce();
        org.easymock.EasyMock.expect(mockRangerEnvConfig.getProperties()).andReturn(rangerEnvConfig).anyTimes();
        org.apache.ambari.server.state.Config mockRangerAdminSiteConfig = easyMockSupport.createNiceMock(org.apache.ambari.server.state.Config.class);
        org.easymock.EasyMock.expect(cluster.getDesiredConfigByType("ranger-admin-site")).andReturn(mockRangerAdminSiteConfig).atLeastOnce();
        org.easymock.EasyMock.expect(mockRangerAdminSiteConfig.getProperties()).andReturn(java.util.Collections.emptyMap()).anyTimes();
        org.apache.ambari.server.state.Config mockRangerUgsyncSiteConfig = easyMockSupport.createNiceMock(org.apache.ambari.server.state.Config.class);
        org.easymock.EasyMock.expect(cluster.getDesiredConfigByType("ranger-ugsync-site")).andReturn(mockRangerUgsyncSiteConfig).atLeastOnce();
        org.easymock.EasyMock.expect(mockRangerUgsyncSiteConfig.getProperties()).andReturn(oldRangerUgsyncSiteConfig).anyTimes();
        org.easymock.Capture<java.util.Map> rangerAdminpropertiesCapture = org.easymock.EasyMock.newCapture();
        org.easymock.EasyMock.expect(controller.createConfig(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.Cluster.class), org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.StackId.class), org.easymock.EasyMock.anyString(), org.easymock.EasyMock.capture(rangerAdminpropertiesCapture), org.easymock.EasyMock.anyString(), org.easymock.EasyMock.anyObject(java.util.Map.class))).andReturn(org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Config.class)).once();
        org.easymock.Capture<java.util.Map> rangerUgsyncPropertiesCapture = org.easymock.EasyMock.newCapture();
        org.easymock.EasyMock.expect(controller.createConfig(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.Cluster.class), org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.StackId.class), org.easymock.EasyMock.anyString(), org.easymock.EasyMock.capture(rangerUgsyncPropertiesCapture), org.easymock.EasyMock.anyString(), org.easymock.EasyMock.anyObject(java.util.Map.class))).andReturn(org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Config.class)).once();
        org.easymock.Capture<java.util.Map> rangerEnvPropertiesCapture = org.easymock.EasyMock.newCapture();
        org.easymock.EasyMock.expect(controller.createConfig(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.Cluster.class), org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.StackId.class), org.easymock.EasyMock.anyString(), org.easymock.EasyMock.capture(rangerEnvPropertiesCapture), org.easymock.EasyMock.anyString(), org.easymock.EasyMock.anyObject(java.util.Map.class))).andReturn(org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Config.class)).once();
        org.easymock.EasyMock.replay(controller, injector, clusters, mockRangerEnvConfig, mockRangerAdminSiteConfig, mockRangerUgsyncSiteConfig, cluster);
        new org.apache.ambari.server.upgrade.UpgradeCatalog271(injector).updateRangerLogDirConfigs();
        easyMockSupport.verifyAll();
        java.util.Map<java.lang.String, java.lang.String> updatedRangerAdminConfig = rangerAdminpropertiesCapture.getValue();
        org.junit.Assert.assertEquals(updatedRangerAdminConfig.get("ranger.logs.base.dir"), "/var/log/ranger/admin");
        java.util.Map<java.lang.String, java.lang.String> updatedRangerUgsyncSiteConfig = rangerUgsyncPropertiesCapture.getValue();
        org.junit.Assert.assertEquals(updatedRangerUgsyncSiteConfig.get("ranger.usersync.logdir"), "/var/log/ranger/usersync");
        java.util.Map<java.lang.String, java.lang.String> updatedRangerEnvConfig = rangerEnvPropertiesCapture.getValue();
        org.junit.Assert.assertFalse(updatedRangerEnvConfig.containsKey("ranger_admin_log_dir"));
        org.junit.Assert.assertFalse(updatedRangerEnvConfig.containsKey("ranger_usersync_log_dir"));
    }

    @org.junit.Test
    public void testUpdateRangerKmsDbUrl() throws java.lang.Exception {
        java.util.Map<java.lang.String, org.apache.ambari.server.state.Service> installedServices = new java.util.HashMap<java.lang.String, org.apache.ambari.server.state.Service>() {
            {
                put("RANGER_KMS", null);
            }
        };
        java.util.Map<java.lang.String, java.lang.String> rangerKmsPropertiesConfig = new java.util.HashMap<java.lang.String, java.lang.String>() {
            {
                put("DB_FLAVOR", "MYSQL");
                put("db_host", "c6401.ambari.apache.org");
            }
        };
        java.util.Map<java.lang.String, java.lang.String> rangerKmsDbksPropertiesConfig = new java.util.HashMap<java.lang.String, java.lang.String>() {
            {
                put("ranger.ks.jpa.jdbc.url", "jdbc:mysql://c6401.ambari.apache.org:3546");
            }
        };
        org.easymock.EasyMockSupport easyMockSupport = new org.easymock.EasyMockSupport();
        org.apache.ambari.server.state.Clusters clusters = easyMockSupport.createNiceMock(org.apache.ambari.server.state.Clusters.class);
        final org.apache.ambari.server.state.Cluster cluster = easyMockSupport.createNiceMock(org.apache.ambari.server.state.Cluster.class);
        com.google.inject.Injector injector = easyMockSupport.createNiceMock(com.google.inject.Injector.class);
        org.apache.ambari.server.controller.AmbariManagementControllerImpl controller = org.easymock.EasyMock.createMockBuilder(org.apache.ambari.server.controller.AmbariManagementControllerImpl.class).addMockedMethod("createConfiguration", org.apache.ambari.server.controller.ConfigurationRequest.class).addMockedMethod("getClusters", new java.lang.Class[]{  }).addMockedMethod("createConfig", org.apache.ambari.server.state.Cluster.class, org.apache.ambari.server.state.StackId.class, java.lang.String.class, java.util.Map.class, java.lang.String.class, java.util.Map.class).createNiceMock();
        org.easymock.EasyMock.expect(injector.getInstance(org.apache.ambari.server.controller.AmbariManagementController.class)).andReturn(controller).anyTimes();
        org.easymock.EasyMock.expect(controller.getClusters()).andReturn(clusters).anyTimes();
        org.easymock.EasyMock.expect(clusters.getClusters()).andReturn(new java.util.HashMap<java.lang.String, org.apache.ambari.server.state.Cluster>() {
            {
                put("normal", cluster);
            }
        }).once();
        org.easymock.EasyMock.expect(cluster.getClusterName()).andReturn("cl1").once();
        org.easymock.EasyMock.expect(cluster.getServices()).andReturn(installedServices).atLeastOnce();
        org.apache.ambari.server.state.Config mockRangerKmsPropertiesConfig = easyMockSupport.createNiceMock(org.apache.ambari.server.state.Config.class);
        org.easymock.EasyMock.expect(cluster.getDesiredConfigByType("kms-properties")).andReturn(mockRangerKmsPropertiesConfig).atLeastOnce();
        org.apache.ambari.server.state.Config mockRangerKmsEnvConfig = easyMockSupport.createNiceMock(org.apache.ambari.server.state.Config.class);
        org.easymock.EasyMock.expect(cluster.getDesiredConfigByType("kms-env")).andReturn(mockRangerKmsEnvConfig).atLeastOnce();
        org.apache.ambari.server.state.Config mockRangerKmsDbksConfig = easyMockSupport.createNiceMock(org.apache.ambari.server.state.Config.class);
        org.easymock.EasyMock.expect(cluster.getDesiredConfigByType("dbks-site")).andReturn(mockRangerKmsDbksConfig).atLeastOnce();
        org.easymock.EasyMock.expect(mockRangerKmsPropertiesConfig.getProperties()).andReturn(rangerKmsPropertiesConfig).anyTimes();
        org.easymock.EasyMock.expect(mockRangerKmsEnvConfig.getProperties()).andReturn(java.util.Collections.emptyMap()).anyTimes();
        org.easymock.EasyMock.expect(mockRangerKmsDbksConfig.getProperties()).andReturn(rangerKmsDbksPropertiesConfig).anyTimes();
        org.easymock.Capture<java.util.Map> propertiesCapture = org.easymock.EasyMock.newCapture();
        org.easymock.EasyMock.expect(controller.createConfig(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.Cluster.class), org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.StackId.class), org.easymock.EasyMock.anyString(), org.easymock.EasyMock.capture(propertiesCapture), org.easymock.EasyMock.anyString(), org.easymock.EasyMock.anyObject(java.util.Map.class))).andReturn(org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Config.class)).once();
        org.easymock.EasyMock.replay(controller, injector, clusters, mockRangerKmsPropertiesConfig, mockRangerKmsEnvConfig, mockRangerKmsDbksConfig, cluster);
        new org.apache.ambari.server.upgrade.UpgradeCatalog271(injector).updateRangerKmsDbUrl();
        easyMockSupport.verifyAll();
        java.util.Map<java.lang.String, java.lang.String> updatedRangerKmsEnvConfig = propertiesCapture.getValue();
        org.junit.Assert.assertEquals(updatedRangerKmsEnvConfig.get("ranger_kms_privelege_user_jdbc_url"), "jdbc:mysql://c6401.ambari.apache.org:3546");
    }

    @org.junit.Test
    public void testUpdateSolrConfigurations() throws java.lang.Exception {
        org.easymock.EasyMockSupport easyMockSupport = new org.easymock.EasyMockSupport();
        org.apache.ambari.server.state.Clusters clusters = easyMockSupport.createNiceMock(org.apache.ambari.server.state.Clusters.class);
        final org.apache.ambari.server.state.Cluster cluster = easyMockSupport.createNiceMock(org.apache.ambari.server.state.Cluster.class);
        org.apache.ambari.server.state.Config mockedServiceLogSolrConfig = easyMockSupport.createNiceMock(org.apache.ambari.server.state.Config.class);
        org.apache.ambari.server.state.Config mockedAudiitLogSolrConfig = easyMockSupport.createNiceMock(org.apache.ambari.server.state.Config.class);
        org.apache.ambari.server.state.Config mockedSolrLog4JConfig = easyMockSupport.createNiceMock(org.apache.ambari.server.state.Config.class);
        java.util.Map<java.lang.String, org.apache.ambari.server.state.Config> allDummy = new java.util.HashMap<>();
        java.util.Map<java.lang.String, java.lang.String> serviceLogProps = new java.util.HashMap<>();
        serviceLogProps.put("content", "<luceneMatchVersion>7.3.1</luceneMatchVersion>");
        java.util.Map<java.lang.String, java.lang.String> auditLogProps = new java.util.HashMap<>();
        auditLogProps.put("content", "<luceneMatchVersion>7.3.1</luceneMatchVersion>");
        java.util.Map<java.lang.String, java.lang.String> solrLog4jProps = new java.util.HashMap<>();
        solrLog4jProps.put("content", "log4jContent");
        com.google.inject.Injector injector = easyMockSupport.createNiceMock(com.google.inject.Injector.class);
        org.apache.ambari.server.controller.AmbariManagementControllerImpl controller = org.easymock.EasyMock.createMockBuilder(org.apache.ambari.server.controller.AmbariManagementControllerImpl.class).addMockedMethod("createConfiguration", org.apache.ambari.server.controller.ConfigurationRequest.class).addMockedMethod("getClusters", new java.lang.Class[]{  }).addMockedMethod("createConfig", org.apache.ambari.server.state.Cluster.class, org.apache.ambari.server.state.StackId.class, java.lang.String.class, java.util.Map.class, java.lang.String.class, java.util.Map.class).createNiceMock();
        org.apache.ambari.server.orm.dao.DaoUtils daoUtilsMock = easyMockSupport.createNiceMock(org.apache.ambari.server.orm.dao.DaoUtils.class);
        java.util.Map<java.lang.String, org.apache.ambari.server.state.Cluster> clusterMap = new java.util.HashMap<>();
        clusterMap.put("cl1", cluster);
        org.easymock.EasyMock.expect(injector.getInstance(org.apache.ambari.server.controller.AmbariManagementController.class)).andReturn(controller).anyTimes();
        org.easymock.EasyMock.expect(injector.getInstance(org.apache.ambari.server.orm.dao.DaoUtils.class)).andReturn(daoUtilsMock).anyTimes();
        org.easymock.EasyMock.expect(controller.getClusters()).andReturn(clusters).anyTimes();
        org.easymock.EasyMock.expect(clusters.getClusters()).andReturn(clusterMap).anyTimes();
        org.easymock.EasyMock.expect(cluster.getDesiredConfigByType("logsearch-service_logs-solrconfig")).andReturn(mockedServiceLogSolrConfig);
        org.easymock.EasyMock.expect(cluster.getDesiredConfigByType("logsearch-audit_logs-solrconfig")).andReturn(mockedAudiitLogSolrConfig);
        org.easymock.EasyMock.expect(cluster.getDesiredConfigByType("infra-solr-log4j")).andReturn(mockedSolrLog4JConfig);
        org.easymock.EasyMock.expect(mockedServiceLogSolrConfig.getProperties()).andReturn(serviceLogProps).anyTimes();
        org.easymock.EasyMock.expect(mockedAudiitLogSolrConfig.getProperties()).andReturn(auditLogProps).anyTimes();
        org.easymock.EasyMock.expect(mockedSolrLog4JConfig.getProperties()).andReturn(solrLog4jProps).anyTimes();
        org.easymock.EasyMock.replay(daoUtilsMock, controller, injector, clusters, cluster, mockedServiceLogSolrConfig, mockedAudiitLogSolrConfig, mockedSolrLog4JConfig);
        org.apache.ambari.server.upgrade.UpgradeCatalog271 underTest = org.easymock.EasyMock.createMockBuilder(org.apache.ambari.server.upgrade.UpgradeCatalog271.class).withConstructor(com.google.inject.Injector.class).withArgs(injector).addMockedMethod("updateConfigurationPropertiesForCluster", org.apache.ambari.server.state.Cluster.class, java.lang.String.class, java.util.Map.class, boolean.class, boolean.class).createNiceMock();
        underTest.updateConfigurationPropertiesForCluster(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.Cluster.class), org.easymock.EasyMock.anyString(), org.easymock.EasyMock.anyObject(), org.easymock.EasyMock.anyBoolean(), org.easymock.EasyMock.anyBoolean());
        org.easymock.EasyMock.expectLastCall().times(3);
        org.easymock.EasyMock.replay(underTest);
        underTest.updateSolrConfigurations();
        easyMockSupport.verifyAll();
    }
}
