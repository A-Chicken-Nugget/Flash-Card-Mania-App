package com.csc331.flash_card_mania_app.pages;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.csc331.flash_card_mania_app.Library;
import com.csc331.flash_card_mania_app.Main;
import com.csc331.flash_card_mania_app.R;

public class MainMenu extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Main.createNewMainInstance(this.getApplicationContext());

        final MainMenu instance = this;

        setContentView(R.layout.main_menu);
        getSupportActionBar().hide();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        findViewById(R.id.my_libraries_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(instance, MyLibraries.class);

                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
        findViewById(R.id.learn_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(instance, SelectLibrary.class);

                intent.putExtra("testType",false);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
        findViewById(R.id.test_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(instance, SelectLibrary.class);

                intent.putExtra("testType",true);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
        findViewById(R.id.about_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(instance, About.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
        findViewById(R.id.statistics_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(instance, MyStatistics.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
    }
}
