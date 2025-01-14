package org.apache.ambari.server.security.authorization;
import static org.easymock.EasyMock.anyLong;
import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
public class AuthorizationHelperInitializer {
    public static void viewInstanceDAOReturningNull() {
        com.google.inject.Provider viewInstanceDAOProvider = org.easymock.EasyMock.createNiceMock(com.google.inject.Provider.class);
        org.apache.ambari.server.orm.dao.ViewInstanceDAO viewInstanceDAO = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.orm.dao.ViewInstanceDAO.class);
        org.easymock.EasyMock.expect(viewInstanceDAOProvider.get()).andReturn(viewInstanceDAO).anyTimes();
        org.easymock.EasyMock.expect(viewInstanceDAO.findByResourceId(org.easymock.EasyMock.anyLong())).andReturn(null).anyTimes();
        org.easymock.EasyMock.replay(viewInstanceDAOProvider, viewInstanceDAO);
        org.apache.ambari.server.security.authorization.AuthorizationHelper.viewInstanceDAOProvider = viewInstanceDAOProvider;
    }
}
