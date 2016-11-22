# -*- coding: utf-8 -*-
"""
Created on Fri Oct 28 10:23:21 2016

@author: Richard
读取数据，去除停止词
"""
lines = open('test_remove.txt').readlines()
print lines
stopkey=[line.decode('utf-8') for line in open('stopwords.txt').readlines()]  
print stopkey[-1]#最后一个是空格，我加的
print type(stopkey[-1])

