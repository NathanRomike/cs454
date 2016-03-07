package com.project.game.boggle;

import android.app.Application;


public class BluetoothContainer extends Application {
    private BluetoothChatService mBluetoothChatService;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public BluetoothChatService getmBluetoothChatService() {
        return mBluetoothChatService;
    }

    public void setmBluetoothChatService(BluetoothChatService mBluetoothChatService) {
        this.mBluetoothChatService = mBluetoothChatService;
    }
}