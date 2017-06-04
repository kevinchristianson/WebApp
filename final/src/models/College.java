package models;

import java.util.Map;

/**
 * Kevin Christianson and Isaac Haseley
 *
 * Handles data on a single college or university from our API
 */
public class College implements Comparable<College> {

    /**
     * How well this institution performs given the user's metrics
     */
    private double totalWeightedOutcome;

    /**
     * Public, private, or for-profit
     */
    private String designation;

    private double acceptanceRate;
    private int inStateTuition, outStateTuition, midpointACT, enrollment;
    private String state, name, url;

    /**
     * Constructs College with API data
     * @param college - Dictionary returned from our API
     */
    public College(Map<String, String> college) {
        this.totalWeightedOutcome = 0;
        this.name = college.get("name");
        this.state = college.get("state");
        this.url = college.get("school_site");
        if (college.get("designation").equals("1")) {
            this.designation = "Public";
        } else if (college.get("designation").equals("2")) {
            this.designation = "Private";
        } else if (college.get("designation").equals("3")) {
            this.designation = "For-profit";
        }

        String acceptanceRate = college.get("acceptance_rate");
        if (!(acceptanceRate.equals("Data not available"))) {
            acceptanceRate = acceptanceRate.replace("%", "");
            this.acceptanceRate = Double.parseDouble(acceptanceRate);
        } else {
            this.acceptanceRate = -1;
        }

        String inStateTuition = college.get("in_state_tuition");
        if (!(inStateTuition.equals("Data not available"))) {
            inStateTuition = inStateTuition.replace(",", "");
            this.inStateTuition = Integer.parseInt(inStateTuition);
        } else {
            this.inStateTuition = -1;
        }

        String outStateTuition = college.get("out_state_tuition");
        if (!(outStateTuition.equals("Data not available"))) {
            outStateTuition = outStateTuition.replace(",", "");
            this.outStateTuition = Integer.parseInt(outStateTuition);
        } else {
            this.outStateTuition = -1;
        }

        String enrollment = college.get("size");
        if (!(enrollment.equals("Data not available"))) {
            this.enrollment = Integer.parseInt(enrollment);
        } else {
            this.enrollment = -1;
        }

        String midpointACT = college.get("midpoint_ACT");
        if (!(midpointACT.equals("Data not available"))) {
            this.midpointACT = Integer.parseInt(midpointACT);
        } else {
            this.midpointACT = -1;
        }
    }

    /**
     * @param userWeights - Dictionary with key for each metric and value for user's weight
     */
    public void calcTotalWeightedOutcome(Map<String, Double> userWeights) {
        this.totalWeightedOutcome = 0;
        if (this.acceptanceRate > 0 && userWeights.get("acceptanceRateW") > 0) {
            this.totalWeightedOutcome += (104 - this.acceptanceRate) * userWeights.get("acceptanceRateW");
        }
        if (this.midpointACT > 0 && userWeights.get("actW") > 0) {
            this.totalWeightedOutcome += ((double)this.midpointACT / 36) * 100 * userWeights.get("actW");
        }
        if (this.inStateTuition > 0 && userWeights.get("inStateTuitionW") > 0) {
            this.totalWeightedOutcome += ((double)(51008 - this.inStateTuition) / 51008) * 100
                    * userWeights.get("inStateTuitionW");
        }
        if (this.outStateTuition > 0 && userWeights.get("outStateTuitionW") > 0) {
            this.totalWeightedOutcome += ((double)(51008 - this.outStateTuition) / 51008) * 100
                    * userWeights.get("outStateTuitionW");
        }
    }

    @Override
    /**
     * @param college2 - The college we're comparing this college to
     * @return -1 if this college is higher-ranked than college2, 1 if lower-ranked, 0 if equal
     */
    public int compareTo(College college2) {
        return -1 * (new Double(this.totalWeightedOutcome).compareTo(college2.getTotalWeightedOutcome()));
    }

    public double getTotalWeightedOutcome() {
        return this.totalWeightedOutcome;
    }

    public double getAcceptanecRate() { return this.acceptanceRate; }

    public int getInStateTuition() {
        return this.inStateTuition;
    }

    public int getOutStateTuition() {
        return this.outStateTuition;
    }

    public int getMidpointACT() {
        return this.midpointACT;
    }

    public int getEnrollment() {
        return this.enrollment;
    }

    public String getDesignation() {
        return this.designation;
    }

    public String getState() {
        return this.state;
    }

    public String getName() { return this.name; }

    public String getURL() { return this.url; }
}