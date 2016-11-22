# -*- coding: utf-8 -*-
"""
learn re
by ztz
re.match(pattern, string, flags=0)

"""

import re
print 1
string = 'this is \nnewline'
print 'string:',string
rawString = r'this is \n not newline'
print 'rawString',rawString
pat = 'tr'
match = re.match(r'dog','dog cat dog')
print match.group(0)
match2 = re.search(r'cat', 'dog cat dog')
print match2.group(0)
print re.findall(r'cat', 'dog cat dog')
contactInfo = 'Doe, John: 555-1212'
match3 = re.search(r'(\w+), (\w+): (\S+)', contactInfo)
print match3.group(1)
print match3.group(2)
print match3.group(3)