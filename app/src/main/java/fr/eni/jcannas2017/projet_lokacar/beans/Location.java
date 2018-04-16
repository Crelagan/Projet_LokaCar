package fr.eni.jcannas2017.projet_lokacar.beans;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity
public class Location {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ForeignKey(entity = Client.class, parentColumns = "id", childColumns = "clientId", onDelete = CASCADE)
    private int clientId;

    @ForeignKey(entity = Vehicule.class, parentColumns = "id", childColumns = "vehiculeId", onDelete = CASCADE)
    private int vehiculeId;

    @ColumnInfo(name = "date_depart")
    private Date depart;

    @ColumnInfo(name = "date_retour")
    private Date retour;

    @ColumnInfo(name = "duree")
    private int duree;

    public Location() {
    }

    public Location(int id, int clientId, int vehiculeId, Date depart, Date retour, int duree) {
        this.id = id;
        this.clientId = clientId;
        this.vehiculeId = vehiculeId;
        this.depart = depart;
        this.retour = retour;
        this.duree = duree;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getVehiculeId() {
        return vehiculeId;
    }

    public void setVehiculeId(int vehiculeId) {
        this.vehiculeId = vehiculeId;
    }

    public Date getDepart() {
        return depart;
    }

    public void setDepart(Date depart) {
        this.depart = depart;
    }

    public Date getRetour() {
        return retour;
    }

    public void setRetour(Date retour) {
        this.retour = retour;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }
}
