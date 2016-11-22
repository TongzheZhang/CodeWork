# -*- coding: utf-8 -*-
"""
Created on Wed Oct 26 22:58:12 2016

@author: Richard
"""
import jieba
chinese1 = '我是中文'#字符串
print chinese1
print isinstance(chinese1, str)
print type(chinese1) #查看chinese的字符类型

chinese2 = u'我是中文'#用python的内在编码unicode
print chinese2
print isinstance(chinese2, unicode)
print type(chinese2) #查看chinese的字符类型

newchi1 = chinese1.decode('utf8')#从utf8解码到python本身的unicode
print chinese1