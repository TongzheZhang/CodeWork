# -*- coding: utf-8 -*-
"""
Created on Sun Dec 17 11:20:59 2017

@author: richard
"""


import tensorflow as tf
from sklearn.datasets import load_digits
import numpy as np
digits = load_digits()
X_data = digits.data.astype(np.float32)
Y_data = digits.target.astype(np.float32).reshape(-1,1)
print X_data.shape
print Y_data.shape

'''error
from sklearn.preprocessing import MinMaxScaler
scaler = MinMaxScaler()
X_data = scaler.fit_transform(X_data)
'''
'''
from sklearn import  preprocessing 
#Y = OneHotEncoder().fit_transform(Y_data).todense() #one-hot编码
print Y
'''