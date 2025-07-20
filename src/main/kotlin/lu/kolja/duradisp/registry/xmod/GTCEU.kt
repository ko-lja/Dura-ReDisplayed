package lu.kolja.duradisp.registry.xmod

import lu.kolja.duradisp.logic.xmod.GTDisplayStore
import lu.kolja.duradisp.registry.DisplayRegistry
import java.util.function.Predicate

class GTCEU: DisplayRegistry() {
    init {
        register {
            val gtceu = GTDisplayStore.from(it)
            return@register gtceu?.register()
        }
    }
}