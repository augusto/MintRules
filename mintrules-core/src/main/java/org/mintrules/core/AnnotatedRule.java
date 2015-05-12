package org.mintrules.core;

import org.mintrules.annotation.Action;
import org.mintrules.annotation.Condition;
import org.mintrules.annotation.Priority;
import org.mintrules.annotation.Rule;
import org.mintrules.api.RuleException;
import org.mintrules.api.Session;
import org.mintrules.util.Strings;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * Class representing an annotated rule. This class introspects on the rule object to retrieve the attributes and also
 * find the methods for the condition and action.
 */
public class AnnotatedRule<R> extends AbstractRule<R> {
    private static final int DEFAULT_RULE_PRIORITY = Integer.MAX_VALUE - 1;

    private final Object rule;
    private final Method conditionMethod;
    private final Method actionMethod;

    public AnnotatedRule(Object rule) {
        super(getAnnotatedRuleName(rule), getAnnotatedPriority(rule), getAnnotatedDescription(rule));
        this.rule = rule;
        //TODO check condition returns boolean (with lower case)
        //TODO check action returns correct data type
        conditionMethod = getConditionMethod(rule);
        actionMethod = getActionMethod(rule);
    }

    private Method getActionMethod(Object rule) {
        Method method = getAnnotatedMethod(rule, Action.class);
        if (method == null) {
            throw new IllegalArgumentException("Rule class " + rule.getClass().getCanonicalName() +
                    " must have one method annotated with @Action");
        }

        method.setAccessible(true);
        return method;
    }

    private Method getConditionMethod(Object rule) {
        Method method = getAnnotatedMethod(rule, Condition.class);
        if (method == null) {
            throw new IllegalArgumentException("Rule class " + rule.getClass().getCanonicalName() +
                    " must have one method annotated with @Condition");
        }
        method.setAccessible(true);

        return method;
    }

    @Override
    public boolean evaluateCondition(Session session) throws RuleException {
        try {
            Class<?>[] parameterTypes = conditionMethod.getParameterTypes();
            Annotation[][] parameterAnnotations = conditionMethod.getParameterAnnotations();
            Object arguments[] = new Object[parameterTypes.length];

            for (int i = 0; i < parameterTypes.length; i++) {
                arguments[i] = session.getValue(parameterTypes[i], parameterAnnotations[i]);
            }

            return (Boolean) conditionMethod.invoke(rule, arguments);
        } catch (IllegalAccessException e) {
            throw new RuleException("Exception thrown by condition method: " + conditionMethod.toString() + " from rule " + getName(), e); //this should never happen...right?
        } catch (InvocationTargetException e) {
            throw new RuleException("Exception thrown by condition method: " + conditionMethod.toString() + " from rule " + getName(), e.getCause());
        }
    }

    @Override
    public R executeAction(Session session) {
        try {
            Class<?>[] parameterTypes = actionMethod.getParameterTypes();
            Annotation[][] parameterAnnotations = actionMethod.getParameterAnnotations();
            Object arguments[] = new Object[parameterTypes.length];

            for (int i = 0; i < parameterTypes.length; i++) {
                if (parameterAnnotations.length > 0) {
                    arguments[i] = session.getValue(parameterTypes[i], parameterAnnotations[i]);
                }
            }

            return (R) actionMethod.invoke(rule, arguments);
        } catch (IllegalAccessException e) {
            throw new RuleException("Exception thrown by action method: " + conditionMethod.toString() + " from rule " + getName(), e); //this should never happen...right?
        } catch (InvocationTargetException e) {
            throw new RuleException("Exception thrown by action method: " + conditionMethod.toString() + " from rule " + getName(), e.getCause());
        }
    }

    /**
     * Finds one annotated method. This
     *
     * @param rule       Rule object to search
     * @param annotation Annotation to find
     * @return The annotated method or null if there's no annotated method.
     * @throws IllegalArgumentException if there's more than one method annotated with a given annotation.
     */
    private static Method getAnnotatedMethod(Object rule, Class<? extends Annotation> annotation) {
        Set<Method> methods = getAnnotatedMethods(rule, annotation);
        if (methods.size() > 1) {
            throw new IllegalArgumentException("More than one method found with the annotation @"
                    + annotation.getSimpleName() + " [" + Strings.join(methods, ",") + "]");
        } else if (methods.size() == 0) {
            return null;
        } else {
            return methods.iterator().next();
        }
    }

    /**
     * Finds annotatedMethods. Because of how java reflection works, this method goes through Class.getMethods() and
     * Class.getDeclaredMethods() to allow methods which are not public.
     *
     * @param rule       Rule object to search
     * @param annotation Annotation to find
     * @return a Set with all the matching methods.
     */
    private static Set<Method> getAnnotatedMethods(Object rule, Class<? extends Annotation> annotation) {
        Set<Method> candidateMethods = new HashSet<Method>();

        for (Method method : rule.getClass().getMethods()) {
            if (method.isAnnotationPresent(annotation)) {
                candidateMethods.add(method);
            }
        }
        for (Method method : rule.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(annotation)) {
                candidateMethods.add(method);
            }
        }

        return candidateMethods;
    }

    private static int getAnnotatedPriority(Object rule) {
        Method priorityMethod = getAnnotatedMethod(rule, Priority.class);

        if (priorityMethod == null) {
            return DEFAULT_RULE_PRIORITY;
        } else {
            try {
                return (Integer) priorityMethod.invoke(rule);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static String getAnnotatedRuleName(Object rule) {
        Rule annotation = getRuleAnnotation(rule);

        String name = annotation.name();
        if (name.isEmpty()) {
            name = rule.getClass().getCanonicalName();
        }

        return name;
    }

    private static String getAnnotatedDescription(Object rule) {
        Rule annotation = getRuleAnnotation(rule);

        return annotation.description();
    }

    private static Rule getRuleAnnotation(Object rule) {
        Rule annotation = rule.getClass().getAnnotation(Rule.class);
        if (annotation == null) {
            throw new IllegalArgumentException("Rule class " + rule.getClass().getCanonicalName() +
                    " must be annotated with @Rule");
        }
        return annotation;
    }
}
