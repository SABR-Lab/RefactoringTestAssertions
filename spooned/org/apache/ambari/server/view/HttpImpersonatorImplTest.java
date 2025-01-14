package org.apache.ambari.server.view;
import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
public class HttpImpersonatorImplTest {
    @org.junit.Test
    public void testRequestURL() throws java.lang.Exception {
        org.apache.ambari.server.controller.internal.URLStreamProvider streamProvider = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.internal.URLStreamProvider.class);
        java.net.HttpURLConnection urlConnection = org.easymock.EasyMock.createNiceMock(java.net.HttpURLConnection.class);
        org.apache.ambari.view.ViewContext viewContext = org.easymock.EasyMock.createNiceMock(org.apache.ambari.view.ViewContext.class);
        java.lang.String responseBody = "Response body...";
        java.io.InputStream inputStream = new java.io.ByteArrayInputStream(responseBody.getBytes(java.nio.charset.Charset.forName("UTF-8")));
        org.easymock.EasyMock.expect(streamProvider.processURL(org.easymock.EasyMock.eq("spec?doAs=joe"), org.easymock.EasyMock.eq("requestMethod"), org.easymock.EasyMock.eq(((java.lang.String) (null))), org.easymock.EasyMock.eq(((java.util.Map<java.lang.String, java.util.List<java.lang.String>>) (null))))).andReturn(urlConnection);
        org.easymock.EasyMock.expect(urlConnection.getInputStream()).andReturn(inputStream);
        org.easymock.EasyMock.expect(viewContext.getUsername()).andReturn("joe").anyTimes();
        org.easymock.EasyMock.replay(streamProvider, urlConnection, viewContext);
        org.apache.ambari.server.view.HttpImpersonatorImpl impersonator = new org.apache.ambari.server.view.HttpImpersonatorImpl(viewContext, streamProvider);
        org.apache.ambari.view.ImpersonatorSetting setting = new org.apache.ambari.server.view.ImpersonatorSettingImpl(viewContext);
        org.junit.Assert.assertEquals(responseBody, impersonator.requestURL("spec", "requestMethod", setting));
        org.easymock.EasyMock.verify(streamProvider, urlConnection, viewContext);
    }

    @org.junit.Test
    public void testRequestURLWithCustom() throws java.lang.Exception {
        org.apache.ambari.server.controller.internal.URLStreamProvider streamProvider = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.internal.URLStreamProvider.class);
        java.net.HttpURLConnection urlConnection = org.easymock.EasyMock.createNiceMock(java.net.HttpURLConnection.class);
        org.apache.ambari.view.ViewContext viewContext = org.easymock.EasyMock.createNiceMock(org.apache.ambari.view.ViewContext.class);
        java.lang.String responseBody = "Response body...";
        java.io.InputStream inputStream = new java.io.ByteArrayInputStream(responseBody.getBytes(java.nio.charset.Charset.forName("UTF-8")));
        org.easymock.EasyMock.expect(streamProvider.processURL(org.easymock.EasyMock.eq("spec?impersonate=hive"), org.easymock.EasyMock.eq("requestMethod"), org.easymock.EasyMock.eq(((java.lang.String) (null))), org.easymock.EasyMock.eq(((java.util.Map<java.lang.String, java.util.List<java.lang.String>>) (null))))).andReturn(urlConnection);
        org.easymock.EasyMock.expect(urlConnection.getInputStream()).andReturn(inputStream);
        org.easymock.EasyMock.replay(streamProvider, urlConnection);
        org.apache.ambari.server.view.HttpImpersonatorImpl impersonator = new org.apache.ambari.server.view.HttpImpersonatorImpl(viewContext, streamProvider);
        org.apache.ambari.view.ImpersonatorSetting setting = new org.apache.ambari.server.view.ImpersonatorSettingImpl("hive", "impersonate");
        org.junit.Assert.assertEquals(responseBody, impersonator.requestURL("spec", "requestMethod", setting));
        org.easymock.EasyMock.verify(streamProvider, urlConnection);
    }
}
