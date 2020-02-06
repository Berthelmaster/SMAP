package com.example.l02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button clickButton;
    Button goButton;
    TextView textView;
    public int counter = 0;

    //Mandatory methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null)
        {
            counter = savedInstanceState.getInt("value");
        }

        clickButton = findViewById(R.id.clickButton);
        goButton = findViewById(R.id.ButtonGo);
        textView = findViewById(R.id.textView);



        textView.setText(String.valueOf(counter));

        clickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(String.valueOf(++counter));
            }
        });

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Activity2.class);
                startActivity(intent);

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("onStart", "onStart() Called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("onResume", "onResume() Called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("onPause", "onPause() Called");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.d("onRestart", "onRestart() Called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("onStop", "onStop() Called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d("onDestroyed", "onDestroyed() Called");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("State", "State Called");
        outState.putInt("value", counter);
    }
}
