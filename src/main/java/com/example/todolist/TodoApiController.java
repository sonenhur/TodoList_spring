package com.example.todolist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController // RESTful API 컨트롤러임을 명시합니다.
@RequestMapping("/api/todos") // 모든 경로 앞에 '/api/todos'를 붙입니다.
public class TodoApiController {

    @Autowired // TodoRepository 객체를 주입받음
    private TodoRepository todoRepository;

    // 할 일 목록 페이지를 보여주는 메서드
    @GetMapping // GET /api/todos : 전체 할 일 목록 조회
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    // 할 일 추가 요청을 처리하는 메서드
    @PostMapping // POST /api/todos : 할 일 추가
    public Todo createTodo(@RequestBody Todo todo) {
        return todoRepository.save(todo);
    }

    // 할 일 삭제 요청을 처리하는 메서드
    @DeleteMapping("/{id}") // DELETE /api/todos/{id} : 할 일 삭제
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        if (todoRepository.existsById(id)) {
            todoRepository.deleteById(id);
            return ResponseEntity.noContent().build(); // 성공 시 204 No Content 응답
        }
        return ResponseEntity.notFound().build(); // ID가 없으면 404 Not Found 응답
    }

    // 할 일 상태 수정 요청을 처리하는 메서드
    @PutMapping("/{id}") // PUT /api/todos/{id} : 할 일 수정
    public ResponseEntity<Todo> updateTodo(@PathVariable Long id, @RequestBody Todo updatedTodo) {
        Optional<Todo> todoOptional = todoRepository.findById(id);
        if (todoOptional.isPresent()) {
            Todo todo = todoOptional.get();
            todo.setContent(updatedTodo.getContent());
            todo.setCompleted(updatedTodo.isCompleted());
            Todo savedTodo = todoRepository.save(todo);
            return ResponseEntity.ok(savedTodo); // 성공 시 200 OK 응답
        }
        return ResponseEntity.notFound().build(); // ID가 없으면 404 Not Found 응답
    }
}