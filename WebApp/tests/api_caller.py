'''
api_caller.py
Isaac Haseley and Kevin Christianson

Convenience functions for calling our college metrics API
'''

import json
import urllib.request


# @param name: A name of a U.S. college or university
# @return data on the specified institution
def get_school(name):
    url = 'http://www.whatever.com/schools/{0}'
    while ' ' in name:
        name = name[:name.index(' ')] + '_' + name[name.index(' ') + 1:]
    url = url.format(name)
    result = get_data(url)
    if (result == None):
        return {}
    return result


# @param state: A two-character state abbreviation
# @return the colleges and universities located in the specified state
def get_state(state):
    if (len(state) > 2):
        return []
    url = 'http://www.whatever.com/states/{0}'
    url = url.format(state)
    result = get_data(url)
    if (result == None):
        return []
    return result


# @param url: A URL to query our college metrics API
# @return the query result
def get_data(url):
    data_from_server = urllib.request.urlopen(url).read()
    string_from_server = data_from_server.decode('utf-8')
    data = json.loads(string_from_server)
    if len(data) == 0:
        return None
    return data