package com.sladderfisk.lifemod.events;

import com.sladderfisk.lifemod.commands.GiveLifeCommand;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

@Mod.EventBusSubscriber(modid = "life_mod")
public class ModEvents {

    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event){
        GiveLifeCommand.register(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());
    }
}
