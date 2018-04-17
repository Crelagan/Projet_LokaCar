package fr.eni.jcannas2017.projet_lokacar;

import java.util.List;

import fr.eni.jcannas2017.projet_lokacar.beans.Agence;

interface AllVilleInSpinner {
    void onPostExecute(List<Agence> agenceList);
}
