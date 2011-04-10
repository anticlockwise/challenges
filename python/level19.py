#!/usr/bin/python
# -*- coding: utf-8 -*-

'''
File: level19.py
Author: rshen <anticlockwise5@gmail.com>
Description: The Python Challenge Level 18 (Please!): http://www.pythonchallenge.com/pc/hex/bin.html
'''

import urllib
import base64
import wave
import array

hex_url = "http://butter:fly@www.pythonchallenge.com/pc/hex/bin.html"
req = urllib.urlopen(hex_url)
lines = req.readlines()[27:1986]
wav_data = base64.b64decode("".join(lines))
open("indian.wav", 'wb').write(wav_data)

wi = wave.open("indian.wav", 'rb')
wo = wave.open("indian_inv.wav", 'wb')

nframes = wi.getnframes()
b = array.array('i')
b.fromstring(wi.readframes(nframes))
# Invert the bytes in the frames
b.byteswap()

wo.setparams(wi.getparams())
wo.writeframes(b.tostring())

wi.close()
wo.close()
