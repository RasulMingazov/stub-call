package io.github.rasulmingazov.stubcall

class StubCall0<R> internal constructor(initial: R) {

    private val recorder = CallRecorder(initial)

    val callCount: Int
        get() = recorder.callCount

    operator fun invoke(): R = recorder.record(emptyList())

    fun calledOnce() = recorder.calledOnce()

    fun notCalled() = recorder.notCalled()

    fun willReturn(value: R) = recorder.willReturn(value)

    fun willThrow(error: Throwable) = recorder.willThrow(error)

    companion object {
        fun <R> value(result: R): StubCall0<R> = StubCall0(result)
        fun unit(): StubCall0<Unit> = StubCall0(Unit)
    }
}
