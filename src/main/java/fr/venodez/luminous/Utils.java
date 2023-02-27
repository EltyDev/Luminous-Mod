package fr.venodez.luminous;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector2f;

import java.util.Map;

public class Utils {

    public static Direction getDirection(PlayerEntity player) {
        Vector2f direction = player.getPitchYaw();
        Vector2f absDirection = new Vector2f(Math.abs(direction.x), Math.abs(direction.y));
        if (absDirection.x <= -45)
            return (Direction.UP);
        if (absDirection.x >= 45)
            return (Direction.DOWN);
        if (absDirection.y >= 135 && absDirection.y < 225)
            return (Direction.NORTH);
        if (absDirection.y >= 45 && absDirection.y < 135)
            return (Direction.WEST);
        if (absDirection.y >= 225 && absDirection.y < 315)
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
}
