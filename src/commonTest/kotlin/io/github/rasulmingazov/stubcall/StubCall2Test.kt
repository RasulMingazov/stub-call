package io.github.rasulmingazov.stubcall

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class StubCall2Test {

    @Test
    fun givenInvokedWithTwoArgumentsWhenCalledWithThenMatchesExactArgumentsOnly() {
        val stub = StubCall2.returns<String, Int, Unit>(Unit)

        stub.invoke("acc-1", 100)

        stub.calledWith("acc-1", 100)
        assertFailsWith<StubCallAssertionError> { stub.calledWith("acc-1", 200) }
    }

    @Test
    fun givenStubbedValueWhenInvokedThenReturnsIt() {
        val stub = StubCall2.returns<String, Int, Boolean>(true)

        assertEquals(true, stub.invoke("acc-1", 100))
    }
}
