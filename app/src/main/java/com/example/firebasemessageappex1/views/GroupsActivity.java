package com.example.firebasemessageappex1.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.firebasemessageappex1.R;
import com.example.firebasemessageappex1.databinding.ActivityGroupsBinding;
import com.example.firebasemessageappex1.model.ChatGroup;
import com.example.firebasemessageappex1.viewmodel.MyViewModel;
import com.example.firebasemessageappex1.views.adapter.GroupAdapter;

import java.util.ArrayList;
import java.util.List;

public class GroupsActivity extends AppCompatActivity {

    private ArrayList<ChatGroup> chatGroupArrayList;
    private RecyclerView recyclerView;
    private ActivityGroupsBinding activityGroupsBinding;
    private GroupAdapter groupAdapter;
    private MyViewModel myViewmodel;

    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        activityGroupsBinding = DataBindingUtil.setContentView(this, R.layout.activity_groups);

        myViewmodel = new ViewModelProvider(this).get(MyViewModel.class);

        recyclerView = activityGroupsBinding.recyclerview;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        myViewmodel.getGroupList().observe(this, new Observer<List<ChatGroup>>() {
            @Override
            public void onChanged(List<ChatGroup> chatGroups) {

                chatGroupArrayList = new ArrayList<>();

                chatGroupArrayList.addAll(chatGroups);

                groupAdapter = new GroupAdapter(chatGroupArrayList);
                recyclerView.setAdapter(groupAdapter);
                groupAdapter.notifyDataSetChanged();


            }
        });

        activityGroupsBinding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }
    public void showDialog(){

        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        View view = LayoutInflater.from(this).inflate(R.layout.dialog_layout,null);

        dialog.setContentView(view);
        dialog.show();;
        Button submit = view.findViewById(R.id.submit_btn);
        EditText editText = view.findViewById(R.id.chat_Group_edt);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String groupName = editText.getText().toString();

                Toast.makeText(GroupsActivity.this,"Your Chat Group" +  groupName, Toast.LENGTH_SHORT).show();

                myViewmodel.createNewGroup(groupName);

                dialog.dismiss();
            }
        });
    }
}