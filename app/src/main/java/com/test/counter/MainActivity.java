package com.test.counter;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<Counter> counters = new ArrayList<>();
        counters.add(new Counter("first", 1));
        counters.add(new Counter("second", 2));
        counters.add(new Counter("third", 3));

        CounterList counterList = new CounterList(findViewById(R.id.counter_list));
        counterList.setCounters(counters);
    }


}