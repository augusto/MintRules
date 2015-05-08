/*
 * The MIT License
 *
 *  Copyright (c) 2015, Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */

package org.mintrules.core;

import org.mintrules.api.Rule;
import org.mintrules.api.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Annotated rules engine, configures the rules engine by reading annotations in the classes.
 */
public class AnnotatedRulesEngine<R> extends AbstractRulesEngine<R> {


    private List<Rule<R>> rules = new ArrayList<Rule<R>>();

    public AnnotatedRulesEngine() {

    }

    public void registerRule(Object rule) {
        AnnotatedRule<R> annotatedRule = new AnnotatedRule<R>(rule);
        rules.add(annotatedRule);
    }

    @Override
    public R fireRules(Session session) {
        getSortedRules();
        for (Rule<R> rule : rules) {
            if (rule.evaluateCondition(session)) {
                return rule.performAction();
            }
        }

        return null;
    }

    @Override
    public Session createSession() {
        return new DefaultSession();
    }

    public List<Rule<R>> getSortedRules() {
        rules.sort(null);
        return rules;
    }
}
