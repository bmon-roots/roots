package ar.edu.ort.bmon.rootsapp.service;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public final class FirebaseServices {
    private static FirebaseFirestore db;
    private static StorageReference mStorageRef;
    private static FirebaseServices instance;

    private FirebaseServices() {
        db = FirebaseFirestore.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
    }

    public static FirebaseServices initFirebaseServices() {
        if (instance == null) {
            instance = new FirebaseServices();
        }
        return instance;
    }

    public static FirebaseFirestore getDb() {
        return db;
    }
    public static StorageReference getStorageRef() { return mStorageRef; }
}
