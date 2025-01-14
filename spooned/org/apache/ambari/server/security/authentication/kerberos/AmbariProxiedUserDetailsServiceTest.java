package org.apache.ambari.server.security.authentication.kerberos;
import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import static org.easymock.EasyMock.expect;
public class AmbariProxiedUserDetailsServiceTest extends org.easymock.EasyMockSupport {
    @org.junit.Test
    public void testValidateHost() throws java.net.UnknownHostException {
        org.apache.ambari.server.configuration.Configuration configuration = createNiceMock(org.apache.ambari.server.configuration.Configuration.class);
        org.apache.ambari.server.security.authorization.Users users = createNiceMock(org.apache.ambari.server.security.authorization.Users.class);
        org.apache.ambari.server.security.authentication.kerberos.AmbariProxiedUserDetailsService service = org.easymock.EasyMock.partialMockBuilder(org.apache.ambari.server.security.authentication.kerberos.AmbariProxiedUserDetailsService.class).withConstructor(configuration, users).addMockedMethod("getIpAddress").createStrictMock();
        service.getIpAddress(org.easymock.EasyMock.anyString());
        org.easymock.EasyMock.expectLastCall().andStubAnswer(() -> {
            java.lang.String hostname = ((java.lang.String) (org.easymock.EasyMock.getCurrentArguments()[0]));
            if ("host1.example.com".equals(hostname)) {
                return "192.168.74.101";
            } else if ("host2.example.com".equals(hostname)) {
                return "192.168.74.102";
            }
            return null;
        });
        org.apache.ambari.server.security.authentication.tproxy.AmbariTProxyConfiguration tproxyConfiguration = createStrictMock(org.apache.ambari.server.security.authentication.tproxy.AmbariTProxyConfiguration.class);
        org.easymock.EasyMock.expect(tproxyConfiguration.getAllowedHosts("proxyUser")).andReturn("*");
        org.easymock.EasyMock.expect(tproxyConfiguration.getAllowedHosts("proxyUser")).andReturn("192.168.74.101");
        org.easymock.EasyMock.expect(tproxyConfiguration.getAllowedHosts("proxyUser")).andReturn("host1.example.com");
        org.easymock.EasyMock.expect(tproxyConfiguration.getAllowedHosts("proxyUser")).andReturn("192.168.74.0/24");
        org.easymock.EasyMock.expect(tproxyConfiguration.getAllowedHosts("proxyUser")).andReturn(null);
        org.easymock.EasyMock.expect(tproxyConfiguration.getAllowedHosts("proxyUser")).andReturn("");
        org.easymock.EasyMock.expect(tproxyConfiguration.getAllowedHosts("proxyUser")).andReturn("192.168.74.102");
        org.easymock.EasyMock.expect(tproxyConfiguration.getAllowedHosts("proxyUser")).andReturn("host2.example.com");
        org.easymock.EasyMock.expect(tproxyConfiguration.getAllowedHosts("proxyUser")).andReturn("192.168.74.1/32");
        org.easymock.EasyMock.replay(configuration, users, service, tproxyConfiguration);
        try {
            org.junit.Assert.assertTrue("Wildcard (*) should allow access", service.validateHost(tproxyConfiguration, "proxyUser", "192.168.74.101"));
            org.junit.Assert.assertTrue("Exact IP match should allow access", service.validateHost(tproxyConfiguration, "proxyUser", "192.168.74.101"));
            org.junit.Assert.assertTrue("Hostname match should allow access", service.validateHost(tproxyConfiguration, "proxyUser", "192.168.74.101"));
            org.junit.Assert.assertTrue("Subnet match should allow access", service.validateHost(tproxyConfiguration, "proxyUser", "192.168.74.101"));
            org.junit.Assert.assertFalse("Null should deny access", service.validateHost(tproxyConfiguration, "proxyUser", "192.168.74.101"));
            org.junit.Assert.assertFalse("Empty string should deny access", service.validateHost(tproxyConfiguration, "proxyUser", "192.168.74.101"));
            org.junit.Assert.assertFalse("Non-matching IP should deny access", service.validateHost(tproxyConfiguration, "proxyUser", "192.168.74.101"));
            org.junit.Assert.assertFalse("Non-matching hostname should deny access", service.validateHost(tproxyConfiguration, "proxyUser", "192.168.74.101"));
            org.junit.Assert.assertFalse("Non-matching subnet should deny access", service.validateHost(tproxyConfiguration, "proxyUser", "192.168.74.101"));
        } finally {
            org.easymock.EasyMock.verify(configuration, users, service, tproxyConfiguration);
        }
    }

