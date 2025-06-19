package com.example.testapp;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.testapp.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFragment(new HomeFragment());

        Log.d("HomeActivity", "onCreate: HomeActivity loaded");

        // Load default fragment
        // replaceFragment(new HomeFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.home) {

                replaceFragment(new HomeFragment());
                Log.d("BottomNav", "Home selected");
                // replaceFragment(new HomeFragment());
            } else if (itemId == R.id.profile) {

                replaceFragment(new ProfileFragment());
                Log.d("BottomNav", "Profile selected");
                // replaceFragment(new ProfileFragment());
            } else if (itemId == R.id.settings) {

                replaceFragment(new SettingFragment());
                Log.d("BottomNav", "Settings selected");
                // replaceFragment(new SettingsFragment());
            }

            return true;
        });
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_Layout, fragment);
        transaction.commit();
    }
}
