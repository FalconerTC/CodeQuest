package com.codequest.main;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by Falcon on 4/26/2015.
 */
public class GameController {

    public final static String EXTRA_QUESTION = "com.codequest.main.QUESTION";
    public final static String EXTRA_ANSWER1 = "com.codequest.main.ANSWER1";
    public final static String EXTRA_ANSWER2 = "com.codequest.main.ANSWER2";
    public final static String EXTRA_ANSWER3 = "com.codequest.main.ANSWER3";
    public final static String EXTRA_ANSWER4 = "com.codequest.main.ANSWER4";

    private static GameController controller;
    private Activity original;
    private Intent game;

    private int totalQuestions;
    private int correctAnswers;
    private int currentQuestion;

    private GameController(Activity original) {
        this.original = original;
    }

    public static GameController getGameController(Activity original) {
        if (controller == null)
            controller = new GameController(original);
        return controller;
    }

//    public Question generateQuestion() {
//
//    }


    public void startGame() {
        game = new Intent(original, GameActivity.class);
        int val = R.array.questions;

        game.putExtra(EXTRA_QUESTION, original.getString(R.string.question1));
        original.startActivity(game);
    }


}
