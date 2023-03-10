package com.thatjamesemo.Commands;

import com.thatjamesemo.ConfigFile;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;
import net.dv8tion.jda.api.events.message.MessageDeleteEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;

public class RsvpMessage extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        long guildId = event.getGuild().getIdLong();
        ConfigFile configFile = new ConfigFile(guildId);

        long rsvpYes = configFile.getOptionAsLong("rsvp-yes");
        long rsvpNo = configFile.getOptionAsLong("rsvp-no");
        long rsvpMaybe = configFile.getOptionAsLong("rsvp-maybe");

        ArrayList<String> rsvpChannels = (ArrayList<String>) configFile.getOption("rsvp-channel");

        if (rsvpChannels.contains(String.valueOf(event.getChannel().getIdLong()))) {
            Message message = event.getMessage();
            message.addReaction(event.getJDA().getEmojiById(rsvpYes)).queue();
            message.addReaction(event.getJDA().getEmojiById(rsvpMaybe)).queue();
            message.addReaction(event.getJDA().getEmojiById(rsvpNo)).queue();
            if (message.getContentRaw().length() >= 100) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < 95; i++) {
                    stringBuilder.append(message.getContentRaw().charAt(i));
                }
                stringBuilder.append("...");
                message.createThreadChannel(stringBuilder.toString()).queue();
            } else {
                message.createThreadChannel(message.getContentRaw()).queue();
            }

            long messageID = message.getIdLong();
            ArrayList<String> rsvpMessages = (ArrayList<String>) configFile.getOption("rsvp-messages");

            rsvpMessages.add(String.valueOf(messageID));

            configFile.setOption("rsvp-messages", rsvpMessages);

        }
    }

    @Override
    public void onMessageDelete(MessageDeleteEvent event){
        long guildID = event.getGuild().getIdLong();
        ConfigFile configFile = new ConfigFile(guildID);
        ArrayList<String> rsvpChannels = (ArrayList<String>) configFile.getOption("rsvp-channel");

        if (rsvpChannels.contains(String.valueOf(event.getChannel().getIdLong()))) {
            long messageId = event.getMessageIdLong();
            ArrayList<String> rsvpMessages = (ArrayList<String>) configFile.getOption("rsvp-messages");

            if (rsvpMessages.contains(String.valueOf(messageId))) {
                ThreadChannel threadChannel = event.getGuild().getThreadChannelById(messageId);
                for (Member member : threadChannel.getMembers()) {
                    threadChannel.removeThreadMember(member).queue();
                }
                event.getJDA().getThreadChannelById(messageId).delete().queue();

                rsvpMessages.remove(String.valueOf(messageId));
                configFile.setOption("rsvp-messages", rsvpMessages);
            }
        }
    }
}
