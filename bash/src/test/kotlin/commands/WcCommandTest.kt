package commands

import kotlin.io.path.div
import kotlin.io.path.fileSize
import kotlin.io.path.readLines
import kotlin.io.path.readText
import kotlin.test.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class WcCommandTest : BaseCommandTest() {

    lateinit var cmd: WcCommand

    @BeforeEach
    override fun init() {
        super.init()
        cmd = WcCommand()
    }

    @Test
    fun testWc() {
        val fileName = "test.txt"
        assertEquals(0, cmd.execute(fileName))

        val filePath = sessionContext.currentDirectory / fileName
        val size = filePath.fileSize()
        val words = filePath.readText().split("\\s+".toRegex()).size
        val lines = filePath.readLines().size
        assertEquals("\t$lines\t$words\t$size\n", output.toByteArray().decodeToString())
    }

}