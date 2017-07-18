# -*- coding: utf-8 -*-
"""
Created on Mon Jun 26 15:17:08 2017

@author: richard
"""

import tushare as ts
hs300 = ts.get_hs300s()
print hs300.head()