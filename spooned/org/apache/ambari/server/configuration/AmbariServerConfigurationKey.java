package org.apache.ambari.server.configuration;
import org.apache.commons.lang.StringUtils;
public enum AmbariServerConfigurationKey {

    AMBARI_MANAGES_LDAP_CONFIGURATION(org.apache.ambari.server.configuration.AmbariServerConfigurationCategory.LDAP_CONFIGURATION, "ambari.ldap.manage_services", org.apache.ambari.server.configuration.ConfigurationPropertyType.PLAINTEXT, "false", "A Boolean value indicating whether Ambari is to manage the LDAP configuration for services or not.", false),
    LDAP_ENABLED_SERVICES(org.apache.ambari.server.configuration.AmbariServerConfigurationCategory.LDAP_CONFIGURATION, "ambari.ldap.enabled_services", org.apache.ambari.server.configuration.ConfigurationPropertyType.PLAINTEXT, null, "A comma-delimited list of services that are expected to be configured for LDAP.  A \"*\" indicates all services.", false),
    LDAP_ENABLED(org.apache.ambari.server.configuration.AmbariServerConfigurationCategory.LDAP_CONFIGURATION, "ambari.ldap.authentication.enabled", org.apache.ambari.server.configuration.ConfigurationPropertyType.PLAINTEXT, "false", "An internal property used for unit testing and development purposes.", false),
    SERVER_HOST(org.apache.ambari.server.configuration.AmbariServerConfigurationCategory.LDAP_CONFIGURATION, "ambari.ldap.connectivity.server.host", org.apache.ambari.server.configuration.ConfigurationPropertyType.PLAINTEXT, "localhost", "The LDAP URL host used for connecting to an LDAP server when authenticating users.", false),
    SERVER_PORT(org.apache.ambari.server.configuration.AmbariServerConfigurationCategory.LDAP_CONFIGURATION, "ambari.ldap.connectivity.server.port", org.apache.ambari.server.configuration.ConfigurationPropertyType.PLAINTEXT, "33389", "The LDAP URL port used for connecting to an LDAP server when authenticating users.", false),
    SECONDARY_SERVER_HOST(org.apache.ambari.server.configuration.AmbariServerConfigurationCategory.LDAP_CONFIGURATION, "ambari.ldap.connectivity.secondary.server.host", org.apache.ambari.server.configuration.ConfigurationPropertyType.PLAINTEXT, null, "A second LDAP URL host to use as a backup when authenticating users.", false),
    SECONDARY_SERVER_PORT(org.apache.ambari.server.configuration.AmbariServerConfigurationCategory.LDAP_CONFIGURATION, "ambari.ldap.connectivity.secondary.server.port", org.apache.ambari.server.configuration.ConfigurationPropertyType.PLAINTEXT, null, "A second LDAP URL port to use as a backup when authenticating users.", false),
    USE_SSL(org.apache.ambari.server.configuration.AmbariServerConfigurationCategory.LDAP_CONFIGURATION, "ambari.ldap.connectivity.use_ssl", org.apache.ambari.server.configuration.ConfigurationPropertyType.PLAINTEXT, "false", "Determines whether to use LDAP over SSL (LDAPS).", false),
    TRUST_STORE(org.apache.ambari.server.configuration.AmbariServerConfigurationCategory.LDAP_CONFIGURATION, "ambari.ldap.connectivity.trust_store", org.apache.ambari.server.configuration.ConfigurationPropertyType.PLAINTEXT, "", "", false),
    TRUST_STORE_TYPE(org.apache.ambari.server.configuration.AmbariServerConfigurationCategory.LDAP_CONFIGURATION, "ambari.ldap.connectivity.trust_store.type", org.apache.ambari.server.configuration.ConfigurationPropertyType.PLAINTEXT, null, "The type of truststore used by the 'javax.net.ssl.trustStoreType' property.", false),
    TRUST_STORE_PATH(org.apache.ambari.server.configuration.AmbariServerConfigurationCategory.LDAP_CONFIGURATION, "ambari.ldap.connectivity.trust_store.path", org.apache.ambari.server.configuration.ConfigurationPropertyType.PLAINTEXT, null, "The location of the truststore to use when setting the 'javax.net.ssl.trustStore' property.", false),
    TRUST_STORE_PASSWORD(org.apache.ambari.server.configuration.AmbariServerConfigurationCategory.LDAP_CONFIGURATION, "ambari.ldap.connectivity.trust_store.password", org.apache.ambari.server.configuration.ConfigurationPropertyType.PASSWORD, null, "The password to use when setting the 'javax.net.ssl.trustStorePassword' property", false),
    ANONYMOUS_BIND(org.apache.ambari.server.configuration.AmbariServerConfigurationCategory.LDAP_CONFIGURATION, "ambari.ldap.connectivity.anonymous_bind", org.apache.ambari.server.configuration.ConfigurationPropertyType.PLAINTEXT, "true", "Determines whether LDAP requests can connect anonymously or if a managed user is required to connect.", false),
    BIND_DN(org.apache.ambari.server.configuration.AmbariServerConfigurationCategory.LDAP_CONFIGURATION, "ambari.ldap.connectivity.bind_dn", org.apache.ambari.server.configuration.ConfigurationPropertyType.PLAINTEXT, null, "The DN of the manager account to use when binding to LDAP if anonymous binding is disabled.", false),
    BIND_PASSWORD(org.apache.ambari.server.configuration.AmbariServerConfigurationCategory.LDAP_CONFIGURATION, "ambari.ldap.connectivity.bind_password", org.apache.ambari.server.configuration.ConfigurationPropertyType.PASSWORD, null, "The password for the manager account used to bind to LDAP if anonymous binding is disabled.", false),
    ATTR_DETECTION(org.apache.ambari.server.configuration.AmbariServerConfigurationCategory.LDAP_CONFIGURATION, "ambari.ldap.attributes.detection", org.apache.ambari.server.configuration.ConfigurationPropertyType.PLAINTEXT, "", "", false),
    DN_ATTRIBUTE(org.apache.ambari.server.configuration.AmbariServerConfigurationCategory.LDAP_CONFIGURATION, "ambari.ldap.attributes.dn_attr", org.apache.ambari.server.configuration.ConfigurationPropertyType.PLAINTEXT, "dn", "The attribute used for determining what the distinguished name property is.", false),
    USER_OBJECT_CLASS(org.apache.ambari.server.configuration.AmbariServerConfigurationCategory.LDAP_CONFIGURATION, "ambari.ldap.attributes.user.object_class", org.apache.ambari.server.configuration.ConfigurationPropertyType.PLAINTEXT, "person", "The class to which user objects in LDAP belong.", false),
    USER_NAME_ATTRIBUTE(org.apache.ambari.server.configuration.AmbariServerConfigurationCategory.LDAP_CONFIGURATION, "ambari.ldap.attributes.user.name_attr", org.apache.ambari.server.configuration.ConfigurationPropertyType.PLAINTEXT, "uid", "The attribute used for determining the user name, such as 'uid'.", false),
    USER_GROUP_MEMBER_ATTRIBUTE(org.apache.ambari.server.configuration.AmbariServerConfigurationCategory.LDAP_CONFIGURATION, "ambari.ldap.attributes.user.group_member_attr", org.apache.ambari.server.configuration.ConfigurationPropertyType.PLAINTEXT, "memberof", "The LDAP attribute which identifies user group membership.", false),
    USER_SEARCH_BASE(org.apache.ambari.server.configuration.AmbariServerConfigurationCategory.LDAP_CONFIGURATION, "ambari.ldap.attributes.user.search_base", org.apache.ambari.server.configuration.ConfigurationPropertyType.PLAINTEXT, "dc=ambari,dc=apache,dc=org", "The base DN to use when filtering LDAP users and groups. This is only used when LDAP authentication is enabled.", false),
    USER_BASE(org.apache.ambari.server.configuration.AmbariServerConfigurationCategory.LDAP_CONFIGURATION, "ambari.ldap.attributes.search_user_base", org.apache.ambari.server.configuration.ConfigurationPropertyType.PLAINTEXT, "ou=people,dc=ambari,dc=apache,dc=org", "The filter used when searching for users in LDAP.", false),
    GROUP_OBJECT_CLASS(org.apache.ambari.server.configuration.AmbariServerConfigurationCategory.LDAP_CONFIGURATION, "ambari.ldap.attributes.group.object_class", org.apache.ambari.server.configuration.ConfigurationPropertyType.PLAINTEXT, "posixGroup", "Specifies the LDAP object class value that defines groups in the directory service.", false),
    GROUP_NAME_ATTRIBUTE(org.apache.ambari.server.configuration.AmbariServerConfigurationCategory.LDAP_CONFIGURATION, "ambari.ldap.attributes.group.name_attr", org.apache.ambari.server.configuration.ConfigurationPropertyType.PLAINTEXT, "cn", "The attribute used to determine the group name in LDAP.", false),
    GROUP_MEMBER_ATTRIBUTE(org.apache.ambari.server.configuration.AmbariServerConfigurationCategory.LDAP_CONFIGURATION, "ambari.ldap.attributes.group.member_attr", org.apache.ambari.server.configuration.ConfigurationPropertyType.PLAINTEXT, "member", "The LDAP attribute which identifies group membership.", false),
    GROUP_SEARCH_BASE(org.apache.ambari.server.configuration.AmbariServerConfigurationCategory.LDAP_CONFIGURATION, "ambari.ldap.attributes.group.search_base", org.apache.ambari.server.configuration.ConfigurationPropertyType.PLAINTEXT, "dc=ambari,dc=apache,dc=org", "The base DN to use when filtering LDAP users and groups. This is only used when LDAP authentication is enabled.", false),
    GROUP_BASE(org.apache.ambari.server.configuration.AmbariServerConfigurationCategory.LDAP_CONFIGURATION, "ambari.ldap.attributes.group.search_group_base", org.apache.ambari.server.configuration.ConfigurationPropertyType.PLAINTEXT, "ou=groups,dc=ambari,dc=apache,dc=org", "The filter used when searching for groups in LDAP.", false),
    USER_SEARCH_FILTER(org.apache.ambari.server.configuration.AmbariServerConfigurationCategory.LDAP_CONFIGURATION, "ambari.ldap.advanced.user_search_filter", org.apache.ambari.server.configuration.ConfigurationPropertyType.PLAINTEXT, "(&({usernameAttribute}={0})(objectClass={userObjectClass}))", "A filter used to lookup a user in LDAP based on the Ambari user name.", false),
    USER_MEMBER_REPLACE_PATTERN(org.apache.ambari.server.configuration.AmbariServerConfigurationCategory.LDAP_CONFIGURATION, "ambari.ldap.advanced.user_member_replace_pattern", org.apache.ambari.server.configuration.ConfigurationPropertyType.PLAINTEXT, "", "Regex pattern to use when replacing the user member attribute ID value with a placeholder. This is used in cases where a UID of an LDAP member is not a full CN or unique ID (e.g.: 'member: <SID=123>;<GID=123>;cn=myCn,dc=org,dc=apache')", false),
    USER_MEMBER_FILTER(org.apache.ambari.server.configuration.AmbariServerConfigurationCategory.LDAP_CONFIGURATION, "ambari.ldap.advanced.user_member_filter", org.apache.ambari.server.configuration.ConfigurationPropertyType.PLAINTEXT, "", "Filter to use for syncing user members of a group from LDAP (by default it is not used). For example: (&(objectclass=posixaccount)(uid={member}))", false),
    ALTERNATE_USER_SEARCH_ENABLED(org.apache.ambari.server.configuration.AmbariServerConfigurationCategory.LDAP_CONFIGURATION, "ambari.ldap.advanced.alternate_user_search_enabled", org.apache.ambari.server.configuration.ConfigurationPropertyType.PLAINTEXT, "false", "Determines whether a secondary (alternate) LDAP user search filer is used if the primary filter fails to find a user.", false),
    ALTERNATE_USER_SEARCH_FILTER(org.apache.ambari.server.configuration.AmbariServerConfigurationCategory.LDAP_CONFIGURATION, "ambari.ldap.advanced.alternate_user_search_filter", org.apache.ambari.server.configuration.ConfigurationPropertyType.PLAINTEXT, "(&(userPrincipalName={0})(objectClass={userObjectClass}))", "An alternate LDAP user search filter which can be used if 'authentication.ldap.alternateUserSearchEnabled' is enabled and the primary filter fails to find a user.", false),
    GROUP_SEARCH_FILTER(org.apache.ambari.server.configuration.AmbariServerConfigurationCategory.LDAP_CONFIGURATION, "ambari.ldap.advanced.group_search_filter", org.apache.ambari.server.configuration.ConfigurationPropertyType.PLAINTEXT, "", "The DN to use when searching for LDAP groups.", false),
    GROUP_MEMBER_REPLACE_PATTERN(org.apache.ambari.server.configuration.AmbariServerConfigurationCategory.LDAP_CONFIGURATION, "ambari.ldap.advanced.group_member_replace_pattern", org.apache.ambari.server.configuration.ConfigurationPropertyType.PLAINTEXT, "", "Regex pattern to use when replacing the group member attribute ID value with a placeholder. This is used in cases where a UID of an LDAP member is not a full CN or unique ID (e.g.: 'member: <SID=123>;<GID=123>;cn=myCn,dc=org,dc=apache')", false),
    GROUP_MEMBER_FILTER(org.apache.ambari.server.configuration.AmbariServerConfigurationCategory.LDAP_CONFIGURATION, "ambari.ldap.advanced.group_member_filter", org.apache.ambari.server.configuration.ConfigurationPropertyType.PLAINTEXT, "", "Filter to use for syncing group members of a group from LDAP. (by default it is not used). For example: (&(objectclass=posixgroup)(cn={member}))", false),
    GROUP_MAPPING_RULES(org.apache.ambari.server.configuration.AmbariServerConfigurationCategory.LDAP_CONFIGURATION, "ambari.ldap.advanced.group_mapping_rules", org.apache.ambari.server.configuration.ConfigurationPropertyType.PLAINTEXT, "Ambari Administrators", "A comma-separate list of groups which would give a user administrative access to Ambari when syncing from LDAP. This is only used when 'authorization.ldap.groupSearchFilter' is blank. For instance: Hadoop Admins, Hadoop Admins.*, DC Admins, .*Hadoop Operators", false),
    FORCE_LOWERCASE_USERNAMES(org.apache.ambari.server.configuration.AmbariServerConfigurationCategory.LDAP_CONFIGURATION, "ambari.ldap.advanced.force_lowercase_usernames", org.apache.ambari.server.configuration.ConfigurationPropertyType.PLAINTEXT, "", "Declares whether to force the ldap user name to be lowercase or leave as-is.\nThis is useful when local user names are expected to be lowercase but the LDAP user names are not.", false),
    REFERRAL_HANDLING(org.apache.ambari.server.configuration.AmbariServerConfigurationCategory.LDAP_CONFIGURATION, "ambari.ldap.advanced.referrals", org.apache.ambari.server.configuration.ConfigurationPropertyType.PLAINTEXT, "follow", "Determines whether to follow LDAP referrals to other URLs when the LDAP controller doesn't have the requested object.", false),
    PAGINATION_ENABLED(org.apache.ambari.server.configuration.AmbariServerConfigurationCategory.LDAP_CONFIGURATION, "ambari.ldap.advanced.pagination_enabled", org.apache.ambari.server.configuration.ConfigurationPropertyType.PLAINTEXT, "true", "Determines whether results from LDAP are paginated when requested.", false),
    COLLISION_BEHAVIOR(org.apache.ambari.server.configuration.AmbariServerConfigurationCategory.LDAP_CONFIGURATION, "ambari.ldap.advanced.collision_behavior", org.apache.ambari.server.configuration.ConfigurationPropertyType.PLAINTEXT, "convert", "Determines how to handle username collision while updating from LDAP.", false),
    DISABLE_ENDPOINT_IDENTIFICATION(org.apache.ambari.server.configuration.AmbariServerConfigurationCategory.LDAP_CONFIGURATION, "ambari.ldap.advanced.disable_endpoint_identification", org.apache.ambari.server.configuration.ConfigurationPropertyType.PLAINTEXT, "false", "Determines whether to disable endpoint identification (hostname verification) during SSL handshake while updating from LDAP.", false),
    SSO_MANAGE_SERVICES(org.apache.ambari.server.configuration.AmbariServerConfigurationCategory.SSO_CONFIGURATION, "ambari.sso.manage_services", org.apache.ambari.server.configuration.ConfigurationPropertyType.PLAINTEXT, "false", "A Boolean value indicating whether Ambari is to manage the SSO configuration for services or not.", false),
    SSO_ENABLED_SERVICES(org.apache.ambari.server.configuration.AmbariServerConfigurationCategory.SSO_CONFIGURATION, "ambari.sso.enabled_services", org.apache.ambari.server.configuration.ConfigurationPropertyType.PLAINTEXT, null, "A comma-delimited list of services that are expected to be configured for SSO.  A \"*\" indicates all services.", false),
    SSO_PROVIDER_URL(org.apache.ambari.server.configuration.AmbariServerConfigurationCategory.SSO_CONFIGURATION, "ambari.sso.provider.url", org.apache.ambari.server.configuration.ConfigurationPropertyType.PLAINTEXT, null, "The URL for SSO provider to use in the absence of a JWT token when handling a JWT request.", false),
    SSO_PROVIDER_CERTIFICATE(org.apache.ambari.server.configuration.AmbariServerConfigurationCategory.SSO_CONFIGURATION, "ambari.sso.provider.certificate", org.apache.ambari.server.configuration.ConfigurationPropertyType.PLAINTEXT, null, "The x509 certificate containing the public key to use when verifying the authenticity of a JWT token from the SSO provider.", false),
    SSO_PROVIDER_ORIGINAL_URL_PARAM_NAME(org.apache.ambari.server.configuration.AmbariServerConfigurationCategory.SSO_CONFIGURATION, "ambari.sso.provider.originalUrlParamName", org.apache.ambari.server.configuration.ConfigurationPropertyType.PLAINTEXT, "originalUrl", "The original URL to use when constructing the URL for SSO provider.", false),
    SSO_JWT_AUDIENCES(org.apache.ambari.server.configuration.AmbariServerConfigurationCategory.SSO_CONFIGURATION, "ambari.sso.jwt.audiences", org.apache.ambari.server.configuration.ConfigurationPropertyType.PLAINTEXT, null, "A list of the JWT audiences expected. Leaving this blank will allow for any audience.", false),
    SSO_JWT_COOKIE_NAME(org.apache.ambari.server.configuration.AmbariServerConfigurationCategory.SSO_CONFIGURATION, "ambari.sso.jwt.cookieName", org.apache.ambari.server.configuration.ConfigurationPropertyType.PLAINTEXT, "hadoop-jwt", "The name of the cookie which will be used to extract the JWT token from the request.", false),
    SSO_AUTHENTICATION_ENABLED(org.apache.ambari.server.configuration.AmbariServerConfigurationCategory.SSO_CONFIGURATION, "ambari.sso.authentication.enabled", org.apache.ambari.server.configuration.ConfigurationPropertyType.PLAINTEXT, "false", "Determines whether to use JWT authentication when logging into Ambari.", false),
    TPROXY_AUTHENTICATION_ENABLED(org.apache.ambari.server.configuration.AmbariServerConfigurationCategory.TPROXY_CONFIGURATION, "ambari.tproxy.authentication.enabled", org.apache.ambari.server.configuration.ConfigurationPropertyType.PLAINTEXT, "false", "Determines whether to allow a proxy user to specifiy a proxied user when logging into Ambari.", false),
    TPROXY_ALLOWED_HOSTS(org.apache.ambari.server.configuration.AmbariServerConfigurationCategory.TPROXY_CONFIGURATION, "ambari\\.tproxy\\.proxyuser\\..+\\.hosts", org.apache.ambari.server.configuration.ConfigurationPropertyType.PLAINTEXT, "", "List of hosts from which trusted-proxy user can connect.", true),
    TPROXY_ALLOWED_USERS(org.apache.ambari.server.configuration.AmbariServerConfigurationCategory.TPROXY_CONFIGURATION, "ambari\\.tproxy\\.proxyuser\\..+\\.users", org.apache.ambari.server.configuration.ConfigurationPropertyType.PLAINTEXT, "", "List of users which the trusted-proxy user can proxy for.", true),
    TPROXY_ALLOWED_GROUPS(org.apache.ambari.server.configuration.AmbariServerConfigurationCategory.TPROXY_CONFIGURATION, "ambari\\.tproxy\\.proxyuser\\..+\\.groups", org.apache.ambari.server.configuration.ConfigurationPropertyType.PLAINTEXT, "", "List of groups which the trusted-proxy user can proxy user for.", true);

    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(org.apache.ambari.server.configuration.AmbariServerConfigurationKey.class);

