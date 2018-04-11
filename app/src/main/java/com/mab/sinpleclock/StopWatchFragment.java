package com.mab.sinpleclock;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class StopWatchFragment extends Fragment {
    private long startTime;
    private long updateTime;
    private int seconds;
    private int minutes;
    private int milliSecond;
    private boolean ifStop = false;

    private TextView stopWatchTimer;
    private Button start;
    private Button stop;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stop_watch, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        buttonListeners();
    }

    private void startStopWatch() {
        ifStop = false;
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(!ifStop) {
                    updateTime = SystemClock.uptimeMillis() - startTime;
                    seconds = (int) (updateTime / 1000);
                    minutes = seconds / 60;
                    seconds = seconds % 60;
                    milliSecond = (int) (updateTime % 100);

                    stopWatchTimer.setText(String.format("%02d:%02d.%02d", minutes, seconds, milliSecond));
                    handler.postDelayed(this, 0);
                }


                if(ifStop){
                    handler.removeCallbacks(this);
                }
            }
        });
    }

    private void stopStopWatch(){
        ifStop = true;
        stopWatchTimer.setText(String.format("%02d:%02d.%02d", minutes, seconds, milliSecond));
        updateTime = 0L;
        seconds = 0 ;
        minutes = 0;
        milliSecond = 0;
        startTime = 0;
    }

    private void buttonListeners(){
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start.setEnabled(false);
                stop.setEnabled(true);
                startTime = SystemClock.uptimeMillis();
                startStopWatch();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start.setEnabled(true);
                stop.setEnabled(false);
                stopStopWatch();
            }
        });
    }

    private void findViews(View view){
        stopWatchTimer = view.findViewById(R.id.stopwatch_timer);
        start = view.findViewById(R.id.stopwatch_start_btn);
        stop = view.findViewById(R.id.stopwatch_stop_btn);
    }
}