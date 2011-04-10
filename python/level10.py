#!/usr/bin/python
# -*- coding: utf-8 -*-

'''
File: level10.py
Author: rshen <anticlockwise5@gmail.com>
Description: The Python Challenge Level 10 (What are you looking at?): http://www.pythonchallenge.com/pc/return/bull.html
'''

a = 1
for i in range(30):
    num_digit = 0
    prev_digit = None
    new_a, k = 0, 1
    while a != 0:
        digit = a % 10
        a = a / 10
        if prev_digit is None or prev_digit == digit:
            num_digit += 1
        else:
            new_a = new_a + (num_digit * (10**k)) + (prev_digit * (10**(k-1)))
            k += 2
            num_digit = 1
        prev_digit = digit

    new_a = new_a + (num_digit * (10**k)) + (prev_digit * (10**(k-1)))
    a = new_a

print len(str(a))
