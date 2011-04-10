#!/usr/bin/python
# -*- coding: utf-8 -*-

'''
File: level22.py
Author: rshen <anticlockwise5@gmail.com>
Description: The Python Challenge Level 22 (Emulate): http://www.pythonchallenge.com/pc/hex/copper.html
'''

import urllib
from PIL import Image, ImageDraw

gif_url = "http://butter:fly@www.pythonchallenge.com/pc/hex/white.gif"
req = urllib.urlopen(gif_url)
open("white.gif", 'wb').write(req.read())

im = Image.open("white.gif")
im.load()
i = 0
vectors = []
while i < 133:
    try:
        l = []
        im.seek(i)
        size = im.size
        pix = list(im.getdata()).index(8)
        y,x = divmod(pix, 200)
        vectors.append((x-100,y-100))
        i += 1
    except:
        break

orig, w, h = 0, 300, 100
im_new = Image.new('RGB', (w, h))
draw = ImageDraw.Draw(im_new)
for v in vectors:
    if v == (0, 0):
        orig += 30
        src = (orig, h/2)
        continue
    dst = (src[0]+v[0], src[1]+v[1])
    orig = max(orig, dst[0])
    draw.line([src, dst], fill=255)
    src = dst

im_new.save("level22.jpg")
