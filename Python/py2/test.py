
import sys

def reverse(x):
    length = 1
    chu = 10
    res = 0
    while x > chu:
        length =length+1
        chu = chu*10

    for i in range(0, length):
        temp = x/(10**(length-i-1))
        print temp
        x = x - temp*(10**(length-i-1))
        res = res + temp*10**i
    return res
if __name__ == "__main__":
    # 读取第一行的n
    a = 12
    b = 4454

    print reverse(a)+reverse(b)