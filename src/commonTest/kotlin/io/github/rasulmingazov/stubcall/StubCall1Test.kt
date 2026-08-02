package io.github.rasulmingazov.stubcall

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

private data class User(val id: String, val name: String)

class StubCall1Test {

    @Test
    fun givenInitialValueWhenInvokedThenReturnsIt() {
        val user = User("42", "Ada")
        val stub = StubCall1.returns<String, User>(user)

        assertEquals(user, stub.invoke("42"))
    }

    @Test
    fun givenStubWhenInvokedTwiceThenCallCountIsTwo() {
        val stub = StubCall1.returns<String, Unit>(Unit)

        stub.invoke("a")
        stub.invoke("b")

        assertEquals(2, stub.callCount)
    }

    @Test
    fun givenInvokedWithArgumentWhenCalledWithItThenPasses() {
        val stub = StubCall1.returns<String, Unit>(Unit)

        stub.invoke("42")

        stub.calledWith("42")
    }

    @Test
    fun givenInvokedWithArgumentWhenCalledWithAnotherThenFails() {
        val stub = StubCall1.returns<String, Unit>(Unit)

        stub.invoke("42")

        assertFailsWith<StubCallAssertionError> { stub.calledWith("43") }
    }

    @Test
    fun givenReturnsCalledAgainWhenInvokedThenReturnsNewValue() {
        val stub = StubCall1.returns<String, String>("first")

        stub.returns("second")

        assertEquals("second", stub.invoke("42"))
    }

    @Test
    fun givenThrowsIsSetWhenInvokedThenThrowsAndRecordsArgument() {
        val stub = StubCall1.returns<String, Unit>(Unit)
        val error = IllegalStateException("boom")

        stub.throws(error)

        assertFailsWith<IllegalStateException> { stub.invoke("42") }
        stub.calledWith("42")
    }
}
