package com.example;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class TodoResourceTest {
//    @Test
//    void testHelloEndpoint() {
//        given()
//                .when().get("/todos")
//                .then()
//                .statusCode(200)
//                .body(is("Hello from Quarkus REST"));
//    }

    @Test
    void testGetAllTodos() {
        given()
                .when().get("/todos")
                .then()
                .statusCode(200)
                .body("$.size()", is(0)); // Permet de check si au départ il n'y a pas de tâches.

    }

    @Test
    public void testCreateTodo() {
        Todo todo = new Todo();
        todo.title = "Learn Quarkus";
        todo.description = "Understand the basics of Quarkus";

        given()
                .contentType("application/json")
                .body(todo)
                .when().post("/todos")
                .then()
                .statusCode(200)
                .body("title", is("Learn Quarkus"));
    }

}