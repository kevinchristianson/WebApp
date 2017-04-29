# api.py
# Kevin Christianson and Isaac Haseley

# !/usr/bin/env python3
'''
    books_api.py
    Jeff Ondich, 25 April 2016
    Simple Flask API used in the sample web app for
    CS 257, Spring 2016-2017. This is the Flask app for the
    "books and authors" API only. There's a separate Flask app
    for the books/authors website.
'''
import sys
import flask
import json
import config
import psycopg2

app = flask.Flask(__name__, static_folder='static', template_folder='templates')


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


@app.route('/schools/search/<search_text>')
def get_school(search_text):
    '''
    Returns a list of all the authors in our database, in alphabetical
    order by last name, then first_name. See get_author_by_id below
    for description of the author resource representation.
        http://.../authors/
        http://.../authors/?sort=last_name
        http://.../authors/?sort=birth_year
    '''
    query = '''SELECT name, state_id, in_state_tuition, out_state_tuition, acceptance_rate, designation, size, midpoint_ACT,
      midpoint_SAT_math, midpoint_SAT_write, FROM schools WHERE name = {0} ORDER BY name'''.format(search_text)
    school_list = []

    rows = _fetch_all_rows_for_query(query)
    if len(rows) == 0:
        return json.dumps([])
    for row in _fetch_all_rows_for_query(query):
        url = flask.url_for('get_school', search_text=row[1], _external=True)
        school = {'name': row[0], 'state': GET STATE HERE, 'in_state': row[2], 'out_of_state': row[3],
                  'acceptance_rate': row[4], 'designation': row[5], 'size': row[6], '' 'url': url}
        school_list.append(school)

    return json.dumps(school_list)

@app.route('/schools/by_state/<state_abbreviation>')
def get_schools_by_state(state):
    '''
    :param state: two letter state abbreviation of any case
    :return: list of dictionaries for all schools in that state
    Returns a list of all the schools in the specified state
    '''
    query = '''SELECT schools.id, schools.name, states.id
                   FROM schools, states
                   WHERE states.name = {0) and schools.state = states.id
                   ORDER BY schools.name'''.format(state.lower())
    school_list = []
    for row in _fetch_all_rows_for_query(query):
        url = flask.url_for('/schools/search/', school_id=row[0], _external=True)
        school = {'id': row[0], 'name': row[1], 'url': url}
        school_list.append(school)
    return json.dumps(school_list)

@app.route('/help')
def help():
    rule_list = []
    for rule in app.url_map.iter_rules():
        rule_text = rule.rule.replace('<', '&lt;').replace('>', '&gt;')
        rule_list.append(rule_text)
    return json.dumps(rule_list)


if __name__ == '__main__':
    if len(sys.argv) != 3:
        print('Usage: {0} host port'.format(sys.argv[0]), file=sys.stderr)
        exit()

    host = sys.argv[1]
    port = sys.argv[2]
    app.run(host=host, port=int(port), debug=True)