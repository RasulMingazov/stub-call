package io.github.rasulmingazov.stubcall

import kotlin.test.Test
import kotlin.test.assertEquals

class StubCall6Test {

    @Test
    fun `GIVEN max arity, WHEN invoked, THEN returns, calledWith and called all work`() {
        val stub = StubCall6.returns<Int, Int, Int, Int, Int, Int, Int>(0)

        stub.returns(21)
        val result = stub.invoke(1, 2, 3, 4, 5, 6)

        assertEquals(21, result)
        stub.calledWith(1, 2, 3, 4, 5, 6)
        stub.called()
    }
}
