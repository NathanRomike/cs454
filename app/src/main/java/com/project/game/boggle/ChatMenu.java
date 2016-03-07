/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.project.game.boggle;

        import android.app.Activity;
        import android.app.Application;
        import android.bluetooth.BluetoothAdapter;
        import android.bluetooth.BluetoothDevice;
        import android.content.Intent;
        import android.os.Bundle;
        import android.os.Handler;
        import android.os.Message;
        import android.util.Log;
        import android.view.KeyEvent;
        import android.view.LayoutInflater;
        import android.view.Menu;
        import android.view.MenuInflater;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.Window;
        import android.view.View.OnClickListener;
        import android.view.inputmethod.EditorInfo;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.LinearLayout;
        import android.widget.ListView;
        import android.widget.TextView;
        import android.widget.Toast;
        import android.support.v4.app.Fragment;
        import android.support.v4.app.FragmentActivity;

        import java.lang.reflect.Array;
        import java.util.Arrays;
        import java.util.Collections;
        import java.util.List;
        import java.util.ArrayList;
        import java.util.Set;
        import java.util.HashSet;
        import android.view.WindowManager;

/**
 * This is the bluetooth_main Activity that displays the current chat session.
 */
public class ChatMenu extends FragmentActivity {
    private static Dictionary dictionary;
    // Debugging
    private static final String TAG = "BluetoothChat";
    private static final boolean D = true;

    // Message types sent from the BluetoothChatService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    //Message Codes embedded as the first char in every message
    public static final int GAME_MODE = 0;
    public static final int BOGGLE_BOARD = 1;
    public static final int WORD_LIST = 2;
    public static final int PLAYER_TWO_WORD = 3;
    public static final int START_GAME = 4;
    public static final int NEW_GAME = 5;
    public static final int END_GAME = 6;
    public static final int CHAT = 7;

    // Key names received from the BluetoothChatService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";

    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;

    //Boolean to determine if game mode is Basic or cutThroat
    private boolean isCutThroat = false;
    //Boolean to determine if device is Master or Slave
    private boolean isMaster = false;
    //Boolean to determine if the game is running
    private boolean gameRunning = false;

    // Layout Views
    private TextView mTitle;
    private EditText mOutEditText;

    //board and its solution
    private static List<Character> board;
    private ArrayList<String> boardSolution;

    // TODO - check if redundant - this may already be handled in the TwoPlayer activity!
    //managing words and points from the user
    public static ArrayList<String> player1WordList;
    public static int player1Pts = 0;
    private String player1Word;
    boolean player1Done;

    // TODO - check if redundant - this may already be handled in the TwoPlayer activity!
    //receiving word and points from other player
    public static ArrayList<String> player2WordList;
    public static int player2Pts;
    private String player2Word;
    boolean player2Ready;
    boolean player2Done;

    // Name of the connected device
    private String mConnectedDeviceName = null;
    // Array adapter for the conversation thread
    private ArrayAdapter<String> mConversationArrayAdapter;
    // String buffer for outgoing messages
    private StringBuffer mOutStringBuffer;
    // Local Bluetooth adapter
    private BluetoothAdapter mBluetoothAdapter = null;
    private BluetoothContainer mBluetoothContainer = null;
    // Member object for the chat services
    private BluetoothChatService mChatService = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(D) Log.e(TAG, "+++ ON CREATE +++");

