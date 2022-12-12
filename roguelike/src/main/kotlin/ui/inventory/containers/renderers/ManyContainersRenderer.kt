package ui.inventory.containers.renderers

import com.varabyte.kotter.foundation.render.OffscreenCommandRenderer
import com.varabyte.kotter.foundation.render.offscreen
import com.varabyte.kotter.foundation.text.text
import com.varabyte.kotter.foundation.text.textLine
import com.varabyte.kotter.runtime.render.RenderScope
import inventory.containers.ItemsContainer
import inventory.items.Item
import ui.ConsoleRenderer
import ui.calculateViewPosition
import ui.inventory.containers.ContainerRendererFactory
import ui.inventory.containers.ContainerWithNavigation
import ui.inventory.containers.ManyContainersWithNavigation
import ui.inventory.items.ItemRenderStrategy
import ui.inventory.items.renderers.ItemRenderer
import ui.inventory.items.renderers.TextItemRenderer

/**
 * Renderer of several containers.
 */
class ManyContainersRenderer(
    var maxWidth: Int,
    var maxHeight: Int,
    var paddingInline: Int = 5,
    var paddingBlock: Int = 1
) : ConsoleRenderer<ManyContainersWithNavigation>() {
    private val containerRendererFactory = ContainerRendererFactory()
    private val itemRenderStrategy: ItemRenderStrategy =
        object : ItemRenderStrategy {
            override fun getItemRenderer(item: Item, maxWidth: Int): ItemRenderer {
                return TextItemRenderer(maxWidth)
            }
        }

    private fun RenderScope.renderContainer(
        containerWithNavigation: ContainerWithNavigation<ItemsContainer, Any>,
        width: Int,
    ) {
        val containerRenderer = containerRendererFactory.getContainerRenderer(
            containerWithNavigation.container,
            width,
            itemRenderStrategy
        )!!

        containerRenderer.run {
            renderData(containerWithNavigation)
        }
    }

    private fun RenderScope.renderEach(
        renderers: List<Pair<OffscreenCommandRenderer, Int>>,
        delimChar: Char,
        padChar: Char,
        drawAction: RenderScope.(Pair<OffscreenCommandRenderer, Int>) -> Unit
    ) {
        text(delimChar)
        renderers.forEach { item ->
            text(padChar.toString().repeat(paddingInline))
            drawAction(item)
            text(padChar.toString().repeat(paddingInline))
            text(delimChar)
        }
    }

    override fun RenderScope.renderData(data: ManyContainersWithNavigation) {
        var remainingContainers = data.containers.size
        var remainingContentWidth = maxWidth - (remainingContainers + 1) - (remainingContainers * 2 * paddingInline)

        val contentHeight = maxHeight - 2 - 2 * paddingBlock

        val renderers = data.containers.mapIndexed { index, containerWithNavigation ->
            val currentWidth = (remainingContentWidth + remainingContainers - 1) / remainingContainers
            containerWithNavigation.isActive = index == data.currentContainerPosition
            remainingContentWidth -= currentWidth
            remainingContainers -= 1

            containerWithNavigation.currentBeginPosition = calculateViewPosition(
                containerWithNavigation.currentBeginPosition,
                containerWithNavigation.currentItemPosition,
                contentHeight
            )

            val buffer = offscreen {
                renderContainer(containerWithNavigation, currentWidth)
            }
            val renderer = buffer.createRenderer()
            renderer to currentWidth
        }

        renderEach(renderers, '+', '-') { (_, width) ->
            text("-".repeat(width))
        }
        textLine()
        repeat(paddingBlock) {
            renderEach(renderers, '|', ' ') { (_, width) ->
                text(" ".repeat(width))
            }
            textLine()
        }

        repeat(contentHeight) {
            renderEach(renderers, '|', ' ') { (renderer, width) ->
                if (!renderer.renderNextRow()) {
                    text(" ".repeat(width))
                }
            }
            textLine()
        }

        repeat(paddingBlock) {
            renderEach(renderers, '|', ' ') { (_, width) ->
                text(" ".repeat(width))
            }
            textLine()
        }
        renderEach(renderers, '+', '-') { (_, width) ->
            text("-".repeat(width))
        }
    }
}