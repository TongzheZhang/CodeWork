# -*- coding: utf-8 -*-
"""
Created on Tue Oct 25 20:05:05 2016

@author: Richard
"""

import tushare as ts
#data = ts.get_hist_data('002415',start='2016-10-17',end='2016-10-21')#DataFrame,海康威视
#print data

#index = ts.get_index()#各种综合指数
#print index

#print ts.get_rrr()#存款准备金

#print ts.realtime_boxoffice() 看电影票房

#print ts.get_latest_news()#得到最新的新闻，主要是财经，美股，证券什么的

hist_data = ts.get_hist_data('600848')
print hist_data.columns
hist_data['close'].plot()