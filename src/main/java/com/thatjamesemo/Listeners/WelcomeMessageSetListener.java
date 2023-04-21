package com.thatjamesemo.Listeners;

import com.thatjamesemo.ConfigFile;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class WelcomeMessageSetListener extends ListenerAdapter {
    private final long channelId;
    private final long authorId;
    private Message initialMessage;

    public WelcomeMessageSetListener(MessageChannel channel, Member author) {
        this.channelId = channel.getIdLong();
        this.authorId = author.getIdLong();
        //this.initialMessage = mainMessage;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getMember().getIdLong() == this.authorId && event.getChannel().getIdLong() == this.channelId) {
            String content = event.getMessage().getContentRaw();
            ConfigFile configFile = new ConfigFile(event.getGuild().getIdLong());

            configFile.setOption("welcome-message", content);

            TextChannel channel = event.getGuild().getTextChannelById(event.getChannel().getIdLong());
            assert channel != null;
            channel.sendMessage("The new welcome message has been saved! Your new welcome message is:\n" + content).queue();
            event.getJDA().removeEventListener(this);

        }
    }
}
