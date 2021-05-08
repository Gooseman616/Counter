package com.test.counter;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class CounterListActivity extends AppCompatActivity implements Repository.RepoListener {

    private CounterList mCounterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter_list);
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        mCounterList = new CounterList(findViewById(R.id.counter_list), new CounterList.Listener() {
            @Override
            public void onPlus(Counter counter) {
                Repository.getInstance(CounterListActivity.this).setValue(counter, counter.value + 1);
            }

            @Override
            public void onMinus(Counter counter) {
                Repository.getInstance(CounterListActivity.this).setValue(counter, counter.value - 1);
            }

            @Override
            public void onOpen(Counter counter) {
                startActivity(new Intent(CounterListActivity.this, CounterActivity.class)
                        .putExtra(CounterActivity.EXTRA_ID, counter.id));
            }
        });
        onDataChanged();
        Repository.getInstance(this).addListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Repository.getInstance(this).removeListener(this);
    }

    @Override
    public void onDataChanged() {
        mCounterList.setCounters(Repository.getInstance(this).getCounters());
    }
}
