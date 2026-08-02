package io.github.rasulmingazov.stubcall

class StubCall3<A, B, C, R> internal constructor(initial: R) {

    private val recorder = CallRecorder(initial)

    val callCount: Int
        get() = recorder.callCount

    fun invoke(a: A, b: B, c: C): R = recorder.record(listOf(a, b, c))

    fun called(times: Int = 1) = recorder.called(times)

    fun notCalled() = recorder.notCalled()

    fun calledWith(a: A, b: B, c: C) = recorder.calledWith(listOf(a, b, c))

    fun returns(value: R) = recorder.returns(value)

    fun throws(error: Throwable) = recorder.throws(error)

    companion object {
        fun <A, B, C, R> returns(result: R): StubCall3<A, B, C, R> = StubCall3(result)
        fun <A, B, C> unit(): StubCall3<A, B, C, Unit> = StubCall3(Unit)
    }
}
