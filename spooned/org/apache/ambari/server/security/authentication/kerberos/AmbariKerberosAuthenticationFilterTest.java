package org.apache.ambari.server.security.authentication.kerberos;
import org.easymock.Capture;
import org.easymock.CaptureType;
import org.easymock.EasyMockSupport;
import org.easymock.IAnswer;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.anyString;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.getCurrentArguments;
import static org.easymock.EasyMock.newCapture;
import static org.easymock.EasyMock.startsWith;
public class AmbariKerberosAuthenticationFilterTest extends org.easymock.EasyMockSupport {
    private org.apache.ambari.server.configuration.Configuration configuration;

    private org.springframework.security.web.AuthenticationEntryPoint entryPoint;

    private org.springframework.security.authentication.AuthenticationManager authenticationManager;

    private org.apache.ambari.server.security.authentication.AmbariAuthenticationEventHandler eventHandler;

    @org.junit.Before
    public void setUp() {
        org.springframework.security.core.context.SecurityContextHolder.getContext().setAuthentication(null);
        entryPoint = createMock(org.apache.ambari.server.security.AmbariEntryPoint.class);
        configuration = createMock(org.apache.ambari.server.configuration.Configuration.class);
        authenticationManager = createMock(org.springframework.security.authentication.AuthenticationManager.class);
        eventHandler = createMock(org.apache.ambari.server.security.authentication.AmbariAuthenticationEventHandler.class);
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void ensureNonNullEventHandler() {
        new org.apache.ambari.server.security.authentication.kerberos.AmbariKerberosAuthenticationFilter(authenticationManager, entryPoint, configuration, null);
    }

    @org.junit.Test
    public void shouldApplyTrue() throws java.lang.Exception {
        javax.servlet.http.HttpServletRequest httpServletRequest = createMock(javax.servlet.http.HttpServletRequest.class);
        org.easymock.EasyMock.expect(httpServletRequest.getHeader("Authorization")).andReturn("Negotiate .....").once();
        org.easymock.EasyMock.expect(configuration.getKerberosAuthenticationProperties()).andReturn(createProperties(true)).once();
        replayAll();
        org.apache.ambari.server.security.authentication.kerberos.AmbariKerberosAuthenticationFilter filter = new org.apache.ambari.server.security.authentication.kerberos.AmbariKerberosAuthenticationFilter(authenticationManager, entryPoint, configuration, eventHandler);
        org.junit.Assert.assertTrue(filter.shouldApply(httpServletRequest));
        verifyAll();
    }

    @org.junit.Test
    public void shouldApplyFalseMissingHeader() throws java.lang.Exception {
        javax.servlet.http.HttpServletRequest httpServletRequest = createMock(javax.servlet.http.HttpServletRequest.class);
        org.easymock.EasyMock.expect(httpServletRequest.getHeader("Authorization")).andReturn(null).once();
        org.easymock.EasyMock.expect(configuration.getKerberosAuthenticationProperties()).andReturn(createProperties(true)).once();
        replayAll();
        org.apache.ambari.server.security.authentication.kerberos.AmbariKerberosAuthenticationFilter filter = new org.apache.ambari.server.security.authentication.kerberos.AmbariKerberosAuthenticationFilter(authenticationManager, entryPoint, configuration, eventHandler);
        org.junit.Assert.assertFalse(filter.shouldApply(httpServletRequest));
        verifyAll();
    }

    @org.junit.Test
    public void shouldApplyNotFalseEnabled() throws java.lang.Exception {
        javax.servlet.http.HttpServletRequest httpServletRequest = createMock(javax.servlet.http.HttpServletRequest.class);
        org.easymock.EasyMock.expect(configuration.getKerberosAuthenticationProperties()).andReturn(createProperties(false)).once();
        replayAll();
        org.apache.ambari.server.security.authentication.kerberos.AmbariKerberosAuthenticationFilter filter = new org.apache.ambari.server.security.authentication.kerberos.AmbariKerberosAuthenticationFilter(authenticationManager, entryPoint, configuration, eventHandler);
        org.junit.Assert.assertFalse(filter.shouldApply(httpServletRequest));
        verifyAll();
    }

    @org.junit.Test
    public void testDoFilterSuccessful() throws java.io.IOException, javax.servlet.ServletException {
        org.easymock.Capture<? extends org.apache.ambari.server.security.authentication.AmbariAuthenticationFilter> captureFilter = org.easymock.EasyMock.newCapture(org.easymock.CaptureType.ALL);
        javax.servlet.http.HttpServletRequest request = createMock(javax.servlet.http.HttpServletRequest.class);
        javax.servlet.http.HttpServletResponse response = createMock(javax.servlet.http.HttpServletResponse.class);
        javax.servlet.http.HttpSession session = createMock(javax.servlet.http.HttpSession.class);
        javax.servlet.FilterChain filterChain = createMock(javax.servlet.FilterChain.class);
        org.easymock.EasyMock.expect(request.getHeader("Authorization")).andReturn("Negotiate ").once();
        org.easymock.EasyMock.expect(request.getHeader(org.easymock.EasyMock.startsWith("X-Forwarded-"))).andReturn(null).times(6);
        org.easymock.EasyMock.expect(request.getRemoteAddr()).andReturn("1.2.3.4").once();
        org.easymock.EasyMock.expect(request.getSession(false)).andReturn(session).once();
        org.easymock.EasyMock.expect(request.getQueryString()).andReturn(null).once();
        org.easymock.EasyMock.expect(request.getParameter(org.easymock.EasyMock.anyString())).andReturn(null).anyTimes();
        org.easymock.EasyMock.expect(session.getId()).andReturn("sessionID").once();
        org.easymock.EasyMock.expect(authenticationManager.authenticate(org.easymock.EasyMock.anyObject(org.springframework.security.core.Authentication.class))).andAnswer(new org.easymock.IAnswer<org.springframework.security.core.Authentication>() {
            @java.lang.Override
            public org.springframework.security.core.Authentication answer() throws java.lang.Throwable {
                return ((org.springframework.security.core.Authentication) (org.easymock.EasyMock.getCurrentArguments()[0]));
            }
        }).anyTimes();
        org.easymock.EasyMock.expect(configuration.getKerberosAuthenticationProperties()).andReturn(createProperties(true)).once();
        eventHandler.beforeAttemptAuthentication(org.easymock.EasyMock.capture(captureFilter), org.easymock.EasyMock.eq(request), org.easymock.EasyMock.eq(response));
        org.easymock.EasyMock.expectLastCall().once();
        eventHandler.onSuccessfulAuthentication(org.easymock.EasyMock.capture(captureFilter), org.easymock.EasyMock.eq(request), org.easymock.EasyMock.eq(response), org.easymock.EasyMock.anyObject(org.springframework.security.core.Authentication.class));
        org.easymock.EasyMock.expectLastCall().once();
        filterChain.doFilter(request, response);
        org.easymock.EasyMock.expectLastCall().once();
        replayAll();
        org.apache.ambari.server.security.authentication.AmbariAuthenticationFilter filter = new org.apache.ambari.server.security.authentication.kerberos.AmbariKerberosAuthenticationFilter(authenticationManager, entryPoint, configuration, eventHandler);
        filter.doFilter(request, response, filterChain);
        verifyAll();
        java.util.List<? extends org.apache.ambari.server.security.authentication.AmbariAuthenticationFilter> capturedFilters = captureFilter.getValues();
        for (org.apache.ambari.server.security.authentication.AmbariAuthenticationFilter capturedFiltered : capturedFilters) {
            org.junit.Assert.assertSame(filter, capturedFiltered);
        }
    }

    @org.junit.Test
    public void testDoFilterUnsuccessful() throws java.io.IOException, javax.servlet.ServletException {
        org.easymock.Capture<? extends org.apache.ambari.server.security.authentication.AmbariAuthenticationFilter> captureFilter = org.easymock.EasyMock.newCapture(org.easymock.CaptureType.ALL);
        javax.servlet.http.HttpServletRequest request = createMock(javax.servlet.http.HttpServletRequest.class);
        javax.servlet.http.HttpServletResponse response = createMock(javax.servlet.http.HttpServletResponse.class);
        javax.servlet.http.HttpSession session = createMock(javax.servlet.http.HttpSession.class);
        javax.servlet.FilterChain filterChain = createMock(javax.servlet.FilterChain.class);
        org.easymock.EasyMock.expect(request.getHeader("Authorization")).andReturn("Negotiate ").once();
        org.easymock.EasyMock.expect(request.getHeader(org.easymock.EasyMock.startsWith("X-Forwarded-"))).andReturn(null).times(6);
        org.easymock.EasyMock.expect(request.getRemoteAddr()).andReturn("1.2.3.4").once();
        org.easymock.EasyMock.expect(request.getSession(false)).andReturn(session).once();
        org.easymock.EasyMock.expect(request.getQueryString()).andReturn(null).once();
        org.easymock.EasyMock.expect(request.getParameter(org.easymock.EasyMock.anyString())).andReturn(null).anyTimes();
        org.easymock.EasyMock.expect(session.getId()).andReturn("sessionID").once();
        org.easymock.EasyMock.expect(authenticationManager.authenticate(org.easymock.EasyMock.anyObject(org.springframework.security.core.Authentication.class))).andThrow(new org.apache.ambari.server.security.authentication.InvalidUsernamePasswordCombinationException("user")).once();
        org.easymock.EasyMock.expect(configuration.getKerberosAuthenticationProperties()).andReturn(createProperties(true)).once();
        eventHandler.beforeAttemptAuthentication(org.easymock.EasyMock.capture(captureFilter), org.easymock.EasyMock.eq(request), org.easymock.EasyMock.eq(response));
        org.easymock.EasyMock.expectLastCall().once();
        eventHandler.onUnsuccessfulAuthentication(org.easymock.EasyMock.capture(captureFilter), org.easymock.EasyMock.eq(request), org.easymock.EasyMock.eq(response), org.easymock.EasyMock.anyObject(org.apache.ambari.server.security.authentication.AmbariAuthenticationException.class));
        org.easymock.EasyMock.expectLastCall().once();
        entryPoint.commence(org.easymock.EasyMock.eq(request), org.easymock.EasyMock.eq(response), org.easymock.EasyMock.anyObject(org.apache.ambari.server.security.authentication.AmbariAuthenticationException.class));
        org.easymock.EasyMock.expectLastCall().once();
        replayAll();
        org.apache.ambari.server.security.authentication.AmbariAuthenticationFilter filter = new org.apache.ambari.server.security.authentication.kerberos.AmbariKerberosAuthenticationFilter(authenticationManager, entryPoint, configuration, eventHandler);
        filter.doFilter(request, response, filterChain);
        verifyAll();
        java.util.List<? extends org.apache.ambari.server.security.authentication.AmbariAuthenticationFilter> capturedFilters = captureFilter.getValues();
        for (org.apache.ambari.server.security.authentication.AmbariAuthenticationFilter capturedFiltered : capturedFilters) {
            org.junit.Assert.assertSame(filter, capturedFiltered);
        }
    }

    private org.apache.ambari.server.security.authentication.kerberos.AmbariKerberosAuthenticationProperties createProperties(java.lang.Boolean enabled) {
        org.apache.ambari.server.security.authentication.kerberos.AmbariKerberosAuthenticationProperties properties = createMock(org.apache.ambari.server.security.authentication.kerberos.AmbariKerberosAuthenticationProperties.class);
        org.easymock.EasyMock.expect(properties.isKerberosAuthenticationEnabled()).andReturn(enabled).once();
        return properties;
    }
}
