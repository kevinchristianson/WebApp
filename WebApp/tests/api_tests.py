'''
api_tests.py
Isaac Haseley and Kevin Christianson

'''

import unittest

class APITester(unittest.TestCase):

    def setUp(self):
        pass

    def tearDown(self):
        pass

    def testNormalCaseUnderscoreCaps(self):
        result = get_school('Carleton_College')
        expectedCarleton = {'name': 'Carleton College', 'state': 'MN', 'in_state': 47736, 'out_of_state': 47736,
                    'acceptance_rate': 22.77, 'designation': 'private', 'size': 2042, 'midpoint_ACT': 32,
                    'midpoint_SAT_math': 710, 'midpoint_SAT_write': 700}
        if (result[0] != expectedCarleton):
            return False
        return True

    def testNormalCaseUnderscoreLower(self):
        result = get_school('carleton_college')
        expectedCarleton = {'name': 'Carleton College', 'state': 'MN', 'in_state': 47736, 'out_of_state': 47736,
                            'acceptance_rate': 22.77, 'designation': 'private', 'size': 2042, 'midpoint_ACT': 32,
                            'midpoint_SAT_math': 710, 'midpoint_SAT_write': 700}
        if (result[0] != expectedCarleton):
            return False
        return True

    def testNormalCaseUnderscoreMixedCaps(self):
        result = get_school('carleton_College')
        expectedCarleton = {'name': 'Carleton College', 'state': 'MN', 'in_state': 47736, 'out_of_state': 47736,
                            'acceptance_rate': 22.77, 'designation': 'private', 'size': 2042, 'midpoint_ACT': 32,
                            'midpoint_SAT_math': 710, 'midpoint_SAT_write': 700}
        if (result[0] != expectedCarleton):
            return False
        return True

    def testNormalCaseSpaceCaps(self):
        result = get_school('Carleton College')
        expectedCarleton = {'name': 'Carleton College', 'state': 'MN', 'in_state': 47736, 'out_of_state': 47736,
                            'acceptance_rate': 22.77, 'designation': 'private', 'size': 2042, 'midpoint_ACT': 32,
                            'midpoint_SAT_math': 710, 'midpoint_SAT_write': 700}
        if (result[0] != expectedCarleton):
            return False
        return True

    def testNormalCaseSpaceLower(self):
        result = get_school('carleton college')
        expectedCarleton = {'name': 'Carleton College', 'state': 'MN', 'in_state': 47736, 'out_of_state': 47736,
                            'acceptance_rate': 22.77, 'designation': 'private', 'size': 2042, 'midpoint_ACT': 32,
                            'midpoint_SAT_math': 710, 'midpoint_SAT_write': 700}
        if (result[0] != expectedCarleton):
            return False
        return True

    def testNormalCaseSpaceMixedCaps(self):
        result = get_school('carleton College')
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

    def testStateNormal(self):
        test_array = ['Casper College', 'Central Wyoming College', 'Eastern Wyoming College', 'Laramie County Community College',
                      'Northwest College', 'Sheridan College', 'University of Phoenix', 'University of Wyoming',
                      'Western Wyoming Community College', 'Wind River Tribal College', 'Wyoming Catholic College', 'Wyoming Technical Institute']
        self.assertEqual(arrays_equal(get_schools_by_state('WY'), test_array), True)

    def testStateLower(self):
        test_array = ['Casper College', 'Central Wyoming College', 'Eastern Wyoming College',
                      'Laramie County Community College',
                      'Northwest College', 'Sheridan College', 'University of Phoenix', 'University of Wyoming',
                      'Western Wyoming Community College', 'Wind River Tribal College', 'Wyoming Catholic College',
                      'Wyoming Technical Institute']
        self.assertEqual(arrays_equal(get_schools_by_state('wy'), test_array), True)

    def testStateEmpty(self):
        self.assertEqual(arrays_equal(get_schools_by_state(''), []), True)

    def testStatePartial(self):
        self.assertEqual(arrays_equal(get_schools_by_state('M'), []), True)

    def testStateInvalid(self):
        self.assertEqual(arrays_equal(get_schools_by_state('MX'), []), True)

    def arrays_equal(self, array1, array2):
        if len(array1) != len(array2):
            return False
        else:
            for i in range(0,len(array1)):
                if not array1[i] == array2[i]:
                    return False
            return True

    def get_school(name):
        pass

    def get_schools_by_state(state):
        pass

if __name__ == '__main__':
    unittest.main()