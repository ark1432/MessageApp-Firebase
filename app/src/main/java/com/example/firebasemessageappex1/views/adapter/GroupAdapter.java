package com.example.firebasemessageappex1.views.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasemessageappex1.views.ChatActivity;
import com.example.firebasemessageappex1.R;
import com.example.firebasemessageappex1.databinding.ItemCardBinding;
import com.example.firebasemessageappex1.model.ChatGroup;

import java.util.ArrayList;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {


    private ArrayList<ChatGroup> groupArrayList;

    public GroupAdapter(ArrayList<ChatGroup> groupArrayList) {
        this.groupArrayList = groupArrayList;
    }


    @NonNull
    @Override
    public GroupAdapter.GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCardBinding binding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_card,parent,false);
        return new GroupViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupAdapter.GroupViewHolder holder, int position) {
    ChatGroup chatGroup= groupArrayList.get(position);
    holder.itemCardBinding.setChatGroup(chatGroup);

    }

    @Override
    public int getItemCount() {
        return groupArrayList.size();
    }

    public class GroupViewHolder extends RecyclerView.ViewHolder {

        private ItemCardBinding  itemCardBinding;


        public GroupViewHolder(ItemCardBinding itemCardBinding) {
            super(itemCardBinding.getRoot());
            this.itemCardBinding= itemCardBinding;

            itemCardBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();

                    ChatGroup chatGroup = groupArrayList.get(position);

                    Intent i = new Intent(v.getContext(), ChatActivity.class);
                    i.putExtra("GroupName", chatGroup.getGroupname());
                    v.getContext().startActivity(i);

                }
            });
        }
    }
}
