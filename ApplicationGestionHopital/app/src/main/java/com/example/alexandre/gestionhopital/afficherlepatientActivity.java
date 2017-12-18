package com.example.alexandre.gestionhopital;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class afficherlepatientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afficherlepatient);
        Intent i = getIntent();
        Patient lepatient = (Patient)i.getSerializableExtra("patient");
        TextView txtviewnom = (TextView)findViewById(R.id.txtnompatient);
        TextView txtviewprenom = (TextView)findViewById(R.id.txtprenompatient);
        TextView txtviewcodepostal = (TextView)findViewById(R.id.txtcodepostalpatient);
        TextView txtviewdatenaiss = (TextView)findViewById(R.id.txtdatenaisspatient);
        TextView txtviewnumsecu = (TextView)findViewById(R.id.txtnumsecupatient);
        TextView txtviewassurer = (TextView)findViewById(R.id.txtassurerpatient);
        TextView txtviewmail = (TextView)findViewById(R.id.txtmailpatient);
        txtviewnom.setText("nom : "+lepatient.getNom());
        txtviewprenom.setText("prenom : "+lepatient.getPrenom());
        txtviewdatenaiss.setText("datenaiss : "+lepatient.getDatenaiss());
        /*Date date = lepatient.getDatenaiss();
        try{
            date = sdf.parse(date.toString());
        }catch (Exception e){

        };*/
        txtviewmail.setText("mail : "+lepatient.getMail());
        txtviewcodepostal.setText("code postal : "+lepatient.getCodepostal());
        txtviewnumsecu.setText("numero de securité social : "+lepatient.getNumsecu());
        txtviewassurer.setText("assurer : "+lepatient.getAssurer());
    }
}
