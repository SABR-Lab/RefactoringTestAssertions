package org.apache.ambari.server.upgrade;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.inject.persist.UnitOfWork;
import javax.persistence.EntityManager;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.MockType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.anyString;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.createMockBuilder;
import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.mock;
import static org.easymock.EasyMock.newCapture;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;
@org.junit.runner.RunWith(org.easymock.EasyMockRunner.class)
public class UpgradeCatalog260Test {
    @org.easymock.Mock(type = org.easymock.MockType.STRICT)
    private com.google.inject.Provider<javax.persistence.EntityManager> entityManagerProvider;

    @org.easymock.Mock(type = org.easymock.MockType.NICE)
    private javax.persistence.EntityManager entityManager;

    @org.easymock.Mock(type = org.easymock.MockType.NICE)
    private org.apache.ambari.server.orm.DBAccessor dbAccessor;

    @org.easymock.Mock(type = org.easymock.MockType.NICE)
    private org.apache.ambari.server.configuration.Configuration configuration;

    @org.easymock.Mock(type = org.easymock.MockType.NICE)
    private java.sql.Connection connection;

    @org.easymock.Mock(type = org.easymock.MockType.NICE)
    private java.sql.Statement statement;

    @org.easymock.Mock(type = org.easymock.MockType.NICE)
    private java.sql.ResultSet resultSet;

    @org.easymock.Mock(type = org.easymock.MockType.NICE)
    private org.apache.ambari.server.state.stack.OsFamily osFamily;

    @org.junit.Rule
    public org.junit.rules.TemporaryFolder temporaryFolder = new org.junit.rules.TemporaryFolder();

    @org.junit.Before
    public void init() {
        org.easymock.EasyMock.reset(entityManagerProvider);
        org.easymock.EasyMock.expect(entityManagerProvider.get()).andReturn(entityManager).anyTimes();
        org.easymock.EasyMock.replay(entityManagerProvider);
    }

    @org.junit.After
    public void tearDown() {
    }

    @org.junit.Test
    public void testExecuteDDLUpdates() throws java.lang.Exception {
        java.util.List<java.lang.Integer> current = new java.util.ArrayList<>();
        current.add(1);
        org.easymock.EasyMock.expect(dbAccessor.getConnection()).andReturn(connection).anyTimes();
        org.easymock.EasyMock.expect(connection.createStatement()).andReturn(statement).anyTimes();
        org.easymock.EasyMock.expect(statement.executeQuery(org.easymock.EasyMock.anyObject(java.lang.String.class))).andReturn(resultSet).anyTimes();
        org.easymock.EasyMock.expect(configuration.getDatabaseType()).andReturn(org.apache.ambari.server.configuration.Configuration.DatabaseType.POSTGRES).anyTimes();
        org.easymock.EasyMock.expect(dbAccessor.tableHasColumn(org.apache.ambari.server.upgrade.UpgradeCatalog260.CLUSTER_CONFIG_TABLE, org.apache.ambari.server.upgrade.UpgradeCatalog260.SERVICE_DELETED_COLUMN)).andReturn(true).anyTimes();
        org.easymock.Capture<java.lang.String[]> scdcaptureKey = org.easymock.EasyMock.newCapture();
        org.easymock.Capture<java.lang.String[]> scdcaptureValue = org.easymock.EasyMock.newCapture();
        expectGetCurrentVersionID(current, scdcaptureKey, scdcaptureValue);
        org.easymock.Capture<org.apache.ambari.server.orm.DBAccessor.DBColumnInfo> scdstadd1 = org.easymock.EasyMock.newCapture();
        org.easymock.Capture<org.apache.ambari.server.orm.DBAccessor.DBColumnInfo> scdstalter1 = org.easymock.EasyMock.newCapture();
        org.easymock.Capture<org.apache.ambari.server.orm.DBAccessor.DBColumnInfo> scdstadd2 = org.easymock.EasyMock.newCapture();
        org.easymock.Capture<org.apache.ambari.server.orm.DBAccessor.DBColumnInfo> scdstalter2 = org.easymock.EasyMock.newCapture();
        expectUpdateServiceComponentDesiredStateTable(scdstadd1, scdstalter1, scdstadd2, scdstalter2);
        org.easymock.Capture<org.apache.ambari.server.orm.DBAccessor.DBColumnInfo> sdstadd = org.easymock.EasyMock.newCapture();
        org.easymock.Capture<org.apache.ambari.server.orm.DBAccessor.DBColumnInfo> sdstalter = org.easymock.EasyMock.newCapture();
        expectUpdateServiceDesiredStateTable(sdstadd, sdstalter);
        org.easymock.Capture<org.apache.ambari.server.orm.DBAccessor.DBColumnInfo> selectedColumnInfo = org.easymock.EasyMock.newCapture();
        org.easymock.Capture<org.apache.ambari.server.orm.DBAccessor.DBColumnInfo> selectedmappingColumnInfo = org.easymock.EasyMock.newCapture();
        org.easymock.Capture<org.apache.ambari.server.orm.DBAccessor.DBColumnInfo> selectedTimestampColumnInfo = org.easymock.EasyMock.newCapture();
        org.easymock.Capture<org.apache.ambari.server.orm.DBAccessor.DBColumnInfo> createTimestampColumnInfo = org.easymock.EasyMock.newCapture();
        expectAddSelectedCollumsToClusterconfigTable(selectedColumnInfo, selectedmappingColumnInfo, selectedTimestampColumnInfo, createTimestampColumnInfo);
        expectUpdateHostComponentDesiredStateTable();
        expectUpdateHostComponentStateTable();
        org.easymock.Capture<org.apache.ambari.server.orm.DBAccessor.DBColumnInfo> rvid = org.easymock.EasyMock.newCapture();
        org.easymock.Capture<org.apache.ambari.server.orm.DBAccessor.DBColumnInfo> orchestration = org.easymock.EasyMock.newCapture();
        org.easymock.Capture<org.apache.ambari.server.orm.DBAccessor.DBColumnInfo> revertAllowed = org.easymock.EasyMock.newCapture();
        expectUpdateUpgradeTable(rvid, orchestration, revertAllowed);
        org.easymock.Capture<java.util.List<org.apache.ambari.server.orm.DBAccessor.DBColumnInfo>> columns = org.easymock.EasyMock.newCapture();
        expectCreateUpgradeHistoryTable(columns);
        expectDropStaleTables();
        org.easymock.Capture<org.apache.ambari.server.orm.DBAccessor.DBColumnInfo> repoVersionHiddenColumnCapture = org.easymock.EasyMock.newCapture();
        org.easymock.Capture<org.apache.ambari.server.orm.DBAccessor.DBColumnInfo> repoVersionResolvedColumnCapture = org.easymock.EasyMock.newCapture();
        expectUpdateRepositoryVersionTableTable(repoVersionHiddenColumnCapture, repoVersionResolvedColumnCapture);
        org.easymock.Capture<org.apache.ambari.server.orm.DBAccessor.DBColumnInfo> unapped = org.easymock.EasyMock.newCapture();
        expectRenameServiceDeletedColumn(unapped);
        expectAddViewUrlPKConstraint();
        expectRemoveStaleConstraints();
        org.easymock.EasyMock.replay(dbAccessor, configuration, connection, statement, resultSet);
        com.google.inject.Injector injector = getInjector();
        org.apache.ambari.server.upgrade.UpgradeCatalog260 upgradeCatalog260 = injector.getInstance(org.apache.ambari.server.upgrade.UpgradeCatalog260.class);
        upgradeCatalog260.executeDDLUpdates();
        org.easymock.EasyMock.verify(dbAccessor);
        verifyGetCurrentVersionID(scdcaptureKey, scdcaptureValue);
        verifyUpdateServiceComponentDesiredStateTable(scdstadd1, scdstalter1, scdstadd2, scdstalter2);
        verifyUpdateServiceDesiredStateTable(sdstadd, sdstalter);
        verifyAddSelectedCollumsToClusterconfigTable(selectedColumnInfo, selectedmappingColumnInfo, selectedTimestampColumnInfo, createTimestampColumnInfo);
        verifyUpdateUpgradeTable(rvid, orchestration, revertAllowed);
        verifyCreateUpgradeHistoryTable(columns);
        verifyUpdateRepositoryVersionTableTable(repoVersionHiddenColumnCapture, repoVersionResolvedColumnCapture);
    }

