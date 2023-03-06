package fr.venodez.luminous;

import net.minecraft.client.Minecraft;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = Luminous.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class Events {

    private static int tick = 0;

    @SubscribeEvent
    public static void onTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            ++tick;
            for (Map.Entry<UUID, CustomCape> entry : Luminous.CAPES.entrySet()) {
                assert Minecraft.getInstance().world != null;
                if (Minecraft.getInstance().world.getPlayerByUuid(entry.getKey()) == null)
                    continue;
                CustomCape cape = entry.getValue();
                int deltaTick = cape.getDeltaTick();
                if (deltaTick != -1 && tick % deltaTick == 0)
                    cape.tick();
            }
        }
    }

}
