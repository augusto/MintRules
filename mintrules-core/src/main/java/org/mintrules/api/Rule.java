package org.mintrules.api;

/**
 * Abstraction for a rule that can be fired by the rules engine.
 * <p/>
 * Rules are registered in the rules engine.
 */
public interface Rule<R> {

    /**
     * Getter for rule name.
     *
     * @return the rule name
     */
    String getName();

    /**
     * Getter for rule description.
     *
     * @return rule description
     */
    String getDescription();

    /**
     * Getter for rule priority.
     *
     * @return rule priority
     */
    int getPriority();

    /**
     * Rule conditions abstraction : this method encapsulates the rule's conditions.
     *
     * @return true if the rule should be applied, false else
     * @throws org.mintrules.api.RuleException if there's an error invoking the method.
     */
    boolean evaluateCondition(Session session) throws RuleException;

    /**
     * Rule actions abstraction : this method encapsulates the rule's actions.
     *
     * @throws Exception thrown if an exception occurs during actions performing
     */
    R executeAction(Session session) throws RuleException;

}
