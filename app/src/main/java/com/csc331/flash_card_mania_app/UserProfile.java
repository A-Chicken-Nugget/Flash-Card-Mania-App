package com.csc331.flash_card_mania_app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class UserProfile {
    private ArrayList<TestResult> testResults = new ArrayList<>();
    private HashMap<UUID,Integer> timeSpentLearning = new HashMap<>();

    public UserProfile() {
    }
    public UserProfile(ArrayList<TestResult> testResults, HashMap<UUID,Integer> timeSpentLearning) {
        this.testResults = testResults;
        this.timeSpentLearning = timeSpentLearning;
    }

    public void addTimeSpentLearning(UUID id, Integer time) {
        if (timeSpentLearning.get(id) == null) {
            timeSpentLearning.put(id,time);
        } else {
            timeSpentLearning.put(id,timeSpentLearning.get(id) + time);
        }
    }
    public void addTestScore(TestResult result) {
        testResults.add(result);
    }
    public TestResult getLatestTestResult() {
        return testResults.get(testResults.size()-1);
    }
    public ArrayList<TestResult> getTestResults() {
        return testResults;
    }
    public HashMap<UUID,Integer> getTimeSpentLearning() {
        return timeSpentLearning;
    }

    public void setTestResults(ArrayList<TestResult> testResults) {
        this.testResults = testResults;
    }
    public void setTimeSpentLearning(HashMap<UUID,Integer> timeSpentLearning) {
        this.timeSpentLearning = timeSpentLearning;
    }
}
