package org.mintrules.samples.decision;

import org.mintrules.annotation.Action;
import org.mintrules.annotation.Condition;
import org.mintrules.annotation.Rule;

@Rule
public class ChargeRule {

    private final int hourFrom;
    private final int hourTo;
    private final double chargeMultiplier;

    public ChargeRule(int hourFrom, int hourTo, double chargeMultiplier) {
        this.hourFrom = hourFrom;
        this.hourTo = hourTo;
        this.chargeMultiplier = chargeMultiplier;
    }

    @Condition
    public boolean applies(int started) {
        return hourFrom <= started && started < hourTo;
    }

    @Action
    public double fare(double length) {
        return length * chargeMultiplier;
    }
}
