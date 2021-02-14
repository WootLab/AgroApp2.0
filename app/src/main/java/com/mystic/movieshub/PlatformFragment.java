package com.mystic.movieshub;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlatformFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlatformFragment extends Fragment {


    private RecyclerView mRecyclerView ;
    private AgroAppRepo agroAppRepo;
    private PlatformAdapter adapter;
    private ProgressBar bar;
    private Button but;
    private FarmProduct specProd;
    private FloatingActionButton floatingActionButton;
    public PlatformFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static PlatformFragment newInstance() {
        return new PlatformFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        agroAppRepo = AgroAppRepo.getInstanceOfAgroApp();


        agroAppRepo.fetchPro(new AgroAppRepo.FireBaseCallbacProduct() {
            @Override
            public void fireBaseProducts(final List<FarmProduct> product) {
                bar.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
                adapter = new PlatformAdapter(product,getActivity());
                mRecyclerView.setAdapter(adapter);
                adapter.setOnAdapterListener(new PlatformAdapter.CustomeAdapterListener() {
                    @Override
                    public void adapterListener(int position) {
                        specProd = product.get(position);
                        Intent intent = new Intent(getActivity(), SpecificProductActivity.class);
                        intent.putExtra(SpecificProductActivity.PRODUCT,specProd);
                        startActivity(intent);
                    }

                    @Override
                    public void deleteAdvert(int position) {
                        specProd = product.get(position);
                        String id = specProd.getProductId();
                        agroAppRepo.removeProduct(id,getContext());
                        adapter.notifyItemChanged(position);
                    }
                });
            }
        });


        agroAppRepo.fetchUser(new AgroAppRepo.FireBaseCallbackUser() {
            @Override
            public void fireBaseUser(User basuser) {
                if(basuser.getRole().equals("farmer")
                        || basuser.getEmail().equals("bam@gmail.com")
                        || basuser.getEmail().equals("yvonne@gmail.com")
                        || basuser.getEmail().equals("chinedu@gmail.com")){
                    floatingActionButton.setVisibility(View.VISIBLE);
                }else{
                    floatingActionButton.setVisibility(View.GONE);
                }


                floatingActionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(),UploadActivity.class);
                        startActivity(intent);
                    }
                });



                if(basuser.getRole().equals("Consumer")){
                    but.setVisibility(View.VISIBLE);
                }else{
                    but.setVisibility(View.GONE);
                }
            }


        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_genericview, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        but = view.findViewById(R.id.button5);
        bar = view.findViewById(R.id.progressBar3);
        floatingActionButton = view.findViewById(R.id.floatingActionButton2);



        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agroAppRepo.loadSpecUser(AgroAppRepo.ADMIN_ID, new AgroAppRepo.SpecificUser() {
                    @Override
                    public void loadSpecUse(User user) {
                        Log.d("user",""+user);
                        Intent intent = new Intent(getActivity(), ChatScreenActivity.class);
                        intent.putExtra(AgroAppRepo.ADMIN_USER, user);
                        startActivity(intent);
                    }
                });

            }
        });
        return view;
    }
}
