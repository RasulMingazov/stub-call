package io.github.rasulmingazov.stubcall

class StubCall3<A, B, C, R> internal constructor(initial: R) {

    private val recorder = CallRecorder(initial)

    val callCount: Int
        get() = recorder.callCount

    operator fun invoke(a: A, b: B, c: C): R = recorder.record(listOf(a, b, c))

    fun calledOnce() = recorder.calledOnce()

    fun notCalled() = recorder.notCalled()

    fun calledWith(a: A, b: B, c: C) = recorder.calledWith(listOf(a, b, c))

    fun willReturn(value: R) = recorder.willReturn(value)

    fun willThrow(error: Throwable) = recorder.willThrow(error)

    companion object {
        fun <A, B, C, R> value(result: R): StubCall3<A, B, C, R> = StubCall3(result)
        fun <A, B, C> unit(): StubCall3<A, B, C, Unit> = StubCall3(Unit)
    }
}
