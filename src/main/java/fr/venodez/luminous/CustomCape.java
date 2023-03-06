package fr.venodez.luminous;

import net.minecraft.util.ResourceLocation;

public class CustomCape {

    private final int maxTick;
    private final int deltaTick;
    private ResourceLocation texture;
    private final String capeName;
    private int tick;

    public CustomCape(String capeName, int maxTick, int fps) {
        this.maxTick = maxTick;
        this.tick = 0;
        this.capeName = capeName;
        this.texture = new ResourceLocation(Luminous.MOD_ID, "textures/cape/"  + capeName  + "/0.png");
        if (fps == -1)
            this.deltaTick = -1;
        else
            this.deltaTick = 20 / fps;
    }

    public void tick() {
        ++tick;
        if (tick % maxTick == 0) {
            tick = 0;
            texture = new ResourceLocation(Luminous.MOD_ID, "textures/cape/" + capeName + "/0.png");
        } else
            texture = new ResourceLocation(Luminous.MOD_ID, "textures/cape/" + capeName + "/" + tick + ".png");
    }

    public ResourceLocation getTexture() {
        return texture;
    }

    public int getDeltaTick() {
        return deltaTick;
    }

    public String getCapeName() {
        return capeName;
    }
}
