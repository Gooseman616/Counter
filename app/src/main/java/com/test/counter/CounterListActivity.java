package com.test.counter;

import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

public class CounterListActivity extends AppCompatActivity implements Repository.RepoListener {

    private static final String APP_NIGHT_MODE = "DAY_NIGHT";
    private static final int DEFAULT_APP_NIGHT_MODE = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
    private CounterList mCounterList;
    private SharedPreferences mPrefs;
    private static UiModeManager oUiModeManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        oUiModeManager = (UiModeManager) getApplicationContext().getSystemService(Context.UI_MODE_SERVICE);
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(mPrefs.getInt(APP_NIGHT_MODE, DEFAULT_APP_NIGHT_MODE));
        Log.d("Day/Night", "onCreate: System Night Mode " + oUiModeManager.getNightMode());
        Log.d("Day/Night", "onCreate: Night Mode " + AppCompatDelegate.getDefaultNightMode());
        setContentView(R.layout.activity_counter_list);

        Toolbar toolbar = findViewById(R.id.counter_list_toolbar);
        MenuItem nightSwitch = toolbar.getMenu().findItem(R.id.m_night_switch);
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            nightSwitch.setIcon(R.drawable.ic_sun);
        } else {
            nightSwitch.setIcon(R.drawable.ic_night);
        }
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.m_add_counter) {
                new AddDialog().show(getSupportFragmentManager(), null);
            }
            if (item == nightSwitch) {
                switchDayNight();
            }
            return true;
        });

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
        if (Repository.getInstance(this).getCounters().isEmpty()) {
            findViewById(R.id.placeholder_text).setVisibility(View.VISIBLE);
            findViewById(R.id.placeholder_text).setOnClickListener(v -> new AddDialog().show(getSupportFragmentManager(), null));
        } else {
            findViewById(R.id.placeholder_text).setVisibility(View.GONE);
        }
        mCounterList.setCounters(Repository.getInstance(this).getCounters());
    }

    private void switchDayNight() {
        switch (AppCompatDelegate.getDefaultNightMode()) {
            //noinspection deprecation
            case AppCompatDelegate.MODE_NIGHT_AUTO_TIME:
            case AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY:
            case AppCompatDelegate.MODE_NIGHT_UNSPECIFIED:
            case AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM:
                Log.d("Day/Night", "onSwitchClick: case follow System or else");
                if (oUiModeManager.getNightMode() == UiModeManager.MODE_NIGHT_NO) {
                    Log.d("Day/Night", "onSwitchClick: System Day (Switch to force Night)");
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    mPrefs.edit().putInt(APP_NIGHT_MODE, AppCompatDelegate.MODE_NIGHT_YES).apply();
                } else {
                    Log.d("Day/Night", "onSwitchClick: System Night (Switch to force day)");
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    mPrefs.edit().putInt(APP_NIGHT_MODE, AppCompatDelegate.MODE_NIGHT_NO).apply();
                }
                break;
            case AppCompatDelegate.MODE_NIGHT_NO:
                Log.d("Day/Night", "onSwitchClick: case Day (Switch to Night)");
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                mPrefs.edit().putInt(APP_NIGHT_MODE, AppCompatDelegate.MODE_NIGHT_YES).apply();
                break;
            case AppCompatDelegate.MODE_NIGHT_YES:
                Log.d("Day/Night", "onSwitchClick: case Night (Switch to Day)");
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                mPrefs.edit().putInt(APP_NIGHT_MODE, AppCompatDelegate.MODE_NIGHT_NO).apply();
                break;
        }
        Log.d("Day/Night", "--------------------------");
    }
}
