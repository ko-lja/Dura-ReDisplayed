package lu.kolja.duradisp.misc

import com.mojang.blaze3d.platform.InputConstants
import lu.kolja.duradisp.Duradisp.Companion.MODID
import lu.kolja.duradisp.ModConfig
import lu.kolja.duradisp.enums.DisplayState
import net.minecraft.client.KeyMapping
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.client.event.RegisterKeyMappingsEvent
import net.minecraftforge.event.TickEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import org.lwjgl.glfw.GLFW

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = [Dist.CLIENT])
object KeyMappings {
    const val CATEGORY = "key.categories.$MODID"

    val CLIENT_MAPPINGS by lazy {
        KeyMapping(
            "key.$MODID.switch_bar_display",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_B,
            CATEGORY
        )
    }

    @SubscribeEvent
    fun registerMappings(event: RegisterKeyMappingsEvent) {
        event.register(CLIENT_MAPPINGS)
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = [Dist.CLIENT])
    object ForgeClient {
        @SubscribeEvent
        fun onClientTick(event: TickEvent.ClientTickEvent) {
            if (event.phase != TickEvent.Phase.END) return
            while (CLIENT_MAPPINGS.consumeClick()) {
                ModConfig.setDisplayState(DisplayState.getNext().configVal)
            }
        }
    }
}