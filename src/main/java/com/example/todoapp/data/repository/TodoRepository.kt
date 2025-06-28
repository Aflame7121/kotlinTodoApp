package com.example.todoapp.data.repository

import com.example.todoapp.data.dao.TodoDao
import com.example.todoapp.data.model.Todo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TodoRepository @Inject constructor(
    private val todoDao: TodoDao
) {
    suspend fun addTodo(todo: Todo): Long {
        return todoDao.insertTodo(todo)
    }

    fun getTodoById(todoId: Long): Flow<Todo?> {
        return todoDao.getTodoById(todoId)
    }
}