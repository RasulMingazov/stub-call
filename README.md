# StubCall

Type-safe test doubles for Kotlin, without a mocking framework or reflection.

## Why

Hand-written stubs usually mean writing the same boilerplate every time: a field to hold the return value, a counter for calls, a list of recorded arguments, an assertion helper. StubCall is that boilerplate, written once and reused — `StubCall0`..`StubCall6` cover interfaces with zero to six arguments, fully typed, so a stub returning the wrong type fails to compile instead of failing at runtime.

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

## API

- `StubCallN.returns(value)` / `StubCallN.unit()` — factory functions, called through the class matching the arity (`N` = 0..6). `unit()` fixes the result type to `Unit`.
- `invoke(a, b, ...)` — a regular method, called explicitly as `.invoke(...)`.
- `returns(value)` / `throws(error)` — change what subsequent calls return or throw.
- `called(times: Int = 1)` / `notCalled()` — assert the call count, throwing `StubCallAssertionError` on mismatch.
- `calledWith(a, b, ...)` — assert that some recorded call matches these arguments (`StubCall0` has none).
- `callCount` — number of recorded calls.

## Setup

Use it as a local composite build. Keep `stub-call` next to the project that consumes it:

```text
Projects/
  stub-call/
  your-app/
```

Add it to the consuming project's `settings.gradle.kts`:

```kotlin
includeBuild("../stub-call")
```

Then add the dependency in the module that owns your tests:

```kotlin
dependencies {
    testImplementation("io.github.rasulmingazov:stubcall:0.1.0")
}
```

For Android instrumentation tests, use `androidTestImplementation(...)` instead.

## Publishing to a file-based Maven repository

Pass the target repository path through the `localMavenRepository` Gradle property:

```bash
./gradlew publishAllPublicationsToLocalRepository \
  -PlocalMavenRepository=/absolute/path/to/maven-repository
```

Increase the library version before publishing changed code. Do not overwrite an already consumed
version because Gradle may continue using its cached artifacts.

## Not supported

Order verification across stubs, suspend functions, argument matchers (`any()`, `argThat {}`), and per-call responses (`returns(x).thenReturn(y)`) — arguments are compared with `equals()` only.
