package org.mintrules.util;

import java.util.Collection;

/**
 * Utility class for all those cool String manipulation methods that are not present in jdk < 1.8
 */
public final class Strings {

    private Strings() {}

    public static String join(Collection<?> items, String separator) {
        boolean first = true;
        StringBuilder sb = new StringBuilder();

        for (Object item : items) {
            if (first) {
                sb.append(item);
            }
            sb.append(separator).append(item);
        }

        return sb.toString();
    }
}
