#!/usr/bin/python
# -*- coding: utf-8 -*-

'''
File: level30.py
Author: rshen <anticlockwise5@gmail.com>
Description: The Python Challenge Level 30
'''

import sys
from PIL import Image

def perthree(data):
    data = iter(data)
    while True:
        yield data.next(), data.next(), data.next()

f = open("yankeedoodle.csv")

contents = f.read()
contents = contents.replace("\n", "")
numbers = contents.split(",")
numbers = [n.strip() for n in numbers]

data = [float(n) for n in numbers]

im = Image.new('F', (53,139))
im.putdata(data, 256)
im = im.transpose(Image.FLIP_LEFT_RIGHT)
im = im.transpose(Image.ROTATE_90)
im.save("level30.gif")

message = ''
for a, b, c in perthree(numbers):
    message += chr(int(a[5] + b[5] + c[6]))

print message
