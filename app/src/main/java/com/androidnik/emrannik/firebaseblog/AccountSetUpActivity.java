package com.androidnik.emrannik.firebaseblog;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountSetUpActivity extends AppCompatActivity {
    private CircleImageView setUpImage;
    private Uri mainImageURI;
    private EditText setname;
    private Button btnSet;
    private StorageReference storageReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private ProgressBar setPrgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_set_up);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.setUpToolbar);
        setUpImage = findViewById(R.id.setupImage);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Account Setting");

        setname = findViewById(R.id.setUsername);
        btnSet = findViewById(R.id.btnSet);
        setPrgressBar = findViewById(R.id.progressBar);

        firebaseAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();
        String user_Id = firebaseAuth.getCurrentUser().getUid();
        setPrgressBar.setVisibility(View.VISIBLE);

        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String user_name = setname.getText().toString();
                if (!TextUtils.isEmpty(user_name) && mainImageURI != null) {
                    final String user_id = firebaseAuth.getCurrentUser().getUid();
                    setPrgressBar.setVisibility(View.VISIBLE);

                    StorageReference image_path = storageReference.child("profile_images").child(user_id + ".jpg");
                    image_path.putFile(mainImageURI).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if(task.isSuccessful()){
                                Uri download_uri=task.getResult().getDownloadUrl();
                                Map<String, String> userMap=new HashMap<>();
                                userMap.put("name",user_name);
                                userMap.put("image",download_uri.toString());
                                //taskSnapshot.getMetadata().getReference().getDownloadUrl().toString()
                                firebaseFirestore.collection("Users").document(user_id).set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(AccountSetUpActivity.this,"The user setting are uploaded.",Toast.LENGTH_SHORT).show();
                                            Intent mainIntent=new Intent(AccountSetUpActivity.this,HomeActivity.class);
                                            startActivity(mainIntent);
                                            finish();
                                        }else {
                                            String error=task.getException().getMessage();
                                            Toast.makeText(AccountSetUpActivity.this,"(Firestore error: )"+error,Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });

                            }else {
                                String error=task.getException().getMessage();
                                Toast.makeText(AccountSetUpActivity.this,"(Firestore error: )"+error,Toast.LENGTH_SHORT).show();
                            }

                            setPrgressBar.setVisibility(View.INVISIBLE);
                        }
                    });

                }
            }
        });

        /*StorageReference image_path=storageReference.child("profile_images").child(user_Id + ".jpg");
          image_path.putFile(mainImageURI).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
              @Override
              public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                  if (task.isSuccessful()){
                      Uri download_uri=task.getResult().getDownloadUrl();
                      firebaseFirestore.collection("Users").document(user_Id);
                      Toast.makeText(AccountSetUpActivity.this,"The image is uploaded",Toast.LENGTH_SHORT).show();
                  }else {
                      String error=task.getException().getMessage();
                      Toast.makeText(AccountSetUpActivity.this,"Error is "+error,Toast.LENGTH_SHORT).show();
                  }
                  setPrgressBar.setVisibility(View.INVISIBLE);
              }
          });

        firebaseFirestore.collection("Users").document(user_Id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().exists()){
                        String name=task.getResult().getString("name");
                        String image=task.getResult().getString("image");

                        setname.setText(name);
                        RequestOptions placeholderRequest=new RequestOptions();
                        placeholderRequest.placeholder(R.drawable.user);
                        Glide.with(AccountSetUpActivity.this).setDefaultRequestOptions(placeholderRequest).load(image).into(setUpImage);

                        //Toast.makeText(AccountSetUpActivity.this,"Data Exists",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(AccountSetUpActivity.this,"Data don't Exists",Toast.LENGTH_SHORT).show();
                    }

                } else {
                    String error=task.getException().getMessage();
                    Toast.makeText(AccountSetUpActivity.this,"Firebase Retriev Error"+error,Toast.LENGTH_SHORT).show();


                }
            }
        });
*/
       /* btnAccountSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_name = setname.getText().toString();
                if (!TextUtils.isEmpty(user_name) && mainImageURI != null) {
                    String user_id = firebaseAuth.getCurrentUser().getUid();
                    setPrgressBar.setVisibility(View.VISIBLE);
                    StorageReference image_path = storageReference.child("profile_images").child(user_id + ".jpg");

                    image_path.putFile(mainImageURI).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()) {
                                Uri download_uri = task.getResult().getDownloadUrl();
                                Toast.makeText(AccountSetUpActivity.this, "The Image is uploaded", Toast.LENGTH_SHORT).show();
                                *//*Map<String, String> userMap=new HashMap<>();
                                userMap.put("name",user_name);
                                userMap.put("image",download_uri.toString());
                                //taskSnapshot.getMetadata().getReference().getDownloadUrl().toString()
                                firebaseFirestore.collection("Users").document(user_id).set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(AccountSetUpActivity.this,"The user setting are uploaded.",Toast.LENGTH_SHORT).show();
                                            Intent mainIntent=new Intent(AccountSetUpActivity.this,HomeActivity.class);
                                            startActivity(mainIntent);
                                            finish();
                                        }else {
                                            String error=task.getException().getMessage();
                                            Toast.makeText(AccountSetUpActivity.this,"(Firestore error: )"+error,Toast.LENGTH_SHORT).show();
                                        }
                                        //setPrgressBar.setVisibility(View.INVISIBLE);

                                    }
                                });*//*

                            } else {
                                String error = task.getException().getMessage();
                                Toast.makeText(AccountSetUpActivity.this, "Eroor: " + error, Toast.LENGTH_SHORT).show();
                                setPrgressBar.setVisibility(View.INVISIBLE);
                            }


                        }
                    });
                }
            }
        });
*/

        setUpImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.M){
                    if(ContextCompat.checkSelfPermission(AccountSetUpActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(AccountSetUpActivity.this,"Permission Denied",Toast.LENGTH_SHORT).show();
                        ActivityCompat.requestPermissions(AccountSetUpActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
                    } else {
                       BringImagePicker();
                    }
                } else {
                    BringImagePicker();
                }
            }

            private void BringImagePicker() {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(AccountSetUpActivity.this);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                mainImageURI = result.getUri();
                setUpImage.setImageURI(mainImageURI);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}
