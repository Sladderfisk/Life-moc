package com.sladderfisk.lifemod.rendering.client.render;

import net.minecraft.util.Mth;

public class DeathCompassWobble {

    public double rotation;

    private double deltaRotation;

    private long lastUpdateTick;

    boolean shouldUpdate( long gameTime ) {

        return lastUpdateTick != gameTime;
    }

    void update( long gameTime, double oldRotation ) {

        lastUpdateTick = gameTime;
        double rotationDelta = oldRotation - rotation;
        rotationDelta = Mth.positiveModulo( rotationDelta + 0.5D, 1.0D ) - 0.5D;
        deltaRotation += rotationDelta * 0.1D;
        deltaRotation *= 0.8D;
        rotation = Mth.positiveModulo( rotation + deltaRotation, 1.0D );
    }
}
