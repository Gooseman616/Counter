package com.test.counter;

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

        CounterList counterList = new CounterList(findViewById(R.id.counter_list), new CounterList.Listener() {
            @Override
            public void onPlus(Counter counter) {
                Repository.getInstance().inc(counter);
                Toast.makeText(MainActivity.this, "Plus on " + counter.name, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMinus(Counter counter) {
                Repository.getInstance().dec(counter);
                Toast.makeText(MainActivity.this, "Minus on " + counter.name, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onOpen(Counter counter) {
                startActivity(new Intent(MainActivity.this, CounterActivity.class));
            }
        });
        counterList.setCounters(Repository.getInstance().getCounters());
    }

}