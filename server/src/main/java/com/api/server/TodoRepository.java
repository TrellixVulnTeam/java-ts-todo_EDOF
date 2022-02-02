package com.api.server;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface TodoRepository extends MongoRepository<Todo, String> {
    
   @Query("{isComplete : ?}")
   List<Todo> findByCompleteTodos(boolean isComplete);
    

}
