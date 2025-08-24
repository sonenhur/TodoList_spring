package com.example.todolist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TodoController {

    @Autowired // TodoRepository 객체를 주입받음
    private TodoRepository todoRepository;

    // 할 일 목록 페이지를 보여주는 메서드
    @GetMapping("/")
    public String list(Model model) {
        // 모든 할 일 데이터를 가져와서 "todos"라는 이름으로 뷰에 전달
        model.addAttribute("todos", todoRepository.findAll());
        // todos.html 템플릿 파일을 반환
        return "todos";
    }

    // 할 일 추가 요청을 처리하는 메서드
    @PostMapping("/add")
    public String addTodo(@RequestParam String content) {
        Todo todo = new Todo();
        todo.setContent(content);
        todo.setCompleted(false); // 초기 상태는 완료되지 않음
        todoRepository.save(todo); // 데이터베이스에 저장
        return "redirect:/"; // 목록 페이지로 리다이렉트
    }

    // 할 일 삭제 요청을 처리하는 메서드
    @PostMapping("/delete/{id}")
    public String deleteTodo(@PathVariable Long id) {
        todoRepository.deleteById(id); // ID에 해당하는 할 일을 삭제
        return "redirect:/"; // 목록 페이지로 리다이렉트
    }

    // 할 일 완료 상태를 토글하는 메서드
    @PostMapping("/toggle/{id}")
    public String toggleTodoCompletion(@PathVariable Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow();
        todo.setCompleted(!todo.isCompleted());
        todoRepository.save(todo);
        return "redirect:/";
    }

    // 할 일 수정 요청을 처리하는 메서드 추가
    @PostMapping("/edit/{id}")
    public String editTodo(@PathVariable Long id, @RequestParam String content) {
        Todo todo = todoRepository.findById(id).orElseThrow();
        todo.setContent(content);
        todoRepository.save(todo); // 수정된 내용으로 저장
        return "redirect:/"; // 목록 페이지로 리다이렉트
    }
}