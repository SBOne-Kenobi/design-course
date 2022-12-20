package ui

import controls.Key
import controls.UserEvent
import controls.UserEventListener
import controls.UserKeyEvent
import engine.Position
import entity.models.User
import kotlin.math.max
import launcher.Settings
import ui.commands.CreateFromMagicPotCommand
import ui.commands.PutOnEquipmentCommand
import ui.commands.TakeOffEquipmentCommand
import ui.commands.TransferItemCommand
import ui.entities.UserBasedViewNavigation
import ui.inventory.containers.DefaultContainerWithNavigation
import ui.inventory.containers.ManyContainersWithNavigation
import ui.inventory.containers.UserEquipmentWithNavigation

/**
 * Class contains the global navigation information on UI.
 */
class NavigationContext(private val user: User) : UserEventListener {

    private var containersNavigation: ManyContainersWithNavigation? = null

    private var viewNavigation: UserBasedViewNavigation? = null

    /**
     * Get the user's inventory navigation.
     */
    fun getUserInventoryWithNavigation(): ManyContainersWithNavigation {
        if (containersNavigation == null) {
            containersNavigation = ManyContainersWithNavigation(
                listOf(
                    UserEquipmentWithNavigation(user.inventory.equipment).apply {
                        enterCommand = TakeOffEquipmentCommand(user, this)
                    },
                    DefaultContainerWithNavigation(user.inventory.storage).apply {
                        enterCommand = PutOnEquipmentCommand(user, this)
                        spaceCommand = TransferItemCommand(user.inventory.storage, user.inventory.pot, this)
                    },
                    DefaultContainerWithNavigation(user.inventory.pot).apply {
                        enterCommand = CreateFromMagicPotCommand(user)
                        spaceCommand = TransferItemCommand(user.inventory.pot, user.inventory.storage, this)
                    }
                ),
                currentContainerPosition = 1
            )
        }
        return containersNavigation!!
    }

    /**
     * Get view navigation that's based on player's position.
     */
    fun getViewNavigation(): UserBasedViewNavigation {
        if (viewNavigation == null) {
            viewNavigation = UserBasedViewNavigation(
                user.gameObject.position,
                user.gameObject.position - Position(Settings.gameMapViewWidth / 2, Settings.gameMapViewHeight / 2)
            )
        }
        return viewNavigation!!.apply {
            this.userPosition = user.gameObject.position
        }
    }

    private fun ManyContainersWithNavigation.fixPositions() {
        currentContainerPosition = currentContainerPosition.coerceIn(0, max(containers.size - 1, 0))
        containers.forEach {
            it.currentItemPosition = it.currentItemPosition.coerceIn(0, max(it.values.size - 1, 0))
        }
    }

    override fun onEvent(event: UserEvent) {
        val navigation = containersNavigation ?: return
        val currentNav = navigation.containers[navigation.currentContainerPosition]
        when (event) {
            is UserKeyEvent -> when (event.key) {
                Key.Left -> {
                    navigation.currentContainerPosition = navigation.currentContainerPosition - 1
                }

                Key.Right -> {
                    navigation.currentContainerPosition = navigation.currentContainerPosition + 1
                }

                Key.Up -> {
                    currentNav.currentItemPosition = currentNav.currentItemPosition - 1
                }

                Key.Down -> {
                    currentNav.currentItemPosition = currentNav.currentItemPosition + 1
                }

                Key.Enter -> currentNav.enterCommand?.execute()

                Key.Space -> currentNav.spaceCommand?.execute()

                else -> {}
            }
        }
        navigation.fixPositions()
    }
}