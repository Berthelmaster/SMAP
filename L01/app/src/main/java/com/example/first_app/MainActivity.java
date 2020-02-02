package com.example.first_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView text;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        text = findViewById(R.id.textcounter);
        button = findViewById(R.id.buttoncounter);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){

                String stringNumber = text.getText().toString();
                Integer IntegerNumber = Integer.parseInt(stringNumber);
                IntegerNumber++;
                text.setText(IntegerNumber.toString());
            }
        });
    }
}
