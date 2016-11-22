# -*- coding: utf-8 -*-
"""
Created on Thu Oct 27 21:23:08 2016

@author: Richard
简单的去除停止词，已成功
"""
import jieba
stopwords = {}.fromkeys([ line.rstrip().decode('utf8') for line in open('stopwords_test.txt') ])
#stopwords.fromkeys(' ')
#print stopwords.keys()[0] 打印出来第一个停用词
'''序列和字典，以及生成键值
seq = ('name', 'age', 'sex')

dic = dict.fromkeys(seq)
print "New Dictionary : %s" %  str(dic)

dic = dict.fromkeys(seq, 10)
print "New Dictionary : %s" %  str(dic)
print "New Dictionary :",dic
'''
#stopwords = {}.fromkeys([u'的', u'附近',' '])
line = '北京 附近的租房 \n 哎呦，在 马路\t边附近'
print '原始的句子：',line
#segs = jieba.cut(line, cut_all=False)#精准模式，默认也是精准模式

#final = ''
#finallist = [] 

#test = 'have not 附近' if u'附近' not in stopwords else 'have 附近'#有附近这个停止词

#print test
#seg is unicode
'''
for seg in segs:
    print str(seg)
    if seg not in stopwords:#字典的特性，看有没有这个键值
        print 
        final += seg
        finallist.append(seg)
#print final.strip(' ')
'''
dataList = line.split('\n')
blanklist = ['\t',' ','']
#print dataList
data = []#还是原行数数据，词用空格隔开
for oneline in dataList:
    #print oneline 可以打印出来每行的内容
    print oneline
    print 'line break'
    temp = []
    for i in jieba.cut(oneline):
        print i
        test = 'here is not stopword' if i not in stopwords else 'here is stopword'
        testblank = 'here is not blank' if i not in blanklist else 'here is blank'
        print test
        print testblank
        if i not in stopwords:
            if i not in blanklist:
                temp.append(i)
    data.append(" ".join(temp)) #'sep'.join(seq)，分隔符为空格，原来的一行还是一行

print 'over seg'

'''
while ' ' in finallist :
    print 1
    finallist.remove(' ')
while '\n' in finallist:
    print 2
    finallist.remove('\n')
       
for fin in range(0,len(finallist)):
    print finallist[fin]
'''
for i in data:
    print i.strip()


