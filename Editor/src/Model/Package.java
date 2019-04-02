package Model;

import java.util.ArrayList;

public class Package {
    private String packageName;
    private int difficulty;
    private String date;
    private ArrayList<Question> questions;

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getDate() {
        return date;
    }

    public int getDifficulty() {
        return difficulty;
    }
}
