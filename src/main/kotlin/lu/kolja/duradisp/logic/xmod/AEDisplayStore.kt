package lu.kolja.duradisp.logic.xmod

import appeng.items.tools.powered.powersink.AEBasePoweredItem
import lu.kolja.duradisp.logic.DisplayStore
import lu.kolja.duradisp.misc.Constants
import net.minecraft.world.item.ItemStack

data class AEDisplayStore(val stack: ItemStack) {
    companion object{
        fun from(stack: ItemStack): AEDisplayStore? {
            return if (stack.item is AEBasePoweredItem) AEDisplayStore(stack) else null
        }
    }

    fun register(): List<DisplayStore>? {
        val displayStore = mutableListOf<DisplayStore>()
        val item = stack.item as AEBasePoweredItem
        val charge = item.getAECurrentPower(stack)
        val maxCharge = item.getAEMaxPower(stack)
        val percentage = charge / maxCharge

        displayStore.add(DisplayStore(charge, percentage, Constants.BAR_ENERGY_COLOR, true))
        return displayStore
    }
}