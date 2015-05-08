package org.mintrules.core;

import org.mintrules.api.Rule;

/**
 * Abstract Rule. This class is responsible for dealing with the common behaviour in the rules, such as holding the
 * priority.
 */
public abstract class AbstractRule<R> implements Comparable<Rule>, Rule<R>{
    private int priority;

    public AbstractRule(int priority) {
        this.priority = priority;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public int compareTo(Rule other) {
        return  this.priority - other.getPriority();
    }

    @Override
    public String toString() {
        return "Rule{" +
                "name=" + getName() +
                ",description=" + getDescription() +
                ",priority=" + priority +
                '}';
    }
}
