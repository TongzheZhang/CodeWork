# -*- coding: utf-8 -*-
"""
Created on Sun Dec 18 12:03:06 2016
An Introduction to Stock Market Data Analysis with Python
@author: Richard
"""

import pandas as pd
import pandas.io.data as web #导入data需要导入的包和模块
import datetime
import matplotlib.pyplot as plt
import numpy as np

#从2016年1月1日一年的的股价
start = datetime.datetime(2016,1,1)
end = datetime.date.today()
print start,end

# get data of Apple from Yahoo
apple = web.DataReader("AAPL","yahoo",start,end)

print type(apple)
print apple.head()

apple["Adj Close"].plot(grid = True)

#多个公司复权收盘价绘制在一起啊
microsoft = web.DataReader("MSFT", "yahoo", start, end)
google = web.DataReader("GOOG", "yahoo", start, end)
 
# Below I create a DataFrame consisting of the adjusted closing price of these stocks, first by making a list of these objects and using the join method
stocks = pd.DataFrame({"AAPL": apple["Adj Close"],
                      "MSFT": microsoft["Adj Close"],
                      "GOOG": google["Adj Close"]})
 
print stocks.head()
stocks.plot(grid = True)

#一个解决方案是在绘制数据时使用两个不同的尺度；一个尺度用于苹果和微软的股票，另一个用于谷歌。
stocks.plot(secondary_y = ["AAPL","MSFT"], grid = True)

#用回报作图
# df.apply(arg) will apply the function arg to each column in df, and return a DataFrame with the result
# Recall that lambda x is an anonymous function accepting parameter x; in this case, x will be a pandas Series object
stock_return = stocks.apply(lambda x: x / x[0])
stock_return.head()
stock_return.plot(grid = True).axhline(y = 1, color = "black", lw = 2)
 
#使用log算百分比？？为什么
stock_change = stocks.apply(lambda x: np.log(x) - np.log(x.shift(1))) # shift moves dates back by 1.
print stock_change.head()

stock_change.plot(grid = True).axhline(y = 0, color = "black", lw = 2)

#计算出移动平均 20天
apple["20d"] = np.round(apple["Close"].rolling(window = 20, center = False).mean(), 2)

