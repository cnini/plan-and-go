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

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        menu = binding.bottomNavigationView.getMenu();

        if (currentUser == null) {
            menu.findItem(R.id.myAccount).setVisible(false);
            menu.findItem(R.id.auth).setVisible(true);
        } else {
            menu.findItem(R.id.myAccount).setVisible(true);
            menu.findItem(R.id.auth).setVisible(false);
        }

        setContentView(binding.getRoot());

        replaceFragment(new HomeFragment());

        listNavItem();
    }

    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();

    }

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

    public void updateMainActivity(FirebaseUser user) {
        if (user == null) {
            menu.findItem(R.id.myAccount).setVisible(false);
            menu.findItem(R.id.auth).setVisible(true);
        } else {
            menu.findItem(R.id.myAccount).setVisible(true);
            menu.findItem(R.id.auth).setVisible(false);
        }

        setContentView(binding.getRoot());

        replaceFragment(new HomeFragment());

        binding.bottomNavigationView.setSelectedItemId(R.id.home);

        listNavItem();
    }
}