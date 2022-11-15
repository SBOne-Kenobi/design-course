package ui.inventory.containers

import com.varabyte.kotter.foundation.text.textLine
import com.varabyte.kotter.runtime.render.RenderScope
import inventory.containers.DefaultContainer
import inventory.items.Item
import ui.inventory.items.ItemRenderStrategy
import ui.inventory.items.ItemWithAmountRenderer

class DefaultContainerRenderer(
    maxWidth: Int,
    itemRenderStrategy: ItemRenderStrategy,
    private val itemOrdering: Comparator<Item> = compareBy { it.name }
) : ItemsContainerRenderer<DefaultContainer>(maxWidth, itemRenderStrategy) {
    override fun RenderScope.renderData(data: ContainerWithNavigation<DefaultContainer>) {
        data.container
            .getItemsList()
            .asSequence()
            .sortedWith(itemOrdering)
            .forEachIndexed { index, item ->
                if (index < data.currentBeginPosition) return@forEachIndexed
                ItemWithAmountRenderer(
                    itemRenderStrategy.getItemRenderer(item, maxWidth),
                    data.container.getItemAmount(item)
                ).wrapRespectingNavigation(
                    data, index
                ).run {
                    scopedState {
                        renderData(item)
                        textLine()
                    }
                }
            }
    }

}
