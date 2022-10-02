package commands

import kotlin.io.path.div
import kotlin.io.path.readBytes
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CatCommandTest : BaseCommandTest() {

    lateinit var cmd: CatCommand

    @BeforeEach
    override fun init() {
        cmd = CatCommand()
        super.init()
    }

    @Test
    fun testNoArguments() {
        assertNotEquals(0, cmd.execute())
        assert(output.toByteArray().isEmpty())
        assert(error.toByteArray().isNotEmpty())
    }

    @Test
    fun testFile() {
        val testFileName = "test.txt"
        val testFile = sessionContext.currentDirectory / testFileName
        assertEquals(0, cmd.execute(testFileName), error.toByteArray().decodeToString())
        assertContentEquals(testFile.readBytes(), output.toByteArray())
        assert(error.toByteArray().isEmpty())
    }

}