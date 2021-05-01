package com.test.counter;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<Counter> counters = new ArrayList<>();
        Random rnd = new Random();
        for (int i = 0; i < 100; i++) {
            counters.add(new Counter(("Counter " + (i + 1)), rnd.nextInt(Integer.MAX_VALUE)));
        }

        CounterList counterList = new CounterList(findViewById(R.id.counter_list));
        counterList.setCounters(counters);
    }


}