    @org.junit.Test
    public void testValidateUser() {
        org.apache.ambari.server.security.authentication.kerberos.AmbariProxiedUserDetailsService service = new org.apache.ambari.server.security.authentication.kerberos.AmbariProxiedUserDetailsService(createNiceMock(org.apache.ambari.server.configuration.Configuration.class), createNiceMock(org.apache.ambari.server.security.authorization.Users.class));
        org.apache.ambari.server.security.authentication.tproxy.AmbariTProxyConfiguration tproxyConfigration = createMock(org.apache.ambari.server.security.authentication.tproxy.AmbariTProxyConfiguration.class);
        org.easymock.EasyMock.expect(tproxyConfigration.getAllowedUsers("proxyUser")).andReturn("*").once();
        org.easymock.EasyMock.expect(tproxyConfigration.getAllowedUsers("proxyUser")).andReturn("validUser").once();
        org.easymock.EasyMock.expect(tproxyConfigration.getAllowedUsers("proxyUser")).andReturn("validuser").once();
        org.easymock.EasyMock.expect(tproxyConfigration.getAllowedUsers("proxyUser")).andReturn("validUser, tom, *").once();
        org.easymock.EasyMock.expect(tproxyConfigration.getAllowedUsers("proxyUser")).andReturn(null).once();
        org.easymock.EasyMock.expect(tproxyConfigration.getAllowedUsers("proxyUser")).andReturn("").once();
        org.easymock.EasyMock.expect(tproxyConfigration.getAllowedUsers("proxyUser")).andReturn("notValidUser").once();
        replayAll();
        org.junit.Assert.assertTrue(service.validateUser(tproxyConfigration, "proxyUser", "validUser"));
        org.junit.Assert.assertTrue(service.validateUser(tproxyConfigration, "proxyUser", "validUser"));
        org.junit.Assert.assertTrue(service.validateUser(tproxyConfigration, "proxyUser", "validUser"));
        org.junit.Assert.assertTrue(service.validateUser(tproxyConfigration, "proxyUser", "validUser"));
        org.junit.Assert.assertFalse(service.validateUser(tproxyConfigration, "proxyUser", "validUser"));
        org.junit.Assert.assertFalse(service.validateUser(tproxyConfigration, "proxyUser", "validUser"));
        org.junit.Assert.assertFalse(service.validateUser(tproxyConfigration, "proxyUser", "validUser"));
        verifyAll();
    }

