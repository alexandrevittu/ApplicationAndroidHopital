package com.example.alexandre.gestionhopital;

import android.content.Intent;
import android.os.TestLooperManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class detailsejourActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailsejour);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Intent i = getIntent();
        /*RadioButton radiobtnoui = (RadioButton)(findViewById(R.id.radiobtnoui));
        RadioButton radiobtnnon = (RadioButton)(findViewById(R.id.radiobtnnon));
        radiobtnnon.setId(0);
        radiobtnoui.setId(1);
        final RadioGroup rg = (RadioGroup)(findViewById(R.id.radiogroup));
        final TextView test = (TextView)findViewById(R.id.textViewtest);*/
        final Sejours lesejour = (Sejours)i.getSerializableExtra("sejour");
        TextView txtnom = (TextView)findViewById(R.id.txtnomsejours);
        TextView txtprenom = (TextView)findViewById(R.id.txtprenomsejours);
        TextView txtdatedebut = (TextView)findViewById(R.id.txtdatedebutsejours);
        TextView txtdatefin = (TextView)findViewById(R.id.txtdatefinsejours);
        TextView txtnumlit = (TextView)findViewById(R.id.txtnumlit);
        TextView txtnumchambre =(TextView)findViewById(R.id.txtchambre);

        txtnom.setText("Nom : "+lesejour.getNom());
        txtprenom.setText("Prenom : "+lesejour.getPrenom());
        txtdatedebut.setText("Date debut de sejours : "+sdf.format(lesejour.getDatedebut()));
        txtdatefin.setText("Date de fin de sejours : "+sdf.format(lesejour.getDatefin()));
        txtnumchambre.setText("Numero de la chambre : "+lesejour.getNumchambre());
        txtnumlit.setText("Numero du lit : "+lesejour.getNumlit());



        Button btntest = (Button) (findViewById(R.id.btnValider));
        btntest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


}
