package io.github.rasulmingazov.stubcall

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

private data class User(val id: String, val name: String)

class StubCall1Test {

    @Test
    fun `returns the initial value regardless of the argument`() {
        val user = User("42", "Ada")
        val stub = StubCall1.value<String, User>(user)

        assertEquals(user, stub("42"))
    }

    @Test
    fun `records the argument of each call`() {
        val stub = StubCall1.value<String, Unit>(Unit)

        stub("a")
        stub("b")

        assertEquals(2, stub.callCount)
    }

    @Test
    fun `calledWith passes when a matching call was recorded`() {
        val stub = StubCall1.value<String, Unit>(Unit)

        stub("42")

        stub.calledWith("42")
    }

    @Test
    fun `calledWith fails when no call matches the argument`() {
        val stub = StubCall1.value<String, Unit>(Unit)

        stub("42")

        assertFailsWith<StubCallAssertionError> { stub.calledWith("43") }
    }

    @Test
    fun `willReturn overrides the result of later calls`() {
        val stub = StubCall1.value<String, String>("first")

        stub.willReturn("second")

        assertEquals("second", stub("42"))
    }

    @Test
    fun `willThrow makes the call throw but still records the argument`() {
        val stub = StubCall1.value<String, Unit>(Unit)
        val error = IllegalStateException("boom")

        stub.willThrow(error)

        assertFailsWith<IllegalStateException> { stub("42") }
        stub.calledWith("42")
    }
}
