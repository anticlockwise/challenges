#!/usr/bin/python
# -*- coding: utf-8 -*-

'''
File: level17.py
Author: rshen <anticlockwise5@gmail.com>
Description: The Python Challenge Level 17 (Eat?): http://www.pythonchallenge.com/pc/return/romance.html
'''

import urllib, urllib2, re, sys, bz2

url = "http://www.pythonchallenge.com/pc/def/linkedlist.php"
req = urllib.urlopen(url)

print req.info().getheader('Set-Cookie')

next = "12345"
busy = "?busynothing=%s"

cookies = []
while True:
    busy_url = url + (busy % next)
    req = urllib.urlopen(busy_url)
    contents = req.read()
    info = req.info().getheader('Set-Cookie')
    cookie = info.split(";")[0].split("=")[1]
    cookies.append(cookie)
    sys.stdout.write(".")
    sys.stdout.flush()

    matches = re.findall("next busynothing is (\d+)", contents)
    if len(matches) == 0:
        break
    next = re.findall("next busynothing is (\d+)", contents)[0]

sys.stdout.write("\n")
print bz2.decompress(urllib.unquote_plus("".join(cookies)))
