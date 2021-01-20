package com.suraj.memorygame.fragments;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.suraj.memorygame.R;
import com.suraj.memorygame.adapters.RecyclerAdapter;
import com.suraj.memorygame.dialogs.LoseDialog;
import com.suraj.memorygame.dialogs.WinnerDialog;
import com.suraj.memorygame.utils.Global;
import com.suraj.memorygame.utils.SoundPlayer;
import com.wajahatkarim3.easyflipview.EasyFlipView;

import java.util.ArrayList;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;

public class EasyFragment extends Fragment {

    RecyclerView recyclerView;
    TextView timer, moves;
    EasyFlipView flipView;
    RecyclerAdapter adapter;

    public ArrayList<Integer> cards;

    public long RemainingTime;
    public boolean isPaused, isCancelled;
    public int count, pos, move = 0;

    public int[] CARDS = {
            R.drawable.card1,
            R.drawable.card2,
            R.drawable.card3,
            R.drawable.card4,
            R.drawable.card5,
            R.drawable.card6,
            R.drawable.card1,
            R.drawable.card2,
            R.drawable.card3,
            R.drawable.card4,
            R.drawable.card5,
            R.drawable.card6
    };


    public EasyFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_easy, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("prefs", MODE_PRIVATE);

        recyclerView = view.findViewById(R.id.recyler_easy);
        timer = view.findViewById(R.id.text_timer);
        moves = view.findViewById(R.id.text_moves);

        cards = new ArrayList<>();

        //set a GridLayoutManager with 3 number of columns
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireActivity(),3, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);

        shuffleCards(CARDS, Global.EASY_NO_OF_CARDS);
        shuffleCards(CARDS,Global.EASY_NO_OF_CARDS);
        for (int card : CARDS){
            cards.add(card);
        }

        adapter = new RecyclerAdapter(cards);

        recyclerView.setAdapter(adapter);

        isPaused = false;
        isCancelled = false;

        new CountDownTimer(Global.EASY_TIME,Global.TIMER_INTERVAL){
            @Override
            public void onTick(long millisUntilFinished) {
                if (isPaused || isCancelled){
                    cancel();
                }
                else {
                    timer.setText((millisUntilFinished / Global.TIMER_INTERVAL+" sec left"));
                    RemainingTime = millisUntilFinished;
                    if (count == Global.EASY_NO_OF_CARDS) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("time_easy", String.valueOf(((Global.EASY_TIME - millisUntilFinished)/ Global.TIMER_INTERVAL)));
                        editor.putString("move_easy", String.valueOf(move));
                        editor.apply();
                        WinnerDialog dialog = new WinnerDialog(requireActivity());
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.getWindow().getAttributes().windowAnimations = R.style.MenuDialogAnim;
                        dialog.setCancelable(false);
                        dialog.show();
                        cancel();
                        this.onFinish();
                    }
                }
            }

            @Override
            public void onFinish() {
                if (count < Global.EASY_NO_OF_CARDS) {
                    LoseDialog dialog = new LoseDialog(requireActivity());
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.getWindow().getAttributes().windowAnimations = R.style.MenuDialogAnim;
                    dialog.setCancelable(false);
                    dialog.show();
                    cancel();
                }

            }
        }.start();


        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP){
                    isPaused = true;
                    AlertDialog.Builder pause = new AlertDialog.Builder(requireContext());
                    pause.setTitle(getString(R.string.game_paused));
                    pause.setMessage(getString(R.string.quit));
                    pause.setCancelable(false);
                    pause.setPositiveButton(getString(R.string.resume), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            isPaused = false;

                            new CountDownTimer(RemainingTime,Global.TIMER_INTERVAL){
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    if (isPaused || isCancelled){
                                        cancel();
                                    }
                                    else {
                                        timer.setText((millisUntilFinished / Global.TIMER_INTERVAL+" sec left"));
                                        RemainingTime = millisUntilFinished;
                                        if (count == Global.EASY_NO_OF_CARDS) {
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString("time_easy", String.valueOf(((Global.EASY_TIME - millisUntilFinished)/ Global.TIMER_INTERVAL)));
                                            editor.putString("move_easy", String.valueOf(move));
                                            editor.apply();
                                            WinnerDialog dialog = new WinnerDialog(requireActivity());
                                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                            dialog.getWindow().getAttributes().windowAnimations = R.style.MenuDialogAnim;
                                            dialog.setCancelable(false);
                                            dialog.show();
                                            cancel();
                                            this.onFinish();
                                        }
                                    }
                                }

                                @Override
                                public void onFinish() {
                                    if (count < Global.EASY_NO_OF_CARDS) {
                                        LoseDialog dialog = new LoseDialog(requireActivity());
                                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                        dialog.getWindow().getAttributes().windowAnimations = R.style.MenuDialogAnim;
                                        dialog.setCancelable(false);
                                        dialog.show();
                                        cancel();
                                    }

                                }
                            }.start();

                        }
                    });
                    pause.setNegativeButton(getString(R.string.exit), (dialog, which) -> {
                        isCancelled = true;
                        requireFragmentManager().popBackStack();
                    });
                    pause.show();
                    return true;
                }
                return false;
            }
        });

        adapter.setClickListener((v, position) -> {

            new SoundPlayer(requireContext()).playSound("flip.mp3");

            if (flipView == null){
                flipView = (EasyFlipView) v;
                pos = position;
            } else {
                if (pos == position){
                    flipView = null;
                } else {
                    if (cards.get(pos).equals(cards.get(position))){
                        ((EasyFlipView) v).setOnFlipListener((easyFlipView, newCurrentSide) -> {
                            for (int i = 0; i < recyclerView.getChildCount(); i++) {
                                EasyFlipView child = (EasyFlipView) recyclerView.getChildAt(i);
                                child.setEnabled(false);
                            }

                            new Handler().postDelayed(() -> {
                                move++;
                                moves.setText(Integer.toString(move));
                                new SoundPlayer(requireContext()).playSound("checkpoint.mp3");
                                flipView.setVisibility(View.GONE);
                                v.setVisibility(View.GONE);
                                v.setEnabled(false);
                                flipView.setEnabled(false);
                                flipView=null;
                                count+=2;

                                for (int i = 0; i < recyclerView.getChildCount(); i++) {
                                    EasyFlipView child = (EasyFlipView) recyclerView.getChildAt(i);
                                    child.setEnabled(true);
                                }

                            },200);

                        });
                    } else {
                        ((EasyFlipView) v).setOnFlipListener((easyFlipView, newCurrentSide) -> {

                            for (int i = 0; i < recyclerView.getChildCount(); i++) {
                                EasyFlipView child = (EasyFlipView) recyclerView.getChildAt(i);
                                child.setEnabled(false);
                            }

                            new Handler().postDelayed(() -> {

                                move++;
                                moves.setText(Integer.toString(move));
                                flipView.flipTheView();
                                ((EasyFlipView) v).flipTheView();
                                flipView = null;
                                ((EasyFlipView) v).setOnFlipListener(null);

                                for (int i = 0; i < recyclerView.getChildCount(); i++) {
                                    EasyFlipView child = (EasyFlipView) recyclerView.getChildAt(i);
                                    child.setEnabled(true);
                                }

                            },200);

                        });
                    }
                }
            }


        });


    }

    public void shuffleCards(int[] cards, int n){
        Random random = new Random();

        for (int i=0;i<n;i++){
            int r = random.nextInt(n-i);

            int temp = cards[r];
            cards[r] = cards[i];
            cards[i] = temp;
        }
    }


}