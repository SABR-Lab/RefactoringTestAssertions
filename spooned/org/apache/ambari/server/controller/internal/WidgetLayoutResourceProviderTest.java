package org.apache.ambari.server.controller.internal;
import org.easymock.Capture;
import org.easymock.EasyMock;
import static org.easymock.EasyMock.anyLong;
import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.resetToStrict;
import static org.easymock.EasyMock.verify;
public class WidgetLayoutResourceProviderTest {
    private org.apache.ambari.server.orm.dao.WidgetLayoutDAO dao = null;

    private org.apache.ambari.server.orm.dao.WidgetDAO widgetDAO = null;

    private com.google.inject.Injector m_injector;

    @org.junit.Before
    public void before() {
        dao = org.easymock.EasyMock.createStrictMock(org.apache.ambari.server.orm.dao.WidgetLayoutDAO.class);
        widgetDAO = org.easymock.EasyMock.createStrictMock(org.apache.ambari.server.orm.dao.WidgetDAO.class);
        m_injector = com.google.inject.Guice.createInjector(com.google.inject.util.Modules.override(new org.apache.ambari.server.orm.InMemoryDefaultTestModule()).with(new org.apache.ambari.server.controller.internal.WidgetLayoutResourceProviderTest.MockModule()));
    }

    @org.junit.Test
    public void testGetResourcesNoPredicate() throws java.lang.Exception {
        org.apache.ambari.server.controller.internal.WidgetLayoutResourceProvider provider = createProvider(null);
        org.apache.ambari.server.controller.spi.Request request = org.apache.ambari.server.controller.utilities.PropertyHelper.getReadRequest("WidgetLayouts/id");
        java.util.Set<org.apache.ambari.server.controller.spi.Resource> results = provider.getResources(request, null);
        org.junit.Assert.assertEquals(0, results.size());
    }

    @org.junit.Test
    public void testGetSingleResource() throws java.lang.Exception {
        org.apache.ambari.server.controller.spi.Request request = org.apache.ambari.server.controller.utilities.PropertyHelper.getReadRequest(org.apache.ambari.server.controller.internal.WidgetLayoutResourceProvider.WIDGETLAYOUT_CLUSTER_NAME_PROPERTY_ID, org.apache.ambari.server.controller.internal.WidgetLayoutResourceProvider.WIDGETLAYOUT_ID_PROPERTY_ID, org.apache.ambari.server.controller.internal.WidgetLayoutResourceProvider.WIDGETLAYOUT_DISPLAY_NAME_PROPERTY_ID, org.apache.ambari.server.controller.internal.WidgetLayoutResourceProvider.WIDGETLAYOUT_LAYOUT_NAME_PROPERTY_ID, org.apache.ambari.server.controller.internal.WidgetLayoutResourceProvider.WIDGETLAYOUT_SECTION_NAME_PROPERTY_ID, org.apache.ambari.server.controller.internal.WidgetLayoutResourceProvider.WIDGETLAYOUT_USERNAME_PROPERTY_ID, org.apache.ambari.server.controller.internal.WidgetLayoutResourceProvider.WIDGETLAYOUT_SCOPE_PROPERTY_ID, org.apache.ambari.server.controller.internal.WidgetLayoutResourceProvider.WIDGETLAYOUT_WIDGETS_PROPERTY_ID);
        org.apache.ambari.server.controller.AmbariManagementController amc = org.easymock.EasyMock.createMock(org.apache.ambari.server.controller.AmbariManagementController.class);
        org.apache.ambari.server.state.Clusters clusters = org.easymock.EasyMock.createMock(org.apache.ambari.server.state.Clusters.class);
        org.apache.ambari.server.state.Cluster cluster = org.easymock.EasyMock.createMock(org.apache.ambari.server.state.Cluster.class);
        org.easymock.EasyMock.expect(amc.getClusters()).andReturn(clusters).atLeastOnce();
        org.easymock.EasyMock.expect(clusters.getClusterById(1L)).andReturn(cluster).atLeastOnce();
        org.easymock.EasyMock.expect(cluster.getClusterName()).andReturn("c1").anyTimes();
        org.apache.ambari.server.controller.spi.Predicate predicate = new org.apache.ambari.server.controller.utilities.PredicateBuilder().property(org.apache.ambari.server.controller.internal.WidgetLayoutResourceProvider.WIDGETLAYOUT_CLUSTER_NAME_PROPERTY_ID).equals("c1").and().property(org.apache.ambari.server.controller.internal.WidgetLayoutResourceProvider.WIDGETLAYOUT_ID_PROPERTY_ID).equals("1").and().property(org.apache.ambari.server.controller.internal.WidgetLayoutResourceProvider.WIDGETLAYOUT_USERNAME_PROPERTY_ID).equals("username").toPredicate();
        org.easymock.EasyMock.expect(dao.findById(1L)).andReturn(getMockEntities().get(0));
        org.easymock.EasyMock.replay(amc, clusters, cluster, dao);
        org.apache.ambari.server.controller.internal.WidgetLayoutResourceProvider provider = createProvider(amc);
        java.util.Set<org.apache.ambari.server.controller.spi.Resource> results = provider.getResources(request, predicate);
        org.junit.Assert.assertEquals(1, results.size());
        org.apache.ambari.server.controller.spi.Resource r = results.iterator().next();
        org.junit.Assert.assertEquals("section0", r.getPropertyValue(org.apache.ambari.server.controller.internal.WidgetLayoutResourceProvider.WIDGETLAYOUT_SECTION_NAME_PROPERTY_ID));
        org.junit.Assert.assertEquals("CLUSTER", r.getPropertyValue(org.apache.ambari.server.controller.internal.WidgetLayoutResourceProvider.WIDGETLAYOUT_SCOPE_PROPERTY_ID));
        org.junit.Assert.assertEquals("username", r.getPropertyValue(org.apache.ambari.server.controller.internal.WidgetLayoutResourceProvider.WIDGETLAYOUT_USERNAME_PROPERTY_ID));
        org.junit.Assert.assertEquals("displ_name", r.getPropertyValue(org.apache.ambari.server.controller.internal.WidgetLayoutResourceProvider.WIDGETLAYOUT_DISPLAY_NAME_PROPERTY_ID));
        org.junit.Assert.assertEquals("layout name0", r.getPropertyValue(org.apache.ambari.server.controller.internal.WidgetLayoutResourceProvider.WIDGETLAYOUT_LAYOUT_NAME_PROPERTY_ID));
        org.junit.Assert.assertEquals("[]", r.getPropertyValue(org.apache.ambari.server.controller.internal.WidgetLayoutResourceProvider.WIDGETLAYOUT_WIDGETS_PROPERTY_ID).toString());
    }

