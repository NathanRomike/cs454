package com.project.game.boggle;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;

public class MainMenu extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private String playerName;
    private View signinButton;
    GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        // receiving player name from title activity
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            playerName = extras.getString("playerName");
        }
        signinButton = findViewById(R.id.signinButton);
        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "HELLO?", Toast.LENGTH_SHORT).show();
            }
        });

        googleApiClient = new GoogleApiClient.Builder(this)
                .addApiIfAvailable(Games.API, Games.SCOPE_GAMES)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        googleApiClient.connect();
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
        if (googleApiClient.isConnected()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Play online or locally?")
                    .setPositiveButton("ONLINE!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent searchPlayersIntent = Games.Players.getPlayerSearchIntent(googleApiClient);
                            startActivityForResult(searchPlayersIntent, 123);
                        }
                    })
                    .setNeutralButton("BLUETOOTH", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), BluetoothChat.class);
                            startActivity(intent);
                        }
                    });

            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            Intent intent = new Intent(getApplicationContext(), BluetoothChat.class);
            startActivity(intent);
        }
    }

    public void highscoresScreen(View view) {
        Intent intent = new Intent(this, Highscores.class);
        startActivity(intent);
    }

    public void helpScreen(View view) {
        Intent intent = new Intent(this, Help.class);
        startActivity(intent);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Toast.makeText(this, "Connected to Google...", Toast.LENGTH_SHORT).show();
        signinButton.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this, "onConnectionSuspended called", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "onConnectionFailed called: " + connectionResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
        try {
            startIntentSenderForResult(connectionResult.getResolution().getIntentSender(), 99, null, 0, 0, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 98) {
            signinButton.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "You're now signed in", Toast.LENGTH_SHORT).show();
        } else if (requestCode == 123) {
            Toast.makeText(this, "NO PLAYERS!", Toast.LENGTH_SHORT).show();
        }
    }
}
