package edu.gatech.seclass.sdpvocabquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Patterns;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.TreeMap;
import java.util.TreeSet;

public class Database extends SQLiteOpenHelper
{
    private SQLiteDatabase DB;

    private class ScoreResult
    {
        double score;
        Date date;
    }

    public enum ErrorCode
    {
        None,
        ItemAlreadyExists,
        ItemMissingFields,
        ItemInsertFailed,
        ItemDoesNotExist,
        InvalidEmail
    }

    private static final String STUDENTS_TABLE_NAME = "STUDENTS";

    private static final int STUDENT_COL_MAJOR = 1;
    private static final int STUDENT_COL_SENIORITY = 2;
    private static final int STUDENT_COL_EMAIL = 3;

    private static final String QUIZZES_TABLE_NAME = "QUIZZES";

    private static final int QUIZ_COL_DESCRIPTION = 1;
    private static final int QUIZ_COL_CREATOR_NAME = 2;
    private static final int QUIZ_COL_NUM_OF_WORDS = 3;
    private static final int QUIZ_COL_WORD_START = 4;
    private static final int QUIZ_COL_WORD_END = 23;
    private static final int QUIZ_COL_INCORRECT_START = 24;
    private static final int QUIZ_COL_INCORRECT_END = 53;

    private static final String QUIZ_RESULTS_TABLE_NAME = "QUIZ_RESULTS";

    private static final double PERFECT_SCORE = 1.00;

    public Database(Context context)
    {
        super(context, "ADPVocabQuiz.DB", null, 1);

        DB = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL(String.format("create table %s (USERNAME text primary key , MAJOR " +
                        "text, SENIORITY text, EMAIL text)",
                STUDENTS_TABLE_NAME));

        sqLiteDatabase.execSQL(String.format("create table %s (NAME text primary key, DESCRIPTION" +
                        " text, CREATOR_NAME text, NUM_OF_WORDS integer, WORD_0 test, " +
                        "DEFINITION_0 text, WORD_1 text, DEFINITION_1 text, WORD_2 text, " +
                        "DEFINITION_2 text, WORD_3 text, DEFINITION_3 text, WORD_4 text, " +
                        "DEFINITION_4 text, WORD_5 text, DEFINITION_5 text, WORD_6 text, " +
                        "DEFINITION_6 text, WORD_7 text, DEFINITION_7 text, WORD_8 text, " +
                        "DEFINITION_8 text, WORD_9 text, DEFINITION_9 text, INCORRECT_0 text, " +
                        "INCORRECT_1 text, INCORRECT_2 text, INCORRECT_3 text, INCORRECT_4 text, " +
                        "INCORRECT_5 text, INCORRECT_6 text, INCORRECT_7 text, INCORRECT_8 text, " +
                        "INCORRECT_9 text, INCORRECT_10 text, INCORRECT_11 text, INCORRECT_12 " +
                        "text, INCORRECT_13 text, INCORRECT_14 text, INCORRECT_15 text, " +
                        "INCORRECT_16 text, INCORRECT_17 text, INCORRECT_18 text, INCORRECT_19 " +
                        "text, INCORRECT_20 text, INCORRECT_21 text, INCORRECT_22 text, " +
                        "INCORRECT_23 text, INCORRECT_24 text, INCORRECT_25 text, INCORRECT_26 " +
                        "text, INCORRECT_27 text, INCORRECT_28 text, INCORRECT_29 text, " +
                        "INCORRECT_30 text)",
                QUIZZES_TABLE_NAME));

        sqLiteDatabase.execSQL(String.format("create table %s (ID integer primary key " +
                        "autoincrement, STUDENT_NAME text, QUIZ_NAME text, DATE integer, SCORE " +
                        "double)",
                QUIZ_RESULTS_TABLE_NAME));
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        onCreate(sqLiteDatabase);
    }


    /*
            METHODS FOR ADDING/REMOVING STUDENTS
     */


