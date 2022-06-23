package org.example;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import org.example.Listeners.onRoleAdd;
import org.example.Listeners.onRoleRemove;

import javax.security.auth.login.LoginException;

public class Main {
    public static void main(String[] args) throws LoginException {
        JDA builder = JDABuilder.createLight(System.getenv("token"))
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .addEventListeners(new onRoleAdd())
                .addEventListeners(new onRoleRemove())
                .addEventListeners(new Database())
                .addEventListeners(new setup())
                .build();
    }
}