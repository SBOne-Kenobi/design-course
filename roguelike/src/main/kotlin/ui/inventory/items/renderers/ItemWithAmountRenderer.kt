package ui.inventory.items.renderers

import com.varabyte.kotter.foundation.text.text
import com.varabyte.kotter.runtime.render.RenderScope
import inventory.items.Item
import ui.inventory.extend
import ui.inventory.items.ItemRendererWrapper

class ItemWithAmountRenderer(
    delegate: ItemRenderer,
    var amount: Int = 0
) : ItemRendererWrapper(delegate) {
    private val amountSize = 3

    override fun RenderScope.renderData(data: Item) {
        val amountString = amount.toString().extend(amountSize)
        require(maxWidth >= 3 + amountSize)
        delegate.withMaxWidth(maxWidth - 3 - amountSize) {
            renderData(data)
        }
        text(" - $amountString")
    }
}
