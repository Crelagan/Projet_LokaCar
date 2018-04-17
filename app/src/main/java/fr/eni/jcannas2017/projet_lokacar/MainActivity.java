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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import fr.eni.jcannas2017.projet_lokacar.beans.Agence;
import fr.eni.jcannas2017.projet_lokacar.dao.Database;

public class MainActivity extends AppCompatActivity {

    private Database db;
    private final String DATABASE_NAME ="LOKACAR.db";
    private Spinner spinner;
    private TextView tvCodePostal;

    private ArrayAdapter<String> adapter;
    //private List<Agence> agences;


    private MarqueHandle handler = new MarqueHandle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


            db = Room.databaseBuilder(getApplicationContext(),
                    Database.class, "LOKACAR.db").build();

        // Alimenter le spinner avec le nom des villes des agences
        spinner = (Spinner) findViewById(R.id.spinner) ;
        //agences = new ArrayList<Agence>();

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


        Button btn = (Button) findViewById((R.id.btn));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });


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



    /**
     * Handler de communication entre l'activité et AccesMarqueTask
     */
    private class MarqueHandle extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1 :

                    break;

                case 2 :
                    miseAJourSpinner((List<Agence>) msg.obj);
                    break;
            }
        }
    }

    private void miseAJourSpinner(List<Agence> al) {

        List<String> villes;
        villes = new ArrayList<String>();

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
