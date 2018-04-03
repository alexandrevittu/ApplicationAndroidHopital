package com.example.alexandre.gestionhopital;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ajouterpatientActivity extends AppCompatActivity {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private Calendar calendar;
    private int year, month, day;
    private EditText txtdatenaiss;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouterpatient);
        Locale locale = new Locale("FR");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getApplicationContext().getResources().updateConfiguration(config, null);


        final EditText txtnom = (EditText)findViewById(R.id.edittxtnom);
        final EditText txtprenom = (EditText)findViewById(R.id.edittxtprenom);
        final EditText txtnumsecu = (EditText)findViewById(R.id.edittxtnumsecu);
        txtdatenaiss = (EditText)findViewById(R.id.edittxtdatenaiss);
        final EditText txtcodepostal = (EditText)findViewById(R.id.edittxtcodepostal);
        final EditText txtmail = (EditText)findViewById(R.id.edittxtmail);
        //final EditText txtassurer = (EditText)findViewById(R.id.edittxtassurer);
        Button btnvalider = (Button)findViewById(R.id.btnvalider);
        final RadioButton radiobtnoui = (RadioButton)(findViewById(R.id.radiobtnoui));
        final RadioButton radiobtnnon = (RadioButton)(findViewById(R.id.radiobtnnon));
        radiobtnoui.setId(1);
        radiobtnnon.setId(0);
        final RadioGroup rg =(RadioGroup)(findViewById(R.id.radiogroup));
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);



        btnvalider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(txtnom.getText().toString().equals("") || txtprenom.getText().toString().equals("") || txtmail.getText().toString().equals("") || rg.getCheckedRadioButtonId()==-1 || txtcodepostal.getText().toString().equals("") || txtdatenaiss.getText().toString().equals("") || txtnumsecu.getText().toString().equals(""))
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
                    if(rg.getCheckedRadioButtonId()==-1){
                        radiobtnoui.setError("requit !");
                        radiobtnnon.setError("requit !");
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
                    //int assurer = Integer.parseInt(txtassurer.getText().toString());
                    Date date = new Date();
                    try {
                        date = sdf.parse(txtdatenaiss.getText().toString());
                    } catch (ParseException e) {
                    }
                    TacheAsync maTache = new TacheAsync();

                    Patient patient1 = new Patient(20, numsecu, nom, prenom, date, codepostal, mail, rg.getCheckedRadioButtonId());
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

                url = new URL("http://192.168.1.13/serviceweb/rest.php");
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


    public void setDate(View view) {
        showDialog(999);
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        if(id==999)
        {
            return new DatePickerDialog(this,myDateListener,year, month, day);
        }
        return null;
    }
    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2+1, arg3);
                }
            };
    private void showDate(int year, int month, int day) {
        txtdatenaiss.setText(new StringBuilder().append(year).append("-")
                .append(month).append("-").append(day));
    }
}
