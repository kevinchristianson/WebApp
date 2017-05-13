'''
    website.py
    Kevin Christianson and Isaac Haseley

    Flask app for our college metrics API. The API offers
    JSON access to the data, while the website (at
    route '/') offers end-user browsing of the data.
'''
import sys
import flask
import config
import json
import urllib.request

app = flask.Flask(__name__, static_folder='static', template_folder='templates')

@app.route('/')
def get_main_page():
    ''' This is the only route intended for human users '''
    return flask.render_template('index.html')

@app.route('/schools/search/<search_text>')
def get_school_search_page(search_text):
    # Retrieve a college's data from the API
    while ' ' in search_text:
        search_text = search_text[:search_text.index(' ')] + '_' + search_text[search_text.index(' ') + 1:]
    api_url = config.api_base_url + 'schools/search/' + search_text
    data_from_server = urllib.request.urlopen(api_url).read()
    string_from_server = data_from_server.decode('utf-8')
    college_list = json.loads(string_from_server)

    # Retrieve a college's image from Bing's image API
    if (len(college_list) == 1):
        search_name = college_list[0]['name'].lower()
        while ' ' in search_name:
            search_name = search_name[:search_name.index(' ')] + '_' + search_name[search_name.index(' ') + 1:]
        image_url = config.images_base_url + 'name=' + search_name + '&state=' + college_list[0]['state'].lower()
        data_from_image_server = urllib.request.urlopen(image_url).read()
        image_data = json.loads(data_from_image_server.decode('utf-8'))
        college_list.append(image_data)
        return flask.render_template('college_page.html', message = college_list)

    return flask.render_template('state_page.html', message = college_list)

@app.route('/schools/by_state/<search_text>')
def get_state_search_page(search_text):
    # Retrieve a state's schools from the API
    while ' ' in search_text:
        search_text = search_text[:search_text.index(' ')] + '_' + search_text[search_text.index(' ') + 1:]
    api_url = config.api_base_url + 'schools/by_state/' + search_text
    data_from_server = urllib.request.urlopen(api_url).read()
    string_from_server = data_from_server.decode('utf-8')
    college_list = json.loads(string_from_server)
    return flask.render_template('state_page.html', message = college_list)

if __name__ == '__main__':
    if len(sys.argv) != 3:
        print('Usage: {0} host port'.format(sys.argv[0]), file=sys.stderr)
        exit()

    host = sys.argv[1]
    port = int(sys.argv[2])
    app.run(host=host, port=port, debug=True)