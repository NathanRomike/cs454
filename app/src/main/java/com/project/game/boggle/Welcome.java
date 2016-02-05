package com.project.game.boggle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;

/**
 * Created by nan on 1/23/16.
 */
public class Welcome extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        HashMap dictionary = new HashMap();
        ArrayList<Integer> highscores = new ArrayList<>();
        ArrayList<String> wordList = new ArrayList<>();

        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(getAssets().open("english-dictionary.txt")));
            String word;

            while ((word = bf.readLine()) != null) {
                dictionary.put(word, word);
            }

            Container.getInstance().setDictionary(dictionary);
            Container.getInstance().setWordList(wordList);
        } catch (IOException e) {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_title_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void enterTittleScreen(View view){
        Intent intent = new Intent(this, TitleScreen.class);
        startActivity(intent);
    }
}
