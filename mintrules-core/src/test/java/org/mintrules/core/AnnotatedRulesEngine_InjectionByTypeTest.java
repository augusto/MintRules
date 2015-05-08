package org.mintrules.core;

import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mintrules.annotation.Action;
import org.mintrules.annotation.Condition;
import org.mintrules.annotation.Rule;
import org.mintrules.api.Session;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class AnnotatedRulesEngine_InjectionByTypeTest {

    @org.junit.Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldInjectOneValueIntoConditionMethod() throws Exception {
        AnnotatedRulesEngine<String> annotatedRulesEngine = new AnnotatedRulesEngine<String>();
        annotatedRulesEngine.registerRule(new ConditionInjectByType());

        Session session = annotatedRulesEngine.createSession();
        session.put(BigDecimal.ZERO);

        assertThat(annotatedRulesEngine.fireRules(session)).isEqualTo("match");
    }

    @Test
    public void shouldInjectAllValuesIntoConditionMethod() throws Exception {
        AnnotatedRulesEngine<String> annotatedRulesEngine = new AnnotatedRulesEngine<String>();
        annotatedRulesEngine.registerRule(new ConditionInjectByType_SeveralTypes());

        Session session = annotatedRulesEngine.createSession();
        session.put(new BigDecimal(100));
        session.put("100");

        assertThat(annotatedRulesEngine.fireRules(session)).isEqualTo("match");
    }

    @Test
    public void shouldFailIfNoInstanceIsFoundInSession() throws Exception {
        AnnotatedRulesEngine<String> annotatedRulesEngine = new AnnotatedRulesEngine<String>();
        annotatedRulesEngine.registerRule(new ConditionInjectByType());

        Session session = annotatedRulesEngine.createSession();

        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Couldn't find an instance of java.math.BigDecimal");

        annotatedRulesEngine.fireRules(session);
    }

    @Test
    public void shouldFailIfMoreThanOneInstanceIsFoundInSession() throws Exception {
        AnnotatedRulesEngine<String> annotatedRulesEngine = new AnnotatedRulesEngine<String>();
        annotatedRulesEngine.registerRule(new ConditionInjectByType());

        Session session = annotatedRulesEngine.createSession();
        session.put("one", BigDecimal.ZERO);
        session.put("two", BigDecimal.ZERO);

        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("More than one instance of java.math.BigDecimal was found ['one', 'two']");

        annotatedRulesEngine.fireRules(session);
    }
}

@Rule
class ConditionInjectByType {
    @Condition
    public boolean when(BigDecimal value) {
        return value.equals(BigDecimal.ZERO);
    }

    @Action
    public String action() {
        return "match";
    }
}

@Rule
class ConditionInjectByType_SeveralTypes {
    @Condition
    public boolean when(BigDecimal value, String numberAsString) {
        return value.equals(new BigDecimal(numberAsString));
    }

    @Action
    public String action() {
        return "match";
    }
}