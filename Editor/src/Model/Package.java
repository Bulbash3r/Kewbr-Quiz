package Model;

import java.util.ArrayList;

public class Package {
    private String packageName;
    private int difficulty;
    private ArrayList<String> questions;
    private ArrayList<String> answers;
    private String date;

    private Package(String packageName, int difficulty, ArrayList<String> questions, ArrayList<String> answers, String date) {
        this.packageName = packageName;
        this.difficulty = difficulty;
        this.questions = questions;
        this.answers = answers;
        this.date = date;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public ArrayList<String> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<String> questions) {
        this.questions = questions;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
