package org.example;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class setup extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e){
        if(!e.getMember().hasPermission(Permission.ADMINISTRATOR)) return;

        String[] args = e.getMessage().getContentRaw().split(" ");

        switch (args[0]){
            case "$help":
                EmbedBuilder helpBuilder = new EmbedBuilder()
                        .setColor(Color.white)
                        .setTitle("Help")
                        .setDescription("ㅤ\n" +
                                "**setup commands:** \n" +
                                "ㅤ\n" +
                                "`.sensitiveRoles roleName(cAsE SeNSiTIve, names separated by whiteSpace)` **role to keep a special eye on** \n" +
                                "ㅤ\n" +
                                "`.rmSensitiveRole roleName(cAsE SeNSiTIve)` **remove sensitive roles** \n" +
                                "ㅤ\n" +
                                "`.roleToPing roleName(cAsE SeNSiTIve)` **when someone messes with sensitive roles, who to ping. Only one can be added** \n" +
                                "ㅤ\n" +
                                "`.loggingChannel` **channel to log changes on** \n" +
                                "ㅤ\n" +
                                "`.config` **see current server settings**" +
                                "");
                e.getMessage().replyEmbeds(helpBuilder.build())
                        .mentionRepliedUser(false)
                        .queue();
        }
    }
}
