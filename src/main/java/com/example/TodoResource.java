package com.example;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/todos") // All the methods inside this class will respond to HTTP requests addressed to "/todos".
public class TodoResource {

    @Inject
    TodoService todoService; // Injects an instance of the TodoService class. This provides access to the methods defined in TodoService.

    @Inject
    Template todos;
    // Inject the HTML template named "todos" located in the "/resources/templates/todos.html" folder.
    // It will be used to generate the HTML view of the to-do list.


    // This method will be called when an HTTP GET request is sent to "/todos". It indicates that the response will be HTML ("text/html").
    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getTodos() {
        return todos.data("todos", todoService.getAllTodos());
    }

    // This method will be called when an HTTP POST request is sent to "/todos".
    @POST
    @Consumes(MediaType.APPLICATION_JSON) // It expects a request body in JSON format (indicated by "application/json").s
    @Produces(MediaType.APPLICATION_JSON) // The response will also be sent as JSON.
    public Response addTodo(Todo todo) {
        Todo addedTodo = todoService.addTodo(todo);
        return Response.status(Response.Status.CREATED).entity(addedTodo).build(); // This line constructs an HTTP response with a status code of 201 (Created), indicating that the task has been successfully created.
    }
}
