package com.crammr.studyflashcard.ui.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.crammr.studyflashcard.R;
import com.crammr.studyflashcard.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    public static final int TAB_HOME = R.id.nav_home;
    public static final int TAB_LIBRARY = R.id.nav_library;
    public static final int TAB_AI_CHAT = R.id.nav_ai_chat;
    public static final int TAB_PROGRESS = R.id.nav_progress;
    public static final int TAB_PROFILE = R.id.nav_profile;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (savedInstanceState == null) {
            loadFragment(new HomeFragment());
        }

        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                loadFragment(new HomeFragment());
                return true;
            } else if (id == R.id.nav_library) {
                loadFragment(new LibraryFragment());
                return true;
            } else if (id == R.id.nav_ai_chat) {
                loadFragment(new AiChatFragment());
                return true;
            } else if (id == R.id.nav_progress) {
                loadFragment(new ProgressFragment());
                return true;
            } else if (id == R.id.nav_profile) {
                loadFragment(new ProfileFragment());
                return true;
            }
            return false;
        });
    }

    public void navigateToTab(int tabId) {
        binding.bottomNavigation.setSelectedItemId(tabId);
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }
}
