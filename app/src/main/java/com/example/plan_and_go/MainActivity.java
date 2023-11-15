package com.example.plan_and_go;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import android.view.Menu;
import com.google.firebase.auth.FirebaseUser;


import com.example.plan_and_go.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    ActivityMainBinding binding;
    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the Firebase instance
        mAuth = FirebaseAuth.getInstance();

        // Get the Firebase user
        currentUser = mAuth.getCurrentUser();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        menu = binding.bottomNavigationView.getMenu();

        updateActivity(currentUser);
    }

    /**
     * Replace the frame layout by the fragment
     * @param fragment
     */
    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();

    }

    /**
     * Listen to the click event on each item menu of the bottom nav bar
     */
    private void listNavItem() {
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.home) {
                replaceFragment(new HomeFragment());
            } else if (item.getItemId() == R.id.trips) {
                replaceFragment(new TripsFragment());
            } else if (item.getItemId() == R.id.myAccount) {
                replaceFragment(new MyAccountFragment());
            } else if (item.getItemId() == R.id.auth) {
                replaceFragment(new AuthFragment());
            }

            return true;
        });
    }

    /**
     * Update the bottom nav bar, depending on the current user
     * @param user
     */
    public void updateActivity(FirebaseUser user) {
        if (user == null) {
            // Display authentication's item menu
            menu.findItem(R.id.myAccount).setVisible(false);
            menu.findItem(R.id.auth).setVisible(true);
        } else {
            // Display my account's item menu
            menu.findItem(R.id.myAccount).setVisible(true);
            menu.findItem(R.id.auth).setVisible(false);
        }

        setContentView(binding.getRoot());

        // Display the home fragment
        replaceFragment(new HomeFragment());

        // Select the home's item menu
        binding.bottomNavigationView.setSelectedItemId(R.id.home);

        // Get the bottom nav bar's click listener
        listNavItem();
    }
}