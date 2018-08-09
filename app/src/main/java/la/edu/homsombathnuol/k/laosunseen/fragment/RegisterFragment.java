package la.edu.homsombathnuol.k.laosunseen.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Collections;

import la.edu.homsombathnuol.k.laosunseen.MainActivity;
import la.edu.homsombathnuol.k.laosunseen.R;
import la.edu.homsombathnuol.k.laosunseen.utility.MyAlert;
import la.edu.homsombathnuol.k.laosunseen.utility.UserModel;

public class RegisterFragment extends Fragment {

    private Uri uri;
    private ImageView imageView;
    private boolean aBoolean = true;
    private String nameString, emailString, passwordString,
            uidString, pathURLString, myPostString;
    private ProgressDialog progressDialog ;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Create Toolbar
        createToolbar();
//        Photo Controller
        photoController();

    }//Main Class

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fragment,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.itemUpload){
            uploadProcess();
            return true;
            
        }
        return super.onOptionsItemSelected(item);
    }

    private void uploadProcess() {

        progressDialog = new ProgressDialog((getActivity()));
        progressDialog.setTitle("Upload Value Process");
        progressDialog.setMessage("Please Wait few Minus...");
        progressDialog.show();

        EditText nameEditText = getView().findViewById(R.id.edtName);
        EditText emailEditText = getView().findViewById(R.id.edtEmail);
        EditText passwordEditText = getView().findViewById(R.id.edtPassword);
//        Get Value From Editext
        nameString = nameEditText.getText().toString().trim();
         emailString = emailEditText.getText().toString().trim();
         passwordString = passwordEditText.getText().toString().trim();

//        Check Choose Photo
        if(aBoolean){
//            Non Choose Photo
            MyAlert myAlert = new MyAlert(getActivity());
            myAlert.nomalDialog("Non Choose Photo",
                    "Please Chosse Photo");
        } else if (nameString.isEmpty() || emailString.isEmpty() || passwordString.isEmpty()) {
//            Have Space
            MyAlert myAlert = new MyAlert(getActivity());
            myAlert.nomalDialog("Have Space",
                    "Please Fill All Every Blank");

        } else {
//No Space
            createAuthentication();
           uploadPhotoToFirebase();
        }

    }

    private void createAuthentication() {
        Log.d("8AugV1","Greating Authen Work");
        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(emailString, passwordString)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            uidString = firebaseAuth.getCurrentUser().getUid();
                            Log.d("8AugV1","uriString ==>" + uidString);

                        }else {
                            MyAlert myAlert = new MyAlert(getActivity());
                            myAlert.nomalDialog("Cannot Register",
                                    "Because ==>" + task.getException().getMessage());
                            progressDialog.dismiss();
                        }

                    }
                });

    }

    private void uploadPhotoToFirebase() {

        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();
        StorageReference storageReference1 = storageReference.child("Avata/" + nameString);

        storageReference1.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getActivity(),"Success Upload Photo",Toast.LENGTH_SHORT).show();
                findPathUrlPhoto();
                createPost();
                createDatabase();
                progressDialog.dismiss();
            }


        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(),"Cannot Upload Photo",Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

    }//UploadPhoto

    private void createDatabase() {

        UserModel userModel = new UserModel(uidString, nameString,
                emailString, pathURLString, myPostString);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference  = firebaseDatabase.getReference()
                .child("User");
        databaseReference.child(uidString).setValue(userModel)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getActivity(),"Success Register",Toast.LENGTH_SHORT).show();
                        getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contentMainflagment, new ServiceFragment())
                        .commit();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("9AugV1","Error ==>" + e.toString());
            }
        });

    }//createDatabase

    private void createPost() {
        ArrayList<String> stringArrayList = new ArrayList<>();
        stringArrayList.add("Hello");
        myPostString = stringArrayList.toString();
        Log.d("9AugV1","myPost ==>" + myPostString);

    }//createPost

    private void findPathUrlPhoto() {
        try {
            FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
            StorageReference storageReference = firebaseStorage.getReference();
            final String[] urlString = new String[1];
            storageReference.child("Avata").child(nameString)
                    .getDownloadUrl()
                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            urlString[0] = uri.toString();
                            pathURLString = urlString[0];
                            Log.d("9AugV1", "PrintURL ==>" + pathURLString);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("9AugV1","Error ==>" + e.toString());
                }
            });


         } catch (Exception e) {
            e.printStackTrace();
        }
    }//FindPath

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            uri = data.getData();
            aBoolean = false;
            try{
                Bitmap bitmap = BitmapFactory
                        .decodeStream(getActivity()
                                .getContentResolver()
                                .openInputStream(uri));
                Bitmap bitmap1 = Bitmap.createScaledBitmap(bitmap,500,300,false);
                imageView.setImageBitmap(bitmap1);

            }catch (Exception e){
                e.printStackTrace();
            }
        }else {
            Toast.makeText(getActivity(),"Please Choose Photo",Toast.LENGTH_SHORT).show();
        }
    }

    private void photoController() {
        imageView = getView().findViewById(R.id.imvPhoto);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent,"Please Choose App"),1);
            }
        });
    }

    private void createToolbar() {
        Toolbar toolbar = getView().findViewById(R.id.toolbarRegister);
        ((MainActivity)getActivity()).setSupportActionBar(toolbar);
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Register");
        ((MainActivity)getActivity()).getSupportActionBar().setSubtitle("Please Choose Photo and Fill Textbox");
        ((MainActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register ,container,false);
        return view;
    }
}
