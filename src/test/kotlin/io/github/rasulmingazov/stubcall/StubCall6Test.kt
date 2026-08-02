package io.github.rasulmingazov.stubcall

import kotlin.test.Test
import kotlin.test.assertEquals

class StubCall6Test {

    @Test
    fun `supports the maximum arity end to end`() {
        val stub = StubCall6.value<Int, Int, Int, Int, Int, Int, Int>(0)

        stub.willReturn(21)
        val result = stub(1, 2, 3, 4, 5, 6)

        assertEquals(21, result)
        stub.calledWith(1, 2, 3, 4, 5, 6)
        stub.calledOnce()
    }
}
