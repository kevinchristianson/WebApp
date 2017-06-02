#!/usr/bin/env python
'''
    config.py
    Kevin Christianson and Isaac Haseley
    Info for database login and server ports
'''

# Change these values as appropriate for your setup.
kevin = False
thacker = False
if kevin:
    database = 'kevinchristianson'
    user = 'kevinchristianson'
    password = ''
else:
    database = 'collegemetrics'
    user = 'haseleyi'
    password = ''
if thacker:
    if kevin:
        database = 'christiansonk'
        user = 'christiansonk'
        password = 'carpet784books'
    else:
        database = 'haseleyi'
        user = 'haseleyi'
        password = 'seal522field'
    api_base_url = 'http://thacker.mathcs.carleton.edu:5107/'
    website_base_url = 'http://thacker.mathcs.carleton.edu:5207/'
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
