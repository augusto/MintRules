package org.mintrules.core;

import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mintrules.annotation.Action;
import org.mintrules.annotation.Condition;
import org.mintrules.annotation.Rule;
import org.mintrules.api.Session;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by augusto on 08/05/2015.
 */
public class AnnotatedRule_ActionInjectionByTypeTest {

    @org.junit.Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldInjectAllValuesIntoConditionMethod() throws Exception {
        AnnotatedRule<String> annotatedRule = new AnnotatedRule<String>(new ActionInjectByType());

        Session session = new DefaultSession();
        session.put(100);
        session.put("str");

        assertThat(annotatedRule.performAction(session)).isEqualTo("str-100");
    }

    @Test
    public void shouldFailIfNoInstanceIsFoundInSession() throws Exception {
        AnnotatedRule<String> annotatedRule = new AnnotatedRule<String>(new ActionInjectByType());

        Session session = new DefaultSession();
        session.put("100");

        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Couldn't find an instance of java.lang.Integer");

        annotatedRule.performAction(session);
    }

    @Test
    public void shouldFailIfMoreThanOneInstanceIsFoundInSession() throws Exception {
        AnnotatedRule<String> annotatedRule = new AnnotatedRule<String>(new ActionInjectByType());

        Session session = new DefaultSession();
        session.put("one", 100);
        session.put("two", 200);
        session.put("100");

        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("More than one instance of java.lang.Integer was found ['one', 'two']");

        annotatedRule.performAction(session);
    }
}

@Rule
class ActionInjectByType {
    @Condition
    public boolean when() {
        return true;
    }

    @Action
    public String action( String string, int number) {
        return string + "-" + number;
    }
}

