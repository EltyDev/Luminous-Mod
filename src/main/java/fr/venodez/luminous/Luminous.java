package fr.venodez.luminous;

import com.google.common.collect.ImmutableMap;
import fr.venodez.luminous.enchantment.ModEnchantments;
import fr.venodez.luminous.item.ModItems;
import fr.venodez.luminous.recipe.ModRecipeSerializer;
import fr.venodez.luminous.recipe.TimeAdderRecipe;
import fr.venodez.luminous.sound.ModSoundEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("luminous")
public class Luminous {

    // Directly reference a log4j logger.
    public static IRecipeSerializer<TimeAdderRecipe> TIME_ADDER = null;
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "luminous";
    public static final Map<UUID, CustomCape> CAPES = ImmutableMap.of(
        UUID.fromString( "3af9b469-f2a4-4495-9b5c-3d1061f21aa3"), new CustomCape("dev", 0, -1)
    );
    public static final ItemGroup GROUP = new ItemGroup("luminous") {
        @Override
        public ItemStack createIcon() {
            return Items.SHROOMLIGHT.getDefaultInstance();
        }
    };

    public Luminous() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModSoundEvents.register(eventBus);
        ModEnchantments.register(eventBus);
        ModItems.register(eventBus);
        ModRecipeSerializer.register(eventBus);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {}

    private void doClientStuff(final FMLClientSetupEvent event) {}

    private void enqueueIMC(final InterModEnqueueEvent event) {}

    private void processIMC(final InterModProcessEvent event) {}

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {}


}
