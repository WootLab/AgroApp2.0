package com.mystic.movieshub;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class AgroAppRepo {

    private FirebaseDatabase firebaseDatabase;
    private List<FarmProduct> farmProductList = new ArrayList<>();
    private static  AgroAppRepo agroAppRepo;
    private List<AgriNews> agriNewsCont;
    private FirebaseAuth mAuth;
    private User user;

    private ProgressBar bar;


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


    public void uploadProduct(FarmProduct farmProduct, final Context context){
        DatabaseReference mDatabaseReference = firebaseDatabase.getReference("PRODUCTS");
        mDatabaseReference.setValue(farmProduct)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(context,"Succesfully added",Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
                        //Action was not succesful
                    }
                });
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


    public void signUp(final String email, final String password, final Context context, final ProgressBar bar, final String phone, final String name, final Uri uri, final String role){
        bar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context,"error"+e.getMessage(),Toast.LENGTH_LONG).show();
                        bar.setVisibility(View.GONE);
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            bar.setVisibility(View.GONE);
                            Toast.makeText(context, "You have signed up", Toast.LENGTH_LONG).show();
                            //This is where we set the values we want our users to have
                            String userId = mAuth.getCurrentUser().getUid();
                            DatabaseReference mDatebaseReference = firebaseDatabase.getReference("USERS");
                            User user = new User(userId);
                            user.setName(name);
                            user.setImage(uri.toString());
                            user.setEmail(email);
                            user.setPassword(password);
                            user.setPhoneNumber(phone);
                            user.setRole(role);
                            mDatebaseReference.child(userId)
                                    .setValue(user)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                login(email, password, context,bar);
                                            }

                                            if(!task.isSuccessful()){
                                                Toast.makeText(context,"not working",Toast.LENGTH_LONG).show();
                                                Log.d("Repo","Not complete");
                                                bar.setVisibility(View.GONE);
                                            }

                                            if (task.isCanceled()) {
                                                Toast.makeText(context, "action was cancelled", Toast.LENGTH_LONG).show();
                                                bar.setVisibility(View.GONE);
                                            }

                                        }
                                    });



                        }

                        if(task.isCanceled()){
                            Toast.makeText(context, "Process was cancelled", Toast.LENGTH_LONG).show();
                            bar.setVisibility(View.GONE);
                        }

                    }
                });

    }

    public void login(String email, String password, final Context context, final ProgressBar bar) {
        if (email != null && password != null) {
            bar.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
                            Log.d("Login error", Objects.requireNonNull(e.getMessage()));
                            bar.setVisibility(View.GONE);
                        }
                    })
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                //Go to the next Activity
                                bar.setVisibility(View.GONE);
                                Intent intent = new Intent(context,MainActivity.class);
                                context.startActivity(intent);
                            }
                        }
                    });

        }else{
            Toast.makeText(context,"Email or password is empty",Toast.LENGTH_LONG).show();
        }

    }


    public void uploadNews(AgriNews agriNews, final Context context){
        DatabaseReference mDatabaseReference = firebaseDatabase.getReference("NEWS");
        mDatabaseReference.setValue(agriNews).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(context,"Succesfully added",Toast.LENGTH_LONG).show();
                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context,"Error occured"+e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
    }

    public interface FireBaseCallback{
        void firebaseAgriNews(List<AgriNews> agriNews);
        void fireBaseUser(User basuser);
        void fireBaseProducts(List<FarmProduct> product);

    }

}
