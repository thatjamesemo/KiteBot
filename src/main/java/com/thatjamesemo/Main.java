package com.thatjamesemo;

import com.thatjamesemo.Commands.ConfigCommands;
import com.thatjamesemo.Commands.PingCommand;
import com.thatjamesemo.Commands.RsvpMessage;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        String token = args[0];
        JDA api = JDABuilder.createDefault(token).enableIntents(GatewayIntent.MESSAGE_CONTENT).build();
        api.awaitReady();

        api.addEventListener(new PingCommand());
        api.addEventListener(new RsvpMessage());
        api.addEventListener(new ConfigCommands());

        for (Guild i : api.getGuilds()) {
            Guild server = api.getGuildById(i.getIdLong());
            if (server != null) {
                server.updateCommands().addCommands(
                        Commands.slash("ping", "Used to test if the discord bot is online."),
                        Commands.slash("showconfig", "Shows the configurations for your discord guild."),
                        Commands.slash("createconfig", "Creates and adds commands to your server for configuration."),
                        Commands.slash("addrsvpchannel", "Sets the RSVP channel for the server.")
                                .addOption(OptionType.CHANNEL, "rsvp-channel", "The channel that you will be using for RSVP.", true),
                        Commands.slash("removersvpchannel", "Removes a RSVP channel")
                                .addOption(OptionType.CHANNEL, "rsvp-channel", "The channel that you will be removing", true),
                        Commands.slash("setrsvpyes", "Set the yes reaction for the RSVP channel")
                                .addOption(OptionType.STRING, "newemojiid", "The number value in the results from \\:emoji_name:", true),
                        Commands.slash("setrsvpmaybe", "Set the yes reaction for the RSVP channel")
                                .addOption(OptionType.STRING, "newemojiid", "The number value in the results from \\:emoji_name:", true),
                        Commands.slash("setrsvpno", "Set the yes reaction for the RSVP channel")
                                .addOption(OptionType.STRING, "newemojiid", "The number value in the results from \\:emoji_name:", true)
                ).queue();
            }
        }
    }
}