package com.example.alexandre.gestionhopital;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.TestLooperManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
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
import java.text.SimpleDateFormat;

public class detailsejourActivity extends AppCompatActivity {
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailsejour);

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
        final TextView txtnumlit = (TextView)findViewById(R.id.txtnumlit);
        TextView txtnumchambre =(TextView)findViewById(R.id.txtchambre);
        final RadioButton RadioValiderentreeOui = (RadioButton)findViewById(R.id.radiobtnouivaliderentree);
        final RadioButton RadioValiderentreenon = (RadioButton)findViewById(R.id.radiobtnnonvaliderentree);
        RadioValiderentreenon.setId(0);
        RadioValiderentreeOui.setId(1);
        final RadioGroup rg =(RadioGroup) findViewById(R.id.radiogroupvaliderentree);
        txtnom.setText("Nom : "+lesejour.getNom());
        txtprenom.setText("Prenom : "+lesejour.getPrenom());
        txtdatedebut.setText("Date debut de sejours : "+sdf.format(lesejour.getDatedebut()));
        txtdatefin.setText("Date de fin de sejours : "+sdf.format(lesejour.getDatefin()));
        txtnumchambre.setText("Numero de la chambre : "+lesejour.getNumchambre());
        txtnumlit.setText("Numero du lit : "+lesejour.getNumlit());

        if(lesejour.getValideRentree() == 0)
        {
        }
        txtnumlit.setText(String.valueOf(lesejour.getValideRentree()));
        txtnumchambre.setText(String.valueOf(lesejour.getId()));


        Button btntest = (Button) (findViewById(R.id.btnValider));
        btntest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    TacheAsync TacheValiderEntree = new TacheAsync();
                    Sejours SejoursValiderEntree = new Sejours(lesejour.getId(),lesejour.getDatedebut(),lesejour.getDatefin(),lesejour.getNom(),lesejour.getPrenom(),lesejour.getNumchambre(),lesejour.getNumlit(),rg.getCheckedRadioButtonId(),lesejour.getValiderSortie());
                    TacheValiderEntree.execute(SejoursValiderEntree);
                    Toast toast = Toast.makeText(getApplication().getBaseContext(), "l'arrive du patient a bien été pris en compte", Toast.LENGTH_SHORT);
                    toast.show();
                    finish();
                    txtnumlit.setText(String.valueOf(SejoursValiderEntree.getId()));



            }
        });
    }

    public class TacheAsync extends AsyncTask<Sejours, Integer, String> {

        protected String doInBackground(Sejours... arg0) {
            String aRetourner ="";
            //aRetourner=arg0[0].getNom();
            StringBuffer leBuffer = new StringBuffer(aRetourner);
            URL url;
            try {

                url = new URL("http://10.0.2.2/serviceweb/sejours.php");
                HttpURLConnection conn =(HttpURLConnection) url.openConnection();
                conn.setRequestMethod("PUT");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                Uri.Builder builder =new Uri.Builder();
                builder.appendQueryParameter("idsejours",String.valueOf(arg0[0].getId()));
                builder.appendQueryParameter("Datedebut",sdf.format(arg0[0].getDatedebut()));
                builder.appendQueryParameter("Datefin",sdf.format(arg0[0].getDatefin()));
                builder.appendQueryParameter("nom",String.valueOf(arg0[0].getNom()));
                builder.appendQueryParameter("prenom",String.valueOf(arg0[0].getPrenom()));
                builder.appendQueryParameter("numchambre",String.valueOf(arg0[0].getNumchambre()));
                builder.appendQueryParameter("numlit",String.valueOf(arg0[0].getNumlit()));
                builder.appendQueryParameter("Validerentree",String.valueOf(arg0[0].getValideRentree()));
                builder.appendQueryParameter("Validesortie",String.valueOf(arg0[0].getValiderSortie()));



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
