'''
api_tests.py
Isaac Haseley and Kevin Christianson

'''

import unittest

class APITester(unittest.TestCase):
    def get_college(name):
        pass

    def setUp(self):
        pass

    def tearDown(self):
        pass

    def testNormalCaseUnderscoreCaps(self):
        result = get_college('Carleton_College')
        expectedCarleton = {'name': 'Carleton College', 'state': 'MN', 'in_state': 47736, 'out_of_state': 47736,
                    'acceptance_rate': 22.77, 'designation': 'private', 'size': 2042, 'midpoint_ACT': 32,
                    'midpoint_SAT_math': 710, 'midpoint_SAT_write': 700}
        if (result[0] != expectedCarleton):
            return False
        return True

    def testNormalCaseUnderscoreLower(self):
        result = get_college('carleton_college')
        expectedCarleton = {'name': 'Carleton College', 'state': 'MN', 'in_state': 47736, 'out_of_state': 47736,
                            'acceptance_rate': 22.77, 'designation': 'private', 'size': 2042, 'midpoint_ACT': 32,
                            'midpoint_SAT_math': 710, 'midpoint_SAT_write': 700}
        if (result[0] != expectedCarleton):
            return False
        return True

    def testNormalCaseUnderscoreMixedCaps(self):
        result = get_college('carleton_College')
        expectedCarleton = {'name': 'Carleton College', 'state': 'MN', 'in_state': 47736, 'out_of_state': 47736,
                            'acceptance_rate': 22.77, 'designation': 'private', 'size': 2042, 'midpoint_ACT': 32,
                            'midpoint_SAT_math': 710, 'midpoint_SAT_write': 700}
        if (result[0] != expectedCarleton):
            return False
        return True

    def testNormalCaseSpaceCaps(self):
        result = get_college('Carleton College')
        expectedCarleton = {'name': 'Carleton College', 'state': 'MN', 'in_state': 47736, 'out_of_state': 47736,
                            'acceptance_rate': 22.77, 'designation': 'private', 'size': 2042, 'midpoint_ACT': 32,
                            'midpoint_SAT_math': 710, 'midpoint_SAT_write': 700}
        if (result[0] != expectedCarleton):
            return False
        return True

    def testNormalCaseSpaceLower(self):
        result = get_college('carleton college')
        expectedCarleton = {'name': 'Carleton College', 'state': 'MN', 'in_state': 47736, 'out_of_state': 47736,
                            'acceptance_rate': 22.77, 'designation': 'private', 'size': 2042, 'midpoint_ACT': 32,
                            'midpoint_SAT_math': 710, 'midpoint_SAT_write': 700}
        if (result[0] != expectedCarleton):
            return False
        return True

    def testNormalCaseSpaceMixedCaps(self):
        result = get_college('carleton College')
        expectedCarleton = {'name': 'Carleton College', 'state': 'MN', 'in_state': 47736, 'out_of_state': 47736,
                            'acceptance_rate': 22.77, 'designation': 'private', 'size': 2042, 'midpoint_ACT': 32,
                            'midpoint_SAT_math': 710, 'midpoint_SAT_write': 700}
        if (result[0] != expectedCarleton):
            return False
        return True

    def testNormalCaseLongName(self):
        pass

    def testPartialNameOneMatch(self):
        pass

    def testPartialNameMultipleMatches(self):
        results = api_test.school_search('Carl')
        expected = ['Carl Albert State College', 'Carl Sandburg College', 'Carleton College']
        self.assertEqual(len(results), len(expected))
        if len(results) == len(expected):
            for i in range(0, len(results)):
                self.assertEqual(results[i]['name'] in expected, True)

    def testInvalidName(self):
        self.assertEqual(array_equal(api_test.school_search('Craleton'), []), true)

    def testEmptyName(self):
        self.assertEqual(array_equal(api_test.school_search(''), []), true)

    def states_normal(self):
        test_array = ['Casper College', 'Central Wyoming College', 'Eastern Wyoming College', 'Laramie County Community College',
                      'Northwest College', 'Sheridan College', 'University of Phoenix', 'University of Wyoming',
                      'Western Wyoming Community College', 'Wind River Tribal College', 'Wyoming Catholic College', 'Wyoming Technical Institute']
        self.assertEqual(array_equal(api_test.state_search('MN'), test_array), true)

    def states_lower_case(self):
        test_array = ['Casper College', 'Central Wyoming College', 'Eastern Wyoming College',
                      'Laramie County Community College',
                      'Northwest College', 'Sheridan College', 'University of Phoenix', 'University of Wyoming',
                      'Western Wyoming Community College', 'Wind River Tribal College', 'Wyoming Catholic College',
                      'Wyoming Technical Institute']
        self.assertEqual(array_equal(api_test.state_search('mn'), test_array), true)

    def states_empty(self):
        self.assertEqual(array_equal(api_test.state_search(''), []), true)

    def states_partial(self):
        self.assertEqual(array_equal(api_test.state_search('M'), []), true)

    def states_invalid(self):
        self.assertEqual(array_equal(api_test.state_search('MX'), []), true)

    def array_equal(self, array1, array2):
        if len(array1) != len(array2):
            return False
        else:
            for i in range(0,len(array1)):
                if not array1[i] == array2[i]:
                    return False
            return True

if __name__ == '__main__':
    unittest.main()