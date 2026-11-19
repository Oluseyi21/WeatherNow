package com.example.weathernow;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.weathernow.databinding.ActivityMainBinding;
import com.example.weathernow.fragments.SearchFragment;
import com.example.weathernow.fragments.SavedFragment;
import com.example.weathernow.fragments.SettingsFragment;

public class MainActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private ActivityMainBinding binding;
    private final String SEARCH_TAG = "SEARCH";
    private final String SAVED_TAG = "SAVED";
    private final String SETTINGS_TAG = "SETTINGS";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (view, windowInsets) -> {
            Insets systemBars = windowInsets.getInsets(
                    WindowInsetsCompat.Type.systemBars()
                            | WindowInsetsCompat.Type.displayCutout()
            );
            view.setPadding(
                    view.getPaddingLeft(),
                    systemBars.top,
                    view.getPaddingRight(),
                    systemBars.bottom
            );
            return windowInsets;
        });
        fragmentManager = getSupportFragmentManager();
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_search) {
                showFragment(SEARCH_TAG, new SearchFragment());
                binding.toolbar.setTitle("WeatherNow");
                return true;
            } else if (itemId == R.id.nav_saved) {
                showFragment(SAVED_TAG, new SavedFragment());
                binding.toolbar.setTitle("Saved Cities");
                return true;
            } else if (itemId == R.id.nav_settings) {
                showFragment(SETTINGS_TAG, new SettingsFragment());
                binding.toolbar.setTitle("Settings");
                return true;
            }
            return false;
        });
        if (savedInstanceState == null) {
            binding.bottomNavigation.setSelectedItemId(R.id.nav_search);
        }
    }

    private void showFragment(String tag, Fragment newFragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment searchFragment = fragmentManager.findFragmentByTag(SEARCH_TAG);
        Fragment savedFragment = fragmentManager.findFragmentByTag(SAVED_TAG);
        Fragment settingsFragment = fragmentManager.findFragmentByTag(SETTINGS_TAG);
        if (searchFragment != null) {
            transaction.hide(searchFragment);
        }
        if (savedFragment != null) {
            transaction.hide(savedFragment);
        }
        if (settingsFragment != null) {
            transaction.hide(settingsFragment);
        }
        Fragment selectedFragment = fragmentManager.findFragmentByTag(tag);
        if (selectedFragment == null) {
            transaction.add(R.id.fragment_container, newFragment, tag);
        } else {
            transaction.show(selectedFragment);
        }
        transaction.commit();
    }
    public void setDetailMode(boolean detailMode) {
        binding.toolbar.setVisibility(detailMode ? View.GONE : View.VISIBLE);
        binding.bottomNavigation.setVisibility(detailMode ? View.GONE : View.VISIBLE);
    }
}
