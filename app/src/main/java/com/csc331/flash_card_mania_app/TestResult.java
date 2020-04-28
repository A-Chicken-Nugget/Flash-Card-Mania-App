package com.csc331.flash_card_mania_app;

import java.util.ArrayList;

public class TestResult {
    private String testName;
    private int percentCorrect;
    private ArrayList<TestQuestion> questions;
    private String letterGrade;
    private int totalQuestions;
    private int questionsCorrectlyAnswered = 0;
    private int timeSpent;

    public TestResult(String testName, int percentCorrect, int timeSpent, ArrayList<TestQuestion> questions) {
        this.testName = testName;
        this.percentCorrect = percentCorrect;
        this.timeSpent = timeSpent;
        this.questions = questions;

        if (percentCorrect > 90) {
            letterGrade = "A";
        } else if (percentCorrect > 80) {
            letterGrade = "B";
        } else if (percentCorrect > 70) {
            letterGrade = "C";
        } else if (percentCorrect > 60) {
            letterGrade = "D";
        } else {
            letterGrade = "F";
        }

        totalQuestions = questions.size();
        for (TestQuestion question : questions) {
            if (question.didGetCorrect()) {
                questionsCorrectlyAnswered++;
            }
        }
    }

    public String getTestName() {
        return testName;
    }
    public int getPercentCorrect() {
        return percentCorrect;
    }
    public int getTimeSpent() {
        return timeSpent;
    }
    public ArrayList<TestQuestion> getTestQuestions() {
        return questions;
    }
    public String getLetterGrade() {
        return letterGrade;
    }
    public int getTotalQuestions() {
        return totalQuestions;
    }
    public int getQuestionsCorrectlyAnswered() {
        return questionsCorrectlyAnswered;
    }
}