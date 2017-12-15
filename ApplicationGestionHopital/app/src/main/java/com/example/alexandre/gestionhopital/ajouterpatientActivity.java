package com.example.alexandre.gestionhopital;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ajouterpatientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouterpatient);

        final EditText txtnom = (EditText)findViewById(R.id.edittxtnom);
        final EditText txtprenom = (EditText)findViewById(R.id.edittxtprenom);
        final EditText txtnumsecu = (EditText)findViewById(R.id.edittxtnumsecu);
        final EditText txtdatenaiss = (EditText)findViewById(R.id.edittxtdatenaiss);
        final EditText txtcodepostal = (EditText)findViewById(R.id.edittxtcodepostal);
        final EditText txtmail = (EditText)findViewById(R.id.edittxtmail);
        final EditText txtassurer = (EditText)findViewById(R.id.edittxtassurer);
        Button btnvalider = (Button)findViewById(R.id.btnvalider);
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        btnvalider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom = txtnom.getText().toString();
                String prenom = txtprenom.getText().toString();
                String mail = txtmail.getText().toString();

                //Log.v("test",nom);
                int codepostal = Integer.parseInt(txtcodepostal.getText().toString());
                int numsecu = Integer.parseInt(txtnumsecu.getText().toString());
                int assurer = Integer.parseInt(txtassurer.getText().toString());
                try {
                    java.util.Date date = sdf.parse(txtdatenaiss.getText().toString());
                }catch (ParseException e){
                }

            }
        });
    }

}
