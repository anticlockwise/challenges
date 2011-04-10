#!/usr/bin/python
# -*- coding: utf-8 -*-

'''
File: level14.py
Author: rshen <anticlockwise5@gmail.com>
Description: The Python Challenge Level 14 (Walk Around): http://www.pythonchallenge.com/pc/return/italy.html
'''

import urllib
import Image

img_url = "http://huge:file@www.pythonchallenge.com/pc/return/wire.png"
req = urllib.urlopen(img_url)
open("wire.png", 'w').write(req.read())

img = Image.open("wire.png")

img_new = Image.new(img.mode, (100, 100))

seq = [i for i in range(1,101)]
seq = seq * 2
seq.sort()
seq.reverse()
seq = seq[1:]

x, y, step = -1, 99, 0
directions = [(1, 0), (0, -1), (-1, 0), (0, 1)]
direction = 0
for num in seq:
    for i in range(num):
        x += directions[direction][0]
        y += directions[direction][1]
        img_new.putpixel((x, y), img.getpixel((step, 0)))
        step += 1
    direction += 1
    if direction == 4:
        direction = 0

img_new.save("wire14.png")
