// script.js - await 누락 없이 수정된 버전
document.addEventListener('DOMContentLoaded', async () => {
    const todoInput  = document.getElementById('todo-input');
    const addButton  = document.getElementById('add-btn');
    const todoList   = document.getElementById('todo-list');
    const API_URL    = 'http://localhost:8080/api/todos';

    // 모든 호출에 await 추가
    const fetchTodos = async () => {
        const response = await fetch(API_URL);
        const todos    = await response.json();
        todoList.innerHTML = '';
        todos.forEach(todo => renderTodo(todo));
    };

    const renderTodo = (todo) => {
        const li = document.createElement('li');
        li.classList.add('todo-item');
        li.dataset.id = todo.id;

        const span = document.createElement('span');
        span.classList.add('todo-content');
        span.textContent = todo.content;
        if (todo.completed) {
            li.classList.add('completed');
        }

        const btnGroup = document.createElement('div');
        btnGroup.classList.add('btn-group');

        // 완료 토글 버튼
        const toggleBtn = document.createElement('button');
        toggleBtn.textContent = todo.completed ? 'undo' : '✓';
        toggleBtn.classList.add('toggle-btn');
        toggleBtn.addEventListener('click', async () => {
            // 즉시 UI 업데이트
            li.classList.toggle('completed');
            const updatedTodo = { ...todo, completed: !todo.completed };

            // 서버 PUT 요청
            const response = await fetch(`${API_URL}/${todo.id}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(updatedTodo)
            });

            if (!response.ok) {
                console.error('Network response was not ok');
                // 실패 시 UI 복구
                li.classList.toggle('completed');
                return;
            }

            // 성공 시 목록 다시 불러오기
            await fetchTodos();
        });

        // 편집 버튼
        const editBtn = document.createElement('button');
        editBtn.textContent = '✏️';
        editBtn.classList.add('edit-btn');
        editBtn.addEventListener('click', async () => {
            const newContent = prompt('할 일을 수정하세요:', todo.content);
            if (newContent && newContent.trim() !== '') {
                const updatedTodo = { ...todo, content: newContent.trim() };
                await fetch(`${API_URL}/${todo.id}`, {
                    method: 'PUT',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(updatedTodo)
                });
                await fetchTodos();
            }
        });

        // 삭제 버튼
        const deleteBtn = document.createElement('button');
        deleteBtn.textContent = '❌';
        deleteBtn.classList.add('delete-btn');
        deleteBtn.addEventListener('click', async () => {
            const isConfirmed = confirm('이 할 일을 정말 삭제하시겠습니까?');
            if (!isConfirmed) return;

            await fetch(`${API_URL}/${todo.id}`, { method: 'DELETE' });
            await fetchTodos();
        });

        li.appendChild(span);
        btnGroup.append(toggleBtn, editBtn, deleteBtn);
        li.appendChild(btnGroup);
        todoList.appendChild(li);
    };

    const addTodo = async () => {
        const content = todoInput.value.trim();
        if (!content) return;

        await fetch(API_URL, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ content, completed: false })
        });

        todoInput.value = '';
        await fetchTodos();
    };

    // 이벤트 핸들러도 async로 변경해서 addTodo 호출 결과를 await
    addButton.addEventListener('click',    async () => await addTodo());
    todoInput.addEventListener('keypress', async e => {
        if (e.key === 'Enter') {
            await addTodo();
        }
    });
    // 초기 로드 시에도 await
    await fetchTodos();
});
