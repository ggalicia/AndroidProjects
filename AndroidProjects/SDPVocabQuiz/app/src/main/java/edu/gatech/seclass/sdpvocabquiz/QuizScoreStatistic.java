package edu.gatech.seclass.sdpvocabquiz;

import java.util.Date;

/**
 * Created by Dylan Barrett on 10/8/2018.
 */

public class QuizScoreStatistic {

    String quizName;
    String studentName;
    float score;
    Date dateCompleted;

    public String getQuizName() {
        return quizName;
    }

    public String getStudentName() {
        return studentName;
    }

    public float getScore() {
        return score;
    }

    public Date getDateCompleted() {
        return dateCompleted;
    }

    public QuizScoreStatistic(String quizName, String studentName, float score, Date dateCompleted) {
        this.quizName = quizName;
        this.studentName = studentName;
        this.score = score;
        this.dateCompleted = dateCompleted;
    }
}
