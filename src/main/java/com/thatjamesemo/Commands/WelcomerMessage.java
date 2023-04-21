package com.thatjamesemo.Commands;

import com.thatjamesemo.ConfigFile;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;

public class WelcomerMessage extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        long guildId = event.getGuild().getIdLong();
        ConfigFile configFile = new ConfigFile(guildId);

        EmbedBuilder embedBuilder = new EmbedBuilder();

        Member member = event.getMember();
        System.out.println(member.getNickname());
        ArrayList<String> welcomeChannels = (ArrayList<String>) configFile.getOption("welcome-channels");
        String welcomeMessage = configFile.getOptionAsString("welcome-message");

        welcomeMessage = welcomeMessage.replace("{member}", "<@" + member.getIdLong() + ">");

        embedBuilder.setDescription(welcomeMessage);
        embedBuilder.setTitle("Welcome, " + event.getMember().getNickname());

        for (String channel : welcomeChannels) {
            TextChannel channel1 = event.getGuild().getTextChannelById(channel);
            channel1.sendMessageEmbeds(embedBuilder.build()).queue();
        }
    }
}
