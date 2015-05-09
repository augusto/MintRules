package org.mintrules.core;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DefaultSessionTest {
    @Test
    public void shouldAddItselfToTheValuesInTheSession() throws Exception {
        DefaultSession session = new DefaultSession();

        assertThat(session.getElements()).containsValue(session);

    }
}