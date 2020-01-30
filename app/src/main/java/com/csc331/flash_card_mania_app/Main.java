package com.csc331.flash_card_mania_app;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    private static Main instance;
    private UserProfile userProfile;
    private HashMap<String,Library> myLibraries;

    public Main() {
        myLibraries = new HashMap<String,Library>() {{
            put("Test Library 1",new Library("Test Library 1","Science"));
        }};
    }

    public UserProfile getProfile() {
        return userProfile;
    }
    public Library getLibrary(String name) {
        return myLibraries.get(name);
    }
    public HashMap<String,Library> getLibraries() {
        return myLibraries;
    }
    public static Main getInstance() {
        if (instance == null) {
            instance = new Main();
        }
        return instance;
    }
}
