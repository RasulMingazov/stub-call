# Project rules

## Test naming

Name test functions using the GIVEN/WHEN/THEN pattern, with the keywords in caps. Keep each part short — a few words, not a full sentence:

```
`GIVEN <precondition>, WHEN <action>, THEN <expected outcome>`
```

Example:

```kotlin
@Test
fun `GIVEN an initial value, WHEN invoked, THEN returns it`() {
    val stub = StubCall0.returns("hello")

    assertEquals("hello", stub.invoke())
}
```
