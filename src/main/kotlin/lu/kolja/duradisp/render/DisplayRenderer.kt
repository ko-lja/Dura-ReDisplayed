package lu.kolja.duradisp.render

import lu.kolja.duradisp.ModConfig
import lu.kolja.duradisp.enums.DisplayState.*
import lu.kolja.duradisp.logic.DisplayStore
import lu.kolja.duradisp.misc.NumberUtil
import lu.kolja.duradisp.registry.DisplayRegistry
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Font
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.world.item.ItemStack
import net.minecraftforge.client.IItemDecorator

class DisplayRenderer: IItemDecorator {
    override fun render(
        guiGraphics: GuiGraphics,
        font: Font,
        stack: ItemStack?,
        xPos: Int,
        yPos: Int
    ): Boolean {
        if (stack == null || stack.isEmpty)
            return false
        return when (ModConfig.getDisplayState()) {
            ENABLED_PERCENTAGE -> {
                renderActual(
                    stack, guiGraphics, font,
                    xPos, yPos
                ) { NumberUtil.formatPercentage(it.percentage) }
            }
            ENABLED_NUMBER -> {
                renderActual(
                    stack, guiGraphics, font,
                    xPos, yPos
                ) { NumberUtil.formatNumber(it.amount) }
            }
            ENABLED_SCIENTIFIC -> {
                renderActual(
                    stack, guiGraphics, font,
                    xPos, yPos
                ) { NumberUtil.formatScientific(it.amount) }
            }
            else -> false
        }
    }

    private fun renderActual(stack: ItemStack, guiGraphics: GuiGraphics, font: Font, xPos: Int, yPos: Int, text: (DisplayStore) -> String): Boolean {
        for (supplier in DisplayRegistry.DS) {
            val displayStore = supplier.get(stack)
            if (displayStore != null && displayStore.isNotEmpty()) {
                for (i in 0..displayStore.size - 1) {
                    val store = displayStore[i]
                    if (store.isActive) {
                        renderText(
                            guiGraphics, font,
                            text(store),
                            xPos, yPos - i * 5, store.color
                        )
                    }
                }
            }
        }
        return false
    }

    fun renderText(graphics: GuiGraphics, font: Font, text: String, xPos: Int, yPos: Int, color: Int) {
        val poseStack = graphics.pose()
        val stringWidth = font.width(text)
        val x = (xPos + 8) * 2 + 1 + stringWidth / 2 - stringWidth
        val y = yPos * 2 + 22
        poseStack.pushPose()
        poseStack.scale(0.5f, 0.5f, 0.5f)
        poseStack.translate(0.0, 0.0, 500.0)
        val bufferSource = Minecraft.getInstance().renderBuffers().bufferSource()
        font.drawInBatch(text, x.toFloat(), y.toFloat(), color, true, poseStack.last().pose(), bufferSource, Font.DisplayMode.NORMAL, 0, 15728880, false)
        bufferSource.endBatch()
        poseStack.popPose()
    }
}