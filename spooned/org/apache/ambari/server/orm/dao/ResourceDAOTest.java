package org.apache.ambari.server.orm.dao;
import javax.persistence.EntityManager;
import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
public class ResourceDAOTest {
    com.google.inject.Provider<javax.persistence.EntityManager> entityManagerProvider = org.easymock.EasyMock.createStrictMock(com.google.inject.Provider.class);

    javax.persistence.EntityManager entityManager = org.easymock.EasyMock.createStrictMock(javax.persistence.EntityManager.class);

    @org.junit.Before
    public void init() {
        org.easymock.EasyMock.reset(entityManagerProvider);
        org.easymock.EasyMock.expect(entityManagerProvider.get()).andReturn(entityManager).atLeastOnce();
        org.easymock.EasyMock.replay(entityManagerProvider);
    }

    @org.junit.Test
    public void testFindById() throws java.lang.Exception {
        org.apache.ambari.server.orm.entities.ResourceEntity entity = new org.apache.ambari.server.orm.entities.ResourceEntity();
        org.easymock.EasyMock.expect(entityManager.find(org.apache.ambari.server.orm.entities.ResourceEntity.class, 99L)).andReturn(entity);
        org.easymock.EasyMock.replay(entityManager);
        org.apache.ambari.server.orm.dao.ResourceDAO dao = new org.apache.ambari.server.orm.dao.ResourceDAO();
        dao.entityManagerProvider = entityManagerProvider;
        org.junit.Assert.assertEquals(entity, dao.findById(99L));
    }
}
