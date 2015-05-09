package org.mintrules.core;

import org.mintrules.api.Rule;
import org.mintrules.api.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Annotated rules engine, configures the rules engine by reading annotations in the classes.
 * All registered rules must return R from their action methods.
 */
public class AnnotatedRulesEngine<R> extends AbstractRulesEngine<R> {

    private List<Rule<R>> rules = new ArrayList<Rule<R>>();

    public AnnotatedRulesEngine() {

    }

    public void registerRule(Object rule) {
        AnnotatedRule<R> annotatedRule = new AnnotatedRule<R>(rule);
        rules.add(annotatedRule);
    }

    @Override
    public R fireRules(Session session) {
        for (Rule<R> rule : getSortedRules()) {
            if (rule.evaluateCondition(session)) {
                return rule.executeAction(session);
            }
        }

        return null;
    }

    @Override
    public Session createSession() {
        return new DefaultSession();
    }

    /**
     * Returns all the registered rules. The list is sorted in the same order as the rules are executed, which makes
     * this method useful for debugging issues.
     *
     * @return List of registered rules.
     */
    public List<Rule<R>> getSortedRules() {
        rules.sort(null);
        return rules;
    }
}
