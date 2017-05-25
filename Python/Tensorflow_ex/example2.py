#!/usr/bin/env python2
# -*- coding: utf-8 -*-
"""
Created on Wed Jan 11 13:14:28 2017
2nd example for tensorflow
how to cal the para of cancer data
@author: tongzhezhang
"""
import tensorflow as tf
import numpy as np
import pandas as pd


train = pd.read_csv('/home/tongzhezhang/Data/Breast-Cancer/breast-cancer-train.csv')
print train.head()
test = pd.read_csv('/home/tongzhezhang/Data/Breast-Cancer/breast-cancer-test.csv')

X_train = np.float32(train[['Clump Thickness','Cell Size']].T)
y_train = np.float32(train['Type'].T)
X_test = np.float32(test[['Clump Thickness','Cell Size']].T)
y_test = np.float32(test['Type'].T)

#define a variable b
b = tf.Variable(tf.zeros([1]))
#define W
W = tf.Variable(tf.random_uniform([1,2], -1.0, 1.0))
#define the function
y = tf.matmul(W, X_train) + b

#get loss
loss = tf.reduce_mean(tf.square(y-y_train))
#set optimizer, step is 0.01
optimizer = tf.train.GradientDescentOptimizer(0.01)
#set optimizer function
train = optimizer.minimize(loss)
#init
init = tf.initialize_all_variables()

#start session
sess = tf.Session()
sess.run(init)

#iterate 1000 times
for step in xrange(0,1000):
    sess.run(train)
    if step%200==0:
        print step,sess.run(W),sess.run(b)
    if step==999:
        print step,sess.run(W),sess.run(b)

'''show the plot'''
#prepare the data
test_negative = test.loc[test['Type']==0][['Clump Thickness','Cell Size']]
test_positive = test.loc[test['Type']==1][['Clump Thickness','Cell Size']]

import matplotlib.pyplot as plt

plt.scatter(test_negative['Clump Thickness'], test_negative['Cell Size'], marker='o',s=200,c='red')
plt.scatter(test_positive['Clump Thickness'], test_positive['Cell Size'], marker='x',s=150,c='black')

plt.xlabel('Clump Thickness')
plt.ylabel('Cell Size')

lx = np.arange(0,12)
ly = (0.5 - sess.run(b) - lx * sess.run(W)[0][0]) / sess.run(W)[0][1]

plt.plot(lx, ly, color = 'green')

plt.show()
























