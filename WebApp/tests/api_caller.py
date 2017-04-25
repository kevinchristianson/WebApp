'''
api_caller.py
Isaac Haseley and Kevin Christianson

'''
import json
import urllib.request

def get_school(name):
    url = 'http://www.whatever.com/school/name={0}'
    url = url.format(name)
    return get_data(url)

def get_state(state):
    url = 'http://www.whatever.com/list/state={0}'
    url = url.format(state)
    return get_data(url)


def get_data(url):
    data_from_server = urllib.request.urlopen(url).read()
    string_from_server = data_from_server.decode('utf-8')
    data = json.loads(string_from_server)
    if len(data) == 0:
        return {}
    return data