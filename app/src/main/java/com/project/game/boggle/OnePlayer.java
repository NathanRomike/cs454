package com.project.game.boggle;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class OnePlayer extends FragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_player);


        Container container = Container.getInstance();
        TextView playerNameTextField  = (TextView) findViewById(R.id.user_name);
        playerNameTextField.setText(container.getUser());

        new CountDownTimer(180000, 1000) {
            TextView timerTextField = (TextView) findViewById(R.id.countdown_timer);

            public void onTick(long millisUntilFinished) {
                timerTextField.setText("Timer: " + (millisUntilFinished / 60000) + ":" + ((millisUntilFinished / 1000) % 60));
            }

            /* TODO stop game and evaluate score */
            public void onFinish() {
                timerTextField.setText("TIME'S UP!");
                Container container = Container.getInstance();
                container.setHighscoresDic(container.getUser(), container.getPlayerScore());

                container.updateHighscores(container.getHighscoresDic());
                goToHighScores();
            }
        }.start();
    }

    public void onShowWords(View view) {
        Intent intent = new Intent(this, Words.class);
        startActivity(intent);
    }

    public void onSubmit(View view) {
        Container container = Container.getInstance();
        String word = container.getWord();
        int wordSize;

        try {
            wordSize = word.length();

            if (wordSize < 3) {
                WordSelection.unhighlightAll();
                return;
            }
        } catch (Exception e) {
            return;
        }


        ArrayList<String> wordList = container.getWordList();

        if (!wordList.isEmpty()) {
            Iterator words = wordList.iterator();

            while (words.hasNext()) {
                if (word.equals(words.next())) {
                    WordSelection.unhighlightAll();
                    return;
                }
            }
        }


        word = word.toLowerCase();

        if (Container.getInstance().getDictionary().containsKey(word)) {
            wordList.add(word);
            container.setWordList(wordList);
            container.setWord(null);
        } else {
            WordSelection.unhighlightAll();
            return;
        }


        int score = container.getPlayerScore();

        switch (wordSize) {
            case 3:  score += 1;
                     break;
            case 4:  score += 1;
                     break;
            case 5:  score += 2;
                     break;
            case 6:  score += 3;
                     break;
            case 7:  score += 5;
                     break;
            case 8:  score += 11;
                     break;
            default: score += 11;
        }

        container.setPlayerScore(score);
        WordSelection.unhighlightAll();
    }

    public void onSolve(View view) {

    }

    public void goToHighScores(){
        Intent intent = new Intent(this, Highscores.class);
        Container container = Container.getInstance();
        // pass player name from this activity to onePlayer activity
        intent.putExtra("playerScore", container.getPlayerScore());
        intent.putExtra("playerName", container.getUser());
        startActivity(intent);
    }
}
