package org.apache.ambari.view.capacityscheduler.utils;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.json.simple.JSONObject;
public class ServiceFormattedException extends javax.ws.rs.WebApplicationException {
    private static final int STATUS = 500;

    public ServiceFormattedException(java.lang.String message) {
        this(message, null);
    }

    public ServiceFormattedException(java.lang.String message, java.lang.Throwable exception) {
        super(org.apache.ambari.view.capacityscheduler.utils.ServiceFormattedException.errorEntity(message, exception));
    }

    protected static javax.ws.rs.core.Response errorEntity(java.lang.String message, java.lang.Throwable e) {
        java.util.HashMap<java.lang.String, java.lang.Object> response = new java.util.HashMap<java.lang.String, java.lang.Object>();
        response.put("message", message);
        java.lang.String trace = null;
        if (e != null)
            trace = org.apache.commons.lang.exception.ExceptionUtils.getStackTrace(e);

        response.put("trace", trace);
        response.put("status", org.apache.ambari.view.capacityscheduler.utils.ServiceFormattedException.STATUS);
        return javax.ws.rs.core.Response.status(org.apache.ambari.view.capacityscheduler.utils.ServiceFormattedException.STATUS).entity(new org.json.simple.JSONObject(response)).type(javax.ws.rs.core.MediaType.APPLICATION_JSON).build();
    }
}