    private void expectRemoveStaleConstraints() throws java.sql.SQLException {
        dbAccessor.dropUniqueConstraint(org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.USERS_TABLE), org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.STALE_POSTGRESS_USERS_LDAP_USER_KEY));
    }

    private void expectAddViewUrlPKConstraint() throws java.sql.SQLException {
        dbAccessor.dropPKConstraint(org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.VIEWURL_TABLE), org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.STALE_POSTGRESS_VIEWURL_PKEY));
        org.easymock.EasyMock.expectLastCall().once();
        dbAccessor.addPKConstraint(org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.VIEWURL_TABLE), org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.PK_VIEWURL), org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.URL_ID_COLUMN));
        org.easymock.EasyMock.expectLastCall().once();
    }

    public void expectDropStaleTables() throws java.sql.SQLException {
        dbAccessor.dropTable(org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.CLUSTER_CONFIG_MAPPING_TABLE));
        org.easymock.EasyMock.expectLastCall().once();
        dbAccessor.dropTable(org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.CLUSTER_VERSION_TABLE));
        org.easymock.EasyMock.expectLastCall().once();
        dbAccessor.dropTable(org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.SERVICE_COMPONENT_HISTORY_TABLE));
        org.easymock.EasyMock.expectLastCall().once();
    }

    public void expectRenameServiceDeletedColumn(org.easymock.Capture<org.apache.ambari.server.orm.DBAccessor.DBColumnInfo> unmapped) throws java.sql.SQLException {
        dbAccessor.renameColumn(org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.CLUSTER_CONFIG_TABLE), org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.SERVICE_DELETED_COLUMN), org.easymock.EasyMock.capture(unmapped));
        org.easymock.EasyMock.expectLastCall().once();
    }

    public void verifyCreateUpgradeHistoryTable(org.easymock.Capture<java.util.List<org.apache.ambari.server.orm.DBAccessor.DBColumnInfo>> columns) {
        java.util.List<org.apache.ambari.server.orm.DBAccessor.DBColumnInfo> columnsValue = columns.getValue();
        org.junit.Assert.assertEquals(columnsValue.size(), 6);
        org.apache.ambari.server.orm.DBAccessor.DBColumnInfo id = columnsValue.get(0);
        org.junit.Assert.assertEquals(org.apache.ambari.server.upgrade.UpgradeCatalog260.ID_COLUMN, id.getName());
        org.junit.Assert.assertEquals(java.lang.Long.class, id.getType());
        org.junit.Assert.assertEquals(null, id.getLength());
        org.junit.Assert.assertEquals(null, id.getDefaultValue());
        org.junit.Assert.assertEquals(false, id.isNullable());
        org.apache.ambari.server.orm.DBAccessor.DBColumnInfo upgradeId = columnsValue.get(1);
        org.junit.Assert.assertEquals(org.apache.ambari.server.upgrade.UpgradeCatalog260.UPGRADE_ID_COLUMN, upgradeId.getName());
        org.junit.Assert.assertEquals(java.lang.Long.class, upgradeId.getType());
        org.junit.Assert.assertEquals(null, upgradeId.getLength());
        org.junit.Assert.assertEquals(null, upgradeId.getDefaultValue());
        org.junit.Assert.assertEquals(false, upgradeId.isNullable());
        org.apache.ambari.server.orm.DBAccessor.DBColumnInfo serviceName = columnsValue.get(2);
        org.junit.Assert.assertEquals(org.apache.ambari.server.upgrade.UpgradeCatalog260.SERVICE_NAME_COLUMN, serviceName.getName());
        org.junit.Assert.assertEquals(java.lang.String.class, serviceName.getType());
        org.junit.Assert.assertEquals(java.lang.Integer.valueOf(255), serviceName.getLength());
        org.junit.Assert.assertEquals(null, serviceName.getDefaultValue());
        org.junit.Assert.assertEquals(false, serviceName.isNullable());
        org.apache.ambari.server.orm.DBAccessor.DBColumnInfo componentName = columnsValue.get(3);
        org.junit.Assert.assertEquals(org.apache.ambari.server.upgrade.UpgradeCatalog260.COMPONENT_NAME_COLUMN, componentName.getName());
        org.junit.Assert.assertEquals(java.lang.String.class, componentName.getType());
        org.junit.Assert.assertEquals(java.lang.Integer.valueOf(255), componentName.getLength());
        org.junit.Assert.assertEquals(null, componentName.getDefaultValue());
        org.junit.Assert.assertEquals(false, componentName.isNullable());
        org.apache.ambari.server.orm.DBAccessor.DBColumnInfo fromRepoID = columnsValue.get(4);
        org.junit.Assert.assertEquals(org.apache.ambari.server.upgrade.UpgradeCatalog260.FROM_REPO_VERSION_ID_COLUMN, fromRepoID.getName());
        org.junit.Assert.assertEquals(java.lang.Long.class, fromRepoID.getType());
        org.junit.Assert.assertEquals(null, fromRepoID.getLength());
        org.junit.Assert.assertEquals(null, fromRepoID.getDefaultValue());
        org.junit.Assert.assertEquals(false, fromRepoID.isNullable());
        org.apache.ambari.server.orm.DBAccessor.DBColumnInfo targetRepoID = columnsValue.get(5);
        org.junit.Assert.assertEquals(org.apache.ambari.server.upgrade.UpgradeCatalog260.TARGET_REPO_VERSION_ID_COLUMN, targetRepoID.getName());
        org.junit.Assert.assertEquals(java.lang.Long.class, targetRepoID.getType());
        org.junit.Assert.assertEquals(null, targetRepoID.getLength());
        org.junit.Assert.assertEquals(null, targetRepoID.getDefaultValue());
        org.junit.Assert.assertEquals(false, targetRepoID.isNullable());
    }

    public void expectCreateUpgradeHistoryTable(org.easymock.Capture<java.util.List<org.apache.ambari.server.orm.DBAccessor.DBColumnInfo>> columns) throws java.sql.SQLException {
        dbAccessor.createTable(org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.UPGRADE_HISTORY_TABLE), org.easymock.EasyMock.capture(columns));
        org.easymock.EasyMock.expectLastCall().once();
        dbAccessor.addPKConstraint(org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.UPGRADE_HISTORY_TABLE), org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.PK_UPGRADE_HIST), org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.ID_COLUMN));
        org.easymock.EasyMock.expectLastCall().once();
        dbAccessor.addFKConstraint(org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.UPGRADE_HISTORY_TABLE), org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.FK_UPGRADE_HIST_UPGRADE_ID), org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.UPGRADE_ID_COLUMN), org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.UPGRADE_TABLE), org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.UPGRADE_ID_COLUMN), org.easymock.EasyMock.eq(false));
        org.easymock.EasyMock.expectLastCall().once();
        dbAccessor.addFKConstraint(org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.UPGRADE_HISTORY_TABLE), org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.FK_UPGRADE_HIST_FROM_REPO), org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.FROM_REPO_VERSION_ID_COLUMN), org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.REPO_VERSION_TABLE), org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.REPO_VERSION_ID_COLUMN), org.easymock.EasyMock.eq(false));
        org.easymock.EasyMock.expectLastCall().once();
        dbAccessor.addFKConstraint(org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.UPGRADE_HISTORY_TABLE), org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.FK_UPGRADE_HIST_TARGET_REPO), org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.TARGET_REPO_VERSION_ID_COLUMN), org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.REPO_VERSION_TABLE), org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.REPO_VERSION_ID_COLUMN), org.easymock.EasyMock.eq(false));
        org.easymock.EasyMock.expectLastCall().once();
        dbAccessor.addUniqueConstraint(org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.UPGRADE_HISTORY_TABLE), org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.UQ_UPGRADE_HIST), org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.UPGRADE_ID_COLUMN), org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.COMPONENT_NAME_COLUMN), org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.SERVICE_NAME_COLUMN));
        org.easymock.EasyMock.expectLastCall().once();
    }

    public void verifyUpdateUpgradeTable(org.easymock.Capture<org.apache.ambari.server.orm.DBAccessor.DBColumnInfo> rvid, org.easymock.Capture<org.apache.ambari.server.orm.DBAccessor.DBColumnInfo> orchestration, org.easymock.Capture<org.apache.ambari.server.orm.DBAccessor.DBColumnInfo> revertAllowed) {
        org.apache.ambari.server.orm.DBAccessor.DBColumnInfo rvidValue = rvid.getValue();
        org.junit.Assert.assertEquals(org.apache.ambari.server.upgrade.UpgradeCatalog260.REPO_VERSION_ID_COLUMN, rvidValue.getName());
        org.junit.Assert.assertEquals(java.lang.Long.class, rvidValue.getType());
        org.junit.Assert.assertEquals(null, rvidValue.getLength());
        org.junit.Assert.assertEquals(null, rvidValue.getDefaultValue());
        org.junit.Assert.assertEquals(false, rvidValue.isNullable());
        org.apache.ambari.server.orm.DBAccessor.DBColumnInfo orchestrationValue = orchestration.getValue();
        org.junit.Assert.assertEquals(org.apache.ambari.server.upgrade.UpgradeCatalog260.ORCHESTRATION_COLUMN, orchestrationValue.getName());
        org.junit.Assert.assertEquals(java.lang.String.class, orchestrationValue.getType());
        org.junit.Assert.assertEquals(java.lang.Integer.valueOf(255), orchestrationValue.getLength());
        org.junit.Assert.assertEquals(org.apache.ambari.server.upgrade.UpgradeCatalog260.STANDARD, orchestrationValue.getDefaultValue());
        org.junit.Assert.assertEquals(false, orchestrationValue.isNullable());
        org.apache.ambari.server.orm.DBAccessor.DBColumnInfo revertAllowedValue = revertAllowed.getValue();
        org.junit.Assert.assertEquals(org.apache.ambari.server.upgrade.UpgradeCatalog260.ALLOW_REVERT_COLUMN, revertAllowedValue.getName());
        org.junit.Assert.assertEquals(java.lang.Short.class, revertAllowedValue.getType());
        org.junit.Assert.assertEquals(null, revertAllowedValue.getLength());
        org.junit.Assert.assertEquals(0, revertAllowedValue.getDefaultValue());
        org.junit.Assert.assertEquals(false, revertAllowedValue.isNullable());
    }

    public void expectUpdateUpgradeTable(org.easymock.Capture<org.apache.ambari.server.orm.DBAccessor.DBColumnInfo> rvid, org.easymock.Capture<org.apache.ambari.server.orm.DBAccessor.DBColumnInfo> orchestration, org.easymock.Capture<org.apache.ambari.server.orm.DBAccessor.DBColumnInfo> revertAllowed) throws java.sql.SQLException {
        dbAccessor.clearTableColumn(org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.CLUSTERS_TABLE), org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.UPGRADE_ID_COLUMN), org.easymock.EasyMock.eq(null));
        org.easymock.EasyMock.expectLastCall().once();
        dbAccessor.clearTable(org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.UPGRADE_TABLE));
        org.easymock.EasyMock.expectLastCall().once();
        dbAccessor.dropFKConstraint(org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.UPGRADE_TABLE), org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.FK_UPGRADE_FROM_REPO_ID));
        org.easymock.EasyMock.expectLastCall().once();
        dbAccessor.dropFKConstraint(org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.UPGRADE_TABLE), org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.FK_UPGRADE_TO_REPO_ID));
        org.easymock.EasyMock.expectLastCall().once();
        dbAccessor.dropColumn(org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.UPGRADE_TABLE), org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.FROM_REPO_VERSION_ID_COLUMN));
        org.easymock.EasyMock.expectLastCall().once();
        dbAccessor.dropColumn(org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.UPGRADE_TABLE), org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.TO_REPO_VERSION_ID_COLUMN));
        org.easymock.EasyMock.expectLastCall().once();
        dbAccessor.addColumn(org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.UPGRADE_TABLE), org.easymock.EasyMock.capture(rvid));
        org.easymock.EasyMock.expectLastCall().once();
        dbAccessor.addColumn(org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.UPGRADE_TABLE), org.easymock.EasyMock.capture(orchestration));
        org.easymock.EasyMock.expectLastCall().once();
        dbAccessor.addColumn(org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.UPGRADE_TABLE), org.easymock.EasyMock.capture(revertAllowed));
        org.easymock.EasyMock.expectLastCall().once();
        dbAccessor.addFKConstraint(org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.UPGRADE_TABLE), org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.FK_UPGRADE_REPO_VERSION_ID), org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.REPO_VERSION_ID_COLUMN), org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.REPO_VERSION_TABLE), org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.REPO_VERSION_ID_COLUMN), org.easymock.EasyMock.eq(false));
        org.easymock.EasyMock.expectLastCall().once();
    }

    public void expectUpdateHostComponentStateTable() throws java.sql.SQLException {
        dbAccessor.dropFKConstraint(org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.HOST_COMPONENT_STATE_TABLE), org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.FK_HCS_CURRENT_STACK_ID));
        org.easymock.EasyMock.expectLastCall().once();
        dbAccessor.dropColumn(org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.HOST_COMPONENT_STATE_TABLE), org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.CURRENT_STACK_ID_COLUMN));
        org.easymock.EasyMock.expectLastCall().once();
    }

    public void expectUpdateHostComponentDesiredStateTable() throws java.sql.SQLException {
        dbAccessor.dropFKConstraint(org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.HOST_COMPONENT_DESIRED_STATE_TABLE), org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.FK_HCDS_DESIRED_STACK_ID));
        org.easymock.EasyMock.expectLastCall().once();
        dbAccessor.dropColumn(org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.HOST_COMPONENT_DESIRED_STATE_TABLE), org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.DESIRED_STACK_ID_COLUMN));
        org.easymock.EasyMock.expectLastCall().once();
    }

    public void verifyAddSelectedCollumsToClusterconfigTable(org.easymock.Capture<org.apache.ambari.server.orm.DBAccessor.DBColumnInfo> selectedColumnInfo, org.easymock.Capture<org.apache.ambari.server.orm.DBAccessor.DBColumnInfo> selectedmappingColumnInfo, org.easymock.Capture<org.apache.ambari.server.orm.DBAccessor.DBColumnInfo> selectedTimestampColumnInfo, org.easymock.Capture<org.apache.ambari.server.orm.DBAccessor.DBColumnInfo> createTimestampColumnInfo) {
        org.apache.ambari.server.orm.DBAccessor.DBColumnInfo selectedColumnInfoValue = selectedColumnInfo.getValue();
        org.junit.Assert.assertEquals(org.apache.ambari.server.upgrade.UpgradeCatalog260.SELECTED_COLUMN, selectedColumnInfoValue.getName());
        org.junit.Assert.assertEquals(java.lang.Short.class, selectedColumnInfoValue.getType());
        org.junit.Assert.assertEquals(null, selectedColumnInfoValue.getLength());
        org.junit.Assert.assertEquals(0, selectedColumnInfoValue.getDefaultValue());
        org.junit.Assert.assertEquals(false, selectedColumnInfoValue.isNullable());
        org.apache.ambari.server.orm.DBAccessor.DBColumnInfo selectedmappingColumnInfoValue = selectedmappingColumnInfo.getValue();
        org.junit.Assert.assertEquals(org.apache.ambari.server.upgrade.UpgradeCatalog260.SELECTED_COLUMN, selectedmappingColumnInfoValue.getName());
        org.junit.Assert.assertEquals(java.lang.Integer.class, selectedmappingColumnInfoValue.getType());
        org.junit.Assert.assertEquals(null, selectedmappingColumnInfoValue.getLength());
        org.junit.Assert.assertEquals(0, selectedmappingColumnInfoValue.getDefaultValue());
        org.junit.Assert.assertEquals(false, selectedmappingColumnInfoValue.isNullable());
        org.apache.ambari.server.orm.DBAccessor.DBColumnInfo selectedTimestampColumnInfoValue = selectedTimestampColumnInfo.getValue();
        org.junit.Assert.assertEquals(org.apache.ambari.server.upgrade.UpgradeCatalog260.SELECTED_TIMESTAMP_COLUMN, selectedTimestampColumnInfoValue.getName());
        org.junit.Assert.assertEquals(java.lang.Long.class, selectedTimestampColumnInfoValue.getType());
        org.junit.Assert.assertEquals(null, selectedTimestampColumnInfoValue.getLength());
        org.junit.Assert.assertEquals(0, selectedTimestampColumnInfoValue.getDefaultValue());
        org.junit.Assert.assertEquals(false, selectedTimestampColumnInfoValue.isNullable());
        org.apache.ambari.server.orm.DBAccessor.DBColumnInfo createTimestampColumnInfoValue = createTimestampColumnInfo.getValue();
        org.junit.Assert.assertEquals(org.apache.ambari.server.upgrade.UpgradeCatalog260.CREATE_TIMESTAMP_COLUMN, createTimestampColumnInfoValue.getName());
        org.junit.Assert.assertEquals(java.lang.Long.class, createTimestampColumnInfoValue.getType());
        org.junit.Assert.assertEquals(null, createTimestampColumnInfoValue.getLength());
        org.junit.Assert.assertEquals(null, createTimestampColumnInfoValue.getDefaultValue());
        org.junit.Assert.assertEquals(false, createTimestampColumnInfoValue.isNullable());
    }

    public void expectAddSelectedCollumsToClusterconfigTable(org.easymock.Capture<org.apache.ambari.server.orm.DBAccessor.DBColumnInfo> selectedColumnInfo, org.easymock.Capture<org.apache.ambari.server.orm.DBAccessor.DBColumnInfo> selectedmappingColumnInfo, org.easymock.Capture<org.apache.ambari.server.orm.DBAccessor.DBColumnInfo> selectedTimestampColumnInfo, org.easymock.Capture<org.apache.ambari.server.orm.DBAccessor.DBColumnInfo> createTimestampColumnInfo) throws java.sql.SQLException {
        dbAccessor.copyColumnToAnotherTable(org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.CLUSTER_CONFIG_MAPPING_TABLE), org.easymock.EasyMock.capture(selectedmappingColumnInfo), org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.CLUSTER_ID_COLUMN), org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.TYPE_NAME_COLUMN), org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.VERSION_TAG_COLUMN), org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.CLUSTER_CONFIG_TABLE), org.easymock.EasyMock.capture(selectedColumnInfo), org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.CLUSTER_ID_COLUMN), org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.TYPE_NAME_COLUMN), org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.VERSION_TAG_COLUMN), org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.SELECTED_COLUMN), org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.SELECTED), org.easymock.EasyMock.eq(0));
        org.easymock.EasyMock.expectLastCall().once();
        dbAccessor.copyColumnToAnotherTable(org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.CLUSTER_CONFIG_MAPPING_TABLE), org.easymock.EasyMock.capture(createTimestampColumnInfo), org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.CLUSTER_ID_COLUMN), org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.TYPE_NAME_COLUMN), org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.VERSION_TAG_COLUMN), org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.CLUSTER_CONFIG_TABLE), org.easymock.EasyMock.capture(selectedTimestampColumnInfo), org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.CLUSTER_ID_COLUMN), org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.TYPE_NAME_COLUMN), org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.VERSION_TAG_COLUMN), org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.SELECTED_COLUMN), org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.SELECTED), org.easymock.EasyMock.eq(0));
        org.easymock.EasyMock.expectLastCall().once();
    }

    public void verifyUpdateServiceDesiredStateTable(org.easymock.Capture<org.apache.ambari.server.orm.DBAccessor.DBColumnInfo> sdstadd, org.easymock.Capture<org.apache.ambari.server.orm.DBAccessor.DBColumnInfo> sdstalter) {
        org.apache.ambari.server.orm.DBAccessor.DBColumnInfo sdstaddValue = sdstadd.getValue();
        org.junit.Assert.assertEquals(org.apache.ambari.server.upgrade.UpgradeCatalog260.DESIRED_REPO_VERSION_ID_COLUMN, sdstaddValue.getName());
        org.junit.Assert.assertEquals(1, sdstaddValue.getDefaultValue());
        org.junit.Assert.assertEquals(java.lang.Long.class, sdstaddValue.getType());
        org.junit.Assert.assertEquals(false, sdstaddValue.isNullable());
        org.junit.Assert.assertEquals(null, sdstaddValue.getLength());
        org.apache.ambari.server.orm.DBAccessor.DBColumnInfo sdstalterValue = sdstalter.getValue();
        org.junit.Assert.assertEquals(org.apache.ambari.server.upgrade.UpgradeCatalog260.DESIRED_REPO_VERSION_ID_COLUMN, sdstalterValue.getName());
        org.junit.Assert.assertEquals(null, sdstalterValue.getDefaultValue());
        org.junit.Assert.assertEquals(java.lang.Long.class, sdstalterValue.getType());
        org.junit.Assert.assertEquals(false, sdstalterValue.isNullable());
        org.junit.Assert.assertEquals(null, sdstalterValue.getLength());
    }

    public void expectUpdateServiceDesiredStateTable(org.easymock.Capture<org.apache.ambari.server.orm.DBAccessor.DBColumnInfo> sdstadd, org.easymock.Capture<org.apache.ambari.server.orm.DBAccessor.DBColumnInfo> sdstalter) throws java.sql.SQLException {
        dbAccessor.addColumn(org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.SERVICE_DESIRED_STATE_TABLE), org.easymock.EasyMock.capture(sdstadd));
        org.easymock.EasyMock.expectLastCall().once();
        dbAccessor.alterColumn(org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.SERVICE_DESIRED_STATE_TABLE), org.easymock.EasyMock.capture(sdstalter));
        org.easymock.EasyMock.expectLastCall().once();
        dbAccessor.addFKConstraint(org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.SERVICE_DESIRED_STATE_TABLE), org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.FK_REPO_VERSION_ID), org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.DESIRED_REPO_VERSION_ID_COLUMN), org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.REPO_VERSION_TABLE), org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.REPO_VERSION_ID_COLUMN), org.easymock.EasyMock.eq(false));
        org.easymock.EasyMock.expectLastCall().once();
        dbAccessor.dropFKConstraint(org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.SERVICE_DESIRED_STATE_TABLE), org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.FK_SDS_DESIRED_STACK_ID));
        org.easymock.EasyMock.expectLastCall().once();
        dbAccessor.dropColumn(org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.SERVICE_DESIRED_STATE_TABLE), org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.DESIRED_STACK_ID_COLUMN));
        org.easymock.EasyMock.expectLastCall().once();
    }

    public void verifyUpdateServiceComponentDesiredStateTable(org.easymock.Capture<org.apache.ambari.server.orm.DBAccessor.DBColumnInfo> scdstadd1, org.easymock.Capture<org.apache.ambari.server.orm.DBAccessor.DBColumnInfo> scdstalter1, org.easymock.Capture<org.apache.ambari.server.orm.DBAccessor.DBColumnInfo> scdstadd2, org.easymock.Capture<org.apache.ambari.server.orm.DBAccessor.DBColumnInfo> scdstalter2) {
        org.apache.ambari.server.orm.DBAccessor.DBColumnInfo scdstaddValue1 = scdstadd1.getValue();
        org.junit.Assert.assertEquals(org.apache.ambari.server.upgrade.UpgradeCatalog260.DESIRED_REPO_VERSION_ID_COLUMN, scdstaddValue1.getName());
        org.junit.Assert.assertEquals(1, scdstaddValue1.getDefaultValue());
        org.junit.Assert.assertEquals(java.lang.Long.class, scdstaddValue1.getType());
        org.junit.Assert.assertEquals(false, scdstaddValue1.isNullable());
        org.apache.ambari.server.orm.DBAccessor.DBColumnInfo scdstalterValue1 = scdstalter1.getValue();
        org.junit.Assert.assertEquals(org.apache.ambari.server.upgrade.UpgradeCatalog260.DESIRED_REPO_VERSION_ID_COLUMN, scdstalterValue1.getName());
        org.junit.Assert.assertEquals(null, scdstalterValue1.getDefaultValue());
        org.junit.Assert.assertEquals(java.lang.Long.class, scdstalterValue1.getType());
        org.junit.Assert.assertEquals(false, scdstalterValue1.isNullable());
        org.apache.ambari.server.orm.DBAccessor.DBColumnInfo scdstaddValue2 = scdstadd2.getValue();
        org.junit.Assert.assertEquals(org.apache.ambari.server.upgrade.UpgradeCatalog260.REPO_STATE_COLUMN, scdstaddValue2.getName());
        org.junit.Assert.assertEquals(org.apache.ambari.server.upgrade.UpgradeCatalog260.CURRENT, scdstaddValue2.getDefaultValue());
        org.junit.Assert.assertEquals(java.lang.String.class, scdstaddValue2.getType());
        org.junit.Assert.assertEquals(false, scdstaddValue2.isNullable());
        org.junit.Assert.assertEquals(java.lang.Integer.valueOf(255), scdstaddValue2.getLength());
        org.apache.ambari.server.orm.DBAccessor.DBColumnInfo scdstalterValue2 = scdstalter2.getValue();
        org.junit.Assert.assertEquals(org.apache.ambari.server.upgrade.UpgradeCatalog260.REPO_STATE_COLUMN, scdstalterValue2.getName());
        org.junit.Assert.assertEquals(org.apache.ambari.server.upgrade.UpgradeCatalog260.NOT_REQUIRED, scdstalterValue2.getDefaultValue());
        org.junit.Assert.assertEquals(java.lang.String.class, scdstalterValue2.getType());
        org.junit.Assert.assertEquals(false, scdstalterValue2.isNullable());
        org.junit.Assert.assertEquals(java.lang.Integer.valueOf(255), scdstaddValue2.getLength());
    }

    public void verifyGetCurrentVersionID(org.easymock.Capture<java.lang.String[]> scdcaptureKey, org.easymock.Capture<java.lang.String[]> scdcaptureValue) {
        org.junit.Assert.assertTrue(java.util.Arrays.equals(scdcaptureKey.getValue(), new java.lang.String[]{ org.apache.ambari.server.upgrade.UpgradeCatalog260.STATE_COLUMN }));
        org.junit.Assert.assertTrue(java.util.Arrays.equals(scdcaptureValue.getValue(), new java.lang.String[]{ org.apache.ambari.server.upgrade.UpgradeCatalog260.CURRENT }));
    }

    public void expectUpdateServiceComponentDesiredStateTable(org.easymock.Capture<org.apache.ambari.server.orm.DBAccessor.DBColumnInfo> scdstadd1, org.easymock.Capture<org.apache.ambari.server.orm.DBAccessor.DBColumnInfo> scdstalter1, org.easymock.Capture<org.apache.ambari.server.orm.DBAccessor.DBColumnInfo> scdstadd2, org.easymock.Capture<org.apache.ambari.server.orm.DBAccessor.DBColumnInfo> scdstalter2) throws java.sql.SQLException {
        dbAccessor.addColumn(org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.SERVICE_COMPONENT_DESIRED_STATE_TABLE), org.easymock.EasyMock.capture(scdstadd1));
        org.easymock.EasyMock.expectLastCall().once();
        dbAccessor.alterColumn(org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.SERVICE_COMPONENT_DESIRED_STATE_TABLE), org.easymock.EasyMock.capture(scdstalter1));
        org.easymock.EasyMock.expectLastCall().once();
        dbAccessor.addColumn(org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.SERVICE_COMPONENT_DESIRED_STATE_TABLE), org.easymock.EasyMock.capture(scdstadd2));
        org.easymock.EasyMock.expectLastCall().once();
        dbAccessor.alterColumn(org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.SERVICE_COMPONENT_DESIRED_STATE_TABLE), org.easymock.EasyMock.capture(scdstalter2));
        org.easymock.EasyMock.expectLastCall().once();
        dbAccessor.addFKConstraint(org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.SERVICE_COMPONENT_DESIRED_STATE_TABLE), org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.FK_SCDS_DESIRED_REPO_ID), org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.DESIRED_REPO_VERSION_ID_COLUMN), org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.REPO_VERSION_TABLE), org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.REPO_VERSION_ID_COLUMN), org.easymock.EasyMock.eq(false));
        org.easymock.EasyMock.expectLastCall().once();
        dbAccessor.dropFKConstraint(org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.SERVICE_COMPONENT_DESIRED_STATE_TABLE), org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.FK_SCDS_DESIRED_STACK_ID));
        org.easymock.EasyMock.expectLastCall().once();
        dbAccessor.dropColumn(org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.SERVICE_COMPONENT_DESIRED_STATE_TABLE), org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.DESIRED_STACK_ID_COLUMN));
        org.easymock.EasyMock.expectLastCall().once();
        dbAccessor.dropColumn(org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.SERVICE_COMPONENT_DESIRED_STATE_TABLE), org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.DESIRED_VERSION_COLUMN));
        org.easymock.EasyMock.expectLastCall().once();
    }

    public void expectGetCurrentVersionID(java.util.List<java.lang.Integer> current, org.easymock.Capture<java.lang.String[]> scdcaptureKey, org.easymock.Capture<java.lang.String[]> scdcaptureValue) throws java.sql.SQLException {
        org.easymock.EasyMock.expect(dbAccessor.tableExists(org.easymock.EasyMock.eq("cluster_version"))).andReturn(true).once();
        org.easymock.EasyMock.expect(dbAccessor.getIntColumnValues(org.easymock.EasyMock.eq("cluster_version"), org.easymock.EasyMock.eq("repo_version_id"), org.easymock.EasyMock.capture(scdcaptureKey), org.easymock.EasyMock.capture(scdcaptureValue), org.easymock.EasyMock.eq(false))).andReturn(current).once();
    }

    @org.junit.Test
    public void testRemoveDruidSuperset() throws java.lang.Exception {
        java.util.List<java.lang.Integer> current = new java.util.ArrayList<>();
        current.add(1);
        org.easymock.EasyMock.expect(dbAccessor.getConnection()).andReturn(connection).anyTimes();
        org.easymock.EasyMock.expect(connection.createStatement()).andReturn(statement).anyTimes();
        org.easymock.EasyMock.expect(statement.executeQuery(org.easymock.EasyMock.anyObject(java.lang.String.class))).andReturn(resultSet).anyTimes();
        org.easymock.EasyMock.expect(configuration.getDatabaseType()).andReturn(org.apache.ambari.server.configuration.Configuration.DatabaseType.POSTGRES).anyTimes();
        dbAccessor.executeQuery("DELETE FROM serviceconfigmapping WHERE config_id IN (SELECT config_id from clusterconfig where type_name like 'druid-superset%')");
        org.easymock.EasyMock.expectLastCall().once();
        dbAccessor.executeQuery("DELETE FROM clusterconfig WHERE type_name like 'druid-superset%'");
        org.easymock.EasyMock.expectLastCall().once();
        dbAccessor.executeQuery("DELETE FROM hostcomponentdesiredstate WHERE component_name = 'DRUID_SUPERSET'");
        org.easymock.EasyMock.expectLastCall().once();
        dbAccessor.executeQuery("DELETE FROM hostcomponentstate WHERE component_name = 'DRUID_SUPERSET'");
        org.easymock.EasyMock.expectLastCall().once();
        dbAccessor.executeQuery("DELETE FROM servicecomponentdesiredstate WHERE component_name = 'DRUID_SUPERSET'");
        org.easymock.EasyMock.expectLastCall().once();
        org.easymock.EasyMock.replay(dbAccessor, configuration, connection, statement, resultSet);
        com.google.inject.Injector injector = getInjector();
        org.apache.ambari.server.upgrade.UpgradeCatalog260 upgradeCatalog260 = injector.getInstance(org.apache.ambari.server.upgrade.UpgradeCatalog260.class);
        upgradeCatalog260.executePreDMLUpdates();
        org.easymock.EasyMock.verify(dbAccessor);
    }

    public void expectUpdateRepositoryVersionTableTable(org.easymock.Capture<org.apache.ambari.server.orm.DBAccessor.DBColumnInfo> hiddenColumnCapture, org.easymock.Capture<org.apache.ambari.server.orm.DBAccessor.DBColumnInfo> repoVersionResolvedColumnCapture) throws java.sql.SQLException {
        dbAccessor.addColumn(org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.REPO_VERSION_TABLE), org.easymock.EasyMock.capture(hiddenColumnCapture));
        dbAccessor.addColumn(org.easymock.EasyMock.eq(org.apache.ambari.server.upgrade.UpgradeCatalog260.REPO_VERSION_TABLE), org.easymock.EasyMock.capture(repoVersionResolvedColumnCapture));
        org.easymock.EasyMock.expectLastCall().once();
    }

    public void verifyUpdateRepositoryVersionTableTable(org.easymock.Capture<org.apache.ambari.server.orm.DBAccessor.DBColumnInfo> hiddenColumnCapture, org.easymock.Capture<org.apache.ambari.server.orm.DBAccessor.DBColumnInfo> resolvedColumnCapture) {
        org.apache.ambari.server.orm.DBAccessor.DBColumnInfo hiddenColumn = hiddenColumnCapture.getValue();
        org.junit.Assert.assertEquals(0, hiddenColumn.getDefaultValue());
        org.junit.Assert.assertEquals(org.apache.ambari.server.upgrade.UpgradeCatalog260.REPO_VERSION_HIDDEN_COLUMN, hiddenColumn.getName());
        org.junit.Assert.assertEquals(false, hiddenColumn.isNullable());
        org.apache.ambari.server.orm.DBAccessor.DBColumnInfo resolvedColumn = resolvedColumnCapture.getValue();
        org.junit.Assert.assertEquals(0, resolvedColumn.getDefaultValue());
        org.junit.Assert.assertEquals(org.apache.ambari.server.upgrade.UpgradeCatalog260.REPO_VERSION_RESOLVED_COLUMN, resolvedColumn.getName());
        org.junit.Assert.assertEquals(false, resolvedColumn.isNullable());
    }

    @org.junit.Test
    public void testEnsureZeppelinProxyUserConfigs() throws org.apache.ambari.server.AmbariException {
        com.google.inject.Injector injector = getInjector();
        final org.apache.ambari.server.state.Clusters clusters = injector.getInstance(org.apache.ambari.server.state.Clusters.class);
        final org.apache.ambari.server.state.ConfigHelper configHelper = injector.getInstance(org.apache.ambari.server.state.ConfigHelper.class);
        final org.apache.ambari.server.state.Cluster cluster = org.easymock.EasyMock.createMock(org.apache.ambari.server.state.Cluster.class);
        final org.apache.ambari.server.state.Config zeppelinEnvConf = org.easymock.EasyMock.createMock(org.apache.ambari.server.state.Config.class);
        final org.apache.ambari.server.state.Config coreSiteConf = org.easymock.EasyMock.createMock(org.apache.ambari.server.state.Config.class);
        final org.apache.ambari.server.state.Config coreSiteConfNew = org.easymock.EasyMock.createMock(org.apache.ambari.server.state.Config.class);
        final org.apache.ambari.server.controller.AmbariManagementController controller = injector.getInstance(org.apache.ambari.server.controller.AmbariManagementController.class);
        org.easymock.Capture<? extends java.util.Map<java.lang.String, java.lang.String>> captureCoreSiteConfProperties = org.easymock.EasyMock.newCapture();
        configHelper.updateAgentConfigs(org.easymock.EasyMock.anyObject(java.util.Set.class));
        org.easymock.EasyMock.expectLastCall();
        org.easymock.EasyMock.expect(clusters.getClusters()).andReturn(java.util.Collections.singletonMap("c1", cluster)).once();
        org.easymock.EasyMock.expect(cluster.getClusterName()).andReturn("c1").atLeastOnce();
        org.easymock.EasyMock.expect(cluster.getDesiredStackVersion()).andReturn(new org.apache.ambari.server.state.StackId("HDP-2.6")).atLeastOnce();
        org.easymock.EasyMock.expect(cluster.getDesiredConfigByType("zeppelin-env")).andReturn(zeppelinEnvConf).atLeastOnce();
        org.easymock.EasyMock.expect(cluster.getDesiredConfigByType("core-site")).andReturn(coreSiteConf).atLeastOnce();
        org.easymock.EasyMock.expect(cluster.getConfigsByType("core-site")).andReturn(java.util.Collections.singletonMap("tag1", coreSiteConf)).atLeastOnce();
        org.easymock.EasyMock.expect(cluster.getConfig(org.easymock.EasyMock.eq("core-site"), org.easymock.EasyMock.anyString())).andReturn(coreSiteConfNew).atLeastOnce();
        org.easymock.EasyMock.expect(cluster.getServiceByConfigType("core-site")).andReturn("HDFS").atLeastOnce();
        org.easymock.EasyMock.expect(cluster.addDesiredConfig(org.easymock.EasyMock.eq("ambari-upgrade"), org.easymock.EasyMock.anyObject(java.util.Set.class), org.easymock.EasyMock.anyString())).andReturn(null).atLeastOnce();
        org.easymock.EasyMock.expect(zeppelinEnvConf.getProperties()).andReturn(java.util.Collections.singletonMap("zeppelin_user", "zeppelin_user")).once();
        org.easymock.EasyMock.expect(coreSiteConf.getProperties()).andReturn(java.util.Collections.singletonMap("hadoop.proxyuser.zeppelin_user.hosts", "existing_value")).atLeastOnce();
        org.easymock.EasyMock.expect(coreSiteConf.getPropertiesAttributes()).andReturn(java.util.Collections.<java.lang.String, java.util.Map<java.lang.String, java.lang.String>>emptyMap()).atLeastOnce();
        org.easymock.EasyMock.expect(controller.createConfig(org.easymock.EasyMock.eq(cluster), org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.StackId.class), org.easymock.EasyMock.eq("core-site"), org.easymock.EasyMock.capture(captureCoreSiteConfProperties), org.easymock.EasyMock.anyString(), org.easymock.EasyMock.anyObject(java.util.Map.class))).andReturn(coreSiteConfNew).once();
        org.easymock.EasyMock.replay(clusters, cluster, zeppelinEnvConf, coreSiteConf, coreSiteConfNew, controller, configHelper);
        org.apache.ambari.server.upgrade.UpgradeCatalog260 upgradeCatalog260 = injector.getInstance(org.apache.ambari.server.upgrade.UpgradeCatalog260.class);
        upgradeCatalog260.ensureZeppelinProxyUserConfigs();
        org.easymock.EasyMock.verify(clusters, cluster, zeppelinEnvConf, coreSiteConf, coreSiteConfNew, controller, configHelper);
        org.junit.Assert.assertTrue(captureCoreSiteConfProperties.hasCaptured());
        org.junit.Assert.assertEquals("existing_value", captureCoreSiteConfProperties.getValue().get("hadoop.proxyuser.zeppelin_user.hosts"));
        org.junit.Assert.assertEquals("*", captureCoreSiteConfProperties.getValue().get("hadoop.proxyuser.zeppelin_user.groups"));
    }

    @org.junit.Test
    public void testUpdateKerberosDescriptorArtifact() throws java.lang.Exception {
        com.google.inject.Injector injector = getInjector();
        java.net.URL systemResourceURL = java.lang.ClassLoader.getSystemResource("kerberos/test_kerberos_descriptor_ranger_kms.json");
        org.junit.Assert.assertNotNull(systemResourceURL);
        final org.apache.ambari.server.state.kerberos.KerberosDescriptor kerberosDescriptor = new org.apache.ambari.server.state.kerberos.KerberosDescriptorFactory().createInstance(new java.io.File(systemResourceURL.getFile()));
        org.junit.Assert.assertNotNull(kerberosDescriptor);
        org.apache.ambari.server.state.kerberos.KerberosServiceDescriptor serviceDescriptor;
        serviceDescriptor = kerberosDescriptor.getService("RANGER_KMS");
        org.junit.Assert.assertNotNull(serviceDescriptor);
        org.junit.Assert.assertNotNull(serviceDescriptor.getIdentity("/smokeuser"));
        org.junit.Assert.assertNotNull(serviceDescriptor.getIdentity("/spnego"));
        org.apache.ambari.server.state.kerberos.KerberosComponentDescriptor componentDescriptor;
        componentDescriptor = serviceDescriptor.getComponent("RANGER_KMS_SERVER");
        org.junit.Assert.assertNotNull(componentDescriptor);
        org.junit.Assert.assertNotNull(componentDescriptor.getIdentity("/smokeuser"));
        org.junit.Assert.assertNotNull(componentDescriptor.getIdentity("/spnego"));
        org.junit.Assert.assertNotNull(componentDescriptor.getIdentity("/spnego").getPrincipalDescriptor());
        org.junit.Assert.assertEquals("invalid_name@${realm}", componentDescriptor.getIdentity("/spnego").getPrincipalDescriptor().getValue());
        org.apache.ambari.server.orm.entities.ArtifactEntity artifactEntity = org.easymock.EasyMock.createMock(org.apache.ambari.server.orm.entities.ArtifactEntity.class);
        org.easymock.EasyMock.expect(artifactEntity.getArtifactData()).andReturn(kerberosDescriptor.toMap()).once();
        org.easymock.Capture<java.util.Map<java.lang.String, java.lang.Object>> captureMap = org.easymock.EasyMock.newCapture();
        org.easymock.EasyMock.expect(artifactEntity.getForeignKeys()).andReturn(java.util.Collections.singletonMap("cluster", "2")).times(2);
        artifactEntity.setArtifactData(org.easymock.EasyMock.capture(captureMap));
        org.easymock.EasyMock.expectLastCall().once();
        org.apache.ambari.server.state.ConfigHelper configHelper = injector.getInstance(org.apache.ambari.server.state.ConfigHelper.class);
        configHelper.updateAgentConfigs(org.easymock.EasyMock.anyObject(java.util.Set.class));
        org.easymock.EasyMock.expectLastCall().once();
        org.apache.ambari.server.orm.dao.ArtifactDAO artifactDAO = org.easymock.EasyMock.createMock(org.apache.ambari.server.orm.dao.ArtifactDAO.class);
        org.easymock.EasyMock.expect(artifactDAO.merge(artifactEntity)).andReturn(artifactEntity).atLeastOnce();
        java.util.Map<java.lang.String, java.lang.String> properties = new java.util.HashMap<>();
        properties.put("ranger.ks.kerberos.principal", "correct_value@EXAMPLE.COM");
        properties.put("xasecure.audit.jaas.Client.option.principal", "wrong_value@EXAMPLE.COM");
        org.apache.ambari.server.state.Config config = org.easymock.EasyMock.createMock(org.apache.ambari.server.state.Config.class);
        org.easymock.EasyMock.expect(config.getProperties()).andReturn(properties).anyTimes();
        org.easymock.EasyMock.expect(config.getPropertiesAttributes()).andReturn(java.util.Collections.<java.lang.String, java.util.Map<java.lang.String, java.lang.String>>emptyMap()).anyTimes();
        org.easymock.EasyMock.expect(config.getTag()).andReturn("version1").anyTimes();
        org.easymock.EasyMock.expect(config.getType()).andReturn("ranger-kms-audit").anyTimes();
        java.util.Map<java.lang.String, java.lang.String> hsiProperties = new java.util.HashMap<>();
        hsiProperties.put("hive.llap.daemon.keytab.file", "/etc/security/keytabs/hive.service.keytab");
        hsiProperties.put("hive.llap.zk.sm.keytab.file", "/etc/security/keytabs/hive.llap.zk.sm.keytab");
        org.apache.ambari.server.state.Config hsiConfig = org.easymock.EasyMock.createMock(org.apache.ambari.server.state.Config.class);
        org.easymock.EasyMock.expect(hsiConfig.getProperties()).andReturn(hsiProperties).anyTimes();
        org.easymock.EasyMock.expect(hsiConfig.getPropertiesAttributes()).andReturn(java.util.Collections.<java.lang.String, java.util.Map<java.lang.String, java.lang.String>>emptyMap()).anyTimes();
        org.easymock.EasyMock.expect(hsiConfig.getTag()).andReturn("version1").anyTimes();
        org.easymock.EasyMock.expect(hsiConfig.getType()).andReturn("hive-interactive-site").anyTimes();
        org.apache.ambari.server.state.Config newConfig = org.easymock.EasyMock.createMock(org.apache.ambari.server.state.Config.class);
        org.easymock.EasyMock.expect(newConfig.getTag()).andReturn("version2").anyTimes();
        org.easymock.EasyMock.expect(newConfig.getType()).andReturn("ranger-kms-audit").anyTimes();
        org.apache.ambari.server.state.Config newHsiConfig = org.easymock.EasyMock.createMock(org.apache.ambari.server.state.Config.class);
        org.easymock.EasyMock.expect(newHsiConfig.getTag()).andReturn("version2").anyTimes();
        org.easymock.EasyMock.expect(newHsiConfig.getType()).andReturn("hive-interactive-site").anyTimes();
        org.apache.ambari.server.controller.ServiceConfigVersionResponse response = org.easymock.EasyMock.createMock(org.apache.ambari.server.controller.ServiceConfigVersionResponse.class);
        org.apache.ambari.server.controller.ServiceConfigVersionResponse response1 = org.easymock.EasyMock.createMock(org.apache.ambari.server.controller.ServiceConfigVersionResponse.class);
        org.apache.ambari.server.state.StackId stackId = org.easymock.EasyMock.createMock(org.apache.ambari.server.state.StackId.class);
        org.apache.ambari.server.state.Cluster cluster = org.easymock.EasyMock.createMock(org.apache.ambari.server.state.Cluster.class);
        org.easymock.EasyMock.expect(cluster.getDesiredStackVersion()).andReturn(stackId).anyTimes();
        org.easymock.EasyMock.expect(cluster.getDesiredConfigByType("dbks-site")).andReturn(config).anyTimes();
        org.easymock.EasyMock.expect(cluster.getDesiredConfigByType("ranger-kms-audit")).andReturn(config).anyTimes();
        org.easymock.EasyMock.expect(cluster.getConfigsByType("ranger-kms-audit")).andReturn(java.util.Collections.singletonMap("version1", config)).anyTimes();
        org.easymock.EasyMock.expect(cluster.getServiceByConfigType("ranger-kms-audit")).andReturn("RANGER").anyTimes();
        org.easymock.EasyMock.expect(cluster.getClusterName()).andReturn("cl1").anyTimes();
        org.easymock.EasyMock.expect(cluster.getConfig(org.easymock.EasyMock.eq("ranger-kms-audit"), org.easymock.EasyMock.anyString())).andReturn(newConfig).once();
        org.easymock.EasyMock.expect(cluster.addDesiredConfig("ambari-upgrade", java.util.Collections.singleton(newConfig), "Updated ranger-kms-audit during Ambari Upgrade from 2.5.2 to 2.6.0.")).andReturn(response).once();
        org.easymock.EasyMock.expect(cluster.getDesiredConfigByType("hive-site")).andReturn(hsiConfig).anyTimes();
        org.easymock.EasyMock.expect(cluster.getDesiredConfigByType("hive-interactive-site")).andReturn(hsiConfig).anyTimes();
        org.easymock.EasyMock.expect(cluster.getConfigsByType("hive-interactive-site")).andReturn(java.util.Collections.singletonMap("version1", hsiConfig)).anyTimes();
        org.easymock.EasyMock.expect(cluster.getServiceByConfigType("hive-interactive-site")).andReturn("HIVE").anyTimes();
        org.easymock.EasyMock.expect(cluster.getConfig(org.easymock.EasyMock.eq("hive-interactive-site"), org.easymock.EasyMock.anyString())).andReturn(newHsiConfig).anyTimes();
        final org.apache.ambari.server.state.Clusters clusters = injector.getInstance(org.apache.ambari.server.state.Clusters.class);
        org.easymock.EasyMock.expect(clusters.getCluster(2L)).andReturn(cluster).anyTimes();
        org.easymock.Capture<? extends java.util.Map<java.lang.String, java.lang.String>> captureProperties = org.easymock.EasyMock.newCapture();
        org.apache.ambari.server.controller.AmbariManagementController controller = injector.getInstance(org.apache.ambari.server.controller.AmbariManagementController.class);
        org.easymock.EasyMock.expect(controller.createConfig(org.easymock.EasyMock.eq(cluster), org.easymock.EasyMock.eq(stackId), org.easymock.EasyMock.eq("ranger-kms-audit"), org.easymock.EasyMock.capture(captureProperties), org.easymock.EasyMock.anyString(), org.easymock.EasyMock.anyObject(java.util.Map.class))).andReturn(null).once();
        org.easymock.Capture<? extends java.util.Map<java.lang.String, java.lang.String>> captureHsiProperties = org.easymock.EasyMock.newCapture();
        org.easymock.EasyMock.expect(controller.createConfig(org.easymock.EasyMock.eq(cluster), org.easymock.EasyMock.eq(stackId), org.easymock.EasyMock.eq("hive-interactive-site"), org.easymock.EasyMock.capture(captureHsiProperties), org.easymock.EasyMock.anyString(), org.easymock.EasyMock.anyObject(java.util.Map.class))).andReturn(null).anyTimes();
        org.easymock.EasyMock.replay(artifactDAO, artifactEntity, cluster, clusters, config, newConfig, hsiConfig, newHsiConfig, response, response1, controller, stackId, configHelper);
        org.apache.ambari.server.upgrade.UpgradeCatalog260 upgradeCatalog260 = injector.getInstance(org.apache.ambari.server.upgrade.UpgradeCatalog260.class);
        upgradeCatalog260.updateKerberosDescriptorArtifact(artifactDAO, artifactEntity);
        org.easymock.EasyMock.verify(artifactDAO, artifactEntity, cluster, clusters, config, newConfig, response, controller, stackId, configHelper);
        org.apache.ambari.server.state.kerberos.KerberosDescriptor kerberosDescriptorUpdated = new org.apache.ambari.server.state.kerberos.KerberosDescriptorFactory().createInstance(captureMap.getValue());
        org.junit.Assert.assertNotNull(kerberosDescriptorUpdated);
        org.junit.Assert.assertNull(kerberosDescriptorUpdated.getService("RANGER_KMS").getIdentity("/smokeuser"));
        org.junit.Assert.assertNull(kerberosDescriptorUpdated.getService("RANGER_KMS").getComponent("RANGER_KMS_SERVER").getIdentity("/smokeuser"));
        org.apache.ambari.server.state.kerberos.KerberosIdentityDescriptor identity;
        org.junit.Assert.assertNull(kerberosDescriptorUpdated.getService("RANGER_KMS").getIdentity("/spnego"));
        identity = kerberosDescriptorUpdated.getService("RANGER_KMS").getIdentity("ranger_kms_spnego");
        org.junit.Assert.assertNotNull(identity);
        org.junit.Assert.assertEquals("/spnego", identity.getReference());
        org.junit.Assert.assertNull(kerberosDescriptorUpdated.getService("RANGER_KMS").getComponent("RANGER_KMS_SERVER").getIdentity("/spnego"));
        identity = kerberosDescriptorUpdated.getService("RANGER_KMS").getComponent("RANGER_KMS_SERVER").getIdentity("ranger_kms_ranger_kms_server_spnego");
        org.junit.Assert.assertNotNull(identity);
        org.junit.Assert.assertEquals("/spnego", identity.getReference());
        org.junit.Assert.assertNotNull(identity.getPrincipalDescriptor());
        org.junit.Assert.assertNull(identity.getPrincipalDescriptor().getValue());
        org.junit.Assert.assertTrue(captureProperties.hasCaptured());
        java.util.Map<java.lang.String, java.lang.String> newProperties = captureProperties.getValue();
        org.junit.Assert.assertEquals("correct_value@EXAMPLE.COM", newProperties.get("xasecure.audit.jaas.Client.option.principal"));
        java.util.Map<java.lang.String, java.util.List<java.lang.String>> identitiesMap = new java.util.HashMap<>();
        identitiesMap.put("llap_zk_hive", new java.util.ArrayList<java.lang.String>() {
            {
                add("hive-interactive-site/hive.llap.zk.sm.keytab.file");
                add("hive-interactive-site/hive.llap.zk.sm.principal");
            }
        });
        identitiesMap.put("llap_task_hive", new java.util.ArrayList<java.lang.String>() {
            {
                add("hive-interactive-site/hive.llap.task.keytab.file");
                add("hive-interactive-site/hive.llap.task.principal");
            }
        });
        for (java.lang.String llapIdentity : identitiesMap.keySet()) {
            org.apache.ambari.server.state.kerberos.KerberosIdentityDescriptor yarnKerberosIdentityDescriptor = kerberosDescriptorUpdated.getService("YARN").getComponent("NODEMANAGER").getIdentity(llapIdentity);
            org.junit.Assert.assertNotNull(yarnKerberosIdentityDescriptor);
            org.junit.Assert.assertEquals("/HIVE/HIVE_SERVER/hive_server_hive", yarnKerberosIdentityDescriptor.getReference());
            org.apache.ambari.server.state.kerberos.KerberosKeytabDescriptor yarnKerberosKeytabDescriptor = yarnKerberosIdentityDescriptor.getKeytabDescriptor();
            org.junit.Assert.assertNotNull(yarnKerberosKeytabDescriptor);
            org.junit.Assert.assertEquals(null, yarnKerberosKeytabDescriptor.getGroupAccess());
            org.junit.Assert.assertEquals(null, yarnKerberosKeytabDescriptor.getGroupName());
            org.junit.Assert.assertEquals(null, yarnKerberosKeytabDescriptor.getOwnerAccess());
            org.junit.Assert.assertEquals(null, yarnKerberosKeytabDescriptor.getOwnerName());
            org.junit.Assert.assertEquals(null, yarnKerberosKeytabDescriptor.getFile());
            org.junit.Assert.assertEquals(identitiesMap.get(llapIdentity).get(0), yarnKerberosKeytabDescriptor.getConfiguration());
            org.apache.ambari.server.state.kerberos.KerberosPrincipalDescriptor yarnKerberosPrincipalDescriptor = yarnKerberosIdentityDescriptor.getPrincipalDescriptor();
            org.junit.Assert.assertNotNull(yarnKerberosPrincipalDescriptor);
            org.junit.Assert.assertEquals(null, yarnKerberosPrincipalDescriptor.getName());
            org.junit.Assert.assertEquals(org.apache.ambari.server.state.kerberos.KerberosPrincipalType.SERVICE, yarnKerberosPrincipalDescriptor.getType());
            org.junit.Assert.assertEquals(null, yarnKerberosPrincipalDescriptor.getValue());
            org.junit.Assert.assertEquals(identitiesMap.get(llapIdentity).get(1), yarnKerberosPrincipalDescriptor.getConfiguration());
        }
    }

    @org.junit.Test
    public void testUpdateAmsConfigs() throws java.lang.Exception {
        java.util.Map<java.lang.String, java.lang.String> oldProperties = new java.util.HashMap<java.lang.String, java.lang.String>() {
            {
                put("ssl.client.truststore.location", "/some/location");
                put("ssl.client.truststore.alias", "test_alias");
            }
        };
        java.util.Map<java.lang.String, java.lang.String> newProperties = new java.util.HashMap<java.lang.String, java.lang.String>() {
            {
                put("ssl.client.truststore.location", "/some/location");
            }
        };
        org.easymock.EasyMockSupport easyMockSupport = new org.easymock.EasyMockSupport();
        org.apache.ambari.server.state.Clusters clusters = easyMockSupport.createNiceMock(org.apache.ambari.server.state.Clusters.class);
        final org.apache.ambari.server.state.Cluster cluster = easyMockSupport.createNiceMock(org.apache.ambari.server.state.Cluster.class);
        org.apache.ambari.server.state.Config mockAmsSslClient = easyMockSupport.createNiceMock(org.apache.ambari.server.state.Config.class);
        org.easymock.EasyMock.expect(clusters.getClusters()).andReturn(new java.util.HashMap<java.lang.String, org.apache.ambari.server.state.Cluster>() {
            {
                put("normal", cluster);
            }
        }).once();
        org.easymock.EasyMock.expect(cluster.getDesiredConfigByType("ams-ssl-client")).andReturn(mockAmsSslClient).atLeastOnce();
        org.easymock.EasyMock.expect(mockAmsSslClient.getProperties()).andReturn(oldProperties).anyTimes();
        com.google.inject.Injector injector = easyMockSupport.createNiceMock(com.google.inject.Injector.class);
        org.easymock.EasyMock.expect(injector.getInstance(com.google.gson.Gson.class)).andReturn(null).anyTimes();
        org.easymock.EasyMock.expect(injector.getInstance(org.apache.ambari.server.controller.MaintenanceStateHelper.class)).andReturn(null).anyTimes();
        org.easymock.EasyMock.replay(injector, clusters, mockAmsSslClient, cluster);
        org.apache.ambari.server.controller.AmbariManagementControllerImpl controller = org.easymock.EasyMock.createMockBuilder(org.apache.ambari.server.controller.AmbariManagementControllerImpl.class).addMockedMethod("createConfiguration", org.apache.ambari.server.controller.ConfigurationRequest.class).addMockedMethod("getClusters", new java.lang.Class[]{  }).addMockedMethod("createConfig", org.apache.ambari.server.state.Cluster.class, org.apache.ambari.server.state.StackId.class, java.lang.String.class, java.util.Map.class, java.lang.String.class, java.util.Map.class).withConstructor(org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.actionmanager.ActionManager.class), clusters, injector).createNiceMock();
        com.google.inject.Injector injector2 = easyMockSupport.createNiceMock(com.google.inject.Injector.class);
        org.easymock.Capture<java.util.Map> propertiesCapture = org.easymock.EasyMock.newCapture();
        org.easymock.EasyMock.expect(injector2.getInstance(org.apache.ambari.server.controller.AmbariManagementController.class)).andReturn(controller).anyTimes();
        org.easymock.EasyMock.expect(controller.getClusters()).andReturn(clusters).anyTimes();
        org.easymock.EasyMock.expect(controller.createConfig(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.Cluster.class), org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.StackId.class), org.easymock.EasyMock.anyString(), org.easymock.EasyMock.capture(propertiesCapture), org.easymock.EasyMock.anyString(), org.easymock.EasyMock.anyObject(java.util.Map.class))).andReturn(org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Config.class)).once();
        org.easymock.EasyMock.replay(controller, injector2);
        new org.apache.ambari.server.upgrade.UpgradeCatalog260(injector2).updateAmsConfigs();
        easyMockSupport.verifyAll();
        java.util.Map<java.lang.String, java.lang.String> updatedProperties = propertiesCapture.getValue();
        org.junit.Assert.assertTrue(com.google.common.collect.Maps.difference(newProperties, updatedProperties).areEqual());
    }

    @org.junit.Test
    public void testUpdateHiveConfigs() throws java.lang.Exception {
        java.util.Map<java.lang.String, java.lang.String> oldProperties = new java.util.HashMap<java.lang.String, java.lang.String>() {
            {
                put("hive.llap.zk.sm.keytab.file", "/etc/security/keytabs/hive.llap.zk.sm.keytab");
                put("hive.llap.daemon.keytab.file", "/etc/security/keytabs/hive.service.keytab");
                put("hive.llap.task.keytab.file", "/etc/security/keytabs/hive.llap.task.keytab");
            }
        };
        java.util.Map<java.lang.String, java.lang.String> newProperties = new java.util.HashMap<java.lang.String, java.lang.String>() {
            {
                put("hive.llap.zk.sm.keytab.file", "/etc/security/keytabs/hive.service.keytab");
                put("hive.llap.daemon.keytab.file", "/etc/security/keytabs/hive.service.keytab");
                put("hive.llap.task.keytab.file", "/etc/security/keytabs/hive.service.keytab");
            }
        };
        org.easymock.EasyMockSupport easyMockSupport = new org.easymock.EasyMockSupport();
        org.apache.ambari.server.state.Clusters clusters = easyMockSupport.createNiceMock(org.apache.ambari.server.state.Clusters.class);
        final org.apache.ambari.server.state.Cluster cluster = easyMockSupport.createNiceMock(org.apache.ambari.server.state.Cluster.class);
        org.apache.ambari.server.state.Config mockHsiConfigs = easyMockSupport.createNiceMock(org.apache.ambari.server.state.Config.class);
        org.easymock.EasyMock.expect(clusters.getClusters()).andReturn(new java.util.HashMap<java.lang.String, org.apache.ambari.server.state.Cluster>() {
            {
                put("normal", cluster);
            }
        }).once();
        org.easymock.EasyMock.expect(cluster.getDesiredConfigByType("hive-interactive-site")).andReturn(mockHsiConfigs).atLeastOnce();
        org.easymock.EasyMock.expect(mockHsiConfigs.getProperties()).andReturn(oldProperties).anyTimes();
        com.google.inject.Injector injector = easyMockSupport.createNiceMock(com.google.inject.Injector.class);
        org.easymock.EasyMock.expect(injector.getInstance(com.google.gson.Gson.class)).andReturn(null).anyTimes();
        org.easymock.EasyMock.expect(injector.getInstance(org.apache.ambari.server.controller.MaintenanceStateHelper.class)).andReturn(null).anyTimes();
        org.easymock.EasyMock.replay(injector, clusters, mockHsiConfigs, cluster);
        org.apache.ambari.server.controller.AmbariManagementControllerImpl controller = org.easymock.EasyMock.createMockBuilder(org.apache.ambari.server.controller.AmbariManagementControllerImpl.class).addMockedMethod("createConfiguration", org.apache.ambari.server.controller.ConfigurationRequest.class).addMockedMethod("getClusters", new java.lang.Class[]{  }).addMockedMethod("createConfig", org.apache.ambari.server.state.Cluster.class, org.apache.ambari.server.state.StackId.class, java.lang.String.class, java.util.Map.class, java.lang.String.class, java.util.Map.class).withConstructor(org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.actionmanager.ActionManager.class), clusters, injector).createNiceMock();
        com.google.inject.Injector injector2 = easyMockSupport.createNiceMock(com.google.inject.Injector.class);
        org.easymock.Capture<java.util.Map> propertiesCapture = org.easymock.EasyMock.newCapture();
        org.easymock.EasyMock.expect(injector2.getInstance(org.apache.ambari.server.controller.AmbariManagementController.class)).andReturn(controller).anyTimes();
        org.easymock.EasyMock.expect(controller.getClusters()).andReturn(clusters).anyTimes();
        org.easymock.EasyMock.expect(controller.createConfig(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.Cluster.class), org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.StackId.class), org.easymock.EasyMock.anyString(), org.easymock.EasyMock.capture(propertiesCapture), org.easymock.EasyMock.anyString(), org.easymock.EasyMock.anyObject(java.util.Map.class))).andReturn(org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Config.class)).once();
        org.easymock.EasyMock.replay(controller, injector2);
        org.apache.ambari.server.upgrade.UpgradeCatalog260 upgradeCatalog260 = new org.apache.ambari.server.upgrade.UpgradeCatalog260(injector2);
        upgradeCatalog260.updateYarnKerberosDescUpdatedList("hive.llap.zk.sm.keytab.file");
        upgradeCatalog260.updateYarnKerberosDescUpdatedList("hive.llap.task.keytab.file");
        upgradeCatalog260.updateHiveConfigs();
        easyMockSupport.verifyAll();
        java.util.Map<java.lang.String, java.lang.String> updatedProperties = propertiesCapture.getValue();
        org.junit.Assert.assertTrue(com.google.common.collect.Maps.difference(newProperties, updatedProperties).areEqual());
    }

    @org.junit.Test
    public void testHDFSWidgetUpdate() throws java.lang.Exception {
        final org.apache.ambari.server.state.Clusters clusters = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Clusters.class);
        final org.apache.ambari.server.state.Cluster cluster = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Cluster.class);
        final org.apache.ambari.server.controller.AmbariManagementController controller = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.AmbariManagementController.class);
        final com.google.gson.Gson gson = new com.google.gson.Gson();
        final org.apache.ambari.server.orm.dao.WidgetDAO widgetDAO = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.orm.dao.WidgetDAO.class);
        final org.apache.ambari.server.api.services.AmbariMetaInfo metaInfo = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.services.AmbariMetaInfo.class);
        org.apache.ambari.server.orm.entities.WidgetEntity widgetEntity = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.orm.entities.WidgetEntity.class);
        org.apache.ambari.server.state.StackId stackId = new org.apache.ambari.server.state.StackId("HDP", "2.0.0");
        org.apache.ambari.server.state.StackInfo stackInfo = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.StackInfo.class);
        org.apache.ambari.server.state.ServiceInfo serviceInfo = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ServiceInfo.class);
        org.apache.ambari.server.state.Service service = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Service.class);
        java.lang.String widgetStr = (((((((((((((("{\n" + "  \"layouts\": [\n") + "      {\n") + "      \"layout_name\": \"default_hdfs_heatmap\",\n") + "      \"display_name\": \"Standard HDFS HeatMaps\",\n") + "      \"section_name\": \"HDFS_HEATMAPS\",\n") + "      \"widgetLayoutInfo\": [\n") + "        {\n") + "          \"widget_name\": \"HDFS Bytes Read\",\n") + "          \"metrics\": [],\n") + "          \"values\": []\n") + "        }\n") + "      ]\n") + "    }\n") + "  ]\n") + "}";
        java.io.File dataDirectory = temporaryFolder.newFolder();
        java.io.File file = new java.io.File(dataDirectory, "hdfs_widget.json");
        org.apache.commons.io.FileUtils.writeStringToFile(file, widgetStr, java.nio.charset.Charset.defaultCharset());
        final com.google.inject.Injector mockInjector = com.google.inject.Guice.createInjector(new com.google.inject.AbstractModule() {
            @java.lang.Override
            protected void configure() {
                org.apache.ambari.server.testutils.PartialNiceMockBinder.newBuilder().addConfigsBindings().addFactoriesInstallBinding().addPasswordEncryptorBindings().addLdapBindings().build().configure(binder());
                bind(javax.persistence.EntityManager.class).toInstance(org.easymock.EasyMock.createNiceMock(javax.persistence.EntityManager.class));
                bind(org.apache.ambari.server.controller.AmbariManagementController.class).toInstance(controller);
                bind(org.apache.ambari.server.state.Clusters.class).toInstance(clusters);
                bind(org.apache.ambari.server.orm.DBAccessor.class).toInstance(org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.orm.DBAccessor.class));
                bind(org.apache.ambari.server.state.stack.OsFamily.class).toInstance(org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.stack.OsFamily.class));
                bind(com.google.gson.Gson.class).toInstance(gson);
                bind(org.apache.ambari.server.orm.dao.WidgetDAO.class).toInstance(widgetDAO);
                bind(org.apache.ambari.server.stack.StackManagerFactory.class).toInstance(org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.stack.StackManagerFactory.class));
                bind(org.apache.ambari.server.api.services.AmbariMetaInfo.class).toInstance(metaInfo);
                bind(org.apache.ambari.server.actionmanager.ActionDBAccessor.class).toInstance(org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.actionmanager.ActionDBAccessorImpl.class));
                bind(org.apache.ambari.server.topology.PersistedState.class).toInstance(org.easymock.EasyMock.mock(org.apache.ambari.server.topology.PersistedStateImpl.class));
                bind(com.google.inject.persist.UnitOfWork.class).toInstance(org.easymock.EasyMock.createNiceMock(com.google.inject.persist.UnitOfWork.class));
                bind(org.apache.ambari.server.metadata.RoleCommandOrderProvider.class).to(org.apache.ambari.server.metadata.CachedRoleCommandOrderProvider.class);
                bind(org.apache.ambari.server.actionmanager.HostRoleCommandFactory.class).to(org.apache.ambari.server.actionmanager.HostRoleCommandFactoryImpl.class);
                bind(org.apache.ambari.server.actionmanager.StageFactory.class).to(org.apache.ambari.server.actionmanager.StageFactoryImpl.class);
                bind(org.apache.ambari.server.audit.AuditLogger.class).toInstance(org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.audit.AuditLoggerDefaultImpl.class));
                bind(org.springframework.security.crypto.password.PasswordEncoder.class).toInstance(new org.springframework.security.crypto.password.StandardPasswordEncoder());
                bind(org.apache.ambari.server.hooks.HookService.class).to(org.apache.ambari.server.hooks.users.UserHookService.class);
                bind(org.apache.ambari.server.state.ServiceComponentHostFactory.class).toInstance(org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ServiceComponentHostFactory.class));
                bind(org.apache.ambari.server.controller.AbstractRootServiceResponseFactory.class).to(org.apache.ambari.server.controller.RootServiceResponseFactory.class);
                bind(org.apache.ambari.server.security.encryption.CredentialStoreService.class).toInstance(org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.security.encryption.CredentialStoreService.class));
                bind(org.apache.ambari.server.scheduler.ExecutionScheduler.class).toInstance(org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.scheduler.ExecutionScheduler.class));
                bind(org.apache.ambari.server.events.publishers.STOMPUpdatePublisher.class).toInstance(org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.events.publishers.STOMPUpdatePublisher.class));
                bind(org.apache.ambari.server.controller.KerberosHelper.class).toInstance(org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.KerberosHelperImpl.class));
                bind(org.apache.ambari.server.mpack.MpackManagerFactory.class).toInstance(org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.mpack.MpackManagerFactory.class));
            }
        });
        org.easymock.EasyMock.expect(controller.getClusters()).andReturn(clusters).anyTimes();
        org.easymock.EasyMock.expect(clusters.getClusters()).andReturn(new java.util.HashMap<java.lang.String, org.apache.ambari.server.state.Cluster>() {
            {
                put("normal", cluster);
            }
        }).anyTimes();
        org.easymock.EasyMock.expect(cluster.getServices()).andReturn(java.util.Collections.singletonMap("HDFS", service)).anyTimes();
        org.easymock.EasyMock.expect(cluster.getClusterId()).andReturn(1L).anyTimes();
        org.easymock.EasyMock.expect(service.getDesiredStackId()).andReturn(stackId).anyTimes();
        org.easymock.EasyMock.expect(stackInfo.getService("HDFS")).andReturn(serviceInfo);
        org.easymock.EasyMock.expect(cluster.getDesiredStackVersion()).andReturn(stackId).anyTimes();
        org.easymock.EasyMock.expect(metaInfo.getStack("HDP", "2.0.0")).andReturn(stackInfo).anyTimes();
        org.easymock.EasyMock.expect(serviceInfo.getWidgetsDescriptorFile()).andReturn(file).anyTimes();
        org.easymock.EasyMock.expect(widgetDAO.findByName(1L, "HDFS Bytes Read", "ambari", "HDFS_HEATMAPS")).andReturn(java.util.Collections.singletonList(widgetEntity));
        org.easymock.EasyMock.expect(widgetDAO.merge(widgetEntity)).andReturn(null);
        org.easymock.EasyMock.expect(widgetEntity.getWidgetName()).andReturn("HDFS Bytes Read").anyTimes();
        org.easymock.EasyMock.replay(clusters, cluster, controller, widgetDAO, metaInfo, widgetEntity, stackInfo, serviceInfo, service);
        mockInjector.getInstance(org.apache.ambari.server.upgrade.UpgradeCatalog260.class).updateHDFSWidgetDefinition();
        org.easymock.EasyMock.verify(clusters, cluster, controller, widgetDAO, widgetEntity, stackInfo, serviceInfo);
    }

    private com.google.inject.Injector getInjector() {
        return com.google.inject.Guice.createInjector(new com.google.inject.Module() {
            @java.lang.Override
            public void configure(com.google.inject.Binder binder) {
                org.apache.ambari.server.testutils.PartialNiceMockBinder.newBuilder().addPasswordEncryptorBindings().addLdapBindings().build().configure(binder);
                binder.bindConstant().annotatedWith(com.google.inject.name.Names.named("actionTimeout")).to(600000L);
                binder.bindConstant().annotatedWith(com.google.inject.name.Names.named("schedulerSleeptime")).to(1L);
                binder.bindConstant().annotatedWith(com.google.inject.name.Names.named(org.apache.ambari.server.orm.dao.HostRoleCommandDAO.HRC_STATUS_SUMMARY_CACHE_ENABLED)).to(true);
                binder.bindConstant().annotatedWith(com.google.inject.name.Names.named(org.apache.ambari.server.orm.dao.HostRoleCommandDAO.HRC_STATUS_SUMMARY_CACHE_SIZE)).to(10000L);
                binder.bindConstant().annotatedWith(com.google.inject.name.Names.named(org.apache.ambari.server.orm.dao.HostRoleCommandDAO.HRC_STATUS_SUMMARY_CACHE_EXPIRY_DURATION_MINUTES)).to(30L);
                binder.bind(org.apache.ambari.server.orm.DBAccessor.class).toInstance(dbAccessor);
                binder.bind(org.apache.ambari.server.state.stack.OsFamily.class).toInstance(osFamily);
                binder.bind(javax.persistence.EntityManager.class).toInstance(entityManager);
                binder.bind(org.apache.ambari.server.state.Clusters.class).toInstance(org.easymock.EasyMock.createMock(org.apache.ambari.server.state.Clusters.class));
                binder.bind(org.apache.ambari.server.controller.AmbariManagementController.class).toInstance(org.easymock.EasyMock.createMock(org.apache.ambari.server.controller.AmbariManagementControllerImpl.class));
                binder.bind(org.apache.ambari.server.actionmanager.ActionDBAccessor.class).toInstance(org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.actionmanager.ActionDBAccessorImpl.class));
                binder.bind(org.apache.ambari.server.topology.PersistedState.class).toInstance(org.easymock.EasyMock.createMock(org.apache.ambari.server.topology.PersistedStateImpl.class));
                binder.bind(org.apache.ambari.server.actionmanager.HostRoleCommandFactory.class).to(org.apache.ambari.server.actionmanager.HostRoleCommandFactoryImpl.class);
                binder.bind(org.apache.ambari.server.audit.AuditLogger.class).toInstance(org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.audit.AuditLoggerDefaultImpl.class));
                binder.bind(org.apache.ambari.server.actionmanager.StageFactory.class).to(org.apache.ambari.server.actionmanager.StageFactoryImpl.class);
                binder.bind(com.google.inject.persist.UnitOfWork.class).toInstance(org.easymock.EasyMock.createNiceMock(com.google.inject.persist.UnitOfWork.class));
                binder.bind(org.apache.ambari.server.metadata.RoleCommandOrderProvider.class).to(org.apache.ambari.server.metadata.CachedRoleCommandOrderProvider.class);
                binder.bind(org.springframework.security.crypto.password.PasswordEncoder.class).toInstance(new org.springframework.security.crypto.password.StandardPasswordEncoder());
                binder.bind(org.apache.ambari.server.hooks.HookService.class).to(org.apache.ambari.server.hooks.users.UserHookService.class);
                binder.bind(org.apache.ambari.server.state.ServiceComponentHostFactory.class).toInstance(org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ServiceComponentHostFactory.class));
                binder.bind(org.apache.ambari.server.controller.AbstractRootServiceResponseFactory.class).to(org.apache.ambari.server.controller.RootServiceResponseFactory.class);
                binder.bind(org.apache.ambari.server.security.encryption.CredentialStoreService.class).toInstance(org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.security.encryption.CredentialStoreService.class));
                binder.bind(org.apache.ambari.server.scheduler.ExecutionScheduler.class).toInstance(org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.scheduler.ExecutionScheduler.class));
                binder.bind(org.apache.ambari.server.api.services.AmbariMetaInfo.class).toInstance(org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.services.AmbariMetaInfo.class));
                binder.bind(org.apache.ambari.server.controller.KerberosHelper.class).toInstance(org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.KerberosHelperImpl.class));
                binder.bind(org.apache.ambari.server.agent.stomp.MetadataHolder.class).toInstance(org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.agent.stomp.MetadataHolder.class));
                binder.bind(org.apache.ambari.server.agent.stomp.AgentConfigsHolder.class).toInstance(org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.agent.stomp.AgentConfigsHolder.class));
                binder.bind(org.apache.ambari.server.state.ConfigHelper.class).toInstance(org.easymock.EasyMock.createStrictMock(org.apache.ambari.server.state.ConfigHelper.class));
                binder.bind(org.apache.ambari.server.mpack.MpackManagerFactory.class).toInstance(org.easymock.EasyMock.createStrictMock(org.apache.ambari.server.mpack.MpackManagerFactory.class));
                binder.install(new com.google.inject.assistedinject.FactoryModuleBuilder().build(org.apache.ambari.server.actionmanager.RequestFactory.class));
                binder.install(new com.google.inject.assistedinject.FactoryModuleBuilder().build(org.apache.ambari.server.topology.tasks.ConfigureClusterTaskFactory.class));
                binder.install(new com.google.inject.assistedinject.FactoryModuleBuilder().implement(org.apache.ambari.server.state.Config.class, org.apache.ambari.server.state.ConfigImpl.class).build(org.apache.ambari.server.state.ConfigFactory.class));
                binder.install(new com.google.inject.assistedinject.FactoryModuleBuilder().build(org.apache.ambari.server.stack.StackManagerFactory.class));
                binder.install(new com.google.inject.assistedinject.FactoryModuleBuilder().implement(org.apache.ambari.server.hooks.HookContext.class, org.apache.ambari.server.hooks.users.PostUserCreationHookContext.class).build(org.apache.ambari.server.hooks.HookContextFactory.class));
                binder.install(new com.google.inject.assistedinject.FactoryModuleBuilder().build(org.apache.ambari.server.stageplanner.RoleGraphFactory.class));
                binder.install(new com.google.inject.assistedinject.FactoryModuleBuilder().implement(org.apache.ambari.server.state.scheduler.RequestExecution.class, org.apache.ambari.server.state.scheduler.RequestExecutionImpl.class).build(org.apache.ambari.server.state.scheduler.RequestExecutionFactory.class));
                binder.install(new com.google.inject.assistedinject.FactoryModuleBuilder().implement(org.apache.ambari.server.state.configgroup.ConfigGroup.class, org.apache.ambari.server.state.configgroup.ConfigGroupImpl.class).build(org.apache.ambari.server.state.configgroup.ConfigGroupFactory.class));
                binder.install(new com.google.inject.assistedinject.FactoryModuleBuilder().implement(org.apache.ambari.server.events.AmbariEvent.class, com.google.inject.name.Names.named("userCreated"), org.apache.ambari.server.hooks.users.UserCreatedEvent.class).build(org.apache.ambari.server.hooks.AmbariEventFactory.class));
                binder.install(new com.google.inject.assistedinject.FactoryModuleBuilder().implement(org.apache.ambari.server.state.ServiceComponent.class, org.apache.ambari.server.state.ServiceComponentImpl.class).build(org.apache.ambari.server.state.ServiceComponentFactory.class));
                binder.install(new com.google.inject.assistedinject.FactoryModuleBuilder().implement(org.apache.ambari.server.state.Host.class, org.apache.ambari.server.state.host.HostImpl.class).build(org.apache.ambari.server.state.host.HostFactory.class));
                binder.install(new com.google.inject.assistedinject.FactoryModuleBuilder().implement(org.apache.ambari.server.state.Cluster.class, org.apache.ambari.server.state.cluster.ClusterImpl.class).build(org.apache.ambari.server.state.cluster.ClusterFactory.class));
                binder.install(new com.google.inject.assistedinject.FactoryModuleBuilder().implement(org.apache.ambari.server.state.Service.class, org.apache.ambari.server.state.ServiceImpl.class).build(org.apache.ambari.server.state.ServiceFactory.class));
                binder.install(new com.google.inject.assistedinject.FactoryModuleBuilder().build(org.apache.ambari.server.stack.upgrade.orchestrate.UpgradeContextFactory.class));
            }
        });
    }
}
