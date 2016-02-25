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
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;


public class OnePlayer extends FragmentActivity {
    private static Dictionary dictionary;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_player);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout(width, height);

        // display player name on middle of top screen
        displayPlayerName();


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

        final String parentPath = this.getFilesDir().getAbsolutePath();
        new CountDownTimer(18000, 1000) {
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
                container.setHighscoresDic(container.getUser(), container.getPlayerScore());
                String fileName = Container.getHIGHSCORES();

                // read rank from file and store into highscores
                String filePath = parentPath+"/"+fileName;
                File file = new File(filePath);

                if(file.exists()){
                    try {
                        container.setHighscores(readHighscoreFromFile());
                        container.updateHighscores(container.getHighscoresDic());
                        // delete the content in file

                        emptyFileContent(filePath);
                        // rewrite rank into file
//                        container.setHighscores(readHighscoreFromFile());

                        writeToFile();
                        container.setHighscores(readHighscoreFromFile());


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    try {
                        container.updateHighscores(container.getHighscoresDic());
                        writeToFile();
                        readHighscoreFromFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                // go to highscore activity from this OnePlayer activity
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
        word = word.toLowerCase();
        int wordSize;

        try {
            wordSize = word.length();

            if (wordSize < 3) {
                WordSelection.unhighlightAll();
                container.setWord(null);
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
                    container.setWord(null);
                    return;
                }
            }
        }


        if (Container.getInstance().getDictionary().containsKey(word)) {
            wordList.add(word);
            container.setWordList(wordList);
            container.setWord(null);
        } else {
            WordSelection.unhighlightAll();
            container.setWord(null);
            return;
        }


        int score = container.getPlayerScore();

        switch (wordSize) {
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
        WordSelection.unhighlightAll();

        // update the score displayed on top left of screen every time hit submit button
        updateScoreOnTop();
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
        startActivity(intent);
    }


    public void goToHighScores() {
        Intent intent = new Intent(this, Highscores.class);
//        Container container = Container.getInstance();
//        // pass player name from this activity to onePlayer activity
//        intent.putExtra("playerScore", container.getPlayerScore());
//        intent.putExtra("playerName", container.getUser());
        startActivity(intent);
    }

    private void dialogConfirmation() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle("New Game!");

        // Setting Dialog Message
        alertDialog.setMessage("Are you sure you want to start a new game?");

        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.warning_icon);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                List<Character> dieList = BoardGenerator.getRandomDice();

                finish();
                startActivity(getIntent());
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

    public void writeToFile() throws IOException {
        Container container = Container.getInstance();
        String fileName = Container.getHIGHSCORES();

//        FileOutputStream writer = openFileOutput(fileName, Context.MODE_PRIVATE);
        OutputStream outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);

        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
        BufferedWriter writer = new BufferedWriter(outputStreamWriter);
        int size = container.getHighscores().size();
        for (int i = 0; i < size; i++) {
            Iterator index = container.getHighscores().get(i).keySet().iterator();
            String tempPlayerName = (String) index.next();
            String score = container.getHighscores().get(i).get(tempPlayerName).toString();

//            for(int j = 0; j < 5; j++) {
            writer.write(tempPlayerName);
            writer.write(",");
            writer.write(score);
            writer.newLine();
//            }
        }
        writer.close();

    }

    public void emptyFileContent(String toDeletePath) throws IOException {
        Container container = Container.getInstance();
        String fileName = Container.getHIGHSCORES();
        File file =  new File(toDeletePath);
        Boolean fileBoolean = file.delete();
//        FileOutputStream writer = openFileOutput(fileName, Context.MODE_PRIVATE);

//        PrintWriter pw = new PrintWriter(fileName);
//        pw.write("");
//        pw.close();

    }


//    public String readHighscoreFromFile() throws IOException {

    public ArrayList<HashMap<String, Integer>> readHighscoreFromFile() throws IOException {
        Container container = Container.getInstance();
        String fileName = Container.getHIGHSCORES();
//        FileInputStream reader = openFileInput(fileName);
        ArrayList<HashMap<String, Integer>> rank =  new ArrayList<HashMap<String, Integer>>();
        String temp = "";
        try {
            InputStream inputStream = openFileInput(fileName);
            // this is how to get file path
//            String path = this.getFilesDir().getAbsolutePath();

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    HashMap<String, Integer> tempPlayerAndScore = new HashMap<String, Integer>();
                    String[] splitString = receiveString.split("\\,");
//                    stringBuilder.append(receiveString);
                    tempPlayerAndScore.put(splitString[0], Integer.parseInt(splitString[1]));
                    rank.add(tempPlayerAndScore);
                }

                inputStream.close();
                temp = stringBuilder.toString();
            }
            else{
                return null;
            }

        } catch (FileNotFoundException e) {
            Log.e("OnePlayer activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("OnePlayer activity", "Can not read file: " + e.toString());

//        container.setHighscoresDic(container.getUser(), container.getPlayerScore());
//        container.updateHighscores(container.getHighscoresDic());

        }
        return rank;
    }


}
