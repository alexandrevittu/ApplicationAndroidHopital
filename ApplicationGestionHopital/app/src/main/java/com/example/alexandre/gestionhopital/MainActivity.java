package com.example.alexandre.gestionhopital;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnafficherpatient = (Button)findViewById(R.id.btnafficherpatient);

        btnafficherpatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent afficherpatientIntent = new Intent(MainActivity.this,afficherpatientActivity.class);
                startActivity(afficherpatientIntent);
            }
        });
    }
}
