package fr.venodez.luminous.recipe;

import com.haoict.tiab.common.registries.ItemRegistry;
import fr.venodez.luminous.item.ModItems;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class TimeAdderRecipe extends SpecialRecipe {

    private int nbSand = 0;
    private int bottleIndex = -1;

    public TimeAdderRecipe(ResourceLocation idIn) {
        super(idIn);
    }

    @Override
    public boolean matches(CraftingInventory inv, World worldIn) {
        nbSand = 0;
        bottleIndex = -1;
        int nbBottle = 0;
        for (int i = 0; i < inv.getSizeInventory(); ++i) {
            ItemStack stack = inv.getStackInSlot(i);
            if (stack.getItem() != ModItems.SAND_OF_TIME.get()) {
                if (stack.getItem() == ItemRegistry.BOTTLE.get() && nbBottle == 0) {
                    bottleIndex = i;
                    ++nbBottle;
                } else if (stack.getItem() != Items.AIR){
                    System.out.println(stack.getItem());
                    nbSand = 0;
                    bottleIndex = -1;
                    return (false);
                }
            } else
                ++nbSand;
        }
        return nbSand != 0;
    }

    @Override
    public ItemStack getCraftingResult(CraftingInventory inv) {
        if (bottleIndex == -1)
            return Items.AIR.getDefaultInstance();
        ItemStack stack = inv.getStackInSlot(bottleIndex).copy();
        CompoundNBT nbtTagCompound = stack.getOrCreateChildTag("timeData");
        int time = nbtTagCompound.getInt("storedTime");
        nbtTagCompound.putInt("storedTime", time + 20 * 60 * 10 * nbSand);
        return stack;
    }

    @Override
    public boolean canFit(int width, int height) {
        return (width * height >= 2);
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipeSerializer.SPECIAL_TIMEADDER_SERIALIZER.get();
    }
}
