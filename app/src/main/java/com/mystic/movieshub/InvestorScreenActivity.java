package com.mystic.movieshub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;

import java.util.List;

public class InvestorScreenActivity extends AppCompatActivity {

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
                                User user = qualifiedfarmers.get(position);
                                Intent intent = new Intent();
                                intent.putExtra("QualifiedFarmer",user);
                                startActivity(intent);
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
}