package com.suraj.memorygame.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;

import com.suraj.memorygame.R;
import com.suraj.memorygame.activities.AboutActivity;
import com.suraj.memorygame.utils.SoundPlayer;

import static android.content.Context.MODE_PRIVATE;

public class SettingFragment extends Fragment {

    ImageButton sound, info;
    Button back;
    boolean hasSound;
    Animation up, down;
    int id;
    String name;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("prefs", MODE_PRIVATE);
        hasSound = sharedPreferences.getBoolean("hasSounds", true);
        name = sharedPreferences.getString("image", "sound");
        id = getResources().getIdentifier(name,"drawable",requireContext().getPackageName());


        Log.e("hasSound", String.valueOf(hasSound));
        Log.e("id", String.valueOf(id));

        up = AnimationUtils.loadAnimation(requireActivity(), R.anim.scale_up);
        down = AnimationUtils.loadAnimation(requireActivity(), R.anim.scale_down);

        sound = view.findViewById(R.id.image_button_sound);
        info = view.findViewById(R.id.image_button_info);
        back = view.findViewById(R.id.button_setting_back);

        sound.setImageResource(id);

        sound.setOnTouchListener( (v, event) -> {
            AudioManager audioManager = (AudioManager)requireActivity().getSystemService(Context.AUDIO_SERVICE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (event.getAction()== MotionEvent.ACTION_DOWN){
                new SoundPlayer(requireContext()).playSound("button.mp3");
                sound.startAnimation(up);
            } else if (event.getAction()==MotionEvent.ACTION_UP){
                sound.startAnimation(down);
                if (hasSound){
                    audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_MUTE, 0);
                    sound.setImageResource(R.drawable.mute);
                    editor.putBoolean("hasSounds",false);
                    editor.putString("image","mute");
                } else {
                    audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_UNMUTE, 0);
                    sound.setImageResource(R.drawable.sound);
                    editor.putBoolean("hasSounds",true);
                    editor.putString("image","sound");
                }
                hasSound = !hasSound;
                editor.apply();
            }

            return true;
        });

        info.setOnTouchListener((v, event) ->{
            if (event.getAction()== MotionEvent.ACTION_DOWN){
                new SoundPlayer(requireContext()).playSound("button.mp3");
                info.startAnimation(up);
            } else if (event.getAction()==MotionEvent.ACTION_UP){
                info.startAnimation(down);
                Intent intent = new Intent(requireContext(),AboutActivity.class);
                startActivity(intent);
            }
            return true;
        });

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


