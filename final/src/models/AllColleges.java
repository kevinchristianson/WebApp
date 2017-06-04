package models;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.*;

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
    public AllColleges() throws IOException {
        URL url = new URL("http://localhost:8080/schools/all");
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        String apiOutput = in.readLine();
        JsonParser myParser = new JsonParser();
        JsonElement myElement = myParser.parse(apiOutput);
        JsonArray myArray = myElement.getAsJsonArray();

        Gson gson = new Gson();
        Type collegeMap = new TypeToken<Map<String, String>>(){}.getType();
        this.collegeList = new ArrayList<>();
        for (int i = 0; i < myArray.size(); i++) {
            JsonObject myObject = myArray.get(i).getAsJsonObject();
            Map<String, String> collegeDictionary = gson.fromJson(myObject, collegeMap);
            this.collegeList.add(new College(collegeDictionary));
        }
    }

    /**
     * Ranks (sorts) all colleges using user's weights
     * @param userWeights - Dictionary with key for each metric and value for user's weight
     */
    public void rankByUserMetrics(Map<String, Double> userWeights) {
        for (College college : this.collegeList) {
            college.calcTotalWeightedOutcome(userWeights);
        }
        Collections.sort(collegeList);
    }

    public List<College> getCollegeList() {
        return this.collegeList;
    }
}