package fr.venodez.luminous.item;

import fr.venodez.luminous.Luminous;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Luminous.MOD_ID);
    public static final RegistryObject<Item> SAND_OF_TIME = ITEMS.register("sand_of_time", () -> new Item(new Item.Properties().group(Luminous.GROUP)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
