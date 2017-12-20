package com.example.alexandre.gestionhopital;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ajouterpatientActivity extends AppCompatActivity {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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


        btnvalider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(txtnom.getText().toString().equals("") || txtprenom.getText().toString().equals("") || txtmail.getText().toString().equals("") || txtassurer.getText().toString().equals("") || txtcodepostal.getText().toString().equals("") || txtdatenaiss.getText().toString().equals("") || txtnumsecu.getText().toString().equals(""))
                {
                    if(txtnom.getText().toString().equals("")){
                        txtnom.setError("requit !");
                    }
                    if(txtprenom.getText().toString().equals("")){
                        txtprenom.setError("requit !");
                    }
                    if(txtmail.getText().toString().equals("")){
                        txtmail.setError("requit !");
                    }
                    if(txtassurer.getText().toString().equals("")){
                        txtassurer.setError("requit !");
                    }
                    if(txtcodepostal.getText().toString().equals("")){
                        txtcodepostal.setError("requit !");
                    }
                    if(txtdatenaiss.getText().toString().equals("")){
                        txtdatenaiss.setError("requit !");
                    }
                    if(txtnumsecu.getText().toString().equals("")){
                        txtnumsecu.setError("requit !");
                    }

                }
                else {
                    String nom = txtnom.getText().toString();
                    String prenom = txtprenom.getText().toString();
                    String mail = txtmail.getText().toString();

                    //Log.v("test",nom);
                    int codepostal = Integer.parseInt(txtcodepostal.getText().toString());
                    int numsecu = Integer.parseInt(txtnumsecu.getText().toString());
                    int assurer = Integer.parseInt(txtassurer.getText().toString());
                    Date date = new Date();
                    try {
                        date = sdf.parse(txtdatenaiss.getText().toString());
                    } catch (ParseException e) {
                    }
                    TacheAsync maTache = new TacheAsync();

                    Patient patient1 = new Patient(20, numsecu, nom, prenom, date, codepostal, mail, assurer);
                    maTache.execute(patient1);
                    Toast toast = Toast.makeText(getApplication().getBaseContext(), "le patient a ete ajouter", Toast.LENGTH_SHORT);
                    toast.show();
                    finish();
                }
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
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                Uri.Builder builder =new Uri.Builder();
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
