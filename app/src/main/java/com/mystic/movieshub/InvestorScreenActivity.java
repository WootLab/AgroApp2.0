package com.mystic.movieshub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class InvestorScreenActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner state, localgov, typeoffarming;
    private RecyclerView recyclerView;
    private Button but;
    private ProgressBar bar;
    private InvestorScreenAdapter investorScreenAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investor_screen);
        defineViews();
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

                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stateStr = state.getSelectedItem().toString();
                String localgovStr = localgov.getSelectedItem().toString();
                String farmType = typeoffarming.getSelectedItem().toString();
                AgroAppRepo.getInstanceOfAgroApp().fetchSelectedfarmers(localgovStr, stateStr, farmType,bar, new AgroAppRepo.FetchQualifiedfarmers() {
                    @Override
                    public void firebaseQualifiedFarmers(final List<User> qualifiedfarmers) {
                        if(investorScreenAdapter == null){
                            investorScreenAdapter = new InvestorScreenAdapter(qualifiedfarmers,InvestorScreenActivity.this);
                        }
                        recyclerView.setVisibility(View.VISIBLE);
                        recyclerView.setLayoutManager(new LinearLayoutManager(InvestorScreenActivity.this));
                        recyclerView.setAdapter(investorScreenAdapter);

                        investorScreenAdapter.showQualifiedFarmer(new InvestorScreenAdapter.QualifiedFarmersListener() {
                            @Override
                            public void farmerlistener(int position) {

                                if(FirebaseAuth.getInstance().getCurrentUser() == null){
                                    Intent intent = new Intent(InvestorScreenActivity.this,LoginActivity.class);
                                    startActivity(intent);
                                }else{
                                    User user = qualifiedfarmers.get(position);
                                    Intent intent = new Intent(InvestorScreenActivity.this,QualifiedFarmersActivity.class);
                                    intent.putExtra("QualifiedFarmer",user);
                                    startActivity(intent);
                                }

                            }
                        });
                    }
                });

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

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {



    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}