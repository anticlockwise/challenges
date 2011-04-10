#!/usr/bin/python
# -*- coding: utf-8 -*-

'''
File: level29.py
Author: rshen <anticlockwise5@gmail.com>
Description: The Python Challenge Level 29
'''

import urllib, bz2

req = urllib.urlopen("http://repeat:switch@www.pythonchallenge.com/pc/ring/guido.html")
st = req.read()

lines = st.splitlines()[12:]
spaces = [len(list(x)) for x in lines]
print bz2.decompress("".join(map(chr, spaces)))
