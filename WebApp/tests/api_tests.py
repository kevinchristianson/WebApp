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

    # ========= STATE OPTIONS TEST ======== #

    def testStateOptions(self):
        expected = [["AK", "Alaska", "http://thacker.mathcs.carleton.edu:5107/schools/by_state/AK/"],
                    ["AL", "Alabama", "http://thacker.mathcs.carleton.edu:5107/schools/by_state/AL/"],
                    ["AR", "Arkansas", "http://thacker.mathcs.carleton.edu:5107/schools/by_state/AR/"],
                    ["AS", "American Samoa", "http://thacker.mathcs.carleton.edu:5107/schools/by_state/AS/"],
                    ["AZ", "Arizona", "http://thacker.mathcs.carleton.edu:5107/schools/by_state/AZ/"],
                    ["CA", "California", "http://thacker.mathcs.carleton.edu:5107/schools/by_state/CA/"],
                    ["CO", "Colorado", "http://thacker.mathcs.carleton.edu:5107/schools/by_state/CO/"],
                    ["CT", "Connecticut", "http://thacker.mathcs.carleton.edu:5107/schools/by_state/CT/"],
                    ["DC", "District of Columbia", "http://thacker.mathcs.carleton.edu:5107/schools/by_state/DC/"],
                    ["DE", "Delaware", "http://thacker.mathcs.carleton.edu:5107/schools/by_state/DE/"],
                    ["FL", "Florida", "http://thacker.mathcs.carleton.edu:5107/schools/by_state/FL/"],
                    ["FM", "Micronesia", "http://thacker.mathcs.carleton.edu:5107/schools/by_state/FM/"],
                    ["GA", "Georgia", "http://thacker.mathcs.carleton.edu:5107/schools/by_state/GA/"],
                    ["GU", "Guam", "http://thacker.mathcs.carleton.edu:5107/schools/by_state/GU/"],
                    ["HI", "Hawaii", "http://thacker.mathcs.carleton.edu:5107/schools/by_state/HI/"],
                    ["IA", "Iowa", "http://thacker.mathcs.carleton.edu:5107/schools/by_state/IA/"],
                    ["ID", "Idaho", "http://thacker.mathcs.carleton.edu:5107/schools/by_state/ID/"],
                    ["IL", "Illinois", "http://thacker.mathcs.carleton.edu:5107/schools/by_state/IL/"],
                    ["IN", "Indiana", "http://thacker.mathcs.carleton.edu:5107/schools/by_state/IN/"],
                    ["KS", "Kansas", "http://thacker.mathcs.carleton.edu:5107/schools/by_state/KS/"],
                    ["KY", "Kentucky", "http://thacker.mathcs.carleton.edu:5107/schools/by_state/KY/"],
                    ["LA", "Louisiana", "http://thacker.mathcs.carleton.edu:5107/schools/by_state/LA/"],
                    ["MA", "Massachusetts", "http://thacker.mathcs.carleton.edu:5107/schools/by_state/MA/"],
                    ["MD", "Maryland", "http://thacker.mathcs.carleton.edu:5107/schools/by_state/MD/"],
                    ["ME", "Maine", "http://thacker.mathcs.carleton.edu:5107/schools/by_state/ME/"],
                    ["MH", "Marshall Islands", "http://thacker.mathcs.carleton.edu:5107/schools/by_state/MH/"],
                    ["MI", "Michigan", "http://thacker.mathcs.carleton.edu:5107/schools/by_state/MI/"],
                    ["MN", "Minnesota", "http://thacker.mathcs.carleton.edu:5107/schools/by_state/MN/"],
                    ["MO", "Missouri", "http://thacker.mathcs.carleton.edu:5107/schools/by_state/MO/"],
                    ["MP", "Northern Marianas", "http://thacker.mathcs.carleton.edu:5107/schools/by_state/MP/"],
                    ["MS", "Mississippi", "http://thacker.mathcs.carleton.edu:5107/schools/by_state/MS/"],
                    ["MT", "Montana", "http://thacker.mathcs.carleton.edu:5107/schools/by_state/MT/"],
                    ["NC", "North Carolina", "http://thacker.mathcs.carleton.edu:5107/schools/by_state/NC/"],
                    ["ND", "North Dakota", "http://thacker.mathcs.carleton.edu:5107/schools/by_state/ND/"],
                    ["NE", "Nebraska", "http://thacker.mathcs.carleton.edu:5107/schools/by_state/NE/"],
                    ["NH", "New Hampshire", "http://thacker.mathcs.carleton.edu:5107/schools/by_state/NH/"],
                    ["NJ", "New Jersey", "http://thacker.mathcs.carleton.edu:5107/schools/by_state/NJ/"],
                    ["NM", "New Mexico", "http://thacker.mathcs.carleton.edu:5107/schools/by_state/NM/"],
                    ["NV", "Nevada", "http://thacker.mathcs.carleton.edu:5107/schools/by_state/NV/"],
                    ["NY", "New York", "http://thacker.mathcs.carleton.edu:5107/schools/by_state/NY/"],
                    ["OH", "Ohio", "http://thacker.mathcs.carleton.edu:5107/schools/by_state/OH/"],
                    ["OK", "Oklahoma", "http://thacker.mathcs.carleton.edu:5107/schools/by_state/OK/"],
                    ["OR", "Oregon", "http://thacker.mathcs.carleton.edu:5107/schools/by_state/OR/"],
                    ["PA", "Pennsylvania", "http://thacker.mathcs.carleton.edu:5107/schools/by_state/PA/"],
                    ["PR", "Puerto Rico", "http://thacker.mathcs.carleton.edu:5107/schools/by_state/PR/"],
                    ["PW", "Palau", "http://thacker.mathcs.carleton.edu:5107/schools/by_state/PW/"],
                    ["RI", "Rhode Island", "http://thacker.mathcs.carleton.edu:5107/schools/by_state/RI/"],
                    ["SC", "South Carolina", "http://thacker.mathcs.carleton.edu:5107/schools/by_state/SC/"],
                    ["SD", "South Dakota", "http://thacker.mathcs.carleton.edu:5107/schools/by_state/SD/"],
                    ["TN", "Tennessee", "http://thacker.mathcs.carleton.edu:5107/schools/by_state/TN/"],
                    ["TX", "Texas", "http://thacker.mathcs.carleton.edu:5107/schools/by_state/TX/"],
                    ["UT", "Utah", "http://thacker.mathcs.carleton.edu:5107/schools/by_state/UT/"],
                    ["VA", "Virginia", "http://thacker.mathcs.carleton.edu:5107/schools/by_state/VA/"],
                    ["VI", "The Virgin Islands", "http://thacker.mathcs.carleton.edu:5107/schools/by_state/VI/"],
                    ["VT", "Vermont", "http://thacker.mathcs.carleton.edu:5107/schools/by_state/VT/"],
                    ["WA", "Washington", "http://thacker.mathcs.carleton.edu:5107/schools/by_state/WA/"],
                    ["WI", "Wisconsin", "http://thacker.mathcs.carleton.edu:5107/schools/by_state/WI/"],
                    ["WV", "West Virginia", "http://thacker.mathcs.carleton.edu:5107/schools/by_state/WV/"],
                    ["WY", "Wyoming", "http://thacker.mathcs.carleton.edu:5107/schools/by_state/WY/"]]
        self.assertEqual(arrays_equal(expected, api_caller.get_states_options()), True)


if __name__ == '__main__':
    unittest.main()