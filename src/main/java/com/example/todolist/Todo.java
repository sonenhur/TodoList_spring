// src/main/java/com/example/todolist/Todo.java
package com.example.todolist;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity // 이 클래스가 데이터베이스 테이블과 매핑됨을 나타냅니다.
public class Todo {

    @Id // 기본 키(Primary Key)를 지정합니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 값을 자동으로 생성합니다.
    private Long id;
    private String content; // 할 일 내용
    private boolean completed; // 완료 여부

    // 기본 생성자 (JPA를 위해 필요)
    public Todo() {
    }

    // getters and setters (IDE에서 자동 생성 가능)
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