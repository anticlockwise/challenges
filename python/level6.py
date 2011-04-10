#!/usr/bin/python
# -*- coding: utf-8 -*-

'''
File: level6.py
Author: rshen <anticlockwise5@gmail.com>
Description: The Python Challenge Level 6 (Now There are Pairs): http://www.pythonchallenge.com/pc/def/channel.html
'''

import urllib
import zipfile
import re

next_nothing = re.compile("Next nothing is (\d+)")

zip_url = "http://www.pythonchallenge.com/pc/def/channel.zip"
req = urllib.urlopen(zip_url)
open("channel.zip", 'w').write(req.read())

nothing = "90052"
zfile = zipfile.ZipFile("channel.zip")
content = zfile.read("%s.txt" % nothing)
match = next_nothing.search(content)

while True:
    nothing = match.group(1)
    content = zfile.read("%s.txt" % nothing)
    match = next_nothing.search(content)
    if not match:
        print content
        break

comments = []

nothing = "90052"
content = zfile.read("%s.txt" % nothing)
match = next_nothing.search(content)

zinfo = zfile.getinfo("%s.txt" % nothing)
comments.append(zinfo.comment)

while True:
    nothing = match.group(1)
    content = zfile.read("%s.txt" % nothing)
    match = next_nothing.search(content)

    zinfo = zfile.getinfo("%s.txt" % nothing)
    comments.append(zinfo.comment)

    if not match:
        break

print "".join(comments)
