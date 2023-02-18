package com.thatjamesemo;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.emoji.CustomEmoji;
import net.dv8tion.jda.api.entities.emoji.Emoji;

import java.util.Optional;

public class getEmoji {
    public static String getEmoji(JDA api, long emojiId) {
        Emoji emoji = api.getEmojiById(emojiId);
        return emoji.getFormatted();
    }
}