    @org.junit.Test
    public void testCreateResources() throws java.lang.Exception {
        org.apache.ambari.server.controller.AmbariManagementController amc = org.easymock.EasyMock.createMock(org.apache.ambari.server.controller.AmbariManagementController.class);
        org.apache.ambari.server.state.Clusters clusters = org.easymock.EasyMock.createMock(org.apache.ambari.server.state.Clusters.class);
        org.apache.ambari.server.state.Cluster cluster = org.easymock.EasyMock.createMock(org.apache.ambari.server.state.Cluster.class);
        org.easymock.EasyMock.expect(amc.getClusters()).andReturn(clusters).atLeastOnce();
        org.easymock.EasyMock.expect(clusters.getCluster(((java.lang.String) (org.easymock.EasyMock.anyObject())))).andReturn(cluster).atLeastOnce();
        org.easymock.EasyMock.expect(cluster.getClusterId()).andReturn(java.lang.Long.valueOf(1)).anyTimes();
        org.easymock.Capture<org.apache.ambari.server.orm.entities.WidgetLayoutEntity> entityCapture = org.easymock.EasyMock.newCapture();
        dao.create(org.easymock.EasyMock.capture(entityCapture));
        org.easymock.EasyMock.expectLastCall();
        org.apache.ambari.server.orm.entities.WidgetEntity widgetEntity = new org.apache.ambari.server.orm.entities.WidgetEntity();
        widgetEntity.setId(1L);
        widgetEntity.setListWidgetLayoutUserWidgetEntity(new java.util.ArrayList<>());
        org.easymock.EasyMock.expect(widgetDAO.findById(org.easymock.EasyMock.anyLong())).andReturn(widgetEntity).anyTimes();
        org.easymock.EasyMock.replay(amc, clusters, cluster, dao, widgetDAO);
        org.apache.ambari.server.controller.internal.WidgetLayoutResourceProvider provider = createProvider(amc);
        java.util.Map<java.lang.String, java.lang.Object> requestProps = new java.util.HashMap<>();
        requestProps.put(org.apache.ambari.server.controller.internal.WidgetLayoutResourceProvider.WIDGETLAYOUT_CLUSTER_NAME_PROPERTY_ID, "c1");
        requestProps.put(org.apache.ambari.server.controller.internal.WidgetLayoutResourceProvider.WIDGETLAYOUT_LAYOUT_NAME_PROPERTY_ID, "layout_name");
        requestProps.put(org.apache.ambari.server.controller.internal.WidgetLayoutResourceProvider.WIDGETLAYOUT_DISPLAY_NAME_PROPERTY_ID, "display_name");
        requestProps.put(org.apache.ambari.server.controller.internal.WidgetLayoutResourceProvider.WIDGETLAYOUT_SECTION_NAME_PROPERTY_ID, "section_name");
        requestProps.put(org.apache.ambari.server.controller.internal.WidgetLayoutResourceProvider.WIDGETLAYOUT_USERNAME_PROPERTY_ID, "admin");
        requestProps.put(org.apache.ambari.server.controller.internal.WidgetLayoutResourceProvider.WIDGETLAYOUT_SCOPE_PROPERTY_ID, "CLUSTER");
        java.util.Set<java.util.Map<java.lang.String, java.lang.String>> widgetsInfo = new java.util.LinkedHashSet<>();
        java.util.Map<java.lang.String, java.lang.String> widget = new java.util.HashMap<>();
        widget.put("id", "1");
        widgetsInfo.add(widget);
        requestProps.put(org.apache.ambari.server.controller.internal.WidgetLayoutResourceProvider.WIDGETLAYOUT_WIDGETS_PROPERTY_ID, widgetsInfo);
        org.apache.ambari.server.controller.spi.Request request = org.apache.ambari.server.controller.utilities.PropertyHelper.getCreateRequest(java.util.Collections.singleton(requestProps), null);
        org.apache.ambari.server.controller.spi.RequestStatus requestStatus = provider.createResources(request);
        org.junit.Assert.assertTrue(entityCapture.hasCaptured());
        org.apache.ambari.server.orm.entities.WidgetLayoutEntity entity = entityCapture.getValue();
        org.junit.Assert.assertNotNull(entity);
        org.junit.Assert.assertEquals(1, requestStatus.getAssociatedResources().size());
        org.junit.Assert.assertEquals(java.lang.Long.valueOf(1), entity.getClusterId());
        org.junit.Assert.assertEquals("CLUSTER", entity.getScope());
        org.junit.Assert.assertEquals("layout_name", entity.getLayoutName());
        org.junit.Assert.assertEquals("display_name", entity.getDisplayName());
        org.junit.Assert.assertEquals("section_name", entity.getSectionName());
        org.junit.Assert.assertEquals("admin", entity.getUserName());
        org.junit.Assert.assertNotNull(entity.getListWidgetLayoutUserWidgetEntity());
        org.junit.Assert.assertNotNull(entity.getListWidgetLayoutUserWidgetEntity().get(0));
        org.junit.Assert.assertNotNull(entity.getListWidgetLayoutUserWidgetEntity().get(0).getWidget().getListWidgetLayoutUserWidgetEntity());
        org.easymock.EasyMock.verify(amc, clusters, cluster, dao, widgetDAO);
    }

