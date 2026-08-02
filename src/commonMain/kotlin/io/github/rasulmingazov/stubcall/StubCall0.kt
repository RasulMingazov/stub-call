package io.github.rasulmingazov.stubcall

class StubCall0<R> internal constructor(initial: R) {

    private val recorder = CallRecorder(initial)

    val callCount: Int
        get() = recorder.callCount

    fun invoke(): R = recorder.record(emptyList())

    fun called(times: Int = 1) = recorder.called(times)

    fun notCalled() = recorder.notCalled()

    fun returns(value: R) = recorder.returns(value)

    fun throws(error: Throwable) = recorder.throws(error)

    companion object {
        fun <R> returns(result: R): StubCall0<R> = StubCall0(result)
        fun unit(): StubCall0<Unit> = StubCall0(Unit)
    }
}
