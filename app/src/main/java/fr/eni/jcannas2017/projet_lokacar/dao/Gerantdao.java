package fr.eni.jcannas2017.projet_lokacar.dao;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import fr.eni.jcannas2017.projet_lokacar.beans.Gerant;

public interface Gerantdao {

    @Query("SELECT * FROM Gerant")
    List<Gerant> getAllGerant();

    @Query("SELECT * FROM Gerant WHERE login = :login")
    Gerant findGerantByLogin(String login);

    @Insert
    void insertAllGerant(Gerant... gerants);

    @Update
    void updateGerant(Gerant gerant);

    @Delete
    void deleteGerant(Gerant gerant);
}
