package fr.eni.jcannas2017.projet_lokacar.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import fr.eni.jcannas2017.projet_lokacar.beans.Vehicule;

@Dao
public interface VehiculeDao {

    @Query("SELECT * FROM Vehicule")
    List<Vehicule> getAllVehicules();

    @Query("SELECT * FROM Vehicule WHERE etat_loc = :etatLoc")
    List<Vehicule> findVehiculeByEtatloc(int etatLoc);

    @Query("SELECT * FROM Vehicule WHERE marque = :marque AND modele = :modele AND carburant = :carburant AND boite_vitesse = :boiteVitesse " +
            "AND type = :type AND consomation = :consomation")
    List<Vehicule> findVehiculeByOptions(String marque, String modele, String carburant, String boiteVitesse, String type, double consomation);

    @Query(("SELECT * FROM Vehicule WHERE agenceId = :idAgence"))
    List<Vehicule> findVehiculeByAgence(int idAgence);

    @Insert
    void insertAllVehicules(Vehicule... vehicules);

    @Update
    void updateVehicule(Vehicule vehicule);

    @Delete
    void deleteVehicule(Vehicule vehicule);
}
