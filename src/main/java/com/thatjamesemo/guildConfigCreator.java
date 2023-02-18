package com.thatjamesemo;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;

public class guildConfigCreator extends ListenerAdapter {
    @Override
    public void onGuildJoin(GuildJoinEvent event) {
        long serverId = event.getGuild().getIdLong(); // Get the ID of the Discord guild to create the file with.
        ConfigFile configFile = new ConfigFile(serverId);

        configFile.setOption("mod-channel-id", "123456789");
        configFile.setOption("rsvp-channel", "0");
        configFile.setOption("rsvp-yes", "1076101499949699162");
        configFile.setOption("rsvp-no", "1076101495143018516");
        configFile.setOption("rsvp-maybe", "1076101493343649855");
        configFile.setOption("rsvp-messages", new ArrayList<>());

        JDA api = event.getJDA();

        Guild server = api.getGuildById(event.getGuild().getIdLong());
        if (server != null) { // Makes sure that the discord bot is registered and working inside this guild.
            server.upsertCommand("ping", "Used to test if the discord bot is online.").queue();
            server.upsertCommand("showconfig", "Shows the configurations for your discord guild.").queue();
            server.upsertCommand("createconfig", "Creates and adds commands to your server for configuration.").queue();
            server.upsertCommand("setrsvpchannel", "Sets the RSVP channel for the server.").addOption(OptionType.CHANNEL, "rsvp-channel", "The channel that you will be using for RSVP.", true).queue();
            server.upsertCommand("setrsvpyes", "Set the yes reaction for the RSVP channel").addOption(OptionType.STRING, "newemojiid", "The number value in the results from \\:emoji_name:", true).queue();
            server.upsertCommand("setrsvpmaybe", "Set the yes reaction for the RSVP channel").addOption(OptionType.STRING, "newemojiid", "The number value in the results from \\:emoji_name:", true).queue();
            server.upsertCommand("setrsvpno", "Set the yes reaction for the RSVP channel").addOption(OptionType.STRING, "newemojiid", "The number value in the results from \\:emoji_name:", true).queue();
            server.upsertCommand("update", "Sends an update message to all connected servers with a channel set up.")
                    .addOption(OptionType.STRING, "content", "The content of the update message", true)
                    .addOption(OptionType.STRING, "title", "The title of the update message.", false)
                    .queue();
        }
    }

    public void createConfig(SlashCommandInteractionEvent event) {
        long serverId = event.getGuild().getIdLong(); // Get the ID of the Discord guild to create the file with.
        ConfigFile configFile = new ConfigFile(serverId);

        configFile.setOption("mod-channel-id", "123456789");
        configFile.setOption("rsvp-channel", "0");
        configFile.setOption("rsvp-yes", "1076101499949699162");
        configFile.setOption("rsvp-no", "1076101495143018516");
        configFile.setOption("rsvp-maybe", "1076101493343649855");
        configFile.setOption("rsvp-messages", new ArrayList<>());



        JDA api = event.getJDA();

        Guild server = api.getGuildById(event.getGuild().getIdLong());
        if (server != null) { // Makes sure that the discord bot is registered and working inside this guild.
            server.upsertCommand("ping", "Used to test if the discord bot is online.").queue();
            server.upsertCommand("showconfig", "Shows the configurations for your discord guild.").queue();
            server.upsertCommand("createconfig", "Creates and adds commands to your server for configuration.").queue();
            server.upsertCommand("setrsvpchannel", "Sets the RSVP channel for the server.").addOption(OptionType.CHANNEL, "rsvp-channel", "The channel that you will be using for RSVP.", true).queue();
            server.upsertCommand("setrsvpyes", "Set the yes reaction for the RSVP channel").addOption(OptionType.STRING, "newemojiid", "The number value in the results from \\:emoji_name:", true).queue();
            server.upsertCommand("setrsvpmaybe", "Set the yes reaction for the RSVP channel").addOption(OptionType.STRING, "newemojiid", "The number value in the results from \\:emoji_name:", true).queue();
            server.upsertCommand("setrsvpno", "Set the yes reaction for the RSVP channel").addOption(OptionType.STRING, "newemojiid", "The number value in the results from \\:emoji_name:", true).queue();
            server.upsertCommand("update", "Sends an update message to all connected servers with a channel set up.")
                    .addOption(OptionType.STRING, "content", "The content of the update message", true)
                    .addOption(OptionType.STRING, "title", "The title of the update message.", false)
                    .queue();
        }
    }

}
