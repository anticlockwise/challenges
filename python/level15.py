#!/usr/bin/python
# -*- coding: utf-8 -*-

'''
File: level15.py
Author: rshen <anticlockwise5@gmail.com>
Description: The Python Challenge Level 15 (Whom?): http://www.pythonchallenge.com/pc/return/uzi.html
'''

import datetime

# he ain't the youngest, he is the second
# todo: buy flowers for tomorrow
# Check on Google!

max_year = 1996
min_year = 1006

for year in range(min_year, max_year+1, 10):
    if year % 4 == 0: # It's a leap year
        d = datetime.date(year, 1, 26)
        if d.weekday() == 0:
            print d
