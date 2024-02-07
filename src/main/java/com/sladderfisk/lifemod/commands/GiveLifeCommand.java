package com.sladderfisk.lifemod.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.MessageArgument;
import net.minecraft.network.chat.OutgoingChatMessage;
import net.minecraft.server.level.ServerPlayer;

public class GiveLifeCommand {

    public GiveLifeCommand() {
    }

    public static void register(CommandDispatcher<CommandSourceStack> commandDispatcher){
        LiteralCommandNode<CommandSourceStack> commandNode = commandDispatcher.register((LiteralArgumentBuilder) Commands.literal("givelife").then(Commands.argument("target", EntityArgument.player()).executes((command) -> {

            ServerPlayer player = EntityArgument.getPlayer(command, "target");

            if (player == null) return 1;


            return 0;
        })));
    }

    private static void sendMessage(ServerPlayer target){
        //OutgoingChatMessage message = OutgoingChatMessage.create()
    }
}
