package edu.gatech.seclass.sdpvocabquiz;

import java.util.ArrayList;
import java.util.Date;
import java.util.TreeSet;

public class QuizStatisticDisplayInfo
{
    public String quizName;

    public ArrayList<String> perfectScorers;

    public Date highestScoreDate;
    public double highestScore;

    public Date firstScoreDate;
    public double firstScore;

    QuizStatisticDisplayInfo(String quizName, ArrayList<String> perfectScorers, double
            highestScore, Date highestScoreDate, double firstScore, Date firstScoreDate)
    {
        this.quizName = quizName;
        this.perfectScorers = perfectScorers;
        this.highestScore = highestScore;
        this.highestScoreDate = highestScoreDate;
        this.firstScore = firstScore;
        this.firstScoreDate = firstScoreDate;
    }
}
