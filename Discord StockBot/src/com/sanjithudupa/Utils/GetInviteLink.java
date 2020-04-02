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
        DiscordApi api = new DiscordApiBuilder().setToken(Secret.token).login().join();

        System.out.println("You can invite the bot by using the following url: " + api.createBotInvite());
    }
}
