package com.suraj.memorygame.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.suraj.memorygame.R;
import com.suraj.memorygame.dialogs.MainMenuDialog;
import com.suraj.memorygame.fragments.EasyFragment;
import com.suraj.memorygame.fragments.HardFragment;
import com.suraj.memorygame.fragments.LeaderBoardFragment;
import com.suraj.memorygame.fragments.MainFragment;
import com.suraj.memorygame.fragments.SettingFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(
                        R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.fade_in,   // popEnter
                        R.anim.slide_out  // popExit
                );
        transaction.replace(R.id.main_frame, new MainFragment());
        transaction.commit();

    }

    int counter = 0;

    @Override
    public void onBackPressed() {
        Fragment currentFragment =  getSupportFragmentManager().findFragmentById(R.id.main_frame);
        counter++;
        if (currentFragment instanceof  MainFragment){
            AlertDialog.Builder last = new AlertDialog.Builder(this);
            last.setTitle("Are you sure you want to exit?");
            last.setPositiveButton("Ok", (dialog, which) -> finish());
            last.setNegativeButton("Dismiss", (dialog, which) -> dialog.dismiss());
            last.show();
        } else if (currentFragment instanceof EasyFragment){
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(
                            R.anim.fade_in,  // enter
                            R.anim.slide_out // exit
                    ).remove(currentFragment).commit();
            super.onBackPressed();
        } else if (currentFragment instanceof HardFragment){
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(
                            R.anim.fade_in,  // enter
                            R.anim.slide_out // exit
                    ).remove(currentFragment).commit();
            super.onBackPressed();
        } else if (currentFragment instanceof LeaderBoardFragment){
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(
                            R.anim.fade_in,  // enter
                            R.anim.slide_out // exit
                    ).remove(currentFragment).commit();
            super.onBackPressed();
        }  else if (currentFragment instanceof SettingFragment){
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(
                            R.anim.fade_in,  // enter
                            R.anim.slide_out // exit
            ).remove(currentFragment).commit();
            super.onBackPressed();
        } else {
            super.onBackPressed();
        }


    }
}