package com.thatjamesemo;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.util.ArrayList;

public class guildConfigCreator extends ListenerAdapter {
    @Override
    public void onGuildJoin(GuildJoinEvent event) {
        long serverId = event.getGuild().getIdLong(); // Get the ID of the Discord guild to create the file with.
        ConfigFile configFile = new ConfigFile(serverId);

        configFile.setOption("mod-channel-id", "123456789");
        configFile.setOption("rsvp-channel", new ArrayList<>()); // Modified from "0" to "new ArrayList<>()"
        configFile.setOption("rsvp-yes", "1076101499949699162");
        configFile.setOption("rsvp-no", "1076101495143018516");
        configFile.setOption("rsvp-maybe", "1076101493343649855");
        configFile.setOption("rsvp-messages", new ArrayList<>());
        configFile.setOption("welcome-channels", new ArrayList<>());
        configFile.setOption("welcome-message", "Welcome to the server {member}!");

        JDA api = event.getJDA();

        Guild server = api.getGuildById(event.getGuild().getIdLong());
        if (server != null) { // Makes sure that the discord bot is registered and working inside this guild.
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
                            .addOption(OptionType.STRING, "newemojiid", "The number value in the results from \\:emoji_name:", true),
                    Commands.slash("addwelcomechannel", "Adds a channel to the welcomer")
                            .addOption(OptionType.CHANNEL, "channel", "The channel you want to add", true),
                    Commands.slash("removewelcomechannel", "Removes a welcome channel")
                            .addOption(OptionType.CHANNEL, "channel", "The channel you want to remove", true),
                    Commands.slash("setwelcomemessage", "Sets the welcome message for your server.")

            ).queue();
        }
    }

    public void createConfig(SlashCommandInteractionEvent event) {
        long serverId = event.getGuild().getIdLong(); // Get the ID of the Discord guild to create the file with.
        ConfigFile configFile = new ConfigFile(serverId);

        configFile.setOption("mod-channel-id", "123456789");
        configFile.setOption("rsvp-channel", new ArrayList<>());
        configFile.setOption("rsvp-yes", "1076101499949699162");
        configFile.setOption("rsvp-no", "1076101495143018516");
        configFile.setOption("rsvp-maybe", "1076101493343649855");
        configFile.setOption("rsvp-messages", new ArrayList<>());
        configFile.setOption("welcome-channels", new ArrayList<>());
        configFile.setOption("welcome-message", "Welcome to the server {member}!");


        JDA api = event.getJDA();

        Guild server = api.getGuildById(event.getGuild().getIdLong());
        if (server != null) { // Makes sure that the discord bot is registered and working inside this guild.
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
                            .addOption(OptionType.STRING, "newemojiid", "The number value in the results from \\:emoji_name:", true),
                    Commands.slash("addwelcomechannel", "Adds a channel to the welcomer")
                            .addOption(OptionType.CHANNEL, "channel", "The channel you want to add", true),
                    Commands.slash("removewelcomechannel", "Removes a welcome channel")
                            .addOption(OptionType.CHANNEL, "channel", "The channel you want to remove", true),
                    Commands.slash("setwelcomemessage", "Sets the welcome message for your server.")

            ).queue();
        }
    }

}
