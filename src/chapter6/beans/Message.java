package chapter6.beans;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {

    private int id;
    private int userId;
    private String text;
    private Date createdDate;
    private Date updatedDate;

    // getter/setterは省略されているので、自分で記述しましょう。

    // getter
    public int getId() {
        return id;
    }
    public int getUserId() {
        return userId;
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
    public void setText(String text) {
        this.text = text;
    }
}