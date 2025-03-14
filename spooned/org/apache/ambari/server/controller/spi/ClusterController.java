package org.apache.ambari.server.controller.spi;
public interface ClusterController extends org.apache.ambari.server.controller.spi.SchemaFactory {
    org.apache.ambari.server.controller.spi.QueryResponse getResources(org.apache.ambari.server.controller.spi.Resource.Type type, org.apache.ambari.server.controller.spi.Request request, org.apache.ambari.server.controller.spi.Predicate predicate) throws org.apache.ambari.server.controller.spi.UnsupportedPropertyException, org.apache.ambari.server.controller.spi.NoSuchResourceException, org.apache.ambari.server.controller.spi.NoSuchParentResourceException, org.apache.ambari.server.controller.spi.SystemException;

    java.util.Set<org.apache.ambari.server.controller.spi.Resource> populateResources(org.apache.ambari.server.controller.spi.Resource.Type type, java.util.Set<org.apache.ambari.server.controller.spi.Resource> resources, org.apache.ambari.server.controller.spi.Request request, org.apache.ambari.server.controller.spi.Predicate predicate) throws org.apache.ambari.server.controller.spi.SystemException;

    java.lang.Iterable<org.apache.ambari.server.controller.spi.Resource> getIterable(org.apache.ambari.server.controller.spi.Resource.Type type, org.apache.ambari.server.controller.spi.QueryResponse queryResponse, org.apache.ambari.server.controller.spi.Request request, org.apache.ambari.server.controller.spi.Predicate predicate, org.apache.ambari.server.controller.spi.PageRequest pageRequest, org.apache.ambari.server.controller.spi.SortRequest sortRequest) throws org.apache.ambari.server.controller.spi.NoSuchParentResourceException, org.apache.ambari.server.controller.spi.UnsupportedPropertyException, org.apache.ambari.server.controller.spi.NoSuchResourceException, org.apache.ambari.server.controller.spi.SystemException;

    org.apache.ambari.server.controller.spi.PageResponse getPage(org.apache.ambari.server.controller.spi.Resource.Type type, org.apache.ambari.server.controller.spi.QueryResponse queryResponse, org.apache.ambari.server.controller.spi.Request request, org.apache.ambari.server.controller.spi.Predicate predicate, org.apache.ambari.server.controller.spi.PageRequest pageRequest, org.apache.ambari.server.controller.spi.SortRequest sortRequest) throws org.apache.ambari.server.controller.spi.UnsupportedPropertyException, org.apache.ambari.server.controller.spi.SystemException, org.apache.ambari.server.controller.spi.NoSuchResourceException, org.apache.ambari.server.controller.spi.NoSuchParentResourceException;

    @java.lang.Override
    org.apache.ambari.server.controller.spi.Schema getSchema(org.apache.ambari.server.controller.spi.Resource.Type type);

    org.apache.ambari.server.controller.spi.ResourceProvider ensureResourceProvider(org.apache.ambari.server.controller.spi.Resource.Type type);

    org.apache.ambari.server.controller.spi.RequestStatus createResources(org.apache.ambari.server.controller.spi.Resource.Type type, org.apache.ambari.server.controller.spi.Request request) throws org.apache.ambari.server.controller.spi.UnsupportedPropertyException, org.apache.ambari.server.controller.spi.SystemException, org.apache.ambari.server.controller.spi.ResourceAlreadyExistsException, org.apache.ambari.server.controller.spi.NoSuchParentResourceException;

    org.apache.ambari.server.controller.spi.RequestStatus updateResources(org.apache.ambari.server.controller.spi.Resource.Type type, org.apache.ambari.server.controller.spi.Request request, org.apache.ambari.server.controller.spi.Predicate predicate) throws org.apache.ambari.server.controller.spi.UnsupportedPropertyException, org.apache.ambari.server.controller.spi.SystemException, org.apache.ambari.server.controller.spi.NoSuchResourceException, org.apache.ambari.server.controller.spi.NoSuchParentResourceException;

    org.apache.ambari.server.controller.spi.RequestStatus deleteResources(org.apache.ambari.server.controller.spi.Resource.Type type, org.apache.ambari.server.controller.spi.Request request, org.apache.ambari.server.controller.spi.Predicate predicate) throws org.apache.ambari.server.controller.spi.UnsupportedPropertyException, org.apache.ambari.server.controller.spi.SystemException, org.apache.ambari.server.controller.spi.NoSuchResourceException, org.apache.ambari.server.controller.spi.NoSuchParentResourceException;

    org.apache.ambari.server.controller.spi.Predicate getAmendedPredicate(org.apache.ambari.server.controller.spi.Resource.Type type, org.apache.ambari.server.controller.spi.Predicate predicate);
}
