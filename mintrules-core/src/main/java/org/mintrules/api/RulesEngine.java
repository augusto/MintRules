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
     */
    R fireRules(Session session);

    /**
     * Creates a new session bound to the rules engine.
     *
     * @return
     */
    Session createSession();
}
