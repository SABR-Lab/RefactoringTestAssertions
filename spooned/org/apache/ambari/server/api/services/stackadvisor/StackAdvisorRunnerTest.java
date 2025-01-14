package org.apache.ambari.server.api.services.stackadvisor;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import static org.easymock.EasyMock.expect;
import static org.powermock.api.easymock.PowerMock.createNiceMock;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.support.membermodification.MemberModifier.stub;
@org.junit.runner.RunWith(org.powermock.modules.junit4.PowerMockRunner.class)
@org.powermock.core.classloader.annotations.PrepareForTest(org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRunner.class)
public class StackAdvisorRunnerTest {
    private org.junit.rules.TemporaryFolder temp = new org.junit.rules.TemporaryFolder();

    @org.junit.Before
    public void setUp() throws java.io.IOException {
        temp.create();
    }

    @org.junit.After
    public void tearDown() throws java.io.IOException {
        temp.delete();
    }

    @org.junit.Test(expected = org.apache.ambari.server.api.services.stackadvisor.StackAdvisorException.class)
    public void testRunScript_processStartThrowsException_returnFalse() throws java.lang.Exception {
        org.apache.ambari.server.api.services.stackadvisor.commands.StackAdvisorCommandType saCommandType = org.apache.ambari.server.api.services.stackadvisor.commands.StackAdvisorCommandType.RECOMMEND_COMPONENT_LAYOUT;
        java.io.File actionDirectory = temp.newFolder("actionDir");
        java.lang.ProcessBuilder processBuilder = org.powermock.api.easymock.PowerMock.createNiceMock(java.lang.ProcessBuilder.class);
        org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRunner saRunner = new org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRunner();
        org.apache.ambari.server.configuration.Configuration configuration = new org.apache.ambari.server.configuration.Configuration();
        configuration.setProperty(org.apache.ambari.server.configuration.Configuration.METADATA_DIR_PATH, "");
        saRunner.setConfigs(configuration);
        org.powermock.api.support.membermodification.MemberModifier.stub(org.powermock.api.easymock.PowerMock.method(org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRunner.class, "prepareShellCommand")).toReturn(processBuilder);
        org.easymock.EasyMock.expect(processBuilder.environment()).andReturn(new java.util.HashMap<>()).times(3);
        org.easymock.EasyMock.expect(processBuilder.start()).andThrow(new java.io.IOException());
        org.powermock.api.easymock.PowerMock.replay(processBuilder);
        saRunner.runScript(org.apache.ambari.server.state.ServiceInfo.ServiceAdvisorType.PYTHON, saCommandType, actionDirectory);
    }

    @org.junit.Test(expected = org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRequestException.class)
    public void testRunScript_processExitCode1_returnFalse() throws java.lang.Exception {
        org.apache.ambari.server.api.services.stackadvisor.commands.StackAdvisorCommandType saCommandType = org.apache.ambari.server.api.services.stackadvisor.commands.StackAdvisorCommandType.RECOMMEND_COMPONENT_LAYOUT;
        java.io.File actionDirectory = temp.newFolder("actionDir");
        java.lang.ProcessBuilder processBuilder = org.powermock.api.easymock.PowerMock.createNiceMock(java.lang.ProcessBuilder.class);
        java.lang.Process process = org.powermock.api.easymock.PowerMock.createNiceMock(java.lang.Process.class);
        org.apache.ambari.server.configuration.Configuration configuration = new org.apache.ambari.server.configuration.Configuration();
        configuration.setProperty(org.apache.ambari.server.configuration.Configuration.METADATA_DIR_PATH, "");
        org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRunner saRunner = new org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRunner();
        saRunner.setConfigs(configuration);
        org.powermock.api.support.membermodification.MemberModifier.stub(org.powermock.api.easymock.PowerMock.method(org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRunner.class, "prepareShellCommand")).toReturn(processBuilder);
        org.easymock.EasyMock.expect(processBuilder.environment()).andReturn(new java.util.HashMap<>()).times(3);
        org.easymock.EasyMock.expect(processBuilder.start()).andReturn(process);
        org.easymock.EasyMock.expect(process.waitFor()).andReturn(1);
        org.powermock.api.easymock.PowerMock.replay(processBuilder, process);
        saRunner.runScript(org.apache.ambari.server.state.ServiceInfo.ServiceAdvisorType.PYTHON, saCommandType, actionDirectory);
    }

