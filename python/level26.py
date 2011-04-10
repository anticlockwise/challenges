#!/usr/bin/python
# -*- coding: utf-8 -*-

'''
File: level26.py
Author: rshen <anticlockwise5@gmail.com>
Description: The Python Challenge Level 26
'''

import md5, array

good_md5 = "bbb8b499a0eef99b52c7f13f4e78c24b"
data = array.array('c', open("mybroken.zip", 'rb').read())
allchar = map(chr, range(256))
repaired = False
for i, oldchar in enumerate(data):
    for c in allchar:
        data[i] = c
        if md5.new(data).hexdigest() == good_md5:
            print "Repaired"
            repaired = True
            break
    if repaired:
        break

    data[i] = oldchar

open("repaired.zip", 'wb').write(data)
