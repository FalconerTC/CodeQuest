package com.codequest.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.codequest.main.R;
import com.codequest.utils.DBHandler;
import com.codequest.utils.Highscore;


public class HighscoreActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        generateTable();
    }

    //Create and add highscore table
    public void generateTable() {
        Highscore[] highscores = DBHandler.getDBHandler(this).getAllHighscores();
        ScrollView sv = new ScrollView(this);
        HorizontalScrollView hsv = new HorizontalScrollView(this);
        TableLayout table = new TableLayout(this);

        for (int i = 0; i < highscores.length; i++) {
            Log.d(HighscoreActivity.class.getSimpleName(), highscores[0].login + " " + highscores[0].score);
            TableRow row = new TableRow(this);

            TextView user = new TextView(this);
            user.setTextSize(32);
            user.setGravity(Gravity.LEFT);
            user.setText(highscores[i].login+"  ");

            TextView score = new TextView(this);
            score.setTextSize(32);
            score.setGravity(Gravity.RIGHT);
            score.setText(Integer.toString(highscores[i].score));

            row.addView(user);
            row.addView(score);

            table.addView(row);
        }

        hsv.addView(table);
        sv.addView(hsv);
        setContentView(sv);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(com.codequest.main.R.menu.menu_highscore, menu);
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
}
