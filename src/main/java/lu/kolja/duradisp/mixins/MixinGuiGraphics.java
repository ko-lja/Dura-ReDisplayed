package lu.kolja.duradisp.mixins;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import lu.kolja.duradisp.enums.DisplayState;
import lu.kolja.duradisp.misc.KeyMappings;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = GuiGraphics.class, remap = false)
public class MixinGuiGraphics {
    @ModifyExpressionValue(
            method = "renderItemDecorations(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;IILjava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;isBarVisible()Z"
            )
    )
    private boolean isBarVisible(boolean isVisible) {
        return KeyMappings.INSTANCE.getState() == DisplayState.DISABLED;
    }
}