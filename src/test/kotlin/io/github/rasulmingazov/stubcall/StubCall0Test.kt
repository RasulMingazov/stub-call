package io.github.rasulmingazov.stubcall

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class StubCall0Test {

    @Test
    fun `returns the initial value`() {
        val stub = StubCall0.value("hello")

        assertEquals("hello", stub())
    }

    @Test
    fun `records every call`() {
        val stub = StubCall0.value(Unit)

        stub()
        stub()
        stub()

        assertEquals(3, stub.callCount)
    }

    @Test
    fun `calledOnce passes for exactly one call`() {
        val stub = StubCall0.value(Unit)

        stub()

        stub.calledOnce()
    }

    @Test
    fun `calledOnce fails when never called`() {
        val stub = StubCall0.value(Unit)

        assertFailsWith<StubCallAssertionError> { stub.calledOnce() }
    }

    @Test
    fun `calledOnce fails when called more than once`() {
        val stub = StubCall0.value(Unit)

        stub()
        stub()

        assertFailsWith<StubCallAssertionError> { stub.calledOnce() }
    }

    @Test
    fun `notCalled passes when never called`() {
        val stub = StubCall0.value(Unit)

        stub.notCalled()
    }

    @Test
    fun `notCalled fails after a call`() {
        val stub = StubCall0.value(Unit)

        stub()

        assertFailsWith<StubCallAssertionError> { stub.notCalled() }
    }

    @Test
    fun `willReturn overrides the result of later calls`() {
        val stub = StubCall0.value("first")

        stub.willReturn("second")

        assertEquals("second", stub())
    }

    @Test
    fun `willThrow makes the call throw but still records it`() {
        val stub = StubCall0.value(Unit)
        val error = IllegalStateException("boom")

        stub.willThrow(error)

        val thrown = assertFailsWith<IllegalStateException> { stub() }
        assertEquals(error, thrown)
        assertEquals(1, stub.callCount)
    }
}
