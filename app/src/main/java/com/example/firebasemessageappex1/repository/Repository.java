package com.example.firebasemessageappex1.repository;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.firebasemessageappex1.model.ChatGroup;
import com.example.firebasemessageappex1.model.MessageChatModel;
import com.example.firebasemessageappex1.views.GroupsActivity;
import com.google.android.gms.tasks.OnCompleteListener;
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

public class Repository {

    MutableLiveData<List<ChatGroup>> chatMutableLiveData;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference groupDatabaseReference;
    MutableLiveData<List<MessageChatModel>> messageMutableLiveData;

    public Repository(){
        this.chatMutableLiveData =new MutableLiveData<>();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        messageMutableLiveData=new MutableLiveData<>();
    }

    public void firebaseAnonymousAuth(Context context) {

        FirebaseAuth.getInstance().signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    Intent i = new Intent(context, GroupsActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                }

            }
        });
    }

    public String getCurrentId() {
        return FirebaseAuth.getInstance().getUid();
    }

    public void signOut() {

        FirebaseAuth.getInstance().signOut();
    }

    public MutableLiveData<List<ChatGroup>> getChatMutableLiveData() {
        List<ChatGroup> chatGroupList = new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    ChatGroup group = new ChatGroup(dataSnapshot.getKey());

                    chatGroupList.add(group);

                }

                chatMutableLiveData.postValue(chatGroupList);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return chatMutableLiveData;
    }

    public void createNewChatGroup(String groupname) {
        databaseReference.child(groupname).setValue(groupname);
    }

    public MutableLiveData<List<MessageChatModel>> getMessageMutableLiveData(String groupName) {
        groupDatabaseReference = firebaseDatabase.getReference().child(groupName);

        List<MessageChatModel> mesageChatModelList = new ArrayList<>();
        groupDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mesageChatModelList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    MessageChatModel mesageChatModel = dataSnapshot.getValue(MessageChatModel.class);

                    mesageChatModelList.add(mesageChatModel);

                }

                messageMutableLiveData.postValue(mesageChatModelList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return messageMutableLiveData;
    }

    public void sendMessage(String messageText, String chatGroup) {

        DatabaseReference reference = firebaseDatabase.getReference(chatGroup);

        if (!messageText.trim().equals("")) {

            MessageChatModel messageChatModel = new MessageChatModel(FirebaseAuth.getInstance().getCurrentUser().getUid(), messageText, System.currentTimeMillis());

            String randomkey = reference.push().getKey();
            reference.child(randomkey).setValue(messageChatModel);

        }
    }

}
