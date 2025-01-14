package org.apache.ambari.server.api.services.parsers;
public class JsonRequestBodyParser implements org.apache.ambari.server.api.services.parsers.RequestBodyParser {
    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(org.apache.ambari.server.api.services.parsers.JsonRequestBodyParser.class);

    private static final com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();

    @java.lang.Override
    public java.util.Set<org.apache.ambari.server.api.services.RequestBody> parse(java.lang.String body) throws org.apache.ambari.server.api.services.parsers.BodyParseException {
        java.util.Set<org.apache.ambari.server.api.services.RequestBody> requestBodySet = new java.util.HashSet<>();
        org.apache.ambari.server.api.services.RequestBody rootBody = new org.apache.ambari.server.api.services.RequestBody();
        rootBody.setBody(body);
        if ((body != null) && (body.length() != 0)) {
            try {
                com.fasterxml.jackson.databind.JsonNode root = org.apache.ambari.server.api.services.parsers.JsonRequestBodyParser.mapper.readTree(ensureArrayFormat(body));
                java.util.Iterator<com.fasterxml.jackson.databind.JsonNode> iterator = root.elements();
                while (iterator.hasNext()) {
                    com.fasterxml.jackson.databind.JsonNode node = iterator.next();
                    java.util.Map<java.lang.String, java.lang.Object> mapProperties = new java.util.HashMap<>();
                    java.util.Map<java.lang.String, java.lang.String> requestInfoProps = new java.util.HashMap<>();
                    org.apache.ambari.server.api.services.NamedPropertySet propertySet = new org.apache.ambari.server.api.services.NamedPropertySet("", mapProperties);
                    processNode(node, "", propertySet, requestInfoProps);
                    if (!requestInfoProps.isEmpty()) {
                        org.apache.ambari.server.api.services.RequestBody requestBody = new org.apache.ambari.server.api.services.RequestBody();
                        requestBody.setBody(body);
                        for (java.util.Map.Entry<java.lang.String, java.lang.String> entry : requestInfoProps.entrySet()) {
                            java.lang.String key = entry.getKey();
                            java.lang.String value = entry.getValue();
                            requestBody.addRequestInfoProperty(key, value);
                            if (key.equals(org.apache.ambari.server.api.services.parsers.RequestBodyParser.QUERY_FIELD_NAME)) {
                                requestBody.setQueryString(value);
                            }
                        }
                        if (!propertySet.getProperties().isEmpty()) {
                            requestBody.addPropertySet(propertySet);
                        }
                        requestBodySet.add(requestBody);
                    } else {
                        if (!propertySet.getProperties().isEmpty()) {
                            rootBody.addPropertySet(propertySet);
                        }
                        requestBodySet.add(rootBody);
                    }
                } 
            } catch (java.io.IOException e) {
                if (org.apache.ambari.server.api.services.parsers.JsonRequestBodyParser.LOG.isDebugEnabled()) {
                    org.apache.ambari.server.api.services.parsers.JsonRequestBodyParser.LOG.debug("Caught exception parsing msg body.");
                    org.apache.ambari.server.api.services.parsers.JsonRequestBodyParser.LOG.debug("Message Body: {}", body, e);
                }
                throw new org.apache.ambari.server.api.services.parsers.BodyParseException(e);
            }
        }
        if (requestBodySet.isEmpty()) {
            requestBodySet.add(rootBody);
        }
        return requestBodySet;
    }

    private void processNode(com.fasterxml.jackson.databind.JsonNode node, java.lang.String path, org.apache.ambari.server.api.services.NamedPropertySet propertySet, java.util.Map<java.lang.String, java.lang.String> requestInfoProps) throws java.io.IOException {
        java.util.Iterator<java.lang.String> iterator = node.fieldNames();
        while (iterator.hasNext()) {
            java.lang.String name = iterator.next();
            com.fasterxml.jackson.databind.JsonNode child = node.get(name);
            if (child.isArray()) {
                java.util.Iterator<com.fasterxml.jackson.databind.JsonNode> arrayIter = child.elements();
                java.util.Set<java.util.Map<java.lang.String, java.lang.Object>> arraySet = new java.util.LinkedHashSet<>();
                java.util.List<java.lang.String> primitives = new java.util.ArrayList<>();
                while (arrayIter.hasNext()) {
                    com.fasterxml.jackson.databind.JsonNode next = arrayIter.next();
                    if (next.isValueNode()) {
                        primitives.add(next.asText());
                    } else {
                        org.apache.ambari.server.api.services.NamedPropertySet arrayPropertySet = new org.apache.ambari.server.api.services.NamedPropertySet(name, new java.util.HashMap<>());
                        processNode(next, "", arrayPropertySet, requestInfoProps);
                        arraySet.add(arrayPropertySet.getProperties());
                    }
                } 
                java.lang.Object properties = (primitives.isEmpty()) ? arraySet : primitives;
                propertySet.getProperties().put(org.apache.ambari.server.controller.utilities.PropertyHelper.getPropertyId(path, name), properties);
            } else if (child.isContainerNode()) {
                if (name.equals(org.apache.ambari.server.api.services.parsers.RequestBodyParser.BODY_TITLE)) {
                    name = "";
                }
                if (name.equals(org.apache.ambari.server.api.services.parsers.RequestBodyParser.REQUEST_BLOB_TITLE)) {
                    propertySet.getProperties().put(org.apache.ambari.server.controller.utilities.PropertyHelper.getPropertyId(path, name), child.toString());
                } else {
                    processNode(child, path.isEmpty() ? name : (path + '/') + name, propertySet, requestInfoProps);
                }
            } else {
                java.lang.String value = (child.isNull()) ? null : child.asText();
                if (path.equals(org.apache.ambari.server.api.services.parsers.RequestBodyParser.REQUEST_INFO_PATH)) {
                    requestInfoProps.put(org.apache.ambari.server.controller.utilities.PropertyHelper.getPropertyId(null, name), value);
                } else if (path.startsWith(org.apache.ambari.server.api.services.parsers.RequestBodyParser.REQUEST_INFO_PATH)) {
                    requestInfoProps.put(org.apache.ambari.server.controller.utilities.PropertyHelper.getPropertyId(path.substring(org.apache.ambari.server.api.services.parsers.RequestBodyParser.REQUEST_INFO_PATH.length() + org.apache.ambari.server.api.services.parsers.RequestBodyParser.SLASH.length()), name), value);
                } else {
                    propertySet.getProperties().put(org.apache.ambari.server.controller.utilities.PropertyHelper.getPropertyId(path.equals(org.apache.ambari.server.api.services.parsers.RequestBodyParser.BODY_TITLE) ? "" : path, name), value);
                }
            }
        } 
    }

    private java.lang.String ensureArrayFormat(java.lang.String s) {
        return s.startsWith("[") ? s : ('[' + s) + ']';
    }
}
