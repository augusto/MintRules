package org.mintrules.api;

/**
 * Rules engine interface.
 *
 * @param <R> The return type of the rule engine
 */
public interface RulesEngine<R> {

    /**
     * Fire all registered rules.
     *
     * @return value
     * @throws org.mintrules.api.RuleException if any of the Rule objects throws an exception.
     */
    R fireRules(Session session) throws RuleException;

    /**
     * Creates a new session bound to the rules engine.
     *
     * @return
     */
    Session createSession();
}
