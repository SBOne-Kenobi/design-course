package ui.inventory.containers

import com.varabyte.kotter.runtime.render.RenderScope
import inventory.containers.DefaultContainer
import inventory.containers.ItemsContainer
import inventory.containers.UserEquipment
import ui.ConsoleRenderer
import ui.inventory.items.ItemRenderStrategy

class ContainerRendererFactory {

    private class ItemsContainerRendererWrapper<T: ItemsContainer>(
        private val delegate: ItemsContainerRenderer<T>,
    ) : ConsoleRenderer<ContainerWithNavigation<ItemsContainer>>() {
        @Suppress("UNCHECKED_CAST")
        override fun RenderScope.renderData(data: ContainerWithNavigation<ItemsContainer>) {
            delegate.run {
                renderData(data as ContainerWithNavigation<T>)
            }
        }
    }

    fun getContainerRenderer(
        container: ItemsContainer,
        maxWidth: Int,
        itemRendererStrategy: ItemRenderStrategy
    ): ConsoleRenderer<ContainerWithNavigation<ItemsContainer>>? =
        when (container) {
            is DefaultContainer ->
                ItemsContainerRendererWrapper(DefaultContainerRenderer(maxWidth, itemRendererStrategy))
            is UserEquipment ->
                ItemsContainerRendererWrapper(UserEquipmentRenderer(maxWidth, itemRendererStrategy))
            else ->
                null
        }
}
