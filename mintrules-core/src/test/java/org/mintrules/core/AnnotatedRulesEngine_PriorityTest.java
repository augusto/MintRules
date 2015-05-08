package org.mintrules.core;

import org.junit.Test;
import org.mintrules.api.Session;
import org.mintrules.testrules.BasicPrioritisedWellFormedRule;
import org.mintrules.testrules.BasicWellFormedRule;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by augusto on 08/05/2015.
 */
public class AnnotatedRulesEngine_PriorityTest {

    @Test
    public void shouldRunRulesInPriorityOrder() throws Exception {

        AnnotatedRulesEngine<String> annotatedRulesEngine = new AnnotatedRulesEngine<String>();
        annotatedRulesEngine.registerRule(new BasicPrioritisedWellFormedRule(true, "first", 5));
        annotatedRulesEngine.registerRule(new BasicPrioritisedWellFormedRule(true, "second", 1));

        assertThat(annotatedRulesEngine.fireRules(annotatedRulesEngine.createSession())).isEqualTo("second");
    }


    @Test
    public void shouldReturnOrderedListOfRules() {
        AnnotatedRulesEngine<String> annotatedRulesEngine = new AnnotatedRulesEngine<String>();
        BasicWellFormedRule rule1 = new BasicPrioritisedWellFormedRule(false, "rule1", 5);
        BasicWellFormedRule rule2 = new BasicPrioritisedWellFormedRule(true, "rule2", 1);

        annotatedRulesEngine.registerRule(rule1);
        annotatedRulesEngine.registerRule(rule2);
        Session session = annotatedRulesEngine.createSession();

        assertThat(annotatedRulesEngine.getSortedRules()).hasSize(2);
        assertThat(annotatedRulesEngine.getSortedRules().get(0).performAction(session)).isEqualTo("rule2");
        assertThat(annotatedRulesEngine.getSortedRules().get(1).performAction(session)).isEqualTo("rule1");
    }
}

