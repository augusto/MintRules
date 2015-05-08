package org.mintrules.core;

import org.mintrules.api.Session;

import java.util.*;

import static java.lang.String.format;

/**
 * Default implementation of a session.
 */
public class DefaultSession implements Session {

    public Map<String, Object> elements = new HashMap<String, Object>();

    @Override
    public void put(Object value) {
        elements.put(value.getClass().getCanonicalName(), value);
    }

    @Override
    public void put(String name, Object value) {
        elements.put(name, value);
    }

    @Override
    public Object getByType(Class<?> parameterType) {
        StringBuilder sb = new StringBuilder(0);
        Map.Entry candidate = null;

        for (Map.Entry<String, Object> entry : elements.entrySet()){
            if(entry.getValue().getClass().isAssignableFrom(parameterType)) {
                if( candidate == null) {
                    candidate = entry;
                } else {
                    if(sb.length() == 0) {
                        sb.append('\'').append(candidate.getKey()).append('\'');
                    }
                    sb.append(", '").append(entry.getKey()).append('\'');
                }
            }
        }

        if( candidate == null) {
            throw new RuntimeException("Couldn't find an instance of " + parameterType.getCanonicalName());
        } else if( sb.length() >0) {
            String message = format("More than one instance of %s was found [%s]", parameterType.getCanonicalName(), sb);
            throw new RuntimeException(message);
        }

        return candidate.getValue();
    }
}
