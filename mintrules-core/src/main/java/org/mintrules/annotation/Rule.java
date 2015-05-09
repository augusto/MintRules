package org.mintrules.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark a class as a rule.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Rule {

    /**
     * The rule name. Defaults to the class name if not provided or if empty.
     *
     * @return The rule name
     */
    public String name() default "";

    /**
     * The rule description.
     *
     * @return The rule description
     */
    public String description() default "No Description";

}
