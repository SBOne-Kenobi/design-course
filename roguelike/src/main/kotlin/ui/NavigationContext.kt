package ui

import controls.Key
import controls.UserEvent
import controls.UserEventListener
import controls.UserKeyEvent
import engine.Position
import entity.models.User
import inventory.UserInventory
import inventory.containers.MagicPot
import inventory.containers.UserEquipment
import inventory.containers.UserStorage
import inventory.items.equipments.AbstractEquipment
import kotlin.math.max
import launcher.Settings
import ui.entities.UserBasedViewNavigation
import ui.inventory.containers.DefaultContainerWithNavigation
import ui.inventory.containers.ManyContainersWithNavigation
import ui.inventory.containers.UserEquipmentWithNavigation

class NavigationContext(private val user: User) : UserEventListener {

    private var containersNavigation: ManyContainersWithNavigation? = null

    private var viewNavigation: UserBasedViewNavigation? = null

    fun getUserInventoryWithNavigation(userInventory: UserInventory): ManyContainersWithNavigation {
        if (containersNavigation == null) {
            containersNavigation = ManyContainersWithNavigation(
                listOf(
                    UserEquipmentWithNavigation(userInventory.equipment),
                    DefaultContainerWithNavigation(userInventory.storage),
                    DefaultContainerWithNavigation(userInventory.pot)
                ),
                currentContainerPosition = 1
            )
        }
        return containersNavigation!!
    }

    private fun ManyContainersWithNavigation.getEquipment(): UserEquipmentWithNavigation =
        containers[0] as UserEquipmentWithNavigation

    private fun ManyContainersWithNavigation.getStorage(): DefaultContainerWithNavigation =
        containers[1] as DefaultContainerWithNavigation

    private fun ManyContainersWithNavigation.getPot(): DefaultContainerWithNavigation =
        containers[2] as DefaultContainerWithNavigation

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
                    if (currentNav.container is UserEquipment) {
                        currentNav.currentItemPosition = currentNav.currentItemPosition + 1
                    } else {
                        currentNav.currentItemPosition = currentNav.currentItemPosition + 1
                    }
                }

                Key.Enter -> {
                    when (currentNav.container) {
                        is UserEquipment -> {
                            currentNav.getCurrentItem()?.let {
                                user.takeOffEquipment(it as AbstractEquipment)
                            }
                        }

                        is MagicPot -> {
                            user.createFromMagicPot()
                        }

                        is UserStorage -> {
                            currentNav.getCurrentItem()?.let {
                                if (it is AbstractEquipment) {
                                    user.putOnEquipment(it)
                                }
                            }
                        }
                    }
                }

                Key.Space -> {
                    when (currentNav.container) {
                        is MagicPot -> {
                            currentNav.getCurrentItem()?.let { item ->
                                user.inventory.pot.removeItem(item)
                                user.inventory.storage.addItem(item)
                            }
                        }

                        is UserStorage -> {
                            currentNav.getCurrentItem()?.let { item ->
                                user.inventory.storage.removeItem(item)
                                user.inventory.pot.addItem(item)
                            }
                        }
                    }
                }

                else -> {}
            }
        }
        navigation.fixPositions()
    }
}