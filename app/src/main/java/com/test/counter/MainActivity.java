package com.test.counter;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getName();
    private int mCounterValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void plusClicked(View view) {
        Log.d(LOG_TAG, "Plus clicked");
    }

    public void minusClicked(View view) {
        Log.d(LOG_TAG, "minus clicked");
    }

    public void resetClicked(View view) {
        Log.d(LOG_TAG, "reset clicked");
    }

    public void showToastMessage(CharSequence message) {
        Toast.makeText(this.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}