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

    private var BUCKET_RENDER: ForgeConfigSpec.BooleanValue = builder
        .comment("Render the fullness of the bucket")
        .define("bucketRender", false)

    private var OUTLINE: ForgeConfigSpec.BooleanValue = builder
        .comment("Render a black outline around the text")
        .define("outline", true)

    val spec: ForgeConfigSpec = builder.build()

    private lateinit var displayState: DisplayState
    var bucketRender = false
    var outline = true

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
            bucketRender = BUCKET_RENDER.get()
            outline = OUTLINE.get()
        }
    }
}