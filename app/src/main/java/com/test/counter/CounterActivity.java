package com.test.counter;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class CounterActivity extends AppCompatActivity implements Repository.RepoListener {

    public static final String EXTRA_ID = "EXTRA_ID";
    private TextView mCounterValueTv;
    private TextView mCounterTitleTV;
    private long mCounterId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCounterId = getIntent().getLongExtra(EXTRA_ID, -1);
        getCounter();
        setContentView(R.layout.activity_counter);
        mCounterValueTv = findViewById(R.id.counter_value);
        mCounterTitleTV = findViewById(R.id.counter_title);
        findViewById(R.id.plus_button).setOnClickListener(v -> changeValue(getCounter().value + 1));
        findViewById(R.id.minus_button).setOnClickListener(v -> changeValue(getCounter().value - 1));
        findViewById(R.id.reset_button).setOnClickListener(v -> {
            int oldCounterValue = getCounter().value;
            changeValue(0);
            Snackbar.make(v, "Counter reset", BaseTransientBottomBar.LENGTH_SHORT)
                    .setAction("Undo", ignored -> {
                        changeValue(oldCounterValue);
                        showToastMessage("Undone");
                    })
                    .show();
        });
        updateValue();
        Repository.getInstance().addListener(this::updateValue);
    }

    private Counter getCounter(){
        return Repository.getInstance().getCounter(mCounterId);
    }

    private void changeValue(int value) {
        Repository.getInstance().setValue(getCounter(), value);
    }

    private void updateValue() {
        Counter counter = getCounter();
        mCounterValueTv.setText(String.valueOf(counter.value));
        mCounterTitleTV.setText(counter.name);
    }

    public void showToastMessage(CharSequence message) {
        Toast.makeText(this.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onDataChanged() {
        updateValue();
    }
}