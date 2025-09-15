package lu.kolja.duradisp.mixins;

import com.gregtechceu.gtceu.api.item.IGTTool;
import com.gregtechceu.gtceu.client.renderer.item.ToolChargeBarRenderer;
import lu.kolja.duradisp.ModConfig;
import lu.kolja.duradisp.enums.DisplayState;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ToolChargeBarRenderer.class, remap = false)
public class MixinToolChargeBarRenderer {
    @Inject(
            method = "renderBarsTool",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void renderBarsTool(GuiGraphics graphics, IGTTool tool, ItemStack stack, int xPosition, int yPosition, CallbackInfo ci) {
        if (duradisp$shouldRender()) ci.cancel();
    }

    @Inject(
            method = "renderElectricBar",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void renderElectricBar(GuiGraphics graphics, long charge, long maxCharge, int xPosition, int yPosition, boolean renderedDurability, CallbackInfoReturnable<Boolean> cir) {
        if (duradisp$shouldRender()) cir.setReturnValue(false);
    }

    @Unique
    private static boolean duradisp$shouldRender() {
        return ModConfig.getDisplayState() != DisplayState.DISABLED;
    }
}
