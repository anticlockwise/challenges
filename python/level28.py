#!/usr/bin/python
# -*- coding: utf-8 -*-

'''
File: level28.py
Author: rshen <anticlockwise5@gmail.com>
Description: The Python Challenge Level 28 (repeat, switch)
'''

from PIL import Image

im = Image.open("bell.png")
data = list(im.getdata())
size = im.size
area = size[0]*size[1]

greens = [data[i][1] for i in range(len(data))]
pairs = [(greens[i],greens[i+1]) for i in range(0, len(greens), 2)]
diffs = [chr(abs(p[0] - p[1])) for p in pairs if abs(p[0] - p[1]) != 42]
print "".join(diffs)
