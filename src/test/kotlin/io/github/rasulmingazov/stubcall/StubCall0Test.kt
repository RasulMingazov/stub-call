package io.github.rasulmingazov.stubcall

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class StubCall0Test {

    @Test
    fun `GIVEN an initial value, WHEN invoked, THEN returns it`() {
        val stub = StubCall0.returns("hello")

        assertEquals("hello", stub.invoke())
    }

    @Test
    fun `GIVEN a stub, WHEN invoked 3 times, THEN callCount is 3`() {
        val stub = StubCall0.returns(Unit)

        stub.invoke()
        stub.invoke()
        stub.invoke()

        assertEquals(3, stub.callCount)
    }

    @Test
    fun `GIVEN invoked once, WHEN called, THEN passes`() {
        val stub = StubCall0.returns(Unit)

        stub.invoke()

        stub.called()
    }

    @Test
    fun `GIVEN never invoked, WHEN called, THEN fails`() {
        val stub = StubCall0.returns(Unit)

        assertFailsWith<StubCallAssertionError> { stub.called() }
    }

    @Test
    fun `GIVEN invoked twice, WHEN called, THEN fails`() {
        val stub = StubCall0.returns(Unit)

        stub.invoke()
        stub.invoke()

        assertFailsWith<StubCallAssertionError> { stub.called() }
    }

    @Test
    fun `GIVEN invoked 3 times, WHEN called(3), THEN passes`() {
        val stub = StubCall0.returns(Unit)

        stub.invoke()
        stub.invoke()
        stub.invoke()

        stub.called(3)
    }

    @Test
    fun `GIVEN a negative times, WHEN called, THEN throws`() {
        val stub = StubCall0.returns(Unit)

        assertFailsWith<IllegalArgumentException> { stub.called(-1) }
    }

    @Test
    fun `GIVEN never invoked, WHEN notCalled, THEN passes`() {
        val stub = StubCall0.returns(Unit)

        stub.notCalled()
    }

    @Test
    fun `GIVEN invoked once, WHEN notCalled, THEN fails`() {
        val stub = StubCall0.returns(Unit)

        stub.invoke()

        assertFailsWith<StubCallAssertionError> { stub.notCalled() }
    }

    @Test
    fun `GIVEN returns called again, WHEN invoked, THEN returns the new value`() {
        val stub = StubCall0.returns("first")

        stub.returns("second")

        assertEquals("second", stub.invoke())
    }

    @Test
    fun `GIVEN throws is set, WHEN invoked, THEN throws and records the call`() {
        val stub = StubCall0.returns(Unit)
        val error = IllegalStateException("boom")

        stub.throws(error)

        val thrown = assertFailsWith<IllegalStateException> { stub.invoke() }
        assertEquals(error, thrown)
        assertEquals(1, stub.callCount)
    }
}
