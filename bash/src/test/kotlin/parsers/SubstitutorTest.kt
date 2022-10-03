package parsers

import SessionContext
import com.github.h0tk3y.betterParse.parser.ErrorResult
import com.github.h0tk3y.betterParse.parser.Parsed
import java.util.stream.Stream
import kotlin.io.path.Path
import kotlin.test.assertEquals
import kotlin.test.assertIs
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

class SubstitutorTest {

    @ParameterizedTest
    @MethodSource("substitutorProvider")
    fun testSubstitutor(values: Map<String, String>, commandLine: String, expectedResult: String?) {
        val context = SessionContext(Path(""), values.toMutableMap())
        val result = Substitutor.parse(context, commandLine)
        if (expectedResult != null) {
            assertIs<Parsed<String>>(result)
            assertEquals(expectedResult, result.value)
        } else {
            assertIs<ErrorResult>(result)
        }
    }

    companion object {
        @JvmStatic
        fun substitutorProvider(): Stream<Arguments> = Stream.of(
            arguments(emptyMap<String, String>(), "", ""),
            arguments(emptyMap<String, String>(), "cmd as1   2sf czpq 'sa   c aq\\'", "cmd as1   2sf czpq 'sa   c aq\\'"),
            arguments(emptyMap<String, String>(), "\$a", ""),
            arguments(emptyMap<String, String>(), "\${a}", ""),
            arguments(mapOf("a" to "some text   lol"), "\$a", "some text   lol"),
            arguments(mapOf("a" to "ex", "b" to "it"), "\$a\$b", "exit")
        )
    }

}