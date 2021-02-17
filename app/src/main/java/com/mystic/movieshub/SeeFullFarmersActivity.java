package com.mystic.movieshub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SeeFullFarmersActivity extends AppCompatActivity {

    List<User> allFarmers;
    RecyclerView recyclerView;
    FullFarmersListAdapter fullFarmersListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_see_full_farmers);

        Toolbar toolbar = findViewById(R.id.toolbarr);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        allFarmers = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclee);
        AgroAppRepo.getInstanceOfAgroApp().fetchAllUser(new AgroAppRepo.FetchQualifiedfarmers() {
            @Override
            public void firebaseQualifiedFarmers(List<User> qualifiedfarmers) {
                if(fullFarmersListAdapter == null){
                    fullFarmersListAdapter = new FullFarmersListAdapter(qualifiedfarmers,SeeFullFarmersActivity.this);
                }
                recyclerView.setAdapter(fullFarmersListAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(SeeFullFarmersActivity.this));




                fullFarmersListAdapter.moveToSpecificFarmer(new FullFarmersListAdapter.FullFarmersListener() {
                    @Override
                    public void farmerlistener(int position) {
                        User user = qualifiedfarmers.get(position);
                        Intent intent = new Intent(SeeFullFarmersActivity.this,QualifiedFarmersActivity.class);
                        intent.putExtra("SeeFull",user);
                        startActivity(intent);
                    }

                    @Override
                    public void fundfarmerListener(int pos) {
                        User user = qualifiedfarmers.get(pos);
                        Intent intent = new Intent(SeeFullFarmersActivity.this,FundFarmerActivity.class);
                        intent.putExtra("FundUser",user);
                        startActivity(intent);
                    }
                });


                fullFarmersListAdapter.approveApplication(new FullFarmersListAdapter.AppListener() {
                    @Override
                    public void appListenerRes(int position) {
                        User user = qualifiedfarmers.get(position);

                        if(user.getRequirements().isEligible()){
                            user.getRequirements().setEligible(false);
                            DatabaseReference mDatebaseReference = FirebaseDatabase.getInstance().getReference("USERS");
                            mDatebaseReference
                                    .child(user.getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(SeeFullFarmersActivity.this,"Successfully changed to false",Toast.LENGTH_SHORT).show();
                                    }

                                }
                            })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(SeeFullFarmersActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }else{
                            user.getRequirements().setEligible(true);
                            DatabaseReference mDatebaseReference = FirebaseDatabase.getInstance().getReference("USERS");
                            mDatebaseReference
                                    .child(user.getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(SeeFullFarmersActivity.this,"Successfully changed eligibilty",Toast.LENGTH_SHORT).show();
                                    }

                                }
                            })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(SeeFullFarmersActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }




                    }
                });
            }
        });




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.fullfarmermenu,menu);
        MenuItem item = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fullFarmersListAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
}