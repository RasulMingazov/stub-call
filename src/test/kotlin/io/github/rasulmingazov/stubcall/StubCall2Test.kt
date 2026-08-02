package io.github.rasulmingazov.stubcall

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class StubCall2Test {

    @Test
    fun `passes both arguments through to calledWith`() {
        val stub = StubCall2.value<String, Int, Unit>(Unit)

        stub("acc-1", 100)

        stub.calledWith("acc-1", 100)
        assertFailsWith<StubCallAssertionError> { stub.calledWith("acc-1", 200) }
    }

    @Test
    fun `returns the stubbed value`() {
        val stub = StubCall2.value<String, Int, Boolean>(true)

        assertEquals(true, stub("acc-1", 100))
    }
}
