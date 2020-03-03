package com.csc331.flash_card_mania_app.pages;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.csc331.flash_card_mania_app.Library;
import com.csc331.flash_card_mania_app.Main;
import com.csc331.flash_card_mania_app.R;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ManageLibrary extends AppCompatActivity {
    private Main mainInstance = Main.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ManageLibrary instance = this;
        //If they are editing a library, get the given id
        final String id = getIntent().getStringExtra("libraryId");
        //If the id isn't null, get the library from its passed in id
        final Library library = (id != null ? mainInstance.getLibraryById(UUID.fromString(id)) : null);

        //Set this pages view layout
        setContentView(R.layout.manage_library);
        //Remove the default app header
        getSupportActionBar().hide();
        //Remove the device notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Set views based on whether or not the user is creating/editing a library
        if (library == null) {
            ((TextView)findViewById(R.id.manageLibrary_page_title)).setText("Create Library");
            ((Button)findViewById(R.id.manageLibrary_updateButton)).setText("Create");
        } else {
            ((TextView)findViewById(R.id.manageLibrary_page_title)).setText(library.getName());
            ((EditText)findViewById(R.id.manageLibrary_question1InputField)).setText(library.getName());
            ((EditText)findViewById(R.id.manageLibrary_question2InputField)).setText(library.getSubject());
            ((EditText)findViewById(R.id.manageLibrary_question3InputField)).setText(library.getDescription());
            ((Button)findViewById(R.id.manageLibrary_updateButton)).setText("Update");
        }
        ((TextView)findViewById(R.id.manageLibrary_page_title)).setGravity(Gravity.CENTER);

        //Handle when the back button is clicked
        findViewById(R.id.manageLibrary_back_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(instance, MyLibraries.class);

                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
        //Handle when the update button is clicked
        findViewById(R.id.manageLibrary_updateButton).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Get the values of the input fields
                final String question1 = ((EditText)findViewById(R.id.manageLibrary_question1InputField)).getText().toString();
                final String question2 = ((EditText)findViewById(R.id.manageLibrary_question2InputField)).getText().toString();
                final String question3 = ((EditText)findViewById(R.id.manageLibrary_question3InputField)).getText().toString();

                //Check if the fields all have values
                if (!question1.equals("") && !question2.equals("") && !question3.equals("")) {
                    HashMap<UUID, Library> libraries = mainInstance.getLibraries();
                    boolean exists = false;

                    //Check if the name of the library is already in use
                    for (Map.Entry<UUID, Library> entry : libraries.entrySet()) {
                        Library libraryy = entry.getValue();

                        //TODO redo at later date. Make it check for a duplicate name if user is editing
                        if (library == null) {
                            if (libraryy.getName().toLowerCase().equals(question1.toLowerCase())) {
                                exists = true;
                                break;
                            }
                        }
                    }
                    if (!exists) {
                        if (library == null) {
                            Library library = new Library(question1,question2,question3);

                            mainInstance.getLibraries().put(library.getID(),library);
                            Toast.makeText(instance, question1 + " has been created", Toast.LENGTH_SHORT).show();
                        } else {
                            library.setName(question1);
                            library.setSubject(question2);
                            library.setDescription(question3);
                            Toast.makeText(instance, "Successfully updated library", Toast.LENGTH_SHORT).show();
                        }
                        startActivity(new Intent(instance, MyLibraries.class));
                        overridePendingTransition(0,0);
                    } else {
                        AlertDialog alertDialog = new AlertDialog.Builder(instance).create();
                        alertDialog.setTitle("Create?");
                        alertDialog.setMessage("A library with the name " + question1 + " already exists. Do you still want to create this new library?");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (library == null) {
                                    Library library = new Library(question1,question2,question3);

                                    mainInstance.getLibraries().put(library.getID(),library);
                                    Toast.makeText(instance, question1 + " has been created", Toast.LENGTH_SHORT).show();
                                }
                                startActivity(new Intent(instance, MyLibraries.class));
                                overridePendingTransition(0,0);
                            }
                        });
                        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alertDialog.show();
                    }
                } else {
                    Toast.makeText(instance, "Please fill out all the fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
