package org.mintrules.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation declare what value to inject to method. The value must have been added to the Session by name to make this
 * work.
 * <br/>
 * For example:
 * <pre>
 *     @Action
 *     public int then(@Value("price") int price, @Value("quantity") int quantity) {
 *         return price * quantity;
 *     }
 * </pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Value {

    /**
     * Name of the value to inject
     *
     * @return value name
     */
    public String value();
}
