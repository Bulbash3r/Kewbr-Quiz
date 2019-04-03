package App.Model;

import java.util.ArrayList;
import java.util.Date;

public class Package {
    private String packageName;
    private int difficulty;
    private ArrayList<String> questions;
    private ArrayList<String> answers;
    private Date date;

    Package(String packageName, int difficulty, ArrayList<String> questions, ArrayList<String> answers, Date date) {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
