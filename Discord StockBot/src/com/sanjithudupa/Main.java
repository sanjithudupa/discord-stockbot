package com.sanjithudupa;


import com.sanjithudupa.Utils.Secret;
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
        String changeCommand = "prefix";

        DiscordApi api = new DiscordApiBuilder().setToken(Secret.token).login().join();

        api.addMessageCreateListener(event -> {

            if (event.getMessageContent().toLowerCase().contains(callCommand[0]) && !event.getMessage().getAuthor().isYourself()) {

                String afterCall = event.getMessageContent().substring(callCommand[0].length());

                if(afterCall.contains(helpCommand)){

                    EmbedBuilder helpInfo = new EmbedBuilder()
                            .setTitle("StockBot Commands")
                            .addField("Help: ", "stock." + helpCommand, true)
                            .addField("Get Stock Information: ", "stock.symbol or stock.name (ex. stock.AAPL)", true)
                            .addField("Change Call Command: ", "stock." + changeCommand)
                            .setColor(Color.ORANGE)
                            .setFooter("StockBot by Sanjith Udupa. Number data from finance.yahoo.com, graphs from research.tdameritrade.com.");


                    event.getChannel().sendMessage(helpInfo);

                }else if(afterCall.contains(changeCommand)){
                    String newCommand = afterCall.substring(afterCall.indexOf(changeCommand) + changeCommand.length());



                    callCommand[0] = newCommand.trim() + ".";

                    event.getChannel().sendMessage("Successfully changed call command to \"" + callCommand[0] + "\"");

                }else{
                    Stock stock = sf[0].companyStock(afterCall);

                    Color messageColor = Color.red;

                    if(stock.positive){
                        messageColor = Color.green;
                    }

                    EmbedBuilder stockData = new EmbedBuilder()
                            .setTitle("**"+stock.companyName+"**")
                            .addField("**Price: **",   stock.price, true)
                            .addField("Symbol: ",   "**" + stock.ticker.toUpperCase() + "**", true)
                            .addField("Change: ", stock.increase, true)
                            .addField("Previous Close: ", stock.prevClose, true)
                            .addField("Open: ", stock.open,true)
                            .addField("Bid: ", stock.bid, true)
                            .addField("Ask: ", stock.ask, true)
                            .addField("Day's Range: ", stock.dayRange, true)
                            .addField("52 Week Range: ", stock.ftwRange, true)
                            .addField("Volume: ", stock.vol, true)
                            .addField("Avg. Volume: ", stock.avgVol, true)
                            .addField("Market Cap: ", stock.marketCap, true)
                            .addField("Beta (5Y Monthly): ", stock.beta,true)
                            .addField("PE Ratio (TTM): ", stock.peRatio, true)
                            .addField("EPS (TTM): ", stock.eps, true)
                            .addField("Earnings Date: ", stock.earningsDate, true)
                            .addField("Forward Dividend & Yield: ", stock.forward,true)
                            .addField("Ex-Dividend Date: ", stock.exDiv, true)
                            .addField("1y Target Est: ", stock.oneYTarget, true)
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
