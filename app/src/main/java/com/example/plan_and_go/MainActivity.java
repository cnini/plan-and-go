package com.example.plan_and_go;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import com.google.firebase.auth.FirebaseAuth;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import com.example.plan_and_go.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    private EditText editTextEmail, editTextPassword;
    private Button buttonSignIn, buttonSignUp;

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        replaceFragment(new HomeFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.home) {
                replaceFragment(new HomeFragment());
            } else if (item.getItemId() == R.id.trips) {
                replaceFragment(new TripsFragment());
            } else if (item.getItemId() == R.id.myAccount) {
                replaceFragment(new MyAccountFragment());
            }

            return true;
        });

//        setContentView(R.layout.activity_login);
//
//        mAuth = FirebaseAuth.getInstance();
//
//        editTextEmail = findViewById(R.id.editTextEmail);
//        editTextPassword = findViewById(R.id.editTextPassword);
//        buttonSignIn = findViewById(R.id.buttonSignIn);
//        buttonSignUp = findViewById(R.id.buttonSignUp);
//
//        buttonSignIn.setOnClickListener(v -> signIn());
//        buttonSignUp.setOnClickListener(v -> signUp());
    }

    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();

    }

    private void signIn() {
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Connexion réussie, redirigez l'utilisateur vers la page principale
                        FirebaseUser user = mAuth.getCurrentUser();
                        // Gérer la redirection ici
                        Toast.makeText(MainActivity.this, "SignIn Successful", Toast.LENGTH_SHORT).show();
                    } else {
                        // La connexion a échoué
                        Toast.makeText(MainActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void signUp() {
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Inscription réussie, redirigez l'utilisateur vers la page principale
                        FirebaseUser user = mAuth.getCurrentUser();
                        // Gérer la redirection ici
                        Toast.makeText(MainActivity.this, "SignUp Successful", Toast.LENGTH_SHORT).show();
                    } else {
                        // L'inscription a échoué
                        Toast.makeText(MainActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}