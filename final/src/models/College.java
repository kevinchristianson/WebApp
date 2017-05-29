package models;

import java.util.HashMap;

/**
 * Kevin Christianson and Isaac Haseley
 *
 * Handles data on a single college or university from our API
 */
public class College {

    /**
     * How well this institution performs given the user's metrics
     */
    private double totalWeightedOutcome;

    /**
     * Public, private, or for-profit
     */
    private String designation;

    private double acceptanecRate;
    private int inStateTuition, outStateTuition, midpointACT, enrollment;
    private String state;

    /**
     * Constructs College with API data
     * @param apiDictionary - Dictionary returned from our API
     */
    public College(HashMap apiDictionary) {
    }

    /**
     * @param userWeights - Dictionary with key for each metric and value for user's weight
     */
    public void calcTotalWeightedOutcome(HashMap userWeights) {
    }

    public double getTotalWeightedOutcome() {
        return totalWeightedOutcome;
    }

    public double getAcceptanecRate() {
        return acceptanecRate;
    }

    public int getInStateTuition() {
        return inStateTuition;
    }

    public int getOutStateTuition() {
        return outStateTuition;
    }

    public int getMidpointACT() {
        return midpointACT;
    }

    public int getEnrollment() {
        return enrollment;
    }

    public String getDesignation() {
        return designation;
    }

    public String getState() {
        return state;
    }
}