        // Set up the window layout
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.bluetooth_main);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title);

        // Set up the custom title
        mTitle = (TextView) findViewById(R.id.title_left_text);
        mTitle.setText(R.string.app_name);
        mTitle = (TextView) findViewById(R.id.title_right_text);

        // Get local Bluetooth adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        mBluetoothContainer = (BluetoothContainer) getApplicationContext();

        // If the adapter is null, then Bluetooth is not supported
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        // TODO - check if redundant - this may already be handled in the TwoPlayer activity!
        // TODO - also this may be handled differently per mode - basic/cutthroat.
        //Set up Two Player Game
        player1WordList = new ArrayList<String>();
        player2WordList = new ArrayList<String>();
        player1Pts = 0;
        player2Pts = 0;
        player2Ready = false;
        player1Done = false;
        player2Done = false;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(D) Log.e(TAG, "++ ON START ++");

        // If BT is not on, request that it be enabled.
        // setupChat() will then be called during onActivityResult
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            // Otherwise, setup the chat session
        } else {
            if (mChatService == null) setupChat();
        }
    }

    @Override
    public synchronized void onResume() {
        super.onResume();
        if(D) Log.e(TAG, "+ ON RESUME +");

        // Performing this check in onResume() covers the case in which BT was
        // not enabled during onStart(), so we were paused to enable it...
        // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
        if (mChatService != null) {
            // Only if the state is STATE_NONE, do we know that we haven't started already
            if (mChatService.getState() == BluetoothChatService.STATE_NONE) {
                // Start the Bluetooth chat services
                mChatService.start();
            }
        }
    }

    private void setupChat() {
        Log.d(TAG, "setupChat()");

        mChatService = new BluetoothChatService(this, mHandler);
        mBluetoothContainer.setmBluetoothChatService(mChatService);

        // Initialize the array adapter for the conversation thread
        mConversationArrayAdapter = new ArrayAdapter<String>(this, R.layout.message);
        ListView mConversationView = (ListView) findViewById(R.id.in);
        mConversationView.setAdapter(mConversationArrayAdapter);

        // Initialize the compose field with a listener for the return key
        mOutEditText = (EditText) findViewById(R.id.edit_text_out);
        mOutEditText.setOnEditorActionListener(mWriteListener);

        // Initialize the buttons with a listener that for click events
        Button mSendButton = (Button) findViewById(R.id.button_send);
        Button basicSButton = (Button) findViewById(R.id.button_basicS);
        Button cutthroatButton = (Button) findViewById(R.id.button_cutthroat);

        basicSButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isMaster()) {
                    startGame();
                } else {
                    Toast.makeText(getApplicationContext(), "Waiting on Player 1", Toast.LENGTH_LONG).show();
                }
            }
        });

        // TODO - cutthroat button
        cutthroatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChatMenu.this, TwoPlayer.class));
                board = BoardFragment.getBoard();
            }
        });

        mSendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Send a message using content of the edit text widget
                TextView view = (TextView) findViewById(R.id.edit_text_out);
                String message = view.getText().toString();
                //sendMessage(message);
                sendMessageNEW(CHAT, message);
            }
        });

        // Initialize the buffer for outgoing messages
        mOutStringBuffer = new StringBuffer("");
    }

    @Override
    public synchronized void onPause() {
        super.onPause();
        if(D) Log.e(TAG, "- ON PAUSE -");
    }

    @Override
    public void onStop() {
        super.onStop();
        if(D) Log.e(TAG, "-- ON STOP --");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Stop the Bluetooth chat services
        if (mChatService != null) mChatService.stop();
        if(D) Log.e(TAG, "--- ON DESTROY ---");
    }

    private void ensureDiscoverable() {
        if(D) Log.d(TAG, "ensure discoverable");
        if (mBluetoothAdapter.getScanMode() !=
                BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
        }
    }

    public Boolean isMaster() {
        return isMaster;
    }

    // TODO - not in use?
    /**
     * Sends a message.
     * @param message  A string of text to send.
     */
    private void sendMessage(String message) {
        // Check that we're actually connected before trying anything
        if (mChatService.getState() != BluetoothChatService.STATE_CONNECTED) {
            Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show();
            return;
        }

        // Check that there's actually something to send
        if (message.length() > 0) {
            // Get the message bytes and tell the BluetoothChatService to write
            byte[] send = message.getBytes();
            mChatService.write(send);

            // Reset out string buffer to zero and clear the edit text field
            mOutStringBuffer.setLength(0);
            mOutEditText.setText(mOutStringBuffer);
        }
    }

    /*
     * Sends a message as a string of text to send to player 2
     */
    public void sendMessageNEW(int messageCode, String message) {
        // Check that we're actually connected before trying anything
        if (mChatService.getState() != BluetoothChatService.STATE_CONNECTED) {
            Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show();
            return;
        }

        // Get the message bytes and tell the BluetoothChatService to write
        String fullMessage = messageCode + message;
        byte[] send = fullMessage.getBytes();
        mChatService.write(send);

        mOutStringBuffer.setLength(0);
        mOutEditText.setText(Container.getInstance().getBoard().toString());
    }

    // The action listener for the EditText widget, to listen for the return key
    private TextView.OnEditorActionListener mWriteListener =
            new TextView.OnEditorActionListener() {
                public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
                    // If the action is a key-up event on the return key, send the message
                    if (actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_UP) {
                        String message = view.getText().toString();
                        //sendMessage(message);
                        sendMessageNEW(CHAT, message);
                    }

                    if(D) Log.i(TAG, "END onEditorAction");

                    return true;
                }
            };

    // The Handler that gets information back from the BluetoothChatService
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_STATE_CHANGE:
                    if(D) Log.i(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);

                    switch (msg.arg1) {
                        case BluetoothChatService.STATE_CONNECTED:
                            mTitle.setText(R.string.title_connected_to);
                            mTitle.append(mConnectedDeviceName);
                            mConversationArrayAdapter.clear();
                            break;
                        case BluetoothChatService.STATE_CONNECTING:
                            mTitle.setText(R.string.title_connecting);
                            isMaster=true;
                            break;
                        case BluetoothChatService.STATE_LISTEN:
                        case BluetoothChatService.STATE_NONE:
                            mTitle.setText(R.string.title_not_connected);
                            break;
                    }
                    break;
                case MESSAGE_WRITE:
                    byte[] writeBuf = (byte[]) msg.obj;
                    // construct a string from the buffer
                    String writeMessage = new String(writeBuf);
                    mConversationArrayAdapter.add("Me:  " + writeMessage);
                    break;
                case MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    String readMessage = new String(readBuf, 0, msg.arg1);

                    //get the messageCode integer at the beginning of every message
                    String strNum = readMessage.substring(0, 1);
                    int messageCode = Integer.parseInt(strNum);
                    //get the rest of the message
                    String message = readMessage.substring(1);

                    //test the message code to see what type of message was sent
                    if(messageCode == GAME_MODE){
                        //if the message is a boggle board parse all the letters and add them to your board
                    }else if(messageCode == BOGGLE_BOARD){

                        String board_str = message.replaceAll("[^a-zA-Z]", "");

                        board = new ArrayList<Character>();
                        //Set<Character> unique = new HashSet<Character>();
                        for(char c : board_str.toCharArray()) {
                            board.add(c);
                            //unique.add(c);
                        }

                        Container.getInstance().setBoard(board);

                        startActivity(new Intent(ChatMenu.this, TwoPlayer.class));

                        //if the message is a word list parse all the words and add them to your searched word list
                    }else if(messageCode == WORD_LIST){
                        System.out.println("##### in WORD_LIST section #####");

                        //Container.getInstance().getSolution().clear();

//                        boardSolution.clear();
//
//                        Collections.addAll(boardSolution, (message.split(",")));
//                        Container.getInstance().setSolution(boardSolution);
//                        System.out.println(Container.getInstance().getSolution());
                        startGame();
                        //if the massage is a player 2 word add it to your player 2 word list
                    }else if(messageCode == PLAYER_TWO_WORD){
//                        player2Word = message;
//                        player2WordList.add(player2Word);
//                        //if the game is cut add the word to the player 2 scroll list display
//                        if(isCutThroat){
//                            pts = getScore(player2Word);
//                            player2Pts+=pts;
//                            addWord(player2Word, pts, R.id.TableLayout02,true);
//                        }
                        //a message sent from the slave to the master to start the game
                    }else if(messageCode == START_GAME){
//                        startGame();
                        //a message sent from player two to let the user know they are ready for a new game
                    }else if(messageCode == NEW_GAME){
//                        player2Ready = true;
//                        Toast.makeText(getApplicationContext(),"Player2 Ready",Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getApplicationContext(),"Press NewGame to play",Toast.LENGTH_SHORT).show();
                        //a message sent from player two to let the user know they finished playing
                    }else if(messageCode == END_GAME){
                        if (isMaster) {
                            if(D) Log.i(TAG, "Master Received Player 2 Score: " + message);
                        } else {
                            if(D) Log.i(TAG, "????? Received Player 2 Score: " + message);
                        }


//                        player2Done = true;
//                        Toast.makeText(getApplicationContext(),"Player2 done",Toast.LENGTH_SHORT).show();
//                        if(player1Done){
//                            endGame();
                        //players chat
                    }else if(messageCode == CHAT){
                        mConversationArrayAdapter.add(mConnectedDeviceName+":  " + readMessage);
                        break;
                    }
                case MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
                    Toast.makeText(getApplicationContext(), "Connected to "
                            + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                    break;
                case MESSAGE_TOAST:
                    Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST),
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(D) Log.d(TAG, "onActivityResult " + resultCode);
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    // Get the device MAC address
                    String address = data.getExtras()
                            .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                    // Get the BLuetoothDevice object
                    BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
                    // Attempt to connect to the device
                    mChatService.connect(device);
                }
                break;
            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // Bluetooth is now enabled, so set up a chat session
                    setupChat();
                } else {
                    // User did not enable Bluetooth or an error occured
                    Log.d(TAG, "BT not enabled");
                    Toast.makeText(this, R.string.bt_not_enabled_leaving, Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.scan:
                // Launch the DeviceListActivity to see devices and do scan
                Intent serverIntent = new Intent(this, DeviceListActivity.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
                return true;
            case R.id.discoverable:
                // Ensure this device is discoverable by others
                ensureDiscoverable();
                return true;
        }
        return false;
    }

    /*
     * Start the Boggle game
     */
    private void startGame(){

        gameRunning = true;

        //Set the board and send Boggle Board message if it is Master
        if(isMaster){
            // set its own board first
            startActivity(new Intent(ChatMenu.this, TwoPlayer.class));

            board = Container.getInstance().getBoard();

            //System.out.println("###### BOARD " + board);

            //boardSolution = Container.getInstance().getSolution();

            //String message = "";
            //send word list to player 2
            //message = board.toString();
//          for(int i = 0; i < searchedWordList.size(); i++){
//              message = message + searchedWordList.get(i) + " ";
//          }
            // sendMessageNEW(CHAT, board.toString());
            sendMessageNEW(BOGGLE_BOARD, board.toString());
            //sendMessageNEW(WORD_LIST, boardSolution.toString());
        }

        //resets the submit button
//      editText = (TextView)findViewById(R.id.SubmitScoreBtn);
//      editText.setText("Submit");
        player1Done = false;
        player2Done = false;
//
//      //display the boggle board
//      resetMatrix();

//      //set the grid path to blank
//      resetPath();

//      //start timer
//      setTimer();

        //keep screen on
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
}