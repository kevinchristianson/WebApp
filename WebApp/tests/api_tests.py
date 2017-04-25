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
        self.expectedCarleton = {'name': 'Carleton College', 'state': 'MN', 'in_state': 47736, 'out_of_state': 47736,
                    'acceptance_rate': 22.77, 'designation': 'private', 'size': 2042, 'midpoint_ACT': 32,
                    'midpoint_SAT_math': 710, 'midpoint_SAT_write': 700}

    def tearDown(self):
        pass

    def testNormalCaseUnderscoreCaps(self):
        result = get_school('Carleton_College')
        self.assertEqual(result, self.expectedCarleton)

    def testNormalCaseUnderscoreLower(self):
        result = get_school('carleton_college')
        self.assertEqual(result, self.expectedCarleton)

    def testNormalCaseUnderscoreMixedCaps(self):
        result = get_school('carleton_College')
        self.assertEqual(result, self.expectedCarleton)

    def testNormalCaseSpaceCaps(self):
        result = get_school('Carleton College')
        self.assertEqual(result, self.expectedCarleton)

    def testNormalCaseSpaceLower(self):
        result = get_school('carleton college')
        self.assertEqual(result, self.expectedCarleton)

    def testNormalCaseSpaceMixedCaps(self):
        result = get_school('carleton College')
        self.assertEqual(result, self.expectedCarleton)

    def testNormalCaseLongName(self):
        result = get_school('University of Minnesota Duluth')
        expected = {'name': 'University of Minnesota-Duluth', 'state': 'MN', 'in_state': 12802, 'out_of_state': 16467,
                            'acceptance_rate': 76.78, 'designation': 'public', 'size': 9120, 'midpoint_ACT': 24.0,
                            'midpoint_SAT_math': 575.0, 'midpoint_SAT_write': 520.0}
        self.assertEqual(result, expected)

    def testPartialNameOneMatch(self):
        result = get_school('Carle')
        self.assertEqual(result, self.expectedCarleton)

    def testPartialNameMultipleMatches(self):
        results = get_school('Carl')
        expected = ['Carl Albert State College', 'Carl Sandburg College', 'Carleton College']
        self.assertEqual(len(results), len(expected))
        for i in range(0, len(results)):
            self.assertEqual(results[i]['name'] in expected, True)

    def testInvalidName(self):
        self.assertEqual(arrays_equal(get_state('Craleton'), []), True)

    def testEmptyName(self):
        self.assertEqual(arrays_equal(get_state(''), []), True)

    def testStateNormal(self):
        test_array = ['Casper College', 'Central Wyoming College', 'Eastern Wyoming College', 'Laramie County Community College',
                      'Northwest College', 'Sheridan College', 'University of Phoenix', 'University of Wyoming',
                      'Western Wyoming Community College', 'Wind River Tribal College', 'Wyoming Catholic College', 'Wyoming Technical Institute']
        self.assertEqual(arrays_equal(get_state('WY'), test_array), True)

    def testStateLower(self):
        test_array = ['Casper College', 'Central Wyoming College', 'Eastern Wyoming College',
                      'Laramie County Community College',
                      'Northwest College', 'Sheridan College', 'University of Phoenix', 'University of Wyoming',
                      'Western Wyoming Community College', 'Wind River Tribal College', 'Wyoming Catholic College',
                      'Wyoming Technical Institute']
        self.assertEqual(arrays_equal(get_state('wy'), test_array), True)

    def states_empty(self):
        self.assertEqual(arrays_equal(get_state(''), []), True)

    def states_partial(self):
        self.assertEqual(arrays_equal(get_state('M'), []), True)

    def states_invalid(self):
        self.assertEqual(arrays_equal(get_state('MX'), []), True)


if __name__ == '__main__':
    unittest.main()