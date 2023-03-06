package fr.venodez.luminous.enchantment;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import fr.venodez.luminous.Luminous;
import fr.venodez.luminous.Utils;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeBuffers;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawHighlightEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ExpansionEnchantment extends Enchantment {
    protected ExpansionEnchantment(Rarity rarityIn) {
        super(rarityIn, EnchantmentType.DIGGER, new EquipmentSlotType[] {});
    }

    public int getMaxLevel() {
        return 3;
    }

    public boolean canApply(ItemStack stack) {
        return stack.getItem() instanceof PickaxeItem || super.canApply(stack);
    }

    @Mod.EventBusSubscriber(modid = Luminous.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ExpansionEquipped {

        @SubscribeEvent
        public static void onBreakFinished(BlockEvent.BreakEvent event) {
            PlayerEntity player = event.getPlayer();
            if (!Utils.hasEnchant(player.getHeldItemMainhand(), ModEnchantments.EXPANSION.get().getClass()))
                return;
            BlockPos pos = event.getPos();
            if (player.world.getBlockState(pos).getMaterial() != Material.ROCK)
                return;
            int lvl = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.EXPANSION.get(), player.getHeldItemMainhand());
            List<BlockPos> blocks = getAreaBlocks(pos, Utils.getDirection(player), player.world, lvl);
            for (BlockPos block : blocks)
                player.world.destroyBlock(block, !player.isCreative(), player);
        }

        private static List<BlockPos> getAreaBlocks(BlockPos targetBlock, Direction direction, World world, int lvl) {
            List<BlockPos> blocks = new ArrayList<>();
            for (int i = 0; i < 3 + 2 * (lvl - 1); ++i) {
                for (int j = 0; j < 3 + 2 * (lvl - 1); ++j) {
                    BlockPos block;
                    if (direction == Direction.UP || direction == Direction.DOWN)
                        block = new BlockPos(targetBlock.getX() - lvl + i, targetBlock.getY(), targetBlock.getZ() + lvl - j);
                    else if (direction == Direction.NORTH || direction == Direction.SOUTH)
                        block = new BlockPos(targetBlock.getX() - lvl + i, targetBlock.getY() + lvl - j, targetBlock.getZ());
                    else
                        block = new BlockPos(targetBlock.getX(), targetBlock.getY() + lvl - j, targetBlock.getZ() - lvl + i);
                    if (world.getBlockState(block).getMaterial() == Material.ROCK)
                        blocks.add(block);
                }
            }
            return (blocks);
        }


        // Code adapted from CoFHCore
        // https://github.com/CoFH/CoFHCore/blob/1.16.5/src/main/java/cofh/core/event/AreaEffectClientEvents.java
        @SubscribeEvent (priority = EventPriority.LOW)
        public static void onBlockHighlight(DrawHighlightEvent.HighlightBlock event) throws InvocationTargetException, IllegalAccessException {
            if (event.isCanceled())
                return;
            PlayerEntity player = Minecraft.getInstance().player;
            if (player == null)
                return;
            ItemStack stack = player.getHeldItemMainhand();
            if (!Utils.hasEnchant(stack, ModEnchantments.EXPANSION.get().getClass()))
                return;
            ActiveRenderInfo renderInfo = Minecraft.getInstance().gameRenderer.getActiveRenderInfo();
            BlockPos pos = event.getTarget().getPos();
            MatrixStack matrixStack = event.getMatrix();
            int lvl = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.EXPANSION.get(), stack);
            WorldRenderer worldRenderer = event.getContext();
            Method method = Utils.getMethod(WorldRenderer.class, "drawSelectionBox", "func_228429_a_", MatrixStack.class, IVertexBuilder.class, Entity.class, double.class, double.class, double.class, BlockPos.class, BlockState.class);
            method.setAccessible(true);
            Vector3d vector3d = renderInfo.getProjectedView();
            double x = vector3d.getX();
            double y = vector3d.getY();
            double z = vector3d.getZ();
            RenderTypeBuffers renderTypeTextures = (RenderTypeBuffers) Utils.getField(WorldRenderer.class, "renderTypeTextures", "field_228415_m_", worldRenderer);
            Direction direction = Utils.getDirection(player);
            World world = player.world;
            List<BlockPos> blocks = getAreaBlocks(pos, direction, world, lvl);
            matrixStack.push();
            for (BlockPos block : blocks) {
                if (world.getWorldBorder().contains(block))
                    method.invoke(worldRenderer, matrixStack, renderTypeTextures.getBufferSource().getBuffer(RenderType.getLines()), renderInfo.getRenderViewEntity(), x, y, z, block, world.getBlockState(block));
            }
            matrixStack.pop();
            Utils.drawBlocksAnimation(matrixStack, renderTypeTextures, blocks, world, x, y, z);
        }
    }

}
