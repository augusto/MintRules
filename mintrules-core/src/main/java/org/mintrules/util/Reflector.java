package org.mintrules.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class Reflector {
    /**
     * Finds annotatedMethods. Because of how java reflection works, this method goes through Class.getMethods() and
     * Class.getDeclaredMethods() to allow methods which are not public.
     *
     * @param clazz     Object to search
     * @param annotation Annotation to find
     * @return a Set with all the matching methods.
     */
    public static Set<Method> getAnnotatedMethods(Class<?> clazz, Class<? extends Annotation> annotation) {
        Set<Method> candidateMethods = new HashSet<Method>();

        for (Method method : clazz.getMethods()) {
            if (method.isAnnotationPresent(annotation)) {
                candidateMethods.add(method);
            }
        }
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(annotation)) {
                candidateMethods.add(method);
            }
        }

        return candidateMethods;
    }
}
