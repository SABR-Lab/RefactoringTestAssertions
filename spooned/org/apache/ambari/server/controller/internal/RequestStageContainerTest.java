package org.apache.ambari.server.controller.internal;
import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
public class RequestStageContainerTest {
    @org.junit.Test
    public void testGetId() {
        org.apache.ambari.server.controller.internal.RequestStageContainer requestStages = new org.apache.ambari.server.controller.internal.RequestStageContainer(500L, null, null, null);
        org.junit.Assert.assertEquals(500, requestStages.getId().longValue());
    }

    @org.junit.Test
    public void testGetAddStages() {
        org.apache.ambari.server.controller.internal.RequestStageContainer requestStages = new org.apache.ambari.server.controller.internal.RequestStageContainer(500L, null, null, null);
        org.junit.Assert.assertTrue(requestStages.getStages().isEmpty());
        org.apache.ambari.server.actionmanager.Stage stage = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.actionmanager.Stage.class);
        requestStages.addStages(java.util.Collections.singletonList(stage));
        org.junit.Assert.assertEquals(1, requestStages.getStages().size());
        org.junit.Assert.assertTrue(requestStages.getStages().contains(stage));
        org.apache.ambari.server.actionmanager.Stage stage2 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.actionmanager.Stage.class);
        org.apache.ambari.server.actionmanager.Stage stage3 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.actionmanager.Stage.class);
        java.util.List<org.apache.ambari.server.actionmanager.Stage> listStages = new java.util.ArrayList<>();
        listStages.add(stage2);
        listStages.add(stage3);
        requestStages.addStages(listStages);
        org.junit.Assert.assertEquals(3, requestStages.getStages().size());
        listStages = requestStages.getStages();
        org.junit.Assert.assertEquals(stage, listStages.get(0));
        org.junit.Assert.assertEquals(stage2, listStages.get(1));
        org.junit.Assert.assertEquals(stage3, listStages.get(2));
    }

    @org.junit.Test
    public void testGetLastStageId() {
        org.apache.ambari.server.controller.internal.RequestStageContainer requestStages = new org.apache.ambari.server.controller.internal.RequestStageContainer(1L, null, null, null);
        org.junit.Assert.assertEquals(-1, requestStages.getLastStageId());
        org.apache.ambari.server.actionmanager.Stage stage1 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.actionmanager.Stage.class);
        org.apache.ambari.server.actionmanager.Stage stage2 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.actionmanager.Stage.class);
        java.util.List<org.apache.ambari.server.actionmanager.Stage> listStages = new java.util.ArrayList<>();
        listStages.add(stage1);
        listStages.add(stage2);
        org.easymock.EasyMock.expect(stage2.getStageId()).andReturn(22L);
        org.easymock.EasyMock.replay(stage1, stage2);
        requestStages = new org.apache.ambari.server.controller.internal.RequestStageContainer(1L, listStages, null, null);
        org.junit.Assert.assertEquals(22, requestStages.getLastStageId());
    }

    @org.junit.Test
    public void testGetProjectedState() {
        java.lang.String hostname = "host";
        java.lang.String componentName = "component";
        org.apache.ambari.server.actionmanager.Stage stage1 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.actionmanager.Stage.class);
        org.apache.ambari.server.actionmanager.Stage stage2 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.actionmanager.Stage.class);
        org.apache.ambari.server.actionmanager.Stage stage3 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.actionmanager.Stage.class);
        org.apache.ambari.server.actionmanager.Stage stage4 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.actionmanager.Stage.class);
        org.apache.ambari.server.actionmanager.HostRoleCommand command1 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.actionmanager.HostRoleCommand.class);
        org.apache.ambari.server.actionmanager.HostRoleCommand command2 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.actionmanager.HostRoleCommand.class);
        org.apache.ambari.server.actionmanager.HostRoleCommand command3 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.actionmanager.HostRoleCommand.class);
        java.util.List<org.apache.ambari.server.actionmanager.Stage> stages = new java.util.ArrayList<>();
        stages.add(stage1);
        stages.add(stage2);
        stages.add(stage3);
        stages.add(stage4);
        org.easymock.EasyMock.expect(stage1.getHostRoleCommands()).andReturn(java.util.Collections.singletonMap(hostname, java.util.Collections.singletonMap(componentName, command1))).anyTimes();
        org.easymock.EasyMock.expect(stage2.getHostRoleCommands()).andReturn(java.util.Collections.singletonMap(hostname, java.util.Collections.singletonMap(componentName, command2))).anyTimes();
        org.easymock.EasyMock.expect(stage3.getHostRoleCommands()).andReturn(java.util.Collections.singletonMap(hostname, java.util.Collections.singletonMap(componentName, command3))).anyTimes();
        org.easymock.EasyMock.expect(stage4.getHostRoleCommands()).andReturn(java.util.Collections.emptyMap()).anyTimes();
        org.easymock.EasyMock.expect(command3.getRoleCommand()).andReturn(org.apache.ambari.server.RoleCommand.SERVICE_CHECK).anyTimes();
        org.easymock.EasyMock.expect(command2.getRoleCommand()).andReturn(org.apache.ambari.server.RoleCommand.INSTALL).anyTimes();
        org.easymock.EasyMock.replay(stage1, stage2, stage3, stage4, command1, command2, command3);
        org.apache.ambari.server.controller.internal.RequestStageContainer requestStages = new org.apache.ambari.server.controller.internal.RequestStageContainer(1L, stages, null, null);
        org.junit.Assert.assertEquals(org.apache.ambari.server.state.State.INSTALLED, requestStages.getProjectedState(hostname, componentName));
        org.easymock.EasyMock.verify(stage1, stage2, stage3, stage4, command1, command2, command3);
    }

    @org.junit.Test
    public void testPersist() throws org.apache.ambari.server.AmbariException {
        org.apache.ambari.server.actionmanager.ActionManager actionManager = org.easymock.EasyMock.createStrictMock(org.apache.ambari.server.actionmanager.ActionManager.class);
        org.apache.ambari.server.actionmanager.RequestFactory requestFactory = org.easymock.EasyMock.createStrictMock(org.apache.ambari.server.actionmanager.RequestFactory.class);
        org.apache.ambari.server.actionmanager.Request request = org.easymock.EasyMock.createStrictMock(org.apache.ambari.server.actionmanager.Request.class);
        org.apache.ambari.server.actionmanager.Stage stage1 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.actionmanager.Stage.class);
        org.apache.ambari.server.actionmanager.Stage stage2 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.actionmanager.Stage.class);
        java.util.List<org.apache.ambari.server.actionmanager.Stage> stages = new java.util.ArrayList<>();
        stages.add(stage1);
        stages.add(stage2);
        org.easymock.EasyMock.expect(requestFactory.createNewFromStages(stages, "{}")).andReturn(request);
        request.setUserName(null);
        org.easymock.EasyMock.expectLastCall().once();
        org.easymock.EasyMock.expect(request.getStages()).andReturn(stages).anyTimes();
        actionManager.sendActions(request, null);
        org.easymock.EasyMock.replay(actionManager, requestFactory, request, stage1, stage2);
        org.apache.ambari.server.controller.internal.RequestStageContainer requestStages = new org.apache.ambari.server.controller.internal.RequestStageContainer(1L, stages, requestFactory, actionManager);
        requestStages.persist();
        org.easymock.EasyMock.verify(actionManager, requestFactory, request, stage1, stage2);
    }

    @org.junit.Test
    public void testPersist_noStages() throws org.apache.ambari.server.AmbariException {
        org.apache.ambari.server.actionmanager.ActionManager actionManager = org.easymock.EasyMock.createStrictMock(org.apache.ambari.server.actionmanager.ActionManager.class);
        org.apache.ambari.server.actionmanager.RequestFactory requestFactory = org.easymock.EasyMock.createStrictMock(org.apache.ambari.server.actionmanager.RequestFactory.class);
        org.easymock.EasyMock.replay(actionManager, requestFactory);
        org.apache.ambari.server.controller.internal.RequestStageContainer requestStages = new org.apache.ambari.server.controller.internal.RequestStageContainer(1L, null, requestFactory, actionManager);
        requestStages.persist();
        org.easymock.EasyMock.verify(actionManager, requestFactory);
    }

    @org.junit.Test
    public void testGetRequestStatusResponse() {
        org.apache.ambari.server.actionmanager.ActionManager actionManager = org.easymock.EasyMock.createStrictMock(org.apache.ambari.server.actionmanager.ActionManager.class);
        org.apache.ambari.server.actionmanager.Stage stage1 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.actionmanager.Stage.class);
        org.apache.ambari.server.actionmanager.Stage stage2 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.actionmanager.Stage.class);
        org.apache.ambari.server.actionmanager.HostRoleCommand command1 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.actionmanager.HostRoleCommand.class);
        org.apache.ambari.server.Role role = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.Role.class);
        java.util.List<org.apache.ambari.server.actionmanager.Stage> stages = new java.util.ArrayList<>();
        org.apache.ambari.server.RoleCommand roleCommand = org.apache.ambari.server.RoleCommand.INSTALL;
        org.apache.ambari.server.actionmanager.HostRoleStatus status = org.apache.ambari.server.actionmanager.HostRoleStatus.IN_PROGRESS;
        stages.add(stage1);
        stages.add(stage2);
        java.util.List<org.apache.ambari.server.actionmanager.HostRoleCommand> hostRoleCommands = new java.util.ArrayList<>();
        hostRoleCommands.add(command1);
        org.easymock.EasyMock.expect(actionManager.getRequestTasks(100)).andReturn(hostRoleCommands);
        org.easymock.EasyMock.expect(actionManager.getRequestContext(100)).andReturn("test");
        org.easymock.EasyMock.expect(command1.getTaskId()).andReturn(1L);
        org.easymock.EasyMock.expect(command1.getRoleCommand()).andReturn(roleCommand);
        org.easymock.EasyMock.expect(command1.getRole()).andReturn(role);
        org.easymock.EasyMock.expect(command1.getStatus()).andReturn(status);
        org.easymock.EasyMock.replay(actionManager, stage1, stage2, command1, role);
        org.apache.ambari.server.controller.internal.RequestStageContainer requestStages = new org.apache.ambari.server.controller.internal.RequestStageContainer(100L, stages, null, actionManager);
        org.apache.ambari.server.controller.RequestStatusResponse response = requestStages.getRequestStatusResponse();
        org.junit.Assert.assertEquals(100, response.getRequestId());
        java.util.List<org.apache.ambari.server.controller.ShortTaskStatus> tasks = response.getTasks();
        org.junit.Assert.assertEquals(1, tasks.size());
        org.apache.ambari.server.controller.ShortTaskStatus task = tasks.get(0);
        org.junit.Assert.assertEquals(1, task.getTaskId());
        org.junit.Assert.assertEquals(roleCommand.toString(), task.getCommand());
        org.junit.Assert.assertEquals(status.toString(), task.getStatus());
        org.junit.Assert.assertEquals("test", response.getRequestContext());
        org.easymock.EasyMock.verify(actionManager, stage1, stage2, command1, role);
    }
}
