package com.mystic.movieshub;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

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
    List<FarmProduct> farmProducts;
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
                farmProducts = product;
                bar.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
                adapter = new PlatformAdapter(product,getActivity());
                mRecyclerView.setAdapter(adapter);
                adapter.setOnAdapterListener(new PlatformAdapter.CustomeAdapterListener() {
                    @Override
                    public void adapterListener(int position) {
                        FarmProduct specProd = product.get(position);
                        Intent intent = new Intent(getActivity(), SpecificProductActivity.class);
                        intent.putExtra(SpecificProductActivity.PRODUCT,specProd);
                        startActivity(intent);
                    }

                    @Override
                    public void deleteAdvert(int position) {
                        FarmProduct specProd = product.get(position);
                        String id = specProd.getProductId();
                        agroAppRepo.removeProduct(id,getContext());
                        adapter.notifyItemChanged(position);
                    }
                });
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


        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to the layout to send message to admin;
                //On the products you want
                agroAppRepo.loadSpecUser(AgroAppRepo.ADMIN_ID, new AgroAppRepo.SpecificUser() {
                    @Override
                    public void loadSpecUser(User user) {
                        Intent intent = new Intent(getActivity(), ChatScreenActivity.class);
                        intent.putExtra(AgroAppRepo.ADMIN_USER,user);
                        startActivity(intent);
                    }
                });


            }
        });
        return view;
    }
}