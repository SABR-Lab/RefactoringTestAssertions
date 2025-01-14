package org.apache.ambari.server.security.authentication.kerberos;
import org.easymock.EasyMockSupport;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
public class AmbariAuthToLocalUserDetailsServiceTest extends org.easymock.EasyMockSupport {
    @org.junit.Before
    public void setup() {
        java.lang.System.setProperty("java.security.krb5.realm", "EXAMPLE.COM");
        java.lang.System.setProperty("java.security.krb5.kdc", "localhost");
    }

    @org.junit.Test
    public void loadUserByUsernameSuccess() throws java.lang.Exception {
        org.apache.ambari.server.security.authentication.kerberos.AmbariKerberosAuthenticationProperties properties = new org.apache.ambari.server.security.authentication.kerberos.AmbariKerberosAuthenticationProperties();
        org.apache.ambari.server.configuration.Configuration configuration = createMock(org.apache.ambari.server.configuration.Configuration.class);
        org.easymock.EasyMock.expect(configuration.getKerberosAuthenticationProperties()).andReturn(properties).once();
        org.apache.ambari.server.orm.entities.PrincipalEntity principal = createMock(org.apache.ambari.server.orm.entities.PrincipalEntity.class);
        org.easymock.EasyMock.expect(principal.getPrivileges()).andReturn(java.util.Collections.emptySet()).atLeastOnce();
        org.apache.ambari.server.orm.entities.UserEntity userEntity = createMock(org.apache.ambari.server.orm.entities.UserEntity.class);
        org.easymock.EasyMock.expect(userEntity.getUserName()).andReturn("user1").atLeastOnce();
        org.easymock.EasyMock.expect(userEntity.getUserId()).andReturn(1).atLeastOnce();
        org.easymock.EasyMock.expect(userEntity.getCreateTime()).andReturn(java.lang.System.currentTimeMillis()).atLeastOnce();
        org.easymock.EasyMock.expect(userEntity.getActive()).andReturn(true).atLeastOnce();
        org.easymock.EasyMock.expect(userEntity.getMemberEntities()).andReturn(java.util.Collections.emptySet()).atLeastOnce();
        org.easymock.EasyMock.expect(userEntity.getAuthenticationEntities()).andReturn(java.util.Collections.emptyList()).atLeastOnce();
        org.easymock.EasyMock.expect(userEntity.getPrincipal()).andReturn(principal).atLeastOnce();
        org.apache.ambari.server.orm.entities.UserAuthenticationEntity kerberosAuthenticationEntity = createMock(org.apache.ambari.server.orm.entities.UserAuthenticationEntity.class);
        org.easymock.EasyMock.expect(kerberosAuthenticationEntity.getAuthenticationType()).andReturn(org.apache.ambari.server.security.authorization.UserAuthenticationType.KERBEROS).anyTimes();
        org.easymock.EasyMock.expect(kerberosAuthenticationEntity.getAuthenticationKey()).andReturn("user1@EXAMPLE.COM").anyTimes();
        org.easymock.EasyMock.expect(kerberosAuthenticationEntity.getUser()).andReturn(userEntity).anyTimes();
        java.util.Collection<org.apache.ambari.server.security.authorization.AmbariGrantedAuthority> userAuthorities = java.util.Collections.singletonList(createNiceMock(org.apache.ambari.server.security.authorization.AmbariGrantedAuthority.class));
        org.apache.ambari.server.security.authorization.Users users = createMock(org.apache.ambari.server.security.authorization.Users.class);
        org.easymock.EasyMock.expect(users.getUserAuthorities(userEntity)).andReturn(userAuthorities).atLeastOnce();
        org.easymock.EasyMock.expect(users.getUserAuthenticationEntities(org.apache.ambari.server.security.authorization.UserAuthenticationType.KERBEROS, "user1@EXAMPLE.COM")).andReturn(java.util.Collections.singleton(kerberosAuthenticationEntity)).atLeastOnce();
        users.validateLogin(userEntity, "user1");
        org.easymock.EasyMock.expectLastCall().once();
        replayAll();
        org.springframework.security.core.userdetails.UserDetailsService userdetailsService = new org.apache.ambari.server.security.authentication.kerberos.AmbariAuthToLocalUserDetailsService(configuration, users);
        org.springframework.security.core.userdetails.UserDetails userDetails = userdetailsService.loadUserByUsername("user1@EXAMPLE.COM");
        junit.framework.Assert.assertNotNull(userDetails);
        junit.framework.Assert.assertTrue(userDetails instanceof org.apache.ambari.server.security.authentication.AmbariUserDetails);
        org.apache.ambari.server.security.authentication.AmbariUserDetails ambariUserDetails = ((org.apache.ambari.server.security.authentication.AmbariUserDetails) (userDetails));
        junit.framework.Assert.assertEquals("user1", ambariUserDetails.getUsername());
        junit.framework.Assert.assertEquals(java.lang.Integer.valueOf(1), ambariUserDetails.getUserId());
        junit.framework.Assert.assertEquals(userAuthorities.size(), ambariUserDetails.getAuthorities().size());
        verifyAll();
    }

    @org.junit.Test(expected = org.apache.ambari.server.security.authentication.UserNotFoundException.class)
    public void loadUserByUsernameUserNotFound() throws java.lang.Exception {
        org.apache.ambari.server.security.authentication.kerberos.AmbariKerberosAuthenticationProperties properties = new org.apache.ambari.server.security.authentication.kerberos.AmbariKerberosAuthenticationProperties();
        org.apache.ambari.server.configuration.Configuration configuration = createMock(org.apache.ambari.server.configuration.Configuration.class);
        org.easymock.EasyMock.expect(configuration.getKerberosAuthenticationProperties()).andReturn(properties).once();
        org.apache.ambari.server.security.authorization.Users users = createMock(org.apache.ambari.server.security.authorization.Users.class);
        org.easymock.EasyMock.expect(users.getUserEntity("user1")).andReturn(null).times(2);
        org.easymock.EasyMock.expect(users.getUserAuthenticationEntities(org.apache.ambari.server.security.authorization.UserAuthenticationType.KERBEROS, "user1@EXAMPLE.COM")).andReturn(null).atLeastOnce();
        replayAll();
        org.springframework.security.core.userdetails.UserDetailsService userdetailsService = new org.apache.ambari.server.security.authentication.kerberos.AmbariAuthToLocalUserDetailsService(configuration, users);
        userdetailsService.loadUserByUsername("user1@EXAMPLE.COM");
        verifyAll();
        junit.framework.Assert.fail("UsernameNotFoundException was not thrown");
    }
}
