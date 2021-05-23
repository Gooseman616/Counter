package com.test.counter;

import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

public class CounterListActivity extends AppCompatActivity implements Repository.RepoListener {

    private CounterList mCounterList;
//    private boolean night;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter_list);
        UiModeManager oUiModeManager = (UiModeManager) getApplicationContext().getSystemService(Context.UI_MODE_SERVICE);
        Log.d("Day/Night", "onCreate: " + oUiModeManager.getNightMode());
        Toolbar toolbar = findViewById(R.id.counter_list_toolbar);
        MenuItem nightSwitch = toolbar.getMenu().findItem(R.id.m_night_switch);
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.m_add_counter) {
                new AddDialog().show(getSupportFragmentManager(), null);
            }
            if (item == nightSwitch) {
                switch (AppCompatDelegate.getDefaultNightMode()) {
                    //noinspection deprecation
                    case AppCompatDelegate.MODE_NIGHT_AUTO_TIME:
                    case AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY:
                    case AppCompatDelegate.MODE_NIGHT_UNSPECIFIED:
                    case AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM:
                        if (oUiModeManager.getNightMode() == UiModeManager.MODE_NIGHT_NO) {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        } else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                            Log.d("Switch", "onCreate: case System or else");
                        break;
                    case AppCompatDelegate.MODE_NIGHT_NO:
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        Log.d("Switch", "onCreate: case no");
                        break;
                    case AppCompatDelegate.MODE_NIGHT_YES:
                        Log.d("Switch", "onCreate: case yes");
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        break;
                }
            }
            return true;
        });
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            nightSwitch.setIcon(R.drawable.ic_night);
        } else {
            nightSwitch.setIcon(R.drawable.ic_sun);
        }

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
        mCounterList.setCounters(Repository.getInstance(this).getCounters());
    }
}
