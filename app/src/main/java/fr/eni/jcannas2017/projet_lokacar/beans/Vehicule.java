package fr.eni.jcannas2017.projet_lokacar.beans;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Vehicule {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "marque")
    private String marque;

    @ColumnInfo(name = "modele")
    private String modele;

    @ColumnInfo(name = "designation")
    private String designation;

    @ColumnInfo(name = "puissance_admin")
    private int puissanceAdmin;

    @ColumnInfo(name = "boite_vitesse")
    private String boiteVitesse;

    @ColumnInfo(name = "consomation")
    private double consomation;

    @ColumnInfo(name = "carburant")
    private String carburant;

    @ColumnInfo(name = "immatriculation")
    private String immatriculation;

    @ColumnInfo(name = "tarif")
    private double tarif;

    @ColumnInfo(name = "type")
    private String type;

    @ColumnInfo(name = "etat_loc")
    private int etatLoc;

    public Vehicule() {
    }

    public Vehicule(int id, String marque, String modele, String designation, int puissanceAdmin, String boiteVitesse,
                    double consomation, String carburant, String immatriculation, double tarif, String type, int etatLoc) {
        this.id = id;
        this.marque = marque;
        this.modele = modele;
        this.designation = designation;
        this.puissanceAdmin = puissanceAdmin;
        this.boiteVitesse = boiteVitesse;
        this.consomation = consomation;
        this.carburant = carburant;
        this.immatriculation = immatriculation;
        this.tarif = tarif;
        this.type = type;
        this.etatLoc = etatLoc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public int getPuissanceAdmin() {
        return puissanceAdmin;
    }

    public void setPuissanceAdmin(int puissanceAdmin) {
        this.puissanceAdmin = puissanceAdmin;
    }

    public String getBoiteVitesse() {
        return boiteVitesse;
    }

    public void setBoiteVitesse(String boiteVitesse) {
        this.boiteVitesse = boiteVitesse;
    }

    public double getConsomation() {
        return consomation;
    }

    public void setConsomation(double consomation) {
        this.consomation = consomation;
    }

    public String getCarburant() {
        return carburant;
    }

    public void setCarburant(String carburant) {
        this.carburant = carburant;
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }

    public double getTarif() {
        return tarif;
    }

    public void setTarif(double tarif) {
        this.tarif = tarif;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getEtatLoc() {
        return etatLoc;
    }

    public void setEtatLoc(int etatLoc) {
        this.etatLoc = etatLoc;
    }
}
