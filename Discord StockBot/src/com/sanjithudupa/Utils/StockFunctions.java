package com.sanjithudupa.Utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Timer;

public class StockFunctions {

    /**
     * @param ticker The ticker name of the company (ex. Tesla = TSLA, Apple = APPL, Google = GOOG, etc.)
     * @return Will return a stock containing the price of a certain stock, the company name and a graph of the stock
     */
    public Stock companyStock(String ticker) {
        double startTime = System.currentTimeMillis();
        String main_url = "https://finance.yahoo.com/quote/" + ticker;
        String beginningOfGraphURL = "https://research.tdameritrade.com/";
        String graph_url = beginningOfGraphURL + "grid/public/research/stocks/summary?fromPage=overview&display=&fromSearch=true&symbol=" + ticker;

        String priceSearch = "<span class=\"Trsdu(0.3s) Fw(b) Fz(36px) Mb(-4px) D(ib)\" data-reactid=\"50\">";
        String stockPrice = "Sorry, I didn't find a value. Perhaps the ticker input is invalid.";

        String nameSearch = "<h1 class=\"D(ib) Fz(18px)\" data-reactid=\"7\">";
        String companyName = "";

        String increase = "";
        boolean positive = false;

        String graphSearch = "<img src=\"/";
        String graphAddress = "https://assets-global.website-files.com/583347ca8f6c7ee058111b55/5afc770caa130421393fa412_google-doc-error-message.png";

        String[] otherData = new String[16];
        String prevClose = "";
        String open = "";
        String bid = "";
        String ask = "";
        String dayRange = "";
        String ftwRange = "";
        String vol = "";
        String avgVol = "";
        String marketCap = "";
        String beta = "";
        String peRatio = "";
        String eps = "";
        String earningsDate = "";
        String forward = "";
        String exDiv = "";
        String oneYTarget = "";

        Stock stock = new Stock(stockPrice, companyName, graphAddress, increase, positive, ticker);

        //find all info from yahoo
        try {
            Document document = Jsoup.connect(main_url).get();
            String htmlCode = document.outerHtml();

            if(htmlCode.contains("Symbols similar to '" + ticker.toLowerCase() + "'")){
                //didn't work

                Element table = document.selectFirst("table");
                Elements rows = table.select("tr");
                rows.remove(0);
                Element row1 = rows.first();
                Element info = row1.selectFirst("td");
                return companyStock(info.text());


            }

            String[] lines = htmlCode.split(System.getProperty("line.separator"));

            //find company name
            for (String line : lines) {
                if (line.contains(nameSearch)) {
                    //trim to not spaces
                    int beginningOfLine = line.indexOf("<");
                    line = line.substring(beginningOfLine + nameSearch.length(), line.indexOf("<", beginningOfLine + 2));
                    companyName = line.substring(0,line.length()-2-ticker.length());
                }

            }

            //find stock price and increase amount
            for (String line : lines) {
                if (line.contains(priceSearch)) {
                    String og = line;

                    int beginningOfLine = line.indexOf("<");
                    line = line.substring(beginningOfLine + priceSearch.length(), line.indexOf("<", beginningOfLine + 2));
                    stockPrice = line;

                    //find increase amount
                    line = og;
                    line = line.substring(line.indexOf(">", line.indexOf("Fz(24px)")) + 1, line.indexOf("<", line.indexOf("Fz(24px")));
                    increase = line;
                }

            }

            //W(100%)

            //find other information
            Elements table1 = document.select("table");
            Elements rows = table1.select("tr");
            Elements tds = rows.select("td");

            int i = 0;
            for(int j = 1; j < tds.size(); j+=2){
                otherData[i] = tds.get(j).text();
                i++;
            }

            //find graph from tdameritrade
            document = Jsoup.connect(graph_url).get();
            htmlCode = document.outerHtml();


            lines = htmlCode.split(System.getProperty("line.separator"));

            for (String line : lines) {
                if (line.contains(graphSearch)) {
                    //trim to just image url
                    int beginningOfLine = line.indexOf("<");
                    line = "/" + line.substring(beginningOfLine + graphSearch.length(), line.indexOf("\"", line.indexOf("/grid")));
                    graphAddress = beginningOfGraphURL + line;
                }

            }


        } catch (Exception e) {
            e.printStackTrace();

        }

        if (increase.contains("+")) {
            positive = true;
        }

        stock.companyName = companyName;
        stock.price = stockPrice;
        stock.graphAddress = graphAddress;
        stock.increase = increase;
        stock.positive = positive;
        stock.timeToGetData = System.currentTimeMillis() - startTime;
        stock.prevClose = otherData[0];
        stock.open = otherData[1];
        stock.bid = otherData[2];
        stock.ask = otherData[3];
        stock.dayRange = otherData[4];
        stock.ftwRange = otherData[5];
        stock.vol = otherData[6];
        stock.avgVol = otherData[7];
        stock.marketCap = otherData[8];
        stock.beta = otherData[9];
        stock.peRatio = otherData[10];
        stock.eps = otherData[11];
        stock.earningsDate = otherData[12];
        stock.forward = otherData[13];
        stock.exDiv = otherData[14];
        stock.oneYTarget = otherData[15];

        return stock;

    }

}
