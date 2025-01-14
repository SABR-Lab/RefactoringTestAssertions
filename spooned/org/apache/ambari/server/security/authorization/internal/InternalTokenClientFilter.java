package org.apache.ambari.server.security.authorization.internal;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
public class InternalTokenClientFilter implements javax.ws.rs.client.ClientRequestFilter {
    public static final java.lang.String INTERNAL_TOKEN_HEADER = "X-Internal-Token";

    private final org.apache.ambari.server.security.authorization.internal.InternalTokenStorage tokenStorage;

    @com.google.inject.Inject
    public InternalTokenClientFilter(org.apache.ambari.server.security.authorization.internal.InternalTokenStorage tokenStorage) {
        this.tokenStorage = tokenStorage;
    }

    public void filter(javax.ws.rs.client.ClientRequestContext clientRequestContext) throws java.io.IOException {
        clientRequestContext.getHeaders().add(org.apache.ambari.server.security.authorization.internal.InternalTokenClientFilter.INTERNAL_TOKEN_HEADER, tokenStorage.getInternalToken());
    }
}
