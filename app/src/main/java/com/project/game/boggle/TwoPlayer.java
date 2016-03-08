package com.project.game.boggle;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;

import android.support.v4.app.FragmentActivity;

import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import android.hardware.Sensor;
import android.hardware.SensorManager;

import java.io.File;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;


public class TwoPlayer extends FragmentActivity {
    private BluetoothContainer mBluetooth;
    private BluetoothChatService mBluetoothChatService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_player);


        mBluetooth = (BluetoothContainer) getApplicationContext();
        mBluetoothChatService = mBluetooth.getmBluetoothChatService();

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout(width, height);

        // display player name on middle of top screen
        displayPlayerName();

        final String parentPath = this.getFilesDir().getAbsolutePath();
        new CountDownTimer(180000, 1000) {
            TextView timerTextField = (TextView) findViewById(R.id.countdown_timer);

            public void onTick(long millisUntilFinished) {
                timerTextField.setText("Timer: " + (millisUntilFinished / 60000) + ":" + ((millisUntilFinished / 1000) % 60));
            }

            // when time is up, set the time display "Time's up!"
            // set hightscore into container highscorcDic
            // if it is one of top ten highscore, update highscore ArrayList
            public void onFinish() {
                timerTextField.setText("TIME'S UP!");
                Container container = Container.getInstance();
                String score = "" + container.getPlayerScore();

                sendMessage(6, score);

//                if(container.getIsMaster())
//                {
//                    container.setPlayer1Done(true);
//                }
//                else
//                {
//                    container.setPlayer2Done(true);
//                }
//
//                sendMessage(8, "");
//                sendMessage(6, score);
//
//
//                if(Container.getInstance().getPlayer1Done() && Container.getInstance().getPlayer2Done()) {
//                    if (container.getPlayerScore() > container.getOtherPlayerScore()) {
//                        Toast.makeText(getApplicationContext(), "YOU WON!", Toast.LENGTH_LONG).show();
//                    } else if (container.getPlayerScore() == container.getOtherPlayerScore()){
//                        Toast.makeText(getApplicationContext(), "TIE!", Toast.LENGTH_LONG).show();
//                    }
//                    else {
//                        Toast.makeText(getApplicationContext(),"YOU LOSE!",Toast.LENGTH_LONG).show();
//                    }
//                }

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
        word = word.toLowerCase();
        int wordSize=word.length();

        // TODO - if cutthroat mode.
        // TODO - make VALID store response for sendmessagenew call.
        //if (!bluetoothChat.isMaster()) {
        //  bluetoothChat.sendMessageNEW(bluetoothChat.PLAYER_TWO_WORD, word);

        // TODO - client waits for response.
        //while (container.getValid() == -1) { }

        //response = bluetoothChat.VALID;
        //}

        Boolean newWord = submitWord(word);

        if (newWord) {
            int score = container.getPlayerScore();

                score = computeScore(wordSize);
                container.setPlayerScore(score);
                WordSelection.unhighlightAll();
                updateScoreOnTop();

        }
    }

    public Boolean submitWord(String word)
    {
        Container container = Container.getInstance();
        int wordSize;

        container.setWordMatch(false);


        try {
            wordSize = word.length();

            if (wordSize < 3) {
                WordSelection.unhighlightAll();
                container.setWord(null);
                return false;
            }
        } catch (Exception e) {
            return false;
        }

        ArrayList<String> wordList = container.getWordList();


        if (!wordList.isEmpty()) {
            Iterator words = wordList.iterator();

            while (words.hasNext()) {
                if (word.equals(words.next())) {
                    WordSelection.unhighlightAll();
                    container.setWord(null);
                    return false;
                }
            }
        }


        ArrayList<String> otherPlayer;

        if (Container.getInstance().getDictionary().containsKey(word)) {

            //for cutthroat mode
            if(container.getIsCutthroat()){


                otherPlayer = container.getOtherPlayer_WordList();



                if (!otherPlayer.isEmpty()) {

                    Iterator words2 = otherPlayer.iterator();

                    while (words2.hasNext()) {
                        if (word.equals(words2.next())) {
                            WordSelection.unhighlightAll();
                            container.setWord(null);
                            return false;
                        }
                    }

                }


                    wordList.add(word);

                    container.setWordList(wordList);
                    container.setWord(null);


                    sendMessage(9, word);
                    return true;


            } else {

                wordList.add(word);

                container.setWordList(wordList);
                container.setWord(null);
                return true;
            }




        } else {
            WordSelection.unhighlightAll();
            container.setWord(null);
            return false;
        }
    }

    public static int computeScore(int wordSize){
        Container container = Container.getInstance();
        int score = container.getPlayerScore();

        switch (wordSize) {
            case 0:
                score += 0;
                break;
            case 1:
                score += 0;
                break;
            case 2:
                score += 0;
                break;
            case 3:
                score += 1;
                break;
            case 4:
                score += 1;
                break;
            case 5:
                score += 2;
                break;
            case 6:
                score += 3;
                break;
            case 7:
                score += 5;
                break;
            case 8:
                score += 11;
                break;
            default:
                score += 11;
        }

        container.setPlayerScore(score);
        return score;
    }

    public void displayPlayerName() {
        Container container = Container.getInstance();
        TextView playerNameTextField = (TextView) findViewById(R.id.user_name);
        playerNameTextField.setText(container.getUser());
    }

    public void updateScoreOnTop() {
        Container container = Container.getInstance();
        TextView scoreTextField = (TextView) findViewById(R.id.user_score);
        int scoreAsInt = container.getPlayerScore();
        String scoreAsString = "Score: " + Integer.toString(scoreAsInt);
        scoreTextField.setText(scoreAsString);
    }


    public void onSolve(View view) {
        Intent intent = new Intent(this, Solution.class);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void sendMessage(int messageCode, String message) {
        String fullMessage = messageCode + message;
        byte[] send = fullMessage.getBytes();
        mBluetoothChatService.write(send);
    }

}