    private final org.apache.ambari.server.configuration.AmbariServerConfigurationCategory configurationCategory;

    private final java.lang.String propertyName;

    private final org.apache.ambari.server.configuration.ConfigurationPropertyType configurationPropertyType;

    private final java.lang.String defaultValue;

    private final java.lang.String description;

    private final boolean regex;

    AmbariServerConfigurationKey(org.apache.ambari.server.configuration.AmbariServerConfigurationCategory configurationCategory, java.lang.String propName, org.apache.ambari.server.configuration.ConfigurationPropertyType configurationPropertyType, java.lang.String defaultValue, java.lang.String description, boolean regex) {
        this.configurationCategory = configurationCategory;
        this.propertyName = propName;
        this.configurationPropertyType = configurationPropertyType;
        this.defaultValue = defaultValue;
        this.description = description;
        this.regex = regex;
    }

    public org.apache.ambari.server.configuration.AmbariServerConfigurationCategory getConfigurationCategory() {
        return configurationCategory;
    }

    public java.lang.String key() {
        return this.propertyName;
    }

    public org.apache.ambari.server.configuration.ConfigurationPropertyType getConfigurationPropertyType() {
        return configurationPropertyType;
    }

    public java.lang.String getDefaultValue() {
        return defaultValue;
    }

