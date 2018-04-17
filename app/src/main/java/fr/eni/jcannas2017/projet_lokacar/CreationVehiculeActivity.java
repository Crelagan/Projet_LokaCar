package fr.eni.jcannas2017.projet_lokacar;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import fr.eni.jcannas2017.projet_lokacar.fragment.CreationFragment;

public class CreationVehiculeActivity extends AppCompatActivity implements CreationFragment.OnFragmentInteractionListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_vehicule);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
