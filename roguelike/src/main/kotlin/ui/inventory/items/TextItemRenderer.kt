package ui.inventory.items

import com.varabyte.kotter.foundation.text.text
import com.varabyte.kotter.runtime.render.RenderScope
import inventory.items.Item
import ui.inventory.extend

class TextItemRenderer(
    maxWidth: Int
) : ItemRenderer(maxWidth) {
    override fun RenderScope.renderData(data: Item) {
        text(data.name.take(maxWidth).extend(maxWidth))
    }
}