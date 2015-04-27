package com.codequest.main;

import android.app.Activity;
import android.content.Intent;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Falcon on 4/26/2015.
 */
public class GameController {

    public final static String EXTRA_MESSAGE = "com.codequest.main.MESSAGE";

    public final static String EXTRA_QUESTION = "com.codequest.main.QUESTION";
    public final static String EXTRA_ANSWER1 = "com.codequest.main.ANSWER1";
    public final static String EXTRA_ANSWER2 = "com.codequest.main.ANSWER2";
    public final static String EXTRA_ANSWER3 = "com.codequest.main.ANSWER3";
    public final static String EXTRA_ANSWER4 = "com.codequest.main.ANSWER4";
    public final static int DEFAULT_QUESTION_COUNT = 5;

    private static GameController controller;
    private Activity original;
    private Intent game;
    private Integer[] questionOrder;

    private int totalQuestions;
    private int correctAnswers;
    private int currentQuestionIndex;
    private Question currentQuestion;

    private GameController(Activity original) {
        this.original = original;
        this.currentQuestionIndex = 0;
        this.correctAnswers = 0;
        this.totalQuestions = DEFAULT_QUESTION_COUNT;
        questionOrder = generateQuestionOrder();
    }

    private Integer[] generateQuestionOrder() {
        ArrayList<Integer> list = new ArrayList<>(totalQuestions);
        for (int i = 1; i <= totalQuestions; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        return list.toArray(new Integer[totalQuestions]);
    }

    public static GameController getGameController(Activity original) {
        if (controller == null)
            controller = new GameController(original);
        return controller;
    }

    // Generate the current question
    public Question generateQuestion() {
        int value = questionOrder[currentQuestionIndex];
        String questionText = original.getResources().getStringArray(R.array.questions)[value];
        String answerA = original.getResources().getStringArray(R.array.answerA)[value];
        String answerB = original.getResources().getStringArray(R.array.answerB)[value];
        String answerC = original.getResources().getStringArray(R.array.answerC)[value];
        String answerD = original.getResources().getStringArray(R.array.answerD)[value];
        String correctAnswer = original.getResources().getStringArray(R.array.correctAnswers)[value];
        return new Question(questionText, answerA, answerB, answerC, answerD, correctAnswer);
    }

    public void startGame() {
        game = new Intent(original, GameActivity.class);
        startNextQuestion();
    }

    //Interpret and answer and progress the game
    public void update(String answer) {
        currentQuestionIndex++;
        // Check answer
        if (answer.equals(currentQuestion.correct_answer))
            correctAnswers++;
        //Progress game
        if (currentQuestionIndex >= totalQuestions) {
            endGame();
        } else {
            startNextQuestion();
        }
    }

    public void startNextQuestion() {
        currentQuestion = generateQuestion();
        game.putExtra(EXTRA_QUESTION, currentQuestion.question);
        game.putExtra(EXTRA_ANSWER1, currentQuestion.answer1);
        game.putExtra(EXTRA_ANSWER2, currentQuestion.answer2);
        game.putExtra(EXTRA_ANSWER3, currentQuestion.answer3);
        game.putExtra(EXTRA_ANSWER4, currentQuestion.answer4);

        original.startActivity(game);
    }

    // Proceed to highscore activity
    public void endGame() {
        Intent intent = new Intent(original, HighscoreActivity.class);
        intent.putExtra(EXTRA_MESSAGE, ("Correct answers: " + correctAnswers));
        original.startActivity(intent);


    }


}
