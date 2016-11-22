# -*- coding: utf-8 -*-
"""
Created on Wed Oct 26 11:57:59 2016

@author: Richard
"""

import tushare as ts

'''上海临港'''
myData = ts.get_h_data('600848',start='2015-01-01',end='2015-12-31') #前复权,从今天数前一年，赛象科技
#print myData.ix[:,'close'].head()
myData.to_csv(r'F:\CodeWork\Python\py2\Tushare\600848.csv')