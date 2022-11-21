package ui.inventory.containers.renderers

import com.varabyte.kotter.foundation.text.green
import com.varabyte.kotter.foundation.text.textLine
import com.varabyte.kotter.foundation.text.yellow
import com.varabyte.kotter.runtime.render.RenderScope
import inventory.containers.DefaultContainer
import inventory.items.Item
import ui.inventory.containers.ContainerWithNavigation
import ui.inventory.extend
import ui.inventory.items.ItemRenderStrategy
import ui.inventory.items.renderers.ItemWithAmountRenderer

class DefaultContainerRenderer(
    maxWidth: Int,
    itemRenderStrategy: ItemRenderStrategy,
    private val itemOrdering: Comparator<Item> = compareBy { it.name }
) : ItemsContainerRenderer<DefaultContainer>(maxWidth, itemRenderStrategy) {
    override fun RenderScope.renderData(data: ContainerWithNavigation<DefaultContainer>) {
        val items = data.container
            .getItemsList()

        if (items.isNotEmpty()) {
            items
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
        } else {
            if (data.isActive) {
                green {
                    textLine("> Empty!".extend(maxWidth))
                }
            } else {
                yellow {
                    textLine("Empty!".extend(maxWidth))
                }
            }
        }
    }

}
