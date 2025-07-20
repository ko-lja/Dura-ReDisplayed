package lu.kolja.duradisp.render

import lu.kolja.duradisp.enums.DisplayState
import lu.kolja.duradisp.logic.DisplayStore
import lu.kolja.duradisp.misc.KeyMappings
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
        if (stack == null || stack.isEmpty || !stack.isBarVisible) return false
        return when (KeyMappings.state) {
            DisplayState.ENABLED_PERCENTAGE -> {
                renderActual(
                    stack, guiGraphics, font,
                    xPos, yPos
                ) { NumberUtil.formatPercentage(it.percentage) }
            }
            DisplayState.ENABLED_NUMBER -> {
                renderActual(
                    stack, guiGraphics, font,
                    xPos, yPos
                ) { NumberUtil.formatNumber(it.amount) }
            }
            else -> true
        }
    }

    private fun renderActual(stack: ItemStack, guiGraphics: GuiGraphics, font: Font, xPos: Int, yPos: Int, text: (DisplayStore) -> String): Boolean {
        for (supplier in DisplayRegistry.DS) {
            val store = supplier.get(stack)
            if (store != null && store.isNotEmpty()) {
                for (i in 0..store.size) {
                    val store = store[i]
                    if (store.isActive) {
                        renderText(
                            guiGraphics, font,
                            text(store),
                            xPos, yPos - i, store.color
                        )
                    }
                }
            }
        }
        return true
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