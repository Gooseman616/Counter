package com.test.counter;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class CounterListActivity extends AppCompatActivity {

    private CounterList mCounterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCounterList = new CounterList(findViewById(R.id.counter_list), new CounterList.Listener() {
            @Override
            public void onPlus(Counter counter) {
                Repository.getInstance().setValue(counter, counter.value + 1);
            }

            @Override
            public void onMinus(Counter counter) {
                Repository.getInstance().setValue(counter, counter.value - 1);
            }

            @Override
            public void onOpen(Counter counter) {
                startActivity(new Intent(CounterListActivity.this, CounterActivity.class)
                        .putExtra(CounterActivity.EXTRA_ID, counter.id));
            }
        });
        updateList();
        Repository.getInstance().addListener(this::updateList);
    }

    private void updateList() {
        mCounterList.setCounters(Repository.getInstance().getCounters());
    }

}
