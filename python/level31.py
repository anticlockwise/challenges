#!/usr/bin/python
# -*- coding: utf-8 -*-

'''
File: level31.py
Author: rshen <anticlockwise5@gmail.com>
Description: The Python Challenge Level 31
'''

# Username: kohsamui
# Password: thailand

from PIL import Image

im = Image.open("mandelbrot.gif")
im_my = Image.open("mandelbrot_31.gif")

data1 = list(im_my.getdata())
data2 = list(im.getdata())

diffs = []
for i in range(len(data1)):
    if data1[i] != data2[i]:
        diffs.append(data1[i] - data2[i])

im_new = Image.new('1', (23, 73))
data = [int(d > 0) for d in diffs]
im_new.putdata(data)
im_new.save("level31.gif")
