package com.suraj.memorygame.dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.suraj.memorygame.R;
import com.suraj.memorygame.fragments.HardFragment;
import com.suraj.memorygame.fragments.MainFragment;
import com.suraj.memorygame.utils.SoundPlayer;

import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;

public class WinnerDialog extends Dialog {

    Button main;
    Context context;
    KonfettiView celebrate;
    Animation up, down;

    public WinnerDialog(@NonNull Context context) {
        super(context);
        this.context=context;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_win);
        setCanceledOnTouchOutside(false);

        new SoundPlayer(context).playSound("win.mp3");

        main = findViewById(R.id.button_home_win);
        celebrate = findViewById(R.id.celebration);

        up = AnimationUtils.loadAnimation(context, R.anim.scale_up);
        down = AnimationUtils.loadAnimation(context, R.anim.scale_down);

        celebrate.build()
                .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA)
                .setDirection(0.0, 359.0)
                .setSpeed(4f,7f)
                .setFadeOutEnabled(true)
                .setTimeToLive(2000L)
                .addSizes(new Size(12,2f), new Size(16, 6f))
                .addShapes(Shape.Circle.INSTANCE,Shape.Square.INSTANCE)
                .setPosition(-50f, celebrate.getWidth() + 50f, -50f, -50f)
                .streamFor(300, 5000L);


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
