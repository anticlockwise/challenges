#!/usr/bin/python
# -*- coding: utf-8 -*-

'''
File: level20.py
Author: rshen <anticlockwise5@gmail.com>
Description: The Python Challenge Level 20 (Go away!): http://www.pythonchallenge.com/pc/hex/idiot2.html
'''
import httplib
import urllib, optparse
import base64
from urllib2 import Request, urlopen
from sys import stdout

base64_login = base64.encodestring('%s:%s' % ("butter", "fly"))[:-1]
headers = {"Authorization": "Basic %s" % base64_login,
        'Range': "bytes=2123456789-"}
req = Request("http://www.pythonchallenge.com/pc/hex/unreal.jpg", headers=headers)
print urlopen(req).read()

for i in range(30202, 30300):
    base64_login = base64.encodestring('%s:%s' % ("butter", "fly"))[:-1]
    headers = {"Authorization": "Basic %s" % base64_login,
            'Range': "bytes=%s-%s" % (i,i+1)}
    req = Request("http://www.pythonchallenge.com/pc/hex/unreal.jpg", headers=headers)
    try:
        res = urlopen(req)
        print res.read()
    except Exception, e:
        stdout.write('.')
        stdout.flush()

f = open("private.zip", 'wb')
for i in range(1152983631, 1152983632):
    base64_login = base64.encodestring('%s:%s' % ("butter", "fly"))[:-1]
    headers = {"Authorization": "Basic %s" % base64_login,
            'Range': "bytes=%s-%s" % (i,i+1)}
    req = Request("http://www.pythonchallenge.com/pc/hex/unreal.jpg", headers=headers)
    try:
        res = urlopen(req)
        f.write(res.read())
    except Exception, e:
        stdout.write('.')
        stdout.flush()
