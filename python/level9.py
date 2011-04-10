#!/usr/bin/python
# -*- coding: utf-8 -*-

'''
File: level9.py
Author: rshen <anticlockwise5@gmail.com>
Description: The Python Challenge Level 8 (Connect the dots): http://www.pythonchallenge.com/pc/return/good.html
'''

import Image
import urllib

img = Image.new("RGB", (640, 480))

req = urllib.urlopen("http://huge:file@www.pythonchallenge.com/pc/return/good.html")
lines = req.readlines()

first_lines = lines[25:43]
first = "".join(first_lines).replace("\n", "")
first = [int(i) for i in first.split(",")]

second_lines = lines[45:50]
second = "".join(second_lines).replace("\n", "")
second = [int(i) for i in second.split(",")]

first.extend(second)
for i in range(0, len(first), 2):
    x, y = first[i:i+2]
    img.putpixel((x, y), (255,255,255))
    img.putpixel((x, y-1), (255,255,255))
    img.putpixel((x, y+1), (255,255,255))
    img.putpixel((x-1, y-1), (255,255,255))
    img.putpixel((x-1, y), (255,255,255))
    img.putpixel((x-1, y+1), (255,255,255))
    img.putpixel((x+1, y+1), (255,255,255))
    img.putpixel((x+1, y), (255,255,255))
    img.putpixel((x+1, y-1), (255,255,255))

img.save("level9.png")
