# Discord StockBot
This is a bot I made for Discord in Java called Stock Bot.

## Usage
When the bot is active/online, type stock.(some company's symbol/ticker)
For example, a valid command would be stock.GOOG

## Libraries
For the Discord connection, I used the [Javacord](https://github.com/Javacord/Javacord) library.
I didn't use any API's for directly collecting the stock data, but I used the [JSoup](jsoup.org) library for scraping data directly from the [Yahoo Finance](https://finance.yahoo.com/) website. For returning graphs of the input, I used the [TDAmeritrade](https://research.tdameritrade.com/grid/public/research/stocks/summary?fromPage=overview&display=&fromSearch=true&symbol=AAPL#) website and used Jsoup to find the image. See [StockFunctions.java](https://github.com/sanjithudupa/discord-stockbot/blob/master/Discord%20StockBot/src/com/sanjithudupa/StockFunctions.java) for the data collection.
