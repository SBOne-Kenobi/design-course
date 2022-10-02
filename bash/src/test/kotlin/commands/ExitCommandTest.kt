package commands

import kotlin.test.assertEquals
import kotlin.test.assertFalse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ExitCommandTest : BaseCommandTest() {

    lateinit var cmd: ExitCommand

    @BeforeEach
    override fun init() {
        super.init()
        cmd = ExitCommand()
    }

    @Test
    fun testExit() {
        assertEquals(0, cmd.execute())
        assertFalse(sessionContext.isRunning)
    }

}