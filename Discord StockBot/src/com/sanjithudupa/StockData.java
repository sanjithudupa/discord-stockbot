package com.sanjithudupa;




import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class StockData {

    /**
     *
     * @param ticker The ticker name of the company (ex. Tesla = TSLA, Apple = APPL, Google = GOOG, etc.)
     * @return Will return a string containing the price of a certain stock
     */
    public String priceOf(String ticker){
        String url = "https://finance.yahoo.com/quote/" + ticker;
        String lookingFor = "<span class=\"Trsdu(0.3s) Fw(b) Fz(36px) Mb(-4px) D(ib)\" data-reactid=\"50\">";
        String stockPrice = "Sorry, I didn't find a value. Perhaps the ticker input is invalid.";

        try{
            Document document = Jsoup.connect(url).get();
            String htmlCode = document.outerHtml();

            String[] lines = htmlCode.split(System.getProperty("line.separator"));

            for(String line : lines){
                if(line.contains(lookingFor)){
                    //trim to not spaces
                    int beginningOfLine = line.indexOf("<");
                    line = line.substring(beginningOfLine + lookingFor.length(), line.indexOf("<",beginningOfLine + 2));
                    stockPrice = "$" + line;
                }

            }

        }catch(Exception e){
            e.printStackTrace();

        }

        return stockPrice;

    }
}
