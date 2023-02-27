package fr.venodez.luminous.item;

import fr.venodez.luminous.Luminous;
import fr.venodez.luminous.sound.ModSoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Luminous.MOD_ID);
    public static final RegistryObject<Item> SAND_OF_TIME = ITEMS.register("sand_of_time", () -> new Item(new Item.Properties().group(Luminous.GROUP)));
    public static final RegistryObject<Item> MUSIC_DISC_STARLIGHT = ITEMS.register("music_disc_starlight", () -> new MusicDiscItem(1, () -> ModSoundEvents.MUSIC_DISC_STARLIGHT.get(), (new Item.Properties()).maxStackSize(1).group(Luminous.GROUP).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> MUSIC_DISC_MEMENTO = ITEMS.register("music_disc_memento", () -> new MusicDiscItem(1, () -> ModSoundEvents.MUSIC_DISC_MEMENTO.get(), (new Item.Properties()).maxStackSize(1).group(Luminous.GROUP).rarity(Rarity.RARE)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
