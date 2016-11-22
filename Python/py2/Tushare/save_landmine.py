# -*- coding: utf-8 -*-
"""
Created on Fri Nov 04 09:17:05 2016
保存某一个股票信息地雷的数据
@author: Richard

"""
import tushare as ts
import datetime as dt

'''
#这两个都表示这一天第一个信息地雷的新闻链接
print ts.get_notices(code='600848',date='2016-10-15').ix[:,'url'][0]
print ts.get_notices(code='600848',date='2016-10-15').ix[0,'url']
#打印第一个新闻
#print ts.notice_content(ts.get_notices(code='600848',date='2016-10-15').ix[:,'url'][1])
'''
today = dt.date.today()
'''关于时间的一些尝试，可以花样输出日期
today = dt.date.today()
print today
yesterday = today - dt.timedelta(days=1)
tomorrow = today + dt.timedelta(days=1)
print "昨天:%s， 今天:%s， 明天：%s" % (yesterday, today, tomorrow) 
begin = '2015-06-06'
begin = dt.datetime.strptime(begin,'%Y-%m-%d')
print begin.strftime('%Y-%m-%d')
finish = begin - dt.timedelta(days=1)
print "finish:",finish
fo = dt.datetime(2013, 4, 15)
print 'fo,',fo
'''
#打印所以某一天的个股信息地雷链接，和某一天信息地雷的个数
#print ts.get_notices(code='600848',date='2016-11-04').ix[:,'url']
#print '今天的关于上海临港的新闻有',ts.get_notices(code='600848',date=today).ix[:,'url'].shape[0],'条'
'''
temp = []
for i in ts.get_notices(code='600848',date='2016-11-04').ix[:,'url']:
    temp.append(i)
print temp
'''

thisday = dt.datetime(2016, 1, 1)
print thisday.strftime('%Y-%m-%d')
for i in range(0,3):#364
    thisday = thisday + dt.timedelta(days=1)
    print thisday.strftime('%Y-%m-%d')
    #print ts.get_notices(code='600848',date = thisday.strftime('%Y-%m-%d')).shape[0]
print thisday.strftime('%Y-%m-%d')

print ts.get_notices(code='600848',date = '2016-10-18')
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    