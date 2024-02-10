package com.sladderfisk.lifemod.rendering.client.render;

import com.sladderfisk.lifemod.items.DeathCompassItem;
import com.sladderfisk.lifemod.items.ModItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@OnlyIn(Dist.CLIENT)
public class DeathCompassVisuals {

    public static DeathCompassItem compass;

    public static void register(FMLClientSetupEvent event){

        event.enqueueWork(() ->{
            ItemProperties.register(ModItems.DEATH_COMPASS.get(),
                    new ResourceLocation("angle"), new DeathCompassPropertyFunction());
        });
    }
}
