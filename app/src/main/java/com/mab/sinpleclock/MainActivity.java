package com.mab.sinpleclock;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button stopWatch;
    private Button  countDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        buttonsSetOnClick();
    }

    private void findViews(){
        stopWatch = findViewById(R.id.stopwatch_btn);
        countDown = findViewById(R.id.count_down_btn);

    }

    private void buttonsSetOnClick() {
        stopWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StopWatchFragment stopWatchFragment = new StopWatchFragment();
                getFragmentManager().beginTransaction()
                        .add(R.id.stopwatch_fragment, stopWatchFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        countDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CountDownFragment countDownFragment = new CountDownFragment();
                getFragmentManager().beginTransaction()
                        .add(R.id.count_down_fragment,countDownFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}
