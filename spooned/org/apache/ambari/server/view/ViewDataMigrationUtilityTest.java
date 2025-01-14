package org.apache.ambari.server.view;
import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
public class ViewDataMigrationUtilityTest {
    private static java.lang.String viewName = "MY_VIEW";

    private static java.lang.String instanceName = "INSTANCE1";

    private static java.lang.String version1 = "1.0.0";

    private static java.lang.String version2 = "2.0.0";

    @org.junit.Rule
    public org.junit.rules.ExpectedException thrown = org.junit.rules.ExpectedException.none();

    org.apache.ambari.server.view.ViewRegistry viewRegistry;

    @org.junit.Before
    public void setUp() throws java.lang.Exception {
        viewRegistry = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.view.ViewRegistry.class);
        viewRegistry.copyPrivileges(org.easymock.EasyMock.anyObject(org.apache.ambari.server.orm.entities.ViewInstanceEntity.class), org.easymock.EasyMock.anyObject(org.apache.ambari.server.orm.entities.ViewInstanceEntity.class));
        org.easymock.EasyMock.expectLastCall().anyTimes();
        org.easymock.EasyMock.replay(viewRegistry);
        org.apache.ambari.server.view.ViewRegistry.initInstance(viewRegistry);
    }

    @org.junit.Test
    public void testMigrateDataSameVersions() throws java.lang.Exception {
        org.apache.ambari.server.view.ViewDataMigrationUtilityTest.TestViewDataMigrationUtility migrationUtility = new org.apache.ambari.server.view.ViewDataMigrationUtilityTest.TestViewDataMigrationUtility(viewRegistry);
        org.apache.ambari.server.view.ViewDataMigrationContextImpl context = org.apache.ambari.server.view.ViewDataMigrationUtilityTest.getViewDataMigrationContext(42, 42);
        migrationUtility.setMigrationContext(context);
        org.apache.ambari.view.migration.ViewDataMigrator migrator = migrationUtility.getViewDataMigrator(getInstanceDefinition(org.apache.ambari.server.view.ViewDataMigrationUtilityTest.viewName, org.apache.ambari.server.view.ViewDataMigrationUtilityTest.version2, org.apache.ambari.server.view.ViewDataMigrationUtilityTest.instanceName), context);
        junit.framework.Assert.assertTrue(migrator instanceof org.apache.ambari.server.view.ViewDataMigrationUtility.CopyAllDataMigrator);
    }

    @org.junit.Test
    public void testMigrateDataDifferentVersions() throws java.lang.Exception {
        org.apache.ambari.server.view.ViewDataMigrationUtilityTest.TestViewDataMigrationUtility migrationUtility = new org.apache.ambari.server.view.ViewDataMigrationUtilityTest.TestViewDataMigrationUtility(viewRegistry);
        org.apache.ambari.server.view.ViewDataMigrationContextImpl context = org.apache.ambari.server.view.ViewDataMigrationUtilityTest.getViewDataMigrationContext(2, 1);
        migrationUtility.setMigrationContext(context);
        org.apache.ambari.view.migration.ViewDataMigrator migrator = org.easymock.EasyMock.createStrictMock(org.apache.ambari.view.migration.ViewDataMigrator.class);
        org.easymock.EasyMock.expect(migrator.beforeMigration()).andReturn(true);
        migrator.migrateEntity(org.easymock.EasyMock.anyObject(java.lang.Class.class), org.easymock.EasyMock.anyObject(java.lang.Class.class));
        org.easymock.EasyMock.expectLastCall();
        migrator.migrateInstanceData();
        org.easymock.EasyMock.expectLastCall();
        migrator.afterMigration();
        org.easymock.EasyMock.expectLastCall();
        org.easymock.EasyMock.replay(migrator);
        org.apache.ambari.server.orm.entities.ViewInstanceEntity targetInstance = getInstanceDefinition(org.apache.ambari.server.view.ViewDataMigrationUtilityTest.viewName, org.apache.ambari.server.view.ViewDataMigrationUtilityTest.version2, org.apache.ambari.server.view.ViewDataMigrationUtilityTest.instanceName, migrator);
        org.apache.ambari.server.orm.entities.ViewInstanceEntity sourceInstance = getInstanceDefinition(org.apache.ambari.server.view.ViewDataMigrationUtilityTest.viewName, org.apache.ambari.server.view.ViewDataMigrationUtilityTest.version1, org.apache.ambari.server.view.ViewDataMigrationUtilityTest.instanceName);
        migrationUtility.migrateData(targetInstance, sourceInstance, false);
        org.easymock.EasyMock.verify(migrator);
    }

    @org.junit.Test
    public void testMigrateDataDifferentVersionsCancel() throws java.lang.Exception {
        org.apache.ambari.server.view.ViewDataMigrationUtilityTest.TestViewDataMigrationUtility migrationUtility = new org.apache.ambari.server.view.ViewDataMigrationUtilityTest.TestViewDataMigrationUtility(viewRegistry);
        org.apache.ambari.server.view.ViewDataMigrationContextImpl context = org.apache.ambari.server.view.ViewDataMigrationUtilityTest.getViewDataMigrationContext(2, 1);
        migrationUtility.setMigrationContext(context);
        org.apache.ambari.view.migration.ViewDataMigrator migrator = org.easymock.EasyMock.createStrictMock(org.apache.ambari.view.migration.ViewDataMigrator.class);
        org.easymock.EasyMock.expect(migrator.beforeMigration()).andReturn(false);
        org.apache.ambari.server.orm.entities.ViewInstanceEntity targetInstance = getInstanceDefinition(org.apache.ambari.server.view.ViewDataMigrationUtilityTest.viewName, org.apache.ambari.server.view.ViewDataMigrationUtilityTest.version2, org.apache.ambari.server.view.ViewDataMigrationUtilityTest.instanceName, migrator);
        org.apache.ambari.server.orm.entities.ViewInstanceEntity sourceInstance = getInstanceDefinition(org.apache.ambari.server.view.ViewDataMigrationUtilityTest.viewName, org.apache.ambari.server.view.ViewDataMigrationUtilityTest.version1, org.apache.ambari.server.view.ViewDataMigrationUtilityTest.instanceName);
        thrown.expect(org.apache.ambari.view.migration.ViewDataMigrationException.class);
        migrationUtility.migrateData(targetInstance, sourceInstance, false);
    }

    private static org.apache.ambari.server.view.ViewDataMigrationContextImpl getViewDataMigrationContext(int currentVersion, int originVersion) {
        java.util.Map<java.lang.String, java.lang.Class> entities = java.util.Collections.singletonMap("MyEntityClass", java.lang.Object.class);
        org.apache.ambari.server.view.ViewDataMigrationContextImpl context = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.view.ViewDataMigrationContextImpl.class);
        org.easymock.EasyMock.expect(context.getOriginDataVersion()).andReturn(originVersion).anyTimes();
        org.easymock.EasyMock.expect(context.getCurrentDataVersion()).andReturn(currentVersion).anyTimes();
        org.easymock.EasyMock.expect(context.getOriginEntityClasses()).andReturn(entities).anyTimes();
        org.easymock.EasyMock.expect(context.getCurrentEntityClasses()).andReturn(entities).anyTimes();
        org.easymock.EasyMock.expect(context.getCurrentInstanceDataByUser()).andReturn(new java.util.HashMap<>());
        org.easymock.EasyMock.replay(context);
        return context;
    }

    private static class TestViewDataMigrationUtility extends org.apache.ambari.server.view.ViewDataMigrationUtility {
        private org.apache.ambari.server.view.ViewDataMigrationContextImpl migrationContext;

        public TestViewDataMigrationUtility(org.apache.ambari.server.view.ViewRegistry viewRegistry) {
            super(viewRegistry);
        }

        @java.lang.Override
        protected org.apache.ambari.server.view.ViewDataMigrationContextImpl getViewDataMigrationContext(org.apache.ambari.server.orm.entities.ViewInstanceEntity targetInstanceDefinition, org.apache.ambari.server.orm.entities.ViewInstanceEntity sourceInstanceDefinition) {
            if (migrationContext == null) {
                return super.getViewDataMigrationContext(targetInstanceDefinition, sourceInstanceDefinition);
            }
            return migrationContext;
        }

        public org.apache.ambari.server.view.ViewDataMigrationContextImpl getMigrationContext() {
            return migrationContext;
        }

        public void setMigrationContext(org.apache.ambari.server.view.ViewDataMigrationContextImpl migrationContext) {
            this.migrationContext = migrationContext;
        }
    }

    private org.apache.ambari.server.orm.entities.ViewInstanceEntity getInstanceDefinition(java.lang.String viewName, java.lang.String viewVersion, java.lang.String instanceName) {
        org.apache.ambari.view.migration.ViewDataMigrator migrator = org.easymock.EasyMock.createNiceMock(org.apache.ambari.view.migration.ViewDataMigrator.class);
        org.easymock.EasyMock.replay(migrator);
        return getInstanceDefinition(viewName, viewVersion, instanceName, migrator);
    }

    private org.apache.ambari.server.orm.entities.ViewInstanceEntity getInstanceDefinition(java.lang.String viewName, java.lang.String viewVersion, java.lang.String instanceName, org.apache.ambari.view.migration.ViewDataMigrator migrator) {
        org.apache.ambari.server.orm.entities.ViewEntity viewEntity = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.orm.entities.ViewEntity.class);
        org.easymock.EasyMock.expect(viewEntity.getViewName()).andReturn(viewName);
        org.easymock.EasyMock.expect(viewEntity.getVersion()).andReturn(viewVersion);
        org.easymock.EasyMock.replay(viewEntity);
        org.apache.ambari.server.orm.entities.ViewInstanceEntity instanceEntity = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.orm.entities.ViewInstanceEntity.class);
        org.easymock.EasyMock.expect(instanceEntity.getViewEntity()).andReturn(viewEntity);
        org.easymock.EasyMock.expect(instanceEntity.getViewName()).andReturn(viewName);
        org.easymock.EasyMock.expect(instanceEntity.getInstanceName()).andReturn(instanceName);
        try {
            org.easymock.EasyMock.expect(instanceEntity.getDataMigrator(org.easymock.EasyMock.anyObject(org.apache.ambari.view.migration.ViewDataMigrationContext.class))).andReturn(migrator);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
        org.easymock.EasyMock.replay(instanceEntity);
        return instanceEntity;
    }
}
