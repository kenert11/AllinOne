package com.example.kenert.allinoneapp;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void calculatorButtonClicked(View view){
        Intent calcu = new Intent(this, Calculator.class);
        startActivity(calcu);
    }
    public void flashlightButtonClicked(View view){
        Intent flash = new Intent(this, Flashlight.class);
        startActivity(flash);
    }
    public void notepadButtonClicked(View view){
        Intent note = new Intent(this, Notepad.class);
        startActivity(note);
    }

}
