package ui.inventory.containers

import com.varabyte.kotter.runtime.render.RenderScope
import inventory.containers.DefaultContainer
import inventory.containers.ItemsContainer
import inventory.containers.UserEquipment
import ui.ConsoleRenderer
import ui.inventory.containers.renderers.DefaultContainerRenderer
import ui.inventory.containers.renderers.ItemsContainerRenderer
import ui.inventory.containers.renderers.UserEquipmentRenderer
import ui.inventory.items.ItemRenderStrategy

class ContainerRendererFactory {

    private class ItemsContainerRendererWrapper<T: ItemsContainer, E>(
        private val delegate: ItemsContainerRenderer<T, E>,
    ) : ConsoleRenderer<ContainerWithNavigation<ItemsContainer, Any>>() {
        @Suppress("UNCHECKED_CAST")
        override fun RenderScope.renderData(data: ContainerWithNavigation<ItemsContainer, Any>) {
            delegate.run {
                renderData(data as ContainerWithNavigation<T, E>)
            }
        }
    }

    fun getContainerRenderer(
        container: ItemsContainer,
        maxWidth: Int,
        itemRendererStrategy: ItemRenderStrategy
    ): ConsoleRenderer<ContainerWithNavigation<ItemsContainer, Any>>? =
        when (container) {
            is DefaultContainer ->
                ItemsContainerRendererWrapper(DefaultContainerRenderer(maxWidth, itemRendererStrategy))
            is UserEquipment ->
                ItemsContainerRendererWrapper(UserEquipmentRenderer(maxWidth, itemRendererStrategy))
            else ->
                null
        }
}
