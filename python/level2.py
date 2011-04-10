#!/usr/bin/python
# -*- coding: utf-8 -*-

'''
File: level2.py
Author: rshen <anticlockwise5@gmail.com>
Description: The Python Challenge Level 2: http://www.pythonchallenge.com/pc/def/ocr.html
'''

import string
import urllib
import re

chars = re.compile("[a-z]")

req = urllib.urlopen("http://www.pythonchallenge.com/pc/def/ocr.html")
content = "".join(req.readlines()[37:-2])
print "".join(chars.findall(content))