    public ErrorCode addStudent(Student student)
    {
        boolean success;
        ErrorCode error = ErrorCode.None;

        success = !student.username.isEmpty();
        success &= !student.email.isEmpty();
        success &= !student.major.isEmpty();

        if (success)
        {
            success = !studentExists(student.username);

            if (success)
            {
                success = Patterns.EMAIL_ADDRESS.matcher(student.email).matches();

                if(success)
                {
                    ContentValues contentValues = new ContentValues();

                    contentValues.put("USERNAME", student.username);
                    contentValues.put("MAJOR", student.major);
                    contentValues.put("EMAIL", student.email);
                    contentValues.put("SENIORITY", student.seniority.name());

                    long result = DB.insert(STUDENTS_TABLE_NAME, null, contentValues);

                    if (result < 0)
                    {
                        error = ErrorCode.ItemInsertFailed;
                    }
                }
                else
                {
                    error = ErrorCode.InvalidEmail;
                }
            } else
            {
                error = ErrorCode.ItemAlreadyExists;
            }
        } else
        {
            error = ErrorCode.ItemMissingFields;
        }

        return error;
    }

    public boolean studentExists(String username)
    {
        boolean exists = false;

        String query = String.format("SELECT * FROM %s WHERE USERNAME = '%s'",
                STUDENTS_TABLE_NAME, username);

        Cursor studentCursor = DB.rawQuery(query, null);

        if (studentCursor.moveToFirst())
        {
            exists = true;
        }

        studentCursor.close();

        return exists;
    }

    public Student getStudent(String username)
    {
        Student student = null;

        String query = String.format("SELECT * FROM %s WHERE USERNAME = '%s'",
                STUDENTS_TABLE_NAME, username);

        Cursor studentCursor = DB.rawQuery(query, null);

        if (studentCursor.moveToFirst())
        {
            String email;
            String major;
            String seniorityStr;
            Seniority seniority;

            email = studentCursor.getString(STUDENT_COL_EMAIL);
            major = studentCursor.getString(STUDENT_COL_MAJOR);
            seniorityStr = studentCursor.getString(STUDENT_COL_SENIORITY);
            seniority = Seniority.valueOf(seniorityStr);

            student = new Student(username, major, seniority, email);
        }

        studentCursor.close();

        return student;
    }


    /*
              METHODS FOR ADDING/REMOVING QUIZZES
     */

    public boolean quizExists(String quizName)
    {
        boolean exists = false;

        String query = String.format("SELECT * FROM %s WHERE NAME = '%s'",
                QUIZZES_TABLE_NAME, quizName);

        Cursor quizCursor = DB.rawQuery(query, null);

        if (quizCursor.moveToFirst())
        {
            exists = true;
        }

        quizCursor.close();

        return exists;
    }


    public ErrorCode addQuiz(Quiz quiz)
    {
        ErrorCode error = ErrorCode.None;

        boolean success;

        success = !quiz.quizName.isEmpty();
        success &= !quiz.description.isEmpty();
        success &= (quiz.quizLength > 0);

        if (success)
        {
            ContentValues contentValues = new ContentValues();

            contentValues.put("NAME", quiz.quizName);
            contentValues.put("DESCRIPTION", quiz.description);
            contentValues.put("CREATOR_NAME", quiz.quizCreator);
            contentValues.put("NUM_OF_WORDS", quiz.quizLength);

            for (int word = 0; word < quiz.quizLength; word++)
            {
                String wordStr = "WORD_" + word;
                contentValues.put(wordStr, quiz.vocabWords.get(word).GetWord());

                String definitionStr = "DEFINITION_" + word;
                contentValues.put(definitionStr, quiz.vocabWords.get(word).GetDefinition());
            }

            int incorrectDefCount = quiz.quizLength * 3;

            for (int incorrect = 0; incorrect < incorrectDefCount; incorrect++)
            {
                String incorrectStr = "INCORRECT_" + incorrect;
                contentValues.put(incorrectStr, quiz.incorrectDefs.get(incorrect));
            }

            long result = DB.insert(QUIZZES_TABLE_NAME, null, contentValues);

            if (result < 0)
            {
                error = ErrorCode.ItemInsertFailed;
            }
        } else
        {
            error = ErrorCode.ItemMissingFields;
        }

        return error;
    }

