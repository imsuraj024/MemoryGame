package com.suraj.memorygame.dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.suraj.memorygame.R;
import com.suraj.memorygame.fragments.MainFragment;
import com.suraj.memorygame.utils.SoundPlayer;

public class LoseDialog extends Dialog {

    Button main;
    Context context;
    Animation up, down;

    public LoseDialog(@NonNull Context context) {
        super(context);
        this.context=context;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_lose);
        setCanceledOnTouchOutside(false);

        new SoundPlayer(context).playSound("lose.mp3");

        main = findViewById(R.id.button_home_lose);
        up = AnimationUtils.loadAnimation(context, R.anim.scale_up);
        down = AnimationUtils.loadAnimation(context, R.anim.scale_down);

        main.setOnTouchListener((v, event) -> {

            if (event.getAction()== MotionEvent.ACTION_DOWN){
                new SoundPlayer(context).playSound("button.mp3");
                main.startAnimation(up);
            } else if (event.getAction()==MotionEvent.ACTION_UP){
                main.startAnimation(down);
                startFragment(new MainFragment());
                dismiss();
            }
            return true;
        });


    }

    private void startFragment(Fragment fragment) {
        FragmentTransaction transaction = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction().setCustomAnimations(
                R.anim.fade_in,  // enter
                R.anim.slide_up  // exit
        );
        transaction.replace(R.id.main_frame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
