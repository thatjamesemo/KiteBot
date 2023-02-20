package com.thatjamesemo.Commands;

import com.thatjamesemo.ConfigFile;
import com.thatjamesemo.getEmoji;
import com.thatjamesemo.guildConfigCreator;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;

public class ShowConfigCommand extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("showconfig")) {
            event.deferReply().setEphemeral(true).queue();
            long guildID = event.getGuild().getIdLong();
            JDA api = event.getJDA();

            ConfigFile configFile = new ConfigFile(guildID);

            EmbedBuilder embedBuilder = new EmbedBuilder();
            Member user = event.getMember();
            assert user != null;
            if (user.hasPermission(Permission.MANAGE_SERVER) || user.hasPermission(Permission.ADMINISTRATOR)) {
                embedBuilder.setTitle("Config options for " + event.getGuild().getName() + ":");
                embedBuilder.setDescription("Display of your configuration options for this server.");


                ArrayList<String> rsvpChannels = (ArrayList<String>) configFile.getOption("rsvp-channel");
                String rsvpYes = getEmoji.getEmoji(api, configFile.getOptionAsLong("rsvp-yes"));
                String rsvpMaybe = getEmoji.getEmoji(api, configFile.getOptionAsLong("rsvp-maybe"));
                String rsvpNo = getEmoji.getEmoji(api, configFile.getOptionAsLong("rsvp-no"));

                StringBuilder rsvpChannel = new StringBuilder();
                for (String s : rsvpChannels) {
                    rsvpChannel.append("<#").append(s).append(">, ");
                }

                embedBuilder.addField("rsvpChannel", "Your RSVP channels is: " + rsvpChannel.toString() + "", true);
                embedBuilder.addField("rsvpYesEmoji", "The emoji used for Yes on RSVP messages: " + rsvpYes, true);
                embedBuilder.addField("rsvpMaybeEmoji", "The emoji used for the Maybe on RSVP messages: " + rsvpMaybe, true);
                embedBuilder.addField("rsvpNoEmoji", "The emoji used for the No on RSVP messages: " + rsvpNo, true);

                event.getHook().sendMessageEmbeds(embedBuilder.build()).queue();
            } else {
                embedBuilder.setTitle("Action Denied");
                embedBuilder.setDescription("You are not permitted to run this action");

                event.getHook().sendMessageEmbeds(embedBuilder.build()).queue();
            }
        } else if (event.getName().equals("createconfig")){
            event.deferReply().setEphemeral(true).queue();

            EmbedBuilder embedBuilder = new EmbedBuilder();
            Member user = event.getMember();
            assert user != null;
            if (user.hasPermission(Permission.MANAGE_SERVER) || user.hasPermission(Permission.ADMINISTRATOR)) {
                embedBuilder.setTitle("Creating config for your server");
                embedBuilder.setDescription("We are now creating your config for your server. It is a good idea to run `/showconfig` afterwards to know what the values are.");

                new guildConfigCreator().createConfig(event);

                event.getHook().sendMessageEmbeds(embedBuilder.build()).queue();

            } else {
                embedBuilder.setTitle("Action Denied");
                embedBuilder.setDescription("You are not permitted to run this action");

                event.getHook().sendMessageEmbeds(embedBuilder.build()).queue();
            }
        } else if (event.getName().equals("addrsvpchannel")) {
            event.deferReply().setEphemeral(true).queue();
            long guildID = event.getGuild().getIdLong();

            ConfigFile configFile = new ConfigFile(guildID);

            EmbedBuilder embedBuilder = new EmbedBuilder();
            Member user = event.getMember();
            assert user != null;
            if (user.hasPermission(Permission.MANAGE_SERVER) || user.hasPermission(Permission.ADMINISTRATOR)) {
                embedBuilder.setTitle("Addring a RSVP channel for " + event.getGuild().getName() + ":");
                embedBuilder.setDescription("Adding a RSVP channel: <#" + event.getOption("rsvp-channel").getAsLong() + ">.");

                ArrayList<String> rsvpChannels = (ArrayList<String>) configFile.getOption("rsvp-channel");
                rsvpChannels.add(String.valueOf(event.getOption("rsvp-channel").getAsLong()));
                configFile.setOption("rsvp-channel", rsvpChannels);

                event.getHook().sendMessageEmbeds(embedBuilder.build()).queue();

            } else {
                embedBuilder.setTitle("Action Denied");
                embedBuilder.setDescription("You are not permitted to run this action");

                event.getHook().sendMessageEmbeds(embedBuilder.build()).queue();
            }
        } else if (event.getName().equals("removersvpchannel")) {
            event.deferReply().setEphemeral(true).queue();
            long guildID = event.getGuild().getIdLong();

            ConfigFile configFile = new ConfigFile(guildID);

            EmbedBuilder embedBuilder = new EmbedBuilder();
            Member user = event.getMember();
            assert user != null;
            if (user.hasPermission(Permission.MANAGE_SERVER) || user.hasPermission(Permission.ADMINISTRATOR)) {
                String rsvpChannelToRemove = String.valueOf(event.getOption("rsvp-channel").getAsLong());

                ArrayList<String> rsvpChannels = (ArrayList<String>) configFile.getOption("rsvp-channel");
                if (rsvpChannels.contains(rsvpChannelToRemove)) {
                    rsvpChannels.remove(rsvpChannelToRemove);
                    embedBuilder.setTitle("RSVP Channel removed for: " + event.getGuild().getName() + "");
                    embedBuilder.setDescription("The channel <#" + rsvpChannelToRemove + "> has been removed from your RSVP Channels");
                } else {
                    embedBuilder.setTitle("Action Not Possible");
                    embedBuilder.setDescription("The channel <#" + rsvpChannelToRemove + "> is not in your registered RSVP Channels");

                    event.getHook().sendMessageEmbeds(embedBuilder.build()).queue();
                }
            } else {
                embedBuilder.setTitle("Action Denied");
                embedBuilder.setDescription("You are not permitted to run this action");

                event.getHook().sendMessageEmbeds(embedBuilder.build()).queue();
            }

        } else if (event.getName().equals("setrsvpyes")){
            event.deferReply().setEphemeral(true).queue();
            long guildID = event.getGuild().getIdLong();

            ConfigFile configFile = new ConfigFile(guildID);

            EmbedBuilder embedBuilder = new EmbedBuilder();
            Member user = event.getMember();
            assert user != null;
            if (user.hasPermission(Permission.MANAGE_SERVER) || user.hasPermission(Permission.ADMINISTRATOR)) {
                embedBuilder.setTitle("Chaning the RSVP Yes emoji for " + event.getGuild().getName() + ":");

                String oldEmoji = getEmoji.getEmoji(event.getJDA(), configFile.getOptionAsLong("rsvp-yes"));
                String newEmoji = getEmoji.getEmoji(event.getJDA(), event.getOption("newemojiid").getAsLong());
                embedBuilder.setDescription("Changing your RSVP Yes reaction from " + oldEmoji + " to " + newEmoji);

                configFile.setOption("rsvp-yes", String.valueOf(event.getOption("newemojiid").getAsLong()));

                event.getHook().sendMessageEmbeds(embedBuilder.build()).queue();
            } else {
                embedBuilder.setTitle("Action Denied");
                embedBuilder.setDescription("You are not permitted to run this action");

                event.getHook().sendMessageEmbeds(embedBuilder.build()).queue();
            }
        } else if (event.getName().equals("setrsvpmaybe")){
            event.deferReply().setEphemeral(true).queue();
            long guildID = event.getGuild().getIdLong();

            ConfigFile configFile = new ConfigFile(guildID);

            EmbedBuilder embedBuilder = new EmbedBuilder();
            Member user = event.getMember();
            assert user != null;
            if (user.hasPermission(Permission.MANAGE_SERVER) || user.hasPermission(Permission.ADMINISTRATOR)) {
                embedBuilder.setTitle("Chaning the RSVP Maybe emoji for " + event.getGuild().getName() + ":");
                String oldEmoji = getEmoji.getEmoji(event.getJDA(), configFile.getOptionAsLong("rsvp-maybe"));
                String newEmoji = getEmoji.getEmoji(event.getJDA(), event.getOption("newemojiid").getAsLong());
                embedBuilder.setDescription("Changing your RSVP Maybe reaction from " + oldEmoji + " to " + newEmoji);

                configFile.setOption("rsvp-maybe", String.valueOf(event.getOption("newemojiid").getAsLong()));

                event.getHook().sendMessageEmbeds(embedBuilder.build()).queue();
            } else {
                embedBuilder.setTitle("Action Denied");
                embedBuilder.setDescription("You are not permitted to run this action");

                event.getHook().sendMessageEmbeds(embedBuilder.build()).queue();
            }
        } else if (event.getName().equals("setrsvpno")){
            event.deferReply().setEphemeral(true).queue();
            long guildID = event.getGuild().getIdLong();

            ConfigFile configFile = new ConfigFile(guildID);

            EmbedBuilder embedBuilder = new EmbedBuilder();
            Member user = event.getMember();
            assert user != null;
            if (user.hasPermission(Permission.MANAGE_SERVER) || user.hasPermission(Permission.ADMINISTRATOR)) {
                embedBuilder.setTitle("Chaning the RSVP No emoji for " + event.getGuild().getName() + ":");
                String oldEmoji = getEmoji.getEmoji(event.getJDA(), configFile.getOptionAsLong("rsvp-no"));
                String newEmoji = getEmoji.getEmoji(event.getJDA(), event.getOption("newemojiid").getAsLong());
                embedBuilder.setDescription("Changing your RSVP No reaction from " + oldEmoji + " to " + newEmoji);

                configFile.setOption("rsvp-no", String.valueOf(event.getOption("newemojiid").getAsLong()));

                event.getHook().sendMessageEmbeds(embedBuilder.build()).queue();
            } else {
                embedBuilder.setTitle("Action Denied");
                embedBuilder.setDescription("You are not permitted to run this action");

                event.getHook().sendMessageEmbeds(embedBuilder.build()).queue();
            }
        }
    }
}
