package lu.kolja.duradisp.mixins;

import com.gregtechceu.gtceu.client.renderer.item.decorator.GTToolBarRenderer;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = GTToolBarRenderer.class, remap = false)
public class MixinGTToolBarRenderer {
    @Inject(
            method = "render",
            at = @At("HEAD"),
            cancellable = true
    )
    private void render(GuiGraphics guiGraphics, Font font, ItemStack stack, int x, int y, CallbackInfoReturnable<Boolean> cir) {
        cir.cancel();
    }
}