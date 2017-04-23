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



if __name__ == '__main__':
    unittest.main()