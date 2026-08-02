package io.github.rasulmingazov.stubcall

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class StubCall0Test {

    @Test
    fun givenInitialValueWhenInvokedThenReturnsIt() {
        val stub = StubCall0.returns("hello")

        assertEquals("hello", stub.invoke())
    }

    @Test
    fun givenStubWhenInvokedThreeTimesThenCallCountIsThree() {
        val stub = StubCall0.returns(Unit)

        stub.invoke()
        stub.invoke()
        stub.invoke()

        assertEquals(3, stub.callCount)
    }

    @Test
    fun givenInvokedOnceWhenCalledThenPasses() {
        val stub = StubCall0.returns(Unit)

        stub.invoke()

        stub.called()
    }

    @Test
    fun givenNeverInvokedWhenCalledThenFails() {
        val stub = StubCall0.returns(Unit)

        assertFailsWith<StubCallAssertionError> { stub.called() }
    }

    @Test
    fun givenInvokedTwiceWhenCalledThenFails() {
        val stub = StubCall0.returns(Unit)

        stub.invoke()
        stub.invoke()

        assertFailsWith<StubCallAssertionError> { stub.called() }
    }

    @Test
    fun givenInvokedThreeTimesWhenCalledWithThreeThenPasses() {
        val stub = StubCall0.returns(Unit)

        stub.invoke()
        stub.invoke()
        stub.invoke()

        stub.called(3)
    }

    @Test
    fun givenNegativeTimesWhenCalledThenThrows() {
        val stub = StubCall0.returns(Unit)

        assertFailsWith<IllegalArgumentException> { stub.called(-1) }
    }

    @Test
    fun givenNeverInvokedWhenNotCalledThenPasses() {
        val stub = StubCall0.returns(Unit)

        stub.notCalled()
    }

    @Test
    fun givenInvokedOnceWhenNotCalledThenFails() {
        val stub = StubCall0.returns(Unit)

        stub.invoke()

        assertFailsWith<StubCallAssertionError> { stub.notCalled() }
    }

    @Test
    fun givenReturnsCalledAgainWhenInvokedThenReturnsNewValue() {
        val stub = StubCall0.returns("first")

        stub.returns("second")

        assertEquals("second", stub.invoke())
    }

    @Test
    fun givenThrowsIsSetWhenInvokedThenThrowsAndRecordsCall() {
        val stub = StubCall0.returns(Unit)
        val error = IllegalStateException("boom")

        stub.throws(error)

        val thrown = assertFailsWith<IllegalStateException> { stub.invoke() }
        assertEquals(error, thrown)
        assertEquals(1, stub.callCount)
    }
}
