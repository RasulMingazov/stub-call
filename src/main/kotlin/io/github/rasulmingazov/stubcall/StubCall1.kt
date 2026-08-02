package io.github.rasulmingazov.stubcall

class StubCall1<A, R> internal constructor(initial: R) {

    private val recorder = CallRecorder(initial)

    val callCount: Int
        get() = recorder.callCount

    operator fun invoke(a: A): R = recorder.record(listOf(a))

    fun calledOnce() = recorder.calledOnce()

    fun notCalled() = recorder.notCalled()

    fun calledWith(a: A) = recorder.calledWith(listOf(a))

    fun willReturn(value: R) = recorder.willReturn(value)

    fun willThrow(error: Throwable) = recorder.willThrow(error)

    companion object {
        fun <A, R> value(result: R): StubCall1<A, R> = StubCall1(result)
        fun <A> unit(): StubCall1<A, Unit> = StubCall1(Unit)
    }
}
