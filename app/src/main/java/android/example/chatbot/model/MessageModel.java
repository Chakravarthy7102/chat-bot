package android.example.chatbot.model;

public class MessageModel {
    private String cnt;

    public MessageModel(String cnt) {
        this.cnt = cnt;
    }

    public String getCnt() {
        return cnt;
    }

    public void setCnt(String cnt) {
        this.cnt = cnt;
    }
}
