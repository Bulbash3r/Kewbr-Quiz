package App.Models;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Date;

public class Pack {

    private String name;
    private int difficulty;
    private ArrayList<String> questions;
    private ArrayList<String> answers;
    private Date date;

    public Pack(String name, int difficulty, Date date) {
        this.name = name;
        this.difficulty = difficulty;
        this.date = date;
        questions = new ArrayList<>();
        answers = new ArrayList<>();
    }

    public void addNewQuestion(String question, String answer) {
        questions.add(question);
        answers.add(answer);
    }

    public void deleteQuestion(int index) {
        questions.remove(index - 1);
        answers.remove(index - 1);
    }

    public void editQuestion(int index, String question, String answer) {
        questions.set(index - 1, question);
        answers.set(index - 1, answer);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ArrayList<ArrayList<String>> getQuestions() {
        ArrayList<ArrayList<String>> lists = new ArrayList<ArrayList<String>>(2);
        lists.add(questions);
        lists.add(answers);
        return lists;
    }

    public void setQuestions(ArrayList<String> questions, ArrayList<String> answers) {
        this.questions = questions;
        this.answers = answers;
    }

    public static class Filter implements FilenameFilter {
        @Override
        public boolean accept(File dir, String name) {
            return name.endsWith(".kwq");
        }
    }
}
