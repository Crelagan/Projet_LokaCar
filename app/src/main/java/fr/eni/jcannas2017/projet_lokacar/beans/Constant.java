package fr.eni.jcannas2017.projet_lokacar.beans;

public class Constant {

    public final static String DATABASE_NAME = "Vehicules.db";
    public final static int DATABASE_VERSION = 1;

    public final static String MODELE_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
            + "MODELES ("
            + "ID INTEGER PRIMARY KEY , "
            + "DESIGNATION TEXT,"
            + "MODELE_COMMERCIAL TEXT,"
            + "CNIT TEXT,"
            + "MARQUE TEXT,"
            + "MODELE_DOSSIER TEXT)";


    public final static String QUERY_DELETE_TABLE_MODELE = "DROP TABLE IF EXISTS MODELES";
}
