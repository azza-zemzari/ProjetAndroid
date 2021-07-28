package com.example.phone;

public class Medecin {
    private String id;
    private String name;
    private  String specialite;
    private String adresse;

    public Medecin() {
    }

    public Medecin(String id, String name, String specialite, String adresse) {
        this.id = id;
        this.name = name;
        this.specialite = specialite;
        this.adresse = adresse;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }


    @Override
    public String toString() {
        return "Medecin{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", specialite='" + specialite + '\'' +
                ", adresse='" + adresse + '\'' +
                '}';
    }
}
