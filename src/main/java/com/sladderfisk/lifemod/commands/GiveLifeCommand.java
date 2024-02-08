package com.sladderfisk.lifemod.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.MessageArgument;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.OutgoingChatMessage;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.ScoreAccess;
import net.minecraft.world.scores.ScoreHolder;
import net.minecraft.world.scores.Scoreboard;

public class GiveLifeCommand {

    public GiveLifeCommand() {
    }

    public static void register(CommandDispatcher<CommandSourceStack> commandDispatcher){
        LiteralCommandNode<CommandSourceStack> commandNode = commandDispatcher.register((LiteralArgumentBuilder) Commands.literal("givelife").then(Commands.argument("target", EntityArgument.player()).executes((command) -> {

            ServerPlayer player = EntityArgument.getPlayer(command, "target");

            if (player == null) return 1;
            //if (player == command.getSource().getPlayer()) return 0;

            SetLife(player, 1);

            WhisperToPlayer(command.getSource().getPlayer(), "Test");

            SetLife(command.getSource().getPlayer(), -1);



            return 0;
        })));
    }

    private static void SetLife(ServerPlayer player, int val){

        Scoreboard scoreboard = player.getScoreboard();
        Objective objective = scoreboard.getObjective("Lives");
        ScoreAccess score = scoreboard.getOrCreatePlayerScore(player, objective);
        score.set(score.get() + val);
    }

    private static void WhisperToPlayer(ServerPlayer player, String mes){


        OutgoingChatMessage message = OutgoingChatMessage.create(PlayerChatMessage.system(mes));
        ChatType.Bound type = ChatType.bind(ChatType.MSG_COMMAND_INCOMING, player);

        player.sendChatMessage(message, true, type);
        player.sendChatMessage(message, false, type);
    }
}
