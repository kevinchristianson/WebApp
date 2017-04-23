'''
api_tests.py
Isaac Haseley and Kevin Christianson

'''


def get_state(self, name):
    pass


def get_school(self, name):
    pass

def arrays_equal(self, array1, array2):
    if len(array1) != len(array2):
        return False
    else:
        for i in range(0,len(array1)):
            if not array1[i] == array2[i]:
                return False
        return True


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
        result = get_school('University of Minnesota Duluth')
        expected = {'name': 'University of Minnesota-Duluth', 'state': 'MN', 'in_state': 12802, 'out_of_state': 16467,
                            'acceptance_rate': 76.78, 'designation': 'private', 'size': 9120, 'midpoint_ACT': 24.0,
                            'midpoint_SAT_math': 575.0, 'midpoint_SAT_write': 520.0}
        if (result[0] != expected):
            return False
        return True

    def testPartialNameOneMatch(self):
        result = get_school('Carle')
        expected = {'name': 'Carleton College', 'state': 'MN', 'in_state': 47736, 'out_of_state': 47736,
                            'acceptance_rate': 22.77, 'designation': 'private', 'size': 2042, 'midpoint_ACT': 32,
                            'midpoint_SAT_math': 710, 'midpoint_SAT_write': 700}
        if (result[0] != expected):
            return False
        return True

    def testPartialNameMultipleMatches(self):
        results = get_school('Carl')
        expected = ['Carl Albert State College', 'Carl Sandburg College', 'Carleton College']
        self.assertEqual(len(results), len(expected))
        if len(results) == len(expected):
            for i in range(0, len(results)):
                self.assertEqual(results[i]['name'] in expected, True)

    def testInvalidName(self):
        self.assertEqual(array_equal(get_state('Craleton'), []), True)

    def testEmptyName(self):
        self.assertEqual(array_equal(get_state(''), []), True)

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
        self.assertEqual(array_equal(get_state('wy'), test_array), True)

    def states_empty(self):
        self.assertEqual(array_equal(get_state(''), []), True)

    def states_partial(self):
        self.assertEqual(array_equal(get_state('M'), []), True)

    def states_invalid(self):
        self.assertEqual(array_equal(get_state('MX'), []), True)


if __name__ == '__main__':
    unittest.main()