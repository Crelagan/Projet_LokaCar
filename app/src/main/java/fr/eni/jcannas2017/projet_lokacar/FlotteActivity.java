package fr.eni.jcannas2017.projet_lokacar;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import fr.eni.jcannas2017.projet_lokacar.dummy.DummyContent;
import fr.eni.jcannas2017.projet_lokacar.fragment.CreationFragment;
import fr.eni.jcannas2017.projet_lokacar.fragment.DetailFragment;
import fr.eni.jcannas2017.projet_lokacar.fragment.ListFlotteFragment;

public class FlotteActivity extends AppCompatActivity
                            implements ListFlotteFragment.OnListFragmentInteractionListener,
                                        DetailFragment.OnFragmentInteractionListener{


    DetailFragment detailFragment =  (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.detail);
    CreationFragment creationFragment = (CreationFragment) getSupportFragmentManager().findFragmentById(R.id.creation);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flotte);

        ListFlotteFragment fragment = (ListFlotteFragment) getSupportFragmentManager().findFragmentById(R.id.listeFlotteFragment);

        //layoutCrea.setVisibility(View.VISIBLE);
        //layoutDetail.setVisibility(View.GONE);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabNew);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(FlotteActivity.this, CreationVehiculeActivity.class);
               startActivity(intent);
            }
        });

        FloatingActionButton search = (FloatingActionButton) findViewById(R.id.fabSearch);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FlotteActivity.this, "Recherche !!!!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(FlotteActivity.this, RechercheActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {
        Intent intent = new Intent(FlotteActivity.this, DetailVehiculeActivity.class);
        startActivity(intent);
    }
}
