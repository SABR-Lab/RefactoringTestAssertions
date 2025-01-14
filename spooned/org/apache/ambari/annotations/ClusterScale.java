package org.apache.ambari.annotations;
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@java.lang.annotation.Target({ java.lang.annotation.ElementType.TYPE, java.lang.annotation.ElementType.FIELD, java.lang.annotation.ElementType.METHOD })
public @interface ClusterScale {
    org.apache.ambari.server.configuration.Configuration.ClusterSizeType clusterSize();

    java.lang.String value();
}
