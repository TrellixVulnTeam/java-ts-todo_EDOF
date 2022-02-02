package com.api.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
@RestController
public class TodoController {

  @Autowired
  TodoRepository repo;

  @RequestMapping("/")
	public @ResponseBody String greeting() {
		return "Hello, World";
	}

  @PostMapping(value = "/todos", consumes = "application/json", produces = "application/json")
    public ResponseEntity<HttpStatus> saveTodo(@RequestBody Todo todo) {
      try {
        repo.save(todo);
        return new ResponseEntity<>(HttpStatus.CREATED);
      } catch (Exception e) {
       return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
  
    }

  @RequestMapping("/todos")
  public ResponseEntity<List<Todo>> getAllTodos() {
    try {
    List<Todo> todos = new ArrayList<Todo>();

    repo.findAll().forEach(todos::add);

    if (todos.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    return new ResponseEntity<>(todos, HttpStatus.OK);
    
  } catch (Exception e) {
    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
  }
  }

  @GetMapping(value = "/todos/{id}", consumes = "application/json", produces = "application/json")
  public ResponseEntity<Todo> getTodo(@PathVariable("id") String id) {
    Optional<Todo> todo = repo.findById(id);

    if(todo.isPresent()) {
      Todo _todo = todo.get();
      return new ResponseEntity<>(_todo, HttpStatus.OK);
      }
      else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
  }


    @PutMapping(value = "/todos/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Todo> editTodo(@PathVariable("id") String id) {

      Optional<Todo> todo = repo.findById(id);
      if(todo.isPresent()) {
        Todo _todo = todo.get();
        if(_todo.getIsComplete()) {
          _todo.setIsComplete(false);
        } else {
           _todo.setIsComplete(true);
        }
        return new ResponseEntity<>(repo.save(_todo), HttpStatus.OK);
      }
      else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }

      
    }

    @DeleteMapping("/todos")
    public ResponseEntity<HttpStatus> deleteAllTodos() {
      try {
        repo.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
  }
    


  @DeleteMapping("/todos/{id}")
public ResponseEntity<HttpStatus> deleteTodo(@PathVariable("id") String id) {
  try {
    repo.deleteById(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  } catch (Exception e) {
    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
  }
}


 @RequestMapping("/todos/completed")
  public ResponseEntity<List<Todo>> getCompletedTodos() {
    try {
    List<Todo> todos = new ArrayList<Todo>();

    repo.findByCompleteTodos(true).forEach(todos::add);

    if (todos.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    return new ResponseEntity<>(todos, HttpStatus.OK);
    
  } catch (Exception e) {
    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
  }
  }

  @RequestMapping("/todos/uncompleted")
  public ResponseEntity<List<Todo>> getUncompletedTodos() {
    try {
    List<Todo> todos = new ArrayList<Todo>();

    repo.findByCompleteTodos(false).forEach(todos::add);

    if (todos.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    return new ResponseEntity<>(todos, HttpStatus.OK);
    
  } catch (Exception e) {
    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
  }
  }
  
}
