package org.apache.ambari.server.view;
import static org.easymock.EasyMock.aryEq;
import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
public class ViewAmbariStreamProviderTest {
    @org.junit.Test
    public void testReadFrom() throws java.lang.Exception {
        org.apache.ambari.server.controller.internal.URLStreamProvider streamProvider = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.internal.URLStreamProvider.class);
        org.apache.ambari.server.controller.AmbariSessionManager sessionManager = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.AmbariSessionManager.class);
        org.apache.ambari.server.controller.AmbariManagementController controller = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.AmbariManagementController.class);
        java.net.HttpURLConnection urlConnection = org.easymock.EasyMock.createNiceMock(java.net.HttpURLConnection.class);
        java.io.InputStream inputStream = org.easymock.EasyMock.createNiceMock(java.io.InputStream.class);
        java.util.Map<java.lang.String, java.lang.String> headers = new java.util.HashMap<>();
        headers.put("header", "headerValue");
        headers.put("Cookie", "FOO=bar");
        java.util.Map<java.lang.String, java.util.List<java.lang.String>> headerMap = new java.util.HashMap<>();
        headerMap.put("header", java.util.Collections.singletonList("headerValue"));
        headerMap.put("Cookie", java.util.Collections.singletonList("FOO=bar; AMBARISESSIONID=abcdefg"));
        org.easymock.EasyMock.expect(sessionManager.getCurrentSessionId()).andReturn("abcdefg");
        org.easymock.EasyMock.expect(sessionManager.getSessionCookie()).andReturn("AMBARISESSIONID");
        org.easymock.EasyMock.expect(controller.getAmbariServerURI("/spec")).andReturn("http://c6401.ambari.apache.org:8080/spec");
        org.easymock.EasyMock.expect(streamProvider.processURL(org.easymock.EasyMock.eq("http://c6401.ambari.apache.org:8080/spec"), org.easymock.EasyMock.eq("requestMethod"), org.easymock.EasyMock.aryEq("params".getBytes()), org.easymock.EasyMock.eq(headerMap))).andReturn(urlConnection);
        org.easymock.EasyMock.expect(urlConnection.getInputStream()).andReturn(inputStream);
        org.easymock.EasyMock.replay(streamProvider, sessionManager, controller, urlConnection, inputStream);
        org.apache.ambari.server.view.ViewAmbariStreamProvider viewAmbariStreamProvider = new org.apache.ambari.server.view.ViewAmbariStreamProvider(streamProvider, sessionManager, controller);
        org.junit.Assert.assertEquals(inputStream, viewAmbariStreamProvider.readFrom("spec", "requestMethod", "params", headers));
        org.easymock.EasyMock.verify(streamProvider, sessionManager, urlConnection, inputStream);
    }

    @org.junit.Test
    public void testReadFromNew() throws java.lang.Exception {
        org.apache.ambari.server.controller.internal.URLStreamProvider streamProvider = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.internal.URLStreamProvider.class);
        org.apache.ambari.server.controller.AmbariSessionManager sessionManager = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.AmbariSessionManager.class);
        org.apache.ambari.server.controller.AmbariManagementController controller = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.AmbariManagementController.class);
        java.net.HttpURLConnection urlConnection = org.easymock.EasyMock.createNiceMock(java.net.HttpURLConnection.class);
        java.io.InputStream inputStream = org.easymock.EasyMock.createNiceMock(java.io.InputStream.class);
        java.io.InputStream body = new java.io.ByteArrayInputStream("params".getBytes());
        java.util.Map<java.lang.String, java.lang.String> headers = new java.util.HashMap<>();
        headers.put("header", "headerValue");
        headers.put("Cookie", "FOO=bar");
        java.util.Map<java.lang.String, java.util.List<java.lang.String>> headerMap = new java.util.HashMap<>();
        headerMap.put("header", java.util.Collections.singletonList("headerValue"));
        headerMap.put("Cookie", java.util.Collections.singletonList("FOO=bar; AMBARISESSIONID=abcdefg"));
        org.easymock.EasyMock.expect(sessionManager.getCurrentSessionId()).andReturn("abcdefg");
        org.easymock.EasyMock.expect(sessionManager.getSessionCookie()).andReturn("AMBARISESSIONID");
        org.easymock.EasyMock.expect(controller.getAmbariServerURI("/spec")).andReturn("http://c6401.ambari.apache.org:8080/spec");
        org.easymock.EasyMock.expect(streamProvider.processURL(org.easymock.EasyMock.eq("http://c6401.ambari.apache.org:8080/spec"), org.easymock.EasyMock.eq("requestMethod"), org.easymock.EasyMock.aryEq("params".getBytes()), org.easymock.EasyMock.eq(headerMap))).andReturn(urlConnection);
        org.easymock.EasyMock.expect(urlConnection.getInputStream()).andReturn(inputStream);
        org.easymock.EasyMock.replay(streamProvider, sessionManager, controller, urlConnection, inputStream);
        org.apache.ambari.server.view.ViewAmbariStreamProvider viewAmbariStreamProvider = new org.apache.ambari.server.view.ViewAmbariStreamProvider(streamProvider, sessionManager, controller);
        org.junit.Assert.assertEquals(inputStream, viewAmbariStreamProvider.readFrom("spec", "requestMethod", body, headers));
        org.easymock.EasyMock.verify(streamProvider, sessionManager, urlConnection, inputStream);
    }

    @org.junit.Test
    public void testReadFromNullStringBody() throws java.lang.Exception {
        org.apache.ambari.server.controller.internal.URLStreamProvider streamProvider = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.internal.URLStreamProvider.class);
        org.apache.ambari.server.controller.AmbariSessionManager sessionManager = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.AmbariSessionManager.class);
        org.apache.ambari.server.controller.AmbariManagementController controller = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.AmbariManagementController.class);
        java.net.HttpURLConnection urlConnection = org.easymock.EasyMock.createNiceMock(java.net.HttpURLConnection.class);
        java.io.InputStream inputStream = org.easymock.EasyMock.createNiceMock(java.io.InputStream.class);
        java.lang.String body = null;
        java.util.Map<java.lang.String, java.lang.String> headers = new java.util.HashMap<>();
        headers.put("header", "headerValue");
        headers.put("Cookie", "FOO=bar");
        java.util.Map<java.lang.String, java.util.List<java.lang.String>> headerMap = new java.util.HashMap<>();
        headerMap.put("header", java.util.Collections.singletonList("headerValue"));
        headerMap.put("Cookie", java.util.Collections.singletonList("FOO=bar; AMBARISESSIONID=abcdefg"));
        org.easymock.EasyMock.expect(sessionManager.getCurrentSessionId()).andReturn("abcdefg");
        org.easymock.EasyMock.expect(sessionManager.getSessionCookie()).andReturn("AMBARISESSIONID");
        org.easymock.EasyMock.expect(controller.getAmbariServerURI("/spec")).andReturn("http://c6401.ambari.apache.org:8080/spec");
        org.easymock.EasyMock.expect(streamProvider.processURL(org.easymock.EasyMock.eq("http://c6401.ambari.apache.org:8080/spec"), org.easymock.EasyMock.eq("requestMethod"), org.easymock.EasyMock.aryEq(((byte[]) (null))), org.easymock.EasyMock.eq(headerMap))).andReturn(urlConnection);
        org.easymock.EasyMock.expect(urlConnection.getInputStream()).andReturn(inputStream);
        org.easymock.EasyMock.replay(streamProvider, sessionManager, controller, urlConnection, inputStream);
        org.apache.ambari.server.view.ViewAmbariStreamProvider viewAmbariStreamProvider = new org.apache.ambari.server.view.ViewAmbariStreamProvider(streamProvider, sessionManager, controller);
        org.junit.Assert.assertEquals(inputStream, viewAmbariStreamProvider.readFrom("spec", "requestMethod", body, headers));
        org.easymock.EasyMock.verify(streamProvider, sessionManager, urlConnection, inputStream);
    }

    @org.junit.Test
    public void testReadFromNullInputStreamBody() throws java.lang.Exception {
        org.apache.ambari.server.controller.internal.URLStreamProvider streamProvider = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.internal.URLStreamProvider.class);
        org.apache.ambari.server.controller.AmbariSessionManager sessionManager = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.AmbariSessionManager.class);
        org.apache.ambari.server.controller.AmbariManagementController controller = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.AmbariManagementController.class);
        java.net.HttpURLConnection urlConnection = org.easymock.EasyMock.createNiceMock(java.net.HttpURLConnection.class);
        java.io.InputStream inputStream = org.easymock.EasyMock.createNiceMock(java.io.InputStream.class);
        java.io.InputStream body = null;
        java.util.Map<java.lang.String, java.lang.String> headers = new java.util.HashMap<>();
        headers.put("header", "headerValue");
        headers.put("Cookie", "FOO=bar");
        java.util.Map<java.lang.String, java.util.List<java.lang.String>> headerMap = new java.util.HashMap<>();
        headerMap.put("header", java.util.Collections.singletonList("headerValue"));
        headerMap.put("Cookie", java.util.Collections.singletonList("FOO=bar; AMBARISESSIONID=abcdefg"));
        org.easymock.EasyMock.expect(sessionManager.getCurrentSessionId()).andReturn("abcdefg");
        org.easymock.EasyMock.expect(sessionManager.getSessionCookie()).andReturn("AMBARISESSIONID");
        org.easymock.EasyMock.expect(controller.getAmbariServerURI("/spec")).andReturn("http://c6401.ambari.apache.org:8080/spec");
        org.easymock.EasyMock.expect(streamProvider.processURL(org.easymock.EasyMock.eq("http://c6401.ambari.apache.org:8080/spec"), org.easymock.EasyMock.eq("requestMethod"), org.easymock.EasyMock.aryEq(((byte[]) (null))), org.easymock.EasyMock.eq(headerMap))).andReturn(urlConnection);
        org.easymock.EasyMock.expect(urlConnection.getInputStream()).andReturn(inputStream);
        org.easymock.EasyMock.replay(streamProvider, sessionManager, controller, urlConnection, inputStream);
        org.apache.ambari.server.view.ViewAmbariStreamProvider viewAmbariStreamProvider = new org.apache.ambari.server.view.ViewAmbariStreamProvider(streamProvider, sessionManager, controller);
        org.junit.Assert.assertEquals(inputStream, viewAmbariStreamProvider.readFrom("spec", "requestMethod", body, headers));
        org.easymock.EasyMock.verify(streamProvider, sessionManager, urlConnection, inputStream);
    }
}
