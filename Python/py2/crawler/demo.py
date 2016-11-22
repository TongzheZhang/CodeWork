# -*- coding: utf-8 -*-
"""
Created on Mon Sep 12 19:35:35 2016

@author: Richa
"""

import urllib2

request = urllib2.Request("http://www.baidu.com")
response = urllib2.urlopen(request)
print response.read()