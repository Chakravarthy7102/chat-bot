package android.example.chatbot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.example.chatbot.adapter.ChatAdapter;
import android.example.chatbot.model.ChatsModel;
import android.example.chatbot.model.MessageModel;
import android.example.chatbot.retrofit.RetrofitAPI;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView chatsRV;
    private FloatingActionButton sendMsgFAB;
    private EditText userMsgEdt;
    private final String USER_KEY = "user";
    private final String BOT_KEY = "bot";
    private ArrayList<ChatsModel> messageModalArrayList;
    private ChatAdapter messageRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chatsRV = findViewById(R.id.chatRecyclerView);
        sendMsgFAB = findViewById(R.id.sendFAB);
        userMsgEdt = findViewById(R.id.chatTextEdit);
        messageModalArrayList = new ArrayList<>();
        messageRVAdapter = new ChatAdapter(messageModalArrayList, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false);
        chatsRV.setLayoutManager(linearLayoutManager);
        chatsRV.setAdapter(messageRVAdapter);

        sendMsgFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userMsgEdt.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter your message..", Toast.LENGTH_SHORT).show();
                    return;
                }
                sendMessage(userMsgEdt.getText().toString());
                userMsgEdt.setText("");
            }
        });
    }

    private void sendMessage(String message) {
        messageModalArrayList.add(new ChatsModel(message, USER_KEY));
        messageRVAdapter.notifyDataSetChanged();
        String url = "http://api.brainshop.ai/get?bid=160440&key=O2rmDYkm4kkXko37&uid=satoshinakamoto&msg=" + message;//mashape
        String BASE_URL = "http://api.brainshop.ai/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<MessageModel> call = retrofitAPI.getMessage(url);
        call.enqueue(new Callback<MessageModel>() {
            @Override
            public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {

                    MessageModel modal = response.body();
                    messageModalArrayList.add(new ChatsModel(modal.getCnt(), BOT_KEY));
                    messageRVAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<MessageModel> call, Throwable t) {
                messageModalArrayList.add(new ChatsModel("Can you please revert", BOT_KEY));
                Log.d("MainActivty","thoes because "+t);
                messageRVAdapter.notifyDataSetChanged();
            }


        });
    }
}

