package org.apache.ambari.server.controller.internal;
import javax.persistence.PersistenceException;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.EasyMockRule;
import org.easymock.Mock;
import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
public class KerberosDescriptorResourceProviderTest {
    @org.junit.Rule
    public final org.easymock.EasyMockRule mocks = new org.easymock.EasyMockRule(this);

    @org.easymock.Mock
    private org.apache.ambari.server.orm.dao.KerberosDescriptorDAO kerberosDescriptorDAO;

    private final org.apache.ambari.server.topology.KerberosDescriptorFactory kerberosDescriptorFactory = new org.apache.ambari.server.topology.KerberosDescriptorFactory();

    @org.easymock.Mock
    private org.apache.ambari.server.controller.spi.Request request;

    private org.apache.ambari.server.controller.internal.KerberosDescriptorResourceProvider kerberosDescriptorResourceProvider;

    @org.junit.Before
    public void before() {
        org.easymock.EasyMock.reset(request, kerberosDescriptorDAO);
        kerberosDescriptorResourceProvider = new org.apache.ambari.server.controller.internal.KerberosDescriptorResourceProvider(kerberosDescriptorDAO, kerberosDescriptorFactory, null);
        org.easymock.EasyMock.expect(kerberosDescriptorDAO.findByName(org.easymock.EasyMock.anyObject())).andStubReturn(null);
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void rejectsCreateWithoutDescriptorText() throws java.lang.Exception {
        org.easymock.EasyMock.expect(request.getProperties()).andReturn(org.apache.ambari.server.controller.internal.KerberosDescriptorResourceProviderTest.descriptorNamed("any name")).anyTimes();
        org.easymock.EasyMock.expect(request.getRequestInfoProperties()).andReturn(com.google.common.collect.ImmutableMap.of()).anyTimes();
        org.easymock.EasyMock.replay(request);
        kerberosDescriptorResourceProvider.createResources(request);
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void rejectsCreateWithoutName() throws java.lang.Exception {
        org.easymock.EasyMock.expect(request.getProperties()).andReturn(com.google.common.collect.ImmutableSet.of()).anyTimes();
        org.easymock.EasyMock.replay(request);
        kerberosDescriptorResourceProvider.createResources(request);
    }

    @org.junit.Test
    public void acceptsValidRequest() throws java.lang.Exception {
        java.lang.String name = "some name";
        java.lang.String text = "any text";
        org.easymock.Capture<org.apache.ambari.server.orm.entities.KerberosDescriptorEntity> entityCapture = creatingDescriptor(name, text);
        org.easymock.EasyMock.replay(request, kerberosDescriptorDAO);
        kerberosDescriptorResourceProvider.createResources(request);
        verifyDescriptorCreated(entityCapture, name, text);
    }

    @org.junit.Test(expected = org.apache.ambari.server.controller.spi.ResourceAlreadyExistsException.class)
    public void rejectsDuplicateName() throws java.lang.Exception {
        java.lang.String name = "any name";
        descriptorAlreadyExists(name);
        tryingToCreateDescriptor(name, "any text");
        org.easymock.EasyMock.replay(request, kerberosDescriptorDAO);
        kerberosDescriptorResourceProvider.createResources(request);
    }

    @org.junit.Test
    public void canCreateDescriptorWithDifferentName() throws java.lang.Exception {
        descriptorAlreadyExists("some name");
        java.lang.String name = "another name";
        java.lang.String text = "any text";
        org.easymock.Capture<org.apache.ambari.server.orm.entities.KerberosDescriptorEntity> entityCapture = creatingDescriptor(name, text);
        org.easymock.EasyMock.replay(request, kerberosDescriptorDAO);
        kerberosDescriptorResourceProvider.createResources(request);
        verifyDescriptorCreated(entityCapture, name, text);
    }

    private void verifyDescriptorCreated(org.easymock.Capture<org.apache.ambari.server.orm.entities.KerberosDescriptorEntity> entityCapture, java.lang.String name, java.lang.String text) {
        org.junit.Assert.assertNotNull(entityCapture.getValue());
        org.junit.Assert.assertEquals(name, entityCapture.getValue().getName());
        org.junit.Assert.assertEquals(text, entityCapture.getValue().getKerberosDescriptorText());
    }

    private void descriptorAlreadyExists(java.lang.String name) {
        org.apache.ambari.server.orm.entities.KerberosDescriptorEntity entity = new org.apache.ambari.server.orm.entities.KerberosDescriptorEntity();
        entity.setName(name);
        org.easymock.EasyMock.expect(kerberosDescriptorDAO.findByName(org.easymock.EasyMock.eq(name))).andReturn(entity).anyTimes();
        kerberosDescriptorDAO.create(org.easymock.EasyMock.eq(entity));
        org.easymock.EasyMock.expectLastCall().andThrow(new javax.persistence.PersistenceException()).anyTimes();
    }

    private org.easymock.Capture<org.apache.ambari.server.orm.entities.KerberosDescriptorEntity> creatingDescriptor(java.lang.String name, java.lang.String text) {
        tryingToCreateDescriptor(name, text);
        org.easymock.Capture<org.apache.ambari.server.orm.entities.KerberosDescriptorEntity> entityCapture = org.easymock.EasyMock.newCapture();
        kerberosDescriptorDAO.create(org.easymock.EasyMock.capture(entityCapture));
        org.easymock.EasyMock.expectLastCall().anyTimes();
        return entityCapture;
    }

    private void tryingToCreateDescriptor(java.lang.String name, java.lang.String text) {
        org.easymock.EasyMock.expect(request.getProperties()).andReturn(org.apache.ambari.server.controller.internal.KerberosDescriptorResourceProviderTest.descriptorNamed(name)).anyTimes();
        org.easymock.EasyMock.expect(request.getRequestInfoProperties()).andReturn(org.apache.ambari.server.controller.internal.KerberosDescriptorResourceProviderTest.descriptorWithText(text)).anyTimes();
    }

    private static java.util.Map<java.lang.String, java.lang.String> descriptorWithText(java.lang.String text) {
        return com.google.common.collect.ImmutableMap.of(org.apache.ambari.server.controller.spi.Request.REQUEST_INFO_BODY_PROPERTY, text);
    }

    private static java.util.Set<java.util.Map<java.lang.String, java.lang.Object>> descriptorNamed(java.lang.String name) {
        return com.google.common.collect.ImmutableSet.of(com.google.common.collect.ImmutableMap.of(org.apache.ambari.server.controller.internal.KerberosDescriptorResourceProvider.KERBEROS_DESCRIPTOR_NAME_PROPERTY_ID, name));
    }
}
