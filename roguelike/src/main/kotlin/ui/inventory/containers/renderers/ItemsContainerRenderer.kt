package ui.inventory.containers.renderers

import inventory.containers.ItemsContainer
import ui.ConsoleRenderer
import ui.inventory.containers.ContainerWithNavigation
import ui.inventory.items.ItemRenderStrategy
import ui.inventory.items.renderers.ItemRenderer
import ui.inventory.items.renderers.SelectedItemRenderer

/**
 * Renderer of [ItemsContainer].
 */
abstract class ItemsContainerRenderer<in T : ItemsContainer, in E>(
    var maxWidth: Int,
    val itemRenderStrategy: ItemRenderStrategy,
) : ConsoleRenderer<ContainerWithNavigation<T, E>>()

internal fun <T: ItemsContainer, E> ItemRenderer.wrapRespectingNavigation(
    data: ContainerWithNavigation<T, E>,
    position: Int,
): ItemRenderer {
    return if (position == data.currentItemPosition) {
        SelectedItemRenderer(data.isActive, this)
    } else {
        this
    }
}
