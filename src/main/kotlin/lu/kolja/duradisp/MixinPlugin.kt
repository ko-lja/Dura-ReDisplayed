package lu.kolja.duradisp

import net.minecraftforge.fml.ModList
import net.minecraftforge.fml.loading.LoadingModList
import net.minecraftforge.fml.loading.moddiscovery.ModInfo
import org.objectweb.asm.tree.ClassNode
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin
import org.spongepowered.asm.mixin.extensibility.IMixinInfo

class MixinPlugin: IMixinConfigPlugin {
    private fun isModLoaded(modId: String) = if (ModList.get() == null) {
        LoadingModList.get().mods.stream()
            .map(ModInfo::getModId)
            .anyMatch { anObject -> modId == anObject }
    } else {
        ModList.get().isLoaded(modId)
    }


    override fun onLoad(p0: String) {}

    override fun getRefMapperConfig() = null

    override fun shouldApplyMixin(targetClassName: String, mixinClassName: String) = when(mixinClassName) {
        "lu.kolja.duradisp.mixins.ToolChargeBarRenderer" -> isModLoaded("gtceu")
        else -> true
    }

    override fun acceptTargets(
        p0: Set<String>,
        p1: Set<String>
    ) {}

    override fun getMixins() = null

    override fun preApply(
        p0: String,
        p1: ClassNode,
        p2: String,
        p3: IMixinInfo
    ) {}

    override fun postApply(
        p0: String,
        p1: ClassNode,
        p2: String,
        p3: IMixinInfo
    ) {}
}