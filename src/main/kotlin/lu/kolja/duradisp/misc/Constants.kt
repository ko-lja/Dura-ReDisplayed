package lu.kolja.duradisp.misc

import net.minecraft.util.FastColor
import net.minecraftforge.fml.ModList

object Constants {
    val GTCEU = ModList.get().isLoaded("gtceu")

    val BAR_ENERGY_COLOR = FastColor.ARGB32.color(255, 47, 155, 237)
    val BAR_DURABILITY_COLOR = FastColor.ARGB32.color(255, 37, 199, 4)
}