package org.apache.ambari.server.checks;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
public class VersionMismatchCheckTest {
    private static final java.lang.String CLUSTER_NAME = "cluster1";

    private static final java.lang.String FIRST_SERVICE_NAME = "service1";

    private static final java.lang.String FIRST_SERVICE_COMPONENT_NAME = "component1";

    private static final java.lang.String FIRST_SERVICE_COMPONENT_HOST_NAME = "host1";

    private org.apache.ambari.server.checks.VersionMismatchCheck versionMismatchCheck;

    private java.util.Map<java.lang.String, org.apache.ambari.server.state.ServiceComponentHost> firstServiceComponentHosts;

    @org.junit.Before
    public void setUp() throws java.lang.Exception {
        final org.apache.ambari.server.state.Clusters clusters = org.mockito.Mockito.mock(org.apache.ambari.server.state.Clusters.class);
        versionMismatchCheck = new org.apache.ambari.server.checks.VersionMismatchCheck();
        versionMismatchCheck.clustersProvider = new com.google.inject.Provider<org.apache.ambari.server.state.Clusters>() {
            @java.lang.Override
            public org.apache.ambari.server.state.Clusters get() {
                return clusters;
            }
        };
        org.apache.ambari.server.state.Cluster cluster = org.mockito.Mockito.mock(org.apache.ambari.server.state.Cluster.class);
        org.mockito.Mockito.when(clusters.getCluster(org.apache.ambari.server.checks.VersionMismatchCheckTest.CLUSTER_NAME)).thenReturn(cluster);
        org.apache.ambari.server.state.Service firstService = org.mockito.Mockito.mock(org.apache.ambari.server.state.Service.class);
        java.util.Map<java.lang.String, org.apache.ambari.server.state.Service> services = com.google.common.collect.ImmutableMap.of(org.apache.ambari.server.checks.VersionMismatchCheckTest.FIRST_SERVICE_NAME, firstService);
        org.mockito.Mockito.when(cluster.getServices()).thenReturn(services);
        org.apache.ambari.server.state.ServiceComponent firstServiceComponent = org.mockito.Mockito.mock(org.apache.ambari.server.state.ServiceComponent.class);
        java.util.Map<java.lang.String, org.apache.ambari.server.state.ServiceComponent> components = com.google.common.collect.ImmutableMap.of(org.apache.ambari.server.checks.VersionMismatchCheckTest.FIRST_SERVICE_COMPONENT_NAME, firstServiceComponent);
        org.mockito.Mockito.when(firstService.getServiceComponents()).thenReturn(components);
        org.apache.ambari.server.state.ServiceComponentHost firstServiceComponentHost = org.mockito.Mockito.mock(org.apache.ambari.server.state.ServiceComponentHost.class);
        firstServiceComponentHosts = com.google.common.collect.ImmutableMap.of(org.apache.ambari.server.checks.VersionMismatchCheckTest.FIRST_SERVICE_COMPONENT_HOST_NAME, firstServiceComponentHost);
        org.mockito.Mockito.when(firstServiceComponent.getServiceComponentHosts()).thenReturn(firstServiceComponentHosts);
    }

    @org.junit.Test
    public void testWarningWhenHostWithVersionMismatchExists() throws java.lang.Exception {
        org.mockito.Mockito.when(firstServiceComponentHosts.get(org.apache.ambari.server.checks.VersionMismatchCheckTest.FIRST_SERVICE_COMPONENT_HOST_NAME).getUpgradeState()).thenReturn(org.apache.ambari.server.state.UpgradeState.VERSION_MISMATCH);
        org.apache.ambari.spi.ClusterInformation clusterInformation = new org.apache.ambari.spi.ClusterInformation(org.apache.ambari.server.checks.VersionMismatchCheckTest.CLUSTER_NAME, false, null, null, null);
        org.apache.ambari.spi.upgrade.UpgradeCheckRequest request = new org.apache.ambari.spi.upgrade.UpgradeCheckRequest(clusterInformation, org.apache.ambari.spi.upgrade.UpgradeType.ROLLING, null, null, null);
        org.apache.ambari.spi.upgrade.UpgradeCheckResult check = versionMismatchCheck.perform(request);
        org.junit.Assert.assertEquals(org.apache.ambari.spi.upgrade.UpgradeCheckStatus.WARNING, check.getStatus());
    }

    @org.junit.Test
    public void testWarningWhenHostWithVersionMismatchDoesNotExist() throws java.lang.Exception {
        org.mockito.Mockito.when(firstServiceComponentHosts.get(org.apache.ambari.server.checks.VersionMismatchCheckTest.FIRST_SERVICE_COMPONENT_HOST_NAME).getUpgradeState()).thenReturn(org.apache.ambari.server.state.UpgradeState.IN_PROGRESS);
        org.apache.ambari.spi.ClusterInformation clusterInformation = new org.apache.ambari.spi.ClusterInformation(org.apache.ambari.server.checks.VersionMismatchCheckTest.CLUSTER_NAME, false, null, null, null);
        org.apache.ambari.spi.upgrade.UpgradeCheckRequest request = new org.apache.ambari.spi.upgrade.UpgradeCheckRequest(clusterInformation, org.apache.ambari.spi.upgrade.UpgradeType.ROLLING, null, null, null);
        org.apache.ambari.spi.upgrade.UpgradeCheckResult check = versionMismatchCheck.perform(request);
        org.junit.Assert.assertEquals(org.apache.ambari.spi.upgrade.UpgradeCheckStatus.PASS, check.getStatus());
    }
}
