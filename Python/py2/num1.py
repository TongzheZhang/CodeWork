# -*- coding: utf-8 -*-
"""
Created on Wed Mar 29 11:13:05 2017

@author: Richard
"""

        # write code here
'''
思路：X∈[1,9],0不适用
        从 1 至 10，在它们的个位数中，任意的 X 都出现了 1 次。
        从 1 至 100，在它们的十位数中，任意的 X 都出现了 10 次。
        从 1 至 1000，在它们的百位数中，任意的 X 都出现了 100 次。
        依此类推，从 1 至  10^i ，在它们的左数第二位（右数第  i  位）中，任意的 X 都出现了  10^(i−1)  次。
'''
n = 1256
x=5

high=-1;low=-1;time=0
i=1 #i为数字n从右往左的第i位
while high!=0:
    high = n / (10 ** i)  # 拿到高位
             
    temp = n % (10 ** i)
    occur = temp / (10 ** (i - 1))  # 拿到第i位上的数字
             
    low = n % (10 ** (i - 1))#拿到i的低位
             
    time+=high*(10**(i-1))#先算基础值，再加上低位的进行补充
    if occur>x:#比x大，把第i位的次数加上
        time=time+10**(i-1)
    elif occur==x:
        time=time+low+1
    i+=1
print time