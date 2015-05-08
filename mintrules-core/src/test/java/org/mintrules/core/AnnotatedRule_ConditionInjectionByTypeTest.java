package org.mintrules.core;

import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mintrules.annotation.Action;
import org.mintrules.annotation.Condition;
import org.mintrules.annotation.Rule;
import org.mintrules.api.Session;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class AnnotatedRule_ConditionInjectionByTypeTest {

    @org.junit.Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldInjectAllValuesIntoConditionMethod() throws Exception {
        AnnotatedRule<String> annotatedRule = new AnnotatedRule<String>(new ConditionInjectByType());

        Session session = new DefaultSession();
        session.put(new BigDecimal(100));
        session.put("100");

        assertThat(annotatedRule.evaluateCondition(session)).isEqualTo(true);
    }

    @Test
    public void shouldFailIfNoInstanceIsFoundInSession() throws Exception {
        AnnotatedRule<String> annotatedRule = new AnnotatedRule<String>(new ConditionInjectByType());

        Session session = new DefaultSession();
        session.put("100");

        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Couldn't find an instance of java.math.BigDecimal");

        annotatedRule.evaluateCondition(session);
    }

    @Test
    public void shouldFailIfMoreThanOneInstanceIsFoundInSession() throws Exception {
        AnnotatedRule<String> annotatedRule = new AnnotatedRule<String>(new ConditionInjectByType());

        Session session = new DefaultSession();
        session.put("one", BigDecimal.ZERO);
        session.put("two", BigDecimal.ZERO);
        session.put("100");

        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("More than one instance of java.math.BigDecimal was found ['one', 'two']");

        annotatedRule.evaluateCondition(session);
    }
}

@Rule
class ConditionInjectByType {
    @Condition
    public boolean when(BigDecimal value, String numberAsString) {
        return value.equals(new BigDecimal(numberAsString));
    }

    @Action
    public String action() {
        return "match";
    }
}
