package fr.venodez.luminous.recipe;

import fr.venodez.luminous.Luminous;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModRecipeSerializer {
    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZER = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Luminous.MOD_ID);

    public static final RegistryObject<SpecialRecipeSerializer<TimeAdderRecipe>> SPECIAL_TIMEADDER_SERIALIZER = RECIPE_SERIALIZER.register("crafting_special_timeadder",
            () -> new SpecialRecipeSerializer<>(TimeAdderRecipe::new));

    public static void register(IEventBus eventBus) {
        RECIPE_SERIALIZER.register(eventBus);
    }
}
