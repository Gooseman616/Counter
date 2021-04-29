package com.test.counter;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private static final String PREF_KEY = "COUNTER_VALUE";
    private static final String LOG_TAG = MainActivity.class.getName();
    private SharedPreferences mData;
    private TextView mCounterValueTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mData = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        mCounterValueTv = findViewById(R.id.counter_value);
        updateValue(getValue());
        findViewById(R.id.plus_button).setOnClickListener(v -> {
            updateValue(getValue() + 1);
        });

        findViewById(R.id.minus_button).setOnClickListener(v -> {
            updateValue(getValue() - 1);
        });

        findViewById(R.id.reset_button).setOnClickListener(v -> {
            int oldCounterValue = getValue();
            updateValue(0);
            Snackbar.make(v, "Counter reset", BaseTransientBottomBar.LENGTH_SHORT)
                    .setAction("Undo", ignored -> {
                        updateValue(oldCounterValue);
                        showToastMessage("Undone");
                    })
                    .show();
        });

    }

    private void updateValue(int value) {
        mData.edit().putInt(PREF_KEY, value).apply();
        mCounterValueTv.setText(String.valueOf(value));
    }

    private int getValue() {
        return mData.getInt(PREF_KEY, 0);
    }

    public void showToastMessage(CharSequence message) {
        Toast.makeText(this.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }


}