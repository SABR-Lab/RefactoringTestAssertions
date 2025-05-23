package org.apache.ambari.server.api.services.persistence;
public interface PersistenceManager {
    org.apache.ambari.server.controller.spi.RequestStatus create(org.apache.ambari.server.api.resources.ResourceInstance resource, org.apache.ambari.server.api.services.RequestBody requestBody) throws org.apache.ambari.server.controller.spi.UnsupportedPropertyException, org.apache.ambari.server.controller.spi.ResourceAlreadyExistsException, org.apache.ambari.server.controller.spi.NoSuchParentResourceException, org.apache.ambari.server.controller.spi.SystemException;

    org.apache.ambari.server.controller.spi.RequestStatus update(org.apache.ambari.server.api.resources.ResourceInstance resource, org.apache.ambari.server.api.services.RequestBody requestBody) throws org.apache.ambari.server.controller.spi.UnsupportedPropertyException, org.apache.ambari.server.controller.spi.SystemException, org.apache.ambari.server.controller.spi.NoSuchParentResourceException, org.apache.ambari.server.controller.spi.NoSuchResourceException;

    org.apache.ambari.server.controller.spi.RequestStatus delete(org.apache.ambari.server.api.resources.ResourceInstance resource, org.apache.ambari.server.api.services.RequestBody requestBody) throws org.apache.ambari.server.controller.spi.UnsupportedPropertyException, org.apache.ambari.server.controller.spi.SystemException, org.apache.ambari.server.controller.spi.NoSuchParentResourceException, org.apache.ambari.server.controller.spi.NoSuchResourceException;
}
