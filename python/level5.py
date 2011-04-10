#!/usr/bin/python
# -*- coding: utf-8 -*-

'''
File: level5.py
Author: rshen <anticlockwise5@gmail.com>
Description: The Python Challenge Level 5 (Peak Hill): http://www.pythonchallenge.com/pc/def/peak.html
'''

import urllib
import pickle
import sys

src_url = "http://www.pythonchallenge.com/pc/def/banner.p"
req = urllib.urlopen(src_url)
pickled_data = req.read()
data = pickle.loads(pickled_data)

for line in data:
    for item in line:
        sys.stdout.write(item[0] * item[1])
    sys.stdout.write("\n")
