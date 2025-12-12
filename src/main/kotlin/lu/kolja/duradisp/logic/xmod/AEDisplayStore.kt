package lu.kolja.duradisp.logic.xmod

import appeng.api.storage.StorageCells
import appeng.api.storage.cells.ICellWorkbenchItem
import appeng.items.tools.powered.powersink.AEBasePoweredItem
import appeng.me.cells.BasicCellHandler
import lu.kolja.duradisp.logic.DisplayStore
import lu.kolja.duradisp.misc.Constants
import net.minecraft.world.item.ItemStack

data class AEDisplayStore(val stack: ItemStack) {
    companion object{
        fun from(stack: ItemStack): AEDisplayStore? {
            return if (stack.item is AEBasePoweredItem || stack.item is ICellWorkbenchItem) AEDisplayStore(stack) else null
        }
    }

    fun register(): List<DisplayStore>? {
        val displayStore = mutableListOf<DisplayStore>()
        val item = stack.item
        when (item) {
            is AEBasePoweredItem -> {
                val charge = item.getAECurrentPower(stack)
                val maxCharge = item.getAEMaxPower(stack)
                val percentage = charge / maxCharge
                displayStore.add(DisplayStore(charge, percentage, Constants.BAR_ENERGY_COLOR, true))
            }
            is ICellWorkbenchItem -> {
                val handler = StorageCells.getHandler(stack) as? BasicCellHandler
                handler?.let {
                    val inv = handler.getCellInventory(stack, null)!!
                    val usedBytes = inv.usedBytes.toDouble()
                    val totalBytes = inv.totalBytes.toDouble()
                    val percentage = usedBytes / totalBytes
                    displayStore.add(DisplayStore(usedBytes, percentage, Constants.BAR_DURABILITY_COLOR, true))
                }
            }
        }
        return displayStore
    }
}