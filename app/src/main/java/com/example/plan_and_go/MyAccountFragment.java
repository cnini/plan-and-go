package com.example.plan_and_go;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.plan_and_go.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyAccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyAccountFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private ActivityMainBinding binding;

    private Button buttonLogout;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyAccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyAccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyAccountFragment newInstance(String param1, String param2) {
        MyAccountFragment fragment = new MyAccountFragment();
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
        View view = inflater.inflate(R.layout.fragment_my_account, container, false);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        binding = ActivityMainBinding.inflate(inflater);

        buttonLogout = view.findViewById(R.id.buttonLogout);

        if (currentUser != null) {
            buttonLogout.setOnClickListener(v -> logout());
        } else {
            binding.bottomNavigationView.getMenu().findItem(R.id.myAccount).setVisible(false);
            binding.bottomNavigationView.getMenu().findItem(R.id.auth).setVisible(true);
        }

        // Inflate the layout for this fragment
        return view;
    }

    private void logout() {
        mAuth.signOut();

        binding.bottomNavigationView.getMenu().findItem(R.id.myAccount).setVisible(false);
        binding.bottomNavigationView.getMenu().findItem(R.id.auth).setVisible(true);
    }
}