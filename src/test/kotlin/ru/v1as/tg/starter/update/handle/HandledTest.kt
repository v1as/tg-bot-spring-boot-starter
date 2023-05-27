package ru.v1as.tg.starter.update.handle

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class HandledTest {

    @ParameterizedTest
    @MethodSource("arguments")
    fun `Should reduce properly`(left: Handled, right: Handled, expected: Handled) {
        assertEquals(expected.type, left.reduce(right).type)
        assertEquals(expected.errors.size, left.reduce(right).errors.size)
    }

    companion object {
        @JvmStatic
        fun arguments() = Stream.of(
            Arguments.of(handled(), handled(), handled()),
            Arguments.of(handled(), unmatched(), handled()),
            Arguments.of(unmatched(), handled(), handled()),
            Arguments.of(unmatched(), unmatched(), unmatched()),
            Arguments.of(handled(), error(RuntimeException()), handledWithError(listOf(RuntimeException()))),
            Arguments.of(unmatched(), error(RuntimeException()), error(RuntimeException())),
            Arguments.of(
                error(RuntimeException("hi")),
                error(RuntimeException("hello")),
                Handled(HandledType.ERROR, listOf(RuntimeException("hi"), RuntimeException("hello")))
            ),
        )
    }
}