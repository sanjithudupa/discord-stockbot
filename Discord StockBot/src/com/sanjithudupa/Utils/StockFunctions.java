package com.sanjithudupa.Utils;




import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class StockFunctions {

    /**
     *
     * @param ticker The ticker name of the company (ex. Tesla = TSLA, Apple = APPL, Google = GOOG, etc.)
     * @return Will return a stock containing the price of a certain stock, the company name and a graph of the stock
     */
    public Stock companyStock(String ticker){
        String main_url =  "https://finance.yahoo.com/quote/" + ticker;
        String beginningOfGraphURL = "https://research.tdameritrade.com/";
        String graph_url = beginningOfGraphURL + "grid/public/research/stocks/summary?fromPage=overview&display=&fromSearch=true&symbol=" + ticker;

        String priceSearch = "<span class=\"Trsdu(0.3s) Fw(b) Fz(36px) Mb(-4px) D(ib)\" data-reactid=\"50\">";
        String stockPrice = "Sorry, I didn't find a value. Perhaps the ticker input is invalid.";

        String nameSearch = "<h1 class=\"D(ib) Fz(18px)\" data-reactid=\"7\">";
        String companyName = "";

        String graphAddress = "https://www.howtogeek.com/thumbcache/2/200/adec3eac582bebd79fcbcdb4fba86a2f/wp-content/uploads/2013/05/windows-8-blue-screen-header.png";
        String graphSearch = "<img src=\"/";


        Stock stock = new Stock(stockPrice, companyName, graphAddress);

        //find all text-based information(company name,
        try{
            Document document = Jsoup.connect(main_url).get();
            String htmlCode = document.outerHtml();

            String[] lines = htmlCode.split(System.getProperty("line.separator"));

            for(String line : lines){
                if(line.contains(nameSearch)){
                    //trim to not spaces
                    int beginningOfLine = line.indexOf("<");
                    line = line.substring(beginningOfLine + nameSearch.length(), line.indexOf("<",beginningOfLine + 2));
                    companyName = line;
                }

            }

            for(String line : lines){
                if(line.contains(priceSearch)){
                    //trim to not spaces
                    int beginningOfLine = line.indexOf("<");
                    line = line.substring(beginningOfLine + priceSearch.length(), line.indexOf("<",beginningOfLine + 2));
                    stockPrice = line;
                }

            }


            //find graph

            document = Jsoup.connect(graph_url).get();
            htmlCode = document.outerHtml();


            lines = htmlCode.split(System.getProperty("line.separator"));

            for(String line : lines){
                if(line.contains(graphSearch)){
                    //trim to just image url
                    int beginningOfLine = line.indexOf("<");
                    line = "/" + line.substring(beginningOfLine + graphSearch.length(), line.indexOf("\"", line.indexOf("/grid")));
                    graphAddress = beginningOfGraphURL + line;
                }

            }




        }catch(Exception e){
            e.printStackTrace();

        }

        stock.companyName = companyName;
        stock.price = stockPrice;
        stock.graphAddress = graphAddress;

        return stock;

    }
}
