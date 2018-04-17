package fr.eni.jcannas2017.projet_lokacar.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import fr.eni.jcannas2017.projet_lokacar.beans.Agence;

@Dao
public interface AgenceDao {

    @Query("SELECT * FROM Agence")
    List<Agence> getAllAgence();

    @Query("SELECT * FROM Agence WHERE id IN (:agenceIds)")
    List<Agence> loadAgencesByIds(int[] agenceIds);

    @Query("SELECT * FROM Agence WHERE code_postal LIKE :codePostal")
    List<Agence> loadAgenceByCP(String codePostal);

    @Insert
    void insertAll(Agence... agences);

    @Update
    void updateAgence(Agence agence);

    @Delete
    void deleteAgence(Agence agence);
}
