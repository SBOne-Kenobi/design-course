package ui.inventory.containers.renderers

import com.varabyte.kotter.foundation.text.green
import com.varabyte.kotter.foundation.text.text
import com.varabyte.kotter.foundation.text.textLine
import com.varabyte.kotter.foundation.text.yellow
import com.varabyte.kotter.runtime.render.RenderScope
import inventory.containers.UserEquipment
import inventory.items.EquipmentType
import ui.inventory.containers.ContainerWithNavigation
import ui.inventory.extend
import ui.inventory.items.ItemRenderStrategy

/**
 * Renderer of [UserEquipment].
 */
class UserEquipmentRenderer(
    maxWidth: Int,
    itemRenderStrategy: ItemRenderStrategy,
) : ItemsContainerRenderer<UserEquipment, EquipmentType>(maxWidth, itemRenderStrategy) {

    private val equipmentLength = 6

    private val textMaxWidth: Int
        get() = maxWidth - equipmentLength - 3

    override fun RenderScope.renderData(data: ContainerWithNavigation<UserEquipment, EquipmentType>) {
        data.values.forEachIndexed { index, type ->
            if (index < data.currentBeginPosition) return@forEachIndexed
            scopedState {
                text("${type.name.extend(equipmentLength)} - ")
                val item = data.container.getEquipment(type)
                if (item == null) {
                    val text = if (index == data.currentItemPosition) {
                        if (data.isActive) {
                            green()
                        } else {
                            yellow()
                        }
                        "> None"
                    } else {
                        "None"
                    }
                    text(text.extend(textMaxWidth))
                } else {
                    itemRenderStrategy
                        .getItemRenderer(item, textMaxWidth)
                        .wrapRespectingNavigation(data, index)
                        .run {
                            renderData(item)
                        }
                }
                textLine()
            }
        }
    }
}