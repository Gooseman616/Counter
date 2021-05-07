package com.test.counter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Repository {

    private static Repository sInstance;
    private final List<Counter> mList;
    private final Set<RepoListener> mListeners = new HashSet<>();

    private Repository() {
        mList = generateCounterList(3);
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

    private List<Counter> generateCounterList(int ammount) {
        List<Counter> counters = new ArrayList<>(ammount);
        for (int i = 0; i < ammount; i++) {
            counters.add(new Counter(i, ("Counter " + (i + 1)), 0));
        }
        return counters;
    }

    public Counter getCounter(long id) {
        return mList.get((int) id);
    }

    public void setValue(Counter counter, int value) {
        int index = mList.indexOf(counter);
        mList.remove(index);
        mList.add(index, new Counter(counter.id, counter.name, value));
        notifyChanged();
    }

    private void notifyChanged() {
        for (RepoListener listener : mListeners) {
            listener.onDataChanged();
        }
    }

    public void addListener(RepoListener listener) {
        mListeners.add(listener);
    }

    public void removeListener(RepoListener listener) {
        mListeners.remove(listener);
    }

    public interface RepoListener {
        void onDataChanged();
    }
}
