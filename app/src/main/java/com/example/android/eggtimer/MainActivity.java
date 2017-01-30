package com.example.android.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar timerSeekBar;
    TextView myTimer;
    Button controllerButton;
    Boolean counterIsActive = false;
    CountDownTimer countDownTimer;


    public void resetTimer() {
        myTimer.setText("0:30");
        timerSeekBar.setProgress(30);
        countDownTimer.cancel();
        controllerButton.setText("Go");
        timerSeekBar.setEnabled(true);
        counterIsActive = false;
    }

    public void updateTimer(int secondsLeft){
        int minutes = (int) secondsLeft / 60;
        int seconds = secondsLeft - minutes * 60;

        // Getting double digits for our seconds.
        String secondString = Integer.toString(seconds);

            if (seconds <= 9){
            secondString = "0" + secondString;
        }

        // Update our timer
        myTimer.setText(Integer.toString(minutes) + ":" + secondString);

    }

    public void controlTimer(View view){

        if(counterIsActive == false) {

            counterIsActive = true;

            // Disable seekbar

            timerSeekBar.setEnabled(false);

            // Change controllesButton Text
            controllerButton.setText("Stop");



            // Start Timer. Odd fix to avoid lag in Timer.
            countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {

                    updateTimer((int) millisUntilFinished / 1000);
                }

                @Override

                public void onFinish() {

                    resetTimer();
                    MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn); //getApplicationContext() substitutes "this" in this case.
                    mplayer.start();
                }
            }.start();
        } else{
           resetTimer();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controllerButton = (Button)findViewById(R.id.controllerButton);

        // Find seekBar

        timerSeekBar = (SeekBar) findViewById(R.id.timerSeekBar);

        // Find Timer

        myTimer = (TextView) findViewById(R.id.timerTextView);

        // Set maximun and current time
        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(30);

        timerSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                updateTimer(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }
}