    public java.lang.String getDescription() {
        return description;
    }

    public boolean isRegex() {
        return regex;
    }

    public static org.apache.ambari.server.configuration.AmbariServerConfigurationKey translate(org.apache.ambari.server.configuration.AmbariServerConfigurationCategory category, java.lang.String keyName) {
        if ((category != null) && org.apache.commons.lang.StringUtils.isNotEmpty(keyName)) {
            for (org.apache.ambari.server.configuration.AmbariServerConfigurationKey key : org.apache.ambari.server.configuration.AmbariServerConfigurationKey.values()) {
                if (key.configurationCategory.equals(category)) {
                    if ((key.regex && keyName.matches(key.propertyName)) || key.propertyName.equals(keyName)) {
                        return key;
                    }
                }
            }
        }
        java.lang.String categoryName = (category == null) ? "null" : category.getCategoryName();
        org.apache.ambari.server.configuration.AmbariServerConfigurationKey.LOG.warn("Invalid Ambari server configuration key: {}:{}", categoryName, keyName);
        return null;
    }

    public static java.util.Set<java.lang.String> findPasswordConfigurations() {
        return java.util.stream.Stream.of(org.apache.ambari.server.configuration.AmbariServerConfigurationKey.values()).filter(k -> org.apache.ambari.server.configuration.ConfigurationPropertyType.PASSWORD == k.getConfigurationPropertyType()).map(f -> f.propertyName).collect(java.util.stream.Collectors.toSet());
    }
}
