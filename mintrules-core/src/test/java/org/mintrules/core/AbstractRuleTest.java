package org.mintrules.core;

import org.junit.Test;
import org.mintrules.api.Session;

import static org.assertj.core.api.Assertions.assertThat;

public class AbstractRuleTest {

    @Test
    public void shouldCompareBasedOnPriority() throws Exception {

        PrioritisedRule priorityOne = new PrioritisedRule(1);
        PrioritisedRule priorityFive = new PrioritisedRule(5);

        assertThat(priorityOne.compareTo(priorityFive)).isLessThan(0);
    }

    class PrioritisedRule extends AbstractRule<String> {

        public PrioritisedRule(int priority) {
            super("", priority, "");
        }

        @Override
        public String getName() {
            return null;
        }

        @Override
        public String getDescription() {
            return null;
        }

        @Override
        public boolean evaluateCondition(Session session) {
            return false;
        }

        @Override
        public String performAction(Session session) {
            return null;
        }
    }
}