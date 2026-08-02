package io.github.rasulmingazov.stubcall

class StubCall1<A, R> internal constructor(initial: R) {

    private val recorder = CallRecorder(initial)

    val callCount: Int
        get() = recorder.callCount

    fun invoke(a: A): R = recorder.record(listOf(a))

    fun called(times: Int = 1) = recorder.called(times)

    fun notCalled() = recorder.notCalled()

    fun calledWith(a: A) = recorder.calledWith(listOf(a))

    fun returns(value: R) = recorder.returns(value)

    fun throws(error: Throwable) = recorder.throws(error)

    companion object {
        fun <A, R> returns(result: R): StubCall1<A, R> = StubCall1(result)
        fun <A> unit(): StubCall1<A, Unit> = StubCall1(Unit)
    }
}
