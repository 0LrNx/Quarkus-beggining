package com.example;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;


// -> https://stackoverflow.com/questions/4555844/what-is-the-difference-between-applicationscoped-and-singleton-scopes-in-cdi
// This means that a single instance of this class is created and shared throughout the application's lifecycle.
// Ideal for the long term, as here => access to a database.
@ApplicationScoped
public class TodoService {
    @Inject
    MongoClient mongoClient; // Injects an instance of MongoClient, an object used to interact with the MongoDB database.


    // This method retrieves all the tasks stored in the MongoDB database and returns them in the form of a list.
    public List<Todo> getAllTodos() {
        List<Todo> list = new ArrayList<>();
        MongoCursor<Document> cursor = getCollection().find().iterator();
        try {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                Todo todo = new Todo();
                todo.setTitle(document.getString("title"));
                todo.setDescription(document.getString("description"));
                todo.setCompleted(document.getBoolean("completed"));
                list.add(todo);
            }
        } finally {
            cursor.close();
        }
        return list;
    }

    // This method adds a new task to the MongoDB collection.
    public Todo addTodo(Todo todo) {
        Document document = new Document()
                .append("title", todo.getTitle())
                .append("description", todo.getDescription())
                .append("completed", todo.isCompleted());
        getCollection().insertOne(document);
        return todo;
    }

    // This method returns the "todos" collection in the "todoapp" database.
    // It is used to both retrieve and insert data into this collection.
    public MongoCollection getCollection() {
        return mongoClient.getDatabase("todoapp").getCollection("todos");
    }
}
