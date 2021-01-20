package com.suraj.memorygame.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.suraj.memorygame.R;
import com.suraj.memorygame.activities.MainActivity;
import com.suraj.memorygame.dialogs.MainMenuDialog;
import com.suraj.memorygame.utils.SoundPlayer;

public class MainFragment extends Fragment {

    Button play, leader, setting;
    TextView title;
    Animation up, down, blink;
    Context context;

    public MainFragment(){

    }

    public static MainFragment newInstance(){
        return new MainFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        play = view.findViewById(R.id.button_play);
        leader = view.findViewById(R.id.button_leaderboard);
        setting = view.findViewById(R.id.button_settings);
        title = view.findViewById(R.id.text_title);

        up = AnimationUtils.loadAnimation(requireActivity(), R.anim.scale_up);
        down = AnimationUtils.loadAnimation(requireActivity(), R.anim.scale_down);
        blink = AnimationUtils.loadAnimation(requireActivity(), R.anim.blink);

        play.setOnTouchListener((v, event) -> {


            if (event.getAction()== MotionEvent.ACTION_DOWN){
                new SoundPlayer(requireContext()).playSound("button.mp3");
                play.startAnimation(up);
            } else if (event.getAction()==MotionEvent.ACTION_UP){
                play.startAnimation(down);
                MainMenuDialog dialog = new MainMenuDialog(requireActivity());
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations = R.style.MenuDialogAnim;
                dialog.setCancelable(false);
                dialog.show();
            }
            return true;
        });

        leader.setOnTouchListener((v, event) -> {

            if (event.getAction()==MotionEvent.ACTION_DOWN){
                new SoundPlayer(requireContext()).playSound("button.mp3");
                leader.startAnimation(up);
            } else if (event.getAction()==MotionEvent.ACTION_UP){
                leader.startAnimation(down);
                startFragment(new LeaderBoardFragment());
            }
            return true;
        });

        setting.setOnTouchListener((v, event) -> {

            if (event.getAction()==MotionEvent.ACTION_DOWN){
                new SoundPlayer(requireContext()).playSound("button.mp3");
                setting.startAnimation(up);
            } else if (event.getAction()==MotionEvent.ACTION_UP){
                setting.startAnimation(down);
                startFragment(new SettingFragment());
            }
            return true;
        });

    }

    private void startFragment(Fragment fragment) {
        FragmentTransaction transaction = requireFragmentManager().beginTransaction().setCustomAnimations(
                R.anim.slide_in,  // enter
                R.anim.fade_out  // exit
        );
        transaction.replace(R.id.main_frame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}