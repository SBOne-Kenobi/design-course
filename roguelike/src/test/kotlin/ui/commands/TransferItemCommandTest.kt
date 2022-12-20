package ui.commands

import inventory.containers.MutableItemsContainer
import inventory.items.storage.Water
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import ui.inventory.containers.ContainerWithNavigation
import kotlin.test.assertTrue

class TransferItemCommandTest {

    @Test
    fun testTransfer() {
        val from = mock(MutableItemsContainer::class.java)
        val to = mock(MutableItemsContainer::class.java)
        val navigation = mock(ContainerWithNavigation::class.java)

        var removed = false
        var added = false

        val item = Water
        `when`(navigation.getCurrentItem()).thenReturn(item)
        `when`(from.removeItem(item, 1)).then {
            removed = true
            true
        }
        `when`(to.addItem(item, 1)).then {
            added = true
            item
        }

        val command = TransferItemCommand(from, to, navigation)
        command.execute()
        assertTrue(removed)
        assertTrue(added)
    }

}