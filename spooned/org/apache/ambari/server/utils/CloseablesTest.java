package org.apache.ambari.server.utils;
import static org.easymock.EasyMock.anyString;
import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.replayAll;
import static org.powermock.api.easymock.PowerMock.verifyAll;
public class CloseablesTest {
    @org.junit.Test
    public void silentIfSucceeds() throws java.lang.Exception {
        java.io.Closeable normalCloseable = org.easymock.EasyMock.createNiceMock(java.io.Closeable.class);
        normalCloseable.close();
        org.powermock.api.easymock.PowerMock.replayAll();
        org.apache.ambari.server.utils.Closeables.closeSilently(normalCloseable);
        org.powermock.api.easymock.PowerMock.verifyAll();
    }

    @org.junit.Test
    public void silentIfThrows() throws java.lang.Exception {
        java.io.Closeable throwingCloseable = org.easymock.EasyMock.createNiceMock(java.io.Closeable.class);
        throwingCloseable.close();
        org.easymock.EasyMock.expectLastCall().andThrow(new java.io.IOException());
        org.powermock.api.easymock.PowerMock.replayAll();
        org.apache.ambari.server.utils.Closeables.closeSilently(throwingCloseable);
        org.powermock.api.easymock.PowerMock.verifyAll();
    }

    @org.junit.Test
    public void succeedsWithoutLog() throws java.lang.Exception {
        java.io.Closeable normalCloseable = org.easymock.EasyMock.createNiceMock(java.io.Closeable.class);
        org.slf4j.Logger logger = org.easymock.EasyMock.createStrictMock(org.slf4j.Logger.class);
        normalCloseable.close();
        org.powermock.api.easymock.PowerMock.replayAll();
        org.apache.ambari.server.utils.Closeables.closeLoggingExceptions(normalCloseable, logger);
        org.powermock.api.easymock.PowerMock.verifyAll();
    }

    @org.junit.Test
    public void warnWithThrownException() throws java.lang.Exception {
        java.io.Closeable throwingCloseable = org.easymock.EasyMock.createNiceMock(java.io.Closeable.class);
        org.slf4j.Logger logger = org.easymock.EasyMock.createNiceMock(org.slf4j.Logger.class);
        java.io.IOException e = new java.io.IOException();
        throwingCloseable.close();
        org.easymock.EasyMock.expectLastCall().andThrow(e);
        logger.warn(org.easymock.EasyMock.anyString(), org.easymock.EasyMock.eq(e));
        org.powermock.api.easymock.PowerMock.replayAll();
        org.apache.ambari.server.utils.Closeables.closeLoggingExceptions(throwingCloseable, logger);
        org.powermock.api.easymock.PowerMock.verifyAll();
    }

    @org.junit.Test
    public void ignoresNullCloseable() {
        org.apache.ambari.server.utils.Closeables.closeSilently(null);
    }
}
