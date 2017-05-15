'''
    website.py
    Kevin Christianson and Isaac Haseley

    Flask app for our college metrics API. The API offers
    JSON access to the data, while the website offers 
    end-user browsing of the data.
'''
import sys
import flask
import config
import json
import urllib.request
import os
import http.client

app = flask.Flask(__name__, static_folder='static', template_folder='templates')

@app.route('/')
def get_main_page():
    ''' This is the only route intended for human users '''
    return flask.render_template('index.html', message = ['', ''])


def format_tuition(college_list):
    '''
    Formats school designation (public/private/for-profit)
    Formats relevant tuition accordingly
    '''
    if college_list[0]['designation'] == 1:
        college_list[0]['designation'] = 'Public'
        college_list[0]['tuition_text'] = ['', 'In-state tuition: ', 'Out-of-state tuition: ']
        college_list[0]['tuition'] = ''
        if college_list[0]['in_state_tuition'] != 'Data not available':
            college_list[0]['in_state_tuition'] = '$' + college_list[0]['in_state_tuition']
            college_list[0]['out_state_tuition'] = '$' + college_list[0]['out_state_tuition']
    else:
        if college_list[0]['designation'] == 2:
            college_list[0]['designation'] = 'Private'
        elif college_list[0]['designation'] == 3:
            college_list[0]['designation'] = 'For-profit'
        college_list[0]['tuition_text'] = ['Tuition: ', '', '']
        if college_list[0]['in_state_tuition'] != 'Data not available':
            college_list[0]['tuition'] = '$' + college_list[0]['in_state_tuition']
        else:
            college_list[0]['tuition'] = college_list[0]['in_state_tuition']
        college_list[0]['in_state_tuition'] = ''
        college_list[0]['out_state_tuition'] = ''


def get_school_image(college_list):
    '''
    Retrieves an image from the Bing images API
    '''
    search_name = college_list[0]['name'].lower()
    while ' ' in search_name:
        search_name = search_name[:search_name.index(' ')] + '_' + search_name[search_name.index(' ') + 1:]
    image_url = config.images_base_url + 'name=' + search_name + '&state=' + college_list[0]['state'].lower()
    try:
        data_from_image_server = urllib.request.urlopen(image_url).read()
        image_data = json.loads(data_from_image_server.decode('utf-8'))
        college_list.append(image_data)
    except (urllib.error.HTTPError, urllib.error.URLError, http.client.BadStatusLine) as e:
        college_list.append(['http://www.wellesleysocietyofartists.org/wp-content/uploads/2015/11/image-not-found.jpg',
                             'http://www.wellesleysocietyofartists.org/image-not-found/'])


@app.route('/schools/search/<search_text>')
def get_school_search_page(search_text):
    '''
    If user search yields a single college, takes user to that college's page
    If it yields multiple colleges, offers user those options
    '''
    original_input = search_text
    while ' ' in search_text:
        search_text = search_text[:search_text.index(' ')] + '_' + search_text[search_text.index(' ') + 1:]
    api_url = config.api_base_url + 'schools/search/' + search_text
    data_from_server = urllib.request.urlopen(api_url).read()
    string_from_server = data_from_server.decode('utf-8')
    college_list = json.loads(string_from_server)
    if (len(college_list) == 1):
        format_tuition(college_list)
        get_school_image(college_list)
        return flask.render_template('college_page.html', message = college_list)
    elif len(college_list) > 1:
        college_list.insert(0, original_input)
        return flask.render_template('multiple_results_page.html', message = college_list)
    else:
        return flask.render_template('index.html', message = ['No Results Found', ''])

@app.route('/schools/by_state/<search_text>')
def get_state_search_page(search_text):
    '''
    Brings user to page displaying all colleges and universities in the state of interest
    '''
    while ' ' in search_text:
        search_text = search_text[:search_text.index(' ')] + '_' + search_text[search_text.index(' ') + 1:]
    api_url = config.api_base_url + 'schools/by_state/' + search_text
    data_from_server = urllib.request.urlopen(api_url).read()
    string_from_server = data_from_server.decode('utf-8')
    college_list = json.loads(string_from_server)
    if (len(college_list) == 0):
        return flask.render_template('index.html', message = ['', 'No Results Found'])
    return flask.render_template('state_page.html', message = college_list)


@app.route('/schools/search/')
def no_param_search():
    return flask.render_template('index.html', message = ['No Results Found', ''])


@app.route('/schools/by_state/')
def no_param_state():
    return flask.render_template('index.html', message = ['', 'No Results Found'])

if __name__ == '__main__':
    app.run(host=config.host, port=config.website_port, debug=True)