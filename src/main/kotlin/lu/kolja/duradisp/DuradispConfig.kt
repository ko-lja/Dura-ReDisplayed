package lu.kolja.duradisp

import lu.kolja.duradisp.enums.DisplayState
import net.minecraftforge.common.ForgeConfigSpec
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.config.ModConfigEvent

@Mod.EventBusSubscriber(modid = Duradisp.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
object DuradispConfig {
    private val builder = ForgeConfigSpec.Builder()

    private var DISPLAY_STATE: ForgeConfigSpec.EnumValue<DisplayState> = builder
        .comment("Format to display durability/energy in")
        .defineEnum("displayState", DisplayState.DISABLED)

    val spec: ForgeConfigSpec = builder.build()

    private lateinit var displayState: DisplayState

    @JvmStatic
    fun getDisplayState() = displayState
    fun setDisplayState(displayState: DisplayState) {
        this.displayState = displayState
        DISPLAY_STATE.set(displayState)
        spec.save()
    }

    @SubscribeEvent
    fun onLoad(event: ModConfigEvent) {
        event.apply {
            displayState = DISPLAY_STATE.get()
            DisplayState.index = displayState.ordinal
        }
    }
}