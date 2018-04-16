package fr.eni.jcannas2017.projet_lokacar.beans;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Agence {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "code_postal")
    private String codePostal;

    @ColumnInfo(name = "ville")
    private String ville;

    public Agence() {
    }

    public Agence(int id, String codePostal, String ville) {
        this.id = id;
        this.codePostal = codePostal;
        this.ville = ville;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }
}
