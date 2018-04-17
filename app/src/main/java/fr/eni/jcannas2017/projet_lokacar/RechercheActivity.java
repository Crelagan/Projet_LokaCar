package fr.eni.jcannas2017.projet_lokacar;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import fr.eni.jcannas2017.projet_lokacar.dummy.DummyContent;
import fr.eni.jcannas2017.projet_lokacar.fragment.FiltreRechercheFragment;
import fr.eni.jcannas2017.projet_lokacar.fragment.ListeRechercheFragment;

public class RechercheActivity extends AppCompatActivity implements FiltreRechercheFragment.OnFragmentInteractionListener, ListeRechercheFragment.OnListFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherche);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {
        Intent intent = new Intent(RechercheActivity.this, DetailVehiculeActivity.class);
        startActivity(intent);
    }
}
