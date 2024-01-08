package com.example.firebasemessageappex1.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.firebasemessageappex1.R;
import com.example.firebasemessageappex1.databinding.ActivityChatBinding;
import com.example.firebasemessageappex1.model.MessageChatModel;
import com.example.firebasemessageappex1.viewmodel.MyViewModel;
import com.example.firebasemessageappex1.views.adapter.ChatAdapter;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private ActivityChatBinding activityChatBinding;
    private MyViewModel myViewmodel;

    private RecyclerView recyclerView;

    private ChatAdapter chatAdapter;

    private List<MessageChatModel> messageChatModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        activityChatBinding = DataBindingUtil.setContentView(this, R.layout.activity_chat);

        myViewmodel = new ViewModelProvider(this).get(MyViewModel.class);

        recyclerView = activityChatBinding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        String groupName = getIntent().getStringExtra("GroupName");

        myViewmodel.getMessageLiveData(groupName).observe(this, new Observer<List<MessageChatModel>>() {
            @Override
            public void onChanged(List<MessageChatModel> messageChatModels) {

                messageChatModelList = new ArrayList<>();

                messageChatModelList.addAll(messageChatModels);

                chatAdapter = new ChatAdapter(messageChatModelList, getApplicationContext());
                recyclerView.setAdapter(chatAdapter);
                chatAdapter.notifyDataSetChanged();

                int latestposition = chatAdapter.getItemCount() - 1;
                if (latestposition > 0) {
                    recyclerView.smoothScrollToPosition(latestposition);
                }


            }
        });

        activityChatBinding.setVModel(myViewmodel);
        activityChatBinding.sendBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String msg = activityChatBinding.edittextChatMessage.getText().toString();
                myViewmodel.sendMessage(msg, groupName);

                activityChatBinding.edittextChatMessage.getText().clear();
            }
        });
    }
}