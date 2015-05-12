## What is Mint Rules?
Mint Rules is a simple rules engine 100% inspired in the super simple library [EasyRules](http://www.easyrules.org/)
by [Mahmoud Ben Hassine](https://github.com/benas). Mint Rules is useful for cases where
[EasyRules](http://www.easyrules.org/) is too simple, but a far more complex solution (drools anyone?) is not
required.

## Difference with Easy Rules?
The rules in [EasyRules](http://www.easyrules.org/) are usually stateful which leads to having to create a new rules engine per thread, this is the
price to pay for the beauty and simplicity of EasyRules.

### Working memory
MintRules borrows the concept of a Session from Drools. A `Session` holds the current state of a
execution of the rules. The values in the Session get automatically injected to the condition and action methods.

The Session can be injected to the rules in order to allow rules to add facts. This must be done with care as
it will create a time dependency between rules. Using priority with this approach is quite important. Another use of
the Session is to set values that are retrieved after the rules engine completes running

### Returning values
The action methods can return a value to allow the rules to be side effect free. All action methods must return the same
type (or a subclass of it).


## TODO

* Handle exceptions triggered from rules in a nicer way rather than wrap them in a RuntimeException
* Ensure error messages are super clear
* Validate return type of @Condition, @Action and @Priority methods as rules are registered
* Separate the mess of reflection (especially the logic to find methods) to another class.
* API to create a session and later use it in the rules engine is a bit crappy.
* Document the hell out of every method
* Add more and better examples
    * using sessions to pass values between rules
    * using sessions to retrieve values at the end
* Add logging (maybe with bridges for JUL and slf4j)
* ability to set rule name/description at the time the rule is registered (to report nicer names/descriptions when
  the same rule is used with different parameters.