package com.sanjithudupa;


import com.sanjithudupa.Utils.Stock;
import com.sanjithudupa.Utils.StockFunctions;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.jsoup.Jsoup;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Main {


    public static void main(String[] args) {
        StockFunctions sf = new StockFunctions();
        String callCommand = "stock.";
        String token = "Njk0MjQ0MDM3MTIwNDkxNjQy.XoM8Kg.QGJEpZruBJcaJ2xVGD6UoGfHR1E";

        DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();

        api.addMessageCreateListener(event -> {

            if (event.getMessageContent().contains(callCommand)) {

                String afterCall = event.getMessageContent().substring(callCommand.length());
                Stock stock = sf.companyStock(afterCall);

                Color messageColor = Color.red;

                if(stock.positive){
                    messageColor = Color.green;
                }

                EmbedBuilder stockData = new EmbedBuilder()
                        .setTitle(stock.companyName)
                        .addField("Price: ",   stock.price, true)
                        .addField("Symbol: ",   afterCall.toUpperCase(), true)
                        .addField("Change: ", stock.increase)
                        .setFooter("StockBot by Sanjith Udupa. Number data from finance.yahoo.com, graphs from research.tdameritrade.com.")
                        .setColor(messageColor)
                        .setImage(stock.graphAddress);

                event.getChannel().sendMessage(stockData);

            }

        });

        System.out.println("logged in");

    }
}
