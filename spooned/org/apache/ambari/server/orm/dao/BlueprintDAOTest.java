package org.apache.ambari.server.orm.dao;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;
public class BlueprintDAOTest {
    com.google.inject.Provider<javax.persistence.EntityManager> entityManagerProvider = org.easymock.EasyMock.createStrictMock(com.google.inject.Provider.class);

    javax.persistence.EntityManager entityManager = org.easymock.EasyMock.createStrictMock(javax.persistence.EntityManager.class);

    @org.junit.Before
    public void init() {
        org.easymock.EasyMock.reset(entityManagerProvider);
        org.easymock.EasyMock.expect(entityManagerProvider.get()).andReturn(entityManager).atLeastOnce();
        org.easymock.EasyMock.replay(entityManagerProvider);
    }

    @org.junit.Test
    public void testFindByName() {
        org.apache.ambari.server.orm.entities.BlueprintEntity entity = new org.apache.ambari.server.orm.entities.BlueprintEntity();
        org.easymock.EasyMock.expect(entityManager.find(org.easymock.EasyMock.eq(org.apache.ambari.server.orm.entities.BlueprintEntity.class), org.easymock.EasyMock.eq("test-cluster-name"))).andReturn(entity);
        org.easymock.EasyMock.replay(entityManager);
        org.apache.ambari.server.orm.dao.BlueprintDAO dao = new org.apache.ambari.server.orm.dao.BlueprintDAO();
        dao.entityManagerProvider = entityManagerProvider;
        org.apache.ambari.server.orm.entities.BlueprintEntity result = dao.findByName("test-cluster-name");
        org.junit.Assert.assertSame(result, entity);
        org.easymock.EasyMock.verify(entityManagerProvider, entityManager);
    }

    @org.junit.Test
    public void testFindAll() {
        org.apache.ambari.server.orm.entities.BlueprintEntity entity = new org.apache.ambari.server.orm.entities.BlueprintEntity();
        javax.persistence.TypedQuery<org.apache.ambari.server.orm.entities.BlueprintEntity> query = org.easymock.EasyMock.createStrictMock(javax.persistence.TypedQuery.class);
        org.easymock.EasyMock.expect(entityManager.createNamedQuery(org.easymock.EasyMock.eq("allBlueprints"), org.easymock.EasyMock.eq(org.apache.ambari.server.orm.entities.BlueprintEntity.class))).andReturn(query);
        org.easymock.EasyMock.expect(query.getResultList()).andReturn(java.util.Collections.singletonList(entity));
        org.easymock.EasyMock.replay(entityManager, query);
        org.apache.ambari.server.orm.dao.BlueprintDAO dao = new org.apache.ambari.server.orm.dao.BlueprintDAO();
        dao.entityManagerProvider = entityManagerProvider;
        java.util.List<org.apache.ambari.server.orm.entities.BlueprintEntity> results = dao.findAll();
        org.junit.Assert.assertEquals(1, results.size());
        org.junit.Assert.assertSame(entity, results.get(0));
        org.easymock.EasyMock.verify(entityManagerProvider, entityManager, query);
    }

    @org.junit.Test
    public void testRefresh() {
        org.apache.ambari.server.orm.entities.BlueprintEntity entity = new org.apache.ambari.server.orm.entities.BlueprintEntity();
        entityManager.refresh(org.easymock.EasyMock.eq(entity));
        org.easymock.EasyMock.replay(entityManager);
        org.apache.ambari.server.orm.dao.BlueprintDAO dao = new org.apache.ambari.server.orm.dao.BlueprintDAO();
        dao.entityManagerProvider = entityManagerProvider;
        dao.refresh(entity);
        org.easymock.EasyMock.verify(entityManagerProvider, entityManager);
    }

    @org.junit.Test
    public void testCreate() {
        org.apache.ambari.server.orm.entities.BlueprintEntity entity = new org.apache.ambari.server.orm.entities.BlueprintEntity();
        entityManager.persist(org.easymock.EasyMock.eq(entity));
        org.easymock.EasyMock.replay(entityManager);
        org.apache.ambari.server.orm.dao.BlueprintDAO dao = new org.apache.ambari.server.orm.dao.BlueprintDAO();
        dao.entityManagerProvider = entityManagerProvider;
        dao.create(entity);
        org.easymock.EasyMock.verify(entityManagerProvider, entityManager);
    }

    @org.junit.Test
    public void testMerge() {
        org.apache.ambari.server.orm.entities.BlueprintEntity entity = new org.apache.ambari.server.orm.entities.BlueprintEntity();
        org.apache.ambari.server.orm.entities.BlueprintEntity entity2 = new org.apache.ambari.server.orm.entities.BlueprintEntity();
        org.easymock.EasyMock.expect(entityManager.merge(org.easymock.EasyMock.eq(entity))).andReturn(entity2);
        org.easymock.EasyMock.replay(entityManager);
        org.apache.ambari.server.orm.dao.BlueprintDAO dao = new org.apache.ambari.server.orm.dao.BlueprintDAO();
        dao.entityManagerProvider = entityManagerProvider;
        org.junit.Assert.assertSame(entity2, dao.merge(entity));
        org.easymock.EasyMock.verify(entityManagerProvider, entityManager);
    }

    @org.junit.Test
    public void testRemove() {
        org.apache.ambari.server.orm.entities.BlueprintEntity entity = new org.apache.ambari.server.orm.entities.BlueprintEntity();
        org.apache.ambari.server.orm.entities.BlueprintEntity entity2 = new org.apache.ambari.server.orm.entities.BlueprintEntity();
        org.easymock.EasyMock.expect(entityManager.merge(org.easymock.EasyMock.eq(entity))).andReturn(entity2);
        entityManager.remove(org.easymock.EasyMock.eq(entity2));
        org.easymock.EasyMock.replay(entityManager);
        org.apache.ambari.server.orm.dao.BlueprintDAO dao = new org.apache.ambari.server.orm.dao.BlueprintDAO();
        dao.entityManagerProvider = entityManagerProvider;
        dao.remove(entity);
        org.easymock.EasyMock.verify(entityManagerProvider, entityManager);
    }

    @org.junit.Test
    public void testRemoveByName() {
        org.apache.ambari.server.orm.entities.BlueprintEntity entity = new org.apache.ambari.server.orm.entities.BlueprintEntity();
        org.apache.ambari.server.orm.dao.BlueprintDAO dao = new org.apache.ambari.server.orm.dao.BlueprintDAO();
        dao.entityManagerProvider = entityManagerProvider;
        org.easymock.EasyMock.expect(entityManager.find(org.easymock.EasyMock.eq(org.apache.ambari.server.orm.entities.BlueprintEntity.class), org.easymock.EasyMock.eq("test"))).andReturn(entity);
        entityManager.remove(entity);
        org.easymock.EasyMock.expectLastCall();
        org.easymock.EasyMock.replay(entityManager);
        dao.removeByName("test");
        org.easymock.EasyMock.verify(entityManager);
    }
}