    public Quiz getQuiz(String quizName)
    {
        Quiz quiz = null;

        String query = String.format("SELECT * FROM %s WHERE NAME = '%s'",
                QUIZZES_TABLE_NAME, quizName);

        Cursor quizCursor = DB.rawQuery(query, null);

        if (quizCursor.moveToFirst())
        {
            String description;
            int quizLength;
            ArrayList<VocabWord> vocabWords = new ArrayList<>();
            ArrayList<String> incorrectDefs = new ArrayList<>();
            String quizCreator;

            description = quizCursor.getString(QUIZ_COL_DESCRIPTION);
            quizLength = quizCursor.getInt(QUIZ_COL_NUM_OF_WORDS);
            quizCreator = quizCursor.getString(QUIZ_COL_CREATOR_NAME);

            int wordSectionEnd = QUIZ_COL_WORD_START + (quizLength * 2);
            int incorrectSectionEnd = QUIZ_COL_INCORRECT_START + (quizLength * 3);

            for (int wordCol = QUIZ_COL_WORD_START; wordCol < wordSectionEnd; wordCol += 2)
            {
                String word = quizCursor.getString(wordCol);
                String definition = quizCursor.getString(wordCol + 1);

                VocabWord vocabWord = new VocabWord(word, definition);

                vocabWords.add(vocabWord);
            }

            for (int incorrectCol = QUIZ_COL_INCORRECT_START; incorrectCol <
                    incorrectSectionEnd; incorrectCol++)
            {
                incorrectDefs.add(quizCursor.getString(incorrectCol));
            }

            quiz = new Quiz(quizName, description, quizLength, vocabWords, incorrectDefs,
                    quizCreator);
        }

        quizCursor.close();

        return quiz;
    }

    public ErrorCode removeQuiz(String quizName)
    {
        ErrorCode error = ErrorCode.None;

        String query = String.format("DELETE FROM %s WHERE NAME = '%s'",
                QUIZZES_TABLE_NAME, quizName);

        DB.execSQL(query);

        removeQuizScores(quizName);

        return error;
    }


    /*
              METHODS FOR ADDING/REMOVING QUIZ SCORES
     */


    public ErrorCode addQuizScore(QuizScoreStatistic quizScore)
    {
        ErrorCode error = ErrorCode.None;

        ContentValues contentValues = new ContentValues();

        contentValues.put("QUIZ_NAME", quizScore.quizName);
        contentValues.put("STUDENT_NAME", quizScore.studentName);
        contentValues.put("SCORE", quizScore.score);
        contentValues.put("DATE", quizScore.dateCompleted.getTime());

        long result = DB.insert(QUIZ_RESULTS_TABLE_NAME, null, contentValues);

        if (result < 0)
        {
            error = ErrorCode.ItemInsertFailed;
        }

        return error;
    }

    private void removeQuizScores(String quizName)
    {
        String query = String.format("DELETE FROM %s WHERE QUIZ_NAME = '%s'",
                QUIZ_RESULTS_TABLE_NAME, quizName);

        DB.execSQL(query);
    }


    /*
                UTILITY METHODS
     */


    public ArrayList<String> getQuizzesCreated(String username)
    {
        ArrayList<String> quizNames = new ArrayList<>();

        String query = String.format("SELECT NAME FROM %s WHERE CREATOR_NAME = '%s'",
                QUIZZES_TABLE_NAME, username);

        Cursor quizCursor = DB.rawQuery(query, null);

        if (quizCursor.moveToFirst())
        {
            do
            {
                quizNames.add(quizCursor.getString(0));
            } while (quizCursor.moveToNext());
        }

        quizCursor.close();

        return quizNames;
    }

    public ArrayList<String> getQuizzesCreatedByOthers(String username)
    {
        ArrayList<String> quizNames = new ArrayList<>();

        String query = String.format("SELECT NAME FROM %s WHERE NOT CREATOR_NAME = '%s'",
                QUIZZES_TABLE_NAME, username);

        Cursor quizCursor = DB.rawQuery(query, null);

        if (quizCursor.moveToFirst())
        {
            do
            {
                quizNames.add(quizCursor.getString(0));
            } while (quizCursor.moveToNext());
        }

        quizCursor.close();

        return quizNames;
    }

    public ArrayList<String> getQuizNamesByDateTaken(String username)
    {
        TreeMap<Long, String> sortedNames = new TreeMap<>(Collections.reverseOrder());
        ArrayList<String> untakenQuizzes = new ArrayList<>();
        ArrayList<String> quizNames = getAllQuizNames();

        for (String Name : quizNames)
        {
            long date = getLastDateTaken(username, Name);

            if(date != -1)
            {
                sortedNames.put(getLastDateTaken(username, Name), Name);
            }
            else
            {
                untakenQuizzes.add(Name);
            }
        }

        ArrayList<String> sorted = new ArrayList<>(sortedNames.values());
        sorted.addAll(untakenQuizzes);

        return sorted;
    }