    @org.junit.Test
    public void testUpdateResources() throws java.lang.Exception {
        org.apache.ambari.server.controller.AmbariManagementController amc = org.easymock.EasyMock.createMock(org.apache.ambari.server.controller.AmbariManagementController.class);
        org.apache.ambari.server.state.Clusters clusters = org.easymock.EasyMock.createMock(org.apache.ambari.server.state.Clusters.class);
        org.apache.ambari.server.state.Cluster cluster = org.easymock.EasyMock.createMock(org.apache.ambari.server.state.Cluster.class);
        org.easymock.EasyMock.expect(amc.getClusters()).andReturn(clusters).atLeastOnce();
        org.easymock.EasyMock.expect(clusters.getCluster(((java.lang.String) (org.easymock.EasyMock.anyObject())))).andReturn(cluster).atLeastOnce();
        org.easymock.EasyMock.expect(cluster.getClusterId()).andReturn(java.lang.Long.valueOf(1)).atLeastOnce();
        org.easymock.Capture<org.apache.ambari.server.orm.entities.WidgetLayoutEntity> entityCapture = org.easymock.EasyMock.newCapture();
        dao.create(org.easymock.EasyMock.capture(entityCapture));
        org.easymock.EasyMock.expectLastCall();
        org.apache.ambari.server.orm.entities.WidgetEntity widgetEntity = new org.apache.ambari.server.orm.entities.WidgetEntity();
        widgetEntity.setId(1L);
        widgetEntity.setListWidgetLayoutUserWidgetEntity(new java.util.ArrayList<>());
        org.apache.ambari.server.orm.entities.WidgetEntity widgetEntity2 = new org.apache.ambari.server.orm.entities.WidgetEntity();
        widgetEntity2.setId(2L);
        widgetEntity2.setListWidgetLayoutUserWidgetEntity(new java.util.ArrayList<>());
        org.easymock.EasyMock.expect(widgetDAO.findById(1L)).andReturn(widgetEntity).atLeastOnce();
        org.easymock.EasyMock.replay(amc, clusters, cluster, dao, widgetDAO);
        java.util.Map<java.lang.String, java.lang.Object> requestProps = new java.util.HashMap<>();
        requestProps.put(org.apache.ambari.server.controller.internal.WidgetLayoutResourceProvider.WIDGETLAYOUT_CLUSTER_NAME_PROPERTY_ID, "c1");
        requestProps.put(org.apache.ambari.server.controller.internal.WidgetLayoutResourceProvider.WIDGETLAYOUT_LAYOUT_NAME_PROPERTY_ID, "layout_name");
        requestProps.put(org.apache.ambari.server.controller.internal.WidgetLayoutResourceProvider.WIDGETLAYOUT_DISPLAY_NAME_PROPERTY_ID, "display_name");
        requestProps.put(org.apache.ambari.server.controller.internal.WidgetLayoutResourceProvider.WIDGETLAYOUT_SECTION_NAME_PROPERTY_ID, "section_name");
        requestProps.put(org.apache.ambari.server.controller.internal.WidgetLayoutResourceProvider.WIDGETLAYOUT_USERNAME_PROPERTY_ID, "admin");
        requestProps.put(org.apache.ambari.server.controller.internal.WidgetLayoutResourceProvider.WIDGETLAYOUT_SCOPE_PROPERTY_ID, "CLUSTER");
        java.util.Set<java.util.Map<java.lang.String, java.lang.String>> widgetsInfo = new java.util.LinkedHashSet<>();
        java.util.Map<java.lang.String, java.lang.String> widget = new java.util.HashMap<>();
        widget.put("id", "1");
        widgetsInfo.add(widget);
        requestProps.put(org.apache.ambari.server.controller.internal.WidgetLayoutResourceProvider.WIDGETLAYOUT_WIDGETS_PROPERTY_ID, widgetsInfo);
        org.apache.ambari.server.controller.spi.Request request = org.apache.ambari.server.controller.utilities.PropertyHelper.getCreateRequest(java.util.Collections.singleton(requestProps), null);
        org.apache.ambari.server.controller.internal.WidgetLayoutResourceProvider provider = createProvider(amc);
        provider.createResources(request);
        org.junit.Assert.assertTrue(entityCapture.hasCaptured());
        org.apache.ambari.server.orm.entities.WidgetLayoutEntity entity = entityCapture.getValue();
        org.junit.Assert.assertNotNull(entity);
        org.apache.ambari.server.controller.spi.Predicate predicate = new org.apache.ambari.server.controller.utilities.PredicateBuilder().property(org.apache.ambari.server.controller.internal.WidgetLayoutResourceProvider.WIDGETLAYOUT_CLUSTER_NAME_PROPERTY_ID).equals("c1").and().property(org.apache.ambari.server.controller.internal.WidgetLayoutResourceProvider.WIDGETLAYOUT_ID_PROPERTY_ID).equals("1").and().property(org.apache.ambari.server.controller.internal.WidgetLayoutResourceProvider.WIDGETLAYOUT_USERNAME_PROPERTY_ID).equals("username").toPredicate();
        entity.setId(java.lang.Long.valueOf(1));
        java.lang.String oldLayoutName = entity.getLayoutName();
        java.lang.String oldScope = entity.getScope();
        org.easymock.EasyMock.resetToStrict(dao, widgetDAO);
        org.easymock.EasyMock.expect(dao.findById(1L)).andReturn(entity).anyTimes();
        org.easymock.EasyMock.expect(dao.mergeWithFlush(((org.apache.ambari.server.orm.entities.WidgetLayoutEntity) (org.easymock.EasyMock.anyObject())))).andReturn(entity).anyTimes();
        org.easymock.EasyMock.expect(widgetDAO.merge(widgetEntity)).andReturn(widgetEntity).anyTimes();
        org.easymock.EasyMock.expect(widgetDAO.findById(2L)).andReturn(widgetEntity2).anyTimes();
        org.easymock.EasyMock.replay(dao, widgetDAO);
        requestProps = new java.util.HashMap<>();
        requestProps.put(org.apache.ambari.server.controller.internal.WidgetLayoutResourceProvider.WIDGETLAYOUT_LAYOUT_NAME_PROPERTY_ID, "layout_name_new");
        requestProps.put(org.apache.ambari.server.controller.internal.WidgetLayoutResourceProvider.WIDGETLAYOUT_SCOPE_PROPERTY_ID, "USER");
        requestProps.put(org.apache.ambari.server.controller.internal.WidgetLayoutResourceProvider.WIDGETLAYOUT_ID_PROPERTY_ID, "1");
        widget.put("id", "2");
        widgetsInfo.add(widget);
        requestProps.put(org.apache.ambari.server.controller.internal.WidgetLayoutResourceProvider.WIDGETLAYOUT_WIDGETS_PROPERTY_ID, widgetsInfo);
        request = org.apache.ambari.server.controller.utilities.PropertyHelper.getUpdateRequest(requestProps, null);
        provider.updateResources(request, predicate);
        org.junit.Assert.assertFalse(oldLayoutName.equals(entity.getLayoutName()));
        org.junit.Assert.assertFalse(oldScope.equals(entity.getScope()));
        org.easymock.EasyMock.verify(amc, clusters, cluster, dao, widgetDAO);
    }

