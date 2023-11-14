package com.example.plan_and_go;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.google.firebase.auth.FirebaseAuth;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    private EditText editTextEmail, editTextPassword;
    private Button buttonSignIn, buttonSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonSignIn = findViewById(R.id.buttonSignIn);
        buttonSignUp = findViewById(R.id.buttonSignUp);

        buttonSignIn.setOnClickListener(v -> signIn());
        buttonSignUp.setOnClickListener(v -> signUp());
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