package fr.eni.jcannas2017.projet_lokacar.dao;

import android.arch.persistence.room.RoomDatabase;

import fr.eni.jcannas2017.projet_lokacar.beans.Agence;
import fr.eni.jcannas2017.projet_lokacar.beans.Client;
import fr.eni.jcannas2017.projet_lokacar.beans.Gerant;
import fr.eni.jcannas2017.projet_lokacar.beans.Location;
import fr.eni.jcannas2017.projet_lokacar.beans.Vehicule;


@android.arch.persistence.room.Database(entities = {Agence.class, Client.class, Gerant.class, Location.class, Vehicule.class},
version = 1)
public abstract class Database extends RoomDatabase {


    public abstract AgenceDao getAgenceDao();
    public abstract ClientDao getClientDao();
    public abstract GerantDao getGerantDao();
    public abstract LocationDao getLocationDao();
    public abstract VehiculeDao getVehiculeDao();
}
