// src/main/java/com/example/todolist/Todo.java
package com.example.todolist;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity // 이 클래스가 데이터베이스 테이블과 매핑됨을 나타냄
public class Todo {

    @Id // 기본 키(Primary Key) 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 값을 자동으로 생성
    private Long id;
    private String content;
    private boolean completed;

    // 기본 생성자 (JPA를 위해 필요)
    public Todo() {
    }
    // getters and setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public boolean isCompleted() {
        return completed;
    }
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}