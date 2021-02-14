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
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChatListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatListFragment newInstance(String param1, String param2) {
        ChatListFragment fragment = new ChatListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        agroAppRepo = AgroAppRepo.getInstanceOfAgroApp();
        agroAppRepo.loadRecentChat(new AgroAppRepo.FetchContact() {
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
        });


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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