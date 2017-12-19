package com.example.alexandre.gestionhopital;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;


public class afficherlepatientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afficherlepatient);
        Intent i = getIntent();
        final Patient lepatient = (Patient)i.getSerializableExtra("patient");
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
        lepatient.getId();
        Button btnsupp = (Button)findViewById(R.id.btnsupppatient);
        btnsupp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    TacheAsync matache = new TacheAsync();
                    matache.execute(lepatient);

            }
        });



    }


    public class TacheAsync extends AsyncTask<Patient, Integer, String> {

        protected String doInBackground(Patient... arg0) {
            String aRetourner = "";
            URL url;
            StringBuffer leBuffer = new StringBuffer(aRetourner);


            try {
                url = new URL("http://10.0.2.2/serviceweb/rest.php?");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setDoOutput(true);
                //conn.setRequestProperty("patient","");
                conn.setRequestMethod("DELETE");
                conn.connect();
                Uri.Builder builder =new Uri.Builder();
                builder.appendQueryParameter("id",String.valueOf(arg0[0].getId()));
                String query = builder.build().getEncodedQuery();
                OutputStream outputPost = conn.getOutputStream();
                BufferedWriter writer =  new BufferedWriter(new OutputStreamWriter(outputPost,"UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                outputPost.close();
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


            } catch (Exception e) {
                e.printStackTrace();
                aRetourner = "erreur";
            }
            return aRetourner;


        }

        protected void onProgressUpdate(Integer... pAvancement) {
        }

    }
}
