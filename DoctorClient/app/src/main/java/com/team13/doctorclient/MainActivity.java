package com.team13.doctorclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
<<<<<<< Updated upstream
=======
        loadFragment(new ScheduleFragment());
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.request);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment fragment;
            switch (item.getItemId()){
                case R.id.blog:
                    fragment = new BlogFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.schedule:
                    fragment = new ScheduleFragment();
                    loadFragment(fragment);
                    return true;
                default:
                    return false;
            }
        });
        BadgeDrawable badge = bottomNavigationView.getOrCreateBadge(R.id.blog);
        badge.setVisible(true);
        badge.setNumber(99);
>>>>>>> Stashed changes
    }
}