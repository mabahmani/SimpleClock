package com.mab.sinpleclock;

import android.app.Fragment;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CountDownFragment extends Fragment {
    private EditText minutes;
    private EditText second;
    private Button  start;
    private Button pause;
    private Button stop;

    private long countDownTimeInMilliSecond;
    private long countDownTimerRemaining;
    private boolean isResume = false;
    private CountDownTimer countDownTimer;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_count_down, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findViews(view);
        btnSetOnClick();
    }

    private void btnSetOnClick(){
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (!isResume) {
                        countDownTimeInMilliSecond += Long.parseLong(minutes.getText().toString()) * 60 * 1000;
                        countDownTimeInMilliSecond += Long.parseLong(second.getText().toString()) * 1000;
                    }
                    onTimerStart();
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Please Enter A Valid Time!", Toast.LENGTH_LONG).show();
                }
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isResume = true;
                pause.setEnabled(false);
                start.setText("Resume");
                start.setEnabled(true);
                onTimerPause();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isResume = false;
                pause.setEnabled(false);
                stop.setEnabled(false);
                start.setEnabled(true);
                start.setText("Start");

                countDownTimer.cancel();
                countDownTimeInMilliSecond = 0;
                minutes.setEnabled(true);
                second.setEnabled(true);
                minutes.setText("MM");
                second.setText("SS");
            }
        });
    }

    private void onTimerStart(){
        countDownTimer = new CountDownTimer(countDownTimeInMilliSecond,1000) {
            @Override
            public void onTick(long l) {
                pause.setEnabled(true);
                stop.setEnabled(true);
                start.setEnabled(false);
                minutes.setEnabled(false);
                second.setEnabled(false);
                countDownTimerRemaining = l;
                minutes.setText(String.valueOf((l/1000)/60));
                second.setText(String.valueOf((l/1000)%60));
            }

            @Override
            public void onFinish() {
                second.setText("0");
                Toast.makeText(getActivity(),"Time's Up!",Toast.LENGTH_LONG).show();
            }
        };

        countDownTimer.start();
    }

    private void onTimerPause(){
        countDownTimer.cancel();
        countDownTimeInMilliSecond = countDownTimerRemaining;
    }

    private void findViews(View view){
        minutes = view.findViewById(R.id.count_down_timer_minutes);
        second = view.findViewById(R.id.count_down_timer_seconds);
        start = view.findViewById(R.id.count_down_start_btn);
        pause = view.findViewById(R.id.count_down_pause_btn);
        stop = view.findViewById(R.id.count_down_stop_btn);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(countDownTimer != null)
            countDownTimer.cancel();
    }
}