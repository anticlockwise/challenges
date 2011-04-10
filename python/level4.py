#!/usr/bin/python
# -*- coding: utf-8 -*-

'''
File: level4.py
Author: rshen <anticlockwise5@gmail.com>
Description: The Python Challenge Level 4: http://www.pythonchallenge.com/pc/def/linkedlist.php
'''

import urllib
import re
import sys

next_nothing = re.compile("next nothing is (\d+)")
url = "http://www.pythonchallenge.com/pc/def/linkedlist.php?nothing=%s"

nothing = sys.argv[1]
req = urllib.urlopen(url % nothing)
content = req.read()
while True:
    match = next_nothing.search(content)
    if not match:
        print content
        break
    print match.group(1)
    nothing = match.group(1)
    req = urllib.urlopen(url % nothing)
    content = req.read()
