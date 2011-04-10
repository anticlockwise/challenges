#!/usr/bin/python
# -*- coding: utf-8 -*-

'''
File: level7.py
Author: rshen <anticlockwise5@gmail.com>
Description: The Python Challenge Level 7 (Smarty): http://www.pythonchallenge.com/pc/def/oxygen.html
'''

import Image
import urllib
import sys
import re

next_level = re.compile(r"\[((\d+,?\s*)+)\]")

oxygen_url = "http://www.pythonchallenge.com/pc/def/oxygen.png"
req = urllib.urlopen(oxygen_url)
open("oxygen.png", 'w').write(req.read())

img = Image.open("oxygen.png")
width, height = img.size
data = list(img.getdata())
data = [data[i:i+width] for i in range(0, width*height, width)]
data = data[43]
pixels = [chr(data[i][0]) for i in range(0, len(data), 7)\
        if data[i][0] == data[i][1] == data[i][2]]
content = "".join(pixels)

next_str = next_level.search(content).group(1)
pixels = [chr(int(d)) for d in next_str.split(", ")]
print "".join(pixels)
