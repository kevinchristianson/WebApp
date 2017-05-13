#!/usr/bin/env python
'''
    config.py
    Kevin Christianson and Isaac Haseley
    Database login info for our college metrics web app
'''

# Change these values as appropriate for your postgresql setup.
kevin = True
thacker = False
if kevin:
    database = 'kevinchristianson'
    user = 'kevinchristianson'
    password = ''
else:
    database = 'CollegeMetrics'
    user = 'haseleyi'
    password = ''
if thacker:
    database = 'christiansonk'
    user = 'christiansonk'
    password = 'carpet784books'
    api_base_url = 'http://thacker.mathcs.carleton.edu:5107/'
    website_base_url = 'http://thacker.mathcs.carleton,edu:5207/'
    images_base_url = 'http://thacker.mathcs.carleton.edu:5116/image?'
    host = 'thacker.mathcs.carleton.edu'
    api_port = 5107
    website_port = 5207
    images_port = 5116
else:
    api_base_url = 'http://localhost:8080/'
    website_base_url = 'http://localhost:8081/'
    images_base_url = 'http://localhost:8082/image?'
    host = 'localhost'
    api_port = 8080
    website_port = 8081
    images_port = 8082