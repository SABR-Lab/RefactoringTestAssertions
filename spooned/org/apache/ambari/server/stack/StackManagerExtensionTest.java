package org.apache.ambari.server.stack;
import org.easymock.EasyMock;
import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
public class StackManagerExtensionTest {
    @org.junit.Test
    public void testExtensions() throws java.lang.Exception {
        org.apache.ambari.server.orm.dao.MetainfoDAO metaInfoDao = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.orm.dao.MetainfoDAO.class);
        org.apache.ambari.server.orm.dao.StackDAO stackDao = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.orm.dao.StackDAO.class);
        org.apache.ambari.server.orm.dao.ExtensionDAO extensionDao = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.orm.dao.ExtensionDAO.class);
        org.apache.ambari.server.orm.dao.ExtensionLinkDAO linkDao = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.orm.dao.ExtensionLinkDAO.class);
        org.apache.ambari.server.metadata.ActionMetadata actionMetadata = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.metadata.ActionMetadata.class);
        org.apache.ambari.server.state.stack.OsFamily osFamily = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.stack.OsFamily.class);
        org.apache.ambari.server.orm.entities.StackEntity stack1 = new org.apache.ambari.server.orm.entities.StackEntity();
        stack1.setStackName("HDP");
        stack1.setStackVersion("0.1");
        org.apache.ambari.server.orm.entities.StackEntity stack2 = new org.apache.ambari.server.orm.entities.StackEntity();
        stack2.setStackName("HDP");
        stack2.setStackVersion("0.2");
        org.apache.ambari.server.orm.entities.StackEntity stack3 = new org.apache.ambari.server.orm.entities.StackEntity();
        stack3.setStackName("HDP");
        stack3.setStackVersion("0.3");
        org.apache.ambari.server.orm.entities.StackEntity stack4 = new org.apache.ambari.server.orm.entities.StackEntity();
        stack4.setStackName("HDP");
        stack4.setStackVersion("0.4");
        org.apache.ambari.server.orm.entities.ExtensionEntity extension1 = new org.apache.ambari.server.orm.entities.ExtensionEntity();
        extension1.setExtensionName("EXT");
        extension1.setExtensionVersion("0.1");
        org.apache.ambari.server.orm.entities.ExtensionEntity extension2 = new org.apache.ambari.server.orm.entities.ExtensionEntity();
        extension2.setExtensionName("EXT");
        extension2.setExtensionVersion("0.2");
        org.apache.ambari.server.orm.entities.ExtensionEntity extension3 = new org.apache.ambari.server.orm.entities.ExtensionEntity();
        extension3.setExtensionName("EXT");
        extension3.setExtensionVersion("0.3");
        org.apache.ambari.server.orm.entities.ExtensionEntity extension4 = new org.apache.ambari.server.orm.entities.ExtensionEntity();
        extension4.setExtensionName("EXT");
        extension4.setExtensionVersion("0.4");
        org.apache.ambari.server.orm.entities.ExtensionLinkEntity link1 = new org.apache.ambari.server.orm.entities.ExtensionLinkEntity();
        link1.setLinkId(new java.lang.Long(-1));
        link1.setStack(stack1);
        link1.setExtension(extension1);
        java.util.List<org.apache.ambari.server.orm.entities.ExtensionLinkEntity> list = new java.util.ArrayList<>();
        java.util.List<org.apache.ambari.server.orm.entities.ExtensionLinkEntity> linkList = new java.util.ArrayList<>();
        linkList.add(link1);
        org.easymock.EasyMock.expect(stackDao.find("HDP", "0.1")).andReturn(stack1).atLeastOnce();
        org.easymock.EasyMock.expect(stackDao.find("HDP", "0.2")).andReturn(stack2).atLeastOnce();
        org.easymock.EasyMock.expect(stackDao.find("HDP", "0.3")).andReturn(stack3).atLeastOnce();
        org.easymock.EasyMock.expect(stackDao.find("HDP", "0.4")).andReturn(stack3).atLeastOnce();
        org.easymock.EasyMock.expect(extensionDao.find("EXT", "0.1")).andReturn(extension1).atLeastOnce();
        org.easymock.EasyMock.expect(extensionDao.find("EXT", "0.2")).andReturn(extension2).atLeastOnce();
        org.easymock.EasyMock.expect(extensionDao.find("EXT", "0.3")).andReturn(extension3).atLeastOnce();
        org.easymock.EasyMock.expect(extensionDao.find("EXT", "0.4")).andReturn(extension4).atLeastOnce();
        org.easymock.EasyMock.expect(linkDao.findByStack("HDP", "0.1")).andReturn(linkList).atLeastOnce();
        org.easymock.EasyMock.expect(linkDao.findByStack(org.easymock.EasyMock.anyObject(java.lang.String.class), org.easymock.EasyMock.anyObject(java.lang.String.class))).andReturn(list).atLeastOnce();
        org.easymock.EasyMock.expect(linkDao.findByStackAndExtension("HDP", "0.2", "EXT", "0.2")).andReturn(null).atLeastOnce();
        org.easymock.EasyMock.expect(linkDao.findByStackAndExtension("HDP", "0.1", "EXT", "0.1")).andReturn(link1).atLeastOnce();
        org.easymock.EasyMock.expect(linkDao.merge(link1)).andReturn(link1).atLeastOnce();
        org.easymock.EasyMock.replay(actionMetadata, stackDao, metaInfoDao, osFamily, extensionDao, linkDao);
        java.lang.String stacks = java.lang.ClassLoader.getSystemClassLoader().getResource("stacks_with_extensions").getPath();
        java.lang.String common = java.lang.ClassLoader.getSystemClassLoader().getResource("common-services").getPath();
        java.lang.String extensions = java.lang.ClassLoader.getSystemClassLoader().getResource("extensions").getPath();
        org.apache.ambari.server.controller.AmbariManagementHelper helper = new org.apache.ambari.server.controller.AmbariManagementHelper(stackDao, extensionDao, linkDao);
        org.apache.ambari.server.stack.StackManager stackManager = null;
        try {
            stackManager = new org.apache.ambari.server.stack.StackManager(new java.io.File(stacks), new java.io.File(common), new java.io.File(extensions), osFamily, false, metaInfoDao, actionMetadata, stackDao, extensionDao, linkDao, helper);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
        org.junit.Assert.assertNotNull("Failed to create Stack Manager", stackManager);
        org.apache.ambari.server.state.ExtensionInfo extension = stackManager.getExtension("EXT", "0.1");
        org.junit.Assert.assertNull("EXT 0.1's parent: " + extension.getParentExtensionVersion(), extension.getParentExtensionVersion());
        org.junit.Assert.assertNotNull(extension.getService("OOZIE2"));
        org.apache.ambari.server.state.ServiceInfo oozie = extension.getService("OOZIE2");
        org.junit.Assert.assertNotNull("Package dir is " + oozie.getServicePackageFolder(), oozie.getServicePackageFolder());
        org.junit.Assert.assertTrue("Package dir is " + oozie.getServicePackageFolder(), oozie.getServicePackageFolder().contains("extensions/EXT/0.1/services/OOZIE2/package"));
        org.junit.Assert.assertEquals(oozie.getVersion(), "3.2.0");
        java.io.File checks = oozie.getChecksFolder();
        org.junit.Assert.assertNotNull(checks);
        org.junit.Assert.assertTrue("Checks dir is " + checks.getPath(), checks.getPath().contains("extensions/EXT/0.1/services/OOZIE2/checks"));
        java.io.File serverActions = oozie.getServerActionsFolder();
        org.junit.Assert.assertNotNull(serverActions);
        org.junit.Assert.assertTrue("Server actions dir is " + serverActions.getPath(), serverActions.getPath().contains("extensions/EXT/0.1/services/OOZIE2/server_actions"));
        java.util.List<org.apache.ambari.server.state.ThemeInfo> themes = oozie.getThemes();
        org.junit.Assert.assertNotNull(themes);
        org.junit.Assert.assertTrue("Number of themes is " + themes.size(), themes.size() == 1);
        org.apache.ambari.server.state.ThemeInfo theme = themes.get(0);
        org.junit.Assert.assertTrue("Theme: " + theme.getFileName(), theme.getFileName().contains("working_theme.json"));
        extension = stackManager.getExtension("EXT", "0.2");
        org.junit.Assert.assertNotNull("EXT 0.2's parent: " + extension.getParentExtensionVersion(), extension.getParentExtensionVersion());
        org.junit.Assert.assertEquals("EXT 0.2's parent: " + extension.getParentExtensionVersion(), "0.1", extension.getParentExtensionVersion());
        org.junit.Assert.assertNotNull(extension.getService("OOZIE2"));
        org.junit.Assert.assertTrue("Extension is set to auto link", !extension.isAutoLink());
        oozie = extension.getService("OOZIE2");
        org.junit.Assert.assertNotNull("Package dir is " + oozie.getServicePackageFolder(), oozie.getServicePackageFolder());
        org.junit.Assert.assertTrue("Package dir is " + oozie.getServicePackageFolder(), oozie.getServicePackageFolder().contains("extensions/EXT/0.1/services/OOZIE2/package"));
        org.junit.Assert.assertEquals(oozie.getVersion(), "4.0.0");
        checks = oozie.getChecksFolder();
        org.junit.Assert.assertNotNull(checks);
        org.junit.Assert.assertTrue("Checks dir is " + checks.getPath(), checks.getPath().contains("extensions/EXT/0.1/services/OOZIE2/checks"));
        serverActions = oozie.getServerActionsFolder();
        org.junit.Assert.assertNotNull(serverActions);
        org.junit.Assert.assertTrue("Server actions dir is " + serverActions.getPath(), serverActions.getPath().contains("extensions/EXT/0.1/services/OOZIE2/server_actions"));
        themes = oozie.getThemes();
        org.junit.Assert.assertNotNull(themes);
        org.junit.Assert.assertTrue("Number of themes is " + themes.size(), themes.size() == 0);
        extension = stackManager.getExtension("EXT", "0.3");
        org.junit.Assert.assertTrue("Extension is not set to auto link", extension.isAutoLink());
        org.apache.ambari.server.state.StackInfo stack = stackManager.getStack("HDP", "0.1");
        org.junit.Assert.assertNotNull(stack.getService("OOZIE2"));
        oozie = stack.getService("OOZIE2");
        org.junit.Assert.assertNotNull("Package dir is " + oozie.getServicePackageFolder(), oozie.getServicePackageFolder());
        org.junit.Assert.assertTrue("Package dir is " + oozie.getServicePackageFolder(), oozie.getServicePackageFolder().contains("extensions/EXT/0.1/services/OOZIE2/package"));
        org.junit.Assert.assertEquals(oozie.getVersion(), "3.2.0");
        org.junit.Assert.assertTrue("Extensions found: " + stack.getExtensions().size(), stack.getExtensions().size() == 1);
        extension = stack.getExtensions().iterator().next();
        org.junit.Assert.assertEquals("Extension name: " + extension.getName(), extension.getName(), "EXT");
        org.junit.Assert.assertEquals("Extension version: " + extension.getVersion(), extension.getVersion(), "0.1");
        org.apache.ambari.server.state.ExtensionInfo extensionInfo2 = stackManager.getExtension("EXT", "0.2");
        helper.updateExtensionLink(stackManager, link1, stack, extension, extensionInfo2);
        org.junit.Assert.assertEquals(link1.getExtension().getExtensionVersion(), link1.getExtension().getExtensionVersion(), "0.2");
        stack = stackManager.getStack("HDP", "0.2");
        org.junit.Assert.assertTrue("Extensions found: " + stack.getExtensions().size(), stack.getExtensions().size() == 0);
        stack = stackManager.getStack("HDP", "0.3");
        org.junit.Assert.assertTrue("Extensions found: " + stack.getExtensions().size(), stack.getExtensions().size() == 1);
        extension = stack.getExtensions().iterator().next();
        org.junit.Assert.assertNotNull(extension.getService("OOZIE2"));
        oozie = extension.getService("OOZIE2");
        org.junit.Assert.assertEquals(oozie.getVersion(), "4.0.0");
        org.junit.Assert.assertEquals("Extension name: " + extension.getName(), extension.getName(), "EXT");
        org.junit.Assert.assertEquals("Extension version: " + extension.getVersion(), extension.getVersion(), "0.4");
        stack = stackManager.getStack("HDP", "0.4");
        org.junit.Assert.assertTrue("Extensions found: " + stack.getExtensions().size(), stack.getExtensions().size() == 1);
        extension = stack.getExtensions().iterator().next();
        org.junit.Assert.assertEquals("Extension name: " + extension.getName(), extension.getName(), "EXT");
        org.junit.Assert.assertEquals("Extension version: " + extension.getVersion(), extension.getVersion(), "0.4");
    }
}
