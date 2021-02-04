package com.mystic.movieshub;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.LinearLayout;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AgroAppRepo {

    public static final String ADMIN_USER = "admin_user";
    public static final String APPROVEDFARMERS = "APPROVEDFARMERS";
    public static final String USERS = "USERS";
    private final FirebaseDatabase firebaseDatabase;
    private static  AgroAppRepo agroAppRepo;
    private final FirebaseAuth mAuth;
    private User user;
    private  FarmProduct farmProduct;
    private List<FarmProduct> farmProductList ;
    private List<Chat> chatContainer;
    private List<AgriNews> agriNewsContainer;
    public static final String PRODUCT = "product";
    public static final String AGRIC_NEWS = "AgricNews";
    public static final String ADMIN_ID = "P3O1w3u7K4NLY7zA6OS7pg3N8633";
    private List<User> qualifiedFarmers;
    private ProgressDialog progressDialog;
    //private ProgressBar bar;


    private AgroAppRepo(){
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        farmProductList = new ArrayList<>();
        agriNewsContainer = new ArrayList<>();
        chatContainer = new ArrayList<>();
        qualifiedFarmers = new ArrayList<>();
    }


    public static AgroAppRepo getInstanceOfAgroApp(){
        if(agroAppRepo == null){
            agroAppRepo = new AgroAppRepo();
        }
        return  agroAppRepo;
    }


    public void uploadProduct(FarmProduct farmProduct, final Context context){
        DatabaseReference mDatabaseReference = firebaseDatabase.getReference(PRODUCT);
        String productId = mDatabaseReference.push().getKey();
        farmProduct.setProductId(productId);
        mDatabaseReference.child(productId).setValue(farmProduct)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(context,"Succesfully added",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(context,"Something went wrong",Toast.LENGTH_LONG).show();
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
        agriNewsContainer = new ArrayList<>();
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

        String newsId = mDatabaseReference.push().getKey();
        agriNews.setNewsId(newsId);
        mDatabaseReference.child(newsId).setValue(agriNews).addOnCompleteListener(new OnCompleteListener<Void>() {
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
        DatabaseReference mDatebaseReference = firebaseDatabase.getReference(PRODUCT);


        mDatebaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                farmProductList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    farmProduct = dataSnapshot.getValue(FarmProduct.class);
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
        DatabaseReference mDatebaseReference = firebaseDatabase.getReference(USERS).child(userId);
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
                            DatabaseReference mDatebaseReference = firebaseDatabase.getReference(USERS);
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


    public void sendMessage(final String message, final String receiverId, final Context context){
        if(!message.equals("")){
            fetchUser(new FireBaseCallbackUser() {
                @Override
                public void fireBaseUser(User basuser) {
                    String senderId = basuser.getUid();
                    DatabaseReference reference = firebaseDatabase.getReference("CHAT");
                    String chatId = reference.push().getKey();
                    Chat chat = new Chat(message,chatId,senderId,receiverId);
                    assert chatId != null;
                    reference.child(chatId).setValue(chat)
                            .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context,"Message cant be sent",Toast.LENGTH_LONG).show();
                        }
                    })
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(context,"Message was sent",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            });
        }else{
            Toast.makeText(context,"Message cant be sent",Toast.LENGTH_LONG).show();
        }

    }



    public void loadMessages(final FireBaseMessages fireBaseMessages, final String receiverId){
        final String senderId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Log.d("SenderId",senderId);
        DatabaseReference databaseChats = FirebaseDatabase.getInstance().getReference("CHAT");
        databaseChats.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatContainer.clear();
                Log.d("FirebaseMessages","I am in ondatachanged");
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Chat chat = dataSnapshot.getValue(Chat.class);

                    Log.d("SenderId",chat.getSenderId()+"="+senderId);

                    Log.d("ReceiverId",chat.getReceiverId()+"="+receiverId);
                    if(chat.getSenderId().equals(senderId) && chat.getReceiverId().equals(receiverId)
                            || chat.getSenderId().equals(receiverId) && chat.getReceiverId().equals(senderId)){
                        Log.d("FirebaseMessages","I am in the loop and passed the conditions");
                        chatContainer.add(chat);
                    }
                }
                Log.d("FirebaseMessages",""+chatContainer.size());
                fireBaseMessages.firebaseMessages(chatContainer);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void loadSpecUser(String userdId, final SpecificUser userCallback){
        DatabaseReference databaseSpecUser = FirebaseDatabase.getInstance().getReference(USERS);
        databaseSpecUser.child(userdId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                userCallback.loadSpecUse(user);
                Log.d("Userc",""+user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void logOut(Context context){
        mAuth.signOut();
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }



    public void removeProduct(String productId, final Context context){
        DatabaseReference mDatabaseReference = firebaseDatabase.getReference(PRODUCT);
        mDatabaseReference
                .child(productId)
                .removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(context,"Advert was removed",Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    private String getFileExtension(Context context, Uri uri){
        // This was just a test
        ContextWrapper rapper = new ContextWrapper(context);
        ContentResolver resolver = rapper.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(resolver.getType(uri));
    }


    public void applyForLoans(final String userId, String location, String description, List<Uri> imageList, String agricType, final Context context){
        DatabaseReference mDatabaseReference = firebaseDatabase.getReference(USERS);
        final DatabaseReference mDatabaseReferenceApproved = firebaseDatabase.getReference(APPROVEDFARMERS);
        StorageReference mStorageRef = FirebaseStorage.getInstance().getReference("FARMIMAGES").child(userId);
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait while we upload your details.................");
        progressDialog.show();
        for(Uri inuri : imageList){
            final StorageReference imageName = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(context,inuri));
            imageName.putFile(inuri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            imageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                        }
                    });
        }

        Requirements requirements = new Requirements();
        requirements.setLocation(location);
        requirements.setDescription(description);
        requirements.setImages(imageList);
        requirements.setAgricTypes(agricType);
        requirements.setEligible(true);
        mDatabaseReference.child(userId).setValue(requirements).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                   /* loadSpecUser(userId, new SpecificUser() {
                        @Override
                        public void loadSpecUse(User user) {
                            mDatabaseReferenceApproved.child(userId).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(context,"You applied",Toast.LENGTH_LONG).show();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context,"There was an error",Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    });*/
                    // An error might stem from her seince am not sure if the user that we get from dis process below will come with the information that the user has
                    User user = new User(userId);
                    mDatabaseReferenceApproved.child(userId).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(context,"You applied",Toast.LENGTH_LONG).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context,"There was an error",Toast.LENGTH_LONG).show();
                        }
                    });


                    progressDialog.dismiss();
                }else{
                    Toast.makeText(context,"Application was not succesful",Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }
        });


    }


    public void fetchSelectedfarmers(final String local, final String state, final String typeoffarming, final ProgressBar bar, final FetchQualifiedfarmers fetchQualifiedfarmers){
        bar.setVisibility(View.VISIBLE);
        DatabaseReference mDatebaseReference = firebaseDatabase.getReference(APPROVEDFARMERS);
        mDatebaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    User user = dataSnapshot.getValue(User.class);
                    Requirements requirements = user.getRequirements();
                    if(requirements.getLocalgov().equals(local)
                            && requirements.getState().equals(state)
                            && requirements.getAgricTypes().equals(typeoffarming))
                    {
                        qualifiedFarmers.add(user);
                    }
                }
                bar.setVisibility(View.GONE);
                fetchQualifiedfarmers.firebaseQualifiedFarmers(qualifiedFarmers);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                bar.setVisibility(View.GONE);
            }
        });
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


    public interface FireBaseMessages{
        void firebaseMessages(List<Chat> chatCont);
    }



    public interface SpecificUser{
        void loadSpecUse(User user);
    }



    public interface FetchQualifiedfarmers{
        void firebaseQualifiedFarmers(List<User> qualifiedfarmers);
    }




}
