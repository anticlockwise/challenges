#!/usr/bin/python
# -*- coding: utf-8 -*-

'''
File: level25.py
Author: rshen <anticlockwise5@gmail.com>
Description: The Python Challenge Level 25
'''

from PIL import Image
import wave

start = 100
im_new = Image.new('RGB', (300,300))
rows = [[] for i in range(300)]
row = 0
for i in range(0, 25):
    if i % 5 == 0 and i != 0:
        row += 60
    spf = wave.open("level25/lake%d.wav" % (i + 1), 'rb')
    im = Image.fromstring('RGB', (60,60), spf.readframes(spf.getnframes()))
    im.save("level25/lake%d.png" % (i+1))
    data = list(im.getdata())
    for j in range(im.size[0]):
        start = j*60
        end = (j+1)*60
        rows[row + j].extend(data[start:end])

data = []
for r in rows:
    data.extend(r)
im_new.putdata(data)
im_new.save("lake.png")
