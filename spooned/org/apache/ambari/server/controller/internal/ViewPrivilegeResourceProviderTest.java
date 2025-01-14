package org.apache.ambari.server.controller.internal;
import org.springframework.security.core.context.SecurityContextHolder;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;
public class ViewPrivilegeResourceProviderTest {
    private static final org.apache.ambari.server.orm.dao.PrivilegeDAO privilegeDAO = org.easymock.EasyMock.createStrictMock(org.apache.ambari.server.orm.dao.PrivilegeDAO.class);

    private static final org.apache.ambari.server.orm.dao.UserDAO userDAO = org.easymock.EasyMock.createStrictMock(org.apache.ambari.server.orm.dao.UserDAO.class);

    private static final org.apache.ambari.server.orm.dao.GroupDAO groupDAO = org.easymock.EasyMock.createStrictMock(org.apache.ambari.server.orm.dao.GroupDAO.class);

    private static final org.apache.ambari.server.orm.dao.PrincipalDAO principalDAO = org.easymock.EasyMock.createStrictMock(org.apache.ambari.server.orm.dao.PrincipalDAO.class);

    private static final org.apache.ambari.server.orm.dao.PermissionDAO permissionDAO = org.easymock.EasyMock.createStrictMock(org.apache.ambari.server.orm.dao.PermissionDAO.class);

    private static final org.apache.ambari.server.orm.dao.ResourceDAO resourceDAO = org.easymock.EasyMock.createStrictMock(org.apache.ambari.server.orm.dao.ResourceDAO.class);

    private static final org.apache.ambari.server.orm.dao.ViewDAO viewDAO = org.easymock.EasyMock.createMock(org.apache.ambari.server.orm.dao.ViewDAO.class);

