package io.github.rasulmingazov.stubcall

internal class CallRecorder<R>(initial: R) {

    private var returnValue: R = initial
    private var thrownError: Throwable? = null
    private val recordedCalls = mutableListOf<List<Any?>>()

    val callCount: Int
        get() = recordedCalls.size

    fun record(args: List<Any?>): R {
        recordedCalls += args
        thrownError?.let { throw it }
        return returnValue
    }

    fun willReturn(value: R) {
        returnValue = value
        thrownError = null
    }

    fun willThrow(error: Throwable) {
        thrownError = error
    }

    fun calledOnce() {
        if (callCount != 1) {
            throw StubCallAssertionError("expected exactly one call, but it was called $callCount time(s)")
        }
    }

    fun notCalled() {
        if (callCount != 0) {
            throw StubCallAssertionError("expected no calls, but it was called $callCount time(s)")
        }
    }

    fun calledWith(args: List<Any?>) {
        if (recordedCalls.none { it == args }) {
            throw StubCallAssertionError("expected a call with arguments $args, but recorded calls were $recordedCalls")
        }
    }
}
