package ar.edu.ort.bmon.rootsapp.ui.plant;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ar.edu.ort.bmon.rootsapp.R;
import ar.edu.ort.bmon.rootsapp.constants.Constants;
import ar.edu.ort.bmon.rootsapp.model.Plant;
import ar.edu.ort.bmon.rootsapp.model.Species;

public class CreatePlantFragment extends Fragment {

    private CreatePlantViewModel mViewModel;
    private ImageView plantPhoto;
    private EditText plantName;
    private EditText plantAge;
    private EditText acquisitionDate;
    private Date userSelectedDate;
    private Switch isBonsaiAble;
    private EditText origin;
    private EditText height;
    private EditText container;
    private EditText plantPh;
    private Switch isSaleable;
    private List<Species> speciesList;
    private int selectedSpeciesIndex;
    private String imageFileName;
    private String imageLocation;
    private Uri imageLocationUri;
    private String imageUriFromStorage;
    private Plant plant;
    private DocumentSnapshot speciesDocument;
    private View viewReference;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();

    public static CreatePlantFragment newInstance() {
        return new CreatePlantFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.create_plant_fragment, container, false);
        viewReference = root;
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CreatePlantViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getAllSpecies(view);
        initializeFields(view);
        view.findViewById(R.id.editPlantDate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(getChildFragmentManager());
            }
        });

        view.findViewById(R.id.btnSavePlant).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDataInFirebase();
            }
        });

        view.findViewById(R.id.btnSelectPhoto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectPhotoDialog();
            }
        });

    }

    private void showSelectPhotoDialog() {
        AlertDialog.Builder selectPhotoDialog = new AlertDialog.Builder(getActivity());
        String[] selectPhotoDialogOptions = new String[] {Constants.SELECT_FROM_GALLERY, Constants.TAKE_PHOTO};
        selectPhotoDialog.setTitle(Constants.CHANGE_PHOTO_MENU_TITLE);
        selectPhotoDialog.setSingleChoiceItems(selectPhotoDialogOptions, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ListView selectionList = ((AlertDialog) dialog).getListView();
                selectionList.setTag(Integer.valueOf(which));
            }
        });
        selectPhotoDialog.setNegativeButton(Constants.CANCEL_BUTTON, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        selectPhotoDialog.setPositiveButton(Constants.ACCEPT_BUTTON, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ListView selectionList = ((AlertDialog) dialog).getListView();
                Integer selectedItemId = (Integer)selectionList.getTag();
                if (selectedItemId == 0) {
                    getSelectedPhotoFromGallery();
                } else {
                    getTakenPhotoFromCamera();
                }
                dialog.dismiss();
            }
        });
        selectPhotoDialog.create().show();
    }

    private void getTakenPhotoFromCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Toast.makeText(getContext(), Constants.IMAGE_GALLERY_ERROR, Toast.LENGTH_LONG).show();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        "ar.edu.ort.bmon.rootsapp.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, Constants.REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private void getSelectedPhotoFromGallery() {
        Intent getPhotoFromGallery = new Intent(Intent.ACTION_GET_CONTENT);
        getPhotoFromGallery.setType("image/*");
        startActivityForResult(getPhotoFromGallery, Constants.GALLERY_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case Constants.GALLERY_REQUEST:
                    Uri imageUri = data.getData();
                    if (imageUri != null) {
                        imageLocation = imageUri.toString();
                        imageLocationUri = data.getData();
                        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmm").format(new Date());
                        StringBuilder sb = new StringBuilder("IMG_");
                        sb.append(timestamp).append('.').append(getFileExt(imageUri));
                        imageFileName = sb.toString();
                        uploadImageToFirebase(imageFileName, imageLocationUri);
                        plantPhoto.setImageURI(imageUri);
                    } else {
                        Toast.makeText(getContext(), Constants.IMAGE_GALLERY_ERROR, Toast.LENGTH_LONG).show();
                    }
                    break;
                case Constants.REQUEST_IMAGE_CAPTURE:
                    File imageFile = new File(imageLocation);
                    imageFileName = imageFile.getName();
                    imageLocationUri = Uri.fromFile(imageFile);
                    uploadImageToFirebase(imageFileName, imageLocationUri);
                    plantPhoto.setImageURI(Uri.fromFile(imageFile));
                    break;
                default:
                    break;
            }
        }
    }

    private void createImageFromBitmap(Intent data) {
        Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
        plantPhoto.setImageBitmap(imageBitmap);
    }

    private void saveDataInFirebase() {
        insertDataIntoFirebase();
    }

    private void insertDataIntoFirebase() {
        if (imageUriFromStorage != null) {
            plant = new Plant(
                    speciesList.get(selectedSpeciesIndex).toString(),
                    plantName.getText().toString(),
                    plantAge.getText().toString(),
                    userSelectedDate,
                    isBonsaiAble.isActivated(),
                    origin.getText().toString(),
                    height.getText().toString(),
                    container.getText().toString(),
                    isSaleable.isActivated(),
                    plantPh.getText().toString(),
                    imageUriFromStorage
            );
        } else {
            plant = new Plant(
                    speciesList.get(selectedSpeciesIndex).toString(),
                    plantName.getText().toString(),
                    plantAge.getText().toString(),
                    userSelectedDate,
                    isBonsaiAble.isActivated(),
                    origin.getText().toString(),
                    height.toString(),
                    container.getText().toString(),
                    isSaleable.isActivated(),
                    plantPh.getText().toString()
            );
        }

        db.collection(Constants.PLANT_COLLECTION).add(plant)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Navigation.findNavController(viewReference).navigate(R.id.nav_plant);
                        Toast.makeText(getContext(), "Se ha creado una nueva planta", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Ha ocurrido un problema, intente nuevamente", Toast.LENGTH_LONG).show();
                    }
                });


    }

    private void uploadImageToFirebase(String imageFileName, Uri imageLocation) {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle(Constants.UPLOADING_PHOTO);
        progressDialog.show();

        final StorageReference image = mStorageRef.child(Constants.PLANT_IMAGES_FOLDER + imageFileName);
        image.putFile(imageLocation).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d("tag", "onSuccess: Uploaded Image URl is " + uri.toString());
                        imageUriFromStorage = uri.toString();
                        progressDialog.dismiss();
                    }
                });
                Toast.makeText(getContext(), "Image Is Uploaded.", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Upload Failled.", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double percentage = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                progressDialog.setMessage(percentage + Constants.PERCENTAGE_COMPLETE);
            }
        });
    }

    private void getAllSpecies(View view) {
        final Spinner speciesSpinner = view.findViewById(R.id.spinnerSpecies);
        speciesList = new ArrayList<>();
        db.collection(Constants.SPECIES_COLLECTION)
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document: task.getResult()) {
                            speciesList.add(document.toObject(Species.class));
                        }
                        ArrayAdapter<Species> speciesAdapter = new ArrayAdapter<Species>(getActivity(), R.layout.species_spinner_dropdown_item, R.id.textView_spinner_species_item, speciesList);
                        speciesAdapter.setDropDownViewResource(R.layout.species_spinner_dropdown_item);
                        speciesSpinner.setAdapter(speciesAdapter);
                        speciesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                selectedSpeciesIndex = speciesList.indexOf(parent.getSelectedItem());
                                final DocumentReference SpeciesDocumentReference = db.collection(Constants.PLANT_COLLECTION).document(speciesList.get(selectedSpeciesIndex).toString());
                                SpeciesDocumentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            speciesDocument = task.getResult();
                                            if (speciesDocument.exists()){
                                                speciesDocument = (DocumentSnapshot) speciesDocument.getData();
                                            }
                                        }
                                    }
                                });
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                }
            });
    }

    private void initializeFields(View view) {
        plantPhoto = view.findViewById(R.id.imageViewPlantPhoto);
        plantName = view.findViewById(R.id.editTextPlantName);
        plantAge = view.findViewById(R.id.editPlantAge);
        acquisitionDate = view.findViewById(R.id.editPlantDate);
        plantPh = view.findViewById(R.id.editPlantPH);
        origin = view.findViewById(R.id.editPlantOrigin);
        height = view.findViewById(R.id.editPlantHeight);
        container = view.findViewById(R.id.editPlantContainerType);
        isBonsaiAble = view.findViewById(R.id.switchBonsaiAble);
        isSaleable = view.findViewById(R.id.switchSellable);
    }

    private void showDatePickerDialog(FragmentManager fragmentManager) {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = twoDigits(day) + "/" + twoDigits(month + 1) + "/" + year;
                acquisitionDate.setText(selectedDate);
                userSelectedDate = getUserSelectedDate(year, month, day);
            }
        });
        newFragment.show(fragmentManager, "datePicker");
    }

    private Date getUserSelectedDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }

    private String twoDigits(int n) {
        return (n<=9) ? ("0"+n) : String.valueOf(n);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        imageLocation = image.getAbsolutePath();
        return image;
    }


    private String getFileExt(Uri contentUri) {
        ContentResolver c = getContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(c.getType(contentUri));
    }

}