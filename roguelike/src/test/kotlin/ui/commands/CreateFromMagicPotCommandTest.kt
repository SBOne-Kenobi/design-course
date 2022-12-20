package ui.commands

import entity.models.User
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import kotlin.test.assertTrue

class CreateFromMagicPotCommandTest {

    @Test
    fun testCommand() {
        var created = false
        val user = mock(User::class.java)
        `when`(user.createFromMagicPot()).then {
            created = true
            null
        }
        val command = CreateFromMagicPotCommand(user)
        command.execute()
        assertTrue(created)
    }

}