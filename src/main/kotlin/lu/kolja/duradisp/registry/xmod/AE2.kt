package lu.kolja.duradisp.registry.xmod

import lu.kolja.duradisp.logic.DisplayStore
import lu.kolja.duradisp.registry.DisplayRegistry
import net.minecraftforge.common.capabilities.ForgeCapabilities

class AE2: DisplayRegistry() {
    init {
        register {
            val energyStorage = it.getCapability(ForgeCapabilities.ENERGY)
            if (energyStorage.isPresent) {
                val energyStore = energyStorage.orElse(null)
                return@register listOf(DisplayStore(
                    (energyStore.energyStored / 2).toDouble(),
                    (energyStore.energyStored / energyStore.maxEnergyStored).toDouble(),
                    it.item.getBarColor(it),
                    it.isBarVisible
                ))
            }
            return@register null
        }
    }
}