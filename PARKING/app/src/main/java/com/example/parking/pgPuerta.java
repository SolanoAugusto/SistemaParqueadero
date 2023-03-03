package com.example.parking;

import static com.example.parking.R.id.btnPuerta;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class pgPuerta extends AppCompatActivity {
    private ImageButton btnPuerta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pg_puerta);
       btnPuerta= (ImageButton) findViewById(R.id.btnPuerta);

       btnPuerta.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(getApplicationContext(),MainActivity.class));
           }
       });

    }
}