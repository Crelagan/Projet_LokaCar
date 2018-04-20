package fr.eni.jcannas2017.projet_lokacar;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import fr.eni.jcannas2017.projet_lokacar.beans.Agence;
import fr.eni.jcannas2017.projet_lokacar.beans.Utils;
import fr.eni.jcannas2017.projet_lokacar.beans.Vehicule;
import fr.eni.jcannas2017.projet_lokacar.dao.Database;
import fr.eni.jcannas2017.projet_lokacar.fragment.CreationFragment;

public class CreationVehiculeActivity extends AppCompatActivity implements CreationFragment.OnFragmentInteractionListener
{

    private Agence agence;
    private Database db;
    private EditText etMarque, etModele, etPuissance, etConso, etPrix, etImmat;
    private Spinner spCarburant, spType, spBoiteVitesse;
    private AppCompatButton btnEnregistrerVehic;

    private MarqueHandle handler = new MarqueHandle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_vehicule);

        db = Room.databaseBuilder(getApplicationContext(),
                Database.class, "LOKACAR.db").build();

        Intent intent = getIntent();
        agence = intent.getParcelableExtra("agence");

        etMarque = (EditText) findViewById(R.id.etMarque);
        etModele = (EditText) findViewById(R.id.etModele);
        etPuissance = (EditText) findViewById(R.id.etPuissance);
        spBoiteVitesse = (Spinner) findViewById(R.id.spBV);
        etConso = (EditText) findViewById(R.id.etConso);
        spCarburant = (Spinner) findViewById(R.id.spCarburant);
        etImmat = (EditText) findViewById(R.id.etImmat);
        etPrix = (EditText) findViewById(R.id.etPrix);
        spType = (Spinner) findViewById(R.id.spType);
        btnEnregistrerVehic = (AppCompatButton) findViewById(R.id.btn);

        init();


    }

    private void init() {

        ArrayAdapter<Utils.enumCarburant> adCarburant = new ArrayAdapter<Utils.enumCarburant>(CreationVehiculeActivity.this,
                R.layout.support_simple_spinner_dropdown_item, Utils.enumCarburant.values());
        spCarburant.setAdapter(adCarburant);

        ArrayAdapter<Utils.enumBoiteVitesse> adBV = new ArrayAdapter<Utils.enumBoiteVitesse>(CreationVehiculeActivity.this,
                R.layout.support_simple_spinner_dropdown_item, Utils.enumBoiteVitesse.values());
        spBoiteVitesse.setAdapter(adBV);

        ArrayAdapter<Utils.enumType> adType = new ArrayAdapter<Utils.enumType>(CreationVehiculeActivity.this,
                R.layout.support_simple_spinner_dropdown_item, Utils.enumType.values());
        spType.setAdapter(adType);
    }

    @Override
    protected void onStart() {
        super.onStart();

        btnEnregistrerVehic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vehicule veh = new Vehicule();

                if (!etMarque.getText().toString().equals("") && !etModele.getText().toString().equals("") &&
                        !etPuissance.getText().toString().equals("") && !spBoiteVitesse.getSelectedItem().toString().equals("") &&
                        !etConso.getText().toString().equals("") && !spCarburant.getSelectedItem().toString().equals("") &&
                        !etImmat.getText().toString().equals("") && !spType.getSelectedItem().toString().equals("")) {
                    veh.setMarque(etMarque.getText().toString());
                    veh.setModele(etModele.getText().toString());
                    veh.setPuissanceAdmin(Integer.parseInt(etPuissance.getText().toString()));
                    veh.setBoiteVitesse(spBoiteVitesse.getSelectedItem().toString());
                    veh.setConsomation(Double.parseDouble(etConso.getText().toString()));
                    veh.setCarburant(spCarburant.getSelectedItem().toString());
                    veh.setImmatriculation(etImmat.getText().toString());
                    veh.setType(spType.getSelectedItem().toString());
                    veh.setEtatLoc(0);
                    veh.setAgenceId(agence.getId());

                    new CreationVehicule().execute(veh);
                }
                else {
                    Toast.makeText(CreationVehiculeActivity.this,"Veuiller renseigner tous les champs !!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public class CreationVehicule extends AsyncTask<Vehicule, Void, Void>{


        @Override
        protected Void doInBackground(Vehicule... vehicules) {

            List<Vehicule> lst = db.vehiculeDao().findVehiculeByAgence(agence.getId());
            Log.i("TAG", "Nombre de vehicule en base avant : " + lst.size());

            db.vehiculeDao().insertAllVehicules(vehicules);

            lst = db.vehiculeDao().findVehiculeByAgence(agence.getId());
            Log.i("TAG", "Nombre de vehicule en base avant : " + lst.size());

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Message msg = new Message();
            msg.what = 1;
            handler.handleMessage(msg);

        }
    }

    /**
     * Handler de communication entre l'activité et AccesMarqueTask
     */
    private class MarqueHandle extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            FinCreationVehicule();
        }
    }

    private void FinCreationVehicule() {

        Intent intent = new Intent(CreationVehiculeActivity.this, FlotteActivity.class);
        intent.putExtra("agence", agence);
        startActivity(intent);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
