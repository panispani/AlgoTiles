package com.example.user.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Start extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_layout);
    }
    public void LevelStart(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void createPuzzle(View v){
        Intent intent = new Intent(this, Createlevel.class);
        startActivity(intent);
    }

}