    private ArrayList<String> getAllQuizNames()
    {
        ArrayList<String> quizNames = new ArrayList<>();

        String query = String.format("SELECT NAME FROM %s", QUIZZES_TABLE_NAME);

        Cursor quizCursor = DB.rawQuery(query, null);

        if (quizCursor.moveToFirst())
        {
            do
            {
                quizNames.add(quizCursor.getString(0));
            } while (quizCursor.moveToNext());
        }

        quizCursor.close();

        return quizNames;
    }

    private long getLastDateTaken(String username, String quizName)
    {
        long date = -1;

        String query = String.format("SELECT DATE FROM %s WHERE STUDENT_NAME = '%s' AND QUIZ_NAME" +
                " = '%s' ORDER BY DATE DESC", QUIZ_RESULTS_TABLE_NAME, username, quizName);

        Cursor dateCursor = DB.rawQuery(query, null);

        if (dateCursor.moveToFirst())
        {
            date = dateCursor.getInt(0);
        }

        dateCursor.close();

        return date;
    }

    public QuizStatisticDisplayInfo getQuizStatisticDisplayInfo(String username, String quizName)
    {
        ArrayList<String> perfectScorers;
        ScoreResult highestScore;
        ScoreResult firstScore;

        firstScore = getFirstQuizScore(username, quizName);

        highestScore = getHighestQuizScore(username, quizName);

        perfectScorers = getPerfectScorers(quizName);

        return new QuizStatisticDisplayInfo(quizName, perfectScorers, highestScore.score,
                highestScore.date, firstScore.score, firstScore.date);
    }

    private ScoreResult getFirstQuizScore(String username, String quizName)
    {
        ScoreResult score = new ScoreResult();

        score.date = null;
        score.score = 0.0;

        String query = String.format("SELECT SCORE, DATE FROM %s WHERE STUDENT_NAME = '%s' AND " +
                "QUIZ_NAME = '%s' ORDER BY DATE ASC", QUIZ_RESULTS_TABLE_NAME, username, quizName);

        Cursor scoreCursor = DB.rawQuery(query, null);

        if (scoreCursor.moveToFirst())
        {
            score.score = scoreCursor.getDouble(0);
            score.date = new Date(scoreCursor.getLong(1));
        }

        scoreCursor.close();

        return score;
    }

    private ScoreResult getHighestQuizScore(String username, String quizName)
    {
        ScoreResult score = new ScoreResult();

        score.date = null;
        score.score = 0.0;

        String query = String.format("SELECT SCORE, DATE FROM %s WHERE STUDENT_NAME = '%s' AND " +
                "QUIZ_NAME = '%s' ORDER BY SCORE DESC", QUIZ_RESULTS_TABLE_NAME, username,
                quizName);

        Cursor scoreCursor = DB.rawQuery(query, null);

        if (scoreCursor.moveToFirst())
        {
            score.score = scoreCursor.getDouble(0);
            score.date = new Date(scoreCursor.getLong(1));
        }

        scoreCursor.close();

        return score;
    }

    private ArrayList<String> getPerfectScorers(String quizName)
    {
        ArrayList<String> perfectScorers = new ArrayList<>();

        String query = String.format("SELECT STUDENT_NAME FROM %s WHERE QUIZ_NAME = '%s' AND " +
                "SCORE = '%.2f' ORDER BY DATE ASC", QUIZ_RESULTS_TABLE_NAME, quizName,
                PERFECT_SCORE);

        Cursor scoreCursor = DB.rawQuery(query, null);

        if (scoreCursor.moveToFirst())
        {
            do
            {
                String name = scoreCursor.getString(0);
                if(!perfectScorers.contains(name))
                {
                    perfectScorers.add(name);
                }
            } while (scoreCursor.moveToNext() && (perfectScorers.size() < 3));
        }

        for(int idx1 = 0; idx1 < perfectScorers.size()-1; idx1++)
        {
            for(int idx2 = idx1 + 1; idx2 < perfectScorers.size(); idx2++)
            {
                if(perfectScorers.get(idx1).compareTo(perfectScorers.get(idx2)) > 0)
                {
                    Collections.swap(perfectScorers, idx1, idx2);
                }
            }
        }

        while (perfectScorers.size() < 3)
        {
            perfectScorers.add("");
        }

        scoreCursor.close();


        return perfectScorers;
    }
}
