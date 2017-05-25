# -*- coding: utf-8 -*-
"""
Created on Fri Apr 28 13:28:16 2017
计算RSI
@author:Tongzhe Zhang
"""
import datetime as dt
import pandas as pd
f = open('data.txt')
lines = f.readlines()
purelines = lines[1:]
total_data = []
#datetime.datetime.now().strftime('%b-%d-%y %H:%M:%S');
lastdate = '2010/4/16'
lastprice = 0.0
for index,i in enumerate(purelines):
    
    temp = i.strip().split(',')
    #write day data except for last day
    if temp[0] != lastdate:

        total_data.append([lastdate,lastprice])
        lastdate = temp[0]
    lastprice = float(temp[5])
    #temptime = temp[0]+' '+temp[1]
    #temptimestd = dt.datetime.strptime(temptime,'%Y/%m/%d %H:%M:%S');
    #tempdata = [temptimestd.strftime('%y-%m-%d %H:%M:%S'),temp[4]]
total_data.append([lastdate,lastprice])
df = pd.DataFrame(total_data,columns = ['date','price'])
##df = df.set_index('time')
#df.index = pd.to_datetime(df.index, unit='s')
#day_df = df['price'].resample('1T').ohlc()
df = df.set_index('date') 