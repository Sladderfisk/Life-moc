package com.sladderfisk.lifemod.rendering.client.render;

import com.sladderfisk.lifemod.LifeMod;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static java.util.Objects.hash;

@OnlyIn(Dist.CLIENT)
public class DeathCompassPropertyFunction implements ClampedItemPropertyFunction {

    private final DeathCompassWobble randomWobble = new DeathCompassWobble();
    private final DeathCompassWobble wobble = new DeathCompassWobble();

    @Override
    public float unclampedCall(ItemStack stack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int i) {

        long gameTime = clientLevel.getGameTime();

        if (!(livingEntity instanceof Player) || !((Player) livingEntity).isLocalPlayer() || livingEntity == null ||
                !stack.getOrCreateTag().hasUUID(LifeMod.MODID + "Player_Position")){

            randomWobble.update(gameTime, StrictMath.random());
            return Mth.positiveModulo(
                    (float)( randomWobble.rotation + ( hash( i ) / 2.14748365E9F ) ),
                    1.0F
            );
        }

        int[] storage = stack.getOrCreateTag().getIntArray(LifeMod.MODID + "Player_Position");

        Vec3 pos = new Vec3(storage[0], storage[1], storage[2]);

        double rot = livingEntity.getYRot();

        rot = Mth.positiveModulo(rot / 360, 1);

        double angleTo = getAngleTo(pos, livingEntity) / ((float)Math.PI * 2);
        double rotation;

        if (wobble.shouldUpdate(gameTime)){
            wobble.update(gameTime, .5d - (rot - .25d));
        }

        rotation = angleTo + wobble.rotation;

        return Mth.positiveModulo((float)rotation, 1);
    }

    @OnlyIn( Dist.CLIENT )
    private double getAngleTo(@NotNull Vec3 vec3, @NotNull Entity entity ) {

        return StrictMath.atan2( vec3.z() - entity.getZ(), vec3.x() - entity.getX() );
    }
}
