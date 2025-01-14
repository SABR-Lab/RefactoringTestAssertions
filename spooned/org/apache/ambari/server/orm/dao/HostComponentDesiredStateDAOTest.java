package org.apache.ambari.server.orm.dao;
import javax.persistence.EntityManager;
import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
public class HostComponentDesiredStateDAOTest {
    @org.junit.Test
    public void testRemove() throws java.lang.Exception {
        com.google.inject.Provider<javax.persistence.EntityManager> entityManagerProvider = org.easymock.EasyMock.createNiceMock(com.google.inject.Provider.class);
        javax.persistence.EntityManager entityManager = org.easymock.EasyMock.createNiceMock(javax.persistence.EntityManager.class);
        org.apache.ambari.server.orm.dao.HostDAO hostDAO = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.orm.dao.HostDAO.class);
        org.apache.ambari.server.orm.entities.HostEntity hostEntity = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.orm.entities.HostEntity.class);
        org.apache.ambari.server.orm.entities.HostComponentDesiredStateEntity hostComponentDesiredStateEntity = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.orm.entities.HostComponentDesiredStateEntity.class);
        org.easymock.EasyMock.expect(entityManagerProvider.get()).andReturn(entityManager).anyTimes();
        entityManager.remove(hostComponentDesiredStateEntity);
        hostEntity.removeHostComponentDesiredStateEntity(hostComponentDesiredStateEntity);
        org.easymock.EasyMock.expect(hostDAO.merge(hostEntity)).andReturn(hostEntity).atLeastOnce();
        org.easymock.EasyMock.expect(hostComponentDesiredStateEntity.getHostEntity()).andReturn(hostEntity).atLeastOnce();
        org.easymock.EasyMock.replay(entityManagerProvider, entityManager, hostDAO, hostEntity, hostComponentDesiredStateEntity);
        org.apache.ambari.server.orm.dao.HostComponentDesiredStateDAO dao = new org.apache.ambari.server.orm.dao.HostComponentDesiredStateDAO();
        dao.entityManagerProvider = entityManagerProvider;
        dao.hostDAO = hostDAO;
        dao.remove(hostComponentDesiredStateEntity);
        org.easymock.EasyMock.verify(entityManagerProvider, entityManager, hostDAO, hostEntity, hostComponentDesiredStateEntity);
    }
}
