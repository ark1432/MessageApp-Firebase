package com.example.firebasemessageappex1.views.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasemessageappex1.BR;
import com.example.firebasemessageappex1.R;
import com.example.firebasemessageappex1.databinding.RowChatBinding;
import com.example.firebasemessageappex1.model.MessageChatModel;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private List<MessageChatModel> chatMessageList;
    private Context context;

    public ChatAdapter(List<MessageChatModel> chatMessageList, Context context) {
        this.chatMessageList = chatMessageList;
        this.context = context;
    }

    @NonNull
    @Override
    public ChatAdapter.ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowChatBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.row_chat,parent,false);
        return new ChatViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ChatViewHolder holder, int position) {
        holder.getBinding().setVariable(BR.chatMessage, chatMessageList.get(position));
        holder.getBinding().executePendingBindings();

    }

    @Override
    public int getItemCount() {
        return chatMessageList.size();
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder {

        private RowChatBinding binding;

        public ChatViewHolder(RowChatBinding binding) {
            super(binding.getRoot());
            setBinding(binding);

        }

        public RowChatBinding getBinding() {
            return binding;
        }

        private void setBinding(RowChatBinding binding) {
            this.binding =binding;
        }
    }

}
