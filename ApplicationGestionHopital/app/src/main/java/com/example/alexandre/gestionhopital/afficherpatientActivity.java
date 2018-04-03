package com.example.alexandre.gestionhopital;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class afficherpatientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afficherpatient);


        final ListView affichage = (ListView) findViewById(R.id.lespatients);
        String leResultat;
        TacheAsync maTache = new TacheAsync();
        maTache.execute();
        JSONObject lobjet;
        String aAfficher = "";
        JSONArray tous;
        ArrayList<Patient> lespatients = new ArrayList<Patient>();
        Patient lepatient = null;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            leResultat = maTache.get();
            tous = new JSONArray(leResultat);
            for (int i = 0; i < tous.length(); i++) {
                lobjet = tous.getJSONObject(i);
                lepatient = new Patient(lobjet.getInt("id"), lobjet.getInt("numerosecu"), lobjet.getString("nom"), lobjet.getString("prenom"), sdf.parse(lobjet.getString("datenaiss")), lobjet.getInt("codepostal"), lobjet.getString("mail"), lobjet.getInt("assurer"));
                aAfficher += "\n" + lepatient.toString();
                lespatients.add(lepatient);

            }
            leResultat = aAfficher;
        } catch (Exception e) {
            leResultat = "erreur";
        }
        ArrayAdapter<Patient> dataAdapter;
        dataAdapter = new ArrayAdapter<Patient>(this,android.R.layout.simple_list_item_1,lespatients);
        affichage.setAdapter(dataAdapter);
        affichage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Patient selected = (Patient) (affichage.getItemAtPosition(i));
                //Toast toast = Toast.makeText(getApplication().getBaseContext(), "nice"+selected.getPrenom()+selected.getCodepostal(), Toast.LENGTH_SHORT);
                //toast.show();
                Intent afficherlepatient = new Intent(afficherpatientActivity.this,afficherlepatientActivity.class);
                afficherlepatient.putExtra("patient",selected);
                startActivity(afficherlepatient);
                finish();

            }
        });
    }

    public class TacheAsync extends AsyncTask<String, Integer, String> {

        protected String doInBackground(String... arg0) {
            String aRetourner = "";
            URL url;
            StringBuffer lebuffer = new StringBuffer(aRetourner);


            try {
                url = new URL("http://192.168.1.13/serviceweb/rest.php?method=GET");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                InputStream lefluxentree = new BufferedInputStream(conn.getInputStream());
                BufferedReader lelecteur = new BufferedReader(new InputStreamReader(lefluxentree));
                String laLigne = lelecteur.readLine();
                while (laLigne != null) {
                    lebuffer.append(laLigne);
                    //lebuffer.append("/n");
                    laLigne = lelecteur.readLine();

                }
                aRetourner = lebuffer.toString();
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

