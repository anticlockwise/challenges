#!/usr/bin/python
# -*- coding: utf-8 -*-

'''
File: level13.py
Author: rshen <anticlockwise5@gmail.com>
Description: The Python Challenge Level 13 (Call Him): http://www.pythonchallenge.com/pc/return/disproportional.html
'''

import xmlrpclib
import sys

phone_book_url = "http://www.pythonchallenge.com/pc/phonebook.php"
proxy = xmlrpclib.ServerProxy(phone_book_url)
print proxy.phone(sys.argv[1])