    @org.junit.Test
    public void testDeleteResources() throws java.lang.Exception {
        org.apache.ambari.server.controller.AmbariManagementController amc = org.easymock.EasyMock.createMock(org.apache.ambari.server.controller.AmbariManagementController.class);
        org.apache.ambari.server.state.Clusters clusters = org.easymock.EasyMock.createMock(org.apache.ambari.server.state.Clusters.class);
        org.apache.ambari.server.state.Cluster cluster = org.easymock.EasyMock.createMock(org.apache.ambari.server.state.Cluster.class);
        org.easymock.EasyMock.expect(amc.getClusters()).andReturn(clusters).atLeastOnce();
        org.easymock.EasyMock.expect(clusters.getCluster(((java.lang.String) (org.easymock.EasyMock.anyObject())))).andReturn(cluster).atLeastOnce();
        org.easymock.EasyMock.expect(cluster.getClusterId()).andReturn(java.lang.Long.valueOf(1)).anyTimes();
        org.easymock.Capture<org.apache.ambari.server.orm.entities.WidgetLayoutEntity> entityCapture = org.easymock.EasyMock.newCapture();
        dao.create(org.easymock.EasyMock.capture(entityCapture));
        org.easymock.EasyMock.expectLastCall();
        org.easymock.EasyMock.replay(amc, clusters, cluster, dao);
        org.apache.ambari.server.controller.internal.WidgetLayoutResourceProvider provider = createProvider(amc);
        java.util.Map<java.lang.String, java.lang.Object> requestProps = new java.util.HashMap<>();
        requestProps.put(org.apache.ambari.server.controller.internal.WidgetLayoutResourceProvider.WIDGETLAYOUT_CLUSTER_NAME_PROPERTY_ID, "c1");
        requestProps.put(org.apache.ambari.server.controller.internal.WidgetLayoutResourceProvider.WIDGETLAYOUT_LAYOUT_NAME_PROPERTY_ID, "layout_name");
        requestProps.put(org.apache.ambari.server.controller.internal.WidgetLayoutResourceProvider.WIDGETLAYOUT_DISPLAY_NAME_PROPERTY_ID, "display_name");
        requestProps.put(org.apache.ambari.server.controller.internal.WidgetLayoutResourceProvider.WIDGETLAYOUT_SECTION_NAME_PROPERTY_ID, "section_name");
        requestProps.put(org.apache.ambari.server.controller.internal.WidgetLayoutResourceProvider.WIDGETLAYOUT_USERNAME_PROPERTY_ID, "admin");
        requestProps.put(org.apache.ambari.server.controller.internal.WidgetLayoutResourceProvider.WIDGETLAYOUT_SCOPE_PROPERTY_ID, "CLUSTER");
        java.util.Set widgetsInfo = new java.util.LinkedHashSet();
        requestProps.put(org.apache.ambari.server.controller.internal.WidgetLayoutResourceProvider.WIDGETLAYOUT_WIDGETS_PROPERTY_ID, widgetsInfo);
        org.apache.ambari.server.controller.spi.Request request = org.apache.ambari.server.controller.utilities.PropertyHelper.getCreateRequest(java.util.Collections.singleton(requestProps), null);
        provider.createResources(request);
        org.junit.Assert.assertTrue(entityCapture.hasCaptured());
        org.apache.ambari.server.orm.entities.WidgetLayoutEntity entity = entityCapture.getValue();
        org.junit.Assert.assertNotNull(entity);
        org.apache.ambari.server.controller.spi.Predicate predicate = new org.apache.ambari.server.controller.utilities.PredicateBuilder().property(org.apache.ambari.server.controller.internal.WidgetLayoutResourceProvider.WIDGETLAYOUT_CLUSTER_NAME_PROPERTY_ID).equals("c1").and().property(org.apache.ambari.server.controller.internal.WidgetLayoutResourceProvider.WIDGETLAYOUT_ID_PROPERTY_ID).equals("1").and().property(org.apache.ambari.server.controller.internal.WidgetLayoutResourceProvider.WIDGETLAYOUT_USERNAME_PROPERTY_ID).equals("username").toPredicate();
        entity.setId(java.lang.Long.valueOf(1));
        org.easymock.EasyMock.resetToStrict(dao);
        org.easymock.EasyMock.expect(dao.findById(1L)).andReturn(entity).anyTimes();
        dao.remove(org.easymock.EasyMock.capture(entityCapture));
        org.easymock.EasyMock.expectLastCall();
        org.easymock.EasyMock.replay(dao);
        provider.deleteResources(request, predicate);
        org.apache.ambari.server.orm.entities.WidgetLayoutEntity entity1 = entityCapture.getValue();
        org.junit.Assert.assertEquals(java.lang.Long.valueOf(1), entity1.getId());
        org.easymock.EasyMock.verify(amc, clusters, cluster, dao);
    }

