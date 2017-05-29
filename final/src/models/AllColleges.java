package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Kevin Christianson and Isaac Haseley
 *
 * Data structure to handle all Colleges that we build from our API
 */
public class AllColleges {

    private List<College> collegeList;

    /**
     * Hits the API and constructs all Colleges
     * Sticks them all in CollegeList
     */
    public AllColleges() {
    }

    /**
     * Ranks (sorts) all colleges using user's weights
     * @param userWeights - Dictionary with key for each metric and value for user's weight
     */
    public void rankByUserMetrics(HashMap userWeights) {
    }

    public List<College> getCollegeList() {
        return null;
    }
}