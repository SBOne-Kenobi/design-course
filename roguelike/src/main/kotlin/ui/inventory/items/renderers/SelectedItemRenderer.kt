package ui.inventory.items.renderers

import com.varabyte.kotter.foundation.text.green
import com.varabyte.kotter.foundation.text.text
import com.varabyte.kotter.foundation.text.yellow
import com.varabyte.kotter.runtime.render.RenderScope
import inventory.items.Item
import ui.inventory.items.ItemRendererWrapper

/**
 * Wrap rendering item with color and choosing symbol '>'.
 */
class SelectedItemRenderer(
    var isActive: Boolean,
    delegate: ItemRenderer
) : ItemRendererWrapper(delegate) {
    override fun RenderScope.renderData(data: Item) {
        require(maxWidth >= 2)
        if (isActive) {
            green()
        } else {
            yellow()
        }
        text("> ")
        delegate.withMaxWidth(maxWidth - 2) {
            renderData(data)
        }
    }
}