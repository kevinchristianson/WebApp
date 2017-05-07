'''
api_caller.py
Isaac Haseley and Kevin Christianson

Convenience functions for testing our college metrics API
'''

import json
import urllib.request


def get_school_helper(name):
    '''
    :param name: A name of a U.S. college or university 
    :return: Data on the specified institution
    '''
    url = 'http://thacker.mathcs.carleton.edu:5107/schools/search/{0}'
    url = url.format(name)
    result = get_data(url)
    return result


def get_schools_by_state_helper(state):
    '''
    :param state: A two-character state abbreviation 
    :return: The colleges and universities located in the specified state
    '''
    if (len(state) > 2):
        return []
    url = 'http://thacker.mathcs.carleton.edu:5107/schools/by_state/{0}'
    url = url.format(state)
    result = get_data(url)
    return result

def get_states_options():
    return get_data('http://thacker.mathcs.carleton.edu:5107/states/options/')



def get_data(url):
    '''
    :param url: A URL to query our college metrics API
    :return: The query result
    '''
    data_from_server = urllib.request.urlopen(url).read()
    string_from_server = data_from_server.decode('utf-8')
    data = json.loads(string_from_server)
    return data