package com.test.counter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CounterList {

    private final CounterAdapter mAdapter;

    public CounterList(RecyclerView counterListView) {
        counterListView.setLayoutManager(new LinearLayoutManager(counterListView.getContext()));
        mAdapter = new CounterAdapter();
        counterListView.setAdapter(mAdapter);
    }

    public void setCounters(List<Counter> counters) {
        mAdapter.setData(counters);
    }

    static class CounterAdapter extends RecyclerView.Adapter<CounterAdapter.Vh> {

        private List<Counter> mData;

        void setData(List<Counter> data) {
            mData = data;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public Vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new Vh(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull Vh holder, int position) {

        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        static class Vh extends RecyclerView.ViewHolder {

            Vh(@NonNull ViewGroup parentGroup) {
                super(LayoutInflater.from(parentGroup.getContext())
                        .inflate(R.layout.item_counter, parentGroup, false));
            }
        }
    }
}
