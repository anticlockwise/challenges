#!/usr/bin/python
# -*- coding: utf-8 -*-

'''
File: level11.py
Author: rshen <anticlockwise5@gmail.com>
Description: The Python Challenge Level 11 (Odd even): http://www.pythonchallenge.com/pc/return/5808.html
'''

import urllib
import Image

img_url = "http://huge:file@www.pythonchallenge.com/pc/return/cave.jpg"
req = urllib.urlopen(img_url)
open("cave.jpg", 'w').write(req.read())

img = Image.open("cave.jpg")
width, height = img.size

odd_width, odd_height = width / 2, height / 2
even_width, even_height = odd_width, odd_height

odd_img = Image.new("RGB", (odd_width, odd_height))
even_img = Image.new("RGB", (even_width, even_height))

odd_data, even_data = [], []
for i in range(height):
    for j in range(width):
        pixel = img.getpixel((j, i))
        if i % 2 == 0 and j % 2 == 0:
            even_data.append(pixel)
        elif i % 2 == 0 and j % 2 != 0:
            odd_data.append(pixel)

odd_img.putdata(odd_data)
even_img.putdata(even_data)

odd_img.save("cave_odd.jpg")
even_img.save("cave_even.jpg")
