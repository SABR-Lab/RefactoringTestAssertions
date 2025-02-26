package org.apache.ambari.server.controller.spi;
public interface ExtendedResourceProvider extends org.apache.ambari.server.controller.spi.ResourceProvider {
    org.apache.ambari.server.controller.spi.QueryResponse queryForResources(org.apache.ambari.server.controller.spi.Request request, org.apache.ambari.server.controller.spi.Predicate predicate) throws org.apache.ambari.server.controller.spi.SystemException, org.apache.ambari.server.controller.spi.UnsupportedPropertyException, org.apache.ambari.server.controller.spi.NoSuchResourceException, org.apache.ambari.server.controller.spi.NoSuchParentResourceException;
}
