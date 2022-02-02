package com.api.server;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("todos")
public class Todo {

    private String id;
    private String text;
    private boolean isComplete;

     
    public Todo(String id, String text, boolean isComplete) {
      this.id = id;
      this.text = text;
      this.isComplete = isComplete;
    }

    public String getText() {
      return text;
    }

    public String getId() {
      return id;
    }

    public boolean getIsComplete() {
      return isComplete;
    }

    public void setIsComplete(boolean isComplete) {
      this.isComplete = isComplete;
    }

  
}
