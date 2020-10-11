package ar.edu.ort.bmon.rootsapp.service;

import com.google.firebase.firestore.FirebaseFirestore;

public final class FirebaseService {
    private static FirebaseFirestore db;
    private static FirebaseService instance;

    private FirebaseService() {
        db = FirebaseFirestore.getInstance();
    }

    public static FirebaseService getFirebaseInstance() {
        if (instance == null) {
            instance = new FirebaseService();
        }
        return instance;
    }

    public static FirebaseFirestore getDb() {
        return db;
    }
}
