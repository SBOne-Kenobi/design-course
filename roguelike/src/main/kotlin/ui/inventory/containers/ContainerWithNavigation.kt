package ui.inventory.containers

import inventory.containers.DefaultContainer
import inventory.containers.ItemsContainer
import inventory.containers.UserEquipment
import inventory.items.EquipmentType
import inventory.items.Item

/**
 * Navigation across a container.
 */
abstract class ContainerWithNavigation<out T : ItemsContainer, out E>(
    val container: T,
    var isActive: Boolean = false,
    var currentItemPosition: Int = 0,
    var currentBeginPosition: Int = 0,
) {
    abstract val values: List<E>

    /**
     * Get selected item.
     */
    abstract fun getCurrentItem(): Item?
}

/**
 * Default navigation across [DefaultContainer].
 */
class DefaultContainerWithNavigation(
    container: DefaultContainer,
    isActive: Boolean = false,
    currentItemPosition: Int = 0,
    currentBeginPosition: Int = 0,
) : ContainerWithNavigation<DefaultContainer, Item>(
    container, isActive, currentItemPosition, currentBeginPosition
) {
    override val values: List<Item>
        get() = container.getItemsList().sortedBy { it.name }

    override fun getCurrentItem(): Item? {
        return values.getOrNull(currentItemPosition)
    }

}

/**
 * The [UserEquipment]'s navigation.
 */
class UserEquipmentWithNavigation(
    container: UserEquipment,
    isActive: Boolean = false,
    currentItemPosition: Int = 0,
    currentBeginPosition: Int = 0,
) : ContainerWithNavigation<UserEquipment, EquipmentType>(
    container, isActive, currentItemPosition, currentBeginPosition
) {
    override val values: List<EquipmentType>
        get() = EquipmentType.values()
            .asSequence()
            .filter { it != EquipmentType.None }
            .sortedBy { it.priority }
            .toList()

    override fun getCurrentItem(): Item? {
        return values.getOrNull(currentItemPosition)?.let {
            container.getEquipment(it)
        }
    }
}
