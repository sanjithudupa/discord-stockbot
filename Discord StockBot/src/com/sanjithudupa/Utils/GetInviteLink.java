package com.sanjithudupa.Utils;


import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

/**
 * @Class
 * Run the main method to get the invite link for the bot
 *
 */

public class GetInviteLink {

    public static void main(String[] args) {
        String token = "Njk0MjQ0MDM3MTIwNDkxNjQy.XoPmCQ.-jcgsVIM8ylT9LhRuUkGQhXOigk";

        DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();

        System.out.println("You can invite the bot by using the following url: " + api.createBotInvite());
    }
}
