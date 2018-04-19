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

    @Query(("SELECT * FROM Vehicule WHERE agenceId = :idAgence"))
    List<Vehicule> findVehiculeByAgence(int idAgence);

    @Query("SELECT * FROM Vehicule WHERE agenceId = :idAgence AND tarif <= :prix AND carburant LIKE :carburant AND boite_vitesse LIKE :boiteVitesse " +
            "AND type LIKE :type AND etat_loc = :etatLoc")
    List<Vehicule> findVehiculeByOptionsTarif(int idAgence, double prix, String  carburant, String  boiteVitesse, String type, int etatLoc);

    @Query("SELECT * FROM Vehicule WHERE agenceId = :idAgence AND tarif > :prix AND carburant LIKE :carburant AND boite_vitesse LIKE :boiteVitesse " +
            "AND type LIKE :type AND etat_loc = :etatLoc")
    List<Vehicule> findVehiculeByOptionsTarifChere(int idAgence, double prix, String  carburant, String  boiteVitesse, String type, int etatLoc);

    @Query("SELECT * FROM Vehicule WHERE agenceId = :idAgence AND carburant LIKE :carburant AND boite_vitesse LIKE :boiteVitesse " +
            "AND type LIKE :type AND etat_loc = :etatLoc")
    List<Vehicule> findVehiculeByOptionsSansTarif(int idAgence, String  carburant, String  boiteVitesse, String type, int etatLoc);

    @Insert
    void insertAllVehicules(Vehicule... vehicules);

    @Update
    void updateVehicule(Vehicule vehicule);

    @Delete
    void deleteVehicule(Vehicule vehicule);
}
