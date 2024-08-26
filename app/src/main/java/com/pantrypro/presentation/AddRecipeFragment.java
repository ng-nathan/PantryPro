package com.pantrypro.presentation;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.pantrypro.R;
import com.pantrypro.logic.AccessRecipes;
import com.pantrypro.logic.RecipeValidator;
import com.pantrypro.logic.exceptions.RecipeException;
import com.pantrypro.objects.ErrorMessage;
import com.pantrypro.objects.Recipe;

import java.util.List;
import java.util.Objects;

/**
 * Fragment class for adding a new recipe.
 */
public class AddRecipeFragment extends Fragment {

    public EditText titleBox, durationBox, ingredientsBox, stepsBox;
    private Spinner spinnerFood;
    private View view;
    private Button create, cancel;
    private ImageView previewImg;
    private String imgLink;
    private Uri imageUri;
    private ProgressBar uploading;
    final private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("images");
    final private StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    private ActivityResultLauncher<Intent> photoPickerLauncher;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize the ActivityResultLauncher for the photo picker
        photoPickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == getActivity().RESULT_OK) {
                            Intent data = result.getData();
                            assert data != null;
                            imageUri = data.getData();
                            previewImg.setImageURI(imageUri);
                        } else {
                            Toast.makeText(requireContext(), "If you didn't pick a picture, a default one will be set", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_recipe, container, false);

        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_new_24);
        toolbar.setTitle("Add a recipe");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeFragment();
            }
        });

        // initialize UI IDs
        initializeElements();

        // Create a recipe
        previewImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPhotoPicker();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(requireContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                closeFragment();
            }
        });

        create.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(imageUri != null){
                    if(validateUserInputs()) {
                        uploadPhotoToFirebase(imageUri);
                    }
                } else {
                    imgLink = "https://www.plantpurenation.com/cdn/shop/articles/hero_22a92638-2b36-4236-8a83-8e1972affa58_1296x.jpg?v=1514269027";
                    try {
                        createRecipe();
                    } catch (RecipeException e) {
                        throw new RuntimeException(e);
                    }
                    Toast.makeText(requireContext(), "Default picture set", Toast.LENGTH_SHORT).show();
                    closeFragment();
                }
            }
        });

        return view;
    }

    private void initializeElements(){
        // Set up dropdown menu for type of food
        spinnerFood = view.findViewById(R.id.spinnerFood);
        String [] foodTypes = getResources().getStringArray(R.array.foodTypesList);
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, foodTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFood.setAdapter(adapter);

        titleBox = view.findViewById(R.id.createTitle);
        durationBox = view.findViewById(R.id.createDuration);
        ingredientsBox = view.findViewById(R.id.createIngredients);
        stepsBox = view.findViewById(R.id.createSteps);

        previewImg = view.findViewById(R.id.createImg);
        uploading = view.findViewById(R.id.createUploading);
        uploading.setVisibility(View.INVISIBLE);
        create = view.findViewById(R.id.createRecipe);
        cancel = view.findViewById(R.id.cancelCreate);
    }

    private void openPhotoPicker() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        photoPickerLauncher.launch(intent);
    }

    //
    // upload photo onto Firebase
    private void uploadPhotoToFirebase(Uri uri) {
        final StorageReference imageReference = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(uri));

        imageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Recipe recipeImage = new Recipe(getTitle(), uri.toString());
                        databaseReference.child(getTitle()).setValue(recipeImage);
                        uploading.setVisibility(View.INVISIBLE);
                        requireActivity().setResult(Activity.RESULT_OK, null);

                        pullPhotoFromFireBase();
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                uploading.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                uploading.setVisibility(View.INVISIBLE);
            }
        });
    }

    private String getFileExtension(Uri uriFile) {
        ContentResolver contentResolver = requireContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uriFile));
    }

    // pull photo from Firebase
    private void pullPhotoFromFireBase(){
        Query recipeImgDB = databaseReference.orderByChild("recipeTitle").equalTo(getTitle());

        recipeImgDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("DataSnapshot", snapshot.toString());
                //if the user exists
                if(snapshot.exists()){
                    imgLink = snapshot.child(getTitle()).child("image").getValue(String.class);
                    try {

                        createRecipe();

                    } catch (RecipeException e) {
                        throw new RuntimeException(e);
                    }
                    Toast.makeText(requireContext(), "Successfully posted", Toast.LENGTH_SHORT).show();
                    closeFragment();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("RecipeImgDB", "Failed to pull image.", error.toException());
            }
        });
    }

    private void createRecipe() throws RecipeException {
        Recipe newRecipe = new Recipe(getTitle(), getDuration(), getTypeOfFood(), getIngredients(), getSteps(), imgLink);
        ((MainActivity) requireContext()).getHandleRecipesDB().addRecipe(newRecipe);
    }

    private void closeFragment(){
        ((MainActivity)requireContext()).replaceFragment(new HomeFragment());
    }

    // user inputs
    private boolean validateUserInputs(){
        return RecipeValidator.validateUserInput(this);
    }

    @NonNull
    public String getTitle() {
        return titleBox.getText().toString();
    }

    @NonNull
    public String getDuration() {
        return durationBox.getText().toString();
    }

    @NonNull
    public String getIngredients() {
        return ingredientsBox.getText().toString();
    }

    @NonNull
    public String getSteps() {
        return stepsBox.getText().toString();
    }
    @NonNull
    public String getTypeOfFood() {
        return spinnerFood.getSelectedItem().toString();
    }

    public void clearAllErrors() {
        titleBox.setError(null);
        durationBox.setError(null);
        ingredientsBox.setError(null);
        stepsBox.setError(null);
    }
}