package chapter6.beans;

public class Comment {

    private int id;
    private int userId;
    private int messageId;
    private String text;

    // getter/setterは省略されているので、自分で記述しましょう。

    // getter
    public int getId() {
        return id;
    }
    public int getUserId() {
        return userId;
    }
    public int getMessageId() {
        return messageId;
    }
    public String getText() {
        return text;
    }

    // setter
    public void setId(int id) {
        this.id = id;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }
    public void setText(String text) {
        this.text = text;
    }

}
