package ui.inventory.containers

import inventory.containers.ItemsContainer

data class ManyContainersWithNavigation(
    val containers: List<ContainerWithNavigation<ItemsContainer>>,
    var currentContainerPosition: Int = 0
)
