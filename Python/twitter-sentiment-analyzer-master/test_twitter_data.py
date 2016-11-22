# -*- coding: utf-8 -*-
"""
Created on Tue Sep 13 11:56:18 2016

@author: Richa
"""

#!/usr/bin/env python
import get_twitter_data

## PLACE YOUR CREDENTIALS in config.json file or run this file with appropriate arguments from command line
keyword = 'iphone'
time = 'today'
twitterData = get_twitter_data.TwitterData()
tweets = twitterData.getTwitterData(keyword, time)
print tweets#tweets[0]