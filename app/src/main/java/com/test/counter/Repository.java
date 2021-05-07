package com.test.counter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Repository {

    private static Repository sInstance;
    private final List<Counter> mList;

    private Repository() {
        mList = generateCounterList(15);
    }

    public static Repository getInstance() {
        if (sInstance == null) {
            sInstance = new Repository();
        }
        return sInstance;
    }

    public List<Counter> getCounters() {
        return mList;
    }

    public void inc(Counter counter) {
        int index = mList.indexOf(counter);
        mList.remove(index);
        mList.add(index, new Counter(counter.name, counter.value + 1));
    }

    public void dec(Counter counter) {
        int index = mList.indexOf(counter);
        mList.remove(index);
        mList.add(index, new Counter(counter.name, counter.value - 1));
    }

    private List<Counter> generateCounterList(int ammount) {
        List<Counter> counters = new ArrayList<>(ammount);
        Random rnd = new Random();
        for (int i = 0; i < ammount; i++) {
            counters.add(new Counter(("Counter " + (i + 1)), rnd.nextInt(127)));
        }
        return counters;
    }
}
