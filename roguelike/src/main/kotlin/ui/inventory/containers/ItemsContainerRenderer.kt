package ui.inventory.containers

import inventory.containers.ItemsContainer
import ui.ConsoleRenderer
import ui.inventory.items.ItemRenderStrategy
import ui.inventory.items.ItemRenderer
import ui.inventory.items.SelectedItemRenderer

abstract class ItemsContainerRenderer<in T : ItemsContainer>(
    var maxWidth: Int,
    val itemRenderStrategy: ItemRenderStrategy,
) : ConsoleRenderer<ContainerWithNavigation<T>>()

fun <T: ItemsContainer> ItemRenderer.wrapRespectingNavigation(
    data: ContainerWithNavigation<T>,
    position: Int,
): ItemRenderer {
    return if (position == data.currentItemPosition) {
        SelectedItemRenderer(data.isActive, this)
    } else {
        this
    }
}
