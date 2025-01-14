package org.apache.ambari.server.scheduler;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.quartz.Scheduler;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@org.junit.runner.RunWith(org.mockito.junit.MockitoJUnitRunner.class)
public class ExecutionSchedulerTest {
    private org.apache.ambari.server.configuration.Configuration configuration;

    @org.mockito.Mock
    private org.quartz.Scheduler scheduler;

    @org.junit.Before
    public void setup() throws java.lang.Exception {
        java.util.Properties properties = new java.util.Properties();
        properties.setProperty(org.apache.ambari.server.configuration.Configuration.EXECUTION_SCHEDULER_THREADS.getKey(), "2");
        properties.setProperty(org.apache.ambari.server.configuration.Configuration.EXECUTION_SCHEDULER_CLUSTERED.getKey(), "false");
        properties.setProperty(org.apache.ambari.server.configuration.Configuration.EXECUTION_SCHEDULER_CONNECTIONS.getKey(), "2");
        properties.setProperty(org.apache.ambari.server.configuration.Configuration.SERVER_JDBC_DRIVER.getKey(), "db.driver");
        properties.setProperty(org.apache.ambari.server.configuration.Configuration.SERVER_JDBC_URL.getKey(), "jdbc:postgresql://localhost/");
        properties.setProperty(org.apache.ambari.server.configuration.Configuration.SERVER_JDBC_USER_NAME.getKey(), "user");
        properties.setProperty(org.apache.ambari.server.configuration.Configuration.SERVER_DB_NAME.getKey(), "derby");
        configuration = new org.apache.ambari.server.configuration.Configuration(properties);
    }

    private class TestExecutionScheduler extends org.apache.ambari.server.scheduler.ExecutionSchedulerImpl {
        public TestExecutionScheduler(org.apache.ambari.server.configuration.Configuration config) throws java.lang.Exception {
            super(config);
            this.scheduler = ExecutionSchedulerTest.this.scheduler;
            this.isInitialized = true;
        }

        @java.lang.Override
        protected synchronized void initializeScheduler() {
        }
    }

    @org.junit.Test
    public void testSchedulerInitialize() throws java.lang.Exception {
        org.apache.ambari.server.scheduler.ExecutionSchedulerImpl executionScheduler = new org.apache.ambari.server.scheduler.ExecutionSchedulerImpl(configuration);
        java.util.Properties actualProperties = executionScheduler.getQuartzSchedulerProperties();
        junit.framework.Assert.assertEquals("2", actualProperties.getProperty("org.quartz.threadPool.threadCount"));
        junit.framework.Assert.assertEquals("2", actualProperties.getProperty("org.quartz.dataSource.myDS.maxConnections"));
        junit.framework.Assert.assertEquals("false", actualProperties.getProperty("org.quartz.jobStore.isClustered"));
        junit.framework.Assert.assertEquals("org.quartz.impl.jdbcjobstore.PostgreSQLDelegate", actualProperties.getProperty("org.quartz.jobStore.driverDelegateClass"));
        junit.framework.Assert.assertEquals("select 0", actualProperties.getProperty("org.quartz.dataSource.myDS.validationQuery"));
        junit.framework.Assert.assertEquals(org.apache.ambari.server.scheduler.ExecutionSchedulerImpl.DEFAULT_SCHEDULER_NAME, actualProperties.getProperty("org.quartz.scheduler.instanceName"));
        junit.framework.Assert.assertEquals("org.quartz.simpl.SimpleThreadPool", actualProperties.getProperty("org.quartz.threadPool.class"));
    }

    @org.junit.Test
    public void testSchedulerStartStop() throws java.lang.Exception {
        org.apache.ambari.server.scheduler.ExecutionSchedulerImpl executionScheduler = new org.apache.ambari.server.scheduler.ExecutionSchedulerTest.TestExecutionScheduler(configuration);
        executionScheduler.startScheduler(180);
        executionScheduler.stopScheduler();
        org.mockito.Mockito.verify(scheduler).startDelayed(180);
        org.mockito.Mockito.verify(scheduler).shutdown();
        junit.framework.Assert.assertTrue(executionScheduler.isInitialized());
    }

    @org.junit.Test
    public void testGetQuartzDbDelegateClassAndValidationQuery() throws java.lang.Exception {
        java.util.Properties testProperties = new java.util.Properties();
        testProperties.setProperty(org.apache.ambari.server.configuration.Configuration.SERVER_JDBC_URL.getKey(), "jdbc:postgresql://host:port/dbname");
        testProperties.setProperty(org.apache.ambari.server.configuration.Configuration.SERVER_DB_NAME.getKey(), "ambari");
        org.apache.ambari.server.configuration.Configuration configuration1 = new org.apache.ambari.server.configuration.Configuration(testProperties);
        org.apache.ambari.server.scheduler.ExecutionSchedulerImpl executionScheduler = new org.apache.ambari.server.scheduler.ExecutionSchedulerImpl(configuration1);
        java.lang.String[] subProps = executionScheduler.getQuartzDbDelegateClassAndValidationQuery();
        junit.framework.Assert.assertEquals("org.quartz.impl.jdbcjobstore.PostgreSQLDelegate", subProps[0]);
        junit.framework.Assert.assertEquals("select 0", subProps[1]);
        testProperties.setProperty(org.apache.ambari.server.configuration.Configuration.SERVER_JDBC_URL.getKey(), "jdbc:mysql://host:port/dbname");
        configuration1 = new org.apache.ambari.server.configuration.Configuration(testProperties);
        executionScheduler = new org.apache.ambari.server.scheduler.ExecutionSchedulerImpl(configuration1);
        subProps = executionScheduler.getQuartzDbDelegateClassAndValidationQuery();
        junit.framework.Assert.assertEquals("org.quartz.impl.jdbcjobstore.StdJDBCDelegate", subProps[0]);
        junit.framework.Assert.assertEquals("select 0", subProps[1]);
        testProperties.setProperty(org.apache.ambari.server.configuration.Configuration.SERVER_JDBC_URL.getKey(), "jdbc:oracle:thin://host:port/dbname");
        configuration1 = new org.apache.ambari.server.configuration.Configuration(testProperties);
        executionScheduler = new org.apache.ambari.server.scheduler.ExecutionSchedulerImpl(configuration1);
        subProps = executionScheduler.getQuartzDbDelegateClassAndValidationQuery();
        junit.framework.Assert.assertEquals("org.quartz.impl.jdbcjobstore.oracle.OracleDelegate", subProps[0]);
        junit.framework.Assert.assertEquals("select 0 from dual", subProps[1]);
    }

    @org.junit.Test
    public void testSchedulerStartDelay() throws java.lang.Exception {
        org.mockito.Mockito.when(scheduler.isStarted()).thenReturn(false);
        org.apache.ambari.server.scheduler.ExecutionSchedulerTest.TestExecutionScheduler executionScheduler = new org.apache.ambari.server.scheduler.ExecutionSchedulerTest.TestExecutionScheduler(configuration);
        executionScheduler.startScheduler(180);
        executionScheduler.startScheduler(null);
        org.mockito.Mockito.verify(scheduler).startDelayed(180);
        org.mockito.Mockito.verify(scheduler).start();
        org.mockito.Mockito.verify(scheduler, org.mockito.Mockito.atLeastOnce()).isStarted();
        junit.framework.Assert.assertTrue(executionScheduler.isInitialized());
    }
}
