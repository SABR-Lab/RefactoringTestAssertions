package org.apache.ambari.annotations;
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@java.lang.annotation.Target({ java.lang.annotation.ElementType.TYPE, java.lang.annotation.ElementType.FIELD, java.lang.annotation.ElementType.METHOD })
public @interface ConfigurationMarkdown {
    org.apache.ambari.annotations.Markdown markdown();

    org.apache.ambari.server.configuration.Configuration.ConfigurationGrouping group();

    org.apache.ambari.annotations.ClusterScale[] scaleValues() default {  };
}
