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
import com.suraj.memorygame.fragments.EasyFragment;
import com.suraj.memorygame.fragments.HardFragment;
import com.suraj.memorygame.fragments.SettingFragment;
import com.suraj.memorygame.utils.SoundPlayer;

public class MainMenuDialog extends Dialog {

    Button easy, hard, cancel;
    Context context;
    Animation up, down;

    public MainMenuDialog(@NonNull Context context) {
        super(context);
        this.context=context;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_main_menu);
        setCanceledOnTouchOutside(false);

        easy = findViewById(R.id.button_easy);
        hard = findViewById(R.id.button_hard);
        cancel = findViewById(R.id.button_cancel);

        up = AnimationUtils.loadAnimation(context, R.anim.scale_up);
        down = AnimationUtils.loadAnimation(context, R.anim.scale_down);


        cancel.setOnTouchListener((v, event) -> {
            if (event.getAction()== MotionEvent.ACTION_DOWN){
                new SoundPlayer(context).playSound("button.mp3");
                cancel.startAnimation(up);
            } else if (event.getAction()==MotionEvent.ACTION_UP){
                cancel.startAnimation(down);
                dismiss();
            }
            return true;
        });

        hard.setOnTouchListener((v, event) -> {
            if (event.getAction()== MotionEvent.ACTION_DOWN){
                new SoundPlayer(context).playSound("button.mp3");
                hard.startAnimation(up);
            } else if (event.getAction()==MotionEvent.ACTION_UP){
                hard.startAnimation(down);
                startFragment(new HardFragment());
                dismiss();
            }
            return true;
        });

        easy.setOnTouchListener((v, event) -> {
            if (event.getAction()== MotionEvent.ACTION_DOWN){
                new SoundPlayer(context).playSound("button.mp3");
                easy.startAnimation(up);
            } else if (event.getAction()==MotionEvent.ACTION_UP){
                easy.startAnimation(down);
                startFragment(new EasyFragment());
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
