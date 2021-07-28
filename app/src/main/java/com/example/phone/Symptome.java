package com.example.phone;

public class Symptome {
    private String name;
    private String niveau;

    public Symptome() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public Symptome(String name, String niveau) {
        this.name = name;
        this.niveau = niveau;
    }

    @Override
    public String toString() {
        return "Symptome{" +
                "name='" + name + '\'' +
                ", niveau='" + niveau + '\'' +
                '}';
    }


}

