package io.github.rasulmingazov.stubcall

/**
 * Thrown by verification methods like [StubCall1.calledOnce] or [StubCall1.calledWith]
 * when the actual recorded calls don't match what was expected.
 */
class StubCallAssertionError(message: String) : AssertionError(message)
