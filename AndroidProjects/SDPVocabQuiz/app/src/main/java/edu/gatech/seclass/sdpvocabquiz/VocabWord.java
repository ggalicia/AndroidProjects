package edu.gatech.seclass.sdpvocabquiz;

/**
 * Created by Dylan Barrett on 10/8/2018.
 */

public class VocabWord {

    private String word;
    private String definition;

    public String GetWord()
    {
        return word;
    }

    public String GetDefinition()
    {
        return definition;
    }

    public VocabWord(String word, String definition) {
        this.word = word;
        this.definition = definition;
    }

    @Override
    public String toString() {
        return word;
    }
}
