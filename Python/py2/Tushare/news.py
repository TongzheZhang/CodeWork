# -*- coding: utf-8 -*-
"""
Created on Wed Oct 26 11:27:35 2016

@author: Richard
"""

import tushare as ts

'''得到最近的新闻'''
#print ts.get_latest_news() #默认获取最近80条新闻数据，只提供新闻类型、链接和标题
#print ts.get_latest_news(top=5,show_content=True) #显示最新5条新闻，并打印出新闻内容
#print ts.latest_content(ts.get_latest_news(top=5,show_content=True).ix[1,'url'])

'''新浪股吧'''
#print ts.guba_sina() #17条数据
#df = ts.guba_sina(show_content=True)
#print df#没尝试成功

'''信息地雷,每天，得到内容'''
#print ts.get_notices(code='600848',date='2016-10-15').ix[:,'url'][1]
#print ts.notice_content(ts.get_notices(code='600848',date='2016-10-15').ix[:,'url'][1])