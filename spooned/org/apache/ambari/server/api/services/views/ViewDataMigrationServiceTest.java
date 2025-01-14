package org.apache.ambari.server.api.services.views;
import javax.ws.rs.WebApplicationException;
import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
public class ViewDataMigrationServiceTest {
    private static java.lang.String viewName = "MY_VIEW";

    private static java.lang.String instanceName = "INSTANCE1";

    private static java.lang.String version1 = "1.0.0";

    private static java.lang.String version2 = "2.0.0";

    @org.junit.Rule
    public org.junit.rules.ExpectedException thrown = org.junit.rules.ExpectedException.none();

    @org.junit.Test
    public void testServiceMigrateCallAdmin() throws java.lang.Exception {
        org.apache.ambari.server.view.ViewRegistry viewRegistry = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.view.ViewRegistry.class);
        org.easymock.EasyMock.expect(viewRegistry.checkAdmin()).andReturn(true).anyTimes();
        org.easymock.EasyMock.replay(viewRegistry);
        org.apache.ambari.server.view.ViewRegistry.initInstance(viewRegistry);
        org.apache.ambari.server.api.services.views.ViewDataMigrationService service = new org.apache.ambari.server.api.services.views.ViewDataMigrationService();
        org.apache.ambari.server.view.ViewDataMigrationUtility migrationUtility = org.easymock.EasyMock.createStrictMock(org.apache.ambari.server.view.ViewDataMigrationUtility.class);
        migrationUtility.migrateData(org.easymock.EasyMock.anyObject(org.apache.ambari.server.orm.entities.ViewInstanceEntity.class), org.easymock.EasyMock.anyObject(org.apache.ambari.server.orm.entities.ViewInstanceEntity.class), org.easymock.EasyMock.eq(false));
        org.easymock.EasyMock.replay(migrationUtility);
        service.setViewDataMigrationUtility(migrationUtility);
        service.migrateData(org.apache.ambari.server.api.services.views.ViewDataMigrationServiceTest.viewName, org.apache.ambari.server.api.services.views.ViewDataMigrationServiceTest.version1, org.apache.ambari.server.api.services.views.ViewDataMigrationServiceTest.instanceName, org.apache.ambari.server.api.services.views.ViewDataMigrationServiceTest.version2, org.apache.ambari.server.api.services.views.ViewDataMigrationServiceTest.instanceName);
        org.easymock.EasyMock.verify(migrationUtility);
    }

    @org.junit.Test
    public void testServiceMigrateCallNotAdmin() throws java.lang.Exception {
        org.apache.ambari.server.view.ViewRegistry viewRegistry = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.view.ViewRegistry.class);
        org.easymock.EasyMock.expect(viewRegistry.checkAdmin()).andReturn(false).anyTimes();
        org.easymock.EasyMock.replay(viewRegistry);
        org.apache.ambari.server.view.ViewRegistry.initInstance(viewRegistry);
        org.apache.ambari.server.api.services.views.ViewDataMigrationService service = new org.apache.ambari.server.api.services.views.ViewDataMigrationService();
        thrown.expect(javax.ws.rs.WebApplicationException.class);
        service.migrateData(org.apache.ambari.server.api.services.views.ViewDataMigrationServiceTest.viewName, org.apache.ambari.server.api.services.views.ViewDataMigrationServiceTest.version1, org.apache.ambari.server.api.services.views.ViewDataMigrationServiceTest.instanceName, org.apache.ambari.server.api.services.views.ViewDataMigrationServiceTest.version2, org.apache.ambari.server.api.services.views.ViewDataMigrationServiceTest.instanceName);
    }
}
