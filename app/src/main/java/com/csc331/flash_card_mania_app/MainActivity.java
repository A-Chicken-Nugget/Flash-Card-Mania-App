package com.csc331.flash_card_mania_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.csc331.flash_card_mania_app.pages.MainMenu;

import java.io.Serializable;
import java.util.ArrayList;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent intent = new Intent(this, MainMenu.class);

        startActivity(intent);
        overridePendingTransition(0,0);

        requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,READ_EXTERNAL_STORAGE}, 1);

        Main.getInstance().loadData(this.getApplicationContext());
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
