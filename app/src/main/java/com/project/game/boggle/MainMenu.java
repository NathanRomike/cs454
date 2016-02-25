package com.project.game.boggle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainMenu extends AppCompatActivity {

    private String playerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        // receiving player name from title activity
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            playerName = extras.getString("playerName");
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

    public void onePlayerScreen(View view) {
        Intent intent = new Intent(this, OnePlayer.class);
        // pass player name from this activity to onePlayer activity
        intent.putExtra("playerName", playerName);
        startActivity(intent);
    }

    public void twoPlayerScreen(View view) {
        Intent intent = new Intent(this, TwoPlayer.class);
        startActivity(intent);
    }

    public void highscoresScreen(View view) {
        Intent intent = new Intent(this, Highscores.class);
        startActivity(intent);
    }

    public void helpScreen(View view) {
        Intent intent = new Intent(this, Help.class);
        startActivity(intent);
    }
}
