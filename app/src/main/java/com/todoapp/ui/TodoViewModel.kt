package com.todoapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.todoapp.data.Todo

class TodoViewModel : ViewModel() {
    // Placeholder for todo items
    private val _todoItems = MutableLiveData<List<Todo>>(emptyList())
    val todoItems: LiveData<List<Todo>> = _todoItems
}