#!/usr/bin/env python2
# -*- coding: utf-8 -*-
"""
Created on Fri Jan 13 09:56:18 2017
Using skflow to predict price of house in Boston
@author: tongzhezhang
"""

from sklearn import datasets, metrics, preprocessing, cross_validation

boston = datasets.load_boston()

X, y = boston.data, boston.target

X_train, X_test, y_train, y_test = cross_validation.train_test_split(X, y, test_size = 0.25, random_state = 33)

#normalization of features
scaler = preprocessing.StandardScaler()
X_train = scaler.fit_transform(X_train)
X_test = scaler.transform(X_test)

import skflow

tf_ddn = skflow.TensorFlowDNNRegressor(hidden_units=[100, 40], steps=10000, learning_rate=0.01, batch_size=50)

tf_ddn.fit(X_train, y_train)
'''
tf_lr_y_prediction = tf_lr.predict(X_test)

print 'MAE of tensorflow linear regressor is,',metrics.mean_absolute_error(tf_lr_y_prediction, y_test)
print 'MSE of tensorflow linear regressor is,',metrics.mean_squared_error(tf_lr_y_prediction, y_test)
print 'R**2 of tensorflow linear regressor is,',metrics.r2_score(tf_lr_y_prediction, y_test)
'''