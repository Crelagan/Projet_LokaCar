package fr.eni.jcannas2017.projet_lokacar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class FlotteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flotte);


        Intent intent = getIntent();

        TextView tv = (TextView) findViewById(R.id.tv);
        int str = intent.getIntExtra("idAgence", 0);
        tv.setText("De l'agence numéro : " + String.valueOf(str));

    }
}
