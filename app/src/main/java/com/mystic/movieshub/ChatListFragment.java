package com.mystic.movieshub;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatListFragment extends Fragment {

    private User userFrom;
    private List<User> users;
    private AgroAppRepo agroAppRepo;
    private RecyclerView recyclerView;
    private ChatlistAdapter contactAdapter;
    private ProgressBar progressBar;
    private TextView noChat;
    private TextView textView;
    private LinearLayoutManager linearLayoutManager;

    public ChatListFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ChatListFragment newInstance(String param1, String param2) {
        ChatListFragment fragment = new ChatListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        agroAppRepo = AgroAppRepo.getInstanceOfAgroApp();
        agroAppRepo.loadRecentChatTwo(contacted -> {
            if(contactAdapter == null){
                contactAdapter = new ChatlistAdapter(contacted, getActivity());
            }

            if(contacted.size() > 0){
                progressBar.setVisibility(View.GONE);
                noChat.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                recyclerView.setAdapter(contactAdapter);
                linearLayoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(linearLayoutManager);
            }else{
                noChat.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }

            contactAdapter.moveToChatScreen(pos -> {
                User user = contacted.get(pos);
                Intent intent = new Intent(getActivity(),ChatScreenActivity.class);
                intent.putExtra("CONTACT",user);
                startActivity(intent);
            });
        });
        /*agroAppRepo.loadRecentChat(new AgroAppRepo.FetchContact() {
            @Override
            public void contactList(List<Chat> contact) {
                if(contactAdapter == null){
                    contactAdapter = new ChatlistAdapter(contact, getActivity());
                }

                if(contact.size() > 0){
                    progressBar.setVisibility(View.GONE);
                    noChat.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView.setAdapter(contactAdapter);
                    linearLayoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(linearLayoutManager);
                }else{
                    noChat.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }

                contactAdapter.moveToChatScreen(new ChatlistAdapter.MyListener() {
                    @Override
                    public void respond(int pos) {
                        Chat chat = contact.get(pos);
                        Intent intent = new Intent(getActivity(),ChatScreenActivity.class);
                        intent.putExtra("CONTACT",chat);
                        startActivity(intent);
                    }
                });
            }
        });*/

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat_list, container, false);
        recyclerView = view.findViewById(R.id.chatholder);
        progressBar = view.findViewById(R.id.progressBar6);
        noChat = view.findViewById(R.id.textView28);
        return view;
    }
}