package lu.kolja.duradisp.registry

import lu.kolja.duradisp.logic.DisplayStore
import lu.kolja.duradisp.logic.xmod.AEDisplayStore
import lu.kolja.duradisp.logic.xmod.GTDisplayStore
import lu.kolja.duradisp.misc.Constants
import net.minecraftforge.common.capabilities.ForgeCapabilities

open class DisplayRegistry {
    companion object {
        val DS = mutableListOf<DisplayStore.StoreSupplier>()
    }

    init {
        if (Constants.GTCEU) {
            register {
                if (it.item.getCreatorModId(it) != "gtceu") return@register null
                val gtceu = GTDisplayStore.from(it)
                return@register gtceu?.register()
            }
        }
        if (Constants.AE2) {
            register {
                val ae2 = AEDisplayStore.from(it)
                return@register ae2?.register()
            }
        }
        register {
            if (Constants.AE2 && AEDisplayStore.from(it) != null) return@register null
            val energyStorage = it.getCapability(ForgeCapabilities.ENERGY)
            if (energyStorage.isPresent) {
                val energyStorage1 = energyStorage.orElseThrow { NullPointerException() }
                return@register listOf(DisplayStore(
                    energyStorage1.energyStored.toDouble(),
                    (energyStorage1.energyStored / energyStorage1.maxEnergyStored).toDouble(),
                    it.item.getBarColor(it),
                    it.isBarVisible
                ))
            }
            return@register null
        }
        register {
            if (Constants.GTCEU && GTDisplayStore.from(it) != null) return@register null
            if (it.isDamageableItem) {
                val damage = it.damageValue.toDouble()
                val maxDamage = it.maxDamage.toDouble()
                val percentage = (maxDamage - damage) / maxDamage
                return@register listOf(DisplayStore(maxDamage - damage, percentage, it.barColor, true))
            }
            return@register null
        }
    }

    fun register(supplier: DisplayStore.StoreSupplier) = DS.add(supplier)
}