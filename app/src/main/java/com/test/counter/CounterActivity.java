package com.test.counter;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
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
        findViewById(R.id.counter_back_button).setOnClickListener(v -> finish());
        mCounterTitleTV.setOnClickListener(v -> EditDialog.create(mCounterId).show(getSupportFragmentManager(), null));
        findViewById(R.id.counter_edit_button).setOnClickListener(v -> EditDialog.create(mCounterId).show(getSupportFragmentManager(), null));

        findViewById(R.id.counter_remove_button).setOnClickListener(v -> {
            MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(this);
            dialog.setMessage(getString(R.string.delete) + " " + getCounter().name + "?");
            dialog.setNegativeButton(getString(R.string.cancel), (d, which) -> d.dismiss());
            dialog.setPositiveButton(getString(R.string.delete), (d, which) ->
            {
                Toast.makeText(getApplicationContext(), String.format("%s deleted", getCounter().name), Toast.LENGTH_SHORT).show();
                Repository.getInstance(this).removeCounter(mCounterId);
            });
            dialog.create();
            dialog.show();
        });
        findViewById(R.id.plus_button).setOnClickListener(v -> changeValue(getCounter().value + 1));
        findViewById(R.id.minus_button).setOnClickListener(v -> changeValue(getCounter().value - 1));
        findViewById(R.id.reset_button).setOnClickListener(v -> {
            int oldCounterValue = getCounter().value;
            changeValue(0);
            Snackbar.make(v, "Counter " + getString(R.string.reset), BaseTransientBottomBar.LENGTH_SHORT)
                    .setAction("Undo", ignored -> changeValue(oldCounterValue))
                    .show();
        });
        onDataChanged();
        Repository.getInstance(this).addListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Repository.getInstance(this).removeListener(this);
    }

    private Counter getCounter() {
        return Repository.getInstance(this).getCounter(mCounterId);
    }

    private void changeValue(int value) {
        Repository.getInstance(this).setValue(getCounter(), value);
    }

    @Override
    public void onDataChanged() {
        Counter counter = getCounter();
        if (counter != null) {
            mCounterValueTv.setText(String.valueOf(counter.value));
            mCounterTitleTV.setText(counter.name);
        } else {
            finish();
        }
    }
}