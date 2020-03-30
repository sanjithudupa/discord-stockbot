package com.sanjithudupa;


import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.UserStatus;

public class Main {


    public static void main(String[] args) {
        StockData sd = new StockData();
        String callCommand = "stock.";
        String helpCommand = "help";

        String token = "Njk0MjQ0MDM3MTIwNDkxNjQy.XoJrig.ftN2Ook5db67tJHgkTzkfF7avfw";

        DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();

        api.addMessageCreateListener(event -> {

            if (event.getMessageContent().contains(callCommand)) {
                String afterCall = event.getMessageContent().substring(callCommand.length());

                
                if(afterCall == helpCommand){
                    //help procedure
                    EmbedBuilder embed = new EmbedBuilder()
                            .setTitle("Stock Bot Commands")
                            .addField(callCommand + helpCommand,   "Help", true)
                            .addField(callCommand + "Stock Symbol", "Find Stock Price", true)
                            .setFooter("Go to this website to find tickers")
                            .setUrl("https://stocks.tradingcharts.com/stocks/symbols/s");
                }else{
                    //stock command
                    event.getChannel().sendMessage(sd.priceOf(afterCall));
                }


            }



        });


        System.out.println("logged in");



    }
}
