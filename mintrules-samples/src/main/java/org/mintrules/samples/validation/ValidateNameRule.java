package org.mintrules.samples.validation;

import org.mintrules.annotation.Action;
import org.mintrules.annotation.Condition;
import org.mintrules.annotation.Rule;

@Rule
public class ValidateNameRule {

    @Condition
    public boolean validateName(Person person) {
        return person.getName() != null && person.getName().length() < 2;
    }

    @Action
    public void action(Person person) {
        throw new ValidationException("Invalid name: " + person.getName());
    }
}
