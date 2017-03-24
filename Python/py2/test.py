# -*- coding: utf-8 -*-
"""
Created on Wed Mar 22 17:23:37 2017
相对路径的示例
@author: Richard
"""

import os

path1=os.path.abspath('.')   #表示当前所处的文件夹的绝对路径
path2=os.path.abspath('../../')  #表示当前所处的文件夹上一级文件夹的绝对路径
testpath = unicode(path1+r'\test1.txt')
print testpath
print path2
testtxt = open(testpath)
word = testtxt.readline()
print word