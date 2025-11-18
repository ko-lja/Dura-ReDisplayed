package lu.kolja.duradisp

import lu.kolja.duradisp.registry.DisplayRegistry
import lu.kolja.duradisp.render.DisplayRenderer
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.client.event.RegisterItemDecorationsEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.config.ModConfig
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.kotlinforforge.forge.registerConfig

@Mod(Duradisp.MODID)
class Duradisp {
    companion object {
        const val MODID = "duradisp"
    }

    init {
        DisplayRegistry()
        registerConfig(ModConfig.Type.CLIENT, DuradispConfig.spec)
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = [Dist.CLIENT])
    object Client {
        @SubscribeEvent
        fun onRegisterItemDecorations(event: RegisterItemDecorationsEvent) {
            for (item in ForgeRegistries.ITEMS) event.register(item, DisplayRenderer())
        }
    }
}