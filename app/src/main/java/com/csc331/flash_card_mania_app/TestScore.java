package com.csc331.flash_card_mania_app;

public class TestScore {
    private String testName;
    private int percentCorrect;
    private int answersCorrect;
    private int totalQuestions;
    private int timeStarted;
    private int timeSpent;

    public TestScore(String testName, int percentCorrect, int answersCorrect, int totalQuestions, int timeStarted, int timeSpent) {
        this.testName = testName;
        this.percentCorrect = percentCorrect;
        this.answersCorrect = answersCorrect;
        this.totalQuestions = totalQuestions;
        this.timeStarted = timeStarted;
        this.timeSpent = timeSpent;
    }

    public String getTestName() {
        return testName;
    }
    public int getPercentCorrect() {
        return (answersCorrect/totalQuestions)*100;
    }
    public int getAnswersCorrect() {
        return answersCorrect;
    }
    public int getTotalQuestions() {
        return totalQuestions;
    }
    public int getTimeStarted() {
        return timeStarted;
    }
    public int getTimeSpent() {
        return timeSpent;
    }
}
