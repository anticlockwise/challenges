#!/usr/bin/python
# -*- coding: utf-8 -*-

'''
File: level1.py
Author: rshen <anticlockwise5@gmail.com>
Description: The Python Challenge Level 1: http://www.pythonchallenge.com/pc/def/map.html
'''

import string

trans_table = string.maketrans("abcdefghijklmnopqrstuvwxyz",
        "cdefghijklmnopqrstuvwxyzab")
data = """g fmnc wms bgblr rpylqjyrc gr zw fylb. rfyrq ufyr amknsrcpq ypc dmp. bmgle gr gl zw fylb gq glcddgagclr ylb rfyr'q ufw rfgq rcvr gq qm jmle. sqgle qrpgle.kyicrpylq() gq pcamkkclbcb. lmu ynnjw ml rfc spj."""
url  = "map"
print string.translate(data, trans_table)
print "Answer: %s" % string.translate(url, trans_table)
