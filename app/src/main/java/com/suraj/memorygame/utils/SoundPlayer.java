package com.suraj.memorygame.utils;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

public class SoundPlayer {

    Context context;

    public SoundPlayer(Context context) {
        this.context = context;
    }

    MediaPlayer player = null;

    public void playSound(String fileName) {
        player = new MediaPlayer();
        try {
            AssetFileDescriptor afd = context.getAssets().openFd(fileName);
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
            player.prepare();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        player.start();
    }
}
