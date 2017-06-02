'''
    api.py
    Kevin Christianson and Isaac Haseley
    
    Flask API for our college metrics web app
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


def format_school(school):
    '''
    Adds % to acceptance rate, comma to tuition, 'Data not available' when that's the case
    '''
    if school['acceptance_rate'] is not None:
        school['acceptance_rate'] = str(round(school['acceptance_rate'] * 100, 2)) +'%'
    if (school['in_state_tuition'] is not None) and (len(str(school['in_state_tuition'])) == 4):
        school['in_state_tuition'] = str(school['in_state_tuition'])[0] + ',' \
        + str(school['in_state_tuition'])[1:]
    elif (school['in_state_tuition'] is not None) and (len(str(school['in_state_tuition'])) == 5):
        school['in_state_tuition'] = str(school['in_state_tuition'])[0:2] + ',' \
        + str(school['in_state_tuition'])[2:]
    if (school['out_state_tuition'] is not None) and (len(str(school['out_state_tuition'])) == 4):
        school['out_state_tuition'] = str(school['out_state_tuition'])[0] + ',' \
        + str(school['out_state_tuition'])[1:]
    elif (school['out_state_tuition'] is not None) and (len(str(school['out_state_tuition'])) == 5):
        school['out_state_tuition'] = str(school['out_state_tuition'])[0:2] + ',' \
        + str(school['out_state_tuition'])[2:]
    for key, value in school.items():
        if value == None:
            school[key] = 'Data not available'


@app.route('/schools/search/<search_text>')
def get_school(search_text):
    '''
    :param search_text
    :return: A list of all the schools in our database, in alphabetical order, that match the search text. If
        a school name matches the search text exactly, just returns that school.
    '''
    while '_' in search_text:
        search_text = search_text[:search_text.index('_')] + ' ' + search_text[search_text.index('_') + 1:]
    word_list = search_text.split()
    search_text = ''
    for word in word_list:
        if "'" in word:
            word = word[:word.index("'")] + "''" + word[word.index("'") + 1:]
        search_text += word + ' '
    search_text = search_text[:-1]
    while ';' in search_text:
        search_text = search_text[:search_text.index(';')] + search_text[search_text.index(';') + 1:]
    query = '''SELECT schools.name, states.name, schools.in_state_tuition, schools.out_state_tuition,
               schools.acceptance_rate, schools.designation, schools.size, schools.midpoint_ACT,
               schools.midpoint_SAT, schools.school_site, schools.state_id
               FROM schools, states
               WHERE LOWER(schools.name) LIKE '%{0}%' and schools.state_id = states.id
               ORDER BY schools.name'''.format(search_text.lower())
    school_list = []
    for row in _fetch_all_rows_for_query(query):
        name = row[0]
        while ' ' in name:
            name = name[:name.index(' ')] + '_' + name[name.index(' ') + 1:]
        url = config.website_base_url + 'schools/search/' + name
        school = {'name': row[0], 'state': row[1], 'in_state_tuition': row[2], 'out_state_tuition': row[3],
                  'acceptance_rate': row[4], 'designation': row[5], 'size': row[6], 'midpoint_ACT': row[7],
                  'midpoint_SAT': row[8], 'school_site': row[9], 'url': url}
        format_school(school)
        school_list.append(school)
    for school in school_list:
        if school['name'] == search_text:
            exact_match = []
            exact_match.append(school)
            return json.dumps(exact_match)
    return json.dumps(school_list)


@app.route('/schools/by_state/<search_text>/')
def get_schools_by_state(search_text):
    '''
    :param state: A two-character state abbreviation
    :return: A list of all schools in that state
    '''
    while '_' in search_text:
        search_text = search_text[:search_text.index('_')] + ' ' + search_text[search_text.index('_') + 1:]
    if len(search_text) == 2:
        search_text = search_text.upper()
    else:
        search_text = search_text.title()
    if search_text.lower() == "district of columbia":
        search_text = "District of Columbia" 
    query = '''SELECT schools.state_id, schools.name, states.id, states.abbrev, states.name
               FROM schools, states
               WHERE schools.state_id = states.id and (states.abbrev = '{0}' or states.name = '{0}')
               ORDER BY schools.name'''.format(search_text)
    school_list = []
    added_state_name = False
    for row in _fetch_all_rows_for_query(query):
        if added_state_name == False:
            school_list.append(row[4])
            added_state_name = True
        school_name = row[1]
        while ' ' in school_name:
            school_name = school_name[:school_name.index(' ')] + '_' + school_name[school_name.index(' ') + 1:]
        url = config.website_base_url + 'schools/search/' + school_name
        school = [row[1], url]
        school_list.append(school)
    return json.dumps(school_list)


@app.route('/schools/all')
def get_all_schools():
    query = '''SELECT schools.name, states.name, schools.in_state_tuition, schools.out_state_tuition,
               schools.acceptance_rate, schools.designation, schools.size, schools.midpoint_ACT,
               schools.midpoint_SAT, schools.school_site, schools.state_id
               FROM schools, states
               WHERE schools.state_id = states.id
               ORDER BY schools.name''' 
    school_list = []
    for row in _fetch_all_rows_for_query(query):
        name = row[0]
        while ' ' in name:
            name = name[:name.index(' ')] + '_' + name[name.index(' ') + 1:]
        url = config.website_base_url + 'schools/search/' + name
        school = {'name': row[0], 'state': row[1], 'in_state_tuition': row[2], 'out_state_tuition': row[3],
                  'acceptance_rate': row[4], 'designation': row[5], 'size': row[6], 'midpoint_ACT': row[7],
                  'midpoint_SAT': row[8], 'school_site': row[9], 'url': url}
        format_school(school)
        school_list.append(school)
    return json.dumps(school_list)


@app.route('/help')
def help():
    '''
    :return: A list of API routes
    '''
    rule_list = []
    for rule in app.url_map.iter_rules():
        rule_text = rule.rule.replace('<', '&lt;').replace('>', '&gt;')
        if rule_text != '/schools/by_state/' and rule_text != '/schools/search/' and not '/static/' in  rule_text:
            rule_list.append(rule_text)
    return json.dumps(rule_list)


@app.route('/state_options')
def options():
    '''
    :return: A list of possible state abbreviations and names
    '''
    states_list = []
    query = '''SELECT states.abbrev, states.name FROM states ORDER BY states.abbrev'''
    for row in _fetch_all_rows_for_query(query):
        url = flask.url_for('get_schools_by_state', search_text=row[0], _external=True)
        result = [row[0], row[1], url]
        states_list.append(result)
    return json.dumps(states_list)


@app.route('/schools/search/')
def no_param_search():
    return json.dumps([])


@app.route('/schools/by_state/')
def no_param_state():
    return json.dumps([])


@app.errorhandler(404)
def page_not_found(e):
    return json.dumps(['PAGE NOT FOUND, AND YET HERE YOU ARE','SEE HELP FOR MORE INFO: http://thacker.mathcs.carleton.edu:5207/help'])


if __name__ == '__main__':
    app.run(host=config.host, port=int(config.api_port), debug=True)