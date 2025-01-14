package org.apache.ambari.server.controller.internal;
import org.easymock.Capture;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.newCapture;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
public class RemoteClusterResourceProviderTest {
    @org.junit.After
    public void clearAuthentication() {
        org.springframework.security.core.context.SecurityContextHolder.getContext().setAuthentication(null);
    }

    @org.junit.Test
    public void testToResource() throws java.lang.Exception {
        org.apache.ambari.server.controller.internal.RemoteClusterResourceProvider provider = new org.apache.ambari.server.controller.internal.RemoteClusterResourceProvider();
        java.util.Set<java.lang.String> propertyIds = new java.util.HashSet<>();
        propertyIds.add(org.apache.ambari.server.controller.internal.RemoteClusterResourceProvider.CLUSTER_NAME_PROPERTY_ID);
        propertyIds.add(org.apache.ambari.server.controller.internal.RemoteClusterResourceProvider.CLUSTER_URL_PROPERTY_ID);
        propertyIds.add(org.apache.ambari.server.controller.internal.RemoteClusterResourceProvider.USERNAME_PROPERTY_ID);
        propertyIds.add(org.apache.ambari.server.controller.internal.RemoteClusterResourceProvider.PASSWORD_PROPERTY_ID);
        propertyIds.add(org.apache.ambari.server.controller.internal.RemoteClusterResourceProvider.SERVICES_PROPERTY_ID);
        org.apache.ambari.server.orm.entities.RemoteAmbariClusterServiceEntity service1 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.orm.entities.RemoteAmbariClusterServiceEntity.class);
        org.easymock.EasyMock.expect(service1.getServiceName()).andReturn("service1").once();
        org.apache.ambari.server.orm.entities.RemoteAmbariClusterServiceEntity service2 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.orm.entities.RemoteAmbariClusterServiceEntity.class);
        org.easymock.EasyMock.expect(service2.getServiceName()).andReturn("service2").once();
        java.util.List<org.apache.ambari.server.orm.entities.RemoteAmbariClusterServiceEntity> serviceList = new java.util.ArrayList<>();
        serviceList.add(service1);
        serviceList.add(service2);
        org.apache.ambari.server.orm.entities.RemoteAmbariClusterEntity entity = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.orm.entities.RemoteAmbariClusterEntity.class);
        org.easymock.EasyMock.expect(entity.getName()).andReturn("test").once();
        org.easymock.EasyMock.expect(entity.getUrl()).andReturn("url").once();
        org.easymock.EasyMock.expect(entity.getUsername()).andReturn("user").once();
        org.easymock.EasyMock.expect(entity.getServices()).andReturn(serviceList).once();
        org.easymock.EasyMock.replay(service1, service2, entity);
        java.util.List<java.lang.String> services = new java.util.ArrayList<>();
        services.add("service1");
        services.add("service2");
        org.apache.ambari.server.controller.spi.Resource resource = provider.toResource(propertyIds, entity);
        org.junit.Assert.assertEquals(resource.getPropertyValue(org.apache.ambari.server.controller.internal.RemoteClusterResourceProvider.CLUSTER_NAME_PROPERTY_ID), "test");
        org.junit.Assert.assertEquals(resource.getPropertyValue(org.apache.ambari.server.controller.internal.RemoteClusterResourceProvider.CLUSTER_URL_PROPERTY_ID), "url");
        org.junit.Assert.assertEquals(resource.getPropertyValue(org.apache.ambari.server.controller.internal.RemoteClusterResourceProvider.USERNAME_PROPERTY_ID), "user");
        org.junit.Assert.assertEquals(resource.getPropertyValue(org.apache.ambari.server.controller.internal.RemoteClusterResourceProvider.SERVICES_PROPERTY_ID), services);
        org.easymock.EasyMock.verify(service1, service2, entity);
    }

    @org.junit.Test
    public void testCreateResourcesAsAdministrator() throws java.lang.Exception {
        testCreateResources(org.apache.ambari.server.security.TestAuthenticationFactory.createAdministrator());
    }

    static void setField(java.lang.reflect.Field field, java.lang.Object newValue) throws java.lang.Exception {
        field.setAccessible(true);
        field.set(null, newValue);
    }

    private void testCreateResources(org.springframework.security.core.Authentication authentication) throws java.lang.Exception {
        org.apache.ambari.server.controller.internal.RemoteClusterResourceProvider provider = new org.apache.ambari.server.controller.internal.RemoteClusterResourceProvider();
        org.apache.ambari.server.orm.dao.RemoteAmbariClusterDAO clusterDAO = org.easymock.EasyMock.createMock(org.apache.ambari.server.orm.dao.RemoteAmbariClusterDAO.class);
        org.apache.ambari.server.controller.internal.RemoteClusterResourceProviderTest.setField(org.apache.ambari.server.controller.internal.RemoteClusterResourceProvider.class.getDeclaredField("remoteAmbariClusterDAO"), clusterDAO);
        org.apache.ambari.server.view.RemoteAmbariClusterRegistry clusterRegistry = org.easymock.EasyMock.createMock(org.apache.ambari.server.view.RemoteAmbariClusterRegistry.class);
        org.apache.ambari.server.controller.internal.RemoteClusterResourceProviderTest.setField(org.apache.ambari.server.controller.internal.RemoteClusterResourceProvider.class.getDeclaredField("remoteAmbariClusterRegistry"), clusterRegistry);
        java.util.Set<java.util.Map<java.lang.String, java.lang.Object>> properties = new java.util.HashSet<>();
        java.util.Map<java.lang.String, java.lang.Object> propertyMap = new java.util.HashMap<>();
        propertyMap.put(org.apache.ambari.server.controller.internal.RemoteClusterResourceProvider.CLUSTER_NAME_PROPERTY_ID, "test");
        propertyMap.put(org.apache.ambari.server.controller.internal.RemoteClusterResourceProvider.CLUSTER_URL_PROPERTY_ID, "url");
        propertyMap.put(org.apache.ambari.server.controller.internal.RemoteClusterResourceProvider.USERNAME_PROPERTY_ID, "username");
        propertyMap.put(org.apache.ambari.server.controller.internal.RemoteClusterResourceProvider.PASSWORD_PROPERTY_ID, "password");
        org.apache.ambari.server.orm.entities.RemoteAmbariClusterServiceEntity service1 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.orm.entities.RemoteAmbariClusterServiceEntity.class);
        org.easymock.EasyMock.expect(service1.getServiceName()).andReturn("service1").once();
        org.apache.ambari.server.orm.entities.RemoteAmbariClusterServiceEntity service2 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.orm.entities.RemoteAmbariClusterServiceEntity.class);
        org.easymock.EasyMock.expect(service2.getServiceName()).andReturn("service2").once();
        java.util.List<org.apache.ambari.server.orm.entities.RemoteAmbariClusterServiceEntity> serviceList = new java.util.ArrayList<>();
        serviceList.add(service1);
        serviceList.add(service2);
        org.easymock.EasyMock.expect(clusterDAO.findByName("test")).andReturn(null).anyTimes();
        org.easymock.Capture<org.apache.ambari.server.orm.entities.RemoteAmbariClusterEntity> clusterEntityCapture = org.easymock.EasyMock.newCapture();
        clusterRegistry.saveOrUpdate(org.easymock.EasyMock.capture(clusterEntityCapture), org.easymock.EasyMock.eq(false));
        org.easymock.EasyMock.replay(clusterRegistry, clusterDAO, service1, service2);
        properties.add(propertyMap);
        org.springframework.security.core.context.SecurityContextHolder.getContext().setAuthentication(authentication);
        provider.createResources(org.apache.ambari.server.controller.utilities.PropertyHelper.getCreateRequest(properties, null));
        org.apache.ambari.server.orm.entities.RemoteAmbariClusterEntity clusterEntity = clusterEntityCapture.getValue();
        org.junit.Assert.assertEquals(clusterEntity.getName(), "test");
        org.junit.Assert.assertEquals(clusterEntity.getPassword(), "password");
        org.junit.Assert.assertEquals(clusterEntity.getUrl(), "url");
        org.junit.Assert.assertEquals(clusterEntity.getUsername(), "username");
    }

    @org.junit.Test
    public void testDeleteResources() throws java.lang.Exception {
        org.apache.ambari.server.controller.internal.RemoteClusterResourceProvider provider = new org.apache.ambari.server.controller.internal.RemoteClusterResourceProvider();
        org.apache.ambari.server.orm.dao.RemoteAmbariClusterDAO clusterDAO = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.orm.dao.RemoteAmbariClusterDAO.class);
        org.apache.ambari.server.orm.entities.RemoteAmbariClusterEntity clusterEntity = new org.apache.ambari.server.orm.entities.RemoteAmbariClusterEntity();
        org.apache.ambari.server.controller.internal.RemoteClusterResourceProviderTest.setField(org.apache.ambari.server.controller.internal.RemoteClusterResourceProvider.class.getDeclaredField("remoteAmbariClusterDAO"), clusterDAO);
        org.apache.ambari.server.controller.predicate.EqualsPredicate equalsPredicate = new org.apache.ambari.server.controller.predicate.EqualsPredicate<>(org.apache.ambari.server.controller.internal.RemoteClusterResourceProvider.CLUSTER_NAME_PROPERTY_ID, "test");
        org.apache.ambari.server.view.RemoteAmbariClusterRegistry clusterRegistry = org.easymock.EasyMock.createMock(org.apache.ambari.server.view.RemoteAmbariClusterRegistry.class);
        org.apache.ambari.server.controller.internal.RemoteClusterResourceProviderTest.setField(org.apache.ambari.server.controller.internal.RemoteClusterResourceProvider.class.getDeclaredField("remoteAmbariClusterRegistry"), clusterRegistry);
        java.util.Set<java.util.Map<java.lang.String, java.lang.Object>> properties = new java.util.HashSet<>();
        java.util.Map<java.lang.String, java.lang.Object> propertyMap = new java.util.HashMap<>();
        propertyMap.put(org.apache.ambari.server.controller.internal.RemoteClusterResourceProvider.CLUSTER_NAME_PROPERTY_ID, "test");
        org.easymock.EasyMock.expect(clusterDAO.findByName("test")).andReturn(clusterEntity);
        clusterRegistry.delete(clusterEntity);
        org.easymock.EasyMock.replay(clusterDAO);
        properties.add(propertyMap);
        org.springframework.security.core.context.SecurityContextHolder.getContext().setAuthentication(org.apache.ambari.server.security.TestAuthenticationFactory.createAdministrator());
        provider.deleteResources(org.apache.ambari.server.controller.utilities.PropertyHelper.getCreateRequest(properties, null), equalsPredicate);
    }
}
