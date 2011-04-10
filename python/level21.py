#!/usr/bin/python
# -*- coding: utf-8 -*-

'''
File: level21.py
Author: rshen <anticlockwise5@gmail.com>
Description: The Python Challenge Level 21
'''

import zlib
import gzip
import bz2

contents = open("package.pack", 'rb').read()
log = ''
log_len = len(log)
while True:
    try:
        contents = zlib.decompress(contents)
        log += ' '
    except:
        try:
            contents = bz2.decompress(contents)
            log += '#'
        except:
            if log_len == len(log): break
            contents = contents[::-1]
            print log[log_len:]
            log_len = len(log)

open("package.unpack", 'wb').write(contents)
