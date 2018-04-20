package fr.eni.jcannas2017.projet_lokacar;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
/*import android.support.v7.widget.CardView;*/
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fr.eni.jcannas2017.projet_lokacar.adapter.MyListFlotteRecyclerViewAdapter;
import fr.eni.jcannas2017.projet_lokacar.beans.Agence;
import fr.eni.jcannas2017.projet_lokacar.beans.Vehicule;
import fr.eni.jcannas2017.projet_lokacar.dao.Database;
import fr.eni.jcannas2017.projet_lokacar.dummy.DummyContent;
import fr.eni.jcannas2017.projet_lokacar.fragment.CreationFragment;
import fr.eni.jcannas2017.projet_lokacar.fragment.DetailFragment;
import fr.eni.jcannas2017.projet_lokacar.fragment.ListFlotteFragment;

public class FlotteActivity extends AppCompatActivity
                            implements ListFlotteFragment.OnListFragmentInteractionListener,
                                        DetailFragment.OnFragmentInteractionListener{


    private DetailFragment detailFragment;
    private CreationFragment creationFragment;
    private List<Vehicule> vehiculeList;
    private Agence agence;
    private Database db;
    private MarqueHandle handler = new MarqueHandle();
    private ListFlotteFragment flotteFragment;
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flotte);

        db = Room.databaseBuilder(getApplicationContext(),
                Database.class, "LOKACAR.db").build();

        detailFragment =  (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.detail);
        creationFragment = (CreationFragment) getSupportFragmentManager().findFragmentById(R.id.creation);
        vehiculeList = new ArrayList<Vehicule>();
        agence = new Agence();
        flotteFragment = (ListFlotteFragment) getSupportFragmentManager().findFragmentById(R.id.listeFlotteFragment);

        Intent intent = getIntent();
        agence = intent.getParcelableExtra("agence");

        recyclerView = (RecyclerView) findViewById(R.id.list);

        new ListVehiculeByAgence().execute();

        ListFlotteFragment fragment = (ListFlotteFragment) getSupportFragmentManager().findFragmentById(R.id.listeFlotteFragment);

        //layoutCrea.setVisibility(View.VISIBLE);
        //layoutDetail.setVisibility(View.GONE);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabNew);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(FlotteActivity.this, CreationVehiculeActivity.class);
               intent.putExtra("agence", agence);
               startActivity(intent);
            }
        });

        FloatingActionButton search = (FloatingActionButton) findViewById(R.id.fabSearch);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Toast.makeText(FlotteActivity.this, "Recherche !!!!", Toast.LENGTH_LONG).show();*/
                Intent intent = new Intent(FlotteActivity.this, RechercheActivity.class);
                intent.putExtra("agence", agence);
                startActivity(intent);
            }
        });
    }

    public class ListVehiculeByAgence extends AsyncTask<Void, Void, List<Vehicule>>{

        List<Vehicule> vehicules;

        @Override
        protected List<Vehicule> doInBackground(Void... voids) {
            vehicules = new ArrayList<Vehicule>();

            db.beginTransaction();
            vehicules = db.vehiculeDao().findVehiculeByAgence(agence.getId());
            db.endTransaction();

            Log.i("TAG", "Nbre de vehicule liste par agence (norm = 3= : " + vehicules.size());

            return vehicules;
        }

        @Override
        protected void onPostExecute(List<Vehicule> vehicules) {

            Message msg = new Message();
            msg.what = 2;
            msg.obj = vehicules;
            handler.handleMessage(msg);

            super.onPostExecute(vehicules);
        }
    }

    /**
     * Handler de communication entre l'activité et AccesMarqueTask
     */
    private class MarqueHandle extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1 :
                    /*connecter((Boolean) msg.obj);*/
                    break;

                case 2 :
                    miseAJourListVehicule((List<Vehicule>) msg.obj);
                    break;
            }
        }
    }

    private void miseAJourListVehicule(List<Vehicule> vehicules) {

        vehiculeList = vehicules;
        /*List<String> modeles = new ArrayList<String>();


        for (int i = 0; i<vehicules.size(); i++){
            modeles.add(vehicules.get(i).getModele());
        }*/

        flotteFragment.setAdapter(new MyListFlotteRecyclerViewAdapter(vehiculeList, FlotteActivity.this));


        //adapter = new RecyclerView.Adapter(modeles, FlotteActivity.this) /*ArrayAdapter(FlotteActivity.this, R.layout.fragment_listflotte, modeles)*/;


        //recyclerView.setAdapter(adapter);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListFragmentInteraction(Vehicule item) {
        Intent intent = new Intent(FlotteActivity.this, DetailVehiculeActivity.class);
        intent.putExtra("agence", agence);
        intent.putExtra("vehicule", item);
        startActivity(intent);
    }
}
