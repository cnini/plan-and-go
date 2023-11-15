package com.example.plan_and_go;

public class Trajet {
    private String adresseDepart;
    private String adresseArrivee;
    private String utilisateurId; // ID de l'utilisateur associé à ce trajet

    public Trajet() {
        // Constructeur par défaut nécessaire pour Firebase
    }

    public Trajet(String adresseDepart, String adresseArrivee, String utilisateurId) {
        this.adresseDepart = adresseDepart;
        this.adresseArrivee = adresseArrivee;
        this.utilisateurId = utilisateurId;
    }

    public String getAdresseDepart() {
        return adresseDepart;
    }

    public void setAdresseDepart(String adresseDepart) {
        this.adresseDepart = adresseDepart;
    }

    public String getAdresseArrivee() {
        return adresseArrivee;
    }

    public void setAdresseArrivee(String adresseArrivee) {
        this.adresseArrivee = adresseArrivee;
    }

    public String getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(String utilisateurId) {
        this.utilisateurId = utilisateurId;
    }
}