    private org.apache.ambari.server.controller.internal.WidgetLayoutResourceProvider createProvider(org.apache.ambari.server.controller.AmbariManagementController amc) {
        return new org.apache.ambari.server.controller.internal.WidgetLayoutResourceProvider(amc);
    }

    private java.util.List<org.apache.ambari.server.orm.entities.WidgetLayoutEntity> getMockEntities() throws java.lang.Exception {
        org.apache.ambari.server.orm.entities.WidgetLayoutEntity widgetLayoutEntity = new org.apache.ambari.server.orm.entities.WidgetLayoutEntity();
        widgetLayoutEntity.setClusterId(java.lang.Long.valueOf(1L));
        widgetLayoutEntity.setLayoutName("layout name0");
        widgetLayoutEntity.setSectionName("section0");
        widgetLayoutEntity.setUserName("username");
        widgetLayoutEntity.setScope("CLUSTER");
        widgetLayoutEntity.setDisplayName("displ_name");
        java.util.List<org.apache.ambari.server.orm.entities.WidgetLayoutUserWidgetEntity> layoutUserWidgetEntityList = new java.util.LinkedList<>();
        widgetLayoutEntity.setListWidgetLayoutUserWidgetEntity(layoutUserWidgetEntityList);
        return java.util.Arrays.asList(widgetLayoutEntity);
    }

    private class MockModule implements com.google.inject.Module {
        @java.lang.Override
        public void configure(com.google.inject.Binder binder) {
            binder.bind(org.apache.ambari.server.orm.dao.WidgetLayoutDAO.class).toInstance(dao);
            binder.bind(org.apache.ambari.server.state.Clusters.class).toInstance(org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Clusters.class));
            binder.bind(org.apache.ambari.server.state.Cluster.class).toInstance(org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Cluster.class));
            binder.bind(org.apache.ambari.server.metadata.ActionMetadata.class);
            binder.bind(org.apache.ambari.server.orm.dao.WidgetDAO.class).toInstance(widgetDAO);
        }
    }
}
