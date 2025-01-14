package org.apache.ambari.server.orm.dao;
import javax.persistence.EntityManager;
import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;
public class ViewInstanceDAOTest {
    com.google.inject.Provider<javax.persistence.EntityManager> entityManagerProvider = org.easymock.EasyMock.createStrictMock(com.google.inject.Provider.class);

    javax.persistence.EntityManager entityManager = org.easymock.EasyMock.createStrictMock(javax.persistence.EntityManager.class);

    @org.junit.Before
    public void init() {
        org.easymock.EasyMock.reset(entityManagerProvider);
        org.easymock.EasyMock.expect(entityManagerProvider.get()).andReturn(entityManager).atLeastOnce();
        org.easymock.EasyMock.replay(entityManagerProvider);
    }

    @org.junit.Test
    public void testMergeData() throws java.lang.Exception {
        org.apache.ambari.server.orm.entities.ViewInstanceDataEntity entity = new org.apache.ambari.server.orm.entities.ViewInstanceDataEntity();
        org.apache.ambari.server.orm.entities.ViewInstanceDataEntity entity2 = new org.apache.ambari.server.orm.entities.ViewInstanceDataEntity();
        org.easymock.EasyMock.expect(entityManager.merge(org.easymock.EasyMock.eq(entity))).andReturn(entity2);
        org.easymock.EasyMock.replay(entityManager);
        org.apache.ambari.server.orm.dao.ViewInstanceDAO dao = new org.apache.ambari.server.orm.dao.ViewInstanceDAO();
        dao.entityManagerProvider = entityManagerProvider;
        org.junit.Assert.assertSame(entity2, dao.mergeData(entity));
        org.easymock.EasyMock.verify(entityManagerProvider, entityManager);
    }

    @org.junit.Test
    public void testRemoveData() throws java.lang.Exception {
        org.apache.ambari.server.orm.entities.ViewInstanceDataEntity entity = new org.apache.ambari.server.orm.entities.ViewInstanceDataEntity();
        org.apache.ambari.server.orm.entities.ViewInstanceDataEntity entity2 = new org.apache.ambari.server.orm.entities.ViewInstanceDataEntity();
        org.easymock.EasyMock.expect(entityManager.merge(org.easymock.EasyMock.eq(entity))).andReturn(entity2);
        entityManager.remove(org.easymock.EasyMock.eq(entity2));
        org.easymock.EasyMock.replay(entityManager);
        org.apache.ambari.server.orm.dao.ViewInstanceDAO dao = new org.apache.ambari.server.orm.dao.ViewInstanceDAO();
        dao.entityManagerProvider = entityManagerProvider;
        dao.removeData(entity);
        org.easymock.EasyMock.verify(entityManagerProvider, entityManager);
    }
}
