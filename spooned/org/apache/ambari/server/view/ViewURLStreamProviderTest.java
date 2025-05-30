package org.apache.ambari.server.view;
import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.aryEq;
import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
public class ViewURLStreamProviderTest {
    @org.junit.Test
    public void testReadFrom() throws java.lang.Exception {
        org.apache.ambari.server.controller.internal.URLStreamProvider streamProvider = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.internal.URLStreamProvider.class);
        java.net.HttpURLConnection urlConnection = org.easymock.EasyMock.createNiceMock(java.net.HttpURLConnection.class);
        java.io.InputStream inputStream = org.easymock.EasyMock.createNiceMock(java.io.InputStream.class);
        org.apache.ambari.view.ViewContext viewContext = org.easymock.EasyMock.createNiceMock(org.apache.ambari.view.ViewContext.class);
        java.util.Map<java.lang.String, java.lang.String> headers = new java.util.HashMap<>();
        headers.put("header", "headerValue");
        java.util.Map<java.lang.String, java.util.List<java.lang.String>> headerMap = new java.util.HashMap<>();
        headerMap.put("header", java.util.Collections.singletonList("headerValue"));
        org.easymock.EasyMock.expect(streamProvider.processURL(org.easymock.EasyMock.eq("spec"), org.easymock.EasyMock.eq("requestMethod"), org.easymock.EasyMock.aryEq("params".getBytes()), org.easymock.EasyMock.eq(headerMap))).andReturn(urlConnection);
        org.easymock.EasyMock.expect(urlConnection.getInputStream()).andReturn(inputStream);
        org.easymock.EasyMock.replay(streamProvider, urlConnection, inputStream);
        org.apache.ambari.server.view.ViewURLStreamProvider viewURLStreamProvider = new org.apache.ambari.server.view.ViewURLStreamProvider(viewContext, streamProvider);
        org.junit.Assert.assertEquals(inputStream, viewURLStreamProvider.readFrom("spec", "requestMethod", "params", headers));
        org.easymock.EasyMock.verify(streamProvider, urlConnection, inputStream);
    }

    @org.junit.Test
    public void testReadFromNullBody() throws java.lang.Exception {
        org.apache.ambari.server.controller.internal.URLStreamProvider streamProvider = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.internal.URLStreamProvider.class);
        java.net.HttpURLConnection urlConnection = org.easymock.EasyMock.createNiceMock(java.net.HttpURLConnection.class);
        java.io.InputStream inputStream = org.easymock.EasyMock.createNiceMock(java.io.InputStream.class);
        org.apache.ambari.view.ViewContext viewContext = org.easymock.EasyMock.createNiceMock(org.apache.ambari.view.ViewContext.class);
        java.util.Map<java.lang.String, java.lang.String> headers = new java.util.HashMap<>();
        headers.put("header", "headerValue");
        java.util.Map<java.lang.String, java.util.List<java.lang.String>> headerMap = new java.util.HashMap<>();
        headerMap.put("header", java.util.Collections.singletonList("headerValue"));
        org.easymock.EasyMock.expect(streamProvider.processURL(org.easymock.EasyMock.eq("spec"), org.easymock.EasyMock.eq("requestMethod"), org.easymock.EasyMock.aryEq(((byte[]) (null))), org.easymock.EasyMock.eq(headerMap))).andReturn(urlConnection);
        org.easymock.EasyMock.expect(urlConnection.getInputStream()).andReturn(inputStream);
        org.easymock.EasyMock.replay(streamProvider, urlConnection, inputStream);
        org.apache.ambari.server.view.ViewURLStreamProvider viewURLStreamProvider = new org.apache.ambari.server.view.ViewURLStreamProvider(viewContext, streamProvider);
        org.junit.Assert.assertEquals(inputStream, viewURLStreamProvider.readFrom("spec", "requestMethod", ((java.lang.String) (null)), headers));
        org.easymock.EasyMock.verify(streamProvider, urlConnection, inputStream);
    }

    @org.junit.Test
    public void testReadAs() throws java.lang.Exception {
        org.apache.ambari.server.controller.internal.URLStreamProvider streamProvider = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.internal.URLStreamProvider.class);
        java.net.HttpURLConnection urlConnection = org.easymock.EasyMock.createNiceMock(java.net.HttpURLConnection.class);
        java.io.InputStream inputStream = org.easymock.EasyMock.createNiceMock(java.io.InputStream.class);
        org.apache.ambari.view.ViewContext viewContext = org.easymock.EasyMock.createNiceMock(org.apache.ambari.view.ViewContext.class);
        java.util.Map<java.lang.String, java.lang.String> headers = new java.util.HashMap<>();
        headers.put("header", "headerValue");
        java.util.Map<java.lang.String, java.util.List<java.lang.String>> headerMap = new java.util.HashMap<>();
        headerMap.put("header", java.util.Collections.singletonList("headerValue"));
        org.easymock.EasyMock.expect(streamProvider.processURL(org.easymock.EasyMock.eq("spec?doAs=joe"), org.easymock.EasyMock.eq("requestMethod"), org.easymock.EasyMock.aryEq("params".getBytes()), org.easymock.EasyMock.eq(headerMap))).andReturn(urlConnection);
        org.easymock.EasyMock.expect(urlConnection.getInputStream()).andReturn(inputStream);
        org.easymock.EasyMock.replay(streamProvider, urlConnection, inputStream);
        org.apache.ambari.server.view.ViewURLStreamProvider viewURLStreamProvider = new org.apache.ambari.server.view.ViewURLStreamProvider(viewContext, streamProvider);
        org.junit.Assert.assertEquals(inputStream, viewURLStreamProvider.readAs("spec", "requestMethod", "params", headers, "joe"));
        org.easymock.EasyMock.verify(streamProvider, urlConnection, inputStream);
    }

    @org.junit.Test
    public void testReadAsCurrent() throws java.lang.Exception {
        org.apache.ambari.server.controller.internal.URLStreamProvider streamProvider = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.internal.URLStreamProvider.class);
        java.net.HttpURLConnection urlConnection = org.easymock.EasyMock.createNiceMock(java.net.HttpURLConnection.class);
        java.io.InputStream inputStream = org.easymock.EasyMock.createNiceMock(java.io.InputStream.class);
        org.apache.ambari.view.ViewContext viewContext = org.easymock.EasyMock.createNiceMock(org.apache.ambari.view.ViewContext.class);
        java.util.Map<java.lang.String, java.lang.String> headers = new java.util.HashMap<>();
        headers.put("header", "headerValue");
        java.util.Map<java.lang.String, java.util.List<java.lang.String>> headerMap = new java.util.HashMap<>();
        headerMap.put("header", java.util.Collections.singletonList("headerValue"));
        org.easymock.EasyMock.expect(streamProvider.processURL(org.easymock.EasyMock.eq("spec?doAs=joe"), org.easymock.EasyMock.eq("requestMethod"), org.easymock.EasyMock.aryEq("params".getBytes()), org.easymock.EasyMock.eq(headerMap))).andReturn(urlConnection);
        org.easymock.EasyMock.expect(urlConnection.getInputStream()).andReturn(inputStream);
        org.easymock.EasyMock.expect(viewContext.getUsername()).andReturn("joe").anyTimes();
        org.easymock.EasyMock.replay(streamProvider, urlConnection, inputStream, viewContext);
        org.apache.ambari.server.view.ViewURLStreamProvider viewURLStreamProvider = new org.apache.ambari.server.view.ViewURLStreamProvider(viewContext, streamProvider);
        org.junit.Assert.assertEquals(inputStream, viewURLStreamProvider.readAsCurrent("spec", "requestMethod", "params", headers));
        org.easymock.EasyMock.verify(streamProvider, urlConnection, inputStream, viewContext);
    }

    @org.junit.Test
    public void testReadFromInputStream() throws java.lang.Exception {
        org.apache.ambari.server.controller.internal.URLStreamProvider streamProvider = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.internal.URLStreamProvider.class);
        java.net.HttpURLConnection urlConnection = org.easymock.EasyMock.createNiceMock(java.net.HttpURLConnection.class);
        java.io.InputStream inputStream = org.easymock.EasyMock.createNiceMock(java.io.InputStream.class);
        org.apache.ambari.view.ViewContext viewContext = org.easymock.EasyMock.createNiceMock(org.apache.ambari.view.ViewContext.class);
        java.io.InputStream body = new java.io.ByteArrayInputStream("params".getBytes());
        java.util.Map<java.lang.String, java.lang.String> headers = new java.util.HashMap<>();
        headers.put("header", "headerValue");
        java.util.Map<java.lang.String, java.util.List<java.lang.String>> headerMap = new java.util.HashMap<>();
        headerMap.put("header", java.util.Collections.singletonList("headerValue"));
        org.easymock.EasyMock.expect(streamProvider.processURL(org.easymock.EasyMock.eq("spec"), org.easymock.EasyMock.eq("requestMethod"), org.easymock.EasyMock.aryEq("params".getBytes()), org.easymock.EasyMock.eq(headerMap))).andReturn(urlConnection);
        org.easymock.EasyMock.expect(urlConnection.getInputStream()).andReturn(inputStream);
        org.easymock.EasyMock.replay(streamProvider, urlConnection, inputStream);
        org.apache.ambari.server.view.ViewURLStreamProvider viewURLStreamProvider = new org.apache.ambari.server.view.ViewURLStreamProvider(viewContext, streamProvider);
        org.junit.Assert.assertEquals(inputStream, viewURLStreamProvider.readFrom("spec", "requestMethod", body, headers));
        org.easymock.EasyMock.verify(streamProvider, urlConnection, inputStream);
    }

    @org.junit.Test
    public void testReadFromNullInputStreamBody() throws java.lang.Exception {
        org.apache.ambari.server.controller.internal.URLStreamProvider streamProvider = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.internal.URLStreamProvider.class);
        java.net.HttpURLConnection urlConnection = org.easymock.EasyMock.createNiceMock(java.net.HttpURLConnection.class);
        java.io.InputStream inputStream = org.easymock.EasyMock.createNiceMock(java.io.InputStream.class);
        org.apache.ambari.view.ViewContext viewContext = org.easymock.EasyMock.createNiceMock(org.apache.ambari.view.ViewContext.class);
        java.util.Map<java.lang.String, java.lang.String> headers = new java.util.HashMap<>();
        headers.put("header", "headerValue");
        java.util.Map<java.lang.String, java.util.List<java.lang.String>> headerMap = new java.util.HashMap<>();
        headerMap.put("header", java.util.Collections.singletonList("headerValue"));
        org.easymock.EasyMock.expect(streamProvider.processURL(org.easymock.EasyMock.eq("spec"), org.easymock.EasyMock.eq("requestMethod"), org.easymock.EasyMock.aryEq(((byte[]) (null))), org.easymock.EasyMock.eq(headerMap))).andReturn(urlConnection);
        org.easymock.EasyMock.expect(urlConnection.getInputStream()).andReturn(inputStream);
        org.easymock.EasyMock.replay(streamProvider, urlConnection, inputStream);
        org.apache.ambari.server.view.ViewURLStreamProvider viewURLStreamProvider = new org.apache.ambari.server.view.ViewURLStreamProvider(viewContext, streamProvider);
        org.junit.Assert.assertEquals(inputStream, viewURLStreamProvider.readFrom("spec", "requestMethod", ((java.io.InputStream) (null)), headers));
        org.easymock.EasyMock.verify(streamProvider, urlConnection, inputStream);
    }

    @org.junit.Test
    public void testReadAsInputStream() throws java.lang.Exception {
        org.apache.ambari.server.controller.internal.URLStreamProvider streamProvider = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.internal.URLStreamProvider.class);
        java.net.HttpURLConnection urlConnection = org.easymock.EasyMock.createNiceMock(java.net.HttpURLConnection.class);
        java.io.InputStream inputStream = org.easymock.EasyMock.createNiceMock(java.io.InputStream.class);
        org.apache.ambari.view.ViewContext viewContext = org.easymock.EasyMock.createNiceMock(org.apache.ambari.view.ViewContext.class);
        java.io.InputStream body = new java.io.ByteArrayInputStream("params".getBytes());
        java.util.Map<java.lang.String, java.lang.String> headers = new java.util.HashMap<>();
        headers.put("header", "headerValue");
        java.util.Map<java.lang.String, java.util.List<java.lang.String>> headerMap = new java.util.HashMap<>();
        headerMap.put("header", java.util.Collections.singletonList("headerValue"));
        org.easymock.EasyMock.expect(streamProvider.processURL(org.easymock.EasyMock.eq("spec?doAs=joe"), org.easymock.EasyMock.eq("requestMethod"), org.easymock.EasyMock.aryEq("params".getBytes()), org.easymock.EasyMock.eq(headerMap))).andReturn(urlConnection);
        org.easymock.EasyMock.expect(urlConnection.getInputStream()).andReturn(inputStream);
        org.easymock.EasyMock.replay(streamProvider, urlConnection, inputStream);
        org.apache.ambari.server.view.ViewURLStreamProvider viewURLStreamProvider = new org.apache.ambari.server.view.ViewURLStreamProvider(viewContext, streamProvider);
        org.junit.Assert.assertEquals(inputStream, viewURLStreamProvider.readAs("spec", "requestMethod", body, headers, "joe"));
        org.easymock.EasyMock.verify(streamProvider, urlConnection, inputStream);
    }

    @org.junit.Test
    public void testReadAsCurrentInputStream() throws java.lang.Exception {
        org.apache.ambari.server.controller.internal.URLStreamProvider streamProvider = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.internal.URLStreamProvider.class);
        java.net.HttpURLConnection urlConnection = org.easymock.EasyMock.createNiceMock(java.net.HttpURLConnection.class);
        java.io.InputStream inputStream = org.easymock.EasyMock.createNiceMock(java.io.InputStream.class);
        org.apache.ambari.view.ViewContext viewContext = org.easymock.EasyMock.createNiceMock(org.apache.ambari.view.ViewContext.class);
        java.io.InputStream body = new java.io.ByteArrayInputStream("params".getBytes());
        java.util.Map<java.lang.String, java.lang.String> headers = new java.util.HashMap<>();
        headers.put("header", "headerValue");
        java.util.Map<java.lang.String, java.util.List<java.lang.String>> headerMap = new java.util.HashMap<>();
        headerMap.put("header", java.util.Collections.singletonList("headerValue"));
        org.easymock.EasyMock.expect(streamProvider.processURL(org.easymock.EasyMock.eq("spec?doAs=joe"), org.easymock.EasyMock.eq("requestMethod"), org.easymock.EasyMock.aryEq("params".getBytes()), org.easymock.EasyMock.eq(headerMap))).andReturn(urlConnection);
        org.easymock.EasyMock.expect(urlConnection.getInputStream()).andReturn(inputStream);
        org.easymock.EasyMock.expect(viewContext.getUsername()).andReturn("joe").anyTimes();
        org.easymock.EasyMock.replay(streamProvider, urlConnection, inputStream, viewContext);
        org.apache.ambari.server.view.ViewURLStreamProvider viewURLStreamProvider = new org.apache.ambari.server.view.ViewURLStreamProvider(viewContext, streamProvider);
        org.junit.Assert.assertEquals(inputStream, viewURLStreamProvider.readAsCurrent("spec", "requestMethod", body, headers));
        org.easymock.EasyMock.verify(streamProvider, urlConnection, inputStream, viewContext);
    }

    @org.junit.Test
    public void testGetConnection() throws java.lang.Exception {
        org.apache.ambari.server.controller.internal.URLStreamProvider streamProvider = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.internal.URLStreamProvider.class);
        java.net.HttpURLConnection urlConnection = org.easymock.EasyMock.createNiceMock(java.net.HttpURLConnection.class);
        org.apache.ambari.view.ViewContext viewContext = org.easymock.EasyMock.createNiceMock(org.apache.ambari.view.ViewContext.class);
        java.util.Map<java.lang.String, java.lang.String> headers = new java.util.HashMap<>();
        headers.put("header", "headerValue");
        java.util.Map<java.lang.String, java.util.List<java.lang.String>> headerMap = new java.util.HashMap<>();
        headerMap.put("header", java.util.Collections.singletonList("headerValue"));
        org.easymock.EasyMock.expect(streamProvider.processURL(org.easymock.EasyMock.eq("spec"), org.easymock.EasyMock.eq("requestMethod"), org.easymock.EasyMock.aryEq("params".getBytes()), org.easymock.EasyMock.eq(headerMap))).andReturn(urlConnection);
        org.easymock.EasyMock.replay(streamProvider, urlConnection);
        org.apache.ambari.server.view.ViewURLStreamProvider viewURLStreamProvider = new org.apache.ambari.server.view.ViewURLStreamProvider(viewContext, streamProvider);
        org.junit.Assert.assertEquals(urlConnection, viewURLStreamProvider.getConnection("spec", "requestMethod", "params", headers));
        org.easymock.EasyMock.verify(streamProvider, urlConnection);
    }

    @org.junit.Test
    public void testGetConnectionAs() throws java.lang.Exception {
        org.apache.ambari.server.controller.internal.URLStreamProvider streamProvider = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.internal.URLStreamProvider.class);
        java.net.HttpURLConnection urlConnection = org.easymock.EasyMock.createNiceMock(java.net.HttpURLConnection.class);
        org.apache.ambari.view.ViewContext viewContext = org.easymock.EasyMock.createNiceMock(org.apache.ambari.view.ViewContext.class);
        java.util.Map<java.lang.String, java.lang.String> headers = new java.util.HashMap<>();
        headers.put("header", "headerValue");
        java.util.Map<java.lang.String, java.util.List<java.lang.String>> headerMap = new java.util.HashMap<>();
        headerMap.put("header", java.util.Collections.singletonList("headerValue"));
        org.easymock.EasyMock.expect(streamProvider.processURL(org.easymock.EasyMock.eq("spec?doAs=joe"), org.easymock.EasyMock.eq("requestMethod"), org.easymock.EasyMock.aryEq("params".getBytes()), org.easymock.EasyMock.eq(headerMap))).andReturn(urlConnection);
        org.easymock.EasyMock.replay(streamProvider, urlConnection);
        org.apache.ambari.server.view.ViewURLStreamProvider viewURLStreamProvider = new org.apache.ambari.server.view.ViewURLStreamProvider(viewContext, streamProvider);
        org.junit.Assert.assertEquals(urlConnection, viewURLStreamProvider.getConnectionAs("spec", "requestMethod", "params", headers, "joe"));
        org.easymock.EasyMock.verify(streamProvider, urlConnection);
    }

    @org.junit.Test
    public void testGetConnectionCurrent() throws java.lang.Exception {
        org.apache.ambari.server.controller.internal.URLStreamProvider streamProvider = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.internal.URLStreamProvider.class);
        java.net.HttpURLConnection urlConnection = org.easymock.EasyMock.createNiceMock(java.net.HttpURLConnection.class);
        org.apache.ambari.view.ViewContext viewContext = org.easymock.EasyMock.createNiceMock(org.apache.ambari.view.ViewContext.class);
        java.util.Map<java.lang.String, java.lang.String> headers = new java.util.HashMap<>();
        headers.put("header", "headerValue");
        java.util.Map<java.lang.String, java.util.List<java.lang.String>> headerMap = new java.util.HashMap<>();
        headerMap.put("header", java.util.Collections.singletonList("headerValue"));
        org.easymock.EasyMock.expect(streamProvider.processURL(org.easymock.EasyMock.eq("spec?doAs=joe"), org.easymock.EasyMock.eq("requestMethod"), org.easymock.EasyMock.aryEq("params".getBytes()), org.easymock.EasyMock.eq(headerMap))).andReturn(urlConnection);
        org.easymock.EasyMock.expect(viewContext.getUsername()).andReturn("joe").anyTimes();
        org.easymock.EasyMock.replay(streamProvider, urlConnection, viewContext);
        org.apache.ambari.server.view.ViewURLStreamProvider viewURLStreamProvider = new org.apache.ambari.server.view.ViewURLStreamProvider(viewContext, streamProvider);
        org.junit.Assert.assertEquals(urlConnection, viewURLStreamProvider.getConnectionAsCurrent("spec", "requestMethod", "params", headers));
        org.easymock.EasyMock.verify(streamProvider, urlConnection, viewContext);
    }

    @org.junit.Test
    public void testProxyRestriction() throws java.lang.Exception {
        org.apache.ambari.server.view.ViewURLStreamProvider viewURLStreamProvider = new org.apache.ambari.server.view.ViewURLStreamProvider(null, null);
        java.util.Properties ambariProperties = new java.util.Properties();
        org.apache.ambari.server.configuration.Configuration configuration = new org.apache.ambari.server.configuration.Configuration(ambariProperties);
        org.junit.Assert.assertEquals(org.apache.ambari.server.configuration.Configuration.PROXY_ALLOWED_HOST_PORTS.getDefaultValue(), configuration.getProxyHostAndPorts());
        org.apache.ambari.server.view.ViewURLStreamProvider.HostPortRestrictionHandler hprh = viewURLStreamProvider.new HostPortRestrictionHandler(configuration.getProxyHostAndPorts());
        org.junit.Assert.assertFalse(hprh.proxyCallRestricted());
        org.junit.Assert.assertTrue(hprh.allowProxy("host1.com", null));
        org.junit.Assert.assertTrue(hprh.allowProxy(null, null));
        org.junit.Assert.assertTrue(hprh.allowProxy("host1.com", " "));
        org.junit.Assert.assertTrue(hprh.allowProxy("host1.com ", " "));
        org.junit.Assert.assertTrue(hprh.allowProxy(" host1.com ", "8080"));
        ambariProperties = new java.util.Properties();
        ambariProperties.setProperty(org.apache.ambari.server.configuration.Configuration.PROXY_ALLOWED_HOST_PORTS.getKey(), "");
        configuration = new org.apache.ambari.server.configuration.Configuration(ambariProperties);
        hprh = viewURLStreamProvider.new HostPortRestrictionHandler(configuration.getProxyHostAndPorts());
        org.junit.Assert.assertFalse(hprh.proxyCallRestricted());
        org.junit.Assert.assertTrue(hprh.allowProxy("host1.com", null));
        org.junit.Assert.assertTrue(hprh.allowProxy(null, null));
        org.junit.Assert.assertTrue(hprh.allowProxy("host1.com", " "));
        org.junit.Assert.assertTrue(hprh.allowProxy("host1.com ", " "));
        org.junit.Assert.assertTrue(hprh.allowProxy(" host1.com ", "8080"));
        ambariProperties = new java.util.Properties();
        ambariProperties.setProperty(org.apache.ambari.server.configuration.Configuration.PROXY_ALLOWED_HOST_PORTS.getKey(), "host1.com:*");
        configuration = new org.apache.ambari.server.configuration.Configuration(ambariProperties);
        hprh = viewURLStreamProvider.new HostPortRestrictionHandler(configuration.getProxyHostAndPorts());
        org.junit.Assert.assertTrue(hprh.proxyCallRestricted());
        org.junit.Assert.assertTrue(hprh.allowProxy("host1.com", null));
        org.junit.Assert.assertTrue(hprh.allowProxy(null, null));
        org.junit.Assert.assertTrue(hprh.allowProxy("host1.com", "20"));
        org.junit.Assert.assertFalse(hprh.allowProxy("host2.com ", " "));
        org.junit.Assert.assertFalse(hprh.allowProxy(" host2.com ", "8080"));
        ambariProperties = new java.util.Properties();
        ambariProperties.setProperty(org.apache.ambari.server.configuration.Configuration.PROXY_ALLOWED_HOST_PORTS.getKey(), " host1.com:80 ,host2.org:443, host2.org:22");
        configuration = new org.apache.ambari.server.configuration.Configuration(ambariProperties);
        hprh = viewURLStreamProvider.new HostPortRestrictionHandler(configuration.getProxyHostAndPorts());
        org.junit.Assert.assertTrue(hprh.proxyCallRestricted());
        org.junit.Assert.assertTrue(hprh.allowProxy("host1.com", "80"));
        org.junit.Assert.assertFalse(hprh.allowProxy("host1.com", "20"));
        org.junit.Assert.assertFalse(hprh.allowProxy("host2.org", "404"));
        org.junit.Assert.assertFalse(hprh.allowProxy("host2.com", "22"));
        org.apache.ambari.view.ViewContext viewContext = org.easymock.EasyMock.createNiceMock(org.apache.ambari.view.ViewContext.class);
        org.easymock.EasyMock.expect(viewContext.getAmbariProperty(org.easymock.EasyMock.anyObject(java.lang.String.class))).andReturn(" host1.com:80 ,host2.org:443, host2.org:22");
        org.easymock.EasyMock.replay(viewContext);
        viewURLStreamProvider = new org.apache.ambari.server.view.ViewURLStreamProvider(viewContext, null);
        org.junit.Assert.assertTrue(viewURLStreamProvider.isProxyCallAllowed("http://host1.com/tt"));
        org.junit.Assert.assertTrue(viewURLStreamProvider.isProxyCallAllowed("https://host2.org/tt"));
        org.junit.Assert.assertFalse(viewURLStreamProvider.isProxyCallAllowed("https://host2.org:444/tt"));
        viewContext = org.easymock.EasyMock.createNiceMock(org.apache.ambari.view.ViewContext.class);
        org.easymock.EasyMock.expect(viewContext.getAmbariProperty(org.easymock.EasyMock.anyObject(java.lang.String.class))).andReturn("c6401.ambari.apache.org:8088");
        org.easymock.EasyMock.replay(viewContext);
        viewURLStreamProvider = new org.apache.ambari.server.view.ViewURLStreamProvider(viewContext, null);
        org.junit.Assert.assertTrue(viewURLStreamProvider.isProxyCallAllowed("http://c6401.ambari.apache.org:8088/ws/v1/cluster/get-node-labels"));
        viewContext = org.easymock.EasyMock.createNiceMock(org.apache.ambari.view.ViewContext.class);
        org.easymock.EasyMock.expect(viewContext.getAmbariProperty(org.easymock.EasyMock.anyObject(java.lang.String.class))).andReturn("*:8088");
        org.easymock.EasyMock.replay(viewContext);
        viewURLStreamProvider = new org.apache.ambari.server.view.ViewURLStreamProvider(viewContext, null);
        org.junit.Assert.assertFalse(viewURLStreamProvider.isProxyCallAllowed("http://c6401.ambari.apache.org:8088/ws/v1/cluster/get-node-labels"));
        viewContext = org.easymock.EasyMock.createNiceMock(org.apache.ambari.view.ViewContext.class);
        org.easymock.EasyMock.expect(viewContext.getAmbariProperty(org.easymock.EasyMock.anyObject(java.lang.String.class))).andReturn("c6401.ambari.apache.org:*");
        org.easymock.EasyMock.replay(viewContext);
        viewURLStreamProvider = new org.apache.ambari.server.view.ViewURLStreamProvider(viewContext, null);
        org.junit.Assert.assertTrue(viewURLStreamProvider.isProxyCallAllowed("http://c6401.ambari.apache.org:8088/ws/v1/cluster/get-node-labels"));
        viewContext = org.easymock.EasyMock.createNiceMock(org.apache.ambari.view.ViewContext.class);
        org.easymock.EasyMock.expect(viewContext.getAmbariProperty(org.easymock.EasyMock.anyObject(java.lang.String.class))).andReturn("c6401.ambari.apache.org:80,c6401.ambari.apache.org:443");
        org.easymock.EasyMock.replay(viewContext);
        viewURLStreamProvider = new org.apache.ambari.server.view.ViewURLStreamProvider(viewContext, null);
        org.junit.Assert.assertTrue(viewURLStreamProvider.isProxyCallAllowed("http://c6401.ambari.apache.org/ws/v1/cluster/get-node-labels"));
        org.junit.Assert.assertTrue(viewURLStreamProvider.isProxyCallAllowed("https://c6401.ambari.apache.org/ws/v1/cluster/get-node-labels"));
    }
}
