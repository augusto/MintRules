package org.mintrules.testrules;

import org.mintrules.annotation.Action;
import org.mintrules.annotation.Condition;
import org.mintrules.annotation.Priority;
import org.mintrules.annotation.Rule;

/**
 *
 */
@Rule()
public class BasicPrioritisedWellFormedRule extends BasicWellFormedRule{

    private final int priority;

    public BasicPrioritisedWellFormedRule(boolean condition, String returnValue, int priority) {
        super(condition, returnValue);
        this.priority = priority;
    }

    @Priority
    public int priority() {
        return priority;
    }

}
