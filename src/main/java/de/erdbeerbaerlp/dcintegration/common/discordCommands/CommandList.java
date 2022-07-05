package de.erdbeerbaerlp.dcintegration.common.discordCommands;

import de.erdbeerbaerlp.dcintegration.common.storage.Localization;
import de.erdbeerbaerlp.dcintegration.common.util.ServerInterface.PlayerData;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

import static de.erdbeerbaerlp.dcintegration.common.util.Variables.discord_instance;


public class CommandList extends DiscordCommand {
    public CommandList() {
        super("list", Localization.instance().commands.descriptions.list);
    }

    @Override
    public void execute(SlashCommandInteractionEvent ev, ReplyCallbackAction reply) {
            final HashMap<UUID, PlayerData> players = discord_instance.srv.getPlayers();
            if (players.isEmpty()) {
                ev.reply("There are no players online...").queue();
                return;
            }
            StringBuilder out = new StringBuilder();
            
            PlayerData[] afkPlayers = players.values().stream().filter(player -> player.isAFK()).toArray(PlayerData[]::new);
            PlayerData[] onlinePlayers = players.values().stream().filter(player -> !player.isAFK()).toArray(PlayerData[]::new);

            if (onlinePlayers.length > 0) {
                if (onlinePlayers.length == 1) {
                    out.append(Localization.instance().commands.cmdList_one + "\n```\n");
                } else {
                    out.append(Localization.instance().commands.cmdList_header.replace("%amount%", "" + onlinePlayers.length) + "\n```\n");
                }

                for (PlayerData player: onlinePlayers) {
                    out.append(player.getName() + "\n");
                }
                out.append("```");
            } else {
                out.append("There are no players online..." + "\n");
            }

            if (afkPlayers.length > 0) {
                if (afkPlayers.length == 1) {
                    out.append("There is 1 player AFK:" + "\n```\n");
                } else {
                    out.append("There are %amount% players AFK:".replace("%amount%", "" + afkPlayers.length) + "\n```\n");
                }

                for (PlayerData player: afkPlayers) {
                    out.append(player.getName() + "\n");
                }

                out.append("```");
            }
            reply.setContent(out.toString()).queue();
    }
}
