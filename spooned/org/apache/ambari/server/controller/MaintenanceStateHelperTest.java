package org.apache.ambari.server.controller;
import org.easymock.EasyMock;
import static org.easymock.EasyMock.anyInt;
import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.createMockBuilder;
import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
public class MaintenanceStateHelperTest {
    @org.junit.Test
    public void testisOperationAllowed() throws java.lang.Exception {
        com.google.inject.Injector injector = org.easymock.EasyMock.createStrictMock(com.google.inject.Injector.class);
        org.apache.ambari.server.state.Cluster cluster = org.easymock.EasyMock.createMock(org.apache.ambari.server.state.Cluster.class);
        java.lang.reflect.Method isOperationAllowed = org.apache.ambari.server.controller.MaintenanceStateHelper.class.getDeclaredMethod("isOperationAllowed", new java.lang.Class[]{ org.apache.ambari.server.state.Cluster.class, org.apache.ambari.server.controller.spi.Resource.Type.class, java.lang.String.class, java.lang.String.class, java.lang.String.class });
        org.apache.ambari.server.controller.MaintenanceStateHelper maintenanceStateHelper = org.easymock.EasyMock.createMockBuilder(org.apache.ambari.server.controller.MaintenanceStateHelper.class).withConstructor(injector).addMockedMethod(isOperationAllowed).createNiceMock();
        org.apache.ambari.server.controller.internal.RequestResourceFilter filter = org.easymock.EasyMock.createMock(org.apache.ambari.server.controller.internal.RequestResourceFilter.class);
        org.apache.ambari.server.controller.internal.RequestOperationLevel level = org.easymock.EasyMock.createMock(org.apache.ambari.server.controller.internal.RequestOperationLevel.class);
        org.easymock.EasyMock.expect(level.getLevel()).andReturn(org.apache.ambari.server.controller.spi.Resource.Type.Cluster);
        org.easymock.EasyMock.expect(maintenanceStateHelper.isOperationAllowed(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.Cluster.class), org.easymock.EasyMock.anyObject(org.apache.ambari.server.controller.spi.Resource.Type.class), org.easymock.EasyMock.anyObject(java.lang.String.class), org.easymock.EasyMock.anyObject(java.lang.String.class), org.easymock.EasyMock.anyObject(java.lang.String.class))).andStubReturn(true);
        org.easymock.EasyMock.replay(cluster, maintenanceStateHelper, level);
        maintenanceStateHelper.isOperationAllowed(cluster, level, filter, "service", "component", "hostname");
        org.easymock.EasyMock.verify(maintenanceStateHelper, level);
        maintenanceStateHelper = org.easymock.EasyMock.createMockBuilder(org.apache.ambari.server.controller.MaintenanceStateHelper.class).withConstructor(injector).addMockedMethod(isOperationAllowed).addMockedMethod("guessOperationLevel").createNiceMock();
        org.easymock.EasyMock.expect(maintenanceStateHelper.guessOperationLevel(org.easymock.EasyMock.anyObject(org.apache.ambari.server.controller.internal.RequestResourceFilter.class))).andReturn(org.apache.ambari.server.controller.spi.Resource.Type.Cluster);
        org.easymock.EasyMock.expect(maintenanceStateHelper.isOperationAllowed(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.Cluster.class), org.easymock.EasyMock.anyObject(org.apache.ambari.server.controller.spi.Resource.Type.class), org.easymock.EasyMock.anyObject(java.lang.String.class), org.easymock.EasyMock.anyObject(java.lang.String.class), org.easymock.EasyMock.anyObject(java.lang.String.class))).andStubReturn(true);
        org.easymock.EasyMock.replay(maintenanceStateHelper);
        maintenanceStateHelper.isOperationAllowed(cluster, null, filter, "service", "component", "hostname");
        org.easymock.EasyMock.verify(maintenanceStateHelper);
    }

    @org.junit.Test
    public void testHostComponentImpliedState() throws java.lang.Exception {
        com.google.inject.Injector injector = org.easymock.EasyMock.createStrictMock(com.google.inject.Injector.class);
        org.apache.ambari.server.controller.MaintenanceStateHelper maintenanceStateHelper = org.easymock.EasyMock.createMockBuilder(org.apache.ambari.server.controller.MaintenanceStateHelper.class).withConstructor(injector).createNiceMock();
        org.apache.ambari.server.state.Clusters clusters = org.easymock.EasyMock.createMock(org.apache.ambari.server.state.Clusters.class);
        org.apache.ambari.server.state.Cluster cluster = org.easymock.EasyMock.createMock(org.apache.ambari.server.state.Cluster.class);
        org.apache.ambari.server.state.ServiceComponentHost sch = org.easymock.EasyMock.createMock(org.apache.ambari.server.state.ServiceComponentHost.class);
        org.apache.ambari.server.state.Service service = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Service.class);
        final org.apache.ambari.server.state.Host host = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Host.class);
        org.easymock.EasyMock.expect(sch.getClusterName()).andReturn("c1").anyTimes();
        org.easymock.EasyMock.expect(clusters.getCluster("c1")).andReturn(cluster).anyTimes();
        org.easymock.EasyMock.expect(cluster.getClusterName()).andReturn("c1").anyTimes();
        org.easymock.EasyMock.expect(cluster.getClusterId()).andReturn(1L).anyTimes();
        org.easymock.EasyMock.expect(clusters.getHost("h1")).andReturn(host).anyTimes();
        org.easymock.EasyMock.expect(sch.getHostName()).andReturn("h1").anyTimes();
        org.easymock.EasyMock.expect(sch.getServiceName()).andReturn("HDFS").anyTimes();
        org.easymock.EasyMock.expect(cluster.getService("HDFS")).andReturn(service).anyTimes();
        org.easymock.EasyMock.expect(sch.getMaintenanceState()).andReturn(org.apache.ambari.server.state.MaintenanceState.ON).times(1).andReturn(org.apache.ambari.server.state.MaintenanceState.OFF).anyTimes();
        org.easymock.EasyMock.expect(service.getMaintenanceState()).andReturn(org.apache.ambari.server.state.MaintenanceState.ON);
        org.easymock.EasyMock.expect(host.getMaintenanceState(1L)).andReturn(org.apache.ambari.server.state.MaintenanceState.ON);
        org.easymock.EasyMock.expect(service.getMaintenanceState()).andReturn(org.apache.ambari.server.state.MaintenanceState.ON);
        org.easymock.EasyMock.expect(host.getMaintenanceState(1L)).andReturn(org.apache.ambari.server.state.MaintenanceState.OFF);
        org.easymock.EasyMock.expect(service.getMaintenanceState()).andReturn(org.apache.ambari.server.state.MaintenanceState.OFF);
        org.easymock.EasyMock.expect(host.getMaintenanceState(1L)).andReturn(org.apache.ambari.server.state.MaintenanceState.ON);
        org.apache.ambari.server.controller.MaintenanceStateHelperTest.injectField(maintenanceStateHelper, clusters);
        org.easymock.EasyMock.replay(maintenanceStateHelper, clusters, cluster, sch, host, service);
        org.apache.ambari.server.state.MaintenanceState state = maintenanceStateHelper.getEffectiveState(sch);
        org.junit.Assert.assertEquals(org.apache.ambari.server.state.MaintenanceState.ON, state);
        state = maintenanceStateHelper.getEffectiveState(sch);
        org.junit.Assert.assertEquals(org.apache.ambari.server.state.MaintenanceState.IMPLIED_FROM_SERVICE_AND_HOST, state);
        state = maintenanceStateHelper.getEffectiveState(sch);
        org.junit.Assert.assertEquals(org.apache.ambari.server.state.MaintenanceState.IMPLIED_FROM_SERVICE, state);
        state = maintenanceStateHelper.getEffectiveState(sch);
        org.junit.Assert.assertEquals(org.apache.ambari.server.state.MaintenanceState.IMPLIED_FROM_HOST, state);
        org.easymock.EasyMock.verify(maintenanceStateHelper, clusters, cluster, sch, host, service);
    }

    @org.junit.Test
    public void testGetEffectiveStateForHostAlert() throws java.lang.Exception {
        com.google.inject.Injector injector = org.easymock.EasyMock.createStrictMock(com.google.inject.Injector.class);
        org.apache.ambari.server.controller.MaintenanceStateHelper maintenanceStateHelper = org.easymock.EasyMock.createMockBuilder(org.apache.ambari.server.controller.MaintenanceStateHelper.class).withConstructor(injector).createNiceMock();
        org.apache.ambari.server.state.Clusters clusters = org.easymock.EasyMock.createMock(org.apache.ambari.server.state.Clusters.class);
        final org.apache.ambari.server.state.Host host = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Host.class);
        long clusterId = 1L;
        java.lang.String hostName = "c6401.ambari.apache.org";
        org.apache.ambari.server.state.Alert alert = new org.apache.ambari.server.state.Alert("foo-alert", null, "HDFS", "DATANODE", hostName, org.apache.ambari.server.state.AlertState.CRITICAL);
        org.easymock.EasyMock.expect(host.getMaintenanceState(clusterId)).andReturn(org.apache.ambari.server.state.MaintenanceState.ON).once();
        org.easymock.EasyMock.expect(clusters.getHost(hostName)).andReturn(host).once();
        org.apache.ambari.server.controller.MaintenanceStateHelperTest.injectField(maintenanceStateHelper, clusters);
        org.easymock.EasyMock.replay(maintenanceStateHelper, clusters, host);
        org.apache.ambari.server.state.MaintenanceState state = maintenanceStateHelper.getEffectiveState(clusterId, alert);
        org.junit.Assert.assertEquals(org.apache.ambari.server.state.MaintenanceState.ON, state);
        org.easymock.EasyMock.verify(maintenanceStateHelper, clusters, host);
    }

    @org.junit.Test
    public void testGetEffectiveStateForServiceAlert() throws java.lang.Exception {
        com.google.inject.Injector injector = org.easymock.EasyMock.createStrictMock(com.google.inject.Injector.class);
        org.apache.ambari.server.controller.MaintenanceStateHelper maintenanceStateHelper = org.easymock.EasyMock.createMockBuilder(org.apache.ambari.server.controller.MaintenanceStateHelper.class).withConstructor(injector).createNiceMock();
        org.apache.ambari.server.state.Clusters clusters = org.easymock.EasyMock.createMock(org.apache.ambari.server.state.Clusters.class);
        org.apache.ambari.server.state.Cluster cluster = org.easymock.EasyMock.createMock(org.apache.ambari.server.state.Cluster.class);
        org.apache.ambari.server.state.Service service = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Service.class);
        final org.apache.ambari.server.state.Host host = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Host.class);
        long clusterId = 1L;
        java.lang.String hostName = "c6401.ambari.apache.org";
        org.apache.ambari.server.state.Alert alert = new org.apache.ambari.server.state.Alert("foo-alert", null, "HDFS", null, hostName, org.apache.ambari.server.state.AlertState.CRITICAL);
        org.easymock.EasyMock.expect(host.getMaintenanceState(clusterId)).andReturn(org.apache.ambari.server.state.MaintenanceState.OFF).once();
        org.easymock.EasyMock.expect(clusters.getHost(hostName)).andReturn(host).once();
        org.easymock.EasyMock.expect(clusters.getClusterById(clusterId)).andReturn(cluster).once();
        org.easymock.EasyMock.expect(cluster.getService("HDFS")).andReturn(service).once();
        org.easymock.EasyMock.expect(service.getMaintenanceState()).andReturn(org.apache.ambari.server.state.MaintenanceState.ON);
        org.apache.ambari.server.controller.MaintenanceStateHelperTest.injectField(maintenanceStateHelper, clusters);
        org.easymock.EasyMock.replay(maintenanceStateHelper, clusters, host, cluster, service);
        org.apache.ambari.server.state.MaintenanceState state = maintenanceStateHelper.getEffectiveState(clusterId, alert);
        org.junit.Assert.assertEquals(org.apache.ambari.server.state.MaintenanceState.ON, state);
        org.easymock.EasyMock.verify(maintenanceStateHelper, clusters, host, cluster, service);
    }

    @org.junit.Test
    public void testGetEffectiveStateForServiceOnlyAlert() throws java.lang.Exception {
        com.google.inject.Injector injector = org.easymock.EasyMock.createStrictMock(com.google.inject.Injector.class);
        org.apache.ambari.server.controller.MaintenanceStateHelper maintenanceStateHelper = org.easymock.EasyMock.createMockBuilder(org.apache.ambari.server.controller.MaintenanceStateHelper.class).withConstructor(injector).createNiceMock();
        org.apache.ambari.server.state.Clusters clusters = org.easymock.EasyMock.createMock(org.apache.ambari.server.state.Clusters.class);
        org.apache.ambari.server.state.Cluster cluster = org.easymock.EasyMock.createMock(org.apache.ambari.server.state.Cluster.class);
        org.apache.ambari.server.state.Service service = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Service.class);
        long clusterId = 1L;
        org.apache.ambari.server.state.Alert alert = new org.apache.ambari.server.state.Alert("foo-alert", null, "HDFS", null, null, org.apache.ambari.server.state.AlertState.CRITICAL);
        org.easymock.EasyMock.expect(clusters.getClusterById(clusterId)).andReturn(cluster).once();
        org.easymock.EasyMock.expect(cluster.getService("HDFS")).andReturn(service).once();
        org.easymock.EasyMock.expect(service.getMaintenanceState()).andReturn(org.apache.ambari.server.state.MaintenanceState.ON);
        org.apache.ambari.server.controller.MaintenanceStateHelperTest.injectField(maintenanceStateHelper, clusters);
        org.easymock.EasyMock.replay(maintenanceStateHelper, clusters, cluster, service);
        org.apache.ambari.server.state.MaintenanceState state = maintenanceStateHelper.getEffectiveState(clusterId, alert);
        org.junit.Assert.assertEquals(org.apache.ambari.server.state.MaintenanceState.ON, state);
        org.easymock.EasyMock.verify(maintenanceStateHelper, clusters, cluster, service);
    }

    @org.junit.Test
    public void testGetEffectiveStateForComponentAlert() throws java.lang.Exception {
        com.google.inject.Injector injector = org.easymock.EasyMock.createStrictMock(com.google.inject.Injector.class);
        org.apache.ambari.server.controller.MaintenanceStateHelper maintenanceStateHelper = org.easymock.EasyMock.createMockBuilder(org.apache.ambari.server.controller.MaintenanceStateHelper.class).withConstructor(injector).createNiceMock();
        org.apache.ambari.server.state.Clusters clusters = org.easymock.EasyMock.createMock(org.apache.ambari.server.state.Clusters.class);
        org.apache.ambari.server.state.Cluster cluster = org.easymock.EasyMock.createMock(org.apache.ambari.server.state.Cluster.class);
        org.apache.ambari.server.state.Service service = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Service.class);
        org.apache.ambari.server.state.ServiceComponent serviceComponent = org.easymock.EasyMock.createMock(org.apache.ambari.server.state.ServiceComponent.class);
        org.apache.ambari.server.state.ServiceComponentHost sch = org.easymock.EasyMock.createMock(org.apache.ambari.server.state.ServiceComponentHost.class);
        final org.apache.ambari.server.state.Host host = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Host.class);
        long clusterId = 1L;
        java.lang.String hostName = "c6401.ambari.apache.org";
        org.apache.ambari.server.state.Alert alert = new org.apache.ambari.server.state.Alert("foo-alert", null, "HDFS", "DATANODE", hostName, org.apache.ambari.server.state.AlertState.CRITICAL);
        org.easymock.EasyMock.expect(host.getMaintenanceState(clusterId)).andReturn(org.apache.ambari.server.state.MaintenanceState.OFF).once();
        org.easymock.EasyMock.expect(clusters.getHost(hostName)).andReturn(host).once();
        org.easymock.EasyMock.expect(clusters.getClusterById(clusterId)).andReturn(cluster).once();
        org.easymock.EasyMock.expect(cluster.getService("HDFS")).andReturn(service).once();
        org.easymock.EasyMock.expect(service.getMaintenanceState()).andReturn(org.apache.ambari.server.state.MaintenanceState.OFF);
        org.easymock.EasyMock.expect(service.getServiceComponent("DATANODE")).andReturn(serviceComponent).once();
        org.easymock.EasyMock.expect(serviceComponent.getServiceComponentHost(hostName)).andReturn(sch).once();
        org.easymock.EasyMock.expect(sch.getMaintenanceState()).andReturn(org.apache.ambari.server.state.MaintenanceState.ON).once();
        org.apache.ambari.server.controller.MaintenanceStateHelperTest.injectField(maintenanceStateHelper, clusters);
        org.easymock.EasyMock.replay(maintenanceStateHelper, clusters, host, cluster, service, serviceComponent, sch);
        org.apache.ambari.server.state.MaintenanceState state = maintenanceStateHelper.getEffectiveState(clusterId, alert);
        org.junit.Assert.assertEquals(org.apache.ambari.server.state.MaintenanceState.ON, state);
        org.easymock.EasyMock.verify(maintenanceStateHelper, clusters, host, cluster, service, serviceComponent, sch);
    }

    @org.junit.Test
    public void testServiceOperationsAllowance() throws java.lang.Exception {
        com.google.inject.Injector injector = org.easymock.EasyMock.createStrictMock(com.google.inject.Injector.class);
        org.apache.ambari.server.controller.MaintenanceStateHelper maintenanceStateHelper = org.easymock.EasyMock.createMockBuilder(org.apache.ambari.server.controller.MaintenanceStateHelper.class).withConstructor(injector).createNiceMock();
        org.apache.ambari.server.state.Service service = org.easymock.EasyMock.createMock(org.apache.ambari.server.state.Service.class);
        org.easymock.EasyMock.expect(service.getMaintenanceState()).andReturn(org.apache.ambari.server.state.MaintenanceState.ON);
        org.easymock.EasyMock.expect(service.getMaintenanceState()).andReturn(org.apache.ambari.server.state.MaintenanceState.OFF);
        org.easymock.EasyMock.replay(maintenanceStateHelper, service);
        org.junit.Assert.assertEquals(false, maintenanceStateHelper.isOperationAllowed(org.apache.ambari.server.controller.spi.Resource.Type.Cluster, service));
        org.junit.Assert.assertEquals(true, maintenanceStateHelper.isOperationAllowed(org.apache.ambari.server.controller.spi.Resource.Type.Service, service));
        org.junit.Assert.assertEquals(true, maintenanceStateHelper.isOperationAllowed(org.apache.ambari.server.controller.spi.Resource.Type.Host, service));
        org.junit.Assert.assertEquals(true, maintenanceStateHelper.isOperationAllowed(org.apache.ambari.server.controller.spi.Resource.Type.HostComponent, service));
        org.junit.Assert.assertEquals(true, maintenanceStateHelper.isOperationAllowed(org.apache.ambari.server.controller.spi.Resource.Type.Cluster, service));
        org.junit.Assert.assertEquals(true, maintenanceStateHelper.isOperationAllowed(org.apache.ambari.server.controller.spi.Resource.Type.Service, service));
        org.junit.Assert.assertEquals(true, maintenanceStateHelper.isOperationAllowed(org.apache.ambari.server.controller.spi.Resource.Type.Host, service));
        org.junit.Assert.assertEquals(true, maintenanceStateHelper.isOperationAllowed(org.apache.ambari.server.controller.spi.Resource.Type.HostComponent, service));
        org.easymock.EasyMock.verify(maintenanceStateHelper, service);
    }

    @org.junit.Test
    public void testHostOperationsAllowance() throws java.lang.Exception {
        com.google.inject.Injector injector = org.easymock.EasyMock.createStrictMock(com.google.inject.Injector.class);
        org.apache.ambari.server.controller.MaintenanceStateHelper maintenanceStateHelper = org.easymock.EasyMock.createMockBuilder(org.apache.ambari.server.controller.MaintenanceStateHelper.class).withConstructor(injector).createNiceMock();
        org.apache.ambari.server.state.Host host = org.easymock.EasyMock.createMock(org.apache.ambari.server.state.Host.class);
        org.easymock.EasyMock.expect(host.getMaintenanceState(org.easymock.EasyMock.anyInt())).andReturn(org.apache.ambari.server.state.MaintenanceState.ON);
        org.easymock.EasyMock.expect(host.getMaintenanceState(org.easymock.EasyMock.anyInt())).andReturn(org.apache.ambari.server.state.MaintenanceState.OFF);
        org.easymock.EasyMock.replay(maintenanceStateHelper, host);
        org.junit.Assert.assertEquals(false, maintenanceStateHelper.isOperationAllowed(host, 1, org.apache.ambari.server.controller.spi.Resource.Type.Cluster));
        org.junit.Assert.assertEquals(true, maintenanceStateHelper.isOperationAllowed(host, 1, org.apache.ambari.server.controller.spi.Resource.Type.Service));
        org.junit.Assert.assertEquals(true, maintenanceStateHelper.isOperationAllowed(host, 1, org.apache.ambari.server.controller.spi.Resource.Type.Host));
        org.junit.Assert.assertEquals(true, maintenanceStateHelper.isOperationAllowed(host, 1, org.apache.ambari.server.controller.spi.Resource.Type.HostComponent));
        org.junit.Assert.assertEquals(true, maintenanceStateHelper.isOperationAllowed(host, 1, org.apache.ambari.server.controller.spi.Resource.Type.Cluster));
        org.junit.Assert.assertEquals(true, maintenanceStateHelper.isOperationAllowed(host, 1, org.apache.ambari.server.controller.spi.Resource.Type.Service));
        org.junit.Assert.assertEquals(true, maintenanceStateHelper.isOperationAllowed(host, 1, org.apache.ambari.server.controller.spi.Resource.Type.Host));
        org.junit.Assert.assertEquals(true, maintenanceStateHelper.isOperationAllowed(host, 1, org.apache.ambari.server.controller.spi.Resource.Type.HostComponent));
        org.easymock.EasyMock.verify(maintenanceStateHelper, host);
    }

    @org.junit.Test
    public void testHostComponentOperationsAllowance() throws java.lang.Exception {
        com.google.inject.Injector injector = org.easymock.EasyMock.createStrictMock(com.google.inject.Injector.class);
        java.lang.reflect.Method getEffectiveState = org.apache.ambari.server.controller.MaintenanceStateHelper.class.getMethod("getEffectiveState", new java.lang.Class[]{ org.apache.ambari.server.state.ServiceComponentHost.class });
        org.apache.ambari.server.controller.MaintenanceStateHelper maintenanceStateHelper = org.easymock.EasyMock.createMockBuilder(org.apache.ambari.server.controller.MaintenanceStateHelper.class).withConstructor(injector).addMockedMethod(getEffectiveState).createNiceMock();
        org.apache.ambari.server.state.ServiceComponentHost sch = org.easymock.EasyMock.createMock(org.apache.ambari.server.state.ServiceComponentHost.class);
        org.easymock.EasyMock.expect(maintenanceStateHelper.getEffectiveState(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.ServiceComponentHost.class))).andReturn(org.apache.ambari.server.state.MaintenanceState.ON);
        org.easymock.EasyMock.expect(maintenanceStateHelper.getEffectiveState(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.ServiceComponentHost.class))).andReturn(org.apache.ambari.server.state.MaintenanceState.IMPLIED_FROM_HOST);
        org.easymock.EasyMock.expect(maintenanceStateHelper.getEffectiveState(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.ServiceComponentHost.class))).andReturn(org.apache.ambari.server.state.MaintenanceState.IMPLIED_FROM_SERVICE);
        org.easymock.EasyMock.expect(maintenanceStateHelper.getEffectiveState(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.ServiceComponentHost.class))).andReturn(org.apache.ambari.server.state.MaintenanceState.IMPLIED_FROM_SERVICE_AND_HOST);
        org.easymock.EasyMock.expect(maintenanceStateHelper.getEffectiveState(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.ServiceComponentHost.class))).andReturn(org.apache.ambari.server.state.MaintenanceState.OFF);
        org.easymock.EasyMock.expect(maintenanceStateHelper.getEffectiveState(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.ServiceComponentHost.class))).andReturn(org.apache.ambari.server.state.MaintenanceState.ON);
        org.easymock.EasyMock.expect(maintenanceStateHelper.getEffectiveState(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.ServiceComponentHost.class))).andReturn(org.apache.ambari.server.state.MaintenanceState.IMPLIED_FROM_HOST);
        org.easymock.EasyMock.expect(maintenanceStateHelper.getEffectiveState(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.ServiceComponentHost.class))).andReturn(org.apache.ambari.server.state.MaintenanceState.IMPLIED_FROM_SERVICE);
        org.easymock.EasyMock.expect(maintenanceStateHelper.getEffectiveState(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.ServiceComponentHost.class))).andReturn(org.apache.ambari.server.state.MaintenanceState.IMPLIED_FROM_SERVICE_AND_HOST);
        org.easymock.EasyMock.expect(maintenanceStateHelper.getEffectiveState(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.ServiceComponentHost.class))).andReturn(org.apache.ambari.server.state.MaintenanceState.OFF);
        org.easymock.EasyMock.expect(maintenanceStateHelper.getEffectiveState(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.ServiceComponentHost.class))).andReturn(org.apache.ambari.server.state.MaintenanceState.ON);
        org.easymock.EasyMock.expect(maintenanceStateHelper.getEffectiveState(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.ServiceComponentHost.class))).andReturn(org.apache.ambari.server.state.MaintenanceState.IMPLIED_FROM_HOST);
        org.easymock.EasyMock.expect(maintenanceStateHelper.getEffectiveState(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.ServiceComponentHost.class))).andReturn(org.apache.ambari.server.state.MaintenanceState.IMPLIED_FROM_SERVICE);
        org.easymock.EasyMock.expect(maintenanceStateHelper.getEffectiveState(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.ServiceComponentHost.class))).andReturn(org.apache.ambari.server.state.MaintenanceState.IMPLIED_FROM_SERVICE_AND_HOST);
        org.easymock.EasyMock.expect(maintenanceStateHelper.getEffectiveState(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.ServiceComponentHost.class))).andReturn(org.apache.ambari.server.state.MaintenanceState.OFF);
        org.easymock.EasyMock.expect(maintenanceStateHelper.getEffectiveState(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.ServiceComponentHost.class))).andReturn(org.apache.ambari.server.state.MaintenanceState.ON);
        org.easymock.EasyMock.expect(maintenanceStateHelper.getEffectiveState(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.ServiceComponentHost.class))).andReturn(org.apache.ambari.server.state.MaintenanceState.IMPLIED_FROM_HOST);
        org.easymock.EasyMock.expect(maintenanceStateHelper.getEffectiveState(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.ServiceComponentHost.class))).andReturn(org.apache.ambari.server.state.MaintenanceState.IMPLIED_FROM_SERVICE);
        org.easymock.EasyMock.expect(maintenanceStateHelper.getEffectiveState(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.ServiceComponentHost.class))).andReturn(org.apache.ambari.server.state.MaintenanceState.IMPLIED_FROM_SERVICE_AND_HOST);
        org.easymock.EasyMock.expect(maintenanceStateHelper.getEffectiveState(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.ServiceComponentHost.class))).andReturn(org.apache.ambari.server.state.MaintenanceState.OFF);
        org.easymock.EasyMock.replay(maintenanceStateHelper, sch);
        org.junit.Assert.assertEquals(false, maintenanceStateHelper.isOperationAllowed(org.apache.ambari.server.controller.spi.Resource.Type.Cluster, sch));
        org.junit.Assert.assertEquals(false, maintenanceStateHelper.isOperationAllowed(org.apache.ambari.server.controller.spi.Resource.Type.Cluster, sch));
        org.junit.Assert.assertEquals(false, maintenanceStateHelper.isOperationAllowed(org.apache.ambari.server.controller.spi.Resource.Type.Cluster, sch));
        org.junit.Assert.assertEquals(false, maintenanceStateHelper.isOperationAllowed(org.apache.ambari.server.controller.spi.Resource.Type.Cluster, sch));
        org.junit.Assert.assertEquals(true, maintenanceStateHelper.isOperationAllowed(org.apache.ambari.server.controller.spi.Resource.Type.Cluster, sch));
        org.junit.Assert.assertEquals(false, maintenanceStateHelper.isOperationAllowed(org.apache.ambari.server.controller.spi.Resource.Type.Service, sch));
        org.junit.Assert.assertEquals(false, maintenanceStateHelper.isOperationAllowed(org.apache.ambari.server.controller.spi.Resource.Type.Service, sch));
        org.junit.Assert.assertEquals(true, maintenanceStateHelper.isOperationAllowed(org.apache.ambari.server.controller.spi.Resource.Type.Service, sch));
        org.junit.Assert.assertEquals(false, maintenanceStateHelper.isOperationAllowed(org.apache.ambari.server.controller.spi.Resource.Type.Service, sch));
        org.junit.Assert.assertEquals(true, maintenanceStateHelper.isOperationAllowed(org.apache.ambari.server.controller.spi.Resource.Type.Service, sch));
        org.junit.Assert.assertEquals(false, maintenanceStateHelper.isOperationAllowed(org.apache.ambari.server.controller.spi.Resource.Type.Host, sch));
        org.junit.Assert.assertEquals(true, maintenanceStateHelper.isOperationAllowed(org.apache.ambari.server.controller.spi.Resource.Type.Host, sch));
        org.junit.Assert.assertEquals(false, maintenanceStateHelper.isOperationAllowed(org.apache.ambari.server.controller.spi.Resource.Type.Host, sch));
        org.junit.Assert.assertEquals(false, maintenanceStateHelper.isOperationAllowed(org.apache.ambari.server.controller.spi.Resource.Type.Host, sch));
        org.junit.Assert.assertEquals(true, maintenanceStateHelper.isOperationAllowed(org.apache.ambari.server.controller.spi.Resource.Type.Host, sch));
        org.junit.Assert.assertEquals(true, maintenanceStateHelper.isOperationAllowed(org.apache.ambari.server.controller.spi.Resource.Type.HostComponent, sch));
        org.junit.Assert.assertEquals(true, maintenanceStateHelper.isOperationAllowed(org.apache.ambari.server.controller.spi.Resource.Type.HostComponent, sch));
        org.junit.Assert.assertEquals(true, maintenanceStateHelper.isOperationAllowed(org.apache.ambari.server.controller.spi.Resource.Type.HostComponent, sch));
        org.junit.Assert.assertEquals(true, maintenanceStateHelper.isOperationAllowed(org.apache.ambari.server.controller.spi.Resource.Type.HostComponent, sch));
        org.junit.Assert.assertEquals(true, maintenanceStateHelper.isOperationAllowed(org.apache.ambari.server.controller.spi.Resource.Type.HostComponent, sch));
        org.easymock.EasyMock.verify(maintenanceStateHelper, sch);
    }

    @org.junit.Test
    public void testGuessOperationLevel() {
        com.google.inject.Injector injector = org.easymock.EasyMock.createStrictMock(com.google.inject.Injector.class);
        org.apache.ambari.server.controller.MaintenanceStateHelper maintenanceStateHelper = org.easymock.EasyMock.createMockBuilder(org.apache.ambari.server.controller.MaintenanceStateHelper.class).withConstructor(injector).createNiceMock();
        org.easymock.EasyMock.replay(maintenanceStateHelper);
        org.junit.Assert.assertEquals(org.apache.ambari.server.controller.spi.Resource.Type.Cluster, maintenanceStateHelper.guessOperationLevel(null));
        org.apache.ambari.server.controller.internal.RequestResourceFilter resourceFilter = new org.apache.ambari.server.controller.internal.RequestResourceFilter(null, null, null);
        org.junit.Assert.assertEquals(org.apache.ambari.server.controller.spi.Resource.Type.Cluster, maintenanceStateHelper.guessOperationLevel(resourceFilter));
        resourceFilter = new org.apache.ambari.server.controller.internal.RequestResourceFilter("HDFS", null, null);
        org.junit.Assert.assertEquals(org.apache.ambari.server.controller.spi.Resource.Type.Service, maintenanceStateHelper.guessOperationLevel(resourceFilter));
        resourceFilter = new org.apache.ambari.server.controller.internal.RequestResourceFilter("HDFS", "NAMENODE", null);
        org.junit.Assert.assertEquals(org.apache.ambari.server.controller.spi.Resource.Type.Service, maintenanceStateHelper.guessOperationLevel(resourceFilter));
        java.util.ArrayList<java.lang.String> hosts = new java.util.ArrayList<>();
        hosts.add("host1");
        hosts.add("host2");
        resourceFilter = new org.apache.ambari.server.controller.internal.RequestResourceFilter("HDFS", null, hosts);
        org.junit.Assert.assertEquals(org.apache.ambari.server.controller.spi.Resource.Type.Cluster, maintenanceStateHelper.guessOperationLevel(resourceFilter));
        resourceFilter = new org.apache.ambari.server.controller.internal.RequestResourceFilter(null, null, hosts);
        org.junit.Assert.assertEquals(org.apache.ambari.server.controller.spi.Resource.Type.Host, maintenanceStateHelper.guessOperationLevel(resourceFilter));
        resourceFilter = new org.apache.ambari.server.controller.internal.RequestResourceFilter("HDFS", "NAMENODE", hosts);
        org.junit.Assert.assertEquals(org.apache.ambari.server.controller.spi.Resource.Type.HostComponent, maintenanceStateHelper.guessOperationLevel(resourceFilter));
    }

    @org.junit.Test
    public void testCutOffHosts() throws org.apache.ambari.server.AmbariException {
        org.apache.ambari.server.controller.MaintenanceStateHelper.HostPredicate predicate = org.easymock.EasyMock.createMock(org.apache.ambari.server.controller.MaintenanceStateHelper.HostPredicate.class);
        org.easymock.EasyMock.expect(predicate.shouldHostBeRemoved(org.easymock.EasyMock.eq("host1"))).andReturn(true);
        org.easymock.EasyMock.expect(predicate.shouldHostBeRemoved(org.easymock.EasyMock.eq("host2"))).andReturn(false);
        org.easymock.EasyMock.expect(predicate.shouldHostBeRemoved(org.easymock.EasyMock.eq("host3"))).andReturn(true);
        org.easymock.EasyMock.expect(predicate.shouldHostBeRemoved(org.easymock.EasyMock.eq("host4"))).andReturn(false);
        java.util.Set<java.lang.String> candidates = new java.util.HashSet<>();
        candidates.add("host1");
        candidates.add("host2");
        candidates.add("host3");
        candidates.add("host4");
        com.google.inject.Injector injector = org.easymock.EasyMock.createStrictMock(com.google.inject.Injector.class);
        org.apache.ambari.server.controller.MaintenanceStateHelper maintenanceStateHelper = org.easymock.EasyMock.createMockBuilder(org.apache.ambari.server.controller.MaintenanceStateHelper.class).withConstructor(injector).createNiceMock();
        org.easymock.EasyMock.replay(predicate, maintenanceStateHelper);
        java.util.Set<java.lang.String> ignored = maintenanceStateHelper.filterHostsInMaintenanceState(candidates, predicate);
        org.easymock.EasyMock.verify(predicate, maintenanceStateHelper);
        org.junit.Assert.assertEquals(candidates.size(), 2);
        org.junit.Assert.assertTrue(candidates.contains("host2"));
        org.junit.Assert.assertTrue(candidates.contains("host4"));
        org.junit.Assert.assertEquals(ignored.size(), 2);
        org.junit.Assert.assertTrue(ignored.contains("host1"));
        org.junit.Assert.assertTrue(ignored.contains("host3"));
    }

    private static void injectField(org.apache.ambari.server.controller.MaintenanceStateHelper maintenanceStateHelper, org.apache.ambari.server.state.Clusters clusters) throws java.lang.NoSuchFieldException, java.lang.IllegalAccessException {
        java.lang.Class<?> maintenanceHelperClass = org.apache.ambari.server.controller.MaintenanceStateHelper.class;
        java.lang.reflect.Field f = maintenanceHelperClass.getDeclaredField("clusters");
        f.setAccessible(true);
        f.set(maintenanceStateHelper, clusters);
    }

    public static org.apache.ambari.server.controller.MaintenanceStateHelper getMaintenanceStateHelperInstance(org.apache.ambari.server.state.Clusters clusters) throws java.lang.NoSuchFieldException, java.lang.IllegalAccessException {
        com.google.inject.Injector injector = org.easymock.EasyMock.createNiceMock(com.google.inject.Injector.class);
        injector.injectMembers(org.easymock.EasyMock.anyObject(org.apache.ambari.server.controller.MaintenanceStateHelper.class));
        org.easymock.EasyMock.expectLastCall().once();
        org.easymock.EasyMock.replay(injector);
        org.apache.ambari.server.controller.MaintenanceStateHelper maintenanceStateHelper = new org.apache.ambari.server.controller.MaintenanceStateHelper(injector);
        org.apache.ambari.server.controller.MaintenanceStateHelperTest.injectField(maintenanceStateHelper, clusters);
        return maintenanceStateHelper;
    }
}
