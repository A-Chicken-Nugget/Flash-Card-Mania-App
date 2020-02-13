package com.csc331.flash_card_mania_app;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Main {
    private static Main instance;
    private UserProfile userProfile;
    private HashMap<UUID,Library> myLibraries;

    public Main() {
        myLibraries = new HashMap<UUID,Library>() {{
            Library library = new Library("Default Library 1","Science", "Test description");
            put(library.getID(),library);
            library = new Library("Default Library 2","Math","......");
            put(library.getID(),library);
        }};
    }

    public void loadData() {

    }
    public void saveData() {

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
    public static Main getInstance() {
        if (instance == null) {
            instance = new Main();
        }
        return instance;
    }
}
