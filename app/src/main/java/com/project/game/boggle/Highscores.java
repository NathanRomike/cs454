package com.project.game.boggle;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Highscores extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Since highscore activity has 20 textViews
        // this is the algorithm allow me to loop on the textView
        // and set the each textView to display player name and their score
        Container container = Container.getInstance();
        try {
            container.setHighscores(readHighscoreFromFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        displayTopTenScore();

    }

    public void displayTopTenScore(){
        Container container = Container.getInstance();
//        if(container.getHighscores() == null){
//
//        }
        int length = container.getHighscores().size();

        for (int i = 0; i < length; i++) {
            int j = i + 10 + 1;
            int k = i + 1;
            String playerTextViewID = "highscoreTextView"+ k;
            String scoreTextViewID = "highscoreTextView" + j;
            int playerID = getResources().getIdentifier(playerTextViewID, "id", getPackageName());
            int scoreID = getResources().getIdentifier(scoreTextViewID, "id", getPackageName());

            if (playerID != 0) {
                TextView playerTextView = (TextView) findViewById(playerID);
                TextView scoreTextView = (TextView) findViewById(scoreID);


                Iterator listIndex = container.getHighscores().get(i).keySet().iterator();
                String listKey = (String)listIndex.next();
                playerTextView.setText(listKey);
                scoreTextView.setText(Integer.toString(container.getHighscores().get(i).get(listKey)));

            }
        }
    }

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
