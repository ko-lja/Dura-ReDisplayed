package lu.kolja.duradisp.logic.xmod

import com.gregtechceu.gtceu.api.capability.GTCapabilityHelper
import com.gregtechceu.gtceu.api.item.IComponentItem
import com.gregtechceu.gtceu.api.item.IGTTool
import com.gregtechceu.gtceu.api.item.component.IDurabilityBar
import lu.kolja.duradisp.logic.DisplayStore
import lu.kolja.duradisp.misc.Constants
import net.minecraft.world.item.ItemStack

data class GTDisplayStore(val stack: ItemStack) {
    companion object{
        fun from(stack: ItemStack): GTDisplayStore? {
            return if (stack.item is IGTTool || stack.item is IComponentItem) GTDisplayStore(stack) else null
        }
    }

    fun register(): List<DisplayStore>? {
        val displayStore = mutableListOf<DisplayStore>()
        val item = stack.item
        when (item) {
            is IGTTool -> {
                if (stack.isDamageableItem) {
                    val damage = stack.damageValue.toDouble()
                    val maxDamage = stack.maxDamage.toDouble()
                    val percentage = (maxDamage - damage) / maxDamage
                    displayStore.add(DisplayStore(maxDamage - damage, percentage, Constants.BAR_DURABILITY_COLOR, true))
                }
                if (item.isElectric) {
                    val charge = item.getCharge(stack).toDouble()
                    val maxCharge = item.getMaxCharge(stack).toDouble()
                    val percentage = charge / maxCharge
                    displayStore.add(DisplayStore(charge, percentage, Constants.BAR_ENERGY_COLOR, true))
                }
                return displayStore
            }
            is IComponentItem -> {
                var bar: IDurabilityBar? = null
                for (component in item.components)
                    if (component is IDurabilityBar) bar = component
                if (bar != null) {
                    val damage = stack.damageValue.toDouble()
                    val maxDamage = stack.maxDamage.toDouble()
                    val percentage = (maxDamage - damage) / maxDamage
                    displayStore.add(DisplayStore(maxDamage - damage, percentage, Constants.BAR_DURABILITY_COLOR, true))
                }
                val electricItem = GTCapabilityHelper.getElectricItem(stack)
                if (electricItem != null) {
                    val charge = electricItem.charge.toDouble()
                    val maxCharge = electricItem.maxCharge.toDouble()
                    val percentage = charge / maxCharge
                    displayStore.add(DisplayStore(charge, percentage, Constants.BAR_ENERGY_COLOR, true))
                }
                return displayStore
            }
        }
        return displayStore
    }
}