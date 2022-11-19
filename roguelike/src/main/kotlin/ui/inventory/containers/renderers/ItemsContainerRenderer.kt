package ui.inventory.containers.renderers

import inventory.containers.ItemsContainer
import ui.ConsoleRenderer
import ui.inventory.containers.ContainerWithNavigation
import ui.inventory.items.ItemRenderStrategy
import ui.inventory.items.renderers.ItemRenderer
import ui.inventory.items.renderers.SelectedItemRenderer

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
