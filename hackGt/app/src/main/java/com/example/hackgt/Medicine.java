package com.example.hackgt;

public class Medicine {
    private String medicineName;
    private String dosage;
    private String frequency;
    private String treatmentName;

    private int id;

    public Medicine( int id, String medicineName, String dosage, String frequency, String treatmentName) {
        this.id = id;
        this.medicineName = medicineName;
        this.dosage = dosage;
        this.frequency = frequency;
        this.treatmentName = treatmentName;
    }

    // Getters and setters for each attribute


    public int getId() {
        return id;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public String getDosage() {
        return dosage;
    }

    public String getFrequency() {
        return frequency;
    }

    public String getTreatmentName() {
        return treatmentName;
    }
}
