package com.csc331.flash_card_mania_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Page2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Page2 instance = this;

        //Set this pages layout
        setContentView(R.layout.page2);

        //Debug log
        Log.d("cDebug","Page 2 has been ran");

        //Handle when the button is clicked
        findViewById(R.id.back_to_main).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Create to tell android what we activity we want to start
                Intent intent = new Intent(instance, Main.class);

                //Start the activity
                startActivity(intent);
            }
        });
    }
}
