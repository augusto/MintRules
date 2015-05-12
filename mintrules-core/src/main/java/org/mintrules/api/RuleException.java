package org.mintrules.api;

/**
 * Wraps around an exception thrown by a rule. The message of the exception contains the name of the rule that failed
 * and the cause will be the exception thrown by the rule.
 */
public class RuleException extends RuntimeException{
    public RuleException(String message, Throwable cause) {
        super(message, cause);
    }
}
