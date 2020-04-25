package com.csc331.flash_card_mania_app;

public class TestQuestion {
    private boolean questionType;
    private String question;
    private ImageInfo image;
    private String userAnswer;
    private String correctAnswer;
    private boolean gotCorrect;

    public TestQuestion(String question, String userAnswer, String correctAnswer, boolean gotCorrect) {
        this.question = question;
        this.userAnswer = userAnswer;
        this.correctAnswer = correctAnswer;
        this.gotCorrect = gotCorrect;

        questionType = true;
    }
    public TestQuestion(ImageInfo image, String userAnswer, String correctAnswer, boolean gotCorrect) {
        this.image = image;
        this.userAnswer = userAnswer;
        this.correctAnswer = correctAnswer;
        this.gotCorrect = gotCorrect;

        questionType = false;
    }

    public boolean getQuestionType() {
        return questionType;
    }
    public String getQuestion() {
        return question;
    }
    public ImageInfo getImage() {
        return image;
    }
    public String getAnswer() {
        return userAnswer;
    }
    public String getCorrectAnswer() {
        return correctAnswer;
    }
    public boolean didGetCorrect() {
        return gotCorrect;
    }
}
