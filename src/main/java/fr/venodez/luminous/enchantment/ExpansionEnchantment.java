package fr.venodez.luminous.enchantment;

import fr.venodez.luminous.Luminous;
import fr.venodez.luminous.Utils;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

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
            BlockPos pos = event.getPos();
            PlayerEntity player = event.getPlayer();
            if (!Utils.hasEnchant(player.getHeldItemMainhand(), ModEnchantments.EXPANSION.get().getClass()))
                return;
            int lvl = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.EXPANSION.get(), event.getPlayer().getHeldItemMainhand());
            Direction direction = Utils.getDirection(event.getPlayer());
            for (int i = 0; i < 3 + 2 * (lvl - 1); ++i) {
                for (int j = 0; j < 3 + 2 * (lvl - 1); ++j) {
                    BlockPos block = getAreaBlock(pos, lvl, direction, i, j);
                    if (player.world.getBlockState(block).getMaterial() == Material.ROCK)
                        player.world.destroyBlock(block, true, event.getPlayer());
                }
            }
        }

        private static BlockPos getAreaBlock(BlockPos pos, int lvl, Direction direction, int i, int j) {
            if (direction == Direction.UP || direction == Direction.DOWN)
                return new BlockPos(pos.getX() - lvl + i, pos.getY(), pos.getZ() + lvl - j);
            else if (direction == Direction.NORTH || direction == Direction.SOUTH)
                return new BlockPos(pos.getX() - lvl + i, pos.getY() + lvl - j, pos.getZ());
            else
                return new BlockPos(pos.getX(), pos.getY() + lvl - j, pos.getZ() - lvl + i);
        }

        //TODO Display AOE break animation (also if possible, show black outline on each blocks)
        /*@SubscribeEvent
        public static void onBreak(PlayerEvent.HarvestCheck event) {
            PlayerEntity player = event.getPlayer();
            if (!Utils.hasEnchant(player.getHeldItemMainhand(), ModEnchantments.EXPANSION.get().getClass()))
                return;
            RayTraceResult blockResult = player.pick(6.0D, 0.0F, false);
            BlockPos pos = ((BlockRayTraceResult) blockResult).getPos();
            System.out.println(player.world.getBlockState(pos).getBlock());
            int lvl = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.EXPANSION.get(), event.getPlayer().getHeldItemMainhand());
            Direction direction = Utils.getDirection(event.getPlayer());
            for (int i = 0; i < 3 + 2 * (lvl - 1); ++i) {
                for (int j = 0; j < 3 + 2 * (lvl - 1); ++j) {
                    BlockPos block = getAreaBlock(pos, lvl, direction, i, j);
                    if (block == pos)
                        continue;
                    BlockState state = player.getEntityWorld().getBlockState(block);
                }
            }
        }*/
    }
}
