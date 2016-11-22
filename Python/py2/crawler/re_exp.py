# -*- coding: utf-8 -*-
"""
Created on Thu Nov 17 14:45:41 2016

@author: Richa
正则表达式的事例
"""

import re

#%%
print (re.match('www','www.runoob.com').span()) # 在起始位置匹配
print (re.match('com','www.runoob.com'))  # 不在起始位置匹配
#%%
pattern = re.compile(r'hello')
match = pattern.match('hello world')
if match:
    print match.group()
    
#%% 以下的两个编译方法是等价的
a = re.compile(r"""\d +  # the integral part
                   \.    # the decimal point
                   \d *  # some fractional digits""", re.X)
b = re.compile(r"\d+\.\d*")
print a == b

#%%
m = re.match(r'hello','hello world')
print m.group()