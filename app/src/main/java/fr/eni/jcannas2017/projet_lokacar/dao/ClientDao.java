package fr.eni.jcannas2017.projet_lokacar.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import fr.eni.jcannas2017.projet_lokacar.beans.Client;

@Dao
public interface ClientDao {

    @Query("SELECT * FROM Client")
    List<Client> getAllClient();

    @Query("SELECT * FROM Client WHERE id IN (:clientIds)")
    List<Client> loadClientByIds(int[] clientIds);

    @Query("SELECT * FROM Client WHERE nom LIKE :nom AND prenom LIKE :prenom")
    Client findClientByNomPrenom(String nom, String prenom);

    @Query("SELECT * FROM Client WHERE telephone = :tel")
    Client findClientByTelephone(int tel);

    @Insert
    void insertAllClient(Client... clients);

    @Update
    void updateClient(Client client);

    @Delete
    void deleteclient(Client client);
}
