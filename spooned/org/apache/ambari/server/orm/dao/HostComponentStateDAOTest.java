package org.apache.ambari.server.orm.dao;
import javax.persistence.EntityManager;
import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
public class HostComponentStateDAOTest {
    @org.junit.Test
    public void testRemove() throws java.lang.Exception {
        com.google.inject.Provider<javax.persistence.EntityManager> entityManagerProvider = org.easymock.EasyMock.createNiceMock(com.google.inject.Provider.class);
        javax.persistence.EntityManager entityManager = org.easymock.EasyMock.createNiceMock(javax.persistence.EntityManager.class);
        org.apache.ambari.server.orm.dao.HostDAO hostDAO = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.orm.dao.HostDAO.class);
        org.apache.ambari.server.orm.entities.HostEntity hostEntity = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.orm.entities.HostEntity.class);
        org.apache.ambari.server.orm.entities.HostComponentStateEntity hostComponentStateEntity = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.orm.entities.HostComponentStateEntity.class);
        org.easymock.EasyMock.expect(entityManagerProvider.get()).andReturn(entityManager).anyTimes();
        entityManager.remove(hostComponentStateEntity);
        org.easymock.EasyMock.replay(entityManagerProvider, entityManager, hostDAO, hostEntity, hostComponentStateEntity);
        org.apache.ambari.server.orm.dao.HostComponentStateDAO dao = new org.apache.ambari.server.orm.dao.HostComponentStateDAO();
        dao.entityManagerProvider = entityManagerProvider;
        dao.hostDAO = hostDAO;
        dao.remove(hostComponentStateEntity);
        org.easymock.EasyMock.verify(entityManagerProvider, entityManager, hostDAO, hostEntity, hostComponentStateEntity);
    }
}
