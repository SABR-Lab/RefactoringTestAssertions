package org.apache.ambari.server.orm.dao;
import javax.persistence.EntityManager;
import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
public class PrincipalTypeDAOTest {
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
        org.apache.ambari.server.orm.entities.PrincipalTypeEntity entity = new org.apache.ambari.server.orm.entities.PrincipalTypeEntity();
        org.easymock.EasyMock.expect(entityManager.find(org.apache.ambari.server.orm.entities.PrincipalTypeEntity.class, 99)).andReturn(entity);
        org.easymock.EasyMock.replay(entityManager);
        org.apache.ambari.server.orm.dao.PrincipalTypeDAO dao = new org.apache.ambari.server.orm.dao.PrincipalTypeDAO();
        dao.entityManagerProvider = entityManagerProvider;
        org.junit.Assert.assertEquals(entity, dao.findById(99));
    }
}
