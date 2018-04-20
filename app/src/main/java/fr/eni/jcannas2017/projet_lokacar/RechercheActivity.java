package fr.eni.jcannas2017.projet_lokacar;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.eni.jcannas2017.projet_lokacar.adapter.MyListFlotteRecyclerViewAdapter;
import fr.eni.jcannas2017.projet_lokacar.adapter.MyListeRechercheRecyclerViewAdapter;
import fr.eni.jcannas2017.projet_lokacar.beans.Agence;
import fr.eni.jcannas2017.projet_lokacar.beans.Utils;
import fr.eni.jcannas2017.projet_lokacar.beans.Vehicule;
import fr.eni.jcannas2017.projet_lokacar.dao.Database;
import fr.eni.jcannas2017.projet_lokacar.dummy.DummyContent;
import fr.eni.jcannas2017.projet_lokacar.fragment.FiltreRechercheFragment;
import fr.eni.jcannas2017.projet_lokacar.fragment.ListeRechercheFragment;

public class RechercheActivity extends AppCompatActivity implements FiltreRechercheFragment.OnFragmentInteractionListener, ListeRechercheFragment.OnListFragmentInteractionListener{

    private List<Vehicule> vehiculesAgence;
    private Agence agence;
    private Database db;
    private ListeRechercheFragment rechercheFragment;
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;

    private Spinner sPrix;
    private Spinner sCarburant;
    private Spinner sBVitesse;
    private Spinner stype;
    private CheckBox cbLouer;

    private Map<String, String> mapChoix;

    private MarqueHandle handler = new MarqueHandle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherche);

        Log.i("TAG", "Entre dans le onCreate");

        db = Room.databaseBuilder(getApplicationContext(),
                Database.class, "LOKACAR.db").build();

        agence = new Agence();
        Intent intent = getIntent();
        agence = intent.getParcelableExtra("agence");
        vehiculesAgence = new ArrayList<Vehicule>();

        rechercheFragment = (ListeRechercheFragment) getSupportFragmentManager().findFragmentById(R.id.recherche);
        recyclerView = (RecyclerView) findViewById(R.id.list);

        mapChoix = new HashMap<String, String>();

        sPrix = (Spinner) findViewById(R.id.spinnerPrix);
        sCarburant = (Spinner) findViewById(R.id.spinnerCarbu);
        sBVitesse = (Spinner) findViewById(R.id.spinnerBV);
        stype = (Spinner) findViewById(R.id.spinnerType);
        cbLouer = (CheckBox) findViewById(R.id.cbLouer);

        init();

        new ListVehiculeByAgence().execute();



    }

    @Override
    protected void onStart() {
        super.onStart();


        sPrix.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                mapChoix.remove("prix");
                mapChoix.put("prix", sPrix.getSelectedItem().toString());

                new SetListe().execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }});
        sCarburant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mapChoix.remove("carbu");
                mapChoix.put("carbu", sCarburant.getSelectedItem().toString());

                new SetListe().execute();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sBVitesse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mapChoix.remove("bv");
                mapChoix.put("bv", sBVitesse.getSelectedItem().toString());

                new SetListe().execute();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        stype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mapChoix.remove("type");
                mapChoix.put("type", stype.getSelectedItem().toString());

                new SetListe().execute();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        cbLouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapChoix.remove("loc");
                mapChoix.put("loc", cbLouer.isChecked()?"louer":"pasLouer");

                new SetListe().execute();
            }

        });

    }

    private void init() {

        ArrayAdapter<Utils.enumTarif> adTarif = new ArrayAdapter<Utils.enumTarif>(RechercheActivity.this,
                R.layout.support_simple_spinner_dropdown_item, Utils.enumTarif.values());
        sPrix.setAdapter(adTarif);

        ArrayAdapter<Utils.enumCarburant> adCarburant = new ArrayAdapter<Utils.enumCarburant>(RechercheActivity.this,
                R.layout.support_simple_spinner_dropdown_item, Utils.enumCarburant.values());
        sCarburant.setAdapter(adCarburant);

        ArrayAdapter<Utils.enumBoiteVitesse> adBV = new ArrayAdapter<Utils.enumBoiteVitesse>(RechercheActivity.this,
                R.layout.support_simple_spinner_dropdown_item, Utils.enumBoiteVitesse.values());
        sBVitesse.setAdapter(adBV);

        ArrayAdapter<Utils.enumType> adType = new ArrayAdapter<Utils.enumType>(RechercheActivity.this,
                R.layout.support_simple_spinner_dropdown_item, Utils.enumType.values());
        stype.setAdapter(adType);

        cbLouer.setChecked(false);


        mapChoix.put("prix", sPrix.getSelectedItem().toString());
        mapChoix.put("carbu", sCarburant.getSelectedItem().toString());
        mapChoix.put("bv", sBVitesse.getSelectedItem().toString());
        mapChoix.put("type", stype.getSelectedItem().toString());
        mapChoix.put("loc", cbLouer.isChecked()?"louer":"pasLouer");

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListFragmentInteraction(String item) {
        Intent intent = new Intent(RechercheActivity.this, DetailVehiculeActivity.class);
        startActivity(intent);
    }

    public class ListVehiculeByAgence extends AsyncTask<Void, Void, List<Vehicule>> {


        List<Vehicule> vehicules;

        @Override
        protected List<Vehicule> doInBackground(Void... voids) {

            vehicules = new ArrayList<Vehicule>();

            db.beginTransaction();
            vehicules = db.vehiculeDao().findVehiculeByAgence(agence.getId());
            db.endTransaction();

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

    public class SetListe extends AsyncTask<Void, Void, List<Vehicule>>{

        List<Vehicule> vehicules;
        @Override
        protected List<Vehicule> doInBackground(Void... voids) {

            vehicules = new ArrayList<Vehicule>();
            int louer = -1;
            int prix;

            if (mapChoix.get("prix").equals("")){
                prix = -1;
            }
            else if(mapChoix.get("prix").equals("> 100€")){
                prix = 1000;
            }
            else if(mapChoix.get("prix").equals("< 100€")){
                prix = 100;
            }
            else if(mapChoix.get("prix").equals("< 90€")){
                prix = 90;
            }
            else if(mapChoix.get("prix").equals("< 70€")){
                prix = 70;
            }
            else {
                prix = 50;
            }

            if (mapChoix.get("loc").equals("louer")){
                louer = 1;
            }else{
                louer = 0;
            }

            db.beginTransaction();

            if (prix < 0){
                vehicules = db.vehiculeDao().findVehiculeByOptionsSansTarif(agence.getId(), mapChoix.get("carbu") + "%",mapChoix.get("bv")+ "%",mapChoix.get("type")+ "%", louer);
            }
            else if(prix > 100){
                vehicules = db.vehiculeDao().findVehiculeByOptionsTarifChere(agence.getId(), 100, mapChoix.get("carbu")+ "%",mapChoix.get("bv")+ "%",mapChoix.get("type")+ "%", louer);
            }
            else {
                vehicules = db.vehiculeDao().findVehiculeByOptionsTarif(agence.getId(), prix, mapChoix.get("carbu")+ "%",mapChoix.get("bv")+ "%",mapChoix.get("type")+ "%", louer);
            }

            db.endTransaction();

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

        vehiculesAgence = vehicules;

       List<String> modeles = new ArrayList<String>();

       for (int i = 0; i<vehicules.size(); i++){
            modeles.add(vehicules.get(i).getModele());
        }

        rechercheFragment.setAdapter(new MyListeRechercheRecyclerViewAdapter(modeles, RechercheActivity.this));
    }
}
