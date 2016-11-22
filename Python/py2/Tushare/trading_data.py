# -*- coding: utf-8 -*-
"""
Created on Wed Oct 26 10:10:03 2016

@author: Richard
"""

import tushare as ts

'''常见股价和指数数据'''
#print ts.get_hist_data('600848',start='2016-08-25',end='2016-09-10') #一次性获取全部日k线数据,hist是三年,从13年10月28号开始。
#print ts.get_hist_data('600848', ktype='W') #获取周k线数据,每周五天，全体数据
#print ts.get_hist_data('sh')#获取上证指数k线数据，其它参数与个股一致，竟然也是三年的

'''上市日期'''
#df = ts.get_stock_basics()
#date = df.ix['600848']['timeToMarket'] #上市日期YYYYMMDD，上海临港
#print date

'''得到复权全部数据'''
#print ts.get_h_data('002337') #前复权,从今天数前一年，赛象科技

'''得到深圳综合指数大盘数据'''
#print ts.get_h_data('399106', index=True) #深圳综合指数，请务必设定index参数为True,由于大盘指数不存在复权的问题

'''今日实时行情，今天休息那就是昨天的'''
#print ts.get_today_all()

'''历史分笔数据'''
#df = ts.get_tick_data('600848',date='2014-01-09')
#print df.head(10)

'''实时！分笔！'''
#df = ts.get_realtime_quotes('000581') #Single stock symbol
#print df[['code','name','price','bid','ask','volume','amount','time']]

'''大宗交易数据，指数数据'''
