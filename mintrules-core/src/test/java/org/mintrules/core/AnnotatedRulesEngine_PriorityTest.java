package org.mintrules.core;

import org.junit.Test;
import org.mintrules.api.Rule;
import org.mintrules.core.AnnotatedRulesEngine;
import org.mintrules.testrules.BasicPrioritisedWellFormedRule;
import org.mintrules.testrules.BasicWellFormedRule;

import java.util.List;

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

        assertThat(annotatedRulesEngine.fireRules()).isEqualTo("second");
    }


    @Test
    public void shouldReturnOrderedListOfRules() {
        AnnotatedRulesEngine<String> annotatedRulesEngine = new AnnotatedRulesEngine<String>();
        BasicWellFormedRule rule1 = new BasicPrioritisedWellFormedRule(false, "rule1", 5);
        BasicWellFormedRule rule2 = new BasicPrioritisedWellFormedRule(true, "rule2", 1);

        annotatedRulesEngine.registerRule(rule1);
        annotatedRulesEngine.registerRule(rule2);

        assertThat(annotatedRulesEngine.getSortedRules()).hasSize(2);
        assertThat(annotatedRulesEngine.getSortedRules().get(0).performAction()).isEqualTo("rule2");
        assertThat(annotatedRulesEngine.getSortedRules().get(1).performAction()).isEqualTo("rule1");
    }
}

