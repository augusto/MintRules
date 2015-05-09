## What is Mint Rules?
Mint Rules is a simple rules engine 100% inspired in the super simple library EasyRules by Mahmoud Ben Hassine. Mint
Rules is useful for cases where EasyRules is too simple, but a far more complex solution (drools anyone?) is not
required.

## Difference with Easy Rules?
The rules in EasyRules are usually stateful which leads to having to create a new rules engine per thread, this is the
price to pay for the beauty and simplicity of EasyRules.

### Working memory
MintRules borrows the concept of a Session from Drools. A `Session` holds the current state of a
execution of the rules. The values in the working memory get automatically injected to the condition and action classes.

The Session can be injected to the rules in order to allow rules to add facts. This must be done with care as
it will create a time dependency between rules. Using priority with this approach is quite important. Another use of
the Session is to set values that are retrieved after the rules engine completes running

### Returning values
The action methods can return a value to allow the rules to be side effect free. All action methods must return the same
type (or a subclass of it).
