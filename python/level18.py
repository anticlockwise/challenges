#!/usr/bin/python
# -*- coding: utf-8 -*-

'''
File: level18.py
Author: rshen <anticlockwise5@gmail.com>
Description: The Python Challenge Level 18 (Can you tell me the difference?): http://www.pythonchallenge.com/pc/return/brightness.html
'''

import urllib
import gzip
import re
import difflib

spaces = re.compile(r"\s+")

gz_url = "http://huge:file@www.pythonchallenge.com/pc/return/deltas.gz"
req = urllib.urlopen(gz_url)
open("deltas.gz", 'w').write(req.read())

gz_file = gzip.GzipFile("deltas.gz", 'rb')
lines = gz_file.readlines()

left_lines, right_lines = [], []
for line in lines:
    left = line[:53]
    right = line[56:].strip()
    left_lines.append(left)
    right_lines.append(right)

left = "\n".join(left_lines)
right = "\n".join(right_lines)
diffs = difflib.ndiff(left_lines, right_lines)

images = [[], [], []]

for row in diffs:
    digits = [chr(int(c, 16)) for c in row.split(" ")[1:] if c != '']
    if row.startswith("+"):
        images[0].append("".join(digits))
    elif row.startswith("-"):
        images[1].append("".join(digits))
    elif row.startswith(" "):
        images[2].append("".join(digits))

for i in range(len(images)):
    img_data = images[i]
    open("level18_%d.png" % i, 'w').write("".join(img_data))
