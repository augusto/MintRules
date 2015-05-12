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

    @Test
    public void ruleObject_ShouldHaveNoMoreThanOneConditionAnnotation() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("More than one method found with the annotation @Condition ");
        expectedException.expectMessage("RuleWithTwoConditionAnnotations.condition1()");
        expectedException.expectMessage("RuleWithTwoConditionAnnotations.condition2()");

        new AnnotatedRule<Void>(new RuleWithTwoConditionAnnotations());
    }

    @Test
    public void ruleObject_ShouldHaveNoMoreThanOneActionAnnotation() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("More than one method found with the annotation @Action ");
        expectedException.expectMessage("RuleWithTwoActionAnnotations.action1()");
        expectedException.expectMessage("RuleWithTwoActionAnnotations.action2()");

        new AnnotatedRule<Void>(new RuleWithTwoActionAnnotations());
    }

    @Test
    public void ruleObject_ShouldConditionMethodMustReturnBoolean() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Condition method int org.mintrules.core.AnnotatedRule_RuleValidationTest$RuleWithConditionThatReturnsNonBooleanValue.condition() must return a boolean (primitive) value");

        new AnnotatedRule<Void>(new RuleWithConditionThatReturnsNonBooleanValue());
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

    @Rule
    static class RuleWithTwoConditionAnnotations {
        @Condition
        boolean condition1() {
            return false;
        }

        @Condition
        boolean condition2() {
            return false;
        }

        @Action
        void action() {
        }
    }

    @Rule
    static class RuleWithTwoActionAnnotations {
        @Condition
        boolean condition() {
            return false;
        }

        @Action
        void action1() {

        }

        @Action
        void action2() {
        }
    }

    @Rule
    static class RuleWithConditionThatReturnsNonBooleanValue {
        @Condition
        int condition() {
            return 1;
        }

        @Action
        void action() {
        }
    }

}
