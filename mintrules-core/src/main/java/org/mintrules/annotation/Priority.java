package org.mintrules.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark the method to execute to get rule priority.
 * The method must
 *
 * <ul>
 *     <li>Receive no parameters</li>
 *     <li>Return an integer</li>
 *     <li>Should always return the same value</li>
 * </ul>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Priority {

}
