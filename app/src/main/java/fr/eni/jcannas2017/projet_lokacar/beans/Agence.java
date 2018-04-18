package fr.eni.jcannas2017.projet_lokacar.beans;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity
public class Agence implements Parcelable{
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "code_postal")
    private String codePostal;

    @ColumnInfo(name = "ville")
    private String ville;

    @ForeignKey(entity = Gerant.class, parentColumns = "id", childColumns = "gerantId", onDelete = CASCADE)
    private int gerantId;

    public Agence() {
    }

    public Agence(int id, String codePostal, String ville) {
        this.id = id;
        this.codePostal = codePostal;
        this.ville = ville;
    }

    protected Agence(Parcel in) {
        id = in.readInt();
        codePostal = in.readString();
        ville = in.readString();
        gerantId = in.readInt();
    }

    public static final Creator<Agence> CREATOR = new Creator<Agence>() {
        @Override
        public Agence createFromParcel(Parcel in) {
            return new Agence(in);
        }

        @Override
        public Agence[] newArray(int size) {
            return new Agence[size];
        }
    };

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

    public int getGerantId() {
        return gerantId;
    }

    public void setGerantId(int gerantId) {
        this.gerantId = gerantId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(codePostal);
        dest.writeString(ville);
        dest.writeInt(gerantId);
    }
}
