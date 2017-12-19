package com.example.alexandre.gestionhopital;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;

public class modifierpatientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifierpatient);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Intent i = getIntent();
        final Patient lepatient = (Patient)i.getSerializableExtra("patient");
        EditText editnom = (EditText)findViewById(R.id.txtnommodif);
        EditText editprenom = (EditText)findViewById(R.id.txtprenommodif);
        EditText editnumsecu = (EditText)findViewById(R.id.txtnumsecumodif);
        EditText editdatenaiss = (EditText)findViewById(R.id.txtdatemodif);
        EditText editmail = (EditText)findViewById(R.id.txtmailmodif);
        EditText editcodepostal = (EditText)findViewById(R.id.txtcodepostalmodif);
        EditText editassurer = (EditText)findViewById(R.id.txtassurermodif);

        Date date = lepatient.getDatenaiss();
        String datestring = sdf.format(date);

        editnom.setText(lepatient.getNom());
        editprenom.setText(lepatient.getPrenom());
        editnumsecu.setText(String.valueOf(lepatient.getNumsecu()));
        editcodepostal.setText(String.valueOf(lepatient.getCodepostal()));
        editdatenaiss.setText(datestring);
        editmail.setText(lepatient.getMail());
        editassurer.setText(String.valueOf(lepatient.getAssurer()));


    }
}
