# -*- coding: utf-8 -*-
"""
Created on Thu Oct 27 22:44:32 2016

@author: Richard
读入停止词列表
"""

stopwords = open('stopwords.txt').readlines()
print type(stopwords[0].decode('utf8'))
print type(stopwords[0])
print stopwords[6]
print stopwords[6].decode('utf8')