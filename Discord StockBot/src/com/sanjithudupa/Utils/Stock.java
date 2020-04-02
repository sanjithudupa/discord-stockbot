package com.sanjithudupa.Utils;

public class Stock {
    public String price;
    public String companyName;
    public String graphAddress;
    public String increase;
    public boolean positive;
    public String prevClose, open, bid, ask, dayRange, ftwRange, vol, avgVol, marketCap, beta, peRatio, eps, earningsDate, forward, exDiv, oneYTarget;
    public String ticker;
    public double timeToGetData;


    public Stock(String p, String c, String g, String i, boolean pos, String t){
        price = p;
        companyName = c;
        graphAddress = c;
        increase = i;
        positive = pos;
        ticker = t;

    }

}
