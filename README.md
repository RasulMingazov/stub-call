# StubCall

A small Kotlin/JVM library for hand-written test doubles: use cases, callbacks, and lambda fields. A single object can return a value, throw an error, record calls, and verify that it was called with the right arguments.

A plain JVM module (no Kotlin Multiplatform) — pulled into backend and Android projects alike with the same dependency.

`invoke(...)` on `StubCallN` is a regular method, not an `operator`. Calls are written explicitly via `.invoke(...)`, with no magic around calling the object with parentheses.

## Example

```kotlin
class StubGetUserUseCase(user: User) : GetUserUseCase {
    val invoke: StubCall1<String, User> = StubCall1.returns(user)
    override fun invoke(id: String): User = invoke.invoke(id)
}

val getUser = StubGetUserUseCase(defaultUser)

getUser.invoke.called()
getUser.invoke.calledWith("42")
getUser.invoke.returns(otherUser)
getUser.invoke.throws(NetworkError())
```

## Arity and factories

Each class `StubCall0`..`StubCall6` has its own static `returns(x)` and `unit()` — call the factory through the class matching the arity you need. For `StubCall0` the result type is inferred automatically. For `StubCall1`..`StubCall6` you'll need an explicit type annotation on the `val`, since the argument types don't otherwise appear anywhere in the factory call:

```kotlin
// 0 arguments — the type is inferred automatically
val invoke = StubCall0.returns(settings)                       // StubCall0<Settings>

// 1 argument
val invoke: StubCall1<String, User> = StubCall1.returns(user)  // explicit annotation required

// 2 arguments, Unit result
val invoke: StubCall2<String, Int, Unit> = StubCall2.unit()
override fun invoke(accountId: String, amount: Int) = invoke.invoke(accountId, amount)

// returns Unit, no arguments
val invoke = StubCall0.unit()                                 // StubCall0<Unit>
override fun invoke() = invoke.invoke()
```

`StubCall3.returns`..`StubCall6.returns` / `StubCall3.unit`..`StubCall6.unit` are also available — up to six arguments.

## API of each StubCallN

- `called(times: Int = 1)` — fails with `StubCallAssertionError` if the recorded call count doesn't equal `times`; `times` must be `>= 0`.
- `notCalled()` — fails if there was at least one call.
- `calledWith(a, b, ...)` — fails if none of the recorded calls matches these arguments (not available on `StubCall0`, which takes no arguments).
- `callCount` — how many times the object was called.
- `returns(value)` — changes the value that subsequent calls will return.
- `throws(error)` — subsequent calls will throw `error` (the call itself is still recorded).

## Setup

The library isn't published to Maven Central yet — include it as a composite build:

```kotlin
// settings.gradle.kts of the consuming project
includeBuild("../stub-call")
```

or build it and publish to your local Maven repository:

```
./gradlew publishToMavenLocal
```

## Deliberately not implemented in this version

- Call order verification across different stubs (order checking).
- suspend/coroutine functions.
- A matcher DSL (`any()`, `argThat {}`) — argument comparison only via `equals()`.
- Different responses by call number (`returns(x).thenReturn(y)`).
- "Pretty" array/collection comparison in error messages — for `Array`, false negatives are possible due to reference-based comparison.
