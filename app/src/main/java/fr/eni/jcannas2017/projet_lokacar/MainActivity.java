package fr.eni.jcannas2017.projet_lokacar;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import fr.eni.jcannas2017.projet_lokacar.beans.Agence;
import fr.eni.jcannas2017.projet_lokacar.beans.Gerant;
import fr.eni.jcannas2017.projet_lokacar.beans.Vehicule;
import fr.eni.jcannas2017.projet_lokacar.dao.Database;

public class MainActivity extends AppCompatActivity {

    private Database db;
    private final String DATABASE_NAME ="LOKACAR.db";
    private Spinner spinner;
    private TextView tvCodePostal;
    private TextView tvLogin;
    private TextView tvMdp;

    private ArrayAdapter<String> adapter;
    private Agence agence;
    private List<Agence> agences;


    private MarqueHandle handler = new MarqueHandle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


            db = Room.databaseBuilder(getApplicationContext(),
                    Database.class, "LOKACAR.db").build();


        spinner = (Spinner) findViewById(R.id.spinner) ;
        tvLogin = (TextView) findViewById(R.id.editText3) ;
        tvMdp = (TextView) findViewById(R.id.editText4);

        tvCodePostal = (TextView) findViewById(R.id.editText2);
        tvCodePostal.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (tvCodePostal.getText() != null){

                    new EditSpinnerList().execute(tvCodePostal.getText().toString());

                }
                else{
                    new AllVilleInSpinner().execute();
                }
                return false;
            }
        });


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String temp = (String)parent.getItemAtPosition(pos);
                agence = new Agence();

                for(int i = 0; i < agences.size(); i++){
                    if (agences.get(i).getVille() == temp){
                        agence = agences.get(i);
                    }
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Button btn = (Button) findViewById((R.id.btn));
        btn.setOnClickListener(new checkLogin());


    }

    private class checkLogin implements View.OnClickListener {
        public void onClick(View v){

            //Recupération des champs
            if (agence == null) {
                agence = new Agence();
                String temp = spinner.getPrompt().toString();
                for(int i = 0; i < agences.size(); i++){
                    if (agences.get(i).getVille() == temp){
                        agence = agences.get(i);
                    }
                }
            }

            String login = tvLogin.getText().toString();
            if (login == ""){
                Toast.makeText(MainActivity.this, "Veuiller entrer votre login !", Toast.LENGTH_LONG).show();

            }
            else {
                String mdp = tvMdp.getText().toString();
                if (mdp == "") {
                    Toast.makeText(MainActivity.this, "Veuiller entrer votre mot de passe !", Toast.LENGTH_LONG).show();

                }
                else {

                    Log.i("TAG", "mot de passe saisie : " + mdp);

                    new ValidatedCheckLogin().execute(login, mdp);
                }
            }
        }
    }

    public class AllVilleInSpinner extends AsyncTask<Void, Void, List<Agence>>  {

        List<Agence> atAgences;

        @Override
        protected List<Agence> doInBackground(Void... voids) {


            atAgences = new ArrayList<Agence>();
            // Alimenter le spinner avec la liste complète des villes
            db.beginTransaction();
            atAgences = db.agenceDao().getAllAgence();
            db.endTransaction();


            return atAgences;
        }

        @Override
        protected void onPostExecute(List<Agence> agenceList) {

            Log.i("TAG_MESSAGE", "Nombre d'agence : " + agenceList.size());

            Message msg = new Message();
            msg.what = 2;
            msg.obj = agenceList;
            handler.handleMessage(msg);

            super.onPostExecute(agenceList);
        }
    }




    public class ValidatedCheckLogin extends AsyncTask<String, Void, Boolean>{

        Boolean isOk = false;

        @Override
        protected Boolean doInBackground(String... params) {

            Gerant g = new Gerant();
            g = db.gerantDao().findGerantByLogin(params[0]);

            Log.i("TAG", "Gerant BDD : \nId : " + g.getId() + "\nLogin : " + g.getLogin() + "\nmdp : " + g.getMdp());

            if (g == null){
               // Toast.makeText(MainActivity.this,"Votre login est incorrecte !", Toast.LENGTH_LONG).show();
                Log.i("TAG", "Votre login est incorrecte !");
            }
            else {
                if (!g.getMdp().equals(params[1])){
                   // Toast.makeText(MainActivity.this,"Votre mot de passe est incorrecte !", Toast.LENGTH_LONG).show();
                    Log.i("TAG", "Votre mot de passe est incorrecte !");
                    Log.i("TAG", "mdp en base " + g.getMdp());
                    Log.i("TAG", "mot de passe saisie" + params[1]);

                }
                else
                {

                    isOk = true;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {

            Message msg = new Message();
            msg.what = 1;
            msg.obj = isOk;
            handler.handleMessage(msg);
            super.onPostExecute(aBoolean);
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
                    connecter((Boolean) msg.obj);
                    break;

                case 2 :
                    miseAJourSpinner((List<Agence>) msg.obj);
                    break;
            }
        }
    }

    private void connecter(Boolean connected) {

        if (connected){
            Intent intent = new Intent(MainActivity.this, FlotteActivity.class);

            Log.i("TAG", "numéro de l'agence à envoyer : " + agence.getId());

            intent.putExtra("idAgence", agence.getId());
            startActivity(intent);
        }
        else
        {
            Toast.makeText(MainActivity.this, "Erreur de saisie, veuiller recommencer !", Toast.LENGTH_LONG);
        }

    }

    private void miseAJourSpinner(List<Agence> al) {

        List<String> villes;
        villes = new ArrayList<String>();

        agences = new ArrayList<Agence>();
        agences = al;

        for (int i = 0; i < al.size(); i++){
            villes.add(al.get(i).getVille());
        }

        adapter = new ArrayAdapter(MainActivity.this, R.layout.support_simple_spinner_dropdown_item,
                villes);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }


    public class EditSpinnerList extends AsyncTask<String, Void, List<Agence>> {

        List<Agence> atAgences;

        @Override
        protected List<Agence> doInBackground(String... codePostal) {


            atAgences = new ArrayList<Agence>();
            // Alimenter le spinner avec la liste complète des villes
            db.beginTransaction();
            atAgences = db.agenceDao().loadAgenceByCP(codePostal[0] + "%");
            db.endTransaction();


            return atAgences;
        }

        @Override
        protected void onPostExecute(List<Agence> agenceList) {

            Log.i("TAG_MESSAGE", "Nombre d'agence : " + agenceList.size());

            Message msg = new Message();
            msg.what = 2;
            msg.obj = agenceList;
            handler.handleMessage(msg);

            super.onPostExecute(agenceList);
        }

    }
}
