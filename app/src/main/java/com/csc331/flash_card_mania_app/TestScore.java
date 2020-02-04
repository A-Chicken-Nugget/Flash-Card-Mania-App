package com.csc331.flash_card_mania_app;

public class TestScore {
    private String testName;
    private int percentCorrect;
    private int answersCorrect;
    private int answersIncorrect;
    private int timeStarted;
    private int timeSpent;

    public TestScore(String testName, int percentCorrect, int answersCorrect, int answersIncorrect, int timeStarted, int timeSpent) {
        this.testName = testName;
        this.percentCorrect = percentCorrect;
        this.answersCorrect = answersCorrect;
        this.answersIncorrect = answersIncorrect;
        this.timeStarted = timeStarted;
        this.timeSpent = timeSpent;
    }
}
