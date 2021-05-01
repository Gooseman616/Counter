package com.test.counter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

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
            counters.add(new Counter(("Counter " + (i + 1)), rnd.nextInt(127)));
        }

        CounterList counterList = new CounterList(findViewById(R.id.counter_list), new CounterList.Listener() {
            @Override
            public void onPlus(Counter counter) {
                Toast.makeText(MainActivity.this, "Plus on " + counter.name, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMinus(Counter counter) {
                Toast.makeText(MainActivity.this, "Minus on " + counter.name, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onOpen(Counter counter) {
                startActivity(new Intent(MainActivity.this, CounterActivity.class));
                Toast.makeText(MainActivity.this, "hi", Toast.LENGTH_SHORT).show();
            }
        });
        counterList.setCounters(counters);
    }


}