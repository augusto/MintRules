package org.mintrules.samples.decision;

import org.junit.Before;
import org.junit.Test;
import org.mintrules.api.Session;
import org.mintrules.core.AnnotatedRulesEngine;

import static org.assertj.core.api.Assertions.assertThat;

public class DecisionExample {

    AnnotatedRulesEngine<Double> rulesEngine;

    @Before
    public void setup() {
        rulesEngine = new AnnotatedRulesEngine<Double>();

        rulesEngine.registerRule(new ChargeRule(0, 6, 1.5));
        rulesEngine.registerRule(new ChargeRule(6, 8, 1.2));
        rulesEngine.registerRule(new ChargeRule(8, 18, 1));
        rulesEngine.registerRule(new ChargeRule(18, 24, 1.2));
    }

    @Test
    public void calculateMorningFare() {
        Session session = rulesEngine.createSession();
        session.put("hourStarted", 8);
        session.put("length", 18.2);

        assertThat(rulesEngine.fireRules(session)).isEqualTo(18.2);
    }

    @Test
    public void calculateEveningFare() {
        Session session = rulesEngine.createSession();
        session.put("hourStarted", 20);
        session.put("length", 10.0);

        assertThat(rulesEngine.fireRules(session)).isEqualTo(12);
    }
}
