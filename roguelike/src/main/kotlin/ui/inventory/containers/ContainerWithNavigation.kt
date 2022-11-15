package ui.inventory.containers

import inventory.containers.ItemsContainer

data class ContainerWithNavigation<out T : ItemsContainer>(
    val container: T,
    var isActive: Boolean = false,
    var currentItemPosition: Int = 0,
    var currentBeginPosition: Int = 0,
)

