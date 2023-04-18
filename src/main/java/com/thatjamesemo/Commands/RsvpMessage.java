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
        long guildId = event.getGuild().getIdLong(); // Get the guild ID for the server
        ConfigFile configFile = new ConfigFile(guildId); // Get the config file for the server

        long rsvpYes = configFile.getOptionAsLong("rsvp-yes"); // Get the emoji ID for the Yes emoji.
        long rsvpNo = configFile.getOptionAsLong("rsvp-no"); // Get the emoji ID for the No emoji.
        long rsvpMaybe = configFile.getOptionAsLong("rsvp-maybe"); // Get the emoji ID for the Maybe emoji.

        ArrayList<String> rsvpChannels = (ArrayList<String>) configFile.getOption("rsvp-channel"); // Gets the list of the RSVP Channels from config.

        if (rsvpChannels.contains(String.valueOf(event.getChannel().getIdLong()))) { // Checks if the list of RSVP channels contains the channel with the message recieved interaction.
            Message message = event.getMessage();
            message.addReaction(event.getJDA().getEmojiById(rsvpYes)).queue(); // Adds the yes reaction.
            message.addReaction(event.getJDA().getEmojiById(rsvpMaybe)).queue(); // Adds the maybe reaction.
            message.addReaction(event.getJDA().getEmojiById(rsvpNo)).queue(); // Adds the no reaction.
            if (message.getContentRaw().length() >= 100) { // Whole if statement shortens the name of the thread to support discord limits.
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < 95; i++) {
                    stringBuilder.append(message.getContentRaw().charAt(i));
                }
                stringBuilder.append("...");
                message.createThreadChannel(stringBuilder.toString()).queue(); // Create the thread with the title.
            } else {
                message.createThreadChannel(message.getContentRaw()).queue(); // Create the thread with the title.
            }

            long messageID = message.getIdLong(); // Gets the ID of the message.
            ArrayList<String> rsvpMessages = (ArrayList<String>) configFile.getOption("rsvp-messages"); // Gets the list of the messages to monitor for RSVP.
            rsvpMessages.add(String.valueOf(messageID)); // Adds the new message to the list.
            configFile.setOption("rsvp-messages", rsvpMessages); // Saves the list to the config file of the server.

        }
    }

    @Override
    public void onMessageDelete(MessageDeleteEvent event) {
        long guildID = event.getGuild().getIdLong(); // Get the id of the server.
        ConfigFile configFile = new ConfigFile(guildID); // Get the config file of the server.
        ArrayList<String> rsvpChannels = (ArrayList<String>) configFile.getOption("rsvp-channel"); // Gets the list of the RSVP channels.

        if (rsvpChannels.contains(String.valueOf(event.getChannel().getIdLong()))) { // Checks if the event channel is a RSVP channel.
            long messageId = event.getMessageIdLong(); // Gets the message ID of the deleted message.
            ArrayList<String> rsvpMessages = (ArrayList<String>) configFile.getOption("rsvp-messages"); // Gets the list of the RSVP messages.

            if (rsvpMessages.contains(String.valueOf(messageId))) { // Checks if the deleted message is an RSVP message.
                ThreadChannel threadChannel = event.getGuild().getThreadChannelById(messageId); // Gets the thread channel of the message.
                for (Member member : threadChannel.getMembers()) {
                    threadChannel.removeThreadMember(member).queue(); // Removes every member from the thread.
                }
                event.getJDA().getThreadChannelById(messageId).delete().queue(); // Deletes the thread channel.

                rsvpMessages.remove(String.valueOf(messageId)); // Removes the RSVP message frmo the list.
                configFile.setOption("rsvp-messages", rsvpMessages); // Saves the RSVP messages list.
            }
        }
    }
}
