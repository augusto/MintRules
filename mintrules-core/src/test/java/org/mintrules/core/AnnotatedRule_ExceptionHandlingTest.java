package org.mintrules.core;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mintrules.annotation.Action;
import org.mintrules.annotation.Condition;
import org.mintrules.annotation.Rule;
import org.mintrules.api.RuleException;

import static org.assertj.core.api.Assertions.assertThat;

public class AnnotatedRule_ExceptionHandlingTest {

    @Test
    public void shouldHandleExceptionOnCondition() throws Exception {
        Exception expectedException = new ArithmeticException("Error");
        AnnotatedRule<Void> rule = new AnnotatedRule<Void>(new ThrowsExceptionOnCondition(expectedException));
        DefaultSession session = new DefaultSession();

        try {
            rule.evaluateCondition(session);
            Assertions.fail("Rule.evaluateCondition should have thrown an exception");
        } catch (Exception re) {
            assertThat(re).isInstanceOf(RuleException.class);
            assertThat(re.getCause()).isEqualTo(expectedException);
            assertThat(re.getMessage()).isEqualTo("Exception thrown by condition method: boolean org.mintrules.core.AnnotatedRule_ExceptionHandlingTest$ThrowsExceptionOnCondition.condition() throws java.lang.Exception from rule org.mintrules.core.AnnotatedRule_ExceptionHandlingTest.ThrowsExceptionOnCondition");
        }

    }

    @Test
    public void shouldHandleExceptionOnAction() throws Exception {
        Exception expectedException = new ArithmeticException("Error");
        AnnotatedRule<Void> rule = new AnnotatedRule<Void>(new ThrowsExceptionOnAction(expectedException));
        DefaultSession session = new DefaultSession();

        try {
            rule.executeAction(session);
            Assertions.fail("Rule.executeAction should have thrown an exception");
        } catch (Exception re) {
            assertThat(re).isInstanceOf(RuleException.class);
            assertThat(re.getCause()).isEqualTo(expectedException);
            assertThat(re.getMessage()).isEqualTo("Exception thrown by action method: boolean org.mintrules.core.AnnotatedRule_ExceptionHandlingTest$ThrowsExceptionOnAction.condition() throws java.lang.Exception from rule org.mintrules.core.AnnotatedRule_ExceptionHandlingTest.ThrowsExceptionOnAction");
        }

    }

    @Rule
    static class ThrowsExceptionOnCondition {
        private final Exception exception;

        public ThrowsExceptionOnCondition(Exception exception) {
            this.exception = exception;
        }

        @Condition
        boolean condition() throws Exception {
            throw exception;
        }

        @Action
        void action() {

        }
    }

    @Rule
    static class ThrowsExceptionOnAction {
        private final Exception exception;

        public ThrowsExceptionOnAction(Exception exception) {
            this.exception = exception;
        }

        @Condition
        boolean condition() throws Exception {
            return true;
        }

        @Action
        void action() throws Exception {
            throw exception;
        }
    }
}
