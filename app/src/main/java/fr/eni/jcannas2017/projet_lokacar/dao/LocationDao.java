package fr.eni.jcannas2017.projet_lokacar.dao;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.Date;
import java.util.List;

import fr.eni.jcannas2017.projet_lokacar.beans.Client;
import fr.eni.jcannas2017.projet_lokacar.beans.Location;
import fr.eni.jcannas2017.projet_lokacar.beans.Vehicule;

public interface LocationDao {

    @Query("SELECT * FROM Location")
    List<Location> getAllLocation();

    @Query("SELECT * FROM Location WHERE date_depart = :depart")
    List<Location> findLocationByDepart(Date depart);

    @Query("SELECT * FROM Location Where date_retour = :retour")
    List<Location> findLocationBy(Date retour);

    @Query("SELECT Client.id, Client.nom, Client.prenom, Client.adresse, Client.code_postal, Client.telephone, Client.mail" +
            " FROM Location INNER JOIN Client ON Client.id = clientId WHERE date_retour >= :retour")
    List<Client> findClientByRetour(Date retour);

    @Query("SELECT Vehicule.id, Vehicule.marque, Vehicule.modele, Vehicule.designation, Vehicule.puissance_admin, Vehicule.boite_vitesse, " +
            "Vehicule.consomation, Vehicule.carburant, Vehicule.immatriculation, Vehicule.tarif, Vehicule.type , Vehicule.etat_loc FROM Location INNER JOIN Vehicule ON Vehicule.id = vehiculeId WHERE date_retour >= :retour")
    List<Vehicule> findVehiculeByRetour(Date retour);

    @Insert
    void insertLocation(Location location);

    @Update
    void updateLocation(Location location);

    @Delete
    void deleteLocation(Location location);
}
