package com.example.l03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button EditText;
    Button Picker;
    Button Sliders;
    int number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText = findViewById(R.id.EditTextButton);
        Picker = findViewById(R.id.PickerButton);
        Sliders = findViewById(R.id.SlidersButton);

        EditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent EditTextIntent = new Intent(MainActivity.this, EditTextActivity.class);
                startActivity(EditTextIntent);
            }
        });
        Picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent EditTextIntent = new Intent(MainActivity.this, PickerActivity.class);
                startActivity(EditTextIntent);
            }
        });
        Sliders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent EditTextIntent = new Intent(MainActivity.this, SlidersActivity.class);
                startActivity(EditTextIntent);
            }
        });
    }
}
