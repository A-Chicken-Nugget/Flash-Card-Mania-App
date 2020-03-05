package com.csc331.flash_card_mania_app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class UserProfile {
//    private int id;
//    private String email;
//    private String userName;
    private ArrayList<TestScore> testScores;
    private HashMap<UUID,Integer> timeSpentLearning;

    public UserProfile(ArrayList<TestScore> testScores, HashMap<UUID,Integer> timeSpentLearning) {
        this.testScores = testScores;
        this.timeSpentLearning = timeSpentLearning;
    }

    public void addTimeSpentLearning(UUID id, Integer time) {
        if (timeSpentLearning.get(id) == null) {
            timeSpentLearning.put(id,time);
        } else {
            timeSpentLearning.put(id,timeSpentLearning.get(id) + time);
        }
    }
    public void addTestScore(TestScore score) {
        testScores.add(score);
    }
}
