package com.suraj.memorygame.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
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
import com.suraj.memorygame.utils.SoundPlayer;

import static android.content.Context.MODE_PRIVATE;

public class LeaderBoardFragment extends Fragment {

    Button back;
    Animation up, down;
    TextView hard_move, hard_time, easy_move, easy_time;
    String time_hard, time_easy, moves_hard, moves_easy;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_leader_board, container, false);
    }

    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("prefs", MODE_PRIVATE);
        moves_easy = sharedPreferences.getString("move_easy", "0");
        time_easy = sharedPreferences.getString("time_easy","00") ;
        moves_hard = sharedPreferences.getString("move","0");
        time_hard = sharedPreferences.getString("time", "00");

        up = AnimationUtils.loadAnimation(requireActivity(), R.anim.scale_up);
        down = AnimationUtils.loadAnimation(requireActivity(), R.anim.scale_down);

        back = view.findViewById(R.id.button_back);
        easy_move = view.findViewById(R.id.text_easy_move);
        easy_time = view.findViewById(R.id.text_easy_time);
        hard_move = view.findViewById(R.id.text_hard_move);
        hard_time = view.findViewById(R.id.text_hard_time);

        easy_move.setText(moves_easy+" Moves");
        easy_time.setText(time_easy+" Seconds");
        hard_move.setText(moves_hard+" Moves");
        hard_time.setText(time_hard+" Seconds");

        back.setOnTouchListener((v, event) ->{
            if (event.getAction()== MotionEvent.ACTION_DOWN){
                new SoundPlayer(requireContext()).playSound("button.mp3");
                back.startAnimation(up);
            } else if (event.getAction()==MotionEvent.ACTION_UP) {
                back.startAnimation(down);
                startFragment(new MainFragment());
            }
            return true;
        });
    }

    private void startFragment(Fragment fragment) {
        FragmentTransaction transaction = requireFragmentManager().beginTransaction().setCustomAnimations(
                R.anim.fade_in,  // enter
                R.anim.slide_out  // exit
        );
        transaction.replace(R.id.main_frame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}