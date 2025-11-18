package lu.kolja.duradisp;

import dev.toma.configuration.Configuration;
import dev.toma.configuration.config.Config;
import dev.toma.configuration.config.Configurable;
import dev.toma.configuration.config.format.ConfigFormats;
import lu.kolja.duradisp.enums.DisplayState;


@Config(id = Duradisp.MODID)
public class ModConfig {
    public static ModConfig INSTANCE;
    private static final Object LOCK = new Object();

    public static void init() {
        synchronized(LOCK) {
            if (INSTANCE == null) {
                INSTANCE = Configuration.registerConfig(ModConfig.class, ConfigFormats.YAML).getConfigInstance();
            }
        }
    }

    @Configurable
    @Configurable.Comment(value = "Format to display durability in (percentage, number, scientific, vanilla)", localize = true)
    public String displayState = "percentage";

    public static void setDisplayState(String newFormat) {
        ModConfig.INSTANCE.displayState = newFormat;
    }

    public static DisplayState getDisplayState() {
        var str = ModConfig.INSTANCE.displayState.toLowerCase().trim();

        return switch (str) {
            case "vanilla" -> DisplayState.DISABLED;
            case "percentage" -> DisplayState.ENABLED_PERCENTAGE;
            case "number" -> DisplayState.ENABLED_NUMBER;
            case "scientific" -> DisplayState.ENABLED_SCIENTIFIC;
            default -> throw new IllegalArgumentException(
                    "Invalid display format! Display state format must be vanilla, percentage, number, or scientific");
        };
    }
}
