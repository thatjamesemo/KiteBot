package com.thatjamesemo;

import com.thatjamesemo.Commands.PingCommand;
import com.thatjamesemo.Commands.RsvpMessage;
import com.thatjamesemo.Commands.ShowConfigCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        String token = args[0];
        JDA api = JDABuilder.createDefault(token).enableIntents(GatewayIntent.MESSAGE_CONTENT).build();
        api.awaitReady();

        api.addEventListener(new PingCommand());
        api.addEventListener(new ShowConfigCommand());
        api.addEventListener(new RsvpMessage());

        for (Guild i : api.getGuilds()) {
            Guild server = api.getGuildById(i.getIdLong());
            if (server != null) {
                server.upsertCommand("ping", "Used to test if the discord bot is online.").queue();
                server.upsertCommand("showconfig", "Shows the configurations for your discord guild.").queue();
                server.upsertCommand("createconfig", "Creates and adds commands to your server for configuration.").queue();
                server.upsertCommand("addrsvpchannel", "Sets the RSVP channel for the server.")
                        .addOption(OptionType.CHANNEL, "rsvp-channel", "The channel that you will be using for RSVP.", true)
                        .queue();
                server.upsertCommand("removersvpchannel", "Removes a RSVP channel")
                        .addOption(OptionType.CHANNEL, "rsvp-channel", "The channel that you will be removing", true)
                        .queue();
                server.upsertCommand("setrsvpyes", "Set the yes reaction for the RSVP channel")
                        .addOption(OptionType.STRING, "newemojiid", "The number value in the results from \\:emoji_name:", true)
                        .queue();
                server.upsertCommand("setrsvpmaybe", "Set the yes reaction for the RSVP channel")
                        .addOption(OptionType.STRING, "newemojiid", "The number value in the results from \\:emoji_name:", true)
                        .queue();
                server.upsertCommand("setrsvpno", "Set the yes reaction for the RSVP channel")
                        .addOption(OptionType.INTEGER, "newemojiid", "The number value in the results from \\:emoji_name:", true)
                        .queue();
//                server.upsertCommand("update", "Sends an update message to all connected servers with a channel set up.")
//                        .addOption(OptionType.STRING, "content", "The content of the update message", true)
//                        .addOption(OptionType.STRING, "title", "The title of the update message.", false)
//                        .queue();
            }
        }
    }
}