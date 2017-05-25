#!/usr/bin/env python2
# -*- coding: utf-8 -*-
"""
Created on Sun Feb  5 01:09:39 2017
MNIST For ML Beginners
@author: tongzhezhang
"""
from tensorflow.examples.tutorials.mnist import input_data
# get data, 55000 for training, 10000 for testing
# label is a one-hot vector, just one bit is 1
mnist = input_data.read_data_sets("MNIST_data/", one_hot=True)
train_images = mnist.train.images
train_labels = mnist.train.labels
test_images = mnist.test.images
test_labels = mnist.test.labels

import tensorflow as tf
# Here None means that a dimension can be of any length
x = tf.placeholder(tf.float32, [None, 784])
# create Variables
W = tf.Variable(tf.zeros([784, 10]))
b = tf.Variable(tf.zeros([10]))

# implement our model
y = tf.nn.softmax(tf.matmul(x, W) + b)
# cross-entropy
y_ = tf.placeholder(tf.float32, [None, 10])
# adds the elements in the second dimension of y
# tf.reduce_mean computes the mean over all the examples in the batch
cross_entropy = tf.reduce_mean(-tf.reduce_sum(y_ * tf.log(y), reduction_indices=[1]))

# training
train_step = tf.train.GradientDescentOptimizer(0.5).minimize(cross_entropy)

# initialize the variables we created
init = tf.global_variables_initializer()

# set session
sess = tf.Session()
sess.run(init)

for i in range(1001):
    # one hundred random data points from our training set everytime
    batch_xs, batch_ys = mnist.train.next_batch(100)
    # feed in the batches data
    sess.run(train_step, feed_dict={x: batch_xs, y_: batch_ys})
    if i % 200 == 0:
        print i
correct_prediction = tf.equal(tf.argmax(y,1), tf.argmax(y_,1))
accuracy = tf.reduce_mean(tf.cast(correct_prediction, tf.float32))
print(sess.run(accuracy, feed_dict={x: mnist.test.images, y_: mnist.test.labels}))


'''another way to cal'''
# start TensorFlow InteractiveSession
import tensorflow as tf
sess = tf.InteractiveSession()

# build a softmax regression model
x = tf.placeholder(tf.float32, shape=[None, 784])
y_ = tf.placeholder(tf.float32, shape=[None, 10])
W = tf.Variable(tf.zeros([784, 10]))
b = tf.Variable(tf.zeros([10]))

# init
sess.run(tf.global_variables_initializer())

# regression model
y = tf.matmul(x, W) + b

# loss function
cross_entropy = tf.reduce_mean(tf.nn.softmax_cross_entropy_with_logits(y, y_))

# train step
train_step = tf.train.GradientDescentOptimizer(0.5).minimize(cross_entropy)
for i in range(1001):
    batch = mnist.train.next_batch(100)
    train_step.run(feed_dict={x: batch[0], y_:batch[1]})
    
# test step
correct_prediction = tf.equal(tf.argmax(y,1), tf.argmax(y_,1))
accuracy = tf.reduce_mean(tf.cast(correct_prediction, tf.float32))
print accuracy.eval(feed_dict={x: mnist.test.images, y_:mnist.test.labels})