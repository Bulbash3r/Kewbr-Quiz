package Model;

public class Question {
    private String question;
    private String answer;
    private int score;
    private int time;

    public void setTime(int time) {
        this.time = time;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public int getTime() {
        return time;
    }

    public int getScore() {
        return score;
    }
}
