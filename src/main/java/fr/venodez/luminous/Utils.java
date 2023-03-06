package fr.venodez.luminous;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.vertex.MatrixApplyingVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerController;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.RenderTypeBuffers;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.world.World;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Utils {

    public static Direction getDirection(PlayerEntity player) {
        Vector2f direction = player.getPitchYaw();
        double x = direction.x;
        double y = Math.abs(direction.y);
        if (x <= -45)
            return (Direction.UP);
        if (x >= 45)
            return (Direction.DOWN);
        if (y >= 135 && y < 225)
            return (Direction.NORTH);
        if (y >= 45 && y < 135)
            return (Direction.WEST);
        if (y >= 225 && y < 315)
            return (Direction.EAST);
        return (Direction.SOUTH);
    }

    public static Boolean hasEnchant(ItemStack itemStack, Class<? extends Enchantment> enchant) {
        Map<Enchantment, Integer> enchants = EnchantmentHelper.getEnchantments(itemStack);
        for (Enchantment enchantment : enchants.keySet()) {
            if (enchantment.getClass().isAssignableFrom(enchant))
                return (true);
        }
        return (false);
    }

    public static Object getField(Class<?> classe, String fieldName, String obfName, Object instance) {
        try {
            Field field = classe.getDeclaredField(fieldName);
            field.setAccessible(true);
            return (field.get(instance));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            try {
                Field field = classe.getDeclaredField(obfName);
                field.setAccessible(true);
                return (field.get(instance));
            } catch (NoSuchFieldException | IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public static Method getMethod(Class<?> classe, String methodName, String obfName, Class<?>... parametersType) {
        try {
            Method method = classe.getDeclaredMethod(methodName, parametersType);
            method.setAccessible(true);
            return (method);
        } catch (NoSuchMethodException e) {
            try {
                Method method = classe.getDeclaredMethod(obfName, parametersType);
                method.setAccessible(true);
                return (method);
            } catch (NoSuchMethodException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    // Code adapted from CoFHCore
    // https://github.com/CoFH/CoFHCore/blob/1.16.5/src/main/java/cofh/core/event/AreaEffectClientEvents.java
    public static void drawBlocksAnimation(MatrixStack matrixStack, RenderTypeBuffers renderTypeTextures, List<BlockPos> blocks, World world, double x, double y, double z) {
        PlayerController controller = Minecraft.getInstance().playerController;
        if (controller == null || !controller.getIsHittingBlock())
            return;
        float curBlockDamageMP = (float) Utils.getField(PlayerController.class, "curBlockDamageMP", "field_78770_f", controller);
        int progress = (int) (curBlockDamageMP * 10.0F) - 1;
        if (progress < 0 || progress > 10)
            return;
        progress = Math.min(progress + 1, 9);
        BlockRendererDispatcher dispatcher = Minecraft.getInstance().getBlockRendererDispatcher();
        IVertexBuilder vertexBuilder = renderTypeTextures.getCrumblingBufferSource().getBuffer(ModelBakery.DESTROY_RENDER_TYPES.get(progress));
        for (BlockPos block : blocks) {
            matrixStack.push();
            matrixStack.translate((double) block.getX() - x, (double) block.getY() - y, (double) block.getZ() - z);
            MatrixStack.Entry entry = matrixStack.getLast();
            IVertexBuilder matrixBuilder = new MatrixApplyingVertexBuilder(vertexBuilder, entry.getMatrix(), entry.getNormal());
            dispatcher.renderBlockDamage(world.getBlockState(block), block, world, matrixStack, matrixBuilder);
            matrixStack.pop();
        }
    }
}
