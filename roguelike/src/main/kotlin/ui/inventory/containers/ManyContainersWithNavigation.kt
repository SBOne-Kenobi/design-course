package ui.inventory.containers

import inventory.containers.ItemsContainer

/**
 * Navigation across many containers.
 */
data class ManyContainersWithNavigation(
    val containers: List<ContainerWithNavigation<ItemsContainer, Any>>,
    var currentContainerPosition: Int = 0
)
