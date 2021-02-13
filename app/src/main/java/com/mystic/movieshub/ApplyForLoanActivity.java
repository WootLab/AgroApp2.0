package com.mystic.movieshub;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ApplyForLoanActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private static final int PICK_IMAGE = 4;
    private EditText farmdescription,address,postalcode;
    private Spinner agrictype,state, localGovernment;
    private List<HashMap<String, List<String>>> fullLocalGov;
    private Button chooseMultipleImages,butApply, cancel;
    private List<String> imageList;
    private List<String> stateContainer;
    private  AgroAppRepo agroAppRepo;


    //private int pos = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_for_loan);
        agroAppRepo = AgroAppRepo.getInstanceOfAgroApp();

        Toolbar toolbar = findViewById(R.id.toolbarr);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Application");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        defineViews();
        setUpSpinner();
        backgroundProcess();
        imageList = new ArrayList<>();
        butApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = FirebaseAuth.getInstance().getUid();
                String description = farmdescription.getText().toString().trim();
                String stateStr = state.getSelectedItem().toString();
                String locgov = localGovernment.getSelectedItem().toString();
                String addres = address.getText().toString().trim();
                String postal = postalcode.getText().toString().trim();
                String agric = agrictype.getSelectedItem().toString();
                agroAppRepo.addToQualified(userId,locgov,stateStr,description,imageList,agric,ApplyForLoanActivity.this);

            }
        });


        chooseMultipleImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseMultiple();
            }
        });


        state.setOnItemSelectedListener(this);



    }

    private void backgroundProcess() {
        agroAppRepo.loadLocal(ApplyForLoanActivity.this)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<HashMap<String, List<String>>>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<HashMap<String, List<String>>> hashMaps) {
                        //This is where we are getting the local government from the background thread;
                        fullLocalGov = hashMaps;

                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null && data.getClipData() != null){
            int count = data.getClipData().getItemCount();
            int currentImageSelect = 0;
            while (currentImageSelect < count){
                Uri imageUri = data.getClipData().getItemAt(currentImageSelect).getUri();
                imageList.add(imageUri.toString());
                currentImageSelect++;
            }

        }else{
            Toast.makeText(ApplyForLoanActivity.this,"please select multiple pictures",Toast.LENGTH_SHORT).show();
        }
    }


    private void chooseMultiple(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        startActivityForResult(intent,PICK_IMAGE);
    }



    private void setUpSpinner(){
        stateContainer = agroAppRepo.loadStates(this);
        ArrayAdapter<String> stateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,stateContainer);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        state.setAdapter(stateAdapter);
    }


    public void defineViews(){
        farmdescription = findViewById(R.id.descript);
        state = findViewById(R.id.spinnerState);
        localGovernment = findViewById(R.id.spinnerlocal);
        address = findViewById(R.id.editTextTextAddress);
        postalcode = findViewById(R.id.editTextTextPostal);
        chooseMultipleImages = findViewById(R.id.button9);
        agrictype = findViewById(R.id.spinnerAgri);
        butApply = findViewById(R.id.button11);
        cancel = findViewById(R.id.button12);
    }



    //This might cause an error
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //private ArrayAdapter<String> arrayListLocal;
        //private ArrayAdapter<String> adapterState;
        ArrayAdapter<String> localGovAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Objects.requireNonNull(fullLocalGov.get(position).get(state.getSelectedItem().toString())));
        localGovernment.setAdapter(localGovAdapter);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}