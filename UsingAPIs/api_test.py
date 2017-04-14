'''
    api_test.py
    Kevin Christianson and Isaac Haseley, 12 April 2017

    Draws and displays data from the College Scorecard API
     
    Instructions for command line arguments:
        To search for data on a college or university: search <Institution Name>
        To view an alphabetical list of all U.S. institutions and their locations: list
'''

import sys
import argparse
import json
import urllib.request


# Searches College Scorecard API for requested institution
# Displays data thereon
def get_college_data(college):
    search_name = college
    base_url = 'https://api.data.gov/ed/collegescorecard/v1/schools?school.name={0}' \
               '&_fields=school.name,school.state,2014.admissions.act_scores.midpoint.cumulative,' \
               '2014.admissions.sat_scores.midpoint.math,2014.admissions.sat_scores.midpoint.writing,' \
               '2014.admissions.admission_rate.overall,2014.student.size,2014.cost.tuition.in_state,' \
               '2014.cost.tuition.out_of_state&api_key=T1gcLItns6RaFOZsvGdmfn0hrZwrVjxd3PsAx0Zp'
    while ' ' in college:
        college = college[:college.index(' ')] + '%20' + college[college.index(' ') + 1:]
    url = base_url.format(college)
    data_from_server = urllib.request.urlopen(url).read()
    string_from_server = data_from_server.decode('utf-8')
    data = json.loads(string_from_server)
    if len(data['results']) == 0:
        print("The institution you requested does not match one in our database.")
        return
    dictionary = data['results'][0]
    name = dictionary['school.name']
    i = 0
    while (name.lower() != search_name.lower() and len(data['results']) > i + 1):
        i += 1
        dictionary = data['results'][i]
        name = dictionary['school.name']
    if name.lower() != search_name.lower():
        print("The institution you requested does not match one in our database.")
        if len(data['results']) > 0:
            print("You may be looking for one of these:")
            for i in range(len(data['results'])):
                dictionary = data['results'][i]
                name = dictionary['school.name']
                print(name)
        return
    tuition_out_state = dictionary['2014.cost.tuition.out_of_state']
    tuition_in_state = dictionary['2014.cost.tuition.in_state']
    size = dictionary['2014.student.size']
    sat_math = dictionary['2014.admissions.sat_scores.midpoint.math']
    sat_writing = dictionary['2014.admissions.sat_scores.midpoint.writing']
    act = dictionary['2014.admissions.act_scores.midpoint.cumulative']
    state = dictionary['school.state']
    rate = dictionary['2014.admissions.admission_rate.overall']
    if state != None:
        print(name, 'is in', state)
    else:
        print(name)
    if size != None:
        print('Size:', size)
    if tuition_in_state != None and tuition_out_state != None:
        print('Tuition: ', ' In State:', tuition_in_state, '  Out of State:', tuition_out_state)
    if rate != None:
        print('Admission Rate:', '%.2f' % (rate * 100), '%')
    if act != None:
        print('Midpoint ACT Score:', act)
    if sat_math != None and sat_writing != None:
        print('Midpoint SAT Math Score:', sat_math)
        print('Midpoint SAT Writing Score:', sat_writing)


# Displays alphabetical list of all U.S. colleges and universities
def get_college_list():
        for page_number in range(78):
            base_url = 'https://api.data.gov/ed/collegescorecard/v1/schools.json?&_fields=school.name,' \
                       'school.state&_sort=school.name&_page={0}&_per_page=100&api_key=T1gcLItns6RaFOZsv' \
                       'Gdmfn0hrZwrVjxd3PsAx0Zp'
            url = base_url.format(page_number)
            data_from_server = urllib.request.urlopen(url).read()
            string_from_server = data_from_server.decode('utf-8')
            data = json.loads(string_from_server)
            for college in range(len(data['results'])):
                dictionary = data['results'][college]
                name = dictionary['school.name']
                state = dictionary['school.state']
                print(name, '---', state)


def main(args, college_name):
    if (args.action == 'search'):
        if (college_name != ''):
            get_college_data(college_name)
        else:
            print('Please add an institution name following "search"')
    elif (args.action == 'list'):
        get_college_list()


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description='Get college info from the College Scorecard API')
    parser.add_argument('action',
                        metavar = 'action',
                        help = 'action to perform on data ("list" or "search")',
                        choices = ['list', 'search'])
    parsed, unknown = parser.parse_known_args()
    for arg in unknown:
        parser.add_argument('arg')
    args = parser.parse_args()
    college_name = ''
    for i in range(2, len(sys.argv)):
        college_name = college_name + sys.argv[i] + ' '
    college_name = college_name[:-1]
    main(args, college_name)