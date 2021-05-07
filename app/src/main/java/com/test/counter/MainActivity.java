package com.test.counter;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public class MainActivity extends AppCompatActivity implements LifecycleObserver {

    private CounterListActivity mCounterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCounterList = new CounterListActivity(findViewById(R.id.counter_list), new CounterListActivity.Listener() {
            @Override
            public void onPlus(Counter counter) {
                Repository.getInstance().setValue(counter, counter.value + 1);
                updateList();
            }

            @Override
            public void onMinus(Counter counter) {
                Repository.getInstance().setValue(counter, counter.value - 1);
                updateList();
            }

            @Override
            public void onOpen(Counter counter) {
                startActivity(new Intent(MainActivity.this, CounterActivity.class)
                        .putExtra(CounterActivity.EXTRA_ID, counter.id));
            }
        });
        updateList();
    }

    @Override
    public void onResume(){
        super.onResume();
        updateList();
    }

    private void updateList() {
        mCounterList.setCounters(Repository.getInstance().getCounters());
    }

}
