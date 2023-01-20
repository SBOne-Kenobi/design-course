package ui.commands

import entity.models.User
import inventory.items.equipments.body.Armor
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import ui.inventory.containers.ContainerWithNavigation
import kotlin.test.assertTrue

class PutOnEquipmentCommandTest {

    @Test
    fun testPutOn() {
        val user = mock(User::class.java)
        val navigation = mock(ContainerWithNavigation::class.java)


        var right = false
        val item = Armor
        `when`(navigation.getCurrentItem()).thenReturn(item)
        `when`(user.putOnEquipment(item)).then {
            right = item == it.arguments[0]
            null
        }

        val command = PutOnEquipmentCommand(user, navigation)
        command.execute()
        assertTrue(right)
    }

}