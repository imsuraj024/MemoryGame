package com.suraj.memorygame.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.suraj.memorygame.R;
import com.suraj.memorygame.fragments.MainFragment;
import com.suraj.memorygame.utils.SoundPlayer;

public class AboutActivity extends AppCompatActivity {

    Button back;
    Animation up, down;

    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(R.anim.slide_in_reverse,R.anim.fade_out);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        up = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        down = AnimationUtils.loadAnimation(this, R.anim.scale_down);

        back = findViewById(R.id.button_about_back);

        back.setOnTouchListener((v, event) ->{
            if (event.getAction()== MotionEvent.ACTION_DOWN){
                new SoundPlayer(this).playSound("button.mp3");
                back.startAnimation(up);
            } else if (event.getAction()==MotionEvent.ACTION_UP) {
                back.startAnimation(down);
                finish();
            }
            return true;
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_in, R.anim.slide_out_reverse);
    }
}