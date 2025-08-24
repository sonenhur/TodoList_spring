// src/main/java/com/example/todolist/TodoRepository.java
package com.example.todolist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    // JpaRepository를 상속받는 것만으로도 기본적인 CRUD 기능이 자동으로 제공됩니다.
    // (예: save(), findById(), findAll(), deleteById() 등)
}