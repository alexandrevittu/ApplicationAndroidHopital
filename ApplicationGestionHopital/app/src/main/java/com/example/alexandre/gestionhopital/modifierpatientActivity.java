package com.example.alexandre.gestionhopital;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class modifierpatientActivity extends AppCompatActivity {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifierpatient);
        Intent i = getIntent();
        final Patient lepatient = (Patient)i.getSerializableExtra("patient");
        final EditText editnom = (EditText)findViewById(R.id.txtnommodif);
        final EditText editprenom = (EditText)findViewById(R.id.txtprenommodif);
        final EditText editnumsecu = (EditText)findViewById(R.id.txtnumsecumodif);
        final EditText editdatenaiss = (EditText)findViewById(R.id.txtdatemodif);
        final EditText editmail = (EditText)findViewById(R.id.txtmailmodif);
        final EditText editcodepostal = (EditText)findViewById(R.id.txtcodepostalmodif);
        final EditText editassurer = (EditText)findViewById(R.id.txtassurermodif);

        Date date = lepatient.getDatenaiss();
        String datestring = sdf.format(date);

        editnom.setText(lepatient.getNom());
        editprenom.setText(lepatient.getPrenom());
        editnumsecu.setText(String.valueOf(lepatient.getNumsecu()));
        editcodepostal.setText(String.valueOf(lepatient.getCodepostal()));
        editdatenaiss.setText(datestring);
        editmail.setText(lepatient.getMail());
        editassurer.setText(String.valueOf(lepatient.getAssurer()));

        Button btnvalidermodif = (Button)findViewById(R.id.btnvalidermodif);
        btnvalidermodif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TacheAsync maTache = new TacheAsync();
                String nom = editnom.getText().toString();
                String prenom = editprenom.getText().toString();
                String mail = editmail.getText().toString();
                int id = lepatient.getId();
                int codepostal = Integer.parseInt(editcodepostal.getText().toString());
                int numsecu = Integer.parseInt(editnumsecu.getText().toString());
                int assurer = Integer.parseInt(editassurer.getText().toString());
                Date date=new Date();
                try {
                    date = sdf.parse(editdatenaiss.getText().toString());
                }catch (ParseException e){
                }
                Patient patient1= new  Patient(id,numsecu,nom,prenom,date,codepostal,mail,assurer);
                maTache.execute(patient1);
                Toast toast = Toast.makeText(getApplication().getBaseContext(), "le patient a ete modifier", Toast.LENGTH_SHORT);
                toast.show();
                finish();
            }
        });
    }

    public class TacheAsync extends AsyncTask<Patient, Integer, String> {

        protected String doInBackground(Patient... arg0) {
            String aRetourner ="";
            //aRetourner=arg0[0].getNom();
            StringBuffer leBuffer = new StringBuffer(aRetourner);
            URL url;
            try {

                url = new URL("http://10.0.2.2/serviceweb/rest.php");
                HttpURLConnection conn =(HttpURLConnection) url.openConnection();
                conn.setRequestMethod("PUT");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                Uri.Builder builder =new Uri.Builder();
                builder.appendQueryParameter("id",String.valueOf(arg0[0].getId()));
                builder.appendQueryParameter("numerosecu",String.valueOf(arg0[0].getNumsecu()));
                builder.appendQueryParameter("nom",arg0[0].getNom());
                builder.appendQueryParameter("prenom",arg0[0].getPrenom());
                builder.appendQueryParameter("datenaiss",(sdf.format(arg0[0].getDatenaiss())));

                builder.appendQueryParameter("codepostal",String.valueOf(arg0[0].getCodepostal()));
                builder.appendQueryParameter("mail",arg0[0].getMail());
                builder.appendQueryParameter("assurer",String.valueOf(arg0[0].getAssurer()));
                String query = builder.build().getEncodedQuery();
                OutputStream outputPost = conn.getOutputStream();
                BufferedWriter writer =  new BufferedWriter(new OutputStreamWriter(outputPost,"UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                outputPost.close();
                conn.connect();
                int codeResponse= conn.getResponseCode();

                if(codeResponse == HttpURLConnection.HTTP_OK){
                    InputStream lefluxentree = new BufferedInputStream(conn.getInputStream());
                    BufferedReader lelecteur = new BufferedReader(new InputStreamReader(lefluxentree));
                    String laligne=lelecteur.readLine();
                    while(laligne!=null){
                        leBuffer.append(laligne);
                        leBuffer.append("\n");
                        laligne=lelecteur.readLine();
                    }
                    aRetourner = leBuffer.toString();
                }
                else{
                    aRetourner="erreur";
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return aRetourner;

        }

        protected void onProgressUpdate(Integer... pAvancement) {
        }

    }
}
