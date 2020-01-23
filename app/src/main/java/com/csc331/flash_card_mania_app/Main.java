package com.csc331.flash_card_mania_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); 

        final Main instance = this;

        //
        // Note: This is all temporary just so you guys can get a feel for how it works
        //

        //This is the best way so far i've found to log debug messages
        Log.d("cDebug","Main has been ran");

        //Sets the default initial view
        setContentView(R.layout.main);

        //Add a listener to handle when the button is clicked
        findViewById(R.id.main_continue_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Create to tell android what we activity we want to start
                Intent intent = new Intent(instance, Page2.class);

                //Start the activity
                startActivity(intent);
            }
        });
    }
}
