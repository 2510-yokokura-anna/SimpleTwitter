package chapter6.beans;

import java.util.Date;

public class UserComment {

    private int id;
    private String account;
    private String name;
    private int userId;
    private int messageId;
    private String text;
    private Date createdDate;

    // getter/setterは省略されているので、自分で記述しましょう。

    // getter
    public int getId() {
        return id;
    }
    public String getAccount() {
        return account;
    }
    public String getName() {
        return name;
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
    public Date getCreatedDate() {
        return createdDate;
    }

    // setter
    public void setId(int id) {
        this.id = id;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    public void setName(String name) {
        this.name = name;
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
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

}
