# -*- coding: utf-8 -*-
"""
Created on Mon Oct  9 11:25:42 2017

@author: richard
"""

import tensorflow as tf

a = tf.constant([1.0, 2.0], name="a")
b = tf.constant([2.0, 3.0], name="b")
result = a + b