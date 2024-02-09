package com.sladderfisk.lifemod.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.LiteralMessage;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ComponentArgument;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.MessageArgument;
import net.minecraft.network.chat.*;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.PlainTextContents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.scores.*;

import java.awt.*;

public class GiveLifeCommand {

    public GiveLifeCommand() {
    }

    public static void register(CommandDispatcher<CommandSourceStack> commandDispatcher){
        LiteralCommandNode<CommandSourceStack> commandNode = commandDispatcher.register((LiteralArgumentBuilder) Commands.literal("givelife").then(Commands.argument("target", EntityArgument.player()).executes((command) -> {

            ServerPlayer player = EntityArgument.getPlayer(command, "target");
            ServerPlayer sender = command.getSource().getPlayer();

            if (player == null) return 1;

            if (player == command.getSource().getPlayer())
                return SendMessage(sender, "You can't give life to yourself!!!");

            if (!CanGiveLife(sender, 1))
                return SendMessage(sender, "You don't have enought lives!");

            if (!CanGiveLife(player, 0))
                return SendMessage(sender, "You can't give life to a dead person. Dummy.");

            SetLife(player, 1);
            SetLife(sender, -1);

            SendMessage(player, sender.getName().getString() + " sent you a life!");


            return 0;
        })));
    }

    private static boolean CanGiveLife(ServerPlayer player, int minLives){

        Scoreboard scoreBoard = player.getScoreboard();
        Objective objective = scoreBoard.getObjective("Lives");
        ScoreAccess score = scoreBoard.getOrCreatePlayerScore(player, objective);
        int lives = score.get();

        return lives > minLives;
    }

    private static int SendMessage(ServerPlayer player, String mes){

        PlayerChatMessage chatMessage = PlayerChatMessage.unsigned(player.getUUID(), mes);

        Component comp = Component.literal(mes);
        player.sendSystemMessage(comp);

        return 0;
    }

    private static void SetLife(ServerPlayer player, int val){

        Scoreboard scoreboard = player.getScoreboard();
        Objective objective = scoreboard.getObjective("Lives");
        ScoreAccess score = scoreboard.getOrCreatePlayerScore(player, objective);
        score.set(score.get() + val);
    }
}
