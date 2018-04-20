package fr.eni.jcannas2017.projet_lokacar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import fr.eni.jcannas2017.projet_lokacar.beans.Agence;
import fr.eni.jcannas2017.projet_lokacar.beans.Client;
import fr.eni.jcannas2017.projet_lokacar.beans.Location;
import fr.eni.jcannas2017.projet_lokacar.beans.Vehicule;
import fr.eni.jcannas2017.projet_lokacar.dao.ClientDao;
import fr.eni.jcannas2017.projet_lokacar.dao.Database;

public class LocationActivity extends AppCompatActivity {

    private long CONST_DURATION_OF_DAY = 1000l * 60 * 60 * 24;

    private int mYear, mMonth, mDay, mHour, mMinute;
    Button btnDateDebut, btnDateFin, btnSearch, btnValid;
    EditText dateDebut, dateFin, marque, modele, duree, prix, nom, prenom, adresse, telephone, codePostal;
    Vehicule vehicule;
    Calendar debut, fin;
    private Agence agence;
    Database db;
    Client client;

    private MarqueHandle handler = new MarqueHandle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        db = Room.databaseBuilder(getApplicationContext(),
                Database.class, "LOKACAR.db").build();

        Intent intent = getIntent();
        vehicule = intent.getParcelableExtra("vehicule");
        agence = intent.getParcelableExtra("agence");

        btnDateDebut = (Button) findViewById(R.id.btn_dateDebut);
        dateDebut = (EditText) findViewById(R.id.etDateDebut);
        dateDebut.setEnabled(false);

        btnDateFin = (Button) findViewById(R.id.btn_dateFin);
        dateFin = (EditText) findViewById(R.id.etDateFin);
        dateFin.setEnabled(false);

        marque = (EditText) findViewById(R.id.etMarque);
        marque.setText(vehicule.getMarque());
        marque.setEnabled(false);
        modele = (EditText) findViewById(R.id.etModele);
        modele.setText(vehicule.getModele());
        modele.setEnabled(false);
        prix = (EditText) findViewById(R.id.etPrix);
        prix.setText("0");
        prix.setEnabled(false);
        duree = (EditText) findViewById(R.id.etDuree);
        duree.setText("0");
        duree.setEnabled(false);

        btnSearch = (Button) findViewById(R.id.btnRecherche);
        btnSearch.setEnabled(false);

        btnValid = (Button) findViewById(R.id.btnValid);

        nom = (EditText) findViewById(R.id.etNom);
        prenom = (EditText) findViewById(R.id.etPrenom);
        adresse = (EditText) findViewById(R.id.etAdresse);
        codePostal = (EditText) findViewById(R.id.etCodePostal);
        telephone = (EditText) findViewById(R.id.etTel);

        nom.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (nom.getText() != null){
                    btnSearch.setEnabled(true);
                }
                return false;
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("TAG", "nom : " + nom.getText().toString());
                new LoadClientList().execute(nom.getText().toString());
            }
        });

        btnValid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Location loc = new Location();
                loc.setVehiculeId(vehicule.getId());
                loc.setDepart(dateDebut.getText().toString());
                loc.setRetour(dateFin.getText().toString());
                loc.setClientId(client.getId());
                loc.setDuree(Integer.parseInt(duree.getText().toString()));

                Log.i("TAG LOG", loc.toString());
                new insertLoc().execute(loc);

                Intent intent = new Intent(LocationActivity.this, FlotteActivity.class);
                intent.putExtra("agence", agence);
                startActivity(intent);
            }
        });

        btnDateDebut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == btnDateDebut) {

                    // Get Current Date
                    Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(LocationActivity.this,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {

                                    dateDebut.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                    debut = Calendar.getInstance();
                                    debut.set(year, monthOfYear+1, dayOfMonth, 8, 0,0);

                                    if (debut != null && fin != null) {
                                        Date date1 = debut.getTime();
                                        Date date2 = fin.getTime();

                                        long diff = Math.abs(date2.getTime() - date1.getTime());
                                        long numberOfDay = diff/CONST_DURATION_OF_DAY;
                                        duree.setText(String.valueOf(numberOfDay));
                                        prix.setText(String.valueOf(Math.abs(numberOfDay*vehicule.getTarif())));
                                    }

                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            }
        });

        btnDateFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == btnDateFin) {

                    // Get Current Date
                    Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(LocationActivity.this,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    fin = Calendar.getInstance();
                                    fin.set(year, monthOfYear+1, dayOfMonth, 8, 0,0);

                                    dateFin.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                    if (debut != null && fin != null) {
                                        Date date1 = debut.getTime();
                                        Date date2 = fin.getTime();

                                        long diff = Math.abs(date2.getTime() - date1.getTime());
                                        long numberOfDay = diff/CONST_DURATION_OF_DAY;
                                        duree.setText(String.valueOf(numberOfDay));
                                        prix.setText(String.valueOf(Math.abs(numberOfDay*vehicule.getTarif())));
                                    }
                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            }
        });
    }

    public class insertLoc extends AsyncTask<Location, Void, Void> {

        @Override
        protected Void doInBackground(Location... locations) {
            Log.i("TAG", "debut");
            Log.i("TAGLOG", String.valueOf(locations[0].getId()));

            /*db.beginTransaction();*/
            db.locationDao().insertLocation(locations[0]);
            /*db.endTransaction();*/

            vehicule.setEtatLoc(1);
            db.vehiculeDao().updateVehicule(vehicule);

            Log.i("TAG", "fin");
            List<Location> listL = new ArrayList<Location>();
            listL = db.locationDao().getAllLocation();
            Log.i("TAG insert", "Nbre de loc en BDD : " + listL.size() + " Adresse BDD : " + getDatabasePath("LOKACAR.db").getAbsolutePath());
            return null;
        }
    }



    public class LoadClientList extends AsyncTask<String, Void, List<Client>> {

        List<Client> atClients;

        @Override
        protected List<Client> doInBackground(String... nom) {
            atClients = new ArrayList<Client>();
            Log.i("Tag nom", nom[0]);
            // Alimenter le spinner avec la liste complète des villes
            db.beginTransaction();
            atClients = db.clientDao().loadClientByNoms(nom[0] + "%");
            db.endTransaction();

            return atClients;
        }

        @Override
        protected void onPostExecute(List<Client> clientList) {

            Log.i("TAG_MESSAGE", "Nombre de client : " + clientList.size());

            Message msg = new Message();
            msg.what = 1;
            msg.obj = clientList;
            handler.handleMessage(msg);

            super.onPostExecute(clientList);
        }
    }

    private class MarqueHandle extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1 :
                    onCreateDialog((List<Client>) msg.obj);
                    break;
            }
        }
    }

    public void onCreateDialog(final List<Client> lstClient) {


        ArrayAdapter adapter = new ArrayAdapter(LocationActivity.this, android.R.layout.simple_list_item_1, lstClient);

        final Dialog dialog = new Dialog(LocationActivity.this);
        dialog.setContentView(R.layout.dialog_layout);
        dialog.setTitle("Liste des clients");

        ListView lstv = dialog.findViewById(R.id.list);
        lstv.setAdapter(adapter);
        lstv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                client = (Client) parent.getItemAtPosition(position);
                Log.i("TAG Client", client.toString());
                nom.setText(client.getNom());
                prenom.setText(client.getPrenom());
                telephone.setText(String.valueOf(client.getNumTel()));
                adresse.setText(client.getAdresse());
                codePostal.setText(client.getCodePostal());
                dialog.cancel();
            }
        });
        FloatingActionButton btn = dialog.findViewById(R.id.priout);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.show();


    }
}
