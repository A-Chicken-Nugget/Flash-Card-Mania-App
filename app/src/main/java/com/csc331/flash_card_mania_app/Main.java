package com.csc331.flash_card_mania_app;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import static android.content.Context.MODE_PRIVATE;

public class Main {
    private static Context context;
    private static Main instance;
    private UserProfile userProfile;
    private HashMap<UUID,Library> myLibraries;

    public Main(Context context) {
        this.context = context;

        userProfile = new UserProfile();
    }

    public void loadData() {
        //Load libraries
        try {
            FileInputStream fis = context.openFileInput("libraries.txt");

            if (fis == null) {
                myLibraries = new HashMap<UUID,Library>() {{
                    Library library = new Library("Default Library 1","Science", "Test description");
                    put(library.getID(),library);
                    library = new Library("Default Library 2","Math","......");
                    put(library.getID(),library);
                }};
                saveData();
            } else {
                //Load libraries
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String text;

                while ((text = br.readLine()) != null) {
                    sb.append(text).append("\n");
                }
                myLibraries = new Gson().fromJson(sb.toString(),new TypeToken<HashMap<UUID,Library>>(){}.getType());

                //Load test results
                isr = new InputStreamReader(context.openFileInput("test_results.txt"));
                br = new BufferedReader(isr);
                sb = new StringBuilder();

                while ((text = br.readLine()) != null) {
                    sb.append(text).append("\n");
                }
                ArrayList<TestResult> testResults = new Gson().fromJson(sb.toString(),new TypeToken<ArrayList<TestResult>>(){}.getType());
                userProfile.setTestResults(testResults);

                //Load time spent learning
                isr = new InputStreamReader(context.openFileInput("learning_time.txt"));
                br = new BufferedReader(isr);
                sb = new StringBuilder();

                while ((text = br.readLine()) != null) {
                    sb.append(text).append("\n");
                }
                HashMap<UUID,Integer> timeSpent = new Gson().fromJson(sb.toString(),new TypeToken<HashMap<UUID,Integer>>(){}.getType());
                userProfile.setTimeSpentLearning(timeSpent);
            }
        } catch (Exception e) {
            Log.d("cDebug","Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void saveData() {
        //Save libraries
        try {
            FileOutputStream fos = context.openFileOutput("libraries.txt", MODE_PRIVATE);
            fos.write(new Gson().toJson(myLibraries,new TypeToken<HashMap<UUID,Library>>(){}.getType()).getBytes());
        }
        catch (Exception e) {
            Log.d("cDebug","Error: " + e.getMessage());
            e.printStackTrace();
        }

        //Save test results
        try {
            FileOutputStream fos = context.openFileOutput("test_results.txt", MODE_PRIVATE);
            fos.write(new Gson().toJson(userProfile.getTestResults(),new TypeToken<ArrayList<TestResult>>(){}.getType()).getBytes());
        }
        catch (Exception e) {
            Log.d("cDebug","Error: " + e.getMessage());
            e.printStackTrace();
        }

        //Save time spent learning
        try {
            FileOutputStream fos = context.openFileOutput("learning_time.txt", MODE_PRIVATE);
            fos.write(new Gson().toJson(userProfile.getTimeSpentLearning(),new TypeToken<HashMap<UUID,Integer>>(){}.getType()).getBytes());
        }
        catch (Exception e) {
            Log.d("cDebug","Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public UserProfile getProfile() {
        return userProfile;
    }
    public Library getLibraryById(UUID id) {
        return myLibraries.get(id);
    }
    public HashMap<UUID,Library> getLibraries() {
        return myLibraries;
    }

    public static void createNewMainInstance(Context context) {
        instance = new Main(context);
    }
    public static Main getInstance() {
        return instance;
    }
}
