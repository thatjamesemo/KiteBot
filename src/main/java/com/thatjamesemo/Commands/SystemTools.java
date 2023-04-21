package com.thatjamesemo.Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SystemTools extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        EmbedBuilder embedBuilder = new EmbedBuilder();

        if (event.getName().equals("ping")) {
            event.deferReply().setEphemeral(false).queue();

            embedBuilder.setTitle("Ping!");
            embedBuilder.setDescription("You have pinged me! My current ping is " + String.valueOf(event.getJDA().getGatewayPing()) + "ms.");

            event.getHook().sendMessageEmbeds(embedBuilder.build()).setEphemeral(false).queue();
        }
    }
}
