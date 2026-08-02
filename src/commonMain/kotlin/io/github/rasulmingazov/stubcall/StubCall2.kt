package io.github.rasulmingazov.stubcall

class StubCall2<A, B, R> internal constructor(initial: R) {

    private val recorder = CallRecorder(initial)

    val callCount: Int
        get() = recorder.callCount

    fun invoke(a: A, b: B): R = recorder.record(listOf(a, b))

    fun called(times: Int = 1) = recorder.called(times)

    fun notCalled() = recorder.notCalled()

    fun calledWith(a: A, b: B) = recorder.calledWith(listOf(a, b))

    fun returns(value: R) = recorder.returns(value)

    fun throws(error: Throwable) = recorder.throws(error)

    companion object {
        fun <A, B, R> returns(result: R): StubCall2<A, B, R> = StubCall2(result)
        fun <A, B> unit(): StubCall2<A, B, Unit> = StubCall2(Unit)
    }
}
