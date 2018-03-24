package com.example.alexandre.gestionhopital;

import android.os.TestLooperManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class detailsejourActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailsejour);

        RadioButton radiobtnoui = (RadioButton)(findViewById(R.id.radiobtnoui));
        RadioButton radiobtnnon = (RadioButton)(findViewById(R.id.radiobtnnon));
        radiobtnnon.setId(0);
        radiobtnoui.setId(1);
        final RadioGroup rg = (RadioGroup)(findViewById(R.id.radiogroup));
        final TextView test = (TextView)findViewById(R.id.textViewtest);


        Button btntest = (Button) (findViewById(R.id.btntest));
        btntest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                test.setText(String.valueOf(rg.getCheckedRadioButtonId()));
            }
        });
    }
}
