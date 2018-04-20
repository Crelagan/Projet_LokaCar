package fr.eni.jcannas2017.projet_lokacar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import fr.eni.jcannas2017.projet_lokacar.beans.Vehicule;
import fr.eni.jcannas2017.projet_lokacar.fragment.DetailFragment;

public class DetailVehiculeActivity extends AppCompatActivity implements DetailFragment.OnFragmentInteractionListener{

    Vehicule vehicule;
    DetailFragment detailFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_vehicule);

        detailFragment =  (DetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.modeleFragment);

        Intent intent = getIntent();
        vehicule = intent.getParcelableExtra("vehicule");


        Button btnLoc = (Button) findViewById(R.id.btnLoc);
        btnLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailVehiculeActivity.this, LocationActivity.class);
                intent.putExtra("vehicule", vehicule);
                startActivity(intent);
            }
        });

        Button btnRetour = (Button) findViewById(R.id.btnRetour);
        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailVehiculeActivity.this);
                builder.setMessage("Location retournée a ce jour ! Merci beaucoup ! ").setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       dialog.cancel();
                    }
                }).setNegativeButton("Plop", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create().show();
            }
        });

        if (vehicule.getEtatLoc() == 1) btnLoc.setEnabled(false);
        else btnRetour.setEnabled(false);

        if(detailFragment != null && detailFragment.isInLayout()){
            detailFragment.chargeDetail(vehicule);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
