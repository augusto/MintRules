package org.mintrules.core;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mintrules.annotation.Action;
import org.mintrules.annotation.Condition;
import org.mintrules.annotation.Rule;
import org.mintrules.testrules.BasicWellFormedRule;

public class AnnotatedRuleTest {

    @Test
    public void ruleName_shouldAutoDetectNameFromClassName() throws Exception {
        BasicWellFormedRule rule = new BasicWellFormedRule(true, "value");

        AnnotatedRule<String> annotatedRule = new AnnotatedRule<String>(rule);

        Assertions.assertThat(annotatedRule.getName()).isEqualTo("org.mintrules.testrules.BasicWellFormedRule");
    }

    @Test
    public void ruleName_shouldUseNameFromAnnotation() throws Exception {
        NamedRule rule = new NamedRule();

        AnnotatedRule<String> annotatedRule = new AnnotatedRule<String>(rule);

        Assertions.assertThat(annotatedRule.getName()).isEqualTo("custom name");
    }

    @Test
    public void description_shouldUseDescriptionFromAnnotation() throws Exception {
        NamedRule rule = new NamedRule();

        AnnotatedRule<String> annotatedRule = new AnnotatedRule<String>(rule);

        Assertions.assertThat(annotatedRule.getDescription()).isEqualTo("custom description");
    }

    @Rule(name = "custom name", description = "custom description")
    class NamedRule {

        @Condition
        public boolean condition() {
            return false;
        }

        @Action
        public void action() {

        }

    }
}