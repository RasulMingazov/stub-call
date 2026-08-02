package io.github.rasulmingazov.stubcall

class StubCall5<A, B, C, D, E, R> internal constructor(initial: R) {

    private val recorder = CallRecorder(initial)

    val callCount: Int
        get() = recorder.callCount

    fun invoke(a: A, b: B, c: C, d: D, e: E): R = recorder.record(listOf(a, b, c, d, e))

    fun called(times: Int = 1) = recorder.called(times)

    fun notCalled() = recorder.notCalled()

    fun calledWith(a: A, b: B, c: C, d: D, e: E) = recorder.calledWith(listOf(a, b, c, d, e))

    fun returns(value: R) = recorder.returns(value)

    fun throws(error: Throwable) = recorder.throws(error)

    companion object {
        fun <A, B, C, D, E, R> returns(result: R): StubCall5<A, B, C, D, E, R> = StubCall5(result)
        fun <A, B, C, D, E> unit(): StubCall5<A, B, C, D, E, Unit> = StubCall5(Unit)
    }
}
