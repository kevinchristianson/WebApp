'''
    api.py
    Kevin Christianson and Isaac Haseley
    Simple Flask API for our college metrics web app
'''
import sys
import flask
import json
import config
import psycopg2


app = flask.Flask(__name__)


def _fetch_all_rows_for_query(query):
    '''
    Returns a list of rows obtained from the books database by the specified SQL
    query. If the query fails for any reason, an empty list is returned.
    Note that this is not necessarily the right error-handling choice. Would users
    of the API like to know the nature of the error? Do we as API implementors
    want to share that information? There are many considerations to balance.
    '''
    try:
        connection = psycopg2.connect(database=config.database, user=config.user, password=config.password)
    except Exception as e:
        print('Connection error:', e, file=sys.stderr)
        return []

    rows = []
    try:
        cursor = connection.cursor()
        cursor.execute(query)
        rows = cursor.fetchall()  # This can be trouble if your query results are really big.
    except Exception as e:
        print('Error querying database:', e, file=sys.stderr)

    connection.close()
    return rows


@app.after_request
def set_headers(response):
    response.headers['Access-Control-Allow-Origin'] = '*'
    return response

@app.route('/schools/search/')
def no_param_search():
    return json.dumps([])


@app.route('/schools/search/<search_text>')
def get_school(search_text):
    '''
    :param search_text
    :return: A list of all the schools in our database, in alphabetical order, that match the search text
    '''
    while '_' in search_text:
        search_text = search_text[:search_text.index('_')] + ' ' + search_text[search_text.index('_') + 1:]
    word_list = search_text.split()
    search_text = ''
    for word in word_list:
        if (word != 'at' and word != 'of'):
            word = word.title()
        search_text += word + ' '
    search_text = search_text[:-1]
    query = '''SELECT schools.name, states.name, schools.in_state_tuition, schools.out_state_tuition,
               schools.acceptance_rate, schools.designation, schools.size, schools.midpoint_ACT,
               schools.midpoint_SAT, schools.school_site, schools.state_id
               FROM schools, states
               WHERE schools.name LIKE '%{0}%' and schools.state_id = states.id
               ORDER BY schools.name'''.format(search_text)
    school_list = []
    for row in _fetch_all_rows_for_query(query):
        url = flask.url_for('get_school', search_text=row[0], _external=True)
        school = {'name': row[0], 'state': row[1], 'in_state_tuition': row[2], 'out_state_tuition': row[3],
                  'acceptance_rate': row[4], 'designation': row[5], 'size': row[6], 'midpoint_ACT':row[7],
                  'midpoint_SAT': row[8], 'school_site': row[9], 'url': url}
        school_list.append(school)

    return json.dumps(school_list)


@app.route('/schools/by_state/')
def no_param_state():
    return json.dumps([])


@app.route('/schools/by_state/<state_abbreviation>/')
def get_schools_by_state(state_abbreviation):
    '''
    :param state: A two-character state abbreviation
    :return: A list of all schools in that state
    '''
    query = '''SELECT schools.state_id, schools.name, states.id, states.abbrev
               FROM schools, states
               WHERE states.abbrev = '{0}' and schools.state_id = states.id
               ORDER BY schools.name'''.format(state_abbreviation.upper())

    school_list = []
    for row in _fetch_all_rows_for_query(query):
        url = flask.url_for('get_school', search_text=row[1], _external=True)
        school = [row[1], url]
        school_list.append(school)
    return json.dumps(school_list)


@app.route('/help')
def help():
    rule_list = []
    for rule in app.url_map.iter_rules():
        rule_text = rule.rule.replace('<', '&lt;').replace('>', '&gt;')
        rule_list.append(rule_text)
    return json.dumps(rule_list)


@app.errorhandler(404)
def page_not_found(e):
    return json.dumps(['PAGE NOT FOUND, AND YET HERE YOU ARE','SEE HELP FOR MORE INFO: http://thacker.mathcs.carleton.edu:5107/help'])


if __name__ == '__main__':
    app.run(host='thacker.mathcs.carleton.edu', port=5107)