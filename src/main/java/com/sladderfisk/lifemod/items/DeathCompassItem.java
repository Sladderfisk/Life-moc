package com.sladderfisk.lifemod.items;

import com.sladderfisk.lifemod.LifeMod;
import net.minecraft.commands.arguments.CompoundTagArgument;
import net.minecraft.commands.arguments.NbtTagArgument;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.ScoreAccess;
import net.minecraft.world.scores.Scoreboard;
import org.apache.logging.log4j.core.jmx.Server;
import org.jetbrains.annotations.NotNull;
import org.joml.Random;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DeathCompassItem extends Item {

    public final String NAME = "death compass";

    public DeathCompassItem(Properties properties) {
        super(properties);
    }


    @Override
    public InteractionResult useOn(UseOnContext p_41427_) {
        return InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {

        player.sendSystemMessage(Component.literal("OvO"));

        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide()){
            CompoundTag storage;

            storage = stack.getOrCreateTag();

            Player targetPlayer = GetPlayer(level.getServer());
            Vec3 playerPos = targetPlayer.position();

            int[] pos = {
                    (int)playerPos.x,
                    (int)playerPos.y,
                    (int)playerPos.z
            };

            player.sendSystemMessage(Component.literal("Found player " + targetPlayer.getName()));

            storage.putIntArray(LifeMod.MODID + "Player_Position", pos);
        }

        return InteractionResultHolder.success(stack);
    }

    private Player GetPlayer(MinecraftServer server){

        PlayerList players = server.getPlayerList();

        Scoreboard scoreboard = server.getScoreboard();
        Objective objective = scoreboard.getObjective("Lives");

        int highestLives = -1;

        List<Player> priorityPlayers = new ArrayList<>() {
        };

        for (Player player : players.getPlayers()){

            ScoreAccess score = scoreboard.getOrCreatePlayerScore(player, objective);
            int playerScore = score.get();

            if (playerScore == highestLives) priorityPlayers.add(player);
            else priorityPlayers.clear();

            highestLives = Math.max(highestLives, playerScore);
        }

        Random ran = new Random();
        return priorityPlayers.get(ran.nextInt(priorityPlayers.size()));
    }
}
