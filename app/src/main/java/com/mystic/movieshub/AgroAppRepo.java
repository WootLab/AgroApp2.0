package com.mystic.movieshub;
import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AgroAppRepo {

    List<Movie> actionMovieList = new ArrayList<>();
    List<Movie> comedyMovieList = new ArrayList<>();
    List<Movie> dramaMovieList = new ArrayList<>();
    private FirebaseDatabase firebaseDatabase;
    List<FarmProduct> farmProductList = new ArrayList<>();
    private static  AgroAppRepo agroAppRepo;
    List<AgriNews> agriNewsCont;
    private FirebaseAuth mAuth;
    private  User user;

    private AgroAppRepo(){
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
    }



    public static AgroAppRepo getInstanceOfAgroApp(){
        if(agroAppRepo == null){
            agroAppRepo = new AgroAppRepo();
        }
        return  agroAppRepo;
    }


    public void uploadProduct(FarmProduct farmProduct){
        //Add to firebase;
    }


    public List<FarmProduct> fetchProduct(){
        //whenever we are fetching the product from this is going to be the plug......
        fetchPro(new FireBaseCallback() {
            @Override
            public void firebaseAgriNews(List<AgriNews> agriNews) {

            }

            @Override
            public void fireBaseUser(User user) {
            }

            @Override
            public void fireBaseProducts(List<FarmProduct> product) {
                farmProductList = product;
            }
        });
        return farmProductList;
    }

    public List<AgriNews> agriNewsFetcher(){

        fetchAgriNews(new FireBaseCallback() {
            @Override
            public void firebaseAgriNews(List<AgriNews> agriNews) {
                agriNewsCont = agriNews;
            }

            @Override
            public void fireBaseUser(User user) {

            }

            @Override
            public void fireBaseProducts(List<FarmProduct> product) {

            }
        });
        return agriNewsCont;
    }

    //Fetches the latest agricultural news for framers
    public void fetchAgriNews(final FireBaseCallback fireBaseCallback){
        final List<AgriNews> agriNewsContainer = new ArrayList<>();
        DatabaseReference mDatebaseReference = firebaseDatabase.getReference("AgriNews");
        mDatebaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    AgriNews agriNews = dataSnapshot.getValue(AgriNews.class);
                    agriNewsContainer.add(agriNews);
                }

                fireBaseCallback.firebaseAgriNews(agriNewsContainer);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void fetchPro(final FireBaseCallback fireBaseCallback){
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mDatebaseReference = firebaseDatabase.getReference("PRODUCTS");
        mDatebaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                farmProductList.clear();
                for(DataSnapshot dataSnapshot :snapshot.getChildren()){
                    FarmProduct farmProduct = dataSnapshot.getValue(FarmProduct.class);
                    farmProductList.add(farmProduct);
                }
                fireBaseCallback.fireBaseProducts(farmProductList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //fetches from base
    private void fetchUser(final FireBaseCallback fireBaseCallback){
        String userId = mAuth.getCurrentUser().getUid();
        DatabaseReference mDatebaseReference = firebaseDatabase.getReference("USERS").child(userId);
        mDatebaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(User.class);
                fireBaseCallback.fireBaseUser(user);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

   //sends to fragmentProfile
    public User getUser(){
        fetchUser(new FireBaseCallback() {
            @Override
            public void firebaseAgriNews(List<AgriNews> agriNews) {
                //gets news
            }

            @Override
            public void fireBaseUser(User basuser) {
                user = basuser;
            }

            @Override
            public void fireBaseProducts(List<FarmProduct> product) {

            }
        });
        return user;
    }

    public interface FireBaseCallback{
        void firebaseAgriNews(List<AgriNews> agriNews);
        void fireBaseUser(User basuser);
        void fireBaseProducts(List<FarmProduct> product);

    }

}
