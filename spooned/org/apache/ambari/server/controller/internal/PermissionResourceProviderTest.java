package org.apache.ambari.server.controller.internal;
import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;
public class PermissionResourceProviderTest {
    private static final org.apache.ambari.server.orm.dao.PermissionDAO dao = org.easymock.EasyMock.createStrictMock(org.apache.ambari.server.orm.dao.PermissionDAO.class);

    @org.junit.BeforeClass
    public static void initClass() {
        org.apache.ambari.server.controller.internal.PermissionResourceProvider.init(org.apache.ambari.server.controller.internal.PermissionResourceProviderTest.dao);
    }

    @org.junit.Before
    public void resetGlobalMocks() {
        org.easymock.EasyMock.reset(org.apache.ambari.server.controller.internal.PermissionResourceProviderTest.dao);
    }

    @org.junit.Test(expected = java.lang.UnsupportedOperationException.class)
    public void testCreateResources() throws java.lang.Exception {
        org.apache.ambari.server.controller.internal.PermissionResourceProvider provider = new org.apache.ambari.server.controller.internal.PermissionResourceProvider();
        org.apache.ambari.server.controller.spi.Request request = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.Request.class);
        provider.createResources(request);
    }

    @org.junit.Test
    public void testGetResources() throws java.lang.Exception {
        java.util.List<org.apache.ambari.server.orm.entities.PermissionEntity> permissionEntities = new java.util.LinkedList<>();
        org.apache.ambari.server.orm.entities.PermissionEntity permissionEntity = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.orm.entities.PermissionEntity.class);
        org.apache.ambari.server.orm.entities.ResourceTypeEntity resourceTypeEntity = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.orm.entities.ResourceTypeEntity.class);
        permissionEntities.add(permissionEntity);
        org.easymock.EasyMock.expect(org.apache.ambari.server.controller.internal.PermissionResourceProviderTest.dao.findAll()).andReturn(permissionEntities);
        org.easymock.EasyMock.expect(permissionEntity.getId()).andReturn(99);
        org.easymock.EasyMock.expect(permissionEntity.getPermissionName()).andReturn("AMBARI.ADMINISTRATOR");
        org.easymock.EasyMock.expect(permissionEntity.getPermissionLabel()).andReturn("Ambari Administrator");
        org.easymock.EasyMock.expect(permissionEntity.getSortOrder()).andReturn(1);
        org.easymock.EasyMock.expect(permissionEntity.getResourceType()).andReturn(resourceTypeEntity);
        org.easymock.EasyMock.expect(resourceTypeEntity.getName()).andReturn("AMBARI");
        org.easymock.EasyMock.replay(org.apache.ambari.server.controller.internal.PermissionResourceProviderTest.dao, permissionEntity, resourceTypeEntity);
        org.apache.ambari.server.controller.internal.PermissionResourceProvider provider = new org.apache.ambari.server.controller.internal.PermissionResourceProvider();
        java.util.Set<org.apache.ambari.server.controller.spi.Resource> resources = provider.getResources(org.apache.ambari.server.controller.utilities.PropertyHelper.getReadRequest(), null);
        org.junit.Assert.assertEquals(1, resources.size());
        org.apache.ambari.server.controller.spi.Resource resource = resources.iterator().next();
        org.junit.Assert.assertEquals(99, resource.getPropertyValue(org.apache.ambari.server.controller.internal.PermissionResourceProvider.PERMISSION_ID_PROPERTY_ID));
        org.junit.Assert.assertEquals("AMBARI.ADMINISTRATOR", resource.getPropertyValue(org.apache.ambari.server.controller.internal.PermissionResourceProvider.PERMISSION_NAME_PROPERTY_ID));
        org.junit.Assert.assertEquals("Ambari Administrator", resource.getPropertyValue(org.apache.ambari.server.controller.internal.PermissionResourceProvider.PERMISSION_LABEL_PROPERTY_ID));
        org.junit.Assert.assertEquals("AMBARI", resource.getPropertyValue(org.apache.ambari.server.controller.internal.PermissionResourceProvider.RESOURCE_NAME_PROPERTY_ID));
        org.junit.Assert.assertEquals(1, resource.getPropertyValue(org.apache.ambari.server.controller.internal.PermissionResourceProvider.SORT_ORDER_PROPERTY_ID));
        org.easymock.EasyMock.verify(org.apache.ambari.server.controller.internal.PermissionResourceProviderTest.dao, permissionEntity, resourceTypeEntity);
    }

    @org.junit.Test(expected = java.lang.UnsupportedOperationException.class)
    public void testUpdateResources() throws java.lang.Exception {
        org.apache.ambari.server.controller.internal.PermissionResourceProvider provider = new org.apache.ambari.server.controller.internal.PermissionResourceProvider();
        org.apache.ambari.server.controller.spi.Request request = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.spi.Request.class);
        provider.updateResources(request, null);
    }

    @org.junit.Test(expected = java.lang.UnsupportedOperationException.class)
    public void testDeleteResources() throws java.lang.Exception {
        org.apache.ambari.server.controller.internal.PermissionResourceProvider provider = new org.apache.ambari.server.controller.internal.PermissionResourceProvider();
        provider.deleteResources(new org.apache.ambari.server.controller.internal.RequestImpl(null, null, null, null), null);
    }
}
