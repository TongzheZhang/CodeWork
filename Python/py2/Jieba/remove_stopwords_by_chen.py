# -*- coding: utf-8 -*-
"""
Created on Mon Oct 31 22:17:21 2016

@author: Richard
"""

import pandas as pd

#参数初始化
inputfile = '../data/education/education1_cut1.txt'#../data/age/age6_cut.txt
stoplist = '../data/stoplist.txt'

file = pd.read_csv(inputfile, encoding = 'utf-8', header = None) #读入数据
#pos = pd.read_csv(posfile, encoding = 'utf-8', header = None)
stop = pd.read_csv(stoplist, encoding = 'utf-8', header = None, sep = 'tipdm')
#sep设置分割词，由于csv默认以半角逗号为分割词，而该词恰好在停用词表中，因此会导致读取出错
#所以解决办法是手动设置一个不存在的分割词，如tipdm。
stop = [' ', ''] + list(stop[0]) #Pandas自动过滤了空格符，这里手动添加

file[1] = file[0].apply(lambda s: s.split(' ')) #定义一个分割函数，然后用apply广播
file[2] = file[1].apply(lambda x: [i for i in x if i not in stop]) #逐词判断是否停用词，思路同上