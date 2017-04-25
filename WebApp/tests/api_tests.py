'''
api_tests.py
Isaac Haseley and Kevin Christianson

'''

from tests import api_caller
import unittest

def arrays_equal(array1, array2):
    if len(array1) != len(array2):
        return False
    else:
        for i in range(0, len(array1)):
            if not array1[i][0] == array2[i]:
                return False
        return True


class APITester(unittest.TestCase):

    def setUp(self):
        pass

    def tearDown(self):
        pass

    def testNormalCaseUnderscoreCaps(self):
        result = api_caller.get_school('Carleton_College')
        expected = {'name': 'Carleton College', 'state': 'MN', 'in_state': 47736, 'out_of_state': 47736,
                    'acceptance_rate': 22.77, 'designation': 'private', 'size': 2042, 'midpoint_ACT': 32,
                    'midpoint_SAT_math': 710, 'midpoint_SAT_write': 700}
        self.assertEqual(result, expected)

    def testNormalCaseUnderscoreLower(self):
        result = api_caller.get_school('carleton_college')
        expected = {'name': 'Carleton College', 'state': 'MN', 'in_state': 47736, 'out_of_state': 47736,
                            'acceptance_rate': 22.77, 'designation': 'private', 'size': 2042, 'midpoint_ACT': 32,
                            'midpoint_SAT_math': 710, 'midpoint_SAT_write': 700}
        self.assertEqual(result, expected)

    def testNormalCaseUnderscoreMixedCaps(self):
        result = api_caller.get_school('carleton_College')
        expected = {'name': 'Carleton College', 'state': 'MN', 'in_state': 47736, 'out_of_state': 47736,
                            'acceptance_rate': 22.77, 'designation': 'private', 'size': 2042, 'midpoint_ACT': 32,
                            'midpoint_SAT_math': 710, 'midpoint_SAT_write': 700}
        self.assertEqual(result, expected)

    def testNormalCaseSpaceCaps(self):
        result = api_caller.get_school('Carleton College')
        expected = {'name': 'Carleton College', 'state': 'MN', 'in_state': 47736, 'out_of_state': 47736,
                            'acceptance_rate': 22.77, 'designation': 'private', 'size': 2042, 'midpoint_ACT': 32,
                            'midpoint_SAT_math': 710, 'midpoint_SAT_write': 700}
        self.assertEqual(result, expected)

    def testNormalCaseSpaceLower(self):
        result = api_caller.get_school('carleton college')
        expected = {'name': 'Carleton College', 'state': 'MN', 'in_state': 47736, 'out_of_state': 47736,
                            'acceptance_rate': 22.77, 'designation': 'private', 'size': 2042, 'midpoint_ACT': 32,
                            'midpoint_SAT_math': 710, 'midpoint_SAT_write': 700}
        self.assertEqual(result, expected)

    def testNormalCaseSpaceMixedCaps(self):
        result = api_caller.get_school('carleton College')
        expected = {'name': 'Carleton College', 'state': 'MN', 'in_state': 47736, 'out_of_state': 47736,
                            'acceptance_rate': 22.77, 'designation': 'private', 'size': 2042, 'midpoint_ACT': 32,
                            'midpoint_SAT_math': 710, 'midpoint_SAT_write': 700}
        self.assertEqual(result, expected)

    def testNormalCaseLongName(self):
        result = api_caller.get_school('University of Minnesota Duluth')
        expected = {'name': 'University of Minnesota-Duluth', 'state': 'MN', 'in_state': 12802, 'out_of_state': 16467,
                            'acceptance_rate': 76.78, 'designation': 'public', 'size': 9120, 'midpoint_ACT': 24.0,
                            'midpoint_SAT_math': 575.0, 'midpoint_SAT_write': 520.0}
        self.assertEqual(result, expected)

    def testPartialNameOneMatch(self):
        result = api_caller.get_school('Carle')
        expected = {'name': 'Carleton College', 'state': 'MN', 'in_state': 47736, 'out_of_state': 47736,
                            'acceptance_rate': 22.77, 'designation': 'private', 'size': 2042, 'midpoint_ACT': 32,
                            'midpoint_SAT_math': 710, 'midpoint_SAT_write': 700}
        self.assertEqual(result, expected)

    def testPartialNameMultipleMatches(self):
        results = api_caller.get_school('Carl')
        expected = ['Carl Albert State College', 'Carl Sandburg College', 'Carleton College']
        self.assertEqual(len(results), len(expected))
        for i in range(0, len(results)):
            self.assertEqual(results[i]['name'] in expected, True)

    def testInvalidName(self):
        self.assertEqual(arrays_equal(api_caller.get_state('Craleton'), []), True)

    def testEmptyName(self):
        self.assertEqual(arrays_equal(api_caller.get_state(''), []), True)

    def testStateNormal(self):
        test_array = ['Casper College', 'Central Wyoming College', 'Eastern Wyoming College', 'Laramie County Community College',
                      'Northwest College', 'Sheridan College', 'University of Phoenix', 'University of Wyoming',
                      'Western Wyoming Community College', 'Wind River Tribal College', 'Wyoming Catholic College', 'Wyoming Technical Institute']
        self.assertEqual(arrays_equal(api_caller.get_state('WY'), test_array), True)

    def testStateLower(self):
        test_array = ['Casper College', 'Central Wyoming College', 'Eastern Wyoming College',
                      'Laramie County Community College',
                      'Northwest College', 'Sheridan College', 'University of Phoenix', 'University of Wyoming',
                      'Western Wyoming Community College', 'Wind River Tribal College', 'Wyoming Catholic College',
                      'Wyoming Technical Institute']
        self.assertEqual(arrays_equal(api_caller.get_state('wy'), test_array), True)

    def states_empty(self):
        self.assertEqual(arrays_equal(api_caller.get_state(''), []), True)

    def states_partial(self):
        self.assertEqual(arrays_equal(api_caller.get_state('M'), []), True)

    def states_invalid(self):
        self.assertEqual(arrays_equal(api_caller.get_state('MX'), []), True)


if __name__ == '__main__':
    unittest.main()