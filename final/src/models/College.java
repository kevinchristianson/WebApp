package models;

import java.util.Map;
import static java.lang.Math.abs;

/**
 * Kevin Christianson and Isaac Haseley
 *
 * Handles data on a single college or university from our API
 */
public class College implements Comparable<College> {

    public static final double MIN_ACCEPTANCE_RATE = 5.05;
    public static final double MAX_ACCEPTANCE_RATE = 100;
    public static final double DIF_ACCEPTANCE_RATE = MAX_ACCEPTANCE_RATE - MIN_ACCEPTANCE_RATE;
    public static final double MIN_ACT = 14;
    public static final double MAX_ACT = 35;
    public static final double DIF_ACT = MAX_ACT - MIN_ACT;
    public static final double MIN_TUITION = 580;
    public static final double MAX_TUITION = 51008;
    public static final double DIF_TUITION = MAX_TUITION - MIN_TUITION;
    public static final double NO_TARGET_INPUT = 557067760;

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
            if (userWeights.get("acceptanceRateT") != NO_TARGET_INPUT) {
                double acceptanceRateT = userWeights.get("acceptanceRateT");
                if (0 <= acceptanceRateT && acceptanceRateT < MIN_ACCEPTANCE_RATE) {
                    acceptanceRateT = MIN_ACCEPTANCE_RATE;
                } else if (acceptanceRateT > MAX_ACCEPTANCE_RATE) {
                    acceptanceRateT = MAX_ACCEPTANCE_RATE;
                }
                this.totalWeightedOutcome += ((DIF_ACCEPTANCE_RATE - abs(this.acceptanceRate - acceptanceRateT))
                        / DIF_ACCEPTANCE_RATE) * 100 * userWeights.get("acceptanceRateW");
            } else {
                this.totalWeightedOutcome += (100 + MIN_ACCEPTANCE_RATE - this.acceptanceRate)
                        * userWeights.get("acceptanceRateW");
            }
        }

        if (this.midpointACT > 0 && userWeights.get("actW") > 0) {
            if (userWeights.get("actT") != NO_TARGET_INPUT) {
                double actT = userWeights.get("actT");
                if (0 <= actT && actT < MIN_ACT) {
                    actT = MIN_ACT;
                } else if (actT > MAX_ACT) {
                    actT = MAX_ACT;
                }
                this.totalWeightedOutcome += ((DIF_ACT - abs(this.midpointACT - actT)) / DIF_ACT) * 100
                        * userWeights.get("actW");
            } else {
                this.totalWeightedOutcome += ((double)this.midpointACT / MAX_ACT) * 100 * userWeights.get("actW");
            }
        }

        double tuitionT = userWeights.get("tuitionT");
        if (0 <= tuitionT && tuitionT < MIN_TUITION) {
            tuitionT = MIN_TUITION;
        } else if (tuitionT > MAX_TUITION) {
            tuitionT = MAX_TUITION;
        }
        if (this.inStateTuition > 0 && userWeights.get("inStateTuitionW") > 0) {
            if (userWeights.get("tuitionT") != NO_TARGET_INPUT) {
                this.totalWeightedOutcome += ((DIF_TUITION - abs(this.inStateTuition - tuitionT)) / DIF_TUITION) * 100
                        * userWeights.get("inStateTuitionW");
            } else {
                this.totalWeightedOutcome += ((double)(MAX_TUITION - this.inStateTuition) / MAX_TUITION) * 100
                        * userWeights.get("inStateTuitionW");
            }
        } else if (this.outStateTuition > 0 && userWeights.get("outStateTuitionW") > 0) {
            if (userWeights.get("tuitionT") != NO_TARGET_INPUT) {
                this.totalWeightedOutcome += ((DIF_TUITION - abs(this.outStateTuition - tuitionT)) / DIF_TUITION) * 100
                        * userWeights.get("outStateTuitionW");
            } else {
                this.totalWeightedOutcome += ((double)(MAX_TUITION - this.outStateTuition) / MAX_TUITION) * 100
                        * userWeights.get("outStateTuitionW");
            }
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