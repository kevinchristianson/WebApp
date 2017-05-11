'''
    school_image.py
    Kevin Christianson and Isaac Haseley

    hits the the bing image search api to get an image of the college searched for
'''
from py_ms_cognitive import PyMsCognitiveImageSearch
#documentation: https://github.com/tristantao/py-ms-cognitive
import flask
import json
from flask import request

app = flask.Flask(__name__)

#example call route: localhost:8082/image?name=carleton_college&state=minnesota
@app.route('/image')
def get_image():
    school_name = request.args.get('name')
    school_state_abbrev =  request.args.get('state')
    #desired_aspect_ratio = 4 / 3
    while '_' in school_name:
        school_name = school_name[:school_name.index('_')] + ' ' + school_name[school_name.index('_') + 1:]
    search_service = PyMsCognitiveImageSearch('e38d51f4b70944d0aa04339ddf1467f4', school_name + ' ' + school_state_abbrev)
    results= search_service.search(limit=1, format='json')
    '''  aspect_ratios = []
    for item in results:
        aspect_ratios.append(item.width / item.height)
    closest = 0
    location = 0
    for i in range(len(aspect_ratios)):
        if(abs(desired_aspect_ratio-closest) > abs(desired_aspect_ratio-aspect_ratios[i])):
            closest = aspect_ratios[i]
            location = i
    result = results[location]
    '''
    result = results[0]
    return json.dumps([result.content_url, result.host_page_url])

@app.errorhandler(404)
def wrong_call(e):
    return json.dumps([])

if __name__ == '__main__':
    app.run(host='localhost', port=8082, debug=True)