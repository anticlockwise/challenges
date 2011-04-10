#!/usr/bin/python

import urllib, urllib2

message = "the flowers are on their way"
url = 'http://www.pythonchallenge.com/pc/stuff/violin.php'
req = urllib2.Request(url, headers={
    'Cookie': 'info=' + urllib.quote_plus(message)})
print urllib2.urlopen(req).read()
