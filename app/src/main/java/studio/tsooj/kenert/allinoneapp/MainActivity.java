package studio.tsooj.kenert.allinoneapp;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.tsooj.kenert.allinoneapp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void nightmodeButtonClicked(View view){
        Intent night = new Intent(this, Nightmode.class);
        startActivity(night);
    }
    public void flashlightButtonClicked(View view){
        Intent flash = new Intent(this, Flashlight.class);
        startActivity(flash);
    }
    public void ToDoButtonClicked(View view){
        Intent todo = new Intent(this, ToDoList.class);
        startActivity(todo);
    }

}
