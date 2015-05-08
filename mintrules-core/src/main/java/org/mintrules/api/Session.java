package org.mintrules.api;

/**
 * Represents a session of the rules engine. The session contain all the state required by the rules as the engine
 * executes.
 *
 * This object is not thread safe. A new one should be created for each run of the rules engine.
 */
public interface Session {

    /**
     * Adds an object to the session and defaults the key to be the class name of the object. This is equivalent to
     * <pre>put(obj.getClass().getCanonicalName(), obj);</pre>
     *
     * If there's already one item with the same name, the old item will be removed from the session.
     *
     * @param value value to put in the session.
     */
    void put(Object value);

    /**
     * Adds an object to the session so it can be injected on <pre>@Condition</pre> and <pre>@Action</pre> methods.
     *
     * If there's already one item with the same name, the old item will be removed from the session.
     *
     * @param name name of the object to put in the session.
     * @param value value to put in the session.
     */
    void put(String name, Object value);

    /**
     * Returns an object in the session that matches the parameterType.
     * @param parameterType type to find in the session
     * @return object in the session
     *
     * TODO THROWS: if not found or if more than one found
     */
    Object getByType(Class<?> parameterType);
}
