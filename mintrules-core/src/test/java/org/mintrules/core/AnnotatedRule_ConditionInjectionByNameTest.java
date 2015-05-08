package org.mintrules.core;

import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mintrules.annotation.Action;
import org.mintrules.annotation.Condition;
import org.mintrules.annotation.Rule;
import org.mintrules.annotation.Value;
import org.mintrules.api.Session;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class AnnotatedRule_ConditionInjectionByNameTest {
    @org.junit.Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldInjectAllValuesIntoConditionMethod() throws Exception {
        AnnotatedRule<String> annotatedRule = new AnnotatedRule<String>(new ConditionInjectByType());

        Session session = new DefaultSession();
        session.put("one", "one");
        session.put("two", "two");

        assertThat(annotatedRule.evaluateCondition(session)).isEqualTo(true);
    }

    @Test
    public void shouldFailIfNoInstanceIsFoundInSession() throws Exception {
        AnnotatedRule<String> annotatedRule = new AnnotatedRule<String>(new ConditionInjectByType());

        Session session = new DefaultSession();
        session.put("two", "two");

        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Couldn't find a parameter with the value 'one'");

        annotatedRule.evaluateCondition(session);
    }

    @Test
    public void shouldFailIfTypeDoesNotMatchWithExpectedType() throws Exception {
        AnnotatedRule<String> annotatedRule = new AnnotatedRule<String>(new ConditionInjectByType());

        Session session = new DefaultSession();
        session.put("one", BigDecimal.ZERO);
        session.put("two", "two");


        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Incompatible types. The parameter with key 'one' requires an instance of java.lang.String, but got an instance of java.math.BigDecimal");

        annotatedRule.evaluateCondition(session);
    }

    @Rule
    class ConditionInjectByType {
        @Condition
        public boolean when(@Value("one") String one, @Value("two") String two) {
            return !one.equals(two);
        }

        @Action
        public String action() {
            return "match";
        }
    }
}


