package lu.kolja.duradisp.logic.xmod

import com.gregtechceu.gtceu.api.capability.GTCapabilityHelper
import com.gregtechceu.gtceu.api.capability.IElectricItem
import com.gregtechceu.gtceu.api.item.IComponentItem
import com.gregtechceu.gtceu.api.item.IGTTool
import com.gregtechceu.gtceu.api.item.component.IDurabilityBar
import com.gregtechceu.gtceu.common.item.TurbineRotorBehaviour
import lu.kolja.duradisp.logic.DisplayStore
import lu.kolja.duradisp.misc.Constants
import net.minecraft.world.item.ItemStack

data class GTDisplayStore(val stack: ItemStack) {
    companion object{
        fun from(stack: ItemStack): GTDisplayStore? {
            return if (stack.item is IGTTool
                || stack.item is IComponentItem
                || stack.item is IElectricItem)
                GTDisplayStore(stack) else null
        }
    }

    fun register(): List<DisplayStore>? {
        val displayStore = mutableListOf<DisplayStore>()
        val item = stack.item
        when (item) {
            is IElectricItem -> {
                val charge = item.charge.toDouble()
                val maxCharge = item.maxCharge.toDouble()
                val percentage = charge / maxCharge
                displayStore.add(DisplayStore(charge, percentage, Constants.BAR_ENERGY_COLOR, true))
                return displayStore
            }
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
                val turbineBehavior = TurbineRotorBehaviour.getBehaviour(stack)
                turbineBehavior?.let {
                    val damage = turbineBehavior.getDamage(stack).toDouble()
                    val maxDamage = turbineBehavior.getMaxDurability(stack).toDouble()
                    val percentage = (maxDamage - damage) / maxDamage
                    displayStore.add(DisplayStore(maxDamage - damage, percentage, Constants.BAR_DURABILITY_COLOR, true))
                    return displayStore // early return because yes
                }
                bar?.let {
                    val damage = stack.damageValue.toDouble()
                    val maxDamage = stack.maxDamage.toDouble()
                    val percentage = (maxDamage - damage) / maxDamage
                    displayStore.add(DisplayStore(maxDamage - damage, percentage, Constants.BAR_DURABILITY_COLOR, true))
                }
                val electricItem = GTCapabilityHelper.getElectricItem(stack)
                electricItem?.let {
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