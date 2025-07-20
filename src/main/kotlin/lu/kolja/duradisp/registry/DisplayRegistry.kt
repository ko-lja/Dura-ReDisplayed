package lu.kolja.duradisp.registry

import lu.kolja.duradisp.logic.DisplayStore
import lu.kolja.duradisp.misc.Constants
import lu.kolja.duradisp.registry.xmod.AE2
import lu.kolja.duradisp.registry.xmod.GTCEU
import net.minecraftforge.common.capabilities.ForgeCapabilities
import net.minecraftforge.fml.ModList

open class DisplayRegistry {
    companion object {
        val DS = mutableListOf<DisplayStore.StoreSupplier>()
    }

    init {
        ModList.get().run {
            if (isLoaded("ae2")) AE2()
            if (isLoaded("gtceu")) GTCEU()
        }
        register {
            if (it.isDamageableItem) {
                val damage = it.damageValue.toDouble()
                val maxDamage = it.maxDamage.toDouble()
                val percentage = (maxDamage - damage) / maxDamage
                return@register listOf(DisplayStore(maxDamage - damage, percentage, Constants.BAR_DURABILITY_COLOR, true))
            }
            return@register null
        }
        register {
            val energyStorage = it.getCapability(ForgeCapabilities.ENERGY)
            if (energyStorage.isPresent) {
                val energyStore = energyStorage.orElse(null)
                return@register listOf(DisplayStore(
                    energyStore.energyStored.toDouble(),
                    (energyStore.energyStored / energyStore.maxEnergyStored).toDouble(),
                    it.item.getBarColor(it),
                    it.isBarVisible
                ))
            }
            return@register null
        }
    }

    fun register(supplier: DisplayStore.StoreSupplier) = DS.add(supplier)
}