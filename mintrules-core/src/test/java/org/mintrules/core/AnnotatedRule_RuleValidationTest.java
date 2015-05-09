package org.mintrules.core;

import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mintrules.annotation.Action;
import org.mintrules.annotation.Condition;
import org.mintrules.annotation.Rule;

public class AnnotatedRule_RuleValidationTest {

    @org.junit.Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void ruleObject_ShouldHaveRuleAnnotation() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(
                "Rule class org.mintrules.core.AnnotatedRule_RuleValidationTest.RuleWithNoRuleAnnotation " +
                        "must be annotated with @Rule");

        new AnnotatedRule<Void>(new RuleWithNoRuleAnnotation());
    }

    @Test
    public void ruleObject_ShouldHaveConditionAnnotation() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(
                "Rule class org.mintrules.core.AnnotatedRule_RuleValidationTest.RuleWithNoConditionMethod " +
                        "must have one method annotated with @Condition");

        new AnnotatedRule<Void>(new RuleWithNoConditionMethod());
    }

    @Test
    public void ruleObject_ShouldHaveActionAnnotation() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(
                "Rule class org.mintrules.core.AnnotatedRule_RuleValidationTest.RuleWithNoActionMethod " +
                        "must have one method annotated with @Action");

        new AnnotatedRule<Void>(new RuleWithNoActionMethod());
    }

    static class RuleWithNoRuleAnnotation {
        @Condition
        boolean condition() {
            return false;
        }

        @Action
        void action() {
        }
    }

    @Rule
    static class RuleWithNoConditionMethod {
        @Action
        void action() {
        }
    }

    @Rule
    static class RuleWithNoActionMethod {
        @Condition
        boolean condition() {
            return false;
        }
    }
}
