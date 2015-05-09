package org.mintrules.core;

import org.mintrules.annotation.Action;
import org.mintrules.annotation.Condition;
import org.mintrules.annotation.Priority;
import org.mintrules.annotation.Rule;
import org.mintrules.api.Session;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
        super(getAnnotatedPriority(rule));
        this.rule = rule;
        //TODO check condition returns boolean (with lower case)
        //TODO check action returns correct data type
        conditionMethod = getAnnotatedMethod(rule, Condition.class);
        conditionMethod.setAccessible(true);
        actionMethod = getAnnotatedMethod(rule, Action.class);
        actionMethod.setAccessible(true);
    }

    @Override
    public String getName() {
        return getAnnotatedRuleName(rule);
    }

    @Override
    public String getDescription() {
        return getAnnotatedDescription(rule);
    }

    @Override
    public boolean evaluateCondition(Session session) {
        try {
            Class<?>[] parameterTypes = conditionMethod.getParameterTypes();
            Annotation[][] parameterAnnotations = conditionMethod.getParameterAnnotations();
            Object arguments[] = new Object[parameterTypes.length];

            for (int i = 0; i < parameterTypes.length; i++) {
                arguments[i] = session.getValue(parameterTypes[i], parameterAnnotations[i]);
            }

            return (Boolean) conditionMethod.invoke(rule, arguments);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public R performAction(Session session) {
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
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static Method getAnnotatedMethod(Object rule, Class<? extends Annotation> annotation) {
        Method[] methods = rule.getClass().getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(annotation)) {
                return method;
            }
        }
        return null;
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
        Rule annotation = rule.getClass().getAnnotation(Rule.class);

        String name = annotation.name();
        if (name.isEmpty()) {
            name = rule.getClass().getCanonicalName();
        }

        return name;
    }

    private static String getAnnotatedDescription(Object rule) {
        Rule annotation = rule.getClass().getAnnotation(Rule.class);

        return annotation.description();
    }
}
