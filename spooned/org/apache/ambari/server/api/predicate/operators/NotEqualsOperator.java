package org.apache.ambari.server.api.predicate.operators;
public class NotEqualsOperator extends org.apache.ambari.server.api.predicate.operators.AbstractOperator implements org.apache.ambari.server.api.predicate.operators.RelationalOperator {
    public NotEqualsOperator() {
        super(0);
    }

    @java.lang.Override
    public org.apache.ambari.server.api.predicate.operators.Operator.TYPE getType() {
        return org.apache.ambari.server.api.predicate.operators.Operator.TYPE.NOT_EQUAL;
    }

    @java.lang.Override
    public org.apache.ambari.server.controller.spi.Predicate toPredicate(java.lang.String prop, java.lang.String val) {
        return new org.apache.ambari.server.controller.predicate.NotPredicate(new org.apache.ambari.server.controller.predicate.EqualsPredicate<>(prop, val));
    }

    @java.lang.Override
    public java.lang.String getName() {
        return "NotEqualsOperator";
    }
}
