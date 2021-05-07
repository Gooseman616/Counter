package com.test.counter;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class CounterActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "EXTRA_ID";
    private TextView mCounterValueTv;
    private long mCounterId;
    private Counter mCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);
        mCounterId = getIntent().getLongExtra(EXTRA_ID, -1);
        mCounter = Repository.getInstance().getCounter(mCounterId);
        mCounterValueTv = findViewById(R.id.counter_value);
        updateValue(mCounter.value);
        findViewById(R.id.plus_button).setOnClickListener(v -> updateValue(mCounter.value + 1));
        findViewById(R.id.minus_button).setOnClickListener(v -> updateValue(mCounter.value - 1));
        findViewById(R.id.reset_button).setOnClickListener(v -> {
            int oldCounterValue = mCounter.value;
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
        Repository.getInstance().setValue(mCounter, value);
        mCounter = Repository.getInstance().getCounter(mCounterId);
        mCounterValueTv.setText(String.valueOf(mCounter.value));
    }

    public void showToastMessage(CharSequence message) {
        Toast.makeText(this.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }


}