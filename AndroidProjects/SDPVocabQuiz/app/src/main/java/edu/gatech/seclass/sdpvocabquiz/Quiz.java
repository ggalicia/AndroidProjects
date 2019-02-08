package edu.gatech.seclass.sdpvocabquiz;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Dylan Barrett on 10/8/2018.
 */

public class Quiz implements Serializable {

    String quizName;
    String description;
    int quizLength;
    ArrayList<VocabWord> vocabWords;
    ArrayList<String> incorrectDefs;
    String quizCreator;

    public String getQuizName() {
        return quizName;
    }

    public String getDescription() {
        return description;
    }

    public int getQuizLength() {
        return quizLength;
    }

    public ArrayList<VocabWord> getVocabWords() {
        return vocabWords;
    }

    public ArrayList<String> getIncorrectDefs() {
        return incorrectDefs;
    }

    public String getQuizCreator() {
        return quizCreator;
    }

    public Quiz(String quizName, String description, int quizLength, ArrayList<VocabWord> vocabWords, ArrayList<String> incorrectDefs, String quizCreator) {
        this.quizName = quizName;
        this.description = description;
        this.quizLength = quizLength;
        this.vocabWords = vocabWords;
        this.incorrectDefs = incorrectDefs;
        this.quizCreator = quizCreator;
    }

    @Override
    public String toString() {
        return quizName;
    }
}
