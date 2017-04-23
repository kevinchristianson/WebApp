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
        result = get_college("Carleton_College")
        expected

    def testNormalCaseUnderscoreLower(self):
        pass

    def testNormalCaseUnderscoreMixedCaps(self):
        pass

    def testNormalCaseSpaceCaps(self):
        pass

    def testNormalCaseSpaceLower(self):
        pass

    def testNormalCaseSpaceMixedCaps(self):
        pass

    def testNormalCaseLongName(self):
        pass

    def testPartialNameOneMatch(self):
        pass

    def testPartialNameMultipleMatches(self):
        pass

    def testInvalidName(self):
        pass

    def testEmptyName(self):
        pass

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