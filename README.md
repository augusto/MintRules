## What is Mint Rules?
Mint Rules is a simple rules engine 100% inspired in the super simple library EasyRules. Mint Rules is usefull for the
cases where EasyRules is too simple, but a far more complex solution (drools anyone?) is not required.

## Difference with Easy Rules?
The rules in EasyRules are usually stateful which leads to having to create a new rules engine per thread, this is the
price to pay for the beauty and simplicity of EasyRules.

MintRules allows the creation of a session, which holds the current state which gets automatically injected to the
condition and action classes. Plus, the action methods can return a value to allow the rules to be side effect free.
