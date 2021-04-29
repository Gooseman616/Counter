package com.test.counter;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getName();
    private int mCounterValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView counterValueTV = findViewById(R.id.counter_value);
        counterValueTV.setText(String.valueOf(mCounterValue));

        findViewById(R.id.plus_button).setOnClickListener(v -> {
            mCounterValue++;
            counterValueTV.setText(String.valueOf(mCounterValue));
        });

        findViewById(R.id.minus_button).setOnClickListener(v -> {
            mCounterValue--;
            counterValueTV.setText(String.valueOf(mCounterValue));
        });

        findViewById(R.id.reset_button).setOnClickListener(v -> {
            mCounterValue = 0;
            counterValueTV.setText(String.valueOf(mCounterValue));
        });

    }
//    public void plusClicked(View view) {
//        Log.d(LOG_TAG, "Plus clicked");
//        mCounterValue++;
//    }
//
//    public void minusClicked(View view) {
//        Log.d(LOG_TAG, "minus clicked");
//        mCounterValue--;
//    }
//
//    public void resetClicked(View view) {
//        Log.d(LOG_TAG, "reset clicked");
//        mCounterValue = 0;
//    }

    public void showToastMessage(CharSequence message) {
        Toast.makeText(this.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }


}