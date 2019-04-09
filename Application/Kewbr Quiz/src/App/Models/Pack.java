package App.Models;

import com.google.gson.*;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Pack {

    private String name;
    private int difficulty;
    private ArrayList<Question> questions;
    private Date date;

    public Pack(String name, int difficulty, Date date) {
        this.name = name;
        this.difficulty = difficulty;
        this.date = date;
        questions = new ArrayList<>();
    }

    public void addNewQuestion(String question, String answer) {
        questions.add(new Question(question, answer));
    }

    public void deleteQuestion(int index) {
        questions.remove(index - 1);
    }

    public void editQuestion(int index, String question, String answer) {
        questions.set(index - 1, new Question(question, answer));
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

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = new ArrayList<>(questions);
    }

    public static class Filter implements FilenameFilter {
        @Override
        public boolean accept(File dir, String name) {
            return name.endsWith(".kwq");
        }
    }
}