package com.example.alexandre.gestionhopital;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
import java.util.Date;

public class affichersejoursActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichersejours);


        final ListView affichage = (ListView) findViewById(R.id.lessejours);
        TacheAsync matache = new TacheAsync();
        String leResultat;
        matache.execute();
        String aAfficher = "";
        JSONObject lobjet;
        JSONArray tous;
        ArrayList<Sejours> lessejours = new ArrayList<>();
        Sejours lesejours = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateactuel = new Date();


        try{
            leResultat = matache.get();
            tous = new JSONArray(leResultat);
            for(int i = 0; i < tous.length(); i++)
            {
                lobjet = tous.getJSONObject(i);
                lesejours = new Sejours(lobjet.getInt("id"), sdf.parse(lobjet.getString("datedebut")),sdf.parse(lobjet.getString("datefin")),lobjet.getString("nom"),lobjet.getString("prenom"),lobjet.getInt("id"),lobjet.getInt("numlit"),0,0);
                //if (dateactuel.before(lesejours.getDatedebut()) || dateactuel.equals(lesejours.getDatedebut())) {
                        //aAfficher += "\n" + lesejours.toString();
                        lessejours.add(lesejours);
                //}

            }
            leResultat = aAfficher;
        }catch (Exception e){
            leResultat = "erreur";
        }

        ArrayAdapter<Sejours> dataAdapter;
        dataAdapter = new ArrayAdapter<Sejours>(this,android.R.layout.simple_list_item_1,lessejours);
        affichage.setAdapter(dataAdapter);
        affichage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Sejours selected = (Sejours) (affichage.getItemAtPosition(i));
                Intent detailsejours = new Intent(affichersejoursActivity.this,detailsejourActivity.class);
                detailsejours.putExtra("sejour",selected);
                startActivity(detailsejours);


            }
        });
    }

    public class TacheAsync extends AsyncTask<String, Integer, String> {

        protected String doInBackground(String... arg0) {
            String aRetourner = "";
            URL url;
            StringBuffer lebuffer = new StringBuffer(aRetourner);


            try {
                url = new URL("http://10.0.2.2/serviceweb/sejours.php");
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
