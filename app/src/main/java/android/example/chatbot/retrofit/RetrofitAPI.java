package android.example.chatbot.retrofit;

import android.example.chatbot.model.MessageModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RetrofitAPI {
    @GET
    Call<MessageModel> getMessage(@Url String url);
}
