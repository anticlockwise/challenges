#!/usr/bin/python
# -*- coding: utf-8 -*-

'''
File: level16.py
Author: rshen <anticlockwise5@gmail.com>
Description: The Python Challenge Level 16 (Let me get this straight): http://www.pythonchallenge.com/pc/return/mozart.html
'''

import urllib
import Image
import collections as c

img_url = "http://huge:file@www.pythonchallenge.com/pc/return/mozart.gif"
req = urllib.urlopen(img_url)
open("mozart.gif", 'w').write(req.read())

img = Image.open("mozart.gif")
width, height = img.size
data = list(img.getdata())
data = [data[i:i+width] for i in range(0, len(data), width)]

new_img = Image.new(img.mode, (width, height))
new_data = []

for row in data:
    index = row.index(195)
    q = c.deque(row)
    q.rotate(-index)
    new_data.extend(q)

new_img.putdata(new_data)
new_img.save("mozart16.gif")