    @org.junit.Test(expected = org.apache.ambari.server.api.services.stackadvisor.StackAdvisorException.class)
    public void testRunScript_processExitCode2_returnFalse() throws java.lang.Exception {
        org.apache.ambari.server.api.services.stackadvisor.commands.StackAdvisorCommandType saCommandType = org.apache.ambari.server.api.services.stackadvisor.commands.StackAdvisorCommandType.RECOMMEND_COMPONENT_LAYOUT;
        java.io.File actionDirectory = temp.newFolder("actionDir");
        java.lang.ProcessBuilder processBuilder = org.powermock.api.easymock.PowerMock.createNiceMock(java.lang.ProcessBuilder.class);
        java.lang.Process process = org.powermock.api.easymock.PowerMock.createNiceMock(java.lang.Process.class);
        org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRunner saRunner = new org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRunner();
        org.apache.ambari.server.configuration.Configuration configuration = new org.apache.ambari.server.configuration.Configuration();
        configuration.setProperty(org.apache.ambari.server.configuration.Configuration.METADATA_DIR_PATH, "");
        saRunner.setConfigs(configuration);
        org.powermock.api.support.membermodification.MemberModifier.stub(org.powermock.api.easymock.PowerMock.method(org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRunner.class, "prepareShellCommand")).toReturn(processBuilder);
        org.easymock.EasyMock.expect(processBuilder.environment()).andReturn(new java.util.HashMap<>()).times(3);
        org.easymock.EasyMock.expect(processBuilder.start()).andReturn(process);
        org.easymock.EasyMock.expect(process.waitFor()).andReturn(2);
        org.powermock.api.easymock.PowerMock.replay(processBuilder, process);
        saRunner.runScript(org.apache.ambari.server.state.ServiceInfo.ServiceAdvisorType.PYTHON, saCommandType, actionDirectory);
    }

    @org.junit.Test
    public void testRunScript_processExitCodeZero_returnTrue() throws java.lang.Exception {
        org.apache.ambari.server.api.services.stackadvisor.commands.StackAdvisorCommandType saCommandType = org.apache.ambari.server.api.services.stackadvisor.commands.StackAdvisorCommandType.RECOMMEND_COMPONENT_LAYOUT;
        java.io.File actionDirectory = temp.newFolder("actionDir");
        java.lang.ProcessBuilder processBuilder = org.powermock.api.easymock.PowerMock.createNiceMock(java.lang.ProcessBuilder.class);
        java.lang.Process process = org.powermock.api.easymock.PowerMock.createNiceMock(java.lang.Process.class);
        org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRunner saRunner = new org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRunner();
        org.apache.ambari.server.configuration.Configuration configuration = new org.apache.ambari.server.configuration.Configuration();
        configuration.setProperty(org.apache.ambari.server.configuration.Configuration.METADATA_DIR_PATH, "");
        saRunner.setConfigs(configuration);
        org.powermock.api.support.membermodification.MemberModifier.stub(org.powermock.api.easymock.PowerMock.method(org.apache.ambari.server.api.services.stackadvisor.StackAdvisorRunner.class, "prepareShellCommand")).toReturn(processBuilder);
        org.easymock.EasyMock.expect(processBuilder.environment()).andReturn(new java.util.HashMap<>()).times(3);
        org.easymock.EasyMock.expect(processBuilder.start()).andReturn(process);
        org.easymock.EasyMock.expect(process.waitFor()).andReturn(0);
        org.powermock.api.easymock.PowerMock.replay(processBuilder, process);
        try {
            saRunner.runScript(org.apache.ambari.server.state.ServiceInfo.ServiceAdvisorType.PYTHON, saCommandType, actionDirectory);
        } catch (org.apache.ambari.server.api.services.stackadvisor.StackAdvisorException ex) {
            org.junit.Assert.fail("Should not fail with StackAdvisorException");
        }
    }
}
