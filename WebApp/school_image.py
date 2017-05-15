'''
    school_image.py
    Kevin Christianson and Isaac Haseley

    Connects our web app to the Bing image API
'''

# Documentation: https://github.com/tristantao/py-ms-cognitive
from py_ms_cognitive import PyMsCognitiveImageSearch
import flask
import json
from flask import request
import config

app = flask.Flask(__name__)

@app.route('/image')
def get_image():
    '''
    Hits the the Bing image API to get an image of the college searched for
    Example call route: localhost:8082/image?name=carleton_college&state=minnesota
    '''
    school_name = request.args.get('name')
    school_state_abbrev =  request.args.get('state')
    # Desired_aspect_ratio = 4 / 3
    while '_' in school_name:
        school_name = school_name[:school_name.index('_')] + ' ' + school_name[school_name.index('_') + 1:]
    search_service = PyMsCognitiveImageSearch('e38d51f4b70944d0aa04339ddf1467f4', school_name + ' ' + school_state_abbrev)
    results= search_service.search(limit=1, format='json')
    result = results[0]
    return json.dumps([result.content_url, result.host_page_url])


@app.errorhandler(404)
def wrong_call(e):
    return json.dumps([])


if __name__ == '__main__':
    app.run(host=config.host, port=config.images_port, debug=True)