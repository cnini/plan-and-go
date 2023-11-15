package com.example.plan_and_go;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button createTripButton;

    private String departureAddress;
    private String arrivalAddress;


    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        // Trouver la référence du bouton dans le layout
        createTripButton = rootView.findViewById(R.id.button);

        // Ajouter un écouteur de clic au bouton
        createTripButton.setOnClickListener(v -> createNewTrip());

        // Obtenez une référence aux fragments AutocompleteSupportFragment
        FragmentManager fragmentManager = getChildFragmentManager();
        AutocompleteSupportFragment departureFragment = (AutocompleteSupportFragment) fragmentManager.findFragmentById(R.id.fragment_container);
        AutocompleteSupportFragment arrivalFragment = (AutocompleteSupportFragment) fragmentManager.findFragmentById(R.id.fragment_container_bis);

        // Récupérer les adresses saisies
        if (departureFragment != null && arrivalFragment != null) {
            departureFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                @Override
                public void onPlaceSelected(@NonNull Place place) {
                    // Récupérer l'adresse de départ sélectionnée
                    departureAddress = place.getName().toString();
                    // Faites quelque chose avec l'adresse de départ
                }

                @Override
                public void onError(@NonNull Status status) {
                    // Gérer les erreurs éventuelles lors de la sélection de l'adresse de départ
                }
            });

            arrivalFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                @Override
                public void onPlaceSelected(@NonNull Place place) {
                    // Récupérer l'adresse d'arrivée sélectionnée
                    arrivalAddress = place.getName().toString();
                    // Faites quelque chose avec l'adresse d'arrivée
                }

                @Override
                public void onError(@NonNull Status status) {
                    // Gérer les erreurs éventuelles lors de la sélection de l'adresse d'arrivée
                }
            });
        }

        // ...

        return rootView;
    }

    // Méthode pour créer un nouveau trajet
    private void createNewTrip() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference trajetsRef = database.getReference("trajets");

                // Récupérer l'ID de l'utilisateur connecté
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String userId = currentUser != null ? currentUser.getUid() : null;

        Trajet newTrip = new Trajet(departureAddress, arrivalAddress, userId);
        trajetsRef.push().setValue(newTrip);

//        if (departureAddress != null && arrivalAddress != null && userId != null) {
//            // Créer un nouvel objet Trajet
//            Trajet newTrip = new Trajet(departureAddress, arrivalAddress, userId);
//
//            // Récupérer une référence à votre base de données Firebase
//            FirebaseDatabase database = FirebaseDatabase.getInstance();
//            DatabaseReference tripsRef = database.getReference("trajets"); // Assurez-vous que "trips" est votre référence appropriée
//
//            // Générer une nouvelle clé pour ce trajet
//            String tripId = tripsRef.push().getKey();
//
//            if (tripId != null) {
//                // Sauvegarder le trajet avec la clé générée dans la base de données
//                tripsRef.child(tripId).setValue(newTrip)
//                        .addOnSuccessListener(aVoid -> {
//                            // Gestion réussie de la sauvegarde du trajet
//                            Toast.makeText(getContext(), "Trajet enregistré avec succès!", Toast.LENGTH_SHORT).show();
//                        })
//                        .addOnFailureListener(e -> {
//                            // Gestion des erreurs lors de la sauvegarde du trajet
//                            Toast.makeText(getContext(), "Erreur lors de l'enregistrement du trajet: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                        });
//            } else {
//                // Gérer le cas où la clé du trajet est null
//                Toast.makeText(getContext(), "La clé du trajet est nulle", Toast.LENGTH_SHORT).show();
//            }
//        } else {
//            // Gérer le cas où les données nécessaires ne sont pas disponibles
//            // Peut-être afficher un message d'erreur ou gérer la logique en conséquence
//            Toast.makeText(getContext(), "Les informations de trajet sont incomplètes", Toast.LENGTH_SHORT).show();
//        }
    }

}