package commands

import kotlin.io.path.absolutePathString
import kotlin.test.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class PwdCommandTest : BaseCommandTest() {
    lateinit var cmd: PwdCommand

    @BeforeEach
    override fun init() {
        super.init()
        cmd = PwdCommand()
    }

    @Test
    fun testPwd() {
        assertEquals(0, cmd.execute())
        assertEquals(
            sessionContext.currentDirectory.absolutePathString(),
            output.toByteArray().decodeToString()
        )
    }
}