    private static final org.apache.ambari.server.orm.dao.ViewInstanceDAO viewInstanceDAO = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.orm.dao.ViewInstanceDAO.class);

    private static final org.apache.ambari.server.orm.dao.MemberDAO memberDAO = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.orm.dao.MemberDAO.class);

    private static final org.apache.ambari.server.orm.dao.ResourceTypeDAO resourceTypeDAO = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.orm.dao.ResourceTypeDAO.class);

    private static final org.apache.ambari.server.security.SecurityHelper securityHelper = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.security.SecurityHelper.class);

    private static final org.apache.ambari.server.view.ViewInstanceHandlerList handlerList = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.view.ViewInstanceHandlerList.class);

    @org.junit.BeforeClass
    public static void initClass() {
        org.apache.ambari.server.controller.internal.PrivilegeResourceProvider.init(org.apache.ambari.server.controller.internal.ViewPrivilegeResourceProviderTest.privilegeDAO, org.apache.ambari.server.controller.internal.ViewPrivilegeResourceProviderTest.userDAO, org.apache.ambari.server.controller.internal.ViewPrivilegeResourceProviderTest.groupDAO, org.apache.ambari.server.controller.internal.ViewPrivilegeResourceProviderTest.principalDAO, org.apache.ambari.server.controller.internal.ViewPrivilegeResourceProviderTest.permissionDAO, org.apache.ambari.server.controller.internal.ViewPrivilegeResourceProviderTest.resourceDAO);
    }

    @org.junit.Before
    public void resetGlobalMocks() {
        org.apache.ambari.server.view.ViewRegistry.initInstance(org.apache.ambari.server.view.ViewRegistryTest.getRegistry(org.apache.ambari.server.controller.internal.ViewPrivilegeResourceProviderTest.viewDAO, org.apache.ambari.server.controller.internal.ViewPrivilegeResourceProviderTest.viewInstanceDAO, org.apache.ambari.server.controller.internal.ViewPrivilegeResourceProviderTest.userDAO, org.apache.ambari.server.controller.internal.ViewPrivilegeResourceProviderTest.memberDAO, org.apache.ambari.server.controller.internal.ViewPrivilegeResourceProviderTest.privilegeDAO, org.apache.ambari.server.controller.internal.ViewPrivilegeResourceProviderTest.permissionDAO, org.apache.ambari.server.controller.internal.ViewPrivilegeResourceProviderTest.resourceDAO, org.apache.ambari.server.controller.internal.ViewPrivilegeResourceProviderTest.resourceTypeDAO, org.apache.ambari.server.controller.internal.ViewPrivilegeResourceProviderTest.securityHelper, org.apache.ambari.server.controller.internal.ViewPrivilegeResourceProviderTest.handlerList, null, null, null));
        org.easymock.EasyMock.reset(org.apache.ambari.server.controller.internal.ViewPrivilegeResourceProviderTest.privilegeDAO, org.apache.ambari.server.controller.internal.ViewPrivilegeResourceProviderTest.userDAO, org.apache.ambari.server.controller.internal.ViewPrivilegeResourceProviderTest.groupDAO, org.apache.ambari.server.controller.internal.ViewPrivilegeResourceProviderTest.principalDAO, org.apache.ambari.server.controller.internal.ViewPrivilegeResourceProviderTest.permissionDAO, org.apache.ambari.server.controller.internal.ViewPrivilegeResourceProviderTest.resourceDAO, org.apache.ambari.server.controller.internal.ViewPrivilegeResourceProviderTest.handlerList);
    }

    @org.junit.Test
    public void testGetResources() throws java.lang.Exception {
        org.apache.ambari.server.orm.entities.ViewEntity viewDefinition = org.apache.ambari.server.orm.entities.ViewEntityTest.getViewEntity();
        org.apache.ambari.server.orm.entities.ViewInstanceEntity viewInstanceDefinition = org.apache.ambari.server.orm.entities.ViewInstanceEntityTest.getViewInstanceEntity();
        viewDefinition.addInstanceDefinition(viewInstanceDefinition);
        viewInstanceDefinition.setViewEntity(viewDefinition);
        viewDefinition.setStatus(org.apache.ambari.view.ViewDefinition.ViewStatus.DEPLOYED);
        org.apache.ambari.server.view.ViewRegistry registry = org.apache.ambari.server.view.ViewRegistry.getInstance();
        registry.addDefinition(viewDefinition);
        registry.addInstanceDefinition(viewDefinition, viewInstanceDefinition);
        java.util.List<org.apache.ambari.server.orm.entities.PrivilegeEntity> privilegeEntities = new java.util.LinkedList<>();
        org.apache.ambari.server.orm.entities.PrivilegeEntity privilegeEntity = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.orm.entities.PrivilegeEntity.class);
        org.apache.ambari.server.orm.entities.ResourceEntity resourceEntity = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.orm.entities.ResourceEntity.class);
        org.apache.ambari.server.orm.entities.UserEntity userEntity = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.orm.entities.UserEntity.class);
        org.apache.ambari.server.orm.entities.PrincipalEntity principalEntity = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.orm.entities.PrincipalEntity.class);
        org.apache.ambari.server.orm.entities.PrincipalTypeEntity principalTypeEntity = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.orm.entities.PrincipalTypeEntity.class);
        org.apache.ambari.server.orm.entities.PermissionEntity permissionEntity = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.orm.entities.PermissionEntity.class);
        java.util.List<org.apache.ambari.server.orm.entities.PrincipalEntity> principalEntities = new java.util.LinkedList<>();
        principalEntities.add(principalEntity);
        java.util.List<org.apache.ambari.server.orm.entities.UserEntity> userEntities = new java.util.LinkedList<>();
        userEntities.add(userEntity);
        privilegeEntities.add(privilegeEntity);
        org.easymock.EasyMock.expect(org.apache.ambari.server.controller.internal.ViewPrivilegeResourceProviderTest.privilegeDAO.findAll()).andReturn(privilegeEntities);
        org.easymock.EasyMock.expect(privilegeEntity.getResource()).andReturn(resourceEntity).anyTimes();
        org.easymock.EasyMock.expect(privilegeEntity.getPrincipal()).andReturn(principalEntity).anyTimes();
        org.easymock.EasyMock.expect(privilegeEntity.getPermission()).andReturn(permissionEntity).anyTimes();
        org.easymock.EasyMock.expect(resourceEntity.getId()).andReturn(20L).anyTimes();
        org.easymock.EasyMock.expect(principalEntity.getId()).andReturn(20L).anyTimes();
        org.easymock.EasyMock.expect(userEntity.getPrincipal()).andReturn(principalEntity).anyTimes();
        org.easymock.EasyMock.expect(userEntity.getUserName()).andReturn("joe").anyTimes();
        org.easymock.EasyMock.expect(permissionEntity.getPermissionName()).andReturn("VIEW.USER").anyTimes();
        org.easymock.EasyMock.expect(permissionEntity.getPermissionLabel()).andReturn("View User").anyTimes();
        org.easymock.EasyMock.expect(principalEntity.getPrincipalType()).andReturn(principalTypeEntity).anyTimes();
        org.easymock.EasyMock.expect(principalTypeEntity.getName()).andReturn("USER").anyTimes();
        org.easymock.EasyMock.expect(org.apache.ambari.server.controller.internal.ViewPrivilegeResourceProviderTest.permissionDAO.findById(org.apache.ambari.server.orm.entities.PermissionEntity.VIEW_USER_PERMISSION)).andReturn(permissionEntity);
        org.easymock.EasyMock.expect(org.apache.ambari.server.controller.internal.ViewPrivilegeResourceProviderTest.userDAO.findUsersByPrincipal(principalEntities)).andReturn(userEntities);
        org.easymock.EasyMock.replay(org.apache.ambari.server.controller.internal.ViewPrivilegeResourceProviderTest.privilegeDAO, org.apache.ambari.server.controller.internal.ViewPrivilegeResourceProviderTest.userDAO, org.apache.ambari.server.controller.internal.ViewPrivilegeResourceProviderTest.groupDAO, org.apache.ambari.server.controller.internal.ViewPrivilegeResourceProviderTest.principalDAO, org.apache.ambari.server.controller.internal.ViewPrivilegeResourceProviderTest.permissionDAO, org.apache.ambari.server.controller.internal.ViewPrivilegeResourceProviderTest.resourceDAO, privilegeEntity, resourceEntity, userEntity, principalEntity, permissionEntity, principalTypeEntity);
        org.springframework.security.core.context.SecurityContextHolder.getContext().setAuthentication(org.apache.ambari.server.security.TestAuthenticationFactory.createAdministrator("admin"));
        org.apache.ambari.server.controller.internal.PrivilegeResourceProvider provider = new org.apache.ambari.server.controller.internal.ViewPrivilegeResourceProvider();
        java.util.Set<org.apache.ambari.server.controller.spi.Resource> resources = provider.getResources(org.apache.ambari.server.controller.utilities.PropertyHelper.getReadRequest(), null);
        org.junit.Assert.assertEquals(1, resources.size());
        org.apache.ambari.server.controller.spi.Resource resource = resources.iterator().next();
        org.junit.Assert.assertEquals("VIEW.USER", resource.getPropertyValue(org.apache.ambari.server.controller.internal.AmbariPrivilegeResourceProvider.PERMISSION_NAME));
        org.junit.Assert.assertEquals("View User", resource.getPropertyValue(org.apache.ambari.server.controller.internal.AmbariPrivilegeResourceProvider.PERMISSION_LABEL));
        org.junit.Assert.assertEquals("joe", resource.getPropertyValue(org.apache.ambari.server.controller.internal.AmbariPrivilegeResourceProvider.PRINCIPAL_NAME));
        org.junit.Assert.assertEquals("USER", resource.getPropertyValue(org.apache.ambari.server.controller.internal.AmbariPrivilegeResourceProvider.PRINCIPAL_TYPE));
        org.easymock.EasyMock.verify(org.apache.ambari.server.controller.internal.ViewPrivilegeResourceProviderTest.privilegeDAO, org.apache.ambari.server.controller.internal.ViewPrivilegeResourceProviderTest.userDAO, org.apache.ambari.server.controller.internal.ViewPrivilegeResourceProviderTest.groupDAO, org.apache.ambari.server.controller.internal.ViewPrivilegeResourceProviderTest.principalDAO, org.apache.ambari.server.controller.internal.ViewPrivilegeResourceProviderTest.permissionDAO, org.apache.ambari.server.controller.internal.ViewPrivilegeResourceProviderTest.resourceDAO, privilegeEntity, resourceEntity, userEntity, principalEntity, permissionEntity, principalTypeEntity);
    }
}
