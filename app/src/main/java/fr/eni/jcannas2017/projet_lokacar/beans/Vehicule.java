package fr.eni.jcannas2017.projet_lokacar.beans;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity
public class Vehicule implements Parcelable{

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

    @ForeignKey(entity = Agence.class, parentColumns = "id", childColumns = "agenceId", onDelete = CASCADE)
    private int agenceId;

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

    protected Vehicule(Parcel in) {
        id = in.readInt();
        marque = in.readString();
        modele = in.readString();
        designation = in.readString();
        puissanceAdmin = in.readInt();
        boiteVitesse = in.readString();
        consomation = in.readDouble();
        carburant = in.readString();
        immatriculation = in.readString();
        tarif = in.readDouble();
        type = in.readString();
        etatLoc = in.readInt();
        agenceId = in.readInt();
    }

    public static final Creator<Vehicule> CREATOR = new Creator<Vehicule>() {
        @Override
        public Vehicule createFromParcel(Parcel in) {
            return new Vehicule(in);
        }

        @Override
        public Vehicule[] newArray(int size) {
            return new Vehicule[size];
        }
    };

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

    public int getAgenceId() {
        return agenceId;
    }

    public void setAgenceId(int agenceId) {
        this.agenceId = agenceId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(marque);
        dest.writeString(modele);
        dest.writeString(designation);
        dest.writeInt(puissanceAdmin);
        dest.writeString(boiteVitesse);
        dest.writeDouble(consomation);
        dest.writeString(carburant);
        dest.writeString(immatriculation);
        dest.writeDouble(tarif);
        dest.writeString(type);
        dest.writeInt(etatLoc);
        dest.writeInt(agenceId);
    }
}
