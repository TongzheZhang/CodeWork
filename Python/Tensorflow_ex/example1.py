#!/usr/bin/env python2
# -*- coding: utf-8 -*-
"""
Created on Wed Jan 11 13:14:28 2017
1st example for tensorflow
@author: tongzhezhang
"""

import tensorflow as tf
import numpy as np
#
greeting = tf.constant('Hello Google Tensorflow! ')
#start a session
sess = tf.Session()
#execute session
result = sess.run(greeting)
print result
sess.close()
#claculate prodduct of two matrix, 3*2+3*2+2
matrix1 = tf.constant([[3.,3.]])
matrix2 = tf.constant([[2.],[2.]])

product = tf.matmul(matrix1,matrix2)
linear = tf.add(product,tf.constant(2.0))
with tf.Session() as sess:
    result = sess.run(linear)
    print result




