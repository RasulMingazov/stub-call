package io.github.rasulmingazov.stubcall

class StubCall6<A, B, C, D, E, F, R> internal constructor(initial: R) {

    private val recorder = CallRecorder(initial)

    val callCount: Int
        get() = recorder.callCount

    operator fun invoke(a: A, b: B, c: C, d: D, e: E, f: F): R = recorder.record(listOf(a, b, c, d, e, f))

    fun calledOnce() = recorder.calledOnce()

    fun notCalled() = recorder.notCalled()

    fun calledWith(a: A, b: B, c: C, d: D, e: E, f: F) = recorder.calledWith(listOf(a, b, c, d, e, f))

    fun willReturn(value: R) = recorder.willReturn(value)

    fun willThrow(error: Throwable) = recorder.willThrow(error)

    companion object {
        fun <A, B, C, D, E, F, R> value(result: R): StubCall6<A, B, C, D, E, F, R> = StubCall6(result)
        fun <A, B, C, D, E, F> unit(): StubCall6<A, B, C, D, E, F, Unit> = StubCall6(Unit)
    }
}
