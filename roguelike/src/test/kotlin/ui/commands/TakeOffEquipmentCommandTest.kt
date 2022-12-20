package ui.commands

import entity.models.User
import inventory.items.equipments.body.Armor
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import ui.inventory.containers.ContainerWithNavigation
import kotlin.test.assertTrue

class TakeOffEquipmentCommandTest {

    @Test
    fun testTakeOff() {
        val user = Mockito.mock(User::class.java)
        val navigation = Mockito.mock(ContainerWithNavigation::class.java)


        var right = false
        val item = Armor
        Mockito.`when`(navigation.getCurrentItem()).thenReturn(item)
        Mockito.`when`(user.takeOffEquipment(item)).then {
            right = item == it.arguments[0]
            null
        }

        val command = TakeOffEquipmentCommand(user, navigation)
        command.execute()
        assertTrue(right)
    }

}