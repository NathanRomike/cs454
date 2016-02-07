package com.project.game.boggle;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class OnePlayer extends FragmentActivity  {


    /*
    // The following are used for the shake detection
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;
*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_player);


/*
        // ShakeDetector initialization
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {
            @Override
            public void onShake(int count) {

                dialogConfirmation();

            }


        });
*/
        new CountDownTimer(180000, 1000) {
            TextView timerTextField = (TextView) findViewById(R.id.countdown_timer);

            public void onTick(long millisUntilFinished) {
                timerTextField.setText("Timer: " + (millisUntilFinished / 60000) + ":" + ((millisUntilFinished / 1000) % 60));
            }

            /* TODO stop game and evaluate score */
            public void onFinish() {
                timerTextField.setText("TIME'S UP!");
            }
        }.start();
    }

    public void onSubmit(View view) {
        Container container = Container.getInstance();
        String word = container.getWord();
        int wordSize;

        try {
            wordSize = word.length();

            if (wordSize < 3) {
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
        //WordSelection.unhighlightAll();
    }

    private void dialogConfirmation()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle("New Game!");

        // Setting Dialog Message
        alertDialog.setMessage("Are you sure you want to start a new game?");

        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.warning_icon);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {

                List<Character> dieList = BoardGenerator.getRandomDice();


                BoggleSolver.setBoard(dieList);
                BoggleSolver.boggleWordListSearch();

                WordSelection.initQueue();

            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    /*
    @Override
    public void onResume() {
        super.onResume();
        // Add the following line to register the Session Manager Listener onResume
        mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        // Add the following line to unregister the Sensor Manager onPause
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }
*/

}
