package org.mintrules.core;

import org.mintrules.api.Rule;
import org.mintrules.api.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Annotated rules engine, configures the rules engine by reading annotations in the classes.
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
        getSortedRules();
        for (Rule<R> rule : rules) {
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

    public List<Rule<R>> getSortedRules() {
        rules.sort(null);
        return rules;
    }
}
