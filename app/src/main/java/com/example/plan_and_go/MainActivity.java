package com.example.plan_and_go;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.firebase.auth.FirebaseAuth;
import android.view.Menu;
import com.google.firebase.auth.FirebaseUser;


import com.example.plan_and_go.databinding.ActivityMainBinding;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    ActivityMainBinding binding;

    AutocompleteSupportFragment autocompleteFragment, autocompleteFragmentBis;
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

        // Initialiser Places API avec votre clé
        Places.initialize(getApplicationContext(), "AIzaSyDl-ISTL_kAZPEmKH0-Qz_Oqkda_gq-MLA");

        updateActivity(currentUser);
    }

    /**
     * Replace the frame layout by the fragment
     * @param fragment
     */
    private void replaceFragment(int layoutId, Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(layoutId, fragment);
        fragmentTransaction.commit();

    }

    private void mapsInputMethods(AutocompleteSupportFragment autoFragment) {
        autoFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                // Traitement lorsque l'utilisateur sélectionne une adresse
                String address = place.getAddress();
                // Faites quelque chose avec l'adresse sélectionnée
            }

            @Override
            public void onError(@NonNull Status status) {
                // Gérer les erreurs
            }
        });
    }

    /**
     * Listen to the click event on each item menu of the bottom nav bar
     */
    private void listNavItem() {
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.home) {

                replaceFragment(R.id.frame_layout, new HomeFragment());

                autocompleteFragment = AutocompleteSupportFragment.newInstance();
                autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS));
                autocompleteFragment.setCountry("FR"); // Code ISO 3166-1 alpha-2 pour la France

                autocompleteFragmentBis = AutocompleteSupportFragment.newInstance();
                autocompleteFragmentBis.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS));
                autocompleteFragmentBis.setCountry("FR"); // Code ISO 3166-1 alpha-2 pour la France

                replaceFragment(R.id.fragment_container, autocompleteFragment);
                mapsInputMethods(autocompleteFragment);

                replaceFragment(R.id.fragment_container_bis, autocompleteFragmentBis);
                mapsInputMethods(autocompleteFragmentBis);
            } else if (item.getItemId() == R.id.trips) {
                replaceFragment(R.id.frame_layout, new TripsFragment());
            } else if (item.getItemId() == R.id.myAccount) {
                replaceFragment(R.id.frame_layout, new MyAccountFragment());
            } else if (item.getItemId() == R.id.auth) {
                replaceFragment(R.id.frame_layout, new AuthFragment());
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
        replaceFragment(R.id.frame_layout, new HomeFragment());

        autocompleteFragment = AutocompleteSupportFragment.newInstance();
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS));
        autocompleteFragment.setCountry("FR"); // Code ISO 3166-1 alpha-2 pour la France

        autocompleteFragmentBis = AutocompleteSupportFragment.newInstance();
        autocompleteFragmentBis.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS));
        autocompleteFragmentBis.setCountry("FR"); // Code ISO 3166-1 alpha-2 pour la France

        replaceFragment(R.id.fragment_container, autocompleteFragment);
        mapsInputMethods(autocompleteFragment);

        replaceFragment(R.id.fragment_container_bis, autocompleteFragmentBis);
        mapsInputMethods(autocompleteFragmentBis);

        // Select the home's item menu
        binding.bottomNavigationView.setSelectedItemId(R.id.home);

        // Get the bottom nav bar's click listener
        listNavItem();
    }
}