package org.apache.ambari.server.orm.dao;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;
public class GroupDAOTest {
    @com.google.inject.Inject
    org.apache.ambari.server.orm.dao.DaoUtils daoUtils;

    com.google.inject.Provider<javax.persistence.EntityManager> entityManagerProvider = org.easymock.EasyMock.createStrictMock(com.google.inject.Provider.class);

    javax.persistence.EntityManager entityManager = org.easymock.EasyMock.createStrictMock(javax.persistence.EntityManager.class);

    @org.junit.Before
    public void init() {
        org.easymock.EasyMock.reset(entityManagerProvider);
        org.easymock.EasyMock.expect(entityManagerProvider.get()).andReturn(entityManager).atLeastOnce();
        org.easymock.EasyMock.replay(entityManagerProvider);
    }

    @org.junit.Test
    public void testfindGroupByName() {
        final java.lang.String groupName = "engineering";
        final org.apache.ambari.server.orm.entities.GroupEntity entity = new org.apache.ambari.server.orm.entities.GroupEntity();
        entity.setGroupName(groupName);
        javax.persistence.TypedQuery<org.apache.ambari.server.orm.entities.GroupEntity> query = org.easymock.EasyMock.createStrictMock(javax.persistence.TypedQuery.class);
        org.easymock.EasyMock.expect(entityManager.createNamedQuery(org.easymock.EasyMock.eq("groupByName"), org.easymock.EasyMock.eq(org.apache.ambari.server.orm.entities.GroupEntity.class))).andReturn(query);
        org.easymock.EasyMock.expect(query.setParameter("groupname", groupName)).andReturn(query);
        org.easymock.EasyMock.expect(query.getSingleResult()).andReturn(entity);
        org.easymock.EasyMock.replay(entityManager, query);
        final org.apache.ambari.server.orm.dao.GroupDAO dao = new org.apache.ambari.server.orm.dao.GroupDAO();
        dao.entityManagerProvider = entityManagerProvider;
        final org.apache.ambari.server.orm.entities.GroupEntity result = dao.findGroupByName(groupName);
        org.junit.Assert.assertSame(entity, result);
        org.easymock.EasyMock.verify(entityManagerProvider, entityManager, query);
    }
}
