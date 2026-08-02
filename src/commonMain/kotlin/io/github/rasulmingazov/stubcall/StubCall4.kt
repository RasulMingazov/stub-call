package io.github.rasulmingazov.stubcall

class StubCall4<A, B, C, D, R> internal constructor(initial: R) {

    private val recorder = CallRecorder(initial)

    val callCount: Int
        get() = recorder.callCount

    fun invoke(a: A, b: B, c: C, d: D): R = recorder.record(listOf(a, b, c, d))

    fun called(times: Int = 1) = recorder.called(times)

    fun notCalled() = recorder.notCalled()

    fun calledWith(a: A, b: B, c: C, d: D) = recorder.calledWith(listOf(a, b, c, d))

    fun returns(value: R) = recorder.returns(value)

    fun throws(error: Throwable) = recorder.throws(error)

    companion object {
        fun <A, B, C, D, R> returns(result: R): StubCall4<A, B, C, D, R> = StubCall4(result)
        fun <A, B, C, D> unit(): StubCall4<A, B, C, D, Unit> = StubCall4(Unit)
    }
}
