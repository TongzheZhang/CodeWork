# -*- coding: utf-8 -*-
"""
Created on Wed Dec 14 15:08:09 2016
构建自己的语料库
@author: Richard
"""

#数据源目录(二级目录)
sourceDataDir='data'

#数据源文件列表
fileLists = []

import os
from gensim import corpora, models, similarities
            
def getSourceFileLists(sourceDataDir):  
    fileLists = []
    subDirList = os.listdir(sourceDataDir)
    for subDir in subDirList:
        subList = os.listdir(sourceDataDir + '/' + subDir)
        fileList = [ sourceDataDir+'/'+subDir+'/'+ x for x in subList if os.path.isfile(sourceDataDir+'/'+subDir+'/'+x)]
        fileLists += fileList

    return  fileLists   
        
        
fileLists = getSourceFileLists(sourceDataDir)  
  
  
if 0 < len(fileLists): 
    import codecs
    import jieba
    punctuations = ['','\n','\t',',', '.', ':', ';', '?', '(', ')', '[', ']', '&', '!', '*', '@', '#', '$', '%'] 
    
    if not os.path.exists('dict'):
        os.mkdir("dict") 
    if not os.path.exists('corpus'):
        os.mkdir("corpus") 
    print 'builded folder'
    for fileName in fileLists:
        print fileName

        hFile = None
        content = None
        try:
            hFile = codecs.open(fileName,'r','gb18030')#打开文件
            content = hFile.readlines()#安行读入
            print len(content)
        except Exception,e:
            print e
        finally:
            if hFile:
                hFile.close()#关闭文件
        
        if content:#每行是一个元素
            fileFenci = [ x for x in jieba.cut(' '.join(content),cut_all=True)]#得到所有词
            fileFenci2 = [word for word in fileFenci if not word in punctuations]  #去掉所有标点符号
            print len(fileFenci2),len(fileFenci)
            
            texts = [fileFenci2] #list外又加了一个list
            all_tokens = sum(texts, [])
            tokens_once = set(word for word in set(all_tokens) if all_tokens.count(word) == 1)
            texts = [[word for word in text if word not in tokens_once] for text in texts]

            sFileDir, sFileName = os.path.split(fileName)
            dictFileName = 'dict/'+sFileName+'.dict'
            corpusFileName = 'corpus/'+sFileName+'.mm'
            
            dictionary = corpora.Dictionary(texts)
            dictionary.save_as_text(dictFileName)

            corpus = ([dictionary.doc2bow(text) for text in texts])
            corpora.MmCorpus.serialize(corpusFileName, corpus) 

print 'Build corpus done'