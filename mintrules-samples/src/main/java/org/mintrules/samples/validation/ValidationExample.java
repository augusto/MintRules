package org.mintrules.samples.validation;

import org.junit.Before;
import org.junit.Test;
import org.mintrules.api.Session;
import org.mintrules.core.AnnotatedRulesEngine;

public class ValidationExample {

    private AnnotatedRulesEngine<Void> rulesEngine;

    @Before
    public void setup() {
        rulesEngine = new AnnotatedRulesEngine<Void>();

        rulesEngine.registerRule(new ValidateNameRule());
        rulesEngine.registerRule(new ValidateEmailRule());
    }

    @Test
    public void testValidEmail() {
        Session session = rulesEngine.createSession();
        session.put(new Person("Jack", "jack.bauer@cia.com"));

        rulesEngine.fireRules(session);
    }

    @Test(expected = RuntimeException.class)
    public void testInvalidEmail() {
        Session session = rulesEngine.createSession();
        session.put(new Person("Jack", "jack.bauer@cia.com."));

        rulesEngine.fireRules(session);
    }
}
