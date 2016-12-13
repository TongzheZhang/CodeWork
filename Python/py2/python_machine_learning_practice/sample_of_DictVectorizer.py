# -*- coding: utf-8 -*-
"""
Created on Mon Dec 12 11:44:35 2016
sample of DictVectorizer
使用字典储存的数据进行特征提取与向量化
@author: Richard
"""
#导入DictVectorizer
from sklearn.feature_extraction import DictVectorizer

#定义一组字典列表，用来表示多个数据样本（每个字典代表一个数据样本）
measurements = [{'city':'Dubai','temperature':33.},{'city':'London','temperature':12.},{'city':'San Fransisco','temperature':18.}]
                
#print measurements[0]['city']

#实例化
vec = DictVectorizer()

#输出特征字典或者特征向量
print vec.fit_transform(measurements)
print vec.fit_transform(measurements).toarray()

#输出各个维度的特征含义
print vec.get_feature_names()
