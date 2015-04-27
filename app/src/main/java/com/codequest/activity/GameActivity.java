package com.codequest.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;

import com.codequest.main.GameController;
import com.codequest.main.R;


public class GameActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //setContentView(com.codequest.main.R.layout.activity_game);

        super.onCreate(savedInstanceState);
        setContentView(com.codequest.main.R.layout.activity_game);

        // Fetch extra paramaters
        Intent intent = getIntent();
        String question_text = intent.getStringExtra(GameController.EXTRA_QUESTION);
        String answer1_text = intent.getStringExtra(GameController.EXTRA_ANSWER1);
        String answer2_text = intent.getStringExtra(GameController.EXTRA_ANSWER2);
        String answer3_text = intent.getStringExtra(GameController.EXTRA_ANSWER3);
        String answer4_text = intent.getStringExtra(GameController.EXTRA_ANSWER4);

        // Set question text
        TextView question = (TextView)findViewById(R.id.question);
        question.setText(question_text);

        // Set answer1 text
        Button answer1 = (Button)findViewById(R.id.button1);
        answer1.setText(answer1_text);

        // Set answer2 text
        Button answer2 = (Button)findViewById(R.id.button2);
        answer2.setText(answer2_text);

        // Set answer3 text
        Button answer3 = (Button)findViewById(R.id.button3);
        answer3.setText(answer3_text);

        // Set answer4 text
        Button answer4 = (Button)findViewById(R.id.button4);
        answer4.setText(answer4_text);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(com.codequest.main.R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == com.codequest.main.R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void submitAnswer(View view) {
        String selectedValue = ((Button)view).getText().toString();
        GameController.getGameController().update(selectedValue);
    }
}
