package org.apache.ambari.server.api;
import org.apache.http.HttpStatus;
import org.easymock.Capture;
import org.easymock.EasyMock;
import static org.easymock.EasyMock.captureBoolean;
import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
public class AmbariViewErrorHandlerProxyTest {
    final org.apache.ambari.server.api.AmbariErrorHandler ambariErrorHandler = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.AmbariErrorHandler.class);

    final org.eclipse.jetty.server.handler.ErrorHandler errorHandler = org.easymock.EasyMock.createNiceMock(org.eclipse.jetty.server.handler.ErrorHandler.class);

    final javax.servlet.http.HttpServletRequest httpServletRequest = org.easymock.EasyMock.createNiceMock(javax.servlet.http.HttpServletRequest.class);

    final javax.servlet.http.HttpServletResponse httpServletResponse = org.easymock.EasyMock.createNiceMock(javax.servlet.http.HttpServletResponse.class);

    final org.eclipse.jetty.server.Request request = org.easymock.EasyMock.createNiceMock(org.eclipse.jetty.server.Request.class);

    final java.lang.String target = "test/target/uri";

    @org.junit.Test
    public void testHandleInternalServerError() throws java.lang.Throwable {
        java.lang.Throwable th = org.easymock.EasyMock.createNiceMock(java.lang.Throwable.class);
        org.easymock.EasyMock.expect(httpServletRequest.getAttribute(javax.servlet.RequestDispatcher.ERROR_EXCEPTION)).andReturn(th).anyTimes();
        org.easymock.EasyMock.expect(httpServletResponse.getStatus()).andReturn(org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR).anyTimes();
        ambariErrorHandler.handle(target, request, httpServletRequest, httpServletResponse);
        org.easymock.EasyMock.expectLastCall();
        org.easymock.EasyMock.replay(ambariErrorHandler, errorHandler, httpServletRequest, httpServletResponse, th);
        org.apache.ambari.server.api.AmbariViewErrorHandlerProxy proxy = new org.apache.ambari.server.api.AmbariViewErrorHandlerProxy(errorHandler, ambariErrorHandler);
        proxy.handle(target, request, httpServletRequest, httpServletResponse);
        org.easymock.EasyMock.verify(ambariErrorHandler, errorHandler, httpServletRequest, httpServletResponse, th);
    }

    @org.junit.Test
    public void testHandleGeneralError() throws java.lang.Throwable {
        java.lang.Throwable th = org.easymock.EasyMock.createNiceMock(java.lang.Throwable.class);
        org.easymock.EasyMock.expect(httpServletRequest.getAttribute(javax.servlet.RequestDispatcher.ERROR_EXCEPTION)).andReturn(th).anyTimes();
        org.easymock.EasyMock.expect(httpServletResponse.getStatus()).andReturn(org.apache.http.HttpStatus.SC_BAD_REQUEST).anyTimes();
        errorHandler.handle(target, request, httpServletRequest, httpServletResponse);
        org.easymock.EasyMock.expectLastCall();
        org.easymock.EasyMock.replay(ambariErrorHandler, errorHandler, httpServletRequest, httpServletResponse, th);
        org.apache.ambari.server.api.AmbariViewErrorHandlerProxy proxy = new org.apache.ambari.server.api.AmbariViewErrorHandlerProxy(errorHandler, ambariErrorHandler);
        proxy.handle(target, request, httpServletRequest, httpServletResponse);
        org.easymock.EasyMock.verify(ambariErrorHandler, errorHandler, httpServletRequest, httpServletResponse, th);
    }

    @org.junit.Test
    public void testShowStacks() {
        org.easymock.Capture<java.lang.Boolean> captureShowStacksErrorHandler = org.easymock.EasyMock.newCapture();
        errorHandler.setShowStacks(org.easymock.EasyMock.captureBoolean(captureShowStacksErrorHandler));
        org.easymock.EasyMock.expectLastCall();
        org.easymock.Capture<java.lang.Boolean> captureShowStacksAmbariErrorHandler = org.easymock.EasyMock.newCapture();
        ambariErrorHandler.setShowStacks(org.easymock.EasyMock.captureBoolean(captureShowStacksAmbariErrorHandler));
        org.easymock.EasyMock.expectLastCall();
        org.easymock.EasyMock.replay(errorHandler, ambariErrorHandler);
        org.apache.ambari.server.api.AmbariViewErrorHandlerProxy proxy = new org.apache.ambari.server.api.AmbariViewErrorHandlerProxy(errorHandler, ambariErrorHandler);
        proxy.setShowStacks(true);
        junit.framework.Assert.assertTrue(captureShowStacksErrorHandler.getValue());
        junit.framework.Assert.assertTrue(captureShowStacksAmbariErrorHandler.getValue());
        org.easymock.EasyMock.verify(errorHandler, ambariErrorHandler);
    }
}
