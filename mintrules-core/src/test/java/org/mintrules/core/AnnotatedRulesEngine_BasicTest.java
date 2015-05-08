package org.mintrules.core;

import org.junit.Test;
import org.mintrules.testrules.BasicWellFormedRule;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by augusto on 08/05/2015.
 */
public class AnnotatedRulesEngine_BasicTest {

    @Test
    public void shouldExecuteSingleRule() throws Exception {

        AnnotatedRulesEngine<String> annotatedRulesEngine = new AnnotatedRulesEngine<String>();
        annotatedRulesEngine.registerRule(new BasicWellFormedRule(true, "expected"));

        assertThat(annotatedRulesEngine.fireRules(annotatedRulesEngine.createSession())).isEqualTo("expected");
    }

    @Test
    public void shouldExecuteRulesInOrderOfAddition() throws Exception {

        AnnotatedRulesEngine<String> annotatedRulesEngine = new AnnotatedRulesEngine<String>();
        annotatedRulesEngine.registerRule(new BasicWellFormedRule(false, "notExpected"));
        annotatedRulesEngine.registerRule(new BasicWellFormedRule(true, "expected"));

        assertThat(annotatedRulesEngine.fireRules(annotatedRulesEngine.createSession())).isEqualTo("expected");
    }

}

