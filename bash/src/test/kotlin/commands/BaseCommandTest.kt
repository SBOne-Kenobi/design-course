package commands

import SessionContext
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import kotlin.io.path.toPath
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach

open class BaseCommandTest {
    lateinit var sessionContext: SessionContext
    lateinit var input: ByteArrayInputStream
    lateinit var output: ByteArrayOutputStream
    lateinit var error: ByteArrayOutputStream

    @BeforeEach
    open fun init() {
        input = ByteArrayInputStream(ByteArray(0))
        output = ByteArrayOutputStream()
        error = ByteArrayOutputStream()
        sessionContext = SessionContext(BaseCommandTest::class.java.getResource("/testRoot")!!.toURI().toPath())
    }

    @AfterEach
    open fun release() {
        input.close()
        output.close()
        error.close()
    }

    fun setInput(bytes: ByteArray) {
        input.close()
        input = ByteArrayInputStream(bytes)
    }

    fun Command.execute(vararg arguments: String): Int =
        execute(input, output, error, sessionContext, arguments.toList().toTypedArray())
}