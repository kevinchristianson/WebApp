'''
    website.py
    Kevin Christianson and Isaac Haseley

    Flask app for our college metrics API. The API offers
    JSON access to the data, while the website (at
    route '/') offers end-user browsing of the data.
'''
import sys
import flask

app = flask.Flask(__name__, static_folder='static', template_folder='templates')

@app.route('/')
def get_main_page():
    ''' This is the only route intended for human users '''
    return flask.render_template('index.html')

@app.route('/schools/search/<search_text>')
def get_school_search_page(search_text):
    return flask.render_template('results_page.html')

if __name__ == '__main__':
    if len(sys.argv) != 3:
        print('Usage: {0} host port'.format(sys.argv[0]), file=sys.stderr)
        exit()

    host = sys.argv[1]
    port = int(sys.argv[2])
    app.run(host=host, port=port, debug=True)