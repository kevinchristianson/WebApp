'''
    api_test.py
    Jeff Ondich, 11 April 2016

    An example for CS 257 Software Design. How to retrieve results
    from an HTTP-based API, parse the results (JSON in this case),
    and manage the potential errors.
'''

import sys
import argparse
import json
import urllib.request


def get_root_words(word, language):
    '''
    Returns a list of root words for the specified word in the
    specified language. The root words are represented as
    dictionaries of the form

       {'root':root_word, 'partofspeech':part_of_speech}

    For example, the results for get_root_words('thought', 'eng')
    would be:

       [{'root':'thought', 'partofspeech':'noun'},
        {'root':'think', 'partofspeech':'verb'}]

    The language parameter must be a 3-letter ISO language code
    (e.g. 'eng', 'fra', 'deu', 'spa', etc.).

    Raises exceptions on network connection errors and on data
    format errors.
    '''
    base_url = 'http://developer.ultralingua.com/api/stems/{0}/{1}'
    url = base_url.format(language, word)
    data_from_server = urllib.request.urlopen(url).read()
    string_from_server = data_from_server.decode('utf-8')
    root_word_list = json.loads(string_from_server)
    result_list = []
    for root_word_dictionary in root_word_list:
        root = root_word_dictionary['text']
        part_of_speech = root_word_dictionary['partofspeech']
        part_of_speech_category = part_of_speech['partofspeechcategory']
        if type(root) != type(''):
            raise Exception('root has wrong type: "{0}"'.format(root))
        if type(part_of_speech) != type(''):
            raise Exception('part of speech has wrong type: "{0}"'.format(part_of_speech))
        result_list.append({'root': root, 'partofspeech': part_of_speech})
    return result_list

def get_college_data(college):
    '''
   commments. Woo!
    '''
    search_name = college;
    base_url = 'https://api.data.gov/ed/collegescorecard/v1/schools?school.name={0}' \
               '&_fields=school.name,school.state,2014.admissions.act_scores.midpoint.cumulative,' \
               '2014.admissions.sat_scores.midpoint.math,2014.admissions.sat_scores.midpoint.writing,' \
               '2014.admissions.admission_rate.overall,2014.student.size,2014.cost.tuition.in_state,' \
               '2014.cost.tuition.out_of_state&api_key=T1gcLItns6RaFOZsvGdmfn0hrZwrVjxd3PsAx0Zp'
    while ' ' in college:
        college = college[:college.index(' ')] + '%20' + college[college.index(' ')+1:]
    url = base_url.format(college)
    data_from_server = urllib.request.urlopen(url).read()
    string_from_server = data_from_server.decode('utf-8')
    data = json.loads(string_from_server)
    i = 0;
    dictionary = data['results'][i]
    name = dictionary['school.name']
    while(name.lower() != search_name.lower() and len(data['results']) > 1):
        i+=1
        dictionary = data['results'][i]
        name = dictionary['school.name']
    if name.lower() != search_name.lower():
        print("INVALID INPUT")
        return
    tuition_out_state = dictionary['2014.cost.tuition.out_of_state']
    tuition_in_state = dictionary['2014.cost.tuition.in_state']
    size = dictionary['2014.student.size']
    math = dictionary['2014.admissions.sat_scores.midpoint.math']
    writing = dictionary['2014.admissions.sat_scores.midpoint.writing']
    act = dictionary['2014.admissions.act_scores.midpoint.cumulative']
    state = dictionary['school.state']
    rate = dictionary['2014.admissions.admission_rate.overall']
    print(name + ' is in ' + state)
    print('Size: ', size)
    print('Tuition: ', 'In State: ', tuition_in_state, ' Out of State: ', tuition_out_state)
    print('Admission Rate: ', '%.2f' % (rate * 100), '%')
    print('Midpoint ACT Score: ', act)
    print('Midpoint SAT Math Score: ', math)
    print('Midpoint SAT Writing Score ', writing)



def get_conjugations(verb, language):
    '''
    Returns a list of conjugations for the specified verb in the
    specified language. The conjugations are represented as
    dictionaries of the form

       {'text':..., 'tense':..., 'person':..., 'number':...}

    For example, the results for get_root_words('parler', 'fra')
    would include:

       [{'text':'parle', 'tense':'present', 'person':'first', 'number':'singular'},
        {'text':'parles', 'tense':'present', 'person':'second', 'number':'singular'},
        ...
       ]

    The language parameter must be a 3-letter ISO language code
    (e.g. 'eng', 'fra', 'deu', 'spa', etc.).

    Raises exceptions on network connection errors and on data
    format errors.
    '''
    base_url = 'http://developer.ultralingua.com/api/conjugations/{0}/{1}'
    url = base_url.format(language, verb)

    data_from_server = urllib.request.urlopen(url).read()
    string_from_server = data_from_server.decode('utf-8')
    conjugation_list = json.loads(string_from_server)

    result_list = []
    for conjugation_dictionary in conjugation_list:
        text = conjugation_dictionary.get('surfaceform', '')
        tense = conjugation_dictionary['partofspeech'].get('tense', '')
        person = conjugation_dictionary['partofspeech'].get('person', '')
        number = conjugation_dictionary['partofspeech'].get('number', '')
        if type(text) != type(''):
            raise Exception('text has wrong type: "{0}"'.format(text))
        if type(tense) != type(''):
            raise Exception('tense has wrong type: "{0}"'.format(tense))
        if type(person) != type(''):
            raise Exception('person has wrong type: "{0}"'.format(person))
        if type(number) != type(''):
            raise Exception('number has wrong type: "{0}"'.format(number))
        result_list.append({'text': text, 'tense': tense, 'person': person, 'number': number})

    return result_list


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
    # When I use argparse to parse my command line, I usually
    # put the argparse setup here in the global code, and then
    # call a function called main to do the actual work of
    # the program.
    '''
    Jeff's code
    parser = argparse.ArgumentParser(description='Get word info from the Ultralingua API')

    parser.add_argument('action',
                        metavar='action',
                        help='action to perform on the word ("root" or "conjugate")',
                        choices=['root', 'conjugate'])

    parser.add_argument('language',
                        metavar='language',
                        help='the language as a 3-character ISO code',
                        choices=['eng', 'fra', 'spa', 'deu', 'ita', 'por'])

    parser.add_argument('word', help='the word you want to act on')
'''

    parser = argparse.ArgumentParser(description='Get college info from the College Scorecard API')
    parser.add_argument('name',
                        metavar='name',
                        help='name of school to search')
    args = parser.parse_args()
    get_college_data(args.name)