'''
    api_test.py
    Kevin Christianson and Isaac Haseley, 12 April 2017

    TO DO
        Add list feature
        Print instructions
        Add functionality explanation to comment here
'''

import sys
import argparse
import json
import urllib.request


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
    print(name , end = '')
    if state != None:
        print(' is in ' + state)
    else:
        print()
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


def main(args):
    if args.action == 'root':
        root_words = get_root_words(args.word, args.language)
        for root_word in root_words:
            root = root_word['root']
            part_of_speech = root_word['partofspeech']
            print('{0} [{1}]'.format(root, part_of_speech))

    elif args.action == 'conjugate':
        conjugations = get_conjugations(args.word, args.language)
        for conjugation in conjugations:
            text = conjugation['text']
            tense = conjugation['tense']
            person = conjugation['person']
            number = conjugation['number']
            print('{0} [{1} {2} {3}]'.format(text, tense, person, number))


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description='Get college info from the College Scorecard API')
    parser.add_argument('action',
                        metavar = 'action',
                        help = 'action to perform on data ("list" or "search")',
                        choices = ['list', 'search'])
    parser.add_argument('name',
                        metavar='name',
                        help='name of school to search')
    parsed, unknown = parser.parse_known_args()
    if (parsed.action == 'list'):
        get_college_list()
    elif (parsed.action == 'search'):
        for arg in unknown:
            parser.add_argument('arg')
        args = parser.parse_args()
        college_name = ''
        for i in range(1, len(args)):
            college_name = college_name + args[i]
        if (college_name != None):
            get_college_data(college_name)
        else:
            print('Please specify a college to search for')
else:
    print("Invalid action - please run file")