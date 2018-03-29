# -*- coding: utf-8 -*-
"""
Spyder Editor

This is a temporary script file.
"""

from snownlp import SnowNLP

s = SnowNLP(u'这个东西真心很赞')
print s.words
print s.sentiments
text = u'''
自然语言处理是计算机科学领域与人工智能领域中的一个重要方向。
它研究能实现人与计算机之间用自然语言进行有效通信的各种理论和方法。
自然语言处理是一门融语言学、计算机科学、数学于一体的科学。
因此，这一领域的研究将涉及自然语言，即人们日常使用的语言，
所以它与语言学的研究有着密切的联系，但又有重要的区别。
自然语言处理并不是一般地研究自然语言，
而在于研制能有效地实现自然语言通信的计算机系统，
特别是其中的软件系统。因而它是计算机科学的一部分。
'''
t = SnowNLP(text)
print t.keywords(3)[0]
print t.summary(3)[0]

print t.idf