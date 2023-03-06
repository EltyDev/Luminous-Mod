package fr.venodez.luminous.enchantment;

import fr.venodez.luminous.Luminous;
import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEnchantments {

    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, Luminous.MOD_ID);

    public static final RegistryObject<Enchantment> EXPANSION = ENCHANTMENTS.register("expansion", () ->
        new ExpansionEnchantment(Enchantment.Rarity.VERY_RARE));

    public static void register(IEventBus eventBus) {
        ENCHANTMENTS.register(eventBus);
    }

}