    @org.junit.Test
    public void testValidateGroup() {
        org.apache.ambari.server.security.authentication.kerberos.AmbariProxiedUserDetailsService service = new org.apache.ambari.server.security.authentication.kerberos.AmbariProxiedUserDetailsService(createNiceMock(org.apache.ambari.server.configuration.Configuration.class), createNiceMock(org.apache.ambari.server.security.authorization.Users.class));
        org.apache.ambari.server.security.authentication.tproxy.AmbariTProxyConfiguration tproxyConfigration = createMock(org.apache.ambari.server.security.authentication.tproxy.AmbariTProxyConfiguration.class);
        org.easymock.EasyMock.expect(tproxyConfigration.getAllowedGroups("proxyUser")).andReturn("*").once();
        org.easymock.EasyMock.expect(tproxyConfigration.getAllowedGroups("proxyUser")).andReturn("validGroup").once();
        org.easymock.EasyMock.expect(tproxyConfigration.getAllowedGroups("proxyUser")).andReturn("validgroup").once();
        org.easymock.EasyMock.expect(tproxyConfigration.getAllowedGroups("proxyUser")).andReturn("validGroup, *").once();
        org.easymock.EasyMock.expect(tproxyConfigration.getAllowedGroups("proxyUser")).andReturn("").once();
        org.easymock.EasyMock.expect(tproxyConfigration.getAllowedGroups("proxyUser")).andReturn(null).once();
        org.easymock.EasyMock.expect(tproxyConfigration.getAllowedGroups("proxyUser")).andReturn("notValidGroup").once();
        java.util.Set<org.apache.ambari.server.orm.entities.MemberEntity> memberEntities = new java.util.HashSet<>();
        memberEntities.add(createMockMemberEntity("validGroup"));
        memberEntities.add(createMockMemberEntity("users"));
        memberEntities.add(createMockMemberEntity(null));
        org.apache.ambari.server.orm.entities.MemberEntity memberEntity = createMock(org.apache.ambari.server.orm.entities.MemberEntity.class);
        org.easymock.EasyMock.expect(memberEntity.getGroup()).andReturn(null).anyTimes();
        memberEntities.add(memberEntity);
        org.apache.ambari.server.orm.entities.UserEntity userEntity = createMock(org.apache.ambari.server.orm.entities.UserEntity.class);
        org.easymock.EasyMock.expect(userEntity.getMemberEntities()).andReturn(memberEntities).anyTimes();
        replayAll();
        org.junit.Assert.assertTrue(service.validateGroup(tproxyConfigration, "proxyUser", userEntity));
        org.junit.Assert.assertTrue(service.validateGroup(tproxyConfigration, "proxyUser", userEntity));
        org.junit.Assert.assertTrue(service.validateGroup(tproxyConfigration, "proxyUser", userEntity));
        org.junit.Assert.assertTrue(service.validateGroup(tproxyConfigration, "proxyUser", userEntity));
        org.junit.Assert.assertFalse(service.validateGroup(tproxyConfigration, "proxyUser", userEntity));
        org.junit.Assert.assertFalse(service.validateGroup(tproxyConfigration, "proxyUser", userEntity));
        org.junit.Assert.assertFalse(service.validateGroup(tproxyConfigration, "proxyUser", userEntity));
        verifyAll();
    }

    @org.junit.Test
    public void testIsInIpAddressRange() {
        org.apache.ambari.server.security.authentication.kerberos.AmbariProxiedUserDetailsService service = new org.apache.ambari.server.security.authentication.kerberos.AmbariProxiedUserDetailsService(createNiceMock(org.apache.ambari.server.configuration.Configuration.class), createNiceMock(org.apache.ambari.server.security.authorization.Users.class));
        org.junit.Assert.assertTrue(service.isInIpAddressRange("192.168.74.10/32", "192.168.74.10"));
        org.junit.Assert.assertFalse(service.isInIpAddressRange("192.168.74.10/32", "192.168.74.11"));
        for (int i = 0; i <= 255; i++) {
            org.junit.Assert.assertTrue(service.isInIpAddressRange("192.168.1.0/24", java.lang.String.format("192.168.1.%d", i)));
        }
        org.junit.Assert.assertFalse(service.isInIpAddressRange("192.168.1.0/24", "192.168.2.100"));
    }

    private org.apache.ambari.server.orm.entities.MemberEntity createMockMemberEntity(java.lang.String groupName) {
        org.apache.ambari.server.orm.entities.GroupEntity groupEntity = createMock(org.apache.ambari.server.orm.entities.GroupEntity.class);
        org.easymock.EasyMock.expect(groupEntity.getGroupName()).andReturn(groupName).anyTimes();
        org.apache.ambari.server.orm.entities.MemberEntity memberEntity = createMock(org.apache.ambari.server.orm.entities.MemberEntity.class);
        org.easymock.EasyMock.expect(memberEntity.getGroup()).andReturn(groupEntity).anyTimes();
        return memberEntity;
    }
}
