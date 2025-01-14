package org.apache.ambari.server.serveraction.kerberos;
import org.easymock.Capture;
import org.easymock.CaptureType;
import org.easymock.EasyMock;
import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.newCapture;
import static org.easymock.EasyMock.replay;
public class IPAKerberosOperationHandlerTest extends org.apache.ambari.server.serveraction.kerberos.KDCKerberosOperationHandlerTest {
    private static com.google.inject.Injector injector;

    private static final java.util.Map<java.lang.String, java.lang.String> KERBEROS_ENV_MAP;

    static {
        java.util.Map<java.lang.String, java.lang.String> map = new java.util.HashMap<>(org.apache.ambari.server.serveraction.kerberos.KerberosOperationHandlerTest.DEFAULT_KERBEROS_ENV_MAP);
        map.put(org.apache.ambari.server.serveraction.kerberos.IPAKerberosOperationHandler.KERBEROS_ENV_USER_PRINCIPAL_GROUP, "");
        KERBEROS_ENV_MAP = java.util.Collections.unmodifiableMap(map);
    }

    @org.junit.BeforeClass
    public static void beforeIPAKerberosOperationHandlerTest() throws java.lang.Exception {
        org.apache.ambari.server.serveraction.kerberos.IPAKerberosOperationHandlerTest.injector = com.google.inject.Guice.createInjector(new com.google.inject.AbstractModule() {
            @java.lang.Override
            protected void configure() {
                org.apache.ambari.server.configuration.Configuration configuration = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.configuration.Configuration.class);
                org.easymock.EasyMock.expect(configuration.getServerOsFamily()).andReturn("redhat6").anyTimes();
                org.easymock.EasyMock.replay(configuration);
                bind(org.apache.ambari.server.state.Clusters.class).toInstance(org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Clusters.class));
                bind(org.apache.ambari.server.configuration.Configuration.class).toInstance(configuration);
                bind(org.apache.ambari.server.state.stack.OsFamily.class).toInstance(org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.stack.OsFamily.class));
            }
        });
    }

    @org.junit.Test
    public void testGetAdminServerHost() throws org.apache.ambari.server.serveraction.kerberos.KerberosOperationException {
        org.apache.ambari.server.utils.ShellCommandUtil.Result kinitResult = createMock(org.apache.ambari.server.utils.ShellCommandUtil.Result.class);
        org.easymock.EasyMock.expect(kinitResult.isSuccessful()).andReturn(true).anyTimes();
        org.easymock.Capture<java.lang.String[]> capturedKinitCommand = org.easymock.EasyMock.newCapture(org.easymock.CaptureType.ALL);
        org.apache.ambari.server.serveraction.kerberos.IPAKerberosOperationHandler handler = createMockedHandler(org.apache.ambari.server.serveraction.kerberos.KDCKerberosOperationHandlerTest.methodExecuteCommand);
        org.easymock.EasyMock.expect(handler.executeCommand(org.easymock.EasyMock.capture(capturedKinitCommand), org.easymock.EasyMock.anyObject(java.util.Map.class), org.easymock.EasyMock.anyObject(org.apache.ambari.server.serveraction.kerberos.KDCKerberosOperationHandler.InteractivePasswordHandler.class))).andReturn(kinitResult).anyTimes();
        java.util.Map<java.lang.String, java.lang.String> config = new java.util.HashMap<>();
        config.put("encryption_types", "aes des3-cbc-sha1 rc4 des-cbc-md5");
        replayAll();
        config.put("admin_server_host", "kdc.example.com");
        handler.open(getAdminCredentials(), org.apache.ambari.server.serveraction.kerberos.KerberosOperationHandlerTest.DEFAULT_REALM, config);
        junit.framework.Assert.assertEquals("kdc.example.com", handler.getAdminServerHost(false));
        junit.framework.Assert.assertEquals("kdc.example.com", handler.getAdminServerHost(true));
        handler.close();
        config.put("admin_server_host", "kdc.example.com:749");
        handler.open(getAdminCredentials(), org.apache.ambari.server.serveraction.kerberos.KerberosOperationHandlerTest.DEFAULT_REALM, config);
        junit.framework.Assert.assertEquals("kdc.example.com", handler.getAdminServerHost(false));
        junit.framework.Assert.assertEquals("kdc.example.com:749", handler.getAdminServerHost(true));
        handler.close();
        verifyAll();
        junit.framework.Assert.assertTrue(capturedKinitCommand.hasCaptured());
        java.util.List<java.lang.String[]> capturedValues = capturedKinitCommand.getValues();
        junit.framework.Assert.assertEquals(2, capturedValues.size());
    }

    @java.lang.Override
    protected org.apache.ambari.server.serveraction.kerberos.IPAKerberosOperationHandler createMockedHandler(java.lang.reflect.Method... mockedMethods) {
        org.apache.ambari.server.serveraction.kerberos.IPAKerberosOperationHandler handler = createMockBuilder(org.apache.ambari.server.serveraction.kerberos.IPAKerberosOperationHandler.class).addMockedMethods(mockedMethods).createMock();
        org.apache.ambari.server.serveraction.kerberos.IPAKerberosOperationHandlerTest.injector.injectMembers(handler);
        return handler;
    }

    @java.lang.Override
    protected java.util.Map<java.lang.String, java.lang.String> getKerberosEnv() {
        return org.apache.ambari.server.serveraction.kerberos.IPAKerberosOperationHandlerTest.KERBEROS_ENV_MAP;
    }

    @java.lang.Override
    protected void setupPrincipalAlreadyExists(org.apache.ambari.server.serveraction.kerberos.KerberosOperationHandler handler, boolean service) throws java.lang.Exception {
        org.apache.ambari.server.utils.ShellCommandUtil.Result result = createMock(org.apache.ambari.server.utils.ShellCommandUtil.Result.class);
        org.easymock.EasyMock.expect(result.getExitCode()).andReturn(1).anyTimes();
        org.easymock.EasyMock.expect(result.isSuccessful()).andReturn(false).anyTimes();
        if (service) {
            org.easymock.EasyMock.expect(result.getStderr()).andReturn("ipa: ERROR: service with name \"service/host@EXAMPLE.COM\" already exists").anyTimes();
        } else {
            org.easymock.EasyMock.expect(result.getStderr()).andReturn("ipa: ERROR: user with name \"user\" already exists").anyTimes();
        }
        org.easymock.EasyMock.expect(result.getStdout()).andReturn("").anyTimes();
        org.easymock.EasyMock.expect(handler.executeCommand(org.apache.ambari.server.serveraction.kerberos.KDCKerberosOperationHandlerTest.arrayContains(new java.lang.String[]{ "ipa", service ? "service-add" : "user-add" }), org.easymock.EasyMock.anyObject(java.util.Map.class), org.easymock.EasyMock.anyObject(org.apache.ambari.server.serveraction.kerberos.KDCKerberosOperationHandler.InteractivePasswordHandler.class))).andReturn(result).anyTimes();
    }

    @java.lang.Override
    protected void setupPrincipalDoesNotExist(org.apache.ambari.server.serveraction.kerberos.KerberosOperationHandler handler, boolean service) throws java.lang.Exception {
        org.apache.ambari.server.utils.ShellCommandUtil.Result result = createMock(org.apache.ambari.server.utils.ShellCommandUtil.Result.class);
        org.easymock.EasyMock.expect(result.getExitCode()).andReturn(2).anyTimes();
        org.easymock.EasyMock.expect(result.isSuccessful()).andReturn(false).anyTimes();
        org.easymock.EasyMock.expect(result.getStderr()).andReturn(java.lang.String.format("ipa: ERROR: %s: user not found", service ? "service/host" : "user")).anyTimes();
        org.easymock.EasyMock.expect(result.getStdout()).andReturn("").anyTimes();
        org.easymock.EasyMock.expect(handler.executeCommand(org.apache.ambari.server.serveraction.kerberos.KDCKerberosOperationHandlerTest.arrayContains(new java.lang.String[]{ "ipa", service ? "service-show" : "user-show" }), org.easymock.EasyMock.anyObject(java.util.Map.class), org.easymock.EasyMock.anyObject(org.apache.ambari.server.serveraction.kerberos.KDCKerberosOperationHandler.InteractivePasswordHandler.class))).andReturn(result).anyTimes();
    }

    @java.lang.Override
    protected void setupPrincipalExists(org.apache.ambari.server.serveraction.kerberos.KerberosOperationHandler handler, boolean service) throws java.lang.Exception {
        org.apache.ambari.server.utils.ShellCommandUtil.Result result = createMock(org.apache.ambari.server.utils.ShellCommandUtil.Result.class);
        org.easymock.EasyMock.expect(result.getExitCode()).andReturn(0).anyTimes();
        org.easymock.EasyMock.expect(result.isSuccessful()).andReturn(true).anyTimes();
        org.easymock.EasyMock.expect(result.getStderr()).andReturn("").anyTimes();
        org.easymock.EasyMock.expect(result.getStdout()).andReturn(java.lang.String.format(((((((((("  User login: %s\n" + "  Last name: User\n") + "  Home directory: /home/user\n") + "  Login shell: /bin/bash\n") + "  Principal alias: user@EXAMPLE.COM\n") + "  UID: 324200000\n") + "  GID: 324200000\n") + "  Account disabled: False\n") + "  Password: True\n") + "  Member of groups: users\n") + "  Kerberos keys available: True", service ? "service/host" : "user")).anyTimes();
        org.easymock.EasyMock.expect(handler.executeCommand(org.apache.ambari.server.serveraction.kerberos.KDCKerberosOperationHandlerTest.arrayContains(new java.lang.String[]{ "ipa", service ? "service-show" : "user-show" }), org.easymock.EasyMock.anyObject(java.util.Map.class), org.easymock.EasyMock.anyObject(org.apache.ambari.server.serveraction.kerberos.KDCKerberosOperationHandler.InteractivePasswordHandler.class))).andReturn(result).anyTimes();
    }
}
