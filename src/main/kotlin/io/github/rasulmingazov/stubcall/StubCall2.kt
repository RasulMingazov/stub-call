package io.github.rasulmingazov.stubcall

class StubCall2<A, B, R> internal constructor(initial: R) {

    private val recorder = CallRecorder(initial)

    val callCount: Int
        get() = recorder.callCount

    operator fun invoke(a: A, b: B): R = recorder.record(listOf(a, b))

    fun calledOnce() = recorder.calledOnce()

    fun notCalled() = recorder.notCalled()

    fun calledWith(a: A, b: B) = recorder.calledWith(listOf(a, b))

    fun willReturn(value: R) = recorder.willReturn(value)

    fun willThrow(error: Throwable) = recorder.willThrow(error)

    companion object {
        fun <A, B, R> value(result: R): StubCall2<A, B, R> = StubCall2(result)
        fun <A, B> unit(): StubCall2<A, B, Unit> = StubCall2(Unit)
    }
}
