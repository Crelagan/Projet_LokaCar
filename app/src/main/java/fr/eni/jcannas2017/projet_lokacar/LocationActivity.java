package fr.eni.jcannas2017.projet_lokacar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

import fr.eni.jcannas2017.projet_lokacar.beans.Vehicule;

public class LocationActivity extends AppCompatActivity {

    private int mYear, mMonth, mDay, mHour, mMinute;
    Button btnDate;
    EditText date, marque, modele, durée, prix;
    Vehicule vehicule;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        Intent intent = getIntent();
        vehicule = intent.getParcelableExtra("vehicule");

        btnDate = (Button) findViewById(R.id.btn_date);
        date = (EditText) findViewById(R.id.etDate);
        marque = (EditText) findViewById(R.id.etMarque);
        marque.setText(vehicule.getMarque());
        marque.setEnabled(false);
        modele = (EditText) findViewById(R.id.etModele);
        modele.setText(vehicule.getModele());
        modele.setEnabled(false);
        prix = (EditText) findViewById(R.id.etPrix);
        durée = (EditText) findViewById(R.id.etDuree);


        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == btnDate) {

                    // Get Current Date
                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);


                    DatePickerDialog datePickerDialog = new DatePickerDialog(LocationActivity.this,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {

                                    date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            }
        });
    }
}
