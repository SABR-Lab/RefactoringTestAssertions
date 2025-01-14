package org.apache.ambari.server.controller;
import org.easymock.Capture;
import org.easymock.EasyMock;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
public class AmbariHandlerListTest {
    private final org.apache.ambari.server.security.AmbariViewsSecurityHeaderFilter ambariViewsSecurityHeaderFilter = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.security.AmbariViewsSecurityHeaderFilter.class);

    private final org.apache.ambari.server.api.AmbariPersistFilter persistFilter = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.api.AmbariPersistFilter.class);

    private final org.springframework.web.filter.DelegatingFilterProxy springSecurityFilter = org.easymock.EasyMock.createNiceMock(org.springframework.web.filter.DelegatingFilterProxy.class);

    private final org.eclipse.jetty.server.session.SessionHandler sessionHandler = org.easymock.EasyMock.createNiceMock(org.eclipse.jetty.server.session.SessionHandler.class);

    private final org.eclipse.jetty.server.SessionIdManager sessionIdManager = org.easymock.EasyMock.createNiceMock(org.eclipse.jetty.server.SessionIdManager.class);

    private final org.apache.ambari.server.controller.SessionHandlerConfigurer sessionHandlerConfigurer = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.SessionHandlerConfigurer.class);

    private final org.eclipse.jetty.server.session.SessionCache sessionCache = org.easymock.EasyMock.createNiceMock(org.eclipse.jetty.server.session.SessionCache.class);

    private final org.apache.ambari.server.configuration.Configuration configuration = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.configuration.Configuration.class);

    @org.junit.Test
    public void testAddViewInstance() throws java.lang.Exception {
        org.apache.ambari.server.orm.entities.ViewInstanceEntity viewInstanceEntity = org.apache.ambari.server.orm.entities.ViewInstanceEntityTest.getViewInstanceEntity();
        final org.eclipse.jetty.webapp.WebAppContext handler = org.easymock.EasyMock.createNiceMock(org.eclipse.jetty.webapp.WebAppContext.class);
        org.eclipse.jetty.server.Server server = org.easymock.EasyMock.createNiceMock(org.eclipse.jetty.server.Server.class);
        org.easymock.EasyMock.expect(handler.getServer()).andReturn(server);
        org.easymock.EasyMock.expect(handler.getChildHandlers()).andReturn(new org.eclipse.jetty.server.Handler[]{  });
        org.easymock.EasyMock.expect(handler.getSessionHandler()).andReturn(org.easymock.EasyMock.createNiceMock(org.eclipse.jetty.server.session.SessionHandler.class));
        handler.setServer(null);
        org.easymock.EasyMock.expect(sessionHandler.getSessionCache()).andReturn(sessionCache);
        org.easymock.Capture<org.eclipse.jetty.servlet.FilterHolder> securityHeaderFilterCapture = org.easymock.EasyMock.newCapture();
        org.easymock.Capture<org.eclipse.jetty.servlet.FilterHolder> persistFilterCapture = org.easymock.EasyMock.newCapture();
        org.easymock.Capture<org.eclipse.jetty.servlet.FilterHolder> securityFilterCapture = org.easymock.EasyMock.newCapture();
        handler.addFilter(org.easymock.EasyMock.capture(securityHeaderFilterCapture), org.easymock.EasyMock.eq("/*"), org.easymock.EasyMock.eq(org.apache.ambari.server.controller.AmbariServer.DISPATCHER_TYPES));
        handler.addFilter(org.easymock.EasyMock.capture(persistFilterCapture), org.easymock.EasyMock.eq("/*"), org.easymock.EasyMock.eq(org.apache.ambari.server.controller.AmbariServer.DISPATCHER_TYPES));
        handler.addFilter(org.easymock.EasyMock.capture(securityFilterCapture), org.easymock.EasyMock.eq("/*"), org.easymock.EasyMock.eq(org.apache.ambari.server.controller.AmbariServer.DISPATCHER_TYPES));
        handler.setAllowNullPathInfo(true);
        final boolean showErrorStacks = true;
        org.easymock.EasyMock.expect(configuration.isServerShowErrorStacks()).andReturn(showErrorStacks);
        org.eclipse.jetty.server.handler.ErrorHandler errorHandler = org.easymock.EasyMock.createNiceMock(org.eclipse.jetty.server.handler.ErrorHandler.class);
        org.easymock.Capture<java.lang.Boolean> showStackCapture = org.easymock.EasyMock.newCapture();
        errorHandler.setShowStacks(org.easymock.EasyMock.captureBoolean(showStackCapture));
        org.easymock.EasyMock.expect(handler.getErrorHandler()).andReturn(errorHandler).times(3);
        org.easymock.EasyMock.replay(handler, server, sessionHandler, configuration, errorHandler);
        org.apache.ambari.server.controller.AmbariHandlerList handlerList = getAmbariHandlerList(handler);
        handlerList.addViewInstance(viewInstanceEntity);
        java.util.ArrayList<org.eclipse.jetty.server.Handler> handlers = new java.util.ArrayList<>(java.util.Arrays.asList(handlerList.getHandlers()));
        org.junit.Assert.assertTrue(handlers.contains(handler));
        org.junit.Assert.assertEquals(ambariViewsSecurityHeaderFilter, securityHeaderFilterCapture.getValue().getFilter());
        org.junit.Assert.assertEquals(persistFilter, persistFilterCapture.getValue().getFilter());
        org.junit.Assert.assertEquals(springSecurityFilter, securityFilterCapture.getValue().getFilter());
        org.junit.Assert.assertEquals(showErrorStacks, showStackCapture.getValue());
        org.easymock.EasyMock.verify(handler, server, sessionHandler, configuration, errorHandler);
    }

    @org.junit.Test
    public void testRemoveViewInstance() throws java.lang.Exception {
        org.apache.ambari.server.orm.entities.ViewInstanceEntity viewInstanceEntity = org.apache.ambari.server.orm.entities.ViewInstanceEntityTest.getViewInstanceEntity();
        final org.eclipse.jetty.webapp.WebAppContext handler = org.easymock.EasyMock.createNiceMock(org.eclipse.jetty.webapp.WebAppContext.class);
        org.eclipse.jetty.server.Server server = org.easymock.EasyMock.createNiceMock(org.eclipse.jetty.server.Server.class);
        org.easymock.EasyMock.expect(handler.getServer()).andReturn(server);
        org.easymock.EasyMock.expect(handler.getChildHandlers()).andReturn(new org.eclipse.jetty.server.Handler[]{  });
        org.easymock.EasyMock.expect(handler.getSessionHandler()).andReturn(org.easymock.EasyMock.createNiceMock(org.eclipse.jetty.server.session.SessionHandler.class));
        handler.setServer(null);
        org.easymock.EasyMock.expect(sessionHandler.getSessionCache()).andReturn(sessionCache);
        org.easymock.EasyMock.replay(handler, server, sessionHandler);
        org.apache.ambari.server.controller.AmbariHandlerList handlerList = getAmbariHandlerList(handler);
        handlerList.addViewInstance(viewInstanceEntity);
        java.util.ArrayList<org.eclipse.jetty.server.Handler> handlers = new java.util.ArrayList<>(java.util.Arrays.asList(handlerList.getHandlers()));
        org.junit.Assert.assertTrue(handlers.contains(handler));
        handlerList.removeViewInstance(viewInstanceEntity);
        handlers = new java.util.ArrayList<>(java.util.Arrays.asList(handlerList.getHandlers()));
        org.junit.Assert.assertFalse(handlers.contains(handler));
        org.easymock.EasyMock.verify(handler, server, sessionHandler);
    }

    @org.junit.Test
    public void testHandle() throws java.lang.Exception {
        final org.eclipse.jetty.webapp.WebAppContext handler = org.easymock.EasyMock.createNiceMock(org.eclipse.jetty.webapp.WebAppContext.class);
        org.apache.ambari.server.view.ViewRegistry viewRegistry = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.view.ViewRegistry.class);
        org.apache.ambari.server.orm.entities.ViewEntity viewEntity = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.orm.entities.ViewEntity.class);
        java.lang.ClassLoader classLoader = org.easymock.EasyMock.createNiceMock(java.lang.ClassLoader.class);
        org.eclipse.jetty.server.Request baseRequest = org.easymock.EasyMock.createNiceMock(org.eclipse.jetty.server.Request.class);
        javax.servlet.http.HttpServletRequest request = org.easymock.EasyMock.createNiceMock(javax.servlet.http.HttpServletRequest.class);
        javax.servlet.http.HttpServletResponse response = org.easymock.EasyMock.createNiceMock(javax.servlet.http.HttpServletResponse.class);
        org.easymock.EasyMock.expect(viewRegistry.getDefinition("TEST", "1.0.0")).andReturn(viewEntity).anyTimes();
        org.easymock.EasyMock.expect(viewEntity.getClassLoader()).andReturn(classLoader).anyTimes();
        org.easymock.EasyMock.expect(handler.isStarted()).andReturn(true).anyTimes();
        org.easymock.EasyMock.expect(handler.getChildHandlers()).andReturn(new org.eclipse.jetty.server.Handler[]{  });
        org.easymock.EasyMock.replay(handler, viewRegistry, viewEntity);
        handler.handle("/api/v1/views/TEST/versions/1.0.0/instances/INSTANCE_1/resources/test", baseRequest, request, response);
        org.apache.ambari.server.controller.AmbariHandlerList handlerList = getAmbariHandlerList(handler);
        handlerList.viewRegistry = viewRegistry;
        handlerList.start();
        handlerList.addHandler(handler);
        handlerList.handle("/api/v1/views/TEST/versions/1.0.0/instances/INSTANCE_1/resources/test", baseRequest, request, response);
        org.easymock.EasyMock.verify(handler, viewRegistry, viewEntity);
    }

    private org.apache.ambari.server.controller.AmbariHandlerList getAmbariHandlerList(final org.eclipse.jetty.webapp.WebAppContext handler) {
        org.apache.ambari.server.controller.AmbariHandlerList handlerList = new org.apache.ambari.server.controller.AmbariHandlerList();
        sessionHandler.setSessionIdManager(sessionIdManager);
        handlerList.webAppContextProvider = new org.apache.ambari.server.controller.AmbariHandlerListTest.HandlerProvider(handler);
        handlerList.ambariViewsSecurityHeaderFilter = ambariViewsSecurityHeaderFilter;
        handlerList.persistFilter = persistFilter;
        handlerList.springSecurityFilter = springSecurityFilter;
        handlerList.sessionHandler = sessionHandler;
        handlerList.sessionHandlerConfigurer = sessionHandlerConfigurer;
        handlerList.configuration = configuration;
        return handlerList;
    }

    private static class HandlerProvider implements javax.inject.Provider<org.eclipse.jetty.webapp.WebAppContext> {
        private final org.eclipse.jetty.webapp.WebAppContext context;

        private HandlerProvider(org.eclipse.jetty.webapp.WebAppContext context) {
            this.context = context;
        }

        @java.lang.Override
        public org.eclipse.jetty.webapp.WebAppContext get() {
            return context;
        }
    }
}
