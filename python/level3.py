#!/usr/bin/python
# -*- coding: utf-8 -*-

'''
File: level3.py
Author: rshen <anticlockwise5@gmail.com>
Description: The Python Challenge Level 3: http://www.pythonchallenge.com/pc/def/equality.html
'''

import re
import urllib

search_str = re.compile("[^A-Z][A-Z]{3}([a-z])[A-Z]{3}[^A-Z]")

req = urllib.urlopen("http://www.pythonchallenge.com/pc/def/equality.html")
content = "".join(req.readlines()[21:-1])
print "".join(search_str.findall(content))
