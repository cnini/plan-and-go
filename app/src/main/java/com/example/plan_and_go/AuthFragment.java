package com.example.plan_and_go;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.plan_and_go.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AuthFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AuthFragment extends Fragment {

    private FirebaseAuth mAuth;
    private ActivityMainBinding binding;

    private EditText editAuthEmail, editAuthPassword;
    private Button buttonLogin, buttonSignup;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AuthFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AuthFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AuthFragment newInstance(String param1, String param2) {
        AuthFragment fragment = new AuthFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_auth, container, false);

        mAuth = FirebaseAuth.getInstance();

        binding = ActivityMainBinding.inflate(inflater);

        editAuthEmail = view.findViewById(R.id.editTextEmail);
        editAuthPassword = view.findViewById(R.id.editTextPassword);

        buttonLogin = view.findViewById(R.id.buttonSignIn);
        buttonSignup = view.findViewById(R.id.buttonSignUp);

        buttonLogin.setOnClickListener(v -> signIn());
        buttonSignup.setOnClickListener(v -> signUp());

        // Inflate the layout for this fragment
        return view;
    }



    private void signIn() {
        String email = editAuthEmail.getText().toString();
        String password = editAuthPassword.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        binding.bottomNavigationView.getMenu().findItem(R.id.myAccount).setVisible(true);
                        binding.bottomNavigationView.getMenu().findItem(R.id.auth).setVisible(false);
                    } else {
                        // La connexion a échoué
                    }
                });
    }

    private void signUp() {
        String email = editAuthEmail.getText().toString();
        String password = editAuthPassword.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        binding.bottomNavigationView.getMenu().findItem(R.id.myAccount).setVisible(true);
                        binding.bottomNavigationView.getMenu().findItem(R.id.auth).setVisible(false);
                    } else {
                        // L'inscription a échoué
                    }
                });
    }
}