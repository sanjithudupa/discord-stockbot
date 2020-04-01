package com.sanjithudupa;


import com.sanjithudupa.Utils.Stock;
import com.sanjithudupa.Utils.StockFunctions;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.Javacord;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.listener.message.MessageCreateListener;
import org.javacord.api.util.event.ListenerManager;

import java.awt.*;

public class Main {


    public static void main(String[] args) {
        final StockFunctions[] sf = {new StockFunctions()};
        final String[] callCommand = {"stock."};
        String helpCommand = "help";
        String changeCommand = "change";
        String token = "Njk0MjQ0MDM3MTIwNDkxNjQy.XoPmCQ.-jcgsVIM8ylT9LhRuUkGQhXOigk";

        DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();

        api.addMessageCreateListener(event -> {

            if (event.getMessageContent().toLowerCase().contains(callCommand[0]) && !event.getMessage().getAuthor().isYourself()) {

                String afterCall = event.getMessageContent().substring(callCommand[0].length());

                if(afterCall.contains(helpCommand)){

                    EmbedBuilder helpInfo = new EmbedBuilder()
                            .setTitle("StockBot Commands")
                            .addField("Help: ", "stock." + helpCommand, true)
                            .addField("Get Stock Information: ", "stock.StockSymbol(fastest & preferred) or stock.CompanyName (ex. stock.AAPL)", true)
                            .addField("**[NOT WORKING ATM]** Change Call Command: ", "stock." + changeCommand, true)
                            .setColor(Color.ORANGE)
                            .setFooter("StockBot by Sanjith Udupa. Number data from finance.yahoo.com, graphs from research.tdameritrade.com.");


                    event.getChannel().sendMessage(helpInfo);

                }/*else if(afterCall.contains(changeCommand)){
                    event.getChannel().sendMessage("You have 5 seconds to respond with a new command");

                    try {
                        api.wait(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    final String[] newCommand = {callCommand[0]};

                    ListenerManager<MessageCreateListener> listenerManager = api.addMessageCreateListener(event2 -> {

                        newCommand[0] = event2.getMessageContent();
                    });

                    callCommand[0] = newCommand[0];

//                    String newCommand = afterCall.substring(afterCall.indexOf(changeCommand) + changeCommand.length());
//                        callCommand[0] = newCommand;

                    listenerManager.remove();

                    event.getChannel().sendMessage("Successfully changed call command to \"" + newCommand[0] + "\"");

                } */else{
                    Stock stock = sf[0].companyStock(afterCall);

                    Color messageColor = Color.red;

                    if(stock.positive){
                        messageColor = Color.green;
                    }

                    EmbedBuilder stockData = new EmbedBuilder()
                            .setTitle(stock.companyName)
                            .addField("Price: ",   stock.price, true)
                            .addField("Symbol: ",   stock.ticker.toUpperCase(), true)
                            .addField("Change: ", stock.increase)
                            .setFooter("StockBot by Sanjith Udupa. Number data from finance.yahoo.com, graphs from research.tdameritrade.com.\nFinished in " + stock.timeToGetData/1000+ "s.")
                            .setColor(messageColor)
                            .setTimestampToNow()
                            .setImage(stock.graphAddress);

                    event.getChannel().sendMessage(stockData);
                }

            }

        });

        System.out.println("logged in");

    }
}
