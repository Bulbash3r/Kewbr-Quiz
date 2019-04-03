package Model;

import java.util.ArrayList;

public class Question {
    private String questions;
    private String answers;

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }

    public Question(String questions, String answers) {

        this.questions = questions;
        this.answers = answers;
    }
}
