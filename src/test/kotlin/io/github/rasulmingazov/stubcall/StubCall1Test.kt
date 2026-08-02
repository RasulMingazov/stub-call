package io.github.rasulmingazov.stubcall

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

private data class User(val id: String, val name: String)

class StubCall1Test {

    @Test
    fun `GIVEN an initial value, WHEN invoked, THEN returns it`() {
        val user = User("42", "Ada")
        val stub = StubCall1.returns<String, User>(user)

        assertEquals(user, stub.invoke("42"))
    }

    @Test
    fun `GIVEN a stub, WHEN invoked twice, THEN callCount is 2`() {
        val stub = StubCall1.returns<String, Unit>(Unit)

        stub.invoke("a")
        stub.invoke("b")

        assertEquals(2, stub.callCount)
    }

    @Test
    fun `GIVEN invoked with an argument, WHEN calledWith it, THEN passes`() {
        val stub = StubCall1.returns<String, Unit>(Unit)

        stub.invoke("42")

        stub.calledWith("42")
    }

    @Test
    fun `GIVEN invoked with an argument, WHEN calledWith another, THEN fails`() {
        val stub = StubCall1.returns<String, Unit>(Unit)

        stub.invoke("42")

        assertFailsWith<StubCallAssertionError> { stub.calledWith("43") }
    }

    @Test
    fun `GIVEN returns called again, WHEN invoked, THEN returns the new value`() {
        val stub = StubCall1.returns<String, String>("first")

        stub.returns("second")

        assertEquals("second", stub.invoke("42"))
    }

    @Test
    fun `GIVEN throws is set, WHEN invoked, THEN throws and records the argument`() {
        val stub = StubCall1.returns<String, Unit>(Unit)
        val error = IllegalStateException("boom")

        stub.throws(error)

        assertFailsWith<IllegalStateException> { stub.invoke("42") }
        stub.calledWith("42")
    }
}
