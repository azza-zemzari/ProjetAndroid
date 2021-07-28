package com.example.phone;

public class Rdv {

    private String date;
    private String patient;

    public Rdv() {
    }

    public Rdv( String date, String patient) {

        this.date = date;
        this.patient = patient;
    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    @Override
    public String toString() {
        return "Rdv{" +
                ", date='" + date + '\'' +
                ", patient='" + patient + '\'' +
                '}';
    }
}
