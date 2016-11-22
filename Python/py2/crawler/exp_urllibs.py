# -*- coding: utf-8 -*-
"""
Created on Thu Nov 17 15:42:47 2016

@author: Richa
"""

import urllib 
import urllib2 

#%%
url = 'http://python.org/'
response = urllib2.urlopen('http://python.org/') 
html = response.read()

data = urllib2.open(url)
#print html

#%%

url = 'http://www.pythontab.com' 
values = {'name' : 'Michael Foord', 
          'location' : 'pythontab', 
          'language' : 'Python' } 
data = urllib.urlencode(values) 
req = urllib2.Request(url, data) 
response = urllib2.urlopen(req) 
the_page = response.read()
#print the_page

#%%
data= {}
data['name'] = 'Somebody Here' 
data['location'] = 'pythontab' 
data['language'] = 'Python' 
url_values = urllib.urlencode(data)
print url_values