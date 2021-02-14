package com.mystic.movieshub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.util.Strings;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class InvestorScreenActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner state, localgov, typeoffarming;
    private RecyclerView recyclerView;
    private TextView noFarmer;
    private Button but;
    private  List<HashMap<String, List<String>>> fullLocalGov;
    private ProgressBar bar;
    private InvestorScreenAdapter investorScreenAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investor_screen);
        Toolbar toolbar = findViewById(R.id.toolbarr);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Search Farmers");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        List<String> stateList = AgroAppRepo.getInstanceOfAgroApp().loadStates(getApplicationContext());
        defineViews();
        backgroundOperation();
        fullLocalGov = new ArrayList<>();
        ArrayAdapter<String> stateArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, stateList);
        state.setAdapter(stateArrayAdapter);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stateStr = state.getSelectedItem().toString();
                String localgovStr = localgov.getSelectedItem().toString();
                String farmType = typeoffarming.getSelectedItem().toString();
                AgroAppRepo.getInstanceOfAgroApp().fetchSelectedfarmers(localgovStr, stateStr, farmType,bar, qualifiedfarmers -> {
                    if(investorScreenAdapter == null){
                        investorScreenAdapter = new InvestorScreenAdapter(qualifiedfarmers,InvestorScreenActivity.this);
                    }
                    if(qualifiedfarmers.size() > 0){
                        recyclerView.setVisibility(View.VISIBLE);
                        recyclerView.setLayoutManager(new LinearLayoutManager(InvestorScreenActivity.this));
                        recyclerView.setAdapter(investorScreenAdapter);
                        noFarmer.setVisibility(View.GONE);
                    }else{
                        noFarmer.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }

                    investorScreenAdapter.showQualifiedFarmer(position -> {
                        Log.d("screenAdapter",qualifiedfarmers.get(position).getName());
                        if(FirebaseAuth.getInstance().getCurrentUser() == null){
                            User user = qualifiedfarmers.get(position);
                            Intent intent = new Intent(InvestorScreenActivity.this,LoginActivity.class);
                            intent.putExtra("FromInvestorScr",user);
                            Toast.makeText(InvestorScreenActivity.this,"You have to login",Toast.LENGTH_SHORT).show();
                            startActivity(intent);

                        }
                        else{
                            Log.d("Mogin","I am not null");
                            User user = qualifiedfarmers.get(position);
                            Intent intent = new Intent(InvestorScreenActivity.this,QualifiedFarmersActivity.class);
                            intent.putExtra("QualifiedFarmers",user);
                            startActivity(intent);
                        }

                    });
                });

            }
        });

        state.setOnItemSelectedListener(this);
    }

    private void backgroundOperation() {
        AgroAppRepo.getInstanceOfAgroApp().loadLocal(InvestorScreenActivity.this)
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

    private void defineViews(){
        state = findViewById(R.id.spinnersta);
        localgov = findViewById(R.id.spinnerloc);
        typeoffarming = findViewById(R.id.spinnerfarm);
        recyclerView = findViewById(R.id.recyclerView2);
        but = findViewById(R.id.button8);
        bar = findViewById(R.id.progressBar5);
        noFarmer = findViewById(R.id.textView27);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ArrayAdapter<String> localGovArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Objects.requireNonNull(fullLocalGov.get(position).get(state.getSelectedItem().toString())));
        localgov.setAdapter(localGovArrayAdapter);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}