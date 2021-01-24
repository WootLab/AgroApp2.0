package com.mystic.movieshub;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AgroAppRepo {

    private FirebaseDatabase firebaseDatabase;
    private static  AgroAppRepo agroAppRepo;
    private FirebaseAuth mAuth;
    private User user;

    public static final String PRODUCT = "product";
    public static final String AGRIC_NEWS = "AgricNews";
    //private ProgressBar bar;


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
        DatabaseReference mDatabaseReference = firebaseDatabase.getReference(PRODUCT);
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


    //Fetches the latest agricultural news for framers
    public void fetchAgriNews(final FireBaseCallbackAgriNews fireBaseCallback){
        final List<AgriNews> agriNewsContainer = new ArrayList<>();
        DatabaseReference mDatebaseReference = firebaseDatabase.getReference(AGRIC_NEWS);
        mDatebaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                agriNewsContainer.clear();
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


    //Uploads the news to the database
    public void uploadNews(AgriNews agriNews, final Context context){
        DatabaseReference mDatabaseReference = firebaseDatabase.getReference(AGRIC_NEWS);
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

    public void fetchPro(final FireBaseCallbacProduct fireBaseCallback){
        final List<FarmProduct> farmProductList = new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference mDatebaseReference = firebaseDatabase.getReference(PRODUCT);
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
    public void fetchUser(final FireBaseCallbackUser fireBaseCallback){
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




    public interface FireBaseCallbackAgriNews{
        void firebaseAgriNews(List<AgriNews> agriNews);
    }


    public interface FireBaseCallbackUser {
        void fireBaseUser(User basuser);
    }

    public interface FireBaseCallbacProduct {
        void fireBaseProducts(List<FarmProduct> product);
    }

}
