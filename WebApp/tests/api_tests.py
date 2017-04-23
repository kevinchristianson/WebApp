'''
api_tests.py
Isaac Haseley and Kevin Christianson

'''

#import CLASS TO TEST HERE
import unittest

class APITester(unittest.TestCase):
    def setUp(self):
        pass

    def tearDown(self):
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
            seen = []
            for item in array1:
                seen.append(item)
                if not item[0] in array2 or item in seen:
                    return False
            return True


if __name__ == '__main__':
    unittest.main()