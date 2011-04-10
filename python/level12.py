#!/usr/bin/python
# -*- coding: utf-8 -*-

'''
File: level12.py
Author: rshen <anticlockwise5@gmail.com>
Description: The Python Challenge Level 12 (Dealing Evil): http://www.pythonchallenge.com/pc/return/evil.html
'''

import urllib

gfx_url = "http://huge:file@www.pythonchallenge.com/pc/return/evil2.gfx"
req = urllib.urlopen(gfx_url)
open("evil2.gfx", 'w').write(req.read())

data = [[], [], [], [], []]
bin_file = open("evil2.gfx").read()
for i in range(0, len(bin_file), 5):
    for j in range(5):
        data[j].append(bin_file[i+j])

for i in range(5):
    open("evil2_%d.png" % (i+1,), 'w').write("".join(data[i]))
