package com.sladderfisk.lifemod.items;

import com.sladderfisk.lifemod.LifeMod;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, LifeMod.MODID);

    public static final RegistryObject<Item> DEATH_COMPASS = ITEMS.register("death_compass",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
