package fr.venodez.luminous.mixin;

import fr.venodez.luminous.CustomCape;
import fr.venodez.luminous.Luminous;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ElytraLayer.class)
public abstract class ElytraLayerMixin<T extends LivingEntity, M extends EntityModel<T>> extends LayerRenderer<T, M> {

    public ElytraLayerMixin(IEntityRenderer<T, M> entityRendererIn) {
        super(entityRendererIn);
    }

    @Redirect(method = "render(Lcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;ILnet/minecraft/entity/LivingEntity;FFFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/layers/ElytraLayer;getElytraTexture(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/LivingEntity;)Lnet/minecraft/util/ResourceLocation;"))
    public ResourceLocation getElytraCustomTexture(ElytraLayer instance, ItemStack stack, T entity) {
        CustomCape cape = Luminous.CAPES.get(entity.getUniqueID());
        if (cape != null)
            return cape.getTexture();
        else
            return instance.getElytraTexture(stack, entity);
    }
}
