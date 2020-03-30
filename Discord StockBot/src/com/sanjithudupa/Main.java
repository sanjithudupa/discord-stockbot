package com.sanjithudupa;


import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

public class Main {


    public static void main(String[] args) {
        StockData sd = new StockData();
        String callCommand = "stock.";

        String token = "Njk0MjQ0MDM3MTIwNDkxNjQy.XoJimQ.IWZVzzlTLQeZe_b5zi4GThpJw98";

        DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();


        api.addMessageCreateListener(event -> {
            if (event.getMessageContent().contains(callCommand)) {
                String ticker = event.getMessageContent().substring(callCommand.length());
                event.getChannel().sendMessage(sd.priceOf(ticker));
            }
        });


        System.out.println("logged in");



    }
}
