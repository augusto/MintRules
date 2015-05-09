package org.mintrules.core;

import org.mintrules.api.Rule;

/**
 * Abstract Rule. This class is responsible for dealing with the common behaviour in the rules, such as holding the
 * priority.
 */
public abstract class AbstractRule<R> implements Comparable<Rule>, Rule<R> {
    private final String name;
    private final int priority;
    private final String description;

    public AbstractRule(String name, int priority, String description) {
        this.name = name;
        this.priority = priority;
        this.description = description;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public int compareTo(Rule other) {
        return this.priority - other.getPriority();
    }

    @Override
    public String toString() {
        return "Rule{" +
                "name=" + name +
                ",description=" + description +
                ",priority=" + priority +
                '}';
    }
}
