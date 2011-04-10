#!/usr/bin/python
# -*- coding: utf-8 -*-

'''
File: level27.py
Author: rshen <anticlockwise5@gmail.com>
Description: The Python Challenge Level 27
'''

import string
import bz2
from PIL import Image

zig = Image.open("zigzag.gif")
data = zig.getdata()
pal = zig.getpalette()[::3]

exceptions = []
next = None
indices = []
for i, d in enumerate(data):
    if next != None and d != next:
        exceptions.append(d)
        indices.append(i)
    next = pal[d]

print bz2.decompress("".join(map(chr, exceptions)))

im_new = Image.new("RGB", zig.size)
colors = [(255, 255, 255) for i in range(len(data))]
for i in indices:
    colors[i] = (0, 0, 0)
im_new.putdata(colors)
im_new.save("level27.gif")
