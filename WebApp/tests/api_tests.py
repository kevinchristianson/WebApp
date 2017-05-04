'''
api_tests.py
Isaac Haseley and Kevin Christianson

Tests our college metrics API
'''

from tests import api_caller
import unittest


def arrays_equal(array1, array2):
    '''
    :param array1: An array of arrays
    :param array2: An array of arrays
    :return: True if arrays contain the same items, false otherwise
    '''
    if len(array1) != len(array2):
        return False
    else:
        for i in range(0, len(array1)):
            if (array1[0] != array2[0]) or (array1[1] != array2[1]):
                return False
        return True


class APITester(unittest.TestCase):

    def setUp(self):
        self.expectedCarleton = [{'name': 'Carleton College', 'state': 'Minnesota', 'in_state_tuition': 47736,
                                 'out_state_tuition': 47736, 'acceptance_rate': .2277, 'designation': 2, 'size': 2042,
                                 'midpoint_ACT': 32, 'midpoint_SAT': 1408, 'school_site':'www.carleton.edu',
                                  "url": "http://thacker.mathcs.carleton.edu:5107/schools/search/Carleton%20College"}]
        self.expectedWY = [["Casper College", "http://thacker.mathcs.carleton.edu:5107/schools/search/Casper%20College"],
                           ["Central Wyoming College", "http://thacker.mathcs.carleton.edu:5107/schools/search/Central%"
                           "20Wyoming%20College"], ["Cheeks International Academy of Beauty Culture-Cheyenne", "http://"
                           "thacker.mathcs.carleton.edu:5107/schools/search/Cheeks%20International%20Academy%20of%20Bea"
                           "uty%20Culture-Cheyenne"], ["CollegeAmerica-Cheyenne", "http://thacker.mathcs.carleton.edu:51"
                           "07/schools/search/CollegeAmerica-Cheyenne"], ["Eastern Wyoming College", "http://thacker.mat"
                           "hcs.carleton.edu:5107/schools/search/Eastern%20Wyoming%20College"], ["Laramie County Communi"
                           "ty College", "http://thacker.mathcs.carleton.edu:5107/schools/search/Laramie%20County%20Comm"
                           "unity%20College"], ["Northwest College", "http://thacker.mathcs.carleton.edu:5107/schools/se"
                           "arch/Northwest%20College"], ["Sheridan College", "http://thacker.mathcs.carleton.edu:5107/sc"
                           "hools/search/Sheridan%20College"], ["University of Wyoming", "http://thacker.mathcs.carleton"
                           ".edu:5107/schools/search/University%20of%20Wyoming"], ["Western Wyoming Community College",
                           "http://thacker.mathcs.carleton.edu:5107/schools/search/Western%20Wyoming%20Community%20Colle"
                           "ge"], ["Wyotech-Laramie", "http://thacker.mathcs.carleton.edu:5107/schools/search/Wyotech-La"
                           "ramie"]]

    def tearDown(self):
        pass

    # ====== SCHOOL SEARCH TESTS ====== #

    def testNormalCaseUnderscoreCaps(self):
        result = api_caller.get_school_helper('Carleton_College')
        self.assertEqual(result, self.expectedCarleton)

    def testNormalCaseUnderscoreLower(self):
        result = api_caller.get_school_helper('carleton_college')
        self.assertEqual(result, self.expectedCarleton)

    def testNormalCaseUnderscoreMixedCaps(self):
        result = api_caller.get_school_helper('carleton_College')
        self.assertEqual(result, self.expectedCarleton)

    def testNormalCaseLongName(self):
        result = api_caller.get_school_helper('University_of_Minnesota-Duluth')
        expected = [{'name': 'University of Minnesota-Duluth', 'state': 'Minnesota', 'in_state_tuition': 12802,
                    'out_state_tuition': 16467, 'acceptance_rate': .7678, 'designation': 1, 'size': 9120,
                    'midpoint_ACT': 24.0, 'midpoint_SAT':1110, 'school_site':'www.d.umn.edu/',
                    'url': 'http://thacker.mathcs.carleton.edu:5107/schools/search/University%20of%20Minnesota-Duluth'}]
        self.assertEqual(result, expected)

    def testPartialNameOneMatch(self):
        result = api_caller.get_school_helper('Carleto')
        self.assertEqual(result, self.expectedCarleton)

    def testPartialNameMultipleMatches(self):
        results = api_caller.get_school_helper('mine')
        expected = ['Colorado School of Mines', 'Mineral Area College', 'Mineral County Vocational Technical Center',
                    'South Dakota School of Mines and Technology']
        self.assertEqual(len(results), len(expected))
        for i in range(0, 4):
            self.assertEqual(results[i]['name'] in expected, True)

    def testInvalidName(self):
        self.assertEqual(arrays_equal(api_caller.get_school_helper('Craleton'), []), True)

    def testEmptyName(self):
        self.assertEqual(arrays_equal(api_caller.get_school_helper(''), []), True)

    # ====== SCHOOLS BY STATE TESTS ====== #

    def testStateNormal(self):
        self.assertEqual(arrays_equal(api_caller.get_schools_by_state_helper('WY'), self.expectedWY), True)

    def testStateLower(self):
        self.assertEqual(arrays_equal(api_caller.get_schools_by_state_helper('wy'), self.expectedWY), True)

    def testStateWonkyCaps(self):
        self.assertEqual(arrays_equal(api_caller.get_schools_by_state_helper('wY'), self.expectedWY), True)

    def testStateEmpty(self):
        self.assertEqual(arrays_equal(api_caller.get_schools_by_state_helper(''), []), True)

    def testStatePartial(self):
        self.assertEqual(arrays_equal(api_caller.get_schools_by_state_helper('M'), []), True)

    def testStateInvalid(self):
        self.assertEqual(arrays_equal(api_caller.get_schools_by_state_helper('MX'), []), True)

    def testStateTooLong(self):
        self.assertEqual(arrays_equal(api_caller.get_schools_by_state_helper('MNO'), []), True)


if __name__ == '__main__':
    unittest.main()