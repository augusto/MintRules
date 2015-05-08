package org.mintrules.testrules;

import org.mintrules.annotation.Action;
import org.mintrules.annotation.Condition;
import org.mintrules.annotation.Rule;

/**
 *
 */
@Rule
public class BasicWellFormedRule {

    private final boolean condition;
    private final String returnValue;

    public BasicWellFormedRule(boolean condition, String returnValue) {
        this.condition = condition;
        this.returnValue = returnValue;
    }

    @Condition
    public boolean condition() {
        return condition;
    }

    @Action
    public String action() {
        return returnValue;
    }

}
