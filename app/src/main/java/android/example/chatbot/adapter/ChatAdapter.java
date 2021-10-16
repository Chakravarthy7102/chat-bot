package android.example.chatbot.adapter;

import android.content.Context;
import android.example.chatbot.model.ChatsModel;
import android.example.chatbot.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter {
    private ArrayList<ChatsModel> messageModalArrayList;
    private Context context;

    public ChatAdapter(ArrayList<ChatsModel> messageModalArrayList, Context context) {
        this.messageModalArrayList = messageModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.usera_message, parent, false);
                return new UserViewHolder(view);
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bot_message, parent, false);
                return new BotViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatsModel modal = messageModalArrayList.get(position);
        switch (modal.getSender()) {
            case "user":
                ((UserViewHolder) holder).userTV.setText(modal.getMessage());
                break;
            case "bot":
                ((BotViewHolder) holder).botTV.setText(modal.getMessage());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return messageModalArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        switch (messageModalArrayList.get(position).getSender()) {
            case "user":
                return 0;
            case "bot":
                return 1;
            default:
                return -1;
        }
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView userTV;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            userTV = itemView.findViewById(R.id.userText);
        }
    }

    public static class BotViewHolder extends RecyclerView.ViewHolder {
        TextView botTV;
        public BotViewHolder(@NonNull View itemView) {
            super(itemView);
            botTV = itemView.findViewById(R.id.botText);
        }
    }
}