# -*- coding: utf-8 -*-
"""
Spyder Editor

This is a temporary script file.
"""

import jieba
'''
支持三种分词模式：
精确模式，试图将句子最精确地切开，适合文本分析；
全模式，把句子中所有的可以成词的词语都扫描出来, 速度非常快，但是不能解决歧义；
搜索引擎模式，在精确模式的基础上，对长词再次切分，提高召回率，适合用于搜索引擎分词。
'''
# ##Part 1. 分词

# jieba.cut 方法接受三个输入参数: 需要分词的字符串；cut_all 参数用来控制是否采用全模式；HMM 参数用来控制是否使用 HMM 模型。
# jieba.cut_for_search 方法接受两个参数：需要分词的字符串；是否使用 HMM 模型。该方法适合用于搜索引擎构建倒排索引的分词，粒度比较细。
# 待分词的字符串可以是 unicode 或 UTF-8 字符串、GBK 字符串。注意：不建议直接输入 GBK 字符串，可能无法预料地错误解码成 UTF-8。
# jieba.cut 以及 jieba.cut_for_search 返回的结构都是一个可迭代的 generator，可以使用 for 循环来获得分词后得到的每一个词语(unicode)，或者用
# jieba.lcut 以及 jieba.lcut_for_search 直接返回 list。
# jieba.Tokenizer(dictionary=DEFAULT_DICT) 新建自定义分词器，可用于同时使用不同词典。jieba.dt 为默认分词器，所有全局分词相关函数都是该分词器的映射。
seg_list = jieba.cut("我来到北京清华大学", cut_all=True)
print("Full Mode: " + "/ ".join(seg_list))  # 全模式
print seg_list

seg_list = jieba.cut("我来到北京清华大学", cut_all = False)
print("Precise Mode: " + "/".join(seg_list))  #精确模式，默认状态下也是精确模式
seg_list = jieba.cut("他来到网易杭研大厦。")
print("Default Mode: " + "/".join(seg_list))

seg_list = jieba.cut_for_search("小明硕士毕业于中国科学院计算所，后在日本京都大学深造。")  #搜索引擎模式
print("Search Mode: " + "/".join(seg_list))

# ##Part 2. 添加自定义词典

# ###载入词典
# 开发者可以指定自己自定义的词典，以便包含 jieba 词库里没有的词。虽然 jieba 有新词识别能力，但是自行添加新词可以保证更高的正确率。
# 用法： jieba.load_userdict(file_name) # file_name 为自定义词典的路径。
# 词典格式和dict.txt一样，一个词占一行；每一行分三部分，一部分为词语，另一部分为词频（可省略），最后为词性（可省略），用空格隔开。
# 词频可省略，使用计算出的能保证分出该词的词频。
# 更改分词器的 tmp_dir 和 cache_file 属性，可指定缓存文件位置，用于受限的文件系统。
seg_list = jieba.cut("李小福是创新办主任也是云计算方面的专家。")
print("Origin: " + "/".join(seg_list))

#jieba.load_userdict("D:\\Anaconda2\\Lib\site-packages\\jieba\\testdict.txt")
seg_list = jieba.cut("李小福是创新办主任也是云计算方面的专家。")
print("Revise: " + "/".join(seg_list))



























