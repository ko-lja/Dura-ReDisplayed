package lu.kolja.duradisp.logic

import net.minecraft.world.item.ItemStack

data class DisplayStore(val amount: Double, val percentage: Double, val color: Int, val isActive: Boolean) {
    @FunctionalInterface
    fun interface StoreSupplier {
        fun get(stack: ItemStack): List<DisplayStore>?
    }
}