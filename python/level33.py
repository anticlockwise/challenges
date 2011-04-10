#!/usr/bin/python
# -*- coding: utf-8 -*-

'''
File: level33.py
Author: rshen <anticlockwise5@gmail.com>
Description: The Python Challenge Level 33
'''

from PIL import Image
from math import sqrt
import sys
import string

power, might = 194, 193
i = 0

im = Image.open("beer2.png")
data = list(im.getdata())
while power > 0:
    st = []
    for d in data:
        if d != power and d != might:
            st.append(d)

    size = int(sqrt(len(st)))
    im_new = Image.new('P', (size,size))
    im_new.putdata(st)
    im_new.save("level33_%d.png" % i)
    data = list(im_new.getdata())

    power, might = power - 6, might - 6
    i += 1
