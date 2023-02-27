package fr.venodez.luminous.sound;

import fr.venodez.luminous.Luminous;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModSoundEvents {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Luminous.MOD_ID);

    public static final RegistryObject<SoundEvent> MUSIC_DISC_STARLIGHT = register("music_disc.starlight");
    public static final RegistryObject<SoundEvent> MUSIC_DISC_MEMENTO = register("music_disc.memento");

    private static RegistryObject<SoundEvent> register(String key) {
        return SOUND_EVENTS.register(key, () -> new SoundEvent(new ResourceLocation(Luminous.MOD_ID, key)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }

}
