package org.mintrules.samples.validation;

import org.junit.Test;
import org.mintrules.api.Session;
import org.mintrules.core.AnnotatedRulesEngine;

public class ValidationExample {

    @Test
    public void testValidEmail() {
        AnnotatedRulesEngine<Void> rulesEngine = new AnnotatedRulesEngine<Void>();

        rulesEngine.registerRule(new ValidateNameRule());
        rulesEngine.registerRule(new ValidateEmailRule());

        Session session = rulesEngine.createSession();
        session.put(new Person("Jack", "jack.bauer@cia.com"));

        rulesEngine.fireRules(session);
    }

    @Test(expected = RuntimeException.class)
    public void testInvalidEmail() {
        AnnotatedRulesEngine<Void> rulesEngine = new AnnotatedRulesEngine<Void>();

        rulesEngine.registerRule(new ValidateNameRule());
        rulesEngine.registerRule(new ValidateEmailRule());

        Session session = rulesEngine.createSession();
        session.put(new Person("Jack", "jack.bauer@cia.om."));

        rulesEngine.fireRules(session);
    }
}
